(ns withjesse.core
  (:use [clojure.string :only (join)]))

(defn count-live [x]
  (get (frequencies x) '* 0))

(defn die? [x]
  (let [alive (count-live x)]
    (or (< alive 2)
	(> alive 3))))

(defn born? [x]
  (= (count-live x) 3))

(defn empty-board [r c]
  (let [row (take c (repeat '.))]
    (take r (repeat row))))

(defn print-board [board]
  (print (str (join "\n" (map (fn [row] (join " " row)) board))
	      "\n")))

(defn get-cell [board r c]
  (nth (nth board r) c))

(defn resurrect-cell [board r c]
  nil)