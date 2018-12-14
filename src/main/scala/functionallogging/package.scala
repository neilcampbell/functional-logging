import cats.effect.IO
import fs2.Stream

package object functionallogging {

  type StreamIO[A] = Stream[IO, A]
}
