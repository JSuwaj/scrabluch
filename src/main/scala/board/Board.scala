package board

class Board(boardsize: Int = 15) {
  final val boardSize = boardsize
  private var board = Array.ofDim[BoardField](boardSize, boardSize)
  initializeBoard()

  def getBoard: Array[Array[BoardField]] = {
    board
  }

  def getBoardSize: Int = {
    boardSize
  }

  def getLetter(row: Int, col: Int): Char = {
    board(row)(col).getLetter
  }

  def setLetter(row: Int, col: Int, letter: Char): Unit = {
    board(row)(col).setLetter(letter)
    if(letter == ' '){
      board(row)(col).setEmpty(true)
    }
    else{
      board(row)(col).setEmpty(false)
    }
  }

  def setPossibleLettersHorizontally(row: Int, col: Int, possibleLetters: List[Char]): Unit = {
    board(row)(col).setPossibleLettersHorizontally(possibleLetters)
    board(row)(col).setAdjacentHorizontally(true)
  }

  def setPossibleLettersVertically(row: Int, col: Int, possibleLetters: List[Char]): Unit = {
    board(row)(col).setPossibleLettersVertically(possibleLetters)
    board(row)(col).setAdjacentVertically(true)
  }

  def getPossibleLettersHorizontally(row: Int, col: Int): List[Char] = {
    board(row)(col).getPossibleLettersHorizontally
  }

  def getPossibleLettersVertically(row: Int, col: Int): List[Char] = {
    board(row)(col).getPossibleLettersVertically
  }

  def isAdjacentHorizontally(row: Int, col: Int): Boolean = {
    board(row)(col).isAdjacentHorizontally
  }

  def isAdjacentVertically(row: Int, col: Int): Boolean = {
    board(row)(col).isAdjacentVertically
  }

  def isEmpty(row: Int, col: Int): Boolean = {
    board(row)(col).isEmpty
  }

  def initializeBoard(): Unit = {
    for (i <- 0 until boardSize) {
      for (j <- 0 until boardSize) {
        board(i)(j) = new BoardField(true, false, false)
      }
    }
  }

  def printBoard(): Unit = {
    print(" ")
    for(i<- 0 until boardSize){
      print(" "+i)
    }
    for (i <- 0 until boardSize) {
      print("\n"+i+" ")
      for (j <- 0 until boardSize) {
        print(board(i)(j).getLetter + " ")
      }
    }
  }

//  def printPossibleLetters(): Unit = {
//    for (i <- 0 until boardSize) {
//      for (j <- 0 until boardSize) {
//        print(i+" "+j+" "+board(i)(j).getPossibleLetters + " ")
//      }
//      println()
//    }
//  }





}

object Board {
  def apply(boardSize: Int): Board = new Board(boardSize)
}
