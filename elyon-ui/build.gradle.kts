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
    additionalOutputs.put(
        "io/elyon/kmp/shader/",
        rootProject.layout.projectDirectory
            .file(
                "elyon-effects/src/androidMain/baselineProfiles/baseline-prof.txt",
            ).asFile.absolutePath,
    )
    additionalOutputs.put(
        "io/elyon/kmp/squircle/",
        rootProject.layout.projectDirectory
            .file(
                "elyon-effects/src/androidMain/baselineProfiles/baseline-prof.txt",
            ).asFile.absolutePath,
    )
}

tasks.matching { it.name == "generateBaselineProfile" }.configureEach {
    finalizedBy(convertBaselineProfile)
}

dependencies {
    baselineProfile(project(":baselineprofile"))
}

val fatAar by tasks.registering(FatAarTask::class) {
    group = "distribution"
    description = "Merge all library module AARs into a single AAR (includes blur, requires minSdk 33)"
    dependsOn(
        ":elyon-ui:assembleRelease",
        ":elyon-core:assembleRelease",
        ":elyon-effects:assembleRelease",
        ":elyon-blur:assembleRelease",
        ":elyon-nav:assembleRelease",
    )
    inputAars.from(
        layout.projectDirectory.file("build/outputs/aar/elyon-ui-release.aar"),
        rootProject.layout.projectDirectory.file("elyon-core/build/outputs/aar/elyon-core-release.aar"),
        rootProject.layout.projectDirectory.file("elyon-effects/build/outputs/aar/elyon-effects-release.aar"),
        rootProject.layout.projectDirectory.file("elyon-blur/build/outputs/aar/elyon-blur-release.aar"),
        rootProject.layout.projectDirectory.file("elyon-nav/build/outputs/aar/elyon-nav-release.aar"),
    )
    outputFile.set(layout.buildDirectory.file("fatAar/elyon-all-release.aar"))
}

val fatAarNoBlur by tasks.registering(FatAarTask::class) {
    group = "distribution"
    description = "Merge library AARs without blur into a single AAR (minSdk 23)"
    dependsOn(
        ":elyon-ui:assembleRelease",
        ":elyon-core:assembleRelease",
        ":elyon-effects:assembleRelease",
        ":elyon-nav:assembleRelease",
    )
    inputAars.from(
        layout.projectDirectory.file("build/outputs/aar/elyon-ui-release.aar"),
        rootProject.layout.projectDirectory.file("elyon-core/build/outputs/aar/elyon-core-release.aar"),
        rootProject.layout.projectDirectory.file("elyon-effects/build/outputs/aar/elyon-effects-release.aar"),
        rootProject.layout.projectDirectory.file("elyon-nav/build/outputs/aar/elyon-nav-release.aar"),
    )
    outputFile.set(layout.buildDirectory.file("fatAar/elyon-all-noblur-release.aar"))
}
