(ns brosor.test-render
  (:use [net.cgrand.enlive-html])
  (:use [clojure.pprint])
  (:use [brosor.render])
  )


(def test-data {:root "maven/central/acegisecurity/acegi-security-catalina",
                :subgroups ["ala" "bala"]
                :versions [
                            {:source true, :version "0.8.0", :packageId "acegisecurity", :artifactId "acegi-security-catalina"}
                            {:source false, :version "0.9.0", :packageId "acegisecurity", :artifactId "acegi-security-catalina"}]}
  )

(println "do-subgroups")
(pprint (apply str (emit* (do-subgroups (:subgroups test-data) "svc" "root"))))

;(println "map:")
;(pprint (map #(link-model %) (:subGroups data)))

(pprint (map #(do-subgroups % "scc" "root") (:subgroups test-data)))

(println "PAGE:")
(println (browse-page "svc" test-data))






