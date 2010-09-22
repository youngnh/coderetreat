(ns narwal.draw
  (:use [narwal.core])
  (:import 
   (java.awt Color Graphics Dimension)
   (java.awt.image BufferedImage)
   (javax.swing JPanel JFrame)))

;dimensions of square world
(def dim 80)
(def scale 10.0)
(def animation-sleep-ms 100)

(def running true)
(def world (atom (-> (create-world)
		     (add-glider))))

(defn fill-cell [#^Graphics g x y c]
  (doto g
    (.setColor c)
    (.fillRect (* x scale) (* y scale) scale scale)))

(defn render-world [g world]
  (doall (map (fn [[x y] c] (when (:alive c)
			      (fill-cell g x y Color/black)))
	      @world)))

(defn render [g]
  (let [img (new BufferedImage (* scale dim) (* scale dim) 
                 (. BufferedImage TYPE_INT_ARGB))
        bg (. img (getGraphics))]
    (doto bg
      (.setColor (. Color white))
      (.fillRect 0 0 (. img (getWidth)) (. img (getHeight))))
    (doseq [[[x y] c] @world]
      (when (:alive c)
	(fill-cell bg x y Color/black)))
    (. g (drawImage img 0 0 nil))
    (. bg (dispose))))

(def panel (doto (proxy [JPanel] []
                        (paint [g] (render g)))
             (.setPreferredSize (new Dimension 
                                     (* scale dim) 
                                     (* scale dim)))))

(def frame (doto (new JFrame) (.add panel)))

(def animator (agent nil))
(defn animation [x]
  (when running
    (send-off *agent* #'animation))
  (. panel (repaint))
  (swap! world step-world)
  (. Thread (sleep animation-sleep-ms))
  nil)

(defn show-frame [frame]
  (doto frame
    .pack
    .show))

(comment
  (show-frame frame)

  ;; start the animation
  (send-off animator animation)

  ;; stop the animation, (send-off animator animation) will step animation
  (alter-var-root (var running) (constantly false))

  ;; restart the animation, (send-off animator animation) will run animation
  (alter-var-root (var running) (constantly true))

  ;; reset the world
  (reset! world (create-world))

  ;; glider
  (reset! world (-> (create-world)
		    (add-glider)))

)
