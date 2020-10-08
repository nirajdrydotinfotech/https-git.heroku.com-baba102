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
  "com.typesafe.akka" %% "akka-http" % akkaHttpVersion
)
