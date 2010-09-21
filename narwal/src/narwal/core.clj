(ns narwal.core)

(defstruct cell :alive :neighbors)

(defn create-world []
  {})

(defn neighbors-of [x y]
  (for [row (range (dec y) (+ y 2))
	col (range (dec x) (+ x 2))	
	:when (not (and (= col x) (= row y)))]
    [col row]))

(defn inc-neighbors [c]
  (struct cell (:alive c) (inc (:neighbors c))))

(defn make-alive [c]
  (struct cell true (:neighbors c)))

(defn get-cell [world x y]
  (get world [x y] (struct cell nil 0)))

(defn add-cell [world x y]
  (let [f (fn [world [x y]]
	    (assoc world [x y] (inc-neighbors (get-cell world x y))))]
    (-> (reduce f world (neighbors-of x y))
	(assoc [x y] (make-alive (get-cell world x y))))))

(defn survive? [alive? neighbors]
  (or (= neighbors 3)
      (and alive?
	   (= neighbors 2))))

(defn step-world [world]
  (let [f (fn [world [[x y] cell]]
	    (if (survive? (:alive cell) (:neighbors cell))
	      (add-cell world x y)
	      world))]
    (reduce f (create-world) world)))

(defn add-glider [world]
  (-> world
      (add-cell 1 0)
      (add-cell 2 1)
      (add-cell 0 2)
      (add-cell 1 2)
      (add-cell 2 2)))