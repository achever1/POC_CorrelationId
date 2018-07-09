package logging

import play.api.MarkerContext
import play.api.mvc.RequestHeader

trait RequestMarkerContext {

  implicit def requestHeaderToMarkerContext(
      implicit request: RequestHeader): MarkerContext = {
    import net.logstash.logback.marker.LogstashMarker
    import net.logstash.logback.marker.Markers._

    val requestMarkers: LogstashMarker =
      append("reqId", request.headers.get("reqId").getOrElse(""))

    MarkerContext(requestMarkers)
  }

}
