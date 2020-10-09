name := "hello_heroku"

version := "0.1"

scalaVersion := "2.12.4"

// this will add the ability to "stage" which is required for Heroku
enablePlugins(JavaAppPackaging)

// this specifies which class is the main class in the package
mainClass in Compile := Some("server.MyServer")

val akkaVersion = "2.6.5"
val akkaHttpVersion = "10.1.12"

// add the Akka HTTP libraries
libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-stream" % akkaVersion,
  "com.typesafe.akka" %% "akka-actor-typed" % akkaVersion,
  "com.typesafe.akka" %% "akka-http" % akkaHttpVersion,
  "com.typesafe.akka" %% "akka-http-spray-json" % akkaHttpVersion,
  "org.mongodb.scala" %% "mongo-scala-driver" % "4.1.0-beta2"
)

// https://mvnrepository.com/artifact/io.circe/circe-core
libraryDependencies += "io.circe" %% "circe-core" % "0.13.0"
// https://mvnrepository.com/artifact/de.heikoseeberger/akka-http-circe
libraryDependencies += "de.heikoseeberger" %% "akka-http-circe" % "1.29.1"
// https://mvnrepository.com/artifact/io.circe/circe-generic
libraryDependencies += "io.circe" %% "circe-generic" % "0.13.0"

