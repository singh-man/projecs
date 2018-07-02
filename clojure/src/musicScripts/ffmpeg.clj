;lein test musicScripts.ffmpeg :only musicScripts.ffmpeg/testMp3ToWav
;lein test musicScripts.ffmpeg :only musicScripts.ffmpeg/testMp3ToM4a

(ns musicScripts.ffmpeg
  "ffmpeg based and doesn't use jaudiotagger 3pp's"
  (use [utils.fileUtils :exclude [-main init]])
  (use utils.utils)
  (use clojure.test)
  (:gen-class)
  )

(def ffmpeg (if (isLinux) 
              "ffmpeg"
              "D:/mani/dev/opt/ffmpeg-20160614-git-cb46b78-win32-static/bin/ffmpeg.exe"))

(defn exe [e] (executeSH (spliter e)))

(defn replaceFileExt [file ext] (str (.replace file (.substring file (.lastIndexOf file ".")) ext)))

(defn ffmpegToM4aWith_libfdk_aac "ID3 metadata transfer handled by ffmpeg lib"
  ([inFile m4aFile] 
   (let [x (str ffmpeg " -i %s -c:a libfdk_aac -profile:a aac_he_v2 -b:a 48k %s")] (format x inFile m4aFile)))
  ([file] 
   (ffmpegToM4aWith_libfdk_aac file (replaceFileExt file ".m4a"))))

(defn ffmpegToM4aWith_builtInAAC "ID3 metadata transfer handled by ffmpeg lib"
  ([inFile m4aFile] 
   (let [x (str ffmpeg " -i %s -c:a aac -b:a 48k %s")] (format x inFile m4aFile)))
  ([file] 
   (ffmpegToM4aWith_builtInAAC file (replaceFileExt file ".m4a"))))

(defn ffmpegToWav
  ([inFile outFile] (let [x (str ffmpeg " -i %s %s")] (format x inFile outFile)))
  ([inFile] (ffmpegToWav inFile (replaceFileExt inFile ".wav"))))

(deftest testMp3ToM4a 
  (def dirPath (if (isLinux) 
                 "/mnt/d/mani/video/"
                 "D:/mani/video/"))
  (def files (listFilesAsString dirPath ".mp3"))
  (def output (map (fn[file] (let [m4aFile (clojure.string/replace file ".mp3" ".m4a")] 
                               (if (isLinux)
                                 (ffmpegToM4aWith_libfdk_aac  file m4aFile)
                                 (ffmpegToM4aWith_builtInAAC  file m4aFile))))
                   files))
  (doall (map #(exe %) output)))

(deftest testMp3ToWav 
  (def dirPath (if (isLinux) 
                 "/mnt/d/mani/video/"
                 "D:/mani/video/"))
  (def files (listFilesAsString dirPath ".mp3"))
  (def output (map (fn[file] (let [wavFile (clojure.string/replace file ".mp3" ".wav")] 
                               (ffmpegToWav file wavFile)))
                   files))
  (doall (map #(exe %) output)))

;(deftest testMp3Tom4a (mp3Tom4a))

(defn -main [args] (prn "call some here"))