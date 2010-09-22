(ns narwal.core
  (:import [java.util Random]))

(defstruct cell :alive :neighbors :pos)

(defn inc-neighbors [c]
  (assoc c :neighbors (inc (:neighbors c))))

(defn make-alive [c]
  (assoc c :alive true))

(defn survive? [cell]
  (or (= (:neighbors cell) 3)
      (and (:alive cell)
	   (= (:neighbors cell) 2))))

(defn create-world [] {})

(defn get-cells [world]
  (vals world))

(defn update-cell [world pos f]
  (assoc world pos (f (get world pos (struct cell false 0 pos)))))

(defn without [x xs]
  (remove #(= % x) xs))

(defn neighbors-of [[x y]]
  (without [x y]
	   (for [xop [dec identity inc]
		 yop [dec identity inc]]
	     [(xop x) (yop y)])))

(defn add-cell [world pos]
  (-> (reduce #(update-cell %1 %2 inc-neighbors) world (neighbors-of pos))
      (update-cell pos make-alive)))

(defn step-world [world]
  (reduce add-cell (create-world) (map :pos (filter survive? (get-cells world)))))

(defn add-glider [world]
  (reduce add-cell world [      [1 0]
			              [2 1]
				      [0 2] [1 2] [2 2]]))

(defn add-diagonal [world length]
  (reduce add-cell world (take length (iterate #(map inc %) [0 0]))))

(defn add-random [world n [xlimit ylimit]]
  (let [xrand (fn [] (.. (Random.) (nextInt xlimit)))
	yrand (fn [] (.. (Random.) (nextInt ylimit)))
	randoms (interleave (repeatedly xrand) (repeatedly yrand))]
    (reduce add-cell world (take n (distinct (partition 2 randoms))))))