package com.elegant.compose.ui.closebutton

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.elegant.compose.ui.foundation.theme.ElegantColors
import com.elegant.compose.ui.foundation.theme.ElegantMotion
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

internal class ElegantCloseButtonContractTest {
    @Test
    fun defaultColorsResolveFromThemeRoles() {
        val resolved = resolveCloseButtonColors(testThemeColors)

        assertEquals(Color.Transparent, resolved.containerColor)
        assertEquals(testThemeColors.textSecondary, resolved.contentColor)
        assertEquals(testThemeColors.surfaceHover, resolved.hoveredContainerColor)
        assertEquals(testThemeColors.backgroundSubtle, resolved.pressedContainerColor)
        assertEquals(testThemeColors.textTertiary, resolved.disabledContentColor)
        assertEquals(testThemeColors.focusRing, resolved.focusedBorderColor)
    }

    @Test
    fun disabledStateOverridesPressedAndHovered() {
        val visuals = resolveCloseButtonVisuals(
            colors = testColors,
            enabled = false,
            pressed = true,
            hovered = true,
        )

        assertEquals(testColors.containerColor, visuals.container)
        assertEquals(testColors.disabledContentColor, visuals.content)
    }

    @Test
    fun pressedStateOverridesHovered() {
        val visuals = resolveCloseButtonVisuals(
            colors = testColors,
            enabled = true,
            pressed = true,
            hovered = true,
        )

        assertEquals(testColors.pressedContainerColor, visuals.container)
        assertEquals(testColors.contentColor, visuals.content)
    }

    @Test
    fun hoveredStateFillsThePill() {
        val visuals = resolveCloseButtonVisuals(
            colors = testColors,
            enabled = true,
            pressed = false,
            hovered = true,
        )

        assertEquals(testColors.hoveredContainerColor, visuals.container)
        assertEquals(testColors.contentColor, visuals.content)
    }

    @Test
    fun restingStateKeepsThePillQuiet() {
        val visuals = resolveCloseButtonVisuals(
            colors = testColors,
            enabled = true,
            pressed = false,
            hovered = false,
        )

        assertEquals(testColors.containerColor, visuals.container)
        assertEquals(testColors.contentColor, visuals.content)
    }

    @Test
    fun glyphEndpointsStayInsideBounds() {
        val sizePx = 28f
        val endpoints = closeGlyphEndpoints(sizePx = sizePx)

        assertEquals(4, endpoints.size)
        endpoints.forEach { endpoint ->
            assertTrue(endpoint.x in 0f..sizePx)
            assertTrue(endpoint.y in 0f..sizePx)
        }
    }

    @Test
    fun glyphEndpointsFormDiagonalSymmetry() {
        val sizePx = 40f
        val endpoints = closeGlyphEndpoints(sizePx = sizePx)
        val inset = sizePx * DefaultCloseGlyphInsetFraction

        assertEquals(Offset(inset, inset), endpoints[0])
        assertEquals(Offset(sizePx - inset, sizePx - inset), endpoints[1])
        assertEquals(Offset(sizePx - inset, inset), endpoints[2])
        assertEquals(Offset(inset, sizePx - inset), endpoints[3])
        assertEquals(
            distanceBetween(endpoints[0], endpoints[1]),
            distanceBetween(endpoints[2], endpoints[3]),
        )
    }

    @Test
    fun glyphInsetFractionIsClamped() {
        val sizePx = 100f
        val wideGlyph = closeGlyphEndpoints(sizePx = sizePx, insetFraction = 0.1f)
        val narrowGlyph = closeGlyphEndpoints(sizePx = sizePx, insetFraction = 0.6f)

        assertEquals(20f, wideGlyph[0].x, absoluteTolerance = 0.001f)
        assertEquals(20f, wideGlyph[0].y, absoluteTolerance = 0.001f)
        assertEquals(45f, narrowGlyph[0].x, absoluteTolerance = 0.001f)
        assertEquals(45f, narrowGlyph[0].y, absoluteTolerance = 0.001f)
    }

    @Test
    fun glyphEndpointOrderMatchesDrawingOrder() {
        val endpoints = closeGlyphEndpoints(sizePx = 20f, insetFraction = 0.25f)

        assertEquals(endpoints[0].x, endpoints[0].y)
        assertEquals(endpoints[1].x, endpoints[1].y)
        assertEquals(endpoints[2].x, endpoints[3].y)
        assertEquals(endpoints[2].y, endpoints[3].x)
    }

    @Test
    fun defaultsPreserveCompactGeometry() {
        assertEquals(48.dp, ElegantCloseButtonDefaults.MinimumTouchHeight)
        assertEquals(28.dp, ElegantCloseButtonDefaults.VisualSize)
        assertEquals(
            ElegantMotion.standardDurationMillis,
            ElegantCloseButtonDefaults.AnimationDurationMillis,
        )
        assertEquals(0.3f, DefaultCloseGlyphInsetFraction)
        assertEquals(0.2f, MinCloseGlyphInsetFraction)
        assertEquals(0.45f, MaxCloseGlyphInsetFraction)
    }

    private companion object {
        val testThemeColors = ElegantColors(
            backgroundCanvas = Color(0xFF000001),
            backgroundSubtle = Color(0xFF000002),
            surfaceDefault = Color(0xFF000003),
            surfaceRaised = Color(0xFF000004),
            surfaceSunken = Color(0xFF000005),
            textPrimary = Color(0xFF000006),
            textSecondary = Color(0xFF000007),
            textTertiary = Color(0xFF000008),
            textInverse = Color(0xFF000009),
            borderDefault = Color(0xFF00000A),
            borderStrong = Color(0xFF00000B),
            interactivePrimary = Color(0xFF00000C),
            interactivePrimaryPressed = Color(0xFF00000D),
            focusRing = Color(0xFF00000E),
            surfaceHover = Color(0xFF00000F),
        )

        val testColors = ElegantCloseButtonColors(
            containerColor = Color(0xFF100000),
            contentColor = Color(0xFF100001),
            hoveredContainerColor = Color(0xFF100002),
            pressedContainerColor = Color(0xFF100003),
            disabledContentColor = Color(0xFF100004),
            focusedBorderColor = Color(0xFF100005),
        )

        fun distanceBetween(start: Offset, end: Offset): Float {
            val dx = end.x - start.x
            val dy = end.y - start.y
            return dx * dx + dy * dy
        }
    }
}
