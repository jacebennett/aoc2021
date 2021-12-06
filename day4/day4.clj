(use '[clojure.string :only (split trim split-lines blank?)])
(use '[clojure.set :as s])

(defn toi [s] (Integer/parseInt s))
(def input (-> "input" slurp split-lines))
(def pick-input (map toi (split (first input) #",")))
(def boards 
  (->> (rest input)
       (partition-by blank?)
       (filter #(-> % first blank? not))
       (mapv (fn [b] (mapv (fn [r] (mapv toi (split (trim r) #"\s+"))) b)))))

(def rows identity)
(defn cols [b] (vec (for [col-idx (range 5)] (mapv #(nth % col-idx) b))))

(defn bingo? [picks board]
  (or (some #(every? picks %) (rows board))
      (some #(every? picks %) (cols board))))

(defn winning-boards [picks boards] (filter #(bingo? (set picks) %) boards))

(defn first-winner [pick-seq boards]
  (loop [pick-count 1]
    (if-let [winner (first (winning-boards (take pick-count pick-seq) boards))]
      {:board winner :picks (vec (take pick-count pick-seq))}
      (recur (inc pick-count)))))

(defn last-winner [pick-seq boards]
  (loop [pick-count 1 remaining-boards boards]
    (let [winners (winning-boards (take pick-count pick-seq) remaining-boards)]
      (if (= (count winners) (count remaining-boards))
        {:board (last winners) :picks (vec (take pick-count pick-seq))}
        (recur (inc pick-count) (remove (set winners) remaining-boards))))))

(defn score [{board :board picks :picks}]
  (let [unchosen  (s/difference (set (flatten board)) (set picks))
        total     (reduce + unchosen)
        last-pick (peek picks)]
    (* total last-pick)))
