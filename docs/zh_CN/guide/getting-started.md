# 快速开始

Elegant UI 是面向 Kotlin 与 Jetpack Compose 的 Android 组件库。当前仓库包含可复用的 `:elegant-ui` 模块和可安装的 `:sample` 应用。

::: warning 当前状态
Elegant UI 正在持续开发。在首个稳定版本发布前，公共 API 仍可能调整。
:::

## 环境要求

- Android 7.0（API 24）或更高版本
- Kotlin 与 Jetpack Compose
- 当前 Android 构建使用 JDK 17
- 文档网站使用 Node.js 22 或更高版本

## 添加组件库

本地开发阶段直接依赖模块：

```kotlin
implementation(project(":elegant-ui"))
```

发布 Maven 坐标后会在此补充正式依赖方式。

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

## 运行 Sample

```bash
gradle :sample:installDebug
```

GitHub Actions 的 **Android Build** 工作流也会上传可安装 APK。

## 运行文档网站

```bash
cd docs
npm install
npm run docs:dev
```

构建静态网站：

```bash
npm run docs:build
```
