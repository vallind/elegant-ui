# Link

`ElegantLink` 是精致的内联文本链接组件,带动画下划线。它适用于句子、卡片与紧凑界面中的导航和次级操作:此时 Button 过重。

<iframe id="demoIframe" style="width: 100%; height: 460px; border: 1px solid var(--vp-c-divider); border-radius: 12px; display: block; background: var(--vp-c-bg-alt);" src="../../compose/index.html?id=link" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>

## 引入

```kotlin
import com.elegant.compose.ui.link.ElegantLink
import com.elegant.compose.ui.link.ElegantLinkColors
import com.elegant.compose.ui.link.ElegantLinkDefaults
```

## 基本用法

Link 以标准标签样式绘制文字,并在字形下方绘制 1dp 下划线。交互根节点保持 48dp 最小触控目标,紧凑的文字在视觉上不膨胀,依然易于点击。

```kotlin
ElegantLink(
    text = "查看发布说明",
    onClick = { onOpenReleaseNotes() },
)
```

## 组件状态

状态优先级:disabled、pressed、hovered、resting。hovered 的链接提亮为 hover 交互色,pressed 的链接回到 resting 交互色,disabled 的链接绘制三级文字色且没有任何交互反馈。disabled 的链接永远不会调用 `onClick`。内容色与下划线色都以标准过渡时长在状态间动画;下划线在 resting 状态为 50% 透明度,其余状态则完全镜像内容色。

```kotlin
ElegantLink(
    text = "打开设置",
    onClick = { onOpenSettings() },
)

ElegantLink(
    text = "不可用操作",
    onClick = { onOpenSettings() },
    enabled = false,
)
```

## 属性

### ElegantLink 属性

| 属性名 | 类型 | 说明 | 默认值 | 是否必须 |
| --- | --- | --- | --- | --- |
| `text` | `String` | 渲染为链接文字的标签 | - | 是 |
| `onClick` | `() -> Unit` | 激活回调;disabled 状态下永不调用 | - | 是 |
| `modifier` | `Modifier` | 仅应用一次到链接根节点的修饰符 | `Modifier` | 否 |
| `enabled` | `Boolean` | 是否接受用户交互 | `true` | 否 |
| `colors` | `ElegantLinkColors` | 主题感知的状态颜色 | `ElegantLinkDefaults.colors()` | 否 |
| `interactionSource` | `MutableInteractionSource?` | 可选的外部交互源,用于观察或控制状态;为空时自动创建并记忆 | `null` | 否 |

### ElegantLinkDefaults

| 成员 | 类型 | 说明 |
| --- | --- | --- |
| `MinimumTouchHeight` | `Dp` | 链接根节点强制保持的 48dp 最小交互目标 |
| `AnimationDurationMillis` | `Int` | 标准 160ms 状态过渡时长 |
| `colors()` | `ElegantLinkColors` | Link 的 Light/Dark 主题感知颜色 |

### ElegantLinkColors

`ElegantLinkColors` 包含 resting、hovered、pressed、disabled 四种内容色以及 resting 下划线色。所有状态色默认回退到 `contentColor`,`underlineColor` 也默认等于 `contentColor`。应先调用 `ElegantLinkDefaults.colors()`,再仅针对产品明确需要的层级使用 `copy(...)`。

## 进阶用法

### 自定义颜色

在保留其余主题感知配色不变的情况下覆盖个别角色。

```kotlin
ElegantLink(
    text = "自定义强调",
    onClick = { onOpenCustom() },
    colors = ElegantLinkDefaults.colors().copy(
        contentColor = Color(0xFF6C4EFF),
        underlineColor = Color(0xFF6C4EFF).copy(alpha = 0.5f),
    ),
)
```

### 提升交互源

传入交互源以在链接外部观察 hover 或 press,例如驱动次级反馈。

```kotlin
val interactionSource = remember { MutableInteractionSource() }
val hovered by interactionSource.collectIsHoveredAsState()

ElegantLink(
    text = "可悬浮链接",
    onClick = { onOpenHoverable() },
    interactionSource = interactionSource,
)

if (hovered) {
    Text("链接被悬浮")
}
```
