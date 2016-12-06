(ns app.core-test
  (:require [clojure.test :refer :all]
            [app.core :refer :all]))

(deftest test-password-generator
  (testing "Password generator is failed"
    (is (= "18f47a30" (password-for-door "abc")))))