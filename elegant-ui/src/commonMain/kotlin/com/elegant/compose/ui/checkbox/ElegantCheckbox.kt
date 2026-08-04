package com.elegant.compose.ui.checkbox

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.lerp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.elegant.compose.ui.foundation.theme.ElegantColors
import com.elegant.compose.ui.foundation.theme.ElegantMotion
import com.elegant.compose.ui.foundation.theme.ElegantSpacing
import com.elegant.compose.ui.foundation.theme.ElegantTheme

/**
 * Theme-aware state colors used by [ElegantCheckbox].
 *
 * Use [ElegantCheckboxDefaults.colors] for theme-aware defaults, then use [copy] for supported
 * product-level customization.
 *
 * @property checkedContainerColor checked box container color.
 * @property uncheckedContainerColor unchecked box container color.
 * @property hoveredCheckedContainerColor hovered checked box container color.
 * @property hoveredUncheckedContainerColor hovered unchecked box container color.
 * @property pressedCheckedContainerColor pressed checked box container color.
 * @property pressedUncheckedContainerColor pressed unchecked box container color.
 * @property disabledCheckedContainerColor disabled checked box container color.
 * @property disabledUncheckedContainerColor disabled unchecked box container color.
 * @property checkedContentColor checkmark color.
 * @property disabledCheckedContentColor disabled checkmark color.
 * @property borderColor default box border color.
 * @property hoveredBorderColor hovered box border color.
 * @property focusedBorderColor keyboard focus-ring border color.
 * @property disabledBorderColor disabled box border color.
 */
@Immutable
public data class ElegantCheckboxColors(
    val checkedContainerColor: Color,
    val uncheckedContainerColor: Color,
    val hoveredCheckedContainerColor: Color = checkedContainerColor,
    val hoveredUncheckedContainerColor: Color = uncheckedContainerColor,
    val pressedCheckedContainerColor: Color = checkedContainerColor,
    val pressedUncheckedContainerColor: Color = uncheckedContainerColor,
    val disabledCheckedContainerColor: Color = checkedContainerColor,
    val disabledUncheckedContainerColor: Color = uncheckedContainerColor,
    val checkedContentColor: Color,
    val disabledCheckedContentColor: Color = checkedContentColor,
    val borderColor: Color,
    val hoveredBorderColor: Color = borderColor,
    val focusedBorderColor: Color = borderColor,
    val disabledBorderColor: Color = borderColor,
)

/** Theme-aware defaults for [ElegantCheckbox]. */
public object ElegantCheckboxDefaults {
    /** 20dp visual box size of the checkbox. */
    public val BoxSize: Dp = 20.dp

    /** Minimum interactive root height used by the checkbox row. */
    public val MinimumTouchHeight: Dp = 48.dp

    /** Standard state-transition duration. */
    public const val AnimationDurationMillis: Int = ElegantMotion.standardDurationMillis

    /** Returns theme-aware state colors. */
    @Composable
    public fun colors(): ElegantCheckboxColors = resolveCheckboxColors(ElegantTheme.colors)
}

@Immutable
internal data class CheckboxVisuals(
    val container: Color,
    val border: Color,
    val check: Color,
    val ripple: Color,
)

/**
 * Displays an Elegant UI checkbox.
 *
 * The control is fully controlled: [checked] defines the selection and [onCheckedChange] is
 * invoked with the requested value whenever the user activates the row. The whole row is
 * announced with [Role.Checkbox] semantics, keeps a 48dp minimum interactive height, and
 * animates its box container and checkmark. The label, when provided, is rendered inline on
 * the same row using the current theme typography.
 *
 * @param checked whether the checkbox is selected.
 * @param onCheckedChange callback invoked with the requested selection state.
 * @param modifier modifier applied once to the checkbox row root.
 * @param enabled whether user interaction is accepted.
 * @param label optional inline text label rendered after the box.
 * @param colors theme-aware state colors.
 * @param interactionSource optional hoisted interaction source for observing or controlling state.
 */
@Composable
public fun ElegantCheckbox(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    label: String? = null,
    colors: ElegantCheckboxColors = ElegantCheckboxDefaults.colors(),
    interactionSource: MutableInteractionSource? = null,
) {
    val resolvedInteractionSource = interactionSource ?: remember { MutableInteractionSource() }
    val hapticFeedback = LocalHapticFeedback.current
    val currentHaptic by rememberUpdatedState(hapticFeedback)
    val pressed by resolvedInteractionSource.collectIsPressedAsState()
    val hovered by resolvedInteractionSource.collectIsHoveredAsState()
    val focused by resolvedInteractionSource.collectIsFocusedAsState()
    val visuals = resolveCheckboxVisuals(
        colors = colors,
        enabled = enabled,
        pressed = pressed,
        hovered = hovered,
        focused = focused,
        checked = checked,
    )

    val animatedContainer by animateColorAsState(
        targetValue = visuals.container,
        animationSpec = tween(
            durationMillis = ElegantCheckboxDefaults.AnimationDurationMillis,
            easing = FastOutSlowInEasing,
        ),
        label = "ElegantCheckboxContainer",
    )
    val animatedBorder by animateColorAsState(
        targetValue = visuals.border,
        animationSpec = tween(
            durationMillis = ElegantCheckboxDefaults.AnimationDurationMillis,
            easing = FastOutSlowInEasing,
        ),
        label = "ElegantCheckboxBorder",
    )
    val animatedCheck by animateColorAsState(
        targetValue = visuals.check,
        animationSpec = tween(
            durationMillis = ElegantCheckboxDefaults.AnimationDurationMillis,
            easing = FastOutSlowInEasing,
        ),
        label = "ElegantCheckboxCheck",
    )
    val animatedRipple by animateColorAsState(
        targetValue = visuals.ripple,
        animationSpec = tween(
            durationMillis = ElegantCheckboxDefaults.AnimationDurationMillis,
            easing = FastOutSlowInEasing,
        ),
        label = "ElegantCheckboxRipple",
    )
    val checkmarkProgress = remember { Animatable(0f) }
    LaunchedEffect(checked) {
        checkmarkProgress.animateTo(
            targetValue = if (checked) 1f else 0f,
            animationSpec = tween(
                durationMillis = ElegantCheckboxDefaults.AnimationDurationMillis,
                easing = FastOutSlowInEasing,
            ),
        )
    }

    val boxShape = RoundedCornerShape(6.dp)
    val themeColors = ElegantTheme.colors

    Row(
        modifier = modifier
            .defaultMinSize(minHeight = ElegantCheckboxDefaults.MinimumTouchHeight)
            .toggleable(
                value = checked,
                enabled = enabled,
                role = Role.Checkbox,
                interactionSource = resolvedInteractionSource,
                indication = ripple(color = animatedRipple),
                onValueChange = { newValue ->
                    currentHaptic.performHapticFeedback(
                        if (newValue) {
                            HapticFeedbackType.ToggleOn
                        } else {
                            HapticFeedbackType.ToggleOff
                        },
                    )
                    onCheckedChange(newValue)
                },
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .size(ElegantCheckboxDefaults.BoxSize)
                .background(color = animatedContainer, shape = boxShape)
                .border(width = 2.dp, color = animatedBorder, shape = boxShape),
            contentAlignment = Alignment.Center,
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val progress = checkmarkProgress.value
                if (progress > 0f) {
                    val path = checkmarkPath(size.width)
                    val strokeWidth = 2.dp.toPx()
                    if (progress < 0.5f) {
                        drawLine(
                            color = animatedCheck,
                            start = path[0],
                            end = lerp(start = path[0], stop = path[1], fraction = progress / 0.5f),
                            strokeWidth = strokeWidth,
                            cap = StrokeCap.Round,
                        )
                    } else {
                        drawLine(
                            color = animatedCheck,
                            start = path[0],
                            end = path[1],
                            strokeWidth = strokeWidth,
                            cap = StrokeCap.Round,
                        )
                        drawLine(
                            color = animatedCheck,
                            start = path[1],
                            end = lerp(
                                start = path[1],
                                stop = path[2],
                                fraction = (progress - 0.5f) / 0.5f,
                            ),
                            strokeWidth = strokeWidth,
                            cap = StrokeCap.Round,
                        )
                    }
                }
            }
        }
        if (label != null) {
            Spacer(modifier = Modifier.width(ElegantSpacing.md))
            ProvideTextStyle(ElegantTheme.typography.labelMedium) {
                Text(
                    text = label,
                    color = if (enabled) {
                        themeColors.textPrimary
                    } else {
                        themeColors.textTertiary
                    },
                )
            }
        }
    }
}

/**
 * Resolves the two-segment checkmark polyline for a box of [boxSizePx] pixels.
 *
 * Segment 1 runs from (0.24, 0.52) to (0.42, 0.70) and segment 2 continues to (0.78, 0.32),
 * both expressed as proportions of the box and scaled to absolute pixel offsets. The returned
 * list holds the start, the shared corner, and the end of the polyline in drawing order.
 */
internal fun checkmarkPath(boxSizePx: Float): List<Offset> {
    require(boxSizePx > 0f)
    return listOf(
        Offset(x = boxSizePx * 0.24f, y = boxSizePx * 0.52f),
        Offset(x = boxSizePx * 0.42f, y = boxSizePx * 0.70f),
        Offset(x = boxSizePx * 0.78f, y = boxSizePx * 0.32f),
    )
}

internal fun resolveCheckboxColors(themeColors: ElegantColors): ElegantCheckboxColors =
    ElegantCheckboxColors(
        checkedContainerColor = themeColors.interactivePrimary,
        uncheckedContainerColor = Color.Transparent,
        hoveredCheckedContainerColor = themeColors.interactivePrimaryHover,
        hoveredUncheckedContainerColor = themeColors.backgroundSubtle,
        pressedCheckedContainerColor = themeColors.interactivePrimaryPressed,
        pressedUncheckedContainerColor = themeColors.surfaceSunken,
        disabledCheckedContainerColor = themeColors.interactivePrimary.copy(alpha = 0.35f),
        disabledUncheckedContainerColor = Color.Transparent,
        checkedContentColor = themeColors.textInverse,
        borderColor = themeColors.borderStrong,
        hoveredBorderColor = themeColors.borderStrong,
        focusedBorderColor = themeColors.focusRing,
        disabledBorderColor = themeColors.borderDefault,
    )

internal fun resolveCheckboxVisuals(
    colors: ElegantCheckboxColors,
    enabled: Boolean,
    pressed: Boolean,
    hovered: Boolean,
    focused: Boolean,
    checked: Boolean,
): CheckboxVisuals {
    val container = when {
        !enabled -> if (checked) {
            colors.disabledCheckedContainerColor
        } else {
            colors.disabledUncheckedContainerColor
        }

        pressed -> if (checked) {
            colors.pressedCheckedContainerColor
        } else {
            colors.pressedUncheckedContainerColor
        }

        hovered -> if (checked) {
            colors.hoveredCheckedContainerColor
        } else {
            colors.hoveredUncheckedContainerColor
        }

        else -> if (checked) {
            colors.checkedContainerColor
        } else {
            colors.uncheckedContainerColor
        }
    }
    val border = when {
        !enabled -> colors.disabledBorderColor
        focused -> colors.focusedBorderColor
        hovered -> colors.hoveredBorderColor
        else -> colors.borderColor
    }
    val check = if (enabled) {
        colors.checkedContentColor
    } else {
        colors.disabledCheckedContentColor
    }
    val ripple = if (checked) check else border

    return CheckboxVisuals(
        container = container,
        border = border,
        check = check,
        ripple = ripple,
    )
}
