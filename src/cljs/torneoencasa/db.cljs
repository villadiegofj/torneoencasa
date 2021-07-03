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

(def default-db-dummy
  {:auth true
   :errors {}
   :nav {:active-page :sign-in}
   :user {:id "xxx"
          :firstname "Eric"
          :lastname "Lensher"
          :roles #{:student}
          :password "xyz"}
   :classboard #{{:id "class1" :name "class with a name" :assignments []}}})
