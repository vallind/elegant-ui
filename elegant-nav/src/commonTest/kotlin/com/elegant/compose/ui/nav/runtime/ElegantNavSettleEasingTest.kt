// Copyright 2026, elegant-ui contributors
// SPDX-License-Identifier: Apache-2.0
// Ported from compose-miuix-ui/miuix (Apache-2.0).

package com.elegant.compose.ui.nav.runtime

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

/**
 * Pins the programmatic settle curve to the established navigation easing
 * (`ElegantNavTransitionEasing(0.8f, 0.95f)` over 500ms in the nav3-based implementation): exact
 * endpoints, reference samples, and monotonicity (no bounce within the played window).
 */
class ElegantNavSettleEasingTest {
    @Test
    fun startsAtZero() {
        assertEquals(0f, ElegantNavProgrammaticEasing.transform(0f))
    }

    @Test
    fun matchesReferenceSamples() {
        // Closed-form samples of the reference curve (response = 0.8, damping = 0.95).
        assertEquals(0.4794f, ElegantNavProgrammaticEasing.transform(0.2f), absoluteTolerance = 1e-3f)
        assertEquals(0.9232f, ElegantNavProgrammaticEasing.transform(0.5f), absoluteTolerance = 1e-3f)
    }

    @Test
    fun endsJustBelowOne_tweenSnapsResidue() {
        // transform(1) ≈ 0.99933; the tween's final frame lands exactly on the target.
        assertEquals(0.99933f, ElegantNavProgrammaticEasing.transform(1f), absoluteTolerance = 1e-3f)
    }

    @Test
    fun monotone_neverBouncesWithinPlayedWindow() {
        var previous = ElegantNavProgrammaticEasing.transform(0f)
        for (i in 1..100) {
            val value = ElegantNavProgrammaticEasing.transform(i / 100f)
            assertTrue(value >= previous, "curve must be monotone, dipped at fraction ${i / 100f}")
            assertTrue(value <= 1f, "curve must not overshoot 1 within the played window")
            previous = value
        }
    }
}
