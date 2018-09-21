package sample.sample00

import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.singleton

val kodeinKtorModule = Kodein.Module(name = "ktorModule") {
  bind<Sample00Controller>() with singleton { Sample00Controller() }
  bind<KtorServerService>() with singleton { KtorServerService() }
}