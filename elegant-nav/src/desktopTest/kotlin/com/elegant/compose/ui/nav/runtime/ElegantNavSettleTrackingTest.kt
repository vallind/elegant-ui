// Copyright 2026, elegant-ui contributors
// SPDX-License-Identifier: Apache-2.0
// Ported from compose-miuix-ui/miuix (Apache-2.0).

package com.elegant.compose.ui.nav.runtime

import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import com.elegant.compose.ui.nav.transition.ElegantNavSettle
import com.elegant.compose.ui.nav.transition.ElegantNavSettlePhase
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertSame
import kotlin.test.assertTrue

private class TestSink : ElegantNavSettleSink {
    override var settle: ElegantNavSettle? = null
    override var pendingSettleVelocity: Float = 0f
}

class ElegantNavSettleTrackingTest {

    @Test
    fun trackSettle_publishesDuringBody_clearsAfter() = runBlocking {
        val sink = TestSink()
        sink.trackSettle(ElegantNavSettlePhase.Programmatic, releaseVelocity = 0f) { _ ->
            val s = sink.settle
            assertEquals(ElegantNavSettlePhase.Programmatic, s?.phase)
            assertEquals(0f, s?.releaseVelocity)
        }
        assertNull(sink.settle)
    }

    @Test
    fun trackSettle_onFrameStampsElapsed_monotonic() = runBlocking {
        val sink = TestSink()
        sink.trackSettle(ElegantNavSettlePhase.Commit, releaseVelocity = 2.5f) { onFrame ->
            assertEquals(2.5f, sink.settle?.releaseVelocity)
            onFrame()
            val first = sink.settle!!.elapsedMillis
            assertTrue(first >= 0f)
            delay(20)
            onFrame()
            assertTrue(sink.settle!!.elapsedMillis >= first)
        }
    }

    @Test
    fun trackSettle_identityGuard_doesNotClearNewerSettle() = runBlocking {
        val sink = TestSink()
        var newer: ElegantNavSettle? = null
        sink.trackSettle(ElegantNavSettlePhase.Cancel, releaseVelocity = 0f) { _ ->
            // Simulate an interrupting settle replacing the context before our finally runs.
            newer = ElegantNavSettleState(ElegantNavSettlePhase.Programmatic, 0f)
            sink.settle = newer
        }
        assertSame(newer, sink.settle, "finally must not clear a newer settle's context")
    }
}
