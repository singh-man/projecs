;lein test musicScripts.ffmpeg :only musicScripts.ffmpeg/testMp3ToWav
;lein test musicScripts.ffmpeg :only musicScripts.ffmpeg/testMp3ToM4a
;lein test musicScripts.ffmpeg :only musicScripts.ffmpeg/testAckermann

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
  
  (def output [])
  
  ; Works perfectly; can't understand how it gets ID3v2 data and album art
  (doseq [file files] 
    (let [m4aFile (clojure.string/replace file ".mp3" ".m4a")
          wavFile (clojure.string/replace file ".mp3" ".wav")] 
      (def output (conj output (exe (ffmpegToWav file wavFile))))
      (def output (conj output (if (isLinux)
                                 (exe (ffmpegToM4aWith_libfdk_aac wavFile m4aFile))
                                 (exe(ffmpegToM4aWith_builtInAAC wavFile m4aFile)))))
      (removeFile wavFile)))
  
  ; Was adding album art as sperate video stream
  #_(do (def output (map (fn[file] (let [m4aFile (clojure.string/replace file ".mp3" ".m4a")] 
                                     (if (isLinux)
                                       (ffmpegToM4aWith_libfdk_aac file m4aFile)
                                       (ffmpegToM4aWith_builtInAAC file m4aFile))))
                         files))
      (doall (map #(exe %) output)))
  
  (prn output))

(defn ackermann [m n] 
  (cond (zero? m) (inc n)
        (zero? n) (ackermann (dec m) 1)
        :else (ackermann (dec m) (ackermann m (dec n)))))

(deftest testMp3ToWav 
  (def dirPath (if (isLinux) 
                 "/mnt/d/mani/video/"
                 "D:/mani/video/"))
  (def files (listFilesAsString dirPath ".mp3"))
  (def output (map (fn[file] (let [wavFile (clojure.string/replace file ".mp3" ".wav")] 
                               (ffmpegToWav file wavFile)))
                   files))
  (def execOutput [])
  (doall (map #(def execOutput (concat execOutput (exe %))) output))
  (prn execOutput))

;(deftest testMp3Tom4a (mp3Tom4a))

(deftest testAckermann 
  ; with 4 0 runs fine with below stack overflow
  (prn (ackermann 4 1)))

(defn -main [args] (prn "call some here"))