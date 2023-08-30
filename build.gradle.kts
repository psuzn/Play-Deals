
import org.jlleitschuh.gradle.ktlint.KtlintExtension
import org.jlleitschuh.gradle.ktlint.KtlintPlugin

plugins {
  kotlin("jvm") version Versions.KOTLIN apply false
  kotlin("multiplatform") version Versions.KOTLIN apply false
  kotlin("android") version Versions.KOTLIN apply false
  kotlin("plugin.serialization") version Versions.KOTLIN apply false
  id("com.android.application") version Versions.AGP apply false
  id("com.android.library") version Versions.AGP apply false
  id("org.jetbrains.compose") version Versions.COMPOSE apply false
  id("com.adarshr.test-logger") version "3.2.0" apply false
  id("org.jlleitschuh.gradle.ktlint") version "11.5.0" apply true
}

allprojects {

  apply<KtlintPlugin>()

  repositories {
    google()
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
  }

  configure<KtlintExtension> {
    version.set("0.50.0")
  }
}

tasks.register("clean", Delete::class) {
  delete(rootProject.buildDir)
}
