package com.elegant.compose.ui.radio

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.elegant.compose.ui.theme.ElegantColors
import com.elegant.compose.ui.theme.ElegantMotion
import com.elegant.compose.ui.theme.ElegantSpacing
import com.elegant.compose.ui.theme.ElegantTheme

/**
 * State colors used by [ElegantRadio].
 *
 * Use [ElegantRadioDefaults.colors] for theme-aware defaults, then use [copy] for supported
 * product-level customization.
 *
 * @property selectedColor default color of the selected indicator.
 * @property unselectedColor default color of the unselected indicator.
 * @property hoveredSelectedColor selected indicator color while a pointer hovers the radio.
 * @property hoveredUnselectedColor unselected indicator color while a pointer hovers the radio.
 * @property pressedSelectedColor selected indicator color while the radio is pressed.
 * @property pressedUnselectedColor unselected indicator color while the radio is pressed.
 * @property disabledSelectedColor selected indicator color while interaction is disabled.
 * @property disabledUnselectedColor unselected indicator color while interaction is disabled.
 * @property focusedBorderColor indicator ring color while keyboard focus is visible.
 */
@Immutable
public data class ElegantRadioColors(
    val selectedColor: Color,
    val unselectedColor: Color,
    val hoveredSelectedColor: Color = selectedColor,
    val hoveredUnselectedColor: Color = unselectedColor,
    val pressedSelectedColor: Color = selectedColor,
    val pressedUnselectedColor: Color = unselectedColor,
    val disabledSelectedColor: Color = selectedColor,
    val disabledUnselectedColor: Color = unselectedColor,
    val focusedBorderColor: Color = unselectedColor,
)

/** Theme-aware defaults for [ElegantRadio]. */
public object ElegantRadioDefaults {
    /** Circular indicator size. */
    public val BoxSize: Dp = 20.dp

    /** Minimum interactive row height. */
    public val MinimumTouchHeight: Dp = 48.dp

    /** Standard state-transition duration. */
    public const val AnimationDurationMillis: Int = ElegantMotion.standardDurationMillis

    /** Returns theme-aware state colors. */
    @Composable
    public fun colors(): ElegantRadioColors = resolveRadioColors(ElegantTheme.colors)
}

@Immutable
internal data class RadioVisuals(
    val color: Color,
    val dotScale: Float,
)

/**
 * Presents one option from a mutually exclusive set.
 *
 * The radio renders a 20dp circular indicator that fills with an animated dot when [selected] and
 * keeps a stroke-only ring otherwise. The whole row is a 48dp minimum interactive target that
 * announces [Role.RadioButton], the selection state, and the disabled state; the optional [label]
 * is read as part of the merged row semantics. Share one selection state across a group so that
 * exactly one radio is selected at a time.
 *
 * @param selected whether this radio communicates the chosen option.
 * @param onSelect callback invoked when the radio accepts a selection.
 * @param modifier modifier applied once to the interactive row.
 * @param enabled whether user interaction is accepted.
 * @param label optional text label shown after the indicator.
 * @param colors theme-aware state colors.
 * @param interactionSource optional hoisted interaction source for observing or controlling state.
 */
@Composable
public fun ElegantRadio(
    selected: Boolean,
    onSelect: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    label: String? = null,
    colors: ElegantRadioColors = ElegantRadioDefaults.colors(),
    interactionSource: MutableInteractionSource? = null,
) {
    val resolvedInteractionSource = interactionSource ?: remember { MutableInteractionSource() }
    val hapticFeedback = LocalHapticFeedback.current
    val currentHaptic by rememberUpdatedState(hapticFeedback)
    val pressed by resolvedInteractionSource.collectIsPressedAsState()
    val hovered by resolvedInteractionSource.collectIsHoveredAsState()
    val focused by resolvedInteractionSource.collectIsFocusedAsState()
    val visuals = resolveRadioVisuals(
        colors = colors,
        enabled = enabled,
        selected = selected,
        pressed = pressed,
        hovered = hovered,
        focused = focused,
    )

    val animatedColor by animateColorAsState(
        targetValue = visuals.color,
        animationSpec = tween(
            durationMillis = ElegantRadioDefaults.AnimationDurationMillis,
            easing = FastOutSlowInEasing,
        ),
        label = "ElegantRadioColor",
    )
    val animatedDotScale by animateFloatAsState(
        targetValue = visuals.dotScale,
        animationSpec = tween(
            durationMillis = ElegantRadioDefaults.AnimationDurationMillis,
            easing = FastOutSlowInEasing,
        ),
        label = "ElegantRadioDotScale",
    )
    val labelColor = if (enabled) {
        ElegantTheme.colors.textPrimary
    } else {
        ElegantTheme.colors.textTertiary
    }

    Row(
        modifier = modifier
            .defaultMinSize(minHeight = ElegantRadioDefaults.MinimumTouchHeight)
            .selectable(
                selected = selected,
                interactionSource = resolvedInteractionSource,
                indication = null,
                enabled = enabled,
                role = Role.RadioButton,
                onClick = {
                    currentHaptic.performHapticFeedback(HapticFeedbackType.ToggleOn)
                    onSelect()
                },
            )
            .indication(
                interactionSource = resolvedInteractionSource,
                indication = ripple(color = animatedColor),
            )
            .padding(horizontal = ElegantSpacing.md),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier.size(ElegantRadioDefaults.BoxSize),
            contentAlignment = Alignment.Center,
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val strokeWidth = 2.dp.toPx()
                drawCircle(
                    color = animatedColor,
                    radius = (size.minDimension - strokeWidth) / 2f,
                    center = center,
                    style = Stroke(width = strokeWidth),
                )
                drawCircle(
                    color = animatedColor,
                    radius = 5.dp.toPx() * animatedDotScale,
                    center = center,
                )
            }
        }

        if (label != null) {
            Spacer(Modifier.width(ElegantSpacing.md))
            Text(
                text = label,
                style = ElegantTheme.typography.labelMedium,
                color = labelColor,
            )
        }
    }
}

internal fun resolveRadioColors(themeColors: ElegantColors): ElegantRadioColors = ElegantRadioColors(
    selectedColor = themeColors.interactivePrimary,
    unselectedColor = themeColors.borderStrong,
    hoveredSelectedColor = themeColors.interactivePrimaryHover,
    hoveredUnselectedColor = themeColors.interactivePrimary.copy(alpha = 0.55f),
    pressedSelectedColor = themeColors.interactivePrimaryPressed,
    pressedUnselectedColor = themeColors.borderStrong,
    disabledSelectedColor = themeColors.interactivePrimary.copy(alpha = 0.35f),
    disabledUnselectedColor = themeColors.borderDefault,
    focusedBorderColor = themeColors.focusRing,
)

internal fun resolveRadioVisuals(
    colors: ElegantRadioColors,
    enabled: Boolean,
    selected: Boolean,
    pressed: Boolean,
    hovered: Boolean,
    focused: Boolean,
): RadioVisuals {
    val color = when {
        !enabled -> if (selected) colors.disabledSelectedColor else colors.disabledUnselectedColor
        pressed -> if (selected) colors.pressedSelectedColor else colors.pressedUnselectedColor
        focused -> colors.focusedBorderColor
        hovered -> if (selected) colors.hoveredSelectedColor else colors.hoveredUnselectedColor
        else -> if (selected) colors.selectedColor else colors.unselectedColor
    }
    return RadioVisuals(
        color = color,
        dotScale = if (selected) 1f else 0f,
    )
}
