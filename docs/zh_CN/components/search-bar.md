# Search Bar

`ElegantSearchBar` 是用于过滤与查找的胶囊形搜索输入框:组件持有自绘的放大镜图标、可选的清除按钮与 IME 搜索动作,可置于任意内容列表之上。当带标签与辅助文本的完整 `ElegantInput` 对该任务而言过重时,请使用它。

<iframe id="demoIframe" style="width: 100%; height: 460px; border: 1px solid var(--vp-c-divider); border-radius: 12px; display: block; background: var(--vp-c-bg-alt);" src="../../compose/index.html?id=search-bar" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>

## 引入

```kotlin
import com.elegant.compose.ui.searchbar.ElegantSearchBar
import com.elegant.compose.ui.searchbar.ElegantSearchBarColors
import com.elegant.compose.ui.searchbar.ElegantSearchBarDefaults
```

## 基本用法

`ElegantSearchBar` 是受控组件:将 `query` 保存在 `remember` 状态中,并在 `onQueryChange` 中写回每一次被接受的变更。前导放大镜图标由组件自绘并持有,因此无需配置图标参数。

```kotlin
var query by remember { mutableStateOf("") }

ElegantSearchBar(
    query = query,
    onQueryChange = { query = it },
    placeholder = "搜索组件",
)
```

## 组件状态

`ElegantSearchBar` 遵循交互优先级:disabled、focused、hovered、resting。主题启用焦点环时,聚焦以 `focusRing` 颜色勾勒胶囊轮廓;悬停时容器轻微着色,几何形状不变。当 `enabled` 为 false 时,输入框拒绝聚焦与输入,并以透明边框渲染凹陷容器。

当查询不为空时,尾端会出现带手绘 X 的清除按钮;点击它会调用 `onClear`,默认行为是清空查询。占位符仅在输入框启用且为空时显示。

```kotlin
ElegantSearchBar(
    query = "Components",
    onQueryChange = {},
    placeholder = "搜索组件",
)

ElegantSearchBar(
    query = "Components",
    onQueryChange = {},
    enabled = false,
)

ElegantSearchBar(
    query = query,
    onQueryChange = { query = it },
    placeholder = "搜索组件",
    onClear = { query = "" },
)
```

## 属性

### ElegantSearchBar 属性

| 属性名 | 类型 | 说明 | 默认值 | 是否必须 |
| --- | --- | --- | --- | --- |
| `query` | `String` | 当前搜索查询,由调用方持有 | - | 是 |
| `onQueryChange` | `(String) -> Unit` | 以最新被接受的查询触发的回调 | - | 是 |
| `modifier` | `Modifier` | 仅应用一次到搜索框根节点的修饰符 | `Modifier` | 否 |
| `enabled` | `Boolean` | 输入框是否接受聚焦、输入与清除动作 | `true` | 否 |
| `placeholder` | `String?` | 输入框启用且为空时显示在内部的提示 | `null` | 否 |
| `onSearch` | `(() -> Unit)?` | 由 IME 搜索动作与回车键触发的回调 | `null` | 否 |
| `onClear` | `(() -> Unit)?` | 由清除按钮触发的回调;为 null 时清空查询 | `null` | 否 |
| `colors` | `ElegantSearchBarColors` | 主题感知的状态颜色 | `ElegantSearchBarDefaults.colors()` | 否 |
| `trailingContent` | `@Composable (() -> Unit)?` | 输入区域之后的内容 | `null` | 否 |

### ElegantSearchBarDefaults

| 成员 | 类型 | 说明 |
| --- | --- | --- |
| `MinimumTouchHeight` | `Dp` | 48dp 最小输入框高度 |
| `colors()` | `ElegantSearchBarColors` | 主题感知的 Light/Dark 状态颜色 |
| `shape()` | `Shape` | 完全圆角的胶囊形 |

### ElegantSearchBarColors

`ElegantSearchBarColors` 包含 resting、hovered、focused 与 disabled 各状态的容器色、边框色与内容色,以及占位符颜色。应先调用 `ElegantSearchBarDefaults.colors()`,再仅针对产品明确需要的层级使用 `copy(...)`。

## 进阶用法

### 搜索动作与回车键

传入 `onSearch` 以播报并触发搜索:IME 会显示搜索动作按钮,而在桌面键盘上按下回车会触发同一回调。

```kotlin
var query by remember { mutableStateOf("") }
var submitted by remember { mutableStateOf("") }

ElegantSearchBar(
    query = query,
    onQueryChange = { query = it },
    placeholder = "搜索商品目录",
    onSearch = { submitted = query },
)
```

### 过滤列表

将搜索框与 `ElegantList` 组合,按查询过滤条目。

```kotlin
val allItems = listOf("Badge", "Divider", "Input", "Tag")
var query by remember { mutableStateOf("") }
val visibleItems = allItems.filter { it.contains(query, ignoreCase = true) }

ElegantSearchBar(
    query = query,
    onQueryChange = { query = it },
    placeholder = "过滤组件",
)

ElegantList {
    visibleItems.forEach { item ->
        ElegantListItem(
            title = { Text(item) },
        )
    }
}
```
