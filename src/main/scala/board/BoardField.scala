package board

class BoardField(var empty: Boolean, var adjacentVertically: Boolean, var adjacentHorizontally: Boolean, var letter: Char = ' ',
                 var possibleLettersVertically: List[Char] = List[Char](),var possibleLettersHorizontally: List[Char] = List[Char]()){
  def getLetter: Char = letter
  def setLetter(newLetter: Char): Unit = {
    letter = newLetter
  }
  def isEmpty: Boolean = empty
  def setEmpty(newEmpty: Boolean): Unit = {
    empty = newEmpty
  }
  def isAdjacentVertically: Boolean = adjacentVertically
  def setAdjacentVertically(newAdjacent: Boolean): Unit = {
    adjacentVertically = newAdjacent
  }
  def isAdjacentHorizontally: Boolean = adjacentHorizontally
  def setAdjacentHorizontally(newAdjacent: Boolean): Unit = {
    adjacentHorizontally = newAdjacent
  }
  def getPossibleLettersVertically: List[Char] = possibleLettersVertically
  def setPossibleLettersVertically(newPossibleLetters: List[Char]): Unit = {
    possibleLettersVertically = newPossibleLetters
  }
  def getPossibleLettersHorizontally: List[Char] = possibleLettersHorizontally
  def setPossibleLettersHorizontally(newPossibleLetters: List[Char]): Unit = {
    possibleLettersHorizontally = newPossibleLetters
  }

}


