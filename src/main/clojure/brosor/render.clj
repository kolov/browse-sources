(ns brosor.render
  (:use [net.cgrand.enlive-html :as h])
  (:use [clojure.pprint])
  (:require [clojure.contrib.string :as cs])
  )


(def browse-layout (html-resource "brosor/browse-page.html"))



(def *subgroups-head* [:div#subgroups :.sectionTitle ])
(def *artifacts-head* [:div#artifacts :.sectionTitle ])
(def *subgroups-links* [:div#subgroups :> :.links :> first-child])
(def *artifacts-links* [:div#artifacts :> :.links :> first-child])
(def *classes-links* [:div#classes :> :.links :> first-child])


;(pprint (select browse-layout *subgroups-links*))

(def TEMPLATE "brosor/browse-page.html")

(h/defsnippet do-subgroup-links TEMPLATE *subgroups-links*
  [sub uri]
  [:a ] (h/do->
          (h/content (str sub))
          (h/set-attr :href (str uri "/" sub))))


(h/defsnippet do-artifact-links TEMPLATE *artifacts-links*
  [v uri]
  [:a ] (h/do->

          (h/content (str (:packageId v) ":" (:artifactId v) ":" (:version v) " " (if (:source v) "source" "no source")))
          (h/set-attr :href (str uri "/" (:version v) "/!"
                              ))
          ))

(h/defsnippet do-class-links TEMPLATE *classes-links*
  [classname library uri]
  [:a ] (h/do->
          (h/content (str classname " " (if (:source library) "" "no source")))
          (h/set-attr :href (str (cs/replace-str "/browse/" "/source/" uri) "/" classname))
          (h/set-attr :onclick (if (:source library) "return true;" "return false;"))
          ))

(defn retnil [x] nil)

(h/deftemplate browse-page TEMPLATE
  [uri
   {subgroups :subgroups classnames :classnames versions :versions library :library}
   ]
  *subgroups-links* (h/content (map #(do-subgroup-links % uri) subgroups))
  *artifacts-links* (h/content (map #(do-artifact-links % uri) versions))
  *classes-links* (h/content (map #(do-class-links % library uri) classnames))

  [:div#subgroups ] (if-let [x subgroups] identity retnil)
  [:div#artifacts ] (if-let [x versions] identity retnil)
  [:div#classes ] (if-let [x classnames] identity retnil)
  [:div#root ] (h/content (str uri))

  )

(h/deftemplate source-page "brosor/source-page.html"
  [uri response]
  [:div#source ] (h/content {:tag :pre :content response}
                   )       )




