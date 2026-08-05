// Copyright 2026, elegant-ui contributors
// SPDX-License-Identifier: Apache-2.0
// Ported from compose-miuix-ui/miuix (Apache-2.0).

package com.elegant.compose.ui.nav.core

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.v2.runComposeUiTest
import androidx.navigationevent.DirectNavigationEventInput
import androidx.navigationevent.NavigationEventDispatcher
import androidx.navigationevent.NavigationEventDispatcherOwner
import androidx.navigationevent.compose.LocalNavigationEventDispatcherOwner
import com.elegant.compose.ui.nav.transition.ElegantNavTransitions
import kotlin.test.Test
import kotlin.test.assertEquals

private data object OuterA : ElegantNavKey

private data object OuterB : ElegantNavKey

private data object InnerX : ElegantNavKey

private data object InnerY : ElegantNavKey

/**
 * Back dispatch across nested displays: every entry's back consumers live under a per-entry child
 * dispatcher enabled only for the interactive top, so a covered entry's inner stack — composed and
 * registered later than the outer display's handler — can never steal the system back via LIFO,
 * while a top entry's inner stack still wins and falls through at its root.
 */
@OptIn(ExperimentalTestApi::class)
class ElegantNavNestedBackDispatchTest {

    private class TestOwner : NavigationEventDispatcherOwner {
        override val navigationEventDispatcher = NavigationEventDispatcher()
        val input = DirectNavigationEventInput().also { navigationEventDispatcher.addInput(it) }
    }

    @Test
    fun back_popsOuterTop_notCoveredEntrysInnerStack() = runComposeUiTest {
        val owner = TestOwner()
        val outer = elegantNavBackStackOf(OuterA, OuterB)
        val inner = elegantNavBackStackOf(InnerX, InnerY)
        setContent {
            CompositionLocalProvider(LocalNavigationEventDispatcherOwner provides owner) {
                ElegantNavDisplay(outer, transition = ElegantNavTransitions.None, effects = ElegantNavDisplayEffects.None) {
                    entry<OuterA> {
                        ElegantNavDisplay(inner, transition = ElegantNavTransitions.None, effects = ElegantNavDisplayEffects.None) {
                            entry<InnerX> { Box(Modifier.fillMaxSize()) }
                            entry<InnerY> { Box(Modifier.fillMaxSize()) }
                        }
                    }
                    entry<OuterB> { Box(Modifier.fillMaxSize()) }
                }
            }
        }
        waitForIdle()

        runOnIdle { owner.input.backCompleted() }
        waitForIdle()

        assertEquals(listOf<ElegantNavKey>(OuterA), outer.toList(), "back must pop the outer top")
        assertEquals(listOf<ElegantNavKey>(InnerX, InnerY), inner.toList(), "the covered entry's inner stack must not be popped")
    }

    @Test
    fun back_popsTopEntrysInnerStack_thenFallsThroughToOuter() = runComposeUiTest {
        val owner = TestOwner()
        val outer = elegantNavBackStackOf(OuterA, OuterB)
        val inner = elegantNavBackStackOf(InnerX, InnerY)
        setContent {
            CompositionLocalProvider(LocalNavigationEventDispatcherOwner provides owner) {
                ElegantNavDisplay(outer, transition = ElegantNavTransitions.None, effects = ElegantNavDisplayEffects.None) {
                    entry<OuterA> { Box(Modifier.fillMaxSize()) }
                    entry<OuterB> {
                        ElegantNavDisplay(inner, transition = ElegantNavTransitions.None, effects = ElegantNavDisplayEffects.None) {
                            entry<InnerX> { Box(Modifier.fillMaxSize()) }
                            entry<InnerY> { Box(Modifier.fillMaxSize()) }
                        }
                    }
                }
            }
        }
        waitForIdle()

        // Top entry's inner stack wins first.
        runOnIdle { owner.input.backCompleted() }
        waitForIdle()
        assertEquals(listOf<ElegantNavKey>(InnerX), inner.toList(), "back must pop the top entry's inner stack first")
        assertEquals(listOf<ElegantNavKey>(OuterA, OuterB), outer.toList())

        // Inner at root: its handler disables and back falls through to the outer display.
        runOnIdle { owner.input.backCompleted() }
        waitForIdle()
        assertEquals(listOf<ElegantNavKey>(InnerX), inner.toList())
        assertEquals(listOf<ElegantNavKey>(OuterA), outer.toList(), "back must fall through to the outer display")
    }
}
