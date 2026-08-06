package com.elegant.compose.ui.foundation.theme

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
 * @property bodyLarge large supporting-content style on the HyperOS 17sp reading scale.
 * @property titleLarge prominent section-title style; used by collapsed app-bar titles.
 * @property titleXl large section-title style.
 * @property headlineLarge display title style; used by expanded large app-bar titles.
 * @property footnote small annotation style.
 */
@Immutable
public data class ElegantTypography(
    val labelSmall: TextStyle,
    val labelMedium: TextStyle,
    val labelLarge: TextStyle,
    val bodyMedium: TextStyle,
    val titleMedium: TextStyle,
    val bodyLarge: TextStyle = TextStyle(
        fontSize = 17.sp,
        lineHeight = 24.sp,
        fontWeight = FontWeight.Normal,
    ),
    val titleLarge: TextStyle = TextStyle(
        fontSize = 20.sp,
        lineHeight = 26.sp,
        fontWeight = FontWeight.SemiBold,
    ),
    val titleXl: TextStyle = TextStyle(
        fontSize = 24.sp,
        lineHeight = 30.sp,
        fontWeight = FontWeight.SemiBold,
    ),
    val headlineLarge: TextStyle = TextStyle(
        fontSize = 32.sp,
        lineHeight = 40.sp,
        fontWeight = FontWeight.Normal,
    ),
    val footnote: TextStyle = TextStyle(
        fontSize = 13.sp,
        lineHeight = 18.sp,
        fontWeight = FontWeight.Normal,
    ),
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
