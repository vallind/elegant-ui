# Tag

`ElegantTag` is a refined classification and labeling component with four visual variants, three optical sizes, and an optional selection interaction. Use it for filters, categories, metadata, and status classification where a Badge is too small or a Button is too heavy.

<iframe id="demoIframe" style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; border: none;" src="../compose/index.html?id=tag" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>

## Import

```kotlin
import com.elegant.compose.ui.tag.ElegantTag
import com.elegant.compose.ui.tag.ElegantTagColors
import com.elegant.compose.ui.tag.ElegantTagDefaults
import com.elegant.compose.ui.tag.ElegantTagSize
import com.elegant.compose.ui.tag.ElegantTagStyle
```

## Basic Usage

A tag without `onClick` is non-interactive, renders at its optical height, and preserves the semantics of its content.

```kotlin
Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
    ElegantTag(style = ElegantTagStyle.Filled) {
        Text("Stable")
    }
    ElegantTag(style = ElegantTagStyle.Outlined) {
        Text("Beta")
    }
    ElegantTag(style = ElegantTagStyle.Plain) {
        Text("Internal")
    }
}
```

## Styles and Sizes

Four variants cover the classification hierarchy: `Filled` dominates with an accent container, `Tinted` balances emphasis on default surfaces, `Outlined` marks secondary categories with a border, and `Plain` stays quiet for tertiary metadata. `Small`, `Medium`, and `Large` share one optical rhythm.

```kotlin
ElegantTag(style = ElegantTagStyle.Tinted) {
    Text("Design")
}

ElegantTag(size = ElegantTagSize.Small) {
    Text("Compact")
}
ElegantTag(size = ElegantTagSize.Large) {
    Text("Prominent")
}
```

## Component States

Non-interactive tags have no hover, press, focus, or disabled state. Passing `onClick` turns the tag into a selectable control: it announces `Role.Button` and its `selected` state, shows a visible focus ring, applies hover and press feedback, and keeps a 48dp minimum interactive root while the optical pill stays compact.

State precedence for interactive tags: disabled, pressed, selected, focused border, hovered, resting. `selected` is a semantic state that combines with interaction visuals.

```kotlin
var filters by remember { mutableStateOf(setOf("Design")) }

ElegantTag(
    onClick = {
        filters = if ("Design" in filters) {
            filters - "Design"
        } else {
            filters + "Design"
        }
    },
    selected = "Design" in filters,
    style = ElegantTagStyle.Filled,
    leadingContent = {
        Box(Modifier.size(6.dp).background(Color.White, CircleShape))
    },
) {
    Text("Design")
}

ElegantTag(
    onClick = {},
    enabled = false,
) {
    Text("Disabled")
}
```

## Properties

### ElegantTag Properties

| Property Name | Type | Description | Default Value | Required |
| --- | --- | --- | --- | --- |
| `onClick` | `(() -> Unit)?` | Optional activation callback; null keeps the tag non-interactive | `null` | No |
| `modifier` | `Modifier` | Modifier applied once to the tag root | `Modifier` | No |
| `selected` | `Boolean` | Whether the tag communicates a chosen filter or category | `false` | No |
| `enabled` | `Boolean` | Whether user interaction is accepted | `true` | No |
| `style` | `ElegantTagStyle` | Visual variant | `ElegantTagStyle.Tinted` | No |
| `size` | `ElegantTagSize` | Optical size preset | `ElegantTagSize.Medium` | No |
| `shape` | `Shape` | Clipping and outline shape | `ElegantTagDefaults.shape()` | No |
| `colors` | `ElegantTagColors` | Theme-aware state colors | `ElegantTagDefaults.colors(style)` | No |
| `leadingContent` | `@Composable (() -> Unit)?` | Content before the label, such as a status dot or icon | `null` | No |
| `content` | `@Composable () -> Unit` | Tag label or custom content | - | Yes |

### ElegantTagStyle Values

| Value | Behavior |
| --- | --- |
| `Filled` | Dominant solid accent container with inverse content |
| `Tinted` | Soft accent container for balanced emphasis |
| `Outlined` | Transparent container with a visible border |
| `Plain` | Quiet container without a border |

### ElegantTagSize Values

| Value | Optical Height |
| --- | --- |
| `Small` | 24dp |
| `Medium` | 28dp |
| `Large` | 32dp |

### ElegantTagDefaults

| Member | Type | Description |
| --- | --- | --- |
| `MinimumTouchHeight` | `Dp` | 48dp minimum interactive root used by selectable tags |
| `AnimationDurationMillis` | `Int` | Standard 160ms state-transition duration |
| `PressAnimationDurationMillis` | `Int` | Immediate 90ms press-response duration |
| `PressedScale` | `Float` | 0.97 restrained pressed scale |
| `shape()` | `Shape` | Fully rounded default pill shape |
| `colors(style)` | `ElegantTagColors` | Theme-aware Light/Dark colors for the selected style |

### ElegantTagColors

`ElegantTagColors` contains container, content, and border colors plus border widths for the default, selected, hovered, pressed, disabled, and focused states. Start with `ElegantTagDefaults.colors(style)` and use `copy(...)` only for a deliberate product-specific hierarchy.

## Advanced Usage

### Selectable Filter Chip

Use `selected` together with an explicit style switch to communicate both category and choice.

```kotlin
ElegantTag(
    onClick = { onSelect(candidate) },
    selected = selected,
    style = if (selected) ElegantTagStyle.Filled else ElegantTagStyle.Outlined,
    leadingContent = if (selected) {
        { BadgeDot() }
    } else {
        null
    },
) {
    Text(candidate)
}
```

### Custom Emphasis

```kotlin
val baseColors = ElegantTagDefaults.colors(ElegantTagStyle.Outlined)

ElegantTag(
    style = ElegantTagStyle.Outlined,
    colors = baseColors.copy(
        selectedContainerColor = Color(0xFF6C4EFF),
        selectedContentColor = Color.White,
        selectedBorderColor = Color(0xFF6C4EFF),
    ),
    onClick = { /* toggle selection */ },
    selected = selected,
) {
    Text("Custom")
}
```
