(ns withben.core-test
  (:use [withben.core] :reload-all)
  (:use [clojure.test]))

(deftest test-print-cells
  (testing "print single line of cells"
    (is (= "* * *" (print-cells '[* * *])))
    (is (= "* . ." (print-cells '[* . .]))))

  (testing "multiple lines of cells"
    (is (= "* * *\n* . ." (print-matrix '[[* * *]
					  [* . .]])))))

(deftest test-count-live-neighbors
  (is (= 0 (count-live '[. . . . . . . .])))
  (is (= 1 (count-live '[*])))
  (is (= 2 (count-live '[* . * .]))))

(deftest test-cell-rules
  (testing "rule 1: fewer than 2 neighbors dies"
    (is (die? '[. . . . . . . .]))
    (is (die? '[* . . . . . . .]))
    (is (not (die? '[* * . . . . . .])))
    (is (not (die? '[* * * . . . . .])))))


(run-tests)