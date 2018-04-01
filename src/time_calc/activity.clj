(ns time-calc.activity
  (:require [java-time :as jt]))

(def start-time first)

(defn durations [activities]
  (map #(jt/duration (start-time %1) (start-time %2)) activities (drop 1 activities)))

(defn summarize-activities [activities]
  (map conj activities (durations activities)))

(defn summarize-activities-by-day [activities-by-day]
  (let [activity-day first
        activities second]
    (into (sorted-map)
          (reduce #(assoc
                     %1
                     (activity-day %2)
                     (summarize-activities (activities %2))) {} activities-by-day))))
