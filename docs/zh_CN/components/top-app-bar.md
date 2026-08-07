# TopAppBar

`ElegantTopAppBar` 是带大标题的顶部应用栏容器,大标题会随着下方内容的滚动而折叠。它承载可选的导航图标、可选副标题与尾部操作,未附加滚动行为时保持完全展开并静止。它支持 Android、Desktop 与 Web。

<iframe id="demoIframe" style="width: 100%; height: 460px; border: 1px solid var(--vp-c-divider); border-radius: 12px; display: block; background: var(--vp-c-bg-alt);" src="../../compose/index.html?id=top-app-bar" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>

## 引入

```kotlin
import com.elegant.compose.ui.topappbar.ElegantTopAppBar
import com.elegant.compose.ui.topappbar.ElegantTopAppBarColors
import com.elegant.compose.ui.topappbar.ElegantTopAppBarDefaults
import com.elegant.compose.ui.topappbar.ElegantTopAppBarScrollBehavior
import com.elegant.compose.ui.topappbar.ElegantTopAppBarState
import com.elegant.compose.ui.topappbar.rememberElegantTopAppBarScrollBehavior
import com.elegant.compose.ui.topappbar.rememberElegantTopAppBarState
```

## 基本用法

应用栏以大标题 `headlineLarge`、折叠小标题 `titleLarge` 渲染标题,副标题使用 `bodyMedium`。未提供 `scrollBehavior` 时,应用栏保持内容高度(至少 `ElegantTopAppBarDefaults.CollapsedHeight`,即 52dp)并完全展开。

```kotlin
ElegantTopAppBar(
    title = "设备",
    largeTitle = "设备",
    subtitle = "存储与内存",
    navigationIcon = {
        ElegantIconButton(
            onClick = { /* 返回 */ },
            contentDescription = "返回",
        ) {
            Icon(Icons.Default.Menu, contentDescription = null)
        }
    },
    actions = {
        ElegantIconButton(
            onClick = { /* 分享 */ },
            contentDescription = "分享",
        ) {
            Icon(Icons.Default.Share, contentDescription = null)
        }
        ElegantIconButton(
            onClick = { /* 更多选项 */ },
            contentDescription = "更多选项",
        ) {
            Icon(Icons.Default.MoreVert, contentDescription = null)
        }
    },
)
```

## 折叠行为

要让应用栏折叠,请创建滚动行为并将其 `nestedScrollConnection` 通过 `Modifier.nestedScroll(...)` 附加到可滚动内容。内容向上滚动时,大标题滑出并淡出,应用栏高度向折叠高度插值,小标题淡入并滑入;向下滚动时应用栏重新展开。

```kotlin
val scrollBehavior = rememberElegantTopAppBarScrollBehavior()

Column(modifier = Modifier.fillMaxSize()) {
    ElegantTopAppBar(
        title = "资料库",
        largeTitle = "资料库",
        subtitle = "10 张专辑",
        navigationIcon = { /* 返回按钮 */ },
        actions = { /* 操作图标 */ },
        scrollBehavior = scrollBehavior,
    )
    Column(
        modifier = Modifier
            .weight(1f)
            .verticalScroll(rememberScrollState())
            .nestedScroll(scrollBehavior.nestedScrollConnection),
    ) {
        // 可滚动内容;向上滚动会折叠应用栏。
    }
}
```

## 组件状态

应用栏有两个稳定状态——展开与折叠——以及两者之间的过渡。`ElegantTopAppBarState.collapsedFraction` 报告进度:`0.0` 完全展开,`1.0` 完全折叠。大标题的透明度在折叠到三分之一时降为零,小标题在折叠越过该点后淡入并滑入。以中间位置结束的 fling 或拖拽会吸附到较近的稳定状态:低于一半回到展开,高于一半保持折叠。状态更新被限制在布局阶段读取,因此滚动折叠应用栏时不会重组内容。

```kotlin
val scrollBehavior = rememberElegantTopAppBarScrollBehavior()

Text(
    text = "折叠进度: ${(scrollBehavior.state.collapsedFraction * 100).roundToInt()}%",
    style = ElegantTheme.typography.bodyMedium,
)
```

## 属性

### ElegantTopAppBar 属性

| 属性名 | 类型 | 说明 | 默认值 | 是否必须 |
| --- | --- | --- | --- | --- |
| `title` | `String` | 折叠后的小标题文字 | - | 是 |
| `modifier` | `Modifier` | 仅应用一次到应用栏根节点的修饰符 | `Modifier` | 否 |
| `largeTitle` | `String` | 展开后的大标题文字 | `title` | 否 |
| `subtitle` | `String?` | 渲染在大标题下方的说明文字 | `null` | 否 |
| `navigationIcon` | `(@Composable () -> Unit)?` | 可选的起始槽位,通常是返回或菜单按钮 | `null` | 否 |
| `actions` | `(@Composable RowScope.() -> Unit)?` | 可选的尾部操作图标槽位 | `null` | 否 |
| `scrollBehavior` | `ElegantTopAppBarScrollBehavior?` | 折叠与展开应用栏的行为 | `null` | 否 |
| `colors` | `ElegantTopAppBarColors` | 主题感知的状态颜色 | `ElegantTopAppBarDefaults.colors()` | 否 |

### ElegantTopAppBarState 属性

| 属性名 | 类型 | 说明 |
| --- | --- | --- |
| `heightOffsetLimit` | `Float` | 应用栏折叠到的像素上限;为负值,由测量的大标题高度写入 |
| `heightOffset` | `Float` | 当前高度偏移(像素),被钳制在 `heightOffsetLimit` 与零之间 |
| `contentOffset` | `Float` | 滚动到应用栏下方的内容总偏移 |
| `collapsedFraction` | `Float` | 折叠百分比;`0.0` 展开,`1.0` 折叠 |

### ElegantTopAppBarScrollBehavior 属性

| 成员 | 类型 | 说明 |
| --- | --- | --- |
| `state` | `ElegantTopAppBarState` | 跟踪折叠进度的状态 |
| `nestedScrollConnection` | `NestedScrollConnection` | 需要附加到可滚动内容的连接 |
| `snapAnimationSpec` | `AnimationSpec<Float>?` | fling 后吸附到完全折叠或展开的动画;默认 Folme spring,响应时长 `0.3s` |
| `flingAnimationSpec` | `DecayAnimationSpec<Float>?` | fling 衰减动画;默认为样条衰减 |

### ElegantTopAppBarDefaults

| 成员 | 类型 | 说明 |
| --- | --- | --- |
| `CollapsedHeight` | `Dp` | 52dp 折叠后的应用栏高度 |
| `TitlePadding` | `Dp` | 标题与大标题的 26dp 水平内边距 |
| `NavigationIconPadding` | `Dp` | 导航图标的 16dp 起始内边距 |
| `ActionIconPadding` | `Dp` | 操作图标的 16dp 末端内边距 |
| `LargeTitleBottomPadding` | `Dp` | 大标题下方的 4dp 底部内边距 |
| `SubtitleBottomPadding` | `Dp` | 副标题下方的 8dp 底部内边距 |
| `colors()` | `ElegantTopAppBarColors` | Light/Dark 主题感知颜色 |

### ElegantTopAppBarColors

`ElegantTopAppBarColors` 包含容器色、小标题色、大标题色与副标题色。应先调用 `ElegantTopAppBarDefaults.colors()`,再仅针对产品明确需要的层级使用 `copy(...)`。

## 进阶用法

### 自定义动画

向 `rememberElegantTopAppBarScrollBehavior` 传入自定义规格,可以改变应用栏在 fling 后的吸附方式;传入 `null` 可跳过衰减或吸附阶段。

```kotlin
val scrollBehavior = rememberElegantTopAppBarScrollBehavior(
    snapAnimationSpec = elegantFolmeSpring(dampingRatio = 1.0f, responseSeconds = 0.4f),
    flingAnimationSpec = null,
)
```

### 自定义颜色

```kotlin
val baseColors = ElegantTopAppBarDefaults.colors()

ElegantTopAppBar(
    title = "资料库",
    largeTitle = "资料库",
    colors = baseColors.copy(
        containerColor = ElegantTheme.colors.surfaceRaised,
        largeTitleColor = ElegantTheme.colors.interactivePrimary,
    ),
)
```
