package org.utils;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.List;

public class NewFileUtils {

    /**
     * Actual file copy takes place
     *
     * @param in: Fully qualified src file
     * @param out: Fully qualified dest file; will not create any non-existing
     * directories
     * @return
     */
    @Deprecated
    private static boolean copy_v1(FileChannel in, FileChannel out) {

        ByteBuffer buf = ByteBuffer.allocate(48);
        int count = 0;
        try {
            int bytesRead = in.read(buf);

            while (bytesRead != -1) {
                count += out.write(buf);
                buf.clear();
                bytesRead = in.read(buf);
            }

            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return (count > 1) ? true : false;
    }

    /**
     * Actual file copy takes place
     *
     * @param in: Fully qualified src file
     * @param out: Fully qualified dest file; will not create any non-existing
     * directories
     * @return
     */
    private static boolean copy_v2(FileChannel in, FileChannel out) {
        try {
            long position = 0;
            long count = in.size();

            int chunk = 100 * 1024 * 1024; //restrict the max. file read to 100Mb at a time
            while (position < in.size()) {
                position += in.transferTo(position, chunk, out);
            }
            //long n = in.transferTo(position, count, out);

            //return (n > 1) ? true:false;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Copy File to a directory; creates directory if it doesn't exist.
     *
     * @param srcFile
     * @param destDir
     * @return
     * @throws IOException
     */
    public static boolean copyFileToDirectory(File srcFile, File destDir) throws IOException {
        if (destDir.isFile()) {
            throw new IllegalArgumentException("Destination is a file");
        }
        return copyFile(srcFile, new File(destDir, srcFile.getName()));
    }

    /**
     * Copy the srcFile to dest. The directories upto dest will be created if
     * they don't exist;
     *
     * Failure: 1. If src is not a file or doesn't exist 2. If dest already
     * exist or can't write to destination. 3. If src is same as dest
     *
     * Note: Can be tricked to rename a file; in the existing src or to the new
     * dest
     *
     * @param srcFile: A valid concrete file
     * @param dest: considered a valid concrete file(if dir is given; an
     * extension-less file of the given dir will be created)
     * @return
     * @throws IOException
     */
    public static boolean copyFile(File srcFile, File dest) throws IOException {
        if (!srcFile.exists() || srcFile.isDirectory()) {
            throw new IllegalArgumentException("Source doesn't exist or is a Directory: " + srcFile.getAbsolutePath());
        }
        if (null != dest.getParentFile() && !dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        if (dest.exists() && !dest.canWrite()) {
            throw new RuntimeException("Unable to open file: " + dest + " for writing");
        }
        if (srcFile.getCanonicalPath().equals(dest.getCanonicalPath())) {
            throw new RuntimeException("Unable to write source: " + srcFile + " itself");
        }

        FileChannel input = new RandomAccessFile(srcFile, "rw").getChannel();
        FileChannel output = new RandomAccessFile(dest, "rw").getChannel();

        return copy_v2(input, output);
    }

    /**
     * Recursively copies the directories and its content
     *
     * @param srcDir
     * @param destDir
     * @return
     * @throws IOException
     */
    public static void copyDirectory(File srcDir, File destDir) throws IOException {
        if (srcDir.isFile()) {
            throw new RuntimeException("Given source is a file");
        }
        List<File> filesList = DirectoryUtils.listAllFiles(srcDir);
        for (File file : filesList) {
            String destFile = slashReplacer(file).replaceAll(slashReplacer(srcDir), slashReplacer(destDir));
            File newDestination = new File(destFile);
            copyFile(file, newDestination);
        }
    }

    private static String slashReplacer(File file) {
        /*
		 * Strange case:
		 * First of all, both Java and regex use backslash as an escape character. 
		 * To get one backslash after escapes, you need four of them.
		 * 
		 * 1. \ considered a escape character in java
		 * 2. \\ used by regex pattern
		 * 3. \\\\ this is required to handle a single \
         */
        return file.getAbsolutePath().replaceAll("\\\\", "/");
    }

    /**
     * Recursively deletes the directory and it contents.
     *
     * @param srcDirectory
     * @return
     */
    public static void deleteDirectory(File srcDirectory) {
        if (srcDirectory.isFile()) {
            throw new IllegalArgumentException("Given path is not a directory");
        }

        cleanDirectory(srcDirectory);

        if (!srcDirectory.delete()) {
            throw new RuntimeException("Unable to delete directory");
        }
    }

    /**
     * Cleans all the content of a directory without deleting it
     *
     * @param srcDirectory
     */
    public static void cleanDirectory(File srcDirectory) {
        File[] files = srcDirectory.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                deleteDirectory(file);
            } else {
                if (!file.delete()) {
                    throw new RuntimeException("Unable to delete file");
                }
            };
        }
    }

    public static void main(String[] args) {
        try {
            Long t1 = System.currentTimeMillis();
            NewFileUtils.copyFileToDirectory(new File("C:/manish/mov/eng/Das.Boot.[The.Boat].1981.Director's.Cut.DVDRip.H264.AAC.Gopo.mp4"), new File("C:/zzzz"));
            System.out.println("Time taken to upload (in secs): " + (int) (System.currentTimeMillis() - t1) / 1000);
            //NewFileUtils.deleteDirectory(new File("C:/toBeDeleted/a"));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
