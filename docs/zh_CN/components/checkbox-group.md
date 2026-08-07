# Checkbox Group

`ElegantCheckboxGroup` 是相关 Checkbox 行的纵向列表,共享一个由调用方持有的选择集合,并提供可选的支持性说明文字。它适用于权限选择器、偏好设置区块与多选过滤器:此时多个相关选项共享同一个受控状态。

<iframe id="demoIframe" style="width: 100%; height: 460px; border: 1px solid var(--vp-c-divider); border-radius: 12px; display: block; background: var(--vp-c-bg-alt);" src="../../compose/index.html?id=checkbox-group" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>

## 引入

```kotlin
import com.elegant.compose.ui.checkboxgroup.ElegantCheckboxGroup
import com.elegant.compose.ui.checkboxgroup.ElegantCheckboxGroupColors
import com.elegant.compose.ui.checkboxgroup.ElegantCheckboxGroupDefaults
import com.elegant.compose.ui.checkboxgroup.ElegantCheckboxGroupItem
```

## 基本用法

Group 完全受控:`selectedValues` 持有当前选择,`onToggle` 以请求的状态报告条目值。调用方持有该 `Set`,切换时应复制集合——推荐使用不可变 `Set`。

```kotlin
var channels by remember { mutableStateOf(setOf("stable")) }

ElegantCheckboxGroup(
    selectedValues = channels,
    onToggle = { value, checked ->
        channels = if (checked) channels + value else channels - value
    },
    items = listOf(
        ElegantCheckboxGroupItem(text = "Stable channel", value = "stable"),
        ElegantCheckboxGroupItem(text = "Beta channel", value = "beta"),
        ElegantCheckboxGroupItem(text = "Nightly", value = "nightly"),
    ),
)
```

## 组件类型

### ElegantCheckboxGroupItem

每个条目将可见的 `text` 与稳定标识 `value` 配对,`value` 用于匹配 `selectedValues`。`enabled = false` 保持行可见,但仅禁用该行。

## 组件状态

仅当 Group 的 `enabled` 与条目自身的 `enabled` 都允许交互时,该行才可交互;禁用行不会调用 `onToggle`。每一行都通过 `ElegantCheckbox` 保留自己的 `Role.Checkbox` 语义、48dp 最小交互高度与动画对勾,Group 在此基础上增加 4dp 纵向节奏与可选的支持性说明文字。

```kotlin
ElegantCheckboxGroup(
    selectedValues = setOf("camera"),
    onToggle = { _, _ -> },
    items = listOf(
        ElegantCheckboxGroupItem(text = "Camera", value = "camera"),
        ElegantCheckboxGroupItem(text = "Microphone", value = "microphone", enabled = false),
    ),
)

ElegantCheckboxGroup(
    selectedValues = setOf("notifications"),
    onToggle = { _, _ -> },
    enabled = false,
    items = listOf(
        ElegantCheckboxGroupItem(text = "Notifications", value = "notifications"),
        ElegantCheckboxGroupItem(text = "Announcements", value = "announcements"),
    ),
)
```

## 属性

### ElegantCheckboxGroup 属性

| 属性名 | 类型 | 说明 | 默认值 | 是否必须 |
| --- | --- | --- | --- | --- |
| `selectedValues` | `Set<String>` | 当前选中的值,由调用方持有 | - | 是 |
| `onToggle` | `(String, Boolean) -> Unit` | 以条目值和请求的选择状态调用的回调 | - | 是 |
| `items` | `List<ElegantCheckboxGroupItem>` | 以 Checkbox 行渲染的条目模型 | - | 是 |
| `modifier` | `Modifier` | 仅应用一次到 Group 根节点的修饰符 | `Modifier` | 否 |
| `enabled` | `Boolean` | 整个 Group 是否接受用户交互 | `true` | 否 |
| `colors` | `ElegantCheckboxGroupColors` | 主题感知的文本颜色 | `ElegantCheckboxGroupDefaults.colors()` | 否 |
| `supportingText` | `String?` | 行下方渲染的可选支持性说明文字 | `null` | 否 |

### ElegantCheckboxGroupItem 属性

| 属性名 | 类型 | 说明 | 默认值 | 是否必须 |
| --- | --- | --- | --- | --- |
| `text` | `String` | Checkbox 行上渲染的标签 | - | 是 |
| `value` | `String` | 与 `selectedValues` 匹配的稳定标识 | - | 是 |
| `enabled` | `Boolean` | 该条目是否接受用户交互 | `true` | 否 |

### ElegantCheckboxGroupDefaults

| 成员 | 类型 | 说明 |
| --- | --- | --- |
| `ItemGap` | `Dp` | 行与行之间的 4dp 纵向间距 |
| `colors()` | `ElegantCheckboxGroupColors` | 主题感知的 Light/Dark 文本颜色 |

### ElegantCheckboxGroupColors

`ElegantCheckboxGroupColors` 承载条目标签配色(`labelColor`、`disabledLabelColor`)与支持性说明文字颜色(`supportingTextColor`)。应先调用 `ElegantCheckboxGroupDefaults.colors()`,再仅针对产品明确需要的层级使用 `copy(...)`。行标签通过 `ElegantCheckbox` 从活动主题渲染,因此默认值天然保持一致。

## 进阶用法

### 带支持性文字权限选择器

```kotlin
var permissions by remember { mutableStateOf(setOf("camera")) }

ElegantCheckboxGroup(
    selectedValues = permissions,
    onToggle = { value, checked ->
        permissions = if (checked) permissions + value else permissions - value
    },
    items = listOf(
        ElegantCheckboxGroupItem(text = "Camera", value = "camera"),
        ElegantCheckboxGroupItem(text = "Photos", value = "photos"),
        ElegantCheckboxGroupItem(text = "Microphone", value = "microphone"),
    ),
    supportingText = "Choose what this app may access.",
)
```

### 自定义颜色

```kotlin
val baseColors = ElegantCheckboxGroupDefaults.colors()

ElegantCheckboxGroup(
    selectedValues = selectedValues,
    onToggle = onToggle,
    items = items,
    colors = baseColors.copy(
        supportingTextColor = Color(0xFF6E727A),
    ),
    supportingText = "Custom caption",
)
```
