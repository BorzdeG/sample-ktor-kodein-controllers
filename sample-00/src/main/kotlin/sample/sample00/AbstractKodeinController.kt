package sample.sample00

import io.ktor.application.Application
import io.ktor.locations.locations
import io.ktor.routing.Routing
import io.ktor.routing.routing
import org.kodein.di.generic.instance

abstract class AbstractKodeinController {
  val app: Application by kodein.instance()

  fun routing(callback: Routing.() -> Unit) = app.routing(callback)

  val TypedRoute.href get() = app.locations.href(this)

  abstract fun registerRoutes(routing: Routing)
}

interface TypedRoute