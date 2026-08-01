# Popover

`ElegantPopover` shows a floating surface anchored to a trigger: clicking the trigger opens it, and clicking outside or pressing Escape dismisses it. Use it for contextual settings, menus, and quick actions that stay anchored to their control. The focusable popup moves keyboard focus into the popover when it opens and returns it to the trigger on dismissal, and the surface is a floating card without an arrow.

<iframe id="demoIframe" style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; border: none;" src="../compose/index.html?id=popover" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>

## Import

```kotlin
import com.elegant.compose.ui.popover.ElegantPopover
import com.elegant.compose.ui.popover.ElegantPopoverDefaults
import com.elegant.compose.ui.popover.ElegantPopoverPlacement
```

## Basic Usage

Wrap the trigger in `ElegantPopover` and supply the popover content. Clicking the trigger toggles the popover open; clicking outside the popup, pressing Escape, or using the platform back gesture dismisses it.

```kotlin
ElegantPopover(
    popover = {
        Column(verticalArrangement = Arrangement.spacedBy(ElegantSpacing.xs)) {
            Text("Settings", style = ElegantTheme.typography.labelMedium)
            Text("Notifications, appearance, and account preferences.")
        }
    },
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(ElegantRadius.sm))
            .background(ElegantTheme.colors.backgroundSubtle)
            .padding(horizontal = ElegantSpacing.lg, vertical = ElegantSpacing.sm),
    ) {
        Text("Open settings")
    }
}
```

## Placements

`ElegantPopoverPlacement` places the popover above, below, or beside the trigger. `Start` and `End` are logical directions and mirror automatically in RTL layouts.

```kotlin
ElegantPopover(
    popover = { Text("Top placement") },
    placement = ElegantPopoverPlacement.Top,
) {
    Text("Top")
}

ElegantPopover(
    popover = { Text("Start placement") },
    placement = ElegantPopoverPlacement.Start,
) {
    Text("Start")
}
```

## Component States

The popover has no visual states of its own. Opening is a pure toggle: clicking the trigger opens it, and the platform popup dismisses it on outside click, Escape, or back. Because the popup is focusable, keyboard focus moves into the popover when it opens and is restored to the trigger when it dismisses; interactive content inside the popover keeps normal focus behavior. A disabled trigger never opens its popover.

```kotlin
ElegantPopover(
    popover = { Text("Unavailable") },
    enabled = false,
) {
    Text("Archived")
}
```

## Properties

### ElegantPopover Properties

| Property Name | Type | Description | Default Value | Required |
| --- | --- | --- | --- | --- |
| `popover` | `@Composable () -> Unit` | Floating popup content shown near the trigger | - | Yes |
| `modifier` | `Modifier` | Modifier applied once to the trigger | `Modifier` | No |
| `enabled` | `Boolean` | Whether clicking the trigger can open the popover | `true` | No |
| `placement` | `ElegantPopoverPlacement` | Logical placement around the trigger | `ElegantPopoverPlacement.Bottom` | No |
| `offset` | `Dp` | Gap between the trigger and the popover | `8.dp` | No |
| `colors` | `ElegantPopoverColors` | Popover surface colors | `ElegantPopoverDefaults.colors()` | No |
| `content` | `@Composable () -> Unit` | Trigger content; clicking it toggles the popover | - | Yes |

### ElegantPopoverColors

| Property Name | Type | Description |
| --- | --- | --- |
| `containerColor` | `Color` | Popover surface background |
| `contentColor` | `Color` | Text and icon color inside the popover |
| `borderColor` | `Color` | Popover surface outline |

### ElegantPopoverPlacement Values

| Value | Behavior |
| --- | --- |
| `Top` | Above the trigger, horizontally centered |
| `Bottom` | Below the trigger, horizontally centered |
| `Start` | Logical start edge; mirrors to the right in RTL |
| `End` | Logical end edge; mirrors to the left in RTL |

### ElegantPopoverDefaults

| Member | Type | Description |
| --- | --- | --- |
| `MaxWidth` | `Dp` | 320dp maximum popover width before wrapping |
| `Offset` | `Dp` | 8dp gap between the trigger and the popover |
| `AnimationDurationMillis` | `Int` | 90ms popover entrance transition duration |

## Advanced Usage

### Interactive Popover Content

The `popover` slot accepts any composable; since the popup is focusable, controls inside it keep normal click and focus behavior.

```kotlin
ElegantPopover(
    popover = {
        Column(verticalArrangement = Arrangement.spacedBy(ElegantSpacing.md)) {
            Text("Clear all filters?", style = ElegantTheme.typography.labelMedium)
            ElegantButton(onClick = { /* confirm */ }) {
                Text("Confirm")
            }
        }
    },
) {
    Text("Manage")
}
```

### Adjusting Placement and Offset

Widen the gap with `offset` and pick a placement that fits the surrounding layout.

```kotlin
ElegantPopover(
    popover = { Text("Account settings") },
    placement = ElegantPopoverPlacement.End,
    offset = 12.dp,
) {
    Text("Account")
}
```
