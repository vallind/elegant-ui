# Divider

`ElegantDivider` is a refined, non-interactive boundary for structuring related content without adding visual weight. It supports horizontal and vertical orientation, solid and dashed treatments, two semantic emphasis levels, and theme-aware colors. `ElegantLabeledDivider` adds logical label placement with responsive and RTL-safe behavior.

<iframe id="demoIframe" style="width: 100%; height: 460px; border: 1px solid var(--vp-c-divider); border-radius: 12px; display: block; background: var(--vp-c-bg-alt);" src="../compose/index.html?id=divider" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>

## Import

```kotlin
import com.elegant.compose.ui.divider.ElegantDivider
import com.elegant.compose.ui.divider.ElegantDividerColors
import com.elegant.compose.ui.divider.ElegantDividerDefaults
import com.elegant.compose.ui.divider.ElegantDividerEmphasis
import com.elegant.compose.ui.divider.ElegantDividerLabelPosition
import com.elegant.compose.ui.divider.ElegantDividerOrientation
import com.elegant.compose.ui.divider.ElegantDividerStyle
import com.elegant.compose.ui.divider.ElegantLabeledDivider
```

## Basic Usage

A horizontal subtle divider fills the available width and remains decorative by default.

```kotlin
Column {
    Text("Account")
    ElegantDivider()
    Text("Security")
}
```

## Orientation and Stroke

Use `Horizontal` between vertically stacked regions. A `Vertical` divider fills its bounded parent height, so give the containing row or the divider an explicit height. `Dashed` communicates a quieter provisional or secondary boundary without changing measurement.

```kotlin
Row(
    modifier = Modifier.height(72.dp),
    verticalAlignment = Alignment.CenterVertically,
) {
    Text("Android", modifier = Modifier.weight(1f))
    ElegantDivider(
        modifier = Modifier.fillMaxHeight(),
        orientation = ElegantDividerOrientation.Vertical,
        emphasis = ElegantDividerEmphasis.Strong,
    )
    Text("Web", modifier = Modifier.weight(1f))
}

ElegantDivider(style = ElegantDividerStyle.Dashed)
```

## Labeled Dividers

`ElegantLabeledDivider` is intentionally horizontal-only. `Start` and `End` are logical positions and mirror in RTL. The component owns line-to-label spacing, content color, and default label typography while the slot owns its internal drawing.

```kotlin
ElegantLabeledDivider(
    labelPosition = ElegantDividerLabelPosition.Center,
    emphasis = ElegantDividerEmphasis.Strong,
) {
    Text("Recent activity")
}
```

## Component States

Divider has no pressed, focused, selected, disabled, or loading state because it is non-interactive. `Subtle` separates related content; `Strong` separates distinct regions. A plain divider is omitted from the semantics tree unless a localized `contentDescription` is supplied.

With a labeled divider, `contentDescription = null` preserves the label slot's semantics, a non-blank value replaces descendants with one localized description, and an empty value makes the entire divider decorative.

```kotlin
ElegantDivider(
    contentDescription = "Next section",
    emphasis = ElegantDividerEmphasis.Strong,
)

ElegantLabeledDivider(contentDescription = "") {
    Text("Decorative ornament")
}
```

Non-positive or non-finite thickness values fall back to `ElegantDividerDefaults.Thickness`. Negative or non-finite label gaps fall back to `ElegantDividerDefaults.LabelGap`. Under narrow constraints, labeled line segments collapse before custom label content is clipped.

## Properties

### ElegantDivider Properties

| Property Name | Type | Description | Default Value | Required |
| --- | --- | --- | --- | --- |
| `modifier` | `Modifier` | Modifier applied once to the divider root | `Modifier` | No |
| `contentDescription` | `String?` | Optional localized boundary description | `null` | No |
| `orientation` | `ElegantDividerOrientation` | Horizontal or vertical layout direction | `ElegantDividerOrientation.Horizontal` | No |
| `style` | `ElegantDividerStyle` | Continuous or segmented stroke treatment | `ElegantDividerStyle.Solid` | No |
| `emphasis` | `ElegantDividerEmphasis` | Semantic separator prominence | `ElegantDividerEmphasis.Subtle` | No |
| `colors` | `ElegantDividerColors` | Theme-aware line and label colors | `ElegantDividerDefaults.colors(emphasis)` | No |
| `thickness` | `Dp` | Visible stroke thickness | `ElegantDividerDefaults.Thickness` | No |

### ElegantLabeledDivider Properties

| Property Name | Type | Description | Default Value | Required |
| --- | --- | --- | --- | --- |
| `modifier` | `Modifier` | Modifier applied once to the labeled-divider root | `Modifier` | No |
| `contentDescription` | `String?` | Optional localized semantic override; null preserves content semantics | `null` | No |
| `labelPosition` | `ElegantDividerLabelPosition` | Logical placement between line segments | `ElegantDividerLabelPosition.Center` | No |
| `style` | `ElegantDividerStyle` | Continuous or segmented stroke treatment | `ElegantDividerStyle.Solid` | No |
| `emphasis` | `ElegantDividerEmphasis` | Semantic separator prominence | `ElegantDividerEmphasis.Subtle` | No |
| `colors` | `ElegantDividerColors` | Theme-aware line and label colors | `ElegantDividerDefaults.colors(emphasis)` | No |
| `thickness` | `Dp` | Visible stroke thickness | `ElegantDividerDefaults.Thickness` | No |
| `labelGap` | `Dp` | Breathing room on both sides of the label | `ElegantDividerDefaults.LabelGap` | No |
| `content` | `@Composable () -> Unit` | Label text, icon, or custom content | - | Yes |

### ElegantDividerOrientation Values

| Value | Behavior |
| --- | --- |
| `Horizontal` | Fills available width and separates vertically stacked content |
| `Vertical` | Fills bounded height and separates horizontally arranged content |

### ElegantDividerStyle Values

| Value | Behavior |
| --- | --- |
| `Solid` | Draws one continuous boundary |
| `Dashed` | Draws fixed 8dp segments separated by 4dp gaps |

### ElegantDividerEmphasis Values

| Value | Line Role | Label Role |
| --- | --- | --- |
| `Subtle` | `borderDefault` | `textSecondary` |
| `Strong` | `borderStrong` | `textPrimary` |

### ElegantDividerLabelPosition Values

`Start`, `Center`, and `End` use logical layout direction. Center uses equal line weights; start and end remove the line segment outside the label.

### ElegantDividerDefaults

| Member | Type | Description |
| --- | --- | --- |
| `Thickness` | `Dp` | Default 1dp hairline thickness |
| `LabelGap` | `Dp` | Default 12dp spacing on each side of label content |
| `DashLength` | `Dp` | Fixed 8dp dashed-segment length |
| `DashGap` | `Dp` | Fixed 4dp space between dashed segments |
| `colors(emphasis)` | `ElegantDividerColors` | Returns Light/Dark theme-aware colors for the selected emphasis |

### ElegantDividerColors

`ElegantDividerColors` contains `lineColor` and `contentColor`. Start with `ElegantDividerDefaults.colors(emphasis)` and use `copy(...)` only for a deliberate product-specific hierarchy.

## Advanced Usage

### Inset Content Boundary

Apply logical padding to create an inset that mirrors automatically in RTL.

```kotlin
ElegantDivider(
    modifier = Modifier.padding(start = 52.dp),
    contentDescription = "Next team member",
)
```

### Custom Section Tone

```kotlin
val baseColors = ElegantDividerDefaults.colors(ElegantDividerEmphasis.Strong)

ElegantLabeledDivider(
    colors = baseColors.copy(
        lineColor = Color(0xFF6C4EFF),
        contentColor = Color(0xFF5840D6),
    ),
) {
    Text("Release candidate")
}
```
