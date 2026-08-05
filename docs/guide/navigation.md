# Navigation

`elegant-nav` is a self-contained Compose Multiplatform navigation runtime with **continuous stack depth** as its core model. The whole back stack is driven by a single `Animatable<Float>`; every entry's visuals are a pure function of its relative depth. This makes continuous push/pop, fully custom float-driven transitions, and 1:1 gesture back fall out naturally. It has **zero dependency** on `androidx.navigation` and on the `elegant-ui` module itself.

<iframe id="demoIframe" style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; border: none;" src="../compose/index.html?id=navigation" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>

## Import

Add the dependency to your `build.gradle.kts`:

```kotlin
implementation("io.github.vallind:elegant-nav:0.1.0-SNAPSHOT")
```

Annotate your route hierarchy with `@Serializable` so the back stack can be saved and restored across configuration changes and process death.

## Basic Usage

```kotlin
import kotlinx.serialization.Serializable
import com.elegant.compose.ui.nav.core.ElegantNavDisplay
import com.elegant.compose.ui.nav.core.ElegantNavKey
import com.elegant.compose.ui.nav.core.rememberElegantNavBackStack
import com.elegant.compose.ui.nav.transition.ElegantNavTransitions

@Serializable
sealed interface Route : ElegantNavKey {
    @Serializable data object Home : Route
    @Serializable data class Detail(val id: String) : Route
}

@Composable
fun App() {
    val backStack = rememberElegantNavBackStack<Route>(Route.Home)
    ElegantNavDisplay(
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
    ) {
        entry<Route.Home> {
            HomeScreen(onOpen = { id -> backStack.add(Route.Detail(id)) })
        }
        entry<Route.Detail> { route ->
            DetailScreen(route.id, onBack = { backStack.removeLastOrNull() })
        }
    }
}
```

`rememberElegantNavBackStack` returns an `ElegantNavBackStack` (a `SnapshotStateList<ElegantNavKey>`). Operate it directly (`add` / `removeLastOrNull`) or wrap it in an `ElegantNavController` for `push` / `pop` / `replace` / `popUntil`.

::: warning
`rememberElegantNavBackStack` is `inline fun <reified T : ElegantNavKey>`. When you seed it with a single concrete key, pass the route **supertype** as the explicit type argument — `rememberElegantNavBackStack<Route>(Route.Home)` — so the whole sealed hierarchy is serializable. Writing `rememberElegantNavBackStack(Route.Home)` infers `T = Route.Home`, and pushing other subtypes (e.g. `Route.Detail`) will later fail to serialize for save/restore.
:::

## Navigation Controller

```kotlin
val navController = rememberNavController<Route>(Route.Home)

ElegantNavDisplay(navController = navController) {
    entry<Route.Home> {
        HomeScreen(
            onOpen = { navController.push(Route.Detail("1")) },
            onReplace = { navController.replace(Route.Detail("2")) },
        )
    }
    entry<Route.Detail> { route ->
        DetailScreen(route.id, onBack = { navController.pop() })
    }
}
```

Because the stack is driven by one float, pushing or popping several entries at once animates as a single continuous sweep instead of collapsing into one top-level cross-fade. The reconciler classifies each change as `Push` / `Pop` / `MultiPush(n)` / `MultiPop(n)` / `Replace` / `ReplaceAll`, surfaced to transitions via `ElegantNavTransitionScope.change`.

## Transitions

A built-in preset library is available as `ElegantNavTransitions`:

| Preset | Description |
| :-- | :-- |
| `ElegantDefault` (default) | Full-width slide + quarter-width parallax + light covered alpha falloff |
| `Modal` | Bottom-up slide; lower layer stays visible |
| `None` | Instant, no animation |

Set a global default on `ElegantNavDisplay(transition = ...)` and override per route with `entry(transition = ...)`:

```kotlin
ElegantNavDisplay(backStack, transition = ElegantNavTransitions.ElegantDefault) {
    entry<Route.Home> { HomeScreen() }
    entry<Route.Detail>(transition = ElegantNavTransitions.Modal) { DetailScreen(it.id) }
}
```

Build any custom transition by reading the raw float depth and writing a `graphicsLayer`. The block runs inside a deferred-read layer, so reading `relativeDepth` does not recompose:

```kotlin
val myTransition = elegantNavGraphicsTransition { scope ->
    val d = scope.relativeDepth          // animatedTop - index
    translationX = -d * scope.layoutSize.width.toFloat()
    scaleX = 1f - 0.1f * d.coerceIn(0f, 1f)
    scaleY = scaleX
    cameraDistance = 16f * scope.density.density
}
```

`ElegantNavTransitionScope` exposes `relativeDepth`, `role`, `change`, `gesture`, `settle`, `layoutSize`, `layoutDirection` and `density`. A transition also declares its settle physics via `motion`: `ElegantNavMotion(commit = ..., cancel = ..., programmatic = ...)`, where each phase is a `Spring(dampingRatio, stiffness, clampOvershoot)` or a fixed-duration `Tween(durationMillis, easing)`. When a design calls for two distinct effect systems (programmatic vs. predictive), compose them with `elegantNavDirectionalTransition(push = ..., pop = ..., predictivePop = ...)`.

## Gestures

Back is built in and shares the same `Animatable` depth driver as a normal pop. On Android, the **system predictive back** streams continuous finger progress into the driver; on Desktop the host window's ESC key triggers a discrete commit; on Web you feed the same back stream from your own trigger. Both sources flow through the shared `androidx.navigationevent` dispatcher, so an open overlay (dialog / bottom sheet / popup) consumes back before the navigation handler does.

```kotlin
@Composable
fun Root() {
    ElegantWindowNavigationEventBridge() // desktop: wires ESC; android: view-tree forwarding
    val navController = rememberNavController<Route>(Route.Home)

    ElegantPredictiveBackHandler(
        enabled = true,
        onCommit = { navController.pop() },
        onCancel = {},
    ) { events ->
        events.collect { event ->
            // event.progress 0..1, event.swipeEdge, event.touchY, event.frameTimeMillis
        }
    }

    ElegantNavDisplay(navController) { /* ... */ }
}
```

The **in-content swipe** is opt-in per route — `entry(swipeDismiss = ...)` — and runs along the same axis as the transition: a horizontal slide is dismissed by a horizontal swipe (`ElegantNavSwipeDirection.LeftToRight` under LTR), a bottom-up modal by a downward swipe (`TopToBottom`). On release, a velocity-first / position-fallback decision commits or cancels, handing the lift velocity to the governing commit curve so motion stays continuous.

## Properties

### ElegantNavDisplay Properties

| Property Name | Type | Description | Default Value | Required |
| --- | --- | --- | --- | --- |
| `backStack` / `navController` | `ElegantNavBackStack` / `ElegantNavController` | The live back stack to render, or the controller driving it | - | Yes |
| `modifier` | `Modifier` | Applied to the host container | `Modifier` | No |
| `onBack` | `() -> Unit` | Callback for a system/predictive back | Pops the last entry | No |
| `transition` | `ElegantNavTransition` | Global default transition; per-route overrides win | `ElegantNavTransitions.ElegantDefault` | No |
| `effects` | `ElegantNavDisplayEffects` | Orthogonal visual effects (corner clip / dim / input blocking) | `ElegantNavDisplayEffects.Default` | No |
| `content` | `ElegantNavEntryBuilder.() -> Unit` | The route-registration DSL block | - | Yes |

### entry Properties

| Property Name | Type | Description | Default Value | Required |
| --- | --- | --- | --- | --- |
| `contentKey` | `((T) -> Any)?` | Value-stable identity for diffing and saveable state; null uses the route value itself | `null` | No |
| `transition` | `ElegantNavTransition?` | Per-route transition override | Inherits the global default | No |
| `swipeDismiss` | `ElegantNavSwipeDirection?` | Per-route interactive swipe-to-dismiss direction | Inherits the transition's direction | No |
| `metadata` | `Map<String, Any>` | Extra per-entry metadata | `emptyMap()` | No |
| `content` | `@Composable (T) -> Unit` | Composable rendering a key of type `T` | - | Yes |

### ElegantNavDisplayEffects Properties

| Property Name | Type | Description | Default Value | Required |
| --- | --- | --- | --- | --- |
| `enableCornerClip` | `Boolean` | Clip the transitioning top entry with smooth rounded corners | `true` | No |
| `cornerClipRadius` | `Dp` | Radius of that clip; pass `rememberElegantNavSystemCornerRadius()` to follow the device screen corner | `0.dp` | No |
| `cornerClipMode` | `ElegantNavCornerClipMode` | `Leading` — corners meeting the screen edge; `All` — every corner | `Leading` | No |
| `dimAmount` | `Float` | Maximum alpha of the dim scrim beneath the top-most layer; `0f` disables | `0.5f` | No |
| `blockInputDuringTransition` | `Boolean` | Swallow touch input on mid-transition entries | `false` | No |
| `backdropColor` | `Color` | Solid fill behind every entry layer; pass the theme background for card-style transitions | `Color.Unspecified` | No |

## Advanced Usage

### Card-style Presentation

A card-style setup rounds the physical screen corners, dims gently, and fills the area revealed behind the shrinking card with the page background:

```kotlin
ElegantNavDisplay(
    backStack = backStack,
    effects = ElegantNavDisplayEffects(
        cornerClipRadius = rememberElegantNavSystemCornerRadius(),
        cornerClipMode = ElegantNavCornerClipMode.All,
        dimAmount = 0.32f,
        backdropColor = ElegantTheme.colors.backgroundCanvas,
    ),
) { /* ... */ }
```

`rememberElegantNavSystemCornerRadius` reads the real system corner radius on Android and returns `0.dp` on Desktop/Web. The corner clip itself is a path-based squircle silhouette, inlined in the module — the navigation host carries no dependency on `elegant-ui`.

### Entry State and ViewModels

Each entry's `rememberSaveable` state is scoped by its `contentKey` — the route value itself, unless you derive one via `entry<T>(contentKey = { route -> ... })`. Distinct keys must print distinct strings (the saveable slot is keyed by `toString()`), and the string must be value-derived: `data class` / `data object` routes qualify out of the box. Every entry also runs under its own `LifecycleOwner` and `ViewModelStoreOwner`, so `collectAsStateWithLifecycle`, `viewModel()` and store-based DI scope per screen with no extra setup. Lifecycle is a pure function of depth: the settled top is `RESUMED`; covered, incoming and leaving layers are `STARTED`; an entry being removed drops to `CREATED` until it unloads.

::: warning
`@Serializable` is a **hard requirement** for every key in a `rememberElegantNavBackStack` stack. A key **type** that is not `@Serializable` throws `SerializationException` at the first composition; a key **instance** outside the captured hierarchy navigates fine all session and then throws at state-save time (on Android: when the app is backgrounded). If you cannot make keys serializable, build the stack with a plain in-memory list (`elegantNavBackStackOf`) instead.
:::
