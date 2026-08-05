// Copyright 2026, elegant-ui contributors
// SPDX-License-Identifier: Apache-2.0

package com.elegant.compose.ui.foundation.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
internal actual fun platformDynamicColors(dark: Boolean): ElegantColors = elegantColorsFromSeed(
    seed = Color(MonetFallbackSeedArgb),
    colorSpec = ElegantThemeColorSpec.Spec2021,
    paletteStyle = ElegantThemePaletteStyle.TonalSpot,
    dark = dark,
)
