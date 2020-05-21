package com.music;

import java.util.List;

public class AVTerminal {

    public static void main(String[] args) {
        String dir = "C:\\mani\\video\\x";
        FFmpegController controller = new FFmpegController();
        List<String> libx265 = controller.encode(dir, 21, "libx265", "-1:-1");
        libx265.forEach(System.out::println);

    }
}
