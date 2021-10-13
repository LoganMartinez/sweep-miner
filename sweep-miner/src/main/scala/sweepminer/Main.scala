package sweepminer

import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.scene.canvas.Canvas
import scalafx.scene.Scene
import scalafx.animation.AnimationTimer
import scalafx.scene.input.MouseEvent
import scalafx.scene.input.KeyEvent
import scalafx.stage.PopupWindow
import scalafx.stage.Popup
import scalafx.scene.control.Alert
import scalafx.scene.control.TextInputDialog

object Main extends JFXApp {
  val canvasSize = 800.0
  val boardSize = 20
  val numBombs = 10

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
          renderer.render()
        }
        lastTime = time
      }
			timer.start()	
    }
  }
  canvas.onMouseClicked = (e:MouseEvent) => {
    e.button.toString match {
      case "PRIMARY" => board.leftClick(e.x.toInt / Renderer.tileSize.toInt, e.y.toInt / Renderer.tileSize.toInt)
      case "SECONDARY" => board.rightClick(e.x.toInt / Renderer.tileSize.toInt, e.y.toInt / Renderer.tileSize.toInt)
      case _ => 
    }
  }

  canvas.onKeyPressed = (e:KeyEvent) => {
    e.code.toString match {
      case "R" => board.reset()
      case _ => 
    }
  }
  

  canvas.requestFocus()
}