(use '[clojure.string :only [split]])

(def input (mapv #(Integer/parseInt %) (-> "input" slurp (split #","))))
(count (nth (iterate (fn [pop] (mapcat #(if (pos? %) [(dec %)] [6 8]) pop)) input) 80)) ; part 1

(def sc 
  (memoize (fn [days timer]
    (cond
      (= days 0) 1
      (= timer 0) (+ (sc (dec days) 6) (sc (dec days) 8))
      :else (sc (dec days) (dec timer))))))

(reduce + (map #(sc 256 %) input))