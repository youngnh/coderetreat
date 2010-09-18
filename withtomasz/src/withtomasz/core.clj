(ns withtomasz.core)

(defn survives [world [cell count]]
  (if (= count 3)
    cell
    (if (and (= count 2) (contains? world cell))
      cell)))

(defn life-iter [world]
  (into #{} (remove nil? (map (partial survives world) (frequencies (mapcat get-neighbours world))))))

(defn get-neighbours [[x y]]
  [[(- x 1) (- y 1)] [x        (- y 1)] [(+ x 1) (- y 1)]
   [(- x 1)       y]                    [(+ x 1)      y]
   [(- x 1) (+ y 1)] [x        (+ y 1)] [(+ x 1) (+ y 1)]])
