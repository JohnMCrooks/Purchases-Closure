(defproject purchases-closure "0.0.1-SNAPSHOT"
  :description "FIXME: write description"
  :plugins [[io.aviso/pretty "0.1.26"]]
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [io.aviso/pretty "0.1.26"]]
  :javac-options ["-target" "1.6" "-source" "1.6" "-Xlint:-options"]
  :aot [purchases-closure.core]
  :main purchases-closure.core)
