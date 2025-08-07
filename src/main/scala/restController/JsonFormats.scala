package restController

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import spray.json.DefaultJsonProtocol._
import requests.LettersAndWord


object JsonFormats {
  implicit val lettersAndWordFormat = jsonFormat2(LettersAndWord)
  implicit val wordFittingInBoardFormat = jsonFormat4(requests.WordFittingInBoard)
  implicit val findLettersToFitInEmptyPlaceInWordFormat = jsonFormat2(requests.FittingLettersToEmptyPlaceInWord)
  implicit val wordsOnBoardFormat = jsonFormat2(requests.WordsOnBoard)
  implicit val wordsFromLettersFormat = jsonFormat1(requests.WordsFromLetters)
}
