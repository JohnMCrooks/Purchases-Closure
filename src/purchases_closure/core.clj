(ns purchases-closure.core
  (:require [clojure.string :as str]
            [compojure.core :as c]
            [ring.adapter.jetty :as j]; jetty is a commonly used java webserver 
            [hiccup.core :as h])  ; almost the equivalent of mustache])
  (:gen-class))



(defn purchases-html [purchases]
  [:html
   [:body {:style  "background-color: #EAE2DA;"}
    [:a {:href "/" } "|  All  |"]
    [:a {:href "/Food"} "  Food  |"]  ; how to create a hyperlink and specify attributes within a tag (they go in the hashmap... HINT HINT)
    [:a {:href "/Alcohol"} "  Alcohol  |"] 
    [:a {:href "/Furniture"} "  Furniture  |"] 
    [:a {:href "/Shoes"} "  Shoes  |"] 
    [:a {:href "/Toiletries"} "  Toiletries  |"]
    [:a {:href "/Jewelry"} "  Jewelry  |"] 
    [:br
     [:br]]
 
    
    [:ul {:style "list-style-type:none; background-color: #C5C1B1; margin:auto; align:center; box-shadow: 0px 0px 10px;"}            ;creating an organized list
     (map (fn [purchase]        
            [:li (str (get purchase "customer_id") ": " (get purchase "date") " " (get purchase "credit_card") " " (get purchase "cvv") " " (get purchase "category"))])  ;grabbing and combining just the first and last names from the person hashmap and combining them with 'str'
       purchases)]]])
           
;        uinput (read-line)
;        purchases(filter(fn [line] (= (get line "category") uinput)) ;Alternative way of writing this function.... purchases(filter #(= input (get % "category")) purchases)
;                   purchases)
;        file-text (pr-str purchases)]
;    (spit "filtered_purchases.edn" file-text)))

(defn read-purchases []
  (let [purchases (slurp "purchases.csv")
        purchases (str/split-lines purchases)
        purchases (map (fn [line] (str/split line #","))  ;Alternative way of writing this line.... purchases (map #(str/split % #",") purchases)
                    purchases)
        header (first purchases)
        purchases (rest purchases)
        purchases (map(fn [line] (zipmap header line))
                    purchases)]
    purchases))
            
(defn filter-by-category [purchases category]
  (filter( fn [purchase]
           (= category (get purchase "category")))
    purchases))

(c/defroutes app
  (c/GET "/:category{.*}" [category]
    (let [purchases (read-purchases)
          purchases (if (= 0 (count category))
                     purchases
                     (filter-by-category purchases category))]
      (h/html (purchases-html purchases)))))
 
(defonce server (atom nil))

(defn -main[]
  (if @server
    (.stop @server))   
  (reset! server  
    (j/run-jetty app {:port 3000 :join? false})))  
              
;
        
