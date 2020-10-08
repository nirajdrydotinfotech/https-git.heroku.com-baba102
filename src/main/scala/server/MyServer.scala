package server
import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.model.{ContentTypes, HttpEntity}

object MyServer {
  val content =
    """
      |<html>
      | <head></head>
      | <body>
      |   This is an HTML page served by Akka HTTP!
      | </body>
      |</html>
    """
  def main(args: Array[String]): Unit = {

    val route = get {
      complete(
        HttpEntity(
          ContentTypes.`text/html(UTF-8)`,
          content
        )
      )
    }
    implicit val system = ActorSystem("Server")

    // set hostname/port combination
    val host = "0.0.0.0"
    val port: Int = sys.env.getOrElse("PORT", "8080").toInt

    // this actually starts the server
    Http().bindAndHandle(route, host, port)
  }
}
