package com.music;

import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FFmpegController {

    private IAudioVideo iav = new FFmpeg("ffmpeg", OS.getEnv());

    private List<String> doLoop(List<File> files, Function<File, String> f) {
        List<String> cmds = files.stream()
                .map(f)
                .collect(Collectors.toList());
        return cmds;
    }

    public List<String> encode(String dirOrFile, int crf, String lib, String resolution) {
        List<File> files = getFilteredFiles(dirOrFile, IAudioVideo::isValidVideoFileExtension);
        List<String> cmds = files.stream()
                .map(e -> iav.encode(e, iav.replaceExtension(e,"_" + lib + ".mkv"), crf, lib, resolution))
                .collect(Collectors.toList());
        return cmds;
    }

    public List<String> volume(String dirOrFile, int decible) {
        List<File> files = getFilteredFiles(dirOrFile, IAudioVideo::isValidVideoFileExtension);
        List<String> cmds = doLoop(files, e -> iav.volume(e, iav.replaceExtension(e,"_v" + decible + ".mkv"), decible));
        return cmds;
    }

    public List<String> mp3ToM4a(String dirOrFile) {
        List<File> files = getFilteredFiles(dirOrFile, IAudioVideo::isValidVideoFileExtension);
        List<String> cmds = doLoop(files, e -> iav.mp3toM4a(e, iav.replaceExtension(e,".m4a")));
        return cmds;
    }

    public String importSubtitles(String in, String srt, String language) {
        return iav.importSubtitles(new File(in), iav.replaceExtension(new File(in), "_en.mkv"), new IAudioVideo.Subtitles(new File(srt), language));
    }

    public String cut(String in, String startTime, String endTime) {
        LocalTime sT = LocalTime.parse(startTime);
        LocalTime eT = LocalTime.parse(endTime);
        Duration.between(eT, sT);
        return iav.cut(new File(in), iav.replaceExtension(new File(in), "_cut.mkv"), startTime, IAudioVideo.timeDIff(startTime, endTime));
    }

    @Nullable
    private List<File> getFilteredFiles(String dirOrFile, Predicate<File> fileFilter) {
        File base = new File(dirOrFile);
        List<File> files = new ArrayList<>();
        files.add(base);
        if(base.isDirectory()) {
            try (Stream<Path> walk = Files.walk(Paths.get(dirOrFile))) {
                files = walk.filter(Files::isRegularFile)
                        .map(Path::toFile)
                        .filter(fileFilter)
                        .collect(Collectors.toList());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return files;
    }

}
