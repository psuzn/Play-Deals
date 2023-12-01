pluginManagement {
  repositories {
    gradlePluginPortal()
    google()
    maven("https://plugins.gradle.org/m2/")
  }
}

rootProject.name = "play-deals-frontend"

include(":androidApp")
include(":shared")
include(":desktopApp")
