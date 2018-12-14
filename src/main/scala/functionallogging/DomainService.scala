package functionallogging

import cats.data.{Kleisli, StateT}

trait DomainService {
  def process(item: String): MyAppK[Unit]
}

class DomainServiceImpl extends DomainService {

  def process(item: String): MyAppK[Unit] =
    StateT[KleisliStreamIO, List[LogEntry], Unit] { s =>
      Kleisli[StreamIO, Platform, (List[LogEntry], Unit)] { p =>
        (for {
          r <- p.infra.getSomething(item)
          _ <- p.infra.saveSomething(r)
          _ <- logInfo("Process completed")
        } yield ()).run(s)
      }
    }
}
