package com.elegant.compose.ui.inputgroup

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.unit.dp
import com.elegant.compose.ui.theme.ElegantDarkColors
import com.elegant.compose.ui.theme.ElegantLightColors
import com.elegant.compose.ui.theme.ElegantRadius
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

internal class ElegantInputGroupContractTest {

    @Test
    fun lightColorsMapToRaisedContainerDefaultBorderAndPrimaryContent() {
        val colors = resolveInputGroupColors(ElegantLightColors)

        assertEquals(ElegantLightColors.surfaceRaised, colors.containerColor)
        assertEquals(ElegantLightColors.borderDefault, colors.borderColor)
        assertEquals(ElegantLightColors.textPrimary, colors.contentColor)
    }

    @Test
    fun darkColorsMapToTheSameThemeRoles() {
        val colors = resolveInputGroupColors(ElegantDarkColors)

        assertEquals(ElegantDarkColors.surfaceRaised, colors.containerColor)
        assertEquals(ElegantDarkColors.borderDefault, colors.borderColor)
        assertEquals(ElegantDarkColors.textPrimary, colors.contentColor)
    }

    @Test
    fun colorsFollowTheActiveTheme() {
        assertNotEquals(
            resolveInputGroupColors(ElegantLightColors),
            resolveInputGroupColors(ElegantDarkColors),
        )
    }

    @Test
    fun groupShapeUsesTheStandardRadius() {
        assertEquals(RoundedCornerShape(ElegantRadius.md), InputGroupShape)
    }

    @Test
    fun borderWidthIsOneDp() {
        assertEquals(1.dp, InputGroupBorderWidth)
    }
}
