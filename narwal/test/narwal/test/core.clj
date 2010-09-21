(ns narwal.test.core
  (:use [narwal.core] :reload-all)
  (:use [clojure.test]))

(deftest test-cell-struct
  (is (false? (:alive (struct cell false))))
  (is (= 0 (:neighbors (struct cell false 0))))
  (is (= [1 1] (:pos (struct cell false 0 [1 1])))))

(deftest test-manipulate-cell
  (let [cell (struct cell false 1)]
    (is (= 2 (:neighbors (inc-neighbors cell))))
    (is (:alive (make-alive cell)))))

(deftest test-rules
  (let [less-than-two-cells (map #(struct cell true %) [0 1])
	more-than-three-cells (map #(struct cell true %) [4 5 6 7 8])]
    (is (every? false? (map survive? less-than-two-cells)))
    (is (every? false? (map survive? more-than-three-cells))))

  (is (survive? (struct cell true 2)))
  (is (survive? (struct cell true 3)))

  (let [dead-not-near-three (map #(struct cell false %) [0 1 2 4 5 6 7 8])]
    (is (every? false? (map survive? dead-not-near-three))))

  (is (survive? (struct cell false 3))))

(deftest test-create-world
  (let [world (create-world)]
   (is (empty? (get-cells world)))))

(deftest test-neighbors-of
  (is (= (into #{} (neighbors-of [1 1]))
	 #{[0 0] [1 0] [2 0]
	   [0 1]       [2 1]
	   [0 2] [1 2] [2 2]})))

(deftest test-update-cell
  (let [world (create-world)
	new-world (update-cell world [1 1] make-alive)
	brave-world (update-cell world [0 0] inc-neighbors)]
    (is (= 0 (count (get-cells world))))

    (is (= 1 (count (get-cells new-world))))
    (let [cell (first (get-cells new-world))]
      (is (:alive cell))
      (is (= 0 (:neighbors cell)))
      (is (= [1 1] (:pos cell))))

    (is (= 1 (count (get-cells brave-world))))
    (let [cell (first (get-cells brave-world))]
      (is (false? (:alive cell)))
      (is (= 1 (:neighbors cell)))
      (is (= [0 0] (:pos cell))))))

(deftest test-add-cell  
  (let [world (-> (create-world)
		  (add-cell [1 1]))
	cells (sort-by :pos (get-cells world))
	neighbors1 (take 4 cells)
	neighbors2 (take-last 4 cells)
	cell (nth cells 4)]
    (is (= 9 (count cells)))

    (is (:alive cell))
    (is (= 0 (:neighbors cell)))

    (is (every? #(= (:neighbors %) 1) neighbors1))
    (is (every? #(= (:neighbors %) 1) neighbors2))))

(deftest test-step-world
  (testing "one cell dies"
   (let [world (-> (create-world)
		   (add-cell [1 1])
		   (step-world))]
     (is (empty? (get-cells world)))))

  (testing "cell with four neighbors dies"
    (let [world (-> (create-world)
		    (add-cell [1 0])
		    (add-cell [2 0])
		    (add-cell [1 1])
		    (add-cell [0 2])
		    (add-cell [2 2])
		    (step-world))]
      (is (some #(and (= [1 1] (:pos %))
		      (not (:alive %)))
		(get-cells world)))))

  (testing "two neighbors lives"
    (let [world (-> (create-world)
		    (add-cell [2 0])
		    (add-cell [1 1])
		    (add-cell [0 2])
		    (step-world))]
      (is (some #(and (= [1 1] (:pos %))
		      (:alive %))
		(get-cells world)))))

  (testing "three neighbors comes to life"
    (let [world (-> (create-world)
		    (add-cell [1 0])
		    (add-cell [0 1])
		    (add-cell [2 1])
		    (step-world))]
      (is (some #(and (= [1 1] (:pos %))
		      (:alive %))
		(get-cells world))))))

(run-tests)