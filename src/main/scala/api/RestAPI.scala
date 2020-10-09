package api

import akka.actor.ActorSystem
import akka.actor.Status.Success
import akka.http.scaladsl.server.Route
import akka.stream.ActorMaterializer
import akka.util.Timeout
import akka.http.scaladsl.server.Directives.{entity, _}
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport
import model.Admin
import repo.AdminRepo
import akka.http.scaladsl.marshalling.ToResponseMarshallable
import akka.http.scaladsl.model.{StatusCode, StatusCodes}
import io.circe.generic.AutoDerivation

import scala.concurrent.duration._
import io.circe.generic.auto._
import sun.security.timestamp.TSResponse

object RestAPI extends FailFastCirceSupport with AutoDerivation{
  implicit val system = ActorSystem("heroku")
  implicit val materializer = ActorMaterializer
  import system.dispatcher
  implicit val timeout = Timeout(5.seconds)

  val getRoute:Route={
    pathPrefix("admin"){
      (post & entity(as[Admin])) { admin =>
        complete {
          (AdminRepo.insertData(admin)).map[ToResponseMarshallable]{
            case admin: Admin =>StatusCodes.OK
            case _ => TSResponse.BAD_REQUEST
          }
        }
      }~
      get{
        complete(AdminRepo.findAll)
      }
  }

  }
}
