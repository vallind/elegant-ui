# Drawer

`ElegantDrawer` is a modal overlay that slides a side panel in over a dimming scrim. Use it for application navigation, filters, and settings that must not leave the current screen.

<iframe id="demoIframe" style="width: 100%; height: 460px; border: 1px solid var(--vp-c-divider); border-radius: 12px; display: block; background: var(--vp-c-bg-alt);" src="../compose/index.html?id=drawer" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>

## Import

```kotlin
import com.elegant.compose.ui.drawer.ElegantDrawer
import com.elegant.compose.ui.drawer.ElegantDrawerColors
import com.elegant.compose.ui.drawer.ElegantDrawerDefaults
import com.elegant.compose.ui.drawer.ElegantDrawerPlacement
```

## Basic Usage

The drawer is a controlled component: pass `visible` to show it and `onDismissRequest` to receive every dismissal. The panel is 280dp wide by default, slides in over a fading scrim, and hosts vertically scrollable content.

```kotlin
var visible by remember { mutableStateOf(false) }

ElegantButton(onClick = { visible = true }) {
    Text("Open navigation")
}

ElegantDrawer(
    visible = visible,
    onDismissRequest = { visible = false },
) {
    Column {
        Text("Inbox")
        Text("Starred")
        Text("Sent mail")
    }
}
```

## Placement

`ElegantDrawerPlacement` picks the logical edge: `Start` draws the panel at the left edge in LTR layouts and mirrors to the right in RTL; `End` is the opposite.

```kotlin
ElegantDrawer(
    visible = visible,
    onDismissRequest = { visible = false },
    placement = ElegantDrawerPlacement.End,
) {
    Text("End drawer")
}
```

## Component States

Dismissal: clicking the scrim, pressing Escape, or pressing the system back key (Android) invokes `onDismissRequest`; set `visible` to false to close the drawer programmatically.

Focus: while visible, the drawer captures keyboard focus inside the panel and restores it to the caller's window when the drawer closes.

The panel rests on the raised surface with the primary text color and casts a medium shadow. Content scrolls vertically when it exceeds the available height; the drawer has no disabled state because the trigger owns the enabled behavior.

```kotlin
ElegantDrawer(
    visible = visible,
    onDismissRequest = { visible = false },
) {
    Column {
        repeat(20) { index ->
            Text("Item $index")
        }
    }
}
```

## Properties

### ElegantDrawer Properties

| Property Name | Type | Description | Default Value | Required |
| --- | --- | --- | --- | --- |
| `visible` | `Boolean` | Whether the drawer is shown | - | Yes |
| `onDismissRequest` | `() -> Unit` | Invoked on scrim click, Escape, back key, or programmatic dismissal | - | Yes |
| `modifier` | `Modifier` | Modifier applied once to the drawer panel | `Modifier` | No |
| `placement` | `ElegantDrawerPlacement` | Logical edge the panel slides in from | `ElegantDrawerPlacement.Start` | No |
| `width` | `Dp` | Panel width | `ElegantDrawerDefaults.Width` | No |
| `colors` | `ElegantDrawerColors` | Theme-aware scrim, container, and content colors | `ElegantDrawerDefaults.colors()` | No |
| `content` | `@Composable () -> Unit` | Drawer panel content | - | Yes |

### ElegantDrawerPlacement Values

| Value | Behavior |
| --- | --- |
| `Start` | Logical start edge; left in LTR, right in RTL |
| `End` | Logical end edge; right in LTR, left in RTL |

### ElegantDrawerDefaults

| Member | Type | Description |
| --- | --- | --- |
| `Width` | `Dp` | 280dp default panel width |
| `ScrimAlpha` | `Float` | 0.4 resting alpha of the scrim overlay |
| `AnimationDurationMillis` | `Int` | Emphasized 220ms slide and fade duration |
| `colors()` | `ElegantDrawerColors` | Theme-aware scrim, container, and content colors |

### ElegantDrawerColors

`ElegantDrawerColors` holds the scrim overlay color, the panel container color, and the content color provided to the panel through `LocalContentColor`. Start from `ElegantDrawerDefaults.colors()` and use `copy(...)` only for a deliberate product-specific hierarchy.

## Advanced Usage

### Navigation Drawer Composition

Combine `ElegantAvatar`, `ElegantDivider`, and text items to build a navigation drawer with a profile header.

```kotlin
ElegantDrawer(
    visible = visible,
    onDismissRequest = { visible = false },
) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            ElegantAvatar(name = "Maya Chen", initials = "MC")
            Column(Modifier.padding(start = ElegantSpacing.lg)) {
                Text("Maya Chen")
                Text("maya@elegant.com")
            }
        }
        ElegantDivider(Modifier.padding(vertical = ElegantSpacing.md))
        Text("Inbox")
        Text("Starred")
        Text("Sent mail")
    }
}
```

### Custom Colors

```kotlin
val baseColors = ElegantDrawerDefaults.colors()

ElegantDrawer(
    visible = visible,
    onDismissRequest = { visible = false },
    colors = baseColors.copy(
        containerColor = Color(0xFF6C4EFF),
        contentColor = Color.White,
    ),
) {
    Text("Custom surface")
}
```
