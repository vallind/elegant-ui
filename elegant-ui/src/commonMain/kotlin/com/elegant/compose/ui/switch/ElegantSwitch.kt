package com.elegant.compose.ui.switch

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.abs
import com.elegant.compose.ui.foundation.theme.ElegantColors
import com.elegant.compose.ui.foundation.theme.ElegantElevation
import com.elegant.compose.ui.foundation.theme.ElegantMotion
import com.elegant.compose.ui.foundation.theme.ElegantSpacing
import com.elegant.compose.ui.foundation.theme.ElegantTheme

/**
 * Theme-aware state colors used by [ElegantSwitch].
 *
 * Use [ElegantSwitchDefaults.colors] for theme-aware defaults, then use [copy] for supported
 * product-level customization.
 *
 * @property trackCheckedColor checked track color.
 * @property trackUncheckedColor unchecked track color.
 * @property hoveredTrackCheckedColor hovered checked track color.
 * @property hoveredTrackUncheckedColor hovered unchecked track color.
 * @property pressedTrackCheckedColor pressed checked track color.
 * @property pressedTrackUncheckedColor pressed unchecked track color.
 * @property disabledTrackCheckedColor disabled checked track color.
 * @property disabledTrackUncheckedColor disabled unchecked track color.
 * @property thumbCheckedColor checked thumb color.
 * @property thumbUncheckedColor unchecked thumb color.
 * @property hoveredThumbCheckedColor hovered checked thumb color.
 * @property hoveredThumbUncheckedColor hovered unchecked thumb color.
 * @property disabledThumbCheckedColor disabled checked thumb color.
 * @property disabledThumbUncheckedColor disabled unchecked thumb color.
 * @property focusedTrackColor keyboard focus-ring border color.
 */
@Immutable
public data class ElegantSwitchColors(
    val trackCheckedColor: Color,
    val trackUncheckedColor: Color,
    val hoveredTrackCheckedColor: Color = trackCheckedColor,
    val hoveredTrackUncheckedColor: Color = trackUncheckedColor,
    val pressedTrackCheckedColor: Color = trackCheckedColor,
    val pressedTrackUncheckedColor: Color = trackUncheckedColor,
    val disabledTrackCheckedColor: Color = trackCheckedColor,
    val disabledTrackUncheckedColor: Color = trackUncheckedColor,
    val thumbCheckedColor: Color,
    val thumbUncheckedColor: Color,
    val hoveredThumbCheckedColor: Color = thumbCheckedColor,
    val hoveredThumbUncheckedColor: Color = thumbUncheckedColor,
    val disabledThumbCheckedColor: Color = thumbCheckedColor,
    val disabledThumbUncheckedColor: Color = thumbUncheckedColor,
    val focusedTrackColor: Color = trackUncheckedColor,
)

/** Theme-aware defaults for [ElegantSwitch]. */
public object ElegantSwitchDefaults {
    /** 44dp visual track width. */
    public val TrackWidth: Dp = 44.dp

    /** 24dp visual track height. */
    public val TrackHeight: Dp = 24.dp

    /** 16dp visual thumb diameter. */
    public val ThumbSize: Dp = 16.dp

    /** Minimum interactive root height used by the switch row. */
    public val MinimumTouchHeight: Dp = 48.dp

    /** Standard state-transition duration. */
    public const val AnimationDurationMillis: Int = ElegantMotion.standardDurationMillis

    /** Returns theme-aware state colors. */
    @Composable
    public fun colors(): ElegantSwitchColors = resolveSwitchColors(ElegantTheme.colors)
}

@Immutable
internal data class SwitchVisuals(
    val track: Color,
    val thumb: Color,
)

/**
 * Displays an Elegant UI switch.
 *
 * The control is fully controlled: [checked] defines the state and [onCheckedChange] is invoked
 * with the requested value whenever the user activates the row. The whole row is announced with
 * [Role.Switch] semantics, keeps a 48dp minimum interactive height, and animates its track, thumb,
 * and thumb offset between the checked and unchecked positions. Dragging the thumb past half of
 * its travel toggles the switch on release; a drag released inside the spring-back zone snaps back.
 * The label, when provided, is
 * rendered inline on the same row using the current theme typography.
 *
 * @param checked whether the switch is on.
 * @param onCheckedChange callback invoked with the requested state.
 * @param modifier modifier applied once to the switch row root.
 * @param enabled whether user interaction is accepted.
 * @param label optional inline text label rendered after the track.
 * @param colors theme-aware state colors.
 * @param interactionSource optional hoisted interaction source for observing or controlling state.
 */
@Composable
public fun ElegantSwitch(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    label: String? = null,
    colors: ElegantSwitchColors = ElegantSwitchDefaults.colors(),
    interactionSource: MutableInteractionSource? = null,
) {
    val resolvedInteractionSource = interactionSource ?: remember { MutableInteractionSource() }
    val hapticFeedback = LocalHapticFeedback.current
    val currentHaptic by rememberUpdatedState(hapticFeedback)
    val pressed by resolvedInteractionSource.collectIsPressedAsState()
    val hovered by resolvedInteractionSource.collectIsHoveredAsState()
    val focused by resolvedInteractionSource.collectIsFocusedAsState()
    val density = LocalDensity.current
    val maxOffsetPx = with(density) {
        ElegantSwitchDefaults.TrackWidth.toPx() - ElegantSwitchDefaults.ThumbSize.toPx() -
            ElegantSpacing.xs.toPx() * 2f
    }
    var rawDragOffset by remember { mutableFloatStateOf(0f) }
    var hasVibrated by remember { mutableStateOf(false) }
    val visuals = resolveSwitchVisuals(
        colors = colors,
        enabled = enabled,
        checked = checked,
        pressed = pressed,
        hovered = hovered,
    )

    val animatedTrack by animateColorAsState(
        targetValue = visuals.track,
        animationSpec = tween(
            durationMillis = ElegantSwitchDefaults.AnimationDurationMillis,
            easing = FastOutSlowInEasing,
        ),
        label = "ElegantSwitchTrack",
    )
    val animatedThumb by animateColorAsState(
        targetValue = visuals.thumb,
        animationSpec = tween(
            durationMillis = ElegantSwitchDefaults.AnimationDurationMillis,
            easing = FastOutSlowInEasing,
        ),
        label = "ElegantSwitchThumb",
    )
    val animatedOffset by animateDpAsState(
        targetValue = if (checked) {
            ElegantSwitchDefaults.TrackWidth - ElegantSwitchDefaults.ThumbSize - ElegantSpacing.xs * 2
        } else {
            0.dp
        },
        animationSpec = tween(
            durationMillis = ElegantSwitchDefaults.AnimationDurationMillis,
            easing = FastOutSlowInEasing,
        ),
        label = "ElegantSwitchThumbOffset",
    )
    val displayedOffset = if (rawDragOffset != 0f) {
        with(density) {
            ((if (checked) maxOffsetPx else 0f) + rawDragOffset).toDp()
        }
    } else {
        animatedOffset
    }
    val themeColors = ElegantTheme.colors

    Row(
        modifier = modifier
            .defaultMinSize(minHeight = ElegantSwitchDefaults.MinimumTouchHeight)
            .toggleable(
                value = checked,
                enabled = enabled,
                role = Role.Switch,
                interactionSource = resolvedInteractionSource,
                indication = null,
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
                .size(
                    width = ElegantSwitchDefaults.TrackWidth,
                    height = ElegantSwitchDefaults.TrackHeight,
                )
                .pointerInput(checked, enabled) {
                    if (!enabled) return@pointerInput
                    val thumbPx = with(density) {
                        ElegantSwitchDefaults.ThumbSize.toPx()
                    }
                    val padPx = with(density) { ElegantSpacing.xs.toPx() }
                    detectDragGestures(
                        onDragStart = { hasVibrated = false },
                        onDrag = { change, dragAmount ->
                            change.consume()
                            val maxDrag = size.width - thumbPx - 2f * padPx
                            rawDragOffset = (rawDragOffset + dragAmount.x).coerceIn(-maxDrag, maxDrag)
                            if (!hasVibrated && abs(rawDragOffset) > maxDrag / 2f) {
                                hasVibrated = true
                                currentHaptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                            }
                        },
                        onDragEnd = {
                            val maxDrag = size.width - thumbPx - 2f * padPx
                            switchDragTarget(checked, rawDragOffset, maxDrag)?.let { newChecked ->
                                if (newChecked != checked) {
                                    currentHaptic.performHapticFeedback(
                                        if (newChecked) {
                                            HapticFeedbackType.ToggleOn
                                        } else {
                                            HapticFeedbackType.ToggleOff
                                        },
                                    )
                                    onCheckedChange(newChecked)
                                }
                            }
                            rawDragOffset = 0f
                        },
                        onDragCancel = { rawDragOffset = 0f },
                    )
                }
                .background(color = animatedTrack, shape = CircleShape)
                .then(
                    if (focused) {
                        Modifier.border(
                            width = 2.dp,
                            color = colors.focusedTrackColor,
                            shape = CircleShape,
                        )
                    } else {
                        Modifier
                    },
                )
                .padding(start = displayedOffset),
            contentAlignment = Alignment.CenterStart,
        ) {
            Box(
                modifier = Modifier
                    .size(ElegantSwitchDefaults.ThumbSize)
                    .shadow(
                        elevation = ElegantElevation.low,
                        shape = CircleShape,
                        clip = false,
                    )
                    .background(color = animatedThumb, shape = CircleShape),
            )
        }

        if (label != null) {
            Spacer(modifier = Modifier.width(10.dp))
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

internal fun resolveSwitchColors(themeColors: ElegantColors): ElegantSwitchColors = ElegantSwitchColors(
    trackCheckedColor = themeColors.interactivePrimary,
    trackUncheckedColor = themeColors.borderStrong,
    hoveredTrackCheckedColor = themeColors.interactivePrimaryHover,
    hoveredTrackUncheckedColor = themeColors.interactivePrimary.copy(alpha = 0.55f),
    pressedTrackCheckedColor = themeColors.interactivePrimaryPressed,
    pressedTrackUncheckedColor = themeColors.borderStrong,
    disabledTrackCheckedColor = themeColors.interactivePrimary.copy(alpha = 0.35f),
    disabledTrackUncheckedColor = themeColors.borderDefault,
    thumbCheckedColor = themeColors.textInverse,
    thumbUncheckedColor = themeColors.surfaceRaised,
    hoveredThumbCheckedColor = themeColors.textInverse,
    hoveredThumbUncheckedColor = themeColors.surfaceRaised,
    disabledThumbCheckedColor = themeColors.textInverse.copy(alpha = 0.8f),
    disabledThumbUncheckedColor = themeColors.textTertiary,
    focusedTrackColor = themeColors.focusRing,
)

internal fun resolveSwitchVisuals(
    colors: ElegantSwitchColors,
    enabled: Boolean,
    checked: Boolean,
    pressed: Boolean,
    hovered: Boolean,
): SwitchVisuals {
    val track = when {
        !enabled -> if (checked) {
            colors.disabledTrackCheckedColor
        } else {
            colors.disabledTrackUncheckedColor
        }

        pressed -> if (checked) {
            colors.pressedTrackCheckedColor
        } else {
            colors.pressedTrackUncheckedColor
        }

        hovered -> if (checked) {
            colors.hoveredTrackCheckedColor
        } else {
            colors.hoveredTrackUncheckedColor
        }

        else -> if (checked) {
            colors.trackCheckedColor
        } else {
            colors.trackUncheckedColor
        }
    }
    val thumb = when {
        !enabled -> if (checked) {
            colors.disabledThumbCheckedColor
        } else {
            colors.disabledThumbUncheckedColor
        }

        hovered -> if (checked) {
            colors.hoveredThumbCheckedColor
        } else {
            colors.hoveredThumbUncheckedColor
        }

        else -> if (checked) {
            colors.thumbCheckedColor
        } else {
            colors.thumbUncheckedColor
        }
    }
    return SwitchVisuals(
        track = track,
        thumb = thumb,
    )
}

/**
 * Computes the horizontal thumb offset, in pixels, for a switch track.
 *
 * The offset is zero while unchecked and equals `trackWidthPx - thumbSizePx - 2 * paddingPx`
 * while checked, so the thumb travels across the track while stopping short of the end edge.
 */
internal fun switchThumbOffsetPx(
    checked: Boolean,
    trackWidthPx: Float,
    thumbSizePx: Float,
    paddingPx: Float,
): Float = if (checked) trackWidthPx - thumbSizePx - 2f * paddingPx else 0f

/**
 * Resolves the drag target for a switch thumb drag.
 *
 * Returns the state the drag should settle into when the release position crossed half of the
 * maximum travel, or `null` when the drag stayed within the spring-back zone.
 */
internal fun switchDragTarget(
    checked: Boolean,
    dragOffsetPx: Float,
    maxOffsetPx: Float,
): Boolean? = when {
    dragOffsetPx > maxOffsetPx / 2f -> true
    dragOffsetPx < -maxOffsetPx / 2f -> false
    else -> null
}
