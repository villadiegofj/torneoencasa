(ns torneoencasa.i18n
  (:require
    ;;[re-frame.core :as rf]
    [taoensso.tempura :refer [tr]]))

(def dictionary
  ; Spanish language resources
  {:es {:app-subtitle     "Todos somos una pieza, y en este rompecabezas todas las piezas son importantes"
        :app-title        "Torneo en Casa"
        :download         "Descargar"
        :email            "Correo-e"
        :errors           {:error-found "hubo un error"
                           :e401        "no autenticado"
                           :e403        "no autorizado"
                           :e404        "no encontrado"
                           :e900        "Hubo un problema al tratar de descargar el archivo"}
        :firstname        "Nombre"
        :home             "Inicio"
        :information      "Información"
        :language         "Language"
        :lastname         "Apellido"
        :made-by          "Hecho por Oraqus"
        :messages         {:no-account?        "¿No tienes cuenta?"
                           :sign-up!           "Inscríbete!"
                           :download-file-here "Puedes bajar el archivo aquí"}
        :na               "ND"
        :name             "Nombre"
        :ok               "Ok"
        :optional         "Opcional"
        :pass-code        "Pase"
        :password         "Contraseña"
        :password-confirm "Confirmar contraseña"
        :signin           "Ingresar"
        :signup           "Registrarse"
        :username         "Usuario"
        :welcome          "Bienvenido/a"}

   ; English language resources
   :en {:app-subtitle     "Todos somos una pieza, y en este rompecabezas todas las piezas son importantes"
        :app-title        "Torneo en Casa"
        :download         "Download"
        :email            "E-mail"
        :errors           {:error-found "error found"
                           :e401        "not authenticated"
                           :e403        "not authorized"
                           :e404        "not found"
                           :e900        "Something failed while trying to download the file"}
        :firstname        "First Name"
        :home             "Home"
        :information      "Information"
        :language         "Language"
        :lastname         "Last Name"
        :made-by          "Made by Oraqus"
        :messages         {:no-account?        "No account?"
                           :sign-up!           "Sign up!"
                           :download-file-here "You can download the file here"}
        :na               "NA"
        :name             "Name"
        :ok               "Ok"
        :optional         "Optional"
        :pass-code        "Pass"
        :password         "Password"
        :password-confirm "Confirm Password"
        :signin           "Sign in"
        :signup           "Sign up"
        :username         "Username"
        :welcome          "Welcome"}})

(defn app-tr [resource & params]
  ;;(let [lang (rf/subscribe [::es-common/lang])]
  (let [lang :es]
    (tr {:dict dictionary} [lang :es] [resource] (vec params))))

