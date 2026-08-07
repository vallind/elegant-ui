# Description

`ElegantDescription` 以堆叠行的形式呈现键值列表，包含固定宽度的标签列与弹性值列，行间以 1dp 分隔线相隔。列表是非交互组件，保留内容语义，禁用项的值以禁用颜色渲染。

<iframe id="demoIframe" style="width: 100%; height: 460px; border: 1px solid var(--vp-c-divider); border-radius: 12px; display: block; background: var(--vp-c-bg-alt);" src="../../compose/index.html?id=description" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>

## 引入

```kotlin
import com.elegant.compose.ui.description.ElegantDescription
import com.elegant.compose.ui.description.ElegantDescriptionColors
import com.elegant.compose.ui.description.ElegantDescriptionDefaults
import com.elegant.compose.ui.description.ElegantDescriptionItem
```

## 基本用法

使用 `ElegantDescriptionItem` 描述每条键值项。标签保持固定的 140dp 列宽，值填充剩余宽度；分隔线分隔各行，但永远不会出现在最后一行之后。

```kotlin
val profile = listOf(
    ElegantDescriptionItem(label = "Owner", value = "Maya Chen"),
    ElegantDescriptionItem(label = "Repository", value = "elegant"),
    ElegantDescriptionItem(label = "License", value = "Proprietary", enabled = false),
)

ElegantDescription(items = profile)
```

## 自定义标签列宽

通过 `labelWidth` 为标签列指定自定义固定宽度。非正数或非有限值会回退为默认 140dp。标签为空的行会渲染空的标签单元格，确保各行的值保持对齐。

```kotlin
val targets = listOf(
    ElegantDescriptionItem(label = "Android", value = "API 24+"),
    ElegantDescriptionItem(label = "Desktop", value = "JVM"),
    ElegantDescriptionItem(label = "Web", value = "Wasm"),
)

ElegantDescription(items = targets, labelWidth = 96.dp)
```

## 组件状态

Description 是非交互组件，没有 pressed、focused、selected 或 loading 状态。条目级 `enabled = false` 会以禁用颜色渲染该值，而标签保持默认颜色。列表保留内容语义，不添加任何语义节点。

## 属性

### ElegantDescription 属性

| 属性名 | 类型 | 说明 | 默认值 | 是否必须 |
| --- | --- | --- | --- | --- |
| `items` | `List<ElegantDescriptionItem>` | 按显示顺序排列的键值条目 | - | 是 |
| `modifier` | `Modifier` | 仅应用一次到描述列表根节点的修饰符 | `Modifier` | 否 |
| `colors` | `ElegantDescriptionColors` | 主题感知的标签、值与分隔线颜色 | `ElegantDescriptionDefaults.colors()` | 否 |
| `labelWidth` | `Dp` | 标签列的固定宽度；非正数或非有限值回退为 `ElegantDescriptionDefaults.DefaultLabelWidth` | `140.dp` | 否 |

### ElegantDescriptionItem 属性

| 属性名 | 类型 | 说明 | 默认值 | 是否必须 |
| --- | --- | --- | --- | --- |
| `label` | `String` | 渲染在标签列的术语 | - | 是 |
| `value` | `String` | 渲染在值列的定义 | - | 是 |
| `enabled` | `Boolean` | 值是否以主要值颜色渲染；`false` 时以禁用颜色渲染 | `true` | 否 |

### ElegantDescriptionDefaults

| 成员 | 类型 | 说明 |
| --- | --- | --- |
| `RowMinHeight` | `Dp` | 默认 36dp 键值行最小高度 |
| `DefaultLabelWidth` | `Dp` | 默认 140dp 标签列宽度 |
| `colors()` | `ElegantDescriptionColors` | 返回 Light/Dark 主题感知的描述列表颜色 |

### ElegantDescriptionColors

`ElegantDescriptionColors` 包含 `labelColor`、`valueColor`、`disabledValueColor` 与 `dividerColor`。默认值依次解析为 `textSecondary`、`textPrimary`、`textTertiary` 与 `borderDefault`。应先调用 `ElegantDescriptionDefaults.colors()`，再仅针对产品明确需要的层级使用 `copy(...)`。

## 进阶用法

### 禁用值的展示

`enabled = false` 保持布局不变，仅让该值变暗，适合数据暂时不可用或已被吊销的条目。

```kotlin
val repository = listOf(
    ElegantDescriptionItem(label = "Public", value = "Yes"),
    ElegantDescriptionItem(label = "Stars", value = "1,024"),
    ElegantDescriptionItem(label = "Deploy key", value = "Revoked", enabled = false),
)

ElegantDescription(items = repository)
```

### 自定义描述列表颜色

```kotlin
val descriptionColors = ElegantDescriptionDefaults.colors().copy(
    labelColor = ElegantTheme.colors.textPrimary,
    dividerColor = ElegantTheme.colors.borderStrong,
)

val environment = listOf(
    ElegantDescriptionItem(label = "Platform", value = "Linux"),
    ElegantDescriptionItem(label = "Architecture", value = "arm64"),
)

ElegantDescription(
    items = environment,
    colors = descriptionColors,
)
```
