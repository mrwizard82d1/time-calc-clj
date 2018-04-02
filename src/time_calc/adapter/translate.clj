(ns time-calc.adapter.translate
  (:require [java-time :as jt]))

(defn make-activity-stamp [activity-day activity-time]
  "Construct a date time stamp for an activity from its day and its time."
  (jt/local-date-time (apply jt/local-date (concat [(jt/as (jt/year) :year)]
                                                   (jt/as activity-day :month-of-year :day-of-month)))
                      activity-time))

(defn parsed-activities->stamped-activities [parsed-activities]
  "Transform a sequence of parsed activities to activities with time stamps."
  (let [activity-time #(first %)
        activity-details #(second %)]
    (for [[day activities-with-start-time] parsed-activities]
      (map #(vector (make-activity-stamp day (activity-time %))
                    (activity-details %))
           activities-with-start-time))))

(defn update-values [m f & args]
  "A generic function to update the values in a map."
  (into {} (for [[k v] m] [k (apply f v args)])))

(defn stamped-activities->grouped-activities [stamped-activities]
  "Create a map of activities"
  (->> stamped-activities
       (apply concat)
       (group-by #(jt/local-date (first %)))))

(defn parsed-activities->activities [parsed-activities]
  (->> parsed-activities
       (parsed-activities->stamped-activities)
       (stamped-activities->grouped-activities)))
