;java -cp .:classes -jar D:\mani\dev\opt\clojure-1.6.0\clojure-1.6.0.jar -i D:\mani\dev\project\z_clojure\src\man\man.clj -m man.man 3
;(load-file "D:/mani/dev/project/z_clojure/src/man/man.clj")
;(man.man/-main 1)

(ns man.man
  ;  (:gen-class)
  )

(defn train [aa]
  (if true
    (do (println "Success! : " aa))  ))

(defn checkCond [x]
  (cond
    (> x 0) (println "greater!")
    (= x 0) (println "zero! ")
    :default (println "neither!"))
  )

(defn printName[name times]
  (dotimes [n times] (println n " : " name) (Thread/sleep 1000)))

(defn -main [args]
  (println args)
  ;(-main 3)
  (train "a")
  (checkCond 0)
  (println (.toString (java.util.Date.)) (printName "Manish.P.Singh" (* 11 108)))
  )

(do (println "Failure :"))


