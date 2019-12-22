package com.datatech.restapi
import MockModels._
import MockRepo._

object PersonRoute {

  class PersonRoute(pathName: String, repo: RepoBase[Person])
     extends RouteBase[Person](pathName,repo)

  val route = new PersonRoute("person",new PersonRepo).route
}


