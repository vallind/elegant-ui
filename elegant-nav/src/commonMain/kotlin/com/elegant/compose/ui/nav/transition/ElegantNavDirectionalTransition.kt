// Copyright 2026, elegant-ui contributors
// SPDX-License-Identifier: Apache-2.0
// Ported from compose-miuix-ui/miuix (Apache-2.0).

package com.elegant.compose.ui.nav.transition

import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import com.elegant.compose.ui.nav.core.ElegantLiveNavTransitionScope
import com.elegant.compose.ui.nav.runtime.ElegantNavChange

/**
 * Composes three [ElegantNavTransition]s into one that dispatches per drive direction, mirroring the
 * platform split between programmatic transitions and the predictive back gesture:
 *
 * - a gesture owns the float (predictive back or edge swipe; the context stays frozen through
 *   the whole release settle) -> [predictivePop];
 * - the last stack change is a pop ([ElegantNavChange.Pop] / [ElegantNavChange.MultiPop]) -> [pop];
 * - anything else (push, replace, initial) -> [push].
 *
 * Static contracts merge from their natural sources: [ElegantNavTransition.opaqueDepth] is the max of
 * the three (the host keeps a layer alive while ANY branch would), [ElegantNavTransition.dismissDirection]
 * comes from [predictivePop] (the edge swipe drives that branch), and [ElegantNavTransition.motion]
 * takes commit/cancel from [predictivePop] and programmatic from [pop]. `push.motion` is never
 * consumed — route-level asymmetry is available via per-route transition overrides instead.
 *
 * Known limit: under the grab-anytime model a gesture claiming the stack mid-programmatic-settle
 * switches the dispatch from [pop]/[push] to [predictivePop] at the grab instant; if the two
 * branches disagree geometrically at that depth the style jumps for one frame. The platform
 * avoids this by making its commit animation uninterruptible; this library keeps interruption
 * and documents the trade-off — author branches that stay close in the grabbable range when
 * that matters.
 *
 * @param push transition for forward changes (and replace/initial states).
 * @param pop transition for programmatic pops; defaults to [push].
 * @param predictivePop transition while a gesture drives; defaults to [pop].
 */
fun elegantNavDirectionalTransition(
    push: ElegantNavTransition,
    pop: ElegantNavTransition = push,
    predictivePop: ElegantNavTransition = pop,
): ElegantNavTransition = ElegantNavDirectionalTransition(push = push, pop = pop, predictivePop = predictivePop)

@Stable
private class ElegantNavDirectionalTransition(
    private val push: ElegantNavTransition,
    private val pop: ElegantNavTransition,
    private val predictivePop: ElegantNavTransition,
) : ElegantNavTransition {
    override val opaqueDepth: Float =
        maxOf(push.opaqueDepth, pop.opaqueDepth, predictivePop.opaqueDepth)

    override val dismissDirection: ElegantNavSwipeDirection = predictivePop.dismissDirection

    override val motion: ElegantNavMotion = ElegantNavMotion(
        commit = predictivePop.motion.commit,
        cancel = predictivePop.motion.cancel,
        programmatic = pop.motion.programmatic,
    )

    private fun branchFor(scope: ElegantNavTransitionScope): ElegantNavTransition = when {
        scope.isGestureDriven() -> predictivePop
        scope.change is ElegantNavChange.Pop || scope.change is ElegantNavChange.MultiPop -> pop
        else -> push
    }

    override fun Modifier.transformEntry(scope: ElegantNavTransitionScope): Modifier {
        val branch = branchFor(scope)
        return with(branch) { transformEntry(scope) }
    }

    override fun scrimFraction(scope: ElegantNavTransitionScope): Float = branchFor(scope).scrimFraction(scope)
}

/**
 * Composition-safe gesture check: the live scope exposes a derived threshold-level flag (the raw
 * [ElegantNavTransitionScope.gesture] is a fresh instance per move event and would recompose hosts per
 * event if read during composition); foreign scope implementations fall back to the raw field.
 */
private fun ElegantNavTransitionScope.isGestureDriven(): Boolean = (this as? ElegantLiveNavTransitionScope)?.gestureActive ?: (gesture != null)
