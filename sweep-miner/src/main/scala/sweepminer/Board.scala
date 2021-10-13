package sweepminer

import scala.util.Random._

class Board {
  private var gameOver = false
  private var _tiles = makeTiles
  private var boardSetup = false
  private var numNonBombs = 0

  def tiles = _tiles

  def leftClick(x:Int, y:Int):Unit = {
    if(!gameOver) {
      if(!boardSetup) {
        setupBoard(x,y)
        boardSetup = true
      }
      _tiles(x)(y).reveal()
      //recursively reveal all neighbors around zero tiles
      if(_tiles(x)(y).num == 0)
        for(neighborRow <- x - 1 to x + 1; neighborCol <- y - 1 to y + 1)
          if (Board.validTile(neighborRow, neighborCol) && !_tiles(neighborRow)(neighborCol).revealed)
            leftClick(neighborRow, neighborCol)
      if(_tiles(x)(y).hasBomb) endGame(x,y) 
      else {
        numNonBombs -= 1
        if(numNonBombs <= 0) {
          gameOver = true
          Main.renderer.setGameWon()
        }
      }
    }
  }

  def rightClick(x:Int, y:Int) = if(!gameOver) _tiles(x)(y).flag()
   
  def setupBoard(firstX:Int, firstY:Int) = {
    numNonBombs = Math.pow(Main.boardSize, 2).toInt - Main.numBombs
    for(bomb <- 1 to Main.numBombs) {
      var randomX = nextInt(Main.boardSize)
      var randomY = nextInt(Main.boardSize)
      while(_tiles(randomX)(randomY).hasBomb || (randomX >= firstX - 1 && randomX <= firstX + 1 &&
            randomY >= firstY - 1 && randomY <= firstY + 1)) {
        randomX = nextInt(Main.boardSize)
        randomY = nextInt(Main.boardSize)
      }
      _tiles(randomX)(randomY).setBomb()
    }
    for(row <- 0 until _tiles.length; col <- 0 until _tiles(0).length) {
      var count = 0
      for(neighborRow <- row - 1 to row + 1; neighborCol <- col - 1 to col + 1)
        if(Board.validTile(neighborRow, neighborCol))
          if(_tiles(neighborRow)(neighborCol).hasBomb) count += 1 
      _tiles(row)(col).setNum(count)
    }
  }

  def endGame(clickedX:Int, clickedY:Int) = {
    gameOver = true
    for(row <- _tiles; tile <- row) {
      if(tile.hasBomb) tile.reveal()
    }
    _tiles(clickedX)(clickedY).revealBomb()
  }

  def reset() = {
    gameOver = false
    boardSetup = false
    for(row <- _tiles; tile <- row) {
      _tiles = makeTiles
    }
    Main.renderer.reset()
  }

  def makeTiles = {
    Array.tabulate(Main.boardSize)( (i:Int) =>
      Array.tabulate(Main.boardSize)( (j:Int) =>
        new Tile(i, j)
      )
    )
  }
}

object Board {
  def validTile(x:Int, y:Int) = x >= 0 && x < Main.boardSize && y >= 0 && y < Main.boardSize
}
