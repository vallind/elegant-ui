# 快速开始

支持平台:**Android 7.0（API 24）及以上** / **桌面端（JVM）** / **Web（Wasm + JS）**

::: warning 当前状态
Elegant UI 仍处于 `0.x` 开发阶段，首个稳定版前公共 API 可能调整。iOS 不在当前支持范围内。
:::

## 环境要求

- Android 使用方需要 Android 7.0（API 24）或更高版本
- Compose Multiplatform 支持的 64 位桌面环境
- 支持 WasmGC 的现代浏览器
- KMP 与 Desktop 打包使用 JDK 17
- 文档网站使用 Node.js 22 或更高版本

## 添加依赖

Elegant UI 通过单一 KMP 坐标 `io.github.vallind:elegant-ui` 发布，Gradle 依据 KMP 元数据解析 Android、Desktop JVM 与 Web 变体。

### KMP 应用

```kotlin
kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation("io.github.vallind:elegant-ui:0.1.0-SNAPSHOT")
        }
    }
}
```

### Android 应用

```kotlin
dependencies {
    implementation("io.github.vallind:elegant-ui:0.1.0-SNAPSHOT")
}
```

本仓库内请改用项目依赖：

```kotlin
implementation(project(":elegant-ui"))
```

快照坐标在 `gradle :elegant-ui:publishToMavenLocal` 后可从 `mavenLocal()` 使用，或使用 **Multiplatform Build** 工作流产出的 `elegant-ui-maven-repository` 制品。完整配置见[安装与依赖](./installation)。

## 基本用法

### 应用 Elegant 主题

```kotlin
@Composable
fun AppTheme(content: @Composable () -> Unit) {
    val controller = remember { ElegantThemeController(keyColor = Color(0xFF147D64)) }
    ElegantTheme(
        darkTheme = isSystemInDarkTheme(),
        colors = if (isSystemInDarkTheme()) controller.darkColors() else controller.lightColors(),
    ) {
        content()
    }
}
```

默认主题跟随系统外观，并使用种子色推导色板。`ElegantThemeController` 通过纯 Kotlin 确定性推导，从一个种子色生成完整 `ElegantColors` 色板。

### 使用组件

```kotlin
ElegantButton(
    onClick = { /* 执行动作 */ },
    style = ElegantButtonStyle.Primary,
    size = ElegantButtonSize.Medium,
) {
    Text("继续")
}
```

## 弹层宿主

`ElegantModal`、`ElegantBottomSheet`、`ElegantMenu` 等浮层组件渲染在自己的对话框窗口中，并自行捕获与恢复焦点，无需宿主 Scaffold。

## 运行三端示例

```bash
gradle :sample:installDebug
gradle :desktop-sample:run
gradle :web-sample:wasmJsBrowserDevelopmentRun
```

三个入口均使用共享 `:showcase` 组件矩阵。

## 检查源码边界

```bash
./scripts/validate-kmp-boundaries.sh
```

脚本会阻止 Android、Desktop-only 和浏览器专用 API 泄漏到通用代码，并检查全部 target 与示例模块持续存在。

## 运行文档网站

先构建真实 Compose Web Demo：

```bash
gradle :web-sample:wasmJsBrowserDistribution
cd docs
npm install
npm run docs:check
npm run docs:dev
```
