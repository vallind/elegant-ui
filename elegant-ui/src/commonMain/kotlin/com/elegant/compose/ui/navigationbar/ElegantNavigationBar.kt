package com.elegant.compose.ui.navigationbar

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.disabled
import androidx.compose.ui.semantics.selected
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.elegant.compose.ui.icon.ElegantIcon
import com.elegant.compose.ui.theme.ElegantColors
import com.elegant.compose.ui.theme.ElegantMotion
import com.elegant.compose.ui.theme.ElegantRadius
import com.elegant.compose.ui.theme.ElegantSpacing
import com.elegant.compose.ui.theme.ElegantTheme

/**
 * Stable data model describing one destination rendered by [ElegantNavigationBar].
 *
 * @property text label rendered with the labelMedium style.
 * @property enabled whether this item accepts user interaction; a disabled item stays visible and
 *   keeps its slot but never invokes [ElegantNavigationBar.onSelect].
 */
@Immutable
public data class ElegantNavigationBarItem(
    val text: String,
    val enabled: Boolean = true,
    val icon: ImageVector? = null,
)

/**
 * Theme-aware state colors used by [ElegantNavigationBar].
 *
 * Use [ElegantNavigationBarDefaults.colors] for theme-aware defaults, then use [copy] for
 * supported product-level customization.
 *
 * @property containerColor bar background color.
 * @property selectedItemColor faint pill background shown behind the label while a pointer hovers
 *   or presses an unselected item.
 * @property selectedContentColor label and active-dot color of the selected item.
 * @property itemColor resting label color.
 * @property hoveredItemColor label color while a pointer hovers an unselected item.
 * @property pressedItemColor label color while an unselected item is pressed.
 * @property disabledItemColor label color of an item that cannot be interacted with.
 * @property indicatorColor selection pill shown behind the label of the selected item.
 */
@Immutable
public data class ElegantNavigationBarColors(
    val containerColor: Color,
    val selectedItemColor: Color,
    val selectedContentColor: Color,
    val itemColor: Color,
    val hoveredItemColor: Color = itemColor,
    val pressedItemColor: Color = itemColor,
    val disabledItemColor: Color = itemColor,
    val indicatorColor: Color,
)

/** Theme-aware defaults for [ElegantNavigationBar]. */
public object ElegantNavigationBarDefaults {
    /** Minimum interactive root height of the bar. */
    public val MinimumTouchHeight: Dp = 56.dp

    /** Height of the selection pill shown behind the selected label. */
    public val IndicatorSize: Dp = 32.dp

    /** Standard label-color transition duration. */
    public const val AnimationDurationMillis: Int = ElegantMotion.standardDurationMillis

    /** Returns theme-aware state colors. */
    @Composable
    public fun colors(): ElegantNavigationBarColors = resolveNavigationBarColors(
        themeColors = ElegantTheme.colors,
    )
}

/**
 * Presents a controlled bottom navigation bar that switches between primary destinations.
 *
 * [selectedIndex] is controlled: the caller must update it from [onSelect] to keep the bar
 * responsive. Values outside the item range are clamped to the last item, and an empty [items]
 * list renders nothing. Every item takes an equal share of the bar width and the bar reaches at
 * least [ElegantNavigationBarDefaults.MinimumTouchHeight] of height; a 1dp hairline runs across
 * the top edge of the bar.
 *
 * The selected item shows a rounded pill ([ElegantNavigationBarDefaults.IndicatorSize] tall)
 * behind its label in [ElegantNavigationBarColors.indicatorColor] together with a small dot above
 * the label. While a pointer hovers or presses an unselected item, a faint pill in
 * [ElegantNavigationBarColors.selectedItemColor] is shown behind the label. The label color
 * follows the precedence disabled, selected, pressed, hovered, resting and transitions with
 * [ElegantNavigationBarDefaults.AnimationDurationMillis].
 *
 * Every item announces [Role.Tab] with its selected and disabled state; an item never invokes
 * [onSelect] while the bar or the item is disabled.
 *
 * @param selectedIndex index of the selected item; out-of-range values clamp to the last item.
 * @param onSelect callback invoked with the newly selected index.
 * @param items models rendered by the bar; an empty list renders nothing.
 * @param modifier modifier applied once to the bar root.
 * @param enabled whether the bar accepts user interaction.
 * @param colors theme-aware state colors.
 */
@Composable
public fun ElegantNavigationBar(
    selectedIndex: Int,
    onSelect: (Int) -> Unit,
    items: List<ElegantNavigationBarItem>,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    colors: ElegantNavigationBarColors = ElegantNavigationBarDefaults.colors(),
) {
    if (items.isEmpty()) return
    val resolvedSelectedIndex = resolveSelectedIndex(selectedIndex, items.size)
    val borderColor = ElegantTheme.colors.borderDefault

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(colors.containerColor)
            .drawBehind {
                val strokeWidth = 1.dp.toPx()
                drawLine(
                    color = borderColor,
                    start = Offset(0f, 0f),
                    end = Offset(size.width, 0f),
                    strokeWidth = strokeWidth,
                )
            }
            .defaultMinSize(minHeight = ElegantNavigationBarDefaults.MinimumTouchHeight),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        items.forEachIndexed { index, item ->
            ElegantNavigationBarItem(
                text = item.text,
                icon = item.icon,
                selected = index == resolvedSelectedIndex,
                enabled = enabled && item.enabled,
                colors = colors,
                onClick = { onSelect(index) },
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
            )
        }
    }
}

@Composable
private fun ElegantNavigationBarItem(
    text: String,
    icon: ImageVector?,
    selected: Boolean,
    enabled: Boolean,
    colors: ElegantNavigationBarColors,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val hovered by interactionSource.collectIsHoveredAsState()
    val pressed by interactionSource.collectIsPressedAsState()
    val targetItemColor = when {
        !enabled -> colors.disabledItemColor
        selected -> colors.selectedContentColor
        pressed -> colors.pressedItemColor
        hovered -> colors.hoveredItemColor
        else -> colors.itemColor
    }
    val targetPillColor = when {
        !enabled -> Color.Transparent
        selected -> colors.indicatorColor
        pressed -> colors.selectedItemColor
        hovered -> colors.selectedItemColor
        else -> Color.Transparent
    }
    val animatedItemColor by animateColorAsState(
        targetValue = targetItemColor,
        animationSpec = tween(
            durationMillis = ElegantNavigationBarDefaults.AnimationDurationMillis,
            easing = FastOutSlowInEasing,
        ),
        label = "ElegantNavigationBarItemColor",
    )
    val animatedPillColor by animateColorAsState(
        targetValue = targetPillColor,
        animationSpec = tween(
            durationMillis = ElegantNavigationBarDefaults.AnimationDurationMillis,
            easing = FastOutSlowInEasing,
        ),
        label = "ElegantNavigationBarPillColor",
    )

    Box(
        modifier = modifier
            .clickable(
                enabled = enabled,
                role = Role.Tab,
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick,
            )
            .hoverable(interactionSource = interactionSource, enabled = enabled)
            .semantics {
                this.selected = selected
                if (!enabled) disabled()
            },
        contentAlignment = Alignment.Center,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(ElegantSpacing.xs),
        ) {
            if (selected && enabled) {
                Box(
                    modifier = Modifier
                        .size(ElegantSpacing.sm)
                        .background(animatedItemColor, CircleShape),
                )
            }
            Box(
                modifier = Modifier
                    .height(ElegantNavigationBarDefaults.IndicatorSize)
                    .defaultMinSize(minWidth = ElegantNavigationBarDefaults.IndicatorSize)
                    .clip(RoundedCornerShape(ElegantRadius.full))
                    .background(animatedPillColor)
                    .padding(horizontal = ElegantSpacing.lg),
                contentAlignment = Alignment.Center,
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(ElegantSpacing.xxs),
                ) {
                    if (icon != null) {
                        ElegantIcon(
                            icon = icon,
                            contentDescription = null,
                            modifier = Modifier.size(20.dp),
                            tint = animatedItemColor,
                        )
                    }
                    Text(
                        text = text,
                        style = ElegantTheme.typography.labelMedium,
                        color = animatedItemColor,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
            }
        }
    }
}

internal fun resolveNavigationBarColors(
    themeColors: ElegantColors,
): ElegantNavigationBarColors = ElegantNavigationBarColors(
    containerColor = themeColors.surfaceRaised,
    selectedItemColor = themeColors.surfaceHover,
    selectedContentColor = themeColors.interactivePrimary,
    itemColor = themeColors.textSecondary,
    hoveredItemColor = themeColors.textPrimary,
    pressedItemColor = themeColors.textPrimary,
    disabledItemColor = themeColors.textTertiary,
    indicatorColor = themeColors.interactivePrimary.copy(alpha = 0.12f),
)

internal fun resolveSelectedIndex(
    selectedIndex: Int,
    itemCount: Int,
): Int = if (itemCount <= 0) {
    0
} else {
    selectedIndex.coerceIn(0, itemCount - 1)
}
