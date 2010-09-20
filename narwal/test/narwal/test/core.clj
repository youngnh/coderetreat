(ns narwal.test.core
  (:use [narwal.core] :reload-all)
  (:use [clojure.test]))

(deftest test-cell-struct
  (is (nil? (:alive (struct cell))))
  (is (= 0 (:neighbors (struct cell false 0)))))

(deftest test-create-world
  (is (empty? (create-world))))

(deftest test-add-cell
  (let [world (-> (create-world)
		  (add-cell 1 1))
	cell (get world [1 1])]
    (is (:alive cell))
    (is (= (:neighbors cell) 0))
    (is (= (count world) 9))
    (is (= (:neighbors (get world [0 0])) 1))
    (is (= (:neighbors (get world [1 0])) 1))
    (is (= (:neighbors (get world [2 0])) 1))
    (is (= (:neighbors (get world [0 1])) 1))
    (is (= (:neighbors (get world [2 1])) 1))
    (is (= (:neighbors (get world [0 2])) 1))
    (is (= (:neighbors (get world [1 2])) 1))
    (is (= (:neighbors (get world [2 2])) 1))))

(deftest test-increment-neighbors
  (let [cell (struct cell false 1)]
    (is (= 2 (:neighbors (inc-neighbors cell))))
    (is (:alive (make-alive cell)))))

(deftest test-neighbors-updated
  (let [world (-> (create-world)
		  (add-cell 1 1)
		  (add-cell 2 1))]
    (is (= (:neighbors (get world [1 0])) 2))
    (is (= (:neighbors (get world [2 0])) 2))
    (is (= (:neighbors (get world [3 0])) 1))
    (is (= (:neighbors (get world [1 1])) 1))
    (is (= (:neighbors (get world [3 1])) 1))
    (is (= (:neighbors (get world [1 2])) 2))
    (is (= (:neighbors (get world [2 2])) 2))
    (is (= (:neighbors (get world [3 2])) 1))))

(deftest test-rules
  (is (every? false? (map (partial survive? true) [0 1])))
  (is (every? true? (map (partial survive? true) [2 3])))
  (is (every? false? (map (partial survive? true) [4 5 6 7 8])))
  (is (every? false? (map (partial survive? false) [0 1 2 4 5 6 7 8])))
  (is (survive? false 3)))

(run-tests)