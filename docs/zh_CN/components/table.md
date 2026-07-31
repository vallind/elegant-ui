# Table

`ElegantTable` 以精致的非交互网格呈现表格数据，包含圆角边框、主题感知表头以及单行省略号单元格。列按权重共享可用宽度或使用固定宽度，单元格数量与列数量不一致的行会自动跳过。

<iframe id="demoIframe" style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; border: none;" src="../../compose/index.html?id=table" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>

## 引入

```kotlin
import com.elegant.compose.ui.table.ElegantTable
import com.elegant.compose.ui.table.ElegantTableColumn
import com.elegant.compose.ui.table.ElegantTableColors
import com.elegant.compose.ui.table.ElegantTableDefaults
import com.elegant.compose.ui.table.ElegantTableRow
```

## 基本用法

使用 `ElegantTableColumn` 描述列，使用 `ElegantTableRow` 提供数据。未设置固定 `width` 的列默认等分可用宽度。

```kotlin
val columns = listOf(
    ElegantTableColumn(title = "组件"),
    ElegantTableColumn(title = "目标平台"),
    ElegantTableColumn(title = "状态", textAlign = TextAlign.End),
)

val rows = listOf(
    ElegantTableRow(cells = listOf("Button", "Android · Desktop · Web", "可用")),
    ElegantTableRow(cells = listOf("Tag", "Android · Desktop · Web", "可用")),
    ElegantTableRow(cells = listOf("Tooltip", "Android · Desktop · Web", "可用")),
)

ElegantTable(columns = columns, rows = rows)
```

## 列权重与对齐

设置 `weight` 可以让列占据更多或更少的可用宽度。`textAlign` 同时对齐表头标签与该列的所有单元格。

```kotlin
val metrics = listOf(
    ElegantTableColumn(title = "指标", weight = 1.5f),
    ElegantTableColumn(title = "数值", weight = 1f, textAlign = TextAlign.End),
)

val metricRows = listOf(
    ElegantTableRow(cells = listOf("可用组件", "16")),
    ElegantTableRow(cells = listOf("支持平台", "3")),
    ElegantTableRow(cells = listOf("通过测试", "412")),
)

ElegantTable(columns = metrics, rows = metricRows)
```

## 组件状态

Table 是非交互组件，没有 pressed、focused、selected、disabled 或 loading 状态。表头标签使用 `labelMedium`，数据单元格使用 `bodyMedium`；每个单元格均为单行，列宽不足时以省略号截断。单元格数量与列数量不一致的行会被跳过不渲染。Table 保留内容语义，不添加任何数据网格语义；需要数据网格语义时，请自行在语义层补充。

## 属性

### ElegantTable 属性

| 属性名 | 类型 | 说明 | 默认值 | 是否必须 |
| --- | --- | --- | --- | --- |
| `columns` | `List<ElegantTableColumn>` | 按显示顺序排列的列定义 | - | 是 |
| `rows` | `List<ElegantTableRow>` | 按显示顺序排列的行数据；单元格数量不匹配的行会被跳过 | - | 是 |
| `modifier` | `Modifier` | 仅应用一次到 Table 根节点的修饰符 | `Modifier` | 否 |
| `colors` | `ElegantTableColors` | 主题感知的表头、行与边框颜色 | `ElegantTableDefaults.colors()` | 否 |

### ElegantTableColumn 属性

| 属性名 | 类型 | 说明 | 默认值 | 是否必须 |
| --- | --- | --- | --- | --- |
| `title` | `String` | 列的表头标签 | - | 是 |
| `weight` | `Float` | 可用宽度的相对占比；非有限或非正数值回退为 `1f` | `1f` | 否 |
| `textAlign` | `TextAlign` | 表头标签与每个单元格的对齐方式 | `TextAlign.Start` | 否 |
| `width` | `Dp?` | 固定列宽；`null` 时使用 `weight` | `null` | 否 |

### ElegantTableRow 属性

| 属性名 | 类型 | 说明 | 默认值 | 是否必须 |
| --- | --- | --- | --- | --- |
| `cells` | `List<String>` | 与列顺序一致的单元格文本；数量必须与列数量相同 | - | 是 |

### ElegantTableDefaults

| 成员 | 类型 | 说明 |
| --- | --- | --- |
| `RowMinHeight` | `Dp` | 默认 44dp 数据行最小高度 |
| `CellHorizontalPadding` | `Dp` | 默认 12dp 每个单元格的水平内边距 |
| `HeaderHeight` | `Dp` | 默认 40dp 表头高度 |
| `colors()` | `ElegantTableColors` | 返回 Light/Dark 主题感知的表格颜色 |

### ElegantTableColors

`ElegantTableColors` 包含 `headerContainerColor`、`headerContentColor`、`rowContentColor`、`rowBackgroundColor` 与 `borderColor`。默认值依次解析为 `backgroundSubtle`、`textSecondary`、`textPrimary`、`Color.Transparent` 与 `borderDefault`。应先调用 `ElegantTableDefaults.colors()`，再仅针对产品明确需要的层级使用 `copy(...)`。

## 进阶用法

### 固定宽度列与横向滚动

Table 本身不会滚动。设置固定 `width` 的列保持其尺寸；当自然宽度超过可用宽度时，请将 Table 包裹在横向滚动容器中。

```kotlin
Row(
    modifier = Modifier.horizontalScroll(rememberScrollState()),
) {
    ElegantTable(
        columns = listOf(
            ElegantTableColumn(title = "指标", width = 120.dp),
            ElegantTableColumn(title = "周", width = 96.dp, textAlign = TextAlign.End),
        ),
        rows = listOf(
            ElegantTableRow(cells = listOf("拉取请求", "24")),
            ElegantTableRow(cells = listOf("已完成评审", "31")),
        ),
    )
}
```

### 自定义表格颜色

```kotlin
val tableColors = ElegantTableDefaults.colors().copy(
    headerContainerColor = ElegantTheme.colors.surfaceSunken,
    headerContentColor = ElegantTheme.colors.textPrimary,
)

val releases = listOf(
    ElegantTableColumn(title = "版本"),
    ElegantTableColumn(title = "状态", textAlign = TextAlign.End),
)

val releaseRows = listOf(
    ElegantTableRow(cells = listOf("0.3", "已发布")),
    ElegantTableRow(cells = listOf("0.4", "进行中")),
)

ElegantTable(
    columns = releases,
    rows = releaseRows,
    colors = tableColors,
)
```
