(ns brosor.test-render
  (:use [net.cgrand.enlive-html])
  (:use [clojure.pprint])
  (:use [brosor.render])
  )


(def test-data {:root "maven/central/acegisecurity/acegi-security-catalina",
                :subgroups ["ala" "bala"  ]
                :versions [
                            {:source true, :version "0.8.0", :packageId "acegisecurity", :artifactId "acegi-security-catalina"}
                            {:source false, :version "0.9.0", :packageId "acegisecurity", :artifactId "acegi-security-catalina"}]}
  )

(println "MAP")
(println "[" (map identity (:subgroups test-data) ) "]" )
(println "MAP")
(println "[" (map identity (:versions test-data) ) "]" )
(println "MAP")
(pprint (map #(do-subgroup-links % "http://usl") test-data )  )

(println "do-subgroup-links")
(pprint (apply str (emit* (do-subgroup-links   test-data "uri"))))

;(println "map:")
;(pprint (map #(link-model %) (:subGroups data)))

(println "do-subgroup-links")
(pprint (map #(do-subgroup-links % "http://service.com/req") (:subgroups test-data)))

(println "PAGE:")
(println (browse-page "http://service.com/req"   test-data))






