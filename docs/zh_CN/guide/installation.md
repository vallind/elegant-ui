# 安装与依赖

Elegant UI 通过一个 Kotlin Multiplatform 坐标发布 Android、Desktop JVM 与 Web/Wasm 变体。

::: warning 当前分发状态
Maven Central 坐标已预留但尚未正式发布。当前使用项目依赖、`publishToMavenLocal`，或 **Multiplatform Build** 生成的 Maven 仓库产物。
:::

## 同一仓库

KMP 应用在匹配的源码集中依赖：

```kotlin
kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(project(":elegant-ui"))
        }
    }
}
```

独立 Android 应用可以使用：

```kotlin
dependencies {
    implementation(project(":elegant-ui"))
}
```

## Maven Local

发布全部正式目标变体：

```bash
gradle :elegant-ui:publishToMavenLocal
```

注册 `mavenLocal()`，然后依赖根级 multiplatform 坐标：

```kotlin
kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation("io.github.vallind:elegant-ui:0.1.0-SNAPSHOT")
        }
    }
}
```

独立 Android 应用使用：

```kotlin
dependencies {
    implementation("io.github.vallind:elegant-ui:0.1.0-SNAPSHOT")
}
```

Gradle 会根据 Kotlin Multiplatform 元数据自动选择 Android、Desktop JVM 或 Web/Wasm 变体。

## GitHub Actions Maven 产物

**Multiplatform Build** 会上传 `elegant-ui-maven-repository`。解压并注册该目录后继续使用同一坐标。必须保持仓库目录完整，其中包含根级元数据以及 Android AAR、Desktop JVM 和 Web/Wasm publication。

## 未来 Maven Central 正式版本

完成签名发布后：

```kotlin
commonMain.dependencies {
    implementation("io.github.vallind:elegant-ui:<version>")
}
```

## 平台示例产物

同一工作流还会上传：

- `elegant-ui-android-sample`
- `elegant-ui-desktop-sample-linux`
- `elegant-ui-web-sample`
- `elegant-ui-android-aar`

正式接入应使用 Maven 仓库；独立平台文件主要用于验收。
