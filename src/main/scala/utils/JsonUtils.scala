package utils

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import model.Admin
import spray.json.DefaultJsonProtocol

trait JsonUtils extends DefaultJsonProtocol with SprayJsonSupport{
implicit val adminFormat=jsonFormat3(Admin)
}
