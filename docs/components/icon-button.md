# IconButton

`ElegantIconButton` is a compact cross-platform action for toolbars, cards, and dense controls. It combines a required accessible name with three emphasis levels, optically tuned icon sizes, pointer hover, touch press, keyboard focus, and a geometry-stable loading state.

<iframe id="demoIframe" style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; border: none;" src="../compose/index.html?id=icon-button" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>

## Import

```kotlin
import com.elegant.compose.ui.iconbutton.ElegantIconButton
import com.elegant.compose.ui.iconbutton.ElegantIconButtonColors
import com.elegant.compose.ui.iconbutton.ElegantIconButtonDefaults
import com.elegant.compose.ui.iconbutton.ElegantIconButtonElevation
import com.elegant.compose.ui.iconbutton.ElegantIconButtonSize
import com.elegant.compose.ui.iconbutton.ElegantIconButtonStyle
```

## Basic Usage

Give the icon button a short action-oriented `contentDescription`. The icon itself should remain decorative because the component owns the accessible name.

```kotlin
ElegantIconButton(
    onClick = { /* Edit the item */ },
    contentDescription = "Edit item",
) {
    Icon(
        imageVector = Icons.Default.Edit,
        contentDescription = null,
    )
}
```

## IconButton Types

Use `Primary` for the dominant compact action, `Secondary` for a contained supporting action, and the default `Tertiary` style for quiet toolbar actions.

```kotlin
Row {
    ElegantIconButton(
        onClick = { /* Save */ },
        contentDescription = "Save",
        style = ElegantIconButtonStyle.Primary,
    ) {
        Icon(Icons.Default.Check, contentDescription = null)
    }
    ElegantIconButton(
        onClick = { /* Share */ },
        contentDescription = "Share",
        style = ElegantIconButtonStyle.Secondary,
    ) {
        Icon(Icons.Default.Share, contentDescription = null)
    }
    ElegantIconButton(
        onClick = { /* More */ },
        contentDescription = "More options",
    ) {
        Icon(Icons.Default.MoreVert, contentDescription = null)
    }
}
```

## Component States

Hover, press, and keyboard focus are resolved from one interaction source. Loading disables activation, preserves the visual container, replaces the icon with progress, and exposes `loadingStateDescription`.

```kotlin
ElegantIconButton(
    onClick = { /* Retry */ },
    contentDescription = "Retry",
    loading = isRetrying,
    loadingStateDescription = "Retrying",
    style = ElegantIconButtonStyle.Primary,
) {
    Icon(Icons.Default.Refresh, contentDescription = null)
}

ElegantIconButton(
    onClick = {},
    contentDescription = "Delete item",
    enabled = false,
) {
    Icon(Icons.Default.Delete, contentDescription = null)
}
```

## Properties

### ElegantIconButton Properties

| Property Name | Type | Description | Default Value | Required |
| --- | --- | --- | --- | --- |
| `onClick` | `() -> Unit` | Callback invoked when the action accepts activation | - | Yes |
| `contentDescription` | `String` | Localized accessible name describing the action | - | Yes |
| `modifier` | `Modifier` | Modifier applied to the 48dp minimum interaction root | `Modifier` | No |
| `enabled` | `Boolean` | Whether the action accepts interaction | `true` | No |
| `loading` | `Boolean` | Replaces the icon with progress and prevents duplicate activation | `false` | No |
| `loadingStateDescription` | `String` | Localized accessibility state announced while loading | `"Loading"` | No |
| `interactionSource` | `MutableInteractionSource?` | Optional hoisted source for observing hover, press, and focus | `null` | No |
| `style` | `ElegantIconButtonStyle` | Visual emphasis of the compact action | `ElegantIconButtonStyle.Tertiary` | No |
| `size` | `ElegantIconButtonSize` | Visual container and icon size preset | `ElegantIconButtonSize.Medium` | No |
| `shape` | `Shape` | Optically tuned container shape | `ElegantIconButtonDefaults.shape(size)` | No |
| `colors` | `ElegantIconButtonColors` | Theme-aware interaction colors and border metrics | `ElegantIconButtonDefaults.colors(style)` | No |
| `elevation` | `ElegantIconButtonElevation` | State-aware tonal elevation model | `ElegantIconButtonDefaults.elevation(style)` | No |
| `content` | `@Composable () -> Unit` | Decorative icon content | - | Yes |

### ElegantIconButtonStyle Values

| Value | Description |
| --- | --- |
| `Primary` | Highest-emphasis compact action with a primary container |
| `Secondary` | Supporting action with a raised container and border |
| `Tertiary` | Quiet action with a transparent resting container |

### ElegantIconButtonSize Values

| Value | Visual Container | Minimum Touch Size | Icon Size |
| --- | --- | --- | --- |
| `Small` | `32.dp` | `48.dp` | `16.dp` |
| `Medium` | `40.dp` | `48.dp` | `20.dp` |
| `Large` | `48.dp` | `48.dp` | `24.dp` |

### ElegantIconButtonDefaults

| Member | Type | Description |
| --- | --- | --- |
| `MinimumTouchSize` | `Dp` | Minimum width and height of every interactive root |
| `AnimationDurationMillis` | `Int` | Standard hover, focus, and state-transition duration |
| `PressAnimationDurationMillis` | `Int` | Immediate press-response duration |
| `HoveredScale` | `Float` | Restrained pointer-hover scale |
| `PressedScale` | `Float` | Restrained press scale |
| `colors(style)` | `ElegantIconButtonColors` | Returns theme-aware colors for an emphasis style |
| `shape(size)` | `Shape` | Returns the optically tuned shape for a size |
| `elevation(style)` | `ElegantIconButtonElevation` | Returns the interaction elevation model for a style |

### ElegantIconButtonColors

`ElegantIconButtonColors` centralizes default, hovered, pressed, focused, and disabled container, content, border, and border-width values. Start with `ElegantIconButtonDefaults.colors(style)` and use `copy(...)` for intentional product-level overrides.

### ElegantIconButtonElevation

`ElegantIconButtonElevation` centralizes default, hovered, pressed, focused, and disabled elevation. Primary and secondary actions gain subtle depth when appropriate; tertiary actions stay visually quiet.

## Advanced Usage

### Responsive Toolbar

```kotlin
Row(
    modifier = Modifier.fillMaxWidth(),
    verticalAlignment = Alignment.CenterVertically,
) {
    Text(
        text = "Release notes",
        modifier = Modifier.weight(1f),
    )
    ElegantIconButton(
        onClick = { /* Edit */ },
        contentDescription = "Edit release notes",
        style = ElegantIconButtonStyle.Secondary,
    ) {
        Icon(Icons.Default.Edit, contentDescription = null)
    }
    ElegantIconButton(
        onClick = { /* More */ },
        contentDescription = "More release note actions",
    ) {
        Icon(Icons.Default.MoreVert, contentDescription = null)
    }
}
```

### Custom Colors

```kotlin
val baseColors = ElegantIconButtonDefaults.colors(ElegantIconButtonStyle.Primary)

ElegantIconButton(
    onClick = { /* Favorite */ },
    contentDescription = "Add to favorites",
    colors = baseColors.copy(
        containerColor = Color(0xFF0F766E),
        pressedContainerColor = Color(0xFF115E59),
    ),
) {
    Icon(Icons.Default.Favorite, contentDescription = null)
}
```
