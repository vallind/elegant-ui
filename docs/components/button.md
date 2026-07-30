# Button

`ElegantButton` triggers an immediate action. It provides three emphasis levels, three sizes, complete interaction states, optional icon slots, and a minimum 48dp touch target.

<ButtonPreview />

## Import

```kotlin
import com.elegant.compose.ui.button.ElegantButton
import com.elegant.compose.ui.button.ElegantButtonSize
import com.elegant.compose.ui.button.ElegantButtonStyle
```

## Basic usage

```kotlin
ElegantButton(
    onClick = { /* action */ },
) {
    Text("Continue")
}
```

## Styles

### Primary

Use once per surface or task for the dominant action.

```kotlin
ElegantButton(
    onClick = { /* action */ },
    style = ElegantButtonStyle.Primary,
) {
    Text("Continue")
}
```

### Secondary

Use for supporting actions that still need a visible container.

```kotlin
ElegantButton(
    onClick = { /* action */ },
    style = ElegantButtonStyle.Secondary,
) {
    Text("Save draft")
}
```

### Tertiary

Use for low-emphasis or contextual actions.

```kotlin
ElegantButton(
    onClick = { /* action */ },
    style = ElegantButtonStyle.Tertiary,
) {
    Text("Learn more")
}
```

## Sizes

| Size | Visual height | Minimum touch height | Horizontal padding |
| --- | ---: | ---: | ---: |
| `Small` | 36dp | 48dp | 12dp |
| `Medium` | 40dp | 48dp | 16dp |
| `Large` | 48dp | 48dp | 20dp |

```kotlin
ElegantButton(
    onClick = { /* action */ },
    size = ElegantButtonSize.Large,
) {
    Text("Create account")
}
```

## States

Default, pressed, focused, disabled, and loading states are supported. Disabled and loading buttons prevent duplicate activation.

```kotlin
ElegantButton(
    onClick = { /* action */ },
    loading = true,
) {
    Text("Submitting")
}
```

## Icons

Leading and trailing icons are content slots rather than style variants.

```kotlin
ElegantButton(
    onClick = { /* action */ },
    leadingIcon = {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = null,
        )
    },
    trailingIcon = {
        Icon(
            imageVector = Icons.AutoMirrored.Default.ArrowForward,
            contentDescription = null,
        )
    },
) {
    Text("Create")
}
```

## Parameters

| Parameter | Type | Default | Description |
| --- | --- | --- | --- |
| `onClick` | `() -> Unit` | required | Invoked for an accepted activation |
| `modifier` | `Modifier` | `Modifier` | Applied to the touch-target container |
| `style` | `ElegantButtonStyle` | `Primary` | Visual emphasis level |
| `size` | `ElegantButtonSize` | `Medium` | Visual size and internal metrics |
| `enabled` | `Boolean` | `true` | Enables user interaction |
| `loading` | `Boolean` | `false` | Shows progress and blocks activation |
| `leadingIcon` | `(@Composable () -> Unit)?` | `null` | Optional leading content |
| `trailingIcon` | `(@Composable () -> Unit)?` | `null` | Optional trailing content |
| `content` | `@Composable () -> Unit` | required | Main button label/content |

## Accessibility

- The touch target is at least 48dp even when the visible button is smaller.
- Compose semantics expose the button role and disabled state.
- Loading exposes a state description while preserving the button role.
- Decorative icons use `contentDescription = null`.
- Directional icons and start/end padding must respect RTL.

## Physical-device checks

Verify visual hierarchy, press feedback, focus visibility, loading and disabled behavior, font scaling, landscape, RTL, and Light/Dark contrast using the latest sample APK artifact.
