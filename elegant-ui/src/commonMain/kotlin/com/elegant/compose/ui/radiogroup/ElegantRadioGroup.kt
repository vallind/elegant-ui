package com.elegant.compose.ui.radiogroup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import com.elegant.compose.ui.radio.ElegantRadio
import com.elegant.compose.ui.theme.ElegantColors
import com.elegant.compose.ui.theme.ElegantSpacing
import com.elegant.compose.ui.theme.ElegantTheme

/**
 * Stable model for one selectable option rendered by [ElegantRadioGroup].
 *
 * @property text label shown next to the option's radio indicator.
 * @property value value reported to [ElegantRadioGroup.onSelect] when the option is picked.
 * @property enabled whether this option accepts interaction; disabled options keep their
 *   selection visible but never report to [ElegantRadioGroup.onSelect].
 */
@Immutable
public data class ElegantRadioGroupItem(
    val text: String,
    val value: String,
    val enabled: Boolean = true,
)

/**
 * Text colors used by [ElegantRadioGroup].
 *
 * Use [ElegantRadioGroupDefaults.colors] for theme-aware defaults, then use [copy] for supported
 * product-level customization. The item labels are drawn by the item radios with the theme text
 * colors; [labelColor] and [disabledLabelColor] mirror those colors so a themed group stays
 * consistent with what the radios render.
 *
 * @property labelColor color of the item labels while the group is enabled.
 * @property supportingTextColor color of the supporting text while the group is enabled.
 * @property disabledLabelColor color of the supporting text while the group is disabled; mirrors
 *   the item-label color the radios draw while disabled.
 */
@Immutable
public data class ElegantRadioGroupColors(
    val labelColor: Color,
    val supportingTextColor: Color,
    val disabledLabelColor: Color,
)

/** Theme-aware defaults for [ElegantRadioGroup]. */
public object ElegantRadioGroupDefaults {
    /** Vertical spacing between item rows. */
    public val ItemGap: Dp = ElegantSpacing.xs

    /** Returns theme-aware text colors. */
    @Composable
    public fun colors(): ElegantRadioGroupColors = resolveRadioGroupColors(ElegantTheme.colors)
}

/**
 * Presents an ordered column of [ElegantRadio] rows that share one exclusive selection.
 *
 * Each item is rendered as an [ElegantRadio] with its own 48dp minimum interactive row, so the
 * group only adds the vertical rhythm between rows and the optional [supportingText] below them.
 * Selection is fully controlled: [selectedValue] must hold the [ElegantRadioGroupItem.value] of
 * the chosen item, and [onSelect] reports the value of the item the user picked. A null or blank
 * [selectedValue] selects nothing. Item values should be unique; when two items share a value,
 * both render selected. Disabled items, and every item while [enabled] is false, never invoke
 * [onSelect].
 *
 * @param selectedValue value of the selected item, or null or blank when nothing is selected.
 * @param onSelect callback invoked with the value of the item the user picked.
 * @param items ordered options rendered as radio rows.
 * @param modifier modifier applied once to the column of rows.
 * @param enabled whether the group accepts user interaction.
 * @param colors theme-aware text colors.
 * @param supportingText optional guidance shown below the items.
 */
@Composable
public fun ElegantRadioGroup(
    selectedValue: String?,
    onSelect: (String) -> Unit,
    items: List<ElegantRadioGroupItem>,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    colors: ElegantRadioGroupColors = ElegantRadioGroupDefaults.colors(),
    supportingText: String? = null,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(ElegantRadioGroupDefaults.ItemGap),
    ) {
        for (item in items) {
            ElegantRadio(
                selected = isItemSelected(selectedValue, item.value),
                onSelect = { if (canSelect(item.enabled, enabled)) onSelect(item.value) },
                enabled = enabled && item.enabled,
                label = item.text,
            )
        }
        if (supportingText != null) {
            Text(
                text = supportingText,
                modifier = Modifier.fillMaxWidth(),
                style = ElegantTheme.typography.bodyMedium,
                color = resolveSupportingTextColor(colors, enabled),
            )
        }
    }
}

internal fun resolveRadioGroupColors(themeColors: ElegantColors): ElegantRadioGroupColors =
    ElegantRadioGroupColors(
        labelColor = themeColors.textPrimary,
        supportingTextColor = themeColors.textSecondary,
        disabledLabelColor = themeColors.textTertiary,
    )

internal fun isItemSelected(selectedValue: String?, itemValue: String): Boolean =
    !selectedValue.isNullOrBlank() && selectedValue == itemValue

internal fun canSelect(itemEnabled: Boolean, groupEnabled: Boolean): Boolean =
    itemEnabled && groupEnabled

internal fun resolveSupportingTextColor(
    colors: ElegantRadioGroupColors,
    enabled: Boolean,
): Color = if (enabled) colors.supportingTextColor else colors.disabledLabelColor
