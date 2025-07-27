package wordPointCalculator

object WordPointCalculator {

  private val letterPointMap = Map(
    'a' -> 1,
    'ą' -> 5,
    'b' -> 3,
    'c' -> 2,
    'ć' -> 6,
    'd' -> 2,
    'e' -> 1,
    'ę' -> 5,
    'f' -> 5,
    'g' -> 3,
    'h' -> 3,
    'i' -> 1,
    'j' -> 3,
    'k' -> 2,
    'l' -> 2,
    'ł' -> 3,
    'm' -> 2,
    'n' -> 1,
    'ń' -> 7,
    'o' -> 1,
    'ó' -> 5,
    'p' -> 2,
    'q' -> 10,
    'r' -> 1,
    's' -> 1,
    'ś' -> 5,
    't' -> 2,
    'u' -> 3,
    'v' -> 4,
    'w' -> 1,
    'x' -> 8,
    'y' -> 2,
    'z' -> 1,
    'ź' -> 9,
    'ż' -> 5
  )

  private val letterBonusBoard= {
    val board = Array.ofDim[PointBonus](15, 15)
    for (i <- 0 until 15; j <- 0 until 15) {
      board(i)(j) = NoBonus
    }
    board(0)(0)= TripleWord
    board(0)(7) = TripleWord
    board(0)(14) = TripleWord
    board(7)(0) = TripleWord
    board(7)(14) = TripleWord
    board(14)(0) = TripleWord
    board(14)(7) = TripleWord
    board(14)(14) = TripleWord
    board(1)(1) = DoubleWord
    board(2)(2) = DoubleWord
    board(3)(3) = DoubleWord
    board(4)(4) = DoubleWord
    board(10)(10) = DoubleWord
    board(11)(11) = DoubleWord
    board(12)(12) = DoubleWord
    board(13)(13) = DoubleWord
    board(1)(13) = DoubleWord
    board(2)(12) = DoubleWord
    board(3)(11) = DoubleWord
    board(4)(10) = DoubleWord
    board(10)(4) = DoubleWord
    board(11)(3) = DoubleWord
    board(12)(2) = DoubleWord
    board(13)(1) = DoubleWord
    board(7)(7) = DoubleWord
    board(0)(3) = DoubleLetter
    board(0)(11) = DoubleLetter
    board(2)(6) = DoubleLetter
    board(2)(8) = DoubleLetter
    board(3)(0) = DoubleLetter
    board(3)(7) = DoubleLetter
    board(3)(14) = DoubleLetter
    board(6)(2) = DoubleLetter
    board(6)(6) = DoubleLetter
    board(6)(8) = DoubleLetter
    board(6)(12) = DoubleLetter
    board(7)(3) = DoubleLetter
    board(7)(11) = DoubleLetter
    board(8)(2) = DoubleLetter
    board(8)(6) = DoubleLetter
    board(8)(8) = DoubleLetter
    board(8)(12) = DoubleLetter
    board(11)(0) = DoubleLetter
    board(11)(7) = DoubleLetter
    board(11)(14) = DoubleLetter
    board(12)(6) = DoubleLetter
    board(12)(8) = DoubleLetter
    board(14)(3) = DoubleLetter
    board(14)(11) = DoubleLetter
    board(1)(5) = TripleLetter
    board(1)(9) = TripleLetter
    board(5)(1) = TripleLetter
    board(5)(5) = TripleLetter
    board(5)(9) = TripleLetter
    board(5)(13) = TripleLetter
    board(9)(1) = TripleLetter
    board(9)(5) = TripleLetter
    board(9)(9) = TripleLetter
    board(9)(13) = TripleLetter
    board(13)(5) = TripleLetter
    board(13)(9) = TripleLetter
    board
  }

  def printBonusBoard(): Unit = {
    for (i <- 0 until 15) {
      for (j <- 0 until 15) {
        if(letterBonusBoard(i)(j) == DoubleWord) {
          print("DW ")
        } else if(letterBonusBoard(i)(j) == TripleWord) {
          print("TW ")
        } else if(letterBonusBoard(i)(j) == DoubleLetter) {
          print("DL ")
        } else if(letterBonusBoard(i)(j) == TripleLetter) {
          print("TL ")
        } else {
          print("   ")
        }
      }
      println()
    }
  }



  def getLetterPoint(letter: Char): Int = {
    letterPointMap(letter)
  }

  def calculateWordPoints(word: String): Int = {
    word.map(getLetterPoint).sum
  }

  def getBonus(x: Int, y: Int): PointBonus = {
    letterBonusBoard(x)(y)
  }

  def updateLetterBonusBoard(x: Int, y: Int): Unit = {
    letterBonusBoard(x)(y) = NoBonus
  }

  def calculateWordPoints(word: String,startX: Int, startY:Int, isVertical: Boolean,allLettersUsed: Boolean): Int = {
    var wordPoints = 0
    var wordMultiplier = 1
    for (i <- 0 until word.length) {
      val letter = word(i)
      val letterPoints = getLetterPoint(letter)
      val letterMultiplier = getLetterMultiplier(startX, startY, isVertical, i)
      wordPoints += letterPoints * letterMultiplier
      wordMultiplier *= getWordMultiplier(startX, startY, isVertical, i)
    }
    if(wordMultiplier > 0) {
      wordPoints *= wordMultiplier
    }
    if(allLettersUsed) {
      wordPoints += 50
    }
    wordPoints
  }

  private def getLetterMultiplier(startX: Int, startY: Int, isVertical: Boolean, i: Int): Int = {
    if (isVertical) {
      if (startX + i >= 15 ) {
        return 1
      }
      letterBonusBoard(startX + i)(startY) match {
        case DoubleLetter => 2
        case TripleLetter => 3
        case _ => 1
      }
    } else {
      if (startY + i >= 15) {
        return 1
      }
      letterBonusBoard(startX)(startY + i) match {
        case DoubleLetter => 2
        case TripleLetter => 3
        case _ => 1
      }
    }
  }

  private def getWordMultiplier(startX: Int, startY: Int, isVertical: Boolean, i: Int): Int = {
    if (isVertical) {
      letterBonusBoard(startX + i)(startY) match {
        case DoubleWord => 2
        case TripleWord => 3
        case _ => 1
      }
    } else {
      letterBonusBoard(startX)(startY + i) match {
        case DoubleWord => 2
        case TripleWord => 3
        case _ => 1
      }
    }
  }

  def calculateWordPointWithBonusForOneLetter(word: String, startX: Int, startY: Int, isVertical: Boolean, i: Int): Int = {
    val letter = word(i)
    val letterPoints = getLetterPoint(letter)
    val letterMultiplier = getLetterMultiplier(startX, startY, isVertical, i)
    val wordMultiplier = getWordMultiplier(startX, startY, isVertical, i)
    (calculateWordPoints(word)- letterPoints + letterPoints * letterMultiplier) * wordMultiplier
  }


}
