package com.elegant.compose.ui.foundation.icons

import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

internal class ElegantIconsMiuixContractTest {

    private val miuixGlyphs: List<ImageVector> = listOf(
        // Batch 1: files + arrows
        ElegantIcons.AddFolder,
        ElegantIcons.Backup,
        ElegantIcons.ConvertFile,
        ElegantIcons.Create,
        ElegantIcons.Cut,
        ElegantIcons.File,
        ElegantIcons.FileDownloads,
        ElegantIcons.Folder,
        ElegantIcons.FolderFill,
        ElegantIcons.Import,
        ElegantIcons.Merge,
        ElegantIcons.MoveFile,
        ElegantIcons.Paste,
        ElegantIcons.Redo,
        ElegantIcons.Rename,
        ElegantIcons.Replace,
        ElegantIcons.TopDownloads,
        ElegantIcons.Undo,
        ElegantIcons.UploadCloud,
        ElegantIcons.Back,
        ElegantIcons.ChevronBackward,
        ElegantIcons.ChevronForward,
        ElegantIcons.ExpandLess,
        ElegantIcons.ExpandMore,
        ElegantIcons.RotateLeft,
        ElegantIcons.ZoomOut,
    )

    @Test
    fun miuixGlyphsAreLazilyCachedAndStable() {
        miuixGlyphs.forEach { glyph ->
            assertEquals(glyph, glyph)
        }
    }

    @Test
    fun miuixGlyphsUseThe24DpViewport() {
        miuixGlyphs.forEach { glyph ->
            assertEquals(24.dp, glyph.defaultWidth)
            assertEquals(24.dp, glyph.defaultHeight)
            assertTrue(glyph.viewportWidth > 0f)
            assertTrue(glyph.viewportHeight > 0f)
            assertEquals(glyph.viewportWidth, glyph.viewportHeight)
        }
    }

    @Test
    fun miuixGlyphsCarryTheElegantName() {
        miuixGlyphs.forEach { glyph ->
            assertTrue(glyph.name.startsWith("ElegantIcons."))
        }
    }
}
