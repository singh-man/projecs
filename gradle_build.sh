#!/usr/bin/env bash

ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
FAILED_MODULES=()
MODULE_DIRS=()
INCLUDED_BUILD_MODULES=()
PARALLEL=false

usage() {
    cat <<EOF
Usage: $(basename "$0") [flags]

Builds each immediate child directory that contains build.gradle.
Parallel mode builds any immediate child modules referenced by includeBuild first,
then builds the remaining modules concurrently.

Flags:
  -p    Run module builds in parallel
  -h    Show this help message

Examples:
  $(basename "$0")
  $(basename "$0") -p
EOF
}

collect_modules() {
    local module_dir

    for module_dir in "$ROOT_DIR"/*/; do
        [ -d "$module_dir" ] || continue
        [ -f "$module_dir/build.gradle" ] || continue

        MODULE_DIRS+=("$module_dir")
    done
}

contains_module() {
    local needle="$1"
    local module

    shift

    for module in "$@"; do
        [ "$module" = "$needle" ] && return 0
    done

    return 1
}

collect_included_build_modules() {
    local module_dir
    local settings_file
    local include_path
    local include_dir
    local module_name

    for module_dir in "${MODULE_DIRS[@]}"; do
        settings_file="$module_dir/settings.gradle"
        [ -f "$settings_file" ] || continue

        while IFS= read -r include_path; do
            include_dir="$(cd "$module_dir/$include_path" 2>/dev/null && pwd)" || continue

            case "$include_dir/" in
                "$ROOT_DIR"/*/)
                    module_name="$(basename "$include_dir")"
                    if ! contains_module "$module_name" "${INCLUDED_BUILD_MODULES[@]}"; then
                        INCLUDED_BUILD_MODULES+=("$module_name")
                    fi
                    ;;
            esac
        done < <(sed -n "s/^[[:space:]]*includeBuild[[:space:]]*['\"]\\([^'\"]*\\)['\"].*/\\1/p" "$settings_file")
    done
}

build_module() {
    local module_dir="$1"
    local module_name
    module_name="$(basename "$module_dir")"

    echo "=== Building $module_name ==="

    if (
        cd "$module_dir" || exit 1
        gradle clean build
    ); then
        echo "=== Finished $module_name ==="
        return 0
    fi

    echo "=== Failed $module_name ==="
    return 1
}

build_sequentially() {
    local module_dir
    local module_name

    for module_dir in "${MODULE_DIRS[@]}"; do
        module_name="$(basename "$module_dir")"

        if ! build_module "$module_dir"; then
            FAILED_MODULES+=("$module_name")
        fi

        echo
    done
}

build_in_parallel() {
    local module_dir
    local module_name
    local pid
    local pids=()
    local module_names=()
    local index

    for module_dir in "${MODULE_DIRS[@]}"; do
        module_name="$(basename "$module_dir")"
        if contains_module "$module_name" "${INCLUDED_BUILD_MODULES[@]}"; then
            if ! build_module "$module_dir"; then
                FAILED_MODULES+=("$module_name")
            fi
            echo
        fi
    done

    for module_dir in "${MODULE_DIRS[@]}"; do
        module_name="$(basename "$module_dir")"
        contains_module "$module_name" "${INCLUDED_BUILD_MODULES[@]}" && continue

        build_module "$module_dir" &
        pid=$!

        pids+=("$pid")
        module_names+=("$module_name")
        echo "=== Started $module_name as PID $pid ==="
    done

    for index in "${!pids[@]}"; do
        if wait "${pids[$index]}"; then
            :
        else
            FAILED_MODULES+=("${module_names[$index]}")
        fi
    done
}

while getopts ":ph" opt; do
    case "$opt" in
        p)
            PARALLEL=true
            ;;
        h)
            usage
            exit 0
            ;;
        \?)
            echo "Unknown flag: -$OPTARG" >&2
            usage >&2
            exit 2
            ;;
    esac
done

shift $((OPTIND - 1))

if [ "$#" -gt 0 ]; then
    echo "Unexpected arguments: $*" >&2
    usage >&2
    exit 2
fi

collect_modules
collect_included_build_modules

if [ "${#MODULE_DIRS[@]}" -eq 0 ]; then
    echo "No Gradle modules found."
    exit 0
fi

if [ "$PARALLEL" = true ]; then
    build_in_parallel
else
    build_sequentially
fi

if [ "${#FAILED_MODULES[@]}" -gt 0 ]; then
    echo
    echo "Failed modules:"
    printf ' - %s\n' "${FAILED_MODULES[@]}"
    exit 1
fi

if [ "$PARALLEL" = true ]; then
    echo
    echo "All Gradle modules built successfully in parallel."
else
    echo "All Gradle modules built successfully."
fi
