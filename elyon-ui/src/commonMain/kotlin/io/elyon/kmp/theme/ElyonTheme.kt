// Copyright 2025, compose-miuix-ui contributors
// SPDX-License-Identifier: Apache-2.0

package io.elyon.kmp.theme

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.LocalOverscrollFactory
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.remember
import io.elyon.kmp.utils.ElyonIndication
import io.elyon.kmp.utils.ElyonOverscrollFactory

/**
 * The Elyon theme that provides color and text styles for the Elyon components.
 * This theme supports dynamic color schemes through the [ThemeController].
 *
 * @param controller The [ThemeController] that controls the current color scheme.
 * @param textStyles The text styles for the Elyon components.
 * @param content The content of the Elyon theme.
 */
@Composable
fun ElyonTheme(
    controller: ThemeController,
    textStyles: TextStyles = ElyonTheme.textStyles,
    content: @Composable () -> Unit,
) {
    val rawColors = controller.currentColors()
    val elyonColors = remember { rawColors.copy() }.apply { updateColorsFrom(rawColors) }
    val elyonTextStyles = remember { textStyles.copy() }.apply { updateTextStylesFrom(textStyles) }
    val elyonIndication = remember(elyonColors.onBackground) { ElyonIndication(color = elyonColors.onBackground) }
    CompositionLocalProvider(
        LocalColors provides elyonColors,
        LocalTextStyles provides elyonTextStyles,
        LocalIndication provides elyonIndication,
        LocalContentColor provides elyonColors.onBackground,
        LocalColorSchemeMode provides controller.colorSchemeMode,
        LocalOverscrollFactory provides ElyonOverscrollFactory,
    ) {
        content()
    }
}

/**
 * The Elyon theme that provides color and text styles for the Elyon components.
 * This theme uses the provided [colors] and [textStyles].
 *
 * @param colors The color scheme for the Elyon components.
 * @param textStyles The text styles for the Elyon components.
 * @param content The content of the Elyon theme.
 */
@Composable
fun ElyonTheme(
    colors: Colors = ElyonTheme.colorScheme,
    textStyles: TextStyles = ElyonTheme.textStyles,
    content: @Composable () -> Unit,
) {
    val elyonColors = remember { colors.copy() }.apply { updateColorsFrom(colors) }
    val elyonTextStyles = remember { textStyles.copy() }.apply { updateTextStylesFrom(textStyles) }
    val elyonIndication = remember(elyonColors.onBackground) { ElyonIndication(color = elyonColors.onBackground) }
    CompositionLocalProvider(
        LocalColors provides elyonColors,
        LocalTextStyles provides elyonTextStyles,
        LocalIndication provides elyonIndication,
        LocalOverscrollFactory provides ElyonOverscrollFactory,
    ) {
        content()
    }
}

object ElyonTheme {
    val colorScheme: Colors
        @Composable @ReadOnlyComposable
        get() = LocalColors.current

    val textStyles: TextStyles
        @Composable @ReadOnlyComposable
        get() = LocalTextStyles.current

    val colorSchemeMode: ColorSchemeMode?
        @Composable @ReadOnlyComposable
        get() = LocalColorSchemeMode.current

    val isDynamicColor: Boolean
        @Composable @ReadOnlyComposable
        get() = when (colorSchemeMode) {
            ColorSchemeMode.MonetSystem,
            ColorSchemeMode.MonetLight,
            ColorSchemeMode.MonetDark,
            -> true

            else -> false
        }
}
