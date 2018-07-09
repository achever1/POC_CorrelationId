package logging

import actors.Command
import akka.event.Logging.MDC

trait MDCActorLogging extends akka.actor.DiagnosticActorLogging {

  override def mdc(message: Any): MDC = {

    message match {
      case command: Command =>
        command.markerContext.marker.fold(Map.empty[String, String]) { marker =>
          val reqId =
            marker.toString.substring(marker.toString.indexOf("=") + 1)
          Map("reqId" -> reqId)
        }
      case _ ⇒ Map.empty
    }
  }

}
