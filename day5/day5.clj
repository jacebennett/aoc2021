(use '[clojure.string :only [split split-lines trim]])

(def input (-> "input" slurp split-lines))
(defn toi [s] (Integer/parseInt s))
(defn pt [s] (mapv toi (map trim (split s #","))))
(defn seg [line] (mapv pt (split line #"->")))

(defn hor? [[[x1 y1] [x2 y2]]] (= y1 y2))
(defn vert? [[[x1 y1] [x2 y2]]] (= x1 x2))
(defn segpts [[[x1 y1] [x2 y2]]]
  (let [dx (cond (< x1 x2) 1 (> x1 x2) -1 :else 0)
        dy (cond (< y1 y2) 1 (> y1 y2) -1 :else 0)
        steps (inc (max (* (- x2 x1) dx) (* (- y2 y1) dy)))]
    (for [step (range steps)] [(+ x1 (* step dx)) (+ y1 (* step dy))])))

(->> input (map seg) (filter #(or (hor? %) (vert? %))) (mapcat segpts) frequencies (filter #(> (val %) 1)) count)