(ns time-calc.io
  (:require [clojure.string :as str]))

(defn all-lines [filename]
  (-> filename
      slurp
      (str/split #"\r?\n")))
