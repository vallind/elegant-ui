package com.elegant.compose.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf

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
 * Android is the only configured target today. The implementation lives in `commonMain` so future
 * Compose Multiplatform targets can reuse the same visual contract without changing component APIs.
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
