;java -cp .:classes -jar D:/mani/dev/opt/clojure-1.6.0/clojure-1.6.0.jar -i D:/mani/dev/project/z_clojure/src/songHandler/songHandler.clj -m songHandler.songHandler 3
(ns songHandler.songHandler
    (use [clojure.java.shell :only [sh]])
    (load-file "./musicHelper.clj")
    (use songHandler.musicHelper)
    (require clojure.java.io)
    (:gen-class)
)

(defn aa [name & more] (println name more) )

(defn -main [args]
  ;(-main 3)
    (def srcDirPath "D:/tmp/test/")
    (def outDirPath "D:/tmp/test_out/")

    (def srcDirPath "C:/Users/msingh/Downloads/")
    (def outDirPath "C:/Users/msingh/Downloads/")

    (println "Welcome to Song Convertor\n Please provide source directory for songs - must end with / : ")
    (def srcDirPath #_(read-line) srcDirPath)

    (time (execAndPrintProblems mp3ToM4a srcDirPath outDirPath true))
    ;(time (execAndPrintProblems wavToM4AInDir srcDirPath true true))

    (aa "aa" "fdsaf" "fdaf" "sfd")

    (System/exit 0)
)
