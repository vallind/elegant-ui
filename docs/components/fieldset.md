# Fieldset

`ElegantFieldset` is a bordered form section that groups related fields behind an optional legend. Use it for shipping addresses, contact forms, and any collection of inputs that belongs to one logical block.

<iframe id="demoIframe" style="width: 100%; height: 460px; border: 1px solid var(--vp-c-divider); border-radius: 12px; display: block; background: var(--vp-c-bg-alt);" src="../compose/index.html?id=fieldset" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>

## Import

```kotlin
import com.elegant.compose.ui.fieldset.ElegantFieldset
import com.elegant.compose.ui.fieldset.ElegantFieldsetColors
import com.elegant.compose.ui.fieldset.ElegantFieldsetDefaults
```

## Basic Usage

A fieldset renders a raised, rounded container with a 1dp border and 16dp internal padding. The legend is trimmed before rendering and appears above the content only when it is non-blank. Content receives the content color through `LocalContentColor`, and the caller owns the spacing between fields.

```kotlin
ElegantFieldset(legend = "Contact details") {
    Column(verticalArrangement = Arrangement.spacedBy(ElegantSpacing.lg)) {
        ElegantInput(value = "", onValueChange = {}, label = "Email")
        ElegantInput(value = "", onValueChange = {}, label = "Phone")
    }
}
```

## Component States

The fieldset is a non-interactive surface: it has no hover, press, focus, or disabled state, and it adds no role, focus, or click handling. The semantics of the content pass through unchanged. A null or blank legend omits the legend row entirely so the content starts at the top of the bordered section.

```kotlin
ElegantFieldset {
    Text("A fieldset without a legend renders the bordered section only.")
}
```

## Properties

### ElegantFieldset Properties

| Property Name | Type | Description | Default Value | Required |
| --- | --- | --- | --- | --- |
| `modifier` | `Modifier` | Modifier applied once to the fieldset root | `Modifier` | No |
| `legend` | `String?` | Optional legend above the content; null or blank legends are omitted | `null` | No |
| `colors` | `ElegantFieldsetColors` | Theme-aware colors | `ElegantFieldsetDefaults.colors()` | No |
| `content` | `@Composable () -> Unit` | Fields or content grouped inside the bordered section | - | Yes |

### ElegantFieldsetDefaults

| Member | Type | Description |
| --- | --- | --- |
| `LegendGap` | `Dp` | 8dp gap between the legend and the content |
| `ContentPadding` | `Dp` | 16dp padding inside the bordered section |
| `colors()` | `ElegantFieldsetColors` | Theme-aware Light/Dark colors |

### ElegantFieldsetColors

`ElegantFieldsetColors` contains the container, border, legend, and content colors. Start with `ElegantFieldsetDefaults.colors()` and use `copy(...)` only for a deliberate product-specific hierarchy.

## Advanced Usage

### Grouping a Checkout Form

Compose two fieldsets inside an `ElegantCard` to separate shipping and payment concerns on one surface.

```kotlin
ElegantCard {
    Column(Modifier.padding(16.dp)) {
        ElegantFieldset(legend = "Shipping address") {
            ElegantInput(value = "", onValueChange = {}, label = "Street")
        }
        Spacer(Modifier.height(12.dp))
        ElegantFieldset(legend = "Payment details") {
            ElegantInput(value = "", onValueChange = {}, label = "Card number")
        }
    }
}
```
