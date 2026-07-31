# Empty State

`ElegantEmptyState` 是非交互的展示组件,用于表达"空"的状态:空收件箱、空白搜索结果与尚未开始的项目。它在中央排列可选图标、必填标题、可选描述与可选操作槽,并提供适配浅色与深色模式的主题感知颜色。

<iframe id="demoIframe" style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; border: none;" src="../../compose/index.html?id=empty-state" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>

## 引入

```kotlin
import com.elegant.compose.ui.emptystate.ElegantEmptyState
import com.elegant.compose.ui.emptystate.ElegantEmptyStateColors
import com.elegant.compose.ui.emptystate.ElegantEmptyStateDefaults
```

## 基本用法

图标、标题与描述在居中的列中垂直排列。图标渲染在 64dp 圆形内,标题使用 `titleMedium` 样式,描述使用 `bodyMedium` 样式并居中对齐。空白的描述会被完全省略。

```kotlin
ElegantEmptyState(
    icon = { Icon(Icons.Default.Inbox, contentDescription = null) },
    title = "暂无消息",
    description = "收到消息后,它会显示在这里。",
)
```

## 组件状态

`ElegantEmptyState` 没有 hover、press、focus 或 disabled 状态,也不添加自身的语义;其内容的语义会被完整保留。图标槽拥有自身的语义:纯装饰性图标应保持 `contentDescription` 为 null,若图标承载含义则提供本地化描述。

```kotlin
ElegantEmptyState(
    title = "这里还没有内容",
)
```

## 属性

### ElegantEmptyState 属性

| 属性名 | 类型 | 说明 | 默认值 | 是否必须 |
| --- | --- | --- | --- | --- |
| `icon` | `(@Composable () -> Unit)?` | 64dp 圆形图标容器内的可选视觉内容 | `null` | 否 |
| `modifier` | `Modifier` | 仅应用一次到空状态根节点的修饰符 | `Modifier` | 否 |
| `title` | `String` | 显示在描述上方的主要消息 | - | 是 |
| `description` | `String?` | 标题下方的辅助消息;空白值会被省略 | `null` | 否 |
| `action` | `(@Composable () -> Unit)?` | 描述下方的可选操作槽,以更宽的间距分隔 | `null` | 否 |
| `colors` | `ElegantEmptyStateColors` | 主题感知的图标、标题与描述颜色 | `ElegantEmptyStateDefaults.colors()` | 否 |
| `contentPadding` | `PaddingValues` | 整个布局周围的内边距 | `PaddingValues(ElegantEmptyStateDefaults.DefaultPadding)` | 否 |

### ElegantEmptyStateColors

`ElegantEmptyStateColors` 包含图标容器、图标内容、标题与描述的颜色。应先调用 `ElegantEmptyStateDefaults.colors()`,再仅针对产品明确需要的配色使用 `copy(...)`。

| 属性名 | 类型 | 说明 |
| --- | --- | --- |
| `iconContainerColor` | `Color` | 圆形图标容器背景 |
| `iconContentColor` | `Color` | 通过 `LocalContentColor` 提供的图标内容颜色 |
| `titleColor` | `Color` | 标题文字颜色 |
| `descriptionColor` | `Color` | 描述文字颜色 |

### ElegantEmptyStateDefaults

| 成员 | 类型 | 说明 |
| --- | --- | --- |
| `IconContainerSize` | `Dp` | 64dp 圆形图标容器直径 |
| `ItemGap` | `Dp` | 图标、标题与描述之间的 8dp 垂直间距 |
| `ActionGap` | `Dp` | 描述与操作槽之间的 16dp 垂直间距 |
| `DefaultPadding` | `Dp` | 布局周围 24dp 的默认内边距 |
| `colors()` | `ElegantEmptyStateColors` | 主题感知的 Light/Dark 颜色 |

## 进阶用法

### 引导下一步操作

操作槽承载主要行动号召,以更宽的间距与描述分隔,让组合聚焦于推荐的下一步操作。

```kotlin
ElegantEmptyState(
    icon = { Icon(Icons.Default.Inbox, contentDescription = null) },
    title = "收件箱已清空",
    description = "放心,所有会话都已处理完毕。",
    action = {
        ElegantButton(onClick = { /* 撰写新消息 */ }) {
            Text("新消息")
        }
    },
)
```

### 自定义颜色

```kotlin
val colors = ElegantEmptyStateDefaults.colors().copy(
    iconContainerColor = Color(0x1A147D64),
    iconContentColor = Color(0xFF147D64),
)

ElegantEmptyState(
    icon = { Icon(Icons.Default.Check, contentDescription = null) },
    title = "全部处理完毕",
    colors = colors,
)
```
