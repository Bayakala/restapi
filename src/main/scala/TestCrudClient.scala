import akka.actor._
import akka.http.scaladsl.model.headers._
import scala.concurrent._
import scala.concurrent.duration._
import akka.http.scaladsl.Http
import spray.json.DefaultJsonProtocol
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.marshalling._
import akka.http.scaladsl.model._
import akka.stream.ActorMaterializer

trait JsonFormats extends SprayJsonSupport with DefaultJsonProtocol
object JsonConverters extends JsonFormats {
  case class Person(name: String,age: Int)
  implicit val fmtPerson = jsonFormat2(Person)
}

object TestCrudClient  {
  type UserInfo = Map[String,Any]
  def main(args: Array[String]): Unit = {
    implicit val system = ActorSystem()
    implicit val materializer = ActorMaterializer()
    // needed for the future flatMap/onComplete in the end
    implicit val executionContext = system.dispatcher

    val helloRequest = HttpRequest(uri = "http://192.168.11.189:50081/")

    val authorization = headers.Authorization(BasicHttpCredentials("johnny", "p4ssw0rd"))
    val authRequest = HttpRequest(
      HttpMethods.POST,
      uri = "http://192.168.11.189:50081/auth",
      headers = List(authorization)
    )


    val futToken: Future[HttpResponse] = Http().singleRequest(authRequest)

    val respToken = for {
      resp <- futToken
      jstr <- resp.entity.dataBytes.runFold("") {(s,b) => s + b.utf8String}
    } yield jstr

    val jstr =  Await.result[String](respToken,2 seconds)
    println(jstr)

    scala.io.StdIn.readLine()

    val authentication = headers.Authorization(OAuth2BearerToken(jstr))

    val getAllRequest = HttpRequest(
      HttpMethods.GET,
      uri = "http://192.168.11.189:50081/api/crud/person",
    ).addHeader(authentication)
    val futGet: Future[HttpResponse] = Http().singleRequest(getAllRequest)
    println(Await.result(futGet,2 seconds))
    scala.io.StdIn.readLine()

    import JsonConverters._

    val saveRequest = HttpRequest(
      HttpMethods.POST,
      uri = "http://192.168.11.189:50081/api/crud/person"
    ).addHeader(authentication)
    val futPost: Future[HttpResponse] =
      for {
        reqEntity <- Marshal(Person("tiger chan",18)).to[RequestEntity]
        response <- Http().singleRequest(saveRequest.copy(entity=reqEntity))
      } yield response

    println(Await.result(futPost,2 seconds))
    scala.io.StdIn.readLine()
    system.terminate()
  }

}