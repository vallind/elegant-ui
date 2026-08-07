# Tooltip

`ElegantTooltipBox` explains a compact control with a lightweight popup that appears on hover, keyboard focus, or touch long-press. Use it for icon-only actions and shortcuts where permanent labels would add clutter; the tooltip never steals focus and stays clamped inside the window.

<iframe id="demoIframe" style="width: 100%; height: 460px; border: 1px solid var(--vp-c-divider); border-radius: 12px; display: block; background: var(--vp-c-bg-alt);" src="../compose/index.html?id=tooltip" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>

## Import

```kotlin
import com.elegant.compose.ui.tooltip.ElegantTooltip
import com.elegant.compose.ui.tooltip.ElegantTooltipBox
import com.elegant.compose.ui.tooltip.ElegantTooltipDefaults
import com.elegant.compose.ui.tooltip.ElegantTooltipPlacement
```

## Basic Usage

Wrap the control in `ElegantTooltipBox` and supply the tooltip content. Hover reveals the tooltip after `showDelayMillis`, keyboard focus reveals it immediately, and touch long-press reveals it immediately.

```kotlin
ElegantTooltipBox(
    tooltip = { ElegantTooltip(text = "Save changes") },
) {
    ElegantButton(onClick = { /* save */ }) {
        Text("Save")
    }
}
```

## Placements

`ElegantTooltipPlacement` places the tooltip above, below, or beside the anchor. `Start` and `End` are logical directions and mirror automatically in RTL layouts.

```kotlin
ElegantTooltipBox(
    tooltip = { ElegantTooltip(text = "Top placement") },
    placement = ElegantTooltipPlacement.Top,
) {
    ElegantButton(onClick = { /* action */ }) {
        Text("Top")
    }
}

ElegantTooltipBox(
    tooltip = { ElegantTooltip(text = "Start placement") },
    placement = ElegantTooltipPlacement.Start,
) {
    ElegantButton(onClick = { /* action */ }) {
        Text("Start")
    }
}
```

## Component States

The tooltip has no visual states of its own; the anchor keeps its own interaction states. Reveal timing follows a fixed precedence: hover waits for `showDelayMillis`, keyboard focus and touch long-press appear immediately, and releasing a long-press hides the tooltip immediately. When the pointer leaves or focus moves away, the tooltip hides after `hideDelayMillis`. A disabled anchor never reveals its tooltip.

```kotlin
ElegantTooltipBox(
    tooltip = { ElegantTooltip(text = "Unavailable") },
    enabled = false,
) {
    ElegantButton(onClick = {}, enabled = false) {
        Text("Archived")
    }
}
```

## Properties

### ElegantTooltipBox Properties

| Property Name | Type | Description | Default Value | Required |
| --- | --- | --- | --- | --- |
| `tooltip` | `@Composable () -> Unit` | Popup content shown near the anchor | - | Yes |
| `modifier` | `Modifier` | Modifier applied once to the anchor | `Modifier` | No |
| `enabled` | `Boolean` | Whether hover, focus, and long-press can reveal the tooltip | `true` | No |
| `placement` | `ElegantTooltipPlacement` | Logical placement around the anchor | `ElegantTooltipPlacement.Top` | No |
| `showDelayMillis` | `Long` | Hover reveal delay; negative values resolve to 0 | `600` | No |
| `hideDelayMillis` | `Long` | Leave and focus-loss hide delay; negative values resolve to 0 | `100` | No |
| `offset` | `Dp` | Gap between the anchor and the tooltip | `8.dp` | No |
| `content` | `@Composable () -> Unit` | Anchor content receiving the interactions | - | Yes |

### ElegantTooltip Properties

| Property Name | Type | Description | Default Value | Required |
| --- | --- | --- | --- | --- |
| `text` | `String` | Tooltip label | - | Yes |
| `modifier` | `Modifier` | Modifier applied once to the tooltip surface | `Modifier` | No |

### ElegantTooltipPlacement Values

| Value | Behavior |
| --- | --- |
| `Top` | Above the anchor, horizontally centered |
| `Bottom` | Below the anchor, horizontally centered |
| `Start` | Logical start edge; mirrors to the right in RTL |
| `End` | Logical end edge; mirrors to the left in RTL |

### ElegantTooltipDefaults

| Member | Type | Description |
| --- | --- | --- |
| `DefaultShowDelayMillis` | `Long` | 600ms hover reveal delay |
| `DefaultHideDelayMillis` | `Long` | 100ms leave and focus-loss hide delay |
| `DefaultOffset` | `Dp` | 8dp gap between the anchor and the tooltip |
| `MaxWidth` | `Dp` | 280dp maximum tooltip width before wrapping |

## Advanced Usage

### Custom Tooltip Content

The `tooltip` slot accepts any composable, so a tooltip can carry structure beyond one label.

```kotlin
ElegantTooltipBox(
    tooltip = {
        Column(horizontalAlignment = Alignment.Start) {
            Text("Keyboard shortcut", style = ElegantTheme.typography.labelMedium)
            Text("Ctrl + S", style = ElegantTheme.typography.labelSmall)
        }
    },
) {
    ElegantButton(onClick = { /* save */ }) {
        Text("Save")
    }
}
```

### Adjusting Reveal Timing

Shorten the hover delay and widen the gap when the anchored control is part of a fast workflow.

```kotlin
ElegantTooltipBox(
    tooltip = { ElegantTooltip(text = "Synced with the server") },
    showDelayMillis = 300,
    hideDelayMillis = 200,
    offset = 12.dp,
) {
    ElegantButton(onClick = { /* sync */ }) {
        Text("Sync")
    }
}
```
