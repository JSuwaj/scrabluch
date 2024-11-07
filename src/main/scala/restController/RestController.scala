package restController



import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import akka.http.scaladsl.server.Route
import requests.{FittingLettersToEmptyPlaceInWord, LettersAndWord, WordFittingInBoard}
import wordProcessor.WordProcessor
import wordTree.WordTree
import JsonFormats._

case class StringList(strings: List[String])


class RestController(wordTree: WordTree,wordProcessor: WordProcessor) {



  def route: Route   =
    path("word") {
      put {
        entity(as[String]) { word =>
          complete(wordTree.contains(word).toString)
        }
      }
    } ~
    path("prefix") {
      put {
        entity(as[String]) { prefix =>
          complete(wordTree.containsPrefix(prefix).toString)
        }
      }
    } ~
    path("wordFromLetters") {
      put {
        entity(as[String]) { letters =>
          complete(serializeList(wordProcessor.tryToFindWordFromLetters(letters)))
        }
      }
    } ~   path("wordFromLettersAndGivenWord") {
      put {
        entity(as[LettersAndWord]) { lettersAndWord =>
          complete(serializeList(wordProcessor.tryToFindWordFromLettersAndGivenWord(lettersAndWord.letters,lettersAndWord.word)))
        }
      }
    } ~  path("wordFittingInBoard") {
      put {
        entity(as[WordFittingInBoard]) { wordFittingInBoard =>
          complete(serializeList(wordProcessor.tryToFindWordFittingInBoard(wordFittingInBoard.letters,wordFittingInBoard.word,wordFittingInBoard.wordsToMatch,wordFittingInBoard.firstWordLetterIndex)))
        }
      }
    } ~ path("findLettersToFitInEmptyPlaceInWord") {
      put {
        entity(as[FittingLettersToEmptyPlaceInWord]) { fittingLettersToEmptyPlaceInWord =>
          complete(serializeList(wordProcessor.findLettersToFitInEmptyPlaceInWord(fittingLettersToEmptyPlaceInWord.leftWord,fittingLettersToEmptyPlaceInWord.rightWord).map(_.toString)))
        }
      }
    }

  def serializeList(list: List[String]): String = {
    list.mkString(",")
  }


}
