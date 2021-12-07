(use '[clojure.string :only [split split-lines]])

(def input (map #(Integer/parseInt %) (-> "input" slurp (split #","))))

(def sum #(reduce + %))
(defn cost [rate xs x] (sum (map #(rate (Math/abs (- x %))) xs)))
(defn cnv [rate xs] (->> (range 1000) (map #(cost rate xs %)) (reduce min)))

(cnv identity input) ; part 1

(cnv #(-> % inc range sum) input) ; part 2
