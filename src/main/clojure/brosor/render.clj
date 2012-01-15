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
          (set-attr :href subs)))

(defsnippet do-artifacts "brosor/browse-page.html" *artifacts-links*
  [subs]
  [:a ] (do->
          (content subs)
          (set-attr :href subs)))

;(println "link-model:")
;(pprint (apply str (emit* (link-model (:subGroups data)))))

;(println "map:")
;(pprint (map #(link-model %) (:subGroups data)))

(defn retnil [x] nil)

(deftemplate browse-page "brosor/browse-page.html"
  [svc listResult]
  *subgroups-links* (content (map #(do-subgroups % svc (:root listResult)) (:subGroups listResult)))
  *artifacts-links* (content (map #(do-artifacts %) (:artifacts listResult)))

  [:div#subgroups ] (if-let [x (:subGroups listResult)] identity retnil)
  [:div#artifacts ] (if-let [x (:artifacts listResult)] identity retnil)
  )

;(println "PAGE:")
;(println (browse-page1 data))





