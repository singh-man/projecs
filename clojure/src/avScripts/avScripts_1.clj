;java -cp .:classes -jar D:/mani/dev/opt/clojure-1.6.0/clojure-1.6.0.jar -i D:/mani/dev/project/z_clojure/src/avScripts/avScripts_1.clj -m avScripts.avScripts_1 3
;(load-file "D:/mani/dev/project/z_clojure/src/avScripts/avScripts_1.clj")

(ns avScripts.avScripts_1
  (use [clojure.java.shell :only [sh]])
  (load-file "../utils/fileUtils.clj")
  (use [utils.fileUtils :exclude [-main]])
  (load-file "./utils.clj")
  ;(use utils.fileUtils)
  ;(use [clojure.string])
  (:gen-class)
  )

;(def ffmpeg "D:/mani/dev/opt/ffmpeg-20151208-git-ff6dd58-win64-static/bin/ffmpeg.exe")
(def ffmpeg "D:/mani/dev/opt/ffmpeg-20160614-git-cb46b78-win32-static/bin/ffmpeg.exe")

(def handbrake "D:/mani/dev/opt/HandBrake-0.10.5-x86_64-Win_CLI/HandBrakeCLI.exe")

(defn executeLoop [f fileMap]
  (def output [])
  (doseq [keyval fileMap] 
    (println "Processing " (key keyval) "->" (val keyval))
    (def output (conj output (f (key keyval) (val keyval)))))
  output)

(defn handbrake_read "command to check the title of DVD check the titles with longest duration -- sh handbrake -i file or <VIDEO_TS folder> --title 0"
  ([inFile]
   (sh handbrake "-i" inFile "--title" "0")))

(defn handbrake_x265
  "
  1. Genral purpose command to encode videos with all subtitles
  2. ripping the dvd, hacker's way sh handbrake -i <folder> -o <output-file> -v -e x265 -q 33 -a 2 -s 1,2,3 -E av_aac --custom-anamorphic --keep-display-aspect -O --title <1 or 2 the biggest duration> --audio 1
  "
  ([opr inFile q outFile]
   (def shOpr {
               :t1 (str handbrake " -i %s -o %s -e x265 -q %s -a 2 -s 1,2,3 -E av_aac --custom-anamorphic --keep-display-aspect -O")
               :t2 (str handbrake " -i %s -o %s -v -e x265 -q %s -a 2 -s 1,2,3 -E av_aac --custom-anamorphic --keep-display-aspect -O --title 1 --audio 1")
               })
   (apply sh (spliter (format (shOpr opr) inFile outFile q))))
  ([opr q fileMap] 
   (executeLoop (fn[x y] (handbrake_x265 opr x q y)) fileMap))
  )

(defn ffmpeg_x265
  "
  1. Encodes all videos only and copying all other streams as is
  2. Encodes all videos/audios and copy all other streams as is
  3. Encodes videos only and copying all other primary streams(only)
  4. Encodes most significant audio and video only ignoring other streams
  5. Input has 4 streams 0=v, 1=s, 2=jap audio, 3=eng audio -> Output has 3 streams mapped like 0=0 encoded video 0=1 subtitle copied 0=3 eng encoded audio
  "
  ([opr inFile crf outFile]
   (def shOpr {
               :t1 (str ffmpeg " -i %s -map 0 -c copy -c:v libx265 -preset medium -x265-params crf=%s %s")
               :t2 (str ffmpeg " -i %s -map 0 -c copy -c:v libx265 -preset medium -x265-params crf=%s -c:a aac -strict experimental -b:a 96k %s")
               :t3 (str ffmpeg " -i %s -c copy -c:v libx265 -preset medium -x265-params crf=%s %s")
               :t4 (str ffmpeg " -i %s -c:v libx265 -preset medium -x265-params crf=%s -c:a aac -strict experimental -b:a 96k %s")
               :t5 (str ffmpeg " -i %s -map 0:0 -map 0:1 -map 0:3 -c:v libx265 -preset medium -x265-params crf=%s -c:a:3 aac -strict experimental -b:a:3 96k -c:s copy %s")
               })
   (apply sh (spliter (format (shOpr opr) inFile crf outFile))))
  ([opr crf fileMap] 
   (executeLoop (fn[x y] (ffmpeg_x265 opr x crf y)) fileMap))
  )

(defn ffmpeg_wmv
  ([opr inFile outFile]
   (def shOpr {
               :t1 (str ffmpeg " -i %s -b:v 1M -vcodec msmpeg4 -acodec wmav2 %s")
               })
   
   (apply sh (spliter (format (shOpr opr) inFile outFile))))
  ([opr fileMap] 
   (executeLoop (fn[x y] (ffmpeg_wmv opr x y)) fileMap))
  )

(defn ffmpeg_vp9
  "
  1. Constant Quality bitrate limit-b:v is 0
  2. Constrained Quality bitrate limit-b:v is set
  3. vbr
  4. Constant bitrate limit-b:v is 1M
  "
  ([opr inFile outFile]
   (def shOpr {
               :t1 (str ffmpeg " -i %s -c:v libvpx-vp9 -crf 20 -b:v 0 -c:a libvorbis %s")
               :t2 (str ffmpeg " -i %s -c:v libvpx-vp9 -crf 20 -b:v 1200k -c:a libvorbis %s")
               :t3 (str ffmpeg " -i %s -c:v libvpx-vp9 -b:v 1200k -c:a libvorbis %s")
               :t4 (str ffmpeg " -i %s -c:v libvpx-vp9 -minrate 1M -maxrate 1M -b:v 1M -c:a libvorbis %s")
               })
   
   (apply sh (spliter (format (shOpr opr) inFile outFile))))
  ([opr fileMap] 
   (executeLoop (fn[x y] (ffmpeg_vp9 opr x y)) fileMap))
  )

(defn ffmpeg_cut_video_old [opr] 
  (def ma {:ffmpeg_cut_video [ffmpeg " -ss" "00:30:59" "-i" "<inFile>" "-ss" "00:00:02" "-t" "00:45:40" "-c" "copy" "<outFile>"]}) 
  (ma opr))

(defn ffmpeg_cut_video
  "
  1. fast and copies only
  2. slow and transocde by syncing
  "
  ([opr inFile outFile]
   (def shOpr {
               :t1 (str ffmpeg " -ss 00:44:29 -i %s -ss 00:00:01 -t 00:14:00 -c copy %s")
               :t2 (str ffmpeg " -i %s -ss 00:01:00 -t 00:01:00 -async 1 -strict -2 %s")
               })
   
   (apply sh (spliter (format (shOpr opr) inFile outFile))))
  ([opr fileMap]
   (executeLoop (fn[x y] (ffmpeg_cut_video opr x y)) fileMap))
  )

(defn ffmpeg_import_subtitles [opr inFile outFile]
  (def shOpr {
              :t1 (str ffmpeg " -i %s -sub_charenc CP1252 -i %s -map 0:v -map 0:a -c copy -map 1 -c:s:0 srt -metadata:s:s:0 language=en %s")
              })
  (spliter (format (shOpr opr) inFile outFile))
  )

(defn ffmpeg_concat [opr concatFilesIn outFile]
  (def shOpr {
              :t1 (str ffmpeg " -f -i %s -c copy %s")
              })
  (apply sh (spliter (format (shOpr opr) concatFilesIn outFile)))
  )

(defn -main [args]
  
  (def dirPath "D:/mani/video/compressed/")
  (def srcFile "D:/mani/video/compressed/<>")
  (def outFile "D:/mani/video/compressed/<>")
  (def fromExt "")
  (def concatFilesIn "D:/mani/video/compressed/file.txt")
  
  (println "Welcome to AV Convertor\n Please provide source directory for videos - must end with / : ")
  (def dirPath #_(read-line) dirPath)
  
  ;(def m (fileMap dirPath fromExt "_t25.mkv"))
  
  (def cmdMap {
               :1 [(str "ffmpeg -> convert all in " dirPath) #(ffmpeg_x265 :t2 "21" (fileMap dirPath fromExt "_f21.mkv"))]
               :2 [(str "ffmpeg -> convert " srcFile " -> " outFile) #(ffmpeg_x265 :t2 srcFile "25" outFile)]
               :3 [(str "ffmpeg -> cut all in " dirPath) #(ffmpeg_cut_video :t1 (fileMap dirPath fromExt (str "_t" fromExt)))]
               :4 [(str "ffmpeg -> cut " srcFile " -> " outFile) #(ffmpeg_cut_video :t1 srcFile outFile)]
               :5 [(str "ffmpeg -> import subtitle " srcFile " -> " outFile) #(ffmpeg_import_subtitles :t1 srcFile outFile)]
               :6 [(str "ffmpeg -> concat videos ") #(ffmpeg_concat :t1 concatFilesIn "D:/mani/video/compressed/output.mp4")]
               :7 [(str "handbrake -> convert all in " dirPath) #(handbrake_x265 :t1 "25" (fileMap dirPath fromExt "_h25.mkv"))]
               :8 [(str "handbrake -> convert " srcFile " -> " outFile) #(handbrake_x265 :t1 srcFile "25" outFile)]
               })
  (doseq [keyval cmdMap] 
    (println (name (key keyval)) "-" ((val keyval) 0)))
  
  (println "Chose Option!")
  (def option (keyword (read-line)))
  
  (def output (time (((cmdMap option) 1))))
  
  ;(println output)
  (if (vector? output) 
    (doseq [out output]
      (clojure.string/blank? (out :err))
      (println (out :err)))
    (println (output :err)))
  
  (System/exit 0)
  )