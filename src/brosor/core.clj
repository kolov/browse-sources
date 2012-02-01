(ns brosor.core
  (:gen-class :extends javax.servlet.http.HttpServlet)
  (:use compojure.core ring.util.servlet ring.middleware.params, ring.middleware.session)
  (:require [compojure.route :as route]
            [compojure.handler :as handler]
            [ring.adapter.jetty :as ring]
            [brosor.render :as r]
            [clj-http.client :as client]
            [org.danlarkin.json :as json]
            ))




(def params {:SVC-LIST "http://service.alljavaclasses.com/list/" ; Service URL
             :SVC-SRC "http://service.alljavaclasses.com/source/" ; Service URL
             :REPO "maven/central" ; initial repo to start browsing from
             })

(defn svc-call [s] (do (println "service=" s) (:body (client/get s))))

(defn list-txt "Calls service and returns text response " [{svc :SVC-LIST} s] (svc-call (str svc s)))

(defn list-json "Calls service and gets json response" [params s] (json/decode (list-txt params s)))

(defn query-list [s] (list-json params s))
(defn query-src [s] (svc-call (str (:SVC-SRC params) s "?format=text")))

(defn echo [x] (do (println x) x))

(defroutes browse-routes
  (GET "/browse/*" [:as req] (r/browse-page (:uri req) (echo (query-list (subs (:uri req) (count "/browse/"))))))
  (GET "/source/*" [:as req] (r/source-page (:uri req) (query-src (subs (:uri req) (count "/source/")))))
  (GET "/req/*" [:as r] {:body (:uri r)})
  (route/not-found  "<h1>Page not found</h1> <br/> Probably you want to <a href=\"/browse/maven/central\"> browse the jars and classes on maven central</a>")
  )

(wrap! browse-routes :session )

(defservice browse-routes)

(def application (handler/site browse-routes))

(defn start [port]
  (ring/run-jetty (var application) {:port port :join? false}))

(defn -main [port]
  (ring/run-jetty application {:port (Integer. port)}))
