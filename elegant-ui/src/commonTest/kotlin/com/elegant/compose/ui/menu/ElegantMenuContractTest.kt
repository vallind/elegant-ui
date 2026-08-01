package com.elegant.compose.ui.menu

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.elegant.compose.ui.theme.ElegantLightColors
import com.elegant.compose.ui.theme.ElegantMotion
import kotlin.test.Test
import kotlin.test.assertEquals

internal class ElegantMenuContractTest {

    @Test
    fun anchorYAddsOffsetBelowAnchorHeight() {
        assertEquals(52, menuAnchorPosition(anchorHeight = 48, offsetPx = 4))
        assertEquals(4, menuAnchorPosition(anchorHeight = 0, offsetPx = 4))
        assertEquals(48, menuAnchorPosition(anchorHeight = 48, offsetPx = 0))
    }

    @Test
    fun positionsClampIntoWindowEdges() {
        val window = IntSize(width = 400, height = 300)
        val menuSize = IntSize(width = 100, height = 50)

        assertEquals(
            IntOffset(x = 0, y = 0),
            clampMenuPosition(
                position = IntOffset(x = -20, y = -10),
                menuSize = menuSize,
                windowSize = window,
            ),
        )
        assertEquals(
            IntOffset(x = 300, y = 250),
            clampMenuPosition(
                position = IntOffset(x = 380, y = 280),
                menuSize = menuSize,
                windowSize = window,
            ),
        )
    }

    @Test
    fun positionsClampWhenMenuExceedsWindow() {
        assertEquals(
            IntOffset(x = 0, y = 0),
            clampMenuPosition(
                position = IntOffset(x = 200, y = 200),
                menuSize = IntSize(width = 200, height = 150),
                windowSize = IntSize(width = 100, height = 100),
            ),
        )
    }

    @Test
    fun colorsResolveFromThemeRoles() {
        val colors = resolveMenuColors(ElegantLightColors)

        assertEquals(ElegantLightColors.surfaceRaised, colors.containerColor)
        assertEquals(ElegantLightColors.textPrimary, colors.contentColor)
        assertEquals(ElegantLightColors.textTertiary, colors.disabledContentColor)
        assertEquals(ElegantLightColors.borderDefault, colors.dividerColor)
        assertEquals(ElegantLightColors.surfaceHover, colors.selectedItemColor)
        assertEquals(ElegantLightColors.surfaceHover, colors.hoveredItemColor)
    }

    @Test
    fun hoveredItemColorDefaultsToContainerColor() {
        val colors = ElegantMenuColors(
            containerColor = Color.Blue,
            contentColor = Color.White,
            disabledContentColor = Color.Gray,
            dividerColor = Color.LightGray,
            selectedItemColor = Color.DarkGray,
        )

        assertEquals(colors.containerColor, colors.hoveredItemColor)
    }

    @Test
    fun defaultsMatchMenuContract() {
        assertEquals(160.dp, ElegantMenuDefaults.MinWidth)
        assertEquals(320.dp, ElegantMenuDefaults.MaxHeight)
        assertEquals(40.dp, ElegantMenuDefaults.ItemHeight)
        assertEquals(16.dp, ElegantMenuDefaults.HorizontalPadding)
        assertEquals(ElegantMotion.fastDurationMillis, ElegantMenuDefaults.AnimationDurationMillis)
    }
}
