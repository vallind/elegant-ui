package com.elegant.compose.ui.link

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.elegant.compose.ui.foundation.theme.ElegantDarkColors
import com.elegant.compose.ui.foundation.theme.ElegantLightColors
import com.elegant.compose.ui.foundation.theme.ElegantMotion
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue

internal class ElegantLinkContractTest {

    @Test
    fun colorsResolveSemanticThemeRoles() {
        val light = resolveLinkColors(ElegantLightColors)
        val dark = resolveLinkColors(ElegantDarkColors)

        assertEquals(ElegantLightColors.interactivePrimary, light.contentColor)
        assertEquals(ElegantLightColors.interactivePrimaryHover, light.hoveredContentColor)
        assertEquals(ElegantLightColors.interactivePrimary, light.pressedContentColor)
        assertEquals(ElegantLightColors.textTertiary, light.disabledContentColor)
        assertEquals(
            ElegantLightColors.interactivePrimary.copy(alpha = LinkUnderlineAlpha),
            light.underlineColor,
        )
        assertNotEquals(light, dark, "link colors must follow the active theme")
    }

    @Test
    fun stateColorsFallBackToTheRestingContentColor() {
        val colors = ElegantLinkColors(contentColor = Color.Red)

        assertEquals(Color.Red, colors.hoveredContentColor)
        assertEquals(Color.Red, colors.pressedContentColor)
        assertEquals(Color.Red, colors.disabledContentColor)
        assertEquals(Color.Red, colors.underlineColor)

        val custom = ElegantLinkColors(
            contentColor = Color.Red,
            underlineColor = Color.Blue,
        )
        assertEquals(Color.Blue, custom.underlineColor)
        assertEquals(
            ElegantLinkColors(contentColor = Color.Red),
            ElegantLinkColors(contentColor = Color.Red).copy(),
        )
    }

    @Test
    fun visualsPrecedenceFollowsDisabledPressedHoveredResting() {
        val colors = ElegantLinkColors(
            contentColor = Color.Red,
            hoveredContentColor = Color.Magenta,
            pressedContentColor = Color.Yellow,
            disabledContentColor = Color.Gray,
            underlineColor = Color.Blue,
        )

        val disabled = resolveLinkVisuals(
            colors = colors,
            enabled = false,
            pressed = true,
            hovered = true,
        )
        assertEquals(Color.Gray, disabled.content)
        assertEquals(Color.Gray, disabled.underline)

        val pressed = resolveLinkVisuals(
            colors = colors,
            enabled = true,
            pressed = true,
            hovered = true,
        )
        assertEquals(Color.Yellow, pressed.content)
        assertEquals(Color.Yellow, pressed.underline)

        val hovered = resolveLinkVisuals(
            colors = colors,
            enabled = true,
            pressed = false,
            hovered = true,
        )
        assertEquals(Color.Magenta, hovered.content)
        assertEquals(Color.Magenta, hovered.underline)

        val resting = resolveLinkVisuals(
            colors = colors,
            enabled = true,
            pressed = false,
            hovered = false,
        )
        assertEquals(Color.Red, resting.content)
        assertEquals(Color.Blue, resting.underline)
    }

    @Test
    fun underlineYOffsetsAboveTheBottomEdgeAndClampsToZero() {
        assertEquals(0f, underlineY(0f))
        assertEquals(0f, underlineY(0.5f))
        assertEquals(0f, underlineY(1f))
        assertEquals(9f, underlineY(10f))
        assertEquals(0f, underlineY(-5f))
    }

    @Test
    fun defaultsConstantsMeetAccessibilityAndRhythmRequirements() {
        assertTrue(ElegantLinkDefaults.MinimumTouchHeight >= 48.dp)
        assertEquals(ElegantMotion.standardDurationMillis, ElegantLinkDefaults.AnimationDurationMillis)
        assertEquals(1.dp, LinkUnderlineWidth)
        assertEquals(0.5f, LinkUnderlineAlpha)
    }
}
