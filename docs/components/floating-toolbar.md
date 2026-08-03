# FloatingToolbar

`ElegantFloatingToolbar` is a floating action strip that hovers above content. A raised pill with fully rounded ends and medium elevation hosts a row of icon actions; the row wraps its content and grows in height from a 48dp minimum. The component provides no positioning of its own — wrap it in a `Box` to float it over a selection, a paragraph, or an editor.

<iframe id="demoIframe" style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; border: none;" src="../compose/index.html?id=floating-toolbar" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>

## Import

```kotlin
import com.elegant.compose.ui.floatingtoolbar.ElegantFloatingToolbar
import com.elegant.compose.ui.floatingtoolbar.ElegantFloatingToolbarColors
import com.elegant.compose.ui.floatingtoolbar.ElegantFloatingToolbarDefaults
```

## Basic Usage

Give each action a short, action-oriented `contentDescription` and keep the icon itself decorative. Space adjacent actions with `ElegantFloatingToolbarDefaults.ItemGap`.

```kotlin
ElegantFloatingToolbar {
    ElegantIconButton(
        onClick = { /* Edit */ },
        contentDescription = "Edit",
    ) {
        Icon(Icons.Default.Edit, contentDescription = null)
    }
    ElegantIconButton(
        onClick = { /* Delete */ },
        contentDescription = "Delete",
    ) {
        Icon(Icons.Default.Delete, contentDescription = null)
    }
}
```

## Component States

FloatingToolbar is a non-interactive container: it has no pressed, focused, selected, disabled, or loading state of its own and contributes no semantics. Actions inside the strip own their interaction and states, and the pill provides their content color through `LocalContentColor`.

```kotlin
ElegantFloatingToolbar {
    ElegantIconButton(
        onClick = { /* Retry */ },
        contentDescription = "Retry",
    ) {
        Icon(Icons.Default.Refresh, contentDescription = null)
    }
    ElegantIconButton(
        onClick = {},
        contentDescription = "Delete item",
        enabled = false,
    ) {
        Icon(Icons.Default.Delete, contentDescription = null)
    }
}
```

## Properties

### ElegantFloatingToolbar Properties

| Property Name | Type | Description | Default Value | Required |
| --- | --- | --- | --- | --- |
| `modifier` | `Modifier` | Modifier applied once to the pill root | `Modifier` | No |
| `colors` | `ElegantFloatingToolbarColors` | Theme-aware pill and content colors | `ElegantFloatingToolbarDefaults.colors()` | No |
| `content` | `@Composable RowScope.() -> Unit` | Row of actions; spacing between actions is the caller's responsibility | - | Yes |

### ElegantFloatingToolbarDefaults

| Member | Type | Description |
| --- | --- | --- |
| `Height` | `Dp` | Minimum 48dp pill height |
| `HorizontalPadding` | `Dp` | 4dp horizontal inset on each side of the pill |
| `ItemGap` | `Dp` | Recommended 4dp gap between adjacent actions |
| `colors()` | `ElegantFloatingToolbarColors` | Returns Light/Dark theme-aware colors |

### ElegantFloatingToolbarColors

`ElegantFloatingToolbarColors` contains `containerColor`, `contentColor`, and `dividerColor`. Start with `ElegantFloatingToolbarDefaults.colors()` and use `copy(...)` only for a deliberate product-specific hierarchy.

## Advanced Usage

### Floating Over Content

The strip wraps its content and positions nothing; wrap it in a `Box` and use `Modifier.align` to float it over a surface or a paragraph.

```kotlin
Box {
    Text("Selected paragraph of text")
    ElegantFloatingToolbar(
        modifier = Modifier.align(Alignment.TopCenter),
    ) {
        ElegantIconButton(
            onClick = { /* Copy */ },
            contentDescription = "Copy",
        ) {
            Icon(Icons.Default.Copy, contentDescription = null)
        }
    }
}
```

### Separating Actions

Draw optional separators between action groups with the theme-aware `dividerColor`.

```kotlin
val toolbarColors = ElegantFloatingToolbarDefaults.colors()

ElegantFloatingToolbar {
    ElegantIconButton(
        onClick = { /* Cut */ },
        contentDescription = "Cut",
    ) {
        Icon(Icons.Default.ContentCut, contentDescription = null)
    }
    ElegantDivider(
        modifier = Modifier
            .height(24.dp)
            .padding(horizontal = 4.dp),
        orientation = ElegantDividerOrientation.Vertical,
        colors = ElegantDividerDefaults.colors(ElegantDividerEmphasis.Subtle).copy(
            lineColor = toolbarColors.dividerColor,
        ),
    )
    ElegantIconButton(
        onClick = { /* Paste */ },
        contentDescription = "Paste",
    ) {
        Icon(Icons.Default.ContentPaste, contentDescription = null)
    }
}
```

### Custom Colors

```kotlin
val baseColors = ElegantFloatingToolbarDefaults.colors()

ElegantFloatingToolbar(
    colors = baseColors.copy(
        containerColor = Color(0xFF17181A),
        contentColor = Color(0xFFF6F7F9),
        dividerColor = Color(0xFF343740),
    ),
) {
    ElegantIconButton(
        onClick = { /* Edit */ },
        contentDescription = "Edit",
    ) {
        Icon(Icons.Default.Edit, contentDescription = null)
    }
}
```
