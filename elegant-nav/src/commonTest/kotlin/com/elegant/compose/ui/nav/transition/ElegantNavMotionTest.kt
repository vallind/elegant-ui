// Copyright 2026, elegant-ui contributors
// SPDX-License-Identifier: Apache-2.0
// Ported from compose-miuix-ui/miuix (Apache-2.0).

package com.elegant.compose.ui.nav.transition

import com.elegant.compose.ui.nav.runtime.ElegantNavDriverSpec
import com.elegant.compose.ui.nav.runtime.ElegantNavProgrammaticEasing
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertIs
import kotlin.test.assertTrue

class ElegantNavMotionTest {

    @Test
    fun springDefaults_matchDriverConstants() {
        val spring = ElegantNavSettleSpec.Spring()
        assertEquals(ElegantNavDriverSpec.DAMPING_RATIO, spring.dampingRatio)
        assertEquals(ElegantNavDriverSpec.STIFFNESS, spring.stiffness)
        assertTrue(spring.clampOvershoot)
    }

    @Test
    fun underdampedSpring_requiresExplicitOvershootAcknowledgement() {
        assertFailsWith<IllegalArgumentException> { ElegantNavSettleSpec.Spring(dampingRatio = 0.8f) }
        // Explicit acknowledgement constructs fine.
        ElegantNavSettleSpec.Spring(dampingRatio = 0.8f, clampOvershoot = false)
    }

    @Test
    fun motionDefault_keepsEstablishedProgrammaticCurve() {
        val programmatic = assertIs<ElegantNavSettleSpec.Tween>(ElegantNavMotion.Default.programmatic)
        assertEquals(ElegantNavDriverSpec.PROGRAMMATIC_DURATION_MILLIS, programmatic.durationMillis)
        assertEquals(ElegantNavProgrammaticEasing, programmatic.easing)
    }
}
