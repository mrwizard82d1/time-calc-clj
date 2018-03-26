(ns time-calc-clj.core
  (:gen-class))

(defn in-filename [args]
 (if-let [time-filename (first args)] time-filename "time.txt"))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, time-calc-clj World!")
  (println (in-filename args)))
