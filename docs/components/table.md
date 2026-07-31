# Table

`ElegantTable` renders tabular data as a refined, non-interactive grid with a rounded border, a theme-aware header, and single-line ellipsized cells. Columns share the available width in proportion to their weight or use a fixed width, and rows whose cell count does not match the columns are omitted automatically.

<iframe id="demoIframe" style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; border: none;" src="../compose/index.html?id=table" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>

## Import

```kotlin
import com.elegant.compose.ui.table.ElegantTable
import com.elegant.compose.ui.table.ElegantTableColumn
import com.elegant.compose.ui.table.ElegantTableColors
import com.elegant.compose.ui.table.ElegantTableDefaults
import com.elegant.compose.ui.table.ElegantTableRow
```

## Basic Usage

Describe the columns with `ElegantTableColumn` and the data with `ElegantTableRow`. Columns without a fixed `width` share the available width equally by default.

```kotlin
val columns = listOf(
    ElegantTableColumn(title = "Component"),
    ElegantTableColumn(title = "Targets"),
    ElegantTableColumn(title = "Status", textAlign = TextAlign.End),
)

val rows = listOf(
    ElegantTableRow(cells = listOf("Button", "Android · Desktop · Web", "Available")),
    ElegantTableRow(cells = listOf("Tag", "Android · Desktop · Web", "Available")),
    ElegantTableRow(cells = listOf("Tooltip", "Android · Desktop · Web", "Available")),
)

ElegantTable(columns = columns, rows = rows)
```

## Column Weights and Alignment

Set `weight` to give a column more or less of the available width. `textAlign` aligns the header label and every cell in the column.

```kotlin
val metrics = listOf(
    ElegantTableColumn(title = "Metric", weight = 1.5f),
    ElegantTableColumn(title = "Value", weight = 1f, textAlign = TextAlign.End),
)

val metricRows = listOf(
    ElegantTableRow(cells = listOf("Available components", "16")),
    ElegantTableRow(cells = listOf("Supported platforms", "3")),
    ElegantTableRow(cells = listOf("Passing tests", "412")),
)

ElegantTable(columns = metrics, rows = metricRows)
```

## Component States

Table is non-interactive and has no pressed, focused, selected, disabled, or loading state. Header labels use `labelMedium` and data cells use `bodyMedium`; every cell is single-line and ellipsizes when its column is too narrow. Rows whose cell count differs from the column count are omitted from rendering. The table preserves content semantics and adds no data-grid semantics of its own; layer your own semantics when a data-grid contract is required.

## Properties

### ElegantTable Properties

| Property Name | Type | Description | Default Value | Required |
| --- | --- | --- | --- | --- |
| `columns` | `List<ElegantTableColumn>` | Column definitions in display order | - | Yes |
| `rows` | `List<ElegantTableRow>` | Row data in display order; rows with a mismatched cell count are omitted | - | Yes |
| `modifier` | `Modifier` | Modifier applied once to the table root | `Modifier` | No |
| `colors` | `ElegantTableColors` | Theme-aware header, row, and border colors | `ElegantTableDefaults.colors()` | No |

### ElegantTableColumn Properties

| Property Name | Type | Description | Default Value | Required |
| --- | --- | --- | --- | --- |
| `title` | `String` | Header label of the column | - | Yes |
| `weight` | `Float` | Relative share of the available width; non-finite or non-positive values fall back to `1f` | `1f` | No |
| `textAlign` | `TextAlign` | Alignment of the header label and every cell | `TextAlign.Start` | No |
| `width` | `Dp?` | Fixed column width; `null` uses `weight` | `null` | No |

### ElegantTableRow Properties

| Property Name | Type | Description | Default Value | Required |
| --- | --- | --- | --- | --- |
| `cells` | `List<String>` | Cell text in the same order as the columns; count must match the column count | - | Yes |

### ElegantTableDefaults

| Member | Type | Description |
| --- | --- | --- |
| `RowMinHeight` | `Dp` | Default 44dp minimum data-row height |
| `CellHorizontalPadding` | `Dp` | Default 12dp horizontal padding applied to every cell |
| `HeaderHeight` | `Dp` | Default 40dp header height |
| `colors()` | `ElegantTableColors` | Returns Light/Dark theme-aware table colors |

### ElegantTableColors

`ElegantTableColors` contains `headerContainerColor`, `headerContentColor`, `rowContentColor`, `rowBackgroundColor`, and `borderColor`. The defaults resolve `backgroundSubtle`, `textSecondary`, `textPrimary`, `Color.Transparent`, and `borderDefault`. Start with `ElegantTableDefaults.colors()` and use `copy(...)` only for a deliberate product-specific hierarchy.

## Advanced Usage

### Fixed-Width Columns and Horizontal Scroll

The table never scrolls by itself. Fixed `width` columns keep their size; wrap the table in a horizontal scroll container when the natural width exceeds the available width.

```kotlin
Row(
    modifier = Modifier.horizontalScroll(rememberScrollState()),
) {
    ElegantTable(
        columns = listOf(
            ElegantTableColumn(title = "Metric", width = 120.dp),
            ElegantTableColumn(title = "Week", width = 96.dp, textAlign = TextAlign.End),
        ),
        rows = listOf(
            ElegantTableRow(cells = listOf("Pull requests", "24")),
            ElegantTableRow(cells = listOf("Reviews completed", "31")),
        ),
    )
}
```

### Custom Table Colors

```kotlin
val tableColors = ElegantTableDefaults.colors().copy(
    headerContainerColor = ElegantTheme.colors.surfaceSunken,
    headerContentColor = ElegantTheme.colors.textPrimary,
)

val releases = listOf(
    ElegantTableColumn(title = "Release"),
    ElegantTableColumn(title = "Status", textAlign = TextAlign.End),
)

val releaseRows = listOf(
    ElegantTableRow(cells = listOf("0.3", "Shipped")),
    ElegantTableRow(cells = listOf("0.4", "In progress")),
)

ElegantTable(
    columns = releases,
    rows = releaseRows,
    colors = tableColors,
)
```
