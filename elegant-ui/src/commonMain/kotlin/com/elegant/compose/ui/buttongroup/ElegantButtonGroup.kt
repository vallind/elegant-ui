package com.elegant.compose.ui.buttongroup

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.disabled
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.selected
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.elegant.compose.ui.foundation.animation.elegantFolmeSpring
import com.elegant.compose.ui.iconbutton.ElegantIcon
import com.elegant.compose.ui.foundation.theme.ElegantColors
import com.elegant.compose.ui.foundation.theme.ElegantMotion
import com.elegant.compose.ui.foundation.theme.ElegantRadius
import com.elegant.compose.ui.foundation.theme.ElegantSpacing
import com.elegant.compose.ui.foundation.theme.ElegantTheme

/**
 * Stable data model describing one cell rendered by [ElegantButtonGroup].
 *
 * @property text label rendered with the labelMedium style.
 * @property enabled whether this cell accepts user interaction; a disabled cell stays visible and
 *   keeps its slot but never invokes [ElegantButtonGroup.onSelect].
 */
@Immutable
public data class ElegantButtonGroupItem(
    val text: String,
    val enabled: Boolean = true,
    val icon: ImageVector? = null,
)

/**
 * Theme-aware state colors used by [ElegantButtonGroup].
 *
 * Use [ElegantButtonGroupDefaults.colors] for theme-aware defaults, then use [copy] for supported
 * product-level customization.
 *
 * @property containerColor background of the segmented control behind every cell.
 * @property contentColor resting label color.
 * @property borderColor 1dp outer border and inter-cell divider color.
 * @property selectedContainerColor background of the selected cell.
 * @property selectedContentColor label color of the selected cell.
 * @property hoveredContainerColor background while a pointer hovers an unselected cell.
 * @property pressedContainerColor background while an unselected cell is pressed.
 * @property disabledContainerColor background of a cell that cannot be interacted with.
 * @property disabledContentColor label color of a cell that cannot be interacted with.
 */
@Immutable
public data class ElegantButtonGroupColors(
    val containerColor: Color,
    val contentColor: Color,
    val borderColor: Color,
    val selectedContainerColor: Color,
    val selectedContentColor: Color,
    val hoveredContainerColor: Color = containerColor,
    val pressedContainerColor: Color = containerColor,
    val disabledContainerColor: Color = containerColor,
    val disabledContentColor: Color = contentColor,
)

/** Theme-aware defaults for [ElegantButtonGroup]. */
public object ElegantButtonGroupDefaults {
    /** Minimum interactive root height used by every cell. */
    public val MinimumTouchHeight: Dp = 48.dp

    /** Visual height of the segmented control. */
    public val Height: Dp = 36.dp

    /** Horizontal padding reserved inside each cell. */
    public val HorizontalPadding: Dp = 12.dp

    /** Standard state-transition duration. */
    public const val AnimationDurationMillis: Int = ElegantMotion.standardDurationMillis

    /** Returns theme-aware state colors. */
    @Composable
    public fun colors(): ElegantButtonGroupColors = resolveButtonGroupColors(ElegantTheme.colors)
}

/**
 * Presents a segmented control that switches between mutually exclusive options.
 *
 * [selectedIndex] is controlled: the caller must update it from [onSelect] to keep the group
 * responsive. Pass `null` to show no selection; out-of-range values clamp to the closest cell,
 * and an empty [items] list renders nothing. Every cell takes an equal share of the row width,
 * and the group is outlined by a 1dp border with matching vertical dividers between cells.
 *
 * Each cell announces [Role.RadioButton] with its selected and disabled state and never invokes
 * [onSelect] while the group or the cell is disabled. The cell background follows the precedence
 * disabled, selected, pressed, hovered, resting (transparent); the label color follows disabled,
 * selected, resting.
 *
 * @param selectedIndex index of the selected cell, or `null` for no selection.
 * @param onSelect callback invoked with the newly selected index.
 * @param items cell models rendered by the group; an empty list renders nothing.
 * @param modifier modifier applied once to the group root.
 * @param enabled whether the group accepts user interaction.
 * @param colors theme-aware state colors.
 */
@Composable
public fun ElegantButtonGroup(
    selectedIndex: Int?,
    onSelect: (Int) -> Unit,
    items: List<ElegantButtonGroupItem>,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    colors: ElegantButtonGroupColors = ElegantButtonGroupDefaults.colors(),
) {
    if (items.isEmpty()) return
    val resolvedSelectedIndex = resolveSelectedIndex(selectedIndex, items.size)
    val shape = RoundedCornerShape(ElegantRadius.sm)

    Row(
        modifier = modifier
            .clip(shape)
            .background(colors.containerColor)
            .border(
                border = BorderStroke(1.dp, colors.borderColor),
                shape = shape,
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        items.forEachIndexed { index, item ->
            if (index > 0) {
                Box(
                    modifier = Modifier
                        .width(1.dp)
                        .fillMaxHeight()
                        .background(colors.borderColor),
                )
            }
            ElegantButtonGroupCell(
                text = item.text,
                icon = item.icon,
                selected = index == resolvedSelectedIndex,
                enabled = enabled && item.enabled,
                colors = colors,
                onSelect = { onSelect(index) },
                modifier = Modifier.weight(1f),
            )
        }
    }
}

@Composable
private fun ElegantButtonGroupCell(
    text: String,
    icon: ImageVector?,
    selected: Boolean,
    enabled: Boolean,
    colors: ElegantButtonGroupColors,
    onSelect: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val hovered by interactionSource.collectIsHoveredAsState()
    val pressed by interactionSource.collectIsPressedAsState()

    val animatedContainer by animateColorAsState(
        targetValue = resolveCellContainerColor(
            colors = colors,
            enabled = enabled,
            selected = selected,
            pressed = pressed,
            hovered = hovered,
        ),
        animationSpec = elegantFolmeSpring(dampingRatio = 1.0f, responseSeconds = 0.3f),
        label = "ElegantButtonGroupContainer",
    )
    val animatedContent by animateColorAsState(
        targetValue = resolveCellContentColor(
            colors = colors,
            enabled = enabled,
            selected = selected,
        ),
        animationSpec = elegantFolmeSpring(dampingRatio = 1.0f, responseSeconds = 0.3f),
        label = "ElegantButtonGroupContent",
    )

    Box(
        modifier = modifier
            .defaultMinSize(minHeight = ElegantButtonGroupDefaults.MinimumTouchHeight)
            .background(animatedContainer)
            .clickable(
                enabled = enabled,
                role = Role.RadioButton,
                interactionSource = interactionSource,
                indication = LocalIndication.current,
                onClick = onSelect,
            )
            .semantics {
                role = Role.RadioButton
                this.selected = selected
                if (!enabled) disabled()
            },
        contentAlignment = Alignment.Center,
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(ElegantSpacing.xs),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            if (icon != null) {
                ElegantIcon(
                    icon = icon,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp),
                    tint = animatedContent,
                )
            }
            Text(
                text = text,
                style = ElegantTheme.typography.labelMedium,
                color = animatedContent,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}

private fun resolveCellContainerColor(
    colors: ElegantButtonGroupColors,
    enabled: Boolean,
    selected: Boolean,
    pressed: Boolean,
    hovered: Boolean,
): Color = when {
    !enabled -> colors.disabledContainerColor
    selected -> colors.selectedContainerColor
    pressed -> colors.pressedContainerColor
    hovered -> colors.hoveredContainerColor
    else -> Color.Transparent
}

private fun resolveCellContentColor(
    colors: ElegantButtonGroupColors,
    enabled: Boolean,
    selected: Boolean,
): Color = when {
    !enabled -> colors.disabledContentColor
    selected -> colors.selectedContentColor
    else -> colors.contentColor
}

/** Resolves theme-aware state colors for [ElegantButtonGroup]. */
internal fun resolveButtonGroupColors(
    themeColors: ElegantColors,
): ElegantButtonGroupColors = ElegantButtonGroupColors(
    containerColor = themeColors.surfaceRaised,
    contentColor = themeColors.textPrimary,
    borderColor = themeColors.borderDefault,
    selectedContainerColor = themeColors.backgroundSubtle,
    selectedContentColor = themeColors.interactivePrimary,
    hoveredContainerColor = themeColors.surfaceHover,
    pressedContainerColor = themeColors.surfaceSunken,
    disabledContainerColor = themeColors.surfaceSunken,
    disabledContentColor = themeColors.textTertiary,
)

/** Clamps [selectedIndex] into the item range, or resolves to `null` for an empty group or a
 * `null` selection. */
internal fun resolveSelectedIndex(
    selectedIndex: Int?,
    itemCount: Int,
): Int? = when {
    itemCount <= 0 || selectedIndex == null -> null
    else -> selectedIndex.coerceIn(0, itemCount - 1)
}
