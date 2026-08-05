// Copyright 2026, elegant-ui contributors
// SPDX-License-Identifier: Apache-2.0

package com.elegant.compose.ui.foundation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color

/**
 * How [ElegantThemeController] resolves the active color scheme.
 *
 * [System], [Light], and [Dark] use the fixed [lightColors]/[darkColors] palettes; the
 * `Monet*` modes derive a palette from the [keyColor] seed with the Material 3 dynamic color
 * algorithm, or from the platform system palette when no [keyColor] is set
 * (see [platformDynamicColors]).
 */
@Stable
public enum class ElegantColorSchemeMode {
    /** Follow the system light/dark appearance with the fixed palettes. */
    System,

    /** Always use [lightColors][ElegantThemeController.lightColors]. */
    Light,

    /** Always use [darkColors][ElegantThemeController.darkColors]. */
    Dark,

    /** Follow the system appearance, deriving colors from the seed or platform palette. */
    MonetSystem,

    /** Derive a light palette from the seed or platform palette. */
    MonetLight,

    /** Derive a dark palette from the seed or platform palette. */
    MonetDark,
}

/**
 * The palette style used by the Material 3 dynamic color algorithm when a [Monet] mode is active.
 */
@Stable
public enum class ElegantThemePaletteStyle {
    /** Baseline tonal-spot scheme. */
    TonalSpot,

    /** Low-chroma neutral scheme. */
    Neutral,

    /** High-chroma vibrant scheme. */
    Vibrant,

    /** Expressive hue-shifted scheme. */
    Expressive,

    /** Rainbow multi-hue scheme. */
    Rainbow,

    /** Fruit-salad complementary-hue scheme. */
    FruitSalad,

    /** Fully monochrome scheme. */
    Monochrome,

    /** Fidelity scheme that preserves the seed hue. */
    Fidelity,

    /** Content scheme tuned for content-driven palettes. */
    Content,
}

/**
 * The Material color specification revision used for dynamic color derivation.
 */
@Stable
public enum class ElegantThemeColorSpec {
    /** Material color specification revision 2021. */
    Spec2021,

    /**
     * Material color specification revision 2025. Honored only by palette styles whose schemes
     * support it; other styles downgrade to [Spec2021] at derivation time.
     */
    Spec2025,
}

/**
 * A state holder that controls how [ElegantTheme] resolves its [ElegantColors].
 *
 * In the fixed modes the controller returns one of the [lightColors]/[darkColors] palettes; in
 * the `Monet*` modes it derives a palette from [keyColor] with [elegantColorsFromSeed], falling
 * back to [platformDynamicColors] when no seed is set. All fields are backed by compose state, so
 * mutating any of them recomposes the theme.
 *
 * @param colorSchemeMode the active [ElegantColorSchemeMode].
 * @param lightColors fixed palette used by [ElegantColorSchemeMode.Light] and the light branch of
 * [ElegantColorSchemeMode.System].
 * @param darkColors fixed palette used by [ElegantColorSchemeMode.Dark] and the dark branch of
 * [ElegantColorSchemeMode.System].
 * @param keyColor seed color for the `Monet*` modes; `null` falls back to the platform palette.
 * @param colorSpec requested Material color specification for Monet derivation.
 * @param paletteStyle palette style for Monet derivation.
 * @param isDark explicit dark-mode override; `null` follows the system appearance.
 */
@Stable
public class ElegantThemeController(
    colorSchemeMode: ElegantColorSchemeMode = ElegantColorSchemeMode.System,
    lightColors: ElegantColors = ElegantLightColors,
    darkColors: ElegantColors = ElegantDarkColors,
    keyColor: Color? = null,
    colorSpec: ElegantThemeColorSpec = ElegantThemeColorSpec.Spec2021,
    paletteStyle: ElegantThemePaletteStyle = ElegantThemePaletteStyle.TonalSpot,
    isDark: Boolean? = null,
) {
    /** The active [ElegantColorSchemeMode]; mutation recomposes the theme. */
    public var colorSchemeMode: ElegantColorSchemeMode by mutableStateOf(colorSchemeMode)

    /** Fixed palette used for light appearance; mutation recomposes the theme. */
    public var lightColors: ElegantColors by mutableStateOf(lightColors)

    /** Fixed palette used for dark appearance; mutation recomposes the theme. */
    public var darkColors: ElegantColors by mutableStateOf(darkColors)

    /** Seed color for Monet derivation; `null` falls back to the platform palette. */
    public var keyColor: Color? by mutableStateOf(keyColor)

    /** Requested Material color specification for Monet derivation. */
    public var colorSpec: ElegantThemeColorSpec by mutableStateOf(colorSpec)

    /** Palette style for Monet derivation. */
    public var paletteStyle: ElegantThemePaletteStyle by mutableStateOf(paletteStyle)

    /** Explicit dark-mode override; `null` follows the system appearance. */
    public var isDark: Boolean? by mutableStateOf(isDark)

    /**
     * Seed-based controller that keeps the HSL derivation contract.
     *
     * Convenience constructor that derives the light and dark palettes from [keyColor] with
     * [deriveElegantColors] and starts in [ElegantColorSchemeMode.System].
     *
     * @param keyColor seed color the derived palettes build on.
     */
    public constructor(keyColor: Color) : this(
        colorSchemeMode = ElegantColorSchemeMode.System,
        lightColors = deriveElegantColors(keyColor, darkTheme = false),
        darkColors = deriveElegantColors(keyColor, darkTheme = true),
        keyColor = keyColor,
    )

    /**
     * Resolves the [ElegantColors] for the current [colorSchemeMode].
     *
     * Monet modes derive from [keyColor] when set and cache the derivation per seed, spec,
     * style, and appearance; without a seed they defer to [platformDynamicColors].
     */
    @Composable
    public fun currentColors(): ElegantColors = when (colorSchemeMode) {
        ElegantColorSchemeMode.System -> {
            val dark = isDark ?: isSystemInDarkTheme()
            if (dark) darkColors else lightColors
        }

        ElegantColorSchemeMode.Light -> lightColors

        ElegantColorSchemeMode.Dark -> darkColors

        ElegantColorSchemeMode.MonetSystem -> {
            val dark = isDark ?: isSystemInDarkTheme()
            keyColor?.let {
                remember(it, colorSpec, paletteStyle, dark) {
                    elegantColorsFromSeed(seed = it, colorSpec = colorSpec, paletteStyle = paletteStyle, dark = dark)
                }
            } ?: platformDynamicColors(dark = dark)
        }

        ElegantColorSchemeMode.MonetLight -> {
            keyColor?.let {
                remember(it, colorSpec, paletteStyle) {
                    elegantColorsFromSeed(seed = it, colorSpec = colorSpec, paletteStyle = paletteStyle, dark = false)
                }
            } ?: platformDynamicColors(dark = false)
        }

        ElegantColorSchemeMode.MonetDark -> {
            keyColor?.let {
                remember(it, colorSpec, paletteStyle) {
                    elegantColorsFromSeed(seed = it, colorSpec = colorSpec, paletteStyle = paletteStyle, dark = true)
                }
            } ?: platformDynamicColors(dark = true)
        }
    }
}

/**
 * Resolves the platform's dynamic color palette for the [dark] appearance.
 *
 * Android reads the system wallpaper palette (API 33+ palette overlay or API 31+ system color
 * resources); Desktop JVM and Web/Wasm fall back to the fixed Monet fallback seed.
 */
@Composable
internal expect fun platformDynamicColors(dark: Boolean): ElegantColors
