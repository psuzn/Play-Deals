plugins {
  kotlin("multiplatform")
  id("com.android.application")
  id("org.jetbrains.compose")
  id("com.google.gms.google-services")
}

kotlin {
  androidTarget()
  sourceSets {
    androidMain {
      dependencies {
        implementation(project(":shared"))
        implementation("androidx.activity:activity-compose:1.8.1")
        implementation("androidx.core:core-ktx:1.12.0")

        implementation(project.dependencies.platform("com.google.firebase:firebase-bom:${Versions.FIREBASE_BOM}"))
        implementation("com.google.firebase:firebase-analytics-ktx")
        implementation("com.google.firebase:firebase-messaging-ktx")
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


  signingConfigs {
    getByName("debug") {
      storeFile = file("./key.debug.jks")
      storePassword = "play-deals"
      keyAlias = "key0"
      keyPassword = "play-deals"
    }
  }

  buildTypes {
    release {
      isMinifyEnabled = true
      proguardFiles(
        getDefaultProguardFile("proguard-android-optimize.txt"),
        "proguard-rules.pro",
      )
    }
    debug {
      isMinifyEnabled = false
    }
  }
}
