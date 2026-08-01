# Snackbar

`ElegantSnackbar` is a transient message surface for lightweight feedback that appears briefly and dismisses itself. An `ElegantSnackbarHostState` paired with an `ElegantSnackbarHost` animates the message in and out, auto-dismisses it after a preset duration, and suspends the calling coroutine until the message is dismissed.

<iframe id="demoIframe" style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; border: none;" src="../compose/index.html?id=snackbar" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>

## Import

```kotlin
import com.elegant.compose.ui.snackbar.ElegantSnackbar
import com.elegant.compose.ui.snackbar.ElegantSnackbarColors
import com.elegant.compose.ui.snackbar.ElegantSnackbarDefaults
import com.elegant.compose.ui.snackbar.ElegantSnackbarDuration
import com.elegant.compose.ui.snackbar.ElegantSnackbarHost
import com.elegant.compose.ui.snackbar.ElegantSnackbarHostState
```

## Basic Usage

The host/state pattern drives every snackbar. Create one `ElegantSnackbarHostState` per host with `remember`, place `ElegantSnackbarHost` pinned to the bottom of a screen or surface, and launch `showSnackbar` from a coroutine scope that outlives the host, such as `rememberCoroutineScope`. The host renders the message aligned to the bottom center of its own bounds.

```kotlin
val snackbarHostState = remember { ElegantSnackbarHostState() }
val scope = rememberCoroutineScope()

Box(Modifier.fillMaxSize()) {
    Column(Modifier.align(Alignment.Center)) {
        ElegantButton(
            onClick = {
                scope.launch {
                    snackbarHostState.showSnackbar("Message deleted")
                }
            },
        ) {
            Text("Show snackbar")
        }
    }
    ElegantSnackbarHost(
        hostState = snackbarHostState,
        modifier = Modifier.align(Alignment.BottomCenter),
    )
}
```

`showSnackbar` suspends until the message is dismissed, so the caller can resume a workflow right after the feedback leaves the screen.

## Durations

`ElegantSnackbarDuration` selects the auto-dismiss timing: `Short` dismisses after 4000ms, `Long` after 10000ms, and `Indefinite` never dismisses on a timer, staying until the action is clicked or the host leaves composition.

```kotlin
snackbarHostState.showSnackbar(
    message = "Saved automatically",
    duration = ElegantSnackbarDuration.Long,
)

snackbarHostState.showSnackbar(
    message = "Recording",
    duration = ElegantSnackbarDuration.Indefinite,
)
```

## Component States

A snackbar is a transient, non-focusable surface: the message preserves the semantics of its content, announces no interactive role, and never intercepts clicks. An optional `actionLabel` is announced as `Role.Button`, keeps a 48dp minimum touch target, and shows a ripple with the action content color. When hosted, clicking the action label dismisses the message immediately. Showing a new message replaces the current one, and the replaced call resumes as if it had been dismissed.

```kotlin
snackbarHostState.showSnackbar(
    message = "Item moved to trash",
    actionLabel = "Undo",
)

ElegantSnackbar(
    text = "Draft saved",
    actionLabel = "Retry",
    onActionClick = { /* retry the failed save */ },
)
```

The second example renders the surface alone for a custom layout; the hosted variant wires `onActionClick` to dismissal automatically.

## Properties

### ElegantSnackbarHost Properties

| Property Name | Type | Description | Default Value | Required |
| --- | --- | --- | --- | --- |
| `hostState` | `ElegantSnackbarHostState` | Hoisted state driving the shown message | - | Yes |
| `modifier` | `Modifier` | Modifier applied once to the host root | `Modifier` | No |
| `colors` | `ElegantSnackbarColors` | Theme-aware state colors | `ElegantSnackbarDefaults.colors()` | No |

### ElegantSnackbarHostState

| Member | Type | Description |
| --- | --- | --- |
| `showSnackbar(message, actionLabel, duration)` | `suspend fun` | Shows a message and suspends until it is dismissed by the timer or the action click |

### ElegantSnackbar Properties

| Property Name | Type | Description | Default Value | Required |
| --- | --- | --- | --- | --- |
| `text` | `String` | Transient message text | - | Yes |
| `modifier` | `Modifier` | Modifier applied once to the surface root | `Modifier` | No |
| `actionLabel` | `String?` | Optional action label shown after the message | `null` | No |
| `onActionClick` | `(() -> Unit)?` | Callback invoked when the action label is clicked; null renders the label without interaction | `null` | No |
| `colors` | `ElegantSnackbarColors` | Theme-aware state colors | `ElegantSnackbarDefaults.colors()` | No |

### ElegantSnackbarDuration Values

| Value | Behavior |
| --- | --- |
| `Short` | Auto-dismissed after 4000ms |
| `Long` | Auto-dismissed after 10000ms |
| `Indefinite` | Never auto-dismissed; only the action click dismisses |

### ElegantSnackbarDefaults

| Member | Type | Description |
| --- | --- | --- |
| `ShortDurationMillis` | `Long` | 4000ms auto-dismiss delay for `Short` |
| `LongDurationMillis` | `Long` | 10000ms auto-dismiss delay for `Long` |
| `AnimationDurationMillis` | `Int` | Standard 160ms slide-and-fade transition duration |
| `colors()` | `ElegantSnackbarColors` | Theme-aware Light/Dark colors |

### ElegantSnackbarColors

`ElegantSnackbarColors` contains the container color, the message content color, and the action content color. Start with `ElegantSnackbarDefaults.colors()` and use `copy(...)` only for a deliberate product-specific hierarchy.

## Advanced Usage

### Sequential Workflow

Because `showSnackbar` suspends until dismissal, a workflow can chain follow-up work behind feedback; the call resumes regardless of whether the message was dismissed by the timer or by the action click.

```kotlin
scope.launch {
    snackbarHostState.showSnackbar(
        message = "Download complete",
        actionLabel = "Open",
    )
    openDownload()
}
```

### Custom Colors

```kotlin
val baseColors = ElegantSnackbarDefaults.colors()

ElegantSnackbar(
    text = "Custom surface",
    colors = baseColors.copy(
        containerColor = ElegantTheme.colors.backgroundSubtle,
    ),
)
```
