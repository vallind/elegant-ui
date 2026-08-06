// Copyright 2025, compose-miuix-ui contributors
// Copyright 2026, elegant-ui contributors
// SPDX-License-Identifier: Apache-2.0
// Ported from Miuix (https://github.com/yukonga/Miuix) under Apache-2.0.

package com.elegant.compose.ui.foundation.animation

import androidx.compose.animation.core.SpringSpec
import androidx.compose.animation.core.spring
import kotlin.math.PI

/**
 * Creates a [SpringSpec] from damping and response parameters, matching the Folme motion system
 * behind Miuix and HyperOS feedback.
 *
 * Folme springs are expressed in physically meaningful terms instead of raw stiffness: [responseSeconds]
 * is the time it takes the spring to settle through its initial response, so a smaller value animates
 * faster, and [dampingRatio] controls overshoot the same way it does for [spring].
 *
 * @param dampingRatio the spring damping ratio; `1.0` is critically damped (no overshoot),
 *   values below `1.0` oscillate, values above `1.0` are overdamped.
 * @param responseSeconds the response time in seconds; smaller values produce faster motion.
 * @param visibilityThreshold the magnitude below which the animation is considered settled and the
 *   invisible settle tail is cut; `null` falls back to the framework default displacement threshold.
 */
public fun <T> elegantFolmeSpring(
    dampingRatio: Float,
    responseSeconds: Float,
    visibilityThreshold: T? = null,
): SpringSpec<T> {
    val stiffness = ((2.0 * PI / responseSeconds) * (2.0 * PI / responseSeconds)).toFloat()
    return spring(
        dampingRatio = dampingRatio,
        stiffness = stiffness,
        visibilityThreshold = visibilityThreshold,
    )
}
