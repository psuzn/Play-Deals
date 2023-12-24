import org.jlleitschuh.gradle.ktlint.KtlintExtension
import org.jlleitschuh.gradle.ktlint.KtlintPlugin

plugins {
  alias(libs.plugins.kotlin.jvm) apply false
  alias(libs.plugins.kotlin.multiplatform) apply false
  alias(libs.plugins.kotlin.android) apply false
  alias(libs.plugins.kotlin.serialization) apply false
  alias(libs.plugins.android.application) apply false
  alias(libs.plugins.android.library) apply false
  alias(libs.plugins.compose) apply false
  alias(libs.plugins.test.logger) apply false
  alias(libs.plugins.ktlint) apply false
  alias(libs.plugins.buildkonfig) apply false
  alias(libs.plugins.sqldelight) apply false
  alias(libs.plugins.google.services) apply false
}

allprojects {

  apply<KtlintPlugin>()

  repositories {
    google()
    mavenCentral()
  }

  configure<KtlintExtension> {
    version.set("0.50.0")
    filter {
      exclude {
        it.file.path.contains("/build")
      }
    }
  }
}

tasks.register("clean", Delete::class) {
  delete(rootProject.buildDir)
}
