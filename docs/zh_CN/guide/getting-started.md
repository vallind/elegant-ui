# 快速开始

当前支持的平台: **Android** / **Desktop(JVM)** / **iOS** / **WasmJs** / **Js** / **macOS(Native)**

::: warning 注意
此库处于实验阶段，API 可能会在未来版本中变更而不另行通知
:::

## 添加依赖

Elyon 尚未发布到 Maven Central。如需使用，请通过复合构建（composite build）引入本仓库：

```kotlin
// settings.gradle.kts
includeBuild("../elyon")
```

```kotlin
// build.gradle.kts
dependencies {
    implementation(project(":elyon-ui"))
    // 可选：添加 elyon-blur 以获取模糊效果
    implementation(project(":elyon-blur"))
    // 可选：添加 elyon-nav 以获取导航支持
    implementation(project(":elyon-nav"))
}
```

Elyon 由多个可独立使用的模块组成：

| 模块 | 说明 |
|---|---|
| `elyon-core` | 工具 + 基础与扩展图标 |
| `elyon-effects` | 运行时着色器 + 平滑圆角形状 |
| `elyon-blur` | 模糊效果库，可独立使用 |
| `elyon-ui` | 核心 UI + Preference 组件（自动包含 core/effects） |
| `elyon-nav` | 自包含导航库，可独立使用 |

## 基本用法

### 应用 Elyon 主题

```kotlin
@Composable
fun AppTheme(
    content: @Composable () -> Unit
) {
    // 可用模式: System, Light, Dark, MonetSystem, MonetLight, MonetDark
    val controller = remember { ThemeController(ColorSchemeMode.System) }
    return ElyonTheme(
        controller = controller,
        content = content
    )
}
```

### 使用 Elyon 脚手架

```kotlin
Scaffold(
    topBar = {
        // TopBar
    },
    bottomBar = {
        // BottomBar
    },
    floatingActionButton = {
        // FloatingActionButton
    },
    floatingToolbar = {
        // FloatingToolbar
    }
) {
    // Content...
}
```

::: warning 注意
Scaffold 组件为跨平台提供了一个合适的弹出窗口的容器。`OverlayDialog`、`OverlayDropdownPreference`、`OverlaySpinnerPreference`、
`OverlayListPopup` 等组件都基于此实现弹出窗口，因此都需要被该组件包裹。
:::

## API 文档

- 查看 [API 文档](/elegant-ui/dokka/index.html){target="_blank"}，此文档使用 Dokka 生成，包含了所有 API
  的详细信息。
