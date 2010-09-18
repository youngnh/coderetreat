(ns withtomasz.core-test
  (:use [withtomasz.core] :reload-all)
  (:use [clojure.test]))

(deftest empty-test
  (let [world (life-iter #{})]
    (is (empty? world))))

(deftest one-test
  (let [world (life-iter #{[1 1]})]
    (is (empty? world))))

(deftest two-test
  (let [world (life-iter #{[1 1] [0 0]})]
    (is (empty? world))))

(deftest three-test
  (let [world (life-iter #{[1 1] [0 0] [0 2]})]
    (is (= 2 (count world)))
    (is (contains? world [1 1]))
    (is (contains? world [0 1]))))	

(deftest three-test
  (let [world (life-iter #{[1 1] [0 0] [0 2]})]
    (is (set? world))))

(run-tests)