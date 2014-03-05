(ns time-calc-clj.core
  (:use (clojure [string :only [split, split-lines] :as str]))
  (:gen-class))

(defrecord Task [start end details])

(defn write-summary
  "Write the task summary to *out*."
  [task-summary]
  (println "The summary."))

(defn summarize-tasks
  "Summarize a sequence of tasks."
  [task-seq])

(defn line->task
  "Convert a single line to a task."
  [a-line]
  (let [[start details] (str/split a-line #"[ \t]")
        end start]
    (Task. start end details)))

(defn zip
  "Equivalent of Python zip()."
  [& colls]
  (apply map vector colls))

(defn join-tasks
  "Creates a contiguous time range for tasks."
  [tasks]
  (let [zipped-tasks (zip tasks (drop 1 tasks))]
    (map #(Task. (:start (%1 0)) (:start (%1 1)) (:details (%1 0)))
         zipped-tasks)))

(defn lines->tasks
  "Convert a sequences of lines to tasks."
  [line-seq]
  (let [raw-tasks (map line->task line-seq)]
    (join-tasks raw-tasks)))

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

