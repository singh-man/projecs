package com.music;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FFmpeg implements IAudioVideo {

    private String library;
    private OS os;

    public FFmpeg(String library, OS os) {
        this.library = library;
        this.os = os;
    }

    private String toWav(File in, File out) {
        return String.format("%s -i %s %s", library, addDoubleQuotes(in.getAbsolutePath()), addDoubleQuotes(out.getAbsolutePath()));
    }

    private String mp3ToM4a_buildInAAC(File in, File out) {
        StringBuilder sb = new StringBuilder();
        File wavFile = replaceExtension(in, ".wav");
        sb.append(toWAV(in, wavFile))
                .append(" && ")
                .append(String.format("%s -i %s -c:a aac -b:a 48k %s", library, addDoubleQuotes(wavFile.getAbsolutePath()), addDoubleQuotes(out.getAbsolutePath())))
                .append(" && ")
                .append("rm " + addDoubleQuotes(wavFile.getAbsolutePath()));
        return sb.toString();
    }

    private String toWAV(File in, File out) {
        return String.format("%s -i %s %s", library, addDoubleQuotes(in.getAbsolutePath()), addDoubleQuotes(out.getAbsolutePath()));
    }

    private String mp3ToM4a_libfdk_aac(File in, File out) {
        return String.format("%s -i %s -c:a libfdk_aac -profile:a aac_he_v2 -b:a 48k %s", library, addDoubleQuotes(in.getAbsolutePath()), addDoubleQuotes(out.getAbsolutePath()));
    }

    @Override
    public String volume(File in, File out, int db) {
        return String.format("%s -i %s -map 0 -c copy -c:a aac -af \"volume=%sdB\" %s", library, addDoubleQuotes(in.getAbsolutePath()), db, addDoubleQuotes(out.getAbsolutePath()));
    }

    @Override
    public String mp3toM4a(File in, File out) {
        if (os == OS.LINUX)
            return mp3ToM4a_libfdk_aac(in, out);
        return mp3ToM4a_buildInAAC(in, out);
    }

    @Override
    public String encode(File in, File out, int crf, String encoder, String resolution) {
        return String.format("%s -i %s -vf scale=%s -map 0 -c copy -c:v %s -preset medium -crf %s %s",
                library, addDoubleQuotes(in.getAbsolutePath()), resolution, encoder, crf, addDoubleQuotes(out.getAbsolutePath()));
    }

    @Override
    public String cut(File in, File out, String startTime, String duration) {
        return String.format("%s -ss %s -i %s -t %s -c copy -avoid_negative_ts make_zero %s",
                library, startTime, addDoubleQuotes(in.getAbsolutePath()), duration, addDoubleQuotes(out.getAbsolutePath()));
    }

    @Override
    public String concat(File in, File out) {
        return String.format("%s -f concat -i %s -c copy %s", library, addDoubleQuotes(in.getAbsolutePath()), addDoubleQuotes(out.getAbsolutePath()));
    }

    @Override
    public String importSubtitles(File in, File out, Subtitles subtitles) {
        Map<Integer, String> m = new HashMap<>();
        m.put(1, "%s -i %s -sub_charenc UTF-8 -i %s -map 0:v -map 0:a -c copy -map 1 -c:s:0 srt -metadata:s:s:0 language=en %s");
        m.put(2, "%s -i %s -c:a aac -vf subtitles=%s %s");
        String cmd = null;
        if(subtitles.file == null || in.getAbsolutePath().equals(subtitles.file.getAbsolutePath()))
            cmd = String.format(m.get(2), library, addDoubleQuotes(in.getAbsolutePath()), addDoubleQuotes(in.getAbsolutePath()), addDoubleQuotes(out.getAbsolutePath()));
        else
            cmd = String.format(m.get(1), library, addDoubleQuotes(in.getAbsolutePath()), addDoubleQuotes(subtitles.file.getAbsolutePath()), addDoubleQuotes(out.getAbsolutePath()));
        return cmd;
    }

    /**
     * ffmpeg -i "in.mp4" -sub_charenc UTF-8 -i "en.srt" -i "hi.srt"
     * -map 0 -c copy -map 1 -map 2
     * -c:s:0 srt -metadata:s:s:0 language=en
     * -c:s:1 srt -metadata:s:s:1 language=hi
     * "out.mp4"
     * @param in
     * @param out
     * @param subtitles
     * @return
     */
    @Override
    public String importSubtitles(File in, File out, Subtitles... subtitles) {
        StringBuilder x = new StringBuilder();
        x.append(String.format("%s -i %s -sub_charenc UTF-8", library, addDoubleQuotes(in.getAbsolutePath())));
        for(Subtitles s : subtitles) x.append(" -i " + addDoubleQuotes(s.file.getAbsolutePath()));
        x.append(" -map 0 -c copy");
        for(int i =1; i <= subtitles.length; i++) x.append(" " + String.format("-map %s", i));
        for(int i =0; i < subtitles.length; i++) {
            x.append(" " + String.format("-c:s:%s srt -metadata:s:s:%s language=%s", i, i, subtitles[i].language));
        }
        x.append(" " + addDoubleQuotes(out.getAbsolutePath()));
        return x.toString();
    }

    public static List<String> getEncoders() {
        return Collections.unmodifiableList(Arrays.asList("libx264", "libx265", "libaom-av1", "libvpx-vp9"));
    }

}
