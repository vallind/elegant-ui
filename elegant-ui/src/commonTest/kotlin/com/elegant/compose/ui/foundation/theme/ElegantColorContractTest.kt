package com.elegant.compose.ui.foundation.theme

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

internal class ElegantColorContractTest {
    @Test
    fun legacyConstructorArgumentsKeepStatusFallbacks() {
        val colors = ElegantColors(
            backgroundCanvas = ElegantLightColors.backgroundCanvas,
            backgroundSubtle = ElegantLightColors.backgroundSubtle,
            surfaceDefault = ElegantLightColors.surfaceDefault,
            surfaceRaised = ElegantLightColors.surfaceRaised,
            surfaceSunken = ElegantLightColors.surfaceSunken,
            textPrimary = ElegantLightColors.textPrimary,
            textSecondary = ElegantLightColors.textSecondary,
            textTertiary = ElegantLightColors.textTertiary,
            textInverse = ElegantLightColors.textInverse,
            borderDefault = ElegantLightColors.borderDefault,
            borderStrong = ElegantLightColors.borderStrong,
            interactivePrimary = ElegantLightColors.interactivePrimary,
            interactivePrimaryPressed = ElegantLightColors.interactivePrimaryPressed,
            focusRing = ElegantLightColors.focusRing,
        )

        assertEquals(colors.interactivePrimary, colors.statusPositive)
        assertEquals(colors.interactivePrimary, colors.statusWarning)
        assertEquals(colors.interactivePrimary, colors.statusCritical)
        assertEquals(colors.textInverse, colors.onStatusPositive)
        assertEquals(colors.textInverse, colors.onStatusWarning)
        assertEquals(colors.textInverse, colors.onStatusCritical)
    }

    @Test
    fun builtInStatusRolesAreDistinctAndThemeAware() {
        assertNotEquals(ElegantLightColors.statusPositive, ElegantLightColors.statusWarning)
        assertNotEquals(ElegantLightColors.statusWarning, ElegantLightColors.statusCritical)
        assertNotEquals(ElegantDarkColors.statusPositive, ElegantDarkColors.statusWarning)
        assertNotEquals(ElegantDarkColors.statusWarning, ElegantDarkColors.statusCritical)
        assertNotEquals(ElegantLightColors.statusPositive, ElegantDarkColors.statusPositive)
        assertNotEquals(ElegantLightColors.onStatusPositive, ElegantDarkColors.onStatusPositive)
    }
}
