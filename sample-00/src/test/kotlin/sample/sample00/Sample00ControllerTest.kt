package sample.sample00

import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.httpPost
import io.kotlintest.Description
import io.kotlintest.Spec
import io.kotlintest.data.forall
import io.kotlintest.shouldBe
import io.kotlintest.specs.FunSpec
import io.kotlintest.tables.row
import org.kodein.di.generic.instance

internal class Sample00ControllerTest : FunSpec() {
  private val ktorServer by kodein.instance<KtorServerService>()

  override fun beforeSpec(description: Description, spec: Spec) {
    ktorServer.run()
  }

  init {
    test("success-get-2") {
      forall(row(1, 2), row(2, 5)) { param0, param1 ->
        val (_, _, result) = "http://127.0.0.1:${ktorServer.serverPort}/sample00".httpGet(
          parameters = listOf("param0" to param0, "param1" to param1)
                                                                                         ).responseString()
        result.get() shouldBe "OK:$param0:$param1"
      }
    }

    test("success-get-3") {

      forall(row(1, 2, 3), row(2, 5, 8)) { param0, param1, param2 ->
        val (_, _, result) = "http://127.0.0.1:${ktorServer.serverPort}/sample00".httpGet(
          parameters = listOf("param0" to param0, "param1" to param1, "param2" to param2)
                                                                                         ).responseString()
        result.get() shouldBe "OK:$param0:$param1"
      }
    }

    test("success-post-2") {
      forall(row(1, 2), row(2, 5)) { param0, param1 ->
        val (_, _, result) = "http://127.0.0.1:${ktorServer.serverPort}/sample00".httpPost(
          parameters = listOf("param0" to param0, "param1" to param1)
                                                                                          ).responseString()
        result.get() shouldBe "OK:$param0:$param1"
      }
    }

    test("success-post-3") {

      forall(row(1, 2, 3), row(2, 5, 8)) { param0, param1, param2 ->
        val (_, _, result) = "http://127.0.0.1:${ktorServer.serverPort}/sample00".httpPost(
          parameters = listOf("param0" to param0, "param1" to param1, "param2" to param2)
                                                                                          ).responseString()
        result.get() shouldBe "OK:$param0:$param1"
      }
    }

  }
}