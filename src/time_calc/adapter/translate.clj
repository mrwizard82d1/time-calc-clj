(ns time-calc.adapter.translate
  (:require [java-time :as jt]))

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

(defn create-activities-by-day-map [parsed-activities]
  "Create a map of activities"
  (->> (-> parsed-activities
           (activities-by-day-with-complete-start-times))
       (apply concat)
       (group-by #(jt/local-date (first %)))))
