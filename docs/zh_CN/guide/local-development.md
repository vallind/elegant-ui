# 本地联调

以下方式适用于 Elegant UI 与 Android、Desktop JVM 或 Web/Wasm 应用同步开发。

## Composite Build

```kotlin
includeBuild("../elegant-ui") {
    dependencySubstitution {
        substitute(module("io.github.vallind:elegant-ui"))
            .using(project(":elegant-ui"))
    }
}
```

使用方在匹配的源码集中继续保留正式发布坐标。

## Maven Local 循环

```bash
# Elegant UI
gradle :elegant-ui:publishToMavenLocal

# Android 使用方
gradle :app:assembleDebug

# Desktop 使用方
gradle :desktopApp:run

# Web/Wasm 使用方
gradle :webApp:wasmJsBrowserDevelopmentRun
```

若使用方没有读取到新 Snapshot，可增加 `--refresh-dependencies`。

## 仓库内示例

```bash
gradle :sample:assembleDebug
gradle :desktop-sample:run
gradle :web-sample:wasmJsBrowserDevelopmentRun
```

三个入口共用 `:showcase`，确保相同组件矩阵在三端运行。
