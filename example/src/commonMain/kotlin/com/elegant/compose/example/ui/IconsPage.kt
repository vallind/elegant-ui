// Copyright 2026, elegant-ui contributors
// SPDX-License-Identifier: Apache-2.0

package com.elegant.compose.example.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.elegant.compose.ui.foundation.icons.*
import com.elegant.compose.ui.foundation.theme.ElegantSpacing
import com.elegant.compose.ui.foundation.theme.ElegantTheme
import com.elegant.compose.ui.smalltitle.ElegantSmallTitle

/**
 * Icon roster tab: every built-in basic glyph plus the full extended set, grouped by family and
 * rendered as a name-tagged grid mirroring the reference example's icon page.
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
internal fun IconsPage() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = ElegantSpacing.lg),
    ) {
        Spacer(modifier = Modifier.height(ElegantSpacing.xl))
        Text(
            text = "Elegant Icons",
            style = ElegantTheme.typography.titleMedium,
        )
        Spacer(modifier = Modifier.height(ElegantSpacing.xs))
        Text(
            text = "The built-in vector set: 24 basic glyphs plus the extended families",
            style = ElegantTheme.typography.bodyMedium,
            color = ElegantTheme.colors.textSecondary,
        )
        Spacer(modifier = Modifier.height(ElegantSpacing.lg))
        for ((group, icons) in IconGroups) {
            ElegantSmallTitle(text = group)
            Spacer(modifier = Modifier.height(ElegantSpacing.sm))
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(ElegantSpacing.md),
                verticalArrangement = Arrangement.spacedBy(ElegantSpacing.md),
            ) {
                for (icon in icons) {
                    IconCell(icon = icon)
                }
            }
            Spacer(modifier = Modifier.height(ElegantSpacing.xl))
        }
    }
}

/** One icon tile: the glyph above its [ImageVector.name]. */
@Composable
private fun IconCell(icon: ImageVector) {
    Column(
        modifier = Modifier.size(width = 72.dp, height = 72.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        androidx.compose.material3.Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(24.dp),
            tint = ElegantTheme.colors.textPrimary,
        )
        Spacer(modifier = Modifier.height(ElegantSpacing.xs))
        Text(
            text = icon.name,
            style = ElegantTheme.typography.labelSmall,
            color = ElegantTheme.colors.textSecondary,
            textAlign = TextAlign.Center,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

/** Icon families shown on the roster: the basic set first, then each extended family. */
private val IconGroups: List<Pair<String, List<ImageVector>>> = listOf(
    "Basic" to ElegantIcons.All,
    "Arrows" to listOf(
        ElegantIcons.Back,
        ElegantIcons.ChevronBackward,
        ElegantIcons.ChevronForward,
        ElegantIcons.ExpandLess,
        ElegantIcons.ExpandMore,
        ElegantIcons.RotateLeft,
        ElegantIcons.ZoomOut,
    ),
    "Common" to listOf(
        ElegantIcons.Upload,
        ElegantIcons.VolumeDown,
        ElegantIcons.Save,
        ElegantIcons.Eye,
        ElegantIcons.EyeOff,
        ElegantIcons.Calendar,
        ElegantIcons.Clock,
        ElegantIcons.Camera,
        ElegantIcons.Warning,
        ElegantIcons.List,
        ElegantIcons.Sun,
        ElegantIcons.Moon,
        ElegantIcons.Brightness,
        ElegantIcons.Power,
    ),
    "Files" to listOf(
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
    ),
    "Status" to listOf(
        ElegantIcons.Alarm,
        ElegantIcons.Answer,
        ElegantIcons.Months,
        ElegantIcons.Promotions,
        ElegantIcons.Report,
        ElegantIcons.Reset,
        ElegantIcons.Stopwatch,
        ElegantIcons.Store,
        ElegantIcons.Timer,
        ElegantIcons.Weeks,
        ElegantIcons.WorldClock,
        ElegantIcons.Years,
    ),
    "Media" to listOf(
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
    ),
    "Social" to listOf(
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
    ),
    "System" to listOf(
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
    ),
    "Calendar" to listOf(
        ElegantIcons.Th1,
        ElegantIcons.Th2,
        ElegantIcons.Th3,
        ElegantIcons.Th4,
        ElegantIcons.Th5,
        ElegantIcons.Th6,
        ElegantIcons.Th7,
        ElegantIcons.Th8,
        ElegantIcons.Th9,
        ElegantIcons.Th10,
        ElegantIcons.Th11,
        ElegantIcons.Th12,
        ElegantIcons.Th13,
        ElegantIcons.Th14,
        ElegantIcons.Th15,
        ElegantIcons.Th16,
        ElegantIcons.Th17,
        ElegantIcons.Th18,
        ElegantIcons.Th19,
        ElegantIcons.Th20,
        ElegantIcons.Th21,
        ElegantIcons.Th22,
        ElegantIcons.Th23,
        ElegantIcons.Th24,
        ElegantIcons.Th25,
        ElegantIcons.Th26,
        ElegantIcons.Th27,
        ElegantIcons.Th28,
        ElegantIcons.Th29,
        ElegantIcons.Th30,
        ElegantIcons.Th31,
    ),
)
