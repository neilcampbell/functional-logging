package functionallogging

import cats.data.Kleisli
import cats.effect.IO
import fs2.Stream

trait DomainService {
  def process(item: String): Kleisli[StreamIO, Platform, Unit]
}

class DomainServiceImpl extends DomainService {
  def process(item: String): Kleisli[StreamIO, Platform, Unit] =
    Kleisli[StreamIO, Platform, Unit] { p =>
      for {
        r <- p.infra.getSomething(item)
        _ <- p.infra.saveSomething(r)
        _ = println("Process completed")
      } yield ()
    }
}
