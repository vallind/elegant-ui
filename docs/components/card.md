# Card

`ElegantCard` is a refined surface component that groups content with three visual variants and an optional activation interaction. Use it for profile rows, statistics, settings entries, and any content block that needs a rounded, theme-aware container.

<iframe id="demoIframe" style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; border: none;" src="../compose/index.html?id=card" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>

## Import

```kotlin
import com.elegant.compose.ui.card.ElegantCard
import com.elegant.compose.ui.card.ElegantCardColors
import com.elegant.compose.ui.card.ElegantCardDefaults
import com.elegant.compose.ui.card.ElegantCardStyle
```

## Basic Usage

A card without `onClick` is non-interactive: it renders the container, border, and shadow only, keeps the semantics of its content, and supports no focus. The card adds no internal padding, so the caller owns spacing inside the content. Content receives the card's content color through `LocalContentColor`.

```kotlin
ElegantCard {
    Column(Modifier.padding(16.dp)) {
        Text("Release notes")
        Text("A card groups related content in one surface.")
    }
}
```

## Styles

Three variants cover the surface hierarchy: `Filled` sits on the default surface, `Outlined` raises the container and marks it with a 1dp border, and `Elevated` raises the container with a resting tonal shadow. All three share the 16dp corner radius.

```kotlin
ElegantCard {
    Text("Filled")
}

ElegantCard(style = ElegantCardStyle.Outlined) {
    Text("Outlined")
}

ElegantCard(style = ElegantCardStyle.Elevated) {
    Text("Elevated")
}
```

## Component States

Non-interactive cards have no hover, press, focus, or disabled state. Passing `onClick` turns the card into a button-like surface: it announces `Role.Button`, keeps a 48dp minimum interactive root, shows a visible focus ring, applies hover and press color feedback with a ripple, and drops to no elevation while pressed or disabled.

State precedence for interactive cards: disabled, pressed, focused border, hovered, resting. Elevation stays at the resting value for hover and focus.

```kotlin
var taps by remember { mutableIntStateOf(0) }

ElegantCard(
    onClick = { taps += 1 },
) {
    Column(Modifier.padding(16.dp)) {
        Text("Tap to activate")
        Text("$taps activations")
    }
}

ElegantCard(
    onClick = {},
    enabled = false,
) {
    Text("Disabled")
}
```

## Properties

### ElegantCard Properties

| Property Name | Type | Description | Default Value | Required |
| --- | --- | --- | --- | --- |
| `onClick` | `(() -> Unit)?` | Optional activation callback; null keeps the card non-interactive | `null` | No |
| `modifier` | `Modifier` | Modifier applied once to the card root | `Modifier` | No |
| `enabled` | `Boolean` | Whether user interaction is accepted | `true` | No |
| `style` | `ElegantCardStyle` | Visual variant | `ElegantCardStyle.Filled` | No |
| `shape` | `Shape` | Clipping, border, and shadow shape | `ElegantCardDefaults.shape(style)` | No |
| `colors` | `ElegantCardColors` | Theme-aware state colors | `ElegantCardDefaults.colors(style)` | No |
| `elevation` | `Dp` | Resting shadow elevation; press and disabled drop to none | `ElegantCardDefaults.elevation(style)` | No |
| `content` | `@Composable () -> Unit` | Card content; padding is the caller's responsibility | - | Yes |

### ElegantCardStyle Values

| Value | Behavior |
| --- | --- |
| `Filled` | Default surface container without border or shadow |
| `Outlined` | Raised container with a visible 1dp border |
| `Elevated` | Raised container with a resting tonal shadow |

### ElegantCardDefaults

| Member | Type | Description |
| --- | --- | --- |
| `MinimumTouchHeight` | `Dp` | 48dp minimum interactive root used by clickable cards |
| `AnimationDurationMillis` | `Int` | Standard 160ms state-transition duration |
| `colors(style)` | `ElegantCardColors` | Theme-aware Light/Dark colors for the selected style |
| `shape(style)` | `Shape` | Shared 16dp rounded corner shape for every style |
| `elevation(style)` | `Dp` | Resting elevation per style; only `Elevated` casts a shadow |

### ElegantCardColors

`ElegantCardColors` contains the container, content, and border colors plus hovered, pressed, disabled, and focus-ring overrides. Start with `ElegantCardDefaults.colors(style)` and use `copy(...)` only for a deliberate product-specific hierarchy.

## Advanced Usage

### Profile Card Composition

Compose `ElegantAvatar`, text, and an `ElegantIconButton` inside an outlined card to build a real surface with a secondary action.

```kotlin
ElegantCard(style = ElegantCardStyle.Outlined) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        ElegantAvatar(name = "Maya Chen", initials = "MC")
        Column(Modifier.weight(1f).padding(start = 12.dp)) {
            Text("Maya Chen")
            Text("Design systems lead")
        }
        ElegantIconButton(
            onClick = { /* open profile menu */ },
            contentDescription = "More profile actions",
        ) {
            Icon(Icons.Default.MoreVert, contentDescription = null)
        }
    }
}
```

### Custom Colors

```kotlin
val baseColors = ElegantCardDefaults.colors(ElegantCardStyle.Filled)

ElegantCard(
    colors = baseColors.copy(
        containerColor = Color(0xFF6C4EFF),
        contentColor = Color.White,
    ),
) {
    Text("Custom surface")
}
```
