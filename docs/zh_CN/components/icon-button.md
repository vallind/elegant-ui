# IconButton

`ElegantIconButton` 是面向工具栏、卡片与紧凑控件的跨平台图标操作组件。它将必需的无障碍名称与三种强调层级、光学调校图标尺寸、指针悬停、触控按压、键盘焦点及几何稳定的加载状态结合在一起。

<iframe id="demoIframe" style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; border: none;" src="../../compose/index.html?id=icon-button" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>

## 引入

```kotlin
import com.elegant.compose.ui.iconbutton.ElegantIconButton
import com.elegant.compose.ui.iconbutton.ElegantIconButtonColors
import com.elegant.compose.ui.iconbutton.ElegantIconButtonDefaults
import com.elegant.compose.ui.iconbutton.ElegantIconButtonElevation
import com.elegant.compose.ui.iconbutton.ElegantIconButtonSize
import com.elegant.compose.ui.iconbutton.ElegantIconButtonStyle
```

## 基本用法

为图标按钮提供简短、面向操作的 `contentDescription`。组件已经拥有无障碍名称，因此内部图标应保持装饰性。

```kotlin
ElegantIconButton(
    onClick = { /* 编辑项目 */ },
    contentDescription = "编辑项目",
) {
    Icon(
        imageVector = Icons.Default.Edit,
        contentDescription = null,
    )
}
```

## 图标按钮类型

最高强调的紧凑操作使用 `Primary`，带容器的辅助操作使用 `Secondary`，安静的工具栏操作使用默认的 `Tertiary`。

```kotlin
Row {
    ElegantIconButton(
        onClick = { /* 保存 */ },
        contentDescription = "保存",
        style = ElegantIconButtonStyle.Primary,
    ) {
        Icon(Icons.Default.Check, contentDescription = null)
    }
    ElegantIconButton(
        onClick = { /* 分享 */ },
        contentDescription = "分享",
        style = ElegantIconButtonStyle.Secondary,
    ) {
        Icon(Icons.Default.Share, contentDescription = null)
    }
    ElegantIconButton(
        onClick = { /* 更多 */ },
        contentDescription = "更多选项",
    ) {
        Icon(Icons.Default.MoreVert, contentDescription = null)
    }
}
```

## 组件状态

悬停、按压与键盘焦点由同一个交互源解析。加载状态会禁用激活、保留可视容器、以进度替换图标，并暴露 `loadingStateDescription`。

```kotlin
ElegantIconButton(
    onClick = { /* 重试 */ },
    contentDescription = "重试",
    loading = isRetrying,
    loadingStateDescription = "正在重试",
    style = ElegantIconButtonStyle.Primary,
) {
    Icon(Icons.Default.Refresh, contentDescription = null)
}

ElegantIconButton(
    onClick = {},
    contentDescription = "删除项目",
    enabled = false,
) {
    Icon(Icons.Default.Delete, contentDescription = null)
}
```

## 属性

### ElegantIconButton 属性

| 属性名 | 类型 | 说明 | 默认值 | 是否必须 |
| --- | --- | --- | --- | --- |
| `onClick` | `() -> Unit` | 操作接受激活时调用的回调 | - | 是 |
| `contentDescription` | `String` | 描述操作的本地化无障碍名称 | - | 是 |
| `modifier` | `Modifier` | 应用于最小 48dp 交互根节点的修饰符 | `Modifier` | 否 |
| `enabled` | `Boolean` | 操作是否接受交互 | `true` | 否 |
| `loading` | `Boolean` | 以进度替换图标并阻止重复激活 | `false` | 否 |
| `loadingStateDescription` | `String` | 加载时播报的本地化无障碍状态 | `"Loading"` | 否 |
| `interactionSource` | `MutableInteractionSource?` | 用于观察悬停、按压和焦点的可选提升交互源 | `null` | 否 |
| `style` | `ElegantIconButtonStyle` | 紧凑操作的视觉强调层级 | `ElegantIconButtonStyle.Tertiary` | 否 |
| `size` | `ElegantIconButtonSize` | 可视容器与图标尺寸预设 | `ElegantIconButtonSize.Medium` | 否 |
| `shape` | `Shape` | 经过光学调校的容器形状 | `ElegantIconButtonDefaults.shape(size)` | 否 |
| `colors` | `ElegantIconButtonColors` | 主题感知的交互颜色与边框度量 | `ElegantIconButtonDefaults.colors(style)` | 否 |
| `elevation` | `ElegantIconButtonElevation` | 感知状态的层级模型 | `ElegantIconButtonDefaults.elevation(style)` | 否 |
| `content` | `@Composable () -> Unit` | 装饰性图标内容 | - | 是 |

### ElegantIconButtonStyle 可选值

| 值 | 说明 |
| --- | --- |
| `Primary` | 使用主要色容器的最高强调紧凑操作 |
| `Secondary` | 使用抬升容器与边框的辅助操作 |
| `Tertiary` | 默认使用透明容器的安静操作 |

### ElegantIconButtonSize 可选值

| 值 | 可视容器 | 最小触控尺寸 | 图标尺寸 |
| --- | --- | --- | --- |
| `Small` | `32.dp` | `48.dp` | `16.dp` |
| `Medium` | `40.dp` | `48.dp` | `20.dp` |
| `Large` | `48.dp` | `48.dp` | `24.dp` |

### ElegantIconButtonDefaults

| 成员 | 类型 | 说明 |
| --- | --- | --- |
| `MinimumTouchSize` | `Dp` | 所有交互根节点的最小宽度与高度 |
| `AnimationDurationMillis` | `Int` | 标准悬停、焦点与状态过渡时长 |
| `PressAnimationDurationMillis` | `Int` | 即时按压反馈时长 |
| `HoveredScale` | `Float` | 克制的指针悬停缩放比例 |
| `PressedScale` | `Float` | 克制的按压缩放比例 |
| `colors(style)` | `ElegantIconButtonColors` | 返回指定强调类型的主题感知颜色 |
| `shape(size)` | `Shape` | 返回指定尺寸的光学调校形状 |
| `elevation(style)` | `ElegantIconButtonElevation` | 返回指定类型的交互层级模型 |

### ElegantIconButtonColors

`ElegantIconButtonColors` 集中管理默认、悬停、按压、聚焦和禁用状态下的容器色、内容色、边框色与边框宽度。应先调用 `ElegantIconButtonDefaults.colors(style)`，再通过 `copy(...)` 进行明确的产品级覆盖。

### ElegantIconButtonElevation

`ElegantIconButtonElevation` 集中管理默认、悬停、按压、聚焦和禁用层级。主要和次要操作会在适当状态获得细微深度，三级操作则保持安静。

## 进阶用法

### 响应式工具栏

```kotlin
Row(
    modifier = Modifier.fillMaxWidth(),
    verticalAlignment = Alignment.CenterVertically,
) {
    Text(
        text = "发布说明",
        modifier = Modifier.weight(1f),
    )
    ElegantIconButton(
        onClick = { /* 编辑 */ },
        contentDescription = "编辑发布说明",
        style = ElegantIconButtonStyle.Secondary,
    ) {
        Icon(Icons.Default.Edit, contentDescription = null)
    }
    ElegantIconButton(
        onClick = { /* 更多 */ },
        contentDescription = "更多发布说明操作",
    ) {
        Icon(Icons.Default.MoreVert, contentDescription = null)
    }
}
```

### 自定义颜色

```kotlin
val baseColors = ElegantIconButtonDefaults.colors(ElegantIconButtonStyle.Primary)

ElegantIconButton(
    onClick = { /* 收藏 */ },
    contentDescription = "添加到收藏",
    colors = baseColors.copy(
        containerColor = Color(0xFF0F766E),
        pressedContainerColor = Color(0xFF115E59),
    ),
) {
    Icon(Icons.Default.Favorite, contentDescription = null)
}
```
