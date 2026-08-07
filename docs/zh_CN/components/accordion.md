# Accordion

`ElegantAccordion` 是带边框的表面组件,将可展开内容组织为堆叠的 `ElegantAccordionItem`。每个条目将 48dp 的头部与 chevron、标题以及可选辅助文本配对,并以垂直动画展开正文。它适用于 FAQ、设置分组与渐进式展示。

<iframe id="demoIframe" style="width: 100%; height: 460px; border: 1px solid var(--vp-c-divider); border-radius: 12px; display: block; background: var(--vp-c-bg-alt);" src="../../compose/index.html?id=accordion" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>

## 引入

```kotlin
import com.elegant.compose.ui.accordion.ElegantAccordion
import com.elegant.compose.ui.accordion.ElegantAccordionColors
import com.elegant.compose.ui.accordion.ElegantAccordionDefaults
import com.elegant.compose.ui.accordion.ElegantAccordionItem
```

## 基本用法

Accordion 是带 1dp 边框与 12dp 圆角的 `Column` 容器;它不添加间距,因此条目紧密堆叠。条目是受控组件:`expanded` 由调用方持有,必须通过 `onToggle` 写回。正文以垂直滑动与淡入动画展开,并带有 16dp 的水平与底部内边距。

```kotlin
var expanded by remember { mutableStateOf(false) }

ElegantAccordion {
    ElegantAccordionItem(
        title = "发布说明",
        expanded = expanded,
        onToggle = { expanded = !expanded },
    ) {
        Text("条目正文以垂直展开动画显现。")
    }
}
```

## 组件状态

每个头部保持 48dp 最小交互根与 16dp 水平内边距。颜色优先级为 disabled、pressed、hovered 或 focused、resting:静止头部为透明色,让表面透出;hovered 头部以 `surfaceHover` 着色,pressed 头部以 `backgroundSubtle` 着色,键盘焦点复用 hovered 着色。禁用时,头部绝不调用 `onToggle`,chevron 降为三级文本色,语义播报禁用状态。

```kotlin
ElegantAccordion {
    ElegantAccordionItem(
        title = "悬停、按下与聚焦头部",
        expanded = true,
        onToggle = {},
        supportingText = "hovered 与 focused 头部以 surfaceHover 着色。",
    ) {
        Text("pressed 头部以 backgroundSubtle 着色。")
    }
    ElegantAccordionItem(
        title = "已禁用条目",
        expanded = false,
        onToggle = {},
        enabled = false,
        supportingText = "禁用的头部绝不调用 onToggle。",
    ) {
        Text("禁用的正文永不展开。")
    }
}
```

## 属性

### ElegantAccordion 属性

| 属性名 | 类型 | 说明 | 默认值 | 是否必须 |
| --- | --- | --- | --- | --- |
| `modifier` | `Modifier` | 仅应用一次到 Accordion 根节点的修饰符 | `Modifier` | 否 |
| `colors` | `ElegantAccordionColors` | 主题感知的状态颜色 | `ElegantAccordionDefaults.colors()` | 否 |
| `content` | `@Composable () -> Unit` | Accordion 内容;通常为一个或多个 `ElegantAccordionItem` | - | 是 |

### ElegantAccordionItem 属性

| 属性名 | 类型 | 说明 | 默认值 | 是否必须 |
| --- | --- | --- | --- | --- |
| `title` | `String` | 头部标签,单行溢出时以省略号截断 | - | 是 |
| `expanded` | `Boolean` | 正文是否展开;由调用方持有 | - | 是 |
| `onToggle` | `() -> Unit` | 头部被激活时切换 `expanded` 的回调 | - | 是 |
| `modifier` | `Modifier` | 仅应用一次到条目根节点的修饰符 | `Modifier` | 否 |
| `enabled` | `Boolean` | 头部是否接受激活 | `true` | 否 |
| `supportingText` | `String?` | 标题下方的可选次级文本行,使用次级文本色 | `null` | 否 |
| `colors` | `ElegantAccordionColors` | 主题感知的状态颜色 | `ElegantAccordionDefaults.colors()` | 否 |
| `content` | `@Composable () -> Unit` | 展开时显示的正文;内边距由条目负责 | - | 是 |

### ElegantAccordionDefaults

| 成员 | 类型 | 说明 |
| --- | --- | --- |
| `MinimumTouchHeight` | `Dp` | 48dp 最小交互头部高度 |
| `AnimationDurationMillis` | `Int` | 标准 160ms 展开、折叠与状态过渡时长 |
| `colors()` | `ElegantAccordionColors` | Light/Dark 主题感知颜色 |

### ElegantAccordionColors

`ElegantAccordionColors` 包含容器色、内容色、头部色与边框色。默认头部容器为透明色,让表面透出;hovered 与 focused 头部使用 `surfaceHover`,pressed 头部使用 `backgroundSubtle`。`dividerColor` 预留给产品级条目分隔线;默认布局不绘制分隔线。应先调用 `ElegantAccordionDefaults.colors()`,再仅针对产品明确需要的层级使用 `copy(...)`。

## 进阶用法

### 单选展开 Accordion

通过记录展开索引保持只有一个条目展开;再次点击同一条目则将其折叠。

```kotlin
var expandedIndex by remember { mutableIntStateOf(-1) }

ElegantAccordion {
    listOf("通用", "外观", "隐私").forEachIndexed { index, item ->
        ElegantAccordionItem(
            title = item,
            expanded = expandedIndex == index,
            onToggle = {
                expandedIndex = if (expandedIndex == index) -1 else index
            },
        ) {
            Text("同一时间只有一个条目保持展开。")
        }
    }
}
```

### 带辅助文本的 FAQ 卡片

将标题与 `supportingText` 配对,在用户展开条目之前预览答案。

```kotlin
ElegantAccordion {
    ElegantAccordionItem(
        title = "什么是 Elegant UI?",
        supportingText = "精致的 Compose Multiplatform 组件",
        expanded = true,
        onToggle = {},
    ) {
        Text("一个在 Android、Desktop 与 Web 之间共享的组件库。")
    }
    ElegantAccordionItem(
        title = "支持哪些平台?",
        expanded = false,
        onToggle = {},
    ) {
        Text("Android 24+、Desktop JVM 与 Web/Wasm。")
    }
}
```
