package sweepminer

class Tile(val x:Double, val y:Double) {
  private var _texture = TileTexture.Default
  private var _revealed = false
  private var flagged = false
  private var _hasBomb = false
  private var _num = 0

  def texture = _texture
  def hasBomb = _hasBomb
  def num = _num
  def revealed = _revealed

  def reveal() = if(!revealed && !flagged) {
    _revealed = true
    if(hasBomb) {
      _texture = TileTexture.Bomb
    } else _texture = num match {
      case 0 => TileTexture.Zero
      case 1 => TileTexture.One
      case 2 => TileTexture.Two
      case 3 => TileTexture.Three
      case 4 => TileTexture.Four
      case 5 => TileTexture.Five
      case 6 => TileTexture.Six
      case 7 => TileTexture.Seven
      case 8 => TileTexture.Eight
    }   
  }
  def flag() = if(!revealed) {
    if(!flagged) {
      _texture = TileTexture.Flag
      flagged = true
    }
    else {
      _texture = TileTexture.Default
      flagged = false
    }
  }

  def revealBomb() = _texture = TileTexture.ClickedBomb

  def setBomb() = _hasBomb = true

  def setNum(x:Int) = _num = x
}