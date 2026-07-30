# Button 按钮

`ElegantButton` 用于触发即时操作，提供三种强调层级、三种尺寸、完整交互状态、可选图标插槽，并保证至少 48dp 的触控区域。

<ButtonPreview />

## 导入

```kotlin
import com.elegant.compose.ui.button.ElegantButton
import com.elegant.compose.ui.button.ElegantButtonSize
import com.elegant.compose.ui.button.ElegantButtonStyle
```

## 基础用法

```kotlin
ElegantButton(
    onClick = { /* 执行动作 */ },
) {
    Text("继续")
}
```

## 样式

### Primary

用于一个页面或任务中的唯一主要操作。

```kotlin
ElegantButton(
    onClick = { /* 执行动作 */ },
    style = ElegantButtonStyle.Primary,
) {
    Text("继续")
}
```

### Secondary

用于仍需要可见容器的辅助操作。

```kotlin
ElegantButton(
    onClick = { /* 执行动作 */ },
    style = ElegantButtonStyle.Secondary,
) {
    Text("保存草稿")
}
```

### Tertiary

用于低强调或上下文操作。

```kotlin
ElegantButton(
    onClick = { /* 执行动作 */ },
    style = ElegantButtonStyle.Tertiary,
) {
    Text("了解更多")
}
```

## 尺寸

| 尺寸 | 可视高度 | 最小触控高度 | 水平内边距 |
| --- | ---: | ---: | ---: |
| `Small` | 36dp | 48dp | 12dp |
| `Medium` | 40dp | 48dp | 16dp |
| `Large` | 48dp | 48dp | 20dp |

```kotlin
ElegantButton(
    onClick = { /* 执行动作 */ },
    size = ElegantButtonSize.Large,
) {
    Text("创建账号")
}
```

## 状态

支持 Default、Pressed、Focused、Disabled 与 Loading。Disabled 和 Loading 状态会阻止重复触发操作。

```kotlin
ElegantButton(
    onClick = { /* 执行动作 */ },
    loading = true,
) {
    Text("提交中")
}
```

## 图标

前后图标使用内容插槽，而不是额外的样式变体。

```kotlin
ElegantButton(
    onClick = { /* 执行动作 */ },
    leadingIcon = {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = null,
        )
    },
    trailingIcon = {
        Icon(
            imageVector = Icons.AutoMirrored.Default.ArrowForward,
            contentDescription = null,
        )
    },
) {
    Text("创建")
}
```

## 参数

| 参数 | 类型 | 默认值 | 说明 |
| --- | --- | --- | --- |
| `onClick` | `() -> Unit` | 必填 | 接受有效激活时触发 |
| `modifier` | `Modifier` | `Modifier` | 应用于触控区域容器 |
| `style` | `ElegantButtonStyle` | `Primary` | 视觉强调层级 |
| `size` | `ElegantButtonSize` | `Medium` | 可视尺寸与内部度量 |
| `enabled` | `Boolean` | `true` | 是否允许交互 |
| `loading` | `Boolean` | `false` | 显示进度并阻止激活 |
| `leadingIcon` | `(@Composable () -> Unit)?` | `null` | 可选前置内容 |
| `trailingIcon` | `(@Composable () -> Unit)?` | `null` | 可选后置内容 |
| `content` | `@Composable () -> Unit` | 必填 | 按钮主标签或内容 |

## 无障碍

- 即使可视按钮更小，触控区域也至少为 48dp。
- Compose Semantics 暴露 Button Role 与禁用状态。
- Loading 保留按钮语义并提供状态描述。
- 装饰图标使用 `contentDescription = null`。
- 方向图标与 start/end 内边距必须适配 RTL。

## 真机检查项

使用最新 Sample APK 检查视觉层级、按压反馈、焦点可见性、Loading 与 Disabled、字体缩放、横屏、RTL 以及 Light/Dark 对比度。
