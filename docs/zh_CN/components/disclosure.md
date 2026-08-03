# Disclosure

`ElegantDisclosure` 是单个可展开区块,以独立带边框块呈现:48dp 的头部带有标题、可选辅助文本与尾部 chevron,正文以垂直动画展开。`ElegantDisclosureGroup` 将多个 disclosure 包裹在一个带边框的表面中。它适用于 FAQ、筛选面板与渐进式展示。

<iframe id="demoIframe" style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; border: none;" src="../../compose/index.html?id=disclosure" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>

## 引入

```kotlin
import com.elegant.compose.ui.disclosure.ElegantDisclosure
import com.elegant.compose.ui.disclosure.ElegantDisclosureColors
import com.elegant.compose.ui.disclosure.ElegantDisclosureDefaults
import com.elegant.compose.ui.disclosure.ElegantDisclosureGroup
```

## 基本用法

Disclosure 是带 1dp 边框与 12dp 圆角的 `Column` 表面,因此独立的 disclosure 表现为一张卡片。它是受控组件:`expanded` 由调用方持有,必须通过 `onToggle` 写回。正文以垂直滑动与淡入动画展开,并带有 16dp 的水平与底部内边距。

```kotlin
var expanded by remember { mutableStateOf(false) }

ElegantDisclosure(
    title = "发布说明",
    expanded = expanded,
    onToggle = { expanded = !expanded },
) {
    Text("正文以垂直展开动画显现。")
}
```

## 组件状态

每个头部保持 48dp 最小交互根与 16dp 水平内边距。颜色优先级为 disabled、pressed、hovered 或 focused、resting:静止头部为透明色,让表面透出;hovered 头部以 `surfaceHover` 着色,pressed 头部以 `backgroundSubtle` 着色,键盘焦点复用 hovered 着色。禁用时,头部绝不调用 `onToggle`,chevron 降为三级文本色,语义播报禁用状态。

```kotlin
ElegantDisclosure(
    title = "悬停、按下与聚焦头部",
    expanded = true,
    onToggle = {},
    supportingText = "hovered 与 focused 头部以 surfaceHover 着色。",
) {
    Text("pressed 头部以 backgroundSubtle 着色。")
}
ElegantDisclosure(
    title = "已禁用 Disclosure",
    expanded = false,
    onToggle = {},
    enabled = false,
    supportingText = "禁用的头部绝不调用 onToggle。",
) {
    Text("禁用的正文永不展开。")
}
```

## 属性

### ElegantDisclosure 属性

| 属性名 | 类型 | 说明 | 默认值 | 是否必须 |
| --- | --- | --- | --- | --- |
| `title` | `String` | 头部标签,单行溢出时以省略号截断 | - | 是 |
| `expanded` | `Boolean` | 正文是否展开;由调用方持有 | - | 是 |
| `onToggle` | `() -> Unit` | 头部被激活时切换 `expanded` 的回调 | - | 是 |
| `modifier` | `Modifier` | 仅应用一次到 disclosure 根节点的修饰符 | `Modifier` | 否 |
| `enabled` | `Boolean` | 头部是否接受激活 | `true` | 否 |
| `supportingText` | `String?` | 标题下方的可选次级文本行,使用次级文本色 | `null` | 否 |
| `colors` | `ElegantDisclosureColors` | 主题感知的状态颜色 | `ElegantDisclosureDefaults.colors()` | 否 |
| `content` | `@Composable () -> Unit` | 展开时显示的正文;内边距由 disclosure 负责 | - | 是 |

### ElegantDisclosureGroup 属性

| 属性名 | 类型 | 说明 | 默认值 | 是否必须 |
| --- | --- | --- | --- | --- |
| `modifier` | `Modifier` | 仅应用一次到分组根节点的修饰符 | `Modifier` | 否 |
| `colors` | `ElegantDisclosureColors` | 主题感知的状态颜色 | `ElegantDisclosureDefaults.colors()` | 否 |
| `content` | `@Composable () -> Unit` | 分组内容;通常为一个或多个 `ElegantDisclosure` | - | 是 |

### ElegantDisclosureDefaults

| 成员 | 类型 | 说明 |
| --- | --- | --- |
| `MinimumTouchHeight` | `Dp` | 48dp 最小交互头部高度 |
| `AnimationDurationMillis` | `Int` | 标准 160ms 展开、折叠与状态过渡时长 |
| `colors()` | `ElegantDisclosureColors` | Light/Dark 主题感知颜色 |

### ElegantDisclosureColors

`ElegantDisclosureColors` 包含容器色、内容色、头部色与边框色。默认头部容器为透明色,让表面透出;hovered 与 focused 头部使用 `surfaceHover`,pressed 头部使用 `backgroundSubtle`。应先调用 `ElegantDisclosureDefaults.colors()`,再仅针对产品明确需要的层级使用 `copy(...)`。

## 进阶用法

### 单选展开 Disclosure

通过记录展开索引保持只有一个 disclosure 展开;再次点击同一个 disclosure 则将其折叠。

```kotlin
var expandedIndex by remember { mutableIntStateOf(-1) }

listOf("通用", "外观", "隐私").forEachIndexed { index, item ->
    ElegantDisclosure(
        title = item,
        expanded = expandedIndex == index,
        onToggle = {
            expandedIndex = if (expandedIndex == index) -1 else index
        },
    ) {
        Text("同一时间只有一个 disclosure 保持展开。")
    }
}
```

### 带 DisclosureGroup 的 FAQ

`ElegantDisclosureGroup` 将堆叠的 disclosure 包裹在一个带边框的表面中;它不绘制兄弟项之间的分隔线,因此每个 disclosure 保留各自的带边框块。

```kotlin
ElegantDisclosureGroup {
    ElegantDisclosure(
        title = "什么是 Elegant UI?",
        supportingText = "精致的 Compose Multiplatform 组件",
        expanded = true,
        onToggle = {},
    ) {
        Text("一个在 Android、Desktop 与 Web 之间共享的组件库。")
    }
    ElegantDisclosure(
        title = "支持哪些平台?",
        expanded = false,
        onToggle = {},
    ) {
        Text("Android 24+、Desktop JVM 与 Web/Wasm。")
    }
}
```
