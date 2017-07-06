(ns cljscript.core
  (:require [reagent.core :as r :refer [atom]]
            [re-frame.core :refer [subscribe dispatch dispatch-sync]]
            [cljscript.handlers]
            [cljscript.subs]
            [cljs-exponent.reagent :refer [text text-input view image touchable-highlight] :as rn]))
(def react-native-elements (js/require "react-native-elements"))
(def rnel-button (r/adapt-react-class  (aget react-native-elements "Button")))
(def rnel-card (r/adapt-react-class  (aget react-native-elements "Card")))

(defn alert [title content]
  (.alert rn/alert title content
          #js [ #js{:text "Yes"
                    :onPress #(print "Closed, Yes")}
                #js{:text "no"
                    :onPress #(print "Closed, No")}]))
;Alert.alert("k", "d", [{text: "ssdf"}]


(def app (r/atom {:view [view {:style {:margin-top 32}} [rnel-button {:title "click" }]]}))
(comment
  (swap! app assoc :view test-screen)
  (swap! app assoc :view [view {:style {:marginTop 32}}
                          [rnel-button {:title "test-click me"
                                        :raised true
                                        :kuy true
                                        :button-style {:backgroundColor "blue"}}]])
  )

(def button-screen
  [view {:style {:margin-top 32}}
   [rnel-button {:title "click me" :raised false :button-style {:background-color "green"}}]])


(def test-screen
  [view {:style {:margin-top 32}}
   [text "ggwp"]])


(defn app-root []
  (fn []
    [view {:style {:marginTop 32}}
     [rnel-button {:title "click me if you can"
                   :on-press #(alert "Hello" "this is content")
                   :raised true
                   :button-style {:background-color "pink"}}]
     [text {:style {:color "red"}} "HEllo"]]))

(defn init []
  (dispatch-sync [:initialize-db])
  (.registerComponent rn/app-registry "main" #(r/reactify-component app-root)))
