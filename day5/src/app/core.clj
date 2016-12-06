;;
;; Tiago Almeida 2016
;;
;; Test with 'lein test' in the main folder
;; Run with 'lein run' in the main folder
;;
;; Tests defined in test/app/core_test.clj
;;

(ns app.core
  (:gen-class))

(use 'digest)
(use 'clojure.test)


(defn password-for-door
  "Solution to problem 1"
  [door-id-str]
  (reduce 
    (fn [password token] 
      (if (= (count password) 8)
        (reduced password)
        (if (= (subs token 0 5) "00000")
          (str password (subs token 5 6)) 
          password))) 
    "" 
    (map digest/md5 (map (fn [s] (str door-id-str s)) (range)))))


(defn -main
  "Prints solution to problem 1"
  [& args]
  (println (password-for-door "uqwqemis")))