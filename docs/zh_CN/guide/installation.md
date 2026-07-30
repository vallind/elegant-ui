# 安装与依赖

Elegant UI 的组件库模块已经采用 common-first Kotlin Multiplatform 架构，但当前只有 Android 是正式支持的运行目标。Android 应用仍按普通 Gradle 依赖使用。

::: warning 当前分发状态
Maven Central 正式坐标已预留但尚未发布。在签名版本可用前，请使用项目依赖、CI 生成的 Maven 仓库产物或 `publishToMavenLocal`。
:::

## 同一仓库

Android 应用与组件库处于同一个 Gradle 工程时：

```kotlin
// app/build.gradle.kts
dependencies {
    implementation(project(":elegant-ui"))
}
```

仓库内的 `:sample` 应用就是这种方式。

## Maven Local

将完整 KMP 元数据和 Android AAR 发布到本机 Maven 仓库：

```bash
gradle :elegant-ui:publishToMavenLocal
```

在使用方项目中加入 `mavenLocal()`：

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

然后依赖根级 multiplatform 坐标：

```kotlin
// app/build.gradle.kts
dependencies {
    implementation("io.github.vallind:elegant-ui:0.1.0-SNAPSHOT")
}
```

Gradle 会读取 Kotlin Multiplatform 模块元数据，并自动选择 Android 变体。

## GitHub Actions Maven 产物

**Android Build** 工作流会上传 `elegant-ui-maven-repository`。下载后解压到 `third-party/elegant-ui-repo` 等目录，再注册该目录：

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

继续使用同一坐标：

```kotlin
dependencies {
    implementation("io.github.vallind:elegant-ui:0.1.0-SNAPSHOT")
}
```

该产物包含根级 KMP publication、Gradle 模块元数据、POM、源码包和 Android AAR。应保持目录完整，不要只复制 AAR。

## 未来 Maven Central 正式版本

完成签名和正式发布后，使用方只需要：

```kotlin
dependencies {
    implementation("io.github.vallind:elegant-ui:<version>")
}
```

后续增加 Desktop、iOS 或 Web target 时，计划保持该坐标不变。

## 临时直接使用 AAR

工作流仍会提供独立 Android AAR，便于检查和紧急验证。长期集成不推荐直接复制 AAR，因为这种方式缺少完整的依赖与版本元数据。
