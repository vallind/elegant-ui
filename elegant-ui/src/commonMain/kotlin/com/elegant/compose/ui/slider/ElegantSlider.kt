package com.elegant.compose.ui.slider

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.semantics.ProgressBarRangeInfo
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.disabled
import androidx.compose.ui.semantics.progressBarRangeInfo
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import com.elegant.compose.ui.foundation.animation.elegantFolmeSpring
import com.elegant.compose.ui.foundation.theme.ElegantColors
import com.elegant.compose.ui.foundation.theme.ElegantElevation
import com.elegant.compose.ui.foundation.theme.ElegantMotion
import com.elegant.compose.ui.foundation.theme.ElegantRadius
import com.elegant.compose.ui.foundation.theme.ElegantTheme
import kotlin.math.roundToInt

/**
 * State colors used by [ElegantSlider].
 *
 * Use [ElegantSliderDefaults.colors] for theme-aware defaults, then use [copy] for supported
 * product-level customization.
 *
 * @property trackColor resting color of the full-width track.
 * @property activeTrackColor resting color of the filled track before the thumb.
 * @property hoveredTrackColor track color while a pointer hovers the slider.
 * @property hoveredActiveTrackColor active track color while a pointer hovers the slider.
 * @property pressedActiveTrackColor active track color while the slider is pressed or dragged.
 * @property disabledTrackColor track color while interaction is disabled.
 * @property disabledActiveTrackColor active track color while interaction is disabled.
 * @property thumbColor resting thumb color.
 * @property hoveredThumbColor thumb color while a pointer hovers the slider.
 * @property pressedThumbColor thumb color while the slider is pressed or dragged.
 * @property disabledThumbColor thumb color while interaction is disabled.
 * @property focusedThumbColor thumb color while keyboard focus is visible.
 */
@Immutable
public data class ElegantSliderColors(
    val trackColor: Color,
    val activeTrackColor: Color,
    val hoveredTrackColor: Color = trackColor,
    val hoveredActiveTrackColor: Color = activeTrackColor,
    val pressedActiveTrackColor: Color = activeTrackColor,
    val disabledTrackColor: Color = trackColor,
    val disabledActiveTrackColor: Color = activeTrackColor,
    val thumbColor: Color,
    val hoveredThumbColor: Color = thumbColor,
    val pressedThumbColor: Color = thumbColor,
    val disabledThumbColor: Color = thumbColor,
    val focusedThumbColor: Color = thumbColor,
)

/** Theme-aware defaults for [ElegantSlider]. */
public object ElegantSliderDefaults {
    /** Height of the full-width track. */
    public val TrackHeight: Dp = 4.dp

    /** Diameter of the circular thumb. */
    public val ThumbSize: Dp = 20.dp

    /** Minimum interactive root height. */
    public val MinimumTouchHeight: Dp = 48.dp

    /** Standard state-transition duration. */
    public const val AnimationDurationMillis: Int = ElegantMotion.standardDurationMillis

    /** Returns theme-aware state colors. */
    @Composable
    public fun colors(): ElegantSliderColors = resolveSliderColors(ElegantTheme.colors)
}

/** Restrained pressed scale of the thumb that preserves the 48dp interactive root. */
private const val PressedThumbScale: Float = 1.1f

/**
 * Presents a controlled value-selection slider.
 *
 * The slider renders a full-width 4dp rounded track with a filled active segment and a 20dp
 * circular thumb inside a 48dp minimum interactive root. [value] is controlled: dragging or
 * tapping the track maps the pointer position to a fraction of the track, snaps it to [steps]
 * discrete positions when [steps] is greater than zero, and reports the resolved value through
 * [onValueChange]. The thumb follows the reported value, so the parent must update [value] to
 * keep the slider responsive. While the slider is focused, the arrow keys adjust the value by
 * one step, or one hundredth of the range when [steps] is zero, mirroring the visual direction.
 * Moving across a whole-percent tick triggers a ToggleOn/ToggleOff haptic
 * in right-to-left layouts.
 *
 * The component announces [Role.Slider] with a [ProgressBarRangeInfo] describing [value],
 * [valueRange], and [steps], and the disabled state when [enabled] is false.
 *
 * @param value current slider value; values outside [valueRange] are clamped and NaN renders at
 *   the start of the range.
 * @param onValueChange callback invoked with the resolved value after user interaction.
 * @param modifier modifier applied once to the interactive root.
 * @param enabled whether user interaction is accepted.
 * @param valueRange range the value is constrained to.
 * @param steps number of discrete snap positions between the range endpoints; zero keeps the
 *   slider continuous.
 * @param colors theme-aware state colors.
 * @param interactionSource optional hoisted interaction source for observing or controlling state.
 */
@Composable
public fun ElegantSlider(
    value: Float,
    onValueChange: (Float) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    valueRange: ClosedFloatingPointRange<Float> = 0f..1f,
    steps: Int = 0,
    colors: ElegantSliderColors = ElegantSliderDefaults.colors(),
    interactionSource: MutableInteractionSource? = null,
) {
    val resolvedInteractionSource = interactionSource ?: remember { MutableInteractionSource() }
    val pressed by resolvedInteractionSource.collectIsPressedAsState()
    val hovered by resolvedInteractionSource.collectIsHoveredAsState()
    val focused by resolvedInteractionSource.collectIsFocusedAsState()
    val currentValueRange by rememberUpdatedState(valueRange)
    val currentSteps by rememberUpdatedState(steps)
    val hapticFeedback = LocalHapticFeedback.current
    val currentHaptic by rememberUpdatedState(hapticFeedback)
    var lastTick by remember { mutableIntStateOf(-1) }
    fun emitValue(value: Float) {
        val nextTick = sliderTickIndex(value, valueRange)
        if (nextTick != lastTick) {
            val previous = lastTick
            lastTick = nextTick
            if (previous != -1) {
                currentHaptic.performHapticFeedback(
                    if (nextTick > previous) {
                        HapticFeedbackType.ToggleOn
                    } else {
                        HapticFeedbackType.ToggleOff
                    },
                )
            }
        }
        onValueChange(value)
    }
    val layoutDirection = LocalLayoutDirection.current
    val fraction = sliderFraction(value, valueRange)

    val trackColor = when {
        !enabled -> colors.disabledTrackColor
        pressed || hovered -> colors.hoveredTrackColor
        else -> colors.trackColor
    }
    val activeTrackColor = when {
        !enabled -> colors.disabledActiveTrackColor
        pressed -> colors.pressedActiveTrackColor
        hovered -> colors.hoveredActiveTrackColor
        else -> colors.activeTrackColor
    }
    val thumbColor = when {
        !enabled -> colors.disabledThumbColor
        pressed -> colors.pressedThumbColor
        focused -> colors.focusedThumbColor
        hovered -> colors.hoveredThumbColor
        else -> colors.thumbColor
    }

    val animatedTrackColor by animateColorAsState(
        targetValue = trackColor,
        animationSpec = elegantFolmeSpring(dampingRatio = 1.0f, responseSeconds = 0.3f),
        label = "ElegantSliderTrackColor",
    )
    val animatedActiveTrackColor by animateColorAsState(
        targetValue = activeTrackColor,
        animationSpec = elegantFolmeSpring(dampingRatio = 1.0f, responseSeconds = 0.3f),
        label = "ElegantSliderActiveTrackColor",
    )
    val animatedThumbColor by animateColorAsState(
        targetValue = thumbColor,
        animationSpec = elegantFolmeSpring(dampingRatio = 1.0f, responseSeconds = 0.3f),
        label = "ElegantSliderThumbColor",
    )
    val animatedThumbScale by animateFloatAsState(
        targetValue = if (pressed && enabled) PressedThumbScale else 1f,
        animationSpec = elegantFolmeSpring(dampingRatio = 1.0f, responseSeconds = 0.3f),
        label = "ElegantSliderThumbScale",
    )

    val semanticModifier = Modifier.semantics(mergeDescendants = true) {
        progressBarRangeInfo = ProgressBarRangeInfo(value, valueRange, steps)
        if (!enabled) disabled()
    }

    BoxWithConstraints(
        modifier = modifier
            .then(semanticModifier)
            .defaultMinSize(minHeight = ElegantSliderDefaults.MinimumTouchHeight)
            .fillMaxWidth()
            .focusable(interactionSource = resolvedInteractionSource, enabled = enabled)
            .hoverable(interactionSource = resolvedInteractionSource, enabled = enabled)
            .onKeyEvent { event ->
                if (event.type == KeyEventType.KeyDown && enabled) {
                    val span = valueRange.endInclusive - valueRange.start
                    val stepValue = if (steps > 0) span / steps else span / 100f
                    val increase = when (event.key) {
                        Key.DirectionRight -> layoutDirection == LayoutDirection.Ltr
                        Key.DirectionLeft -> layoutDirection == LayoutDirection.Rtl
                        else -> null
                    }
                    if (increase != null) {
                        val base = if (value.isNaN()) valueRange.start else value
                        val next = if (increase) base + stepValue else base - stepValue
                        emitValue(next.coerceIn(valueRange.start, valueRange.endInclusive))
                        true
                    } else {
                        false
                    }
                } else {
                    false
                }
            }
            .pointerInput(enabled, layoutDirection) {
                if (enabled) {
                    fun fractionAt(x: Float): Float {
                        val rawFraction = (x / size.width).coerceIn(0f, 1f)
                        return if (layoutDirection == LayoutDirection.Rtl) {
                            1f - rawFraction
                        } else {
                            rawFraction
                        }
                    }

                    detectTapGestures(
                        onPress = { pressPosition ->
                            val press = PressInteraction.Press(pressPosition)
                            resolvedInteractionSource.tryEmit(press)
                            try {
                                tryAwaitRelease()
                            } finally {
                                resolvedInteractionSource.tryEmit(PressInteraction.Release(press))
                            }
                        },
                        onTap = { offset ->
                            emitValue(
                                resolveStepValue(
                                    fraction = fractionAt(offset.x),
                                    valueRange = currentValueRange,
                                    steps = currentSteps,
                                ),
                            )
                        },
                    )
                }
            }
            .pointerInput(enabled, layoutDirection) {
                if (enabled) {
                    fun fractionAt(x: Float): Float {
                        val rawFraction = (x / size.width).coerceIn(0f, 1f)
                        return if (layoutDirection == LayoutDirection.Rtl) {
                            1f - rawFraction
                        } else {
                            rawFraction
                        }
                    }

                    var dragPress: PressInteraction.Press? = null

                    detectDragGestures(
                        onDragStart = {
                            val press = PressInteraction.Press(Offset.Zero)
                            dragPress = press
                            resolvedInteractionSource.tryEmit(press)
                        },
                        onDrag = { change, _ ->
                            change.consume()
                            emitValue(
                                resolveStepValue(
                                    fraction = fractionAt(change.position.x),
                                    valueRange = currentValueRange,
                                    steps = currentSteps,
                                ),
                            )
                        },
                        onDragEnd = {
                            dragPress?.let { resolvedInteractionSource.tryEmit(PressInteraction.Release(it)) }
                            dragPress = null
                        },
                        onDragCancel = {
                            dragPress?.let { resolvedInteractionSource.tryEmit(PressInteraction.Release(it)) }
                            dragPress = null
                        },
                    )
                }
            },
        contentAlignment = Alignment.CenterStart,
    ) {
        val thumbOffset = maxWidth * fraction - ElegantSliderDefaults.ThumbSize / 2

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(ElegantSliderDefaults.TrackHeight)
                .clip(RoundedCornerShape(ElegantRadius.full))
                .background(animatedTrackColor),
        )
        Box(
            modifier = Modifier
                .fillMaxWidth(fraction)
                .height(ElegantSliderDefaults.TrackHeight)
                .clip(RoundedCornerShape(ElegantRadius.full))
                .background(animatedActiveTrackColor),
        )
        Box(
            modifier = Modifier
                .offset(x = thumbOffset)
                .size(ElegantSliderDefaults.ThumbSize)
                .graphicsLayer {
                    scaleX = animatedThumbScale
                    scaleY = animatedThumbScale
                }
                .shadow(
                    elevation = ElegantElevation.low,
                    shape = CircleShape,
                    clip = false,
                )
                .background(animatedThumbColor, CircleShape),
        )
    }
}

/**
 * Resolves theme-aware [ElegantSliderColors] from [themeColors].
 */
internal fun resolveSliderColors(themeColors: ElegantColors): ElegantSliderColors = ElegantSliderColors(
    trackColor = themeColors.borderDefault,
    activeTrackColor = themeColors.interactivePrimary,
    hoveredTrackColor = themeColors.borderStrong,
    hoveredActiveTrackColor = themeColors.interactivePrimaryHover,
    pressedActiveTrackColor = themeColors.interactivePrimaryPressed,
    disabledTrackColor = themeColors.borderDefault,
    disabledActiveTrackColor = themeColors.interactivePrimary.copy(alpha = 0.35f),
    thumbColor = themeColors.surfaceRaised,
    hoveredThumbColor = themeColors.surfaceRaised,
    pressedThumbColor = themeColors.interactivePrimary,
    disabledThumbColor = themeColors.textTertiary,
    focusedThumbColor = themeColors.interactivePrimaryHover,
)

/**
 * Maps [value] onto the 0..1 position of [valueRange].
 *
 * Values below the range resolve to 0f and values above it resolve to 1f; NaN and degenerate
 * ranges resolve to 0f.
 */
internal fun sliderTickIndex(
    value: Float,
    valueRange: ClosedFloatingPointRange<Float>,
): Int {
    val span = valueRange.endInclusive - valueRange.start
    val fraction = if (value.isNaN() || span == 0f) {
        0f
    } else {
        (value - valueRange.start) / span
    }
    return (fraction.coerceIn(0f, 1f) * 100).toInt()
}

internal fun shouldTickHaptic(previousTick: Int, nextTick: Int): Boolean =
    previousTick != -1 && nextTick != previousTick

internal fun sliderFraction(value: Float, valueRange: ClosedFloatingPointRange<Float>): Float {
    val span = valueRange.endInclusive - valueRange.start
    if (value.isNaN() || span <= 0f) return 0f
    return ((value - valueRange.start) / span).coerceIn(0f, 1f)
}

/**
 * Resolves the slider value for a 0..1 [fraction] of [valueRange].
 *
 * With [steps] less than or equal to zero the value is continuous; otherwise it snaps to the
 * nearest of the [steps] + 1 discrete positions, rounding ties toward the end of the range and
 * clamping boundary positions. A NaN [fraction] resolves to the start of the range.
 */
internal fun resolveStepValue(
    fraction: Float,
    valueRange: ClosedFloatingPointRange<Float>,
    steps: Int,
): Float {
    val clamped = if (fraction.isNaN()) 0f else fraction.coerceIn(0f, 1f)
    val span = valueRange.endInclusive - valueRange.start
    if (steps <= 0) return valueRange.start + clamped * span
    val position = (clamped * steps).roundToInt()
    return valueRange.start + position * span / steps
}
