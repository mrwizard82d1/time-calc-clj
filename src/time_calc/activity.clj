(ns time-calc.activity
  (:require [time-calc.helpers :as tc.h]
            [java-time :as jt]))

(defn summarize-details-with-durations [details-with-durations]
  (let [details second
        duration #(get % 2)]
    (reduce #(assoc %1
                    (details %2)
                    (jt/plus (get %1 (details %2) (jt/duration))
                             (duration %2)))
            {}
            details-with-durations)))

(defn summarize-details-with-durations-for-day [grouped-activities-with-durations]
  (tc.h/update-values grouped-activities-with-durations
                      summarize-details-with-durations))

(defn grouped-activities-with-durations->activities [grouped-activities-with-durations-by-day]
  (summarize-details-with-durations-for-day grouped-activities-with-durations-by-day))
