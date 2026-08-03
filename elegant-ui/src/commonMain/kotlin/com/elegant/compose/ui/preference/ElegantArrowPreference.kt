package com.elegant.compose.ui.preference

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.disabled
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.elegant.compose.ui.divider.ElegantDivider
import com.elegant.compose.ui.divider.ElegantDividerDefaults
import com.elegant.compose.ui.theme.ElegantColors
import com.elegant.compose.ui.theme.ElegantMotion
import com.elegant.compose.ui.theme.ElegantSpacing
import com.elegant.compose.ui.theme.ElegantTheme

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
internal const val ArrowPreferenceAnimationDurationMillis: Int = ElegantMotion.standardDurationMillis

/**
 * Presents one settings-style row that navigates or drills into another screen.
 *
 * The whole row is the interactive target: clicking the title, the supporting text, or the
 * trailing chevron activates [onClick]. The row keeps a 48dp minimum height, animates a hovered
 * and pressed container color, and announces [Role.Button] and the disabled state. The trailing
 * chevron points in the logical layout direction and mirrors horizontally in RTL.
 *
 * @param title row title.
 * @param onClick callback invoked when the row accepts a click.
 * @param modifier modifier applied once to the interactive row.
 * @param supportingText optional supporting text below the title; blank text is hidden.
 * @param enabled whether user interaction is accepted.
 * @param colors theme-aware state colors.
 * @param showDivider whether a bottom divider is drawn, inset 16dp from the start edge.
 */
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
    val interactionSource = remember { MutableInteractionSource() }
    val pressed by interactionSource.collectIsPressedAsState()
    val hovered by interactionSource.collectIsHoveredAsState()
    val visuals = resolveArrowPreferenceVisuals(
        colors = colors,
        enabled = enabled,
        pressed = pressed,
        hovered = hovered,
    )
    val animatedContainer by animateColorAsState(
        targetValue = visuals.containerColor,
        animationSpec = tween(
            durationMillis = ArrowPreferenceAnimationDurationMillis,
            easing = FastOutSlowInEasing,
        ),
        label = "ElegantArrowPreferenceContainer",
    )
    val resolvedSupportingText = resolveSupportingText(supportingText)
    val resolvedTitleColor = if (enabled) colors.titleColor else colors.disabledTitleColor

    Column(
        modifier = modifier
            .background(animatedContainer)
            .defaultMinSize(minHeight = ElegantArrowPreferenceDefaults.MinimumTouchHeight)
            .clickable(
                enabled = enabled,
                role = Role.Button,
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick,
            )
            .semantics {
                role = Role.Button
                if (!enabled) disabled()
            },
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = ArrowPreferenceContentPadding),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = ElegantTheme.typography.labelMedium,
                    color = resolvedTitleColor,
                )
                if (resolvedSupportingText != null) {
                    Text(
                        text = resolvedSupportingText,
                        style = ElegantTheme.typography.bodyMedium,
                        color = colors.supportingTextColor,
                    )
                }
            }
            Spacer(modifier = Modifier.width(ArrowPreferenceGap))
            ArrowPreferenceChevron(color = colors.arrowColor)
        }
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
