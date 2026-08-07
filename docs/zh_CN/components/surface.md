# Surface

`ElegantSurface` 是组件库中的低层容器原语。与 `ElegantCard`(带 `Filled`/`Outlined`/`Elevated` 风格预设的内容表面)不同,Surface 没有风格枚举、没有内置内边距,也没有文本样式:它只渲染背景、可选边框与可选点击交互,把间距与样式完全交给调用方。请把它用作自定义容器的地基,或嵌套它来搭建分层布局。

<iframe id="demoIframe" style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; border: none;" src="../../compose/index.html?id=surface" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>

## 引入

```kotlin
import com.elegant.compose.ui.surface.ElegantSurface
import com.elegant.compose.ui.surface.ElegantSurfaceColors
import com.elegant.compose.ui.surface.ElegantSurfaceDefaults
```

## 基本用法

不传 `onClick` 的 Surface 是纯容器:它按 `shape` 裁剪,填充容器色,绘制可选边框,并保留内容自身的语义。Surface 没有内边距也没有文本样式——间距与排版由调用方在内容中负责,内容通过 `LocalContentColor` 获得内容色。

```kotlin
ElegantSurface(borderWidth = 1.dp) {
    Column(Modifier.padding(16.dp)) {
        Text("基础层")
        Text("Surface 本身不携带间距或文本样式。")
    }
}
```

## 组件状态

传入 `onClick` 后 Surface 变为按钮式容器:它保持 48dp 最小交互根,播报 `Role.Button`,提供带波纹的 hover 与 press 容器色反馈,在主题启用焦点环且聚焦、启用时绘制 2dp 焦点环,并在 `enabled` 为 false 时拒绝交互。

状态优先级:disabled、pressed、hovered、resting。focus 只覆盖边框,从不改变容器色。

```kotlin
var taps by remember { mutableIntStateOf(0) }

ElegantSurface(
    onClick = { taps += 1 },
    borderWidth = 1.dp,
) {
    Column(Modifier.padding(16.dp)) {
        Text("点击激活")
        Text("已激活 $taps 次")
    }
}

ElegantSurface(
    onClick = {},
    enabled = false,
) {
    Text("已禁用")
}
```

## 属性

### ElegantSurface 属性

| 属性名 | 类型 | 说明 | 默认值 | 是否必须 |
| --- | --- | --- | --- | --- |
| `onClick` | `(() -> Unit)?` | 可选激活回调;null 保持非交互 | `null` | 否 |
| `modifier` | `Modifier` | 仅应用一次到 Surface 根节点的修饰符 | `Modifier` | 否 |
| `enabled` | `Boolean` | 是否接受用户交互 | `true` | 否 |
| `shape` | `Shape` | 裁剪与边框形状 | `ElegantSurfaceDefaults.shape()` | 否 |
| `colors` | `ElegantSurfaceColors` | 主题感知的状态颜色 | `ElegantSurfaceDefaults.colors()` | 否 |
| `borderWidth` | `Dp` | 常驻边框宽度;0 表示无边框 | `0.dp` | 否 |
| `content` | `@Composable () -> Unit` | Surface 内容;内边距与文本样式由调用方负责 | - | 是 |

### ElegantSurfaceDefaults

| 成员 | 类型 | 说明 |
| --- | --- | --- |
| `MinimumTouchHeight` | `Dp` | 可点击 Surface 使用的 48dp 最小交互根高度 |
| `AnimationDurationMillis` | `Int` | 标准 160ms 状态过渡时长 |
| `colors()` | `ElegantSurfaceColors` | Light/Dark 主题感知颜色 |
| `shape()` | `Shape` | 共享的 8dp 圆角形状 |

### ElegantSurfaceColors

`ElegantSurfaceColors` 包含容器色、内容色与边框色,以及 hovered、pressed、disabled 与焦点环覆盖值。应先调用 `ElegantSurfaceDefaults.colors()`,再仅针对产品明确需要的层级使用 `copy(...)`。

## 进阶用法

### 嵌套表面组合

Surface 可以自由嵌套:外层带边框的 Surface 可以容纳内层 Surface,在更深的视觉层级上组织内容。每个 Surface 各自提供 `LocalContentColor`,因此嵌套文本始终使用其直接容器的颜色。

```kotlin
ElegantSurface(borderWidth = 1.dp) {
    Column(Modifier.padding(16.dp)) {
        Text("外层表面")
        ElegantSurface {
            Column(Modifier.padding(12.dp)) {
                Text("内层表面")
            }
        }
    }
}
```

### 自定义颜色与边框

```kotlin
val baseColors = ElegantSurfaceDefaults.colors()

ElegantSurface(
    borderWidth = 1.dp,
    colors = baseColors.copy(
        containerColor = Color(0xFF6C4EFF),
        contentColor = Color.White,
        borderColor = Color(0xFF6C4EFF),
    ),
) {
    Text("自定义表面")
}
```
