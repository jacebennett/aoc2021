(use '[clojure.string :only [split split-lines trim]])

(def input (-> "input" slurp split-lines))
(defn read-out [line] (-> line (split #"\s*\|\s*") second (split #"\s+")))

(->> input (mapcat read-out) (map count) (filter #{2 3 4 7}) count)
