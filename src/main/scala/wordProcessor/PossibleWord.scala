package wordProcessor

case class PossibleWord(var word: String, var isVertical: Boolean, var x : Int, var y : Int, var points: Int = 0){

  //overrite equals method
  override def equals(that: Any): Boolean = that match {
    case that: PossibleWord => that.word == this.word && that.isVertical == this.isVertical && that.x == this.x && that.y == this.y
    case _ => false
  }

  //overrite toString method
  override def toString: String = {
    word+x+"."+y+"_"+points
  }
}
