(ns withmario.core-test
  (:use [withmario.core] :reload-all)
  (:use [clojure.test]))

(deftest test-world
  (is (empty? (create-world))))

(deftest test-add-cell
  (let [world (create-world)
	new-world (add-cell world 1 1)]
	(is (= 1 (count new-world)))))

(deftest test-step-world
  (let [world (add-cell (create-world) 1 1)
	new-world (step-world world)]
    (is (empty? new-world))))

(deftest test-neighbors
  (let [cells (neighbors [1 1])]
    (is (= 8 (count cells)))
    (is (contains? cells [0 0]))
    (is (contains? cells [0 1]))
    (is (contains? cells [0 2]))
    (is (contains? cells [1 0]))
    (is (contains? cells [1 2]))
    (is (contains? cells [2 0]))
    (is (contains? cells [2 1]))
    (is (contains? cells [2 2]))))

(deftest test-rule-4
  (let [world (-> (create-world)
		  (add-cell 0 1)
		  (add-cell 1 0)
		  (add-cell 1 2))
	new-world (step-world world)]
    (is (= 4 (count new-world)))))

(run-tests)