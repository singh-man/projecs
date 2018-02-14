/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.music

import org.jaudiotagger.audio.AudioFile
import org.jaudiotagger.audio.AudioFileIO
import org.jaudiotagger.audio.exceptions.CannotReadException
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException
import org.jaudiotagger.tag.*
import org.junit.Test
import org.utils.DirectoryUtils
import org.utils.timer.StopWatch
import java.io.File
import java.io.IOException
import java.util.*
import java.util.function.Consumer

/**
 * @author msingh
 */
class MusicMetaDataMapper {


    @Throws(InvalidAudioFrameException::class, TagException::class, KeyNotFoundException::class)
    private fun mapSrcTagToDestTag(outF: File, srcTag: Tag, problems: MutableList<String>): String {
        val fields = Arrays.asList(FieldKey.TITLE, FieldKey.ARTIST, FieldKey.ALBUM, FieldKey.YEAR, FieldKey.GENRE, FieldKey.COMMENT, FieldKey.LYRICIST, FieldKey.COMPOSER)
        if (outF.exists()) {
            var destF: AudioFile? = null
            try {
                destF = AudioFileIO.read(outF)
            } catch (e: CannotReadException) {
                throw RuntimeException("Unable to read : " + outF.absolutePath + " : " + e.message)
            } catch (e: IOException) {
                throw RuntimeException("Unable to read : " + outF.absolutePath + " : " + e.message)
            } catch (e: ReadOnlyFileException) {
                throw RuntimeException("Unable to read : " + outF.absolutePath + " : " + e.message)
            }

            val destTag = destF!!.tag

            fields.forEach { e ->
                try {
                    if (destTag.getFirst(e).trim { it <= ' ' } != srcTag.getFirst(e).trim { it <= ' ' }) {
                        destTag.setField(e, srcTag.getFirst(e))
                    }
                } catch (e1: NullPointerException) {
                    problems.add("unable to add tag : " + outF.absolutePath + " : " + e + " : " + e1.message)
                } catch (e1: FieldDataInvalidException) {
                    problems.add("unable to add tag : " + outF.absolutePath + " : " + e + " : " + e1.message)
                }
            }

            try {
                destTag.setField(srcTag.firstArtwork)
            } catch (e: NullPointerException) {
                problems.add("unable to add artwork : " + outF.absolutePath + " : " + e.message)
            }

            try {
                AudioFileIO.write(destF)
                return outF.absolutePath
            } catch (/*CannotWriteException*/ e: Exception) {
                throw RuntimeException("Unable to write : " + outF.absolutePath + " : " + e.message)
            }

            //            System.out.println(destTag.getAll(FieldKey.ARTIST));
            //            System.out.println(outF.getAbsolutePath() +" : " + srcTag.getFirst(FieldKey.COMMENT) +" : " + destTag.getFirst(FieldKey.COMMENT));
        } else {
            throw RuntimeException("Destination File does not exist." + outF.absolutePath)
        }
    }

    @Throws(Exception::class)
    private fun mapSrcTagToDestTag(srcDir: String, outDir: String, srcExt: String, destExt: String) {
        val sw = StopWatch().start()
        println("mapMp3SrcTagToDestM4aTag")

        val inFiles = DirectoryUtils.listAllFilteredFiles(File(srcDir), srcExt)

        val success = ArrayList<String>()
        val problems = ArrayList<String>()
        for (fi in inFiles) {
            val outF = File(fi.absolutePath.replace(srcDir, outDir).replace(srcExt, destExt))
            try {
                val srcF = AudioFileIO.read(fi)
                val srcTag = srcF.tag
                val ah = srcF.audioHeader

                success.add(mapSrcTagToDestTag(outF, srcTag, problems))
            } catch (e: Exception) {
                problems.add(e.message + " : " + fi.absolutePath + " : " + outF.absolutePath)
            }

        }

        println("Success : " + success.size)
        success.forEach(Consumer<String> { println(it) })
        println("PROBLEMS : " + problems.size)
        problems.forEach(Consumer<String> { println(it) })
        sw.log("Total time taken for mp3 to m4a tag mapping")
        sw.printConsole()
    }

    @Test
    @Throws(Exception::class)
    fun testMapMp3SrcTagToDestM4aTag() {
        var srcDir = "D:\\tmp\\aaa\\"
        var outDir = "D:\\tmp\\aaa_out\\"
        srcDir = "D:\\mani\\music\\mobile\\hindi\\"
        outDir = "G:\\manish\\Music\\hindi\\"
        srcDir = "C:\\Users\\msingh\\Downloads\\"
        outDir = "C:\\Users\\msingh\\Downloads\\"
        mapSrcTagToDestTag(srcDir, outDir, ".mp3", ".m4a")
        println("fdsadsa")
    }

    @Test
    @Throws(Exception::class)
    fun testMapM4aSrcTagToDestMp3Tag() {
        var srcDir = "D:\\tmp\\aaa\\"
        var outDir = "D:\\tmp\\aaa_out\\"
        srcDir = "D:\\mani\\music\\mobile\\hindi\\"
        outDir = "G:\\manish\\Music\\hindi\\"
        srcDir = "C:\\Users\\msingh\\Downloads\\"
        outDir = "C:\\Users\\msingh\\Downloads\\"
        mapSrcTagToDestTag(srcDir, outDir, ".m4a", ".mp3")
    }

    @Test
    @Throws(Exception::class)
    fun testListNamesWhichHaveEmpty() {

        val srcDir = "D:\\tmp\\aaa\\"

        val inFiles = DirectoryUtils.listAllFilteredFiles(File(srcDir), ".mp3")

        for (fi in inFiles) {
            val srcF = AudioFileIO.read(fi)
            val srcTag = srcF.tag
            val ah = srcF.audioHeader
            val sb = StringBuilder()
            if (srcTag.getFirst(FieldKey.TITLE).trim { it <= ' ' } == "") {
                sb.append("TITLE ")
            }
            if (srcTag.getFirst(FieldKey.ARTIST).trim { it <= ' ' } == "") {
                sb.append("ARTIST ")
            }
            if (srcTag.getFirst(FieldKey.ALBUM).trim { it <= ' ' } == "") {
                sb.append("ALBUM ")
            }
            if (srcTag.getFirst(FieldKey.YEAR).trim { it <= ' ' } == "") {
                sb.append("YEAR ")
            }
            if (sb.length > 0) {
                println(fi.absolutePath + " : " + sb.toString())
            }
        }
    }

    companion object {

        @Throws(Exception::class)
        @JvmStatic fun main(args: Array<String>) {


        }
    }

}
