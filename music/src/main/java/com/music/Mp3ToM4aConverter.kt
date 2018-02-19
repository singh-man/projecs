package com.music

import org.jaudiotagger.audio.AudioFile
import org.jaudiotagger.audio.AudioFileIO
import org.junit.Test
import org.utils.DirectoryUtils
import org.utils.FileUtils
import org.utils.LoggerUtil
import java.io.BufferedReader
import java.io.File
import java.io.IOException
import java.io.InputStreamReader
import java.util.*
import java.util.logging.Level
import java.util.logging.Logger

class Mp3ToM4aConverter {

    private val log: Logger

    init {
        log = LoggerUtil.getLogger(this.javaClass)
    }

    private fun exec(cmds: String): Int {
        val sb = StringBuilder()
        var proc: Process? = null
        try {
            proc = Runtime.getRuntime().exec(cmds)
            Thread.sleep(100*100)
        } catch (e: IOException) {
            log.log(Level.SEVERE, "Unable to start process for : " + cmds)
            return 0
        }

        log.log(Level.INFO, "Executed Command : " + cmds)

        val stdInput = BufferedReader(InputStreamReader(proc!!.inputStream))
        val stdError = BufferedReader(InputStreamReader(proc.errorStream))

        // read the output from the command
        var s: String? = null
        // Hangs in the case of NeroAAC
        //        try {
        //            while ((s = stdInput.readLine()) != null) {
        //                sb.append(s);
        //            }
        //        } catch (IOException e) {
        //            e.printStackTrace();
        //        }

        // read any errors from the attempted command
//        try {
//            while ((s = stdError.readLine()) != null) {
//                sb.append(s!!.replace(",".toRegex(), ">"))
//            }
//        } catch (e: IOException) {
//            log.log(Level.WARNING, "Might have completed successfully : " + e.message + " : " + sb.toString())
//        }

        //        log.log(Level.INFO, sb.toString()); // full output if required
        return 0
    }

    private fun mapSrcFileTagToDestFileTag(inFile: File, outFile: File): Int {
        val status = "%s,%s,%s,%s"
        var srcF: AudioFile? = null
        try {
            srcF = AudioFileIO.read(inFile)
        } catch (e: Exception) {
            log.log(Level.SEVERE, "Unable to access input file" + inFile.absolutePath + " : " + e.message)
            return -1
        }

        var destF: AudioFile? = null
        try {
            destF = AudioFileIO.read(outFile)
        } catch (e: Exception) {
            log.log(Level.SEVERE, "Unable to access output file" + outFile.absolutePath + " : " + e.message)
            return -1
        }

        val srcTag = srcF!!.tag
        val destTag = destF!!.tag
        val ah = srcF.audioHeader
        MusicMetaDataProcessor().audioFieldOpr.keys.stream().forEach { e ->
            try {
                destTag.setField(e, srcTag.getFirst(e))
            } catch (e1: Exception) {
                log.log(Level.WARNING, inFile.absolutePath + " : Problem in setting getting field: " + e + " : " + e1.message)
            }
        }
        try {
            destTag.setField(srcTag.firstArtwork)
        } catch (e: Exception) {
            log.log(Level.WARNING, inFile.absolutePath + " : Problem in setting getting Cover Art: " + e.message)
        }

        try {
            AudioFileIO.write(destF)
        } catch (/*CannotWriteException*/ e: Exception) {
            log.log(Level.SEVERE, "Unable to write output file" + outFile.absolutePath + " : " + e.message)
            return -1
        }

        return 0
    }

    @Test
    fun testMapMetaDataMp3ToM4a() {
        val mp3Dir = "C:\\Users\\m.singh\\Downloads\\nusrat"
        val m4aDir = "C:\\Users\\m.singh\\Downloads\\nusrat"

        val files = DirectoryUtils.listAllFilteredFiles(File(mp3Dir), ".mp3", ".m4a")
        for (f in files) {
            val m4aFile = f.absolutePath.replace(".mp3", ".m4a").replace(mp3Dir, m4aDir)
            mapSrcFileTagToDestFileTag(f, File(m4aFile))
        }
    }

    @Test
    fun testConvertMp3ToM4a() {
        val mp3Dir = "C:\\Users\\m.singh\\Downloads"
        val m4aDir = "C:\\Users\\m.singh\\Downloads"
        val mpg123 = "D:\\mani\\dev\\opt\\mpg123-1.21.0-x86-64\\mpg123.exe -w %s %s"
        val neroAAC = "D:\\mani\\dev\\opt\\NeroAACCodec-1.5.1\\win32\\neroAacEnc.exe -q 0.19 -hev2 -if %s -of %s"

        val result = ArrayList<String>()
        val files = DirectoryUtils.listAllFilteredFiles(File(mp3Dir), ".mp3", ".m4a")
        for (f in files) {
            if (f.name.contains(".m4a")) {
                try {
                    FileUtils.copyFileToDirectory(f, File(m4aDir))
                    continue
                } catch (e: IOException) {
                    log.log(Level.SEVERE, "Unable to copy m4a file" + f.absolutePath + " : " + e.message)
                    continue
                }

            }
            //mp3 convert to wave
            val wavFile = f.absolutePath.replace(".mp3", ".wav").replace(mp3Dir, m4aDir)
            File(wavFile.substring(0, wavFile.lastIndexOf("\\"))).mkdirs()
            exec(String.format(mpg123, wavFile, f.absolutePath))
            //wav to m4a
            val m4aFile = f.absolutePath.replace(".mp3", ".m4a").replace(mp3Dir, m4aDir)
            exec(String.format(neroAAC, wavFile, m4aFile))
            //mp3 tags to m4a
            mapSrcFileTagToDestFileTag(f, File(m4aFile))
            FileUtils.removeFile(File(wavFile))
        }
    }

    @Test
    fun test() {
        println("Hello")
        val mp3Dir = "D:\\mani\\music"
        val list = DirectoryUtils.listAllFiles(File(mp3Dir))
        list.forEach { e -> println(e.absolutePath) }
    }

    fun main(args: Array<String>) {
        println("Hello, World!")
    }
}
