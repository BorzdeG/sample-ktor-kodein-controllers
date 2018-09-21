package sample.sample00

import org.kodein.di.conf.ConfigurableKodein
import org.kodein.di.generic.instance

val kodein = ConfigurableKodein().apply {
  addImport(kodeinKtorModule)
}

class Application {
  companion object {
    @JvmStatic
    fun main(args: Array<String>) {
      val ktorServer by kodein.instance<KtorServerService>()
      ktorServer.run()
    }
  }
}
