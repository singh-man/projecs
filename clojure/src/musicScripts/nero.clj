;lein test musicScripts.nero

(ns musicScripts.nero
  "nero encoder based and relies on jaudiotagger 3pp's for ID3 tags"
  (use [utils.fileUtils :exclude [-main init]])
  (use utils.utils)
  (use clojure.test)
  (:gen-class)
  )

(defn neroAacEnc "encrypts wav file to m4a"
  ([wavFile m4aFile] 
   (def _neroAacEnc (if (isLinux) 
                      "neroAacEnc"
                      "D:/mani/dev/opt/NeroAACCodec-1.5.1/win32/neroAacEnc.exe"))
   (let [x (str _neroAacEnc " -q 0.19 -hev2 -if %s -of %s")] (format x wavFile m4aFile)))
  ([file] 
   (neroAacEnc file (.replace file ".wav" ".m4a"))))

(defn neroAacDec "decrypts m4a file to wav"
  ([m4aFile wavFile] 
   (def _neroAacDec "neroAacDec")
   (let [x (str _neroAacDec " -if %s -of %s")] (format x m4aFile wavFile)))
  ([file] 
   (neroAacDec file (.replace file ".wav" ".m4a"))))

#_(defn neroAacTag
    ([m4aFile id3] 
     (let [x ((cmds :neroAac) :tag)]
       (format (second x) m4aFile (tempSol (id3 :genre)) (tempSol (id3 :title)) (tempSol (id3 :artist)) (tempSol (id3 :year)) (tempSol (id3 :album)) (tempSol (id3 :comment))))))

(defn mp3ToWav
  ([mp3file wavFile] 
   (let [mpg123 (if-not (isLinux) "D:/mani/dev/opt/mpg123-1.21.0-x86-64/mpg123.exe" "mpg123")] 
     (format (str mpg123 " -w %s %s") wavFile mp3file)))
  ([file] 
   (mp3ToWav file (.replace file ".mp3" ".wav"))))

(def id3tag {:artist "" :album "" :composer "" :lyricist "" :title "" :year "" :genre "" :comment "" :cover ""})
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

(defn exe [e] (executeSH (spliter e)))

(defn mp3Tom4a [] "Will use jaudiotagger to copy ID3 metadata to m4a File"
  (def dirPath (if (isLinux) 
                 "/mnt/d/mani/video/"
                 "D:/mani/video/"))
  (def files (listFilesAsString dirPath ".mp3"))
  (doall (map (fn[file] (let [wavFile (.replace file ".mp3" ".wav")
                              m4aFile (.replace file ".mp3" ".m4a")] 
                          (exe (mp3ToWav file)) 
                          (exe (neroAacEnc  wavFile m4aFile)) 
                          (removeFile wavFile))) files))
  (prn files)
  (doseq [f files] (mapTags f (.replace f ".mp3" ".m4a"))))

(deftest testMp3Tom4a (mp3Tom4a))

(defn init[])

(defn -main [args] (init))