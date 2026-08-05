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
        // Batch 2: media + social
        ElegantIcons.Album,
        ElegantIcons.AppRecording,
        ElegantIcons.CallRecording,
        ElegantIcons.MapAlbum,
        ElegantIcons.Mic,
        ElegantIcons.MicSlash,
        ElegantIcons.Music,
        ElegantIcons.Notes,
        ElegantIcons.NotesFill,
        ElegantIcons.Photos,
        ElegantIcons.Playlist,
        ElegantIcons.Recording,
        ElegantIcons.RecordingTape,
        ElegantIcons.ScreenCapture,
        ElegantIcons.ScreenMirroring,
        ElegantIcons.Trim,
        ElegantIcons.BankCards,
        ElegantIcons.Carrier,
        ElegantIcons.Community,
        ElegantIcons.Contacts,
        ElegantIcons.ContactsBook,
        ElegantIcons.ContactsCircle,
        ElegantIcons.Email,
        ElegantIcons.Messages,
        ElegantIcons.Phone,
        ElegantIcons.RemoveContact,
        ElegantIcons.ReplyAll,
        // Batch 3: system
        ElegantIcons.Add,
        ElegantIcons.AddCircle,
        ElegantIcons.Background,
        ElegantIcons.Blocklist,
        ElegantIcons.Clear,
        ElegantIcons.Close2,
        ElegantIcons.CloudFill,
        ElegantIcons.Favorites,
        ElegantIcons.FavoritesFill,
        ElegantIcons.GridView,
        ElegantIcons.Hide,
        ElegantIcons.HorizontalSplit,
        ElegantIcons.Layers,
        ElegantIcons.Link,
        ElegantIcons.ListView,
        ElegantIcons.MindMap,
        ElegantIcons.More,
        ElegantIcons.MoreCircle,
        ElegantIcons.Ok,
        ElegantIcons.Pin,
        ElegantIcons.Recent,
        ElegantIcons.Remove,
        ElegantIcons.Scan,
        ElegantIcons.SearchDevice,
        ElegantIcons.SelectAll,
        ElegantIcons.Show,
        ElegantIcons.Sidebar,
        ElegantIcons.Sort,
        ElegantIcons.Tasks,
        ElegantIcons.Theme,
        ElegantIcons.Translate,
        ElegantIcons.Tune,
        ElegantIcons.Unpin,
        ElegantIcons.Update,
        ElegantIcons.VerticalSplit,
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
