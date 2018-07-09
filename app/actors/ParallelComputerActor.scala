package actors

import akka.actor.Actor
import business.ParallelComputer
import javax.inject.Inject
import logging.MDCActorLogging

class ParallelComputerActor @Inject()(computer: ParallelComputer) extends Actor with MDCActorLogging {

  def receive = {
    case cmd: Command ⇒
      implicit val mctx = cmd.markerContext
      log.info("In Actor ! Received compute command !")
      computer.compute
    case _      ⇒
      log.info("Received unknown message")
  }
}