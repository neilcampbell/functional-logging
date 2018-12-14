package functionallogging

import cats.effect.IO
import fs2.Stream

trait InfrastructureService {
  def getSomething(event: String): StreamIO[Int]
  def saveSomething(id: Int): StreamIO[Unit]
}

class InfrastructureServiceImpl extends InfrastructureService {
  def getSomething(event: String): StreamIO[Int] =
    Stream.eval(IO {
      val id = Integer.parseInt(event)
      println(s"getSomething was called with $event")
      id
    })
  def saveSomething(id: Int): StreamIO[Unit] =
    Stream.eval(IO { println(s"saveSomething was called with $id") })
}
