# Installation

Elegant UI uses a common-first Kotlin Multiplatform library module while Android remains the only supported runtime target. Android applications consume it like a normal Gradle dependency.

::: warning Current distribution status
The public Maven Central coordinate is reserved but not released yet. Use a project dependency, the CI Maven repository artifact, or `publishToMavenLocal` until a signed release is available.
:::

## Same repository

For an Android application in the same Gradle build:

```kotlin
// app/build.gradle.kts
dependencies {
    implementation(project(":elegant-ui"))
}
```

The included `:sample` application uses this form.

## Maven Local

Publish the complete KMP metadata and Android AAR to your local Maven repository:

```bash
gradle :elegant-ui:publishToMavenLocal
```

Add `mavenLocal()` to the consuming project's repositories:

```kotlin
// settings.gradle.kts
dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        mavenLocal()
    }
}
```

Then depend on the root multiplatform coordinate:

```kotlin
// app/build.gradle.kts
dependencies {
    implementation("io.github.vallind:elegant-ui:0.1.0-SNAPSHOT")
}
```

Gradle reads Kotlin Multiplatform module metadata and selects the Android variant automatically.

## GitHub Actions Maven artifact

The **Android Build** workflow uploads `elegant-ui-maven-repository`. Download and extract it into a directory such as `third-party/elegant-ui-repo`, then register that directory:

```kotlin
// settings.gradle.kts
dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven {
            url = uri(rootDir.resolve("third-party/elegant-ui-repo"))
        }
    }
}
```

Use the same dependency coordinate:

```kotlin
dependencies {
    implementation("io.github.vallind:elegant-ui:0.1.0-SNAPSHOT")
}
```

This artifact contains the root KMP publication, Gradle module metadata, POM files, sources, and the Android AAR. Keep the directory intact rather than copying only the AAR.

## Future Maven Central release

After release signing and publishing are configured, consumers will only need:

```kotlin
dependencies {
    implementation("io.github.vallind:elegant-ui:<version>")
}
```

The coordinate is intended to remain stable when Desktop, iOS, or Web targets are added later.

## Temporary direct AAR use

The workflow also exposes a standalone Android AAR for inspection and emergency testing. Direct AAR use is not the supported long-term integration path because it does not carry complete dependency metadata.
