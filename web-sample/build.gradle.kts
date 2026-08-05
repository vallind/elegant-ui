plugins {
    id("org.jetbrains.kotlin.multiplatform")
    id("org.jetbrains.compose")
    id("org.jetbrains.kotlin.plugin.compose")
}

kotlin {
    wasmJs {
        browser()
        binaries.executable()
    }

    js(IR) {
        browser()
        binaries.executable()
    }

    sourceSets {
        wasmJsMain.dependencies {
            implementation(project(":example"))
            implementation(project(":showcase"))
            implementation(compose.runtime)
            implementation(compose.ui)
        }
        jsMain.dependencies {
            implementation(project(":example"))
            implementation(project(":showcase"))
            implementation(compose.runtime)
            implementation(compose.ui)
        }
    }
}
