(use '[clojure.string :only [split-lines split]])

(defn readln [line] (mapv #(Integer/parseInt %) (split line #"")))
(def input (->> "input" slurp split-lines (mapv readln)))

(defn lookup [pt] (get-in input pt))

(defn adj* [n]
  (filter (complement nil?)
    (for [d (range -1 2) :when (not (zero? d))]
      (let [n' (+ n d)]
        (when (and (>= n' 0) (< n' 100))
          n')))))

(defn adjacent [[x y]]
  (concat
    (map (fn [x] [x y]) (adj* x))
    (map (fn [y] [x y]) (adj* y))))

(defn local-min? [pt]
  (let [val (lookup pt)]
    (every? #(< val (lookup %)) (adjacent pt))))

(->> (for [x (range 100) y (range 100)] [x y])
  (filter local-min?)
  (map lookup)
  (map inc)
  (reduce +))
