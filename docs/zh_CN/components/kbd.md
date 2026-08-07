# Kbd

`ElegantKbd` 为快捷键文档提供紧凑且不可交互的按键徽标。它将下沉表面与微妙的圆角轮廓及小号标签相结合，让按键文本对辅助技术保持可读，并适配 Light 与 Dark 主题，同时不引入交互角色。

<iframe id="demoIframe" style="width: 100%; height: 460px; border: 1px solid var(--vp-c-divider); border-radius: 12px; display: block; background: var(--vp-c-bg-alt);" src="../../compose/index.html?id=kbd" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>

## 引入

```kotlin
import com.elegant.compose.ui.kbd.ElegantKbd
import com.elegant.compose.ui.kbd.ElegantKbdColors
import com.elegant.compose.ui.kbd.ElegantKbdDefaults
```

## 基本用法

直接将按键或组合键标签传给 `text`。徽标以 24dp 最小高度与 6dp 水平内边距确定尺寸，因此单个按键与多键组合都能保持视觉平衡。

```kotlin
Row(
    horizontalArrangement = Arrangement.spacedBy(ElegantSpacing.sm),
) {
    ElegantKbd(text = "⌘")
    ElegantKbd(text = "Ctrl + K")
}
```

## 组件状态

`ElegantKbd` 不可交互：它没有角色、没有按压或焦点状态，也没有禁用条件。它同样不添加自身的语义节点，因此按键标签文本保持可读，同时快捷键内容对辅助技术保持安静。

```kotlin
Row(
    horizontalArrangement = Arrangement.spacedBy(ElegantSpacing.sm),
    verticalAlignment = Alignment.CenterVertically,
) {
    ElegantKbd(text = "Shift")
    Text(text = "+")
    ElegantKbd(text = "P")
}
```

## 属性

### ElegantKbd 属性

| 属性名 | 类型 | 说明 | 默认值 | 是否必须 |
| --- | --- | --- | --- | --- |
| `text` | `String` | 显示在徽标内的按键或组合键标签 | - | 是 |
| `modifier` | `Modifier` | 仅应用一次到徽标容器的修饰符 | `Modifier` | 否 |
| `colors` | `ElegantKbdColors` | 主题感知的容器色、内容色与轮廓色 | `ElegantKbdDefaults.colors()` | 否 |

### ElegantKbdDefaults

| 成员 | 类型 | 说明 |
| --- | --- | --- |
| `MinHeight` | `Dp` | 默认 24dp 最小高度 |
| `HorizontalPadding` | `Dp` | 默认 6dp 水平内边距 |
| `BorderWidth` | `Dp` | 默认 1dp 光学轮廓 |
| `colors()` | `ElegantKbdColors` | 返回 Light/Dark 主题感知的按键徽标颜色 |

### ElegantKbdColors

`ElegantKbdColors` 包含 `containerColor`、`contentColor` 与 `borderColor`。应先调用 `ElegantKbdDefaults.colors()`，再通过 `copy(...)` 进行产品明确需要的定制。

## 进阶用法

当按键属于产品专用词汇时，可在保留徽标几何与语义契约的同时定制颜色模型。

```kotlin
val kbdColors = ElegantKbdDefaults.colors().copy(
    containerColor = ElegantTheme.colors.backgroundSubtle,
    borderColor = ElegantTheme.colors.borderStrong,
)

ElegantKbd(
    text = "Enter",
    colors = kbdColors,
)
```
