package com.elegant.compose.ui.foundation.shape

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import kotlin.math.abs
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertTrue

internal class ElegantSquircleShapeContractTest {

    private val testDensity = object : Density {
        override val density: Float = 1f
        override val fontScale: Float = 1f
    }

    @Test
    fun bezierFactorMapsSmoothingOntoTheCircleConstant() {
        assertEquals(0f, superellipseBezierFactor(0f), absoluteTolerance = 0.0001f)
        assertEquals(0.552f, superellipseBezierFactor(1f), absoluteTolerance = 0.0001f)
        assertEquals(0.276f, superellipseBezierFactor(0.5f), absoluteTolerance = 0.0001f)
        assertEquals(0.3312f, superellipseBezierFactor(0.6f), absoluteTolerance = 0.0001f)
    }

    @Test
    fun bezierFactorClampsOutOfRangeAndNonFiniteSmoothing() {
        assertEquals(0f, superellipseBezierFactor(-1f), absoluteTolerance = 0.0001f)
        assertEquals(0.552f, superellipseBezierFactor(2f), absoluteTolerance = 0.0001f)
        assertEquals(0f, superellipseBezierFactor(Float.NaN), absoluteTolerance = 0.0001f)
        assertEquals(0f, superellipseBezierFactor(Float.POSITIVE_INFINITY), absoluteTolerance = 0.0001f)
        assertEquals(0f, superellipseBezierFactor(Float.NEGATIVE_INFINITY), absoluteTolerance = 0.0001f)
    }

    @Test
    fun cornerGeometryKeepsControlsOrderedAndInsideTheCorner() {
        val geometry = squircleCornerGeometry(cornerRadiusPx = 40f, smoothing = 1f)

        assertEquals(0f, geometry.c1.y, absoluteTolerance = 0.001f)
        assertTrue(geometry.c1.x > 0f && geometry.c1.x < 40f, "first control must sit on the top tangent inside the corner")
        assertEquals(0f, geometry.c2.x, absoluteTolerance = 0.001f)
        assertTrue(geometry.c2.y > 0f && geometry.c2.y < 40f, "second control must sit on the left tangent inside the corner")
        assertOffsetEquals(Offset(0f, 40f), geometry.end)

        assertTrue(geometry.c1.x >= geometry.c2.x, "controls must order along the arc")
        assertTrue(geometry.c1.y <= geometry.c2.y, "controls must order along the arc")
        assertEquals(geometry.c1.x, geometry.c2.y, absoluteTolerance = 0.0001f)
    }

    @Test
    fun cornerGeometryCollapsesToAPlainCornerAtZeroSmoothing() {
        val geometry = squircleCornerGeometry(cornerRadiusPx = 40f, smoothing = 0f)

        assertOffsetEquals(Offset(40f, 0f), geometry.c1)
        assertOffsetEquals(Offset(0f, 40f), geometry.c2)
        assertOffsetEquals(Offset(0f, 40f), geometry.end)
    }

    @Test
    fun cornerRadiusClampsToHalfTheMinimumDimensionAndStaysNonNegative() {
        assertEquals(30f, resolveSquircleCornerRadiusPx(999.dp, Size(100f, 60f), testDensity))
        assertEquals(50f, resolveSquircleCornerRadiusPx(999.dp, Size(100f, 100f), testDensity))
        assertEquals(12f, resolveSquircleCornerRadiusPx(12.dp, Size(100f, 60f), testDensity))
        assertEquals(0f, resolveSquircleCornerRadiusPx((-8).dp, Size(100f, 60f), testDensity))
    }

    @Test
    fun mirroredCornersProduceCoincidentStraightEdgeEndpoints() {
        val size = Size(100f, 80f)
        val radius = 16f
        val geometry = squircleCornerGeometry(cornerRadiusPx = radius, smoothing = 0.6f)
        val segments = squircleCornerSegments(size, geometry)
        assertEquals(4, segments.size)
        val (topLeft, bottomLeft, bottomRight, topRight) = segments

        assertEquals(Offset(radius, 0f), topLeft.start)
        assertEquals(Offset(0f, radius), topLeft.end)

        assertEquals(Offset(0f, size.height - radius), bottomLeft.start)
        assertEquals(Offset(radius, size.height), bottomLeft.end)

        assertEquals(Offset(size.width - radius, size.height), bottomRight.start)
        assertEquals(Offset(size.width, size.height - radius), bottomRight.end)

        assertEquals(Offset(size.width, radius), topRight.start)
        assertEquals(Offset(size.width - radius, 0f), topRight.end)

        assertEquals(topLeft.end.x, bottomLeft.start.x, "left edge endpoints must share the x axis")
        assertEquals(bottomLeft.end.y, bottomRight.start.y, "bottom edge endpoints must share the y axis")
        assertEquals(bottomRight.end.x, topRight.start.x, "right edge endpoints must share the x axis")
        assertEquals(topRight.end.y, topLeft.start.y, "top edge endpoints must share the y axis")

        assertEquals(Offset(0f, size.height - topLeft.c1.x), bottomLeft.c1, "bottom-left must mirror the top-left corner")
        assertEquals(Offset(size.width, size.height - topLeft.c1.x), bottomRight.c2, "bottom-right must mirror the top-left corner")
    }

    @Test
    fun degenerateSizesProduceARectangleOutline() {
        val shape = ElegantSquircleShape()

        assertIs<Outline.Rectangle>(
            shape.createOutline(Size(0f, 100f), LayoutDirection.Ltr, testDensity),
        )
        assertIs<Outline.Rectangle>(
            shape.createOutline(Size(100f, 0f), LayoutDirection.Ltr, testDensity),
        )
    }

    @Test
    fun shapeExposesConfiguredRadiusAndSmoothing() {
        val defaults = ElegantSquircleShape()
        assertEquals(16.dp, defaults.cornerRadius)
        assertEquals(0.65f, defaults.smoothing)

        val custom = ElegantSquircleShape(cornerRadius = 24.dp, smoothing = 1f)
        assertEquals(24.dp, custom.cornerRadius)
        assertEquals(1f, custom.smoothing)
    }

    private fun assertPathCoversSize(path: Path, size: Size) {
        val bounds = path.getBounds()
        assertTrue(abs(bounds.left) < 0.01f, "path must start at x = 0")
        assertTrue(abs(bounds.top) < 0.01f, "path must start at y = 0")
        assertTrue(abs(bounds.right - size.width) < 0.01f, "path must reach the full width")
        assertTrue(abs(bounds.bottom - size.height) < 0.01f, "path must reach the full height")
    }
}

private fun assertOffsetEquals(expected: Offset, actual: Offset, tolerance: Float = 0.001f) {
    assertTrue(
        kotlin.math.abs(expected.x - actual.x) <= tolerance &&
            kotlin.math.abs(expected.y - actual.y) <= tolerance,
        "expected $expected but was $actual",
    )
}
