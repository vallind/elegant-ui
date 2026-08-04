// Copyright 2026, elegant-ui contributors
// SPDX-License-Identifier: Apache-2.0

package com.elegant.compose.ui.icon

import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.PathBuilder
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

/**
 * Extended Elegant UI vector icons rendered in the same 24dp viewport stroke style as
 * [ElegantIcons]. Icons are defined lazily and cached on first access, mirroring the pattern used
 * by reference icon sets, so unused glyphs cost nothing at startup.
 */
private var refreshIconCache: ImageVector? = null

/** Circular refresh arrow. */
public val ElegantIcons.Refresh: ImageVector
    get() {
        refreshIconCache?.let { return it }
        return strokeIcon("ElegantIcons.Refresh") {
            moveTo(19f, 12f)
            curveTo(19.5f, 7.86f, 16.14f, 4.5f, 12f, 4.5f)
            curveTo(7.86f, 4.5f, 4.5f, 7.86f, 4.5f, 12f)
            curveTo(4.5f, 16.14f, 7.86f, 19.5f, 12f, 19.5f)
            moveTo(19.5f, 12f)
            lineTo(16.5f, 9.5f)
            moveTo(19.5f, 12f)
            lineTo(16.5f, 14.5f)
            moveTo(4.5f, 12f)
            lineTo(7.5f, 9f)
            moveTo(4.5f, 12f)
            lineTo(7.5f, 15f)
        }.also { refreshIconCache = it }
    }

private var downloadIconCache: ImageVector? = null

/** Download arrow above a tray. */
public val ElegantIcons.Download: ImageVector
    get() {
        downloadIconCache?.let { return it }
        return strokeIcon("ElegantIcons.Download") {
            moveTo(12f, 4f)
            lineTo(12f, 14f)
            moveTo(8f, 10.5f)
            lineTo(12f, 14f)
            lineTo(16f, 10.5f)
            moveTo(4.5f, 18.5f)
            lineTo(19.5f, 18.5f)
        }.also { downloadIconCache = it }
    }

private var uploadIconCache: ImageVector? = null

/** Upload arrow above a tray. */
public val ElegantIcons.Upload: ImageVector
    get() {
        uploadIconCache?.let { return it }
        return strokeIcon("ElegantIcons.Upload") {
            moveTo(12f, 19f)
            lineTo(12f, 8f)
            moveTo(8f, 11.5f)
            lineTo(12f, 7.5f)
            lineTo(16f, 11.5f)
            moveTo(4.5f, 18.5f)
            lineTo(19.5f, 18.5f)
        }.also { uploadIconCache = it }
    }

private var volumeUpIconCache: ImageVector? = null

/** Speaker with a large sound wave. */
public val ElegantIcons.VolumeUp: ImageVector
    get() {
        volumeUpIconCache?.let { return it }
        return strokeIcon("ElegantIcons.VolumeUp") {
            moveTo(4.5f, 9.5f)
            lineTo(9f, 9.5f)
            lineTo(13f, 6f)
            lineTo(13f, 18f)
            lineTo(9f, 14.5f)
            lineTo(4.5f, 14.5f)
            close()
            moveTo(16.5f, 9.5f)
            quadTo(19f, 12f, 16.5f, 14.5f)
            moveTo(19.5f, 7.5f)
            quadTo(23f, 12f, 19.5f, 16.5f)
        }.also { volumeUpIconCache = it }
    }

private var volumeDownIconCache: ImageVector? = null

/** Speaker with a small sound wave. */
public val ElegantIcons.VolumeDown: ImageVector
    get() {
        volumeDownIconCache?.let { return it }
        return strokeIcon("ElegantIcons.VolumeDown") {
            moveTo(4.5f, 9.5f)
            lineTo(9f, 9.5f)
            lineTo(13f, 6f)
            lineTo(13f, 18f)
            lineTo(9f, 14.5f)
            lineTo(4.5f, 14.5f)
            close()
            moveTo(16.5f, 9.5f)
            quadTo(18.5f, 12f, 16.5f, 14.5f)
        }.also { volumeDownIconCache = it }
    }

private var volumeOffIconCache: ImageVector? = null

/** Muted speaker with a cross. */
public val ElegantIcons.VolumeOff: ImageVector
    get() {
        volumeOffIconCache?.let { return it }
        return strokeIcon("ElegantIcons.VolumeOff") {
            moveTo(4.5f, 9.5f)
            lineTo(9f, 9.5f)
            lineTo(13f, 6f)
            lineTo(13f, 18f)
            lineTo(9f, 14.5f)
            lineTo(4.5f, 14.5f)
            close()
            moveTo(17f, 9f)
            lineTo(21.5f, 15f)
            moveTo(21.5f, 9f)
            lineTo(17f, 15f)
        }.also { volumeOffIconCache = it }
    }

private var filterIconCache: ImageVector? = null

/** Funnel filter. */
public val ElegantIcons.Filter: ImageVector
    get() {
        filterIconCache?.let { return it }
        return strokeIcon("ElegantIcons.Filter") {
            moveTo(4f, 5f)
            lineTo(20f, 5f)
            moveTo(9f, 5f)
            lineTo(12.5f, 12f)
            moveTo(15f, 5f)
            lineTo(12.5f, 12f)
            moveTo(12.5f, 12f)
            lineTo(12.5f, 19f)
        }.also { filterIconCache = it }
    }

private var saveIconCache: ImageVector? = null

/** Floppy disk save. */
public val ElegantIcons.Save: ImageVector
    get() {
        saveIconCache?.let { return it }
        return strokeIcon("ElegantIcons.Save") {
            moveTo(4.5f, 5.5f)
            lineTo(4.5f, 18.5f)
            lineTo(19.5f, 18.5f)
            lineTo(19.5f, 5.5f)
            close()
            moveTo(7f, 5.5f)
            lineTo(7f, 10f)
            lineTo(17f, 10f)
            lineTo(17f, 5.5f)
            moveTo(9f, 13f)
            lineTo(15f, 13f)
            lineTo(15f, 18.5f)
        }.also { saveIconCache = it }
    }

private var sendIconCache: ImageVector? = null

/** Paper plane send. */
public val ElegantIcons.Send: ImageVector
    get() {
        sendIconCache?.let { return it }
        return strokeIcon("ElegantIcons.Send") {
            moveTo(4f, 5f)
            lineTo(20f, 12f)
            lineTo(4f, 19f)
            lineTo(7.5f, 12f)
            lineTo(4f, 5f)
            close()
        }.also { sendIconCache = it }
    }

private var replyIconCache: ImageVector? = null

/** Reply arrow pointing back. */
public val ElegantIcons.Reply: ImageVector
    get() {
        replyIconCache?.let { return it }
        return strokeIcon("ElegantIcons.Reply") {
            moveTo(19.5f, 17.5f)
            lineTo(19.5f, 11f)
            lineTo(11f, 11f)
            moveTo(15f, 14.5f)
            lineTo(11f, 11f)
            lineTo(15f, 7.5f)
        }.also { replyIconCache = it }
    }

private var forwardIconCache: ImageVector? = null

/** Forward arrow. */
public val ElegantIcons.Forward: ImageVector
    get() {
        forwardIconCache?.let { return it }
        return strokeIcon("ElegantIcons.Forward") {
            moveTo(4.5f, 17.5f)
            lineTo(4.5f, 11f)
            lineTo(13f, 11f)
            moveTo(9f, 14.5f)
            lineTo(13f, 11f)
            lineTo(9f, 7.5f)
        }.also { forwardIconCache = it }
    }

private var lockIconCache: ImageVector? = null

/** Locked padlock. */
public val ElegantIcons.Lock: ImageVector
    get() {
        lockIconCache?.let { return it }
        return strokeIcon("ElegantIcons.Lock") {
            moveTo(9f, 11f)
            lineTo(9f, 7.5f)
            quadTo(9f, 4.5f, 12f, 4.5f)
            quadTo(15f, 4.5f, 15f, 7.5f)
            lineTo(15f, 11f)
            moveTo(6.5f, 11f)
            lineTo(6.5f, 19.5f)
            lineTo(17.5f, 19.5f)
            lineTo(17.5f, 11f)
            close()
        }.also { lockIconCache = it }
    }

private var unlockIconCache: ImageVector? = null

/** Unlocked padlock with an open shackle. */
public val ElegantIcons.Unlock: ImageVector
    get() {
        unlockIconCache?.let { return it }
        return strokeIcon("ElegantIcons.Unlock") {
            moveTo(9f, 11f)
            lineTo(9f, 7.5f)
            quadTo(9f, 4.5f, 12f, 4.5f)
            quadTo(14f, 4.5f, 14.5f, 6.5f)
            moveTo(6.5f, 11f)
            lineTo(6.5f, 19.5f)
            lineTo(17.5f, 19.5f)
            lineTo(17.5f, 11f)
            close()
        }.also { unlockIconCache = it }
    }

private var eyeIconCache: ImageVector? = null

/** Open eye. */
public val ElegantIcons.Eye: ImageVector
    get() {
        eyeIconCache?.let { return it }
        return strokeIcon("ElegantIcons.Eye") {
            moveTo(4f, 12f)
            quadTo(8f, 6.5f, 12f, 6.5f)
            quadTo(16f, 6.5f, 20f, 12f)
            quadTo(16f, 17.5f, 12f, 17.5f)
            quadTo(8f, 17.5f, 4f, 12f)
            close()
            circle(12f, 12f, 2.5f)
        }.also { eyeIconCache = it }
    }

private var eyeOffIconCache: ImageVector? = null

/** Eye struck through for hidden content. */
public val ElegantIcons.EyeOff: ImageVector
    get() {
        eyeOffIconCache?.let { return it }
        return strokeIcon("ElegantIcons.EyeOff") {
            moveTo(4f, 12f)
            quadTo(8f, 6.5f, 12f, 6.5f)
            quadTo(16f, 6.5f, 20f, 12f)
            quadTo(16f, 17.5f, 12f, 17.5f)
            quadTo(8f, 17.5f, 4f, 12f)
            close()
            moveTo(5.5f, 5.5f)
            lineTo(18.5f, 18.5f)
        }.also { eyeOffIconCache = it }
    }

private var calendarIconCache: ImageVector? = null

/** Calendar with a header and day grid. */
public val ElegantIcons.Calendar: ImageVector
    get() {
        calendarIconCache?.let { return it }
        return strokeIcon("ElegantIcons.Calendar") {
            moveTo(5f, 5.5f)
            lineTo(5f, 19f)
            lineTo(19f, 19f)
            lineTo(19f, 5.5f)
            close()
            moveTo(5f, 9f)
            lineTo(19f, 9f)
            moveTo(8f, 3.5f)
            lineTo(8f, 7.5f)
            moveTo(16f, 3.5f)
            lineTo(16f, 7.5f)
            moveTo(10.5f, 9f)
            lineTo(10.5f, 19f)
            moveTo(14.5f, 9f)
            lineTo(14.5f, 19f)
        }.also { calendarIconCache = it }
    }

private var clockIconCache: ImageVector? = null

/** Clock with hour and minute hands. */
public val ElegantIcons.Clock: ImageVector
    get() {
        clockIconCache?.let { return it }
        return strokeIcon("ElegantIcons.Clock") {
            circle(12f, 12f, 7.5f)
            moveTo(12f, 12f)
            lineTo(12f, 7.5f)
            moveTo(12f, 12f)
            lineTo(15.5f, 13.5f)
        }.also { clockIconCache = it }
    }

private var locationIconCache: ImageVector? = null

/** Location pin. */
public val ElegantIcons.Location: ImageVector
    get() {
        locationIconCache?.let { return it }
        return strokeIcon("ElegantIcons.Location") {
            circle(12f, 8.5f, 4f)
            moveTo(12f, 12.5f)
            lineTo(12f, 19.5f)
        }.also { locationIconCache = it }
    }

private var cameraIconCache: ImageVector? = null

/** Camera body with a lens. */
public val ElegantIcons.Camera: ImageVector
    get() {
        cameraIconCache?.let { return it }
        return strokeIcon("ElegantIcons.Camera") {
            moveTo(9f, 7f)
            lineTo(10.5f, 4.5f)
            lineTo(13.5f, 4.5f)
            lineTo(15f, 7f)
            moveTo(4.5f, 7f)
            lineTo(4.5f, 18.5f)
            lineTo(19.5f, 18.5f)
            lineTo(19.5f, 7f)
            close()
            circle(12f, 12.5f, 2.5f)
        }.also { cameraIconCache = it }
    }

private var imageIconCache: ImageVector? = null

/** Picture frame with a landscape. */
public val ElegantIcons.Image: ImageVector
    get() {
        imageIconCache?.let { return it }
        return strokeIcon("ElegantIcons.Image") {
            moveTo(4.5f, 5f)
            lineTo(4.5f, 19f)
            lineTo(19.5f, 19f)
            lineTo(19.5f, 5f)
            close()
            moveTo(5f, 18.5f)
            lineTo(10.5f, 12.5f)
            lineTo(14f, 16f)
            lineTo(18.5f, 10.5f)
            circle(16.5f, 8.5f, 1.5f)
        }.also { imageIconCache = it }
    }

private var playIconCache: ImageVector? = null

/** Play triangle. */
public val ElegantIcons.Play: ImageVector
    get() {
        playIconCache?.let { return it }
        return strokeIcon("ElegantIcons.Play") {
            moveTo(8.5f, 6f)
            lineTo(19f, 12f)
            lineTo(8.5f, 18f)
            close()
        }.also { playIconCache = it }
    }

private var pauseIconCache: ImageVector? = null

/** Pause bars. */
public val ElegantIcons.Pause: ImageVector
    get() {
        pauseIconCache?.let { return it }
        return strokeIcon("ElegantIcons.Pause") {
            moveTo(8.5f, 6f)
            lineTo(8.5f, 18f)
            moveTo(15.5f, 6f)
            lineTo(15.5f, 18f)
        }.also { pauseIconCache = it }
    }

private var infoIconCache: ImageVector? = null

/** Info circle with an i. */
public val ElegantIcons.Info: ImageVector
    get() {
        infoIconCache?.let { return it }
        return strokeIcon("ElegantIcons.Info") {
            circle(12f, 12f, 7.5f)
            moveTo(12f, 11f)
            lineTo(12f, 16.5f)
            circle(12f, 7.5f, 1f)
        }.also { infoIconCache = it }
    }

private var warningIconCache: ImageVector? = null

/** Warning triangle with an exclamation. */
public val ElegantIcons.Warning: ImageVector
    get() {
        warningIconCache?.let { return it }
        return strokeIcon("ElegantIcons.Warning") {
            moveTo(12f, 5f)
            lineTo(21f, 19.5f)
            lineTo(3f, 19.5f)
            close()
            moveTo(12f, 10.5f)
            lineTo(12f, 15.5f)
            circle(12f, 17.5f, 1f)
        }.also { warningIconCache = it }
    }

private var helpIconCache: ImageVector? = null

/** Help circle with a question mark. */
public val ElegantIcons.Help: ImageVector
    get() {
        helpIconCache?.let { return it }
        return strokeIcon("ElegantIcons.Help") {
            circle(12f, 12f, 7.5f)
            moveTo(9.8f, 9.5f)
            quadTo(9.8f, 7f, 12f, 7f)
            quadTo(14.2f, 7f, 14.2f, 9.3f)
            quadTo(14.2f, 11.5f, 12f, 12.5f)
            lineTo(12f, 15f)
            circle(12f, 17f, 1f)
        }.also { helpIconCache = it }
    }

private var listIconCache: ImageVector? = null

/** Bulleted list rows. */
public val ElegantIcons.List: ImageVector
    get() {
        listIconCache?.let { return it }
        return strokeIcon("ElegantIcons.List") {
            moveTo(8f, 6f)
            lineTo(19f, 6f)
            moveTo(8f, 12f)
            lineTo(19f, 12f)
            moveTo(8f, 18f)
            lineTo(19f, 18f)
            circle(5.5f, 5.5f, 1f)
            circle(5.5f, 11.5f, 1f)
            circle(5.5f, 17.5f, 1f)
        }.also { listIconCache = it }
    }

private var gridIconCache: ImageVector? = null

/** Grid of four cells. */
public val ElegantIcons.Grid: ImageVector
    get() {
        gridIconCache?.let { return it }
        return strokeIcon("ElegantIcons.Grid") {
            moveTo(5f, 5f)
            lineTo(5f, 19f)
            lineTo(19f, 19f)
            lineTo(19f, 5f)
            close()
            moveTo(12f, 5f)
            lineTo(12f, 19f)
            moveTo(5f, 12f)
            lineTo(19f, 12f)
        }.also { gridIconCache = it }
    }

private var sunIconCache: ImageVector? = null

/** Sun with rays. */
public val ElegantIcons.Sun: ImageVector
    get() {
        sunIconCache?.let { return it }
        return strokeIcon("ElegantIcons.Sun") {
            circle(12f, 12f, 3.5f)
            moveTo(12f, 4f)
            lineTo(12f, 6.5f)
            moveTo(12f, 17.5f)
            lineTo(12f, 20f)
            moveTo(4f, 12f)
            lineTo(6.5f, 12f)
            moveTo(17.5f, 12f)
            lineTo(20f, 12f)
            moveTo(6.3f, 6.3f)
            lineTo(8.2f, 8.2f)
            moveTo(15.8f, 15.8f)
            lineTo(17.7f, 17.7f)
            moveTo(6.3f, 17.7f)
            lineTo(8.2f, 15.8f)
            moveTo(15.8f, 8.2f)
            lineTo(17.7f, 6.3f)
        }.also { sunIconCache = it }
    }

private var moonIconCache: ImageVector? = null

/** Crescent moon. */
public val ElegantIcons.Moon: ImageVector
    get() {
        moonIconCache?.let { return it }
        return strokeIcon("ElegantIcons.Moon") {
            moveTo(18.5f, 13.5f)
            quadTo(18f, 7.5f, 12f, 6.5f)
            quadTo(17.5f, 11f, 15.5f, 17.5f)
        }.also { moonIconCache = it }
    }

private var brightnessIconCache: ImageVector? = null

/** Brightness half-disc. */
public val ElegantIcons.Brightness: ImageVector
    get() {
        brightnessIconCache?.let { return it }
        return strokeIcon("ElegantIcons.Brightness") {
            moveTo(12f, 5.5f)
            curveTo(15.59f, 5.5f, 18.5f, 8.41f, 18.5f, 12f)
            curveTo(18.5f, 15.59f, 15.59f, 18.5f, 12f, 18.5f)
            lineTo(12f, 5.5f)
            moveTo(5.5f, 12f)
            lineTo(18.5f, 12f)
        }.also { brightnessIconCache = it }
    }

private var copyIconCache: ImageVector? = null

/** Two overlapping sheets. */
public val ElegantIcons.Copy: ImageVector
    get() {
        copyIconCache?.let { return it }
        return strokeIcon("ElegantIcons.Copy") {
            moveTo(9.5f, 9.5f)
            lineTo(9.5f, 5.5f)
            lineTo(18.5f, 5.5f)
            lineTo(18.5f, 14.5f)
            lineTo(14.5f, 14.5f)
            moveTo(5.5f, 9.5f)
            lineTo(5.5f, 18.5f)
            lineTo(14.5f, 18.5f)
            lineTo(14.5f, 9.5f)
            close()
        }.also { copyIconCache = it }
    }

private var powerIconCache: ImageVector? = null

/** Power switch. */
public val ElegantIcons.Power: ImageVector
    get() {
        powerIconCache?.let { return it }
        return strokeIcon("ElegantIcons.Power") {
            moveTo(12f, 4.5f)
            lineTo(12f, 9.5f)
            circle(12f, 12f, 7.5f)
        }.also { powerIconCache = it }
    }

private fun PathBuilder.circle(cx: Float, cy: Float, r: Float) {
    val k = 0.5523f * r
    moveTo(cx - r, cy)
    curveTo(cx - r, cy - k, cx - k, cy - r, cx, cy - r)
    curveTo(cx + k, cy - r, cx + r, cy - k, cx + r, cy)
    curveTo(cx + r, cy + k, cx + k, cy + r, cx, cy + r)
    curveTo(cx - k, cy + r, cx - r, cy + k, cx - r, cy)
}

private fun strokeIcon(name: String, block: PathBuilder.() -> Unit): ImageVector =
    ImageVector.Builder(
        name = name,
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f,
    ).apply {
        path(
            fill = null,
            stroke = SolidColor(Color.Black),
            strokeLineWidth = 2f,
            strokeLineCap = StrokeCap.Round,
            strokeLineJoin = StrokeJoin.Round,
        ) {
            block()
        }
    }.build()
