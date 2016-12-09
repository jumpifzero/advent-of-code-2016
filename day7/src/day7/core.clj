;;
;; Tiago Almeida 2016
;;
;; Test with 'lein test' in the main folder
;; Run with 'lein run' in the main folder
;;
;; Tests defined in test/app/core_test.clj
;;


(ns day7.core
  (:gen-class))


(defn contains-abba
	[ipv7]
	(let [abba (re-find #".*(\w)(\w)(\2)(\1).*" ipv7)]
		(and 
			(not (nil? abba)) ; there is a match
			(not (= (abba 1) (abba 2)))))) ; abba letters differ


(defn not-contains-abba-in-hypers
	"Get the hypernet sequences. Then make sure no abba there"
	[ipv7]
	(let [hypernets (re-seq #"\[\w*\]" ipv7)
				abbas? (map contains-abba hypernets)]
		(not (some true? abbas?))))
	

(defn tls
	[ipv7-lst]
	(filter (fn [s] (and (contains-abba s) (not-contains-abba-in-hypers s))) ipv7-lst))


(defn -main
  "Prints result for both parts of day 7"
  [& args]
  (println (count (tls (clojure.string/split-lines (slurp "./resources/day_7_input.txt"))))))
