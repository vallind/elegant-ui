package com.elegant.compose.ui.switchgroup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.elegant.compose.ui.switch.ElegantSwitch
import com.elegant.compose.ui.foundation.theme.ElegantColors
import com.elegant.compose.ui.foundation.theme.ElegantTheme

/**
 * Stable data model describing one switch row rendered by [ElegantSwitchGroup].
 *
 * @property text label rendered on the switch row.
 * @property value stable identifier matched against the group's selected values.
 * @property enabled whether this item accepts user interaction; a disabled item stays visible
 *   but never invokes [ElegantSwitchGroup.onToggle].
 */
@Immutable
public data class ElegantSwitchGroupItem(
    val text: String,
    val value: String,
    val enabled: Boolean = true,
)

/**
 * Theme-aware text colors used by [ElegantSwitchGroup].
 *
 * Use [ElegantSwitchGroupDefaults.colors] for theme-aware defaults, then use [copy] for
 * supported product-level customization.
 *
 * @property labelColor color of the labels rendered on the switch rows.
 * @property supportingTextColor color of the supporting text below the rows.
 * @property disabledLabelColor color of the labels on disabled rows.
 */
@Immutable
public data class ElegantSwitchGroupColors(
    val labelColor: Color,
    val supportingTextColor: Color,
    val disabledLabelColor: Color,
)

/** Theme-aware defaults for [ElegantSwitchGroup]. */
public object ElegantSwitchGroupDefaults {
    /** Vertical gap between consecutive switch rows. */
    public val ItemGap: Dp = 4.dp

    /** Returns theme-aware text colors. */
    @Composable
    public fun colors(): ElegantSwitchGroupColors = resolveSwitchGroupColors(ElegantTheme.colors)
}

/**
 * Presents a vertical group of related switches with one shared controlled selection state.
 *
 * [selectedValues] is owned by the caller: [onToggle] is invoked with the item value and the
 * requested selection state, and the caller must write back a new set to keep the group
 * responsive. Prefer an immutable [Set] and copy it when toggling, for example
 * `if (checked) values + value else values - value`.
 *
 * Each [items] entry renders as a full-width row with its label at the start and an
 * [ElegantSwitch] at the end, keeping the switch's 48dp minimum interactive height and
 * Role.Switch semantics. A row is interactive only when [enabled] and the item's
 * [ElegantSwitchGroupItem.enabled] both allow it; disabled rows never invoke [onToggle].
 * When [supportingText] is provided it renders below the rows with the bodyMedium style and
 * [ElegantSwitchGroupColors.supportingTextColor].
 *
 * @param selectedValues values currently selected, owned by the caller.
 * @param onToggle callback invoked with the item value and the requested selection state.
 * @param items item models rendered as switch rows; an empty list renders nothing.
 * @param modifier modifier applied once to the group root.
 * @param enabled whether the whole group accepts user interaction.
 * @param colors theme-aware text colors.
 * @param supportingText optional supporting text rendered below the rows.
 */
@Composable
public fun ElegantSwitchGroup(
    selectedValues: Set<String>,
    onToggle: (String, Boolean) -> Unit,
    items: List<ElegantSwitchGroupItem>,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    colors: ElegantSwitchGroupColors = ElegantSwitchGroupDefaults.colors(),
    supportingText: String? = null,
) {
    if (items.isEmpty()) return
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(ElegantSwitchGroupDefaults.ItemGap),
    ) {
        items.forEach { item ->
            val checked = isChecked(selectedValues, item.value)
            val interactive = canToggle(item.enabled, enabled)
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = item.text,
                    modifier = Modifier.weight(1f),
                    style = ElegantTheme.typography.labelMedium,
                    color = if (interactive) colors.labelColor else colors.disabledLabelColor,
                )
                ElegantSwitch(
                    checked = checked,
                    onCheckedChange = { requested ->
                        if (interactive) onToggle(item.value, requested)
                    },
                    enabled = interactive,
                )
            }
        }
        if (supportingText != null) {
            Text(
                text = supportingText,
                style = ElegantTheme.typography.bodyMedium,
                color = colors.supportingTextColor,
            )
        }
    }
}

/** Resolves theme-aware text colors for [ElegantSwitchGroup]. */
internal fun resolveSwitchGroupColors(themeColors: ElegantColors): ElegantSwitchGroupColors =
    ElegantSwitchGroupColors(
        labelColor = themeColors.textPrimary,
        supportingTextColor = themeColors.textSecondary,
        disabledLabelColor = themeColors.textTertiary,
    )

/** Returns whether [itemValue] is present in [selectedValues]. */
internal fun isChecked(selectedValues: Set<String>, itemValue: String): Boolean =
    itemValue in selectedValues

/** Returns whether the item accepts a toggle given both its own and the group state. */
internal fun canToggle(itemEnabled: Boolean, groupEnabled: Boolean): Boolean =
    itemEnabled && groupEnabled
