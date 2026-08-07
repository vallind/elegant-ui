# Disclosure

`ElegantDisclosure` is a single expandable section presented as its own bordered block: a 48dp header with a title, optional supporting text, and a trailing chevron, plus a body that expands with a vertical animation. `ElegantDisclosureGroup` wraps multiple disclosures in one bordered surface. Use it for FAQs, filter panels, and progressive disclosure.

<iframe id="demoIframe" style="width: 100%; height: 460px; border: 1px solid var(--vp-c-divider); border-radius: 12px; display: block; background: var(--vp-c-bg-alt);" src="../compose/index.html?id=disclosure" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>

## Import

```kotlin
import com.elegant.compose.ui.disclosure.ElegantDisclosure
import com.elegant.compose.ui.disclosure.ElegantDisclosureColors
import com.elegant.compose.ui.disclosure.ElegantDisclosureDefaults
import com.elegant.compose.ui.disclosure.ElegantDisclosureGroup
```

## Basic Usage

The disclosure is a `Column` surface with a 1dp border and a 12dp corner radius, so a standalone disclosure reads as one card. It is controlled: `expanded` is owned by the caller and must be written back from `onToggle`. The body expands with a vertical slide and fade and carries 16dp horizontal and bottom padding.

```kotlin
var expanded by remember { mutableStateOf(false) }

ElegantDisclosure(
    title = "Release notes",
    expanded = expanded,
    onToggle = { expanded = !expanded },
) {
    Text("The body reveals with a vertical expand animation.")
}
```

## Component States

Each header keeps a 48dp minimum interactive root with 16dp horizontal padding. Color precedence is disabled, pressed, hovered-or-focused, resting: the resting header is transparent so the surface shows through, hovered headers tint with `surfaceHover`, pressed headers with `backgroundSubtle`, and keyboard focus reuses the hovered tint. While disabled, the header never invokes `onToggle`, the chevron drops to the tertiary text color, and the semantics announce the disabled state.

```kotlin
ElegantDisclosure(
    title = "Hover, press, and focus the header",
    expanded = true,
    onToggle = {},
    supportingText = "Hovered and focused headers tint with surfaceHover.",
) {
    Text("Pressed headers tint with backgroundSubtle.")
}
ElegantDisclosure(
    title = "Disabled disclosure",
    expanded = false,
    onToggle = {},
    enabled = false,
    supportingText = "A disabled header never invokes onToggle.",
) {
    Text("Disabled bodies never reveal.")
}
```

## Properties

### ElegantDisclosure Properties

| Property Name | Type | Description | Default Value | Required |
| --- | --- | --- | --- | --- |
| `title` | `String` | Header label, truncated with an ellipsis when it overflows one line | - | Yes |
| `expanded` | `Boolean` | Whether the body is expanded; owned by the caller | - | Yes |
| `onToggle` | `() -> Unit` | Callback invoked when the header is activated to toggle `expanded` | - | Yes |
| `modifier` | `Modifier` | Modifier applied once to the disclosure root | `Modifier` | No |
| `enabled` | `Boolean` | Whether the header accepts activation | `true` | No |
| `supportingText` | `String?` | Optional secondary line under the title in the secondary text color | `null` | No |
| `colors` | `ElegantDisclosureColors` | Theme-aware state colors | `ElegantDisclosureDefaults.colors()` | No |
| `content` | `@Composable () -> Unit` | Body shown while expanded; padding is owned by the disclosure | - | Yes |

### ElegantDisclosureGroup Properties

| Property Name | Type | Description | Default Value | Required |
| --- | --- | --- | --- | --- |
| `modifier` | `Modifier` | Modifier applied once to the group root | `Modifier` | No |
| `colors` | `ElegantDisclosureColors` | Theme-aware state colors | `ElegantDisclosureDefaults.colors()` | No |
| `content` | `@Composable () -> Unit` | Group content; typically one or more `ElegantDisclosure`s | - | Yes |

### ElegantDisclosureDefaults

| Member | Type | Description |
| --- | --- | --- |
| `MinimumTouchHeight` | `Dp` | 48dp minimum interactive header height |
| `AnimationDurationMillis` | `Int` | Standard 160ms expand, collapse, and state-transition duration |
| `colors()` | `ElegantDisclosureColors` | Theme-aware Light/Dark colors |

### ElegantDisclosureColors

`ElegantDisclosureColors` contains the container, content, header, and border colors. The default header container is transparent so the surface shows through, hovered and focused headers use `surfaceHover`, and pressed headers use `backgroundSubtle`. Start with `ElegantDisclosureDefaults.colors()` and use `copy(...)` only for a deliberate product-specific hierarchy.

## Advanced Usage

### Single-Expand Disclosure

Track the expanded index to keep one disclosure open; toggling the same disclosure collapses it again.

```kotlin
var expandedIndex by remember { mutableIntStateOf(-1) }

listOf("General", "Appearance", "Privacy").forEachIndexed { index, item ->
    ElegantDisclosure(
        title = item,
        expanded = expandedIndex == index,
        onToggle = {
            expandedIndex = if (expandedIndex == index) -1 else index
        },
    ) {
        Text("Only one disclosure stays expanded at a time.")
    }
}
```

### FAQ with DisclosureGroup

`ElegantDisclosureGroup` wraps stacked disclosures in one bordered surface; it draws no dividers between siblings, so each disclosure keeps its own bordered block.

```kotlin
ElegantDisclosureGroup {
    ElegantDisclosure(
        title = "What is Elegant UI?",
        supportingText = "Refined Compose Multiplatform components",
        expanded = true,
        onToggle = {},
    ) {
        Text("A component library shared across Android, Desktop, and Web.")
    }
    ElegantDisclosure(
        title = "Which platforms are supported?",
        expanded = false,
        onToggle = {},
    ) {
        Text("Android 24+, Desktop JVM, and Web/Wasm.")
    }
}
```
