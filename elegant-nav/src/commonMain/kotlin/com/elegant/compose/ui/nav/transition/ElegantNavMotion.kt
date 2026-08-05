// Copyright 2026, elegant-ui contributors
// SPDX-License-Identifier: Apache-2.0
// Ported from compose-miuix-ui/miuix (Apache-2.0).

package com.elegant.compose.ui.nav.transition

import androidx.compose.animation.core.Easing
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import com.elegant.compose.ui.nav.runtime.ElegantNavDriverSpec
import com.elegant.compose.ui.nav.runtime.ElegantNavProgrammaticEasing

/**
 * Timing curve of one settle phase of the shared `animatedTop` driver.
 *
 * Structured (rather than a raw `AnimationSpec`) so the runtime can reason about it: the
 * no-overshoot velocity floor needs [Spring.stiffness], and a velocity-carrying settle must
 * detect a [Tween] (which cannot seed velocity) to fall back to a spring.
 */
@Stable
sealed interface ElegantNavSettleSpec {

    /**
     * A physical spring. The defaults reproduce the established navigation feel (critically
     * damped, low stiffness on the entry-index axis, roughly half a second for a full step).
     *
     * @param dampingRatio damping ratio; `1f` (critical) never oscillates. Values `< 1`
     *   (underdamped) overshoot by construction and require [clampOvershoot] `= false`.
     * @param stiffness spring stiffness on the depth axis (units = entries).
     * @param clampOvershoot when `true` (default), a commit settle floors its seed velocity at
     *   the exact no-overshoot bound, so a flung release never bounces past the rest position.
     *   Pass `false` to keep the full release velocity (and, with `dampingRatio < 1`, a visible
     *   velocity-scaled bounce).
     */
    @Immutable
    data class Spring(
        val dampingRatio: Float = ElegantNavDriverSpec.DAMPING_RATIO,
        val stiffness: Float = ElegantNavDriverSpec.STIFFNESS,
        val clampOvershoot: Boolean = true,
    ) : ElegantNavSettleSpec {
        init {
            require(dampingRatio >= 1f || !clampOvershoot) {
                "An underdamped spring (dampingRatio = $dampingRatio) overshoots by construction; " +
                    "pass clampOvershoot = false to acknowledge the bounce."
            }
        }
    }

    /**
     * A fixed-duration tween. Completes in exactly [durationMillis] regardless of distance, but
     * cannot seed a release velocity: a gesture-commit tween starts from the commit point with a
     * velocity discontinuity, and a velocity-carrying programmatic settle falls back to a spring
     * (see `ElegantNavMotion.programmatic`).
     */
    @Stable
    data class Tween(
        val durationMillis: Int,
        val easing: Easing,
    ) : ElegantNavSettleSpec
}

/**
 * The settle physics a [ElegantNavTransition] declares for the phases it governs. The host resolves the
 * motion from the topmost presented entry's governing transition (per-route overrides win), so a
 * preset carries its own feel together with its geometry.
 *
 * @param commit curve of a gesture-release commit (predictive back and edge swipe), seeded with
 *   the release velocity when it is a [ElegantNavSettleSpec.Spring].
 * @param cancel curve of a gesture-release cancel. The host pins the rest position as an upper
 *   bound during cancel settles, so even an underdamped cancel spring never crosses into the
 *   covered regime (input blocking and boundary ownership live there).
 * @param programmatic curve of a from-rest, full-step programmatic push/pop (multi-step
 *   included). Settles that carry velocity or cover a partial distance use [commit] when it is a
 *   spring, else the default spring — a tween cannot continue a velocity.
 */
@Stable
class ElegantNavMotion(
    val commit: ElegantNavSettleSpec = ElegantNavSettleSpec.Spring(),
    val cancel: ElegantNavSettleSpec = ElegantNavSettleSpec.Spring(),
    val programmatic: ElegantNavSettleSpec = ElegantNavSettleSpec.Tween(
        durationMillis = ElegantNavDriverSpec.PROGRAMMATIC_DURATION_MILLIS,
        easing = ElegantNavProgrammaticEasing,
    ),
) {
    companion object {
        /** Shared default instance: the established navigation feel, no overshoot anywhere. */
        val Default: ElegantNavMotion = ElegantNavMotion()
    }
}
