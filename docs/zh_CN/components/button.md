# Button

`ElegantButton` 是 Elegant UI 中经过细化的跨平台操作组件。它提供主要、次要和三级强调层级、三种光学调校尺寸、指针悬停、触控按压、键盘焦点、可选图标插槽以及宽度稳定的加载状态。

<iframe id="demoIframe" style="width: 100%; height: 460px; border: 1px solid var(--vp-c-divider); border-radius: 12px; display: block; background: var(--vp-c-bg-alt);" src="../../compose/index.html?id=button" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>

## 引入

```kotlin
import com.elegant.compose.ui.button.ElegantButton
import com.elegant.compose.ui.button.ElegantButtonColors
import com.elegant.compose.ui.button.ElegantButtonDefaults
import com.elegant.compose.ui.button.ElegantButtonElevation
import com.elegant.compose.ui.button.ElegantButtonSize
import com.elegant.compose.ui.button.ElegantButtonStyle
```

## 基本用法

Button 组件可以用于触发操作：

```kotlin
ElegantButton(
    onClick = { /* 处理点击事件 */ },
) {
    Text("按钮")
}
```

## 按钮类型

Elegant UI 提供了适用于不同强调层级的按钮类型。

### 主要按钮（Primary Button）

主要按钮用于一个任务或页面中的最高强调操作。

```kotlin
ElegantButton(
    onClick = { /* 处理点击事件 */ },
    style = ElegantButtonStyle.Primary,
) {
    Text("主要按钮")
}
```

### 次要按钮（Secondary Button）

次要按钮用于仍需要可见容器的辅助操作。

```kotlin
ElegantButton(
    onClick = { /* 处理点击事件 */ },
    style = ElegantButtonStyle.Secondary,
) {
    Text("次要按钮")
}
```

### 三级按钮（Tertiary Button）

三级按钮用于低强调或上下文操作。

```kotlin
ElegantButton(
    onClick = { /* 处理点击事件 */ },
    style = ElegantButtonStyle.Tertiary,
) {
    Text("三级按钮")
}
```

## 组件状态

悬停、按压和键盘焦点反馈会通过共享交互源自动解析。主要按钮在悬停时轻微抬升、按压时自然回落，主题启用焦点环时,所有类型都保留清晰的焦点环。

### 禁用状态

```kotlin
ElegantButton(
    onClick = { /* 处理点击事件 */ },
    enabled = false,
) {
    Text("禁用按钮")
}
```

### 加载状态

加载时会保留标签与图标的原始测量尺寸，在中央覆盖进度指示器、阻止重复触发，并提供可自定义的无障碍状态说明。

## 属性

### ElegantButton 属性

| 属性名 | 类型 | 说明 | 默认值 | 是否必须 |
| --- | --- | --- | --- | --- |
| `onClick` | `() -> Unit` | 按钮接受点击时触发的回调 | - | 是 |
| `modifier` | `Modifier` | 应用于最小 48dp 触控区域容器的修饰符 | `Modifier` | 否 |
| `enabled` | `Boolean` | 按钮是否可以接受用户交互 | `true` | 否 |
| `loading` | `Boolean` | 显示进度并阻止重复触发 | `false` | 否 |
| `loadingStateDescription` | `String` | 加载时播报的本地化无障碍状态 | `"Loading"` | 否 |
| `interactionSource` | `MutableInteractionSource?` | 用于观察悬停、按压和焦点的可选提升交互源 | `null` | 否 |
| `style` | `ElegantButtonStyle` | 按钮的视觉强调类型 | `ElegantButtonStyle.Primary` | 否 |
| `size` | `ElegantButtonSize` | 可视尺寸与内部度量 | `ElegantButtonSize.Medium` | 否 |
| `shape` | `Shape` | 经过光学调校的容器形状 | `ElegantButtonDefaults.shape(size)` | 否 |
| `colors` | `ElegantButtonColors` | 主题感知的默认、悬停、按压、聚焦和禁用颜色 | `ElegantButtonDefaults.colors(style)` | 否 |
| `elevation` | `ElegantButtonElevation` | 感知交互状态的层级模型 | `ElegantButtonDefaults.elevation(style)` | 否 |
| `leadingIcon` | `(@Composable () -> Unit)?` | 标签前的可选图标或内容 | `null` | 否 |
| `trailingIcon` | `(@Composable () -> Unit)?` | 标签后的可选图标或内容 | `null` | 否 |
| `content` | `@Composable () -> Unit` | 显示为按钮标签的可组合内容 | - | 是 |

### ElegantButtonStyle 可选值

| 值 | 说明 |
| --- | --- |
| `Primary` | 使用主要交互容器的最高强调操作 |
| `Secondary` | 使用抬升容器与可见边框的辅助操作 |
| `Tertiary` | 默认使用透明容器的低强调操作 |

### ElegantButtonSize 可选值

| 值 | 可视高度 | 最小触控高度 | 水平内边距 | 图标尺寸 |
| --- | --- | --- | --- | --- |
| `Small` | `40.dp` | `48.dp` | `12.dp` | `16.dp` |
| `Medium` | `40.dp` | `48.dp` | `16.dp` | `18.dp` |
| `Large` | `48.dp` | `48.dp` | `20.dp` | `20.dp` |

### ElegantButtonDefaults

| 成员 | 类型 | 说明 |
| --- | --- | --- |
| `MinimumTouchHeight` | `Dp` | 所有尺寸统一使用的最小交互根高度 |
| `AnimationDurationMillis` | `Int` | 标准状态过渡时长 |
| `PressAnimationDurationMillis` | `Int` | 即时按压反馈时长 |
| `HoveredScale` | `Float` | 克制的指针悬停缩放比例 |
| `PressedScale` | `Float` | 克制的按压缩放比例 |
| `colors(style)` | `ElegantButtonColors` | 返回指定按钮类型的主题感知颜色 |
| `shape(size)` | `Shape` | 返回指定尺寸的光学调校形状 |
| `elevation(style)` | `ElegantButtonElevation` | 返回指定类型的交互层级模型 |

### ElegantButtonColors

`ElegantButtonColors` 包含默认、悬停、按压、聚焦和禁用状态下的容器色、内容色、边框色与边框宽度。应先调用 `ElegantButtonDefaults.colors(style)`，再通过 `copy(...)` 只覆盖产品明确支持的视觉值。

### ElegantButtonElevation

`ElegantButtonElevation` 集中管理默认、悬停、按压、聚焦和禁用层级。主要操作具有轻微静止阴影，悬停或聚焦时抬升，按压时回落；次要和三级操作保持更克制的层级。

## 进阶用法

### 带图标按钮

```kotlin
ElegantButton(
    onClick = { /* 处理点击事件 */ },
    leadingIcon = {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = null,
        )
    },
) {
    Text("创建")
}
```

### 带前后图标按钮

```kotlin
ElegantButton(
    onClick = { /* 处理点击事件 */ },
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
    Text("继续")
}
```

### 自定义颜色

```kotlin
val baseColors = ElegantButtonDefaults.colors(ElegantButtonStyle.Primary)

ElegantButton(
    onClick = { /* 处理点击事件 */ },
    colors = baseColors.copy(
        containerColor = Color(0xFF0F766E),
        pressedContainerColor = Color(0xFF115E59),
    ),
) {
    Text("自定义操作")
}
```

### 加载状态按钮

```kotlin
var isLoading by remember { mutableStateOf(false) }
val scope = rememberCoroutineScope()

ElegantButton(
    onClick = {
        isLoading = true
        scope.launch {
            delay(2000)
            isLoading = false
        }
    },
    loading = isLoading,
    loadingStateDescription = "提交中",
) {
    Text("提交")
}
```
