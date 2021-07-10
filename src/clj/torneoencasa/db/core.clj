(ns torneoencasa.db.core
  (:require [clojure.spec.alpha :as s]
            [clojure.java.io :as io]
            [clojure.edn :as edn]
            [next.jdbc :as jdbc]
            [next.jdbc.sql :as sql])
  (:gen-class))

(s/def ::roles #{:admin :regular :anonymous})
(s/def ::id string?)
(s/def ::password string?)
(s/def ::auth-request (s/keys :req-un [::id ::password]))

(defn read-users []
  (->> "dev/resources/users.edn"
       io/resource
       slurp
       edn/read-string))

(defn retrieve [id password]
  (let [users (read-users)
        filters [#(-> % :user :id (= id))
                 #(-> % :user :password (= password))]]
    (into {} (filter (apply every-pred filters) users))))

(def ^:private roles
  "List of roles."
  ["admin" "participant" "invited"])

(def ^:private initial-user-data
  "Seed the database with this data."
  [{:first_name "Bruce" :last_name "Banner"
    :email "bruce@wayne.com" :role_id 1}
   {:first_name "Jack" :last_name "Napier"
    :email "joker@gotham.com" :role_id 2}])

(defn populate
  [db db-type]
  (println db db-type))

(defn populate
  "Called at application startup. Attempts to create the
  database table and populate it. Takes no action if the
  database table already exists."
  [db db-type]
  (let [auto-key (if (= "h2" db-type)
                   "primary key autoincrement"
                   (str "generated always as identity"
                        " (start with 1, increment by 1)"
                        " primary key"))]
    (try
      (jdbc/execute-one! db [(str "create table roles (
                                     id            integer " auto-key ",
                                     name          varchar(32))")])
      (jdbc/execute-one! db [(str "create table users (
                                     id            integer " auto-key ",
                                     first_name    varchar(32),
                                     last_name     varchar(32),
                                     email         varchar(64),
                                     password      varchar(64),
                                     role_id       integer not null)")])
      (println "Created database and users table!")
      ;; if table creation was successful, it didn't exist before
      ;; so populate it...
      (try
        (doseq [r roles]
          (sql/insert! db :roles {:name r}))
        (doseq [row initial-user-data]
          (sql/insert! db :users row))
        (println "Populated database with initial data!")
        (catch Exception e
          (println "Exception:" (ex-message e))
          (println "Unable to populate the initial data -- proceed with caution!")))
      (catch Exception e
        (println "Exception:" (ex-message e))
        (println "Looks like the database is already setup")))))

(defn get-users
  "Return all available users, sorted by name.

  Since this is a join, the keys in the hash maps returned will
  be namespace-qualified by the table from which they are drawn:

  users/id, users/first_name, etc, role/name"
  [db]
  (sql/query db ["select a.*, r.name from users a
                  join roles r on a.role_id = r.id
                  order by a.last_name, a.first_name"]))

(defn get-user-by-id
  "Given a user id, return the user record."
  [db {:keys [id password]}]
  (sql/get-by-id db :id id))

(defn save-user
  "Save a user record. If ID is present and not zero, then this is an update operation, otherwise it's an insert."
  [db user]
  (sql/insert! db :users users))