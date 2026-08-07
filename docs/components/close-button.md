# CloseButton

`ElegantCloseButton` is a compact dismiss action for dialogs, cards, and floating surfaces. It draws a fixed X glyph on a quiet transparent pill inside a 48dp interaction target, with a pill hover, a restrained press, a keyboard-focus ring when the theme enables focus rings, and a localizable accessible name.

<iframe id="demoIframe" style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; border: none;" src="../compose/index.html?id=close-button" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>

## Import

```kotlin
import com.elegant.compose.ui.closebutton.ElegantCloseButton
import com.elegant.compose.ui.closebutton.ElegantCloseButtonColors
import com.elegant.compose.ui.closebutton.ElegantCloseButtonDefaults
```

## Basic Usage

Place the close button wherever a dismiss action belongs. `contentDescription` defaults to `"Close"`; localize it for production use.

```kotlin
ElegantCloseButton(
    onClick = { /* Dismiss the overlay */ },
)
```

## Component States

Hover, press, and keyboard focus are resolved from one interaction source. The pill fills on hover, darkens while pressed, and shows a focus ring while focused when the theme enables focus rings; the disabled state dims the glyph and rejects activation.

```kotlin
ElegantCloseButton(
    onClick = { /* Dismiss the dialog */ },
)

ElegantCloseButton(
    onClick = {},
    enabled = false,
)
```

## Properties

### ElegantCloseButton Properties

| Property Name | Type | Description | Default Value | Required |
| --- | --- | --- | --- | --- |
| `onClick` | `() -> Unit` | Callback invoked when the close action accepts activation | - | Yes |
| `modifier` | `Modifier` | Modifier applied to the 48dp minimum interaction root | `Modifier` | No |
| `enabled` | `Boolean` | Whether the close action accepts interaction | `true` | No |
| `contentDescription` | `String` | Localized accessible name describing the close action | `"Close"` | No |
| `colors` | `ElegantCloseButtonColors` | Theme-aware interaction colors for the pill and the glyph | `ElegantCloseButtonDefaults.colors()` | No |
| `interactionSource` | `MutableInteractionSource?` | Optional hoisted source for observing hover, press, and focus | `null` | No |

### ElegantCloseButtonDefaults

| Member | Type | Description |
| --- | --- | --- |
| `MinimumTouchHeight` | `Dp` | Minimum width and height of every interactive root |
| `VisualSize` | `Dp` | Visual pill diameter hosting the X glyph |
| `AnimationDurationMillis` | `Int` | Standard hover, focus, and state-transition duration |
| `colors()` | `ElegantCloseButtonColors` | Returns theme-aware colors |

### ElegantCloseButtonColors

`ElegantCloseButtonColors` centralizes resting, hovered, pressed, disabled, and focused colors. The resting container is transparent, hover fills the pill, press darkens it, and the focused border color becomes the focus ring. Start with `ElegantCloseButtonDefaults.colors()` and use `copy(...)` for intentional product-level overrides.

## Advanced Usage

### Card with a Dismiss Action

Overlay the close button on the top end of a card; the 48dp target keeps the hit area generous while the 28dp pill stays visually compact.

```kotlin
ElegantCard {
    Box {
        Text(
            text = "Release notes",
            modifier = Modifier
                .fillMaxWidth()
                .padding(ElegantSpacing.xl),
        )
        ElegantCloseButton(
            onClick = { /* Dismiss the card */ },
            modifier = Modifier.align(Alignment.TopEnd),
        )
    }
}
```
