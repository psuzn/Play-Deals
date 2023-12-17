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
  id("app.cash.sqldelight")
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
  applyDefaultHierarchyTemplate()

  androidTarget()
  jvm("desktop")

  iosArm64() { configureFramework() }
  iosX64() { configureFramework() }
  iosSimulatorArm64().configureFramework()

  sourceSets {
    commonMain {
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

        api("org.kodein.di:kodein-di:${Versions.KODE_IN}")

        implementation("io.ktor:ktor-client-core:${Versions.KTOR}")
        implementation("io.ktor:ktor-client-content-negotiation:${Versions.KTOR}")
        implementation("io.ktor:ktor-serialization-kotlinx-json:${Versions.KTOR}")
        implementation("io.ktor:ktor-client-logging:${Versions.KTOR}")

        implementation("media.kamel:kamel-image:0.8.3")
        implementation("com.russhwolf:multiplatform-settings:${Versions.SETTINGS}")
        implementation("com.russhwolf:multiplatform-settings-no-arg:${Versions.SETTINGS}")

        implementation("com.mikepenz:multiplatform-markdown-renderer:0.7.2")
        implementation("app.cash.sqldelight:coroutines-extensions:2.0.0-alpha05")
        implementation("app.cash.sqldelight:primitive-adapters:2.0.0-alpha05")
      }
    }

    androidMain {
      dependencies {
        implementation("io.ktor:ktor-client-android:${Versions.KTOR}")
        implementation("androidx.appcompat:appcompat:1.6.1")
        implementation("app.cash.sqldelight:android-driver:${Versions.SQLDELIGHT}")
        implementation("androidx.startup:startup-runtime:1.1.1")
        implementation("com.google.accompanist:accompanist-permissions:0.32.0")

        implementation(project.dependencies.platform("com.google.firebase:firebase-bom:${Versions.FIREBASE_BOM}"))
        implementation("com.google.firebase:firebase-analytics-ktx")
        implementation("com.google.firebase:firebase-messaging-ktx")
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

    iosMain {
      dependencies {
        implementation("io.ktor:ktor-client-darwin:${Versions.KTOR}")
        implementation("app.cash.sqldelight:native-driver:${Versions.SQLDELIGHT}")
      }
    }

    val iosSimulatorArm64Main by getting {
      dependsOn(iosMain.get())
    }

    val desktopMain by getting {
      dependencies {
        implementation(compose.desktop.common)
        implementation("io.ktor:ktor-client-okhttp:${Versions.KTOR}")
        implementation("app.cash.sqldelight:sqlite-driver:${Versions.SQLDELIGHT}")
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
    kotlinCompilerExtensionVersion = Versions.COMPOSE_COMPILER_VERSION
  }

  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
  }

  buildTypes {
    release {
      isMinifyEnabled = true
      proguardFiles("proguard-rules.pro")
    }

    debug {
      isMinifyEnabled = false
    }
  }
}

buildkonfig {
  packageName = pkgName
  defaultConfigs {
    buildConfigField(STRING, "VERSION_NAME", Artifact.VERSION_NAME)
    buildConfigField(STRING, "PACKAGE_NAME", Artifact.APP_ID)
    buildConfigField(INT, "VERSION_CODE", Artifact.VERSION_CODE.toString())
    buildConfigField(BOOLEAN, "MAJOR_RELEASE", Artifact.MAJOR_RELEASE.toString())
  }
}

sqldelight {
  databases {
    create("SqliteDatabase") {
      generateAsync.set(true)
      verifyMigrations.set(true)
      deriveSchemaFromMigrations.set(true)
      packageName.set(pkgName)
    }
  }
}

tasks.withType<AndroidUnitTest> {
  useJUnitPlatform()
}
