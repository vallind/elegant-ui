package com.elegant.compose.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

@Immutable
public data class ElegantColors(
    val backgroundCanvas: Color,
    val backgroundSubtle: Color,
    val surfaceDefault: Color,
    val surfaceRaised: Color,
    val surfaceSunken: Color,
    val textPrimary: Color,
    val textSecondary: Color,
    val textTertiary: Color,
    val textInverse: Color,
    val borderDefault: Color,
    val borderStrong: Color,
    val interactivePrimary: Color,
    val interactivePrimaryPressed: Color,
    val focusRing: Color,
)

internal val ElegantLightColors = ElegantColors(
    backgroundCanvas = Color(0xFFF7F7F8),
    backgroundSubtle = Color(0xFFF1F1F3),
    surfaceDefault = Color(0xFFFFFFFF),
    surfaceRaised = Color(0xFFFFFFFF),
    surfaceSunken = Color(0xFFEDEEF1),
    textPrimary = Color(0xFF17181A),
    textSecondary = Color(0xFF6E727A),
    textTertiary = Color(0xFF92969E),
    textInverse = Color(0xFFFFFFFF),
    borderDefault = Color(0xFFE1E2E6),
    borderStrong = Color(0xFFC5C8CF),
    interactivePrimary = Color(0xFF6C4EFF),
    interactivePrimaryPressed = Color(0xFF5739D8),
    focusRing = Color(0xFF9B8AFF),
)

internal val ElegantDarkColors = ElegantColors(
    backgroundCanvas = Color(0xFF111216),
    backgroundSubtle = Color(0xFF17191E),
    surfaceDefault = Color(0xFF191B20),
    surfaceRaised = Color(0xFF202229),
    surfaceSunken = Color(0xFF121318),
    textPrimary = Color(0xFFF6F7F9),
    textSecondary = Color(0xFFA9ADB5),
    textTertiary = Color(0xFF747983),
    textInverse = Color(0xFF111216),
    borderDefault = Color(0xFF343740),
    borderStrong = Color(0xFF50545F),
    interactivePrimary = Color(0xFF8B78FF),
    interactivePrimaryPressed = Color(0xFF725EE8),
    focusRing = Color(0xFFAA9BFF),
)
