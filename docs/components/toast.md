# Toast

`ElegantToast` is a top-positioned transient message surface for lightweight feedback that appears briefly and dismisses itself. Unlike `ElegantSnackbar`, which anchors to the bottom of a screen, a toast slides down from the top, supports a title with an optional description, and offers a close action. An `ElegantToastHostState` paired with an `ElegantToastHost` animates the message in and out, auto-dismisses it after a preset duration, and suspends the calling coroutine until the message is dismissed.

<iframe id="demoIframe" style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; border: none;" src="../compose/index.html?id=toast" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>

## Import

```kotlin
import com.elegant.compose.ui.toast.ElegantToast
import com.elegant.compose.ui.toast.ElegantToastColors
import com.elegant.compose.ui.toast.ElegantToastDefaults
import com.elegant.compose.ui.toast.ElegantToastDuration
import com.elegant.compose.ui.toast.ElegantToastHost
import com.elegant.compose.ui.toast.ElegantToastHostState
```

## Basic Usage

The host/state pattern drives every toast. Create one `ElegantToastHostState` per host with `remember`, place `ElegantToastHost` pinned to the top of a screen or surface, and launch `showToast` from a coroutine scope that outlives the host, such as `rememberCoroutineScope`. The host renders the message aligned to the top center of its own bounds — the opposite anchor of `ElegantSnackbarHost`, which pins messages to the bottom.

```kotlin
val toastHostState = remember { ElegantToastHostState() }
val scope = rememberCoroutineScope()

Box(Modifier.fillMaxSize()) {
    Column(Modifier.align(Alignment.Center)) {
        ElegantButton(
            onClick = {
                scope.launch {
                    toastHostState.showToast("Changes saved")
                }
            },
        ) {
            Text("Show toast")
        }
    }
    ElegantToastHost(
        hostState = toastHostState,
        modifier = Modifier.align(Alignment.TopCenter),
    )
}
```

`showToast` suspends until the message is dismissed, so the caller can resume a workflow right after the feedback leaves the screen.

## Durations

`ElegantToastDuration` selects the auto-dismiss timing: `Short` dismisses after 4000ms, `Long` after 10000ms, and `Indefinite` never dismisses on a timer, staying until the close action is clicked or the host leaves composition.

```kotlin
toastHostState.showToast(
    title = "Saved automatically",
    duration = ElegantToastDuration.Long,
)

toastHostState.showToast(
    title = "Uploading",
    description = "This file stays until you close it.",
    duration = ElegantToastDuration.Indefinite,
)
```

## Component States

A toast is a transient, non-focusable surface: the title and description preserve the semantics of their text and announce no interactive role. The optional close action is announced as `Role.Button` with the content description `"Close"`, keeps a 48dp minimum touch target, and shows a ripple with the close icon color. When hosted, clicking the close action dismisses the message immediately. Showing a new message replaces the current one, and the replaced call resumes as if it had been dismissed.

```kotlin
toastHostState.showToast(
    title = "Download complete",
    description = "The file is ready in your library.",
)

ElegantToast(
    title = "Draft saved",
    description = "Synced a moment ago.",
    onClose = { /* keep a custom dismissal flow */ },
)
```

The second example renders the surface alone for a custom layout; the hosted variant wires `onClose` to dismissal automatically.

## Properties

### ElegantToastHost Properties

| Property Name | Type | Description | Default Value | Required |
| --- | --- | --- | --- | --- |
| `hostState` | `ElegantToastHostState` | Hoisted state driving the shown message | - | Yes |
| `modifier` | `Modifier` | Modifier applied once to the host root | `Modifier` | No |
| `colors` | `ElegantToastColors` | Theme-aware state colors | `ElegantToastDefaults.colors()` | No |

### ElegantToastHostState

| Member | Type | Description |
| --- | --- | --- |
| `showToast(title, description, duration)` | `suspend fun` | Shows a message and suspends until it is dismissed by the timer or the close action |

### ElegantToast Properties

| Property Name | Type | Description | Default Value | Required |
| --- | --- | --- | --- | --- |
| `title` | `String` | Title text of the message | - | Yes |
| `modifier` | `Modifier` | Modifier applied once to the surface root | `Modifier` | No |
| `description` | `String?` | Optional supporting text; blank values hide the description | `null` | No |
| `colors` | `ElegantToastColors` | Theme-aware state colors | `ElegantToastDefaults.colors()` | No |
| `onClose` | `(() -> Unit)?` | Callback invoked when the close action is clicked; null renders no close action | `null` | No |

### ElegantToastDuration Values

| Value | Behavior |
| --- | --- |
| `Short` | Auto-dismissed after 4000ms |
| `Long` | Auto-dismissed after 10000ms |
| `Indefinite` | Never auto-dismissed; only the close action dismisses |

### ElegantToastDefaults

| Member | Type | Description |
| --- | --- | --- |
| `ShortDurationMillis` | `Long` | 4000ms auto-dismiss delay for `Short` |
| `LongDurationMillis` | `Long` | 10000ms auto-dismiss delay for `Long` |
| `AnimationDurationMillis` | `Int` | Standard 160ms slide-and-fade transition duration |
| `MaxWidth` | `Dp` | 360dp maximum surface width |
| `colors()` | `ElegantToastColors` | Theme-aware Light/Dark colors |

### ElegantToastColors

`ElegantToastColors` contains the container color, the title color, the description color, and the close icon color. Start with `ElegantToastDefaults.colors()` and use `copy(...)` only for a deliberate product-specific hierarchy.

## Advanced Usage

### Sequential Workflow

Because `showToast` suspends until dismissal, a workflow can chain follow-up work behind feedback; the call resumes regardless of whether the message was dismissed by the timer or by the close action.

```kotlin
scope.launch {
    toastHostState.showToast(
        title = "Download complete",
        description = "Opening the file now.",
    )
    openDownload()
}
```

### Custom Colors

```kotlin
val baseColors = ElegantToastDefaults.colors()

ElegantToast(
    title = "Custom surface",
    colors = baseColors.copy(
        containerColor = ElegantTheme.colors.backgroundSubtle,
    ),
)
```
