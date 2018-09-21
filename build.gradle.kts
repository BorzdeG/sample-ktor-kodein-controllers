import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.DetektPlugin
import io.gitlab.arturbosch.detekt.extensions.DetektExtension
import org.gradle.plugins.ide.idea.model.IdeaModel
import org.jetbrains.kotlin.gradle.dsl.Coroutines
import org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinPlatformPluginBase
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
  val kotlinVersion: String by extra

  repositories {
    jcenter()
    gradlePluginPortal()
  }
  dependencies {
    classpath(kotlin("gradle-plugin", kotlinVersion))
    classpath("gradle.plugin.io.gitlab.arturbosch.detekt:detekt-gradle-plugin:latest.release")
  }
}

tasks.withType<Wrapper> {
  gradleVersion = "4.10.2"
}

allprojects {
  val kotlinVersion: String by extra

  repositories {
    jcenter()
    maven(url = "https://dl.bintray.com/kotlin/kotlinx")
    maven(url = "https://dl.bintray.com/kotlin/ktor")
    maven(url = "https://jitpack.io")
  }

  configurations.all {
    resolutionStrategy.apply {
      failOnVersionConflict()
      eachDependency {
        when (requested.group) {
          "org.jetbrains.kotlin"        -> {
            useVersion(kotlinVersion)
            when (requested.name) {
              "kotlin-stdlib-jre7" -> this.useTarget("${requested.group}:kotlin-stdlib-jdk7:$kotlinVersion")
              "kotlin-stdlib-jre8" -> this.useTarget("${requested.group}:kotlin-stdlib-jdk8:$kotlinVersion")
            }
          }

          "io.ktor"                     -> useVersion("0.9.5")
          "org.kodein.di"               -> useVersion("5.2.0")
          "io.kotlintest"               -> useVersion("3.1.10")
          "org.mockito"                 -> useVersion("2.22.0")
          "com.nhaarman.mockitokotlin2" -> useVersion("2.0.0-RC2")
          "ch.qos.logback"              -> useVersion("1.2.3")
          "org.junit.platform"          -> useVersion("1.3.1")
          "org.junit.jupiter"           -> useVersion("5.3.1")
          "com.github.kittinunf.fuel"   -> useVersion("1.15.0")

          "com.github.lewik.klogging"   -> useVersion("1.2.41")
          "com.github.lewik"            -> useTarget("com.github.lewik.klogging:${requested.name}:${requested.version}")
        }
      }
    }
  }

  apply {
    plugin<IdeaPlugin>()
    plugin<DetektPlugin>()
  }

  configure<IdeaModel> {
    module {
      isDownloadSources = false
      isDownloadJavadoc = false
    }
  }
  configure<DetektExtension> {
    config = files(rootDir.resolve("detekt-config.yml"))
  }
  plugins.withType(LifecycleBasePlugin::class.java) {
    tasks.getByName(LifecycleBasePlugin.CHECK_TASK_NAME)
      .dependsOn(tasks.withType(Detekt::class.java))
  }

  plugins.withType(JavaBasePlugin::class.java) {
    configure<JavaPluginConvention> {
      sourceCompatibility = JavaVersion.VERSION_1_8
    }
  }

  tasks.withType(KotlinCompile::class.java) {
    kotlinOptions {
      jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
  }

  tasks.withType(Test::class.java).all {
    useJUnitPlatform {}
    testLogging {
      showStandardStreams = true
    }
  }

  plugins.withType(KotlinPlatformPluginBase::class.java) {
    configure<KotlinProjectExtension> {
      experimental.coroutines = Coroutines.ENABLE
    }
    dependencies {
      arrayOf(
        "com.github.lewik.klogging:klogging.jvm", "ch.qos.logback:logback-classic"
             ).forEach {
        "compile"(it)
      }
    }
    dependencies {
      arrayOf(
        kotlin("test"),
        kotlin("test-junit5"),
        "io.kotlintest:kotlintest-runner-junit5",
        "org.mockito:mockito-inline",
        "com.nhaarman.mockitokotlin2:mockito-kotlin",
        "com.github.kittinunf.fuel:fuel"
             ).forEach {
        "testCompile"(it)
      }
    }
  }
}