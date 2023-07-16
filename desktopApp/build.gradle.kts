plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
}

kotlin {
    jvm {

    }
    sourceSets {
        val jvmMain by getting  {
            dependencies {
                implementation(compose.desktop.currentOs)
                implementation(project(":shared"))
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-swing:${Versions.COROUTINE}")
            }
        }
    }
}

compose.desktop {
    application {
        mainClass = "MainKt"
    }
}
