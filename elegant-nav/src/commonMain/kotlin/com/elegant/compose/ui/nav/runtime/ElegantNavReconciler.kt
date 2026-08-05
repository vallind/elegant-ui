// Copyright 2026, elegant-ui contributors
// SPDX-License-Identifier: Apache-2.0
// Ported from compose-miuix-ui/miuix (Apache-2.0).

package com.elegant.compose.ui.nav.runtime

/**
 * Pure classifier of a back-stack change over `contentKey` lists.
 *
 * Algorithm (design spec §8):
 * - `common` = [commonPrefixLength] of [old] and [new];
 * - `removed` = old.size - common, `added` = new.size - common;
 * - classify (the first matching arm wins, so pure add/remove are decided before the mixed arm):
 *   - identical lists -> [ElegantNavChange.None];
 *   - added > 0 && removed == 0 -> [ElegantNavChange.Push] (added == 1) else [ElegantNavChange.MultiPush]
 *     (regardless of `common`, so pushing onto an empty stack is still a [ElegantNavChange.Push]);
 *   - removed > 0 && added == 0 -> [ElegantNavChange.Pop] (removed == 1) else [ElegantNavChange.MultiPop];
 *   - added > 0 && removed > 0 -> [ElegantNavChange.Replace] only when it is a one-for-one top swap
 *     (common == new.size - 1 && removed == 1 && added == 1); this also covers swapping a lone
 *     root such as `["a"] -> ["b"]` (common == 0 == new.size - 1). Every other mixed add+remove
 *     (including a no-shared-prefix multi-entry replacement) -> [ElegantNavChange.ReplaceAll].
 *
 * The reconciler caller additionally marks removed entries as exiting and drives
 * `animatedTop` target = `new.lastIndex`; that side of the work lives in the rendering layer.
 */
internal fun elegantNavReconcile(old: List<Any>, new: List<Any>): ElegantNavChange {
    val common = commonPrefixLength(old, new)
    val removed = old.size - common
    val added = new.size - common
    return when {
        removed == 0 && added == 0 -> ElegantNavChange.None
        removed == 0 -> if (added == 1) ElegantNavChange.Push else ElegantNavChange.MultiPush(added)
        added == 0 -> if (removed == 1) ElegantNavChange.Pop else ElegantNavChange.MultiPop(removed)
        common == new.size - 1 && removed == 1 && added == 1 -> ElegantNavChange.Replace
        else -> ElegantNavChange.ReplaceAll
    }
}

/**
 * Length of the longest common prefix of [old] and [new] compared by element equality.
 *
 * Both lists are `contentKey` lists; equality (not identity) decides a match so that value-stable
 * keys recompose-survive correctly.
 */
internal fun commonPrefixLength(old: List<Any>, new: List<Any>): Int {
    val limit = minOf(old.size, new.size)
    var i = 0
    while (i < limit && old[i] == new[i]) i++
    return i
}
