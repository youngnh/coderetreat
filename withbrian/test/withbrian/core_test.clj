(ns withbrian.core-test
  (:use [withbrian.core] :reload-all)
  (:use [clojure.test]))

(deftest test-world-all-dead
  (let [world (create-world)]
   (is (every? (fn [s] (false? (:alive s))) (map deref (flatten world))))))

(deftest test-make-live
  (let [world (create-world)]
   (testing "one cell"
     (make-live world 10 10)
     (is (:alive (deref (place world [10 10])))))))

(deftest test-count-live
  (is (= 0 (count-live (take 5 (repeat (struct cell false)))))))

(run-tests)