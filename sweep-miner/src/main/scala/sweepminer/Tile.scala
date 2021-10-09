package sweepminer

class Tile(val x:Double, val y:Double) {
  private var _texture = TileTexture.Default
  def texture = _texture
}