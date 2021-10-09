package sweepminer

import scalafx.Includes._
import scalafx.scene.canvas.Canvas
import scalafx.scene.canvas.GraphicsContext
import scalafx.scene.image.Image

class Renderer(gc:GraphicsContext, board:Board) {
  private val defaultTileImage = Renderer.loadImage("/images/tile.png")
  private val flagImage = Renderer.loadImage("/images/flag.png")


  def render = {
    for(col <- board.tiles) {
      for(tile <- col) {
        val img = tile.texture match {
          case TileTexture.Default => defaultTileImage
          case TileTexture.Flag => flagImage
        }
        gc.drawImage(img, tile.x * Renderer.tileSize, tile.y * Renderer.tileSize, Renderer.tileSize, Renderer.tileSize)
      }
    }
  }
}

object Renderer {
  val tileSize = Main.canvasSize / Main.boardSize

  def loadImage(path: String): Image = {
    val res = getClass.getResource(path)
    if(res == null) {
      new Image("file:src/main/resources"+path)
    } else {
      new Image(res.toExternalForm())
    }
  } 
}
