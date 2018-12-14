import cats.data.{Kleisli, StateT}
import cats.effect.IO
import fs2.Stream

package object functionallogging {

  type StreamIO[A] = Stream[IO, A]
  type KleisliStreamIO[A] = Kleisli[StreamIO, Platform, A]

  type MyAppK[A] = StateT[KleisliStreamIO, List[String], A]
  type MyApp[A] = StateT[StreamIO, List[String], A]

}
