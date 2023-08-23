import com.android.build.gradle.tasks.factory.AndroidUnitTest

plugins {
  kotlin("multiplatform")
  kotlin("plugin.serialization")
  kotlin("native.cocoapods")
  id("com.android.library")
  id("org.jetbrains.compose")
  id("com.adarshr.test-logger")
  id("dev.icerock.mobile.multiplatform-resources")
}

version = "1.0-SNAPSHOT"

kotlin {
  android()
  jvm("desktop")
  ios()
  iosSimulatorArm64()

  cocoapods {
    summary = "Shared code for the sample"
    homepage = "https://github.com/JetBrains/compose-jb"
    ios.deploymentTarget = "14.1"
    podfile = project.file("../iosApp/Podfile")
    framework {
      baseName = "shared"
      isStatic = true
    }
    extraSpecAttributes["resources"] = "['src/commonMain/resources/**', 'src/iosMain/resources/**']"
  }

  sourceSets {
    val commonMain by getting {
      dependencies {
        implementation(compose.ui)
        implementation(compose.foundation)
        implementation(compose.material3)
        implementation(compose.animation)
        implementation(compose.materialIconsExtended)
        @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
        implementation(compose.components.resources)
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.COROUTINE}")
        implementation("org.jetbrains.kotlinx:kotlinx-datetime:${Versions.KOTLINX_DATE_TIME}")

        implementation("org.kodein.di:kodein-di:${Versions.KODE_IN}")

        implementation("io.ktor:ktor-client-core:${Versions.KTOR}")
        implementation("io.ktor:ktor-client-content-negotiation:${Versions.KTOR}")
        implementation("io.ktor:ktor-serialization-kotlinx-json:${Versions.KTOR}")
        implementation("io.ktor:ktor-client-logging:${Versions.KTOR}")

        implementation("dev.icerock.moko:resources:${Versions.MOKO_RESOURCES}")
        implementation("dev.icerock.moko:resources-compose:${Versions.MOKO_RESOURCES}")

        implementation("media.kamel:kamel-image:0.6.0")
      }
    }

    val androidMain by getting {
      dependencies {
        implementation("io.ktor:ktor-client-android:${Versions.KTOR}")
      }
    }

    val androidUnitTest by getting {
      dependsOn(commonTest.get())

      dependencies {
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.COROUTINE}")
        implementation("io.kotest:kotest-assertions-core:${Versions.KO_TEST}")
        implementation("io.mockk:mockk:${Versions.MOCKK}")
        implementation("org.junit.jupiter:junit-jupiter:${Versions.JUNIT_JUPITER}")
      }
    }

    val iosMain by getting {
      dependencies {
        implementation("io.ktor:ktor-client-darwin:${Versions.KTOR}")
      }
    }

    val iosSimulatorArm64Main by getting {
      dependsOn(iosMain)
    }

    val desktopMain by getting {
      dependencies {
        implementation(compose.desktop.common)
        implementation("io.ktor:ktor-client-okhttp:${Versions.KTOR}")
      }
    }
  }
}

multiplatformResources {
  multiplatformResourcesPackage = "me.sujanpoudel.playdeals.common.resources"
}

android {
  namespace = "me.sujanpoudel.playdeals.common"
  compileSdk = 33
  sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
  sourceSets["main"].res.srcDirs("src/androidMain/res")
  sourceSets["main"].resources.srcDirs("src/commonMain/resources")
  defaultConfig {
    minSdk = 26
  }

  buildFeatures {
    compose = true
    buildConfig = true
  }

  composeOptions {
    kotlinCompilerExtensionVersion = "1.4.4"
  }

  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
  }
}

tasks.withType<AndroidUnitTest> {
  useJUnitPlatform()
}
