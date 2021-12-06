(use '[clojure.string :only (split-lines starts-with?)])

(def input (split-lines (slurp "input.txt")))

(defn rotate [table]
  (for [col (-> table first count range)]
    (for [row table]
      (nth row col))))

(def gamma
  (->> input
       rotate
       (map #(->> % frequencies (apply max-key val) key))
       (apply str)))

(def epsilon
  (->> gamma
       (map {\0 \1 \1 \0})
       (apply str)))

(* (Integer/parseInt gamma 2) (Integer/parseInt epsilon 2))

;; (println "Part 1")
;; (println "======")
;; (println "gamma =" (Integer/parseInt gamma 2) "(" gamma ")")
;; (println "epsilon =" (Integer/parseInt epsilon 2) "(" epsilon ")")
;; (println "power consumption = " (* (Integer/parseInt gamma 2) (Integer/parseInt epsilon 2)))
;; (println)

(defn search [next-bit vals]
  (loop [prefix "" xs vals]
    (if (= (count xs) 1)
      (first xs)
      (let [nextp (str prefix (next-bit (count prefix) xs))]
        (recur nextp (filter #(starts-with? % nextp) xs))))))

(defn most-common-bit [col vals]
  (->> vals
       (map #(nth % col))
       frequencies
       (apply max-key val)
       key))

(defn least-common-bit [col vals]
  ({\0 \1 \1 \0} (most-common-bit col vals)))

(def orate (search most-common-bit input))
(def srate (search least-common-bit input))

(* (Integer/parseInt orate 2) (Integer/parseInt srate 2))

;; (println "Part 2")
;; (println "======")
;; (println "orate =" (Integer/parseInt orate 2) "(" orate ")") ; 000111100110
;; (println "srate =" (Integer/parseInt srate 2) "(" srate ")") ; 101011100000
;; (println "life support rating =" (* (Integer/parseInt orate 2) (Integer/parseInt srate 2)))