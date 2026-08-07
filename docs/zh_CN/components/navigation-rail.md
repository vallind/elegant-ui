# Navigation Rail

`ElegantNavigationRail` 是精致的紧凑导航表面:受控的纵向轨道带有选中索引、逐条 enabled 标记,以及可选的页眉与页脚插槽。它适用于在桌面与平板布局中锚定目标切换,位于侧边栏旁或主内容旁边。

<iframe id="demoIframe" style="width: 100%; height: 460px; border: 1px solid var(--vp-c-divider); border-radius: 12px; display: block; background: var(--vp-c-bg-alt);" src="../../compose/index.html?id=navigation-rail" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>

## 引入

```kotlin
import com.elegant.compose.ui.navigationrail.ElegantNavigationRail
import com.elegant.compose.ui.navigationrail.ElegantNavigationRailColors
import com.elegant.compose.ui.navigationrail.ElegantNavigationRailDefaults
import com.elegant.compose.ui.navigationrail.ElegantNavigationRailItem
```

## 基本用法

`ElegantNavigationRail` 是受控组件:把 `selectedIndex` 保存在 `remember` 状态中,并从 `onSelect` 写回每次选择。按显示顺序传入条目列表;超出范围的索引会被收敛到条目范围内。轨道在完整高度上绘制容器色,条目溢出时纵向滚动。

```kotlin
var selected by remember { mutableStateOf(0) }
val items = listOf(
    ElegantNavigationRailItem("Home"),
    ElegantNavigationRailItem("Search"),
    ElegantNavigationRailItem("Profile"),
)

ElegantNavigationRail(
    selectedIndex = selected,
    onSelect = { selected = it },
    items = items,
)
```

## 条目模型

`ElegantNavigationRailItem` 是轨道渲染的导航模型。`text` 是显示内容;设置 `enabled = false` 会使条目保持静止外观,并且永远不能被触发。

```kotlin
ElegantNavigationRailItem(
    text = "Legacy settings",
    enabled = false,
)
```

## 组件状态

条目遵循视觉优先级:disabled、selected、hovered 或 pressed、resting。选中条目在 `selectedContentColor` 标签下绘制 `indicatorColor`;悬停或按下未选中条目时绘制 `hoveredContainerColor` 与 `hoveredItemColor`,悬停或按下选中条目时绘制 `selectedItemColor`。禁用条目保持静止指示器,并显示 `disabledItemColor`。

每个交互条目暴露 `Role.Tab`,并带有 `selected` 与 `disabled` 语义,且每个点击目标至少 48dp 高。整个轨道通过 `enabled` 禁用,`ElegantNavigationRailDefaults.colors()` 会把状态颜色映射到当前的 Light 或 Dark 主题。

```kotlin
ElegantNavigationRail(
    selectedIndex = 1,
    onSelect = { index -> selected = index },
    items = listOf(
        ElegantNavigationRailItem("Inbox"),
        ElegantNavigationRailItem("Archived"),
        ElegantNavigationRailItem("Trash", enabled = false),
    ),
)

ElegantNavigationRail(
    selectedIndex = selected,
    onSelect = { selected = it },
    enabled = false,
    items = listOf(
        ElegantNavigationRailItem("Read-only A"),
        ElegantNavigationRailItem("Read-only B"),
    ),
)
```

## 属性

### ElegantNavigationRail 属性

| 属性名 | 类型 | 说明 | 默认值 | 是否必须 |
| --- | --- | --- | --- | --- |
| `selectedIndex` | `Int` | 当前选中的条目索引,由调用方持有并收敛到条目范围内 | - | 是 |
| `onSelect` | `(Int) -> Unit` | 携带所选索引的回调 | - | 是 |
| `items` | `List<ElegantNavigationRailItem>` | 按顺序渲染的导航条目 | - | 是 |
| `modifier` | `Modifier` | 仅应用一次到轨道根节点的修饰符 | `Modifier` | 否 |
| `enabled` | `Boolean` | 轨道是否接受选择且条目可被触发 | `true` | 否 |
| `colors` | `ElegantNavigationRailColors` | 主题感知的状态颜色 | `ElegantNavigationRailDefaults.colors()` | 否 |
| `header` | `(@Composable () -> Unit)?` | 显示在条目上方的可选页眉内容 | `null` | 否 |
| `footer` | `(@Composable () -> Unit)?` | 显示在条目下方的可选页脚内容 | `null` | 否 |

### ElegantNavigationRailItem 属性

| 属性名 | 类型 | 说明 | 默认值 | 是否必须 |
| --- | --- | --- | --- | --- |
| `text` | `String` | 作为条目标签渲染的文本 | - | 是 |
| `enabled` | `Boolean` | 条目是否可被触发 | `true` | 否 |

### ElegantNavigationRailDefaults

| 成员 | 类型 | 说明 |
| --- | --- | --- |
| `Width` | `Dp` | 80dp 默认轨道宽度 |
| `MinimumTouchHeight` | `Dp` | 每条目 48dp 最小点击目标高度 |
| `IndicatorSize` | `Dp` | 圆形指示器 48dp 直径 |
| `AnimationDurationMillis` | `Int` | 标准 160ms 状态过渡时长 |
| `colors()` | `ElegantNavigationRailColors` | 主题感知的 Light/Dark 状态颜色 |

### ElegantNavigationRailColors

`ElegantNavigationRailColors` 包含容器色、选中指示器与内容色,以及 resting、hovered、disabled 条目覆盖值。应先调用 `ElegantNavigationRailDefaults.colors()`,再仅针对产品明确需要的层级使用 `copy(...)`。

## 进阶用法

### 带页眉与页脚的轨道

在页眉插槽中放入 `ElegantAvatar`,在页脚插槽中保留短标签。两个插槽都是无样式的区域,带 8dp 垂直内边距,并在轨道宽度内居中。

```kotlin
ElegantNavigationRail(
    selectedIndex = selected,
    onSelect = { selected = it },
    items = listOf(
        ElegantNavigationRailItem("Home"),
        ElegantNavigationRailItem("Search"),
        ElegantNavigationRailItem("Notifications"),
        ElegantNavigationRailItem("Profile"),
    ),
    header = {
        ElegantAvatar(name = "Maya Chen", initials = "MC")
    },
    footer = {
        Text("Settings")
    },
)
```

### 与主内容并排

轨道会填满父容器高度,因此它可以锚定布局行,让内容在旁侧流动。

```kotlin
Row {
    ElegantNavigationRail(
        selectedIndex = selected,
        onSelect = { selected = it },
        items = listOf(
            ElegantNavigationRailItem("Dashboard"),
            ElegantNavigationRailItem("Projects"),
            ElegantNavigationRailItem("Team"),
        ),
        header = {
            ElegantAvatar(name = "Acme Corp", initials = "AC")
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
