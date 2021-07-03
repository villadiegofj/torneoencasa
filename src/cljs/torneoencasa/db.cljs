(ns torneoencasa.db)

(def default-db
  {:auth false
   :errors {}
   :nav {:active-page :sign-in}
   :user {:id ""
          :firstname ""
          :lastname ""
          :roles #{:none}
          :password ""}
   :classboard #{}})
