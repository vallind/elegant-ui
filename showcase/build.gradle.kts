import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    id("org.jetbrains.kotlin.multiplatform")
    id("com.android.kotlin.multiplatform.library")
    id("org.jetbrains.compose")
    id("org.jetbrains.kotlin.plugin.compose")
}

kotlin {
    android {
        namespace = "com.elegant.compose.showcase"
        compileSdk = 37
        minSdk = 24

        compilerOptions.configure {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }

    jvm("desktop") {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }

    wasmJs {
        browser()
    }

    sourceSets {
        commonMain.dependencies {
            api(project(":elegant-ui"))
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
        }

        commonTest.dependencies {
            implementation(kotlin("test"))
        }
    }
}
