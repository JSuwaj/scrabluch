package responses

object WordsFromLettersResponse {

  def serializeList(list: List[String]): String = {
    if (list.isEmpty) {
      "[]"
    } else {
      val serialized = list.map(word => "\"" + word + "\"").mkString(",")
      s"""[$serialized]"""
    }
  }

}
