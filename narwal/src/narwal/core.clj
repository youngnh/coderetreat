(ns narwal.core)

(defstruct cell :alive :neighbors)

(defn create-world []
  {})

(defn inc-neighbors [c]
  (struct cell (:alive c) (inc (:neighbors c))))

(defn make-alive [c]
  (struct cell true (:neighbors c)))

(defn get-cell [world x y]
  (get world [x y] (struct cell false 0)))

(defn add-cell [world x y]
  (-> world
      (assoc [(dec x) (dec y)] (inc-neighbors (get-cell world (dec x) (dec y))))
      (assoc [x (dec y)] (inc-neighbors (get-cell world x (dec y))))
      (assoc [(inc x) (dec y)] (inc-neighbors (get-cell world (inc x) (dec y))))
      (assoc [(dec x) y] (inc-neighbors (get-cell world (dec x) y)))
      (assoc [(inc x) y] (inc-neighbors (get-cell world (inc x) y)))
      (assoc [(dec x) (inc y)] (inc-neighbors (get-cell world (dec x) (inc y))))
      (assoc [x (inc y)] (inc-neighbors (get-cell world x (inc y))))
      (assoc [(inc x) (inc y)] (inc-neighbors (get-cell world (inc x) (inc y))))
      (assoc [x y] (make-alive (get-cell world x y)))))

(defn survive? [alive? neighbors]
  (or (= neighbors 3)
      (and alive?
	   (= neighbors 2))))