package com.elegant.compose.ui.preference

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import com.elegant.compose.ui.switch.ElegantSwitch
import com.elegant.compose.ui.basiccomponent.ElegantBasicComponent
import com.elegant.compose.ui.basiccomponent.ElegantBasicComponentColors
import com.elegant.compose.ui.foundation.theme.ElegantColors
import com.elegant.compose.ui.foundation.theme.ElegantTheme

/**
 * Theme-aware colors used by [ElegantSwitchPreference].
 *
 * Use [ElegantPreferenceDefaults.colors] for theme-aware defaults, then use [copy] for supported
 * product-level customization.
 *
 * @property containerColor default row container color; transparent for the default look.
 * @property titleColor primary title text color.
 * @property supportingTextColor supporting-text color.
 * @property disabledTitleColor title color while [ElegantSwitchPreference.enabled] is false.
 * @property dividerColor bottom divider color.
 */
@Immutable
public data class ElegantPreferenceColors(
    val containerColor: Color,
    val titleColor: Color,
    val supportingTextColor: Color,
    val disabledTitleColor: Color,
    val dividerColor: Color,
)

/** Theme-aware defaults for [ElegantSwitchPreference]. */
public object ElegantPreferenceDefaults {
    /** Minimum row height kept by every preference row. */
    public val MinimumTouchHeight: Dp = 48.dp

    /** Returns theme-aware state colors for the default look. */
    @Composable
    public fun colors(): ElegantPreferenceColors = resolvePreferenceColors(
        themeColors = ElegantTheme.colors,
    )
}

internal object PreferenceMetrics {
    /** Row content inset from the start and end edges. */
    val RowHorizontalPadding: Dp = 16.dp

    /** Vertical gap between the title and the supporting text. */
    val TitleBlockSpacing: Dp = 2.dp

    /** Gap between the title block and the end control. */
    val EndControlGap: Dp = 8.dp

    /** Bottom divider start inset, aligned with the title block. */
    val DividerInsetStart: Dp = 16.dp

    /** Bottom divider stroke height. */
    val DividerHeight: Dp = 1.dp
}

/**
 * Renders a settings row with a start title block and an end-anchored switch.
 *
 * The row keeps a 48dp minimum height, fills the width of its container, and insets its content by
 * 16dp on the start and end edges. The title block stacks [title] in `labelMedium` typography over
 * an optional non-blank [supportingText] in `bodyMedium` typography with a 2dp gap. The end control
 * is an [ElegantSwitch] that owns the toggle interaction: only the switch is toggleable, the row
 * itself adds no role and never invokes [onCheckedChange] on tap. When [showDivider] is set, a 1dp
 * divider line inset by 16dp from the start edge closes the row.
 *
 * The component is fully controlled: [checked] defines the state and [onCheckedChange] is invoked
 * with the requested value whenever the switch is activated. While [enabled] is false the title
 * falls back to [ElegantPreferenceColors.disabledTitleColor] and the switch renders and announces
 * its disabled state through its own `Role.Switch` semantics without invoking the callback.
 *
 * @param title primary title text rendered in `labelMedium` typography.
 * @param checked whether the end switch is on.
 * @param onCheckedChange callback invoked with the requested state.
 * @param modifier modifier applied once to the row root.
 * @param supportingText optional secondary text rendered only when non-blank.
 * @param enabled whether user interaction is accepted.
 * @param colors theme-aware state colors.
 * @param showDivider whether a 1dp divider line closes the row bottom.
 */
@Composable
public fun ElegantSwitchPreference(
    title: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    supportingText: String? = null,
    enabled: Boolean = true,
    colors: ElegantPreferenceColors = ElegantPreferenceDefaults.colors(),
    showDivider: Boolean = true,
) {
    val resolvedSupportingText = resolveSupportingText(supportingText)

    Column(modifier = modifier.fillMaxWidth()) {
        ElegantBasicComponent(
            title = title,
            summary = resolvedSupportingText,
            endActions = {
                ElegantSwitch(
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
                    .padding(start = PreferenceMetrics.DividerInsetStart)
                    .height(PreferenceMetrics.DividerHeight)
                    .background(colors.dividerColor),
            )
        }
    }
}


/**
 * Resolves the theme-aware default preference colors.
 *
 * The row container stays transparent, the title uses the primary text role, the supporting text
 * uses the secondary text role, the disabled title uses the tertiary text role, and the bottom
 * divider uses the default border role.
 */
internal fun ElegantPreferenceColors.toBasicComponentColors(): ElegantBasicComponentColors =
    ElegantBasicComponentColors(
        containerColor = containerColor,
        titleColor = titleColor,
        summaryColor = supportingTextColor,
        disabledTitleColor = disabledTitleColor,
        disabledSummaryColor = supportingTextColor,
    )

internal fun resolvePreferenceColors(themeColors: ElegantColors): ElegantPreferenceColors =
    ElegantPreferenceColors(
        containerColor = Color.Transparent,
        titleColor = themeColors.textPrimary,
        supportingTextColor = themeColors.textSecondary,
        disabledTitleColor = themeColors.textTertiary,
        dividerColor = themeColors.borderDefault,
    )

/**
 * Normalizes an optional supporting text value.
 *
 * Returns null for null or blank input so preference rows with empty supporting text collapse to a
 * single title line.
 */
internal fun resolveSupportingText(text: String?): String? = text?.takeIf { it.isNotBlank() }
