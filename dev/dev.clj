(ns dev
  (:require [mount.core :refer [defstate]]
            [hy-clojure.core :refer [hy-str! hyq! hy!]]))

(defn add []
  (hy-str! "(+ 1 2 3)"))

(defn add-macro []
  (hy! (+ 1 2 3)))

(defn spacy-setup []
  (hyq!
    '(do
       (import spacy)
       (global nlp)
       (setv nlp (.load spacy "en_core_web_sm")))))

(defn spacy-stuff []
  (hyq!
    '(do
       (setv text "Amar (tekacs) is a Clojure developer who wanted to use Python libraries")
       (setv doc (nlp text))
       (list (map (fn [ent] [ent.text ent.label_]) doc.ents)))))
