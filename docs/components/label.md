# Label

`ElegantLabel` renders a non-interactive form-field label with an optional required marker. It pairs the label text in a secondary text color with a compact `"*"` suffix in a critical color, keeps the text readable to assistive technology, and adapts to Light and Dark themes without adding an interaction role.

<iframe id="demoIframe" style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; border: none;" src="../compose/index.html?id=label" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>

## Import

```kotlin
import com.elegant.compose.ui.label.ElegantLabel
import com.elegant.compose.ui.label.ElegantLabelColors
import com.elegant.compose.ui.label.ElegantLabelDefaults
```

## Basic Usage

Pass the field label directly as `text`. The label renders on a single line with ellipsis, uses the theme-aware secondary text color, and is placed directly above the field it describes.

```kotlin
Column(verticalArrangement = Arrangement.spacedBy(ElegantSpacing.xs)) {
    ElegantLabel(text = "Full name")
    ElegantInput(value = "", onValueChange = {})
}
```

## Component States

`ElegantLabel` is non-interactive: it has no role, no press or focus states, and no touch target of its own. When `enabled` is false the text renders in the disabled content color, and when `required` is true a separate `"*"` suffix in the critical color follows the text with a 2dp gap, so the marker never gets truncated away.

```kotlin
Column(verticalArrangement = Arrangement.spacedBy(ElegantSpacing.xs)) {
    ElegantLabel(text = "Email address", required = true)
    ElegantLabel(text = "Nickname", enabled = false)
}
```

## Properties

### ElegantLabel Properties

| Property Name | Type | Description | Default Value | Required |
| --- | --- | --- | --- | --- |
| `text` | `String` | Label text shown next to the field | - | Yes |
| `modifier` | `Modifier` | Modifier applied once to the label root | `Modifier` | No |
| `required` | `Boolean` | Whether the required-field suffix is rendered after the text | `false` | No |
| `enabled` | `Boolean` | Whether the label renders in its enabled content color | `true` | No |
| `colors` | `ElegantLabelColors` | Theme-aware content and required-marker colors | `ElegantLabelDefaults.colors()` | No |

### ElegantLabelDefaults

| Member | Type | Description |
| --- | --- | --- |
| `RequiredSuffix` | `String` | Default `"*"` required-field suffix |
| `colors()` | `ElegantLabelColors` | Returns Light/Dark theme-aware label colors |

### ElegantLabelColors

`ElegantLabelColors` contains `contentColor`, `requiredColor`, and `disabledContentColor` (defaulting to `contentColor`). Start with `ElegantLabelDefaults.colors()` and use `copy(...)` for deliberate product-specific customization.

## Advanced Usage

Customize the label color model while preserving the single-line ellipsis and the required-marker geometry for product-specific forms.

```kotlin
val labelColors = ElegantLabelDefaults.colors().copy(
    requiredColor = ElegantTheme.colors.interactivePrimary,
)

ElegantLabel(
    text = "Promo code",
    required = true,
    colors = labelColors,
)
```
