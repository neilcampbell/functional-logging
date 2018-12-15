import cats.data.{IndexedStateT, Kleisli, StateT}
import cats.effect.IO
import fs2.Stream

package object functionallogging {

  type StreamIO[A] = Stream[IO, A]
  type KleisliStreamIO[A] = Kleisli[StreamIO, Platform, A]

  type MyAppK[A] = StateT[KleisliStreamIO, Logs, A]
  type MyApp[A] = StateT[StreamIO, Logs, A]

  type Logs = List[LogEntry]

  case class LogEntry(level: String, msg: String)

  def logInfo(msg: String): MyApp[Unit] =
    StateT[StreamIO, Logs, Unit] { s =>
      Stream.emit((LogEntry("INFO", msg) :: s, ()))
    }

  def myAppK[A](
      f: Platform => IndexedStateT[StreamIO, Logs, Logs, A]): MyAppK[A] =
    StateT[KleisliStreamIO, Logs, A] { s =>
      Kleisli[StreamIO, Platform, (Logs, A)] { p =>
        f(p).run(s)
      }
    }

  def myApp[A](f: Logs => StreamIO[(Logs, A)]): MyApp[A] =
    StateT[StreamIO, Logs, A](f)

}
