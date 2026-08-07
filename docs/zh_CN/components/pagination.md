# Pagination

`ElegantPagination` 是 Elegant UI 中紧凑的跨平台分页控件。它渲染一行有边界的页码项与上一页、下一页的 chevron 按钮,始终保留首页与末页,将远处的页码折叠为省略号,并通过受控的 `page` 状态配合 `onPageChange` 暴露当前页。

<iframe id="demoIframe" style="width: 100%; height: 460px; border: 1px solid var(--vp-c-divider); border-radius: 12px; display: block; background: var(--vp-c-bg-alt);" src="../../compose/index.html?id=pagination" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>

## 引入

```kotlin
import com.elegant.compose.ui.pagination.ElegantPagination
import com.elegant.compose.ui.pagination.ElegantPaginationColors
import com.elegant.compose.ui.pagination.ElegantPaginationDefaults
```

## 基本用法

`ElegantPagination` 是受控组件:传入当前 `page`,并在 `onPageChange` 中更新它。

```kotlin
var page by remember { mutableStateOf(1) }

ElegantPagination(
    page = page,
    onPageChange = { page = it },
    pageCount = 10,
)
```

## 页码项

这一行始终展示首页、末页以及当前页附近的页码,并把每个折叠的空隙折叠为单个省略号。当前页高亮显示且不会调用 `onPageChange`;首页与末页时,上一页与下一页的 chevron 按钮会被禁用。使用 `siblingCount` 可以扩大可见页码的窗口。

```kotlin
var page by remember { mutableStateOf(1) }

ElegantPagination(
    page = page,
    onPageChange = { page = it },
    pageCount = 20,
    siblingCount = 1,
)
```

## 组件状态

Hover、press 与键盘焦点反馈会按项自动解析。禁用项使用三级文本色,保持原有布局,且不会调用 `onPageChange`。

### 禁用状态

```kotlin
ElegantPagination(
    page = 1,
    onPageChange = { /* 处理页码切换事件 */ },
    pageCount = 10,
    enabled = false,
)
```

## 属性

### ElegantPagination 属性

| 属性名 | 类型 | 说明 | 默认值 | 是否必须 |
| --- | --- | --- | --- | --- |
| `page` | `Int` | 当前页码,以选中态展示 | - | 是 |
| `onPageChange` | `(Int) -> Unit` | 某项被激活时携带目标页调用的回调 | - | 是 |
| `pageCount` | `Int` | 总页数;非正数时渲染为空 | - | 是 |
| `modifier` | `Modifier` | 应用到分页行的修饰符 | `Modifier` | 否 |
| `enabled` | `Boolean` | 分页是否接受用户交互 | `true` | 否 |
| `siblingCount` | `Int` | 当前页两侧展示的页码数量 | `ElegantPaginationDefaults.SiblingCount` | 否 |
| `colors` | `ElegantPaginationColors` | 行、页码项与选中页的主题感知颜色 | `ElegantPaginationDefaults.colors()` | 否 |

### ElegantPaginationDefaults

| 成员 | 类型 | 说明 |
| --- | --- | --- |
| `ItemSize` | `Dp` | 每个方形页码项与圆形 chevron 按钮的边长 |
| `ItemGap` | `Dp` | 相邻项之间的间距 |
| `AnimationDurationMillis` | `Int` | 标准状态过渡时长 |
| `SiblingCount` | `Int` | 当前页两侧默认展示的页码数量 |
| `colors()` | `ElegantPaginationColors` | 返回主题感知的分页颜色 |

### ElegantPaginationColors

`ElegantPaginationColors` 包含行容器色、选中页的容器色与内容色、静止项的内容色,以及 hovered、pressed、disabled 各项的颜色。应先调用 `ElegantPaginationDefaults.colors()`,再仅针对产品支持的取值使用 `copy(...)` 覆盖。

## 进阶用法

### 更多相邻页码

```kotlin
var page by remember { mutableStateOf(5) }

ElegantPagination(
    page = page,
    onPageChange = { page = it },
    pageCount = 10,
    siblingCount = 2,
)
```

### 自定义颜色

```kotlin
val baseColors = ElegantPaginationDefaults.colors()

ElegantPagination(
    page = 1,
    onPageChange = { /* 处理页码切换事件 */ },
    pageCount = 10,
    colors = baseColors.copy(
        selectedItemColor = Color(0xFF0F766E),
        hoveredItemColor = Color(0xFFCCFBF1),
    ),
)
```
