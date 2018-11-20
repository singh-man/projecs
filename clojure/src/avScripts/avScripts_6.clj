;lein test avScripts.avScripts_6
;lein run -m avScripts.avScripts_6 3
;;Try using avScripts_5 for finner control of encoding
(ns avScripts.avScripts_6
  (use [utils.fileUtils :exclude [-main init]])
  (use utils.utils)
  (require [clojure.java.io :as io] )
  (use clojure.test)
  (:gen-class)
  )

;(def ffmpeg "D:/mani/dev/opt/ffmpeg-20151208-git-ff6dd58-win64-static/bin/ffmpeg.exe")
(def ffmpeg (if (isLinux) 
              "ffmpeg" 
              "D:/mani/dev/opt/ffmpeg-20180912-b69ea74-win64-static/bin/ffmpeg.exe"))

(def handbrake "D:/mani/dev/opt/HandBrake-0.10.5-x86_64-Win_CLI/HandBrakeCLI.exe")

(def dirPath (if (isLinux) 
               "/mnt/d/mani/video/compressed/"
               "D:/mani/video/compressed/"))

(defn promptDirPath[] 
  (def in (do (println "Provide input file or press enter for <" dirPath ">") (read-line)))
  (if (not (clojure.string/blank? in)) (def dirPath in)))
;(:1 ["command to check the title of DVD check the titles with longest duration -- sh handbrake -i file or <VIDEO_TS folder> --title 0" (str handbrake " -i %s --title 0"))

(defn ffmpegEncode
  ([inFile outFile crf encoder resolution]
   (def cmd (str ffmpeg " -i %s -vf scale=%s -map 0 -c copy -c:v %s -preset medium -crf %s -c:a aac -strict experimental -b:a 96k %s"))
   (format cmd inFile resolution encoder crf outFile))
  ([]
   (promptDirPath)
   (def encoders {:1 "libx264" :2 "libx265" :3 "libaom-av1" :4 "libvpx-vp9"})
   (println "Chose the encoders : " (for [[k v] encoders] (str "\n" (name k) " : " v)))
   (def encoder (encoders (keyword (read-line))))
   (def crf (do (println "Provide CRF : [0-23-51 least]") (read-line)))
   (def ratios ["-1:-1" "426:240" "-1:360" "852:480" "-1:720" "-1:1080"])
   (println "Provide vertical resolution : " (for [o ratios] (str "\n" (.indexOf ratios o) " : " o)))
   (def resolution (nth ratios (Integer/parseInt (read-line))))
   (if (.isDirectory (io/as-file dirPath))
     (do (def f_fileMap (fileMap_3 dirPath "" (str "_" encoder "_" crf ".mkv")))
       (for [[k v] f_fileMap] (ffmpegEncode k v crf encoder resolution)))
     (do (def outFile (str (first (getNameAndExtFromFileName dirPath)) "_" encoder "_" crf ".mkv"))
       [(ffmpegEncode in outFile crf encoder resolution)]))))

(defn ffmpegVolume
  ([inFile outFile db]
   (def cmds {
              :1 ["inc-dec volume of audio"
                  (str ffmpeg " -i %s -map 0 -c copy -c:a aac -af \"volume=%sdB\" %s")]
              :2 ["inc-dec volume of audio"
                  (str ffmpeg " -i %s -vcodec copy -af \"volume=%sdB\" %s")]
              })
   (let [x (second (cmds :1))]
     (format x inFile db outFile)))
  ([]
   (promptDirPath)
   (def db (do (println "Provide db : ") (read-line)))
   (if (.isDirectory (io/as-file dirPath))
     (let [f_fileMap (fileMap_3 dirPath "" (str "_f_v_" db ".mkv"))]
       (for [[k v] f_fileMap] (ffmpegVolume k v db)))
     (let [outFile (str (first (getNameAndExtFromFileName dirPath)) "_f_v_" db ".mkv")]
       [(ffmpegVolume in outFile db)]))))

(defn ffmpegCut
  ([inFile outFile cutFrom cutTill]
   (def cmds {
              :1 ["fast and copies only"
                  (str ffmpeg " -ss %s -i %s -ss 00:00:01 -t %s -c copy %s")]
              :2 ["slow and transocde by syncing"
                  (str ffmpeg " -i %s -ss 00:01:00 -t 00:01:00 -async 1 -strict -2 %s")]
              })
   (def cutDuration 0)
   (let [dateFormat (java.time.format.DateTimeFormatter/ofPattern "HH:mm:ss")
         d1 (java.time.LocalTime/parse cutFrom dateFormat)
         d2 (java.time.LocalTime/parse cutTill dateFormat)
         dur (java.time.Duration/between d1 d2)]
     (def cutDuration (.format (java.time.LocalTime/ofNanoOfDay (.toNanos dur)) dateFormat)))
   (let [x (second (cmds :1))]
     (format x cutFrom inFile cutDuration outFile)))
  ([inFile outFile]
   (def cutFrom (do (println "Provide cut from : [00:00:00]") (read-line)))
   (def cutTill (do (println "Provide cut till : [00:00:00]") (read-line)))
   (ffmpegCut inFile outFile cutFrom cutTill))
  ([]
   (println "Provide input file : ")
   (def inFile (clojure.string/replace (read-line) #"\\" "/"))
   (def outFile (str (first (getNameAndExtFromFileName inFile)) "_cut" (.substring inFile (.lastIndexOf inFile "."))))
   [(ffmpegCut inFile outFile)])
  )

(defn ffmpegImport
  ([inFile srtFile outFile]
   (def cmds {
              :1 ["Import srt file in video as mkv"
                  (str ffmpeg " -i %s -sub_charenc UTF-8 -i %s -map 0:v -map 0:a -c copy -map 1 -c:s:0 srt -metadata:s:s:0 language=en %s")]
              :2 ["Import multiple subtitles"
                  (str ffmpeg " -i %s -sub_charenc UTF-8 -i %s -i %s -map 0:v -map 0:a -c copy -map 1 -c:s:0 srt -metadata:s:s:0 language=en -map 2 -c:s:1 srt -metadata:s:s:1 language=swe %s")]
              :3 ["Import subtitles from mkv and hardcode"
                  (str ffmpeg " -i %s -c:a aac -vf subtitles=%s %s")]
              })
   (def option (if (= inFile srtFile) :3 :1))
   (let [x (second (cmds option))]
     (format x inFile srtFile outFile)))
  ([]
   (println "Provide input file : ")
   (def inFile (clojure.string/replace (read-line) #"\\" "/"))
   (def outFile (str (first (getNameAndExtFromFileName inFile)) "_en.mkv"))
   
   (println "Provide srt file or leave blank if same as input file: ")
   (def srtFile (clojure.string/replace (read-line) #"\\" "/"))
   (if (clojure.string/blank? srtFile) (def srtFile inFile))
   [(ffmpegImport inFile srtFile outFile)])
  )

(defn ffmpegConcat []
  (println "Provide input file : ")
  (def inFile (clojure.string/replace (read-line) #"\\" "/"))
  (println "Provide output file : ")
  (def outFile (read-line))
  (def cmds {
             :1 ["Concat all files provided in -f"
                 (str ffmpeg " -f concat -i %s -c copy %s")]
             })
  (let [x (second (cmds :1))]
    [(format x inFile outFile)]))

(defn handbrakeEncodeX265
  ([inFile outFile crf]
   (def cmds {
              :1 ["Genral purpose command to encode videos with all subtitles"
                  (str handbrake " -i %s -o %s -e x265 -q %s -a 2 -s 1,2,3 -E av_aac --custom-anamorphic --keep-display-aspect -O")]
              :2 ["Ripping the dvd, hacker's way sh handbrake -i <folder> -o <output-file> -v -e x265 -q 33 -a 2 -s 1,2,3 -E av_aac --custom-anamorphic --keep-display-aspect -O --title <1 or 2 the biggest duration> --audio 1"
                  (str handbrake " -i %s -o %s -v -e x265 -q %s -a 2 -s 1,2,3 -E av_aac --custom-anamorphic --keep-display-aspect -O --title 1 --audio 1")]
              })
   (let [x (second (cmds :2))]
     (format x inFile outFile crf)))
  ([]
   (println "Provide CRF : [0-28-51 least]")
   (def crf (read-line))
   (if (.isDirectory (io/as-file dirPath))
     (let [f_fileMap (fileMap_3 dirPath "" (str "_h_x265_" crf ".mkv"))]
       (for [[k v] f_fileMap] (handbrakeEncodeX265 k v crf)))
     (let [outFile (str (first (getNameAndExtFromFileName dirPath)) "_h_x265_" crf ".mkv")]
       [(handbrakeEncodeX265 in outFile crf)])))
  )

(defn init []
  
  (println "Welcome to AV Convertor\n Please provide source directory for videos - must end with / : ")
  (def dirPath #_(read-line) dirPath)
  
  (def cmdMap [[(str "ffmpeg -> encode ") #(ffmpegEncode)]
               [(str "ffmpeg -> inc-dec volume of audio ") #(ffmpegVolume)]
               [(str "ffmpeg -> cut ") #(ffmpegCut)]
               [(str "ffmpeg -> import subtitle ") #(ffmpegImport)]
               [(str "ffmpeg -> concat videos in file ") #(ffmpegConcat)]
               [(str "handbrake -> x265 convert ") #(handbrakeEncodeX265)]
               ])
  
  (doall (map (fn [v] (let [[v1 v2] v] (println (.indexOf cmdMap v) "." v1))) cmdMap))
  
  ;(def cmdMap (array-map :1 [(str "ffmpeg -> x265 convert all in " dirPath) #(ffmpegEncodeX265 (fileMap dirPath fromExt "_f_x265_27.mkv") "27")] :3 [(str "ffmpeg -> x264 convert all in " dirPath) #(ffmpegEncodeX264 (fileMap dirPath fromExt "_f_x264_21.mkv") "21")] :4 [(str "ffmpeg -> x264 convert " srcFile " -> " outFile) #(ffmpegEncodeX264 srcFile outFile "21")] :5 [(str "ffmpeg -> cut " srcFile " -> " outFile) #(ffmpegCut srcFile outFile)] :6 [(str "ffmpeg -> import subtitle " srcFile " + " srtFile " -> " outFile) #(ffmpegImport srcFile srtFile outFile)] :7 [(str "ffmpeg -> concat videos in file " concatFilesIn " -> " outFile) #(ffmpegConcat concatFilesIn outFile)] :8 [(str "ffmpeg -> inc-dec volume of audio all in " dirPath) #(ffmpegVolume (fileMap dirPath fromExt "_v9") 9)] :9 [(str "ffmpeg -> inc-dec volume of audio " srcFile " -> " outFile) #(ffmpegVolume srcFile outFile 19)] :10 [(str "ffmpeg -> convert to HD ready TV " srcFile " -> " outFile) #(ffmpegEncodeForHDReadyTV srcFile outFile 19)] :11 [(str "ffmpeg -> convert to HD ready TV in " dirPath) #(ffmpegEncodeForHDReadyTV (fileMap dirPath fromExt "_f_x264_hdReady.mkv") "19")] :12 [(str "ffmpeg -> mpeg2 convert all in " dirPath) #(ffmpegEncodeMpeg2 (fileMap dirPath fromExt "_f_mpeg2_5.mpg") "5")] :13 [(str "handbrake -> x265 convert all in " dirPath) #(handbrakeEncodeX265 (fileMap dirPath fromExt "_h_x265_21.mkv") "21")] :14 [(str "handbrake -> x265 convert " srcFile " -> " outFile) #(handbrakeEncodeX265 srcFile outFile "21")]))
  ;(doseq [keyval cmdMap] (println (name (key keyval)) "-" ((val keyval) 0)))
  
  (println "Chose Option!")
  (def option (read-line) #_(keyword (read-line)))
  
  (def output (((cmdMap (Integer/parseInt option)) 1)) #_(((cmdMap option) 1)))
  
  ; (prn "This: " output)
  ;(doseq [out output] (prn "myexe: " out))
  ;(def output (if (not (vector? output)) (conj [] output) output))
  (doseq [out output] (prn "exe -> " out))
  
  (println "Should I start executing them! y/n")
  (def option (read-line))
  
  ;Dump everything to a shell file for easier execution.
  (if (= option "y") 
    (do (with-open [w (clojure.java.io/writer  "../../ffmpeg.sh" :write true)]
          (doseq [out output] (.write w (str "\n" out))))
      (prn "Execute:-> " "../../ffmpeg.sh")))
  
  ; (def execOutput [])
  ; (if (= option "y")
  ;   (doseq [out output]
  ;     (println "exec: " out)
  ;     (def execOutput (concat execOutput (executeSH (spliter out)))))
  ;   (def execOutput "Chose n/N!"))
  ; (println execOutput)
  
  ; (if (vector? execOutput)
  ;     (doseq [out execOutput]
  ;       (clojure.string/blank? (out :err))
  ;       (println (out :err)))
  ;     (println (execOutput :err)))
  
  ;extract video and specific tracks from the video e.g. 0:0 video 0:1 audio 0:2 audio 0:3 srt
  ;(executeSH (spliter (format (str ffmpeg " -i %s -map 0:0 -map 0:2 -c copy %s") srcFile outFile)))))
  
  (System/exit 0)
  )

(deftest testAvScripts
  (time (init)))

(defn -main [args] (init))