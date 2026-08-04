package com.elegant.compose.ui.preference

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.elegant.compose.ui.checkbox.ElegantCheckbox
import com.elegant.compose.ui.basiccomponent.ElegantBasicComponent
import com.elegant.compose.ui.basiccomponent.ElegantBasicComponentColors
import com.elegant.compose.ui.theme.ElegantColors
import com.elegant.compose.ui.theme.ElegantSpacing
import com.elegant.compose.ui.theme.ElegantTheme

/**
 * Theme-aware colors used by [ElegantCheckboxPreference].
 *
 * Use [ElegantCheckboxPreferenceDefaults.colors] for theme-aware defaults, then use [copy] for
 * supported product-level customization.
 *
 * @property containerColor preference row container color.
 * @property titleColor enabled title color.
 * @property supportingTextColor supporting text color.
 * @property disabledTitleColor disabled title color.
 * @property dividerColor bottom divider color.
 */
@Immutable
public data class ElegantCheckboxPreferenceColors(
    val containerColor: Color,
    val titleColor: Color,
    val supportingTextColor: Color,
    val disabledTitleColor: Color,
    val dividerColor: Color,
)

/** Theme-aware defaults for [ElegantCheckboxPreference]. */
public object ElegantCheckboxPreferenceDefaults {
    /** Minimum interactive row height of the preference. */
    public val MinimumTouchHeight: Dp = 48.dp

    /** Returns theme-aware colors. */
    @Composable
    public fun colors(): ElegantCheckboxPreferenceColors =
        resolveCheckboxPreferenceColors(ElegantTheme.colors)
}

/** 16dp start inset shared by the row content and the bottom divider. */
internal val CheckboxPreferenceRowHorizontalPadding: Dp = ElegantSpacing.xl

/** 4dp vertical padding keeping a compact single-line row at the 48dp minimum. */
internal val CheckboxPreferenceRowVerticalPadding: Dp = ElegantSpacing.xs

/** 8dp gap between the title block and the trailing checkbox. */
internal val CheckboxPreferenceControlGap: Dp = ElegantSpacing.md

/** 1dp stroke height of the bottom divider. */
internal val CheckboxPreferenceDividerHeight: Dp = 1.dp

/** 16dp start inset of the bottom divider. */
internal val CheckboxPreferenceDividerStartInset: Dp = ElegantSpacing.xl

/**
 * Displays an Elegant UI checkbox preference row.
 *
 * The row keeps a 48dp minimum interactive height and lays out a title block on the start side
 * with an optional supporting text below the title, and a trailing [ElegantCheckbox] as the end
 * control. The row itself is not clickable: the embedded checkbox owns the [Role.Checkbox] toggle
 * semantics and never invokes [onCheckedChange] while disabled. An optional 1dp divider is drawn
 * at the bottom edge, inset 16dp from the start side.
 *
 * @param title the preference title.
 * @param checked whether the checkbox is selected.
 * @param onCheckedChange callback invoked with the requested selection state.
 * @param modifier modifier applied once to the preference row root.
 * @param supportingText optional supporting text rendered under the title; blank values are
 * omitted.
 * @param enabled whether user interaction is accepted.
 * @param colors theme-aware colors.
 * @param showDivider whether the bottom divider is rendered.
 */
@Composable
public fun ElegantCheckboxPreference(
    title: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    supportingText: String? = null,
    enabled: Boolean = true,
    colors: ElegantCheckboxPreferenceColors = ElegantCheckboxPreferenceDefaults.colors(),
    showDivider: Boolean = true,
) {
    val resolvedSupportingText = resolveSupportingText(supportingText)

    Column(modifier = modifier) {
        ElegantBasicComponent(
            title = title,
            summary = resolvedSupportingText,
            endActions = {
                ElegantCheckbox(
                    checked = checked,
                    onCheckedChange = onCheckedChange,
                    enabled = enabled,
                )
            },
            enabled = enabled,
            colors = colors.toBasicComponentColors(),
            insideMargin = PreferenceRowInsideMargin,
        )
        if (showDivider) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = CheckboxPreferenceDividerStartInset)
                    .height(CheckboxPreferenceDividerHeight)
                    .background(color = colors.dividerColor),
            )
        }
    }
}


/**
 * Resolves theme-aware colors for [ElegantCheckboxPreference] from [themeColors].
 *
 * The row is transparent by default, the title uses the primary text role, the supporting text
 * uses the secondary text role, the disabled title uses the tertiary text role, and the divider
 * uses the default border role.
 */
internal fun ElegantCheckboxPreferenceColors.toBasicComponentColors(): ElegantBasicComponentColors =
    ElegantBasicComponentColors(
        containerColor = containerColor,
        titleColor = titleColor,
        summaryColor = supportingTextColor,
        disabledTitleColor = disabledTitleColor,
        disabledSummaryColor = supportingTextColor,
    )

internal fun resolveCheckboxPreferenceColors(themeColors: ElegantColors): ElegantCheckboxPreferenceColors =
    ElegantCheckboxPreferenceColors(
        containerColor = Color.Transparent,
        titleColor = themeColors.textPrimary,
        supportingTextColor = themeColors.textSecondary,
        disabledTitleColor = themeColors.textTertiary,
        dividerColor = themeColors.borderDefault,
    )

/**
 * Normalizes an optional supporting text for [ElegantCheckboxPreference].
 *
 * Blank values, including empty and whitespace-only strings, are treated as absent and resolve to
 * null so no supporting text row is rendered.
 */
