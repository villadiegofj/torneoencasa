(ns torneoencasa.components.core
  (:require
    [torneoencasa.i18n :refer [app-tr]]))

(defn link [{:keys [text on-click]}]
  [:a {:on-click on-click} text])

(defn field [{:keys [id label type icon-class help-text values]
              :or {id "" label "" type "text" icon-class "" help-text "" values nil}}]
  [:div.field
   [:label.label (str label ":")
    [:div.control.has-icons-left
     [:input.input {:id id
                    :type type
                    :value (id @values)
                    :on-change #(swap! values assoc id (.. % -target -value))}]
     [:span.icon.is-small.is-left
      [:i.fas {:class icon-class}]]]]
   (when help-text
     [:p.help help-text])])

(defn button [{:keys [label on-click css-classes]
               :or   {label (app-tr :ok) on-click "" css-classes ""}}]
  [:button.button.is-link.is-small
   {:class    css-classes
    :on-click on-click}
   label])

(defn nav-item
  [{:keys [id href name on-click]} active-page]
    [:a.navbar-item {:href href
                     :class (when (= active-page id) "active")
                     :on-click on-click}
     name])

(defn list-errors [errors]
  [:ul.error-messages
   (for [[k v] errors]
     ^{:key k}
     [:li v])])

(defn errors [errors]
  (fn []
    (if-let [errors errors]
      [:div.message.is-danger
       [:div (list-errors @errors)]])))

(defn banner [{:keys [title subtitle url]
               :or {title "" subtitle "" url "#"}}]
  [:section.hero.is-dark.is-bold
   [:a {:href url}
    [:div.hero-body
     [:div.container
      [:h1.title title]
      [:h6.subtitle subtitle]]]]])

(defn options [{:keys [id label icon-class values]}]
  [:div.field
   [:label.label label
    [:div.control.has-icons-left
     [:select.select.is-link.is-rounded {:id id}
      (for [[value text] values]
        ^{:key value}
        [:option {:value value} text])]
     [:div.icon.is-small.is-left
      [:i.fas {:class icon-class}]]]]])

(defn tabs [ts]
  [:div.tabs.is-centered
   [:ul
    (for [{:keys [label icon-class on-click]} ts]
      [:li [:a {:on-click on-click}
             [:span.icon.is-small [:i.fas {:class icon-class}]]
             [:span label]]])]])

#_(defn modal [content button-label]
    [:div.modal
     [:div.modal-background
      [:div.modal-content content ]
      [:button.modal-close.is-large {:aria-label button-label}]]])

#_(defn tiled-category [{:keys [label url color]
                       :or   {label (app-tr :na) url "#" color ""}}]
  [:div.tile.is-child
   [:a {:href url}
    [:article {:class (str "message" " " color)}
     [:div.message-body
      [:p.title.is-4 label]]]]])

#_(defn cards []
  [:div.tile.is-parent
   [:div.tile.is-child.card
    [:div.card-image
     [:figure.image.is-4by3
      [:img
       {:alt "Placeholder image",
        :src "https://via.placeholder.com/192x96"}]]]
    [:div.card-content
     [:div.content "Subir archivos"
      [:time {:datetime "2016-1-1"} "11:09 PM - 1 Jan 2016"]]]]] )

#_(defn tiled [tiles]
    [:div.tile.is-ancestor
      (for [{:keys [title img on-click]} tiles]
        [:div.tile.is-parent
         [:div.tile.is-child.box [:p.title title]
          [:figure.image.is-4by3
            [:img {:src img}]]]])])

#_(defn menu []
  [:aside.menu
   [:p.menu-label "General"]
   [:ul.menu-list
    [:li [:a "Dashboard"]]
    [:li [:a.is-active "Customers"]]]])