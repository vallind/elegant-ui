package com.elegant.compose.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

/**
 * Typography roles owned by Elegant UI and shared by all supported targets.
 *
 * @property labelSmall compact component label style.
 * @property labelMedium default component label style.
 * @property labelLarge prominent component label style.
 * @property bodyMedium default supporting-content style.
 * @property titleMedium standard section-title style.
 */
@Immutable
public data class ElegantTypography(
    val labelSmall: TextStyle,
    val labelMedium: TextStyle,
    val labelLarge: TextStyle,
    val bodyMedium: TextStyle,
    val titleMedium: TextStyle,
)

internal val DefaultElegantTypography = ElegantTypography(
    labelSmall = TextStyle(
        fontSize = 12.sp,
        lineHeight = 16.sp,
        fontWeight = FontWeight.Medium,
        letterSpacing = 0.2.sp,
    ),
    labelMedium = TextStyle(
        fontSize = 14.sp,
        lineHeight = 20.sp,
        fontWeight = FontWeight.Medium,
        letterSpacing = 0.1.sp,
    ),
    labelLarge = TextStyle(
        fontSize = 16.sp,
        lineHeight = 22.sp,
        fontWeight = FontWeight.SemiBold,
        letterSpacing = 0.sp,
    ),
    bodyMedium = TextStyle(
        fontSize = 14.sp,
        lineHeight = 20.sp,
        fontWeight = FontWeight.Normal,
    ),
    titleMedium = TextStyle(
        fontSize = 18.sp,
        lineHeight = 24.sp,
        fontWeight = FontWeight.SemiBold,
    ),
)
