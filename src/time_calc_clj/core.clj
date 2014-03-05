(ns time-calc-clj.core
  (:use (clojure [string :only [split-lines] :as str]))
  (:gen-class))

(defn write-summary
  "Write the task summary to *out*."
  [task-summary]
  (println "The summary."))

(defn summarize-tasks
  "Summarize a sequence of tasks."
  [task-seq])

(defn lines->tasks
  "Convert a sequences of lines to tasks."
  [line-seq])

(defn read-all-lines
  "Read all lines from a file."
  [filename]
  (str/split-lines (slurp filename)))
  
(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (let [filename "time_calc.txt"]
    (write-summary (summarize-tasks (lines->tasks (read-all-lines
                                                   filename)))))) 

