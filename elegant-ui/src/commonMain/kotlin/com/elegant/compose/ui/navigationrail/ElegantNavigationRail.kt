package com.elegant.compose.ui.navigationrail

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.disabled
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.selected
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.elegant.compose.ui.iconbutton.ElegantIcon
import com.elegant.compose.ui.foundation.theme.ElegantColors
import com.elegant.compose.ui.foundation.theme.ElegantMotion
import com.elegant.compose.ui.foundation.theme.ElegantRadius
import com.elegant.compose.ui.foundation.theme.ElegantSpacing
import com.elegant.compose.ui.foundation.theme.ElegantTheme

private val NavigationRailSlotVerticalPadding: Dp = ElegantSpacing.md

/**
 * One navigation destination offered by [ElegantNavigationRail].
 *
 * [text] is what is rendered as the item label. Setting [enabled] to false renders the item
 * disabled: it keeps its resting look, cannot be invoked, and announces itself as disabled
 * through semantics.
 *
 * @property text text rendered as the item label.
 * @property enabled whether the item can be invoked.
 */
@Immutable
public data class ElegantNavigationRailItem(
    val text: String,
    val enabled: Boolean = true,
    val icon: ImageVector? = null,
)

/**
 * Theme-aware state colors used by [ElegantNavigationRail].
 *
 * Use [ElegantNavigationRailDefaults.colors] for theme-aware defaults, then use [copy] for
 * supported product-level customization.
 *
 * @property containerColor rail background color.
 * @property selectedItemColor indicator background of the selected item while hovered or pressed.
 * @property selectedContentColor text color of the selected item.
 * @property itemColor resting text color of an unselected item.
 * @property hoveredItemColor text color of a hovered or pressed item.
 * @property hoveredContainerColor indicator background of a hovered or pressed unselected item.
 * @property disabledItemColor text color of a disabled item.
 * @property indicatorColor resting indicator background of the selected item.
 */
@Immutable
public data class ElegantNavigationRailColors(
    val containerColor: Color,
    val selectedItemColor: Color,
    val selectedContentColor: Color,
    val itemColor: Color,
    val hoveredItemColor: Color = itemColor,
    val hoveredContainerColor: Color = containerColor,
    val disabledItemColor: Color = itemColor,
    val indicatorColor: Color,
)

/** Theme-aware defaults for [ElegantNavigationRail]. */
public object ElegantNavigationRailDefaults {
    /** Default rail width. */
    public val Width: Dp = 80.dp

    /** Minimum height of every rail item hit target. */
    public val MinimumTouchHeight: Dp = 48.dp

    /** Diameter of the rounded indicator behind every item label. */
    public val IndicatorSize: Dp = 48.dp

    /** Standard state-transition duration. */
    public const val AnimationDurationMillis: Int = ElegantMotion.standardDurationMillis

    /** Returns theme-aware state colors for the navigation rail. */
    @Composable
    public fun colors(): ElegantNavigationRailColors = resolveNavigationRailColors(ElegantTheme.colors)
}

/**
 * Displays a controlled vertical navigation rail with selectable text items and optional header
 * and footer slots.
 *
 * Selection is controlled: [selectedIndex] is owned by the caller and must be written back from
 * [onSelect]; out-of-range indexes are coerced into the item range. Each item carries its own
 * [ElegantNavigationRailItem.enabled] flag, and [enabled] disables the whole rail; disabled items
 * keep their resting look, cannot be invoked, and announce themselves through semantics.
 *
 * The rail is [ElegantNavigationRailDefaults.Width] wide, paints [colors]'s container color
 * across its full height, and scrolls vertically once the content overflows. Every item is a
 * [ElegantNavigationRailDefaults.MinimumTouchHeight] tall hit target holding a
 * [ElegantNavigationRailDefaults.IndicatorSize] rounded indicator behind its label. The indicator
 * shows [colors]'s indicator color for the selected item, the selected item color while the
 * selected item is hovered or pressed, and the hover container color while an unselected item is
 * hovered or pressed. Item label colors follow the precedence: disabled, selected, hovered or
 * pressed, resting. Indicator and label colors animate with the standard motion duration.
 *
 * The optional [header] and [footer] slots are unstyled areas padded with 8dp vertically and
 * centered within the rail width.
 *
 * @param selectedIndex currently selected item index, owned by the caller and coerced into the
 *   item range.
 * @param onSelect callback invoked with the index of the item chosen by the user.
 * @param items navigation entries rendered in order.
 * @param modifier modifier applied once to the rail root.
 * @param enabled whether the rail accepts selection and the items can be invoked.
 * @param colors theme-aware state colors.
 * @param header optional header content shown above the items.
 * @param footer optional footer content shown below the items.
 */
@Composable
public fun ElegantNavigationRail(
    selectedIndex: Int,
    onSelect: (Int) -> Unit,
    items: List<ElegantNavigationRailItem>,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    colors: ElegantNavigationRailColors = ElegantNavigationRailDefaults.colors(),
    header: (@Composable () -> Unit)? = null,
    footer: (@Composable () -> Unit)? = null,
) {
    val resolvedSelectedIndex = resolveSelectedIndex(selectedIndex, items.size)
    val indicatorShape = RoundedCornerShape(ElegantRadius.full)

    Column(
        modifier = modifier
            .width(ElegantNavigationRailDefaults.Width)
            .fillMaxHeight()
            .background(colors.containerColor)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        if (header != null) {
            Box(
                modifier = Modifier.padding(vertical = NavigationRailSlotVerticalPadding),
                contentAlignment = Alignment.Center,
            ) {
                header()
            }
        }

        items.forEachIndexed { index, item ->
            key(index) {
                val selected = resolvedSelectedIndex == index
                val interactive = enabled && item.enabled
                val interactionSource = remember { MutableInteractionSource() }
                val hoveredState by interactionSource.collectIsHoveredAsState()
                val pressedState by interactionSource.collectIsPressedAsState()
                val hovered = hoveredState || pressedState

                val animatedIndicator by animateColorAsState(
                    targetValue = resolveIndicatorContainer(colors, selected, hovered),
                    animationSpec = tween(
                        durationMillis = ElegantNavigationRailDefaults.AnimationDurationMillis,
                        easing = FastOutSlowInEasing,
                    ),
                    label = "ElegantNavigationRailItemIndicator",
                )
                val animatedContent by animateColorAsState(
                    targetValue = resolveItemColor(colors, selected, hovered, enabled = interactive),
                    animationSpec = tween(
                        durationMillis = ElegantNavigationRailDefaults.AnimationDurationMillis,
                        easing = FastOutSlowInEasing,
                    ),
                    label = "ElegantNavigationRailItemContent",
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .defaultMinSize(minHeight = ElegantNavigationRailDefaults.MinimumTouchHeight)
                        .semantics(mergeDescendants = true) {
                            role = Role.Tab
                            this.selected = selected
                            if (!interactive) disabled()
                        }
                        .then(
                            if (interactive) {
                                Modifier.clickable(
                                    interactionSource = interactionSource,
                                    indication = null,
                                    role = Role.Tab,
                                    onClick = { onSelect(index) },
                                )
                            } else {
                                Modifier
                            },
                        ),
                    contentAlignment = Alignment.Center,
                ) {
                    Box(
                        modifier = Modifier
                            .size(ElegantNavigationRailDefaults.IndicatorSize)
                            .clip(indicatorShape)
                            .background(animatedIndicator),
                        contentAlignment = Alignment.Center,
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(ElegantSpacing.xxs),
                        ) {
                            if (item.icon != null) {
                                ElegantIcon(
                                    icon = item.icon,
                                    contentDescription = null,
                                    modifier = Modifier.size(20.dp),
                                    tint = animatedContent,
                                )
                            }
                            Text(
                                text = item.text,
                                color = animatedContent,
                                style = ElegantTheme.typography.labelSmall,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                            )
                        }
                    }
                }
            }
        }

        if (footer != null) {
            Box(
                modifier = Modifier.padding(vertical = NavigationRailSlotVerticalPadding),
                contentAlignment = Alignment.Center,
            ) {
                footer()
            }
        }
    }
}

internal fun resolveNavigationRailColors(themeColors: ElegantColors): ElegantNavigationRailColors =
    ElegantNavigationRailColors(
        containerColor = Color.Transparent,
        selectedItemColor = themeColors.surfaceHover,
        selectedContentColor = themeColors.interactivePrimary,
        itemColor = themeColors.textSecondary,
        hoveredItemColor = themeColors.textPrimary,
        hoveredContainerColor = themeColors.surfaceHover,
        disabledItemColor = themeColors.textTertiary,
        indicatorColor = themeColors.interactivePrimary.copy(alpha = 0.12f),
    )

internal fun resolveSelectedIndex(selectedIndex: Int, itemCount: Int): Int =
    if (itemCount <= 0) 0 else selectedIndex.coerceIn(0, itemCount - 1)

internal fun resolveItemColor(
    colors: ElegantNavigationRailColors,
    selected: Boolean,
    hovered: Boolean,
    enabled: Boolean,
): Color = when {
    !enabled -> colors.disabledItemColor
    selected -> colors.selectedContentColor
    hovered -> colors.hoveredItemColor
    else -> colors.itemColor
}

internal fun resolveIndicatorContainer(
    colors: ElegantNavigationRailColors,
    selected: Boolean,
    hovered: Boolean,
): Color = when {
    selected && hovered -> colors.selectedItemColor
    selected -> colors.indicatorColor
    hovered -> colors.hoveredContainerColor
    else -> colors.containerColor
}
