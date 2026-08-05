// Copyright 2026, elegant-ui contributors
// SPDX-License-Identifier: Apache-2.0
// Ported from compose-miuix-ui/miuix (Apache-2.0).

package com.elegant.compose.ui.nav.runtime

/**
 * Classification of a back-stack change, computed by [elegantNavReconcile] and surfaced to transitions
 * via `ElegantNavTransitionScope.change`. Lets callers animate a multi-level pop differently from a
 * single pop.
 */
public sealed interface ElegantNavChange {
    /** The stack is unchanged. */
    public data object None : ElegantNavChange

    /** Exactly one entry was added (added == 1, removed == 0). */
    public data object Push : ElegantNavChange

    /** Exactly one entry was removed (removed == 1, added == 0). */
    public data object Pop : ElegantNavChange

    /** More than one entry was added at once (added > 1, removed == 0). [count] == added. */
    public data class MultiPush(val count: Int) : ElegantNavChange

    /** More than one entry was removed at once (removed > 1, added == 0). [count] == removed. */
    public data class MultiPop(val count: Int) : ElegantNavChange

    /** The top entry was replaced (common == new.size - 1 && removed == 1 && added == 1). */
    public data object Replace : ElegantNavChange

    /** The whole stack (or a non-top mixed add/remove) was replaced. */
    public data object ReplaceAll : ElegantNavChange
}
