# RadioGroup

`ElegantRadioGroup` 以纵列展示一组共享互斥选择状态的 `ElegantRadio` 行。每一行由行内 Radio 保持自身的 48dp 交互目标,组负责行与行之间的垂直节奏,并在选项下方提供可选的辅助文本。它适用于设置、筛选与表单中必须从有界集合里恰好选择一个的场景。

<iframe id="demoIframe" style="width: 100%; height: 460px; border: 1px solid var(--vp-c-divider); border-radius: 12px; display: block; background: var(--vp-c-bg-alt);" src="../../compose/index.html?id=radio-group" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>

## 引入

```kotlin
import com.elegant.compose.ui.radiogroup.ElegantRadioGroup
import com.elegant.compose.ui.radiogroup.ElegantRadioGroupColors
import com.elegant.compose.ui.radiogroup.ElegantRadioGroupDefaults
import com.elegant.compose.ui.radiogroup.ElegantRadioGroupItem
```

## 基本用法

构建稳定的模型列表,并在整组共享一个 `selectedValue`。回调会回报用户所选条目的 value。

```kotlin
val deliveryItems = listOf(
    ElegantRadioGroupItem(text = "标准", value = "standard"),
    ElegantRadioGroupItem(text = "快速", value = "express"),
)

ElegantRadioGroup(
    selectedValue = selectedValue,
    onSelect = { selectedValue = it },
    items = deliveryItems,
)
```

## 组件状态

每一行都会播报 `Role.RadioButton` 与选中状态,在主题启用焦点环时显示焦点环,并保持 48dp 最小交互目标。禁用整组会拒绝所有行的交互;禁用单个条目会保留其选中态,但仅拒绝该行交互。当 `selectedValue` 为 null 或空白时,不选中任何项。

```kotlin
var plan by remember { mutableStateOf("pro") }

ElegantRadioGroup(
    selectedValue = plan,
    onSelect = { plan = it },
    items = listOf(
        ElegantRadioGroupItem(text = "免费", value = "free"),
        ElegantRadioGroupItem(text = "专业", value = "pro"),
        ElegantRadioGroupItem(text = "团队", value = "team", enabled = false),
    ),
)

ElegantRadioGroup(
    selectedValue = "pro",
    onSelect = {},
    enabled = false,
    items = listOf(
        ElegantRadioGroupItem(text = "免费", value = "free"),
        ElegantRadioGroupItem(text = "专业", value = "pro"),
    ),
)
```

## 属性

### ElegantRadioGroup 属性

| 属性名 | 类型 | 说明 | 默认值 | 是否必须 |
| --- | --- | --- | --- | --- |
| `selectedValue` | `String?` | 被选中条目的 value;null 或空白表示不选中任何项 | - | 是 |
| `onSelect` | `(String) -> Unit` | 用户选中条目时,以该条目的 value 调用的回调 | - | 是 |
| `items` | `List<ElegantRadioGroupItem>` | 按顺序渲染为 Radio 行的选项 | - | 是 |
| `modifier` | `Modifier` | 仅应用一次到行纵列的修饰符 | `Modifier` | 否 |
| `enabled` | `Boolean` | 组是否接受用户交互 | `true` | 否 |
| `colors` | `ElegantRadioGroupColors` | 主题感知的文本颜色 | `ElegantRadioGroupDefaults.colors()` | 否 |
| `supportingText` | `String?` | 选项下方可选显示的辅助文本 | `null` | 否 |

### ElegantRadioGroupItem

| 属性名 | 类型 | 说明 | 默认值 | 是否必须 |
| --- | --- | --- | --- | --- |
| `text` | `String` | 由行内 Radio 渲染的标签 | - | 是 |
| `value` | `String` | 选中时由 `onSelect` 回报的值 | - | 是 |
| `enabled` | `Boolean` | 该条目是否接受交互 | `true` | 否 |

### ElegantRadioGroupDefaults

| 成员 | 类型 | 说明 |
| --- | --- | --- |
| `ItemGap` | `Dp` | 行之间 4dp 垂直间距 |
| `colors()` | `ElegantRadioGroupColors` | Light/Dark 主题感知颜色 |

### ElegantRadioGroupColors

`ElegantRadioGroupColors` 包含条目标签与辅助文本的文本颜色。行内 Radio 使用主题文本颜色绘制标签,`labelColor` 与 `disabledLabelColor` 与之对应;`supportingTextColor` 与 `disabledLabelColor` 用于组自身的辅助文本。应先调用 `ElegantRadioGroupDefaults.colors()`,再仅针对产品明确需要的层级使用 `copy(...)`。

## 进阶用法

### 辅助文本

可选的 `supportingText` 位于选项下方,组被禁用时会变暗。

```kotlin
ElegantRadioGroup(
    selectedValue = region,
    onSelect = { region = it },
    items = regions,
    supportingText = "配送时效会随所选地区更新。",
)
```

### 与输入框组合的表单行

将组与输入框组合,构成紧凑的表单区块。

```kotlin
var zone by remember { mutableStateOf("north") }
var street by remember { mutableStateOf("") }

ElegantRadioGroup(
    selectedValue = zone,
    onSelect = { zone = it },
    items = listOf(
        ElegantRadioGroupItem(text = "北区", value = "north"),
        ElegantRadioGroupItem(text = "南区", value = "south"),
    ),
)

ElegantInput(
    value = street,
    onValueChange = { street = it },
    label = "街道",
    placeholder = "街道名称与门牌号",
)
```
