package com.elegant.compose.ui.foundation.icons

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.dp
import com.elegant.compose.ui.iconbutton.ElegantIconDefaults
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

internal class ElegantIconsContractTest {

    @Test
    fun iconSetContains24BuiltInIcons() {
        assertEquals(24, ElegantIcons.All.size)
    }

    @Test
    fun everyIconBuildsWith24DpViewport() {
        ElegantIcons.All.forEach { icon ->
            assertEquals(24f, icon.viewportWidth)
            assertEquals(24f, icon.viewportHeight)
        }
    }

    @Test
    fun chevronPolylineIsCenteredAndProportional() {
        val arm = 24f * 0.22f
        val right = ElegantIcons.chevronPolyline(1f, vertical = false, size = 24f)
        assertEquals(3, right.size)
        assertEquals(12f + arm, right[1].x, absoluteTolerance = 0.01f)
        assertEquals(12f, right[1].y, absoluteTolerance = 0.01f)
        assertEquals(12f - 2f * arm, right[0].x, absoluteTolerance = 0.01f)
        assertEquals(12f - arm, right[0].y, absoluteTolerance = 0.01f)
        assertEquals(12f - 2f * arm, right[2].x, absoluteTolerance = 0.01f)
        assertEquals(12f + arm, right[2].y, absoluteTolerance = 0.01f)

        val left = ElegantIcons.chevronPolyline(-1f, vertical = false, size = 24f)
        assertEquals(12f - arm, left[1].x, absoluteTolerance = 0.01f)
        assertEquals(12f + 2f * arm, left[0].x, absoluteTolerance = 0.01f)

        val down = ElegantIcons.chevronPolyline(1f, vertical = true, size = 24f)
        assertEquals(12f, down[1].x, absoluteTolerance = 0.01f)
        assertEquals(12f + arm, down[1].y, absoluteTolerance = 0.01f)
        assertEquals(12f - arm, down[0].x, absoluteTolerance = 0.01f)
        assertEquals(12f - 2f * arm, down[0].y, absoluteTolerance = 0.01f)
        assertEquals(12f + arm, down[2].x, absoluteTolerance = 0.01f)
        assertEquals(12f - 2f * arm, down[2].y, absoluteTolerance = 0.01f)
    }

    @Test
    fun closeEndpointsAreSymmetricAndClamped() {
        val endpoints = ElegantIcons.closeEndpoints(24f, 0.25f)
        assertEquals(4, endpoints.size)
        assertEquals(endpoints[0].x, endpoints[2].y, absoluteTolerance = 0.01f)
        assertEquals(endpoints[0].y, endpoints[3].x, absoluteTolerance = 0.01f)
        assertEquals(endpoints[1].x, endpoints[3].y, absoluteTolerance = 0.01f)
        assertEquals(endpoints[1].y, endpoints[2].x, absoluteTolerance = 0.01f)
        assertEquals(6f, endpoints[0].x, absoluteTolerance = 0.01f)
        assertEquals(18f, endpoints[1].x, absoluteTolerance = 0.01f)
        assertEquals(18f, endpoints[2].x, absoluteTolerance = 0.01f)
        assertEquals(6f, endpoints[3].x, absoluteTolerance = 0.01f)

        val clampedLow = ElegantIcons.closeEndpoints(24f, 0.1f)
        assertEquals(24f * 0.2f, clampedLow[0].x, absoluteTolerance = 0.01f)
        val clampedHigh = ElegantIcons.closeEndpoints(24f, 0.9f)
        assertEquals(24f * 0.45f, clampedHigh[0].x, absoluteTolerance = 0.01f)
    }

    @Test
    fun checkPolylineRunsCornerToCorner() {
        val points = ElegantIcons.checkPolyline(24f)
        assertEquals(3, points.size)
        assertTrue(points[0].x < points[1].x)
        assertTrue(points[0].y < points[1].y)
        assertTrue(points[1].x < points[2].x)
        assertTrue(points[2].y < points[1].y)
        assertTrue(points[2].x > 20f)
    }

    @Test
    fun plusEndpointsAreCenteredAndSymmetric() {
        val endpoints = ElegantIcons.plusEndpoints(24f)
        assertEquals(4, endpoints.size)
        assertEquals(12f, endpoints[0].x, absoluteTolerance = 0.01f)
        assertEquals(12f, endpoints[1].x, absoluteTolerance = 0.01f)
        assertEquals(12f, endpoints[2].y, absoluteTolerance = 0.01f)
        assertEquals(12f, endpoints[3].y, absoluteTolerance = 0.01f)
        assertEquals(endpoints[0].y, 24f * 0.16f, absoluteTolerance = 0.01f)
        assertEquals(endpoints[1].y, 24f * 0.84f, absoluteTolerance = 0.01f)
        assertEquals(endpoints[2].x, 24f * 0.16f, absoluteTolerance = 0.01f)
        assertEquals(endpoints[3].x, 24f * 0.84f, absoluteTolerance = 0.01f)
    }

    @Test
    fun starPointsAlternateRadiiAndStayInBounds() {
        val points = ElegantIcons.starPoints(Offset(12f, 12f), 9.5f, 3.9f)
        assertEquals(10, points.size)
        points.forEachIndexed { index, point ->
            val radius = if (index % 2 == 0) 9.5f else 3.9f
            val distance = kotlin.math.sqrt(
                (point.x - 12f) * (point.x - 12f) + (point.y - 12f) * (point.y - 12f),
            )
            assertEquals(radius, distance, absoluteTolerance = 0.01f)
            assertTrue(point.x in 0f..24f)
            assertTrue(point.y in 0f..24f)
        }
    }

    @Test
    fun personGeometryIsInBounds() {
        val geometry = ElegantIcons.personGeometry(24f)
        assertEquals(12f, geometry.headCenter.x, absoluteTolerance = 0.01f)
        assertTrue(geometry.headRadius in 3f..5f)
        assertTrue(geometry.shoulderLeft.x < geometry.shoulderRight.x)
        assertTrue(geometry.shoulderLeft.y > geometry.headCenter.y)
    }

    @Test
    fun directionalIconsPairUp() {
        assertTrue(ElegantIcons.ArrowLeft != ElegantIcons.ArrowRight)
        assertTrue(ElegantIcons.ChevronLeft != ElegantIcons.ChevronRight)
        assertTrue(ElegantIcons.ArrowUp != ElegantIcons.ArrowDown)
        assertTrue(ElegantIcons.MoreVert != ElegantIcons.MoreHoriz)
    }

    @Test
    fun defaultsExposeStandardSize() {
        assertEquals(24.dp, ElegantIconDefaults.Size)
    }
}
