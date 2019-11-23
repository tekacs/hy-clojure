# hy-clojure
Wrap [Python](https://python.org) from [Clojure](https://clojure.org) via [Hy](http://hylang.org) using the excellent [libpython-clj](https://github.com/cnuernber/libpython-clj) ([JNA](https://github.com/java-native-access/jna))

For those who would like Python interop but would prefer to write using (some) editor support or without giving up on Lisp syntax. :)

With this pattern, from Clojure you can write Hy code inside a macro and have it run in a Python VM:

```clojure
(require '[hy-clojure.core :refer [hy!]])

(hy! (do (import pickle) (pickle.dumps [1 2 3])))
;; => b'\x80\x03]q\x00(K\x01K\x02K\x03e.'
```

In order to stop your IDE from raising issues, you can quote your Hy code. You can also use Python libraries trivially.

```clojure
(require '[hy-clojure.core :refer [hyq!]])

(hyq!
 '(do (import spacy)
      (setv nlp (.load spacy "en_core_web_sm"))
      (setv text "Hello, world!")
      (nlp text)))
```

### TODO

- [easy] Allow the caller to pass locals the Hy interpreter
- [easy] Add tests
- [medium] Allow inline substitution of variables into Hy forms in the macro:  
  `(let [clj# "clojure!"] (hy! (print clj#)))`
- [???] Support sending reader macros to hy via the macro
