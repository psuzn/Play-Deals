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
        api("androidx.core:core-ktx:1.10.1")
      }
    }
  }
}

android {
  compileSdk = 34
  namespace = Artifact.APP_ID
  defaultConfig {
    applicationId = "me.sujanpoudel.playdeals.app" // Artifact.APP_ID
    minSdk = 26
    targetSdk = 34
    versionCode = Artifact.VERSION_CODE
    versionName = Artifact.VERSION_NAME
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
