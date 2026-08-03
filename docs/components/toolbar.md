# Toolbar

`ElegantToolbar` is an inline action strip that sits flush in the caller's layout. A flat, borderless surface fills the width of its container at a fixed 48dp height and hosts a row of icon actions. Unlike `ElegantFloatingToolbar` — a raised floating pill with fully rounded ends and medium elevation — the Toolbar adds no shadow, no rounding, and no positioning of its own: it is meant to sit at the top or bottom of a card, list, or editor, where borders and separators are the caller's choice.

<iframe id="demoIframe" style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; border: none;" src="../compose/index.html?id=toolbar" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>

## Import

```kotlin
import com.elegant.compose.ui.toolbar.ElegantToolbar
import com.elegant.compose.ui.toolbar.ElegantToolbarColors
import com.elegant.compose.ui.toolbar.ElegantToolbarDefaults
```

## Basic Usage

Give each action a short, action-oriented `contentDescription` and keep the icon itself decorative. Space adjacent actions with `ElegantToolbarDefaults.ItemGap`.

```kotlin
ElegantToolbar {
    ElegantIconButton(
        onClick = { /* Bold */ },
        contentDescription = "Bold",
    ) {
        Icon(Icons.Default.FormatBold, contentDescription = null)
    }
    ElegantIconButton(
        onClick = { /* Italic */ },
        contentDescription = "Italic",
    ) {
        Icon(Icons.Default.FormatItalic, contentDescription = null)
    }
    ElegantIconButton(
        onClick = { /* Underline */ },
        contentDescription = "Underline",
    ) {
        Icon(Icons.Default.FormatUnderlined, contentDescription = null)
    }
}
```

## Component States

Toolbar is a non-interactive container: it has no pressed, focused, selected, disabled, or loading state of its own and contributes no semantics. Actions inside the strip own their interaction and states, and the strip provides their content color through `LocalContentColor`.

```kotlin
ElegantToolbar {
    ElegantIconButton(
        onClick = { /* Align left */ },
        contentDescription = "Align left",
    ) {
        Icon(Icons.Default.FormatAlignLeft, contentDescription = null)
    }
    ElegantIconButton(
        onClick = { /* Align center */ },
        contentDescription = "Align center",
    ) {
        Icon(Icons.Default.FormatAlignCenter, contentDescription = null)
    }
    ElegantIconButton(
        onClick = {},
        contentDescription = "Align right",
        enabled = false,
    ) {
        Icon(Icons.Default.FormatAlignRight, contentDescription = null)
    }
}
```

## Properties

### ElegantToolbar Properties

| Property Name | Type | Description | Default Value | Required |
| --- | --- | --- | --- | --- |
| `modifier` | `Modifier` | Modifier applied once to the strip root | `Modifier` | No |
| `colors` | `ElegantToolbarColors` | Theme-aware strip and content colors | `ElegantToolbarDefaults.colors()` | No |
| `content` | `@Composable RowScope.() -> Unit` | Row of actions; spacing between actions is the caller's responsibility | - | Yes |

### ElegantToolbarDefaults

| Member | Type | Description |
| --- | --- | --- |
| `Height` | `Dp` | Fixed 48dp strip height |
| `HorizontalPadding` | `Dp` | 4dp horizontal inset on each side of the strip |
| `ItemGap` | `Dp` | Recommended 4dp gap between adjacent actions |
| `colors()` | `ElegantToolbarColors` | Returns Light/Dark theme-aware colors |

### ElegantToolbarColors

`ElegantToolbarColors` contains `containerColor`, `contentColor`, and `dividerColor`. Start with `ElegantToolbarDefaults.colors()` and use `copy(...)` only for a deliberate product-specific hierarchy.

## Advanced Usage

### Inline in an Editor

The strip fills the width of its container, so it flows naturally inside a card or editor between other content.

```kotlin
Column {
    Text("Selected paragraph of text")
    ElegantToolbar {
        ElegantIconButton(
            onClick = { /* Copy */ },
            contentDescription = "Copy",
        ) {
            Icon(Icons.Default.Copy, contentDescription = null)
        }
        ElegantIconButton(
            onClick = { /* Paste */ },
            contentDescription = "Paste",
        ) {
            Icon(Icons.Default.ContentPaste, contentDescription = null)
        }
    }
}
```

### Separating Actions

Draw optional separators between action groups with the theme-aware `dividerColor`.

```kotlin
val toolbarColors = ElegantToolbarDefaults.colors()

ElegantToolbar {
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
val baseColors = ElegantToolbarDefaults.colors()

ElegantToolbar(
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
