package wordTree

class WordTree(wordList: List[String]) {
  val root = new WordTreeNode(' ', false, Map[Char, WordTreeNode]())
  for (word <- wordList) {
    var currentNode = root
    for (letter <- word) {
      if (currentNode.contains(letter)) {
        currentNode = currentNode.getChild(letter)
      } else {
        val newNode = new WordTreeNode(letter, false, Map[Char, WordTreeNode]())
        currentNode.children += (letter -> newNode)
        currentNode = newNode
      }
    }
    currentNode.isWord = true
  }
  def getRoot: WordTreeNode = {
    root
  }
  def getWordList: List[String] = {
    root.getWordList
  }
  def containsPrefix(prefix: String): Boolean = {
    root.containsPrefix(prefix)
  }
  def contains(word: String): Boolean = {
    root.contains(word)
  }
  def getWordListSize: Int = {
    root.getWordList.size
  }
  def getWordListLength(index: Int): Int = {
    root.getWordList(index).length
  }

}
