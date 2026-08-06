// Copyright 2026, elegant-ui contributors
// SPDX-License-Identifier: Apache-2.0

package com.elegant.compose.ui.foundation.indication

import androidx.compose.animation.core.SpringSpec
import androidx.compose.animation.core.spring
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

internal class ElegantPressFeedbackContractTest {

    @Test
    fun publicFeedbackTypesRemainStable() {
        assertEquals(
            listOf("None", "Sink", "Tilt"),
            ElegantPressFeedbackType.entries.map(ElegantPressFeedbackType::name),
        )
    }

    @Test
    fun sinkDefaultsSinkToNinetyFourPercentWithSoftSpring() {
        val sink = ElegantSinkFeedback()

        assertEquals(0.94f, sink.sinkAmount)
        val expected = spring<Float>(dampingRatio = 0.8f, stiffness = 600f)
        assertEquals(expected.dampingRatio, (sink.animationSpec as SpringSpec).dampingRatio)
        assertEquals(expected.stiffness, (sink.animationSpec as SpringSpec).stiffness)
    }

    @Test
    fun tiltDefaultsToEightDegreesWithSnappySpring() {
        val tilt = ElegantTiltFeedback()

        assertEquals(8f, tilt.tiltAmount)
        val expected = spring<Float>(dampingRatio = 0.6f, stiffness = 400f)
        assertEquals(expected.dampingRatio, (tilt.animationSpec as SpringSpec).dampingRatio)
        assertEquals(expected.stiffness, (tilt.animationSpec as SpringSpec).stiffness)
    }

    @Test
    fun resolutionMapsNoneToNullAndFactoriesToFeedback() {
        assertNull(resolvePressFeedback(ElegantPressFeedbackType.None))
        assertEquals(
            ElegantSinkFeedback(),
            resolvePressFeedback(ElegantPressFeedbackType.Sink),
        )
        assertEquals(
            ElegantTiltFeedback(),
            resolvePressFeedback(ElegantPressFeedbackType.Tilt),
        )
    }
}
