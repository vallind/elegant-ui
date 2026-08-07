# Alert Dialog

`ElegantAlertDialog` is the confirmation variant of the Elegant UI overlay family: a compact surface with a title, an optional description, and a paired dismiss/confirm action row, centered on a dimming scrim inside a platform dialog window. Use it for destructive or important confirmations that must block the rest of the app until resolved.

<iframe id="demoIframe" style="width: 100%; height: 460px; border: 1px solid var(--vp-c-divider); border-radius: 12px; display: block; background: var(--vp-c-bg-alt);" src="../compose/index.html?id=alert-dialog" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>

## Import

```kotlin
import com.elegant.compose.ui.alertdialog.ElegantAlertDialog
import com.elegant.compose.ui.alertdialog.ElegantAlertDialogColors
import com.elegant.compose.ui.alertdialog.ElegantAlertDialogDefaults
```

## Basic Usage

`ElegantAlertDialog` is a controlled overlay: the caller owns the `visible` state and removes the dialog from composition by setting it to false. The dialog renders the scrim, the centered surface capped at 400dp with 24dp internal padding, the title and optional description, and the action row; the optional `content` slot sits between the description and the actions.

```kotlin
var visible by remember { mutableStateOf(false) }

ElegantButton(onClick = { visible = true }) {
    Text("Delete project")
}

ElegantAlertDialog(
    visible = visible,
    onDismissRequest = { visible = false },
    title = "Delete project?",
    description = "This action cannot be undone.",
    confirmText = "Delete",
    onConfirm = { visible = false },
    dismissText = "Cancel",
    onDismiss = { visible = false },
)
```

## Component States

`ElegantAlertDialog` is a controlled overlay with one visible state and a `confirmEnabled` gate on the confirm button; it has no loading state.

**Dismissal.** Four paths close or resolve the dialog. An outside click on the scrim calls `onDismissRequest` through `dismissOnClickOutside`. The system back key on Android, or the Escape key on Desktop and Web, calls `onDismissRequest` through `dismissOnBackPress`. The dismiss button calls `onDismiss` when provided and `onDismissRequest` otherwise. The confirm button calls only `onConfirm`; it never dismisses the dialog itself, so the caller keeps the dialog open or sets `visible` to false as the confirmation outcome requires.

**Focus.** While visible, the platform dialog window captures focus and restricts focus traversal to the dialog content; the surrounding app is inert. On dismissal, focus returns to the previously focused element. The confirm and dismiss buttons are `ElegantButton`s with their own roles and states.

**Description and dismiss button.** A `description` that is null or blank is not rendered. A `dismissText` that is null or blank hides the dismiss button, leaving the confirm button as the only action.

**Entrance.** The surface fades and scales in (alpha 0 to 1, scale 0.98 to 1) over `AnimationDurationMillis`; the scrim appears with the dialog window.

```kotlin
// Scrim click, back/Escape, and Cancel leave the dialog closed;
// Confirm resolves the outcome without dismissing by itself.
ElegantAlertDialog(
    visible = visible,
    onDismissRequest = { visible = false },
    title = "Discard changes?",
    confirmText = "Discard",
    onConfirm = {
        // Perform the destructive work; the dialog stays open until visible changes.
        visible = false
    },
    dismissText = "Keep editing",
    onDismiss = { visible = false },
)
```

## Properties

### ElegantAlertDialog Properties

| Property Name | Type | Description | Default Value | Required |
| --- | --- | --- | --- | --- |
| `visible` | `Boolean` | Whether the dialog is shown; false composes nothing | - | Yes |
| `onDismissRequest` | `() -> Unit` | Invoked on scrim click, back/Escape, and the dismiss button when `onDismiss` is null | - | Yes |
| `modifier` | `Modifier` | Modifier applied once to the full-screen dialog root | `Modifier` | No |
| `title` | `String` | Dialog title text rendered in the `titleMedium` style | - | Yes |
| `description` | `String?` | Optional supporting text; hidden while null or blank | `null` | No |
| `confirmText` | `String` | Label of the confirm button | - | Yes |
| `onConfirm` | `() -> Unit` | Invoked when the confirm button accepts an activation; the dialog never dismisses itself | - | Yes |
| `dismissText` | `String?` | Label of the dismiss button; hidden while null or blank | `null` | No |
| `onDismiss` | `(() -> Unit)?` | Optional callback invoked by the dismiss button; falls back to `onDismissRequest` | `null` | No |
| `confirmEnabled` | `Boolean` | Whether the confirm button accepts activation | `true` | No |
| `colors` | `ElegantAlertDialogColors` | Theme-aware dialog colors | `ElegantAlertDialogDefaults.colors()` | No |
| `content` | `@Composable () -> Unit` | Optional slot rendered between the description and the action row | `{}` | No |

### ElegantAlertDialogDefaults

| Member | Type | Description |
| --- | --- | --- |
| `MaxWidth` | `Dp` | 400dp maximum surface width before the content wraps |
| `ScrimAlpha` | `Float` | 0.4 alpha applied to the black scrim overlay |
| `AnimationDurationMillis` | `Int` | 220ms emphasized entrance duration |
| `colors()` | `ElegantAlertDialogColors` | Theme-aware Light/Dark colors |

### ElegantAlertDialogColors

`ElegantAlertDialogColors` contains the scrim color, the surface container color, the content color provided through `LocalContentColor`, the title color, and the description color. Start with `ElegantAlertDialogDefaults.colors()` and use `copy(...)` only for a deliberate product-specific surface.

## Advanced Usage

### Destructive Confirmation

A destructive confirmation keeps the confirm button disabled until a precondition is met, so an unintended activation cannot happen before the caller is ready.

```kotlin
var pending by remember { mutableStateOf(false) }
var canDelete by remember { mutableStateOf(false) }

ElegantButton(onClick = { pending = true }) {
    Text("Delete project")
}

ElegantAlertDialog(
    visible = pending,
    onDismissRequest = { pending = false },
    title = "Delete project?",
    description = "The project and its history are removed permanently.",
    confirmText = "Delete",
    onConfirm = {
        pending = false
        canDelete = true
    },
    dismissText = "Cancel",
    onDismiss = { pending = false },
    confirmEnabled = canDelete,
)
```

### Custom Colors

Use `ElegantAlertDialogDefaults.colors()` as the base and `copy(...)` to restyle the surface for a product-specific look.

```kotlin
val baseColors = ElegantAlertDialogDefaults.colors()

ElegantAlertDialog(
    visible = visible,
    onDismissRequest = { visible = false },
    title = "Delete project?",
    confirmText = "Delete",
    onConfirm = { visible = false },
    dismissText = "Cancel",
    onDismiss = { visible = false },
    colors = baseColors.copy(
        containerColor = Color(0xFF202229),
        titleColor = Color(0xFFF6F7F9),
    ),
)
```
