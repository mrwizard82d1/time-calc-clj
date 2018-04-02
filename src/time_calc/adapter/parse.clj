(ns time-calc.adapter.parse
  (:require [clojure.string :as str]
    [java-time :as jt]))

(defn no-empty-lines [lines]
  (filter not-empty lines))

(defn activity-month-date-text [text]
  (second (str/split text #"\s")))

(defn parse-date [month-day-text]
  (jt/month-day "dd-MMM" month-day-text))

(defn extract-activity-date [activity-on-day]
  (parse-date (activity-month-date-text (first (first activity-on-day)))))

(defn parse-time [hh-mm-time]
  (jt/local-time "HHmm" hh-mm-time))

(defn extract-start [activity-text]
  (->> (first (str/split activity-text #"\s+"))
       (parse-time)))

(defn extract-details [activity-text]
  (second (str/split activity-text #"\s+" 2)))

(defn extract-activities [activity-on-day]
  (->> (second activity-on-day)
       (map #(vector (extract-start %)
                     (extract-details %)))))

(defn parse-activities-by-date [lines]
  (->> lines
       no-empty-lines
       (partition-by #(re-find #"#\s+(\d{2}-\w{3})" %))
       (partition 2)
       (map #(vector (extract-activity-date %)
                     (extract-activities %)))))
