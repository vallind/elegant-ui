# Tag Group

`ElegantTagGroup` 是相关可选 Tag Chip 的自动换行列表,共享一个由调用方持有的选择集合。它适用于筛选栏、分类选择器与多选关键词列表:此时多个相关选项共享同一个受控状态。

<iframe id="demoIframe" style="width: 100%; height: 460px; border: 1px solid var(--vp-c-divider); border-radius: 12px; display: block; background: var(--vp-c-bg-alt);" src="../../compose/index.html?id=tag-group" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>

## 引入

```kotlin
import com.elegant.compose.ui.taggroup.ElegantTagGroup
import com.elegant.compose.ui.taggroup.ElegantTagGroupColors
import com.elegant.compose.ui.taggroup.ElegantTagGroupDefaults
import com.elegant.compose.ui.taggroup.ElegantTagGroupItem
```

## 基本用法

Group 完全受控:`selectedValues` 持有当前选择,`onToggle` 以请求的状态报告条目值。调用方持有该 `Set`,切换时应复制集合——推荐使用不可变 `Set`。

```kotlin
var filters by remember { mutableStateOf(setOf("design")) }

ElegantTagGroup(
    selectedValues = filters,
    onToggle = { value, checked ->
        filters = if (checked) filters + value else filters - value
    },
    items = listOf(
        ElegantTagGroupItem(text = "Design", value = "design"),
        ElegantTagGroupItem(text = "Engineering", value = "engineering"),
        ElegantTagGroupItem(text = "Release", value = "release"),
    ),
)
```

## 组件类型

### ElegantTagGroupItem

每个条目将可见的 `text` 与稳定标识 `value` 配对,`value` 用于匹配 `selectedValues`。`enabled = false` 保持 Chip 可见,但仅禁用该 Chip。

## 组件状态

仅当 Group 的 `enabled` 与条目自身的 `enabled` 都允许交互时,该 Chip 才可交互;禁用 Chip 不会调用 `onToggle`。每个 Chip 都会播报 `Role.Button` 与 `selected` 状态,保持 48dp 最小交互目标,内部为 28dp 的紧凑药丸,并在选中时切换为实心药丸、未选中时切换为描边药丸。Group 以 8dp 节奏流动,并自动换行。

```kotlin
ElegantTagGroup(
    selectedValues = setOf("design"),
    onToggle = { _, _ -> },
    items = listOf(
        ElegantTagGroupItem(text = "Design", value = "design"),
        ElegantTagGroupItem(text = "Release", value = "release", enabled = false),
    ),
)

ElegantTagGroup(
    selectedValues = setOf("notifications"),
    onToggle = { _, _ -> },
    enabled = false,
    items = listOf(
        ElegantTagGroupItem(text = "Notifications", value = "notifications"),
        ElegantTagGroupItem(text = "Announcements", value = "announcements"),
    ),
)
```

## 属性

### ElegantTagGroup 属性

| 属性名 | 类型 | 说明 | 默认值 | 是否必须 |
| --- | --- | --- | --- | --- |
| `selectedValues` | `Set<String>` | 当前选中的值,由调用方持有 | - | 是 |
| `onToggle` | `(String, Boolean) -> Unit` | 以条目值和请求的选择状态调用的回调 | - | 是 |
| `items` | `List<ElegantTagGroupItem>` | 以可选 Tag Chip 渲染的条目模型 | - | 是 |
| `modifier` | `Modifier` | 仅应用一次到 Group 根节点的修饰符 | `Modifier` | 否 |
| `enabled` | `Boolean` | 整个 Group 是否接受用户交互 | `true` | 否 |
| `colors` | `ElegantTagGroupColors` | 主题感知的 Chip 颜色 | `ElegantTagGroupDefaults.colors()` | 否 |

### ElegantTagGroupItem 属性

| 属性名 | 类型 | 说明 | 默认值 | 是否必须 |
| --- | --- | --- | --- | --- |
| `text` | `String` | Tag Chip 上渲染的标签 | - | 是 |
| `value` | `String` | 与 `selectedValues` 匹配的稳定标识 | - | 是 |
| `enabled` | `Boolean` | 该条目是否接受用户交互 | `true` | 否 |

### ElegantTagGroupDefaults

| 成员 | 类型 | 说明 |
| --- | --- | --- |
| `ItemGap` | `Dp` | 两个流动方向上的 8dp Chip 间距 |
| `colors()` | `ElegantTagGroupColors` | 主题感知的 Light/Dark Chip 颜色 |

### ElegantTagGroupColors

`ElegantTagGroupColors` 承载选中药丸配色(`selectedContainerColor`、`selectedContentColor`)、未选中药丸配色(`unselectedContainerColor`、`unselectedContentColor`、`unselectedBorderColor`)与禁用标签颜色(`disabledContentColor`)。应先调用 `ElegantTagGroupDefaults.colors()`,再仅针对产品明确需要的层级使用 `copy(...)`。

## 进阶用法

### 筛选栏

```kotlin
var platforms by remember { mutableStateOf(setOf("android")) }

ElegantTagGroup(
    selectedValues = platforms,
    onToggle = { value, checked ->
        platforms = if (checked) platforms + value else platforms - value
    },
    items = listOf(
        ElegantTagGroupItem(text = "Android", value = "android"),
        ElegantTagGroupItem(text = "Desktop", value = "desktop"),
        ElegantTagGroupItem(text = "Web", value = "web"),
        ElegantTagGroupItem(text = "Wasm", value = "wasm"),
    ),
)
```

### 自定义颜色

```kotlin
val baseColors = ElegantTagGroupDefaults.colors()

ElegantTagGroup(
    selectedValues = selectedValues,
    onToggle = onToggle,
    items = items,
    colors = baseColors.copy(
        selectedContainerColor = Color(0xFF5840D6),
    ),
)
```
