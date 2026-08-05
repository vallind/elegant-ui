// Copyright 2026, elegant-ui contributors
// SPDX-License-Identifier: Apache-2.0
// Ported from compose-miuix-ui/miuix (Apache-2.0).

package com.elegant.compose.ui.nav.core

import androidx.compose.runtime.Stable
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.LayoutDirection
import com.elegant.compose.ui.nav.runtime.ElegantNavChange
import com.elegant.compose.ui.nav.runtime.ElegantNavPresentation
import com.elegant.compose.ui.nav.runtime.relativeDepth
import com.elegant.compose.ui.nav.runtime.roleFor
import com.elegant.compose.ui.nav.transition.ElegantNavGesture
import com.elegant.compose.ui.nav.transition.ElegantNavRole
import com.elegant.compose.ui.nav.transition.ElegantNavSettle
import com.elegant.compose.ui.nav.transition.ElegantNavTransitionScope

/**
 * A [ElegantNavTransitionScope] whose [relativeDepth] (and derived [role]) are **deferred reads** of the
 * live [ElegantNavPresentation.animatedTop]. Passing this into a transition that itself reads inside a
 * `graphicsLayer { }` (e.g. [com.elegant.compose.ui.nav.transition.elegantNavGraphicsTransition]) yields
 * zero-recomposition, per-frame visual updates.
 *
 * @param presentation source of the single driving float.
 * @param entryIndex this entry's index in the back stack (for the depth subtraction).
 * @param isRemoving whether this entry is animating out (affects [role]).
 */
@Stable
internal class ElegantLiveNavTransitionScope(
    private val presentation: ElegantNavPresentation,
    private val entryIndex: Int,
    private val isRemoving: Boolean,
    override val change: ElegantNavChange,
    override val layoutSize: IntSize,
    override val layoutDirection: LayoutDirection,
    override val density: Density,
) : ElegantNavTransitionScope {
    override val relativeDepth: Float
        get() = relativeDepth(presentation.animatedTop.value, entryIndex)

    override val role: ElegantNavRole
        get() = roleFor(relativeDepth, isRemoving)

    override val gesture: ElegantNavGesture? get() = presentation.gesture

    override val settle: ElegantNavSettle? get() = presentation.settle

    /** Coarse gesture flag for composition-time branch dispatch (see elegantNavDirectionalTransition). */
    internal val gestureActive: Boolean get() = presentation.gestureActive
}
