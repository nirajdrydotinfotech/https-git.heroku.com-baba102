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
import akka.http.scaladsl.model.headers.RawHeader
import akka.http.scaladsl.model.{HttpResponse, StatusCode, StatusCodes}
import io.circe.generic.AutoDerivation

import scala.concurrent.duration._
import io.circe.generic.auto._
import sun.security.timestamp.TSResponse
import utils.JwtService

import scala.concurrent.Await

object RestAPI extends FailFastCirceSupport with AutoDerivation{
  implicit val system = ActorSystem("heroku")
  implicit val materializer = ActorMaterializer
  import system.dispatcher
  implicit val timeout = Timeout(5.seconds)

  val jwtService = new JwtService
  val getRoute: Route = {
    path("auth") {
      post {
        entity(as[Admin]) {
          case Admin(_id, name, password) =>
            Await.result(AdminRepo.checkCredential(name, password), 60.seconds) match {
              case Some(_) =>
                val token = jwtService.createToken(name, 1)
                respondWithHeader(RawHeader("Access-Token", token)) {
                  complete(StatusCodes.OK)
                }
              case _ => complete(StatusCodes.Unauthorized)
            }
        }
      }
    }~
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
          optionalHeaderValueByName("Authorization") {
            case Some(token) =>
              if (jwtService.isTokenValid(token)) {
                if (jwtService.isTokenExpired(token)) {
                  complete(HttpResponse(status = StatusCodes.Unauthorized, entity = "Token expired."))
                } else
                  complete(AdminRepo.findAll)
              } else {
                complete(HttpResponse(status = StatusCodes.Unauthorized, entity = "Token is invalid, or has been tampered with."))
              }
            case _ => complete(HttpResponse(status = StatusCodes.Unauthorized, entity = "No token provided!"))
          }
        }
  }

  }
}
