(use '[clojure.string :only [split-lines split]])

(defn readln [line] (mapv #(Integer/parseInt %) (split line #"")))
(def input (->> "input" slurp split-lines (mapv readln)))

(defn lookup [pt] (get-in input pt))

(defn adj* [n]
  (filter #(<= 0 % 99)
    (for [d [-1 1]] (+ n d))))

(defn adjacent [[x y]]
  (concat
    (map (fn [x] [x y]) (adj* x))
    (map (fn [y] [x y]) (adj* y))))

(defn local-min? [pt]
  (let [val (lookup pt)]
    (every? #(< val (lookup %)) (adjacent pt))))

(defn find-basin [pt]
  (loop [to-visit (conj clojure.lang.PersistentQueue/EMPTY pt)
         visited #{}
         basin #{}]
    (if (empty? to-visit)
      basin
      (let [pt (peek to-visit)
            q (pop to-visit)]
        (if (or (visited pt) (>= (lookup pt) 9))
          (recur q (conj visited pt) basin)
          (recur (apply conj q (adjacent pt)) (conj visited pt) (conj basin pt)))))))

(->> (for [x (range 100) y (range 100)] [x y])
  (filter local-min?)
  (map find-basin)
  (map count)
  (sort >)
  (take 3)
  (reduce *))
