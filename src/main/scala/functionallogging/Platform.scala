package functionallogging

trait Platform {
  def infra: InfrastructureService
}

class PlatformImpl extends Platform {
  def infra: InfrastructureService = new InfrastructureServiceImpl
}
