import com.android.build.gradle.tasks.factory.AndroidUnitTest
import com.codingfeline.buildkonfig.compiler.FieldSpec.Type.BOOLEAN
import com.codingfeline.buildkonfig.compiler.FieldSpec.Type.INT
import com.codingfeline.buildkonfig.compiler.FieldSpec.Type.STRING
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
  alias(libs.plugins.kotlin.multiplatform)
  alias(libs.plugins.kotlin.serialization)
  alias(libs.plugins.android.library)
  alias(libs.plugins.compose)
  alias(libs.plugins.test.logger)
  alias(libs.plugins.buildkonfig)
  alias(libs.plugins.sqldelight)
}


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
//  iosX64() { configureFramework() }
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
        implementation(libs.kotlinx.coroutines.core)
        implementation(libs.kotlinx.datetime)

        api(libs.kodein.di)

        implementation(libs.ktor.client.core)
        implementation(libs.ktor.client.content.negotiation)
        implementation(libs.ktor.serialization.kotlinx.json)
        implementation(libs.ktor.client.logging)

        implementation(libs.kamel.image)
        implementation(libs.multiplatform.settings)
        implementation(libs.multiplatform.settings.no.arg)

        implementation(libs.multiplatform.markdown.renderer)
        implementation(libs.sqldelight.coroutines.extensions)
        implementation(libs.sqldelight.primitive.adapters)

        implementation(libs.kotlinx.io.core)
      }
    }

    androidMain {
      dependencies {
        implementation(libs.ktor.client.android)
        implementation(libs.appcompat)
        implementation(libs.sqldelight.android.driver)
        implementation(libs.startup.runtime)
        implementation(libs.accompanist.permissions)

        implementation(project.dependencies.platform(libs.firebase.bom))
        implementation(libs.firebase.analytics)
        implementation(libs.firebase.messaging)
      }
    }

    val androidUnitTest by getting {
      dependsOn(commonTest.get())

      dependencies {
        implementation(libs.kotlinx.coroutines.test)
        implementation(libs.kotest.assertions.core)
        implementation(libs.mockk)
        implementation(libs.junit.jupiter)
        implementation(libs.multiplatform.settings.test)
      }
    }

    iosMain {
      dependencies {
        implementation(libs.ktor.client.darwin)
        implementation(libs.sqldelight.native.driver)
      }
    }

    val iosSimulatorArm64Main by getting {
      dependsOn(iosMain.get())
    }

    val desktopMain by getting {
      dependencies {
        implementation(compose.desktop.common)
        implementation(libs.ktor.client.okhttp)
        implementation(libs.sqldelight.sqlite.driver)
        implementation(libs.mputils.paths)
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
    kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
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
