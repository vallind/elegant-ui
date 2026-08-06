// Copyright 2026, elegant-ui contributors
// SPDX-License-Identifier: Apache-2.0

package com.elegant.compose.ui.foundation.theme

import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import kotlin.test.Test
import kotlin.test.assertEquals

internal class ElegantTypographyContractTest {

    @Test
    fun baseRolesKeepTheStableScale() {
        val typography = DefaultElegantTypography

        assertEquals(12.sp, typography.labelSmall.fontSize)
        assertEquals(14.sp, typography.labelMedium.fontSize)
        assertEquals(16.sp, typography.labelLarge.fontSize)
        assertEquals(14.sp, typography.bodyMedium.fontSize)
        assertEquals(18.sp, typography.titleMedium.fontSize)
    }

    @Test
    fun extendedRolesAddTheHyperosReadingScale() {
        val typography = DefaultElegantTypography

        assertEquals(17.sp, typography.bodyLarge.fontSize)
        assertEquals(13.sp, typography.footnote.fontSize)
        assertEquals(20.sp, typography.titleLarge.fontSize)
        assertEquals(24.sp, typography.titleXl.fontSize)
        assertEquals(32.sp, typography.headlineLarge.fontSize)
    }

    @Test
    fun extendedRolesKeepExplicitWeightsAndLineHeights() {
        val typography = DefaultElegantTypography

        assertEquals(FontWeight.Normal, typography.bodyLarge.fontWeight)
        assertEquals(24.sp, typography.bodyLarge.lineHeight)
        assertEquals(FontWeight.Normal, typography.headlineLarge.fontWeight)
        assertEquals(40.sp, typography.headlineLarge.lineHeight)
        assertEquals(FontWeight.SemiBold, typography.titleLarge.fontWeight)
        assertEquals(FontWeight.SemiBold, typography.titleXl.fontWeight)
    }

    @Test
    fun customTypographyOverridesExtendedRolesIndependently() {
        val custom = DefaultElegantTypography.copy(
            headlineLarge = DefaultElegantTypography.headlineLarge.copy(fontSize = 40.sp),
        )

        assertEquals(40.sp, custom.headlineLarge.fontSize)
        // untouched roles fall back to the defaults
        assertEquals(20.sp, custom.titleLarge.fontSize)
        assertEquals(17.sp, custom.bodyLarge.fontSize)
    }
}
