package com.datatech.restapi
import MockModels.{Person,Address}
import akka.stream.scaladsl._

import scala.concurrent.Future
object MockRepo {
   class PersonRepo extends RepoBase[Person] {
    override def getById(id: Long): Future[Option[Person]] = Future.successful(Some(Person("johnny lee",23)))

    override def getAll: Future[Seq[Person]] = Future.successful(
      Seq(Person("jonny lee",23),Person("candy wang",45),Person("jimmy kowk",34))
    )

    override def filter(expr: Person => Boolean): Future[Seq[Person]] = Future.successful(
      Seq(Person("jonny lee",23),Person("candy wang",45),Person("jimmy kowk",34))
    )

    override def save(row: Person): Future[Person] = Future.successful(row)

    override def deleteById(id: Long): Future[Int] = Future.successful(1)

    override def updateById(id: Long, row: Person): Future[Int] = Future.successful(1)
  }

  object AddressRepo {
     def getById(id: Long): Future[Option[Address]] = ???

     def getAll: Source[Address,_] = ???

     def filter(expr: Address => Boolean): Future[Seq[Address]] = ???

     def saveAll(rows: Source[Address,_]): Future[Int] = ???
     def saveAll(rows: Future[Seq[Address]]): Future[Int] = ???

     def deleteById(id: Long): Future[Address] = ???

     def updateById(id: Long, row: Address): Future[Address] = ???
  }

}
