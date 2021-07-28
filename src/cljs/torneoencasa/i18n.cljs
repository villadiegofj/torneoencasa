(ns torneoencasa.i18n
  (:require
    [re-frame.core :as rf]
    [taoensso.tempura :refer [tr]]))

(def dictionary
  ; English language resources
  {:en {:app-subtitle "Todos somos una pieza, y en este rompecabezas todas las piezas son importantes"
        :app-title   "Torneo en Casa"
        :download    "Download"
        :email       "E-mail"
        :errors {:error-found "error found"
                 :e401 "not authenticated"
                 :e403 "not authorized"
                 :e404 "not found"}
        :firstname   "First Name"
        :home        "Home"
        :language    "Language"
        :lastname    "Last Name"
        :made-by     "Made by Oraqus"
        :messages    {:no-account? "No account?"
                      :sign-up! "Sign up!"}
        :na          "NA"
        :name        "Name"
        :ok          "Ok"
        :optional-code "Code (optional)"
        :password    "Password"
        :password-confirm "Confirm Password"
        :signin      "Sign in"
        :signup      "Sign up"
        :username    "Username"
        :welcome     "Welcome"}

   ; Spanish language resources
   :es {:app-subtitle "Todos somos una pieza, y en este rompecabezas todas las piezas son importantes"
        :app-title    "Torneo en Casa"
        :download    "Descargar"
        :email        "Correo-e"
        :errors {:error-found "hubo un error"
                 :e401 "no autenticado"
                 :e403 "no autorizado"
                 :e404 "no encontrado"}
        :firstname   "Nombre"
        :home        "Inicio"
        :language    "Language"
        :lastname    "Apellido"
        :made-by     "Hecho por Oraqus"
        :messages    {:no-account? "¿No tienes cuenta?"
                      :sign-up! "Inscríbete!"}
        :na          "ND"
        :name        "Nombre"
        :ok          "Ok"
        :optional-code "Pase (opcional)"
        :password    "Contraseña"
        :password-confirm "Confirmar contraseña"
        :signin      "Ingresar"
        :signup      "Registrarse"
        :username    "Usuario"
        :welcome     "Bienvenido/a"}})

(defn app-tr [resource & params]
  ;;(let [lang (rf/subscribe [::es-common/lang])]
  (let [lang :es]
    (tr {:dict dictionary} [lang :es] [resource] (vec params))))

