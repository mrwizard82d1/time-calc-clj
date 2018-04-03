(ns time-calc.io
  (:require [clojure.string :as str]))

(defn all-lines [filename]
  (-> filename
      slurp
      (str/split #"\r?\n")))

(defn summary->str [summary]
  (str/join "\n"
            (for [[name duration] summary]
              (str name ": " duration))))

(defn summary-by-day->str [summary-by-day]
  (str/join "\n\n" (for [[day summary] summary-by-day]
                     (str/join "\n" [day (summary->str summary)]))))

