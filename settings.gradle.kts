// Copyright 2025, compose-miuix-ui contributors
// SPDX-License-Identifier: Apache-2.0

@file:Suppress("UnstableApiUsage")

rootProject.name = "elyon"

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    includeBuild("build-plugins")
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

include(":elyon-core")
include(":elyon-ui")
include(":elyon-preference")
include(":elyon-shader")
include(":elyon-blur")
include(":elyon-squircle")
include(":elyon-icons")
include(":elyon-nav")

include(":baselineprofile")

include(":example:shared")
include(":example:android")
include(":example:desktop")
include(":example:web")
include(":example:macos")

include(":docs:demo")
include(":docs:iconGen")
