(ns time-calc.helpers)

(defn update-values [m f & args]
  "A generic function to update the values in a map."
  (into {} (for [[k v] m] [k (apply f v args)])))

