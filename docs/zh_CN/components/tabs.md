# Tabs

`ElegantTabRow` 是用于切换互斥视图的受控标签栏。它默认将 `ElegantTab` 模型渲染为等宽标签;开启滚动模式后,标签按自然宽度排列并支持横向滚动,同时通过环绕式方向键导航保持选中项可及。

<iframe id="demoIframe" style="width: 100%; height: 460px; border: 1px solid var(--vp-c-divider); border-radius: 12px; display: block; background: var(--vp-c-bg-alt);" src="../../compose/index.html?id=tabs" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>

## 引入

```kotlin
import com.elegant.compose.ui.tabs.ElegantTab
import com.elegant.compose.ui.tabs.ElegantTabColors
import com.elegant.compose.ui.tabs.ElegantTabDefaults
import com.elegant.compose.ui.tabs.ElegantTabRow
```

## 基本用法

`ElegantTabRow` 是受控组件:将 `selectedIndex` 保存在状态中,并通过 `onSelect` 更新它。默认情况下每个标签等分整行的宽度。

```kotlin
var selected by remember { mutableStateOf(0) }
val tabs = listOf(
    ElegantTab("概览"),
    ElegantTab("项目"),
    ElegantTab("设置"),
)

ElegantTabRow(
    tabs = tabs,
    selectedIndex = selected,
    onSelect = { selected = it },
)
```

### 滚动模式

设置 `scrollable = true` 后,多个标签保持自然宽度并横向滚动。

```kotlin
ElegantTabRow(
    tabs = tabs,
    selectedIndex = selected,
    onSelect = { selected = it },
    scrollable = true,
)
```

## Tab 模型

`ElegantTab` 是驱动标签栏的稳定数据模型。将某个模型的 `enabled` 设为 `false`,可以保持该标签可见但不可交互。

```kotlin
val tabs = listOf(
    ElegantTab("通用"),
    ElegantTab("安全", enabled = false),
    ElegantTab("账单"),
)
```

## 组件状态

标签栏与每个标签共享 disabled 与 selected 语义:每个标签播报 `Role.Tab` 及其 `selected`、`disabled` 状态;整行是单个键盘可聚焦节点,方向键将选中项移动到下一个可用的标签,并在两端环绕。超出标签范围的 `selectedIndex` 会被钳制到最后一个标签,空标签列表不渲染任何内容。

标签文字颜色的状态优先级:disabled、selected、hovered、resting。`hoveredContentColor` 仅在标签未选中时生效。

```kotlin
var selected by remember { mutableStateOf(1) }

ElegantTabRow(
    tabs = listOf(
        ElegantTab("概览"),
        ElegantTab("归档"),
        ElegantTab("共享", enabled = false),
    ),
    selectedIndex = selected,
    onSelect = { selected = it },
)

ElegantTabRow(
    tabs = listOf(ElegantTab("离线")),
    selectedIndex = 0,
    onSelect = {},
    enabled = false,
)
```

## 属性

### ElegantTabRow 属性

| 属性名 | 类型 | 说明 | 默认值 | 是否必须 |
| --- | --- | --- | --- | --- |
| `tabs` | `List<ElegantTab>` | 标签栏渲染的标签模型 | - | 是 |
| `selectedIndex` | `Int` | 选中标签的索引;超出范围的值钳制到最后一个标签 | - | 是 |
| `onSelect` | `(Int) -> Unit` | 携带新选中索引的回调 | - | 是 |
| `modifier` | `Modifier` | 仅应用一次到标签栏根节点的修饰符 | `Modifier` | 否 |
| `enabled` | `Boolean` | 标签栏是否接受用户交互 | `true` | 否 |
| `scrollable` | `Boolean` | 标签是否保持自然宽度并横向滚动 | `false` | 否 |
| `colors` | `ElegantTabColors` | 主题感知的状态颜色 | `ElegantTabDefaults.colors()` | 否 |
| `interactionSource` | `MutableInteractionSource?` | 用于观察标签栏焦点状态的外部交互源 | `null` | 否 |

### ElegantTab 属性

| 属性名 | 类型 | 说明 | 默认值 | 是否必须 |
| --- | --- | --- | --- | --- |
| `text` | `String` | 以 labelMedium 样式渲染的标签文字 | - | 是 |
| `enabled` | `Boolean` | 该标签是否接受用户交互 | `true` | 否 |

### ElegantTabDefaults

| 成员 | 类型 | 说明 |
| --- | --- | --- |
| `MinimumTouchHeight` | `Dp` | 48dp 最小交互根高度 |
| `IndicatorHeight` | `Dp` | 2dp 选中指示条高度 |
| `AnimationDurationMillis` | `Int` | 标准 160ms 标签文字颜色过渡时长 |
| `colors()` | `ElegantTabColors` | Light/Dark 主题感知颜色 |

### ElegantTabColors

`ElegantTabColors` 包含 resting、selected、hovered 与 disabled 状态的容器色、指示条色与文字颜色。应先调用 `ElegantTabDefaults.colors()`,再仅针对产品明确需要的层级使用 `copy(...)`。

## 进阶用法

### 标签栏位于内容区之上

保持标签栏受控,并用同一份状态切换下方的内容。

```kotlin
var selected by remember { mutableStateOf(0) }
val tabs = listOf(ElegantTab("设计"), ElegantTab("工程"), ElegantTab("发布"))

Column {
    ElegantTabRow(
        tabs = tabs,
        selectedIndex = selected,
        onSelect = { selected = it },
    )
    Text(
        text = "正在显示 ${tabs[selected].text}",
        style = ElegantTheme.typography.bodyMedium,
        color = ElegantTheme.colors.textSecondary,
        modifier = Modifier.padding(ElegantSpacing.lg),
    )
}
```

### 自定义强调

```kotlin
val baseColors = ElegantTabDefaults.colors()

ElegantTabRow(
    tabs = listOf(ElegantTab("概览"), ElegantTab("详情")),
    selectedIndex = selected,
    onSelect = { selected = it },
    colors = baseColors.copy(
        selectedContentColor = Color(0xFF147D64),
        indicatorColor = Color(0xFF147D64),
    ),
)
```
