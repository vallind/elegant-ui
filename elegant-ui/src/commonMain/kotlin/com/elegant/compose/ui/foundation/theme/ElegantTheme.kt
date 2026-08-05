package com.elegant.compose.ui.foundation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

private val LocalElegantColors = staticCompositionLocalOf { ElegantLightColors }
private val LocalElegantTypography = staticCompositionLocalOf { DefaultElegantTypography }

/** Accesses Elegant UI semantic values from the current composition. */
public object ElegantTheme {
    /** Semantic color roles for the active light or dark theme. */
    public val colors: ElegantColors
        @Composable
        @ReadOnlyComposable
        get() = LocalElegantColors.current

    /** Typography roles owned by Elegant UI. */
    public val typography: ElegantTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalElegantTypography.current
}

/**
 * Provides Elegant UI colors and typography to [content].
 *
 * Android, Desktop JVM, and Web/Wasm share this implementation from `commonMain`, preserving
 * one visual and semantic contract across supported targets.
 */
@Composable
public fun ElegantTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    colors: ElegantColors = if (darkTheme) ElegantDarkColors else ElegantLightColors,
    typography: ElegantTypography = DefaultElegantTypography,
    content: @Composable () -> Unit,
) {
    val materialColors = if (darkTheme) {
        darkColorScheme(
            primary = colors.interactivePrimary,
            onPrimary = colors.textInverse,
            background = colors.backgroundCanvas,
            onBackground = colors.textPrimary,
            surface = colors.surfaceDefault,
            onSurface = colors.textPrimary,
            outline = colors.borderDefault,
        )
    } else {
        lightColorScheme(
            primary = colors.interactivePrimary,
            onPrimary = colors.textInverse,
            background = colors.backgroundCanvas,
            onBackground = colors.textPrimary,
            surface = colors.surfaceDefault,
            onSurface = colors.textPrimary,
            outline = colors.borderDefault,
        )
    }

    CompositionLocalProvider(
        LocalElegantColors provides colors,
        LocalElegantTypography provides typography,
    ) {
        MaterialTheme(
            colorScheme = materialColors,
            content = content,
        )
    }
}

/**
 * Provides Elegant UI colors from a [controller] and typography to [content].
 *
 * Resolves the palette through [ElegantThemeController.currentColors] so fixed, system-following,
 * seed-derived, and platform dynamic color schemes share one entry point. Mutating the controller
 * recomposes the theme.
 *
 * @param controller state holder that resolves the active [ElegantColors].
 * @param typography typography roles provided to [content].
 * @param content content rendered with the resolved palette.
 */
@Composable
public fun ElegantTheme(
    controller: ElegantThemeController,
    typography: ElegantTypography = DefaultElegantTypography,
    content: @Composable () -> Unit,
) {
    ElegantTheme(
        colors = controller.currentColors(),
        typography = typography,
        content = content,
    )
}

/**
 * Provides Elegant UI colors derived from a single [keyColor] seed.
 *
 * Convenience overload of [ElegantTheme] that derives the full [ElegantColors] palette from
 * [keyColor] with [deriveElegantColors] and delegates to the existing function, which keeps its
 * signature untouched. Pass an explicit `colors` value to the existing overload when the palette
 * needs to be built or customized outside the derivation.
 *
 * @param keyColor seed color the light or dark palette derives from.
 * @param darkTheme whether to derive the dark palette.
 * @param typography typography roles provided to [content].
 * @param content content rendered with the derived palette.
 */
@Composable
public fun ElegantTheme(
    keyColor: Color,
    darkTheme: Boolean = isSystemInDarkTheme(),
    typography: ElegantTypography = DefaultElegantTypography,
    content: @Composable () -> Unit,
) {
    ElegantTheme(
        darkTheme = darkTheme,
        colors = deriveElegantColors(keyColor, darkTheme),
        typography = typography,
        content = content,
    )
}
