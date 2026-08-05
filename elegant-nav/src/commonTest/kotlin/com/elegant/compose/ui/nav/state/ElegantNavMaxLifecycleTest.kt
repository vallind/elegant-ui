// Copyright 2026, elegant-ui contributors
// SPDX-License-Identifier: Apache-2.0
// Ported from compose-miuix-ui/miuix (Apache-2.0).

package com.elegant.compose.ui.nav.state

import androidx.lifecycle.Lifecycle
import com.elegant.compose.ui.nav.core.ElegantNavPresentationState
import com.elegant.compose.ui.nav.transition.ElegantNavRole
import kotlin.test.Test
import kotlin.test.assertEquals

class ElegantNavMaxLifecycleTest {
    // elegantNavMaxLifecycleFor reads only presentation.isRemoving; role is irrelevant to the mapping,
    // so a fixed Top role is used and only isRemoving is varied per case.
    private fun presentation(isRemoving: Boolean): ElegantNavPresentationState = ElegantNavPresentationState(role = ElegantNavRole.Top, isRemoving = isRemoving)

    @Test
    fun topSteadyStateIsResumed() {
        assertEquals(Lifecycle.State.RESUMED, elegantNavMaxLifecycleFor(presentation(isRemoving = false), d = 0f, gestureActive = false))
    }

    @Test
    fun topSettlingFromIncomingIsResumed() {
        // d in (-0.5, 0.5) and not removing -> still treated as top.
        assertEquals(Lifecycle.State.RESUMED, elegantNavMaxLifecycleFor(presentation(isRemoving = false), d = -0.2f, gestureActive = false))
        assertEquals(Lifecycle.State.RESUMED, elegantNavMaxLifecycleFor(presentation(isRemoving = false), d = 0.4f, gestureActive = false))
    }

    @Test
    fun coveredLayerIsStarted() {
        assertEquals(Lifecycle.State.STARTED, elegantNavMaxLifecycleFor(presentation(isRemoving = false), d = 1f, gestureActive = false))
        assertEquals(Lifecycle.State.STARTED, elegantNavMaxLifecycleFor(presentation(isRemoving = false), d = 0.5f, gestureActive = false))
    }

    @Test
    fun incomingAboveTopIsStarted() {
        // Entering from the leading edge (-1 < d < -0.5), not removing.
        assertEquals(Lifecycle.State.STARTED, elegantNavMaxLifecycleFor(presentation(isRemoving = false), d = -0.8f, gestureActive = false))
    }

    @Test
    fun removingNearTopIsCreated() {
        // Being popped: still near the top window but marked removing -> CREATED.
        assertEquals(Lifecycle.State.CREATED, elegantNavMaxLifecycleFor(presentation(isRemoving = true), d = 0f, gestureActive = false))
        assertEquals(Lifecycle.State.CREATED, elegantNavMaxLifecycleFor(presentation(isRemoving = true), d = -0.4f, gestureActive = false))
    }

    @Test
    fun fullyExitedIsDestroyed() {
        assertEquals(Lifecycle.State.DESTROYED, elegantNavMaxLifecycleFor(presentation(isRemoving = true), d = -1f, gestureActive = false))
        assertEquals(Lifecycle.State.DESTROYED, elegantNavMaxLifecycleFor(presentation(isRemoving = false), d = -1.5f, gestureActive = false))
    }

    @Test
    fun gestureCapsResumedWindowAtStarted() {
        // While a gesture owns the float the RESUMED window is capped at STARTED: a hovering
        // finger may oscillate around the +-0.5 thresholds without bound, and capping for the
        // whole gesture removes the RESUMED<->STARTED flap without hysteresis memory.
        assertEquals(Lifecycle.State.STARTED, elegantNavMaxLifecycleFor(presentation(isRemoving = false), d = 0f, gestureActive = true))
        assertEquals(Lifecycle.State.STARTED, elegantNavMaxLifecycleFor(presentation(isRemoving = false), d = -0.2f, gestureActive = true))
        assertEquals(Lifecycle.State.STARTED, elegantNavMaxLifecycleFor(presentation(isRemoving = false), d = 0.4f, gestureActive = true))
    }

    @Test
    fun gestureLeavesOtherBandsUntouched() {
        assertEquals(Lifecycle.State.STARTED, elegantNavMaxLifecycleFor(presentation(isRemoving = false), d = 1f, gestureActive = true))
        assertEquals(Lifecycle.State.STARTED, elegantNavMaxLifecycleFor(presentation(isRemoving = false), d = -0.8f, gestureActive = true))
        assertEquals(Lifecycle.State.CREATED, elegantNavMaxLifecycleFor(presentation(isRemoving = true), d = 0f, gestureActive = true))
        assertEquals(Lifecycle.State.DESTROYED, elegantNavMaxLifecycleFor(presentation(isRemoving = false), d = -1.5f, gestureActive = true))
    }
}
