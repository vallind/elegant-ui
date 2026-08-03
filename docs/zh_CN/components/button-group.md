# Button Group

`ElegantButtonGroup` 是 Elegant UI 中的分段控件，在单行紧凑布局中呈现互斥选项。它为每个条目渲染等宽的单元格，用 1dp 边框勾勒控件轮廓，高亮选中的单元格，并在每个单元格上播报单选按钮语义。

<iframe id="demoIframe" style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; border: none;" src="../../compose/index.html?id=button-group" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>

## 引入

```kotlin
import com.elegant.compose.ui.buttongroup.ElegantButtonGroup
import com.elegant.compose.ui.buttongroup.ElegantButtonGroupColors
import com.elegant.compose.ui.buttongroup.ElegantButtonGroupDefaults
import com.elegant.compose.ui.buttongroup.ElegantButtonGroupItem
```

## 基本用法

`ElegantButtonGroup` 是受控组件：将 `selectedIndex` 保存在状态中，并在 `onSelect` 中更新它。每个单元格平均占据行宽度。

```kotlin
var selected by remember { mutableStateOf(0) }
val items = listOf(
    ElegantButtonGroupItem("日"),
    ElegantButtonGroupItem("周"),
    ElegantButtonGroupItem("月"),
)

ElegantButtonGroup(
    selectedIndex = selected,
    onSelect = { selected = it },
    items = items,
)
```

## 条目模型

`ElegantButtonGroupItem` 是驱动分组的稳定数据模型。在模型上将 `enabled` 设为 `false` 可以使该单元格保持可见但不可交互。

```kotlin
val items = listOf(
    ElegantButtonGroupItem("常规"),
    ElegantButtonGroupItem("安全", enabled = false),
    ElegantButtonGroupItem("账单"),
)
```

## 组件状态

单元格背景遵循禁用、选中、按压、悬停、静止（透明）的优先级；标签颜色遵循禁用、选中、静止。每个单元格都会播报 `Role.RadioButton` 以及自身的 `selected` 与 `disabled` 状态，分组或单元格禁用时绝不会触发 `onSelect`。向 `selectedIndex` 传入 `null` 表示无选中项，空 `items` 列表不渲染任何内容。

```kotlin
var selected by remember { mutableStateOf(0) }

ElegantButtonGroup(
    selectedIndex = selected,
    onSelect = { selected = it },
    items = listOf(
        ElegantButtonGroupItem("草稿"),
        ElegantButtonGroupItem("已发布"),
        ElegantButtonGroupItem("已归档", enabled = false),
    ),
)

ElegantButtonGroup(
    selectedIndex = 1,
    onSelect = {},
    items = listOf(ElegantButtonGroupItem("离线"), ElegantButtonGroupItem("在线")),
    enabled = false,
)
```

## 属性

### ElegantButtonGroup 属性

| 属性名 | 类型 | 说明 | 默认值 | 是否必须 |
| --- | --- | --- | --- | --- |
| `selectedIndex` | `Int?` | 选中单元格的索引，或 `null` 表示无选中；越界值会钳制到最近的单元格 | `null` | 是 |
| `onSelect` | `(Int) -> Unit` | 新选中索引确定时触发的回调 | - | 是 |
| `items` | `List<ElegantButtonGroupItem>` | 分组渲染的单元格模型 | - | 是 |
| `modifier` | `Modifier` | 应用于分组根节点一次的修饰符 | `Modifier` | 否 |
| `enabled` | `Boolean` | 分组是否可以接受用户交互 | `true` | 否 |
| `colors` | `ElegantButtonGroupColors` | 主题感知的状态颜色 | `ElegantButtonGroupDefaults.colors()` | 否 |

### ElegantButtonGroupItem 属性

| 属性名 | 类型 | 说明 | 默认值 | 是否必须 |
| --- | --- | --- | --- | --- |
| `text` | `String` | 使用 labelMedium 样式渲染的标签 | - | 是 |
| `enabled` | `Boolean` | 该单元格是否可以接受用户交互 | `true` | 否 |

### ElegantButtonGroupDefaults

| 成员 | 类型 | 说明 |
| --- | --- | --- |
| `MinimumTouchHeight` | `Dp` | 每个单元格统一使用的最小交互根高度 |
| `Height` | `Dp` | 分段控件的可视高度 |
| `HorizontalPadding` | `Dp` | 每个单元格内保留的水平内边距 |
| `AnimationDurationMillis` | `Int` | 标准状态过渡时长 |
| `colors()` | `ElegantButtonGroupColors` | 主题感知的浅色/深色颜色 |

### ElegantButtonGroupColors

`ElegantButtonGroupColors` 包含控件容器色、边框色与分隔线色，以及静止、选中、悬停、按压和禁用状态下的单元格容器色与标签色。应先调用 `ElegantButtonGroupDefaults.colors()`，再通过 `copy(...)` 只覆盖产品明确支持的视觉值。

## 进阶用法

### 切换内容

使用同一个受控状态同时驱动分组及其下方的内容。

```kotlin
var selected by remember { mutableStateOf(0) }
val items = listOf(
    ElegantButtonGroupItem("列表"),
    ElegantButtonGroupItem("网格"),
)

Column {
    ElegantButtonGroup(
        selectedIndex = selected,
        onSelect = { selected = it },
        items = items,
    )
    Text(
        text = "正在查看 ${items[selected].text}",
        style = ElegantTheme.typography.bodyMedium,
        color = ElegantTheme.colors.textSecondary,
        modifier = Modifier.padding(top = ElegantSpacing.lg),
    )
}
```

### 无选中项

传入 `null` 可以渲染一个没有选中单元格的分组。

```kotlin
ElegantButtonGroup(
    selectedIndex = null,
    onSelect = { selected = it },
    items = listOf(ElegantButtonGroupItem("紧凑"), ElegantButtonGroupItem("舒适")),
)
```

### 自定义颜色

```kotlin
val baseColors = ElegantButtonGroupDefaults.colors()

ElegantButtonGroup(
    selectedIndex = selected,
    onSelect = { selected = it },
    items = listOf(ElegantButtonGroupItem("概览"), ElegantButtonGroupItem("详情")),
    colors = baseColors.copy(
        selectedContainerColor = Color(0xFF147D64),
        selectedContentColor = Color(0xFFFFFFFF),
    ),
)
```
