# CloseButton

`ElegantCloseButton` 是面向对话框、卡片与浮层的紧凑关闭操作组件。它在 48dp 交互目标内绘制一个安静的透明胶囊与固定的 X 字形，并提供胶囊悬停、克制的按压反馈、主题启用时的键盘焦点环，以及可本地化的无障碍名称。

<iframe id="demoIframe" style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; border: none;" src="../../compose/index.html?id=close-button" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>

## 引入

```kotlin
import com.elegant.compose.ui.closebutton.ElegantCloseButton
import com.elegant.compose.ui.closebutton.ElegantCloseButtonColors
import com.elegant.compose.ui.closebutton.ElegantCloseButtonDefaults
```

## 基本用法

将关闭按钮放在需要关闭操作的位置。`contentDescription` 默认为 `"Close"`，实际产品应提供本地化文本。

```kotlin
ElegantCloseButton(
    onClick = { /* 关闭浮层 */ },
)
```

## 组件状态

悬停、按压与键盘焦点由同一个交互源解析。悬停时胶囊填充容器，按压时加深，主题启用焦点环时,聚焦时显示焦点环；禁用状态会淡化字形并拒绝激活。

```kotlin
ElegantCloseButton(
    onClick = { /* 关闭对话框 */ },
)

ElegantCloseButton(
    onClick = {},
    enabled = false,
)
```

## 属性

### ElegantCloseButton 属性

| 属性名 | 类型 | 说明 | 默认值 | 是否必须 |
| --- | --- | --- | --- | --- |
| `onClick` | `() -> Unit` | 关闭操作接受激活时调用的回调 | - | 是 |
| `modifier` | `Modifier` | 应用于最小 48dp 交互根节点的修饰符 | `Modifier` | 否 |
| `enabled` | `Boolean` | 关闭操作是否接受交互 | `true` | 否 |
| `contentDescription` | `String` | 描述关闭操作的本地化无障碍名称 | `"Close"` | 否 |
| `colors` | `ElegantCloseButtonColors` | 胶囊与字形的主题感知交互颜色 | `ElegantCloseButtonDefaults.colors()` | 否 |
| `interactionSource` | `MutableInteractionSource?` | 用于观察悬停、按压和焦点的可选提升交互源 | `null` | 否 |

### ElegantCloseButtonDefaults

| 成员 | 类型 | 说明 |
| --- | --- | --- |
| `MinimumTouchHeight` | `Dp` | 所有交互根节点的最小宽度与高度 |
| `VisualSize` | `Dp` | 承载 X 字形的可视胶囊直径 |
| `AnimationDurationMillis` | `Int` | 标准悬停、焦点与状态过渡时长 |
| `colors()` | `ElegantCloseButtonColors` | 返回主题感知颜色 |

### ElegantCloseButtonColors

`ElegantCloseButtonColors` 集中管理静止、悬停、按压、禁用与聚焦颜色。静止容器为透明，悬停填充胶囊，按压加深胶囊，聚焦边框色用作焦点环。应先调用 `ElegantCloseButtonDefaults.colors()`，再通过 `copy(...)` 进行明确的产品级覆盖。

## 进阶用法

### 带关闭操作的卡片

将关闭按钮叠加在卡片顶部末端；48dp 目标保持宽裕的点击区域，28dp 胶囊保持视觉紧凑。

```kotlin
ElegantCard {
    Box {
        Text(
            text = "发布说明",
            modifier = Modifier
                .fillMaxWidth()
                .padding(ElegantSpacing.xl),
        )
        ElegantCloseButton(
            onClick = { /* 关闭卡片 */ },
            modifier = Modifier.align(Alignment.TopEnd),
        )
    }
}
```
