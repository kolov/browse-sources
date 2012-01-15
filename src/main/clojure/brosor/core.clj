(ns brosor.core
  (:gen-class :extends javax.servlet.http.HttpServlet)
  (:use compojure.core ring.util.servlet ring.middleware.params, ring.middleware.session)
  (:require [compojure.route :as route]
            [compojure.handler :as handler]
            [ring.adapter.jetty :as ring]
            [hiccup.core :as h]
            [hiccup.form-helpers :as fh]
            [brosor.render :as r]
            [clj-http.client :as client]
            [org.danlarkin.json :as json]
            ))



;(def SVC "http://service.alljavaclasses.com/service/list/")
(def SVC "http://localhost:8080/service/list/")
(def REPO "maven/central")

(defn read-dir [s] (client/get (str SVC s)))

(defn list-dir [s] (json/decode (:body (read-dir s))))

(defroutes browse-routes
  (GET "/browse" [] (r/browse-page SVC (list-dir REPO)))
  )

(wrap! browse-routes :session )

(defservice browse-routes)

(def application (handler/site browse-routes))

(defn start [port]
  (ring/run-jetty (var application) {:port port :join? false}))

#_ "Comment the following line if building a WAR"
(start 9900)
