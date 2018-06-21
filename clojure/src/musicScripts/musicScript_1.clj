;lein test musicScripts.musicScript_1

(ns musicScripts.musicScript_1
  (use [clojure.java.shell :only [sh]])
    (use [utils.fileUtils :exclude [-main init]])
    (use utils.utils)
    (use clojure.test)
    (:import java.io.File)
    (:import [org.jaudiotagger.audio AudioFile AudioFileIO])
    (:import [org.jaudiotagger.tag FieldKey] )
    (:gen-class)
    )

(def neroAacEnc "/home/manish/dev/opt/NeroAACCodec-1.5.1/linux/neroAacEnc")
(def neroAacDec "/home/manish/dev/opt/NeroAACCodec-1.5.1/linux/neroAacDec")

(defn wavToM4a
  ([wavFile m4aFile] 
   (let [x (str neroAacEnc " -q 0.19 -hev2 -if %s -of %s")] (format x wavFile m4aFile)))
  ([file] 
  (wavToM4a file (.replace file ".wav" ".m4a"))))

(defn m4aToWav
  ([m4aFile wavFile] 
  (let [x (str neroAacDec " -if %s -of %s")] (format x m4aFile wavFile)))
  ([file] 
  (m4aToWav file (.replace file ".wav" ".m4a"))))

#_(defn neroAacTag
  ([m4aFile id3] 
   (let [x ((cmds :neroAac) :tag)]
     (executeSH (spliter (format (second x) m4aFile (tempSol (id3 :genre)) (tempSol (id3 :title)) (tempSol (id3 :artist)) (tempSol (id3 :year)) (tempSol (id3 :album)) (tempSol (id3 :comment))))))
   ))

(defn mp3ToWav
  ([mp3file wavFile] 
   (format "mpg123 -w %s %s" wavFile mp3file))
  ([file] 
   (mp3ToWav file (.replace file ".mp3" ".wav"))))

;{:exit 0, :out title=Haye Mera Dil artist=Alfaaz and honey singh album=Boy Next Door date=2011 genre= comment=, :err }
#_(defn mp3Info "reads ID3 tag note:- not good quality"
  [mp3file] 
  (let [id3 (executeSH (spliter (str (second (cmds :mp3info)) " " mp3file)))
        id3Out (id3 :out)]
    ;(println "id3Out:" (seq (.split id3Out "-:-")))
    ;Gets the data as "title-:-abc-:-album-:-bcd-:-....." -> ""title" "abc" "album" "bcd"..." -> {[title abc],[album bcd]...}
    (def tmpMap (apply hash-map (.split id3Out "-:-")))
    ;(println "tmpMap" (seq tmpMap))
    (def id3TagMap {:artist (tmpMap "artist") :album (tmpMap "album") :composer (tmpMap "composer") :lyricist (tmpMap "lyricist") 
                    :title (tmpMap "title") :year (tmpMap "year") :genre (tmpMap "genre") :comment (tmpMap "comment") :cover (tmpMap "cover")}))
  id3TagMap)

(defn wavToMp3 "wav to mp3" [dirPath] "lame -b 224 %s %s")

(defn mapTags [srcFile destFile]
  (def srcTag (.getTag (AudioFileIO/read (File. srcFile))))
  (let [dest_file (AudioFileIO/read (File. destFile))
        destTag (.getTag dest_file)]
        (.setField destTag FieldKey/TITLE (.getFirst srcTag FieldKey/TITLE))
        (.setField destTag FieldKey/ARTIST (.getFirst srcTag FieldKey/ARTIST))
        (.setField destTag FieldKey/ALBUM (.getFirst srcTag FieldKey/ALBUM))
        (.setField destTag FieldKey/GENRE (.getFirst srcTag FieldKey/GENRE))
        (.setField destTag FieldKey/LYRICIST (.getFirst srcTag FieldKey/LYRICIST))
        (.setField destTag FieldKey/COMPOSER (.getFirst srcTag FieldKey/COMPOSER))
        (.setField destTag FieldKey/ALBUM_ARTIST (.getFirst srcTag FieldKey/ALBUM_ARTIST))
        ;(.setField destTag FieldKey/TRACK (.getFirst srcTag FieldKey/TRACK))
        ;(.setField destTag FieldKey/YEAR (.getFirst srcTag FieldKey/YEAR))
        ;(.setField destTag FieldKey/COMMENT (.getFirst srcTag FieldKey/COMMENT))
        ;(.setField destTag FieldKey/PRODUCER (.getFirst srcTag FieldKey/PRODUCER))
        (.setField destTag (.getFirstArtwork srcTag))
        (AudioFileIO/write dest_file)
        )
)

(defn exe [e] (executeSH (spliter e)))

(defn mp3Tom4a [] 
  (def dirPath (if (isLinux) 
          "/home/manish/mani/video/"
          "D:/mani/video/"))
  
  (def files (listFilesAsString dirPath ".mp3"))
  (prn files)
  (doall (map (fn[file] (let [wavFile (.replace file ".mp3" ".wav")
                              m4aFile (.replace file ".mp3" ".m4a")] 
                              (exe (mp3ToWav file)) 
                              (exe (wavToM4a  wavFile m4aFile)) 
                              (removeFile wavFile))) files))

  (doall (map #((let [m4aFile (.replace %1 ".mp3" ".m4a")]
                (mapTags %1 m4aFile))) files))

)

(defn init[] (mp3Tom4a))

(deftest testMusicScript
    (init))

(defn -main [args] (init))