# Scroll Shadow

`ElegantScrollShadow` 是用于可滚动内容的装饰性边缘淡出遮罩。将其放置在 `verticalScroll` 列或 `horizontalScroll` 行之上,当内容滚离起始边缘时淡出起始边缘,当仍有更多内容在前方时淡出末端边缘,让长列表不再突兀地结束。

<iframe id="demoIframe" style="width: 100%; height: 460px; border: 1px solid var(--vp-c-divider); border-radius: 12px; display: block; background: var(--vp-c-bg-alt);" src="../../compose/index.html?id=scroll-shadow" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>

## 引入

```kotlin
import com.elegant.compose.ui.scrollshadow.ElegantScrollShadow
import com.elegant.compose.ui.scrollshadow.ElegantScrollShadowOrientation
import com.elegant.compose.ui.scrollshadow.ElegantScrollShadowColors
import com.elegant.compose.ui.scrollshadow.ElegantScrollShadowDefaults
```

## 基本用法

为可滚动内容提供一个 `rememberScrollState()`,并将 `ElegantScrollShadow` 作为包裹内容的 `Box` 的最后一个子元素。遮罩会填满整个盒子、读取滚动状态,并在某个方向无法继续滚动时不绘制任何内容。它永远不会拦截滚动手势。

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
    ElegantScrollShadow(state = scrollState)
}
```

## 方向

`ElegantScrollShadowOrientation` 选择阴影跟踪的滚动方向。`Vertical`(默认值)在内容向下滚动后淡出顶部边缘,并在下方仍有更多内容时淡出底部边缘。`Horizontal` 在内容向内滚动后淡出逻辑起始边缘,并在前方仍有更多内容时淡出末端边缘,在 RTL 布局中自动镜像。

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
    ElegantScrollShadow(
        state = scrollState,
        orientation = ElegantScrollShadowOrientation.Horizontal,
    )
}
```

## 组件状态

滚动阴影没有 hover、press、focus 或 disabled 状态。它默认是装饰性的:它会清除自身的语义,让屏幕阅读器跳过该遮罩,并且从不阻挡指针输入。每个淡出从透明开始,随着剩余可滚动距离从零增长到 `ElegantScrollShadowDefaults.ShadowHeight`,逐渐增至 `ElegantScrollShadowDefaults.MaxAlpha`。阴影颜色从 `ElegantTheme` 解析并在 Light 与 Dark 之间自动适配。

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
    ElegantScrollShadow(
        state = scrollState,
        colors = ElegantScrollShadowDefaults.colors(),
    )
}
```

## 属性

### ElegantScrollShadow 属性

| 属性名 | 类型 | 说明 | 默认值 | 是否必须 |
| --- | --- | --- | --- | --- |
| `state` | `ScrollState` | 驱动起始与末端边缘淡出的滚动状态 | - | 是 |
| `modifier` | `Modifier` | 仅应用一次到遮罩根节点的修饰符 | `Modifier` | 否 |
| `colors` | `ElegantScrollShadowColors` | 主题感知的阴影颜色 | `ElegantScrollShadowDefaults.colors()` | 否 |
| `orientation` | `ElegantScrollShadowOrientation` | 阴影跟踪的滚动方向 | `ElegantScrollShadowOrientation.Vertical` | 否 |

### ElegantScrollShadowOrientation

| 选项 | 说明 |
| --- | --- |
| `Vertical` | 淡出纵向滚动内容的顶部与底部边缘 |
| `Horizontal` | 淡出横向滚动内容的起始与末端边缘 |

### ElegantScrollShadowDefaults

| 成员 | 类型 | 说明 |
| --- | --- | --- |
| `ShadowHeight` | `Dp` | 每个可滚动边缘上淡出带的 24dp 高度 |
| `MaxAlpha` | `Float` | 完全显现淡出的 0.35f 最大透明度 |
| `colors()` | `ElegantScrollShadowColors` | Light/Dark 主题感知的阴影颜色 |

### ElegantScrollShadowColors

`ElegantScrollShadowColors` 持有按淡出透明度绘制的单一 `shadowColor`。应先调用 `ElegantScrollShadowDefaults.colors()`,再仅针对产品明确需要的色调使用 `copy(...)`。

## 进阶用法

### 横向滚动阴影

淡出横向滚动卡片行的边缘,让行在两个方向上都暗示内容的延续。

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
    ElegantScrollShadow(
        state = scrollState,
        orientation = ElegantScrollShadowOrientation.Horizontal,
    )
}
```

### 自定义阴影颜色

将淡出色调改为匹配周围表面,而不是主文本颜色。

```kotlin
val shadowColors = ElegantScrollShadowDefaults.colors().copy(
    shadowColor = ElegantTheme.colors.textSecondary,
)

ElegantScrollShadow(
    state = rememberScrollState(),
    colors = shadowColors,
)
```
