// Copyright 2026, elegant-ui contributors
// SPDX-License-Identifier: Apache-2.0

package com.elegant.compose.ui.topappbar

import androidx.compose.runtime.saveable.Saver
import kotlin.test.Test
import kotlin.test.assertEquals

internal class ElegantTopAppBarStateContractTest {

    @Test
    fun heightOffsetClampsIntoTheCollapseRange() {
        val state = ElegantTopAppBarState(
            initialHeightOffsetLimit = -200f,
            initialHeightOffset = 0f,
            initialContentOffset = 0f,
        )
        assertEquals(0f, clampTopAppBarHeightOffset(50f, state.heightOffsetLimit))
        assertEquals(-100f, clampTopAppBarHeightOffset(-100f, state.heightOffsetLimit))
        assertEquals(-200f, clampTopAppBarHeightOffset(-500f, state.heightOffsetLimit))
    }

    @Test
    fun collapsedFractionTracksTheOffsetOverTheLimit() {
        assertEquals(0f, resolveTopAppBarCollapsedFraction(0f, -200f), absoluteTolerance = 0.0001f)
        assertEquals(0.5f, resolveTopAppBarCollapsedFraction(-100f, -200f))
        assertEquals(1f, resolveTopAppBarCollapsedFraction(-200f, -200f), absoluteTolerance = 0.0001f)
        assertEquals(0f, resolveTopAppBarCollapsedFraction(-50f, 0f))
    }

    @Test
    fun settleTargetSnapsToTheNearerStableState() {
        val limit = -200f
        assertEquals(0f, resolveTopAppBarSettleTarget(0f, limit))
        assertEquals(0f, resolveTopAppBarSettleTarget(0.49f, limit))
        assertEquals(limit, resolveTopAppBarSettleTarget(0.5f, limit))
        assertEquals(limit, resolveTopAppBarSettleTarget(0.99f, limit))
    }

    @Test
    fun saverRestoresTheSavedFields() {
        @Suppress("UNCHECKED_CAST")
        val saver = ElegantTopAppBarState.Saver as Saver<ElegantTopAppBarState, List<Any?>>
        // The list mirrors the field order written by the Saver's save lambda.
        val restored = saver.restore(listOf(-160f, -80f, 40f))
        assertEquals(-160f, restored?.heightOffsetLimit)
        assertEquals(-80f, restored?.heightOffset)
        assertEquals(40f, restored?.contentOffset)
    }
}
