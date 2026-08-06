package com.elegant.compose.ui.foundation.shape

import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.border
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import kotlin.math.min

/**
 * Continuous-curvature rounded rectangle whose corners approximate the superellipse
 * `|x/a|^n + |y/b|^n = 1` with `n ≈ 4`.
 *
 * Unlike [androidx.compose.foundation.shape.RoundedCornerShape], whose corners join a circle arc
 * to a straight edge at a tangent discontinuity, every corner of [ElegantSquircleShape] is a
 * cubic Bezier arc whose curvature stays continuous into the adjoining straight edges. The result
 * reads as a single soft silhouette, which is why squircles are common in app icons, avatars, and
 * modern card layouts.
 *
 * Each corner arc spans from `(radius, 0)` to `(0, radius)` in corner-local coordinates (top-left,
 * clockwise). The two interior control points are placed on the tangents at a fraction
 * `1 - k` of the radius, where `k = 0.552f * smoothing`. `k` is the standard circle-Bezier
 * constant: `k = 0` collapses each corner to a plain right angle and `k ≈ 0.552` reproduces the
 * control-offset ratio of a circle arc, which is the roundest stable superellipse look.
 * The effective radius clamps to half of the smaller side, so the outline never self-intersects
 * on small surfaces.
 *
 * @property cornerRadius corner radius of the superellipse arcs.
 * @property smoothing Bezier factor in `0..1`; `0` yields plain corners, `1` the roundest arc.
 */
@Immutable
public class ElegantSquircleShape(
    public val cornerRadius: Dp = 16.dp,
    public val smoothing: Float = 0.65f,
) : Shape {

    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density,
    ): Outline {
        if (size.width <= 0f || size.height <= 0f) {
            return Outline.Rectangle(Rect(0f, 0f, size.width, size.height))
        }
        val radiusPx = resolveSquircleCornerRadiusPx(cornerRadius, size, density)
        val segments = squircleCornerSegments(size, squircleCornerGeometry(radiusPx, smoothing))
        return Outline.Generic(
            path = buildSquirclePath(segments),
        )
    }
}

internal data class CornerGeometry(
    val c1: Offset,
    val c2: Offset,
    val end: Offset,
)

internal fun superellipseBezierFactor(smoothing: Float): Float {
    val clamped = if (smoothing.isFinite()) smoothing.coerceIn(0f, 1f) else 0f
    return CircleBezierFactor * clamped
}

private const val CircleBezierFactor: Float = 0.552f

internal fun squircleCornerGeometry(
    cornerRadiusPx: Float,
    smoothing: Float,
): CornerGeometry {
    val factor = superellipseBezierFactor(smoothing)
    return CornerGeometry(
        c1 = Offset(cornerRadiusPx * (1f - factor), 0f),
        c2 = Offset(0f, cornerRadiusPx * (1f - factor)),
        end = Offset(0f, cornerRadiusPx),
    )
}

internal data class CornerSegment(
    val start: Offset,
    val c1: Offset,
    val c2: Offset,
    val end: Offset,
)

internal fun squircleCornerSegments(
    size: Size,
    geometry: CornerGeometry,
): List<CornerSegment> {
    val width = size.width
    val height = size.height
    val radius = geometry.end.y
    return listOf(
        CornerSegment(
            start = Offset(radius, 0f),
            c1 = geometry.c1,
            c2 = geometry.c2,
            end = geometry.end,
        ),
        CornerSegment(
            start = Offset(0f, height - radius),
            c1 = Offset(0f, height - geometry.c1.x),
            c2 = Offset(geometry.c1.x, height),
            end = Offset(radius, height),
        ),
        CornerSegment(
            start = Offset(width - radius, height),
            c1 = Offset(width - geometry.c1.x, height),
            c2 = Offset(width, height - geometry.c1.x),
            end = Offset(width, height - radius),
        ),
        CornerSegment(
            start = Offset(width, radius),
            c1 = Offset(width, geometry.c1.x),
            c2 = Offset(width - geometry.c1.x, 0f),
            end = Offset(width - radius, 0f),
        ),
    )
}

internal fun resolveSquircleCornerRadiusPx(
    cornerRadius: Dp,
    size: Size,
    density: Density,
): Float {
    val radiusPx = with(density) { cornerRadius.toPx() }
    return radiusPx.coerceAtLeast(0f).coerceAtMost(min(size.width, size.height) / 2f)
}

internal fun buildSquirclePath(segments: List<CornerSegment>): Path {
    require(segments.size == 4) { "squircle outlines require exactly four corner segments" }
    return Path().apply {
        val first = segments.first()
        moveTo(first.start.x, first.start.y)
        segments.forEachIndexed { index, segment ->
            cubicTo(
                x1 = segment.c1.x,
                y1 = segment.c1.y,
                x2 = segment.c2.x,
                y2 = segment.c2.y,
                x3 = segment.end.x,
                y3 = segment.end.y,
            )
            if (index < segments.lastIndex) {
                val next = segments[index + 1]
                lineTo(next.start.x, next.start.y)
            }
        }
        close()
    }
}

/**
 * Enables or disables squircle rendering for the subtree.
 *
 * When disabled, the [Modifier.elegantSquircleSurface], [Modifier.elegantSquircleBorder], and
 * [Modifier.elegantSquircleClip] helpers fall back to plain rounded rectangles with the same
 * corner radius.
 */
public val LocalSquircleEnabled: androidx.compose.runtime.ProvidableCompositionLocal<Boolean> =
    androidx.compose.runtime.staticCompositionLocalOf { true }

/** Clips [this] node with a squircle of the given corner radius and smoothing. */
@androidx.compose.runtime.Composable
public fun Modifier.elegantSquircleClip(
    cornerRadius: Dp = 16.dp,
    smoothing: Float = 0.65f,
): Modifier = clip(resolvedSquircleShape(cornerRadius, smoothing))

/** Clips and fills [this] node with a squircle surface of [color]. */
@androidx.compose.runtime.Composable
public fun Modifier.elegantSquircleSurface(
    color: Color,
    cornerRadius: Dp = 16.dp,
    smoothing: Float = 0.65f,
): Modifier = clip(resolvedSquircleShape(cornerRadius, smoothing)).background(color)

/** Draws a squircle border of [width] and [color] on [this] node. */
@androidx.compose.runtime.Composable
public fun Modifier.elegantSquircleBorder(
    width: Dp,
    color: Color,
    cornerRadius: Dp = 16.dp,
    smoothing: Float = 0.65f,
): Modifier = border(width, color, resolvedSquircleShape(cornerRadius, smoothing))

/**
 * Resolves a component's default surface shape to a squircle when the caller did not customize
 * [userShape] and squircle rendering is enabled; otherwise the default rounded shape stands.
 */
@androidx.compose.runtime.Composable
internal fun resolveSquircleAwareShape(
    userShape: Shape,
    defaultShape: Shape,
    cornerRadius: Dp,
): Shape =
    if (userShape == defaultShape) {
        if (LocalSquircleEnabled.current) {
            ElegantSquircleShape(cornerRadius = cornerRadius)
        } else {
            defaultShape
        }
    } else {
        userShape
    }

@androidx.compose.runtime.Composable
internal fun resolvedSquircleShape(cornerRadius: Dp, smoothing: Float): Shape =
    if (LocalSquircleEnabled.current) {
        ElegantSquircleShape(cornerRadius = cornerRadius, smoothing = smoothing)
    } else {
        RoundedCornerShape(cornerRadius)
    }
