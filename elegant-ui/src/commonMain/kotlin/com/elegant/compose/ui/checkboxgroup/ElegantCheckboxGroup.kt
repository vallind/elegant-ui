package com.elegant.compose.ui.checkboxgroup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.elegant.compose.ui.checkbox.ElegantCheckbox
import com.elegant.compose.ui.foundation.theme.ElegantColors
import com.elegant.compose.ui.foundation.theme.ElegantTheme

/**
 * Stable data model describing one checkbox row rendered by [ElegantCheckboxGroup].
 *
 * @property text label rendered on the checkbox row.
 * @property value stable identifier matched against the group's selected values.
 * @property enabled whether this item accepts user interaction; a disabled item stays visible
 *   but never invokes [ElegantCheckboxGroup.onToggle].
 */
@Immutable
public data class ElegantCheckboxGroupItem(
    val text: String,
    val value: String,
    val enabled: Boolean = true,
)

/**
 * Theme-aware text colors used by [ElegantCheckboxGroup].
 *
 * Use [ElegantCheckboxGroupDefaults.colors] for theme-aware defaults, then use [copy] for
 * supported product-level customization.
 *
 * @property labelColor palette of the labels rendered by the checkbox rows.
 * @property supportingTextColor color of the supporting text below the rows.
 * @property disabledLabelColor palette of the labels on disabled rows.
 */
@Immutable
public data class ElegantCheckboxGroupColors(
    val labelColor: Color,
    val supportingTextColor: Color,
    val disabledLabelColor: Color,
)

/** Theme-aware defaults for [ElegantCheckboxGroup]. */
public object ElegantCheckboxGroupDefaults {
    /** Vertical gap between consecutive checkbox rows. */
    public val ItemGap: Dp = 4.dp

    /** Returns theme-aware text colors. */
    @Composable
    public fun colors(): ElegantCheckboxGroupColors = resolveCheckboxGroupColors(ElegantTheme.colors)
}

/**
 * Presents a vertical group of related checkboxes with one shared controlled selection state.
 *
 * [selectedValues] is owned by the caller: [onToggle] is invoked with the item value and the
 * requested selection state, and the caller must write back a new set to keep the group
 * responsive. Prefer an immutable [Set] and copy it when toggling, for example
 * `if (checked) values + value else values - value`.
 *
 * Each [items] entry renders as a full checkbox row with its own Role.Checkbox semantics and
 * 48dp minimum interactive height. A row is interactive only when [enabled] and the item's
 * [ElegantCheckboxGroupItem.enabled] both allow it; disabled rows never invoke [onToggle].
 * When [supportingText] is provided it renders below the rows with the bodyMedium style and
 * [ElegantCheckboxGroupColors.supportingTextColor]. The label palette resolves from the active
 * theme, keeping row labels aligned with the [colors] contract by construction.
 *
 * @param selectedValues values currently selected, owned by the caller.
 * @param onToggle callback invoked with the item value and the requested selection state.
 * @param items item models rendered as checkbox rows; an empty list renders nothing.
 * @param modifier modifier applied once to the group root.
 * @param enabled whether the whole group accepts user interaction.
 * @param colors theme-aware text colors.
 * @param supportingText optional supporting text rendered below the rows.
 */
@Composable
public fun ElegantCheckboxGroup(
    selectedValues: Set<String>,
    onToggle: (String, Boolean) -> Unit,
    items: List<ElegantCheckboxGroupItem>,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    colors: ElegantCheckboxGroupColors = ElegantCheckboxGroupDefaults.colors(),
    supportingText: String? = null,
) {
    if (items.isEmpty()) return
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(ElegantCheckboxGroupDefaults.ItemGap),
    ) {
        items.forEach { item ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                ElegantCheckbox(
                    checked = isChecked(selectedValues, item.value),
                    onCheckedChange = { checked ->
                        if (canToggle(item.enabled, enabled)) onToggle(item.value, checked)
                    },
                    enabled = canToggle(item.enabled, enabled),
                    label = item.text,
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

/** Resolves theme-aware text colors for [ElegantCheckboxGroup]. */
internal fun resolveCheckboxGroupColors(themeColors: ElegantColors): ElegantCheckboxGroupColors =
    ElegantCheckboxGroupColors(
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
