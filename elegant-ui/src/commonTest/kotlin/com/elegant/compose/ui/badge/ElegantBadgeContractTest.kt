package com.elegant.compose.ui.badge

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.elegant.compose.ui.foundation.theme.ElegantDarkColors
import com.elegant.compose.ui.foundation.theme.ElegantLightColors
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue

internal class ElegantBadgeContractTest {
    @Test
    fun publicEnumsRemainStable() {
        assertEquals(
            listOf("Neutral", "Accent", "Positive", "Warning", "Critical"),
            ElegantBadgeStyle.entries.map(ElegantBadgeStyle::name),
        )
        assertEquals(
            listOf("Small", "Medium", "Large"),
            ElegantBadgeSize.entries.map(ElegantBadgeSize::name),
        )
        assertEquals(
            listOf("TopStart", "TopEnd", "BottomStart", "BottomEnd"),
            ElegantBadgePlacement.entries.map(ElegantBadgePlacement::name),
        )
    }

    @Test
    fun countLabelsHandleBoundariesAndInvalidInput() {
        assertEquals("0", ElegantBadgeDefaults.countLabel(-4))
        assertEquals("0", ElegantBadgeDefaults.countLabel(0))
        assertEquals("99", ElegantBadgeDefaults.countLabel(99))
        assertEquals("99+", ElegantBadgeDefaults.countLabel(100))
        assertEquals("1+", ElegantBadgeDefaults.countLabel(2, maxCount = 0))
    }

    @Test
    fun opticalMetricsRemainStable() {
        assertEquals(
            BadgeMetrics(dotSize = 6.dp, minContainerSize = 18.dp, horizontalPadding = 5.dp),
            badgeMetricsFor(ElegantBadgeSize.Small),
        )
        assertEquals(
            BadgeMetrics(dotSize = 8.dp, minContainerSize = 22.dp, horizontalPadding = 7.dp),
            badgeMetricsFor(ElegantBadgeSize.Medium),
        )
        assertEquals(
            BadgeMetrics(dotSize = 10.dp, minContainerSize = 26.dp, horizontalPadding = 9.dp),
            badgeMetricsFor(ElegantBadgeSize.Large),
        )
    }

    @Test
    fun logicalPlacementsCenterBadgeOnEveryCorner() {
        assertEquals(
            IntOffset(x = -4, y = -3),
            badgePositionFor(
                placement = ElegantBadgePlacement.TopStart,
                contentWidth = 40,
                contentHeight = 32,
                badgeWidth = 8,
                badgeHeight = 6,
            ),
        )
        assertEquals(
            IntOffset(x = 36, y = -3),
            badgePositionFor(
                placement = ElegantBadgePlacement.TopEnd,
                contentWidth = 40,
                contentHeight = 32,
                badgeWidth = 8,
                badgeHeight = 6,
            ),
        )
        assertEquals(
            IntOffset(x = -4, y = 29),
            badgePositionFor(
                placement = ElegantBadgePlacement.BottomStart,
                contentWidth = 40,
                contentHeight = 32,
                badgeWidth = 8,
                badgeHeight = 6,
            ),
        )
        assertEquals(
            IntOffset(x = 36, y = 29),
            badgePositionFor(
                placement = ElegantBadgePlacement.BottomEnd,
                contentWidth = 40,
                contentHeight = 32,
                badgeWidth = 8,
                badgeHeight = 6,
            ),
        )
    }

    @Test
    fun semanticStylesResolveDistinctThemeRoles() {
        val positive = resolveBadgeColors(ElegantBadgeStyle.Positive, ElegantLightColors)
        val warning = resolveBadgeColors(ElegantBadgeStyle.Warning, ElegantLightColors)
        val critical = resolveBadgeColors(ElegantBadgeStyle.Critical, ElegantLightColors)

        assertEquals(ElegantLightColors.statusPositive, positive.containerColor)
        assertEquals(ElegantLightColors.onStatusPositive, positive.contentColor)
        assertEquals(ElegantLightColors.statusWarning, warning.containerColor)
        assertEquals(ElegantLightColors.onStatusWarning, warning.contentColor)
        assertEquals(ElegantLightColors.statusCritical, critical.containerColor)
        assertEquals(ElegantLightColors.onStatusCritical, critical.contentColor)
        assertNotEquals(positive.containerColor, warning.containerColor)
        assertNotEquals(warning.containerColor, critical.containerColor)
    }

    @Test
    fun statusColorsAdaptBetweenLightAndDarkThemes() {
        for (style in listOf(
            ElegantBadgeStyle.Positive,
            ElegantBadgeStyle.Warning,
            ElegantBadgeStyle.Critical,
        )) {
            assertNotEquals(
                resolveBadgeColors(style, ElegantLightColors).containerColor,
                resolveBadgeColors(style, ElegantDarkColors).containerColor,
            )
            assertNotEquals(
                resolveBadgeColors(style, ElegantLightColors).contentColor,
                resolveBadgeColors(style, ElegantDarkColors).contentColor,
            )
        }
    }

    @Test
    fun everyBuiltInStylePreservesCompactTextContrast() {
        for (themeColors in listOf(ElegantLightColors, ElegantDarkColors)) {
            for (style in ElegantBadgeStyle.entries) {
                val colors = resolveBadgeColors(style, themeColors)
                assertTrue(
                    actual = contrastRatio(
                        foreground = colors.contentColor,
                        background = colors.containerColor,
                    ) >= MinimumTextContrast,
                    message = "$style does not meet compact text contrast in $themeColors",
                )
            }
        }
    }
}

private const val MinimumTextContrast: Float = 4.5f

private fun contrastRatio(
    foreground: Color,
    background: Color,
): Float {
    val foregroundLuminance = foreground.luminance()
    val backgroundLuminance = background.luminance()
    val lighter = maxOf(foregroundLuminance, backgroundLuminance)
    val darker = minOf(foregroundLuminance, backgroundLuminance)
    return (lighter + 0.05f) / (darker + 0.05f)
}
