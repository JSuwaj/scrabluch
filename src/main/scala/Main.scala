import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http
import dictionaryLoader.DictionaryLoader
import restController.RestController
import restController.RestActor
import wordProcessor.WordProcessor
import wordTree.WordTree
import board.Board

object Main extends App{



  //load words from file and print them
  val dictionaryLoader = new DictionaryLoader().getDictionary
  //dictionaryLoader.foreach(println)
  println("Dictionary size: " + dictionaryLoader.size)

  //create tree from words and print them
  val wordTree = new WordTree(dictionaryLoader)
 // wordTree.getWordList.foreach(println)

  //create rest controller

  implicit val system = ActorSystem(Behaviors.empty, "my-system")
  implicit val executionContext = system.executionContext
  val wordProcessor = new WordProcessor(wordTree,Board(15))
  val restController = new RestController(wordTree,wordProcessor)
  val route = restController.route
  println("Server started")
  val bindingFuture = Http().newServerAt("localhost", 8080).bind(route)
  println(s"Server online at http://localhost:8080/\nPress RETURN to stop...")

  scala.io.StdIn.readLine()
  bindingFuture
    .flatMap(_.unbind())
    .onComplete(_ => system.terminate())



}
