(ns time-calc.adapter.core
  (:require [time-calc.adapter.parse :as tc.a.parse]
            [time-calc.adapter.translate :as tc.a.translate]))

(defn lines->activities [the-lines]
  (->> the-lines
       (tc.a.parse/parse-activities-by-date)
       (tc.a.translate/parsed-activities->activities)))
