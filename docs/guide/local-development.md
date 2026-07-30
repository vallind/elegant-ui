# Local development

Use these workflows when developing Elegant UI together with Android, Desktop JVM, or Web/Wasm applications.

## Composite build

```kotlin
includeBuild("../elegant-ui") {
    dependencySubstitution {
        substitute(module("io.github.vallind:elegant-ui"))
            .using(project(":elegant-ui"))
    }
}
```

The consuming application keeps the published coordinate in its matching source set.

## Maven Local loop

```bash
# Elegant UI
gradle :elegant-ui:publishToMavenLocal

# Android consumer
gradle :app:assembleDebug

# Desktop consumer
gradle :desktopApp:run

# Web/Wasm consumer
gradle :webApp:wasmJsBrowserDevelopmentRun
```

Use `--refresh-dependencies` when a consuming build does not observe a new snapshot.

## Repository samples

```bash
gradle :sample:assembleDebug
gradle :desktop-sample:run
gradle :web-sample:wasmJsBrowserDevelopmentRun
```

The shared UI lives in `:showcase`, so the same component matrix is exercised by all three launchers.
