package com.elegant.compose.ui.alert

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.unit.dp
import com.elegant.compose.ui.foundation.theme.ElegantDarkColors
import com.elegant.compose.ui.foundation.theme.ElegantLightColors
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue

internal class ElegantAlertContractTest {
    @Test
    fun publicEnumRemainsStable() {
        assertEquals(
            listOf("Neutral", "Positive", "Warning", "Critical"),
            ElegantAlertStyle.entries.map(ElegantAlertStyle::name),
        )
    }

    @Test
    fun defaultMetricsRemainStable() {
        assertEquals(
            AlertMetrics(
                horizontalPadding = 16.dp,
                verticalPadding = 12.dp,
                borderWidth = 1.dp,
                iconSize = 20.dp,
                iconGap = 12.dp,
                actionGap = 16.dp,
            ),
            DefaultAlertMetrics,
        )
    }

    @Test
    fun descriptionResolvesBlankToNull() {
        assertNull(resolveDescription(null))
        assertNull(resolveDescription(""))
        assertNull(resolveDescription("   "))
        assertEquals("Saved", resolveDescription("Saved"))
    }

    @Test
    fun neutralStyleResolvesQuietThemeRoles() {
        val neutral = resolveAlertColors(ElegantAlertStyle.Neutral, ElegantLightColors)

        assertEquals(ElegantLightColors.backgroundSubtle, neutral.containerColor)
        assertEquals(ElegantLightColors.textPrimary, neutral.contentColor)
        assertEquals(ElegantLightColors.textSecondary, neutral.supportingColor)
        assertEquals(ElegantLightColors.borderDefault, neutral.borderColor)
        assertEquals(ElegantLightColors.textSecondary, neutral.iconColor)
    }

    @Test
    fun semanticStylesResolveDistinctThemeRoles() {
        val positive = resolveAlertColors(ElegantAlertStyle.Positive, ElegantLightColors)
        val warning = resolveAlertColors(ElegantAlertStyle.Warning, ElegantLightColors)
        val critical = resolveAlertColors(ElegantAlertStyle.Critical, ElegantLightColors)

        assertEquals(ElegantLightColors.statusPositive.copy(alpha = 0.10f), positive.containerColor)
        assertEquals(ElegantLightColors.statusPositive.copy(alpha = 0.30f), positive.borderColor)
        assertEquals(ElegantLightColors.statusPositive, positive.iconColor)
        assertEquals(ElegantLightColors.statusWarning.copy(alpha = 0.10f), warning.containerColor)
        assertEquals(ElegantLightColors.statusWarning.copy(alpha = 0.30f), warning.borderColor)
        assertEquals(ElegantLightColors.statusWarning, warning.iconColor)
        assertEquals(ElegantLightColors.statusCritical.copy(alpha = 0.10f), critical.containerColor)
        assertEquals(ElegantLightColors.statusCritical.copy(alpha = 0.30f), critical.borderColor)
        assertEquals(ElegantLightColors.statusCritical, critical.iconColor)

        assertNotEquals(positive.containerColor, warning.containerColor)
        assertNotEquals(warning.containerColor, critical.containerColor)
        assertNotEquals(positive.borderColor, warning.borderColor)
        assertNotEquals(positive.iconColor, warning.iconColor)
        assertNotEquals(warning.iconColor, critical.iconColor)
    }

    @Test
    fun statusColorsAdaptBetweenLightAndDarkThemes() {
        for (style in ElegantAlertStyle.entries) {
            assertNotEquals(
                resolveAlertColors(style, ElegantLightColors).containerColor,
                resolveAlertColors(style, ElegantDarkColors).containerColor,
            )
            assertNotEquals(
                resolveAlertColors(style, ElegantLightColors).iconColor,
                resolveAlertColors(style, ElegantDarkColors).iconColor,
            )
        }
    }

    @Test
    fun everyStyleMeetsReadableTextContrast() {
        for (themeColors in listOf(ElegantLightColors, ElegantDarkColors)) {
            for (style in ElegantAlertStyle.entries) {
                val colors = resolveAlertColors(style, themeColors)
                val background = composite(colors.containerColor, themeColors.backgroundCanvas)
                assertTrue(
                    actual = contrastRatio(
                        foreground = colors.contentColor,
                        background = background,
                    ) >= MinimumTextContrast,
                    message = "$style title does not meet text contrast in $themeColors",
                )
                assertTrue(
                    actual = contrastRatio(
                        foreground = colors.supportingColor,
                        background = background,
                    ) >= MinimumSupportingTextContrast,
                    message = "$style description does not meet supporting text contrast in $themeColors",
                )
                assertTrue(
                    actual = contrastRatio(
                        foreground = colors.iconColor,
                        background = background,
                    ) >= MinimumIconContrast,
                    message = "$style icon does not meet graphic contrast in $themeColors",
                )
            }
        }
    }
}

private const val MinimumTextContrast: Float = 4.5f

private const val MinimumSupportingTextContrast: Float = 3.5f

private const val MinimumIconContrast: Float = 3.0f

private fun composite(
    foreground: Color,
    background: Color,
): Color = Color(
    red = foreground.red * foreground.alpha + background.red * (1f - foreground.alpha),
    green = foreground.green * foreground.alpha + background.green * (1f - foreground.alpha),
    blue = foreground.blue * foreground.alpha + background.blue * (1f - foreground.alpha),
    alpha = 1f,
)

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
