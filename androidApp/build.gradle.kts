plugins {
  kotlin("multiplatform")
  id("com.android.application")
  id("org.jetbrains.compose")
}

kotlin {
  androidTarget()
  sourceSets {
    val androidMain by getting {
      dependencies {
        implementation(project(":shared"))
        api("androidx.activity:activity-compose:1.7.2")
        api("androidx.core:core-ktx:1.12.0")
      }
    }
  }
}

android {
  compileSdk = Artifact.ANDROID_COMPILE_SDK
  namespace = Artifact.APP_ID

  defaultConfig {
    applicationId = Artifact.APP_ID

    minSdk = Artifact.ANDROID_MIN_SDK
    targetSdk = Artifact.ANDROID_TARGET_SDK

    versionCode = Artifact.VERSION_CODE
    versionName = Artifact.VERSION_NAME

    setProperty("archivesBaseName", "play-deals-v$versionName-c$versionCode")
  }

  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
  }

  buildTypes {
    getByName("release") {
      isMinifyEnabled = true
      proguardFiles(
        getDefaultProguardFile("proguard-android-optimize.txt"),
        "proguard-rules.pro",
      )
    }
  }
}
