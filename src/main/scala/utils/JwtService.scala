package utils

import java.util.concurrent.TimeUnit

import java.util.concurrent.TimeUnit
import pdi.jwt.{JwtAlgorithm, JwtClaim, JwtSprayJson}
import scala.util.{Failure, Success}

class JwtService {
  implicit val algorithm = JwtAlgorithm.HS256
  val secretKey = "secretkey"

  def createToken(name: String, expirationPeriodInDays: Int): String = {
    val claims = JwtClaim(
      expiration = Some(System.currentTimeMillis() / 1000 + TimeUnit.DAYS.toSeconds(expirationPeriodInDays)),
      issuedAt = Some(System.currentTimeMillis() / 1000),
      //issuer = Some("www.rydotinfotech.com")
    )
    JwtSprayJson.encode(claims, secretKey, algorithm) // JWT string
  }
  def isTokenExpired(token: String): Boolean = JwtSprayJson.decode(token, secretKey, Seq(algorithm)) match {
    case Success(claims) => claims.expiration.getOrElse(0L) < System.currentTimeMillis() / 1000
    case Failure(_) => true
  }
  def isTokenValid(token: String): Boolean = JwtSprayJson.isValid(token, secretKey, Seq(algorithm))
}
