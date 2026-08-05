// Copyright 2026, elegant-ui contributors
// SPDX-License-Identifier: Apache-2.0

package com.elegant.compose.ui.foundation.theme

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.materialkolor.dynamiccolor.ColorSpec
import com.materialkolor.hct.Hct
import com.materialkolor.scheme.DynamicScheme
import com.materialkolor.scheme.SchemeContent
import com.materialkolor.scheme.SchemeExpressive
import com.materialkolor.scheme.SchemeFidelity
import com.materialkolor.scheme.SchemeFruitSalad
import com.materialkolor.scheme.SchemeMonochrome
import com.materialkolor.scheme.SchemeNeutral
import com.materialkolor.scheme.SchemeRainbow
import com.materialkolor.scheme.SchemeTonalSpot
import com.materialkolor.scheme.SchemeVibrant

/** Fallback seed used whenever no system or caller palette is available. */
internal const val MonetFallbackSeedArgb = 0xFF6750A4

/** The Material 3 dynamic color roles a seed scheme exposes before mapping into [ElegantColors]. */
internal data class ElegantMonetRoles(
    val primary: Color,
    val onPrimary: Color,
    val primaryFixed: Color,
    val onPrimaryFixed: Color,
    val error: Color,
    val onError: Color,
    val errorContainer: Color,
    val onErrorContainer: Color,
    val primaryContainer: Color,
    val onPrimaryContainer: Color,
    val secondary: Color,
    val onSecondary: Color,
    val secondaryContainer: Color,
    val onSecondaryContainer: Color,
    val tertiaryContainer: Color,
    val onTertiaryContainer: Color,
    val background: Color,
    val onBackground: Color,
    val surface: Color,
    val onSurface: Color,
    val surfaceVariant: Color,
    val surfaceContainer: Color,
    val surfaceContainerHigh: Color,
    val surfaceContainerHighest: Color,
    val outline: Color,
    val outlineVariant: Color,
    val onSurfaceVariant: Color,
)

internal const val MonetSurfaceSunkenDarkenAmount = 0.05f
internal const val MonetSurfaceHoverLightenAmount = 0.04f

/**
 * Maps Material 3 dynamic color roles onto the [ElegantColors] contract.
 *
 * Interactive, text, background, surface, and border roles map 1:1 from the Monet scheme;
 * status roles use the tertiary, secondary, and error containers so positive, warning, and
 * critical stay visually separated. Hover, press, focus, sunken, and hover-surface roles have
 * no direct Monet equivalent, so they derive from their base role with the same HSL shifts the
 * seed derivation uses, keeping interaction visuals consistent across palettes.
 */
internal fun mapMonetRolesToElegantColors(roles: ElegantMonetRoles): ElegantColors {
    val interactivePrimary = roles.primary
    val surfaceDefault = roles.surfaceContainer
    return ElegantColors(
        backgroundCanvas = roles.background,
        backgroundSubtle = roles.surfaceVariant,
        surfaceDefault = surfaceDefault,
        surfaceRaised = roles.surfaceContainerHigh,
        surfaceSunken = darken(surfaceDefault, MonetSurfaceSunkenDarkenAmount),
        surfaceHover = lighten(surfaceDefault, MonetSurfaceHoverLightenAmount),
        textPrimary = roles.onSurface,
        textSecondary = roles.onSurfaceVariant,
        textTertiary = roles.outline,
        textInverse = roles.onPrimary,
        borderDefault = roles.outlineVariant,
        borderStrong = roles.outline,
        interactivePrimary = interactivePrimary,
        interactivePrimaryHover = lighten(interactivePrimary, HoverLightenAmount),
        interactivePrimaryPressed = darken(interactivePrimary, PressedDarkenAmount),
        focusRing = lighten(interactivePrimary, FocusRingLightenAmount),
        statusPositive = roles.tertiaryContainer,
        onStatusPositive = roles.onTertiaryContainer,
        statusWarning = roles.secondaryContainer,
        onStatusWarning = roles.onSecondaryContainer,
        statusCritical = roles.errorContainer,
        onStatusCritical = roles.onErrorContainer,
    )
}

/**
 * Derives a complete [ElegantColors] palette from a [seed] color with the Material 3 dynamic
 * color algorithm (HCT + tonal schemes), then maps the scheme roles through
 * [mapMonetRolesToElegantColors]. Pure, deterministic, and identical across targets.
 *
 * [colorSpec] selects the Material color specification; [Spec2025][ElegantThemeColorSpec.Spec2025]
 * is honored only for palette styles whose scheme supports it and downgrades to
 * [Spec2021][ElegantThemeColorSpec.Spec2021] otherwise.
 */
internal fun elegantColorsFromSeed(
    seed: Color,
    colorSpec: ElegantThemeColorSpec,
    paletteStyle: ElegantThemePaletteStyle,
    dark: Boolean,
): ElegantColors {
    val internalSpec = when {
        colorSpec == ElegantThemeColorSpec.Spec2025 && paletteStyle.supportsSpec2025() -> ColorSpec.SpecVersion.SPEC_2025
        else -> ColorSpec.SpecVersion.SPEC_2021
    }
    val hctColor = Hct.fromInt(seed.toArgb())
    val scheme: DynamicScheme = when (paletteStyle) {
        ElegantThemePaletteStyle.TonalSpot -> SchemeTonalSpot(
            sourceColorHct = hctColor,
            isDark = dark,
            contrastLevel = 0.0,
            specVersion = internalSpec,
            platform = DynamicScheme.Platform.PHONE,
        )

        ElegantThemePaletteStyle.Neutral -> SchemeNeutral(
            sourceColorHct = hctColor,
            isDark = dark,
            contrastLevel = 0.0,
            specVersion = internalSpec,
            platform = DynamicScheme.Platform.PHONE,
        )

        ElegantThemePaletteStyle.Vibrant -> SchemeVibrant(
            sourceColorHct = hctColor,
            isDark = dark,
            contrastLevel = 0.0,
            specVersion = internalSpec,
            platform = DynamicScheme.Platform.PHONE,
        )

        ElegantThemePaletteStyle.Expressive -> SchemeExpressive(
            sourceColorHct = hctColor,
            isDark = dark,
            contrastLevel = 0.0,
            specVersion = internalSpec,
            platform = DynamicScheme.Platform.PHONE,
        )

        ElegantThemePaletteStyle.Rainbow -> SchemeRainbow(
            sourceColorHct = hctColor,
            isDark = dark,
            contrastLevel = 0.0,
            specVersion = internalSpec,
            platform = DynamicScheme.Platform.PHONE,
        )

        ElegantThemePaletteStyle.FruitSalad -> SchemeFruitSalad(
            sourceColorHct = hctColor,
            isDark = dark,
            contrastLevel = 0.0,
            specVersion = internalSpec,
            platform = DynamicScheme.Platform.PHONE,
        )

        ElegantThemePaletteStyle.Monochrome -> SchemeMonochrome(
            sourceColorHct = hctColor,
            isDark = dark,
            contrastLevel = 0.0,
            specVersion = internalSpec,
            platform = DynamicScheme.Platform.PHONE,
        )

        ElegantThemePaletteStyle.Fidelity -> SchemeFidelity(
            sourceColorHct = hctColor,
            isDark = dark,
            contrastLevel = 0.0,
            specVersion = internalSpec,
            platform = DynamicScheme.Platform.PHONE,
        )

        ElegantThemePaletteStyle.Content -> SchemeContent(
            sourceColorHct = hctColor,
            isDark = dark,
            contrastLevel = 0.0,
            specVersion = internalSpec,
            platform = DynamicScheme.Platform.PHONE,
        )
    }
    val roles = ElegantMonetRoles(
        primary = Color(scheme.primary),
        onPrimary = Color(scheme.onPrimary),
        primaryFixed = Color(scheme.primaryFixed),
        onPrimaryFixed = Color(scheme.onPrimaryFixed),
        error = Color(scheme.error),
        onError = Color(scheme.onError),
        errorContainer = Color(scheme.errorContainer),
        onErrorContainer = Color(scheme.onErrorContainer),
        primaryContainer = Color(scheme.primaryContainer),
        onPrimaryContainer = Color(scheme.onPrimaryContainer),
        secondary = Color(scheme.secondary),
        onSecondary = Color(scheme.onSecondary),
        secondaryContainer = Color(scheme.secondaryContainer),
        onSecondaryContainer = Color(scheme.onSecondaryContainer),
        tertiaryContainer = Color(scheme.tertiaryContainer),
        onTertiaryContainer = Color(scheme.onTertiaryContainer),
        background = Color(scheme.background),
        onBackground = Color(scheme.onBackground),
        surface = Color(scheme.surface),
        onSurface = Color(scheme.onSurface),
        surfaceVariant = Color(scheme.surfaceVariant),
        surfaceContainer = Color(scheme.surfaceContainer),
        surfaceContainerHigh = Color(scheme.surfaceContainerHigh),
        surfaceContainerHighest = Color(scheme.surfaceContainerHighest),
        outline = Color(scheme.outline),
        outlineVariant = Color(scheme.outlineVariant),
        onSurfaceVariant = Color(scheme.onSurfaceVariant),
    )
    return mapMonetRolesToElegantColors(roles)
}

private fun ElegantThemePaletteStyle.supportsSpec2025(): Boolean = when (this) {
    ElegantThemePaletteStyle.TonalSpot,
    ElegantThemePaletteStyle.Neutral,
    ElegantThemePaletteStyle.Vibrant,
    ElegantThemePaletteStyle.Expressive,
    -> true

    else -> false
}
