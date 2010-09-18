(ns withbrian.core
  (:import 
   (java.awt Color Graphics Dimension)
   (java.awt.image BufferedImage)
   (javax.swing JPanel JFrame)))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn make-live [world x y]
  (dosync (ref-set (place world [10 10]) (struct cell true))))

( defn count-live [cells]
  (count (filter :alive cells)))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;dimensions of square world
(def dim 80)
(def scale 10.0)

(defstruct cell :alive) ;may also have :ant and :home

(defn create-world []
  (apply vector 
	 (map (fn [_] 
		(apply vector (map (fn [_] (ref (struct cell false))) 
				   (range dim)))) 
	      (range dim))))

(defn place [world [x y]]
  (-> world (nth x) (nth y)))

(defn fill-cell [#^Graphics g x y c]
  (doto g
    (.setColor c)
    (.fillRect (* x scale) (* y scale) scale scale)))

(defn render-place [g p x y]
  (when (:alive p)
    (fill-cell g x y Color/black)))

(defn render [g]
  (let [v (dosync (apply vector (for [x (range dim) y (range dim)] 
                                   @(place [x y]))))
        img (new BufferedImage (* scale dim) (* scale dim) 
                 (. BufferedImage TYPE_INT_ARGB))
        bg (. img (getGraphics))]
    (doto bg
      (.setColor (. Color white))
      (.fillRect 0 0 (. img (getWidth)) (. img (getHeight))))
    (dorun 
     (for [x (range dim) y (range dim)]
       (render-place bg (v (+ (* x dim) y)) x y)))
    (. g (drawImage img 0 0 nil))
    (. bg (dispose))))

(def panel (doto (proxy [JPanel] []
                        (paint [g] (render g)))
             (.setPreferredSize (new Dimension 
                                     (* scale dim) 
                                     (* scale dim)))))

(def frame (doto (new JFrame) (.add panel)))

(defn show-frame [frame]
  (doto frame
    .pack
    .show))
