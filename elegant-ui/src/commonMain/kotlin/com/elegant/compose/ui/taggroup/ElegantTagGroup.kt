package com.elegant.compose.ui.taggroup

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.disabled
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.selected
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.elegant.compose.ui.foundation.theme.ElegantColors
import com.elegant.compose.ui.foundation.theme.ElegantRadius
import com.elegant.compose.ui.foundation.theme.ElegantTheme

/**
 * Stable data model describing one selectable tag chip rendered by [ElegantTagGroup].
 *
 * @property text label rendered on the tag chip.
 * @property value stable identifier matched against the group's selected values.
 * @property enabled whether this item accepts user interaction; a disabled item stays visible
 *   but never invokes [ElegantTagGroup.onToggle].
 */
@Immutable
public data class ElegantTagGroupItem(
    val text: String,
    val value: String,
    val enabled: Boolean = true,
)

/**
 * Theme-aware state colors used by [ElegantTagGroup].
 *
 * Use [ElegantTagGroupDefaults.colors] for theme-aware defaults, then use [copy] for supported
 * product-level customization.
 *
 * @property selectedContainerColor container color of a selected chip pill.
 * @property selectedContentColor content color of a selected chip pill.
 * @property unselectedContainerColor container color of an unselected chip pill.
 * @property unselectedContentColor content color of an unselected chip pill.
 * @property unselectedBorderColor border color of an unselected chip pill.
 * @property disabledContentColor content color of a disabled chip pill.
 */
@Immutable
public data class ElegantTagGroupColors(
    val selectedContainerColor: Color,
    val selectedContentColor: Color,
    val unselectedContainerColor: Color,
    val unselectedContentColor: Color,
    val unselectedBorderColor: Color,
    val disabledContentColor: Color,
)

/** Theme-aware defaults for [ElegantTagGroup]. */
public object ElegantTagGroupDefaults {
    /** Gap between chip pills in both the horizontal and vertical flow axes. */
    public val ItemGap: Dp = 8.dp

    /** Returns theme-aware chip colors. */
    @Composable
    public fun colors(): ElegantTagGroupColors = resolveTagGroupColors(ElegantTheme.colors)
}

/** Minimum interactive root height used by every chip. */
internal val TagGroupMinimumTouchHeight: Dp = 48.dp

/** Optical pill height of every chip. */
internal val TagGroupChipHeight: Dp = 28.dp

/** Horizontal content padding inside the chip pill. */
internal val TagGroupChipHorizontalPadding: Dp = 10.dp

/** Border width of an unselected chip pill. */
internal val TagGroupChipBorderWidth: Dp = 1.dp

/**
 * Presents a wrapping row of related selectable tag chips with one shared controlled selection
 * state.
 *
 * [selectedValues] is owned by the caller: [onToggle] is invoked with the item value and the
 * requested selection state, and the caller must write back a new set to keep the group
 * responsive. Prefer an immutable [Set] and copy it when toggling, for example
 * `if (checked) values + value else values - value`.
 *
 * Each [items] entry renders as a compact pill inside a 48dp minimum interactive target. A
 * selected chip fills with the selected palette, an unselected chip stays transparent with a
 * border, and a disabled chip keeps its pill while dimming the label. Chips announce
 * [Role.Button] and their `selected` state, and [FlowRow] wraps them automatically on the
 * [ElegantTagGroupDefaults.ItemGap] rhythm. A chip is interactive only when [enabled] and the
 * item's [ElegantTagGroupItem.enabled] both allow it; disabled chips never invoke [onToggle].
 *
 * @param selectedValues values currently selected, owned by the caller.
 * @param onToggle callback invoked with the item value and the requested selection state.
 * @param items item models rendered as selectable tag chips; an empty list renders nothing.
 * @param modifier modifier applied once to the group root.
 * @param enabled whether the whole group accepts user interaction.
 * @param colors theme-aware chip colors.
 */
@Composable
public fun ElegantTagGroup(
    selectedValues: Set<String>,
    onToggle: (String, Boolean) -> Unit,
    items: List<ElegantTagGroupItem>,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    colors: ElegantTagGroupColors = ElegantTagGroupDefaults.colors(),
) {
    if (items.isEmpty()) return
    val shape: Shape = RoundedCornerShape(ElegantRadius.full)
    FlowRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(ElegantTagGroupDefaults.ItemGap),
        verticalArrangement = Arrangement.spacedBy(ElegantTagGroupDefaults.ItemGap),
    ) {
        items.forEach { item ->
            val checked = isChecked(selectedValues, item.value)
            val interactive = canToggle(item.enabled, enabled)
            val containerColor = if (checked) {
                colors.selectedContainerColor
            } else {
                colors.unselectedContainerColor
            }
            val contentColor = when {
                !interactive -> colors.disabledContentColor
                checked -> colors.selectedContentColor
                else -> colors.unselectedContentColor
            }
            val borderColor = if (checked) Color.Transparent else colors.unselectedBorderColor
            Box(
                modifier = Modifier
                    .semantics(mergeDescendants = true) {
                        role = Role.Button
                        if (!interactive) disabled()
                        this.selected = checked
                    }
                    .defaultMinSize(minHeight = TagGroupMinimumTouchHeight)
                    .clickable(
                        enabled = interactive,
                        interactionSource = remember { MutableInteractionSource() },
                        indication = LocalIndication.current,
                        onClick = { onToggle(item.value, !checked) },
                    ),
                contentAlignment = Alignment.Center,
            ) {
                Box(
                    modifier = Modifier
                        .defaultMinSize(minHeight = TagGroupChipHeight)
                        .clip(shape)
                        .background(containerColor)
                        .border(
                            border = BorderStroke(TagGroupChipBorderWidth, borderColor),
                            shape = shape,
                        )
                        .padding(horizontal = TagGroupChipHorizontalPadding),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = item.text,
                        style = ElegantTheme.typography.labelSmall,
                        color = contentColor,
                    )
                }
            }
        }
    }
}

/** Resolves theme-aware chip colors for [ElegantTagGroup]. */
internal fun resolveTagGroupColors(themeColors: ElegantColors): ElegantTagGroupColors =
    ElegantTagGroupColors(
        selectedContainerColor = themeColors.interactivePrimary,
        selectedContentColor = themeColors.textInverse,
        unselectedContainerColor = Color.Transparent,
        unselectedContentColor = themeColors.textPrimary,
        unselectedBorderColor = themeColors.borderStrong,
        disabledContentColor = themeColors.textTertiary,
    )

/** Returns whether [itemValue] is present in [selectedValues]. */
internal fun isChecked(selectedValues: Set<String>, itemValue: String): Boolean =
    itemValue in selectedValues

/** Returns whether the item accepts a toggle given both its own and the group state. */
internal fun canToggle(itemEnabled: Boolean, groupEnabled: Boolean): Boolean =
    itemEnabled && groupEnabled
