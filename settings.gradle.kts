pluginManagement {
  repositories {
    gradlePluginPortal()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    google()
    maven("https://plugins.gradle.org/m2/")
  }
}

rootProject.name = "play-deals-frontend"

include(":androidApp")
include(":shared")
include(":desktopApp")
