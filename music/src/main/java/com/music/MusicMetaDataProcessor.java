package com.music;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.AudioHeader;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.CannotWriteException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagException;
import org.junit.Test;
import org.utils.DirectoryUtils;
import org.utils.FileUtils;
import org.utils.LoggerUtil;
import org.utils.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author msingh
 */
public class MusicMetaDataProcessor {

    private Logger log;
    public Map<FieldKey, Opr> audioFieldOpr;

    private interface Opr {
        String fixFieldText(File file, String text) throws Exception;
        static String getProperText(String text) throws Exception { return StringUtils.convertToProperCase(StringUtils.camelCaseToTitleCase(text)); }
    }

    public MusicMetaDataProcessor()
    {
        log = LoggerUtil.getLogger(this.getClass());

        audioFieldOpr = new LinkedHashMap<FieldKey, Opr>();
        audioFieldOpr.put(FieldKey.TITLE, new Opr() {
            @Override
            public String fixFieldText(File file, String text) throws Exception {
                text = text == null ? "" : text.trim();
                if(text.isEmpty()) {
                    String[] name = file.getName().split("_");
                    text = name.length > 1 ? name[1] : name[0];
                }
                return Opr.getProperText(text);
            }
        });
        audioFieldOpr.put(FieldKey.ARTIST, new Opr() {
            @Override
            public String fixFieldText(File file, String text) throws Exception {
                String[] artists = text.contains(";") ? text.split(";") : text.contains("/") ? text.split("/") : text.contains(",") ? text.split(",") : new String[]{text};
                StringBuilder sb = new StringBuilder();
                for(String str : artists) {
                    sb.append(Opr.getProperText(str.trim())).append("/");
                }
                return sb.deleteCharAt(sb.length()-1).toString();
            }
        });
        audioFieldOpr.put(FieldKey.ALBUM, new Opr() {
            @Override
            public String fixFieldText(File file, String text) throws Exception {
                text = text == null ? "" : text.trim();
                if(text.isEmpty() && file.getName().contains("_")) {
                    text = file.getName().split("_")[0];
                }
                return Opr.getProperText(text);
            }
        });
        audioFieldOpr.put(FieldKey.GENRE, new Opr() {
            @Override
            public String fixFieldText(File file, String text) throws Exception {
                return Arrays.asList("blues","other","others","blue").contains(text.toLowerCase()) ? "" : Opr.getProperText(text);
            }
        });
        audioFieldOpr.put(FieldKey.LYRICIST, new Opr() {
            @Override
            public String fixFieldText(File file, String text) throws Exception {
                return Opr.getProperText(text);
            }
        });
        audioFieldOpr.put(FieldKey.COMPOSER, new Opr() {
            @Override
            public String fixFieldText(File file, String text) throws Exception {
                return Opr.getProperText(text);
            }
        });
        audioFieldOpr.put(FieldKey.ALBUM_ARTIST, new Opr() {
            @Override
            public String fixFieldText(File file, String text) {
                return text;
            }
        });
        audioFieldOpr.put(FieldKey.TRACK, new Opr() {
            @Override
            public String fixFieldText(File file, String text) {
                return text;
            }
        });
        audioFieldOpr.put(FieldKey.YEAR, new Opr() {
            @Override
            public String fixFieldText(File file, String text) {
                return text;
            }
        });
        audioFieldOpr.put(FieldKey.COMMENT, new Opr() {
            @Override
            public String fixFieldText(File file, String text) throws Exception {
                return Opr.getProperText(text);
            }
        });
        audioFieldOpr.put(FieldKey.PRODUCER, new Opr() {
            @Override
            public String fixFieldText(File file, String text) {
                return text;
            }
        });


        audioFieldOpr = Collections.unmodifiableMap((audioFieldOpr));
    }

    private void setFieldInFile(File file, FieldKey key, String text) throws Exception {
        AudioFile srcF = AudioFileIO.read(file);
        Tag srcTag = srcF.getTag();
        AudioHeader ah = srcF.getAudioHeader();
        srcTag.setField(key, text);
        System.out.println("For : " + key + " : " + text);
        AudioFileIO.write(srcF);
    }

    private void setAlbumNameAsPerFileName(String srcDir) throws Exception {
        List<File> inFiles = DirectoryUtils.listAllFilteredFiles(new File(srcDir), ".mp3", ".m4a");
        inFiles.forEach(e -> {
            try {
                setFieldInFile(e, FieldKey.ALBUM, e.getName().split("_")[0]);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });
    }

    private void formatFields(String srcDir) {
        List<File> inFiles = DirectoryUtils.listAllFilteredFiles(new File(srcDir), ".m4a");
        List<String> fMods = new ArrayList<>();
        for (File fi : inFiles) {
            AudioFile srcF;
            try {
                srcF = AudioFileIO.read(fi);
                Tag srcTag = srcF.getTag();
                if (srcTag.getFirst(FieldKey.ARTIST).trim().equalsIgnoreCase("Udit Narayan, Sadhna Sargam")) {
                    srcTag.setField(FieldKey.ARTIST, "Sadhna Sargam, Udit Narayan");
                    AudioFileIO.write(srcF);
                    fMods.add(fi.getAbsolutePath());
                }
            } catch (CannotWriteException | CannotReadException | IOException | TagException | ReadOnlyFileException | InvalidAudioFrameException ex) {
                log.log(Level.SEVERE, null, ex);
            }
        }
        fMods.forEach(System.out::println);
    }

    private void removeCoverArt(String srcDir) {
        List<File> inFiles = DirectoryUtils.listAllFilteredFiles(new File(srcDir), ".mp3");
        List<String> fMods = new ArrayList<>();
        for (File fi : inFiles) {
            AudioFile srcF;
            try {
                srcF = AudioFileIO.read(fi);
                Tag srcTag = srcF.getTag();
                srcTag.deleteField(FieldKey.COVER_ART);
                AudioFileIO.write(srcF);
                fMods.add(fi.getAbsolutePath());
            } catch (CannotWriteException | CannotReadException | IOException | TagException | ReadOnlyFileException | InvalidAudioFrameException ex) {
                log.log(Level.SEVERE, null, ex);
            }
        }
        fMods.forEach(System.out::println);
    }

    @Test
    public void testRemoveCoverArt() {
        removeCoverArt("C:\\Users\\msingh\\Downloads");
    }

    private void listNamesWhichCOMandAT(String srcDir) throws Exception {
        List<File> inFiles = DirectoryUtils.listAllFilteredFiles(new File(srcDir), ".mp3");
        for (File fi : inFiles) {
            AudioFile srcF = AudioFileIO.read(fi);
            Tag srcTag = srcF.getTag();
            AudioHeader ah = srcF.getAudioHeader();
            StringBuilder sb = new StringBuilder();
            audioFieldOpr.keySet().forEach(e -> sb.append(srcTag.getFirst(e).trim()));

            if (sb.toString().contains("@") || sb.toString().contains("com")) {
                System.out.println(fi.getAbsolutePath());
            }
        }
    }

    @Test
    public void testDumpMusicMetaDataMP3() {
        List<String> problems = new ArrayList<>();
        String srcDir = "D:\\mani\\music\\mobile\\hindi";
        String origCSVFile = "D:\\tmp\\gajal.csv";
        String modCsvFile = "D:\\tmp\\gajal1.csv";
        FileUtils.removeFile(new File(origCSVFile));
        FileUtils.removeFile(new File(modCsvFile));
        List<File> inFiles = DirectoryUtils.listAllFilteredFiles(new File(srcDir), ".mp3",".m4a");
        for (File fi : inFiles) {
            AudioFile srcF = null;
            try {
                srcF = AudioFileIO.read(fi);
                Tag srcTag = srcF.getTag();
                AudioHeader ah = srcF.getAudioHeader();
                StringBuilder origText = new StringBuilder(fi.getAbsolutePath());
                StringBuilder modText = new StringBuilder(fi.getAbsolutePath());
                audioFieldOpr.keySet().forEach(e -> origText.append(",").append(srcTag.getFirst(e).replaceAll(",", "/").trim()));
                audioFieldOpr.keySet().forEach(e -> {
                    try {
                        modText.append(",").append(audioFieldOpr.get(e).fixFieldText(fi, srcTag.getFirst(e).replaceAll(",", "/").trim()));
                    } catch (Exception e1) {
                        log.log(Level.SEVERE, "Unable to process field: " + fi.getAbsolutePath() + " : " + e.name() + " : " + e1.getMessage());
                    }
                });
                FileUtils.writeToFile(origText.toString(), new File(origCSVFile));
                FileUtils.writeToFile(modText.toString(), new File(modCsvFile));
            } catch (Exception e) {
                log.log(Level.SEVERE, "Unable to process file: " + fi.getAbsolutePath() + " : " + e.getMessage());
            }
        }
        problems.forEach(System.out::println);
    }

    @Test
    public void testWriteMusicMetaDataFromDump() {
        List<String> problems = new ArrayList<>();
        String csvFile = "D:\\tmp\\mus.csv";
        List<String> rows = FileUtils.readFile(new File(csvFile));
        for (String row : rows) {
            String[] rr = row.split(",");
            try {
//                AudioFile srcF = AudioFileIO.read(new File(rr[0].replace("hddMusic", "mobile").replace(".mp3", ".m4a")));
                AudioFile srcF = AudioFileIO.read(new File(rr[0]));
                Tag srcTag = srcF.getTag();
                int i = 1;
                for(FieldKey key : audioFieldOpr.keySet()) {
                    String value = "";
                    try {
                        value = rr[i++];
                    } catch (ArrayIndexOutOfBoundsException e) {
                        log.log(Level.INFO, "Array Index issue after: " + rr[0] + " : " + key + " : " + e.getMessage());
                    }
                    try{
                        srcTag.setField(key, value);
                    }catch (Exception e) {
                        log.log(Level.SEVERE, "Unable to set field: " + rr[0] + " : " + key + " : " + e.getMessage());
                    }
                }
                AudioFileIO.write(srcF);
            } catch (Exception e) {
                log.log(Level.SEVERE, "Unable to process file: " + rr[0] + " : " + e.getMessage());
            }

        }
        problems.forEach(System.out::println);
    }

    @Test
    public void test() throws TagException, ReadOnlyFileException, CannotReadException, InvalidAudioFrameException, IOException, CannotWriteException {
        AudioFile destFile = AudioFileIO.read(new File("D:/mani/video/lifeStyle_sidhuMooseWala.m4a"));
        Tag srctag = AudioFileIO.read(new File("D:/mani/video/lifeStyle_sidhuMooseWala.mp3")).getTag();
        destFile.getTag().setField(FieldKey.TITLE, srctag.getFirst(FieldKey.TITLE));
        AudioFileIO.write(destFile);
    }
}
