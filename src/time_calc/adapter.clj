(ns time-calc.adapter
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

(defn make-activity-stamp [activity-day activity-time]
  "Construct a date time stamp for an activity from its day and its time."
  (jt/local-date-time (apply jt/local-date (concat [(jt/as (jt/year) :year)]
                                                   (jt/as activity-day :month-of-year :day-of-month)))
                      activity-time))

(defn activities-by-day-with-complete-start-times [activity-map]
  "Transform a sequence of lines to a map of activities for each day."
  (let [activity-time #(first %)
        activity-details #(second %)]
    (for [[day activities-with-start-time] activity-map]
      (map #(vector (make-activity-stamp day (activity-time %))
                    (activity-details %))
           activities-with-start-time))))

(defn create-activities-by-day [the-lines]
  "Create a map of activities"
  (->> (-> the-lines
           (parse-activities-by-date)
           (activities-by-day-with-complete-start-times))
       (apply concat)
       (group-by #(jt/local-date (first %)))))

