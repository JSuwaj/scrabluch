package wordTree

class WordTreeNode(char: Char,isWordd: Boolean, childrenn: Map[Char, WordTreeNode]) {
  val letter = char
  var isWord = isWordd
  var children = childrenn
  def getLetter: Char = {
    letter
  }
  def getIsWord: Boolean = {
    isWord
  }
  def getChildren: Map[Char, WordTreeNode] = {
    children
  }
  def contains(letter: Char): Boolean = {
    children.contains(letter)
  }
  def getChild(letter: Char): WordTreeNode = {
    children(letter)
  }
  def getWordList: List[String] = {
    var wordList = List[String]()
    def getWords(node: WordTreeNode, word: String): Unit = {
      if (node.isWord) {
        wordList = wordList :+ word
      }
      for ((letter, child) <- node.children) {
        getWords(child, word + letter)
      }
    }
    getWords(this, "")
    wordList
  }
  def containsPrefix(prefix: String): Boolean = {
    var currentNode = this
    for (letter <- prefix) {
      if (currentNode.children.contains(letter)) {
        currentNode = currentNode.children(letter)
      } else {
        return false
      }
    }
    true
  }
  def contains(word: String): Boolean = {
    var currentNode = this
    for (letter <- word) {
      if (currentNode.children.contains(letter)) {
        currentNode = currentNode.children(letter)
      } else {
        return false
      }
    }
    currentNode.isWord
  }
}
