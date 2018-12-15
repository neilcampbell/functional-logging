package functionallogging

import fs2.Stream

trait InfrastructureService {
  def getSomething(event: String): MyApp[Int]
  def saveSomething(id: Int): MyApp[Unit]
}

class InfrastructureServiceImpl extends InfrastructureService {
  def getSomething(event: String): MyApp[Int] = {
    val id = Integer.parseInt(event)
    for {
      _ <- logInfo(s"getSomething was called with $event")
    } yield id
  }

  //Return an error
  /*def getSomething(event: String): MyApp[Int] = myApp { _ =>
    Stream.raiseError(new Error("Boom"))
  }*/

  def saveSomething(id: Int): MyApp[Unit] =
    for {
      _ <- logInfo(s"saveSomething was called with $id")
    } yield ()

  //OR

  /*def saveSomething(id: Int): MyApp[Unit] =
    myApp { s =>
      (for {
        _ <- logInfo(s"saveSomething was called with $id")
      } yield ()).run(s)
    }*/

  //OR

  /*def saveSomething(id: Int): MyApp[Unit] =
    StateT[StreamIO, Logs, Unit] { s =>
      (for {
        _ <- logInfo(s"saveSomething was called with $id")
      } yield ()).run(s)
    }*/
}
