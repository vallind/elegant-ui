# 快速开始

Elegant UI 是面向 Android、Desktop JVM 与 Web/Wasm 的 Compose Multiplatform 组件库。公开组件位于 `commonMain`，三个轻量平台入口共用同一个 `:showcase`。

::: warning 当前状态
Elegant UI 仍处于 `0.x` 开发阶段，首个稳定版前公共 API 可能调整。iOS 不在当前支持范围内。
:::

## 环境要求

- Android 使用方需要 Android 7.0（API 24）或更高版本
- Compose Multiplatform 支持的 64 位桌面环境
- 支持 WasmGC 的现代浏览器
- KMP 与 Desktop 打包使用 JDK 17
- 文档网站使用 Node.js 22 或更高版本

## 添加组件库

KMP 应用：

```kotlin
kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(project(":elegant-ui"))
        }
    }
}
```

独立 Android 应用：

```kotlin
dependencies {
    implementation(project(":elegant-ui"))
}
```

不同仓库请使用 Maven Local 或 Actions 生成的 `elegant-ui-maven-repository`。完整配置见[安装与依赖](./installation)。

## 应用主题

```kotlin
ElegantTheme {
    AppContent()
}
```

## 使用组件

```kotlin
ElegantButton(
    onClick = { /* 执行动作 */ },
    style = ElegantButtonStyle.Primary,
    size = ElegantButtonSize.Medium,
) {
    Text("继续")
}
```

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

脚本会阻止 Android、Desktop-only 和浏览器专用 API 泄漏到通用代码，并检查三个 target 与示例模块持续存在。

## 运行文档网站

先构建真实 Compose Web Demo：

```bash
gradle :web-sample:wasmJsBrowserDistribution
cd docs
npm install
npm run docs:check
npm run docs:dev
```
