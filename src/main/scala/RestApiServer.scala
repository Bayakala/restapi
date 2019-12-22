package com.datatech.restapi

import akka.actor._
import akka.stream._
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
import pdi.jwt._
import AuthBase._
import MockUserAuthService._

object RestApiServer extends App {

  implicit val httpSys = ActorSystem("httpSystem")
  implicit val httpMat = ActorMaterializer()
  implicit val httpEC = httpSys.dispatcher



  implicit val authenticator = new AuthBase()
    .withAlgorithm(JwtAlgorithm.HS256)
    .withSecretKey("OpenSesame")
    .withUserFunc(getValidUser)

  val route =
    path("auth") {
      authenticateBasic(realm = "auth", authenticator.getUserInfo) { userinfo =>
        post { complete(authenticator.issueJwt(userinfo))}
      }
    } ~
      pathPrefix("api") {
        authenticateOAuth2(realm = "api", authenticator.authenticateToken) { validToken =>
            FileRoute(validToken)
              .route ~
            (pathPrefix("crud")) {
              PersonRoute.route
            }
          // ~ ...
        } ~
          (pathPrefix("crud")) {
            PersonRoute.route
            // ~ ...
          }
      }

  val (port, host) = (50081,"192.168.11.189")

  val bindingFuture = Http().bindAndHandle(route,host,port)

  println(s"Server running at $host $port. Press any key to exit ...")

  scala.io.StdIn.readLine()


  bindingFuture.flatMap(_.unbind())
    .onComplete(_ => httpSys.terminate())


}