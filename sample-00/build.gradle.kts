import org.jetbrains.kotlin.gradle.plugin.KotlinPlatformJvmPlugin

apply {
  plugin<KotlinPlatformJvmPlugin>()
}

dependencies {
  "compile"("io.ktor:ktor-server-core")
  "compile"("io.ktor:ktor-server-cio")
  "compile"("io.ktor:ktor-locations")
}