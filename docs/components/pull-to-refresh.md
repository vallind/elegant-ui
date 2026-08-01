# Pull To Refresh

`ElegantPullToRefresh` adds a pull-to-refresh gesture to any scrollable content without nesting a scrollable. While the content is at its start, dragging downward accumulates a pull distance; releasing at or beyond the 80dp threshold invokes `onRefresh` once and snaps the indicator into an indeterminate rotation that stays visible until the caller flips `isRefreshing` back to false.

<iframe id="demoIframe" style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; border: none;" src="../compose/index.html?id=pull-to-refresh" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>

## Import

```kotlin
import com.elegant.compose.ui.pulltorefresh.ElegantPullToRefresh
import com.elegant.compose.ui.pulltorefresh.ElegantPullToRefreshColors
import com.elegant.compose.ui.pulltorefresh.ElegantPullToRefreshDefaults
```

## Basic Usage

Wrap a scrollable such as a `verticalScroll` column in `ElegantPullToRefresh`. `isRefreshing` is caller-controlled: flip it to true from `onRefresh` and back to false when the refresh completes. The indicator is a scrim disc with a circular ring: while pulling it grows and rotates with the pull fraction, and past the threshold it switches to an indeterminate rotation.

```kotlin
var refreshing by remember { mutableStateOf(false) }

ElegantPullToRefresh(
    isRefreshing = refreshing,
    onRefresh = { refreshing = true },
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
    ) {
        repeat(20) { index ->
            Text("Item $index")
        }
    }
}
```

## Component States

The component has three visual states. **Idle** hides the indicator. **Pulling** shows the determinate ring fading in and rotating as the drag accumulates distance; releasing below the threshold animates it back to idle. **Refreshing** shows the indeterminate ring at the threshold position until `isRefreshing` becomes false, after which the indicator retracts. Setting `enabled = false` keeps the content fully scrollable but disables the pull gesture entirely.

```kotlin
var refreshing by remember { mutableStateOf(false) }

LaunchedEffect(refreshing) {
    if (refreshing) {
        delay(ElegantPullToRefreshDefaults.RefreshDurationMillis.toLong())
        refreshing = false
    }
}

ElegantPullToRefresh(
    isRefreshing = refreshing,
    onRefresh = { refreshing = true },
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
    ) {
        Text("Pull down to refresh")
    }
}
```

## Properties

### ElegantPullToRefresh Properties

| Property Name | Type | Description | Default Value | Required |
| --- | --- | --- | --- | --- |
| `isRefreshing` | `Boolean` | Whether a refresh is in progress; keeps the indeterminate ring visible | - | Yes |
| `onRefresh` | `() -> Unit` | Called once when a pull crosses the threshold and is released | - | Yes |
| `modifier` | `Modifier` | Modifier applied once to the wrapper root | `Modifier` | No |
| `enabled` | `Boolean` | Whether the pull gesture may accumulate distance and trigger a refresh | `true` | No |
| `colors` | `ElegantPullToRefreshColors` | Theme-aware indicator, track, and scrim colors | `ElegantPullToRefreshDefaults.colors()` | No |
| `content` | `@Composable () -> Unit` | Scrollable content wrapped by the pull gesture | - | Yes |

### ElegantPullToRefreshDefaults

| Member | Type | Description |
| --- | --- | --- |
| `IndicatorSize` | `Dp` | 40dp diameter of the indicator disc and ring |
| `IndicatorStrokeWidth` | `Dp` | 4dp ring stroke thickness |
| `PullThreshold` | `Dp` | 80dp pull distance that triggers a refresh |
| `RefreshDurationMillis` | `Int` | 1200ms recommended refresh window matching one indeterminate sweep |
| `AnimationDurationMillis` | `Int` | Emphasized 220ms pull settle duration |
| `colors()` | `ElegantPullToRefreshColors` | Theme-aware Light/Dark colors |

### ElegantPullToRefreshColors

`ElegantPullToRefreshColors` contains the ring and disc colors: `indicatorColor` fills the pull arc and the indeterminate ring, `trackColor` renders the empty ring behind it, and `scrimColor` fills the disc beneath the ring so it stays legible over content. Start with `ElegantPullToRefreshDefaults.colors()` and use `copy(...)` only for a deliberate product-specific hierarchy.

## Advanced Usage

### Refreshable List

Combine the pull gesture with `ElegantList` for a refreshable feed. A counter tracks completed refreshes so the update is visible after the 1200ms window.

```kotlin
var refreshes by remember { mutableStateOf(0) }
var refreshing by remember { mutableStateOf(false) }

LaunchedEffect(refreshing) {
    if (refreshing) {
        delay(ElegantPullToRefreshDefaults.RefreshDurationMillis.toLong())
        refreshes += 1
        refreshing = false
    }
}

ElegantPullToRefresh(
    isRefreshing = refreshing,
    onRefresh = { refreshing = true },
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
    ) {
        ElegantList {
            repeat(20) { index ->
                ElegantListItem(
                    title = { Text("Inbox message $index") },
                    supportingText = { Text("Refreshed $refreshes times") },
                )
            }
        }
    }
}
```
