package business

import akka.actor.ActorSystem
import javax.inject.Inject
import play.api.{Logger, MarkerContext}

import scala.concurrent.{ExecutionContext, Future}

class ParallelComputer @Inject()(system: ActorSystem) {

  implicit val myExecutionContext: ExecutionContext =
    system.dispatchers.lookup("my-context")

  def compute(implicit marker: MarkerContext) = {
    val futures = (1 to 5).map(Future(_))
    futures.foreach {
      _.map { int =>
        val start = System.currentTimeMillis()
        while ((System.currentTimeMillis() - start) < 100) {}
        val res = int * 10
        Logger.info(s"Computed x10 $res")
        res
      }.map { int =>
          val start = System.currentTimeMillis()
          while ((System.currentTimeMillis() - start) < 100) {}
          val res = int + 1
          Logger.info(s"Computed +1 $res")
          res
        }
        .foreach { int =>
          Logger.info(s"Final result $int")
        }
    }
  }
}
