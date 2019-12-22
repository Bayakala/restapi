package com.datatech.restapi
import akka.actor._
import akka.stream._
import akka.http.scaladsl.common._
import spray.json.DefaultJsonProtocol
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.server._
import MockModels.Address
import MockRepo._


trait FormatConverter extends SprayJsonSupport with DefaultJsonProtocol{
  implicit val addrFormat = jsonFormat4(Address.apply)
}

case class AddressRoute(val pathName: String)(implicit akkaSys: ActorSystem) extends Directives with FormatConverter {
  implicit val mat = ActorMaterializer()
  implicit val jsonStreamingSupport = EntityStreamingSupport.json()
    .withParallelMarshalling(parallelism = 2, unordered = false)

  val route = path(pathName) {
    get {
      complete(AddressRepo.getAll)
    } ~ post {
      withoutSizeLimit {
        entity(asSourceOf[Address]) { source =>
          /*           val futSavedRows: Future[Seq[Address]] =
              source.runFold(Seq[Address]())((acc, addr) => acc :+ addr)
            onComplete(futSavedRows) { rows =>  */
          onComplete(AddressRepo.saveAll(source)) { rows =>
            complete {
              s"$rows address saved."
            }
          }
        }
      }
    } ~ path(pathName / LongNumber) { id =>
      get {
        complete(AddressRepo.getById(id))
      } ~ put {
        entity(as[Address]) { addr =>
          onComplete(AddressRepo.updateById(id, addr)) { addr =>
            complete(s"address updated to: $addr")
          }
        } ~ delete {
          onComplete(AddressRepo.deleteById(id)) { addr =>
            complete(s"address deleted: $addr")
          }
        }
      }
    }
  }
}