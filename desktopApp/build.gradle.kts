import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
  kotlin("multiplatform")
  id("org.jetbrains.compose")
}

kotlin {
  jvm {
  }
  sourceSets {
    val jvmMain by getting {
      dependencies {
        implementation(compose.desktop.currentOs)
        implementation(project(":shared"))
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-swing:${Versions.COROUTINE}")
      }
    }
  }
}

compose.desktop {
  application {
    mainClass = "MainKt"
    nativeDistributions {
      targetFormats(TargetFormat.Dmg, TargetFormat.Exe)
      packageVersion = Artifact.VERSION_NAME
      packageName = Artifact.APP_NAME
      copyright = "© 2023 Sujan Poudel. All rights reserved."
      licenseFile.set(project.file("../LICENSE"))

      buildTypes.release {
        includeAllModules = true
        proguard {
          isEnabled.set(false)
          configurationFiles.from("compose.desktop.pro")
        }
      }

      macOS {
        iconFile.set(project.file("app_icon.icns"))
        bundleID = Artifact.APP_ID
      }
      windows {
        iconFile.set(project.file("app_icon.ico"))
      }
      linux {
        iconFile.set(project.file("app_icon.png"))
      }
    }

  }

}
