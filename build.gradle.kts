tasks.withType<Wrapper> {
  gradleVersion = "4.10.1"
}

apply<IdeaPlugin>()

configure<IdeaModel> {
  module {
    isDownloadSources = false
    isDownloadJavadoc = false
  }
}

configure<JavaPluginConvention> {
  sourceCompatibility = JavaVersion.VERSION_1_8
}