package functionallogging

import cats.effect.IO
import fs2.Pipe

object EntryPoint extends App {

  val p = new PlatformImpl
  val service = new DomainServiceImpl

  service
    .process("2")
    .run(Nil)
    .run(p)
    .through(flushLogs)
    .compile
    .drain
    .unsafeRunSync()

  //This would batch write the logs/accumulated context.
  //One of the cool things here is you can inspect what logs were collected and conditionally write to the logger.
  //For example you could capture a very verbose level in the state, and only write everything if an error was recorded.
  def flushLogs[A]: Pipe[IO, (Logs, A), A] = { in =>
    in.map { r =>
      r._1.foreach(l => println(l))
      r._2
    }
  }
}
