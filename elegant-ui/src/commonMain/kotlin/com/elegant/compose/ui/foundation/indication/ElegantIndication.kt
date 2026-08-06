// Copyright 2025, compose-miuix-ui contributors
// Copyright 2026, elegant-ui contributors
// SPDX-License-Identifier: Apache-2.0
// Ported from Miuix (https://github.com/yukonga/Miuix) under Apache-2.0.

package com.elegant.compose.ui.foundation.indication

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.SpringSpec
import androidx.compose.foundation.Indication
import androidx.compose.foundation.IndicationNodeFactory
import androidx.compose.foundation.interaction.FocusInteraction
import androidx.compose.foundation.interaction.HoverInteraction
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.node.DelegatableNode
import androidx.compose.ui.node.DrawModifierNode
import com.elegant.compose.ui.foundation.animation.elegantFolmeSpring
import com.elegant.compose.ui.foundation.theme.ElegantTheme
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

/** Overlay alpha added while the pointer hovers the component. */
internal const val HoverAlphaDelta: Float = 0.06f

/** Overlay alpha added while the component holds keyboard focus. */
internal const val FocusAlphaDelta: Float = 0.08f

/** Overlay alpha added while the component is pressed. */
internal const val PressAlphaDelta: Float = 0.10f

private val PressEnterSpring: SpringSpec<Float> =
    elegantFolmeSpring(dampingRatio = 1.0f, responseSeconds = 0.2f)
private val PressExitSpring: SpringSpec<Float> =
    elegantFolmeSpring(dampingRatio = 0.95f, responseSeconds = 0.35f)
private val HoverEnterSpring: SpringSpec<Float> =
    elegantFolmeSpring(dampingRatio = 1.0f, responseSeconds = 0.6f)
private val HoverExitSpring: SpringSpec<Float> =
    elegantFolmeSpring(dampingRatio = 0.96f, responseSeconds = 0.2f)

/**
 * Sum of the overlay alphas contributed by the active interaction states, following the
 * HyperOS feedback convention: press `+0.10`, hover `+0.06`, focus `+0.08`.
 */
internal fun indicationTargetAlpha(
    isPressed: Boolean,
    isHovered: Boolean,
    isFocused: Boolean,
): Float {
    var targetAlpha = 0.0f
    if (isHovered) targetAlpha += HoverAlphaDelta
    if (isFocused) targetAlpha += FocusAlphaDelta
    if (isPressed) targetAlpha += PressAlphaDelta
    return targetAlpha
}

/**
 * Default [Indication] used by Elegant UI components: a flat rectangular overlay in [color] whose
 * alpha is driven by press, hover, and focus interactions with Folme springs.
 *
 * Unlike a Material ripple, the overlay covers the full component bounds, which is the HyperOS
 * feedback language: a pressed surface darkens uniformly instead of spreading a highlight from the
 * touch point. Components clip the overlay with their own shape.
 *
 * @param color the overlay color; the theme default is the primary text color so the overlay works
 *   over both light and dark containers.
 */
@Immutable
public class ElegantIndication(
    private val color: Color = Color.Black,
) : IndicationNodeFactory {

    override fun create(interactionSource: InteractionSource): DelegatableNode =
        ElegantIndicationInstance(interactionSource, color)

    override fun hashCode(): Int = color.hashCode()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ElegantIndication) return false
        if (color != other.color) return false
        return true
    }

    private class ElegantIndicationInstance(
        private val interactionSource: InteractionSource,
        private val color: Color,
    ) : Modifier.Node(),
        DrawModifierNode {
        private var isPressed = false
        private var isHovered = false
        private var isFocused = false
        private val animatedAlpha = Animatable(0f)
        private var pressedAnimation: Job? = null
        private var restingAnimation: Job? = null

        private fun animateOverlay(spring: SpringSpec<Float>, fromPressRelease: Boolean) {
            val target = indicationTargetAlpha(isPressed, isHovered, isFocused)
            if (fromPressRelease || target == 0f) {
                restingAnimation?.cancel()
                restingAnimation =
                    coroutineScope.launch {
                        pressedAnimation?.join()
                        animatedAlpha.animateTo(targetValue = target, animationSpec = spring)
                    }
            } else {
                pressedAnimation?.cancel()
                restingAnimation?.cancel()
                pressedAnimation =
                    coroutineScope.launch {
                        animatedAlpha.animateTo(targetValue = target, animationSpec = spring)
                    }
            }
        }

        override fun onAttach() {
            coroutineScope.launch {
                interactionSource.interactions.collect { interaction ->
                    val previousPressed = isPressed
                    val previousHovered = isHovered
                    val previousFocused = isFocused

                    when (interaction) {
                        is PressInteraction.Press -> isPressed = true
                        is PressInteraction.Release, is PressInteraction.Cancel -> isPressed = false
                        is HoverInteraction.Enter -> isHovered = true
                        is HoverInteraction.Exit -> isHovered = false
                        is FocusInteraction.Focus -> isFocused = true
                        is FocusInteraction.Unfocus -> isFocused = false
                        else -> return@collect
                    }

                    val spring =
                        when {
                            previousPressed != isPressed ->
                                if (isPressed) PressEnterSpring else PressExitSpring
                            previousHovered != isHovered ->
                                if (isHovered) HoverEnterSpring else HoverExitSpring
                            previousFocused != isFocused ->
                                if (isFocused) HoverEnterSpring else HoverExitSpring
                            else -> return@collect
                        }
                    val fromPressRelease = previousPressed && !isPressed
                    animateOverlay(spring, fromPressRelease)
                }
            }
        }

        override fun ContentDrawScope.draw() {
            drawContent()
            val alpha = animatedAlpha.value
            if (alpha > 0f) {
                drawRect(color = color, alpha = alpha, size = size)
            }
        }
    }
}

/**
 * The theme-default [ElegantIndication], colored with the primary text color of the active palette.
 *
 * Prefer reading [androidx.compose.foundation.LocalIndication] inside components so callers can
 * override the indication for a subtree; this factory exists for callers that need an explicit
 * indication outside of [ElegantTheme].
 */
@Composable
public fun elegantIndication(): Indication {
    val overlayColor = ElegantTheme.colors.textPrimary
    return remember(overlayColor) { ElegantIndication(color = overlayColor) }
}
