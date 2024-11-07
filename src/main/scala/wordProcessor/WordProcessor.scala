package wordProcessor

import board.{ Board, PossibleLetter}
import requests.LettersAndWord
import wordTree.{WordTree, WordTreeNode}

class WordProcessor(wordTree: WordTree,var board: Board) {

  private final val playerLettersNumber = 7

  def tryToFindWordFromLetters(letters: String): List[String] = {
    var wordList = List[String]()
    def getWords(node: WordTreeNode, word: String,lettersLeft: String): Unit = {
      if (node.isWord && !wordList.contains(word)){
        wordList = wordList :+ word
      }
      for ((letter, child) <- node.children) {
        if(lettersLeft.contains(letter)){
          getWords(child, word + letter,lettersLeft.replaceFirst(letter.toString,""))
        }
      }
    }
    getWords(wordTree.getRoot, "",letters)
    wordList.sortBy(_.length)

  }

  def tryToFindWordFromLettersAndGivenWord(letters: String,givenWord: String): List[String] = {
    var wordList = List[String]()
    def getWords(node: WordTreeNode, word: String,lettersLeft: String,givenWordLetters: String): Unit = {
      if (node.isWord && !wordList.contains(word) && givenWordLetters.isEmpty){
        wordList = wordList :+ word
      }
      if(givenWordLetters.nonEmpty){
        tryFitWord(node,word,lettersLeft,givenWordLetters)
      }
      for ((letter, child) <- node.children) {
        if(lettersLeft.contains(letter)){
          getWords(child, word + letter,lettersLeft.replaceFirst(letter.toString,""),givenWordLetters)
        }
      }
    }
    def tryFitWord(node: WordTreeNode, word: String,lettersLeft: String,givenWordLetters: String): Unit = {
      for((letter,child) <- node.children){
       if(givenWordLetters.startsWith(letter.toString)){
         tryFitWord(child,word+letter,lettersLeft.replaceFirst(letter.toString,""),givenWordLetters.substring(1))
       }
     }
      if(givenWordLetters.isEmpty ){
        if(node.isWord && !wordList.contains(word)){
          wordList = wordList :+ word
        }
        getWords(node,word,lettersLeft,"")
      }
    }

    getWords(wordTree.getRoot, "",letters,givenWord)
    wordList.sortBy(_.length)

  }

  def tryToFindWordFittingInBoard(letters: String,givenWord: String,wordsToMatch:List[String],firstWordLetterIndex: Int): List[String] = {

    var wordList = List[String]()
    def getWords(node: WordTreeNode, word: String,lettersLeft: String,givenWordLetters: String, currentIndex:Int = -1): Unit = {
      if (node.isWord && !wordList.contains(word) && givenWordLetters.isEmpty) {
        wordList = wordList :+ word
      }
      if (givenWordLetters.nonEmpty) {
        if (checkPreviousLetters(word)) {
          tryFitWord(node, word, lettersLeft, givenWordLetters, wordsToMatch, firstWordLetterIndex)
        }
      }
      for ((letter, child) <- node.children) {
        if (lettersLeft.contains(letter)) {
          if (givenWordLetters.nonEmpty || (givenWordLetters.isEmpty && checkIfWordExists(letter,currentIndex+1))) {
            {
              getWords(child, word + letter, lettersLeft.replaceFirst(letter.toString, ""), givenWordLetters, currentIndex + 1)
            }
          }
        }
      }
    }
    def tryFitWord(node: WordTreeNode, word: String,lettersLeft: String,givenWordLetters: String,wordsToMatch:List[String],firstWordLetterIndex: Int): Unit = {
      for((letter,child) <- node.children){
       if(givenWordLetters.startsWith(letter.toString)){
         tryFitWord(child,word+letter,lettersLeft.replaceFirst(letter.toString,""),givenWordLetters.substring(1),wordsToMatch,firstWordLetterIndex)
       }
     }
      if(givenWordLetters.isEmpty ){
        if(node.isWord && !wordList.contains(word) && wordsToMatch.contains(word) && word.indexOf(givenWord) == firstWordLetterIndex){
          wordList = wordList :+ word
        }
        getWords(node,word,lettersLeft,"",firstWordLetterIndex+givenWord.length)
      }
    }
    def checkPreviousLetters(word: String): Boolean = {
      if(word.length < firstWordLetterIndex){
        return false
      }
      for (i <- word.length - firstWordLetterIndex until firstWordLetterIndex+ word.length-1) {
        if (!checkIfWordExists(word.charAt(i), i)) {
          return false
        }
      }
      true
    }

    def checkIfWordExists(letter: Char,letterIndex: Int): Boolean = {
      if(letterIndex >= wordsToMatch.length){
        return true
      }
      wordTree.contains(wordsToMatch(letterIndex).replaceFirst("_",""+letter))
    }


    getWords(wordTree.getRoot, "",letters,givenWord)
    wordList.sortBy(_.length)
  }


  def createPossibleLettersMap() = {
    for(i <- 0 until board.getBoardSize){
      for(j <- 0 until board.getBoardSize){
        if(board.isEmpty(i,j)) {
          if (hasAdjacentNonEmptyHorizontally(i, j)) {
            val adjacentLetters = getPossibleLettersHorizontally(i, j)
            board.setPossibleLettersHorizontally(i, j, adjacentLetters)
          }
          if (hasAdjacentNonEmptyVertically(i, j)) {
            val adjacentLetters = getPossibleLettersVertically(i, j)
            board.setPossibleLettersVertically(i, j, adjacentLetters)
          }
        }

      }
    }
  }

  def hasAdjacentNonEmptyHorizontally(i: Int, j: Int): Boolean = {
    if(j-1 >= 0 && !board.isEmpty(i,j-1)){
      return true
    }
    if(j+1 < board.getBoardSize && !board.isEmpty(i,j+1)){
      return true
    }
    false
  }

  def hasAdjacentNonEmptyVertically(i: Int, j: Int): Boolean = {
    if (i - 1 >= 0 && !board.isEmpty(i - 1, j)) {
      return true
    }
    if (i + 1 < board.getBoardSize && !board.isEmpty(i + 1, j)) {
      return true
    }
    false
  }

  def getPossibleLettersHorizontally(i: Int, j: Int): List[Char] = {

    def checkLeft(j: Int, word: String):String = {
      if(j-1 >= 0 && !board.isEmpty(i,j-1)){
       checkLeft(j-1,board.getLetter(i,j-1)+word)
      }
      else{
        word
      }
    }

    def checkRight(j: Int, word: String):String = {
      if(j+1 < board.getBoardSize && !board.isEmpty(i,j+1)){
       checkRight(j+1,word+board.getLetter(i,j+1))
      }
      else{
        word
      }
    }

    val left = checkLeft(j,"")
    val right = checkRight(j,"")

    findLettersToFitInEmptyPlaceInWord(left,right)
  }

  def getPossibleLettersVertically(i: Int, j: Int): List[Char] = {

    def checkUp(i: Int, word: String): String = {
      if (i - 1 >= 0 && !board.isEmpty(i - 1, j)) {
        checkUp(i - 1, board.getLetter(i - 1, j) + word)
      }
      else {
        word
      }
    }

    def checkDown(i: Int, word: String): String = {
      if (i + 1 < board.getBoardSize && !board.isEmpty(i + 1, j)) {
        checkDown(i + 1, word + board.getLetter(i + 1, j))
      }
      else {
        word
      }
    }

    val up = checkUp(i, "")
    val down = checkDown(i, "")

    findLettersToFitInEmptyPlaceInWord(up, down)

  }



  def findLettersToFitInEmptyPlaceInWord(leftString: String, rightString: String): List[Char] = {
    var node: WordTreeNode = wordTree.getRoot
    for(letter <- leftString){
      if(node.contains(letter)){
        node = node.getChild(letter)
      }
      else{
        return List[Char]()
      }
    }
    var possibleLetters = List[Char]()
    def getLetters(node: WordTreeNode, word: String,lettersLeft: String): Unit = {
      if (lettersLeft.isEmpty && node.isWord && !possibleLetters.contains(word.charAt(0))){
        possibleLetters = possibleLetters :+ word.charAt(0)
      }
     if(lettersLeft.nonEmpty && node.children.contains(lettersLeft.charAt(0))) {
       getLetters(node.getChild(lettersLeft.charAt(0)), word + lettersLeft.charAt(0), lettersLeft.substring(1))
     }

    }
    for ((letter, child) <- node.children) {
      getLetters(child, letter.toString,rightString)
    }
    possibleLetters

  }


  def findAllPossibleWordsOnBoardFromLetters(letters: String): List[String] = {
    createPossibleLettersMap()
    var possibleWords = List[String]()
    val possibleLetterPlaces = findPossibleLetterPlaces()
    for (i <- 0 until board.getBoardSize) {
      for (j <- 0 until board.getBoardSize) {
        if (possibleLetterPlaces(i)(j).vertical) {
          possibleWords = possibleWords ++ findWordsVertical(i, j, letters)
        }
        if (possibleLetterPlaces(i)(j).horizontal) {
          possibleWords = possibleWords ++ findWordsHorizontal(i, j, letters)
        }
      }
    }
    println(possibleWords.sortBy(_.length))
    possibleWords

  }

  def findWordsVertical(i: Int, j: Int, letters: String): List[String] = {
    var possibleWords = List[String]()
    def getWords(node: WordTreeNode, word: String,lettersLeft: String,boardX: Int,boardY: Int,connected: Boolean): Unit = {
      if (node.isWord && !possibleWords.contains(word) && connected && (boardX>=board.getBoardSize || board.isEmpty(boardX,boardY))){
        possibleWords = possibleWords :+ word
      }
      if(lettersLeft.isEmpty){
        return
      }
      if(boardX>=board.getBoardSize || boardY>=board.getBoardSize){
        return
      }
      if(!board.isEmpty(boardX,boardY)){
        if(node.children.contains(board.getLetter(boardX,boardY))){
          getWords(node.getChild(board.getLetter(boardX,boardY)),word+board.getLetter(boardX,boardY),lettersLeft,boardX+1,boardY,connected = true)
        }
      } else {
        for ((letter, child) <- node.children) {
          if (lettersLeft.contains(letter) && (!board.isAdjacentHorizontally(boardX, boardY) || board.getPossibleLettersHorizontally(boardX, boardY).contains(letter))) {
            getWords(child, word + letter, lettersLeft.replaceFirst(letter.toString, ""), boardX + 1, boardY, connected )
          }
        }
      }

    }
    getWords(wordTree.getRoot, "",letters,i,j,connected = false)
    possibleWords
  }

  def findWordsHorizontal(i: Int, j: Int, letters: String): List[String] = {
    var possibleWords = List[String]()
    def getWords(node: WordTreeNode, word: String,lettersLeft: String,boardX: Int,boardY: Int,connected: Boolean): Unit = {
      if (node.isWord && !possibleWords.contains(word) && connected && (boardY>=board.getBoardSize ||board.isEmpty(boardX,boardY) )){
        possibleWords = possibleWords :+ word
      }
      if(lettersLeft.isEmpty){
        return
      }
      if(boardX>=board.getBoardSize || boardY>=board.getBoardSize){
        return
      }
      if(!board.isEmpty(boardX,boardY)){
        if(node.children.contains(board.getLetter(boardX,boardY))){
          getWords(node.getChild(board.getLetter(boardX,boardY)),word+board.getLetter(boardX,boardY),lettersLeft,boardX,boardY+1,connected = true)
        }
      } else {
        for ((letter, child) <- node.children) {
          if (lettersLeft.contains(letter) && (!board.isAdjacentVertically(boardX, boardY) || board.getPossibleLettersVertically(boardX, boardY).contains(letter))) {
            getWords(child, word + letter, lettersLeft.replaceFirst(letter.toString, ""), boardX, boardY + 1,connected)
          }
        }
      }

    }
    getWords(wordTree.getRoot, "",letters,i,j,connected = false)
    possibleWords
  }

  def findPossibleLetterPlaces(): Array[Array[PossibleLetter]] = {
    var possibleLetterPlaces = Array.ofDim[PossibleLetter](board.getBoardSize,board.getBoardSize)
    for(i <- 0 until board.getBoardSize){
      for(j <- 0 until board.getBoardSize){
        possibleLetterPlaces(i)(j) = PossibleLetter(vertical = false,horizontal = false)
      }
    }
    for(i <- 0 until board.getBoardSize){
      for(j <- 0 until board.getBoardSize){
        if(!board.isEmpty(i,j)){
          for(k <- 1 until playerLettersNumber+1){
            if (i - k >= 0 && board.isEmpty(i - k, j)) {
              possibleLetterPlaces(i-k)(j).vertical = true
            }
            if (i + k < board.getBoardSize && board.isEmpty(i + k, j)) {
              possibleLetterPlaces(i+k)(j).vertical = true
            }
            if (j - k >= 0 && board.isEmpty(i, j - k)) {
              possibleLetterPlaces(i)(j-k).horizontal = true
            }
            if (j + k < board.getBoardSize && board.isEmpty(i, j + k)) {
              possibleLetterPlaces(i)(j+k).horizontal = true

            }
          }
        }
      }
    }
    possibleLetterPlaces
  }





}
