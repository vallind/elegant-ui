# 本地联调

以下方式适用于 Elegant UI 与另一个 Android 应用同时开发的场景。

## Composite Build

当应用和组件库位于不同仓库、但希望源码改动立即生效时，可使用 Composite Build。

在使用方项目的 `settings.gradle.kts` 中包含组件库构建，并映射发布坐标：

```kotlin
includeBuild("../elegant-ui") {
    dependencySubstitution {
        substitute(module("io.github.vallind:elegant-ui"))
            .using(project(":elegant-ui"))
    }
}
```

应用仍保留正常依赖声明：

```kotlin
implementation("io.github.vallind:elegant-ui:0.1.0-SNAPSHOT")
```

## Maven Local 循环

```bash
# 在 Elegant UI 仓库
gradle :elegant-ui:publishToMavenLocal

# 在使用方 Android 应用
gradle :app:assembleDebug
```

Snapshot 版本可能被缓存；若使用方没有读取到新发布内容，可增加 `--refresh-dependencies`。

## 真机验证

仓库内的 `:sample` 仍是真机验收的标准入口。业务应用可以验证接入效果，但不能替代 `VALIDATION.md` 中的组件状态矩阵与验收记录。
