(ns hel.db.db
  (:require [clojure.java.jdbc :as jdbc]))

;; there are many ways to write a db-spec but the easiest way is to
;; use :dbtype and then provide the :dbname and any of :user, :password,
;; :host, :port, and other options as needed:
(def pg-db {:dbtype   "postgresql"
            :dbname   "hel"
            :host     "localhost"
            :user     "postgres"
            :password "nxCXwqKhfdw4o2"})

;; if the dbtype is not known to clojure.java.jdbc, or you want to override the
;; default choice of JDBC driver class name, you can provide :classname and the
;; name of the class to use:


;; you can also specify a full connection string if you'd prefer:
(def pg-uri
  {:connection-uri (str "postgresql://myuser:secret@mydb.server.com:5432/mypgdatabase"
                        "?ssl=true&sslfactory=org.postgresql.ssl.NonValidatingFactory")})

;; invoke this function with: lein run -m hel.db.db
(defn -main []
  #_(jdbc/drop-table-ddl :testing)
  (jdbc/db-do-commands "postgresql://localhost:5432/hel?user=postgres&password=nxCXwqKhfdw4o2"
                       (jdbc/create-table-ddl :testing [[:id "BIGINT" "UNIQUE" "NOT NULL"]
                                                        [:applied "TIMESTAMP" "" ""]
                                                        [:description "VARCHAR(1024)" "" ""]]))
  (jdbc/insert-multi! pg-db :testing
                      [{:id 1}
                       {:id 2}])
  ;; ({:generated_key 1} {:generated_key 2})
  )
