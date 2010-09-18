(ns withmario.core)

(defn create-world []
  '())

(defn add-cell [world x y]
  '(1))

(defn step-world [world]
  '())

(defn neighbors [[x y]]
  (into #{} (remove #(= [x y] %)
		    (for [a (range (dec x) (+ x 2))
			  b (range (dec y) (+ y 2))]
		      [a b]))))