import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    id("org.jetbrains.kotlin.multiplatform")
    id("com.android.kotlin.multiplatform.library")
    id("org.jetbrains.compose")
    id("org.jetbrains.kotlin.plugin.compose")
    id("org.jetbrains.kotlin.plugin.serialization")
    id("maven-publish")
}

group = "io.github.vallind"
version = "0.1.0-SNAPSHOT"

kotlin {
    android {
        namespace = "com.elegant.compose.ui.nav"
        compileSdk = 37
        minSdk = 24

        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }

        withHostTestBuilder {}.configure {}
    }

    jvm("desktop") {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }

    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        browser()
    }

    js(IR) {
        browser()
    }

    sourceSets {
        // Shared Skia-backed layer shared by desktop, wasmJs, and js (mirrors the Miuix
        // hierarchy): platform actuals for system corner radius and the navigation event
        // bridge live here.
        val skikoMain by creating {
            dependsOn(getByName("commonMain"))
        }
        named("desktopMain") {
            dependsOn(skikoMain)
        }
        named("wasmJsMain") {
            dependsOn(skikoMain)
        }
        named("jsMain") {
            dependsOn(skikoMain)
        }

        commonMain.dependencies {
            api(compose.runtime)
            api(compose.ui)
            api(compose.animation)
            implementation(compose.foundation)
            implementation("org.jetbrains.androidx.lifecycle:lifecycle-runtime:2.11.0")
            implementation("org.jetbrains.androidx.lifecycle:lifecycle-runtime-compose:2.11.0")
            implementation("org.jetbrains.androidx.lifecycle:lifecycle-viewmodel:2.11.0")
            implementation("org.jetbrains.androidx.lifecycle:lifecycle-viewmodel-compose:2.11.0")
            implementation("androidx.navigationevent:navigationevent-compose:1.1.2")
            implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.11.0")
        }

        commonTest.dependencies {
            implementation(kotlin("test"))
        }

        getByName("desktopTest").dependencies {
            implementation(compose.desktop.uiTestJUnit4)
            implementation(compose.desktop.currentOs)
        }
    }
}

publishing {
    repositories {
        maven {
            name = "Build"
            url = layout.buildDirectory.dir("repo").get().asFile.toURI()
        }
    }
}
