
import com.android.build.gradle.tasks.factory.AndroidUnitTest
import com.codingfeline.buildkonfig.compiler.FieldSpec.Type.BOOLEAN
import com.codingfeline.buildkonfig.compiler.FieldSpec.Type.INT
import com.codingfeline.buildkonfig.compiler.FieldSpec.Type.STRING
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
  kotlin("multiplatform")
  kotlin("plugin.serialization")
  id("com.android.library")
  id("org.jetbrains.compose")
  id("com.adarshr.test-logger")
  id("com.codingfeline.buildkonfig")
}

version = "1.0-SNAPSHOT"

val pkgName = "me.sujanpoudel.playdeals.common"

fun KotlinNativeTarget.configureFramework() {
  binaries.framework {
    baseName = "shared"
    isStatic = true
  }
}

kotlin {
  jvmToolchain(17)

  androidTarget()
  jvm("desktop")
  ios { configureFramework() }
  iosSimulatorArm64().configureFramework()

  sourceSets {
    val commonMain by getting {
      dependencies {
        implementation(compose.ui)
        implementation(compose.foundation)
        implementation(compose.material)
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

        implementation("media.kamel:kamel-image:0.6.0")
        implementation("com.russhwolf:multiplatform-settings:${Versions.SETTINGS}")
        implementation("com.russhwolf:multiplatform-settings-no-arg:${Versions.SETTINGS}")

        implementation("com.mikepenz:multiplatform-markdown-renderer:0.7.2")
      }
    }

    val androidMain by getting {
      dependencies {
        implementation("io.ktor:ktor-client-android:${Versions.KTOR}")
        api("androidx.appcompat:appcompat:1.6.1")
      }
    }

    val androidUnitTest by getting {
      dependsOn(commonTest.get())

      dependencies {
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.COROUTINE}")
        implementation("io.kotest:kotest-assertions-core:${Versions.KO_TEST}")
        implementation("io.mockk:mockk:${Versions.MOCKK}")
        implementation("org.junit.jupiter:junit-jupiter:${Versions.JUNIT_JUPITER}")
        implementation("com.russhwolf:multiplatform-settings-test:${Versions.SETTINGS}")
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

android {
  namespace = pkgName
  sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
  sourceSets["main"].res.srcDirs("src/androidMain/res", "src/commonMain/resources")
  sourceSets["main"].resources.srcDirs("src/commonMain/resources")

  compileSdk = Artifact.ANDROID_COMPILE_SDK

  defaultConfig {
    minSdk = Artifact.ANDROID_MIN_SDK
  }

  buildFeatures {
    compose = true
    buildConfig = true
  }

  composeOptions {
    kotlinCompilerExtensionVersion = "1.5.3"
  }

  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
  }

  buildTypes {
    getByName("release") {
      isMinifyEnabled = false
      proguardFiles("proguard-rules.pro")
    }
  }
}

tasks.withType<AndroidUnitTest> {
  useJUnitPlatform()
}

buildkonfig {
  packageName = pkgName
  defaultConfigs {
    buildConfigField(STRING, "VERSION_NAME", Artifact.VERSION_NAME)
    buildConfigField(INT, "VERSION_CODE", Artifact.VERSION_CODE.toString())
    buildConfigField(BOOLEAN, "MAJOR_RELEASE", Artifact.MAJOR_RELEASE.toString())
  }
}
