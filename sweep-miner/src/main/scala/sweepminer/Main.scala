package sweepminer

import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.scene.canvas.Canvas
import scalafx.scene.Scene
import scalafx.animation.AnimationTimer

object Main extends JFXApp {
  val canvasSize = 800.0
  val boardSize = 20

  val canvas = new Canvas(canvasSize, canvasSize)
  val gc = canvas.graphicsContext2D
  val board = new Board
  val renderer = new Renderer(gc, board)
  
  stage = new JFXApp.PrimaryStage {
    title = "Sweep Miner"
    scene = new Scene(canvasSize, canvasSize) {
      content += canvas
      var lastTime = -1L
      val timer = AnimationTimer { time =>
        if (lastTime >= 0) {
          val delay = (time - lastTime) / 1e9
          renderer.render
        }
        lastTime = time
      }
			timer.start()	
    }
  }
}