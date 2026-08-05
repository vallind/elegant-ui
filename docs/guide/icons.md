# Icons

`ElegantIcon` renders the built-in `ElegantIcons` vector set with theme-aware tinting. The built-in 42 icons are vector paths shipped with the library — no resource files, no platform assets — so they work identically on Android, Desktop JVM, and Web/Wasm. A further 145 glyphs load lazily and cache on first access.

<iframe id="demoIframe" style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; border: none;" src="../compose/index.html?id=icons" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>

## Import

```kotlin
import com.elegant.compose.ui.icon.ElegantIcon
import com.elegant.compose.ui.icon.ElegantIconDefaults
import com.elegant.compose.ui.foundation.icons.ElegantIcons
```

## Basic Usage

A null `contentDescription` makes the icon decorative; a non-null value labels it for accessibility.

```kotlin
ElegantIcon(
    icon = ElegantIcons.Search,
    contentDescription = "Search",
)
```

## Component States

Icons are non-interactive. Their color follows the ambient content color; pass `tint` for a specific role. The fill of the vector path is replaced at render time, so the same icon works in every theme and state color.

```kotlin
ElegantIcon(
    icon = ElegantIcons.Delete,
    contentDescription = "Delete",
    tint = ElegantTheme.colors.statusCritical,
)
```

## Properties

### ElegantIcon Properties

| Property Name | Type | Description | Default Value | Required |
| --- | --- | --- | --- | --- |
| `icon` | `ImageVector` | The vector icon to draw | - | Yes |
| `contentDescription` | `String?` | Localized accessibility label; null for decorative icons | - | Yes |
| `modifier` | `Modifier` | Modifier applied once to the icon root | `Modifier` | No |
| `tint` | `Color` | Icon color | `LocalContentColor.current` | No |

### ElegantIcons

`ElegantIcons` exposes 42 built-in icons: `ArrowLeft/Right/Up/Down`, `ChevronLeft/Right/Up/Down`, `Check`, `Close`, `Plus`, `Minus`, `Search`, `Edit`, `Delete`, `Share`, `MoreVert`, `MoreHoriz`, `Person`, `Home`, `Settings`, `Notifications`, `Star`, `Heart`, `Refresh`, `Download`, `VolumeUp`, `VolumeOff`, `Filter`, `Send`, `Reply`, `Forward`, `Lock`, `Unlock`, `Location`, `Image`, `Play`, `Pause`, `Info`, `Help`, `Grid`, `Copy`, plus `All` (the full list in declaration order).

### ElegantIconDefaults

| Member | Type | Description |
| --- | --- | --- |
| `Size` | `Dp` | Default 24dp icon edge length |

## Advanced Usage

### Inside a Button Slot

```kotlin
ElegantButton(
    onClick = {},
    leadingIcon = {
        ElegantIcon(icon = ElegantIcons.Plus, contentDescription = null)
    },
) {
    Text("Create")
}
```

### Themed Role Tinting

```kotlin
Row(horizontalArrangement = Arrangement.spacedBy(ElegantSpacing.md)) {
    ElegantIcon(
        icon = ElegantIcons.Home,
        contentDescription = "Home",
        tint = ElegantTheme.colors.textPrimary,
    )
    ElegantIcon(
        icon = ElegantIcons.Notifications,
        contentDescription = null,
        tint = ElegantTheme.colors.textSecondary,
    )
}
```

## Extended Icons

`ElegantIcons` also carries 145 lazily loaded glyphs, built and cached on first access: 14 hand-drawn action glyphs in the same 24dp stroke style as the built-in set, plus 131 replicas of the Miuix Regular icon geometry grouped by category in `ElegantIconsFiles`, `ElegantIconsArrows`, `ElegantIconsMedia`, `ElegantIconsSocial`, `ElegantIconsSystem`, `ElegantIconsStatus`, and `ElegantIconsCalendar`:

```kotlin
ElegantIcon(
    icon = ElegantIcons.Folder,
    contentDescription = "Folder",
)
```

The Miuix replicas mirror the reference Regular-weight geometry exactly — same viewport and path data — so the rendered glyphs match the source set pixel for pixel while keeping the `ElegantIcons.<Name>` naming, the 24dp layout size, and lazy caching. Unused glyphs cost nothing at startup.

Available file glyphs: AddFolder, Backup, ConvertFile, Create, Cut, File, FileDownloads, Folder, FolderFill, Import, Merge, MoveFile, Paste, Redo, Rename, Replace, TopDownloads, Undo, UploadCloud.

Available arrow glyphs: Back, ChevronBackward, ChevronForward, ExpandLess, ExpandMore, RotateLeft, ZoomOut.

Available media glyphs: Album, AppRecording, CallRecording, MapAlbum, Mic, MicSlash, Music, Notes, NotesFill, Photos, Playlist, Recording, RecordingTape, ScreenCapture, ScreenMirroring, Trim.

Available social glyphs: BankCards, Carrier, Community, Contacts, ContactsBook, ContactsCircle, Email, Messages, Phone, RemoveContact, ReplyAll.

Available system glyphs: Add, AddCircle, Background, Blocklist, Clear, Close2, CloudFill, Favorites, FavoritesFill, GridView, Hide, HorizontalSplit, Layers, Link, ListView, MindMap, More, MoreCircle, Ok, Pin, Recent, Remove, Scan, SearchDevice, SelectAll, Show, Sidebar, Sort, Tasks, Theme, Translate, Tune, Unpin, Update, VerticalSplit.

Available status glyphs: Alarm, Answer, Months, Promotions, Report, Reset, Stopwatch, Store, Timer, Weeks, WorldClock, Years.

Available calendar glyphs: Th1–Th31 (calendar day thumbnails).
