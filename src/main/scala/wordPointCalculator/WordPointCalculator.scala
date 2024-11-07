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


  def getLetterPoint(letter: Char): Int = {
    letterPointMap(letter)
  }

  def getWordPoint(word: String): Int = {
    word.map(getLetterPoint).sum
  }

}
