// Copyright 2025, compose-miuix-ui contributors
// Copyright 2026, elegant-ui contributors
// SPDX-License-Identifier: Apache-2.0
// Ported from Miuix (https://github.com/yukonga/Miuix) under Apache-2.0.

package com.elegant.compose.ui.foundation.indication

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.spring
import androidx.compose.foundation.IndicationNodeFactory
import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.input.pointer.PointerEvent
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.MeasureResult
import androidx.compose.ui.layout.MeasureScope
import androidx.compose.ui.node.DelegatableNode
import androidx.compose.ui.node.LayoutModifierNode
import androidx.compose.ui.node.PointerInputModifierNode
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.IntSize
import kotlinx.coroutines.launch

/**
 * Press feedback that sinks the component to [sinkAmount] of its size while pressed.
 *
 * The scale animates with [animationSpec] and leaves the layout size untouched, so neighboring
 * content never moves. Apply through [ElegantPressFeedbackType.Sink].
 */
@Stable
public data class ElegantSinkFeedback(
    public val sinkAmount: Float = 0.94f,
    public val animationSpec: AnimationSpec<Float> = spring(dampingRatio = 0.8f, stiffness = 600f),
) : IndicationNodeFactory {

    override fun create(interactionSource: InteractionSource): DelegatableNode =
        SinkFeedbackNode(interactionSource, sinkAmount, animationSpec)

    private class SinkFeedbackNode(
        var interactionSource: InteractionSource,
        var sinkAmount: Float,
        var animationSpec: AnimationSpec<Float>,
    ) : Modifier.Node(),
        LayoutModifierNode {

        private val animatedScale = Animatable(1f)
        private var isPressed = false

        private fun updateState() {
            val target = if (isPressed) sinkAmount else 1f
            coroutineScope.launch { animatedScale.animateTo(target, animationSpec) }
        }

        override fun onAttach() {
            coroutineScope.launch {
                interactionSource.interactions.collect { interaction: Interaction ->
                    when (interaction) {
                        is PressInteraction.Press -> isPressed = true
                        is PressInteraction.Release, is PressInteraction.Cancel -> isPressed = false
                        else -> return@collect
                    }
                    updateState()
                }
            }
        }

        override fun MeasureScope.measure(
            measurable: Measurable,
            constraints: Constraints,
        ): MeasureResult {
            val placeable = measurable.measure(constraints)
            return layout(placeable.width, placeable.height) {
                placeable.placeWithLayer(0, 0) {
                    scaleX = animatedScale.value
                    scaleY = animatedScale.value
                }
            }
        }
    }
}

/**
 * Press feedback that tilts the component up to [tiltAmount] degrees around the corner opposite
 * the touch point, producing the three-dimensional tilt of iOS app icons.
 *
 * The rotation pivots around the touch anchor while pressed and returns to rest on release.
 * Apply through [ElegantPressFeedbackType.Tilt].
 */
@Stable
public data class ElegantTiltFeedback(
    public val tiltAmount: Float = 8f,
    public val animationSpec: AnimationSpec<Float> = spring(dampingRatio = 0.6f, stiffness = 400f),
) : IndicationNodeFactory {

    override fun create(interactionSource: InteractionSource): DelegatableNode =
        TiltFeedbackNode(interactionSource, tiltAmount, animationSpec)

    private class TiltFeedbackNode(
        var interactionSource: InteractionSource,
        var tiltAmount: Float,
        var animationSpec: AnimationSpec<Float>,
    ) : Modifier.Node(),
        LayoutModifierNode,
        PointerInputModifierNode {

        private var transformOrigin: TransformOrigin = TransformOrigin.Center
        private var targetX = 0f
        private var targetY = 0f
        private val animatedTiltX = Animatable(0f)
        private val animatedTiltY = Animatable(0f)
        private var isPressed = false

        private fun updateState() {
            if (isPressed) {
                coroutineScope.launch { animatedTiltX.animateTo(targetX, animationSpec) }
                coroutineScope.launch { animatedTiltY.animateTo(targetY, animationSpec) }
            } else {
                coroutineScope.launch { animatedTiltX.animateTo(0f, animationSpec) }
                coroutineScope.launch { animatedTiltY.animateTo(0f, animationSpec) }
            }
        }

        override fun onAttach() {
            coroutineScope.launch {
                interactionSource.interactions.collect { interaction: Interaction ->
                    when (interaction) {
                        is PressInteraction.Press -> isPressed = true
                        is PressInteraction.Release, is PressInteraction.Cancel -> isPressed = false
                        else -> return@collect
                    }
                    updateState()
                }
            }
        }

        override fun onPointerEvent(
            pointerEvent: PointerEvent,
            pass: PointerEventPass,
            bounds: IntSize,
        ) {
            if (pass != PointerEventPass.Main) return
            if (pointerEvent.type == PointerEventType.Press) {
                val offset = pointerEvent.changes.first().position

                transformOrigin = TransformOrigin(
                    pivotFractionX = if (offset.x < bounds.width / 2f) 1f else 0f,
                    pivotFractionY = if (offset.y < bounds.height / 2f) 1f else 0f,
                )

                targetX = if (offset.y < bounds.height / 2f) tiltAmount else -tiltAmount
                targetY = if (offset.x < bounds.width / 2f) -tiltAmount else tiltAmount
            }
        }

        override fun onCancelPointerInput() {
            transformOrigin = TransformOrigin.Center
            targetX = 0f
            targetY = 0f
        }

        override fun MeasureScope.measure(
            measurable: Measurable,
            constraints: Constraints,
        ): MeasureResult {
            val placeable = measurable.measure(constraints)
            return layout(placeable.width, placeable.height) {
                placeable.placeWithLayer(0, 0) {
                    rotationX = animatedTiltX.value
                    rotationY = animatedTiltY.value
                    cameraDistance = 12 * density
                    this.transformOrigin = this@TiltFeedbackNode.transformOrigin
                }
            }
        }
    }
}

/**
 * The type of visual feedback applied while a component is pressed.
 */
public enum class ElegantPressFeedbackType {
    /** Default overlay indication. */
    None,

    /** Sinks the component to `0.94` of its size with a soft spring. */
    Sink,

    /** Tilts the component up to `8` degrees around the touch anchor. */
    Tilt,
}

/** Resolves [ElegantPressFeedbackType] to its [IndicationNodeFactory]; `null` for [ElegantPressFeedbackType.None]. */
internal fun resolvePressFeedback(feedback: ElegantPressFeedbackType): IndicationNodeFactory? =
    when (feedback) {
        ElegantPressFeedbackType.None -> null
        ElegantPressFeedbackType.Sink -> ElegantSinkFeedback()
        ElegantPressFeedbackType.Tilt -> ElegantTiltFeedback()
    }
