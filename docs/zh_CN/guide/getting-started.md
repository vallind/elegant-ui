# 快速开始

Elegant UI 是采用 common-first Kotlin Multiplatform 架构的 Compose 组件库，Android 是首个且当前唯一正式支持的运行目标。仓库包含可复用的 `:elegant-ui` 模块和可安装的 Android `:sample` 应用。

::: warning 当前状态
Elegant UI 仍处于 `0.x` 开发阶段，首个稳定版前公共 API 可能调整。代码已进入 `commonMain`，但 Desktop、iOS 和 Web 目前仍未正式支持。
:::

## 环境要求

- Android 7.0（API 24）或更高版本
- Kotlin 与 Compose
- Android/KMP 构建使用 JDK 17
- 文档网站使用 Node.js 22 或更高版本

## 添加组件库

同一个 Gradle 工程内直接依赖模块：

```kotlin
dependencies {
    implementation(project(":elegant-ui"))
}
```

不同仓库请使用 Maven Local 或 GitHub Actions 生成的 Maven 仓库产物。完整配置见[安装与依赖](./installation)。

## 应用主题

```kotlin
ElegantTheme {
    AppContent()
}
```

视觉值应来自 `ElegantTheme`、`ElegantSpacing` 与 `ElegantRadius` 等语义 Token，避免在业务代码中散落硬编码颜色和尺寸。

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

## 运行 Android Sample

```bash
gradle :sample:installDebug
```

**Android Build** 工作流也会上传可安装 APK、完整 Maven 仓库和独立 Android AAR。

## 检查 KMP 边界

```bash
./scripts/validate-kmp-boundaries.sh
```

该脚本会阻止 Android-only import 进入 `commonMain`，并阻止组件库恢复旧的 `src/main` 目录结构。

## 运行文档网站

```bash
cd docs
npm install
npm run docs:check
npm run docs:dev
```

构建静态网站：

```bash
npm run docs:build
```
