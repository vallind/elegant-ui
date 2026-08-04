package com.elegant.compose.ui.breadcrumb

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.disabled
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.elegant.compose.ui.foundation.theme.ElegantColors
import com.elegant.compose.ui.foundation.theme.ElegantSpacing
import com.elegant.compose.ui.foundation.theme.ElegantTheme

/**
 * One entry of an [ElegantBreadcrumb] hierarchy.
 *
 * @property text visible label of the entry.
 * @property enabled whether the entry can be activated; false renders it disabled.
 */
@Immutable
public data class ElegantBreadcrumbItem(
    val text: String,
    val enabled: Boolean = true,
)

/**
 * Theme-aware colors used by [ElegantBreadcrumb].
 *
 * Use [ElegantBreadcrumbDefaults.colors] for theme-aware defaults, then use [copy] for supported
 * product-level customization.
 *
 * @property itemColor resting color of interactive entries.
 * @property currentColor color of the trailing current-page entry.
 * @property separatorColor chevron separator color.
 * @property hoveredItemColor color of a hovered interactive entry; defaults to [itemColor].
 * @property disabledItemColor color of a disabled entry; defaults to [itemColor].
 */
@Immutable
public data class ElegantBreadcrumbColors(
    val itemColor: Color,
    val currentColor: Color,
    val separatorColor: Color,
    val hoveredItemColor: Color = itemColor,
    val disabledItemColor: Color = itemColor,
)

/** Theme-aware defaults for [ElegantBreadcrumb]. */
public object ElegantBreadcrumbDefaults {
    /** Minimum touch height applied to interactive entries. */
    public val MinimumTouchHeight: Dp = 48.dp

    /** Default spacing between entries and their chevron separators. */
    public val ItemGap: Dp = ElegantSpacing.xs

    /** Returns theme-aware breadcrumb colors. */
    @Composable
    public fun colors(): ElegantBreadcrumbColors = resolveBreadcrumbColors(ElegantTheme.colors)
}

/** Size of the square box that hosts a chevron separator. */
internal val BreadcrumbSeparatorSize: Dp = 16.dp

/** Stroke width of the chevron separator lines. */
internal val BreadcrumbStrokeWidth: Dp = 1.5.dp

/**
 * Shows the current page's position within a hierarchy.
 *
 * The trailing entry is the current page: it is never interactive, exposes no button role, and
 * renders in [ElegantBreadcrumbColors.currentColor]. Every earlier entry is interactive when both
 * its [ElegantBreadcrumbItem.enabled] and [onItemClick] are provided: it reports a
 * [androidx.compose.ui.semantics.Role.Button] through merged semantics, honors a 48dp minimum
 * touch height, and paints [ElegantBreadcrumbColors.hoveredItemColor] on pointer hover. Disabled
 * entries paint [ElegantBreadcrumbColors.disabledItemColor] and never invoke the callback.
 *
 * With a null [onItemClick] every entry renders as non-interactive plain text in
 * [ElegantBreadcrumbColors.itemColor], which suits a pure display of the current location.
 *
 * Chevron separators point in the logical layout direction and mirror horizontally in RTL; they
 * are decorative and omitted from the semantics tree. The row measures its natural width, so wrap
 * the breadcrumb in a horizontally scrolling container when the hierarchy is long.
 *
 * @param items hierarchy entries in logical order; the trailing entry is the current page.
 * @param onItemClick optional callback invoked with the clicked entry's index; null disables all
 * entries.
 * @param modifier modifier applied once to the breadcrumb root.
 * @param colors theme-aware item, current, separator, hovered, and disabled colors.
 */
@Composable
public fun ElegantBreadcrumb(
    items: List<ElegantBreadcrumbItem>,
    onItemClick: ((Int) -> Unit)? = null,
    modifier: Modifier = Modifier,
    colors: ElegantBreadcrumbColors = ElegantBreadcrumbDefaults.colors(),
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(ElegantBreadcrumbDefaults.ItemGap),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        items.forEachIndexed { index, item ->
            if (index > 0) {
                BreadcrumbSeparator(color = colors.separatorColor)
            }

            val current = isCurrentItem(index, items.size)
            BreadcrumbItem(
                text = item.text,
                index = index,
                isCurrent = current,
                interactive = onItemClick != null && !current,
                enabled = item.enabled,
                colors = colors,
                onItemClick = onItemClick,
            )
        }
    }
}

@Composable
private fun BreadcrumbItem(
    text: String,
    index: Int,
    isCurrent: Boolean,
    interactive: Boolean,
    enabled: Boolean,
    colors: ElegantBreadcrumbColors,
    onItemClick: ((Int) -> Unit)?,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val hovered by interactionSource.collectIsHoveredAsState()
    val resolvedColor = resolveItemColor(
        colors = colors,
        isCurrent = isCurrent,
        enabled = enabled,
        hovered = interactive && enabled && hovered,
    )
    val itemModifier = if (interactive) {
        Modifier
            .semantics(mergeDescendants = true) {
                role = Role.Button
                if (!enabled) disabled()
            }
            .defaultMinSize(minHeight = ElegantBreadcrumbDefaults.MinimumTouchHeight)
            .clickable(
                enabled = enabled,
                role = Role.Button,
                interactionSource = interactionSource,
                indication = null,
                onClick = { onItemClick?.invoke(index) },
            )
    } else {
        Modifier
    }

    Box(
        modifier = itemModifier,
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = text,
            color = resolvedColor,
            style = ElegantTheme.typography.labelMedium,
        )
    }
}

@Composable
private fun BreadcrumbSeparator(
    color: Color,
    modifier: Modifier = Modifier,
) {
    val layoutDirection = LocalLayoutDirection.current
    val scaleX = if (layoutDirection == LayoutDirection.Rtl) -1f else 1f
    Box(
        modifier = modifier
            .size(BreadcrumbSeparatorSize)
            .clearAndSetSemantics {}
            .graphicsLayer {
                this.scaleX = scaleX
            }
            .drawBehind {
                val strokeWidth = BreadcrumbStrokeWidth.toPx()
                val start = Offset(size.width * 0.34f, size.height * 0.30f)
                val mid = Offset(size.width * 0.62f, size.height * 0.50f)
                val end = Offset(size.width * 0.34f, size.height * 0.70f)
                drawLine(
                    color = color,
                    start = start,
                    end = mid,
                    strokeWidth = strokeWidth,
                    cap = StrokeCap.Round,
                )
                drawLine(
                    color = color,
                    start = mid,
                    end = end,
                    strokeWidth = strokeWidth,
                    cap = StrokeCap.Round,
                )
            },
    )
}

/**
 * Resolves theme-aware breadcrumb colors for [themeColors].
 *
 * @param themeColors semantic roles of the active light or dark theme.
 * @return breadcrumb colors derived from the semantic roles.
 */
internal fun resolveBreadcrumbColors(themeColors: ElegantColors): ElegantBreadcrumbColors =
    ElegantBreadcrumbColors(
        itemColor = themeColors.interactivePrimary,
        currentColor = themeColors.textPrimary,
        separatorColor = themeColors.textTertiary,
        hoveredItemColor = themeColors.interactivePrimaryHover,
        disabledItemColor = themeColors.textTertiary,
    )

/**
 * Resolves the color of one breadcrumb entry.
 *
 * Precedence: current page, then disabled, then hovered, then resting.
 *
 * @param colors breadcrumb colors to pick from.
 * @param isCurrent whether the entry is the trailing current page.
 * @param enabled whether the entry accepts interaction.
 * @param hovered whether a pointer currently hovers the entry.
 * @return the color that matches the leading entry state.
 */
internal fun resolveItemColor(
    colors: ElegantBreadcrumbColors,
    isCurrent: Boolean,
    enabled: Boolean,
    hovered: Boolean,
): Color = when {
    isCurrent -> colors.currentColor
    !enabled -> colors.disabledItemColor
    hovered -> colors.hoveredItemColor
    else -> colors.itemColor
}

/**
 * Whether the entry at [index] is the trailing current page.
 *
 * @param index zero-based entry index.
 * @param itemCount total number of entries.
 * @return true only when the breadcrumb is non-empty and [index] is the last position.
 */
internal fun isCurrentItem(index: Int, itemCount: Int): Boolean =
    itemCount > 0 && index == itemCount - 1
