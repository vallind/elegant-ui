// Copyright 2026, elegant-ui contributors
// SPDX-License-Identifier: Apache-2.0
// Ported from compose-miuix-ui/miuix (Apache-2.0).

package com.elegant.compose.ui.nav.internal

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import kotlin.math.max
import kotlin.math.min

/**
 * Corner-tile size as a multiple of `cornerRadius`. 1.0 = circular arc, 1.1 = continuous corner.
 */
internal const val ElegantSquircleExtension = 1.1f

/**
 * Cubic Bézier handle ratio used by the squircle corner path. Must stay in lock-step with the
 * value the pre-baked SDF was generated for so path-based and shader-backed silhouettes line up.
 */
internal const val ElegantSquircleControl = 0.643f

/**
 * Absolute-positioning variant of a squircle clip: carves each physical corner with a squircle
 * silhouette (Cubic Bézier handles at [ElegantSquircleControl] of the corner tile) and never
 * flips the radii by `LocalLayoutDirection`. Falls back to a plain rectangle for zero radii.
 *
 * Inline path-based stand-in for the shader-backed `absoluteSquircleClip` of miuix-squircle,
 * kept local to keep the navigation module free of any `:elegant-ui` dependency.
 *
 * @param topLeft The physical top-left corner radius, never flipped by `LocalLayoutDirection`.
 * @param topRight The physical top-right corner radius, never flipped by `LocalLayoutDirection`.
 * @param bottomRight The physical bottom-right corner radius, never flipped by `LocalLayoutDirection`.
 * @param bottomLeft The physical bottom-left corner radius, never flipped by `LocalLayoutDirection`.
 * @param extension The corner-tile size as a multiple of each corner radius, clamped to 1f..2f.
 */
@Composable
internal fun Modifier.absoluteSquircleClip(
    topLeft: Dp,
    topRight: Dp,
    bottomRight: Dp,
    bottomLeft: Dp,
    extension: Float = ElegantSquircleExtension,
): Modifier {
    val shape = remember(topLeft, topRight, bottomRight, bottomLeft, extension) {
        AbsoluteSquircleShape(topLeft, topRight, bottomRight, bottomLeft, extension)
    }
    return this.clip(shape)
}

private class AbsoluteSquircleShape(
    private val topLeft: Dp,
    private val topRight: Dp,
    private val bottomRight: Dp,
    private val bottomLeft: Dp,
    private val extension: Float,
) : Shape {

    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density,
    ): Outline {
        val path = Path()
        if (size.width <= 0f || size.height <= 0f) {
            return Outline.Generic(path)
        }
        path.addAbsoluteSquircleRect(
            size.width,
            size.height,
            topLeft = topLeft.toPx(density),
            topRight = topRight.toPx(density),
            bottomRight = bottomRight.toPx(density),
            bottomLeft = bottomLeft.toPx(density),
            extension = extension,
        )
        return Outline.Generic(path)
    }

    private fun Dp.toPx(density: Density): Float = this.value * density.density
}

/**
 * Appends a squircle-shaped rounded rectangle with an independent radius per physical corner,
 * starting at the top-left corner and winding clockwise. Nothing is appended when the size is
 * not positive; zero radii degrade to straight edges.
 */
private fun Path.addAbsoluteSquircleRect(
    width: Float,
    height: Float,
    topLeft: Float,
    topRight: Float,
    bottomRight: Float,
    bottomLeft: Float,
    extension: Float,
) {
    val extClamped = extension.coerceIn(1f, 2f)
    val halfMin = min(width, height) * 0.5f
    val tiles = listOf(topLeft, topRight, bottomRight, bottomLeft).map { radius ->
        max(0f, radius * extClamped).coerceAtMost(halfMin)
    }
    val (tl, tr, br, bl) = tiles
    if (tl <= 0f && tr <= 0f && br <= 0f && bl <= 0f) {
        addRect(Rect(0f, 0f, width, height))
        return
    }
    val handles = tiles.map { it * (1f - ElegantSquircleControl) }
    val (tlH, trH, brH, blH) = handles
    moveTo(tl, 0f)
    lineTo(width - tr, 0f)
    cubicTo(width - trH, 0f, width, trH, width, tr)
    lineTo(width, height - br)
    cubicTo(width, height - brH, width - brH, height, width - br, height)
    lineTo(bl, height)
    cubicTo(blH, height, 0f, height - blH, 0f, height - bl)
    lineTo(0f, tl)
    cubicTo(0f, tlH, tlH, 0f, tl, 0f)
    close()
}
