;java -cp .:classes -jar D:/mani/dev/opt/clojure-1.6.0/clojure-1.6.0.jar -i D:/mani/dev/project/z_clojure/src/avScripts/avScripts_2.clj -m avScripts.avScripts_2 3
;(load-file "D:/mani/dev/project/z_clojure/src/avScripts/avScripts_2.clj")

(ns avScripts.avScripts_2
  (use [clojure.java.shell :only [sh]])
  (load-file "../utils/fileUtils.clj")
  (use [utils.fileUtils :exclude [-main]])
  (load-file "../utils/utils.clj")
  (use utils.utils)
  ;(use utils.fileUtils)
  ;(use [clojure.string])
  (:gen-class)
  )

;(def ffmpeg "D:/mani/dev/opt/ffmpeg-20151208-git-ff6dd58-win64-static/bin/ffmpeg.exe")
(def ffmpeg "D:/mani/dev/opt/ffmpeg-20160614-git-cb46b78-win32-static/bin/ffmpeg.exe")

(def handbrake "D:/mani/dev/opt/HandBrake-0.10.5-x86_64-Win_CLI/HandBrakeCLI.exe")

(def cmds {
           :ffmpeg {
                    :encode {
                             :x265 {
                                    :1 ["1. Encodes all videos only and copying all other streams as is" 
                                        (str ffmpeg " -i %s -map 0 -c copy -c:v libx265 -preset medium -x265-params crf=%s %s")]
                                    :2 ["2. Encodes all videos/audios and copy all other streams as is" 
                                        (str ffmpeg " -i %s -map 0 -c copy -c:v libx265 -preset medium -x265-params crf=%s -c:a aac -strict experimental -b:a 96k %s")]
                                    :3 ["3. Encodes videos only and copying all other primary streams(only)" 
                                        (str ffmpeg " -i %s -c copy -c:v libx265 -preset medium -x265-params crf=%s %s")]
                                    :4 ["4. Encodes most significant audio and video only ignoring other streams" 
                                        (str ffmpeg " -i %s -c:v libx265 -preset medium -x265-params crf=%s -c:a aac -strict experimental -b:a 96k %s")]
                                    :5 ["5. Input has 4 streams 0=v, 1=s, 2=jap audio, 3=eng audio -> Output has 3 streams mapped like 0=0 encoded video 0=1 subtitle copied 0=3 eng encoded audio" 
                                        (str ffmpeg " -i %s -map 0:0 -map 0:1 -map 0:3 -c:v libx265 -preset medium -x265-params crf=%s -c:a:3 aac -strict experimental -b:a:3 96k -c:s copy %s")]
                                    }
                             :x264 {
                                    :1 ["1. Encodes all videos only and copying all other streams as is" 
                                        (str ffmpeg " -i %s -map 0 -c copy -c:v libx264 -preset slow -crf %s %s")]
                                    }
                             :vp9 {
                                   :1 ["1. Constant Quality bitrate limit-b:v is 0" 
                                       (str ffmpeg " -i %s -c:v libvpx-vp9 -crf %s -b:v 0 -c:a libvorbis %s")]
                                   :2 ["2. Constrained Quality bitrate limit-b:v is set" 
                                       (str ffmpeg " -i %s -c:v libvpx-vp9 -crf %s -b:v 1200k -c:a libvorbis %s")]
                                   :3 ["vbr" 
                                       (str ffmpeg " -i %s -c:v libvpx-vp9 -b:v 1200k -c:a libvorbis %s")]
                                   :4 ["4. Constant bitrate limit-b:v is 1M" 
                                       (str ffmpeg " -i %s -c:v libvpx-vp9 -minrate 1M -maxrate 1M -b:v 1M -c:a libvorbis %s")]
                                   }
                             :mpeg2 {
                                   :1 ["1. Convert to mpeg2. -qscale 2-31 is quality, 2 being highest" 
                                       (str ffmpeg " -i %s -codec:v mpeg2video -qscale:v %s -codec:a mp2 -b:a 192k %s")]
                                   }
                             }
                    :cut {
                          :1 ["1. fast and copies only" 
                              (str ffmpeg " -ss 00:04:55 -i %s -ss 00:00:01 -t 00:05:00 -c copy %s")]
                          :2 ["2. slow and transocde by syncing" 
                              (str ffmpeg " -i %s -ss 00:01:00 -t 00:01:00 -async 1 -strict -2 %s")]
                          }
                    :resize {
                          :1 ["1. resize video" 
                              (str ffmpeg " -i %s -filter:v scale=-1:720 -c:a copy %s")]
                          }
                    :concat {
                             :1 ["concat all files provided in -f" 
                                 (str ffmpeg " -f concat -i %s -c copy %s")]
                             }
                    :import {
                             :1 ["import subtitles to file" 
                                 (str ffmpeg " -i %s -sub_charenc CP1252 -i %s -map 0:v -map 0:a -c copy -map 1 -c:s:0 srt -metadata:s:s:0 language=en %s")]
                             }
                    :volume {
                             :1 ["inc-dec volume of audio" 
                                 (str ffmpeg " -i %s -vcodec copy -af \"volume=%sdB\" %s")]
                             }
                    }
           :handbrake {
                       :encode {
                                :x265 {
                                       :1 ["1. Genral purpose command to encode videos with all subtitles" 
                                           (str handbrake " -i %s -o %s -e x265 -q %s -a 2 -s 1,2,3 -E av_aac --custom-anamorphic --keep-display-aspect -O")]
                                       :2 ["2. ripping the dvd, hacker's way sh handbrake -i <folder> -o <output-file> -v -e x265 -q 33 -a 2 -s 1,2,3 -E av_aac --custom-anamorphic --keep-display-aspect -O --title <1 or 2 the biggest duration> --audio 1"
                                           (str handbrake " -i %s -o %s -v -e x265 -q %s -a 2 -s 1,2,3 -E av_aac --custom-anamorphic --keep-display-aspect -O --title 1 --audio 1")]
                                       }
                                }
                       :read {
                              :1 ["command to check the title of DVD check the titles with longest duration -- sh handbrake -i file or <VIDEO_TS folder> --title 0"
                                  (str handbrake " -i %s --title 0")]
                              }
                       }
           })

(defn loopEncode [f fileMap crf] 
  (def output (reduce-kv (fn[res k v] (conj res (f k v crf))) [] fileMap))
  output)

(defn ffmpegEncodeMpeg2
  ([inFile outFile qscale]
   (let [x ((((cmds :ffmpeg) :encode) :mpeg2) :1)]
     (executeSH (spliter (format (second x) inFile qscale outFile)))))
  ([fileMap crf]
   (loopEncode ffmpegEncodeMpeg2 fileMap crf))
  )

(defn ffmpegEncodeX265 
  ([inFile outFile crf]
   (let [x ((((cmds :ffmpeg) :encode) :x265) :2)]
     (executeSH (spliter (format (second x) inFile crf outFile)))))
  ([fileMap crf]
   (loopEncode ffmpegEncodeX265 fileMap crf))
  )

(defn ffmpegEncodeX264 
  ([inFile outFile crf]
   (let [x ((((cmds :ffmpeg) :encode) :x264) :1)]
     (executeSH (spliter (format (second x) inFile crf outFile)))))
  ([fileMap crf]
   (loopEncode ffmpegEncodeX264 fileMap crf))
  )

(defn ffmpegEncodeVP9 
  ([inFile outFile crf]
   (let [x ((((cmds :ffmpeg) :encode) :vp9) :1)]
     (executeSH (spliter (format (second x) inFile crf outFile)))))
  ([fileMap crf]
   (loopEncode ffmpegEncodeVP9 fileMap crf))
  )

(defn ffmpegCut[inFile outFile]
  (let [x (((cmds :ffmpeg) :cut) :1)]
    (executeSH (spliter (format (second x) inFile outFile)))))

(defn ffmpegImport[inFile srtFile outFile]
  (let [x (((cmds :ffmpeg) :import) :1)]
    (executeSH (spliter (format (second x) inFile srtFile outFile)))))

(defn ffmpegConcat[inFile outFile]
  (let [x (((cmds :ffmpeg) :concat) :1)]
    (executeSH (spliter (format (second x) inFile outFile)))))

(defn ffmpegVolume 
  ([inFile outFile db]
   (let [x (((cmds :ffmpeg) :volume) :1)]
     (executeSH (spliter (format (second x) inFile db outFile)))))
  ([fileMap db]
   (loopEncode ffmpegVolume fileMap db))
  )

(defn handbrakeEncodeX265 
  ([inFile outFile crf]
   (let [x ((((cmds :handbrake) :encode) :x265) :2)]
     (executeSH (spliter (format (second x) inFile outFile crf)))))
  ([fileMap crf]
   (loopEncode handbrakeEncodeX265 fileMap crf))
  )

(defn -main [args]
  
  (def dirPath "D:/mani/video/compressed/")
  (def srcFile "D:/mani/video/compressed/Uncle.Boonmee.Who.Can.Recall.His.Past.Lives.2010.mp4")
  (def srtFile "D:/mani/video/compressed/Uncle.Boonmee.Who.Can.Recall.His.Past.Lives.2010.srt")
  (def outFile "D:/mani/video/compressed/Uncle.Boonmee.Who.Can.Recall.His.Past.Lives.2010_en.mkv")
  (def fromExt "")
  (def concatFilesIn "D:/mani/video/compressed/file.txt")
  
  (println "Welcome to AV Convertor\n Please provide source directory for videos - must end with / : ")
  (def dirPath #_(read-line) dirPath)
  
  ;(def m (fileMap dirPath fromExt "_t25.mkv"))
  
  (def cmdMap (array-map 
               :1 [(str "ffmpeg -> x265 convert all in " dirPath) #(ffmpegEncodeX265 (fileMap dirPath fromExt "_f_x265_19.mkv") "19")]
               :2 [(str "ffmpeg -> x265 convert " srcFile " -> " outFile) #(ffmpegEncodeX265 srcFile outFile "21")]
               :3 [(str "ffmpeg -> x264 convert all in " dirPath) #(ffmpegEncodeX264 (fileMap dirPath fromExt "_f21.mkv") "21")]
               :4 [(str "ffmpeg -> x264 convert " srcFile " -> " outFile) #(ffmpegEncodeX264 srcFile outFile "21")]
               :5 [(str "ffmpeg -> cut " srcFile " -> " outFile) #(ffmpegCut srcFile outFile)]
               :6 [(str "ffmpeg -> import subtitle " srcFile  " + " srtFile " -> " outFile) #(ffmpegImport srcFile srtFile outFile)]
               :7 [(str "ffmpeg -> concat videos in file " concatFilesIn " -> " outFile) #(ffmpegConcat concatFilesIn outFile)]
               :8 [(str "ffmpeg -> inc-dec volume of audio all in " dirPath) #(ffmpegVolume (fileMap dirPath fromExt "_v7") 7)]
               :9 [(str "ffmpeg -> inc-dec volume of audio " srcFile " -> " outFile) #(ffmpegVolume srcFile outFile 19)]
               :10 [(str "ffmpeg -> convert to mpeg2 all in " dirPath) #(ffmpegEncodeMpeg2 (fileMap dirPath fromExt "_f_mpeg2_5.mpg") "5")]
               :11 [(str "handbrake -> x265 convert all in " dirPath) #(handbrakeEncodeX265 (fileMap dirPath fromExt "_h21.mkv") "21")]
               :12 [(str "handbrake -> x265 convert " srcFile " -> " outFile) #(handbrakeEncodeX265 srcFile outFile "21")]
               
               ))
  
  (doseq [keyval cmdMap] 
    (println (name (key keyval)) "-" ((val keyval) 0)))
  
  (println "Chose Option!")
  (def option (keyword (read-line)))
  
  (def output (time (((cmdMap option) 1))))
  
  (if (vector? output) 
    (doseq [out output]
      (clojure.string/blank? (out :err))
      (println (out :err)))
    (println (output :err)))
  
  (System/exit 0)
  )