(ns torneoencasa.db.core
  (:require
    [buddy.hashers :as buddy-hashers]
    [clojure.spec.alpha :as s]
    [java-time :as t]
    [next.jdbc :as jdbc]
    [next.jdbc.result-set :as rs]
    [next.jdbc.sql :as sql])
  (:gen-class))

(s/def ::roles #{:admin :regular :anonymous})
(s/def ::id string?)
(s/def ::password string?)
(s/def ::auth-request (s/keys :req-un [::id ::password]))

(def ds-opts {:return-keys true
              :builder-fn rs/as-unqualified-kebab-maps})

(def ^:private roles
  "List of roles."
  [{:id 1 :name "admin"       :created (t/instant)}
   {:id 2 :name "participant" :created (t/instant)}
   {:id 3 :name "invited"     :created (t/instant)}])

(def ^:private initial-user-data
  "Seed the database with this data."
  [{:id "ec3e9528-a0b8-4d6c-865a-44c9d376c001"
    :username "batman"
    :firstname "Bruce"
    :lastname "Banner"
    :email "bruce@wayne.com"
    :password (buddy-hashers/encrypt "batman")
    :role_id 1
    :created (t/instant)}
   {:id "ec3e9528-a0b8-4d6c-865a-44c9d376c002"
    :username "joker"
    :firstname "Jack"
    :lastname "Napier"
    :email "joker@gotham.com"
    :password (buddy-hashers/encrypt "joker")
    :role_id 2
    :created (t/instant)}
   {:id "ec3e9528-a0b8-4d6c-865a-44c9d376c003"
    :username "penguin"
    :firstname "Oscar"
    :lastname "Cobblepot"
    :email "penguin@gotham.com"
    :password (buddy-hashers/encrypt "penguin")
    :role_id 3
    :created (t/instant)}])

(def ^:private user-roles
  "List of roles by user."
  [{:user_id 1 :role_id 1 :created (t/instant)}
   {:user_id 2 :role_id 2 :created (t/instant)}
   {:user_id 3 :role_id 2 :created (t/instant)}
   {:user_id 3 :role_id 3 :created (t/instant)}])

(defn populate
  "Called at application startup. Attempts to create the
  database table and populate it. Takes no action if the
  database table already exists."
  [db]
  (let [auto-key "auto_increment primary key"]
    (try
      (jdbc/execute-one! db [(str "create table roles (
                                     id            integer " auto-key ",
                                     name          varchar(32),
                                     created    timestamp)")])
      (jdbc/execute-one! db [(str "create table users (
                                     id           UUID,
                                     username     varchar(32),
                                     firstname    varchar(32),
                                     lastname     varchar(32),
                                     email        varchar(64),
                                     password     varchar(128),
                                     role_id      integer,
                                     created      timestamp,
                                     constraint fk_roles
                                       foreign key (role_id) references roles(id))")])
      (println "Created users, roles and user_roles table!")
      ;; if table creation was successful, it didn't exist before
      ;; so populate it...
      (try
        (doseq [r roles]
          (sql/insert! db :roles r))
        (println "inserted roles!")
        (doseq [row initial-user-data]
          (sql/insert! db :users row))
        (println "inserted users!")
        (println "Populated database with initial data!")
        (catch Exception e
          (println "Exception:" (ex-message e))
          (println "Unable to populate the initial data -- proceed with caution!")))
      (catch Exception e
        (println "Exception:" (ex-message e))
        (println "Looks like the database is already setup")))))

(defn get-users
  "Return all available users, sorted by name."
  [db]
  (sql/query db ["select u.id,
                         u.username,
                         u.firstname,
                         u.lastname,
                         u.email,
                         u.password,
                         r.name role,
                  from users u
                  left outer join roles r
                    on u.role_id = r.id"]))

(defn get-user-by-id
  "Given a user id, return the user record."
  [db id]
  (sql/get-by-id db :users id))

(defn get-user-by-username
  "Given a username, return the user record."
  [db username]
  (sql/query db ["select u.id,
                         u.username,
                         u.firstname,
                         u.lastname,
                         u.email,
                         u.password,
                         r.name role,
                  from users u
                  left outer join roles r
                    on u.role_id = r.id
                  where u.username = ?" username]))

(defn save-user
  "Save a user record."
  [db user]
  (sql/insert! db :users user))
