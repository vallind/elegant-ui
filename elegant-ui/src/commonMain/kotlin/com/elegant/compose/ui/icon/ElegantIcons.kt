package com.elegant.compose.ui.icon

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

/**
 * Built-in Elegant UI vector icons.
 *
 * Icons are 24dp viewport vector paths that inherit their color from the render tint; the fill
 * color is fixed to opaque black and replaced at draw time. Use [ElegantIcon] to render them.
 */
public object ElegantIcons {

    /** Arrow pointing left. */
    public val ArrowLeft: ImageVector = arrowIcon(direction = -1f)

    /** Arrow pointing right. */
    public val ArrowRight: ImageVector = arrowIcon(direction = 1f)

    /** Arrow pointing up. */
    public val ArrowUp: ImageVector = arrowIcon(direction = -1f, vertical = true)

    /** Arrow pointing down. */
    public val ArrowDown: ImageVector = arrowIcon(direction = 1f, vertical = true)

    /** Chevron pointing left. */
    public val ChevronLeft: ImageVector = chevronIcon(direction = -1f)

    /** Chevron pointing right. */
    public val ChevronRight: ImageVector = chevronIcon(direction = 1f)

    /** Chevron pointing up. */
    public val ChevronUp: ImageVector = chevronIcon(direction = -1f, vertical = true)

    /** Chevron pointing down. */
    public val ChevronDown: ImageVector = chevronIcon(direction = 1f, vertical = true)

    /** Check mark. */
    public val Check: ImageVector = checkIcon()

    /** Close (X) mark. */
    public val Close: ImageVector = closeIcon()

    /** Plus mark. */
    public val Plus: ImageVector = plusIcon()

    /** Minus mark. */
    public val Minus: ImageVector = minusIcon()

    /** Magnifying glass for search. */
    public val Search: ImageVector = searchIcon()

    /** Pencil for editing. */
    public val Edit: ImageVector = editIcon()

    /** Trash can for deletion. */
    public val Delete: ImageVector = deleteIcon()

    /** Share nodes and links. */
    public val Share: ImageVector = shareIcon()

    /** Three vertical dots. */
    public val MoreVert: ImageVector = dotsIcon(vertical = true)

    /** Three horizontal dots. */
    public val MoreHoriz: ImageVector = dotsIcon(vertical = false)

    /** Person silhouette. */
    public val Person: ImageVector = personIcon()

    /** Home silhouette. */
    public val Home: ImageVector = homeIcon()

    /** Gear for settings. */
    public val Settings: ImageVector = settingsIcon()

    /** Bell for notifications. */
    public val Notifications: ImageVector = notificationsIcon()

    /** Five-point star. */
    public val Star: ImageVector = starIcon()

    /** Heart silhouette. */
    public val Heart: ImageVector = heartIcon()

    /** All icons in declaration order. */
    public val All: List<ImageVector> = listOf(
        ArrowLeft,
        ArrowRight,
        ArrowUp,
        ArrowDown,
        ChevronLeft,
        ChevronRight,
        ChevronUp,
        ChevronDown,
        Check,
        Close,
        Plus,
        Minus,
        Search,
        Edit,
        Delete,
        Share,
        MoreVert,
        MoreHoriz,
        Person,
        Home,
        Settings,
        Notifications,
        Star,
        Heart,
    )

    /** Three polyline points for a chevron in a 24-grid of [size]. */
    internal fun chevronPolyline(direction: Float, vertical: Boolean, size: Float): List<Offset> {
        val s = size
        val tip = s / 2f
        val arm = s * 0.22f
        val start = if (vertical) s * 0.30f else if (direction < 0) tip + arm else tip - arm
        val end = if (vertical) s * 0.70f else if (direction < 0) tip - arm else tip + arm
        return if (vertical) {
            listOf(
                Offset(tip, start),
                Offset(if (direction < 0) tip + arm else tip - arm, tip),
                Offset(tip, end),
            )
        } else {
            listOf(
                Offset(start, tip),
                Offset(tip, if (direction < 0) tip - arm else tip + arm),
                Offset(end, tip),
            )
        }
    }

    /** Four endpoints of the close (X) glyph for a 24-grid of [size] with [insetFraction] inset. */
    internal fun closeEndpoints(size: Float, insetFraction: Float): List<Offset> {
        val inset = size * insetFraction.coerceIn(0.2f, 0.45f)
        return listOf(
            Offset(inset, inset),
            Offset(size - inset, size - inset),
            Offset(size - inset, inset),
            Offset(inset, size - inset),
        )
    }

    /** Three polyline points of the check glyph for a 24-grid of [size]. */
    internal fun checkPolyline(size: Float): List<Offset> = listOf(
        Offset(size * 0.17f, size * 0.52f),
        Offset(size * 0.42f, size * 0.75f),
        Offset(size * 0.84f, size * 0.26f),
    )

    /** Four endpoints of the plus glyph for a 24-grid of [size]. */
    internal fun plusEndpoints(size: Float): List<Offset> = listOf(
        Offset(size / 2f, size * 0.16f),
        Offset(size / 2f, size * 0.84f),
        Offset(size * 0.16f, size / 2f),
        Offset(size * 0.84f, size / 2f),
    )

    /** Ten points of a five-point star centered at [center] with the given radii. */
    internal fun starPoints(center: Offset, outerRadius: Float, innerRadius: Float): List<Offset> {
        val points = mutableListOf<Offset>()
        for (index in 0 until 10) {
            val angle = kotlin.math.PI * (90.0 + index * 36.0) / 180.0
            val radius = if (index % 2 == 0) outerRadius else innerRadius
            points.add(
                Offset(
                    x = center.x + (radius * kotlin.math.cos(angle)).toFloat(),
                    y = center.y - (radius * kotlin.math.sin(angle)).toFloat(),
                ),
            )
        }
        return points
    }

    /** Person geometry: head center + radius, shoulder arc endpoints. */
    internal fun personGeometry(size: Float): PersonGeometry = PersonGeometry(
        headCenter = Offset(size / 2f, size * 0.36f),
        headRadius = size * 0.17f,
        shoulderLeft = Offset(size * 0.14f, size * 0.88f),
        shoulderRight = Offset(size * 0.86f, size * 0.88f),
    )

    /** Geometry of the person icon. */
    internal data class PersonGeometry(
        val headCenter: Offset,
        val headRadius: Float,
        val shoulderLeft: Offset,
        val shoulderRight: Offset,
    )
}

private fun vectorIcon(block: androidx.compose.ui.graphics.vector.ImageVector.Builder.() -> Unit): ImageVector =
    ImageVector.Builder(
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f,
    ).apply(block).build()

private fun filledPath(
    builder: androidx.compose.ui.graphics.vector.ImageVector.Builder,
    block: androidx.compose.ui.graphics.vector.PathBuilder.() -> Unit,
) {
    builder.path(fill = SolidColor(Color.Black), pathBuilder = block)
}

private fun strokedPath(
    builder: androidx.compose.ui.graphics.vector.ImageVector.Builder,
    width: Float,
    block: androidx.compose.ui.graphics.vector.PathBuilder.() -> Unit,
) {
    builder.path(
        fill = null,
        stroke = SolidColor(Color.Black),
        strokeLineWidth = width,
        strokeLineCap = StrokeCap.Round,
        strokeLineJoin = StrokeJoin.Round,
        pathBuilder = block,
    )
}

private fun arrowIcon(direction: Float, vertical: Boolean = false): ImageVector = vectorIcon {
    if (vertical) {
        strokedPath(this, 2.4f) {
            moveTo(12f, 3.5f + if (direction > 0) 1f else 0f)
            lineTo(12f, if (direction > 0) 20.5f else 3.5f)
        }
        filledPath(this) {
            moveTo(7.5f, if (direction > 0) 13f else 8.5f)
            lineTo(12f, if (direction > 0) 19f else 4f)
            lineTo(16.5f, if (direction > 0) 13f else 8.5f)
            close()
        }
    } else {
        strokedPath(this, 2.4f) {
            moveTo(if (direction > 0) 3.5f else 20.5f, 12f)
            lineTo(if (direction > 0) 20.5f else 3.5f, 12f)
        }
        filledPath(this) {
            moveTo(if (direction > 0) 11f else 8.5f, 7.5f)
            lineTo(if (direction > 0) 20f else 4f, 12f)
            lineTo(if (direction > 0) 11f else 8.5f, 16.5f)
            close()
        }
    }
}

private fun chevronIcon(direction: Float, vertical: Boolean = false): ImageVector = vectorIcon {
    val points = ElegantIcons.chevronPolyline(direction, vertical, 24f)
    strokedPath(this, 2.2f) {
        moveTo(points[0].x, points[0].y)
        lineTo(points[1].x, points[1].y)
        lineTo(points[2].x, points[2].y)
    }
}

private fun checkIcon(): ImageVector = vectorIcon {
    val points = ElegantIcons.checkPolyline(24f)
    strokedPath(this, 2.6f) {
        moveTo(points[0].x, points[0].y)
        lineTo(points[1].x, points[1].y)
        lineTo(points[2].x, points[2].y)
    }
}

private fun closeIcon(): ImageVector = vectorIcon {
    val endpoints = ElegantIcons.closeEndpoints(24f, 0.25f)
    strokedPath(this, 2.2f) {
        moveTo(endpoints[0].x, endpoints[0].y)
        lineTo(endpoints[1].x, endpoints[1].y)
        moveTo(endpoints[2].x, endpoints[2].y)
        lineTo(endpoints[3].x, endpoints[3].y)
    }
}

private fun plusIcon(): ImageVector = vectorIcon {
    val endpoints = ElegantIcons.plusEndpoints(24f)
    strokedPath(this, 2.2f) {
        moveTo(endpoints[0].x, endpoints[0].y)
        lineTo(endpoints[1].x, endpoints[1].y)
        moveTo(endpoints[2].x, endpoints[2].y)
        lineTo(endpoints[3].x, endpoints[3].y)
    }
}

private fun minusIcon(): ImageVector = vectorIcon {
    strokedPath(this, 2.2f) {
        moveTo(4f, 12f)
        lineTo(20f, 12f)
    }
}

private fun searchIcon(): ImageVector = vectorIcon {
    strokedPath(this, 2.2f) {
        moveTo(15.4f, 10.4f)
        arcTo(
            horizontalEllipseRadius = 5.4f,
            verticalEllipseRadius = 5.4f,
            theta = 0f,
            isMoreThanHalf = false,
            isPositiveArc = true,
            x1 = 4.6f,
            y1 = 10.4f,
        )
        arcTo(
            horizontalEllipseRadius = 5.4f,
            verticalEllipseRadius = 5.4f,
            theta = 0f,
            isMoreThanHalf = false,
            isPositiveArc = true,
            x1 = 15.4f,
            y1 = 10.4f,
        )
        moveTo(14.2f, 14.2f)
        lineTo(20f, 20f)
    }
}

private fun editIcon(): ImageVector = vectorIcon {
    filledPath(this) {
        moveTo(16.6f, 3.4f)
        lineTo(20.6f, 7.4f)
        lineTo(6.5f, 21.5f)
        lineTo(2.5f, 21.5f)
        lineTo(2.5f, 17.5f)
        close()
    }
}

private fun deleteIcon(): ImageVector = vectorIcon {
    filledPath(this) {
        moveTo(4f, 7f)
        lineTo(20f, 7f)
        lineTo(20f, 9f)
        lineTo(4f, 9f)
        close()
    }
    filledPath(this) {
        moveTo(9.5f, 3.5f)
        lineTo(14.5f, 3.5f)
        lineTo(15.5f, 7f)
        lineTo(8.5f, 7f)
        close()
    }
    filledPath(this) {
        moveTo(6.5f, 10f)
        lineTo(17.5f, 10f)
        lineTo(16.5f, 20f)
        lineTo(7.5f, 20f)
        close()
    }
}

private fun shareIcon(): ImageVector = vectorIcon {
    strokedPath(this, 2f) {
        moveTo(7.5f, 12f)
        lineTo(16.5f, 6.5f)
        moveTo(7.5f, 12f)
        lineTo(16.5f, 17.5f)
    }
    fun dot(builder: androidx.compose.ui.graphics.vector.ImageVector.Builder, x: Float, y: Float) {
        filledPath(builder) {
            moveTo(x + 2f, y)
            arcTo(2f, 2f, 0f, false, true, x - 2f, y)
            arcTo(2f, 2f, 0f, false, true, x + 2f, y)
            close()
        }
    }
    dot(this, 6f, 12f)
    dot(this, 17.5f, 6f)
    dot(this, 17.5f, 17.5f)
}

private fun dotsIcon(vertical: Boolean): ImageVector = vectorIcon {
    fun dot(builder: androidx.compose.ui.graphics.vector.ImageVector.Builder, x: Float, y: Float) {
        filledPath(builder) {
            moveTo(x + 1.8f, y)
            arcTo(1.8f, 1.8f, 0f, false, true, x - 1.8f, y)
            arcTo(1.8f, 1.8f, 0f, false, true, x + 1.8f, y)
            close()
        }
    }
    if (vertical) {
        dot(this, 12f, 5.5f)
        dot(this, 12f, 12f)
        dot(this, 12f, 18.5f)
    } else {
        dot(this, 5.5f, 12f)
        dot(this, 12f, 12f)
        dot(this, 18.5f, 12f)
    }
}

private fun personIcon(): ImageVector = vectorIcon {
    val geometry = ElegantIcons.personGeometry(24f)
    filledPath(this) {
        moveTo(geometry.headCenter.x + geometry.headRadius, geometry.headCenter.y)
        arcTo(
            geometry.headRadius,
            geometry.headRadius,
            0f,
            false,
            true,
            geometry.headCenter.x - geometry.headRadius,
            geometry.headCenter.y,
        )
        arcTo(
            geometry.headRadius,
            geometry.headRadius,
            0f,
            false,
            true,
            geometry.headCenter.x + geometry.headRadius,
            geometry.headCenter.y,
        )
        close()
    }
    filledPath(this) {
        moveTo(geometry.shoulderLeft.x, geometry.shoulderLeft.y)
        curveTo(
            geometry.shoulderLeft.x + 1.2f,
            geometry.headCenter.y + geometry.headRadius * 1.2f,
            geometry.shoulderRight.x - 1.2f,
            geometry.headCenter.y + geometry.headRadius * 1.2f,
            geometry.shoulderRight.x,
            geometry.shoulderRight.y,
        )
        close()
    }
}

private fun homeIcon(): ImageVector = vectorIcon {
    filledPath(this) {
        moveTo(12f, 3f)
        lineTo(21f, 11f)
        lineTo(19.2f, 11f)
        lineTo(19.2f, 21f)
        lineTo(4.8f, 21f)
        lineTo(4.8f, 11f)
        lineTo(3f, 11f)
        close()
    }
}

private fun settingsIcon(): ImageVector = vectorIcon {
    strokedPath(this, 2.2f) {
        moveTo(18.5f, 12f)
        arcTo(6.5f, 6.5f, 0f, false, true, 5.5f, 12f)
        arcTo(6.5f, 6.5f, 0f, false, true, 18.5f, 12f)
        close()
    }
    strokedPath(this, 2.2f) {
        for (angle in intArrayOf(0, 45, 90, 135, 180, 225, 270, 315)) {
            val radians = kotlin.math.PI * angle / 180.0
            val cos = kotlin.math.cos(radians).toFloat()
            val sin = kotlin.math.sin(radians).toFloat()
            moveTo(12f + 8.5f * cos, 12f + 8.5f * sin)
            lineTo(12f + 10.5f * cos, 12f + 10.5f * sin)
        }
    }
}

private fun notificationsIcon(): ImageVector = vectorIcon {
    filledPath(this) {
        moveTo(4.6f, 17f)
        arcTo(7.4f, 6.4f, 0f, false, false, 19.4f, 17f)
        lineTo(19.4f, 18f)
        lineTo(4.6f, 18f)
        close()
    }
    filledPath(this) {
        moveTo(12f, 18.2f)
        arcTo(1.6f, 1.6f, 0f, false, true, 10.4f, 19.8f)
        arcTo(1.6f, 1.6f, 0f, false, true, 12f, 21.4f)
        arcTo(1.6f, 1.6f, 0f, false, true, 13.6f, 19.8f)
        arcTo(1.6f, 1.6f, 0f, false, true, 12f, 18.2f)
        close()
    }
}

private fun starIcon(): ImageVector = vectorIcon {
    val points = ElegantIcons.starPoints(Offset(12f, 12f), 9.5f, 3.9f)
    filledPath(this) {
        moveTo(points[0].x, points[0].y)
        for (index in 1 until points.size) {
            lineTo(points[index].x, points[index].y)
        }
        close()
    }
}

private fun heartIcon(): ImageVector = vectorIcon {
    filledPath(this) {
        moveTo(12f, 20.5f)
        curveTo(13f, 19.5f, 20f, 14.5f, 20f, 8.5f)
        curveTo(20f, 5.6f, 17.5f, 4f, 15f, 4f)
        curveTo(13.1f, 4f, 12f, 5.4f, 12f, 7f)
        curveTo(12f, 5.4f, 10.9f, 4f, 9f, 4f)
        curveTo(6.5f, 4f, 4f, 5.6f, 4f, 8.5f)
        curveTo(4f, 14.5f, 11f, 19.5f, 12f, 20.5f)
        close()
    }
}
