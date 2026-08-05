# Navigation 导航

`elegant-nav` 是一个自包含的 Compose Multiplatform 导航运行时,核心模型是**连续栈深度**。整个返回栈由单个 `Animatable<Float>` 驱动,每个页面的视觉效果都是其相对深度的纯函数。连续 push/pop、完全自定义的 float 驱动过渡、以及 1:1 手势返回因此自然成立。它**零依赖**于 `androidx.navigation`,也不依赖 `elegant-ui` 模块本身。

<iframe id="demoIframe" style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; border: none;" src="../../compose/index.html?id=navigation" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>

## 引入

在 `build.gradle.kts` 中添加依赖:

```kotlin
implementation("io.github.vallind:elegant-nav:0.1.0-SNAPSHOT")
```

为路由层级标注 `@Serializable`,返回栈即可跨配置变更与进程死亡保存与恢复。

## 基本用法

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

`rememberElegantNavBackStack` 返回 `ElegantNavBackStack`(即 `SnapshotStateList<ElegantNavKey>`)。可以直接操作(`add` / `removeLastOrNull`),也可以包进 `ElegantNavController` 使用 `push` / `pop` / `replace` / `popUntil`。

::: warning
`rememberElegantNavBackStack` 是 `inline fun <reified T : ElegantNavKey>`。用单个具体 key 初始化时,请把路由**父类型**作为显式类型参数传入 —— `rememberElegantNavBackStack<Route>(Route.Home)` —— 整个 sealed 层级才能序列化。写成 `rememberElegantNavBackStack(Route.Home)` 会推断出 `T = Route.Home`,之后 push 其他子类型(如 `Route.Detail`)在保存/恢复时会序列化失败。
:::

## 导航控制器

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

由于整个栈由单个 float 驱动,一次 push 或 pop 多个页面会合并成一次连续的扫动动画,而不是塌缩成单一顶层交叉淡入。协调器把每次变更分类为 `Push` / `Pop` / `MultiPush(n)` / `MultiPop(n)` / `Replace` / `ReplaceAll`,通过 `ElegantNavTransitionScope.change` 暴露给过渡,因此多级 pop 可以区别于单级 pop 单独做动画。

## 过渡动画

内置预设库 `ElegantNavTransitions`:

| 预设 | 描述 |
| :-- | :-- |
| `ElegantDefault`(默认) | 全宽滑动 + 四分之一宽视差 + 轻量覆盖层透明度衰减 |
| `Modal` | 自下而上滑入;下层保持可见 |
| `None` | 瞬时切换,无动画 |

在 `ElegantNavDisplay(transition = ...)` 上设置全局默认,并按路由用 `entry(transition = ...)` 覆盖:

```kotlin
ElegantNavDisplay(backStack, transition = ElegantNavTransitions.ElegantDefault) {
    entry<Route.Home> { HomeScreen() }
    entry<Route.Detail>(transition = ElegantNavTransitions.Modal) { DetailScreen(it.id) }
}
```

任何自定义过渡都可以通过读取原始 float 深度并写入 `graphicsLayer` 构建。该代码块在延迟读取的图层内执行,读取 `relativeDepth` 不会触发重组:

```kotlin
val myTransition = elegantNavGraphicsTransition { scope ->
    val d = scope.relativeDepth          // animatedTop - index
    translationX = -d * scope.layoutSize.width.toFloat()
    scaleX = 1f - 0.1f * d.coerceIn(0f, 1f)
    scaleY = scaleX
    cameraDistance = 16f * scope.density.density
}
```

`ElegantNavTransitionScope` 暴露 `relativeDepth`、`role`、`change`、`gesture`、`settle`、`layoutSize`、`layoutDirection` 与 `density`。过渡还通过 `motion` 声明其物理曲线:`ElegantNavMotion(commit = ..., cancel = ..., programmatic = ...)`,每个阶段是 `Spring(dampingRatio, stiffness, clampOvershoot)` 或固定时长 `Tween(durationMillis, easing)`。当设计需要两套不同的动画系统(程序式与预测式)时,用 `elegantNavDirectionalTransition(push = ..., pop = ..., predictivePop = ...)` 组合。

## 手势

返回能力内建,与普通 pop 共享同一个 `Animatable` 深度驱动。Android 上**系统预测性返回**把连续的指压进度流入驱动;Desktop 上宿主窗口的 ESC 键触发离散提交;Web 上由你自己的触发器喂入同一返回流。两条来源都经过共享的 `androidx.navigationevent` dispatcher,因此打开的浮层(对话框 / 底部弹层 / 弹窗)会先于导航处理器消费返回事件。

```kotlin
@Composable
fun Root() {
    ElegantWindowNavigationEventBridge() // desktop: 接入 ESC;android: 视图树转发
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

**页面内滑动返回**按路由显式开启 —— `entry(swipeDismiss = ...)` —— 并且与过渡同轴:横向滑入的页面用横向滑动返回(LTR 下为 `ElegantNavSwipeDirection.LeftToRight`),自下而上的弹层用向下滑动(`TopToBottom`)。松手时以速度优先、位置兜底的判定提交或取消,并把抬指速度交给 commit 曲线,保证运动连续。

## 属性

### ElegantNavDisplay 属性

| 属性名 | 类型 | 描述 | 默认值 | 必填 |
| --- | --- | --- | --- | --- |
| `backStack` / `navController` | `ElegantNavBackStack` / `ElegantNavController` | 要渲染的实时返回栈,或驱动它的控制器 | - | 是 |
| `modifier` | `Modifier` | 应用于宿主容器 | `Modifier` | 否 |
| `onBack` | `() -> Unit` | 系统/预测性返回回调 | 弹出末尾条目 | 否 |
| `transition` | `ElegantNavTransition` | 全局默认过渡;按路由覆盖优先 | `ElegantNavTransitions.ElegantDefault` | 否 |
| `effects` | `ElegantNavDisplayEffects` | 正交视觉效果(圆角裁剪 / 变暗 / 输入拦截) | `ElegantNavDisplayEffects.Default` | 否 |
| `content` | `ElegantNavEntryBuilder.() -> Unit` | 路由注册 DSL 代码块 | - | 是 |

### entry 属性

| 属性名 | 类型 | 描述 | 默认值 | 必填 |
| --- | --- | --- | --- | --- |
| `contentKey` | `((T) -> Any)?` | 用于 diff 与保存状态的值稳定标识;null 时使用路由值本身 | `null` | 否 |
| `transition` | `ElegantNavTransition?` | 按路由的过渡覆盖 | 继承全局默认 | 否 |
| `swipeDismiss` | `ElegantNavSwipeDirection?` | 按路由的滑动返回方向 | 继承过渡声明的方向 | 否 |
| `metadata` | `Map<String, Any>` | 额外逐条目元数据 | `emptyMap()` | 否 |
| `content` | `@Composable (T) -> Unit` | 渲染类型 `T` 的可组合函数 | - | 是 |

### ElegantNavDisplayEffects 属性

| 属性名 | 类型 | 描述 | 默认值 | 必填 |
| --- | --- | --- | --- | --- |
| `enableCornerClip` | `Boolean` | 为过渡中的顶层页面裁剪平滑圆角 | `true` | 否 |
| `cornerClipRadius` | `Dp` | 裁剪半径;传 `rememberElegantNavSystemCornerRadius()` 跟随设备屏幕圆角 | `0.dp` | 否 |
| `cornerClipMode` | `ElegantNavCornerClipMode` | `Leading` — 贴屏幕边缘的角;`All` — 全部角 | `Leading` | 否 |
| `dimAmount` | `Float` | 顶层下方变暗遮罩的最大透明度;`0f` 关闭 | `0.5f` | 否 |
| `blockInputDuringTransition` | `Boolean` | 过渡中的页面吞掉触摸输入 | `false` | 否 |
| `backdropColor` | `Color` | 每层页面背后的纯色填充;卡片式过渡时传主题背景色 | `Color.Unspecified` | 否 |

## 进阶用法

### 卡片式呈现

卡片式布局圆化物理屏幕四角、轻柔变暗,并用页面背景填充收缩卡片露出的区域:

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

`rememberElegantNavSystemCornerRadius` 在 Android 上读取真实系统圆角,Desktop/Web 上返回 `0.dp`。圆角裁剪本身是模块内联的基于 Path 的 squircle 轮廓 —— 导航宿主不依赖 `elegant-ui`。

### 条目状态与 ViewModel

每个条目的 `rememberSaveable` 状态由其 `contentKey` 作用域化 —— 默认即路由值本身,也可通过 `entry<T>(contentKey = { route -> ... })` 派生。不同的 key 必须打印出不同的字符串(保存槽按 `toString()` 键控),且该字符串必须由值派生:`data class` / `data object` 路由开箱即符合。每个条目还在自己的 `LifecycleOwner` 与 `ViewModelStoreOwner` 下运行,`collectAsStateWithLifecycle`、`viewModel()` 与基于 store 的依赖注入无需额外设置即可按屏作用域化。生命周期是深度的纯函数:稳定的顶层为 `RESUMED`;被覆盖、进入中与离开中的页面为 `STARTED`;被移除的条目降到 `CREATED` 直至卸载。

::: warning
`@Serializable` 是 `rememberElegantNavBackStack` 中**每个 key 的硬性要求**。key **类型**未标注 `@Serializable` 会在首次组合时抛出 `SerializationException`;key **实例**落在捕获层级之外则整个会话导航正常,直到状态保存时(Android 上:应用进入后台)才抛出。无法让 key 可序列化时,改用纯内存列表构建栈(`elegantNavBackStackOf`)。
:::
