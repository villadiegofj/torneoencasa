(ns torneoencasa.components.core)

(defn input-field [label icon attrs]
  [:div.field
   [:label.label (str label ":")
    [:div.control.has-icons-left
     [:input.input attrs]
     [:span.icon.is-small.is-left
      [:i.fas {:class icon}]]]]])

(defn button [label attrs]
   [:div.control
    [:button.button.is-link attrs label]])

(defn nav-item
  [{:keys [id href name dispatch]} active-page]
    [:a.navbar-item {:href href
                     :class (when (= active-page id) "active")
                     :on-click dispatch}
     name])
