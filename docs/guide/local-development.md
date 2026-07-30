# Local development

Use these workflows when developing Elegant UI together with another Android application.

## Composite build

A composite build is useful when the application and library live in separate repositories but should resolve source changes immediately.

In the consuming project's `settings.gradle.kts`, include the library build and map the published coordinate:

```kotlin
includeBuild("../elegant-ui") {
    dependencySubstitution {
        substitute(module("io.github.vallind:elegant-ui"))
            .using(project(":elegant-ui"))
    }
}
```

The app can keep its normal dependency declaration:

```kotlin
implementation("io.github.vallind:elegant-ui:0.1.0-SNAPSHOT")
```

## Maven Local loop

```bash
# In Elegant UI
gradle :elegant-ui:publishToMavenLocal

# In the consuming Android application
gradle :app:assembleDebug
```

Because snapshot versions may be cached, use `--refresh-dependencies` when the consuming build does not observe a newly published local snapshot.

## Physical-device verification

The repository `:sample` app remains the canonical acceptance surface. A consuming application can verify integration, but it does not replace the component state matrix and checks recorded in `VALIDATION.md`.
