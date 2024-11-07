package restController

import akka.actor.{Actor, Props}

class RestActor extends Actor{

  override def receive: Receive = {
    case _ => println("RestActor received a message")
  }




}

object RestActor{
  def props: Props = Props(new RestActor)
}
