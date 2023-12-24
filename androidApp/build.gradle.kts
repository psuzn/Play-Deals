plugins {
  alias(libs.plugins.kotlin.multiplatform)
  alias(libs.plugins.android.application)
  alias(libs.plugins.compose)
  alias(libs.plugins.google.services)
}

kotlin {
  androidTarget()
  sourceSets {
    androidMain {
      dependencies {
        implementation(project(":shared"))
        implementation(libs.activity.compose)
        implementation(libs.core.ktx)

        implementation(project.dependencies.platform(libs.firebase.bom))
        implementation(libs.firebase.analytics)
        implementation(libs.firebase.messaging)
      }
    }
  }
}


android {
  compileSdk = Artifact.ANDROID_COMPILE_SDK
  namespace = Artifact.APP_ID

  defaultConfig {
    applicationId = Artifact.APP_ID

    minSdk =  Artifact.ANDROID_MIN_SDK
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
