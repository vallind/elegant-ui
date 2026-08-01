# Pull To Refresh

`ElegantPullToRefresh` 无需嵌套滚动容器即可为任意可滚动内容添加下拉刷新手势。当内容位于起始位置时,向下拖拽会累积下拉距离;在达到或超过 80dp 阈值时松手会调用一次 `onRefresh`,并将指示器吸附到无限态旋转,保持可见直到调用方将 `isRefreshing` 重新置为 false。

<iframe id="demoIframe" style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; border: none;" src="../../compose/index.html?id=pull-to-refresh" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>

## 引入

```kotlin
import com.elegant.compose.ui.pulltorefresh.ElegantPullToRefresh
import com.elegant.compose.ui.pulltorefresh.ElegantPullToRefreshColors
import com.elegant.compose.ui.pulltorefresh.ElegantPullToRefreshDefaults
```

## 基本用法

将 `verticalScroll` 列等可滚动内容包裹在 `ElegantPullToRefresh` 中。`isRefreshing` 由调用方控制:在 `onRefresh` 中将其置为 true,并在刷新完成时置回 false。指示器由遮罩圆盘与圆形圆环组成:下拉时圆环随下拉分数逐渐显现并旋转,超过阈值后切换为无限态旋转。

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

## 组件状态

该组件包含三种视觉状态。**空闲**时隐藏指示器。**下拉中**时确定态圆环随拖拽距离淡入并旋转;在未达阈值时松手会将其动画收回。**刷新中**时无限态圆环停留在阈值位置旋转,直到 `isRefreshing` 变为 false 后指示器收回。将 `enabled` 置为 false 会保持内容完全可滚动,但彻底禁用下拉手势。

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

## 属性

### ElegantPullToRefresh 属性

| 属性名 | 类型 | 说明 | 默认值 | 是否必须 |
| --- | --- | --- | --- | --- |
| `isRefreshing` | `Boolean` | 刷新是否正在进行;保持无限态圆环可见 | - | 是 |
| `onRefresh` | `() -> Unit` | 下拉越过阈值并松手时调用一次 | - | 是 |
| `modifier` | `Modifier` | 仅应用一次到包装器根节点的修饰符 | `Modifier` | 否 |
| `enabled` | `Boolean` | 下拉手势是否可累积距离并触发刷新 | `true` | 否 |
| `colors` | `ElegantPullToRefreshColors` | 主题感知的指示器、轨道与遮罩颜色 | `ElegantPullToRefreshDefaults.colors()` | 否 |
| `content` | `@Composable () -> Unit` | 被下拉手势包裹的可滚动内容 | - | 是 |

### ElegantPullToRefreshDefaults

| 成员 | 类型 | 说明 |
| --- | --- | --- |
| `IndicatorSize` | `Dp` | 指示器圆盘与圆环的 40dp 直径 |
| `IndicatorStrokeWidth` | `Dp` | 圆环的 4dp 描边粗细 |
| `PullThreshold` | `Dp` | 触发刷新的 80dp 下拉距离 |
| `RefreshDurationMillis` | `Int` | 1200ms 建议刷新窗口,与单次无限态扫描一致 |
| `AnimationDurationMillis` | `Int` | 强调 220ms 下拉回弹时长 |
| `colors()` | `ElegantPullToRefreshColors` | Light/Dark 主题感知颜色 |

### ElegantPullToRefreshColors

`ElegantPullToRefreshColors` 包含圆环与圆盘颜色:`indicatorColor` 填充下拉弧与无限态圆环,`trackColor` 渲染其后的空白轨道,`scrimColor` 填充圆环下方的圆盘,使其在内容之上保持清晰。应先调用 `ElegantPullToRefreshDefaults.colors()`,再仅针对产品明确需要的层级使用 `copy(...)`。

## 进阶用法

### 可刷新列表

将下拉手势与 `ElegantList` 组合成可刷新的信息流。计数器记录已完成的刷新次数,使 1200ms 窗口后的更新清晰可见。

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
