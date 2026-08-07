# Accordion

`ElegantAccordion` is a bordered surface that groups expandable content into stacked `ElegantAccordionItem`s. Each item pairs a 48dp header with a chevron, title, and optional supporting text, and expands its body with a vertical animation. Use it for FAQs, settings groups, and progressive disclosure.

<iframe id="demoIframe" style="width: 100%; height: 460px; border: 1px solid var(--vp-c-divider); border-radius: 12px; display: block; background: var(--vp-c-bg-alt);" src="../compose/index.html?id=accordion" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>

## Import

```kotlin
import com.elegant.compose.ui.accordion.ElegantAccordion
import com.elegant.compose.ui.accordion.ElegantAccordionColors
import com.elegant.compose.ui.accordion.ElegantAccordionDefaults
import com.elegant.compose.ui.accordion.ElegantAccordionItem
```

## Basic Usage

The accordion is a `Column` container with a 1dp border and a 12dp corner radius; it adds no spacing, so items stack flush. Items are controlled: `expanded` is owned by the caller and must be written back from `onToggle`. The body expands with a vertical slide and fade and carries 16dp horizontal and bottom padding.

```kotlin
var expanded by remember { mutableStateOf(false) }

ElegantAccordion {
    ElegantAccordionItem(
        title = "Release notes",
        expanded = expanded,
        onToggle = { expanded = !expanded },
    ) {
        Text("The item body reveals with a vertical expand animation.")
    }
}
```

## Component States

Each header keeps a 48dp minimum interactive root with 16dp horizontal padding. Color precedence is disabled, pressed, hovered-or-focused, resting: the resting header is transparent so the surface shows through, hovered headers tint with `surfaceHover`, pressed headers with `backgroundSubtle`, and keyboard focus reuses the hovered tint. While disabled, the header never invokes `onToggle`, the chevron drops to the tertiary text color, and the semantics announce the disabled state.

```kotlin
ElegantAccordion {
    ElegantAccordionItem(
        title = "Hover, press, and focus the header",
        expanded = true,
        onToggle = {},
        supportingText = "Hovered and focused headers tint with surfaceHover.",
    ) {
        Text("Pressed headers tint with backgroundSubtle.")
    }
    ElegantAccordionItem(
        title = "Disabled item",
        expanded = false,
        onToggle = {},
        enabled = false,
        supportingText = "A disabled header never invokes onToggle.",
    ) {
        Text("Disabled bodies never reveal.")
    }
}
```

## Properties

### ElegantAccordion Properties

| Property Name | Type | Description | Default Value | Required |
| --- | --- | --- | --- | --- |
| `modifier` | `Modifier` | Modifier applied once to the accordion root | `Modifier` | No |
| `colors` | `ElegantAccordionColors` | Theme-aware state colors | `ElegantAccordionDefaults.colors()` | No |
| `content` | `@Composable () -> Unit` | Accordion content; typically one or more `ElegantAccordionItem`s | - | Yes |

### ElegantAccordionItem Properties

| Property Name | Type | Description | Default Value | Required |
| --- | --- | --- | --- | --- |
| `title` | `String` | Header label, truncated with an ellipsis when it overflows one line | - | Yes |
| `expanded` | `Boolean` | Whether the body is expanded; owned by the caller | - | Yes |
| `onToggle` | `() -> Unit` | Callback invoked when the header is activated to toggle `expanded` | - | Yes |
| `modifier` | `Modifier` | Modifier applied once to the item root | `Modifier` | No |
| `enabled` | `Boolean` | Whether the header accepts activation | `true` | No |
| `supportingText` | `String?` | Optional secondary line under the title in the secondary text color | `null` | No |
| `colors` | `ElegantAccordionColors` | Theme-aware state colors | `ElegantAccordionDefaults.colors()` | No |
| `content` | `@Composable () -> Unit` | Body shown while expanded; padding is owned by the item | - | Yes |

### ElegantAccordionDefaults

| Member | Type | Description |
| --- | --- | --- |
| `MinimumTouchHeight` | `Dp` | 48dp minimum interactive header height |
| `AnimationDurationMillis` | `Int` | Standard 160ms expand, collapse, and state-transition duration |
| `colors()` | `ElegantAccordionColors` | Theme-aware Light/Dark colors |

### ElegantAccordionColors

`ElegantAccordionColors` contains the container, content, header, and border colors. The default header container is transparent so the surface shows through, hovered and focused headers use `surfaceHover`, and pressed headers use `backgroundSubtle`. `dividerColor` is reserved for product-level item dividers; the default layout draws no dividers. Start with `ElegantAccordionDefaults.colors()` and use `copy(...)` only for a deliberate product-specific hierarchy.

## Advanced Usage

### Single-Expand Accordion

Keep one expanded item by tracking the expanded index; toggling the same item collapses it again.

```kotlin
var expandedIndex by remember { mutableIntStateOf(-1) }

ElegantAccordion {
    listOf("General", "Appearance", "Privacy").forEachIndexed { index, item ->
        ElegantAccordionItem(
            title = item,
            expanded = expandedIndex == index,
            onToggle = {
                expandedIndex = if (expandedIndex == index) -1 else index
            },
        ) {
            Text("Only one item stays expanded at a time.")
        }
    }
}
```

### FAQ Card with Supporting Text

Pair a title with `supportingText` to preview the answer before the user expands the item.

```kotlin
ElegantAccordion {
    ElegantAccordionItem(
        title = "What is Elegant UI?",
        supportingText = "Refined Compose Multiplatform components",
        expanded = true,
        onToggle = {},
    ) {
        Text("A component library shared across Android, Desktop, and Web.")
    }
    ElegantAccordionItem(
        title = "Which platforms are supported?",
        expanded = false,
        onToggle = {},
    ) {
        Text("Android 24+, Desktop JVM, and Web/Wasm.")
    }
}
```
