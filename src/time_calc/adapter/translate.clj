(ns time-calc.adapter.translate
  (:require [java-time :as jt]))

(def start-time first)

(def details second)

(defn duration [activity]
  (get activity 2))

(defn make-activity-stamp [activity-day activity-time]
  "Construct a date time stamp for an activity from its day and its time."
  (jt/local-date-time (apply jt/local-date (concat [(jt/as (jt/year) :year)]
                                                   (jt/as activity-day :month-of-year :day-of-month)))
                      activity-time))

(defn parsed-activities->stamped-activities [parsed-activities]
  "Transform a sequence of parsed activities to activities with time stamps."
  (for [[day activities-with-start-time] parsed-activities]
      (map #(vector (make-activity-stamp day (start-time %))
                    (details %))
           activities-with-start-time)))

(defn update-values [m f & args]
  "A generic function to update the values in a map."
  (into {} (for [[k v] m] [k (apply f v args)])))

(defn stamped-activities->grouped-activities [stamped-activities]
  "Create a map of activities"
  (let [activity-date first]
    (->> stamped-activities
         (apply concat)
         (group-by #(jt/local-date (activity-date %))))))

(defn durations [details]
  (map #(jt/duration (start-time %1) (start-time %2)) details (drop 1 details)))

(defn add-durations-to-details [details]
  (let [details-durations (durations details)]
    (map #(conj %1 %2) details details-durations)))

(defn add-durations-to-grouped-activities [grouped-activities]
  (update-values grouped-activities add-durations-to-details))

(defn summarize-details-with-durations [details-with-durations]
  (reduce #(assoc %1
                  (details %2)
                  (jt/plus (get %1 (details %2) (jt/duration))
                           (duration %2)))
          {}
          details-with-durations))

(defn summarize-details-with-durations-for-day [grouped-activities-with-durations]
  (update-values grouped-activities-with-durations
                 summarize-details-with-durations))

(defn parsed-activities->activities [parsed-activities]
  (->> parsed-activities
       (parsed-activities->stamped-activities)
       (stamped-activities->grouped-activities)
       (add-durations-to-grouped-activities)
       (summarize-details-with-durations-for-day)))
