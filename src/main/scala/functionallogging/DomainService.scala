package functionallogging

trait DomainService {
  def process(item: String): MyAppK[Unit]
}

class DomainServiceImpl extends DomainService {

  def process(item: String): MyAppK[Unit] =
    myAppK { p =>
      for {
        _ <- logInfo("Process started")
        r <- p.infra.getSomething(item)
        _ <- p.infra.saveSomething(r)
        _ <- logInfo("Process completed")
      } yield ()
    }
}
