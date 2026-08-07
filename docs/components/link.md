# Link

`ElegantLink` is a refined inline text link with an animated underline. Use it for navigation and secondary actions inside sentences, cards, and dense surfaces where a Button is too heavy.

<iframe id="demoIframe" style="width: 100%; height: 460px; border: 1px solid var(--vp-c-divider); border-radius: 12px; display: block; background: var(--vp-c-bg-alt);" src="../compose/index.html?id=link" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>

## Import

```kotlin
import com.elegant.compose.ui.link.ElegantLink
import com.elegant.compose.ui.link.ElegantLinkColors
import com.elegant.compose.ui.link.ElegantLinkDefaults
```

## Basic Usage

A link paints its label in the standard label style and draws a 1dp underline beneath the glyphs. The interactive root keeps a 48dp minimum touch target, so the compact text stays comfortably clickable without growing visually.

```kotlin
ElegantLink(
    text = "View release notes",
    onClick = { onOpenReleaseNotes() },
)
```

## Component States

State precedence: disabled, pressed, hovered, resting. A hovered link brightens to the hover interactive color, a pressed link returns to the resting interactive color, and a disabled link paints the tertiary text color with no interaction feedback. Disabled links never invoke `onClick`. The content color and the underline color both animate between states with the standard transition; the underline rests at 50% alpha and mirrors the full content color in every other state.

```kotlin
ElegantLink(
    text = "Open settings",
    onClick = { onOpenSettings() },
)

ElegantLink(
    text = "Unavailable action",
    onClick = { onOpenSettings() },
    enabled = false,
)
```

## Properties

### ElegantLink Properties

| Property Name | Type | Description | Default Value | Required |
| --- | --- | --- | --- | --- |
| `text` | `String` | Label rendered as link text | - | Yes |
| `onClick` | `() -> Unit` | Activation callback; never invoked while disabled | - | Yes |
| `modifier` | `Modifier` | Modifier applied once to the link root | `Modifier` | No |
| `enabled` | `Boolean` | Whether user interaction is accepted | `true` | No |
| `colors` | `ElegantLinkColors` | Theme-aware state colors | `ElegantLinkDefaults.colors()` | No |
| `interactionSource` | `MutableInteractionSource?` | Optional hoisted interaction source for observing or controlling state; one is created and remembered otherwise | `null` | No |

### ElegantLinkDefaults

| Member | Type | Description |
| --- | --- | --- |
| `MinimumTouchHeight` | `Dp` | 48dp minimum interactive target enforced on the link root |
| `AnimationDurationMillis` | `Int` | Standard 160ms state-transition duration |
| `colors()` | `ElegantLinkColors` | Theme-aware Light/Dark colors for links |

### ElegantLinkColors

`ElegantLinkColors` holds the resting, hovered, pressed, and disabled content colors plus the resting underline color. Every state color defaults to `contentColor`, and `underlineColor` defaults to `contentColor` as well. Start with `ElegantLinkDefaults.colors()` and use `copy(...)` only for a deliberate product-specific hierarchy.

## Advanced Usage

### Custom Colors

Override individual roles while keeping the rest of the theme-aware palette.

```kotlin
ElegantLink(
    text = "Custom emphasis",
    onClick = { onOpenCustom() },
    colors = ElegantLinkDefaults.colors().copy(
        contentColor = Color(0xFF6C4EFF),
        underlineColor = Color(0xFF6C4EFF).copy(alpha = 0.5f),
    ),
)
```

### Hoisted Interaction Source

Pass an interaction source to observe hover or press outside the link, for example to drive secondary feedback.

```kotlin
val interactionSource = remember { MutableInteractionSource() }
val hovered by interactionSource.collectIsHoveredAsState()

ElegantLink(
    text = "Hoverable link",
    onClick = { onOpenHoverable() },
    interactionSource = interactionSource,
)

if (hovered) {
    Text("Link hovered")
}
```
