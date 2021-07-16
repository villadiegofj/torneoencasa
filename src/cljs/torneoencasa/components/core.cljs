(ns torneoencasa.components.core)

(defn input-field [label icon attrs]
  [:div.field
   [:label.label (str label ":")
    [:div.control.has-icons-left
     [:input.input.is-small attrs]
     [:span.icon.is-small.is-left
      [:i.fas {:class icon}]]]]])

(defn button [label attrs]
    [:button.button.is-link.is-small.is-fullwidth attrs label])

(defn nav-item
  [{:keys [id href name dispatch]} active-page]
    [:a.navbar-item {:href href
                     :class (when (= active-page id) "active")
                     :on-click dispatch}
     name])
