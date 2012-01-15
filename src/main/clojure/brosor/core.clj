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
(def SITE "/browse/")
(def REPO "maven/central")

(defn read-dir [s] (client/get (str SVC s)))

(defn list-dir [s] (json/decode (:body (read-dir s))))

(defroutes browse-routes
  (GET "/browse/:u1/:u2" [u1 u2] (r/browse-page SITE (list-dir (str u1 "/" u2))))
  (GET "/browse/:u1/:u2/:u3" [u1 u2 u3] (r/browse-page SITE (list-dir (str u1 "/" u2 "/" u3))))
  (GET "/browse/:u1/:u2/:u3/:u4" [u1 u2 u3 u4] (r/browse-page SITE (list-dir (str u1 "/" u2 "/" u3 "/" u4))))
  (GET "/raw" [] (r/view-raw (list-dir REPO)))
  ;  (route/not-found {:status 404 :body "<h1>Page not found</h1>"})
  )

(wrap! browse-routes :session )

(defservice browse-routes)

(def application (handler/site browse-routes))

(defn start [port]
  (ring/run-jetty (var application) {:port port :join? false}))

#_ "Comment the following line if building a WAR"
;(start 9900)
