// Copyright 2026, elegant-ui contributors
// SPDX-License-Identifier: Apache-2.0
// Ported from compose-miuix-ui/miuix (Apache-2.0).

package com.elegant.compose.ui.nav.core

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class ElegantNavDisplayEffectsTest {

    private val effects = ElegantNavDisplayEffects()

    @Test
    fun clipCorners_onlyWhileLeadingTopTransitions() {
        assertTrue(effects.shouldClipCornersAt(-0.5f))
        assertFalse(effects.shouldClipCornersAt(0f))
        assertFalse(effects.shouldClipCornersAt(0.5f))
        assertFalse(effects.shouldClipCornersAt(-1f))
    }

    @Test
    fun clipCorners_disabledWhenSwitchOff() {
        val noClip = ElegantNavDisplayEffects(enableCornerClip = false)
        assertFalse(noClip.shouldClipCornersAt(-0.5f))
    }

    @Test
    fun blockInput_onlyOnNonTopMidTransition() {
        val blocking = ElegantNavDisplayEffects(blockInputDuringTransition = true)
        assertFalse(blocking.shouldBlockInputAt(0f))
        assertFalse(blocking.shouldBlockInputAt(1f))
        assertTrue(blocking.shouldBlockInputAt(0.5f))
        assertTrue(blocking.shouldBlockInputAt(1.5f))
    }

    @Test
    fun blockInput_disabledByDefault() {
        assertFalse(effects.shouldBlockInputAt(0.5f))
        assertFalse(effects.shouldBlockInputAt(1.5f))
    }

    @Test
    fun companionNone_disablesEverything() {
        assertEquals(0f, ElegantNavDisplayEffects.None.dimAmount)
        assertFalse(ElegantNavDisplayEffects.None.shouldClipCornersAt(-0.5f))
        assertFalse(ElegantNavDisplayEffects.None.shouldBlockInputAt(0.5f))
    }
}
