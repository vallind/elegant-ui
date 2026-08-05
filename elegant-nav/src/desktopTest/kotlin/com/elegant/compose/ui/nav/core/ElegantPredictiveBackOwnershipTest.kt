// Copyright 2026, elegant-ui contributors
// SPDX-License-Identifier: Apache-2.0
// Ported from compose-miuix-ui/miuix (Apache-2.0).

package com.elegant.compose.ui.nav.core

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class ElegantPredictiveBackOwnershipTest {
    @Test
    fun staleLeaseCannotReleaseNewerSession() {
        val ownership = ElegantPredictiveBackOwnership()
        val sessionA = ownership.acquire()
        val sessionB = ownership.acquire()

        assertEquals(3L, ownership.generation)
        assertFalse(ownership.release(sessionA))
        assertEquals(3L, ownership.generation)
        assertTrue(ownership.release(sessionB))
        assertEquals(4L, ownership.generation)
    }
}
