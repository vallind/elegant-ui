# Switch Group

`ElegantSwitchGroup` 是相关 Switch 行的纵向列表,共享一个由调用方持有的选择集合,并提供可选的支持性说明文字。它适用于设置区块与开关型偏好设置:此时多个相关选项共享同一个受控状态。

<iframe id="demoIframe" style="width: 100%; height: 460px; border: 1px solid var(--vp-c-divider); border-radius: 12px; display: block; background: var(--vp-c-bg-alt);" src="../../compose/index.html?id=switch-group" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>

## 引入

```kotlin
import com.elegant.compose.ui.switchgroup.ElegantSwitchGroup
import com.elegant.compose.ui.switchgroup.ElegantSwitchGroupColors
import com.elegant.compose.ui.switchgroup.ElegantSwitchGroupDefaults
import com.elegant.compose.ui.switchgroup.ElegantSwitchGroupItem
```

## 基本用法

Group 完全受控:`selectedValues` 持有当前选择,`onToggle` 以请求的状态报告条目值。调用方持有该 `Set`,切换时应复制集合——推荐使用不可变 `Set`。

```kotlin
var channels by remember { mutableStateOf(setOf("push")) }

ElegantSwitchGroup(
    selectedValues = channels,
    onToggle = { value, checked ->
        channels = if (checked) channels + value else channels - value
    },
    items = listOf(
        ElegantSwitchGroupItem(text = "Push notifications", value = "push"),
        ElegantSwitchGroupItem(text = "Email digest", value = "email"),
        ElegantSwitchGroupItem(text = "In-app mentions", value = "mentions"),
    ),
)
```

## 组件类型

### ElegantSwitchGroupItem

每个条目将可见的 `text` 与稳定标识 `value` 配对,`value` 用于匹配 `selectedValues`。`enabled = false` 保持行可见,但仅禁用该行。

## 组件状态

仅当 Group 的 `enabled` 与条目自身的 `enabled` 都允许交互时,该行才可交互;禁用行不会调用 `onToggle`。每一行都通过 `ElegantSwitch` 保留 48dp 最小交互高度与 `Role.Switch` 语义,标签位于行首、Switch 位于行末,Group 在此基础上增加 4dp 纵向节奏与可选的支持性说明文字。

```kotlin
ElegantSwitchGroup(
    selectedValues = setOf("camera"),
    onToggle = { _, _ -> },
    items = listOf(
        ElegantSwitchGroupItem(text = "Camera", value = "camera"),
        ElegantSwitchGroupItem(text = "Microphone", value = "microphone", enabled = false),
    ),
)

ElegantSwitchGroup(
    selectedValues = setOf("notifications"),
    onToggle = { _, _ -> },
    enabled = false,
    items = listOf(
        ElegantSwitchGroupItem(text = "Notifications", value = "notifications"),
        ElegantSwitchGroupItem(text = "Announcements", value = "announcements"),
    ),
)
```

## 属性

### ElegantSwitchGroup 属性

| 属性名 | 类型 | 说明 | 默认值 | 是否必须 |
| --- | --- | --- | --- | --- |
| `selectedValues` | `Set<String>` | 当前选中的值,由调用方持有 | - | 是 |
| `onToggle` | `(String, Boolean) -> Unit` | 以条目值和请求的选择状态调用的回调 | - | 是 |
| `items` | `List<ElegantSwitchGroupItem>` | 以 Switch 行渲染的条目模型 | - | 是 |
| `modifier` | `Modifier` | 仅应用一次到 Group 根节点的修饰符 | `Modifier` | 否 |
| `enabled` | `Boolean` | 整个 Group 是否接受用户交互 | `true` | 否 |
| `colors` | `ElegantSwitchGroupColors` | 主题感知的文本颜色 | `ElegantSwitchGroupDefaults.colors()` | 否 |
| `supportingText` | `String?` | 行下方渲染的可选支持性说明文字 | `null` | 否 |

### ElegantSwitchGroupItem 属性

| 属性名 | 类型 | 说明 | 默认值 | 是否必须 |
| --- | --- | --- | --- | --- |
| `text` | `String` | Switch 行上渲染的标签 | - | 是 |
| `value` | `String` | 与 `selectedValues` 匹配的稳定标识 | - | 是 |
| `enabled` | `Boolean` | 该条目是否接受用户交互 | `true` | 否 |

### ElegantSwitchGroupDefaults

| 成员 | 类型 | 说明 |
| --- | --- | --- |
| `ItemGap` | `Dp` | 行与行之间的 4dp 纵向间距 |
| `colors()` | `ElegantSwitchGroupColors` | 主题感知的 Light/Dark 文本颜色 |

### ElegantSwitchGroupColors

`ElegantSwitchGroupColors` 承载行标签配色(`labelColor`、`disabledLabelColor`)与支持性说明文字颜色(`supportingTextColor`)。应先调用 `ElegantSwitchGroupDefaults.colors()`,再仅针对产品明确需要的层级使用 `copy(...)`。

## 进阶用法

### 带支持性文字的设置区块

```kotlin
var channels by remember { mutableStateOf(setOf("push")) }

ElegantSwitchGroup(
    selectedValues = channels,
    onToggle = { value, checked ->
        channels = if (checked) channels + value else channels - value
    },
    items = listOf(
        ElegantSwitchGroupItem(text = "Push notifications", value = "push"),
        ElegantSwitchGroupItem(text = "Email digest", value = "email"),
        ElegantSwitchGroupItem(text = "In-app mentions", value = "mentions"),
    ),
    supportingText = "Choose how you want to be notified.",
)
```

### 自定义颜色

```kotlin
val baseColors = ElegantSwitchGroupDefaults.colors()

ElegantSwitchGroup(
    selectedValues = selectedValues,
    onToggle = onToggle,
    items = items,
    colors = baseColors.copy(
        supportingTextColor = Color(0xFF6E727A),
    ),
    supportingText = "Custom caption",
)
```
