# Empty State

`ElegantEmptyState` is a non-interactive display component that communicates absence: empty inboxes, blank search results, and unstarted projects. It centers an optional icon, a required title, an optional description, and an optional action slot with theme-aware colors that adapt to Light and Dark.

<iframe id="demoIframe" style="width: 100%; height: 460px; border: 1px solid var(--vp-c-divider); border-radius: 12px; display: block; background: var(--vp-c-bg-alt);" src="../compose/index.html?id=empty-state" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>

## Import

```kotlin
import com.elegant.compose.ui.emptystate.ElegantEmptyState
import com.elegant.compose.ui.emptystate.ElegantEmptyStateColors
import com.elegant.compose.ui.emptystate.ElegantEmptyStateDefaults
```

## Basic Usage

The icon, title, and description stack in a centered column. The icon renders inside a 64dp circle, the title uses the `titleMedium` style, and the description uses `bodyMedium` with centered alignment. A blank description is omitted entirely.

```kotlin
ElegantEmptyState(
    icon = { Icon(Icons.Default.Inbox, contentDescription = null) },
    title = "No messages yet",
    description = "When a message arrives, it will appear here.",
)
```

## Component States

`ElegantEmptyState` has no hover, press, focus, or disabled state and adds no semantics of its own; the semantics of its content are preserved. The icon slot owns its own semantics: keep the icon's `contentDescription` null for a decorative icon, or supply a localized description when the icon carries meaning.

```kotlin
ElegantEmptyState(
    title = "Nothing here yet",
)
```

## Properties

### ElegantEmptyState Properties

| Property Name | Type | Description | Default Value | Required |
| --- | --- | --- | --- | --- |
| `icon` | `(@Composable () -> Unit)?` | Optional visual content inside the 64dp circular icon container | `null` | No |
| `modifier` | `Modifier` | Modifier applied once to the empty-state root | `Modifier` | No |
| `title` | `String` | Primary message shown above the description | - | Yes |
| `description` | `String?` | Supporting message under the title; blank values are omitted | `null` | No |
| `action` | `(@Composable () -> Unit)?` | Optional action slot below the description, separated by a wider gap | `null` | No |
| `colors` | `ElegantEmptyStateColors` | Theme-aware icon, title, and description colors | `ElegantEmptyStateDefaults.colors()` | No |
| `contentPadding` | `PaddingValues` | Padding around the whole layout | `PaddingValues(ElegantEmptyStateDefaults.DefaultPadding)` | No |

### ElegantEmptyStateColors

`ElegantEmptyStateColors` contains the icon container, icon content, title, and description colors. Start with `ElegantEmptyStateDefaults.colors()` and use `copy(...)` only for a deliberate product-specific palette.

| Property Name | Type | Description |
| --- | --- | --- |
| `iconContainerColor` | `Color` | Circular icon container background |
| `iconContentColor` | `Color` | Icon content color provided through `LocalContentColor` |
| `titleColor` | `Color` | Title text color |
| `descriptionColor` | `Color` | Description text color |

### ElegantEmptyStateDefaults

| Member | Type | Description |
| --- | --- | --- |
| `IconContainerSize` | `Dp` | 64dp circular icon container diameter |
| `ItemGap` | `Dp` | 8dp vertical gap between the icon, title, and description |
| `ActionGap` | `Dp` | 16dp vertical gap between the description and the action slot |
| `DefaultPadding` | `Dp` | 24dp default padding around the layout |
| `colors()` | `ElegantEmptyStateColors` | Theme-aware Light/Dark colors |

## Advanced Usage

### Guiding the Next Step

The action slot hosts a primary call to action separated from the description by a wider gap, keeping the composition focused on the recommended next move.

```kotlin
ElegantEmptyState(
    icon = { Icon(Icons.Default.Inbox, contentDescription = null) },
    title = "Inbox zero",
    description = "Relax — every conversation has been handled.",
    action = {
        ElegantButton(onClick = { /* compose a new message */ }) {
            Text("New message")
        }
    },
)
```

### Custom Colors

```kotlin
val colors = ElegantEmptyStateDefaults.colors().copy(
    iconContainerColor = Color(0x1A147D64),
    iconContentColor = Color(0xFF147D64),
)

ElegantEmptyState(
    icon = { Icon(Icons.Default.Check, contentDescription = null) },
    title = "All caught up",
    colors = colors,
)
```
