(ns songHandler.musicHelper
  (use [clojure.java.shell :only [sh]])
  (load-file "../utils/fileUtils.clj")
  (use [utils.fileUtils :exclude [-main]])
  ;(use utils.fileUtils)
  (load-file "../musicScripts/musicScript.clj")
  (use musicScripts.musicScript)
  (:gen-class)
  )

(defn readMp3tag [file] (mp3Info file))

(defn writeM4aTag [file, id3] (neroAacTag file id3))

(defn wavToM4A "[inFile outFile] or [file] adds {:file <file>} to the output"
  ([inFile outFile] 
   (makeDir outFile)
   (conj (neroAacEnc inFile outFile) {:file inFile}))
  ([file] 
   (wavToM4A file (.replace file ".wav" ".m4a"))))

(defn wavToM4AInDir "m4a's will be in out dir" 
  [dirPath] 
  (doall (map (fn[file] (wavToM4A file (.replace file ".wav" ".m4a"))) 
              (listFilesAsString dirPath ".wav"))))

(defn mp3ToWav 
  ([inFile outFile] 
   (makeDir outFile)
   (mpg123 inFile outFile))
  ([file] 
   (mp3ToWav file (.replace file ".mp3" ".wav"))))

(defn mp3ToWavInDir [dirPath] 
  (doall (map (fn[file] (mp3ToWav file (.replace file ".mp3" ".wav"))) 
              (listFilesAsString dirPath ".mp3"))))

(declare writeID3tagMp3ToM4a)

(defn mp3ToM4a
  ([dirPath, outDirPath, withTag] 
   (def scriptResult []) ;will be a map like {:out :err :exit} + {:file}
   (doall (map (fn[file] 
                 (let [wavFile (.replace file ".mp3" ".wav") 
                       m4aFile (.replace (.replace wavFile ".wav" ".m4a") dirPath outDirPath)] 
                   (mp3ToWav file wavFile)
                   (def scriptResult (conj scriptResult (wavToM4A wavFile m4aFile)))
                   (if (true? withTag) (writeID3tagMp3ToM4a file m4aFile))
                   (removeFile wavFile)))
               (listFilesAsString dirPath ".mp3")))
   scriptResult))

(defn wavToMp3 [dirPath] "lame -b 224 %s %s")

(defn writeID3tagMp3ToM4a 
  [mp3File, m4aFile] 
  (writeM4aTag m4aFile (readMp3tag mp3File)))

(defn getMapValue [m key] (if (contains? m key) (m key) ""))

(defn printProblemMap [problems] 
  (doseq [out problems] 
    (if (not (clojure.string/blank? (out :out))) 
      (println (out :out) (out :file)))))

(defn execAndPrintProblems [which dirPath, outDirPath, withTag] 
  (printProblemMap (which dirPath outDirPath withTag))
  ;(printProblemMap (which dirPath))
  )