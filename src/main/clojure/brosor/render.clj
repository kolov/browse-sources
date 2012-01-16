(ns brosor.render
  (:use [net.cgrand.enlive-html :as h])
  (:use [clojure.pprint])
  )


(def browse-layout (html-resource "brosor/browse-page.html"))



(def *subgroups-head* [:div#subgroups :.sectionTitle ])
(def *artifacts-head* [:div#artifacts :.sectionTitle ])
(def *subgroups-links* [:div#subgroups :> :.links :> first-child])
(def *artifacts-links* [:div#artifacts :> :.links :> first-child])


;(pprint (select browse-layout *subgroups-links*))


(h/defsnippet do-subgroups "brosor/browse-page.html" *subgroups-links*
  [subs svc root]
  [:a ] (h/do->
          (h/content subs)
          (h/set-attr :href (str svc root "/" subs))))

(def LIBSOURCE "http://alljavaclasses.com/libsource/maven/central/")

(h/defsnippet do-artifacts "brosor/browse-page.html" *artifacts-links*
  [v svc]
  [:a ] (h/do->
          (h/content (str (:packageId v) ":" (:artifactId v) ":" (:version v)))
          (h/set-attr :href (str LIBSOURCE
                            (:packageId v) "/" (:artifactId v) "/" (:version v)))
          ))

(defn retnil [x] nil)

(h/deftemplate browse-page "brosor/browse-page.html"
  [svc listResult]
  *subgroups-links* (h/content (map #(do-subgroups % svc (:root listResult)) (:subgroups listResult)))
  *artifacts-links* (h/content (map #(do-artifacts % svc) (:versions listResult)))

  [:div#subgroups ] (if-let [x (:subgroups listResult)] identity retnil)
  [:div#artifacts ] (if-let [x (:versions listResult)] identity retnil)
  [:span#root ] (h/content (:root listResult))
  [:p#raw ] (h/content listResult)
  )

(h/deftemplate view-raw "brosor/browse-page.html"
  [listResult]
  [:p#raw ] (content listResult)
  )




