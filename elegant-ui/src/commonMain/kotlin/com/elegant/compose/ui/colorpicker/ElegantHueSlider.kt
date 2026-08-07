package com.elegant.compose.ui.colorpicker

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.focusable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.semantics.ProgressBarRangeInfo
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.disabled
import androidx.compose.ui.semantics.progressBarRangeInfo
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.elegant.compose.ui.foundation.theme.ElegantColors
import com.elegant.compose.ui.foundation.theme.ElegantTheme

/**
 * Theme-aware colors used by [ElegantHueSlider].
 *
 * Use [ElegantHueSliderDefaults.colors] for theme-aware defaults, then use [copy] for supported
 * product-level customization.
 *
 * @property trackColor base color painted behind the rainbow hue gradient; transparent by default
 *   because the gradient is the track.
 * @property thumbColor fill color of the circular thumb.
 * @property borderColor outline color of the track; also the thumb ring color.
 * @property focusedBorderColor outline and thumb ring color while the slider is keyboard focused.
 */
@Immutable
public data class ElegantHueSliderColors(
    val trackColor: Color,
    val thumbColor: Color,
    val borderColor: Color,
    val focusedBorderColor: Color,
)

/** Theme-aware defaults for [ElegantHueSlider]. */
public object ElegantHueSliderDefaults {
    /** Height of the rainbow track. */
    public val Height: Dp = 12.dp

    /** Diameter of the circular thumb. */
    public val ThumbSize: Dp = 16.dp

    /** Returns theme-aware colors. */
    @Composable
    public fun colors(): ElegantHueSliderColors = resolveHueSliderColors(ElegantTheme.colors)
}

/** Minimum interactive root height of the hue slider. */
internal val HueSliderMinimumTouchHeight: Dp = 48.dp

/** Arrow-key step applied to the hue in degrees while the slider is keyboard focused. */
internal const val HueSliderKeyStep: Float = 10f

/**
 * Presents a controlled hue-selection slider.
 *
 * The slider renders a full-width 12dp track filled with the rainbow hue gradient inside a 48dp
 * minimum interactive root. [hue] is controlled: dragging or tapping the track maps the pointer
 * position onto the `0..360` hue circle and reports the resolved hue through [onHueChange], which
 * the caller must write back. A 16dp circular thumb sits at the `hue / 360` fraction of the track
 * and draws a 1dp ring in [ElegantHueSliderColors.borderColor] (the focused border color while
 * focused) so it stays visible on every hue. While the slider is focused, the arrow keys move the
 * hue by [HueSliderKeyStep] degrees, mirroring the visual direction in right-to-left layouts.
 *
 * While [enabled] is false the slider renders at 40% opacity, loses its focus border, and never
 * invokes [onHueChange]. The slider announces [Role.Slider] with a
 * [ProgressBarRangeInfo] describing [hue] over the `0..360` range.
 *
 * @param hue current hue in degrees, owned by the caller; values outside `0..360` are clamped and
 *   the thumb renders at the nearest end.
 * @param onHueChange callback invoked with the resolved hue after user interaction.
 * @param modifier modifier applied once to the interactive root.
 * @param enabled whether the slider accepts pointer and keyboard interaction.
 * @param colors theme-aware colors.
 */
@Composable
public fun ElegantHueSlider(
    hue: Float,
    onHueChange: (Float) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    colors: ElegantHueSliderColors = ElegantHueSliderDefaults.colors(),
) {
    val interactionSource = remember { MutableInteractionSource() }
    val focused by interactionSource.collectIsFocusedAsState()
    val focusRingEnabled = ElegantTheme.focusRingEnabled
    val currentOnHueChange by rememberUpdatedState(onHueChange)
    val layoutDirection = LocalLayoutDirection.current
    val trackBorderColor = if (focused && enabled && focusRingEnabled) colors.focusedBorderColor else colors.borderColor
    val thumbRingColor = if (focused && enabled && focusRingEnabled) colors.focusedBorderColor else colors.borderColor
    val fillAlpha = if (enabled) 1f else ColorPickerDisabledAlpha

    BoxWithConstraints(
        modifier = modifier
            .semantics(mergeDescendants = true) {
                progressBarRangeInfo = ProgressBarRangeInfo(hue, 0f..360f, 0)
                if (!enabled) disabled()
            }
            .defaultMinSize(minHeight = HueSliderMinimumTouchHeight)
            .fillMaxWidth()
            .focusable(interactionSource = interactionSource, enabled = enabled)
            .onKeyEvent { event ->
                if (event.type == KeyEventType.KeyDown && enabled) {
                    val increase = when (event.key) {
                        Key.DirectionRight -> layoutDirection == LayoutDirection.Ltr
                        Key.DirectionLeft -> layoutDirection == LayoutDirection.Rtl
                        else -> null
                    }
                    if (increase != null) {
                        val next = if (increase) hue + HueSliderKeyStep else hue - HueSliderKeyStep
                        onHueChange(next.coerceIn(0f, 360f))
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
                    fun positionAt(x: Float): Float {
                        val raw = x.coerceIn(0f, size.width.toFloat())
                        return if (layoutDirection == LayoutDirection.Rtl) {
                            size.width - raw
                        } else {
                            raw
                        }
                    }

                    detectTapGestures(
                        onTap = { offset ->
                            currentOnHueChange(
                                hueFromPosition(positionAt(offset.x), size.width.toFloat()),
                            )
                        },
                    )
                }
            }
            .pointerInput(enabled, layoutDirection) {
                if (enabled) {
                    fun positionAt(x: Float): Float {
                        val raw = x.coerceIn(0f, size.width.toFloat())
                        return if (layoutDirection == LayoutDirection.Rtl) {
                            size.width - raw
                        } else {
                            raw
                        }
                    }

                    detectDragGestures(
                        onDragStart = { offset ->
                            currentOnHueChange(
                                hueFromPosition(positionAt(offset.x), size.width.toFloat()),
                            )
                        },
                        onDrag = { change, _ ->
                            change.consume()
                            currentOnHueChange(
                                hueFromPosition(positionAt(change.position.x), size.width.toFloat()),
                            )
                        },
                    )
                }
            },
        contentAlignment = Alignment.CenterStart,
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(ElegantHueSliderDefaults.Height)
                .alpha(fillAlpha),
        ) {
            val radius = CornerRadius(size.height / 2f)
            val ringWidth = ColorPickerRingWidth.toPx()
            drawRoundRect(
                color = colors.trackColor,
                cornerRadius = radius,
            )
            drawRoundRect(
                brush = Brush.horizontalGradient(hueGradientColors()),
                cornerRadius = radius,
            )
            drawRoundRect(
                color = trackBorderColor,
                cornerRadius = radius,
                style = Stroke(width = ringWidth),
            )
        }
        Box(
            modifier = Modifier
                .alpha(fillAlpha)
                .offset(
                    x = maxWidth * thumbXFraction(hue) - ElegantHueSliderDefaults.ThumbSize / 2,
                )
                .size(ElegantHueSliderDefaults.ThumbSize)
                .border(ColorPickerRingWidth, thumbRingColor, CircleShape)
                .background(colors.thumbColor, CircleShape),
        )
    }
}

/** Resolves theme-aware [ElegantHueSliderColors] from [themeColors]. */
internal fun resolveHueSliderColors(themeColors: ElegantColors): ElegantHueSliderColors =
    ElegantHueSliderColors(
        trackColor = Color.Transparent,
        thumbColor = themeColors.surfaceRaised,
        borderColor = themeColors.borderDefault,
        focusedBorderColor = themeColors.focusRing,
    )

/**
 * Resolves the hue in degrees for a pointer position [xPx] on a track measured by [widthPx].
 *
 * The position is mapped linearly onto `0..360` and clamped, so positions outside the track
 * resolve to the nearest end; a non-positive width resolves to 0.
 */
internal fun hueFromPosition(xPx: Float, widthPx: Float): Float {
    if (widthPx <= 0f) return 0f
    return ((xPx / widthPx) * 360f).coerceIn(0f, 360f)
}

/**
 * Returns the rainbow hue spectrum rendered by [ElegantHueSlider]: red, yellow, green, cyan, blue,
 * magenta, and red again at the 360-degree wrap.
 *
 * The stops are the required visual spectrum of the track and, like the curated swatch palette,
 * are a deliberate product constant that intentionally bypasses theme roles.
 */
internal fun hueGradientColors(): List<Color> =
    listOf(0f, 60f, 120f, 180f, 240f, 300f, 360f).map { hueToRgb(it) }

/**
 * Resolves the thumb position of [hue] as a fraction of the track.
 *
 * The hue is divided by 360 and clamped into `0..1`; a NaN hue resolves to 0.
 */
internal fun thumbXFraction(hue: Float): Float {
    if (hue.isNaN()) return 0f
    return (hue / 360f).coerceIn(0f, 1f)
}
