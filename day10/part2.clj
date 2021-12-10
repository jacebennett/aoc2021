(use '[clojure.string :only [split-lines split]])

(use '[clojure.string :only [split-lines split]])

(def input (-> "input" slurp split-lines))

(defn complete [s]
  (let [pairs {\( \) \[ \] \{ \} \< \>}]
    (loop [cs s st []]
      (cond
        (contains? pairs (first cs)) (recur (rest cs) (conj st (first cs)))
        (= (first cs) (pairs (peek st))) (recur (rest cs) (pop st))
        (empty? cs) (->> st (map pairs) reverse)
        :else []))))

(defn score [cs]
  (let [cscore {\) 1 \] 2 \} 3 \> 4}]
    (reduce (fn [acc c] (+ (cscore c) (* 5 acc))) 0 cs)))

(defn middle [coll] (nth coll (int (/ (count coll) 2))))

(->> input (map complete) (filter not-empty) (map score) sort middle)

(def examples
  ["[({(<(())[]>[[{[]{<()<>>"
   "[(()[<>])]({[<{<<[]>>("
   "(((({<>}<{<{<>}{[]{[]{}"
   "{<[[]]>}<{[{[{[]{()[[[]"
   "<{([{{}}[<[[[<>{}]]]>[]]"])