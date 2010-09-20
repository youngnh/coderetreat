(ns narwal.draw
  (:use [narwal.core])
  (:import 
   (java.awt Color Graphics Dimension)
   (java.awt.image BufferedImage)
   (javax.swing JPanel JFrame)))

;dimensions of square world
(def dim 80)
(def scale 10.0)

(def world (atom (create-world)))

(defn fill-cell [#^Graphics g x y c]
  (doto g
    (.setColor c)
    (.fillRect (* x scale) (* y scale) scale scale)))

(defn render-world [g world]
  (map (fn [[x y] _] (fill-cell g x y Color/black))
       (filter (fn [_ c] (survive? c)) @world)))

(defn render [g]
  (let [img (new BufferedImage (* scale dim) (* scale dim) 
                 (. BufferedImage TYPE_INT_ARGB))
        bg (. img (getGraphics))]
    (doto bg
      (.setColor (. Color white))
      (.fillRect 0 0 (. img (getWidth)) (. img (getHeight))))
    (dorun 
     (render-world bg world))
    (. g (drawImage img 0 0 nil))
    (. bg (dispose))))

(def panel (doto (proxy [JPanel] []
                        (paint [g] (render g)))
             (.setPreferredSize (new Dimension 
                                     (* scale dim) 
                                     (* scale dim)))))

(def frame (doto (new JFrame) (.add panel)))

(defn animation [x]
  (when running
    (send-off *agent* #'animation))
  (. panel (repaint))
  (. Thread (sleep animation-sleep-ms))
  nil)

(defn )

(defn show-frame [frame]
  (doto frame
    .pack
    .show))

(comment
 (show-frame frame)
 (send-off animator animation))