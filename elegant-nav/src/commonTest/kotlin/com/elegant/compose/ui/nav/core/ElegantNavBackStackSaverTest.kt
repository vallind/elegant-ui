// Copyright 2026, elegant-ui contributors
// SPDX-License-Identifier: Apache-2.0
// Ported from compose-miuix-ui/miuix (Apache-2.0).

package com.elegant.compose.ui.nav.core

import androidx.compose.runtime.saveable.SaverScope
import kotlinx.serialization.Serializable
import kotlinx.serialization.serializer
import kotlin.test.Test
import kotlin.test.assertEquals

private val TestSaverScope: SaverScope = SaverScope { true }

class ElegantNavBackStackSaverTest {
    @Serializable
    private sealed interface Route : ElegantNavKey {
        @Serializable
        data object Home : Route

        @Serializable
        data class Detail(val id: String) : Route
    }

    @Test
    fun saver_roundTripsPolymorphicKeys() {
        val original: ElegantNavBackStack = elegantNavBackStackOf(Route.Home, Route.Detail("42"))
        val saver = elegantNavBackStackSaver(serializer<List<Route>>())
        val saved = with(saver) { TestSaverScope.save(original) }
        checkNotNull(saved) { "Saver.save returned null" }
        val restored = saver.restore(saved)
        checkNotNull(restored) { "Saver.restore returned null" }
        assertEquals(original.toList(), restored.toList())
    }

    @Test
    fun saver_roundTripsEmptyStack() {
        val original: ElegantNavBackStack = elegantNavBackStackOf()
        val saver = elegantNavBackStackSaver(serializer<List<Route>>())
        val saved = with(saver) { TestSaverScope.save(original) }
        checkNotNull(saved)
        val restored = saver.restore(saved)
        checkNotNull(restored)
        assertEquals(emptyList(), restored.toList())
    }
}
