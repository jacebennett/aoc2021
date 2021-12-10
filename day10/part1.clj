(use '[clojure.string :only [split-lines split]])

(def input (-> "input" slurp split-lines))

(defn first-illegal-char [s]
  (let [pairs {\( \) \[ \] \{ \} \< \>}]
    (loop [cs s st []]
      (cond
        (contains? pairs (first cs)) (recur (rest cs) (conj st (first cs)))
        (= (first cs) (pairs (peek st))) (recur (rest cs) (pop st))
        :else (first cs)))))

(def score {\) 3 \] 57 \} 1197 \> 25137})

(->> input
  (map first-illegal-char)
  (filter some?)
  (map score)
  (reduce +))
