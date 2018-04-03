(ns time-calc.core
  (:require [time-calc.io :as tc.i]
            [time-calc.adapter.core :as tc.a.core]
            [time-calc.activity :as tc.activity])
  (:gen-class))

(defn in-filename [args]
  (or (first args) "time.md"))

(defn summarize [time-filename]
  (->> time-filename
       tc.i/all-lines
       (tc.a.core/lines->activities)
       (tc.activity/summarize-activities)
       (tc.i/summary-by-day->str)))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println (summarize (in-filename args))))
