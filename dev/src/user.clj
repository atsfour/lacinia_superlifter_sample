(ns user
  "REPL起動時の名前空間")


(defn dev
  "`dev` 名前空間をロードし、 移動する。"
  []
  (require 'dev)
  (in-ns 'dev)
  :loaded)
