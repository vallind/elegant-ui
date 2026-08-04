package com.elegant.compose.ui.preference

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.disabled
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.selected
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.elegant.compose.ui.divider.ElegantDivider
import com.elegant.compose.ui.divider.ElegantDividerDefaults
import com.elegant.compose.ui.radio.ElegantRadio
import com.elegant.compose.ui.basiccomponent.ElegantBasicComponent
import com.elegant.compose.ui.basiccomponent.ElegantBasicComponentColors
import com.elegant.compose.ui.foundation.theme.ElegantColors
import com.elegant.compose.ui.foundation.theme.ElegantMotion
import com.elegant.compose.ui.foundation.theme.ElegantSpacing
import com.elegant.compose.ui.foundation.theme.ElegantTheme

/**
 * Theme-aware state colors used by [ElegantRadioPreference].
 *
 * Use [ElegantRadioPreferenceDefaults.colors] for theme-aware defaults, then use [copy] for
 * supported product-level customization.
 *
 * @property containerColor resting row background color.
 * @property titleColor default title color.
 * @property supportingTextColor supporting-text color.
 * @property disabledTitleColor title color while interaction is disabled.
 * @property dividerColor bottom divider line color.
 * @property hoveredContainerColor row background color while a pointer hovers the row.
 * @property pressedContainerColor row background color while the row is pressed.
 */
@Immutable
public data class ElegantRadioPreferenceColors(
    val containerColor: Color,
    val titleColor: Color,
    val supportingTextColor: Color,
    val disabledTitleColor: Color,
    val dividerColor: Color,
    val hoveredContainerColor: Color = containerColor,
    val pressedContainerColor: Color = containerColor,
)

/** Theme-aware defaults for [ElegantRadioPreference]. */
public object ElegantRadioPreferenceDefaults {
    /** Minimum interactive row height. */
    public val MinimumTouchHeight: Dp = 48.dp

    /** Standard state-transition duration. */
    public const val AnimationDurationMillis: Int = ElegantMotion.standardDurationMillis

    /** Returns theme-aware state colors. */
    @Composable
    public fun colors(): ElegantRadioPreferenceColors = resolveRadioPreferenceColors(ElegantTheme.colors)
}

@Immutable
internal data class RadioPreferenceVisuals(
    val containerColor: Color,
)

internal val RadioPreferenceContentPadding: Dp = ElegantSpacing.xl
internal val RadioPreferenceGap: Dp = ElegantSpacing.md
internal val RadioPreferenceDividerInset: Dp = ElegantSpacing.xl

/**
 * Presents one selectable row inside a settings-style list.
 *
 * Unlike a bare [ElegantRadio], the whole row is the interactive target: clicking the title, the
 * supporting text, or the trailing indicator activates [onSelect]. The row keeps a 48dp minimum
 * height, animates a hovered and pressed container color, and announces [Role.RadioButton], the
 * [selected] state, and the disabled state. The trailing [ElegantRadio] mirrors the same state and
 * accepts its own pointer input; share one selection state across a group so that exactly one row
 * is selected at a time.
 *
 * @param title row title.
 * @param selected whether this row communicates the chosen option.
 * @param onSelect callback invoked when the row accepts a selection.
 * @param modifier modifier applied once to the interactive row.
 * @param supportingText optional supporting text below the title; blank text is hidden.
 * @param enabled whether user interaction is accepted.
 * @param colors theme-aware state colors.
 * @param showDivider whether a bottom divider is drawn, inset 16dp from the start edge.
 */
@Composable
public fun ElegantRadioPreference(
    title: String,
    selected: Boolean,
    onSelect: () -> Unit,
    modifier: Modifier = Modifier,
    supportingText: String? = null,
    enabled: Boolean = true,
    colors: ElegantRadioPreferenceColors = ElegantRadioPreferenceDefaults.colors(),
    showDivider: Boolean = true,
) {
    Column(modifier = modifier) {
        ElegantBasicComponent(
            title = title,
            summary = resolveSupportingText(supportingText),
            endActions = {
                ElegantRadio(
                    selected = selected,
                    onSelect = onSelect,
                    enabled = enabled,
                )
            },
            onClick = onSelect,
            role = Role.RadioButton,
            selected = selected,
            enabled = enabled,
            colors = colors.toBasicComponentColors(),
            insideMargin = PreferenceRowInsideMargin,
        )
        if (showDivider) {
            ElegantDivider(
                modifier = Modifier.padding(start = RadioPreferenceDividerInset),
                colors = ElegantDividerDefaults.colors().copy(lineColor = colors.dividerColor),
            )
        }
    }
}


internal fun ElegantRadioPreferenceColors.toBasicComponentColors(): ElegantBasicComponentColors =
    ElegantBasicComponentColors(
        containerColor = containerColor,
        titleColor = titleColor,
        summaryColor = supportingTextColor,
        disabledTitleColor = disabledTitleColor,
        disabledSummaryColor = supportingTextColor,
        hoveredContainerColor = hoveredContainerColor,
        pressedContainerColor = pressedContainerColor,
    )

internal fun resolveRadioPreferenceColors(themeColors: ElegantColors): ElegantRadioPreferenceColors =
    ElegantRadioPreferenceColors(
        containerColor = Color.Transparent,
        titleColor = themeColors.textPrimary,
        supportingTextColor = themeColors.textSecondary,
        disabledTitleColor = themeColors.textTertiary,
        dividerColor = themeColors.borderDefault,
        hoveredContainerColor = themeColors.surfaceHover,
        pressedContainerColor = themeColors.backgroundSubtle,
    )

internal fun resolveRadioPreferenceVisuals(
    colors: ElegantRadioPreferenceColors,
    enabled: Boolean,
    pressed: Boolean,
    hovered: Boolean,
): RadioPreferenceVisuals = RadioPreferenceVisuals(
    containerColor = when {
        !enabled -> colors.containerColor
        pressed -> colors.pressedContainerColor
        hovered -> colors.hoveredContainerColor
        else -> colors.containerColor
    },
)

