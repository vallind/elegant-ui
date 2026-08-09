// Copyright 2026, compose-miuix-ui contributors
// SPDX-License-Identifier: Apache-2.0

import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl

plugins {
    alias(libs.plugins.androidKotlinMultiplatformLibrary)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.kotlinMultiplatform)
    id("module.kotlin-jvm-toolchain")
}

kotlin {
    withSourcesJar(true)

    android {
        buildToolsVersion = BuildConfig.BUILD_TOOLS_VERSION
        compileSdk {
            version =
                release(BuildConfig.COMPILE_SDK) {
                    minorApiLevel = BuildConfig.COMPILE_SDK_MINOR
                }
        }
        minSdk = 33
        namespace = "${BuildConfig.LIBRARY_ID}.blur"
    }

    jvm("desktop")

    iosArm64()
    iosSimulatorArm64()
    macosArm64()

    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        browser()
    }

    js {
        browser()
    }

    compilerOptions {
        freeCompilerArgs.add("-Xcontext-parameters")
    }

    applyElyonSourceSetHierarchy()

    sourceSets {
        commonMain.dependencies {
            api(projects.elyonEffects)
            implementation(libs.jetbrains.compose.foundation)
        }
    }
}
