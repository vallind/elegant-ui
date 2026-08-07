# List

`ElegantList` and `ElegantListItem` build accessible, theme-aware vertical rows for settings, navigation, and roster surfaces. `ElegantList` is a plain non-scrolling column container, and `ElegantListItem` is the row primitive with an optional leading slot, a two-line title block, an optional trailing slot, and an optional selection interaction.

<iframe id="demoIframe" style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; border: none;" src="../compose/index.html?id=list" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>

## Import

```kotlin
import com.elegant.compose.ui.list.ElegantList
import com.elegant.compose.ui.list.ElegantListItem
import com.elegant.compose.ui.list.ElegantListItemColors
import com.elegant.compose.ui.list.ElegantListItemDefaults
```

## Basic Usage

`ElegantList` arranges items vertically without adding spacing of its own, so each item owns its padding and the caller stays in control of row density. The list does not scroll; wrap it in `verticalScroll` or a lazy column when the content can overflow the viewport. An item without `onClick` is non-interactive: it shows the title, supporting text, and slots, keeps the semantics of its content, and supports no focus.

```kotlin
ElegantList {
    ElegantListItem(title = { Text("General") })
    ElegantListItem(
        title = { Text("Notifications") },
        supportingText = { Text("Badges, sounds, and summary") },
    )
    ElegantListItem(
        title = { Text("Account") },
        supportingText = { Text("Sign-in, privacy, and security") },
    )
}
```

## Component States

Passing `onClick` turns the item into a button-like row: it announces `Role.Button` with `selected` and `disabled`, keeps the 48dp minimum row height, shows a focus ring when the theme enables focus rings, and animates hover and press container feedback with a ripple. Selected items replace the resting transparent container with the accent-tinted container resolved from the active theme, which also applies to non-interactive items.

State precedence: disabled, pressed, selected, hovered, resting. Hover and press feedback apply only to interactive items, and the focus ring renders only for focused interactive items and only when the theme enables focus rings.

```kotlin
var selected by remember { mutableIntStateOf(0) }

ElegantList {
    ElegantListItem(
        title = { Text("Wi-Fi") },
        onClick = { selected = 0 },
        selected = selected == 0,
    )
    ElegantListItem(
        title = { Text("Bluetooth") },
        onClick = { selected = 1 },
        selected = selected == 1,
    )
    ElegantListItem(
        title = { Text("Airplane mode") },
        onClick = {},
        enabled = false,
    )
}
```

## Properties

### ElegantList Properties

| Property Name | Type | Description | Default Value | Required |
| --- | --- | --- | --- | --- |
| `modifier` | `Modifier` | Modifier applied once to the column root | `Modifier` | No |
| `content` | `@Composable () -> Unit` | Items rendered vertically; each item owns its padding | - | Yes |

### ElegantListItem Properties

| Property Name | Type | Description | Default Value | Required |
| --- | --- | --- | --- | --- |
| `leadingContent` | `(@Composable () -> Unit)?` | Optional content before the title, centered in a 20dp box | `null` | No |
| `modifier` | `Modifier` | Modifier applied once to the item root | `Modifier` | No |
| `title` | `@Composable () -> Unit` | Primary line, provided `labelLarge` typography | - | Yes |
| `supportingText` | `(@Composable () -> Unit)?` | Optional secondary line, provided `bodyMedium` typography | `null` | No |
| `trailingContent` | `(@Composable () -> Unit)?` | Optional content after the title block, centered in a 20dp box | `null` | No |
| `onClick` | `(() -> Unit)?` | Optional activation callback; null keeps the item non-interactive | `null` | No |
| `enabled` | `Boolean` | Whether user interaction is accepted | `true` | No |
| `selected` | `Boolean` | Whether the item communicates a chosen state | `false` | No |
| `colors` | `ElegantListItemColors` | Theme-aware state colors | `ElegantListItemDefaults.colors()` | No |
| `contentPadding` | `PaddingValues` | Inner padding around the row content | `PaddingValues(horizontal = 16.dp, vertical = 8.dp)` | No |

### ElegantListItemDefaults

| Member | Type | Description |
| --- | --- | --- |
| `MinimumTouchHeight` | `Dp` | 48dp minimum row height kept by every item |
| `AnimationDurationMillis` | `Int` | Standard 160ms state-transition duration |
| `colors()` | `ElegantListItemColors` | Theme-aware Light/Dark colors |

### ElegantListItemColors

`ElegantListItemColors` contains the transparent resting container, primary content, supporting text, and leading and trailing slot colors, plus hovered, pressed, disabled, and focus-ring overrides. Start with `ElegantListItemDefaults.colors()` and use `copy(...)` only for a deliberate product-specific hierarchy.

## Advanced Usage

### Team Roster Composition

Compose `ElegantAvatar`, a two-line title block, and an `ElegantIconButton` inside `ElegantListItem` rows to build a real roster surface; wrap the list in `verticalScroll` when the team grows.

```kotlin
Column(
    modifier = Modifier
        .fillMaxWidth()
        .heightIn(max = 320.dp)
        .verticalScroll(rememberScrollState()),
) {
    ElegantList {
        ElegantListItem(
            leadingContent = {
                ElegantAvatar(
                    name = "Maya Chen",
                    initials = "MC",
                    size = ElegantAvatarSize.Small,
                )
            },
            title = { Text("Maya Chen") },
            supportingText = { Text("Design systems lead") },
            trailingContent = {
                ElegantIconButton(
                    onClick = { /* open profile menu */ },
                    contentDescription = "More profile actions",
                ) {
                    Icon(Icons.Default.MoreVert, contentDescription = null)
                }
            },
            onClick = {},
        )
        ElegantListItem(
            leadingContent = {
                ElegantAvatar(
                    name = "Noah Williams",
                    initials = "NW",
                    size = ElegantAvatarSize.Small,
                )
            },
            title = { Text("Noah Williams") },
            supportingText = { Text("Multiplatform engineering") },
            onClick = {},
        )
    }
}
```
