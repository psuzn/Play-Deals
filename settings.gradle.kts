pluginManagement {
  repositories {
    gradlePluginPortal()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    google()
    maven("https://plugins.gradle.org/m2/")
  }

  plugins {
    val kotlinVersion = extra["kotlin.version"] as String
    val agpVersion = extra["agp.version"] as String
    val composeVersion = extra["compose.version"] as String

    kotlin("jvm").version(kotlinVersion)
    kotlin("multiplatform").version(kotlinVersion)
    kotlin("android").version(kotlinVersion)
    kotlin("plugin.serialization").version(kotlinVersion)
    id("com.android.base").version(agpVersion)
    id("com.android.application").version(agpVersion)
    id("com.android.library").version(agpVersion)
    id("org.jetbrains.compose").version(composeVersion)
    id("com.adarshr.test-logger").version("3.2.0")
    id("dev.icerock.mobile.multiplatform-resources") version "0.23.0"
  }
}

rootProject.name = "play-deals-frontend"

include(":androidApp")
include(":shared")
include(":desktopApp")
