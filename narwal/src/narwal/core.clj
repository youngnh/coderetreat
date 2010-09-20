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

(defn step-world [world]
  (loop [w {}
	 cells (filter (fn [[[x y] c]] (survive? (:alive c) (:neighbors c))) world)]
    (if (empty? cells)
      w
      (let [[[x y] _] (first cells)]
	(recur (add-cell w x y) (rest cells))))))

(defn add-glider [world]
  (-> world
      (add-cell 1 0)
      (add-cell 2 1)
      (add-cell 0 2)
      (add-cell 1 2)
      (add-cell 2 2)))