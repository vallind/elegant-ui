// Copyright 2026, elegant-ui contributors
// SPDX-License-Identifier: Apache-2.0

package com.elegant.compose.ui.foundation.indication

import androidx.compose.ui.graphics.Color
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

internal class ElegantIndicationContractTest {

    @Test
    fun restingStateAddsNoOverlay() {
        assertEquals(0.0f, indicationTargetAlpha(isPressed = false, isHovered = false, isFocused = false))
    }

    @Test
    fun pressAddsTheLargestDelta() {
        assertEquals(
            PressAlphaDelta,
            indicationTargetAlpha(isPressed = true, isHovered = false, isFocused = false),
        )
        assertEquals(HoverAlphaDelta, indicationTargetAlpha(isPressed = false, isHovered = true, isFocused = false))
        assertEquals(FocusAlphaDelta, indicationTargetAlpha(isPressed = false, isHovered = false, isFocused = true))
    }

    @Test
    fun statesSumWithoutCapping() {
        assertEquals(
            PressAlphaDelta + HoverAlphaDelta + FocusAlphaDelta,
            indicationTargetAlpha(isPressed = true, isHovered = true, isFocused = true),
            absoluteTolerance = 0.0001f,
        )
    }

    @Test
    fun indicationEqualityIsDrivenByOverlayColor() {
        assertEquals(ElegantIndication(Color.Black), ElegantIndication(Color.Black))
        assertEquals(ElegantIndication(Color.Black).hashCode(), ElegantIndication(Color.Black).hashCode())
        assertNotEquals(ElegantIndication(Color.Black), ElegantIndication(Color.White))
    }
}
