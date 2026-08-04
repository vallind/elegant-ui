# 主题

Elegant UI 的主题从一个种子色开始。`ElegantTheme` 组合 `ElegantColors`、排版与设计令牌,`ElegantThemeController` 通过纯 Kotlin 确定性推导,从单一主色生成完整的亮色与暗色色板。

## 主题组合

```kotlin
ElegantTheme(
    darkTheme = true,
) {
    // 应用内容
}
```

不带参数时,主题跟随系统外观并使用内置默认色板。

## 种子色推导色板

`ElegantThemeController` 从一个种子色推导两套色板;推导过程不接触平台颜色 API,因此在 Android、Desktop JVM 与 Web 上的结果完全一致。

```kotlin
val controller = remember { ElegantThemeController(keyColor = Color(0xFF147D64)) }

ElegantTheme(
    darkTheme = true,
    colors = controller.darkColors(),
) {
    // 绿色系暗色色板
}
```

## 跟随系统外观

```kotlin
val darkTheme = isSystemInDarkTheme()
val controller = remember { ElegantThemeController(keyColor = Color(0xFFB45309)) }

ElegantTheme(
    darkTheme = darkTheme,
    colors = if (darkTheme) controller.darkColors() else controller.lightColors(),
) {
    // 色板随系统切换
}
```

## 自定义颜色

任意组件都可以通过其 defaults 对象覆盖颜色:

```kotlin
val buttonColors = ElegantButtonDefaults.colors(
    style = ElegantButtonStyle.Primary,
).copy(
    containerColor = Color(0xFF147D64),
)

ElegantButton(
    onClick = { },
    colors = buttonColors,
) {
    Text("自定义")
}
```

## 设计令牌

组件通过 `ElegantSpacing`、`ElegantRadius`、`ElegantMotion` 与 `ElegantElevation` 解析间距、圆角、动效与阴影。请消费令牌而不是原始数值:

```kotlin
Modifier.padding(ElegantSpacing.lg)
RoundedCornerShape(ElegantRadius.md)
tween(durationMillis = ElegantMotion.standardDurationMillis)
```
