name := "restapi"

version := "0.3"

scalaVersion := "2.12.8"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-http"   % "10.1.8",
  "com.typesafe.akka" %% "akka-stream" % "2.5.23",
  "com.pauldijou" %% "jwt-core" % "3.0.1",
  "de.heikoseeberger" %% "akka-http-json4s" % "1.22.0",
  "org.json4s" %% "json4s-native" % "3.6.1",
  "com.typesafe.akka" %% "akka-http-spray-json" % "10.1.8",
  "com.typesafe.scala-logging" %% "scala-logging" % "3.9.0",
  "org.slf4j" % "slf4j-simple" % "1.7.25",
  "org.json4s" %% "json4s-jackson" % "3.6.7",
  "org.json4s" %% "json4s-ext" % "3.6.7"
)
