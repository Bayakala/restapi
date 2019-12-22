/*
import Messages._
object TestVoided extends App {

  val actions:List[Any] = List(
    SalesLogged("01","01","912889",3,1000)
    ,Discounted(0,false,"001",50)
    ,Voided(2)
    ,Subtotal(0)
    ,PaymentMade("01","123456",2000)
  )

  def unzip[T](target: Seq[T])(p: T => Boolean): (Seq[T],Seq[T]) = {
    target.foldLeft((Seq[T](),Seq[T]())){
      case (b,t) => if (p(t)) (b._1 :+ t, b._2) else (b._1, b._2 :+ t)
    }
  }



  val (vtxn,txn) = unzip(actions) {
     t => t.isInstanceOf[Voided]
  }


  println(s"$vtxn, $txn")

  val voidtxns = actions.filter(a => a.isInstanceOf[Voided])
  val listOfActions = (1 to actions.length) zip actions
  listOfActions.foreach { case (idx, txn) =>
    txn match {
      case Voided(seq) => println(s"Voided($seq)")
      case ti@_ =>
        if (!(voidtxns.find(a => a.asInstanceOf[Voided].seq == idx)).isEmpty)
          println(s"$ti been voided")
        else
          println(s"$ti passed")

    }
  }

}

*/