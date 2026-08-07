# Scaffold

`ElegantScaffold` lays out a screen shell with pinned top and bottom bars, a floating action button, and a snackbar host. It measures the bars with `onSizeChanged` and hands the resulting insets to the content slot through `PaddingValues`, so scrollable content never hides behind the chrome.

<iframe id="demoIframe" style="width: 100%; height: 460px; border: 1px solid var(--vp-c-divider); border-radius: 12px; display: block; background: var(--vp-c-bg-alt);" src="../compose/index.html?id=scaffold" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>

## Import

```kotlin
import com.elegant.compose.ui.scaffold.ElegantScaffold
import com.elegant.compose.ui.scaffold.ElegantScaffoldColors
import com.elegant.compose.ui.scaffold.ElegantScaffoldDefaults
```

## Basic Usage

The top bar is pinned to the top edge and the bottom bar to the bottom edge; both are measured automatically, so bar height changes propagate to the content insets without caller bookkeeping. The content slot fills the area underneath the bars and receives a `PaddingValues` whose top equals the measured top bar height and whose bottom equals the measured bottom bar height.

```kotlin
ElegantScaffold(
    topBar = {
        ElegantNavbar(
            title = {
                Text(
                    text = "Home",
                    style = ElegantTheme.typography.titleMedium,
                )
            },
        )
    },
) { innerPadding ->
    Column(
        modifier = Modifier.padding(innerPadding),
    ) {
        Text(
            text = "Content insets below the measured top bar height.",
            modifier = Modifier.padding(ElegantSpacing.xl),
            style = ElegantTheme.typography.bodyMedium,
        )
    }
}
```

## Component States

Every slot is optional: dropping a bar removes its inset, and a scaffold without bars leaves the content full-bleed. The floating action button floats above the bottom bar at the bottom end, and the snackbar host floats above the bottom bar with 8dp of clearance. The scaffold defines no role, owns no focus, and never merges or clears the semantics of its content, so interactive children keep their own accessibility contract.

```kotlin
ElegantScaffold(
    floatingActionButton = {
        ElegantFloatingActionButton(onClick = { /* compose a message */ }) {
            Icon(Icons.Default.Edit, contentDescription = null)
        }
    },
) { innerPadding ->
    Column(
        modifier = Modifier.padding(innerPadding),
    ) {
        Text(
            text = "No bars, no insets: content fills the canvas.",
            modifier = Modifier.padding(ElegantSpacing.xl),
            style = ElegantTheme.typography.bodyMedium,
        )
    }
}
```

## Properties

### ElegantScaffold Properties

| Property Name | Type | Description | Default Value | Required |
| --- | --- | --- | --- | --- |
| `modifier` | `Modifier` | Modifier applied once to the scaffold root | `Modifier` | No |
| `topBar` | `(@Composable () -> Unit)?` | Content pinned to the top edge and measured for the content top inset | `null` | No |
| `bottomBar` | `(@Composable () -> Unit)?` | Content pinned to the bottom edge and measured for the content bottom inset | `null` | No |
| `floatingActionButton` | `(@Composable () -> Unit)?` | Content floating above the bottom bar at the bottom end | `null` | No |
| `snackbarHost` | `(@Composable () -> Unit)?` | Content floating above the bottom bar, centered horizontally | `null` | No |
| `colors` | `ElegantScaffoldColors` | Theme-aware background and content colors | `ElegantScaffoldDefaults.colors()` | No |
| `content` | `@Composable (PaddingValues) -> Unit` | Primary content; receives insets from the measured bar heights | - | Yes |

### ElegantScaffoldDefaults

| Member | Type | Description |
| --- | --- | --- |
| `FloatingActionButtonMargin` | `Dp` | 16dp gap between the floating action button and the screen edge and bottom bar |
| `SnackbarHostMargin` | `Dp` | 8dp gap between the snackbar host and the bottom bar |
| `colors()` | `ElegantScaffoldColors` | Theme-aware Light/Dark colors |

### ElegantScaffoldColors

`ElegantScaffoldColors` contains the background color (canvas painted behind every layer) and the content color (provided to the content slot through `LocalContentColor`). Start with `ElegantScaffoldDefaults.colors()` and use `copy(...)` only for a deliberate product-specific hierarchy.

## Advanced Usage

### Full Screen Shell

Combine the navbar, a bottom navigation bar, a floating action button, and a snackbar host into one screen. The snackbar host draws below the floating action button, and `showSnackbar` suspends until dismissal so follow-up work chains behind the feedback.

```kotlin
val snackbarHostState = remember { ElegantSnackbarHostState() }
val scope = rememberCoroutineScope()

ElegantScaffold(
    topBar = {
        ElegantNavbar(
            title = {
                Text(
                    text = "Library",
                    style = ElegantTheme.typography.titleMedium,
                )
            },
        )
    },
    bottomBar = {
        ElegantNavigationBar(
            selectedIndex = 0,
            onSelect = { /* switch destination */ },
            items = listOf(
                ElegantNavigationBarItem("Home"),
                ElegantNavigationBarItem("Library"),
                ElegantNavigationBarItem("Settings"),
            ),
        )
    },
    floatingActionButton = {
        ElegantFloatingActionButton(
            onClick = {
                scope.launch {
                    snackbarHostState.showSnackbar("Message drafted")
                }
            },
        ) {
            Icon(Icons.Default.Edit, contentDescription = null)
        }
    },
    snackbarHost = {
        ElegantSnackbarHost(hostState = snackbarHostState)
    },
) { innerPadding ->
    Column(
        modifier = Modifier.padding(innerPadding),
    ) {
        Text(
            text = "Navbar, scrollable content, navigation bar, FAB, and snackbar share one shell.",
            modifier = Modifier.padding(ElegantSpacing.xl),
            style = ElegantTheme.typography.bodyMedium,
        )
    }
}
```
