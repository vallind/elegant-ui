package com.elegant.compose.ui.foundation.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

/**
 * Semantic color roles used by Elegant UI components.
 *
 * Prefer supplying product colors through these roles instead of passing raw colors to individual
 * components. This preserves consistent light/dark behavior and keeps component APIs portable.
 */
@Immutable
public data class ElegantColors(
    /** App or screen canvas background. */
    val backgroundCanvas: Color,
    /** Subtle background used for pressed or grouped surfaces. */
    val backgroundSubtle: Color,
    /** Default content surface. */
    val surfaceDefault: Color,
    /** Raised content surface. */
    val surfaceRaised: Color,
    /** Recessed or disabled surface. */
    val surfaceSunken: Color,
    /** Highest-emphasis text and icon color. */
    val textPrimary: Color,
    /** Supporting text and icon color. */
    val textSecondary: Color,
    /** Disabled or lowest-emphasis text and icon color. */
    val textTertiary: Color,
    /** Text and icon color shown on strong interactive containers. */
    val textInverse: Color,
    /** Default separator and outline color. */
    val borderDefault: Color,
    /** Strong separator and pressed outline color. */
    val borderStrong: Color,
    /** Primary interactive color. */
    val interactivePrimary: Color,
    /** Pressed primary interactive color. */
    val interactivePrimaryPressed: Color,
    /** Keyboard or accessibility focus-ring color. */
    val focusRing: Color,
    /** Hovered surface for pointer-driven interaction. */
    val surfaceHover: Color = surfaceRaised,
    /** Hovered primary interactive color. */
    val interactivePrimaryHover: Color = interactivePrimary,
    /** Positive status container or indicator color. */
    val statusPositive: Color = interactivePrimary,
    /** Text and icon color shown on positive status containers. */
    val onStatusPositive: Color = textInverse,
    /** Warning status container or indicator color. */
    val statusWarning: Color = interactivePrimary,
    /** Text and icon color shown on warning status containers. */
    val onStatusWarning: Color = textInverse,
    /** Critical status container or indicator color. */
    val statusCritical: Color = interactivePrimary,
    /** Text and icon color shown on critical status containers. */
    val onStatusCritical: Color = textInverse,
)

internal val ElegantLightColors = ElegantColors(
    backgroundCanvas = Color(0xFFFFFFFF),
    backgroundSubtle = Color(0xFFF1F1F3),
    surfaceDefault = Color(0xFFFFFFFF),
    surfaceRaised = Color(0xFFFFFFFF),
    surfaceHover = Color(0xFFF0F0F0),
    surfaceSunken = Color(0xFFEDEEF1),
    textPrimary = Color(0xFF17181A),
    textSecondary = Color(0x99000000),
    textTertiary = Color(0x66000000),
    textInverse = Color(0xFFFFFFFF),
    borderDefault = Color(0xFFE0E0E0),
    borderStrong = Color(0xFFC5C8CF),
    interactivePrimary = Color(0xFF3482FF),
    interactivePrimaryHover = Color(0xFF4A90FF),
    interactivePrimaryPressed = Color(0xFF1F6DE8),
    focusRing = Color(0xFF7FB2FF),
    statusPositive = Color(0xFF147D64),
    onStatusPositive = Color(0xFFFFFFFF),
    statusWarning = Color(0xFF965C00),
    onStatusWarning = Color(0xFFFFFFFF),
    statusCritical = Color(0xFFC63D52),
    onStatusCritical = Color(0xFFFFFFFF),
)

internal val ElegantDarkColors = ElegantColors(
    backgroundCanvas = Color(0xFF242424),
    backgroundSubtle = Color(0xFF17191E),
    surfaceDefault = Color(0xFF242424),
    surfaceRaised = Color(0xFF2D2D2D),
    surfaceHover = Color(0xFF2D2D2D),
    surfaceSunken = Color(0xFF121318),
    textPrimary = Color(0xFFF6F7F9),
    textSecondary = Color(0x80FFFFFF),
    textTertiary = Color(0x66FFFFFF),
    textInverse = Color(0xFFFFFFFF),
    borderDefault = Color(0xFF393939),
    borderStrong = Color(0xFF50545F),
    interactivePrimary = Color(0xFF277AF7),
    interactivePrimaryHover = Color(0xFF3E87F8),
    interactivePrimaryPressed = Color(0xFF1D5FD8),
    focusRing = Color(0xFF6FA5F7),
    statusPositive = Color(0xFF55D6A2),
    onStatusPositive = Color(0xFF07251A),
    statusWarning = Color(0xFFF2B84B),
    onStatusWarning = Color(0xFF2D2100),
    statusCritical = Color(0xFFFF8FA3),
    onStatusCritical = Color(0xFF300A11),
)
