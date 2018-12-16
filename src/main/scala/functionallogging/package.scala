import cats.data.{Kleisli, StateT}
import cats.effect.IO
import fs2.Stream

package object functionallogging {

  type StreamIO[A] = Stream[IO, A]

  type MyAppK[A] = Kleisli[MyApp, Platform, A]
  type MyApp[A] = StateT[StreamIO, Logs, A]

  type Logs = List[LogEntry]

  case class LogEntry(level: String, msg: String)

  def logInfo(msg: String): MyApp[Unit] =
    StateT[StreamIO, Logs, Unit] { s =>
      Stream.emit((LogEntry("INFO", msg) :: s, ()))
    }

  def myAppK[A](f: Platform => MyApp[A]): MyAppK[A] =
    Kleisli[MyApp, Platform, A] { p =>
      f(p)
    }

  def myApp[A](f: Logs => StreamIO[(Logs, A)]): MyApp[A] =
    StateT[StreamIO, Logs, A](f)

  def myApp[A](f: => IO[A]): MyApp[A] = myApp[A] { s: Logs =>
    Stream.eval(for {
      a <- f
    } yield (s, a))
  }
}
