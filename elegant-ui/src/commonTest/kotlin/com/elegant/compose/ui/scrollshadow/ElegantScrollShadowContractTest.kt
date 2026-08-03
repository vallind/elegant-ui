package com.elegant.compose.ui.scrollshadow

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.elegant.compose.ui.theme.ElegantDarkColors
import com.elegant.compose.ui.theme.ElegantLightColors
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue

internal class ElegantScrollShadowContractTest {
    @Test
    fun publicEnumsRemainStable() {
        assertEquals(
            listOf("Vertical", "Horizontal"),
            ElegantScrollShadowOrientation.entries.map(ElegantScrollShadowOrientation::name),
        )
    }

    @Test
    fun publicDefaultsRemainStable() {
        assertEquals(24.dp, ElegantScrollShadowDefaults.ShadowHeight)
        assertEquals(0.35f, ElegantScrollShadowDefaults.MaxAlpha)
    }

    @Test
    fun leadingAlphaScalesWithTheScrolledDistance() {
        assertEquals(0f, leadingAlpha(0f, 1000f, 24f, 0.35f))
        assertEquals(0.0875f, leadingAlpha(6f, 1000f, 24f, 0.35f))
        assertEquals(0.175f, leadingAlpha(12f, 1000f, 24f, 0.35f))
        assertEquals(0.35f, leadingAlpha(24f, 1000f, 24f, 0.35f))
        assertEquals(0.35f, leadingAlpha(480f, 1000f, 24f, 0.35f))
        assertEquals(0.2f, leadingAlpha(480f, 1000f, 24f, 0.2f))
    }

    @Test
    fun trailingAlphaScalesWithTheRemainingDistance() {
        assertEquals(0.35f, trailingAlpha(0f, 1000f, 24f, 0.35f))
        assertEquals(0.35f, trailingAlpha(500f, 1000f, 24f, 0.35f))
        assertEquals(0.175f, trailingAlpha(988f, 1000f, 24f, 0.35f))
        assertEquals(0.0875f, trailingAlpha(994f, 1000f, 24f, 0.35f))
        assertEquals(0f, trailingAlpha(1000f, 1000f, 24f, 0.35f))
        assertEquals(0.2f, trailingAlpha(0f, 1000f, 24f, 0.2f))
    }

    @Test
    fun alphaMathIsNanSafeAndDimensionSafe() {
        assertEquals(0f, leadingAlpha(Float.NaN, 1000f, 24f, 0.35f))
        assertEquals(0f, leadingAlpha(10f, Float.NaN, 24f, 0.35f))
        assertEquals(0f, leadingAlpha(10f, 1000f, Float.NaN, 0.35f))
        assertEquals(0f, leadingAlpha(10f, 1000f, 24f, Float.NaN))
        assertEquals(0f, leadingAlpha(10f, 1000f, 0f, 0.35f))
        assertEquals(0f, leadingAlpha(10f, 1000f, -24f, 0.35f))
        assertEquals(0f, leadingAlpha(10f, 1000f, 24f, 0f))
        assertEquals(0f, leadingAlpha(10f, 1000f, 24f, -0.35f))

        assertEquals(0f, trailingAlpha(Float.NaN, 1000f, 24f, 0.35f))
        assertEquals(0f, trailingAlpha(10f, Float.NaN, 24f, 0.35f))
        assertEquals(0f, trailingAlpha(10f, 1000f, Float.NaN, 0.35f))
        assertEquals(0f, trailingAlpha(10f, 1000f, 24f, Float.NaN))
        assertEquals(0f, trailingAlpha(10f, 1000f, 0f, 0.35f))
        assertEquals(0f, trailingAlpha(10f, 1000f, -24f, 0.35f))
        assertEquals(0f, trailingAlpha(10f, 1000f, 24f, 0f))
        assertEquals(0f, trailingAlpha(10f, 1000f, 24f, -0.35f))
    }

    @Test
    fun drawFlagsFollowTheScrollPosition() {
        assertTrue(shouldDrawLeading(1f, 1000f))
        assertFalse(shouldDrawLeading(0f, 1000f))
        assertFalse(shouldDrawLeading(-1f, 1000f))
        assertFalse(shouldDrawLeading(Float.NaN, 1000f))

        assertTrue(shouldDrawTrailing(0f, 1000f))
        assertTrue(shouldDrawTrailing(500f, 1000f))
        assertFalse(shouldDrawTrailing(1000f, 1000f))
        assertFalse(shouldDrawTrailing(1001f, 1000f))
        assertFalse(shouldDrawTrailing(Float.NaN, 1000f))
        assertFalse(shouldDrawTrailing(10f, Float.NaN))
    }

    @Test
    fun defaultColorsResolveSemanticThemeRoles() {
        val light = resolveScrollShadowColors(ElegantLightColors)
        val dark = resolveScrollShadowColors(ElegantDarkColors)

        assertEquals(ElegantLightColors.textPrimary, light.shadowColor)
        assertEquals(ElegantDarkColors.textPrimary, dark.shadowColor)
        assertNotEquals(light.shadowColor, dark.shadowColor)
        assertNotEquals(light, dark)
    }

    @Test
    fun customColorsRemainAnImmutableValueContract() {
        val colors = ElegantScrollShadowColors(
            shadowColor = Color.Red,
        )

        assertEquals(Color.Red, colors.shadowColor)
        assertEquals(colors, colors.copy())
    }
}
