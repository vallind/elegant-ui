# TopAppBar

`ElegantTopAppBar` is a top app-bar container with a large title that collapses while the content below it scrolls. It hosts an optional navigation icon, an optional subtitle, and trailing actions, and renders fully expanded and static when no scroll behavior is attached. It runs on Android, Desktop, and Web.

<iframe id="demoIframe" style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; border: none;" src="../compose/index.html?id=top-app-bar" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>

## Import

```kotlin
import com.elegant.compose.ui.topappbar.ElegantTopAppBar
import com.elegant.compose.ui.topappbar.ElegantTopAppBarColors
import com.elegant.compose.ui.topappbar.ElegantTopAppBarDefaults
import com.elegant.compose.ui.topappbar.ElegantTopAppBarScrollBehavior
import com.elegant.compose.ui.topappbar.ElegantTopAppBarState
import com.elegant.compose.ui.topappbar.rememberElegantTopAppBarScrollBehavior
import com.elegant.compose.ui.topappbar.rememberElegantTopAppBarState
```

## Basic Usage

The bar renders the large title with `headlineLarge`, the collapsed small title with `titleLarge`, and the subtitle with `bodyMedium`. Without a `scrollBehavior` the bar stays expanded at its content height (at least `ElegantTopAppBarDefaults.CollapsedHeight`, 52dp).

```kotlin
ElegantTopAppBar(
    title = "Device",
    largeTitle = "Device",
    subtitle = "Storage & memory",
    navigationIcon = {
        ElegantIconButton(
            onClick = { /* navigate back */ },
            contentDescription = "Back",
        ) {
            Icon(Icons.Default.Menu, contentDescription = null)
        }
    },
    actions = {
        ElegantIconButton(
            onClick = { /* share */ },
            contentDescription = "Share",
        ) {
            Icon(Icons.Default.Share, contentDescription = null)
        }
        ElegantIconButton(
            onClick = { /* more options */ },
            contentDescription = "More options",
        ) {
            Icon(Icons.Default.MoreVert, contentDescription = null)
        }
    },
)
```

## Collapsing Behavior

To make the bar collapse, create a scroll behavior and attach its `nestedScrollConnection` to the scrollable content with `Modifier.nestedScroll(...)`. While the content scrolls up, the large title slides out and fades, the bar height interpolates down to the collapsed height, and the small title fades and slides in; scrolling down expands the bar back.

```kotlin
val scrollBehavior = rememberElegantTopAppBarScrollBehavior()

Column(modifier = Modifier.fillMaxSize()) {
    ElegantTopAppBar(
        title = "Library",
        largeTitle = "Library",
        subtitle = "10 albums",
        navigationIcon = { /* back button */ },
        actions = { /* action icons */ },
        scrollBehavior = scrollBehavior,
    )
    Column(
        modifier = Modifier
            .weight(1f)
            .verticalScroll(rememberScrollState())
            .nestedScroll(scrollBehavior.nestedScrollConnection),
    ) {
        // Scrollable content; scrolling up collapses the bar.
    }
}
```

## Component States

The bar has two stable states — expanded and collapsed — plus the transition between them. `ElegantTopAppBarState.collapsedFraction` reports the progress: `0.0` is fully expanded, `1.0` is fully collapsed. The large title's alpha reaches zero at one third of the collapse, and the small title fades and slides in once the collapse passes that point. A fling or drag that ends in an intermediate position settles to the nearer stable state: below half collapses back, above half stays collapsed. State updates are throttled to layout phase reads, so scrolling collapses the bar without recomposing the content.

```kotlin
val scrollBehavior = rememberElegantTopAppBarScrollBehavior()

Text(
    text = "Collapse progress: ${(scrollBehavior.state.collapsedFraction * 100).roundToInt()}%",
    style = ElegantTheme.typography.bodyMedium,
)
```

## Properties

### ElegantTopAppBar Properties

| Property Name | Type | Description | Default Value | Required |
| --- | --- | --- | --- | --- |
| `title` | `String` | Collapsed small title text | - | Yes |
| `modifier` | `Modifier` | Modifier applied once to the bar root | `Modifier` | No |
| `largeTitle` | `String` | Expanded large title text | `title` | No |
| `subtitle` | `String?` | Supporting text rendered below the large title | `null` | No |
| `navigationIcon` | `(@Composable () -> Unit)?` | Optional leading slot, typically a back or menu button | `null` | No |
| `actions` | `(@Composable RowScope.() -> Unit)?` | Optional trailing slot for action icons | `null` | No |
| `scrollBehavior` | `ElegantTopAppBarScrollBehavior?` | Behavior that collapses and expands the bar | `null` | No |
| `colors` | `ElegantTopAppBarColors` | Theme-aware state colors | `ElegantTopAppBarDefaults.colors()` | No |

### ElegantTopAppBarState Properties

| Property Name | Type | Description |
| --- | --- | --- |
| `heightOffsetLimit` | `Float` | The pixel limit the bar collapses to; negative, written from the measured large title height |
| `heightOffset` | `Float` | The current height offset in pixels, coerced between `heightOffsetLimit` and zero |
| `contentOffset` | `Float` | The total offset of the content scrolled under the bar |
| `collapsedFraction` | `Float` | The collapsed percentage; `0.0` expanded, `1.0` collapsed |

### ElegantTopAppBarScrollBehavior Properties

| Member | Type | Description |
| --- | --- | --- |
| `state` | `ElegantTopAppBarState` | The state that tracks the collapse progress |
| `nestedScrollConnection` | `NestedScrollConnection` | Connection to attach to the scrollable content |
| `snapAnimationSpec` | `AnimationSpec<Float>?` | Snap animation to fully collapsed or expanded after a fling; defaults to a Folme spring with a `0.3s` response |
| `flingAnimationSpec` | `DecayAnimationSpec<Float>?` | Fling decay animation; defaults to spline-based decay |

### ElegantTopAppBarDefaults

| Member | Type | Description |
| --- | --- | --- |
| `CollapsedHeight` | `Dp` | 52dp collapsed bar height |
| `TitlePadding` | `Dp` | 26dp horizontal padding of the title and large title |
| `NavigationIconPadding` | `Dp` | 16dp start padding of the navigation icon |
| `ActionIconPadding` | `Dp` | 16dp end padding of the action icons |
| `LargeTitleBottomPadding` | `Dp` | 4dp bottom padding below the large title |
| `SubtitleBottomPadding` | `Dp` | 8dp bottom padding below the subtitle |
| `colors()` | `ElegantTopAppBarColors` | Theme-aware Light/Dark colors |

### ElegantTopAppBarColors

`ElegantTopAppBarColors` holds the container, small title, large title, and subtitle colors. Start with `ElegantTopAppBarDefaults.colors()` and use `copy(...)` only for a deliberate product-specific hierarchy.

## Advanced Usage

### Custom Animations

Pass custom specs to `rememberElegantTopAppBarScrollBehavior` to change how the bar settles after a fling, or pass `null` to skip the decay or snap phase.

```kotlin
val scrollBehavior = rememberElegantTopAppBarScrollBehavior(
    snapAnimationSpec = elegantFolmeSpring(dampingRatio = 1.0f, responseSeconds = 0.4f),
    flingAnimationSpec = null,
)
```

### Custom Colors

```kotlin
val baseColors = ElegantTopAppBarDefaults.colors()

ElegantTopAppBar(
    title = "Library",
    largeTitle = "Library",
    colors = baseColors.copy(
        containerColor = ElegantTheme.colors.surfaceRaised,
        largeTitleColor = ElegantTheme.colors.interactivePrimary,
    ),
)
```
