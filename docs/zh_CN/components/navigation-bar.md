# NavigationBar

`ElegantNavigationBar` 是用于切换主要目的地的受控底部导航栏。它默认将 `ElegantNavigationBarItem` 模型渲染为等宽项目,用标签后的实色胶囊指示器标记当前目的地,并在 Android、Desktop 与 Web 上保持一致的可达性。

<iframe id="demoIframe" style="width: 100%; height: 460px; border: 1px solid var(--vp-c-divider); border-radius: 12px; display: block; background: var(--vp-c-bg-alt);" src="../../compose/index.html?id=navigation-bar" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>

## 引入

```kotlin
import com.elegant.compose.ui.navigationbar.ElegantNavigationBar
import com.elegant.compose.ui.navigationbar.ElegantNavigationBarColors
import com.elegant.compose.ui.navigationbar.ElegantNavigationBarDefaults
import com.elegant.compose.ui.navigationbar.ElegantNavigationBarItem
```

## 基本用法

`ElegantNavigationBar` 是受控组件:将 `selectedIndex` 保存在状态中,并通过 `onSelect` 更新它。默认情况下每个项目等分整条的宽度。

```kotlin
var selected by remember { mutableStateOf(0) }
val items = listOf(
    ElegantNavigationBarItem("首页"),
    ElegantNavigationBarItem("资料库"),
    ElegantNavigationBarItem("设置"),
)

ElegantNavigationBar(
    selectedIndex = selected,
    onSelect = { selected = it },
    items = items,
)
```

## Item 模型

`ElegantNavigationBarItem` 是驱动导航栏的稳定数据模型。将某个模型的 `enabled` 设为 `false`,可以保持该项目可见但不可交互。

```kotlin
val items = listOf(
    ElegantNavigationBarItem("首页"),
    ElegantNavigationBarItem("归档", enabled = false),
    ElegantNavigationBarItem("设置"),
)
```

## 组件状态

每个项目播报 `Role.Tab` 及其 `selected`、`disabled` 状态。超出项目范围的 `selectedIndex` 会被钳制到最后一个项目,空项目列表不渲染任何内容。选中项目在标签后显示实色 32dp 胶囊指示器,并以半粗体渲染标签;悬停或按压未选中项目时,其标签后显示浅色胶囊。

标签文字颜色的状态优先级:disabled、selected、pressed、hovered、resting。

```kotlin
var selected by remember { mutableStateOf(1) }

ElegantNavigationBar(
    selectedIndex = selected,
    onSelect = { selected = it },
    items = listOf(
        ElegantNavigationBarItem("首页"),
        ElegantNavigationBarItem("归档"),
        ElegantNavigationBarItem("共享", enabled = false),
    ),
)

ElegantNavigationBar(
    selectedIndex = 0,
    onSelect = {},
    items = listOf(ElegantNavigationBarItem("离线")),
    enabled = false,
)
```

## 属性

### ElegantNavigationBar 属性

| 属性名 | 类型 | 说明 | 默认值 | 是否必须 |
| --- | --- | --- | --- | --- |
| `items` | `List<ElegantNavigationBarItem>` | 导航栏渲染的项目模型 | - | 是 |
| `selectedIndex` | `Int` | 选中项目的索引;超出范围的值钳制到最后一个项目 | - | 是 |
| `onSelect` | `(Int) -> Unit` | 携带新选中索引的回调 | - | 是 |
| `modifier` | `Modifier` | 仅应用一次到导航栏根节点的修饰符 | `Modifier` | 否 |
| `enabled` | `Boolean` | 导航栏是否接受用户交互 | `true` | 否 |
| `colors` | `ElegantNavigationBarColors` | 主题感知的状态颜色 | `ElegantNavigationBarDefaults.colors()` | 否 |

### ElegantNavigationBarItem 属性

| 属性名 | 类型 | 说明 | 默认值 | 是否必须 |
| --- | --- | --- | --- | --- |
| `text` | `String` | 以 labelMedium 样式渲染的项目文字 | - | 是 |
| `enabled` | `Boolean` | 该项目是否接受用户交互 | `true` | 否 |

### ElegantNavigationBarDefaults

| 成员 | 类型 | 说明 |
| --- | --- | --- |
| `MinimumTouchHeight` | `Dp` | 64dp 最小交互根高度 |
| `IndicatorSize` | `Dp` | 32dp 选中胶囊指示器高度 |
| `AnimationDurationMillis` | `Int` | 标准 160ms 标签文字颜色过渡时长 |
| `colors()` | `ElegantNavigationBarColors` | Light/Dark 主题感知颜色 |

### ElegantNavigationBarColors

`ElegantNavigationBarColors` 包含 resting、selected、hovered、pressed 与 disabled 状态的容器色、项目色、文字颜色与指示器颜色。应先调用 `ElegantNavigationBarDefaults.colors()`,再仅针对产品明确需要的层级使用 `copy(...)`。

## 进阶用法

### 页面底部的导航栏

保持导航栏受控,并用 spacer 将导航栏固定在内容下方。

```kotlin
var selected by remember { mutableStateOf(0) }
val items = listOf(
    ElegantNavigationBarItem("首页"),
    ElegantNavigationBarItem("搜索"),
    ElegantNavigationBarItem("我的"),
)

Column(modifier = Modifier.fillMaxSize()) {
    Text(
        text = "正在显示 ${items[selected].text}",
        style = ElegantTheme.typography.bodyMedium,
        color = ElegantTheme.colors.textSecondary,
        modifier = Modifier.padding(ElegantSpacing.xl),
    )
    Spacer(modifier = Modifier.weight(1f))
    ElegantNavigationBar(
        selectedIndex = selected,
        onSelect = { selected = it },
        items = items,
    )
}
```

### 自定义强调

```kotlin
val baseColors = ElegantNavigationBarDefaults.colors()

ElegantNavigationBar(
    selectedIndex = selected,
    onSelect = { selected = it },
    items = listOf(ElegantNavigationBarItem("首页"), ElegantNavigationBarItem("我的")),
    colors = baseColors.copy(
        selectedContentColor = Color(0xFF147D64),
        indicatorColor = Color(0xFFDCE8FF),
    ),
)
```
