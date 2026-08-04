package com.elegant.compose.ui.foundation.effect

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Edge treatment of a blurred layer, mirroring the Compose Multiplatform blur edge treatment.
 *
 * [BlurEdgeTreatment.Rectangle] clips the blurred layer to the node's rectangular bounds;
 * [BlurEdgeTreatment.Unbounded] lets the blur extend beyond the node's bounds.
 */
public enum class BlurEdgeTreatment {
    /** The blurred layer is clipped to the node's rectangular bounds. */
    Rectangle,

    /** The blur may extend beyond the node's bounds. */
    Unbounded,
}

/**
 * Applies a Gaussian blur render effect to the node's own drawn content.
 *
 * The modifier maps onto the Compose Multiplatform blur render effect (`Modifier.blur` with its
 * [BlurredEdgeTreatment]), which every supported target implements with the platform
 * render-effect pipeline: Skia `ImageFilter` on Desktop JVM, the blur `RenderEffect` on Android,
 * and the equivalent canvas filter on Web/Wasm.
 *
 * The effect blurs only the node's own drawn content: siblings drawn behind the node are not
 * blurred on any supported target. Callers that need a background blur should compose a blurred
 * copy of the background content behind the foreground content.
 *
 * A non-positive or non-finite [radius] leaves the modifier unchanged.
 *
 * @param radius blur radius; values of `0.dp` or less, `NaN`, or infinities are coerced to
 * `0.dp`, which keeps the modifier a no-op.
 * @param edgeTreatment how the blurred layer is treated at its edges.
 */
public fun Modifier.elegantBlur(
    radius: Dp,
    edgeTreatment: BlurEdgeTreatment = BlurEdgeTreatment.Rectangle,
): Modifier {
    val resolvedRadius = resolveBlurRadius(radius)
    if (resolvedRadius == 0.dp) {
        return this
    }
    return this.blur(
        radius = resolvedRadius,
        edgeTreatment = resolveBlurEdgeTreatment(edgeTreatment),
    )
}

internal fun resolveBlurRadius(radius: Dp): Dp {
    if (!radius.value.isFinite()) {
        return 0.dp
    }
    return radius.coerceAtLeast(0.dp)
}

internal fun resolveBlurEdgeTreatment(
    edgeTreatment: BlurEdgeTreatment,
): BlurredEdgeTreatment =
    when (edgeTreatment) {
        BlurEdgeTreatment.Rectangle -> BlurredEdgeTreatment.Rectangle
        BlurEdgeTreatment.Unbounded -> BlurredEdgeTreatment.Unbounded
    }
