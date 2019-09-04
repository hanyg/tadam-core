(ns tadam.db
  (:require
   [environ.core :refer [env]]
   [migratus.core :as migratus]
   ))

;;;; MIGRATIONS
(defn cast-data-db
  "Cast data from project.clj and sets SQLite path to point to the resources folder"
  [raw-data]
  (if-not (nil? raw-data)
    (let [cast-data (clojure.edn/read-string raw-data)]
      (assoc cast-data :subname (.getFile (clojure.java.io/resource (:subname cast-data)))))))

(def db (cast-data-db (env :db)))

(def config-migrations {:store                :database
                        :migration-dir        "migrations/"
                        :init-script          "init.sql"
                        :init-in-transaction? false
                        :db                   db})
(defn migrate
  "Migrate"
  []
  (migratus/migrate config-migrations))

(defn rollback
  "Rollback"
  []
  (migratus/rollback config-migrations))

;;;; END MIGRATIONS

;;;; SUPERUSERS
(defmacro prn-console
  "Prints text by console without double quotes with new line."
  [nsym] `(println "" ~(name nsym) ""))

(defn createsuperuser
  "Create superuser"
  []
  ;; Get first name
  (prn-console "> First name:")
  (let [first-name (read-line)]
    ;; Get email
    (prn-console "> E-mail: ")
    (let [email (read-line)]
      ;; Get password
      (prn-console "> Password: ")
      (let [password (read-line)]
        ;; Create superuser
        ;; Informs the user
        (prn-console "🎩Superuser created with great success 🥳!")
        )
      )
    )
  )

(defn listsuperusers
  "List all superusers"
  []
  )
;;;; END SUPERUSERS
