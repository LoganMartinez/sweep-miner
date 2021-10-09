package sweepminer

class Board {
  private var _tiles = Array.tabulate(Main.boardSize)( (i:Int) =>
    Array.tabulate(Main.boardSize)( (j:Int) =>
      new Tile(i, j)
    )
  )

  def tiles = _tiles
}
