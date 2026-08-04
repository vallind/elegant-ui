# 最佳实践

使用 Elegant UI 构建完整应用的模式。

## 只封装一次主题

创建唯一应用主题,统一管理种子色与暗色模式:

```kotlin
@Composable
fun AppTheme(content: @Composable () -> Unit) {
    val darkTheme = isSystemInDarkTheme()
    val controller = remember { ElegantThemeController(keyColor = Color(0xFF147D64)) }
    ElegantTheme(
        darkTheme = darkTheme,
        colors = if (darkTheme) controller.darkColors() else controller.lightColors(),
    ) {
        content()
    }
}
```

## 保持受控

`ElegantInput`、`ElegantSwitch`、`ElegantBottomSheet` 等受控组件要求调用方持有状态。将 `value` 保存在 `remember` 状态中,并从回调写回变更:

```kotlin
var checked by remember { mutableStateOf(false) }

ElegantSwitch(
    checked = checked,
    onCheckedChange = { checked = it },
    label = "通知",
)
```

## 自己持有关闭

浮层组件从不自行关闭。所有关闭路径都通过 `onDismissRequest`:

```kotlin
ElegantBottomSheet(
    visible = visible,
    onDismissRequest = { visible = false },
) {
    // 面板内容
}
```

## 优先使用语义令牌

使用 `ElegantTheme.colors.*`、`ElegantTheme.typography.*` 与间距、圆角、动效、阴影令牌对象。原始数值会让亮暗适配不一致。

## 满足触摸与焦点基线

交互根节点高度至少 48dp;键盘焦点在两种主题中都可见。按压动效不得缩小点击目标或移动相邻布局。

## 从 Showcase 学习

本仓库共享的 `:showcase` 组件矩阵展示了每个组件的状态与交互。Android、Desktop 与 Web 示例渲染同一矩阵,因此一处验证处处成立。
