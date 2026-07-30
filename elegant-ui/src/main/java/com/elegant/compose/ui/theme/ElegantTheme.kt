package com.elegant.compose.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

private val LocalElegantColors = staticCompositionLocalOf { ElegantLightColors }

public object ElegantTheme {
    public val colors: ElegantColors
        @Composable
        @ReadOnlyComposable
        get() = LocalElegantColors.current

    public val typography
        @Composable
        @ReadOnlyComposable
        get() = MaterialTheme.typography
}

@Composable
public fun ElegantTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colors = if (darkTheme) ElegantDarkColors else ElegantLightColors
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

    androidx.compose.runtime.CompositionLocalProvider(LocalElegantColors provides colors) {
        MaterialTheme(
            colorScheme = materialColors,
            content = content,
        )
    }
}
