(ns pytest.core
  (:require
    [libpython-clj.python :as py]
    [mount.core :as mount :refer [defstate]]))

(System/setProperty
  "jna.library.path"
  "/usr/local/Frameworks/Python.framework/Versions/3.7/lib/")

(mount/in-cljc-mode)

(defstate ^{:on-reload :noop} connection
  :start (py/initialize! :library-path "python3.7m"))

(defstate ^{:on-reload :noop} hy
  :start (py/import-module "hy"))

(defstate ^{:on-reload :noop} indirect
  :start (py/import-module "indirect"))

(defn hy-read [string]
  @connection
  (py/call-attr @hy "read_str" string))

(defn hy-str! [string & {:keys [locals]}]
  (py/call-attr @indirect "eval_indirect" (hy-read string)))

(defmacro hy! [& forms]
  (let [hy-code (pr-str (conj forms 'do))]
    `(hy-str! ~hy-code)))

(defmacro hyq! [do-form]
  (let [hy-code (pr-str (second do-form))]
    `(hy-str! ~hy-code)))
