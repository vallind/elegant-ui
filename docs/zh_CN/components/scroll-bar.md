# Scroll Bar

`ElegantScrollBar` 是纤细的、不可交互的滚动位置指示器。将其放置在 `verticalScroll` 列或 `horizontalScroll` 行之上,当内容滚动时,圆角拇指沿轨道移动,让长内容始终显示其当前位置。

<iframe id="demoIframe" style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; border: none;" src="../../compose/index.html?id=scroll-bar" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>

## 引入

```kotlin
import com.elegant.compose.ui.scrollbar.ElegantScrollBar
import com.elegant.compose.ui.scrollbar.ElegantScrollBarOrientation
import com.elegant.compose.ui.scrollbar.ElegantScrollBarColors
import com.elegant.compose.ui.scrollbar.ElegantScrollBarDefaults
```

## 基本用法

为可滚动内容提供一个 `rememberScrollState()`,并将 `ElegantScrollBar` 作为包裹内容的 `Box` 的最后一个子元素,对齐到末端边缘。指示器会按轨道确定自身大小、读取滚动状态,并且永远不会拦截滚动手势。

```kotlin
val scrollState = rememberScrollState()

Box {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState),
    ) {
        repeat(20) { index ->
            ElegantListItem(title = { Text("Item $index") })
        }
    }
    ElegantScrollBar(
        state = scrollState,
        modifier = Modifier.align(Alignment.CenterEnd),
    )
}
```

## 方向

`ElegantScrollBarOrientation` 选择指示器跟踪的滚动方向。`Vertical`(默认值)沿整个高度运行垂直轨道。`Horizontal` 镜像几何结构:沿整个宽度运行水平轨道,拇指从起始边缘开始移动。

```kotlin
val scrollState = rememberScrollState()

Box {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .horizontalScroll(scrollState),
    ) {
        repeat(20) { index ->
            ElegantListItem(title = { Text("Item $index") })
        }
    }
    ElegantScrollBar(
        state = scrollState,
        orientation = ElegantScrollBarOrientation.Horizontal,
        modifier = Modifier.align(Alignment.BottomCenter),
    )
}
```

## 组件状态

滚动条没有 hover、press、focus 或 disabled 状态。它默认是装饰性的:它会清除自身的语义,让屏幕阅读器跳过该指示器,并且从不阻挡指针输入。拇指长度为轨道长度乘以轨道与可滚动距离之比,并至少占轨道的 `ElegantScrollBarDefaults.MinThumbFraction`;拇指偏移为剩余轨道长度的已滚动比例。当内容恰好容纳且无法滚动时,拇指在起始边缘填满轨道。颜色从 `ElegantTheme` 解析并在 Light 与 Dark 之间自动适配。

```kotlin
val scrollState = rememberScrollState()

Box {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState),
    ) {
        repeat(20) { index ->
            ElegantListItem(title = { Text("Item $index") })
        }
    }
    ElegantScrollBar(
        state = scrollState,
        colors = ElegantScrollBarDefaults.colors(),
        modifier = Modifier.align(Alignment.CenterEnd),
    )
}
```

## 属性

### ElegantScrollBar 属性

| 属性名 | 类型 | 说明 | 默认值 | 是否必须 |
| --- | --- | --- | --- | --- |
| `state` | `ScrollState` | 驱动拇指位置与长度的滚动状态 | - | 是 |
| `modifier` | `Modifier` | 仅应用一次到指示器根节点的修饰符 | `Modifier` | 否 |
| `orientation` | `ElegantScrollBarOrientation` | 指示器跟踪的滚动方向 | `ElegantScrollBarOrientation.Vertical` | 否 |
| `colors` | `ElegantScrollBarColors` | 主题感知的拇指与轨道颜色 | `ElegantScrollBarDefaults.colors()` | 否 |

### ElegantScrollBarOrientation

| 选项 | 说明 |
| --- | --- |
| `Vertical` | 沿垂直轨道运行,拇指从上到下移动 |
| `Horizontal` | 沿水平轨道运行,拇指从起始边缘开始移动 |

### ElegantScrollBarDefaults

| 成员 | 类型 | 说明 |
| --- | --- |
| `ThumbWidth` | `Dp` | 滚动位置拇指的 4dp 宽度 |
| `TrackWidth` | `Dp` | 拇指后方轨道的 8dp 宽度 |
| `MinThumbFraction` | `Float` | 拇指可占据轨道的最小 0.1f 比例 |
| `colors()` | `ElegantScrollBarColors` | Light/Dark 主题感知的拇指与轨道颜色 |

### ElegantScrollBarColors

`ElegantScrollBarColors` 持有指示器绘制的 `thumbColor` 与 `trackColor`。应先调用 `ElegantScrollBarDefaults.colors()`,再仅针对产品明确需要的色调使用 `copy(...)`。

## 进阶用法

### 横向滚动条

为横向滚动的卡片行底部边缘添加滚动位置指示器。

```kotlin
val scrollState = rememberScrollState()

Box {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .horizontalScroll(scrollState),
        horizontalArrangement = Arrangement.spacedBy(ElegantSpacing.md),
    ) {
        repeat(12) { index ->
            ElegantListItem(
                title = { Text("Card $index") },
                modifier = Modifier.width(160.dp),
            )
        }
    }
    ElegantScrollBar(
        state = scrollState,
        orientation = ElegantScrollBarOrientation.Horizontal,
        modifier = Modifier.align(Alignment.BottomCenter),
    )
}
```

### 自定义滚动条颜色

将指示器色调改为匹配周围表面,而不是主题默认值。

```kotlin
val scrollBarColors = ElegantScrollBarDefaults.colors().copy(
    thumbColor = ElegantTheme.colors.textSecondary,
    trackColor = ElegantTheme.colors.borderStrong,
)

ElegantScrollBar(
    state = rememberScrollState(),
    colors = scrollBarColors,
)
```
