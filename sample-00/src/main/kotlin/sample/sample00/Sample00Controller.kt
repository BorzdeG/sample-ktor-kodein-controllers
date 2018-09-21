package sample.sample00

import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.locations.Location
import io.ktor.locations.get
import io.ktor.locations.post
import io.ktor.request.receive
import io.ktor.response.respondText
import io.ktor.routing.Routing

@Location("/sample00")
data class Sample00Data(val param0: String, val param1: String) : TypedRoute

class Sample00Controller : AbstractKodeinController() {
  override fun registerRoutes(routing: Routing) {
    routing {
      get<Sample00Data> { sample00Data ->
        call.respondText(
          text = "OK:${sample00Data.param0}:${sample00Data.param1}", status = HttpStatusCode.OK
                        )
      }

      post<Sample00Data> {
        val sample00Data = call.receive<Sample00Data>()
        call.respondText(
          text = "OK:${sample00Data.param0}:${sample00Data.param1}", status = HttpStatusCode.OK
                        )
      }
    }
  }
}