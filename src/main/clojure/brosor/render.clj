(ns brosor.render
  (:use [net.cgrand.enlive-html])
  (:use [clojure.pprint])
  )


(def browse-layout (html-resource "brosor/browse-page.html"))


(def data {:subGroups ["abbot" "acegisecurity" "activation"]
           :artifacts ["xx"]
           })


(def *subgroups-head* [:div#subgroups :.sectionTitle ])
(def *artifacts-head* [:div#artifacts :.sectionTitle ])
(def *subgroups-links* [:div#subgroups :> :.links :> first-child])
(def *artifacts-links* [:div#artifacts :> :.links :> first-child])


;(pprint (select browse-layout *subgroups-links*))


(defsnippet do-subgroups "brosor/browse-page.html" *subgroups-links*
  [subs svc root]
  [:a ] (do->
          (content subs)
          (set-attr :href (str svc root "/" subs))))

(def LIBSOURCE "http://alljavaclasses.com/libsource/maven/central/")

(defsnippet do-artifacts "brosor/browse-page.html" *artifacts-links*
  [v svc]
  [:a ] (do->
          (content (str (:packageId v) ":" (:artifactId v) ":" (:version v)))
          (set-attr :href (str LIBSOURCE
                            (:packageId v) "/" (:artifactId v) "/" (:version v)))
          ))

;(println "link-model:")
;(pprint (apply str (emit* (link-model (:subGroups data)))))

;(println "map:")
;(pprint (map #(link-model %) (:subGroups data)))

(defn retnil [x] nil)

(deftemplate browse-page "brosor/browse-page.html"
  [svc listResult]
  *subgroups-links* (content (map #(do-subgroups % svc (:root listResult)) (:subgroups listResult)))
  *artifacts-links* (content (map #(do-artifacts % svc) (:versions listResult)))

  [:div#subgroups ] (if-let [x (:subgroups listResult)] identity retnil)
  [:div#artifacts ] (if-let [x (:versions listResult)] identity retnil)
  [:span#root ] (content (:root listResult))
  [:p#raw ] (content listResult)
  )

;(println "PAGE:")
;(println (browse-page1 data))

(deftemplate view-raw "brosor/browse-page.html"
  [listResult]

  [:p#raw ] (content listResult)
  )




