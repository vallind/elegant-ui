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

`ElegantThemeController` 从一个种子色推导两套色板;`Monet*` 模式使用 Material 3 动态色彩算法(HCT),种子色构造函数则保留原有 HSL 推导。将控制器传给 `ElegantTheme` 并修改它即可重组主题:

```kotlin
val controller = remember { ElegantThemeController(keyColor = Color(0xFF147D64)) }

ElegantTheme(
    controller = controller,
) {
    // 绿色系色板跟随系统外观
}
```

## 跟随系统外观

```kotlin
val controller = remember {
    ElegantThemeController(
        colorSchemeMode = ElegantColorSchemeMode.MonetSystem,
        keyColor = Color(0xFFB45309),
    )
}

ElegantTheme(controller = controller) {
    // 色板从种子派生并随系统切换
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

## 焦点环

键盘焦点环通过主题开启。向 `ElegantTheme` 传入 `focusRingEnabled = true` 后,交互组件在持有键盘焦点时会绘制焦点边框;默认(`false`)下,聚焦状态仍通过语义与覆盖层指示播报,聚焦视觉回落到 hovered 或 resting 颜色。

```kotlin
ElegantTheme(
    focusRingEnabled = true,
) {
    // 键盘聚焦时,交互组件会显示焦点边框
}
```
