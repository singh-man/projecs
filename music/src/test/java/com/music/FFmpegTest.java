package com.music;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class FFmpegTest {

    private IAudioVideo ffmpeg;

    @Before
    public void setUp() throws Exception {
        ffmpeg = new FFmpeg("ffmpeg", OS.getEnv());
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void volume() {
        String val = ffmpeg.volume(new File("//in.mp4"), new File("//out.mp4"), 11);
        Assert.assertEquals("ffmpeg -i \"\\\\in.mp4\" -map 0 -c copy -c:a aac -af \"volume=11dB\" \"\\\\out.mp4\"",
                val);
    }

    @Test
    public void mp3toM4a_windows() {
        String val = ffmpeg.mp3toM4a(new File("//in.mp3"), new File("//out.m4a"));
        Assert.assertEquals("ffmpeg -i \"\\\\in.mp3\" \"\\\\in.wav\" && ffmpeg -i \"\\\\in.wav\" -c:a aac -b:a 48k \"\\\\out.m4a\" && rm \"\\\\in.wav\"",
                val);
    }

    @Test
    public void mp3toM4a_linux() {
        String val = new FFmpeg("ffmpeg", OS.LINUX).mp3toM4a(new File("//in.mp3"), new File("//out.m4a"));
        Assert.assertEquals("ffmpeg -i \"\\\\in.mp3\" -c:a libfdk_aac -profile:a aac_he_v2 -b:a 48k \"\\\\out.m4a\"",
                val);
    }

    @Test
    public void encode() {
        String val = ffmpeg.encode(new File("//in.mp4"), new File("//out.mp4"), 11, FFmpeg.getEncoders().get(2), IAudioVideo.videoResolution.get(2));
        Assert.assertEquals("ffmpeg -i \"\\\\in.mp4\" -vf scale=-1:360 -map 0 -c copy -c:v libaom-av1 -preset medium -crf 11 \"\\\\out.mp4\"",
                val);
    }

    @Test
    public void cut() {
        IAudioVideo.timeDIff("00:10:20", "00:14:47");
        String val = ffmpeg.cut(new File("//in.mp4"), new File("//out.mp4"), "00:10:20", "00:04:27");
        Assert.assertEquals("ffmpeg -ss 00:10:20 -i \"\\\\in.mp4\" -t 00:04:27 -c copy -avoid_negative_ts make_zero \"\\\\out.mp4\"",
                val);
    }

    @Test
    public void concat() {
        String val = ffmpeg.concat(new File("//in.txt"), new File("//out.mp4"));
        Assert.assertEquals("ffmpeg -f concat -i \"\\\\in.txt\" -c copy \"\\\\out.mp4\"",
                val);
    }

    @Test
    public void importSubtitles() {
        String val = ffmpeg.importSubtitles(new File("//in.mp4"), new File("//out.mp4"), new IAudioVideo.Subtitles(new File("//in.srt"), "en"));
        Assert.assertEquals("ffmpeg -i \"\\\\in.mp4\" -sub_charenc UTF-8 -i \"\\\\in.srt\" -map 0:v -map 0:a -c copy -map 1 -c:s:0 srt -metadata:s:s:0 language=en \"\\\\out.mp4\"",
                val);
    }

    @Test
    public void importMultipleSubtitles() {
        IAudioVideo.Subtitles[] subtitles = {new IAudioVideo.Subtitles(new File("//en.srt"), "en"), new IAudioVideo.Subtitles(new File("//hi.srt"), "hi")};
        String val = ffmpeg.importSubtitles(new File("//in.mp4"), new File("//out.mp4"), subtitles);
        Assert.assertEquals("ffmpeg -i \"\\\\in.mp4\" -sub_charenc UTF-8 -i \"\\\\en.srt\" -i \"\\\\hi.srt\" -map 0 -c copy -map 1 -map 2 -c:s:0 srt -metadata:s:s:0 language=en -c:s:1 srt -metadata:s:s:1 language=hi \"\\\\out.mp4\"",
                val);
    }

    @Test
    public void sample_1() {
        List<Function<File, String>> l = new ArrayList<>();
        l.add(f -> ffmpeg.concat(f, f));
        l.add(f -> ffmpeg.importSubtitles(f, f, new IAudioVideo.Subtitles(f, "en")));
        l.add(f -> ffmpeg.cut(f, f, "", ""));

        File in = new File("//in.mp4");

        l.stream().map(e -> e).forEach(System.out::println);
        l.stream().map(e -> e.apply(in)).forEach(System.out::println);
    }

    @Test
    public void sample_2() {
        File in = new File("//in.mp4");
        File out = new File("//out.mp4");

        List<Function<File, String>> l = new ArrayList<>();
        l.add(f -> ffmpeg.concat(f, out));
        l.add(f -> ffmpeg.importSubtitles(f, out, new IAudioVideo.Subtitles(f, "en")));
        l.add(f -> ffmpeg.cut(f, out, "", ""));

        l.stream().map(e -> e.apply(in)).forEach(System.out::println);
    }
}