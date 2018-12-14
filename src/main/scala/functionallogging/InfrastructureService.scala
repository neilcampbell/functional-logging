package functionallogging

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

  def saveSomething(id: Int): MyApp[Unit] =
    for {
      _ <- logInfo(s"saveSomething was called with $id")
    } yield ()

  //OR

  /*def saveSomething(id: Int): MyApp[Unit] =
    StateT[StreamIO, List[LogEntry], Unit] { s =>
      (for {
        _ <- logInfo(s"saveSomething was called with $id")
      } yield ()).run(s)
    }*/
}
