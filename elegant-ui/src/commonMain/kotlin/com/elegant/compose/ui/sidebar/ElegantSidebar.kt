package com.elegant.compose.ui.sidebar

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.disabled
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.selected
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.elegant.compose.ui.theme.ElegantColors
import com.elegant.compose.ui.theme.ElegantMotion
import com.elegant.compose.ui.theme.ElegantRadius
import com.elegant.compose.ui.theme.ElegantSpacing
import com.elegant.compose.ui.theme.ElegantTheme

private val SidebarSlotVerticalPadding: Dp = ElegantSpacing.md

/**
 * One navigation entry offered by [ElegantSidebar].
 *
 * [text] is what is rendered in the sidebar item. Setting [enabled] to false renders the item
 * disabled: it keeps its resting look, cannot be selected or invoked, and announces itself as
 * disabled through semantics.
 *
 * @property text text rendered in the sidebar item.
 * @property enabled whether the item can be selected or invoked.
 */
@Immutable
public data class ElegantSidebarItem(
    val text: String,
    val enabled: Boolean = true,
)

/**
 * Theme-aware state colors used by [ElegantSidebar].
 *
 * Use [ElegantSidebarDefaults.colors] for theme-aware defaults, then use [copy] for supported
 * product-level customization.
 *
 * @property containerColor sidebar background color.
 * @property itemContentColor resting item text color, also provided to header and footer content
 *   through [LocalContentColor].
 * @property selectedItemContentColor text color of the selected item.
 * @property selectedItemContainerColor background color of the selected item.
 * @property hoveredItemContentColor text color of a hovered item.
 * @property hoveredItemContainerColor background color of a hovered item.
 * @property disabledItemContentColor text color of a disabled item.
 */
@Immutable
public data class ElegantSidebarColors(
    val containerColor: Color,
    val itemContentColor: Color,
    val selectedItemContentColor: Color,
    val selectedItemContainerColor: Color,
    val hoveredItemContentColor: Color = itemContentColor,
    val hoveredItemContainerColor: Color = containerColor,
    val disabledItemContentColor: Color = itemContentColor,
)

/** Theme-aware defaults for [ElegantSidebar]. */
public object ElegantSidebarDefaults {
    /** Default sidebar width. */
    public val Width: Dp = 240.dp

    /** Minimum height of every sidebar item. */
    public val ItemHeight: Dp = 40.dp

    /** Horizontal padding inside every sidebar item. */
    public val ItemHorizontalPadding: Dp = 12.dp

    /** Vertical spacing between adjacent sidebar items. */
    public val ItemGap: Dp = 4.dp

    /** Standard state-transition duration. */
    public const val AnimationDurationMillis: Int = ElegantMotion.standardDurationMillis

    /** Returns theme-aware state colors for the sidebar. */
    @Composable
    public fun colors(): ElegantSidebarColors = resolveSidebarColors(ElegantTheme.colors)
}

/**
 * Displays a controlled vertical navigation sidebar with selectable items and optional header and
 * footer slots.
 *
 * Selection is controlled: [selectedIndex] is owned by the caller and must be written back from
 * [onSelect]. A null [selectedIndex] means nothing is selected. When [onSelect] is null the items
 * are rendered as plain text: they expose no Tab role and receive no hover, press, or focus
 * feedback. Each item carries its own [ElegantSidebarItem.enabled] flag, and [enabled] disables
 * the whole sidebar; disabled items keep their resting look, cannot be selected or invoked, and
 * announce themselves through semantics.
 *
 * The sidebar paints [colors]'s container color across its full height and scrolls vertically once
 * the content overflows. Items are 40dp tall with a 4dp gap, fill the sidebar width, and animate
 * their container and content colors with the standard motion duration. Visual precedence per
 * item: disabled, selected, hovered, resting.
 *
 * The optional [header] and [footer] slots are unstyled areas padded with 12dp horizontally and 8dp
 * vertically; their content receives [colors]'s item content color through [LocalContentColor].
 *
 * @param selectedIndex currently selected item index, or null when nothing is selected; owned by
 *   the caller.
 * @param onSelect callback invoked with the index of the item chosen by the user; null renders the
 *   items as non-interactive text.
 * @param items navigation entries rendered in order.
 * @param modifier modifier applied once to the sidebar root.
 * @param enabled whether the sidebar accepts selection and the items can be invoked.
 * @param width sidebar width.
 * @param colors theme-aware state colors.
 * @param header optional header content shown above the items.
 * @param footer optional footer content shown below the items.
 */
@Composable
public fun ElegantSidebar(
    selectedIndex: Int?,
    onSelect: ((Int) -> Unit)? = null,
    items: List<ElegantSidebarItem>,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    width: Dp = ElegantSidebarDefaults.Width,
    colors: ElegantSidebarColors = ElegantSidebarDefaults.colors(),
    header: (@Composable () -> Unit)? = null,
    footer: (@Composable () -> Unit)? = null,
) {
    val itemShape = RoundedCornerShape(ElegantRadius.sm)

    Column(
        modifier = modifier
            .width(width)
            .fillMaxHeight()
            .background(colors.containerColor)
            .verticalScroll(rememberScrollState()),
    ) {
        if (header != null) {
            CompositionLocalProvider(LocalContentColor provides colors.itemContentColor) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = ElegantSidebarDefaults.ItemHorizontalPadding,
                            vertical = SidebarSlotVerticalPadding,
                        ),
                ) {
                    header()
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = SidebarSlotVerticalPadding),
            verticalArrangement = Arrangement.spacedBy(ElegantSidebarDefaults.ItemGap),
        ) {
            items.forEachIndexed { index, item ->
                key(index) {
                    val selected = selectedIndex == index
                    val interactive = onSelect != null && enabled && item.enabled
                    val interactionSource = remember { MutableInteractionSource() }
                    val hoveredState by interactionSource.collectIsHoveredAsState()
                    val pressedState by interactionSource.collectIsPressedAsState()
                    val hovered = hoveredState || pressedState

                    val animatedContainer by animateColorAsState(
                        targetValue = resolveItemContainer(
                            colors = colors,
                            selected = selected,
                            hovered = hovered,
                        ),
                        animationSpec = tween(
                            durationMillis = ElegantSidebarDefaults.AnimationDurationMillis,
                            easing = FastOutSlowInEasing,
                        ),
                        label = "ElegantSidebarItemContainer",
                    )
                    val animatedContent by animateColorAsState(
                        targetValue = resolveItemColor(
                            colors = colors,
                            selected = selected,
                            hovered = hovered,
                            enabled = enabled && item.enabled,
                        ),
                        animationSpec = tween(
                            durationMillis = ElegantSidebarDefaults.AnimationDurationMillis,
                            easing = FastOutSlowInEasing,
                        ),
                        label = "ElegantSidebarItemContent",
                    )

                    val semanticsModifier = if (onSelect != null) {
                        Modifier.semantics(mergeDescendants = true) {
                            role = Role.Tab
                            this.selected = selected
                            if (!(enabled && item.enabled)) disabled()
                        }
                    } else {
                        Modifier
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .defaultMinSize(minHeight = ElegantSidebarDefaults.ItemHeight)
                            .then(semanticsModifier)
                            .clip(itemShape)
                            .background(animatedContainer)
                            .then(
                                if (interactive) {
                                    Modifier.clickable(
                                        interactionSource = interactionSource,
                                        indication = null,
                                        role = Role.Tab,
                                        onClick = { onSelect?.invoke(index) },
                                    )
                                } else {
                                    Modifier
                                },
                            )
                            .padding(horizontal = ElegantSidebarDefaults.ItemHorizontalPadding),
                        contentAlignment = Alignment.CenterStart,
                    ) {
                        Text(
                            text = item.text,
                            modifier = Modifier.fillMaxWidth(),
                            color = animatedContent,
                            style = ElegantTheme.typography.labelMedium,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )
                    }
                }
            }
        }

        if (footer != null) {
            CompositionLocalProvider(LocalContentColor provides colors.itemContentColor) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = ElegantSidebarDefaults.ItemHorizontalPadding,
                            vertical = SidebarSlotVerticalPadding,
                        ),
                ) {
                    footer()
                }
            }
        }
    }
}

internal fun resolveSidebarColors(themeColors: ElegantColors): ElegantSidebarColors =
    ElegantSidebarColors(
        containerColor = Color.Transparent,
        itemContentColor = themeColors.textSecondary,
        selectedItemContentColor = themeColors.interactivePrimary,
        selectedItemContainerColor = themeColors.interactivePrimary.copy(alpha = 0.10f),
        hoveredItemContentColor = themeColors.textPrimary,
        hoveredItemContainerColor = themeColors.surfaceHover,
        disabledItemContentColor = themeColors.textTertiary,
    )

internal fun resolveItemColor(
    colors: ElegantSidebarColors,
    selected: Boolean,
    hovered: Boolean,
    enabled: Boolean,
): Color = when {
    !enabled -> colors.disabledItemContentColor
    selected -> colors.selectedItemContentColor
    hovered -> colors.hoveredItemContentColor
    else -> colors.itemContentColor
}

internal fun resolveItemContainer(
    colors: ElegantSidebarColors,
    selected: Boolean,
    hovered: Boolean,
): Color = when {
    selected -> colors.selectedItemContainerColor
    hovered -> colors.hoveredItemContainerColor
    else -> colors.containerColor
}
