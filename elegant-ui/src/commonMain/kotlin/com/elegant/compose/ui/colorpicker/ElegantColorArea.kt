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
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.disabled
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.elegant.compose.ui.foundation.theme.ElegantColors
import com.elegant.compose.ui.foundation.theme.ElegantTheme
import kotlin.math.abs

/**
 * A color expressed in the HSV (hue, saturation, value) color space.
 *
 * [hue] is an angle in degrees in the `0..360` range, [saturation] and [value] are fractions in
 * the `0..1` range. The model is a pure-Kotlin value with no platform dependencies; convert from
 * and to [Color] with [rgbToHsv] and [hsvToRgb].
 */
@Immutable
internal data class HsvColor(
    val hue: Float,
    val saturation: Float,
    val value: Float,
)

/**
 * Converts [color] to HSV space.
 *
 * The hue is normalized into `0..360` and the saturation and value into `0..1`. Achromatic colors
 * (saturation zero) resolve to a hue of 0.
 */
internal fun rgbToHsv(color: Color): HsvColor {
    val red = color.red
    val green = color.green
    val blue = color.blue
    val max = maxOf(red, green, blue)
    val min = minOf(red, green, blue)
    val delta = max - min
    val hue = when {
        delta <= 0f -> 0f
        max == red -> 60f * (((green - blue) / delta) % 6f)
        max == green -> 60f * ((blue - red) / delta + 2f)
        else -> 60f * ((red - green) / delta + 4f)
    }
    val saturation = if (max <= 0f) 0f else delta / max
    return HsvColor(
        hue = ((hue % 360f) + 360f) % 360f,
        saturation = saturation.coerceIn(0f, 1f),
        value = max.coerceIn(0f, 1f),
    )
}

/**
 * Converts [hsv] back to RGB space.
 *
 * The hue wraps around 360 and the saturation and value are clamped into `0..1` before conversion,
 * so out-of-range channels degrade gracefully.
 */
internal fun hsvToRgb(hsv: HsvColor): Color {
    val hue = ((hsv.hue % 360f) + 360f) % 360f
    val saturation = hsv.saturation.coerceIn(0f, 1f)
    val value = hsv.value.coerceIn(0f, 1f)
    val chroma = value * saturation
    val secondary = chroma * (1f - abs((hue / 60f) % 2f - 1f))
    val match = value - chroma
    val (red, green, blue) = when {
        hue < 60f -> Triple(chroma, secondary, 0f)
        hue < 120f -> Triple(secondary, chroma, 0f)
        hue < 180f -> Triple(0f, chroma, secondary)
        hue < 240f -> Triple(0f, secondary, chroma)
        hue < 300f -> Triple(secondary, 0f, chroma)
        else -> Triple(chroma, 0f, secondary)
    }
    return Color(red + match, green + match, blue + match)
}

/**
 * Resolves [hue] to the fully saturated, full-value RGB color of that hue.
 *
 * [hue] is an angle in degrees; it wraps around 360 so any value is valid.
 */
internal fun hueToRgb(hue: Float): Color = hsvToRgb(HsvColor(hue, 1f, 1f))

/**
 * Clamps every channel of [hsv] into its valid range: hue into `0..360` and saturation and value
 * into `0..1`.
 */
internal fun clampHsv(hsv: HsvColor): HsvColor = HsvColor(
    hue = hsv.hue.coerceIn(0f, 360f),
    saturation = hsv.saturation.coerceIn(0f, 1f),
    value = hsv.value.coerceIn(0f, 1f),
)

/**
 * Theme-aware colors used by [ElegantColorArea].
 *
 * Use [ElegantColorAreaDefaults.colors] for theme-aware defaults, then use [copy] for supported
 * product-level customization.
 *
 * @property containerColor base fill painted behind the saturation/value gradients.
 * @property borderColor outline color of the panel; also the thumb ring color.
 * @property thumbColor fill color of the circular thumb.
 * @property focusedBorderColor outline and thumb ring color while the panel is keyboard focused.
 */
@Immutable
public data class ElegantColorAreaColors(
    val containerColor: Color,
    val borderColor: Color,
    val thumbColor: Color,
    val focusedBorderColor: Color,
)

/** Theme-aware defaults for [ElegantColorArea]. */
public object ElegantColorAreaDefaults {
    /** Default panel width. */
    public val Width: Dp = 220.dp

    /** Default panel height. */
    public val Height: Dp = 160.dp

    /** Diameter of the circular thumb. */
    public val ThumbSize: Dp = 16.dp

    /** Returns theme-aware colors. */
    @Composable
    public fun colors(): ElegantColorAreaColors = resolveColorAreaColors(ElegantTheme.colors)
}

/** Arrow-key step applied to saturation or value while the area is keyboard focused. */
internal val ColorAreaKeyStep: Float = 0.01f

/**
 * Presents a saturation (x) by value (y) color-selection panel.
 *
 * The panel is a controlled 220x160 area: [color] is owned by the caller, and dragging or tapping
 * the panel reports a new color with the same hue and the saturation and value resolved from the
 * pointer position through [onColorChange], which the caller must write back. The background is
 * filled with the hue of [color]: a horizontal white-to-hue gradient for saturation over a vertical
 * transparent-to-black gradient for value, so the top-left corner is white and the bottom row is
 * black. A 16dp circular thumb sits at the (saturation, 1 - value) fraction of the panel and draws
 * a 1dp ring in [ElegantColorAreaColors.borderColor] (the focused border color while focused) so it
 * stays visible on light areas.
 *
 * The panel maps saturation and value to physical axes and does not mirror in right-to-left
 * layouts. While it is keyboard focused, the arrow keys adjust saturation (left/right) and value
 * (up/down) by one hundredth of the range; the left/right axes are not layout-direction aware.
 *
 * While [enabled] is false the panel renders at 40% opacity, loses its focus border, and never
 * invokes [onColorChange]. The panel announces [Role.Slider] and the `#RRGGBB` hex value of
 * [color] as its content description.
 *
 * @param color currently selected color, owned by the caller.
 * @param onColorChange callback invoked with the color resolved from the pointer or key position.
 * @param modifier modifier applied once to the panel root; a size modifier overrides the default
 *   [ElegantColorAreaDefaults.Width] by [ElegantColorAreaDefaults.Height] sizing.
 * @param enabled whether the panel accepts pointer and keyboard interaction.
 * @param colors theme-aware colors.
 */
@Composable
public fun ElegantColorArea(
    color: Color,
    onColorChange: (Color) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    colors: ElegantColorAreaColors = ElegantColorAreaDefaults.colors(),
) {
    val interactionSource = remember { MutableInteractionSource() }
    val focused by interactionSource.collectIsFocusedAsState()
    val focusRingEnabled = ElegantTheme.focusRingEnabled
    val currentColor by rememberUpdatedState(color)
    val currentOnColorChange by rememberUpdatedState(onColorChange)
    val hueColor = hueToRgb(rgbToHsv(color).hue)
    val position = thumbPosition(color)
    val ringColor = if (focused && enabled && focusRingEnabled) colors.focusedBorderColor else colors.borderColor
    val fillAlpha = if (enabled) 1f else ColorPickerDisabledAlpha

    BoxWithConstraints(
        modifier = modifier
            .semantics(mergeDescendants = true) {
                contentDescription = colorHex(color)
                if (!enabled) disabled()
            }
            .size(ElegantColorAreaDefaults.Width, ElegantColorAreaDefaults.Height)
            .focusable(interactionSource = interactionSource, enabled = enabled)
            .onKeyEvent { event ->
                if (event.type == KeyEventType.KeyDown && enabled) {
                    val hsv = rgbToHsv(color)
                    val next = when (event.key) {
                        Key.DirectionRight -> hsv.copy(saturation = hsv.saturation + ColorAreaKeyStep)
                        Key.DirectionLeft -> hsv.copy(saturation = hsv.saturation - ColorAreaKeyStep)
                        Key.DirectionUp -> hsv.copy(value = hsv.value + ColorAreaKeyStep)
                        Key.DirectionDown -> hsv.copy(value = hsv.value - ColorAreaKeyStep)
                        else -> null
                    }
                    if (next != null) {
                        onColorChange(hsvToRgb(clampHsv(next)))
                        true
                    } else {
                        false
                    }
                } else {
                    false
                }
            }
            .pointerInput(enabled) {
                if (enabled) {
                    detectTapGestures(
                        onTap = { offset ->
                            val fraction = areaFractionFromPosition(
                                offset.x,
                                offset.y,
                                size.width.toFloat(),
                                size.height.toFloat(),
                            )
                            currentOnColorChange(panelColor(currentColor, fraction.x, fraction.y))
                        },
                    )
                }
            }
            .pointerInput(enabled) {
                if (enabled) {
                    detectDragGestures(
                        onDragStart = { offset ->
                            val fraction = areaFractionFromPosition(
                                offset.x,
                                offset.y,
                                size.width.toFloat(),
                                size.height.toFloat(),
                            )
                            currentOnColorChange(panelColor(currentColor, fraction.x, fraction.y))
                        },
                        onDrag = { change, _ ->
                            change.consume()
                            val fraction = areaFractionFromPosition(
                                change.position.x,
                                change.position.y,
                                size.width.toFloat(),
                                size.height.toFloat(),
                            )
                            currentOnColorChange(panelColor(currentColor, fraction.x, fraction.y))
                        },
                    )
                }
            },
        contentAlignment = Alignment.TopStart,
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .alpha(fillAlpha),
        ) {
            val ringWidth = ColorPickerRingWidth.toPx()
            drawRect(color = colors.containerColor)
            drawRect(brush = Brush.horizontalGradient(listOf(Color.White, hueColor)))
            drawRect(brush = Brush.verticalGradient(listOf(Color.Transparent, Color.Black)))
            drawRect(
                color = ringColor,
                topLeft = Offset(ringWidth / 2f, ringWidth / 2f),
                size = Size(size.width - ringWidth, size.height - ringWidth),
                style = Stroke(width = ringWidth),
            )
        }
        Box(
            modifier = Modifier
                .alpha(fillAlpha)
                .absoluteOffset(
                    x = maxWidth * position.x - ElegantColorAreaDefaults.ThumbSize / 2,
                    y = maxHeight * position.y - ElegantColorAreaDefaults.ThumbSize / 2,
                )
                .size(ElegantColorAreaDefaults.ThumbSize)
                .border(ColorPickerRingWidth, ringColor, CircleShape)
                .background(colors.thumbColor, CircleShape),
        )
    }
}

/** Resolves theme-aware [ElegantColorAreaColors] from [themeColors]. */
internal fun resolveColorAreaColors(themeColors: ElegantColors): ElegantColorAreaColors =
    ElegantColorAreaColors(
        containerColor = themeColors.surfaceSunken,
        borderColor = themeColors.borderDefault,
        thumbColor = themeColors.surfaceRaised,
        focusedBorderColor = themeColors.focusRing,
    )

/**
 * Resolves the color of the panel at the [xFraction] by [yFraction] position: the hue of [color]
 * is kept, the saturation becomes [xFraction], and the value becomes `1 - [yFraction]`.
 *
 * Both fractions are clamped into `0..1`.
 */
internal fun panelColor(color: Color, xFraction: Float, yFraction: Float): Color {
    val hsv = rgbToHsv(color)
    return hsvToRgb(
        HsvColor(
            hue = hsv.hue,
            saturation = xFraction.coerceIn(0f, 1f),
            value = (1f - yFraction).coerceIn(0f, 1f),
        ),
    )
}

/**
 * Resolves the thumb position of [color] as a fraction of the panel: the x component is the
 * saturation and the y component is `1 - value`, so the thumb moves up as the value increases.
 */
internal fun thumbPosition(color: Color): Offset {
    val hsv = rgbToHsv(color)
    return Offset(hsv.saturation, 1f - hsv.value)
}

/**
 * Maps a pointer position in pixels onto the `0..1` fractions of a panel measured by [widthPx] by
 * [heightPx].
 *
 * Positions outside the panel clamp to the nearest edge; a non-positive width or height resolves
 * to the origin.
 */
internal fun areaFractionFromPosition(
    xPx: Float,
    yPx: Float,
    widthPx: Float,
    heightPx: Float,
): Offset {
    val x = if (widthPx <= 0f) 0f else (xPx / widthPx).coerceIn(0f, 1f)
    val y = if (heightPx <= 0f) 0f else (yPx / heightPx).coerceIn(0f, 1f)
    return Offset(x, y)
}
