(ns user
  (:require [mount.core :as mount :refer [defstate]]
            [pytest.core :as core :refer [hy-str! hyq! hy!]]
            [libpython-clj.python :as py]))

(set! *warn-on-reflection* true)

(def text
  "Amar (tekacs) is a Clojure developer who wanted to use Python libraries")

(defn text-proc []
  @core/connection
  (let [document (@core/nlp text)]
    (map
      (juxt #(py/get-attr % "text") #(py/get-attr % "label_"))
      (py/get-attr document "ents"))))

(defn add []
  @core/connection
  (hy-str! "(+ 1 2 3)"))

(defn add-macro []
  @core/connection
  (hy! (+ 1 2 3)))

(defn spacy-setup []
  @core/connection
  (hyq!
    '(do
       (import spacy)
       (global nlp)
       (setv nlp (.load spacy "en_core_web_sm")))))

(defn spacy-stuff []
  @core/connection
  (hyq!
    '(do
       (setv text "Amar (tekacs) is a Clojure developer who wanted to use Python libraries")
       (setv doc (nlp text))
       (list (map (fn [ent] [ent.text ent.label_]) doc.ents)))))
