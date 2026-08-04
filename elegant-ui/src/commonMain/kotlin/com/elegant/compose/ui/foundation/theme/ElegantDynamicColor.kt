package com.elegant.compose.ui.foundation.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance

private const val TextInverseLuminanceThreshold = 0.45f
private const val HoverLightenAmount = 0.06f
private const val PressedDarkenAmount = 0.10f
private const val FocusRingLightenAmount = 0.30f
private const val StatusHuePositive = 150f
private const val StatusHueWarning = 45f
private const val StatusHueCritical = 350f
private const val StatusSaturationFloor = 0.45f
private const val StatusLightnessLight = 0.33f
private const val StatusLightnessDark = 0.60f

private const val LightCanvasSaturation = 0.05f
private const val LightCanvasLightness = 0.97f
private const val LightSubtleSaturation = 0.05f
private const val LightSubtleLightness = 0.945f
private const val LightDefaultSaturation = 0.04f
private const val LightDefaultLightness = 0.995f
private const val LightRaisedSaturation = 0.05f
private const val LightRaisedLightness = 1.0f
private const val LightSunkenSaturation = 0.06f
private const val LightSunkenLightness = 0.925f
private const val LightHoverSaturation = 0.08f
private const val LightHoverLightness = 0.955f
private const val LightTextPrimaryLightness = 0.10f
private const val LightTextSecondaryLightness = 0.44f
private const val LightTextTertiaryLightness = 0.58f
private const val LightBorderDefaultLightness = 0.88f
private const val LightBorderStrongLightness = 0.77f

private const val DarkCanvasSaturation = 0.05f
private const val DarkCanvasLightness = 0.072f
private const val DarkSubtleSaturation = 0.05f
private const val DarkSubtleLightness = 0.10f
private const val DarkDefaultSaturation = 0.05f
private const val DarkDefaultLightness = 0.115f
private const val DarkRaisedSaturation = 0.06f
private const val DarkRaisedLightness = 0.14f
private const val DarkSunkenSaturation = 0.05f
private const val DarkSunkenLightness = 0.078f
private const val DarkHoverSaturation = 0.09f
private const val DarkHoverLightness = 0.165f
private const val DarkTextPrimaryLightness = 0.965f
private const val DarkTextSecondaryLightness = 0.67f
private const val DarkTextTertiaryLightness = 0.47f
private const val DarkBorderDefaultLightness = 0.22f
private const val DarkBorderStrongLightness = 0.34f

internal data class HslColor(
    val h: Float,
    val s: Float,
    val l: Float,
)

internal fun rgbToHsl(color: Color): HslColor {
    val r = color.red
    val g = color.green
    val b = color.blue
    val max = maxOf(r, g, b)
    val min = minOf(r, g, b)
    val l = (max + min) / 2f
    if (max == min) {
        return HslColor(h = 0f, s = 0f, l = l)
    }
    val delta = max - min
    val s = if (l > 0.5f) delta / (2f - max - min) else delta / (max + min)
    val h = when (max) {
        r -> 60f * ((g - b) / delta + if (g < b) 6f else 0f)
        g -> 60f * ((b - r) / delta + 2f)
        else -> 60f * ((r - g) / delta + 4f)
    }
    return HslColor(h = clampHue(h), s = s, l = l)
}

internal fun hslToColor(hsl: HslColor): Color {
    val h = clampHue(hsl.h)
    val c = (1f - kotlin.math.abs(2f * hsl.l - 1f)) * hsl.s
    val x = c * (1f - kotlin.math.abs(h / 60f % 2f - 1f))
    val m = hsl.l - c / 2f
    val (r, g, b) = when {
        h < 60f -> Triple(c, x, 0f)
        h < 120f -> Triple(x, c, 0f)
        h < 180f -> Triple(0f, c, x)
        h < 240f -> Triple(0f, x, c)
        h < 300f -> Triple(x, 0f, c)
        else -> Triple(c, 0f, x)
    }
    return Color(red = r + m, green = g + m, blue = b + m)
}

internal fun clampHue(h: Float): Float = ((h % 360f) + 360f) % 360f

internal fun shiftHue(color: Color, degrees: Float): Color {
    val hsl = rgbToHsl(color)
    return hslToColor(HslColor(h = clampHue(hsl.h + degrees), s = hsl.s, l = hsl.l))
}

internal fun lighten(color: Color, amount: Float): Color {
    val hsl = rgbToHsl(color)
    return hslToColor(HslColor(h = hsl.h, s = hsl.s, l = (hsl.l + amount).coerceIn(0f, 1f)))
}

internal fun darken(color: Color, amount: Float): Color {
    val hsl = rgbToHsl(color)
    return hslToColor(HslColor(h = hsl.h, s = hsl.s, l = (hsl.l - amount).coerceIn(0f, 1f)))
}

internal fun contrastRatio(colorA: Color, colorB: Color): Float {
    val light = maxOf(colorA.luminance(), colorB.luminance())
    val dark = minOf(colorA.luminance(), colorB.luminance())
    return (light + 0.05f) / (dark + 0.05f)
}

internal fun bestInverseTextColor(background: Color): Color =
    if (background.luminance() < TextInverseLuminanceThreshold) Color.White else Color(0xFF111216)

private fun statusTone(seed: HslColor, hue: Float, darkTheme: Boolean): Color {
    val lightness = if (darkTheme) StatusLightnessDark else StatusLightnessLight
    return hslToColor(HslColor(h = hue, s = seed.s.coerceAtLeast(StatusSaturationFloor), l = lightness))
}

/**
 * Derives a complete [ElegantColors] palette from a single [keyColor] seed.
 *
 * The derivation is pure, deterministic, and implemented entirely in common Kotlin so the same
 * seed produces the same palette on Android, Desktop JVM, and Web/Wasm. The derived palette fills
 * the same [ElegantColors] structure the built-in light and dark instances use, so every component
 * keeps resolving its roles without changes. The seed is treated as opaque; alpha is ignored.
 *
 * Derivation rules:
 * - `interactivePrimary` is the key color itself; hover lightens it by `0.06`, press darkens it by
 *   `0.10`, and the focus ring lightens it by `0.30`.
 * - `textInverse` is white when key-color luminance is below `0.45`, otherwise near-black
 *   (`#111216`); `onStatus*` colors resolve the same way against their status container.
 * - Surfaces keep the seed hue with a low saturation and fixed neutral lightness per theme
 *   (canvases at `0.97`/`0.072`, raised surfaces at `1.0`/`0.14`, hovered surfaces at `0.955`/
 *   `0.165` for light/dark).
 * - Text and borders are pure neutral grays with fixed lightness per theme.
 * - Status colors fix their hue at green `150`, amber `45`, and red `350`; saturation keeps the
 *   seed saturation floored at `0.45`; lightness is `0.33` in light and `0.60` in dark themes.
 *
 * @param keyColor seed color every derived role builds on.
 * @param darkTheme whether to derive the dark palette (dark surfaces, light text, brightened
 * status tones) instead of the light palette.
 */
public fun deriveElegantColors(keyColor: Color, darkTheme: Boolean): ElegantColors {
    val seed = rgbToHsl(keyColor)
    val interactivePrimary = keyColor
    val interactivePrimaryHover = lighten(keyColor, HoverLightenAmount)
    val interactivePrimaryPressed = darken(keyColor, PressedDarkenAmount)
    val focusRing = lighten(keyColor, FocusRingLightenAmount)
    val textInverse = bestInverseTextColor(keyColor)
    val statusPositive = statusTone(seed, StatusHuePositive, darkTheme)
    val statusWarning = statusTone(seed, StatusHueWarning, darkTheme)
    val statusCritical = statusTone(seed, StatusHueCritical, darkTheme)
    return if (darkTheme) {
        ElegantColors(
            backgroundCanvas = hslToColor(HslColor(seed.h, DarkCanvasSaturation, DarkCanvasLightness)),
            backgroundSubtle = hslToColor(HslColor(seed.h, DarkSubtleSaturation, DarkSubtleLightness)),
            surfaceDefault = hslToColor(HslColor(seed.h, DarkDefaultSaturation, DarkDefaultLightness)),
            surfaceRaised = hslToColor(HslColor(seed.h, DarkRaisedSaturation, DarkRaisedLightness)),
            surfaceSunken = hslToColor(HslColor(seed.h, DarkSunkenSaturation, DarkSunkenLightness)),
            surfaceHover = hslToColor(HslColor(seed.h, DarkHoverSaturation, DarkHoverLightness)),
            textPrimary = hslToColor(HslColor(h = 0f, s = 0f, l = DarkTextPrimaryLightness)),
            textSecondary = hslToColor(HslColor(h = 0f, s = 0f, l = DarkTextSecondaryLightness)),
            textTertiary = hslToColor(HslColor(h = 0f, s = 0f, l = DarkTextTertiaryLightness)),
            textInverse = textInverse,
            borderDefault = hslToColor(HslColor(h = 0f, s = 0f, l = DarkBorderDefaultLightness)),
            borderStrong = hslToColor(HslColor(h = 0f, s = 0f, l = DarkBorderStrongLightness)),
            interactivePrimary = interactivePrimary,
            interactivePrimaryHover = interactivePrimaryHover,
            interactivePrimaryPressed = interactivePrimaryPressed,
            focusRing = focusRing,
            statusPositive = statusPositive,
            onStatusPositive = bestInverseTextColor(statusPositive),
            statusWarning = statusWarning,
            onStatusWarning = bestInverseTextColor(statusWarning),
            statusCritical = statusCritical,
            onStatusCritical = bestInverseTextColor(statusCritical),
        )
    } else {
        ElegantColors(
            backgroundCanvas = hslToColor(HslColor(seed.h, LightCanvasSaturation, LightCanvasLightness)),
            backgroundSubtle = hslToColor(HslColor(seed.h, LightSubtleSaturation, LightSubtleLightness)),
            surfaceDefault = hslToColor(HslColor(seed.h, LightDefaultSaturation, LightDefaultLightness)),
            surfaceRaised = hslToColor(HslColor(seed.h, LightRaisedSaturation, LightRaisedLightness)),
            surfaceSunken = hslToColor(HslColor(seed.h, LightSunkenSaturation, LightSunkenLightness)),
            surfaceHover = hslToColor(HslColor(seed.h, LightHoverSaturation, LightHoverLightness)),
            textPrimary = hslToColor(HslColor(h = 0f, s = 0f, l = LightTextPrimaryLightness)),
            textSecondary = hslToColor(HslColor(h = 0f, s = 0f, l = LightTextSecondaryLightness)),
            textTertiary = hslToColor(HslColor(h = 0f, s = 0f, l = LightTextTertiaryLightness)),
            textInverse = textInverse,
            borderDefault = hslToColor(HslColor(h = 0f, s = 0f, l = LightBorderDefaultLightness)),
            borderStrong = hslToColor(HslColor(h = 0f, s = 0f, l = LightBorderStrongLightness)),
            interactivePrimary = interactivePrimary,
            interactivePrimaryHover = interactivePrimaryHover,
            interactivePrimaryPressed = interactivePrimaryPressed,
            focusRing = focusRing,
            statusPositive = statusPositive,
            onStatusPositive = bestInverseTextColor(statusPositive),
            statusWarning = statusWarning,
            onStatusWarning = bestInverseTextColor(statusWarning),
            statusCritical = statusCritical,
            onStatusCritical = bestInverseTextColor(statusCritical),
        )
    }
}

/**
 * Monet-style dynamic color controller for Elegant UI.
 *
 * Holds a single [keyColor] seed and exposes the palettes [deriveElegantColors] produces from it
 * for the light and dark themes. The controller is a plain state holder with no platform coupling;
 * pass the palette to `ElegantTheme(colors = ...)` or use the `ElegantTheme(keyColor = ...)`
 * overload directly.
 *
 * @property keyColor seed color the derived palettes build on.
 */
public class ElegantThemeController(
    public val keyColor: Color,
) {
    /** Palette derived from [keyColor] for the light theme. */
    @Composable
    @ReadOnlyComposable
    public fun lightColors(): ElegantColors = deriveElegantColors(keyColor, darkTheme = false)

    /** Palette derived from [keyColor] for the dark theme. */
    @Composable
    @ReadOnlyComposable
    public fun darkColors(): ElegantColors = deriveElegantColors(keyColor, darkTheme = true)
}
