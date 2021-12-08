(use '[clojure.string :only [split split-lines trim]])

(defn readln [line] (mapv #(split % #"\s+") (-> line (split #"\s*\|\s*"))))
(def input (->> "input" slurp split-lines (map readln)))

(defn permutations [colls]
  (if (= 1 (count colls))
    (list colls)
    (for [head colls
          tail (permutations (disj (set colls) head))]
      (cons head tail))))

(def stddigits
  {"abcefg"  \0
   "cf"      \1
   "acdeg"   \2
   "acdfg"   \3
   "bcdf"    \4
   "abdfg"   \5
   "abdefg"  \6
   "acf"     \7
   "abcdefg" \8
   "abcdfg"  \9})

(def possible-wirings (map zipmap (repeat [\a \b \c \d \e \f \g]) (permutations [\a \b \c \d \e \f \g])))

(defn decode-digit [scrambled wiring]
  (->> scrambled (map wiring) sort (apply str)))

(defn check-wiring [example wiring]
  (let [decoded-digits (map #(decode-digit % wiring) example)]
    (every? #(contains? stddigits %) decoded-digits)))

(defn find-wiring [ex]
  (first (filter #(check-wiring ex %) possible-wirings)))

(defn read-output [output wiring]
  (->> output (map #(decode-digit % wiring)) (map stddigits) (apply str) (Integer/parseInt)))

(defn read-outputs [inputs]
  (map (fn [[example output]] (read-output output (find-wiring example))) inputs))

(reduce + (read-outputs input))