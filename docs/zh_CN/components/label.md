# Label

`ElegantLabel` 以不可交互的表单字段标签形式展示可选的必填标记。它将标签文本的次要文本色与紧凑的 `"*"` 后缀（使用关键色）相结合，让文本对辅助技术保持可读，并适配 Light 与 Dark 主题，同时不引入交互角色。

<iframe id="demoIframe" style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; border: none;" src="../../compose/index.html?id=label" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>

## 引入

```kotlin
import com.elegant.compose.ui.label.ElegantLabel
import com.elegant.compose.ui.label.ElegantLabelColors
import com.elegant.compose.ui.label.ElegantLabelDefaults
```

## 基本用法

直接将字段标签传给 `text`。标签以省略号效果单行渲染，使用主题感知的次要文本色，并直接放置在它所描述的字段上方。

```kotlin
Column(verticalArrangement = Arrangement.spacedBy(ElegantSpacing.xs)) {
    ElegantLabel(text = "Full name")
    ElegantInput(value = "", onValueChange = {})
}
```

## 组件状态

`ElegantLabel` 不可交互：它没有角色、没有按压或焦点状态，也没有自身的触摸目标。当 `enabled` 为 false 时，文本以禁用内容色渲染；当 `required` 为 true 时，一个独立的 `"*"` 后缀以关键色紧随文本之后并留有 2dp 间距，因此标记永远不会因截断而消失。

```kotlin
Column(verticalArrangement = Arrangement.spacedBy(ElegantSpacing.xs)) {
    ElegantLabel(text = "Email address", required = true)
    ElegantLabel(text = "Nickname", enabled = false)
}
```

## 属性

### ElegantLabel 属性

| 属性名 | 类型 | 说明 | 默认值 | 是否必须 |
| --- | --- | --- | --- | --- |
| `text` | `String` | 显示在字段旁的标签文本 | - | 是 |
| `modifier` | `Modifier` | 仅应用一次到标签根部的修饰符 | `Modifier` | 否 |
| `required` | `Boolean` | 是否在文本后渲染必填标记 | `false` | 否 |
| `enabled` | `Boolean` | 是否以启用内容色渲染标签 | `true` | 否 |
| `colors` | `ElegantLabelColors` | 主题感知的内容色与必填标记色 | `ElegantLabelDefaults.colors()` | 否 |

### ElegantLabelDefaults

| 成员 | 类型 | 说明 |
| --- | --- | --- |
| `RequiredSuffix` | `String` | 默认的 `"*"` 必填后缀 |
| `colors()` | `ElegantLabelColors` | 返回 Light/Dark 主题感知的标签颜色 |

### ElegantLabelColors

`ElegantLabelColors` 包含 `contentColor`、`requiredColor` 与 `disabledContentColor`（默认与 `contentColor` 相同）。应先调用 `ElegantLabelDefaults.colors()`，再通过 `copy(...)` 进行产品明确需要的定制。

## 进阶用法

在保留单行省略与必填标记几何的同时定制标签颜色模型，以适配产品专用表单。

```kotlin
val labelColors = ElegantLabelDefaults.colors().copy(
    requiredColor = ElegantTheme.colors.interactivePrimary,
)

ElegantLabel(
    text = "Promo code",
    required = true,
    colors = labelColors,
)
```
