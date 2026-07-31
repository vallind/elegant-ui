package com.elegant.compose.ui.table

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.elegant.compose.ui.theme.ElegantColors
import com.elegant.compose.ui.theme.ElegantRadius
import com.elegant.compose.ui.theme.ElegantTheme

/**
 * Describes one column of an [ElegantTable].
 *
 * A column without a fixed [width] shares the available width in proportion to its [weight].
 *
 * @property title header label rendered with the header content style.
 * @property weight relative share of the available width; non-finite or non-positive values
 *   fall back to `1f`.
 * @property textAlign alignment used by the header label and every cell in the column.
 * @property width fixed column width; `null` lets the column share space by [weight].
 */
@Immutable
public data class ElegantTableColumn(
    val title: String,
    val weight: Float = 1f,
    val textAlign: TextAlign = TextAlign.Start,
    val width: Dp? = null,
)

/**
 * One data row of an [ElegantTable].
 *
 * Rows whose [cells] count differs from the table's column count are omitted from rendering.
 *
 * @property cells cell text in the same order as the table columns.
 */
@Immutable
public data class ElegantTableRow(
    val cells: List<String>,
)

/**
 * Theme-aware colors used by an [ElegantTable].
 *
 * @property headerContainerColor background of the header row.
 * @property headerContentColor color of the header labels.
 * @property rowContentColor color of the data cell text.
 * @property rowBackgroundColor background applied to every data row.
 * @property borderColor color of the outer border and the divider lines between rows.
 */
@Immutable
public data class ElegantTableColors(
    val headerContainerColor: Color,
    val headerContentColor: Color,
    val rowContentColor: Color,
    val rowBackgroundColor: Color,
    val borderColor: Color,
)

/** Defaults and theme-aware factories shared by the Elegant UI table API. */
public object ElegantTableDefaults {
    /** Default minimum height of a data row. */
    public val RowMinHeight: Dp = 44.dp

    /** Default horizontal padding applied to every cell. */
    public val CellHorizontalPadding: Dp = 12.dp

    /** Default height of the header row. */
    public val HeaderHeight: Dp = 40.dp

    /** Returns theme-aware colors for an [ElegantTable]. */
    @Composable
    public fun colors(): ElegantTableColors = resolveTableColors(ElegantTheme.colors)
}

/**
 * Renders tabular data as a bordered, rounded grid of single-line text cells.
 *
 * The table is non-interactive and does not scroll by itself; wrap it in a horizontal scroll
 * container when fixed-width columns exceed the available width. Columns without a fixed
 * [ElegantTableColumn.width] share the available width in proportion to their weight. Header
 * labels use `labelMedium` and data cells use `bodyMedium`; every cell ellipsizes when its
 * column is too narrow. Rows whose cell count differs from the column count are omitted.
 *
 * The table keeps content semantics and adds no semantics node of its own. Callers that need a
 * data-grid semantics contract should layer their own, for example with `Modifier.semantics`.
 *
 * @param columns column definitions in display order.
 * @param rows row data in display order; rows with a mismatched cell count are omitted.
 * @param modifier modifier applied once to the table root.
 * @param colors theme-aware colors for the header, rows, and borders.
 */
@Composable
public fun ElegantTable(
    columns: List<ElegantTableColumn>,
    rows: List<ElegantTableRow>,
    modifier: Modifier = Modifier,
    colors: ElegantTableColors = ElegantTableDefaults.colors(),
) {
    val visibleRows = remember(columns, rows) {
        rows.filter { row -> validateTable(columns, listOf(row)) }
    }

    Column(
        modifier = modifier
            .clip(RoundedCornerShape(ElegantRadius.sm))
            .border(
                width = TableBorderWidth,
                color = colors.borderColor,
                shape = RoundedCornerShape(ElegantRadius.sm),
            ),
    ) {
        TableHeader(columns = columns, colors = colors)
        visibleRows.forEach { row ->
            TableDividerLine(color = colors.borderColor)
            TableDataRow(row = row, columns = columns, colors = colors)
        }
    }
}

@Composable
private fun TableHeader(
    columns: List<ElegantTableColumn>,
    colors: ElegantTableColors,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(ElegantTableDefaults.HeaderHeight)
            .background(colors.headerContainerColor),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        columns.forEach { column ->
            TableColumnCell(column = column) {
                Text(
                    text = column.title,
                    modifier = Modifier.fillMaxWidth(),
                    style = ElegantTheme.typography.labelMedium,
                    color = colors.headerContentColor,
                    textAlign = column.textAlign,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
    }
}

@Composable
private fun TableDataRow(
    row: ElegantTableRow,
    columns: List<ElegantTableColumn>,
    colors: ElegantTableColors,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = ElegantTableDefaults.RowMinHeight)
            .background(colors.rowBackgroundColor),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        columns.forEachIndexed { index, column ->
            TableColumnCell(column = column) {
                Text(
                    text = row.cells[index],
                    modifier = Modifier.fillMaxWidth(),
                    style = ElegantTheme.typography.bodyMedium,
                    color = colors.rowContentColor,
                    textAlign = column.textAlign,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
    }
}

@Composable
private fun RowScope.TableColumnCell(
    column: ElegantTableColumn,
    content: @Composable () -> Unit,
) {
    Box(
        modifier = Modifier
            .then(
                if (column.width != null) {
                    Modifier.width(column.width)
                } else {
                    Modifier.weight(resolveColumnWeight(column))
                },
            )
            .padding(horizontal = ElegantTableDefaults.CellHorizontalPadding),
    ) {
        content()
    }
}

@Composable
private fun TableDividerLine(color: Color) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(TableBorderWidth)
            .background(color),
    )
}

/** Resolves theme-aware table colors from [themeColors]. */
internal fun resolveTableColors(themeColors: ElegantColors): ElegantTableColors = ElegantTableColors(
    headerContainerColor = themeColors.backgroundSubtle,
    headerContentColor = themeColors.textSecondary,
    rowContentColor = themeColors.textPrimary,
    rowBackgroundColor = Color.Transparent,
    borderColor = themeColors.borderDefault,
)

/** Returns true only when every [rows] entry has exactly [columns] count of cells. */
internal fun validateTable(
    columns: List<ElegantTableColumn>,
    rows: List<ElegantTableRow>,
): Boolean = rows.all { row -> row.cells.size == columns.size }

/** Coerces the [column] weight to a positive value, falling back to `1f` when non-finite or non-positive. */
internal fun resolveColumnWeight(column: ElegantTableColumn): Float =
    if (column.weight.isFinite() && column.weight > 0f) {
        column.weight
    } else {
        1f
    }

private val TableBorderWidth: Dp = 1.dp
