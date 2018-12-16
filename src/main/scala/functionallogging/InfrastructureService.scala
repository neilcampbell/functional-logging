package functionallogging

import cats.effect.IO

trait InfrastructureService {
  def getSomething(event: String): MyApp[Int]
  def saveSomething(id: Int): MyApp[Unit]
}

class InfrastructureServiceImpl extends InfrastructureService {
  def getSomething(event: String): MyApp[Int] = {
    for {
      id <- myApp[Int](IO(Integer.parseInt(event)))
      _ <- logInfo(s"getSomething was called with $event")
    } yield id
  }

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
