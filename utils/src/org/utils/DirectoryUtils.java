package org.utils;

import org.junit.Test;

import java.io.File;
import java.io.FileFilter;
import java.util.*;
import java.util.stream.Collectors;

public class DirectoryUtils {

    private static List<File> listFileFilter(File srcDir, FileFilter filter) {
        if (!srcDir.isDirectory()) throw new IllegalArgumentException("Given path is not a directory : " + srcDir.getAbsolutePath());
        File[] files = srcDir.listFiles(filter);
        return Arrays.asList(files);
    }

    public static List<File> listSubDirectories(File srcDir) {
        return listFileFilter(srcDir, File::isDirectory);
    }

    @Test
    public void testListSubDirectories() {
        List<File> files = listSubDirectories(new File("D:\\tmp\\"));
        System.out.println("Total number of sub-directories : " + files.size());
        files.forEach(System.out::println);
    }

    public static List<File> listAllSubDirectories(File srcDir) {
        List<File> tmpDirList = listSubDirectories(srcDir);
        List<File> dirList = new ArrayList<>();
        dirList.addAll(tmpDirList);
        tmpDirList.forEach(f -> dirList.addAll(listAllSubDirectories(f)));
        return dirList;
    }

    @Test
    public void testListAllSubDirectories() {
        List<File> files = listAllSubDirectories(new File("D:\\tmp\\"));
        System.out.println("Total number of all sub-directories : " + files.size());
        files.forEach(System.out::println);
    }

    public static List<File> listAllDirectoriesIncludingThis(File srcDir) {
        List<File> dirList = listAllSubDirectories(srcDir);
        dirList.add(0, srcDir);
        return dirList;
    }

    @Test
    public void testListAllDirectoriesIncludingThis() {
        listAllDirectoriesIncludingThis(new File("D:\\tmp\\")).forEach(System.out::println);
    }

    public static List<File> listFiles(File srcDir) {
        return listFileFilter(srcDir, File::isFile);
    }

    @Test
    public void testListFiles() {
        List<File> files = listFiles(new File("D:\\tmp\\"));
        System.out.println("Total number of files : " + files.size());
        files.forEach(System.out::println);
    }

    public static List<File> listAllFiles(File srcDir) {
        List<File> fileList = new ArrayList<>();
        List<File> dirList = listAllDirectoriesIncludingThis(srcDir);
        dirList.forEach(f -> fileList.addAll(listFiles(f)));
        return fileList;
    }

    @Test
    public void testListAllFiles() {
        List<File> files = listAllFiles(new File("D:\\tmp\\"));
        System.out.println("Total no. of files = "+ files.size());
        files.forEach(System.out::println);
    }

    public static List<File> listAllFilteredFiles(File srcDir, String... exts) {
        List<String> extList = Arrays.asList(exts).stream().map(String::toLowerCase).collect(Collectors.toList());
        List<File> fileList = listAllFiles(srcDir);
        fileList = fileList.stream()
                .filter(e -> e.getName().contains(".") && extList.contains(e.getName().substring(e.getName().lastIndexOf(".")).toLowerCase()))
                .collect(Collectors.toList());
        return fileList;
    }

    @Test
    public void testListAllFilteredFiles() {
        listAllFilteredFiles(new File("D:\\tmp\\"), ".mp4", ".jpg").forEach(System.out::println);
    }

    public static Map<File, List<File>> getDirectoryMap(File srcDir) {
        Map<File, List<File>> dirMap = new HashMap<>();
        List<File> dirList = listAllDirectoriesIncludingThis(srcDir);
        dirList.forEach(f -> dirMap.put(f, listFiles(f)));
        return dirMap;
    }

    @Test
    public void testGetDirectoryMap() {
        getDirectoryMap(new File("D:\\tmp\\")).forEach((k,v) -> System.out.println(k + " :: " + v));
    }
}
