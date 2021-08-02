(ns torneoencasa.components.core
  (:require
    [torneoencasa.i18n :refer [app-tr]]))

(defn link [{:keys [text on-click]}]
  [:a {:on-click on-click} text])

(defn field [{:keys [id label type icon-class values]}]
  [:div.field
   [:label.label (str label ":")
    [:div.control.has-icons-left
     [:input.input {:id id
                    :type type
                    :value (id @values)
                    :on-change #(swap! values assoc id (.. % -target -value))}]
     [:span.icon.is-small.is-left
      [:i.fas {:class icon-class}]]]]])

(defn button [{:keys [label on-click css-classes]
               :or {label (app-tr :ok) on-click "" css-classes ""}}]
  [:div.control
   [:button.button.is-link.is-small
    {:class    css-classes
     :on-click on-click}
    label]])

(defn nav-item
  [{:keys [id href name dispatch]} active-page]
    [:a.navbar-item {:href href
                     :class (when (= active-page id) "active")
                     :on-click dispatch}
     name])

(defn list-errors [errors]
  [:ul.error-messages
   (for [[k v] errors]
     ^{:key k}
     [:li v])])

(defn modal [content button-label]
  [:div.modal
   [:div.modal-background
   [:div.modal-content content ]
   [:button.modal-close.is-large {:aria-label button-label}]]])

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

(defn tiled-category [{:keys [label url color]
                       :or   {label (app-tr :na) url "#" color ""}}]
  [:div.tile.is-child
   [:a {:href url}
    [:article {:class (str "message" " " color)}
     [:div.message-body
      [:p.title.is-4 label]]]]])