import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
  alias(libs.plugins.kotlin.multiplatform)
  alias(libs.plugins.compose)
}

kotlin {
  jvm {
  }
  sourceSets {
    val jvmMain by getting {
      dependencies {
        implementation(compose.desktop.currentOs)
        implementation(project(":shared"))
        implementation(libs.kotlinx.coroutines.swing)
      }
    }
  }
}

compose.desktop {
  application {
    mainClass = "MainKt"
    nativeDistributions {
      targetFormats(TargetFormat.Dmg, TargetFormat.Exe, TargetFormat.Deb)

      packageVersion = Artifact.VERSION_NAME
      packageName = "deals-${System.getProperty("os.arch")}"
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
