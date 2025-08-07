package responses

import wordProcessor.PossibleWord

object FindWordsOnBoardResponse {

  def serializeList(list: List[PossibleWord]): String = {
    if (list.isEmpty) {
      "[]"
    } else {
      val serialized = list.map(word => s"""{"word":"${word.word}","x":${word.x},"y":${word.y},"vertical":${word.isVertical},"points":${word.points}}""").mkString(",")
      s"""[$serialized]"""
    }
  }
}
