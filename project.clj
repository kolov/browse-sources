(defproject browse-sources "1.0.0-SNAPSHOT"
  :description "rbrowse sources"
  :dependencies [[org.clojure/clojure "1.2.0"]
                 [org.clojure/clojure-contrib "1.2.0"]
                 [compojure "0.6.2"]
                 [ring/ring-core "0.3.7"]
                 [ring/ring-jetty-adapter "0.3.7"]
                 [clj-stacktrace "0.2.4"]
                 [clj-http "0.3.0"]
                 [org.danlarkin/clojure-json "1.2-SNAPSHOT"]
                 [enlive "1.0.0" ]]
  :dev-dependencies [[ring/ring-devel "0.3.7"]
                     [lein-ring "0.4.0"]]
  :ring {:handler hello-clojure-web.core/app})
