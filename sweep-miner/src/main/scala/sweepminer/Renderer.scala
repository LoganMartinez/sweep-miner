package sweepminer

import scalafx.Includes._
import scalafx.scene.canvas.Canvas
import scalafx.scene.canvas.GraphicsContext
import scalafx.scene.image.Image
import scalafx.scene.text.Text
import scalafx.scene.text.Font

class Renderer(gc:GraphicsContext, board:Board) {
  private var gameWon = false

  private val defaultTileImage = Renderer.loadImage("/images/tile.png")
  private val flagImage = Renderer.loadImage("/images/flag.png")
  private val bombImage = Renderer.loadImage("/images/bomb.png")
  private val clickedBombImage = Renderer.loadImage("/images/clickedBomb.png")
  private def findTileImage(num:Int) = {
    require(num >= 0 && num <= 8)
    Renderer.loadImage("/images/tile" + num + ".png")
  }


  def render() = {
    for(col <- board.tiles) {
      for(tile <- col) {
        val img = tile.texture match {
          case TileTexture.Default => defaultTileImage
          case TileTexture.Flag => flagImage
          case TileTexture.Bomb => bombImage
          case TileTexture.ClickedBomb => clickedBombImage
          case TileTexture.Zero => findTileImage(0)
          case TileTexture.One => findTileImage(1)
          case TileTexture.Two => findTileImage(2)
          case TileTexture.Three => findTileImage(3)
          case TileTexture.Four => findTileImage(4)
          case TileTexture.Five => findTileImage(5)
          case TileTexture.Six => findTileImage(6)
          case TileTexture.Seven => findTileImage(7)
          case TileTexture.Eight => findTileImage(8)
        }
        gc.drawImage(img, tile.x * Renderer.tileSize, tile.y * Renderer.tileSize, Renderer.tileSize, Renderer.tileSize)
      }
    }
    if(gameWon) {
      gc.setFont(new Font("Verdana", 50))
      gc.fillText("You Won", Main.canvasSize / 2 - 110, Main.canvasSize / 2)
    }
  }

  def setGameWon() = gameWon = true
  def reset() = gameWon = false
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
