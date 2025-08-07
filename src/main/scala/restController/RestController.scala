package restController



import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import akka.http.scaladsl.server.Route
import requests.{FittingLettersToEmptyPlaceInWord, LettersAndWord, WordFittingInBoard, WordsFromLetters, WordsOnBoard}
import wordProcessor.WordProcessor
import cors.CORSHandler
import wordTree.WordTree
import responses.{FindWordsOnBoardResponse, WordsFromLettersResponse}
import JsonFormats._
import akka.http.scaladsl.model.HttpMethods
import akka.http.scaladsl.model.headers.{HttpOriginRange, `Access-Control-Allow-Credentials`, `Access-Control-Allow-Headers`, `Access-Control-Allow-Methods`, `Access-Control-Allow-Origin`}
import ch.megard.akka.http.cors.scaladsl.CorsDirectives.cors
import ch.megard.akka.http.cors.scaladsl.model.HttpOriginMatcher
import ch.megard.akka.http.cors.scaladsl.settings.CorsSettings


case class StringList(strings: List[String])


class RestController(wordTree: WordTree,wordProcessor: WordProcessor) extends CORSHandler {

  val corsSettings = CorsSettings.defaultSettings.withAllowedOrigins(HttpOriginMatcher.*)

  val corsHeaders = List(
    `Access-Control-Allow-Origin`.*,
    `Access-Control-Allow-Credentials`(true),
    `Access-Control-Allow-Headers`("Authorization", "Content-Type", "X-Requested-With"),
    `Access-Control-Allow-Methods`(HttpMethods.GET, HttpMethods.POST, HttpMethods.PUT, HttpMethods.DELETE, HttpMethods.OPTIONS)
  )

  def route: Route   = {
    corsHandler(
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
    path("wordsFromLetters") {
      put {
        entity(as[WordsFromLetters]) { letters =>
          complete(WordsFromLettersResponse.serializeList(wordProcessor.tryToFindWordFromLetters(letters.letters)))
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
    } ~ path("findWordsOnBoard"){
      put {
        entity(as[WordsOnBoard]) { wordsOnBoard =>
          complete(FindWordsOnBoardResponse.serializeList(wordProcessor.findAllWordsOnBoardFromLetters(wordsOnBoard.board,wordsOnBoard.letters)))
        }
      }
    }
    )
  }

  def serializeList(list: List[String]): String = {
    list.mkString(",")
  }


}
