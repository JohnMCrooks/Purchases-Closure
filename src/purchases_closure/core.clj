(ns purchases-closure.core
  (:require [clojure.string :as str])
  (:gen-class))

(defn -main []
  (let [purchases (slurp "purchases.csv")
        purchases (str/split-lines purchases)
        purchases (map (fn [line] (str/split line #","))
                    purchases)
        header (first purchases)
        purchases (rest purchases)
        purchases (map(fn [line] (zipmap header line))
                    purchases)
        _ (println "What would you like to filter by? [Furniture, Alcohol, Toiletries, Shoes, Food, Jewelry]")
        uinput (read-line)
        purchases(filter(fn [line] (= (get line "category") uinput))
                   purchases)
        file-text (pr-str purchases)]
       
    (spit "filtered_purchases.edn" file-text)))
            
            
;
        
