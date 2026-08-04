package com.elegant.compose.ui.preference

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.elegant.compose.ui.basiccomponent.ElegantBasicComponent
import com.elegant.compose.ui.basiccomponent.ElegantBasicComponentColors
import com.elegant.compose.ui.divider.ElegantDivider
import com.elegant.compose.ui.divider.ElegantDividerDefaults
import com.elegant.compose.ui.foundation.theme.ElegantColors
import com.elegant.compose.ui.foundation.theme.ElegantMotion
import com.elegant.compose.ui.foundation.theme.ElegantSpacing
import com.elegant.compose.ui.foundation.theme.ElegantTheme

/**
 * Theme-aware state colors used by [ElegantArrowPreference].
 *
 * Use [ElegantArrowPreferenceDefaults.colors] for theme-aware defaults, then use [copy] for
 * supported product-level customization.
 *
 * @property containerColor resting row background color.
 * @property titleColor default title color.
 * @property supportingTextColor supporting-text color.
 * @property disabledTitleColor title color while interaction is disabled.
 * @property dividerColor bottom divider line color.
 * @property arrowColor trailing chevron color.
 * @property hoveredContainerColor row background color while a pointer hovers the row.
 * @property pressedContainerColor row background color while the row is pressed.
 */
@Immutable
public data class ElegantArrowPreferenceColors(
    val containerColor: Color,
    val titleColor: Color,
    val supportingTextColor: Color,
    val disabledTitleColor: Color,
    val dividerColor: Color,
    val arrowColor: Color,
    val hoveredContainerColor: Color = containerColor,
    val pressedContainerColor: Color = containerColor,
)

/** Theme-aware defaults for [ElegantArrowPreference]. */
public object ElegantArrowPreferenceDefaults {
    /** Minimum interactive row height. */
    public val MinimumTouchHeight: Dp = 48.dp

    /** Returns theme-aware state colors. */
    @Composable
    public fun colors(): ElegantArrowPreferenceColors = resolveArrowPreferenceColors(ElegantTheme.colors)
}

@Immutable
internal data class ArrowPreferenceVisuals(
    val containerColor: Color,
)


internal val ArrowPreferenceContentPadding: Dp = ElegantSpacing.xl
internal val ArrowPreferenceGap: Dp = ElegantSpacing.md
internal val ArrowPreferenceDividerInset: Dp = ElegantSpacing.xl
internal val ArrowPreferenceChevronSize: Dp = 18.dp
internal val ArrowPreferenceChevronStrokeWidth: Dp = 2.dp
internal val ArrowPreferenceAnimationDurationMillis: Int = ElegantMotion.standardDurationMillis
internal val PreferenceRowInsideMargin: PaddingValues = PaddingValues(
    horizontal = ElegantSpacing.xl,
    vertical = 0.dp,
)



@Composable
public fun ElegantArrowPreference(
    title: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    supportingText: String? = null,
    enabled: Boolean = true,
    colors: ElegantArrowPreferenceColors = ElegantArrowPreferenceDefaults.colors(),
    showDivider: Boolean = true,
) {
    val resolvedSupportingText = resolveSupportingText(supportingText)

    Column(modifier = modifier) {
        ElegantBasicComponent(
            title = title,
            summary = resolvedSupportingText,
            endActions = {
                ArrowPreferenceChevron(color = colors.arrowColor)
            },
            onClick = onClick,
            enabled = enabled,
            colors = colors.toBasicComponentColors(),
            insideMargin = PreferenceRowInsideMargin,
        )
        if (showDivider) {
            ElegantDivider(
                modifier = Modifier.padding(start = ArrowPreferenceDividerInset),
                colors = ElegantDividerDefaults.colors().copy(lineColor = colors.dividerColor),
            )
        }
    }
}

/**
 * Draws the trailing chevron that points toward the row end.
 *
 * The chevron mirrors horizontally when the layout is right-to-left.
 *
 * @param color chevron line color.
 */
@Composable
private fun ArrowPreferenceChevron(
    color: Color,
) {
    val layoutDirection = LocalLayoutDirection.current
    val scaleX = if (layoutDirection == LayoutDirection.Rtl) -1f else 1f
    Canvas(
        modifier = Modifier
            .size(ArrowPreferenceChevronSize)
            .graphicsLayer {
                this.scaleX = scaleX
            },
    ) {
        val strokeWidth = ArrowPreferenceChevronStrokeWidth.toPx()
        val mid = Offset(size.width * 0.62f, size.height * 0.50f)
        val top = Offset(size.width * 0.34f, size.height * 0.30f)
        val bottom = Offset(size.width * 0.34f, size.height * 0.70f)
        drawLine(
            color = color,
            start = top,
            end = mid,
            strokeWidth = strokeWidth,
            cap = StrokeCap.Round,
        )
        drawLine(
            color = color,
            start = mid,
            end = bottom,
            strokeWidth = strokeWidth,
            cap = StrokeCap.Round,
        )
    }
}

/**
 * Resolves theme-aware arrow-preference colors for [themeColors].
 *
 * @param themeColors semantic roles of the active light or dark theme.
 * @return arrow-preference colors derived from the semantic roles.
 */

internal fun resolveArrowPreferenceColors(themeColors: ElegantColors): ElegantArrowPreferenceColors =
    ElegantArrowPreferenceColors(
        containerColor = Color.Transparent,
        titleColor = themeColors.textPrimary,
        supportingTextColor = themeColors.textSecondary,
        disabledTitleColor = themeColors.textTertiary,
        dividerColor = themeColors.borderDefault,
        arrowColor = themeColors.textTertiary,
        hoveredContainerColor = themeColors.surfaceHover,
        pressedContainerColor = themeColors.backgroundSubtle,
    )

/**
 * Resolves the container color for the current interaction state.
 *
 * Precedence: disabled, pressed, hovered, resting.
 *
 * @param colors arrow-preference colors to pick from.
 * @param enabled whether the row accepts interaction.
 * @param pressed whether the row is pressed.
 * @param hovered whether a pointer hovers the row.
 * @return visuals carrying the resolved container color.
 */
internal fun resolveArrowPreferenceVisuals(
    colors: ElegantArrowPreferenceColors,
    enabled: Boolean,
    pressed: Boolean,
    hovered: Boolean,
): ArrowPreferenceVisuals = ArrowPreferenceVisuals(
    containerColor = when {
        !enabled -> colors.containerColor
        pressed -> colors.pressedContainerColor
        hovered -> colors.hoveredContainerColor
        else -> colors.containerColor
    },
)

internal fun ElegantArrowPreferenceColors.toBasicComponentColors(): ElegantBasicComponentColors =
    ElegantBasicComponentColors(
        containerColor = containerColor,
        titleColor = titleColor,
        summaryColor = supportingTextColor,
        disabledTitleColor = disabledTitleColor,
        disabledSummaryColor = supportingTextColor,
        hoveredContainerColor = hoveredContainerColor,
        pressedContainerColor = pressedContainerColor,
    )
