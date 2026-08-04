plugins {
    id("com.android.application") version "9.1.0" apply false
    id("com.android.kotlin.multiplatform.library") version "9.1.0" apply false
    id("org.jetbrains.kotlin.multiplatform") version "2.4.10" apply false
    id("org.jetbrains.kotlin.plugin.compose") version "2.4.10" apply false
    id("org.jetbrains.compose") version "1.11.1" apply false
}

allprojects {
    plugins.withType<org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsPlugin> {
        extensions.configure<org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsEnvSpec> {
            download.set(false)
        }
    }

    plugins.withType<org.jetbrains.kotlin.gradle.targets.wasm.nodejs.WasmNodeJsPlugin> {
        extensions.configure<org.jetbrains.kotlin.gradle.targets.wasm.nodejs.WasmNodeJsEnvSpec> {
            download.set(false)
        }
    }

    plugins.withType<org.jetbrains.kotlin.gradle.targets.wasm.binaryen.BinaryenPlugin> {
        extensions.configure<org.jetbrains.kotlin.gradle.targets.wasm.binaryen.BinaryenEnvSpec> {
            download.set(false)
        }
    }
}
