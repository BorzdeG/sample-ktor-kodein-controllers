package sample.sample00

import io.ktor.application.install
import io.ktor.features.CallLogging
import io.ktor.locations.Locations
import io.ktor.routing.Routing
import io.ktor.server.cio.CIO
import io.ktor.server.engine.ApplicationEngine
import io.ktor.server.engine.embeddedServer
import klogging.KLogger
import klogging.KLoggers
import klogging.WithLogging
import org.kodein.di.bindings.ScopeCloseable
import org.kodein.di.generic.allInstances
import java.net.ServerSocket
import java.util.concurrent.TimeUnit.SECONDS

class KtorServerService : Runnable, ScopeCloseable, WithLogging {

  override val logger: KLogger
    get() = KLoggers.logger(this)

  val serverPort by lazy {
    var serverSocket = ServerSocket(0)
    val localPort = serverSocket.localPort
    serverSocket.close()
    localPort
  }

  private lateinit var server: ApplicationEngine

  override fun run() {
    println("ttt1")
    server = embeddedServer(CIO, serverPort) {
      install(CallLogging)
      install(Locations)
      install(Routing) {
        val controllers: List<AbstractKodeinController> by kodein.allInstances()
        controllers.forEach { controller ->
          logger.info { "controller: ${controller.javaClass}" }
          controller.registerRoutes(this)
        }
      }
    }
    server.start()
  }

  override fun close() {
    server.stop(0, 0, SECONDS)
  }
}
