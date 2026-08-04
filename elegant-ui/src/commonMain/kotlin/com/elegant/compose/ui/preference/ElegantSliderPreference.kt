package com.elegant.compose.ui.preference

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.elegant.compose.ui.slider.ElegantSlider
import com.elegant.compose.ui.basiccomponent.ElegantBasicComponent
import com.elegant.compose.ui.basiccomponent.ElegantBasicComponentColors
import com.elegant.compose.ui.theme.ElegantColors
import com.elegant.compose.ui.theme.ElegantSpacing
import com.elegant.compose.ui.theme.ElegantTheme

/**
 * State colors used by [ElegantSliderPreference].
 *
 * Use [ElegantSliderPreferenceDefaults.colors] for theme-aware defaults, then use [copy] for
 * supported product-level customization.
 *
 * @property titleColor color of the title while the preference is enabled.
 * @property supportingTextColor color of the guidance text below the title.
 * @property valueColor color of the formatted value while the preference is enabled.
 * @property disabledTitleColor color of the title and the formatted value while the preference is
 *   disabled.
 * @property dividerColor color of the hairline divider below the preference.
 */
@Immutable
public data class ElegantSliderPreferenceColors(
    val titleColor: Color,
    val supportingTextColor: Color,
    val valueColor: Color,
    val disabledTitleColor: Color,
    val dividerColor: Color,
)

/** Theme-aware defaults for [ElegantSliderPreference]. */
public object ElegantSliderPreferenceDefaults {
    /** Minimum height of the preference content row. */
    public val MinimumTouchHeight: Dp = 56.dp

    /** Returns theme-aware text and divider colors. */
    @Composable
    public fun colors(): ElegantSliderPreferenceColors = resolveSliderPreferenceColors(ElegantTheme.colors)
}

/** Standard content padding around the preference row. */
internal val SliderPreferenceContentPadding: Dp = ElegantSpacing.xl

/** Horizontal gap between the title and the formatted value. */
internal val SliderPreferenceTitleValueGap: Dp = ElegantSpacing.md

/** Horizontal inset of the bottom divider from the row edges. */
internal val SliderPreferenceDividerInset: Dp = ElegantSpacing.xl

/** Visual thickness of the bottom hairline divider. */
internal val SliderPreferenceDividerThickness: Dp = 1.dp

/**
 * Presents a settings-style preference whose bounded value is edited with a full-width slider.
 *
 * The preference renders a title row with [title] on the leading side and the formatted current
 * [value] on the trailing side, an optional [supportingText] below the title (hidden when blank),
 * and an [ElegantSlider] beneath the row. The value is controlled: every interaction is reported
 * through [onValueChange] and the parent must update [value] to keep the slider responsive.
 * [valueFormatter] renders the current value; it is caller-owned and called on every recomposition
 * of the title row.
 *
 * The title row itself is not clickable; the [ElegantSlider] owns all pointer and keyboard
 * interaction, including its own 48dp interactive root and [androidx.compose.ui.semantics.Role.Slider]
 * announcement. When [showDivider] is true a hairline divider is drawn below the row, inset by
 * 16dp on both sides.
 *
 * @param title label shown at the start of the title row.
 * @param value current slider value; values outside [valueRange] are clamped and NaN renders at
 *   the start of the range.
 * @param onValueChange callback invoked with the resolved value after user interaction.
 * @param modifier modifier applied once to the preference root.
 * @param supportingText optional guidance shown below the title row; blank text is hidden.
 * @param valueRange range the value is constrained to.
 * @param steps number of discrete snap positions between the range endpoints; zero keeps the
 *   slider continuous.
 * @param valueFormatter formats the current value for display at the end of the title row.
 * @param enabled whether the slider accepts user interaction.
 * @param colors theme-aware text and divider colors.
 * @param showDivider whether a hairline divider is drawn below the preference.
 */
@Composable
public fun ElegantSliderPreference(
    title: String,
    value: Float,
    onValueChange: (Float) -> Unit,
    modifier: Modifier = Modifier,
    supportingText: String? = null,
    valueRange: ClosedFloatingPointRange<Float> = 0f..1f,
    steps: Int = 0,
    valueFormatter: (Float) -> String = { it.toString() },
    enabled: Boolean = true,
    colors: ElegantSliderPreferenceColors = ElegantSliderPreferenceDefaults.colors(),
    showDivider: Boolean = true,
){
    Column(modifier = modifier) {
        ElegantBasicComponent(
            title = title,
            summary = resolveSupportingText(supportingText),
            endActions = {
                Text(
                    text = valueFormatter(value),
                    style = ElegantTheme.typography.labelMedium,
                    color = resolveSliderPreferenceValueColor(colors, enabled),
                )
            },
            enabled = enabled,
            colors = colors.toBasicComponentColors(),
            insideMargin = PaddingValues(SliderPreferenceContentPadding),
            bottomAction = {
                ElegantSlider(
                    value = value,
                    onValueChange = onValueChange,
                    modifier = Modifier.fillMaxWidth(),
                    enabled = enabled,
                    valueRange = valueRange,
                    steps = steps,
                )
            },
        )
        if (showDivider) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = SliderPreferenceDividerInset)
                    .height(SliderPreferenceDividerThickness)
                    .background(colors.dividerColor),
            )
        }
    }
}


/**
 * Resolves theme-aware [ElegantSliderPreferenceColors] from [themeColors].
 */
internal fun ElegantSliderPreferenceColors.toBasicComponentColors(): ElegantBasicComponentColors =
    ElegantBasicComponentColors(
        containerColor = Color.Transparent,
        titleColor = titleColor,
        summaryColor = supportingTextColor,
        disabledTitleColor = disabledTitleColor,
        disabledSummaryColor = supportingTextColor,
    )

internal fun resolveSliderPreferenceColors(themeColors: ElegantColors): ElegantSliderPreferenceColors =
    ElegantSliderPreferenceColors(
        titleColor = themeColors.textPrimary,
        supportingTextColor = themeColors.textSecondary,
        valueColor = themeColors.textSecondary,
        disabledTitleColor = themeColors.textTertiary,
        dividerColor = themeColors.borderDefault,
    )

/**
 * Resolves the title color for the given [enabled] state.
 */
internal fun resolveSliderPreferenceTitleColor(
    colors: ElegantSliderPreferenceColors,
    enabled: Boolean,
): Color = if (enabled) colors.titleColor else colors.disabledTitleColor

/**
 * Resolves the formatted-value color for the given [enabled] state.
 */
internal fun resolveSliderPreferenceValueColor(
    colors: ElegantSliderPreferenceColors,
    enabled: Boolean,
): Color = if (enabled) colors.valueColor else colors.disabledTitleColor

