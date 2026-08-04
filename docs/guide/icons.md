# Icons

`ElegantIcon` renders the built-in `ElegantIcons` vector set with theme-aware tinting. The 24 icons are vector paths shipped with the library — no resource files, no platform assets — so they work identically on Android, Desktop JVM, and Web/Wasm.

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

`ElegantIcons` exposes 24 icons: `ArrowLeft/Right/Up/Down`, `ChevronLeft/Right/Up/Down`, `Check`, `Close`, `Plus`, `Minus`, `Search`, `Edit`, `Delete`, `Share`, `MoreVert`, `MoreHoriz`, `Person`, `Home`, `Settings`, `Notifications`, `Star`, `Heart`, plus `All` (the full list in declaration order).

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

`ElegantIcons` also carries an extended set of 32 lazily loaded glyphs for common actions and media, defined on the same 24dp stroke style and cached on first access:

```kotlin
ElegantIcon(
    imageVector = ElegantIcons.Refresh,
    contentDescription = "Refresh",
)
```

Available glyphs: Refresh, Download, Upload, VolumeUp, VolumeDown, VolumeOff, Filter, Save, Send, Reply, Forward, Lock, Unlock, Eye, EyeOff, Calendar, Clock, Location, Camera, Image, Play, Pause, Info, Warning, Help, List, Grid, Sun, Moon, Brightness, Copy, Power.
