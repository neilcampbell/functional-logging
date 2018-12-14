package functionallogging

import cats.instances.all._

object EntryPoint extends App {

  val p = new PlatformImpl
  val service = new DomainServiceImpl

  service
    .process("2")
    .run(Nil)
    .run(p)
    .compile
    .foldMonoid
    .attempt
    .unsafeRunSync() match {
    case Right(r) => r._1.foreach(l => println(l))
    case Left(th) => println("BOOM"); throw th
  }

}
