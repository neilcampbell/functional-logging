import cats.data.{Kleisli, StateT}
import cats.effect.IO
import fs2.Stream

package object functionallogging {

  type StreamIO[A] = Stream[IO, A]
  type KleisliStreamIO[A] = Kleisli[StreamIO, Platform, A]

  type MyAppK[A] = StateT[KleisliStreamIO, List[LogEntry], A]
  type MyApp[A] = StateT[StreamIO, List[LogEntry], A]

  case class LogEntry(level: String, msg: String)

  def logInfo(msg: String): MyApp[Unit] =
    StateT[StreamIO, List[LogEntry], Unit] { s =>
      Stream.emit((LogEntry("INFO", msg) :: s, ()))
    }

}
