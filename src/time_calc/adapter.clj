(ns time-calc.adapter
  (:require [clojure.string :as str]))

(defn no-empty-lines [lines]
  (filter not-empty lines))

(defn activities-by-day [lines]
  (->> lines
       (partition-by #(re-find #"#\s+(\d{2}-\w{3})" %))
       (partition 2)
       (map #(vector (second (str/split (first (first %)) #"\s")) (second %)))))
