(ns withben.core
  (:use [clojure.string :only (join)]))

(defn print-cells [cells]
  (join " " cells))

(defn print-matrix [matrix]
  (join "\n" (map print-cells matrix)))

(defn count-live [peeps]
  (get (frequencies peeps) '* 0))

(defn die? [neighbors]
  (frequencies neighbors))