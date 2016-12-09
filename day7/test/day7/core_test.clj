;;
;; Tiago Almeida 2016
;;
;; Test with 'lein test' in the main folder
;; Run with 'lein run' in the main folder
;;
;; Tests for day 7 Advent of Code 2016
;;


(ns day7.core-test
  (:require [clojure.test :refer :all]
            [day7.core :refer :all]))


(deftest a-test
  (testing "Basic tests"
    (is (= 5 (count (clojure.string/split-lines (slurp "./resources/mini.txt")))))
    (is (= 2 (count (tls (clojure.string/split-lines (slurp "./resources/mini.txt"))))))
  ))


(deftest test2
  (testing "Some more basic tests"
  	(let 
  		[input (clojure.string/split-lines (slurp "./resources/mini2.txt"))]
    	(is (= 30 (count input)))
  		)))
 

 (deftest test3
 	(testing "Example input"
 		(is (= 1 (count (tls (list "abba[mnop]qrst")))))
 		(is (= 0 (count (tls (list "abcd[bddb]xyyx")))))
 		(is (= 0 (count (tls (list "aaaa[qwer]tyui")))))
 		(is (= 1 (count (tls (list "ioxxoj[asdfgh]zxcvbn")))))
 	))


(deftest test4
  (testing "Total result"
  	(let 
  		[input (clojure.string/split-lines (slurp "./resources/day_7_input.txt"))]
    	(is (= (count input) 2000)) 		; Do we read everything?
    	(is (= (count (tls input)) 118)) 	; Do we get the right result?
  		)))