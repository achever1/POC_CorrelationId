package controllers

import actors.{ComputeCommand, ParallelComputerActor}
import akka.actor.{ActorSystem, Props}
import business.ParallelComputer
import javax.inject.{Inject, _}
import logging.RequestMarkerContext
import play.api.Logger
import play.api.libs.ws.WSClient
import play.api.mvc.{AbstractController, _}

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class Controller @Inject()(cc: ControllerComponents,
                           ws: WSClient,
                           computer: ParallelComputer)(
    implicit ec: ExecutionContext,
    system: ActorSystem)
    extends AbstractController(cc)
    with RequestMarkerContext {

  val props = Props(new ParallelComputerActor(computer))
  val actor = system.actorOf(props)

  def call() = Action.async { implicit request =>
    Logger.info("in call")
    computer.compute //(myExecutionContext)
    val requester = ws
      .url("http://s2:9000/called")
      .addHttpHeaders(("reqId", request.headers.get("reqId").getOrElse("")))
    Logger.info("calling called...")
    requester.get().recoverWith {
      case e =>
        Logger.error(s"${e.getMessage}", e)
        Future.failed(e)
    }
    Future(Ok("call!"))
  }

  def called() = Action.async { implicit request =>
    Logger.info("in called")
    //computer.compute // (myExecutionContext)
    computeInactor(request)
    Future(Ok("called!"))
  }

  def computeInactor(implicit req: RequestHeader) = {
    val cmd = ComputeCommand()
    actor ! cmd
  }

}
