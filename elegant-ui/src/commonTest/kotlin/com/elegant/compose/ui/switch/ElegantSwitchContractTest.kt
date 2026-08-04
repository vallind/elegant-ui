package com.elegant.compose.ui.switch

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.elegant.compose.ui.theme.ElegantDarkColors
import com.elegant.compose.ui.theme.ElegantLightColors
import com.elegant.compose.ui.theme.ElegantMotion
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue

internal class ElegantSwitchContractTest {

    @Test
    fun colorsResolveThemeAwareDefaults() {
        val light = resolveSwitchColors(ElegantLightColors)
        val dark = resolveSwitchColors(ElegantDarkColors)

        assertEquals(ElegantLightColors.interactivePrimary, light.trackCheckedColor)
        assertEquals(ElegantLightColors.borderStrong, light.trackUncheckedColor)
        assertEquals(ElegantLightColors.textInverse, light.thumbCheckedColor)
        assertEquals(ElegantLightColors.surfaceRaised, light.thumbUncheckedColor)
        assertEquals(ElegantLightColors.focusRing, light.focusedTrackColor)
        assertNotEquals(light, dark, "switch colors must follow the active theme")
    }

    @Test
    fun hoveredAndPressedTracksStayDistinctFromResting() {
        val light = resolveSwitchColors(ElegantLightColors)

        assertEquals(ElegantLightColors.interactivePrimaryHover, light.hoveredTrackCheckedColor)
        assertEquals(ElegantLightColors.interactivePrimaryPressed, light.pressedTrackCheckedColor)
        assertEquals(
            ElegantLightColors.interactivePrimary.copy(alpha = 0.55f),
            light.hoveredTrackUncheckedColor,
        )
        assertEquals(ElegantLightColors.borderStrong, light.pressedTrackUncheckedColor)
        assertNotEquals(light.trackCheckedColor, light.hoveredTrackCheckedColor)
        assertNotEquals(light.trackUncheckedColor, light.hoveredTrackUncheckedColor)
    }

    @Test
    fun disabledColorsFadeTrackAndThumb() {
        val light = resolveSwitchColors(ElegantLightColors)

        assertEquals(
            ElegantLightColors.interactivePrimary.copy(alpha = 0.35f),
            light.disabledTrackCheckedColor,
        )
        assertEquals(ElegantLightColors.borderDefault, light.disabledTrackUncheckedColor)
        assertEquals(
            ElegantLightColors.textInverse.copy(alpha = 0.8f),
            light.disabledThumbCheckedColor,
        )
        assertEquals(ElegantLightColors.textTertiary, light.disabledThumbUncheckedColor)
    }

    @Test
    fun stateColorsDefaultToTheirBaseVisuals() {
        val trackChecked = Color(0xFFFF0000)
        val trackUnchecked = Color(0xFF00FF00)
        val thumbChecked = Color(0xFF0000FF)
        val thumbUnchecked = Color(0xFFFFFF00)
        val colors = ElegantSwitchColors(
            trackCheckedColor = trackChecked,
            trackUncheckedColor = trackUnchecked,
            thumbCheckedColor = thumbChecked,
            thumbUncheckedColor = thumbUnchecked,
        )

        assertEquals(trackChecked, colors.hoveredTrackCheckedColor)
        assertEquals(trackUnchecked, colors.hoveredTrackUncheckedColor)
        assertEquals(trackChecked, colors.pressedTrackCheckedColor)
        assertEquals(trackUnchecked, colors.pressedTrackUncheckedColor)
        assertEquals(trackChecked, colors.disabledTrackCheckedColor)
        assertEquals(trackUnchecked, colors.disabledTrackUncheckedColor)
        assertEquals(thumbChecked, colors.hoveredThumbCheckedColor)
        assertEquals(thumbUnchecked, colors.hoveredThumbUncheckedColor)
        assertEquals(thumbChecked, colors.disabledThumbCheckedColor)
        assertEquals(thumbUnchecked, colors.disabledThumbUncheckedColor)
        assertEquals(trackUnchecked, colors.focusedTrackColor)
    }

    @Test
    fun visualPrecedenceFollowsDisabledPressedHoveredResting() {
        val colors = resolveSwitchColors(ElegantLightColors)

        fun visuals(
            enabled: Boolean = true,
            checked: Boolean = false,
            pressed: Boolean = false,
            hovered: Boolean = false,
        ) = resolveSwitchVisuals(
            colors = colors,
            enabled = enabled,
            checked = checked,
            pressed = pressed,
            hovered = hovered,
        )

        assertEquals(colors.disabledTrackUncheckedColor, visuals(enabled = false).track)
        assertEquals(
            colors.disabledTrackCheckedColor,
            visuals(enabled = false, pressed = true, hovered = true, checked = true).track,
        )
        assertEquals(
            colors.disabledThumbCheckedColor,
            visuals(enabled = false, checked = true).thumb,
        )
        assertEquals(
            colors.pressedTrackUncheckedColor,
            visuals(pressed = true, hovered = true).track,
        )
        assertEquals(colors.hoveredTrackUncheckedColor, visuals(hovered = true).track)
        assertEquals(colors.trackUncheckedColor, visuals().track)
    }

    @Test
    fun checkedIsSemanticAndCombinesWithInteractionStates() {
        val colors = resolveSwitchColors(ElegantLightColors)

        assertEquals(
            colors.pressedTrackCheckedColor,
            resolveSwitchVisuals(
                colors = colors,
                enabled = true,
                checked = true,
                pressed = true,
                hovered = true,
            ).track,
        )
        assertEquals(
            colors.hoveredTrackCheckedColor,
            resolveSwitchVisuals(
                colors = colors,
                enabled = true,
                checked = true,
                pressed = false,
                hovered = true,
            ).track,
        )
        assertEquals(
            colors.trackCheckedColor,
            resolveSwitchVisuals(
                colors = colors,
                enabled = true,
                checked = true,
                pressed = false,
                hovered = false,
            ).track,
        )
    }

    @Test
    fun thumbFollowsCheckedAndDisabledStates() {
        val colors = resolveSwitchColors(ElegantLightColors)

        assertEquals(
            colors.thumbCheckedColor,
            resolveSwitchVisuals(
                colors = colors,
                enabled = true,
                checked = true,
                pressed = false,
                hovered = false,
            ).thumb,
        )
        assertEquals(
            colors.hoveredThumbUncheckedColor,
            resolveSwitchVisuals(
                colors = colors,
                enabled = true,
                checked = false,
                pressed = false,
                hovered = true,
            ).thumb,
        )
        assertEquals(
            colors.disabledThumbUncheckedColor,
            resolveSwitchVisuals(
                colors = colors,
                enabled = false,
                checked = false,
                pressed = false,
                hovered = true,
            ).thumb,
        )
    }

    @Test
    fun thumbOffsetIsZeroWhenUnchecked() {
        assertEquals(
            0f,
            switchThumbOffsetPx(
                checked = false,
                trackWidthPx = 44f,
                thumbSizePx = 16f,
                paddingPx = 4f,
            ),
        )
    }

    @Test
    fun thumbOffsetTravelsAcrossTheTrackWhenChecked() {
        val trackWidth = 44f
        val thumbSize = 16f
        val padding = 4f

        val offset = switchThumbOffsetPx(
            checked = true,
            trackWidthPx = trackWidth,
            thumbSizePx = thumbSize,
            paddingPx = padding,
        )

        assertEquals(trackWidth - thumbSize - 2 * padding, offset, absoluteTolerance = 0.001f)
        assertTrue(offset >= 0f, "thumb offset must never be negative")
        assertTrue(
            offset + thumbSize + 2 * padding <= trackWidth,
            "thumb must stay inside the track",
        )
    }

    @Test
    fun thumbOffsetScalesWithCustomGeometry() {
        val offset = switchThumbOffsetPx(
            checked = true,
            trackWidthPx = 80f,
            thumbSizePx = 24f,
            paddingPx = 6f,
        )

        assertEquals(80f - 24f - 2 * 6f, offset, absoluteTolerance = 0.001f)
    }

    @Test
    fun defaultsMeetAccessibilityAndRhythmContracts() {
        assertEquals(44.dp, ElegantSwitchDefaults.TrackWidth)
        assertEquals(24.dp, ElegantSwitchDefaults.TrackHeight)
        assertEquals(16.dp, ElegantSwitchDefaults.ThumbSize)
        assertTrue(ElegantSwitchDefaults.MinimumTouchHeight >= 48.dp)
        assertEquals(ElegantMotion.standardDurationMillis, ElegantSwitchDefaults.AnimationDurationMillis)
    }
    @Test
    fun dragTargetCrossesHalfTravel() {
        assertEquals(true, switchDragTarget(checked = false, dragOffsetPx = 51f, maxOffsetPx = 100f))
        assertEquals(false, switchDragTarget(checked = true, dragOffsetPx = -51f, maxOffsetPx = 100f))
    }

    @Test
    fun dragTargetStaysInSpringBackZone() {
        assertEquals(null, switchDragTarget(checked = false, dragOffsetPx = 49f, maxOffsetPx = 100f))
        assertEquals(null, switchDragTarget(checked = true, dragOffsetPx = -49f, maxOffsetPx = 100f))
        assertEquals(null, switchDragTarget(checked = true, dragOffsetPx = 0f, maxOffsetPx = 100f))
}
}
