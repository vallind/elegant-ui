package com.elegant.compose.ui.floatingtoolbar

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.elegant.compose.ui.foundation.theme.ElegantColors
import kotlin.test.Test
import kotlin.test.assertEquals

internal class ElegantFloatingToolbarContractTest {
    @Test
    fun themeRolesResolveIntoToolbarColors() {
        val resolved = resolveFloatingToolbarColors(testThemeColors)

        assertEquals(testThemeColors.surfaceRaised, resolved.containerColor)
        assertEquals(testThemeColors.textPrimary, resolved.contentColor)
        assertEquals(testThemeColors.borderDefault, resolved.dividerColor)
    }

    @Test
    fun defaultsRemainStable() {
        assertEquals(48.dp, ElegantFloatingToolbarDefaults.Height)
        assertEquals(4.dp, ElegantFloatingToolbarDefaults.HorizontalPadding)
        assertEquals(4.dp, ElegantFloatingToolbarDefaults.ItemGap)
    }

    @Test
    fun colorsClassSupportsCopyOverrides() {
        val colors = ElegantFloatingToolbarColors(
            containerColor = Color(0xFF000001),
            contentColor = Color(0xFF000002),
            dividerColor = Color(0xFF000003),
        )

        val custom = colors.copy(contentColor = Color(0xFF000004))

        assertEquals(Color(0xFF000001), custom.containerColor)
        assertEquals(Color(0xFF000004), custom.contentColor)
        assertEquals(Color(0xFF000003), custom.dividerColor)
        assertEquals(
            colors,
            ElegantFloatingToolbarColors(
                containerColor = Color(0xFF000001),
                contentColor = Color(0xFF000002),
                dividerColor = Color(0xFF000003),
            ),
        )
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
        )
    }
}
