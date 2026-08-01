# Kbd

`ElegantKbd` renders a compact non-interactive keyboard-key badge for shortcut documentation. It pairs a recessed surface with a subtle rounded outline and a small label, keeps the key text readable to assistive technology, and adapts to Light and Dark themes without adding an interaction role.

<iframe id="demoIframe" style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; border: none;" src="../compose/index.html?id=kbd" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>

## Import

```kotlin
import com.elegant.compose.ui.kbd.ElegantKbd
import com.elegant.compose.ui.kbd.ElegantKbdColors
import com.elegant.compose.ui.kbd.ElegantKbdDefaults
```

## Basic Usage

Pass the key or chord label directly as `text`. The badge takes its sizing from a 24dp minimum height with 6dp horizontal padding, so single keys and multi-key chords both look balanced.

```kotlin
Row(
    horizontalArrangement = Arrangement.spacedBy(ElegantSpacing.sm),
) {
    ElegantKbd(text = "⌘")
    ElegantKbd(text = "Ctrl + K")
}
```

## Component States

`ElegantKbd` is non-interactive: it has no role, no press or focus states, and no disabled condition. It also adds no semantics node of its own, so the key label text remains readable while shortcuts stay quiet for assistive technology.

```kotlin
Row(
    horizontalArrangement = Arrangement.spacedBy(ElegantSpacing.sm),
    verticalAlignment = Alignment.CenterVertically,
) {
    ElegantKbd(text = "Shift")
    Text(text = "+")
    ElegantKbd(text = "P")
}
```

## Properties

### ElegantKbd Properties

| Property Name | Type | Description | Default Value | Required |
| --- | --- | --- | --- | --- |
| `text` | `String` | Key or chord label displayed inside the badge | - | Yes |
| `modifier` | `Modifier` | Modifier applied once to the badge container | `Modifier` | No |
| `colors` | `ElegantKbdColors` | Theme-aware container, content, and border colors | `ElegantKbdDefaults.colors()` | No |

### ElegantKbdDefaults

| Member | Type | Description |
| --- | --- | --- |
| `MinHeight` | `Dp` | Default 24dp minimum height |
| `HorizontalPadding` | `Dp` | Default 6dp horizontal padding |
| `BorderWidth` | `Dp` | Default 1dp optical outline |
| `colors()` | `ElegantKbdColors` | Returns Light/Dark theme-aware keyboard-key badge colors |

### ElegantKbdColors

`ElegantKbdColors` contains `containerColor`, `contentColor`, and `borderColor`. Start with `ElegantKbdDefaults.colors()` and use `copy(...)` for deliberate product-specific customization.

## Advanced Usage

Customize the color model while preserving badge geometry and semantics when keys belong to a product-specific vocabulary.

```kotlin
val kbdColors = ElegantKbdDefaults.colors().copy(
    containerColor = ElegantTheme.colors.backgroundSubtle,
    borderColor = ElegantTheme.colors.borderStrong,
)

ElegantKbd(
    text = "Enter",
    colors = kbdColors,
)
```
