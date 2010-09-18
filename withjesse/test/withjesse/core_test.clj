(ns withjesse.core-test
  (:use [withjesse.core] :reload-all)
  (:use [clojure.test])
  (:import [java.io StringWriter]))

(deftest test-count-live-neighbors
  (is (= 0 (count-live '[. . . . .])))
  (is (= 1 (count-live '[* . . . .])))
  (is (= 3 (count-live '[* . * . * . . . .]))))

(deftest test-rules
  (testing "fewer than two live neighbors dies"
    (is (die? '[. . . . . . . .]))
    (is (not (die? '[* * . . . . . .]))))

  (testing "more than three living neighbors dies"
    (is (die? '[* * * * . . . .])))

  (testing "2 or 3 live neighbors lives"
    (is (not (die? '[* * . . . . . .])))
    (is (not (die? '[* * * . . . . .]))))

  (testing "dead cell with 3 neighbors comes alive"
    (is (born? '[* * * . . . . .]))))

(deftest test-create-board
  (is (= (count (empty-board 3 3)) 3))
  (is (= [3 3 3] (map count (empty-board 3 3))))

  (testing "dimensions"
    (is (= (count (empty-board 5 5)) 5))
    (is (= [5 5 5 5 5] (map count (empty-board 5 5))))))

(deftest test-print-board
  (let [writer (StringWriter.)]
    (binding [*out* writer]
      (print-board (empty-board 3 3)))
    (is (= (str ". . .\n"
		 ". . .\n"
		 ". . .\n")
	     (str writer)))))

(deftest test-get-cell
  (is (= '. (get-cell (empty-board 3 3) 1 1))))

(deftest test-resurrect-cell
  (is (= '[[* . .]
	   [. . .]
	   [. . .]]
	 (resurrect-cell (empty-board 3 3) 0 0))))

(run-tests)