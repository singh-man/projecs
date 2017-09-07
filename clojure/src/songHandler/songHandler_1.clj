;java -cp .;classes;D:/mani/dev/src/man/z_clojure/src/songHandler_1/jaudiotagger-2.2.5.jar -jar D:/mani/dev/opt/clojure-1.6.0/clojure-1.6.0.jar -i D:/mani/dev/src/man/z_clojure/src/songHandler/songHandler_1.clj -m songHandler.songHandler_1 3
(ns songHandler.songHandler_1
    (use [clojure.java.shell :only [sh]])
    (load-file "../utils/fileUtils.clj")
    (use [utils.fileUtils :exclude [-main]])
    (load-file "./musicHelper.clj")
    (use songHandler_1.musicHelper)
    (require clojure.java.io)
    (:gen-class)
)

(defn -main [args]
    (def dirPath "D:/tmp/test/")

    (println (clojure.string/join (doall (map clojure.string/capitalize (.split "aa bb cc dd" " ")))))

    (def mp3File "D:/tmp/test/devD_aankhMicholi.mp3" #_(dsfdsfshdflsdfhjsdahfhsdkf))
    (println "clj parser " (parse-mp3-file mp3File))
    (println "mp3info " (sh "D:/mani/dev/opt/mp3info/mp3info.exe" "-p" "title-:-%t-:-artist-:-%a-:-album-:-%l-:-year-:-%y-:-genre-:-%g-:-comment-:-%c " mp3File))

    #_(doall (map (fn[file] 
            (println file)
            (try (println (parse-mp3-file file)) (catch Exception e (println (str "caught exception: " (.getMessage e))))))
        (listFilesAsString "D:/mani/music/hdd/hindi/" ".mp3")))

    (def i 0)
    #_(doseq [file (listFilesAsString "D:/mani/music/hdd/hindi/" ".mp3")]
        (def i (inc i))
            (println file i)
          (try (println (parse-mp3-file file)) (catch Exception e (println (str "caught exception: " (.getMessage e)))))
      )


    ;(def ma (parse-mp3-file "D:/tmp/test/a.mp3"))
    ;(println ma)
    ;(println (contains? ma :artist))

    ;(println (ma :artist))

    (System/exit 0)
)


;{:lyricist Songspk.name, :performer Songspk.name, :composer Honey Singh, :artist Alfaaz & Honey Singh, :album Boy Next Door, :title Haye Mera dil, :track 1, :recording-date 2011, :album-artist Alfaaz}
;(Failed for  D:\tmp\test\a1.mp3 Value out of range for byte: 255)
;{:lyricist Songspk.name, :performer Songspk.name, :composer Honey Singh, :artist Alfaaz & Honey Singh, :album Boy Next Door, :title Haye Mera dil, :track 1, :recording-date 2011, :album-artist Alfaaz} 
;Failed for  D:\tmp\test\a2.mp3 Value out of range for byte: 255
;nil Failed for  D:\tmp\test\a3.mp3 Value out of range for byte: 255
;nil nil 
;{:recording-date 1967, :album Bhanu Begam, :artist Md. Rafi, asha bohsle, :title Hum Intezar Karenge} 
;{:recording-date 1989, :album Billu Badshah, :artist Hasan Jahangir, :title hawa hawa o hawa} 
;{:recording-date 1988, :album Dayavan, :artist pankaj udhas, anuradha podwal, :title aaj phir tum pe pyar aaya hai} 
;{:year 2011, :album Delhi Belly, :artist Keerthi Sagathia, :title Switty Tera Pyaar Chaida} 
;{:recording-date 1973, :album Dhund, :artist asha bohsle, :title Uljhan Suljhe Na} 
;{:year 1956, :album Funtoosh, :artist kishore kumar, :title deneWalaJabBhi} 
;{:recording-date 1977, :album Hum Kisi Se Kam Nahi, :artist mohammed rafi, :title chand mere dil} 
;{:artist amitabh, :year nil, :album nil, :title madhushala_1} 
;{:year 1979, :album Meri Biwi Ki Shadi, :artist Suresh Wadhekar, :title ramdulari maike gi} 
;{:artist Md. Rafi, :album-artist Lata Mangeshkar/Mohd. Rafi, :recording-date 1964, :track 2, :album Rajkumar, :title Is Rang Badalti Duniya Mein})