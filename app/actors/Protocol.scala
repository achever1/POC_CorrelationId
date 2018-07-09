package actors

import play.api.MarkerContext

trait Command {
  def markerContext: MarkerContext
}

case class ComputeCommand()(implicit markerCtx: MarkerContext) extends Command {

  override def markerContext = markerCtx

}
