;java -cp .:classes -jar D:/mani/dev/opt/clojure-1.6.0/clojure-1.6.0.jar -i D:/mani/dev/project/z_clojure/src/utils/fileUtils.clj -m utils.fileUtils 3
;lein test utils.fileUtils :only utils.fileUtils/test_fileMap3
(ns utils.fileUtils
    (:import java.io.File)
    (use [clojure.java.shell :only [sh]])
    (use clojure.test)
    ;(:gen-class)
    )

(defn getNameAndExtFromFileName "takes a fully qualified file name and return a list of (fileName ext)" 
  [file] (for [f (.split file "\\.(?=[^\\.]+$)")] f))

(defn convertToUnixFormat "clojure's replace calls .toString i.e. java.io.File.toString"
      [fileName] (clojure.string/replace fileName #"\\" "/"))

(defn isFileOfExt? "ignores case of extension"
      [file ext]
      (and
        (not (.isDirectory file))
        (.contains (.toLowerCase (convertToUnixFormat (.getAbsolutePath file))) (.toLowerCase ext))))

(defn listFilesInDir "ext=\"\" means all"
      ([dirPath ext]
        (filter (fn [fi] (isFileOfExt? fi ext)) (file-seq (clojure.java.io/file dirPath))))
      ([dirPath]
        (listFilesInDir dirPath "")))

(defn listFilesAsString "ext=\"\" means all"
      ([dirPath ext]
        (doall (map convertToUnixFormat (listFilesInDir dirPath ext))))
      ([dirPath]
        (listFilesAsString dirPath "")))

(defn removeFile [filePath] (clojure.java.io/delete-file filePath))

(defn removeFiles [files] (doseq [f files] (removeFile f)) #_(doall (map removeFile files)))

(defn makeDir [dirPath] (clojure.java.io/make-parents dirPath))

(defn fileMap "generates a hash-map between --from file <-> to file-- using map"
      [dirPath ext to]
      (def fMap {})
      (doall (map (fn [file]
                      (let [fName (.substring file 0 (.lastIndexOf file "."))
                            fExt (.substring file (.lastIndexOf file "."))
                            oFile (str fName to (if (not (.contains to ".")) fExt))]
                           (def fMap (assoc fMap file oFile))
                           ))
                  (listFilesAsString dirPath ext)))
      fMap)

(defn fileMap_2 "generates a hash-map between --from file <-> to file-- using reduce"
      [dirPath ext to]
      (reduce (fn [m file]
                  (let [fl (.split file "\\.(?=[^\\.]+$)")
                        fName (first fl)
                        oFile (str fName to)]
                       (assoc m file oFile))) {} (listFilesAsString dirPath ext))
      )

(defn fileMap_3 "generates a hash-map between --from file <-> to file-- using list comprehension and regex"
      [dirPath ext to]
      (into {} (for [f (listFilesAsString dirPath ext) 
                  :let [[f_name e_name] (getNameAndExtFromFileName f)
                        oFile (str f_name to)]] 
              [f oFile]))
      )

(defn readFileLineByLine "takes func->fn(to execute for each line) and file(to read from)"
      [func file]
      (use 'clojure.java.io)
      (with-open [rdr (clojure.java.io/reader file)]
                 (doseq [line (line-seq rdr)]
                        (func line)
                        )
                 )
      )

(defn appendToFile "data->[](that has to be appended) file(to which to appended)"
      [data file]
      (with-open [wrtr (clojure.java.io/writer file :append true)]
                 (.write wrtr (clojure.string/join " " data)))
      )

(defn readFileWriteFile [rFile wFile]
      (def myLine [])
      (readFileLineByLine
        (fn [line]
            (if (not (.contains line "new file:")) (def myLine (conj myLine line)))
            (if (== (count myLine) 100)
              (do
                (appendToFile myLine wFile)
                (def myLine [])))) rFile)
      )

(deftest test_fileMap
  (prn (fileMap "/mnt/d/mani/video/" "" ".test")))

(deftest test_fileMap2
  (assert (= (fileMap_2 "/mnt/d/mani/video/" "" ".test") (fileMap "/mnt/d/mani/video/" "" ".test")))
  #_(prn (fileMap_2 "/mnt/d/mani/video/" "" ".test")))

(deftest test_fileMap3
  (assert (= (fileMap_3 "/mnt/d/mani/video/" "" ".test") (fileMap "/mnt/d/mani/video/" "" ".test")))
  #_(prn (fileMap_3 "/mnt/d/mani/video/" "" ".test")))

(defn init []
      (def direPath "/home/manish/")

      (println "Welcome to File Utils\n Please provide source directory must end with / : ")
      (def direPath #_(read-line) direPath)

      (println (filter even? #{1 2 3 4 5}))

      (println "listFilesInDir : " (listFilesInDir "D:/tmp/sao_1"))

      (println "listFilesAsString : " (listFilesAsString "D:/mani/music/hddMusic"))

      (System/exit 0))

#_(deftest testFileUtils (init))