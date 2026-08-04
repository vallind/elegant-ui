# BottomSheet

A modal sheet that slides in from the bottom edge over a scrim. Use it for share sheets, actions tied to a selection, or any flow that benefits from a wide, near-full-height surface on mobile.

<iframe id="demoIframe" src="../compose/index.html?id=bottom-sheet" style="width: 100%; height: 320px; border: 1px solid var(--vp-c-divider); border-radius: 8px;"></iframe>

## Import

```kotlin
import com.elegant.compose.ui.bottomsheet.ElegantBottomSheet
import com.elegant.compose.ui.bottomsheet.ElegantBottomSheetDefaults
```

## Basic Usage

`ElegantBottomSheet` is fully controlled: the caller owns the visibility state and every dismissal route invokes `onDismissRequest`.

```kotlin
var visible by remember { mutableStateOf(false) }

ElegantButton(onClick = { visible = true }) {
    Text("Share")
}

ElegantBottomSheet(
    visible = visible,
    onDismissRequest = { visible = false },
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Text("Share this file", style = ElegantTheme.typography.titleMedium)
        Text(
            "Choose where to send it. The sheet keeps focus while open.",
            style = ElegantTheme.typography.bodyMedium,
            color = ElegantTheme.colors.textSecondary,
        )
        ElegantButton(
            onClick = { visible = false },
            style = ElegantButtonStyle.Secondary,
        ) {
            Text("Cancel")
        }
    }
}
```

The sheet is width-capped at `ElegantBottomSheetDefaults.MaxWidth`, keeps the top corners rounded, centers a drag handle above the content, and scrolls its content when it grows tall.

## Component States

- **Visible**: the sheet slides in from the bottom edge while the scrim fades in.
- **Dismissing**: scrim clicks, back, and Escape all invoke `onDismissRequest`; the sheet never closes itself.
- **Scrollable**: content taller than the screen scrolls inside the rounded panel.

## Properties

| Property Name | Type | Description | Default Value | Required |
| :--- | :--- | :--- | :--- | :--- |
| `visible` | `Boolean` | Whether the sheet is shown; the caller owns the dismiss state. | — | Yes |
| `onDismissRequest` | `() -> Unit` | Called for scrim click, back, or Escape. | — | Yes |
| `modifier` | `Modifier` | Applied to the sheet panel. | `Modifier` | No |
| `colors` | `ElegantBottomSheetColors` | Theme-aware scrim, surface, content, and handle colors. | `ElegantBottomSheetDefaults.colors()` | No |
| `content` | `@Composable ColumnScope.() -> Unit` | Sheet content below the drag handle. | — | Yes |

### Colors

| Property Name | Type | Description |
| :--- | :--- | :--- |
| `scrimColor` | `Color` | Scrim overlay color. |
| `containerColor` | `Color` | Sheet surface color. |
| `contentColor` | `Color` | Locally provided content color. |
| `handleColor` | `Color` | Drag-handle indicator color. |

## Advanced Usage

### Scrollable content

The sheet column is scrollable by default; content taller than the screen scrolls inside the rounded panel:

```kotlin
ElegantBottomSheet(visible = visible, onDismissRequest = { visible = false }) {
    Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
        repeat(20) { index ->
            Text("Row $index", modifier = Modifier.padding(vertical = 4.dp))
        }
    }
}
```

### Custom colors

```kotlin
val colors = ElegantBottomSheetDefaults.colors().copy(
    containerColor = ElegantTheme.colors.surfaceRaised,
    handleColor = ElegantTheme.colors.borderStrong,
)
```
