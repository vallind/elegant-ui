// Copyright 2025, compose-miuix-ui contributors
// SPDX-License-Identifier: Apache-2.0

import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl

plugins {
    alias(libs.plugins.androidKotlinMultiplatformLibrary)
    alias(libs.plugins.baselineprofile)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.dokka)
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
        minSdk = BuildConfig.MIN_SDK
        namespace = "${BuildConfig.LIBRARY_ID}.ui"
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

    applyElyonSourceSetHierarchy()

    sourceSets {
        commonMain.dependencies {
            api(projects.elyonCore)
            api(projects.elyonEffects)
            api(libs.jetbrains.compose.foundation)

            implementation(libs.androidx.navigationevent)
            implementation(libs.jetbrains.compose.window.size)

            implementation(libs.materialKolor.utilities) // Material Color for Multiplatform
        }
    }
}

baselineProfile {
    filter {
        include("io.elyon.kmp.**")
    }
}

val convertBaselineProfile by tasks.registering(ConvertBaselineProfileTask::class) {
    description = "convertBaselineProfile"
    inputFile.set(
        layout.projectDirectory.file("src/androidMain/generated/baselineProfiles/baseline-prof.txt"),
    )
    outputFile.set(
        layout.projectDirectory.file("src/androidMain/baselineProfiles/baseline-prof.txt"),
    )
    targetPackage.set("io/elyon/kmp/")
    excludePackages.set(
        listOf(
            "io/elyon/kmp/icon/extended/",
            "io/elyon/kmp/shared/",
        ),
    )
    additionalOutputs.put(
        "io/elyon/kmp/blur/",
        rootProject.layout.projectDirectory
            .file(
                "elyon-blur/src/androidMain/baselineProfiles/baseline-prof.txt",
            ).asFile.absolutePath,
    )
    additionalOutputs.put(
        "io/elyon/kmp/nav/",
        rootProject.layout.projectDirectory
            .file(
                "elyon-nav/src/androidMain/baselineProfiles/baseline-prof.txt",
            ).asFile.absolutePath,
    )
}

tasks.matching { it.name == "generateBaselineProfile" }.configureEach {
    finalizedBy(convertBaselineProfile)
}

dependencies {
    baselineProfile(project(":baselineprofile"))
}
