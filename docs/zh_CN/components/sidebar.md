# Sidebar

`ElegantSidebar` 是精致的纵向导航表面:受控的条目列表带有选中索引、逐条 enabled 标记,以及可选的页眉与页脚插槽。它适用于应用导航栏、设置面板,以及任何在主内容旁边锚定布局的紧凑目标列表。

<iframe id="demoIframe" style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; border: none;" src="../../compose/index.html?id=sidebar" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>

## 引入

```kotlin
import com.elegant.compose.ui.sidebar.ElegantSidebar
import com.elegant.compose.ui.sidebar.ElegantSidebarColors
import com.elegant.compose.ui.sidebar.ElegantSidebarDefaults
import com.elegant.compose.ui.sidebar.ElegantSidebarItem
```

## 基本用法

`ElegantSidebar` 是受控组件:把 `selectedIndex` 保存在 `remember` 状态中,并从 `onSelect` 写回每次选择。按显示顺序传入条目列表;`selectedIndex` 为 null 时所有条目均处于未选中状态。侧边栏在完整高度上绘制容器色,条目溢出时纵向滚动。

```kotlin
var selected by remember { mutableStateOf(0) }
val items = listOf(
    ElegantSidebarItem("Overview"),
    ElegantSidebarItem("Analytics"),
    ElegantSidebarItem("Reports"),
)

ElegantSidebar(
    selectedIndex = selected,
    onSelect = { selected = it },
    items = items,
    header = {
        Text("Workspace")
    },
)
```

## 条目模型

`ElegantSidebarItem` 是侧边栏渲染的导航模型。`text` 是显示内容;设置 `enabled = false` 会使条目保持静止外观,并且永远不能被选中或触发。

```kotlin
ElegantSidebarItem(
    text = "Legacy settings",
    enabled = false,
)
```

## 组件状态

条目遵循视觉优先级:disabled、selected、hovered、resting。选中条目在 `selectedItemContentColor` 标签下绘制 `selectedItemContainerColor` 背景;悬停或按下未选中条目时绘制 `hoveredItemContainerColor` 与 `hoveredItemContentColor`。禁用条目保持静止容器,并显示 `disabledItemContentColor`。

每个交互条目暴露 `Role.Tab`,并带有 `selected` 与 `disabled` 语义。当 `onSelect` 为 null 时条目退化为纯文本:没有 Tab 角色,也没有 hover、press 或 focus 反馈。整个侧边栏通过 `enabled` 禁用,`ElegantSidebarDefaults.colors()` 会把状态颜色映射到当前的 Light 或 Dark 主题。

```kotlin
ElegantSidebar(
    selectedIndex = 1,
    onSelect = { index -> selected = index },
    items = listOf(
        ElegantSidebarItem("Inbox"),
        ElegantSidebarItem("Archived"),
        ElegantSidebarItem("Trash", enabled = false),
    ),
)

ElegantSidebar(
    selectedIndex = null,
    onSelect = null,
    items = listOf(
        ElegantSidebarItem("Read-only A"),
        ElegantSidebarItem("Read-only B"),
    ),
)
```

## 属性

### ElegantSidebar 属性

| 属性名 | 类型 | 说明 | 默认值 | 是否必须 |
| --- | --- | --- | --- | --- |
| `selectedIndex` | `Int?` | 当前选中的条目索引,由调用方持有;null 表示无选中 | - | 是 |
| `onSelect` | `((Int) -> Unit)?` | 携带所选索引的回调;null 使条目退化为纯文本 | `null` | 否 |
| `items` | `List<ElegantSidebarItem>` | 按顺序渲染的导航条目 | - | 是 |
| `modifier` | `Modifier` | 仅应用一次到侧边栏根节点的修饰符 | `Modifier` | 否 |
| `enabled` | `Boolean` | 侧边栏是否接受选择且条目可被触发 | `true` | 否 |
| `width` | `Dp` | 侧边栏宽度 | `ElegantSidebarDefaults.Width` | 否 |
| `colors` | `ElegantSidebarColors` | 主题感知的状态颜色 | `ElegantSidebarDefaults.colors()` | 否 |
| `header` | `(@Composable () -> Unit)?` | 显示在条目上方的可选页眉内容 | `null` | 否 |
| `footer` | `(@Composable () -> Unit)?` | 显示在条目下方的可选页脚内容 | `null` | 否 |

### ElegantSidebarItem 属性

| 属性名 | 类型 | 说明 | 默认值 | 是否必须 |
| --- | --- | --- | --- | --- |
| `text` | `String` | 侧边栏条目中渲染的文本 | - | 是 |
| `enabled` | `Boolean` | 条目是否可被选中或触发 | `true` | 否 |

### ElegantSidebarDefaults

| 成员 | 类型 | 说明 |
| --- | --- | --- |
| `Width` | `Dp` | 240dp 默认侧边栏宽度 |
| `ItemHeight` | `Dp` | 每条目 40dp 最小高度 |
| `ItemHorizontalPadding` | `Dp` | 每条目内部 12dp 水平内边距 |
| `ItemGap` | `Dp` | 条目之间 4dp 纵向间距 |
| `AnimationDurationMillis` | `Int` | 标准 160ms 状态过渡时长 |
| `colors()` | `ElegantSidebarColors` | 主题感知的 Light/Dark 状态颜色 |

### ElegantSidebarColors

`ElegantSidebarColors` 包含容器色与静止条目色,以及 selected、hovered、disabled 覆盖值。应先调用 `ElegantSidebarDefaults.colors()`,再仅针对产品明确需要的层级使用 `copy(...)`。页眉与页脚内容通过 `LocalContentColor` 获得条目内容色。

## 进阶用法

### 带页眉与页脚的侧边栏

在页眉插槽中组合 `ElegantAvatar` 与账户文本,在页脚插槽中保留短标签。两个插槽都是无样式的区域,带 12dp 水平与 8dp 垂直内边距。

```kotlin
ElegantSidebar(
    selectedIndex = selected,
    onSelect = { selected = it },
    items = listOf(
        ElegantSidebarItem("Overview"),
        ElegantSidebarItem("Analytics"),
        ElegantSidebarItem("Reports"),
    ),
    header = {
        Row(
            horizontalArrangement = Arrangement.spacedBy(ElegantSpacing.md),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            ElegantAvatar(name = "Maya Chen", initials = "MC")
            Column {
                Text("Maya Chen")
                Text("Design systems")
            }
        }
    },
    footer = {
        Text("Settings")
    },
)
```

### 与主内容并排

侧边栏会填满父容器高度,因此它可以锚定布局行,让内容在旁侧流动。

```kotlin
Row {
    ElegantSidebar(
        selectedIndex = selected,
        onSelect = { selected = it },
        items = listOf(
            ElegantSidebarItem("Dashboard"),
            ElegantSidebarItem("Projects"),
            ElegantSidebarItem("Team"),
        ),
        header = {
            Text("Acme Corp")
        },
    )
    Column(
        modifier = Modifier
            .weight(1f)
            .padding(ElegantSpacing.xl),
    ) {
        Text("Dashboard", style = ElegantTheme.typography.titleMedium)
        Text("欢迎回来,Maya。", style = ElegantTheme.typography.bodyMedium)
    }
}
```
