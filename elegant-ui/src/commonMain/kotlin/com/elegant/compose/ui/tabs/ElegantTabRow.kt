package com.elegant.compose.ui.tabs

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.disabled
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.selected
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.elegant.compose.ui.theme.ElegantColors
import com.elegant.compose.ui.theme.ElegantMotion
import com.elegant.compose.ui.theme.ElegantRadius
import com.elegant.compose.ui.theme.ElegantSpacing
import com.elegant.compose.ui.theme.ElegantTheme

/**
 * Stable data model describing one tab rendered by [ElegantTabRow].
 *
 * @property text label rendered with the labelMedium style.
 * @property enabled whether this tab accepts user interaction; a disabled tab stays visible and
 *   keeps its slot but never invokes [ElegantTabRow.onSelect].
 */
@Immutable
public data class ElegantTab(
    val text: String,
    val enabled: Boolean = true,
)

/**
 * Theme-aware state colors used by [ElegantTabRow].
 *
 * Use [ElegantTabDefaults.colors] for theme-aware defaults, then use [copy] for supported
 * product-level customization.
 *
 * @property containerColor background of every tab; transparent by default.
 * @property indicatorColor selection indicator shown at the bottom of the selected tab.
 * @property contentColor resting label color.
 * @property selectedContentColor label color of the selected tab.
 * @property hoveredContentColor label color while a pointer hovers an unselected tab.
 * @property disabledContentColor label color of a tab that cannot be interacted with.
 */
@Immutable
public data class ElegantTabColors(
    val containerColor: Color,
    val indicatorColor: Color,
    val contentColor: Color,
    val selectedContentColor: Color,
    val hoveredContentColor: Color = contentColor,
    val disabledContentColor: Color = contentColor,
)

/** Theme-aware defaults for [ElegantTabRow]. */
public object ElegantTabDefaults {
    /** Minimum interactive root height used by every tab. */
    public val MinimumTouchHeight: Dp = 48.dp

    /** Height of the selection indicator. */
    public val IndicatorHeight: Dp = 2.dp

    /** Standard label-color transition duration. */
    public const val AnimationDurationMillis: Int = ElegantMotion.standardDurationMillis

    /** Returns theme-aware state colors. */
    @Composable
    public fun colors(): ElegantTabColors = resolveTabColors(ElegantTheme.colors)
}

/**
 * Presents a controlled strip of [ElegantTab] models that switches between mutually exclusive
 * views.
 *
 * [selectedIndex] is controlled: the caller must update it from [onSelect] to keep the row
 * responsive. Values outside the tab range are clamped to the last tab, and an empty [tabs] list
 * renders nothing. Fixed mode (the default) gives every tab an equal share of the row width;
 * with [scrollable] each tab keeps its natural width and the row scrolls horizontally.
 *
 * The label color follows the precedence disabled, selected, hovered, resting:
 * [ElegantTabColors.hoveredContentColor] applies only to unselected tabs. The selection indicator
 * is drawn at the bottom of the selected tab and spans the full tab width.
 *
 * Keyboard interaction treats the whole row as one focusable node: while focused, the logical
 * left and right arrow keys move the selection to the next enabled tab with wrap-around in both
 * directions and invoke [onSelect]. Every tab announces [Role.Tab] with its selected and disabled
 * state; the row node itself announces [Role.Tab] together with its selection. A tab never
 * invokes [onSelect] while the row or the tab is disabled.
 *
 * @param tabs models rendered by the row; an empty list renders nothing.
 * @param selectedIndex index of the selected tab; out-of-range values clamp to the last tab.
 * @param onSelect callback invoked with the newly selected index.
 * @param modifier modifier applied once to the row root.
 * @param enabled whether the row accepts user interaction.
 * @param scrollable whether tabs keep their natural width and the row scrolls horizontally.
 * @param colors theme-aware state colors.
 * @param interactionSource optional hoisted interaction source for observing or controlling the
 *   row's focus state; each tab keeps an independent interaction source for its own press and
 *   hover state.
 */
@Composable
public fun ElegantTabRow(
    tabs: List<ElegantTab>,
    selectedIndex: Int,
    onSelect: (Int) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    scrollable: Boolean = false,
    colors: ElegantTabColors = ElegantTabDefaults.colors(),
    interactionSource: MutableInteractionSource? = null,
) {
    val resolvedSelectedIndex = resolveSelectedIndex(selectedIndex, tabs.size)
    if (tabs.isEmpty()) return

    val resolvedInteractionSource = interactionSource ?: remember { MutableInteractionSource() }
    val layoutDirection = LocalLayoutDirection.current
    val hasEnabledTabs = tabs.any { it.enabled }

    val rowModifier = modifier
        .semantics {
            role = Role.Tab
            selected = resolvedSelectedIndex >= 0
            if (!enabled) disabled()
        }
        .focusable(interactionSource = resolvedInteractionSource, enabled = enabled)
        .onKeyEvent { event ->
            if (event.type == KeyEventType.KeyDown && enabled && hasEnabledTabs) {
                val forward = when (event.key) {
                    Key.DirectionRight -> layoutDirection == LayoutDirection.Ltr
                    Key.DirectionLeft -> layoutDirection == LayoutDirection.Rtl
                    else -> null
                }
                if (forward != null) {
                    val target = nextEnabledTab(
                        tabs = tabs,
                        fromIndex = resolvedSelectedIndex,
                        direction = if (forward) 1 else -1,
                    )
                    if (target >= 0 && target != resolvedSelectedIndex) {
                        onSelect(target)
                    }
                    true
                } else {
                    false
                }
            } else {
                false
            }
        }

    if (scrollable) {
        Row(
            modifier = rowModifier.horizontalScroll(rememberScrollState()),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            tabs.forEachIndexed { index, tab ->
                ElegantTabItem(
                    text = tab.text,
                    selected = index == resolvedSelectedIndex,
                    enabled = enabled && tab.enabled,
                    colors = colors,
                    onClick = { onSelect(index) },
                    modifier = Modifier.padding(horizontal = ElegantSpacing.xl),
                )
            }
        }
    } else {
        Row(
            modifier = rowModifier,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            tabs.forEachIndexed { index, tab ->
                ElegantTabItem(
                    text = tab.text,
                    selected = index == resolvedSelectedIndex,
                    enabled = enabled && tab.enabled,
                    colors = colors,
                    onClick = { onSelect(index) },
                    modifier = Modifier.weight(1f),
                )
            }
        }
    }
}

@Composable
private fun ElegantTabItem(
    text: String,
    selected: Boolean,
    enabled: Boolean,
    colors: ElegantTabColors,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val hovered by interactionSource.collectIsHoveredAsState()
    val targetContentColor = when {
        !enabled -> colors.disabledContentColor
        selected -> colors.selectedContentColor
        hovered -> colors.hoveredContentColor
        else -> colors.contentColor
    }
    val animatedContentColor by animateColorAsState(
        targetValue = targetContentColor,
        animationSpec = tween(
            durationMillis = ElegantTabDefaults.AnimationDurationMillis,
            easing = FastOutSlowInEasing,
        ),
        label = "ElegantTabContentColor",
    )

    Box(
        modifier = modifier
            .defaultMinSize(minHeight = ElegantTabDefaults.MinimumTouchHeight)
            .background(colors.containerColor)
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
        Text(
            text = text,
            style = ElegantTheme.typography.labelMedium,
            color = animatedContentColor,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .height(ElegantTabDefaults.IndicatorHeight)
                .clip(RoundedCornerShape(ElegantRadius.full))
                .background(if (selected) colors.indicatorColor else Color.Transparent),
        )
    }
}

internal fun resolveTabColors(
    themeColors: ElegantColors,
): ElegantTabColors = ElegantTabColors(
    containerColor = Color.Transparent,
    indicatorColor = themeColors.interactivePrimary,
    contentColor = themeColors.textSecondary,
    selectedContentColor = themeColors.interactivePrimary,
    hoveredContentColor = themeColors.textPrimary,
    disabledContentColor = themeColors.textTertiary,
)

internal fun resolveSelectedIndex(
    selectedIndex: Int,
    tabCount: Int,
): Int = if (tabCount <= 0) {
    0
} else {
    selectedIndex.coerceIn(0, tabCount - 1)
}

internal fun nextEnabledTab(
    tabs: List<ElegantTab>,
    fromIndex: Int,
    direction: Int,
): Int {
    if (tabs.isEmpty()) return -1
    val count = tabs.size
    var candidate = (fromIndex + direction).mod(count)
    repeat(count) {
        if (tabs[candidate].enabled) return candidate
        candidate = (candidate + direction).mod(count)
    }
    return -1
}
