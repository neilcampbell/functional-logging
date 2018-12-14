package functionallogging

object EntryPoint extends App {

  val p = new PlatformImpl
  val service = new DomainServiceImpl

  service.process("1").run(p).compile.drain.unsafeRunSync()
}
