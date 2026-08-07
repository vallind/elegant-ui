# ToggleButton

`ElegantToggleButton` 是 Elegant UI 中经过细化的跨平台开关组件。它提供可选择的单一状态与复选框风格的语义契约、指针悬停、触控按压、键盘焦点，以及动画化的容器、内容与边框反馈。`ElegantToggleButtonGroup` 将多个开关以零间距拼合成单个圆角簇，同时每个子项保留自己的边框与交互行为。

<iframe id="demoIframe" style="width: 100%; height: 460px; border: 1px solid var(--vp-c-divider); border-radius: 12px; display: block; background: var(--vp-c-bg-alt);" src="../../compose/index.html?id=toggle-button" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>

## 引入

```kotlin
import com.elegant.compose.ui.togglebutton.ElegantToggleButton
import com.elegant.compose.ui.togglebutton.ElegantToggleButtonColors
import com.elegant.compose.ui.togglebutton.ElegantToggleButtonDefaults
import com.elegant.compose.ui.togglebutton.ElegantToggleButtonGroup
```

## 基本用法

开关是受控组件：传入当前状态与负责应用下一个状态的回调。开关保持 48dp 的最小触控区域，内含 36dp 的可视框。

```kotlin
var bold by remember { mutableStateOf(false) }

ElegantToggleButton(
    selected = bold,
    onToggle = { bold = it },
) {
    Text("加粗")
}
```

## 开关按钮组

`ElegantToggleButtonGroup` 以零间距渲染一行拼接开关，并将整簇裁剪为单个小圆角轮廓。组的 `enabled` 与 `colors` 会成为未显式传值子项的默认值。

```kotlin
var viewPeriod by remember { mutableIntStateOf(0) }

ElegantToggleButtonGroup {
    ElegantToggleButton(
        selected = viewPeriod == 0,
        onToggle = { if (it) viewPeriod = 0 },
    ) {
        Text("日")
    }
    ElegantToggleButton(
        selected = viewPeriod == 1,
        onToggle = { if (it) viewPeriod = 1 },
    ) {
        Text("周")
    }
    ElegantToggleButton(
        selected = viewPeriod == 2,
        onToggle = { if (it) viewPeriod = 2 },
    ) {
        Text("月")
    }
}
```

## 组件状态

悬停、按压和键盘焦点反馈会通过共享交互源自动解析。选中状态优先于按压与悬停视觉：选中的开关在按压时仍保持强调容器与内容。主题启用焦点环时,键盘焦点会将边框替换为焦点环，且边框宽度在所有状态下保持恒定。禁用开关回落到低饱和的容器与文字色调，且永远不会触发 `onToggle`。

### 选中状态

选中状态将容器切换为微弱的强调表面，并将内容与边框切换为主要交互色。选中状态会通过合并后的 `Role.Checkbox` 节点以 `On` 或 `Off` 开关状态向无障碍服务播报。

### 禁用状态

```kotlin
ElegantToggleButton(
    selected = false,
    onToggle = { },
    enabled = false,
) {
    Text("禁用开关")
}
```

## 属性

### ElegantToggleButton 属性

| 属性名 | 类型 | 说明 | 默认值 | 是否必须 |
| --- | --- | --- | --- | --- |
| `selected` | `Boolean` | 开关是否处于开启状态 | - | 是 |
| `onToggle` | `(Boolean) -> Unit` | 开关接受激活时、携带待应用状态触发的回调 | - | 是 |
| `modifier` | `Modifier` | 应用于最小 48dp 触控区域容器的修饰符 | `Modifier` | 否 |
| `enabled` | `Boolean` | 开关是否可以接受用户交互 | `true` | 否 |
| `colors` | `ElegantToggleButtonColors` | 主题感知的默认、选中、悬停、按压、聚焦和禁用颜色 | `ElegantToggleButtonDefaults.colors()` | 否 |
| `content` | `@Composable () -> Unit` | 显示为开关标签的可组合内容 | - | 是 |

### ElegantToggleButtonGroup 属性

| 属性名 | 类型 | 说明 | 默认值 | 是否必须 |
| --- | --- | --- | --- | --- |
| `modifier` | `Modifier` | 应用于拼接簇的修饰符 | `Modifier` | 否 |
| `enabled` | `Boolean` | 子项是否可以接受用户交互 | `true` | 否 |
| `colors` | `ElegantToggleButtonColors` | 未显式传值的子项所继承的颜色 | `ElegantToggleButtonDefaults.colors()` | 否 |
| `content` | `@Composable () -> Unit` | 以拼接开关形式渲染的可组合内容 | - | 是 |

### ElegantToggleButtonDefaults

| 成员 | 类型 | 说明 |
| --- | --- | --- |
| `MinimumTouchHeight` | `Dp` | 最小交互根高度 |
| `Height` | `Dp` | 开关框的可视高度 |
| `HorizontalPadding` | `Dp` | 可视框内的水平内边距 |
| `AnimationDurationMillis` | `Int` | 标准状态过渡时长 |
| `colors()` | `ElegantToggleButtonColors` | 返回主题感知颜色，或在组内返回所在组的颜色 |
| `shape()` | `Shape` | 返回小圆角默认形状 |

### ElegantToggleButtonColors

`ElegantToggleButtonColors` 包含默认、选中、悬停、按压、禁用和聚焦状态下的容器色、内容色与边框色，以及恒定边框宽度。未显式设置的悬停、按压与禁用值会回落到对应的静止值。应先调用 `ElegantToggleButtonDefaults.colors()`，再通过 `copy(...)` 只覆盖产品明确支持的视觉值。

## 进阶用法

### 带图标开关

```kotlin
var starred by remember { mutableStateOf(false) }

ElegantToggleButton(
    selected = starred,
    onToggle = { starred = it },
) {
    Icon(
        imageVector = Icons.AutoMirrored.Default.Star,
        contentDescription = null,
    )
}
```

### 过滤器栏与组

禁用组会在底层数据加载期间让每个子开关保持不可交互。

```kotlin
var filter by remember { mutableIntStateOf(0) }
var loading by remember { mutableStateOf(false) }

ElegantToggleButtonGroup(enabled = !loading) {
    ElegantToggleButton(selected = filter == 0, onToggle = { if (it) filter = 0 }) { Text("全部") }
    ElegantToggleButton(selected = filter == 1, onToggle = { if (it) filter = 1 }) { Text("收藏") }
    ElegantToggleButton(selected = filter == 2, onToggle = { if (it) filter = 2 }) { Text("已归档") }
}
```

### 自定义颜色

```kotlin
val baseColors = ElegantToggleButtonDefaults.colors()

ElegantToggleButton(
    selected = false,
    onToggle = { },
    colors = baseColors.copy(
        selectedContainerColor = Color(0xFF0F766E),
        selectedContentColor = Color(0xFFFFFFFF),
    ),
) {
    Text("自定义选中")
}
```
