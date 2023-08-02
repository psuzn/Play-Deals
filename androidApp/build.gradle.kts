plugins {
  kotlin("multiplatform")
  id("com.android.application")
  id("org.jetbrains.compose")
}

kotlin {
  android()
  sourceSets {
    val androidMain by getting {
      dependencies {
        implementation(project(":shared"))
        api("androidx.activity:activity-compose:1.7.2")
        api("androidx.appcompat:appcompat:1.6.1")
        api("androidx.core:core-ktx:1.10.1")
      }
    }
  }
}

android {
  compileSdk = 33
  namespace = "me.sujanpoudel.playdeals"
  defaultConfig {
    applicationId = "me.sujanpoudel.playdeals"
    minSdk = 26
    targetSdk = 33
    versionCode = 1
    versionName = "1.0"
  }

  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
  }

  buildTypes {
    getByName("release") {
      isMinifyEnabled = false
    }
  }
}
