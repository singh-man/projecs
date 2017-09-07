(ns musicScripts.musicScript
  (use [clojure.java.shell :only [sh]])
  (load-file "../utils/utils.clj")
  (use utils.utils)
  (:gen-class))

(def cmds {
           :neroAac {
                     :enc ["wav to m4a -q 0.19(46kbps) 0.25(66kbps), 0.35(100kbps), 0.50(185kbps)" 
                           "D:/mani/dev/opt/NeroAACCodec-1.5.1/win32/neroAacEnc.exe -q 0.19 -hev2 -if %s -of %s"]
                     :dec ["m4a to wav file" 
                           "D:/mani/dev/opt/NeroAACCodec-1.5.1/win32/neroAacDec.exe -if %s -of %s"]
                     :tag ["[m4aFile] and [id3] map {title <title>, album <album,...> }" 
                           "D:/mani/dev/opt/NeroAACCodec-1.5.1/win32/neroAacTag.exe %s -meta:genre=%s -meta:title=%s -meta:artist=%s -meta:year=%s -meta:album=%s -meta:comment=%s"]
                     }
           :mpg123 ["converts mp3 to wav format -> [outputFile inFile]" 
                    "D:/mani/dev/opt/mpg123-1.21.0-x86-64/mpg123.exe -w %s %s"]
           :mp3info ["reads ID3 tag note:- not good quality" 
                     "D:/mani/dev/opt/mp3info/mp3info.exe -p title-:-%t-:-artist-:-%a-:-album-:-%l-:-year-:-%y-:-genre-:-%g-:-comment-:-%c."]
           :lame ["wav to mp3" 
                  "lame -b 224 %s %s"]
           })

(def id3tag {:artist "" :album "" :composer "" :lyricist "" :title "" :year "" :genre "" :comment "" :cover ""})

(defn neroAacEncDec [x inFile outFile] (executeSH (spliter (format (second x) inFile outFile))))

(defn neroAacEnc
  ([wavFile m4aFile] 
   (let [x ((cmds :neroAac) :enc)] (neroAacEncDec x wavFile m4aFile)))
  ([file] (neroAacEnc file (.replace file ".wav" ".m4a"))))

(defn neroAacDec
  ([wavFile m4aFile] 
   (let [x ((cmds :neroAac) :dec)] (neroAacEncDec x wavFile m4aFile)))
  ([file] (neroAacDec file (.replace file ".wav" ".m4a"))))

(defn neroAacTag
  ([m4aFile id3] 
   (let [x ((cmds :neroAac) :tag)]
     (executeSH (spliter (format (second x) m4aFile (tempSol (id3 :genre)) (tempSol (id3 :title)) (tempSol (id3 :artist)) (tempSol (id3 :year)) (tempSol (id3 :album)) (tempSol (id3 :comment))))))
   ))

(defn mpg123
  ([mp3file wavFile] 
   (let [x (cmds :mpg123)]
     (executeSH (spliter (format (second x) wavFile mp3file)))))
  ([file] 
   (mpg123 file (.replace file ".mp3" ".wav"))))

;{:exit 0, :out title=Haye Mera Dil artist=Alfaaz and honey singh album=Boy Next Door date=2011 genre= comment=, :err }
(defn mp3Info "reads ID3 tag note:- not good quality"
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

(defn lame "wav to mp3" [dirPath] "lame -b 224 %s %s")