# Description

`ElegantDescription` renders a key-value list as stacked rows with a fixed-width label column and a flexible value column, separated by 1dp dividers. The list is non-interactive, keeps content semantics, and renders the values of disabled items in the disabled color.

<iframe id="demoIframe" style="width: 100%; height: 460px; border: 1px solid var(--vp-c-divider); border-radius: 12px; display: block; background: var(--vp-c-bg-alt);" src="../compose/index.html?id=description" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>

## Import

```kotlin
import com.elegant.compose.ui.description.ElegantDescription
import com.elegant.compose.ui.description.ElegantDescriptionColors
import com.elegant.compose.ui.description.ElegantDescriptionDefaults
import com.elegant.compose.ui.description.ElegantDescriptionItem
```

## Basic Usage

Describe one key-value entry per `ElegantDescriptionItem`. Labels keep a fixed 140dp column and values fill the remaining width; divider lines separate rows but never follow the last one.

```kotlin
val profile = listOf(
    ElegantDescriptionItem(label = "Owner", value = "Maya Chen"),
    ElegantDescriptionItem(label = "Repository", value = "elegant"),
    ElegantDescriptionItem(label = "License", value = "Proprietary", enabled = false),
)

ElegantDescription(items = profile)
```

## Customizing the Label Column

Pass `labelWidth` to give the label column a custom fixed width. Non-positive or non-finite values fall back to the default 140dp. Rows with a blank label render an empty label cell so values stay aligned.

```kotlin
val targets = listOf(
    ElegantDescriptionItem(label = "Android", value = "API 24+"),
    ElegantDescriptionItem(label = "Desktop", value = "JVM"),
    ElegantDescriptionItem(label = "Web", value = "Wasm"),
)

ElegantDescription(items = targets, labelWidth = 96.dp)
```

## Component States

Description is non-interactive and has no pressed, focused, selected, or loading state. A per-item `enabled = false` renders the value in the disabled value color while the label keeps its default color. The list preserves content semantics and adds no semantics node of its own.

## Properties

### ElegantDescription Properties

| Property Name | Type | Description | Default Value | Required |
| --- | --- | --- | --- | --- |
| `items` | `List<ElegantDescriptionItem>` | Key-value entries in display order | - | Yes |
| `modifier` | `Modifier` | Modifier applied once to the description list root | `Modifier` | No |
| `colors` | `ElegantDescriptionColors` | Theme-aware label, value, and divider colors | `ElegantDescriptionDefaults.colors()` | No |
| `labelWidth` | `Dp` | Fixed width of the label column; non-positive or non-finite values fall back to `ElegantDescriptionDefaults.DefaultLabelWidth` | `140.dp` | No |

### ElegantDescriptionItem Properties

| Property Name | Type | Description | Default Value | Required |
| --- | --- | --- | --- | --- |
| `label` | `String` | Term rendered in the label column | - | Yes |
| `value` | `String` | Definition rendered in the value column | - | Yes |
| `enabled` | `Boolean` | Whether the value renders in the primary value color; `false` renders it in the disabled color | `true` | No |

### ElegantDescriptionDefaults

| Member | Type | Description |
| --- | --- | --- |
| `RowMinHeight` | `Dp` | Default 36dp minimum key-value row height |
| `DefaultLabelWidth` | `Dp` | Default 140dp label-column width |
| `colors()` | `ElegantDescriptionColors` | Returns Light/Dark theme-aware description list colors |

### ElegantDescriptionColors

`ElegantDescriptionColors` contains `labelColor`, `valueColor`, `disabledValueColor`, and `dividerColor`. The defaults resolve `textSecondary`, `textPrimary`, `textTertiary`, and `borderDefault`. Start with `ElegantDescriptionDefaults.colors()` and use `copy(...)` only for a deliberate product-specific hierarchy.

## Advanced Usage

### Disabled Values in Context

`enabled = false` keeps the layout intact and dims only the value, which suits entries whose data is temporarily unavailable or revoked.

```kotlin
val repository = listOf(
    ElegantDescriptionItem(label = "Public", value = "Yes"),
    ElegantDescriptionItem(label = "Stars", value = "1,024"),
    ElegantDescriptionItem(label = "Deploy key", value = "Revoked", enabled = false),
)

ElegantDescription(items = repository)
```

### Custom Description Colors

```kotlin
val descriptionColors = ElegantDescriptionDefaults.colors().copy(
    labelColor = ElegantTheme.colors.textPrimary,
    dividerColor = ElegantTheme.colors.borderStrong,
)

val environment = listOf(
    ElegantDescriptionItem(label = "Platform", value = "Linux"),
    ElegantDescriptionItem(label = "Architecture", value = "arm64"),
)

ElegantDescription(
    items = environment,
    colors = descriptionColors,
)
```
