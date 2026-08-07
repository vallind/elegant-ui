# Breadcrumb

`ElegantBreadcrumb` 是精致的导航辅助组件，用于展示当前页面在层级中的位置。当前页之前的条目作为链接，拥有 48dp 触摸目标、悬停反馈并支持禁用态；末尾条目是当前页，始终保持不可交互的纯文本。逻辑方向的分隔符在 RTL 中自动镜像，并对辅助技术保持纯装饰。

<iframe id="demoIframe" style="width: 100%; height: 460px; border: 1px solid var(--vp-c-divider); border-radius: 12px; display: block; background: var(--vp-c-bg-alt);" src="../../compose/index.html?id=breadcrumb" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>

## 引入

```kotlin
import com.elegant.compose.ui.breadcrumb.ElegantBreadcrumb
import com.elegant.compose.ui.breadcrumb.ElegantBreadcrumbColors
import com.elegant.compose.ui.breadcrumb.ElegantBreadcrumbDefaults
import com.elegant.compose.ui.breadcrumb.ElegantBreadcrumbItem
```

## 基本用法

按逻辑顺序传入层级条目，最后一项始终被视为当前页。`onItemClick` 接收被点击条目的索引，调用方可将索引映射回对应的导航目标。

```kotlin
ElegantBreadcrumb(
    items = listOf(
        ElegantBreadcrumbItem("首页"),
        ElegantBreadcrumbItem("资源库"),
        ElegantBreadcrumbItem("Compose"),
    ),
    onItemClick = { index -> openSection(index) },
)
```

## 条目模型

`ElegantBreadcrumbItem` 描述层级中的一个条目。`text` 是可见标签；`enabled = false` 会使条目呈禁用状态，永远无法被激活。

```kotlin
ElegantBreadcrumbItem(
    text = "已归档",
    enabled = false,
)
```

## 组件状态

末尾条目始终是当前页：不可点击、不暴露按钮角色，并以 `currentColor` 渲染。当条目的 `enabled` 与 `onItemClick` 同时提供时，当前页之前的每个条目都可交互：通过合并语义声明 `Button` 角色、满足 48dp 最小触摸高度，并在指针悬停时显示 `hoveredItemColor`。禁用条目以 `disabledItemColor` 渲染，且永远不会触发回调。

当 `onItemClick` 为 null 时，所有条目都以 `itemColor` 渲染为不可交互的纯文本，此时 Breadcrumb 可单纯用于展示当前位置。

```kotlin
ElegantBreadcrumb(
    items = listOf(
        ElegantBreadcrumbItem("首页"),
        ElegantBreadcrumbItem("草稿箱", enabled = false),
        ElegantBreadcrumbItem("当前草稿"),
    ),
    onItemClick = { index -> openSection(index) },
)

ElegantBreadcrumb(
    items = listOf(
        ElegantBreadcrumbItem("首页"),
        ElegantBreadcrumbItem("设置"),
    ),
)
```

分隔符为纯装饰，不会进入语义树。Row 按自然宽度测量，因此层级较长时请在外部包裹一个横向滚动容器。

## 属性

### ElegantBreadcrumb 属性

| 属性名 | 类型 | 说明 | 默认值 | 是否必须 |
| --- | --- | --- | --- | --- |
| `items` | `List<ElegantBreadcrumbItem>` | 按逻辑顺序排列的层级条目，末尾条目是当前页 | - | 是 |
| `onItemClick` | `((Int) -> Unit)?` | 被点击条目的索引回调；null 使所有条目不可交互 | `null` | 否 |
| `modifier` | `Modifier` | 仅应用一次到 Breadcrumb 根节点的修饰符 | `Modifier` | 否 |
| `colors` | `ElegantBreadcrumbColors` | 主题感知的条目、当前、分隔符、悬停与禁用颜色 | `ElegantBreadcrumbDefaults.colors()` | 否 |

### ElegantBreadcrumbItem 属性

| 属性名 | 类型 | 说明 | 默认值 | 是否必须 |
| --- | --- | --- | --- | --- |
| `text` | `String` | 可见的 Breadcrumb 标签 | - | 是 |
| `enabled` | `Boolean` | 条目是否可以被激活 | `true` | 否 |

### ElegantBreadcrumbDefaults

| 成员 | 类型 | 说明 |
| --- | --- | --- |
| `MinimumTouchHeight` | `Dp` | 可交互条目的 48dp 最小触摸高度 |
| `ItemGap` | `Dp` | 条目与分隔符之间默认 4dp 间距 |
| `colors()` | `ElegantBreadcrumbColors` | 返回 Light/Dark 主题感知的 Breadcrumb 颜色 |

### ElegantBreadcrumbColors

`ElegantBreadcrumbColors` 包含 `itemColor`、`currentColor`、`separatorColor`、`hoveredItemColor`（默认为 `itemColor`）与 `disabledItemColor`（默认为 `itemColor`）。应先调用 `ElegantBreadcrumbDefaults.colors()`，再仅针对产品明确需要的层级使用 `copy(...)`。

## 进阶用法

### 文档标题上方的 Breadcrumb

将 Breadcrumb 放在页面内容上方，使当前条目读起来就像文档标题。

```kotlin
Column {
    ElegantBreadcrumb(
        items = listOf(
            ElegantBreadcrumbItem("首页"),
            ElegantBreadcrumbItem("指南"),
            ElegantBreadcrumbItem("Breadcrumb"),
        ),
        onItemClick = { index -> openGuide(index) },
    )

    Text(
        text = "Breadcrumb",
        style = ElegantTheme.typography.titleMedium,
    )
}
```

### 自定义颜色

```kotlin
val baseColors = ElegantBreadcrumbDefaults.colors()

ElegantBreadcrumb(
    items = listOf(
        ElegantBreadcrumbItem("首页"),
        ElegantBreadcrumbItem("设置"),
    ),
    onItemClick = { index -> openSection(index) },
    colors = baseColors.copy(
        itemColor = Color(0xFF6C4EFF),
        hoveredItemColor = Color(0xFF5840D6),
    ),
)
```
