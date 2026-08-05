// Copyright 2026, elegant-ui contributors
// SPDX-License-Identifier: Apache-2.0
// Ported from compose-miuix-ui/miuix (Apache-2.0).

package com.elegant.compose.ui.nav.runtime

import kotlin.test.Test
import kotlin.test.assertEquals

class ElegantNavReconcilerTest {
    // --- commonPrefixLength ---

    @Test
    fun commonPrefix_identicalLists() {
        assertEquals(3, commonPrefixLength(listOf("a", "b", "c"), listOf("a", "b", "c")))
    }

    @Test
    fun commonPrefix_emptyEither() {
        assertEquals(0, commonPrefixLength(emptyList(), listOf("a")))
        assertEquals(0, commonPrefixLength(listOf("a"), emptyList()))
        assertEquals(0, commonPrefixLength(emptyList(), emptyList()))
    }

    @Test
    fun commonPrefix_sharedPrefixThenDiverge() {
        assertEquals(2, commonPrefixLength(listOf("a", "b", "x"), listOf("a", "b", "y")))
    }

    @Test
    fun commonPrefix_oneIsPrefixOfOther() {
        assertEquals(2, commonPrefixLength(listOf("a", "b"), listOf("a", "b", "c", "d")))
        assertEquals(2, commonPrefixLength(listOf("a", "b", "c", "d"), listOf("a", "b")))
    }

    @Test
    fun commonPrefix_firstElementDiffers() {
        assertEquals(0, commonPrefixLength(listOf("x", "b", "c"), listOf("a", "b", "c")))
    }

    @Test
    fun commonPrefix_usesEqualityNotIdentity() {
        // Distinct String instances that are value-equal must count as a match.
        val a1 = StringBuilder("a").toString()
        val a2 = StringBuilder("a").toString()
        assertEquals(1, commonPrefixLength(listOf<Any>(a1, "b"), listOf<Any>(a2, "c")))
    }

    // --- elegantNavReconcile ---

    @Test
    fun reconcile_identical_isNone() {
        assertEquals(ElegantNavChange.None, elegantNavReconcile(listOf("a", "b"), listOf("a", "b")))
    }

    @Test
    fun reconcile_bothEmpty_isNone() {
        assertEquals(ElegantNavChange.None, elegantNavReconcile(emptyList(), emptyList()))
    }

    @Test
    fun reconcile_singlePush() {
        assertEquals(ElegantNavChange.Push, elegantNavReconcile(listOf("a"), listOf("a", "b")))
    }

    @Test
    fun reconcile_pushFromEmpty_isPush() {
        assertEquals(ElegantNavChange.Push, elegantNavReconcile(emptyList(), listOf("a")))
    }

    @Test
    fun reconcile_multiPush() {
        assertEquals(ElegantNavChange.MultiPush(2), elegantNavReconcile(listOf("a"), listOf("a", "b", "c")))
        assertEquals(ElegantNavChange.MultiPush(3), elegantNavReconcile(listOf("a"), listOf("a", "b", "c", "d")))
    }

    @Test
    fun reconcile_singlePop() {
        assertEquals(ElegantNavChange.Pop, elegantNavReconcile(listOf("a", "b"), listOf("a")))
    }

    @Test
    fun reconcile_multiPop() {
        assertEquals(ElegantNavChange.MultiPop(2), elegantNavReconcile(listOf("a", "b", "c"), listOf("a")))
        assertEquals(ElegantNavChange.MultiPop(3), elegantNavReconcile(listOf("a", "b", "c", "d"), listOf("a")))
    }

    @Test
    fun reconcile_popToEmpty_isMultiPopOrPop() {
        // Removing the only element: removed == 1, added == 0 -> Pop.
        assertEquals(ElegantNavChange.Pop, elegantNavReconcile(listOf("a"), emptyList()))
        // Removing all of several: removed > 1 -> MultiPop.
        assertEquals(ElegantNavChange.MultiPop(2), elegantNavReconcile(listOf("a", "b"), emptyList()))
    }

    @Test
    fun reconcile_topReplace_isReplace() {
        // common == new.size - 1 && removed == 1 && added == 1.
        assertEquals(ElegantNavChange.Replace, elegantNavReconcile(listOf("a", "b"), listOf("a", "c")))
    }

    @Test
    fun reconcile_topReplaceWithDeeperStack_isReplace() {
        assertEquals(ElegantNavChange.Replace, elegantNavReconcile(listOf("a", "b", "c"), listOf("a", "b", "d")))
    }

    @Test
    fun reconcile_replaceRootKeepsNothingCommon_isReplaceAll() {
        // First element differs -> common == 0.
        assertEquals(ElegantNavChange.ReplaceAll, elegantNavReconcile(listOf("a", "b"), listOf("x", "y")))
    }

    @Test
    fun reconcile_mixedAddRemove_notSingleTop_isReplaceAll() {
        // common == 1, removed == 2, added == 2 -> not a single top Replace.
        assertEquals(ElegantNavChange.ReplaceAll, elegantNavReconcile(listOf("a", "b", "c"), listOf("a", "x", "y")))
    }

    @Test
    fun reconcile_replaceTopWhenAddedMoreThanOne_isReplaceAll() {
        // common == 1, removed == 1, added == 2 -> not Replace (added != 1).
        assertEquals(ElegantNavChange.ReplaceAll, elegantNavReconcile(listOf("a", "b"), listOf("a", "x", "y")))
    }

    @Test
    fun reconcile_replaceWholeStackToSingle_isReplaceAll() {
        // common == 0, removed == 2, added == 1.
        assertEquals(ElegantNavChange.ReplaceAll, elegantNavReconcile(listOf("a", "b"), listOf("z")))
    }

    @Test
    fun reconcile_singleRootSwap_isReplace() {
        // common == 0 == new.size - 1, removed == 1, added == 1 -> a one-for-one swap of the lone
        // root is classified as Replace (in-place top swap with no layer below), not ReplaceAll.
        assertEquals(ElegantNavChange.Replace, elegantNavReconcile(listOf("a"), listOf("b")))
    }
}
