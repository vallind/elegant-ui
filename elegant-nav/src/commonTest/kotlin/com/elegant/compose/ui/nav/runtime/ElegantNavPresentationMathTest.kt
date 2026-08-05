// Copyright 2026, elegant-ui contributors
// SPDX-License-Identifier: Apache-2.0
// Ported from compose-miuix-ui/miuix (Apache-2.0).

package com.elegant.compose.ui.nav.runtime

import com.elegant.compose.ui.nav.transition.ElegantNavRole
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class ElegantNavPresentationMathTest {
    // --- relativeDepth ---

    @Test
    fun relativeDepth_isAnimatedTopMinusIndex() {
        assertEquals(0f, relativeDepth(2f, 2))
        assertEquals(1f, relativeDepth(2f, 1))
        assertEquals(2f, relativeDepth(2f, 0))
        assertEquals(-1f, relativeDepth(2f, 3))
    }

    @Test
    fun relativeDepth_fractionalDuringAnimation() {
        assertEquals(0.4f, relativeDepth(2.4f, 2), 1e-6f)
        assertEquals(-0.6f, relativeDepth(2.4f, 3), 1e-6f)
    }

    // --- roleFor ---

    @Test
    fun roleFor_atTop_isTop() {
        assertEquals(ElegantNavRole.Top, roleFor(0f, isRemoving = false))
        // Tiny jitter around zero still resolves to Top.
        assertEquals(ElegantNavRole.Top, roleFor(0.0001f, isRemoving = false))
        assertEquals(ElegantNavRole.Top, roleFor(-0.0001f, isRemoving = false))
    }

    @Test
    fun roleFor_coveredFirstLayer() {
        assertEquals(ElegantNavRole.Covered, roleFor(0.5f, isRemoving = false))
        assertEquals(ElegantNavRole.Covered, roleFor(1f, isRemoving = false))
    }

    @Test
    fun roleFor_deeperThanOne_isCovered() {
        assertEquals(ElegantNavRole.Covered, roleFor(2f, isRemoving = false))
    }

    @Test
    fun roleFor_aboveTop_incomingWhenNotRemoving() {
        assertEquals(ElegantNavRole.Incoming, roleFor(-0.5f, isRemoving = false))
    }

    @Test
    fun roleFor_aboveTop_outgoingWhenRemoving() {
        assertEquals(ElegantNavRole.Outgoing, roleFor(-0.5f, isRemoving = true))
    }

    // --- isVisibleAt ---

    @Test
    fun isVisible_topAndCoveredWithinOpaqueDepth() {
        assertTrue(isVisibleAt(0f, opaqueDepth = 1f))
        assertTrue(isVisibleAt(1f, opaqueDepth = 1f))
        assertTrue(isVisibleAt(-0.5f, opaqueDepth = 1f))
    }

    @Test
    fun isVisible_fullyLeftAtMinusOne_isCulled() {
        assertFalse(isVisibleAt(-1f, opaqueDepth = 1f))
        assertFalse(isVisibleAt(-1.5f, opaqueDepth = 1f))
    }

    @Test
    fun isVisible_beyondOpaqueDepth_isCulled() {
        assertFalse(isVisibleAt(1.5f, opaqueDepth = 1f))
    }

    @Test
    fun isVisible_modalKeepsLowerLayerWithLargerOpaqueDepth() {
        // Modal presets use a larger opaqueDepth so the covered layer is not culled.
        assertTrue(isVisibleAt(1.5f, opaqueDepth = 2f))
        assertTrue(isVisibleAt(2f, opaqueDepth = 2f))
        assertFalse(isVisibleAt(2.5f, opaqueDepth = 2f))
    }
}
