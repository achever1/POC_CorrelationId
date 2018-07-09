package logging

import java.util.UUID

import javax.inject.Inject
import play.api.Logger
import play.api.mvc.{RequestHeader, _}

import scala.concurrent.ExecutionContext

class ReqIdFilter @Inject()(implicit ec: ExecutionContext)
    extends EssentialFilter {
  def apply(nextFilter: EssentialAction) = new EssentialAction {
    def apply(requestHeader: RequestHeader) = {

      val reqId = requestHeader.headers.get("reqId").getOrElse {
        val reqId = UUID.randomUUID().toString
        Logger.debug(
        s"No request ID present in current request, generating a new one : $reqId ")
        reqId
      }

      nextFilter(
        requestHeader.copy(
          headers = requestHeader.headers.add(("reqId", reqId))))

    }
  }
}
