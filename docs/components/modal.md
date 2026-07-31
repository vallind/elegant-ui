# Modal

`ElegantModal` is a modal overlay that centers a rounded surface on a dimming scrim inside a platform dialog window. Use it for confirmations, focused forms, and other tasks that must block the rest of the app until resolved.

<iframe id="demoIframe" style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; border: none;" src="../compose/index.html?id=modal" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>

## Import

```kotlin
import com.elegant.compose.ui.modal.ElegantModal
import com.elegant.compose.ui.modal.ElegantModalColors
import com.elegant.compose.ui.modal.ElegantModalDefaults
```

## Basic Usage

`ElegantModal` is a controlled overlay: the caller owns the `visible` state and removes the dialog from composition by setting it to false. The dialog renders the scrim, the centered surface capped at 480dp with 24dp internal padding, and the content color; the caller owns the title, description, and action layout inside the content.

```kotlin
var visible by remember { mutableStateOf(false) }

ElegantButton(onClick = { visible = true }) {
    Text("Open modal")
}

ElegantModal(
    visible = visible,
    onDismissRequest = { visible = false },
) {
    Column {
        Text("Delete project?", style = ElegantTheme.typography.titleMedium)
        Text("This action cannot be undone.", style = ElegantTheme.typography.bodyMedium)
        Row(Modifier.align(Alignment.End)) {
            ElegantButton(
                onClick = { visible = false },
                style = ElegantButtonStyle.Secondary,
            ) {
                Text("Cancel")
            }
            ElegantButton(onClick = { visible = false }) {
                Text("Delete")
            }
        }
    }
}
```

## Component States

`ElegantModal` is a controlled overlay with one visible state; it has no disabled or loading states.

**Dismissal.** Three paths close the modal. An outside click on the scrim calls `onDismissRequest` through `dismissOnClickOutside`. The system back key on Android, or the Escape key on Desktop and Web, calls `onDismissRequest` through `dismissOnBackPress`. Setting `visible` to false removes the dialog window directly, without invoking `onDismissRequest`, because the caller already owns that decision.

**Focus.** While visible, the platform dialog window captures focus and restricts focus traversal to the modal content; the surrounding app is inert. On dismissal, focus returns to the previously focused element.

**Entrance.** The surface fades and scales in (alpha 0 to 1, scale 0.98 to 1) over `AnimationDurationMillis`; the scrim appears with the dialog window.

```kotlin
// Scrim click, back/Escape, and visible = false all leave the modal closed.
ElegantModal(
    visible = visible,
    onDismissRequest = { visible = false },
) {
    Text("Modal content")
}
```

## Properties

### ElegantModal Properties

| Property Name | Type | Description | Default Value | Required |
| --- | --- | --- | --- | --- |
| `visible` | `Boolean` | Whether the modal is shown; false composes nothing | - | Yes |
| `onDismissRequest` | `() -> Unit` | Invoked on scrim click and back/Escape dismissal | - | Yes |
| `modifier` | `Modifier` | Modifier applied once to the full-screen dialog root | `Modifier` | No |
| `shape` | `Shape` | Clipping and shadow shape of the modal surface | `ElegantModalDefaults.Shape` | No |
| `colors` | `ElegantModalColors` | Theme-aware modal colors | `ElegantModalDefaults.colors()` | No |
| `content` | `@Composable () -> Unit` | Modal content; padding is provided by the modal | - | Yes |

### ElegantModalDefaults

| Member | Type | Description |
| --- | --- | --- |
| `MaxWidth` | `Dp` | 480dp maximum surface width before the content wraps |
| `Shape` | `Shape` | Shared 16dp rounded corner shape |
| `ScrimAlpha` | `Float` | 0.4 alpha applied to the black scrim overlay |
| `AnimationDurationMillis` | `Int` | 220ms emphasized entrance duration |
| `colors()` | `ElegantModalColors` | Theme-aware Light/Dark colors |

### ElegantModalColors

`ElegantModalColors` contains the scrim color, the surface container color, and the content color provided through `LocalContentColor`. Start with `ElegantModalDefaults.colors()` and use `copy(...)` only for a deliberate product-specific surface.

## Advanced Usage

### Confirmation Flow

A confirmation modal pairs a warning title and description with a secondary cancel action and a primary confirm action. `onDismissRequest` covers Cancel, the outside click, and back/Escape, so every path leaves the modal closed.

```kotlin
var confirmVisible by remember { mutableStateOf(false) }

ElegantButton(onClick = { confirmVisible = true }) {
    Text("Delete file")
}

ElegantModal(
    visible = confirmVisible,
    onDismissRequest = { confirmVisible = false },
) {
    Column(verticalArrangement = Arrangement.spacedBy(ElegantSpacing.lg)) {
        Column(verticalArrangement = Arrangement.spacedBy(ElegantSpacing.xs)) {
            Text("Delete this file?", style = ElegantTheme.typography.titleMedium)
            Text("The file is removed permanently.", style = ElegantTheme.typography.bodyMedium)
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(ElegantSpacing.md, Alignment.End),
        ) {
            ElegantButton(
                onClick = { confirmVisible = false },
                style = ElegantButtonStyle.Secondary,
            ) {
                Text("Cancel")
            }
            ElegantButton(onClick = { confirmVisible = false }) {
                Text("Delete")
            }
        }
    }
}
```

### Custom Surface

```kotlin
val baseColors = ElegantModalDefaults.colors()

ElegantModal(
    visible = visible,
    onDismissRequest = { visible = false },
    colors = baseColors.copy(
        containerColor = Color(0xFF202229),
        contentColor = Color(0xFFF6F7F9),
    ),
) {
    Text("Custom surface")
}
```
