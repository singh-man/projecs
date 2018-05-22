;java -cp .:classes -jar D:/mani/dev/opt/clojure-1.6.0/clojure-1.6.0.jar -i D:/mani/dev/project/z_clojure/src/avScripts/avScripts_2.clj -m avScripts.avScripts_2 3
;(load-file "D:/mani/dev/project/z_clojure/src/avScripts/avScripts_2.clj")

(ns utils.utils
  (use [clojure.java.shell :only [sh]])
  ;(load-file "src/utils/fileUtils.clj")
  (use [utils.fileUtils :exclude [-main]])
  ;(use utils.fileUtils)
  ;(use [clojure.string])
  (:gen-class)
  )

;(map (fn [[k v]] (prn k v)) cmds)

(defn tempSol "coverts to CamelCase as a temp sol" 
  [strin_g] (clojure.string/join (doall (map clojure.string/capitalize (.split strin_g " ")))))

(defn resovelKeyword "returns function associated with keyword"
  [k] (resolve (symbol k)))

(defn spliter "turns the args into a vector/list" [args] 
  (def result (.split args " ")) 
  result)

(defn isLinux[] (if (= (System/getProperty "os.name") "Linux") true false))

#_(defn loopEncode [f fileMap crf] 
  (def output [])
  (doseq [keyval fileMap] 
    (def output (conj output (f (key keyval) (val keyval) crf))))
  output)

(defn executeSH [args] 
  (apply println args)
  (apply sh args))