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


(use '[clojure.string :only (split)])
(require 'clojure.set)


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
	(filter 
		(fn [s] 
			(and 	(contains-abba s) 
						(not-contains-abba-in-hypers s))) 
		ipv7-lst))


(defn abas-in-supernets
	"Given a string, returns all xYx sequences outside square brackets"
	[ipv7-str]
	(let 
		[ais (re-seq #"(?<!\[)(?=\w*)(\w)(?=(\w)(\1))(?=\w*)(?!\])" 
					ipv7-str)]
		;; ais (abas-in-supernets) is a list of vectors or nil:
		;; e.g. for input "abaca[123]abc", returns
		;; 			(["a" "a" "b" "a"] ["a" "a" "c" "a"]) 
		;; The regex uses a char X (\w) followed by a lookahead matching
		;; Y and X. We use a lookahead instead of a literal match
		;; so that Yx is not consumed from the input. This allows
		;; it to pickup overlapping ABAs: xYxZx -> xYx | xZx.
		;; The regex above picks up AAA as well which is invalid so we 
		;; filter them out below.
		(if (not (nil? ais))
			(let [filtered-lst (filter (fn [v] (not= (nth v 2) (nth v 1))) ais)]
				(if (empty? filtered-lst)
					nil	; if after filtering we get an empty list, return nil
					filtered-lst)) 
			ais) ;nil
		))


(defn abas-in-hypernets
	"Given a string, returns all xYx sequences inside square brackets"
	[ipv7-str]
	(let 
		[aih (re-seq #"(?<=\[)(?=\w*)(\w)(?=(\w)(\1))(?=\w*)(?=\])" 
					ipv7-str)]
		;; See abas-in-supernets for how the regex works. 
		;; It is very similar
		(if (not (nil? aih))
			(let [filtered-lst (filter (fn [v] (not= (nth v 2) (nth v 1))) aih)]
				(if (empty? filtered-lst)
					nil	; if after filtering we get an empty list, return nil
					filtered-lst)) 
			aih) ;nil
		))


(defn prn-ais
	[]
	(list 
		(abas-in-supernets "aba[bab]xyz")
		(abas-in-supernets "abc[aba]xyz")
		(abas-in-supernets "daba[aba]ede")
		(abas-in-supernets "dabao[aba]ede")
		(abas-in-supernets "ddfgfo[aba]ede")
		(abas-in-supernets "abaca[123]abc")
		(abas-in-supernets "aaaa[123]abc")
		))


(defn prn-aih
	[]
	(list 
		(abas-in-hypernets "aba[bab]xyz")
		(abas-in-hypernets "abc[aba]xyz")
		(abas-in-hypernets "daba[aba]ede")
		(abas-in-hypernets "dabao[aba]ede")
		(abas-in-hypernets "ddfgfo[aba]ede")
		(abas-in-hypernets "abaca[123]abc")
		(abas-in-hypernets "aaaa[123]abc")
		))


(defn hypernets 
	"All sub-sequences inside brackets"
	[ipv7]
	(re-seq #"(?<=\[)\w*(?=\])" ipv7))


(defn supernets 
	"All sub-sequences outside brackets"
	[ipv7]
	(let [
		hypernets (hypernets ipv7)
		; transform hypernets into an hardcore regex like \[abc\]|\[cde\]
		b-hypernets (map (fn [s] (str "\\[" s "\\]")) hypernets)
		joined-b-hypernets (reduce (fn [s1 s2] (str s1 "|" s2)) b-hypernets)
		split-pattern (re-pattern joined-b-hypernets)
		]
		; split ipv7 on the split-pattern. Remove empty strings
		(filter 
			(fn [s] (> (count s) 0))
			(split ipv7 split-pattern))))


(defn abas
	"All 3 char sequece like aBa from input-str. Even overlapping"
	[input-str]
	(let [
		; all aba sequences even overlapping
		matches (re-seq #"(\w)(?=(\w)(\1))" input-str)
		; make sure b is != a
		filtered-lst (filter (fn [v] (not= (nth v 2) (nth v 1))) matches)
		]
		; join into 3 char strings
		(map (fn [v] (str (nth v 1) (nth v 2) (nth v 3))) filtered-lst)))


(defn invert-aba
	"Given a string aba, produces bab"
	[istr]
	(str (nth istr 1) (nth istr 0) (nth istr 1)))


(defn ssl
	""
	[ipv7]
	(let [
		hypernets (hypernets ipv7)
		supernets (supernets ipv7)
		hyper-abas (flatten (map abas hypernets))
		super-abas (flatten (map abas supernets))
		hyper-babs (map invert-aba hyper-abas)
		; now transform them into sets and intersect them
		super-set (set super-abas) ; set of aba in supernets
		hyper-set (set hyper-babs) ; set of bab in hypernets (inverted)
		]
		(> (count (clojure.set/intersection super-set hyper-set)) 0)))


(defn count-ssl
	"Given a list of ipv7, count the ones that are ssl"
	[ipv7-lst]
	(count (filter true? (map ssl ipv7-lst))))


(defn -main
  "Prints result for both parts of day 7"
  [& args]
  (let [input (clojure.string/split-lines 
  							(slurp "./resources/day_7_input.txt"))]
  	(println (count (tls input)))
  	(println (count-ssl input))))
