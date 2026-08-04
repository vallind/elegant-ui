package com.elegant.compose.ui.listpopup

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntRect
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.elegant.compose.ui.theme.ElegantLightColors
import com.elegant.compose.ui.theme.ElegantMotion
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

internal class ElegantListPopupContractTest {

    @Test
    fun isSelectedMatchesOnlyWhenSelectionIsPresent() {
        assertFalse(isSelected(selectedValue = null, optionValue = "paris"))
        assertFalse(isSelected(selectedValue = "", optionValue = "paris"))
        assertFalse(isSelected(selectedValue = "paris", optionValue = "london"))
        assertTrue(isSelected(selectedValue = "paris", optionValue = "paris"))
    }

    @Test
    fun itemTextColorFollowsDisabledThenSelectedPrecedence() {
        val colors = resolveListPopupColors(ElegantLightColors)

        assertEquals(
            colors.disabledContentColor,
            listPopupItemTextColor(colors, enabled = false, selected = true),
        )
        assertEquals(
            colors.disabledContentColor,
            listPopupItemTextColor(colors, enabled = false, selected = false),
        )
        assertEquals(
            colors.selectedContentColor,
            listPopupItemTextColor(colors, enabled = true, selected = true),
        )
        assertEquals(
            colors.contentColor,
            listPopupItemTextColor(colors, enabled = true, selected = false),
        )
    }

    @Test
    fun itemBackgroundFollowsDisabledThenHoveredThenSelectedPrecedence() {
        val colors = resolveListPopupColors(ElegantLightColors)

        assertEquals(
            Color.Transparent,
            listPopupItemBackground(colors, enabled = false, hovered = true, selected = true),
        )
        assertEquals(
            colors.hoveredContainerColor,
            listPopupItemBackground(colors, enabled = true, hovered = true, selected = true),
        )
        assertEquals(
            colors.hoveredContainerColor,
            listPopupItemBackground(colors, enabled = true, hovered = true, selected = false),
        )
        assertEquals(
            colors.selectedContainerColor,
            listPopupItemBackground(colors, enabled = true, hovered = false, selected = true),
        )
        assertEquals(
            Color.Transparent,
            listPopupItemBackground(colors, enabled = true, hovered = false, selected = false),
        )
    }

    @Test
    fun positionsBelowAnchorPlusOffset() {
        assertEquals(
            IntOffset(x = 20, y = 82),
            listPopupPosition(
                anchorBounds = IntRect(20, 30, 180, 78),
                popupSize = IntSize(width = 200, height = 100),
                offsetPx = 4,
                windowSize = IntSize(width = 1000, height = 800),
            ),
        )
        assertEquals(
            IntOffset(x = 20, y = 34),
            listPopupPosition(
                anchorBounds = IntRect(20, 30, 180, 30),
                popupSize = IntSize(width = 200, height = 100),
                offsetPx = 4,
                windowSize = IntSize(width = 1000, height = 800),
            ),
        )
        assertEquals(
            IntOffset(x = 20, y = 30),
            listPopupPosition(
                anchorBounds = IntRect(20, 30, 180, 78),
                popupSize = IntSize(width = 200, height = 100),
                offsetPx = 0,
                windowSize = IntSize(width = 1000, height = 800),
            ),
        )
    }

    @Test
    fun positionsClampIntoWindowEdges() {
        val windowSize = IntSize(width = 400, height = 300)
        val popupSize = IntSize(width = 100, height = 50)

        assertEquals(
            IntOffset(x = 0, y = 0),
            listPopupPosition(
                anchorBounds = IntRect(-20, -10, -10, 0),
                popupSize = popupSize,
                offsetPx = 4,
                windowSize = windowSize,
            ),
        )
        assertEquals(
            IntOffset(x = 300, y = 250),
            listPopupPosition(
                anchorBounds = IntRect(380, 280, 390, 290),
                popupSize = popupSize,
                offsetPx = 4,
                windowSize = windowSize,
            ),
        )
    }

    @Test
    fun positionsClampWhenPopupExceedsWindow() {
        assertEquals(
            IntOffset(x = 0, y = 0),
            listPopupPosition(
                anchorBounds = IntRect(20, 30, 40, 40),
                popupSize = IntSize(width = 200, height = 150),
                offsetPx = 4,
                windowSize = IntSize(width = 100, height = 100),
            ),
        )
    }

    @Test
    fun colorsResolveFromThemeRoles() {
        val colors = resolveListPopupColors(ElegantLightColors)

        assertEquals(ElegantLightColors.surfaceRaised, colors.containerColor)
        assertEquals(ElegantLightColors.textPrimary, colors.contentColor)
        assertEquals(ElegantLightColors.textTertiary, colors.disabledContentColor)
        assertEquals(ElegantLightColors.interactivePrimary, colors.selectedContentColor)
        assertEquals(ElegantLightColors.backgroundSubtle, colors.selectedContainerColor)
        assertEquals(ElegantLightColors.surfaceHover, colors.hoveredContainerColor)
        assertEquals(ElegantLightColors.borderDefault, colors.borderColor)
    }

    @Test
    fun defaultsMatchListPopupContract() {
        assertEquals(160.dp, ElegantListPopupDefaults.MinWidth)
        assertEquals(320.dp, ElegantListPopupDefaults.MaxHeight)
        assertEquals(40.dp, ElegantListPopupDefaults.ItemHeight)
        assertEquals(16.dp, ElegantListPopupDefaults.HorizontalPadding)
        assertEquals(ElegantMotion.fastDurationMillis, ElegantListPopupDefaults.AnimationDurationMillis)
    }
}
