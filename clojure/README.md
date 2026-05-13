# z_clojure

This module is a standalone Clojure project managed with Leiningen. It does not use Gradle.

## Prerequisites

- Java installed and available on `PATH`
- Leiningen installed and available as `lein`

Check the tools:

```sh
java -version
lein version
```

## Install Dependencies

Run commands from this directory:

```sh
cd clojure
lein deps
```

The project dependencies are defined in `project.clj`.

## Run a REPL

```sh
lein repl
```

Example REPL usage:

```clojure
(require '[z-clojure.core :as core])
(core/foo "Manish")
```

## Run a Namespace with `-main`

This project does not define a default `:main` namespace in `project.clj`, so run executable namespaces explicitly with `-m`.

Examples:

```sh
lein run -m man.man
lein run -m musicScripts.ffmpeg
lein run -m musicScripts.nero
lein run -m avScripts.avScripts_1
```

## Run Tests

If tests are added under the standard Leiningen test paths, run:

```sh
lein test
```
