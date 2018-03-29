(ns time-calc.adapter
  (:require [clojure.string :as str]
            [java-time :as jt]))

(defn no-empty-lines [lines]
  (filter not-empty lines))

(defn activity-month-date-text [text]
  (second (str/split text #"\s")))

(defn parse-date [month-day-text]
  (jt/month-day "dd-MMM" month-day-text))

(defn activities-by-day [lines]
  (->> lines
       no-empty-lines
       (partition-by #(re-find #"#\s+(\d{2}-\w{3})" %))
       (partition 2)
       (map #(vector (parse-date (activity-month-date-text (first (first %)))) (second %)))
       (into {} )))
