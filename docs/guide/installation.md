# Installation

Elegant UI publishes Android, Desktop JVM, and Web/Wasm variants from one Kotlin Multiplatform coordinate.

::: warning Current distribution status
The Maven Central coordinate is reserved but not released yet. Use a project dependency, `publishToMavenLocal`, or the Maven repository artifact produced by **Multiplatform Build**.
:::

## Same repository

Use the project dependency from a matching KMP source set:

```kotlin
kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(project(":elegant-ui"))
        }
    }
}
```

A standalone Android application can use:

```kotlin
dependencies {
    implementation(project(":elegant-ui"))
}
```

## Maven Local

Publish every supported variant:

```bash
gradle :elegant-ui:publishToMavenLocal
```

Register `mavenLocal()` and depend on the root multiplatform coordinate:

```kotlin
kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation("io.github.vallind:elegant-ui:0.1.0-SNAPSHOT")
        }
    }
}
```

For a standalone Android app:

```kotlin
dependencies {
    implementation("io.github.vallind:elegant-ui:0.1.0-SNAPSHOT")
}
```

Gradle selects the Android, Desktop JVM, or Web/Wasm variant from Kotlin Multiplatform metadata.

## GitHub Actions Maven artifact

The **Multiplatform Build** workflow uploads `elegant-ui-maven-repository`. Extract it, register the directory as a Maven repository, and use the same coordinate. Keep the repository intact because it contains root metadata plus Android AAR, Desktop JVM, and Web/Wasm publications.

## Future Maven Central release

After release signing is configured:

```kotlin
commonMain.dependencies {
    implementation("io.github.vallind:elegant-ui:<version>")
}
```

## Platform sample artifacts

The same workflow also uploads:

- `elegant-ui-android-sample`
- `elegant-ui-desktop-sample-linux`
- `elegant-ui-web-sample`
- `elegant-ui-android-aar`

The Maven repository is the supported integration handoff; direct platform files are validation artifacts.
