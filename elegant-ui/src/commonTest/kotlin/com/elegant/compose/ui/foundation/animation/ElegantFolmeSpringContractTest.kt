// Copyright 2026, elegant-ui contributors
// SPDX-License-Identifier: Apache-2.0

package com.elegant.compose.ui.foundation.animation

import kotlin.math.PI
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

internal class ElegantFolmeSpringContractTest {

    @Test
    fun stiffnessFollowsFolmeResponseFormula() {
        val spec = elegantFolmeSpring<Float>(dampingRatio = 1.0f, responseSeconds = 0.3f)

        assertEquals(1.0f, spec.dampingRatio)
        val expectedStiffness = ((2.0 * PI / 0.3) * (2.0 * PI / 0.3)).toFloat()
        assertEquals(expectedStiffness, spec.stiffness, absoluteTolerance = 0.001f)
    }

    @Test
    fun shorterResponseYieldsHigherStiffness() {
        val slow = elegantFolmeSpring<Float>(dampingRatio = 1.0f, responseSeconds = 0.6f)
        val fast = elegantFolmeSpring<Float>(dampingRatio = 1.0f, responseSeconds = 0.2f)

        assertTrue(fast.stiffness > slow.stiffness)
    }

    @Test
    fun dampingRatioPassesThroughUntouched() {
        val underdamped = elegantFolmeSpring<Float>(dampingRatio = 0.6f, responseSeconds = 0.25f)
        val overdamped = elegantFolmeSpring<Float>(dampingRatio = 1.2f, responseSeconds = 0.25f)

        assertEquals(0.6f, underdamped.dampingRatio)
        assertEquals(1.2f, overdamped.dampingRatio)
    }
}
