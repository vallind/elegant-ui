package com.elegant.compose.ui.description

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.elegant.compose.ui.foundation.theme.ElegantColors
import com.elegant.compose.ui.foundation.theme.ElegantTheme

/**
 * One key-value entry of an [ElegantDescription].
 *
 * @property label term rendered in the fixed-width label column with `labelMedium`.
 * @property value definition rendered in the flexible value column with `bodyMedium`.
 * @property enabled whether the [value] renders in the primary value color; `false` renders it
 *   in the disabled value color.
 */
@Immutable
public data class ElegantDescriptionItem(
    val label: String,
    val value: String,
    val enabled: Boolean = true,
)

/**
 * Theme-aware colors used by an [ElegantDescription].
 *
 * @property labelColor color of the term labels.
 * @property valueColor color of enabled values.
 * @property disabledValueColor color of values whose item is disabled.
 * @property dividerColor color of the 1dp divider lines between rows.
 */
@Immutable
public data class ElegantDescriptionColors(
    val labelColor: Color,
    val valueColor: Color,
    val disabledValueColor: Color,
    val dividerColor: Color,
)

/** Defaults and theme-aware factories shared by the Elegant UI description list API. */
public object ElegantDescriptionDefaults {
    /** Default minimum height of a single key-value row. */
    public val RowMinHeight: Dp = 36.dp

    /** Default width of the label column. */
    public val DefaultLabelWidth: Dp = 140.dp

    /** Returns theme-aware colors for an [ElegantDescription]. */
    @Composable
    public fun colors(): ElegantDescriptionColors = resolveDescriptionColors(ElegantTheme.colors)
}

/**
 * Renders a non-interactive key-value list as stacked rows separated by 1dp dividers.
 *
 * Every row keeps a fixed-width label column and a flexible value column, so values stay aligned
 * across the list. Labels use `labelMedium` and render single-line with ellipsis; values use
 * `bodyMedium` and fill the remaining width. A divider line appears between rows but never after
 * the last one. Rows with a blank label render an empty label cell. Items with [enabled] set to
 * false render their value in the disabled value color.
 *
 * The list is non-interactive, keeps content semantics, and adds no semantics node of its own.
 *
 * @param items key-value entries in display order.
 * @param modifier modifier applied once to the description list root.
 * @param colors theme-aware colors for labels, values, and divider lines.
 * @param labelWidth fixed width of the label column; non-positive or non-finite values fall back
 *   to [ElegantDescriptionDefaults.DefaultLabelWidth].
 */
@Composable
public fun ElegantDescription(
    items: List<ElegantDescriptionItem>,
    modifier: Modifier = Modifier,
    colors: ElegantDescriptionColors = ElegantDescriptionDefaults.colors(),
    labelWidth: Dp = ElegantDescriptionDefaults.DefaultLabelWidth,
) {
    val resolvedLabelWidth = remember(labelWidth) { resolveLabelWidth(labelWidth) }

    Column(modifier = modifier) {
        items.forEachIndexed { index, item ->
            if (rowDividerVisible(index = index, itemCount = items.size)) {
                DescriptionDivider(color = colors.dividerColor)
            }
            DescriptionRow(item = item, labelWidth = resolvedLabelWidth, colors = colors)
        }
    }
}

@Composable
private fun DescriptionRow(
    item: ElegantDescriptionItem,
    labelWidth: Dp,
    colors: ElegantDescriptionColors,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = ElegantDescriptionDefaults.RowMinHeight),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = item.label,
            modifier = Modifier.width(labelWidth),
            style = ElegantTheme.typography.labelMedium,
            color = colors.labelColor,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
        Text(
            text = item.value,
            modifier = Modifier.weight(1f),
            style = ElegantTheme.typography.bodyMedium,
            color = if (item.enabled) colors.valueColor else colors.disabledValueColor,
        )
    }
}

@Composable
private fun DescriptionDivider(color: Color) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(DescriptionDividerWidth)
            .background(color),
    )
}

/** Resolves theme-aware description list colors from [themeColors]. */
internal fun resolveDescriptionColors(themeColors: ElegantColors): ElegantDescriptionColors = ElegantDescriptionColors(
    labelColor = themeColors.textSecondary,
    valueColor = themeColors.textPrimary,
    disabledValueColor = themeColors.textTertiary,
    dividerColor = themeColors.borderDefault,
)

/** Returns true when a divider line must follow the row at [index] out of [itemCount] rows. */
internal fun rowDividerVisible(index: Int, itemCount: Int): Boolean = index in 0 until itemCount - 1

/**
 * Coerces [labelWidth] to a positive finite width, falling back to the default label width when
 * the value is non-positive or non-finite.
 */
internal fun resolveLabelWidth(labelWidth: Dp): Dp =
    if (labelWidth.value.isFinite() && labelWidth.value > 0f) {
        labelWidth
    } else {
        ElegantDescriptionDefaults.DefaultLabelWidth
    }

private val DescriptionDividerWidth: Dp = 1.dp
