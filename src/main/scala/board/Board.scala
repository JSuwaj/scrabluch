package board

import wordPointCalculator.{DoubleLetter, DoubleWord, TripleLetter, TripleWord, WordPointCalculator}

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

  def fillBoard(boardArray: Array[Array[Char]])={
    for (i <- 0 until boardSize) {
      for (j <- 0 until boardSize) {
        if(boardArray(i)(j) != ' '){
          setLetter(i,j,boardArray(i)(j))
        }
      }
    }
  }

  def printBoardWithBonusses(): Unit = {
    print(" ")
    for(i<- 0 until boardSize){
      print(" "+i)
    }
    for (i <- 0 until boardSize) {
      print("\n"+i+" ")
      for (j <- 0 until boardSize) {
        if(board(i)(j).getLetter != ' '){
          print(board(i)(j).getLetter + " ")
        }else{
          print(WordPointCalculator.getBonus(i,j) match {
            case DoubleWord => "w "
            case TripleWord => "W "
            case DoubleLetter => "l "
            case TripleLetter => "L "
            case _ => "  "
          })
        }
      }
    }

  }

//  def printPointBonuses(): Unit = {
//    for (i <- 0 until boardSize) {
//      for (j <- 0 until boardSize) {
//        if(board(i)(j).getPointBonus == TripleWord){
//          print("T ")
//        }
//        else if(board(i)(j).getPointBonus == DoubleWord){
//          print("D ")
//        }
//        else if(board(i)(j).getPointBonus == TripleLetter){
//          print("t ")
//        }
//        else if(board(i)(j).getPointBonus == DoubleLetter){
//          print("d ")
//        }
//        else{
//          print("  ")
//        }
//      }
//      println()
//    }
//  }

//  def fillPointBonuses(): Unit = {
//    board(0)(0).setPointBonus(TripleWord)
//    board(0)(7).setPointBonus(TripleWord)
//    board(0)(14).setPointBonus(TripleWord)
//    board(7)(0).setPointBonus(TripleWord)
//    board(7)(14).setPointBonus(TripleWord)
//    board(14)(0).setPointBonus(TripleWord)
//    board(14)(7).setPointBonus(TripleWord)
//    board(14)(14).setPointBonus(TripleWord)
//    board(1)(1).setPointBonus(DoubleWord)
//    board(2)(2).setPointBonus(DoubleWord)
//    board(3)(3).setPointBonus(DoubleWord)
//    board(4)(4).setPointBonus(DoubleWord)
//    board(10)(10).setPointBonus(DoubleWord)
//    board(11)(11).setPointBonus(DoubleWord)
//    board(12)(12).setPointBonus(DoubleWord)
//    board(13)(13).setPointBonus(DoubleWord)
//    board(1)(13).setPointBonus(DoubleWord)
//    board(2)(12).setPointBonus(DoubleWord)
//    board(3)(11).setPointBonus(DoubleWord)
//    board(4)(10).setPointBonus(DoubleWord)
//    board(10)(4).setPointBonus(DoubleWord)
//    board(11)(3).setPointBonus(DoubleWord)
//    board(12)(2).setPointBonus(DoubleWord)
//    board(13)(1).setPointBonus(DoubleWord)
//    board(7)(7).setPointBonus(DoubleWord)
//    board(0)(3).setPointBonus(DoubleLetter)
//    board(0)(11).setPointBonus(DoubleLetter)
//    board(2)(6).setPointBonus(DoubleLetter)
//    board(2)(8).setPointBonus(DoubleLetter)
//    board(3)(0).setPointBonus(DoubleLetter)
//    board(3)(7).setPointBonus(DoubleLetter)
//    board(3)(14).setPointBonus(DoubleLetter)
//    board(6)(2).setPointBonus(DoubleLetter)
//    board(6)(6).setPointBonus(DoubleLetter)
//    board(6)(8).setPointBonus(DoubleLetter)
//    board(6)(12).setPointBonus(DoubleLetter)
//    board(7)(3).setPointBonus(DoubleLetter)
//    board(7)(11).setPointBonus(DoubleLetter)
//    board(8)(2).setPointBonus(DoubleLetter)
//    board(8)(6).setPointBonus(DoubleLetter)
//    board(8)(8).setPointBonus(DoubleLetter)
//    board(8)(12).setPointBonus(DoubleLetter)
//    board(11)(0).setPointBonus(DoubleLetter)
//    board(11)(7).setPointBonus(DoubleLetter)
//    board(11)(14).setPointBonus(DoubleLetter)
//    board(12)(6).setPointBonus(DoubleLetter)
//    board(12)(8).setPointBonus(DoubleLetter)
//    board(14)(3).setPointBonus(DoubleLetter)
//    board(14)(11).setPointBonus(DoubleLetter)
//    board(1)(5).setPointBonus(TripleLetter)
//    board(1)(9).setPointBonus(TripleLetter)
//    board(5)(1).setPointBonus(TripleLetter)
//    board(5)(5).setPointBonus(TripleLetter)
//    board(5)(9).setPointBonus(TripleLetter)
//    board(5)(13).setPointBonus(TripleLetter)
//    board(9)(1).setPointBonus(TripleLetter)
//    board(9)(5).setPointBonus(TripleLetter)
//    board(9)(9).setPointBonus(TripleLetter)
//    board(9)(13).setPointBonus(TripleLetter)
//    board(13)(5).setPointBonus(TripleLetter)
//    board(13)(9).setPointBonus(TripleLetter)
//    board
//  }

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
