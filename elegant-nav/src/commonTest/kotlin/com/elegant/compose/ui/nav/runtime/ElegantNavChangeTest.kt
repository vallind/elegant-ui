// Copyright 2026, elegant-ui contributors
// SPDX-License-Identifier: Apache-2.0
// Ported from compose-miuix-ui/miuix (Apache-2.0).

package com.elegant.compose.ui.nav.runtime

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class ElegantNavChangeTest {
    @Test
    fun dataObjects_areSingletons() {
        assertEquals(ElegantNavChange.None, ElegantNavChange.None)
        assertEquals(ElegantNavChange.Push, ElegantNavChange.Push)
        assertEquals(ElegantNavChange.Pop, ElegantNavChange.Pop)
        assertEquals(ElegantNavChange.Replace, ElegantNavChange.Replace)
        assertEquals(ElegantNavChange.ReplaceAll, ElegantNavChange.ReplaceAll)
    }

    @Test
    fun multiPush_carriesCount_andEqualsByCount() {
        assertEquals(ElegantNavChange.MultiPush(3), ElegantNavChange.MultiPush(3))
        assertNotEquals(ElegantNavChange.MultiPush(3), ElegantNavChange.MultiPush(2))
        assertEquals(3, ElegantNavChange.MultiPush(3).count)
    }

    @Test
    fun multiPop_carriesCount_andEqualsByCount() {
        assertEquals(ElegantNavChange.MultiPop(4), ElegantNavChange.MultiPop(4))
        assertNotEquals(ElegantNavChange.MultiPop(4), ElegantNavChange.MultiPop(1))
        assertEquals(4, ElegantNavChange.MultiPop(4).count)
    }
}
