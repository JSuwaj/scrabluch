package dictionaryLoader

class DictionaryLoader {

  val dictionary = scala.io.Source.fromFile("src/main/resources/slowa.txt").getLines.toList


  def getDictionary: List[String] = {
    dictionary
  }

  def getDictionarySize: Int = {
    dictionary.size
  }

  def getWord(index: Int): String = {
    dictionary(index)
  }

  def getWordLength(index: Int): Int = {
    dictionary(index).length
  }

  def getWordList: List[String] = {
    dictionary
  }

  def getWordListSize: Int = {
    dictionary.size
  }

  def getWordListLength(index: Int): Int = {
    dictionary(index).length
  }

}

