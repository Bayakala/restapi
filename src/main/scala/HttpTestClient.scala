import akka.actor._
import akka.stream._
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.headers._
import akka.http.scaladsl.model._
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.http.scaladsl.marshallers.sprayjson._
import spray.json._


import scala.concurrent.duration._
import scala.concurrent._
/*
class JsonMarshaller extends  SprayJsonSupport with DefaultJsonProtocol {
  //collect your json format instances
  implicit val fmtState = jsonFormat12(VchStates.apply)
  implicit val fmtTxnItem = jsonFormat16(TxnItem.apply)
  implicit val fmtResponse = jsonFormat4(POSResponse.apply)

}

object HttpTestClient extends JsonMarshaller {
  type UserInfo = Map[String,Any]

  //val url = "http://132.232.229.60:50081/"
  val url = "http://192.168.11.189:50081/"

  def main(args: Array[String]): Unit = {
    implicit val system = ActorSystem()
    implicit val materializer = ActorMaterializer()
    // needed for the future flatMap/onComplete in the end
    implicit val executionContext = system.dispatcher

    val helloRequest = HttpRequest(uri = url)

    val authorization = headers.Authorization(BasicHttpCredentials("johnny", "p4ssw0rd"))
    val authRequest = HttpRequest(
      HttpMethods.POST,
      uri = url+"auth",
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

    implicit val authentication: Authorization = headers.Authorization(OAuth2BearerToken(jstr))

    httpCommand("logon?opr=999")
    httpCommand("voidall")
    scala.io.StdIn.readLine()
    httpCommand("logsales?acct=002&dpt=01&code=911208763&qty=1&price=1000")
    httpCommand("logsales?acct=002&dpt=01&code=911208763&qty=2&price=1000")
    httpCommand("void?seq=2")
    httpCommand("gettxnitems")
    scala.io.StdIn.readLine()
    httpCommand("logsales?acct=002&dpt=01&code=911208763&qty=3&price=1000")
    httpCommand("void?seq=3")
    scala.io.StdIn.readLine()
    httpCommand("gettxnitems")
    scala.io.StdIn.readLine()
    system.terminate()
  }

  def httpCommand(cmd: String)(implicit sys: ActorSystem,auth: Authorization) = {

    import sys.dispatcher
    implicit val mat = ActorMaterializer()
    val cmdRequest = HttpRequest(
      HttpMethods.GET,
      uri = url+"pos-on-cloud/"+cmd,
    ).addHeader(auth)

    try {
      val futposresp = for {
        resp <- Http().singleRequest(cmdRequest)
        posresp <- Unmarshal(resp).to[POSResponse]
      } yield (resp, posresp)

      val res = Await.result(futposresp, 3 second)
      println(res._1)
      println(res._2)
    } catch {
      case err: Throwable => println(s"Error: ${err.getMessage}")
    }

    /*
    futposresp.onComplete{
      case Success(respair) =>
        val (resp,posresp) = respair
        println(resp)
        println(posresp)
      case Failure(err) => println(s"Error: ${err.getMessage}")
    } */
  }

}
*/