(ns time-calc.core
  (:require [time-calc.io :as tc.i]
            [time-calc.adapter.core :as tc.a.core]
            [time-calc.activity :as tc.activity])
  (:gen-class))

(defn in-filename [args]
  (let [time-filename (or (first args) "time.md")]
    (->> time-filename
         tc.i/all-lines
         (tc.a.core/lines->activities)
         (tc.activity/summarize-activities))))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, time-calc-clj World!")
  (println (in-filename args)))
