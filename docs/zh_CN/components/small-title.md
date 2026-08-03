# SmallTitle

`ElegantSmallTitle` 为偏好分组与设置界面提供紧凑且不可交互的小节标题。它使用 `labelSmall` 排版与次要文本色将标签渲染为单行，对过长的文本以省略号截断，并适配 Light 与 Dark 主题，同时不引入交互角色或自身的语义节点。

<iframe id="demoIframe" style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; border: none;" src="../../compose/index.html?id=small-title" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>

## 引入

```kotlin
import com.elegant.compose.ui.smalltitle.ElegantSmallTitle
import com.elegant.compose.ui.smalltitle.ElegantSmallTitleColors
import com.elegant.compose.ui.smalltitle.ElegantSmallTitleDefaults
```

## 基本用法

直接将分组标签传给 `text`，并将标题放在相关偏好行的上方。标签使用 `labelSmall` 排版，因此读起来是安静的区块标记，不会与下方的行争夺视觉重点。

```kotlin
Column(
    verticalArrangement = Arrangement.spacedBy(ElegantSpacing.sm),
) {
    ElegantSmallTitle(text = "GENERAL")
    ElegantSwitchPreference(
        title = "Dark mode",
        checked = darkMode,
        onCheckedChange = { darkMode = it },
    )
}
```

## 组件状态

`ElegantSmallTitle` 不可交互：它没有角色、没有按压或焦点状态，也没有禁用条件。它同样不添加自身的语义节点，因此标签文本对辅助技术保持可读，而不会播报标题结构。无法在单行内容纳的文本将以省略号截断。

```kotlin
ElegantSmallTitle(
    text = "A very long section heading that does not fit on one line",
)
```

## 属性

### ElegantSmallTitle 属性

| 属性名 | 类型 | 说明 | 默认值 | 是否必须 |
| --- | --- | --- | --- | --- |
| `text` | `String` | 以 `labelSmall` 排版渲染的标题文本 | - | 是 |
| `modifier` | `Modifier` | 仅应用一次到标题的修饰符 | `Modifier` | 否 |
| `colors` | `ElegantSmallTitleColors` | 主题感知的标签颜色 | `ElegantSmallTitleDefaults.colors()` | 否 |

### ElegantSmallTitleDefaults

| 成员 | 类型 | 说明 |
| --- | --- | --- |
| `colors()` | `ElegantSmallTitleColors` | 返回 Light/Dark 主题感知的小标题颜色 |

### ElegantSmallTitleColors

`ElegantSmallTitleColors` 包含 `contentColor`。应先调用 `ElegantSmallTitleDefaults.colors()`，再通过 `copy(...)` 进行产品明确需要的定制。

## 进阶用法

当小节标题需要更强的视觉重点时，可在保留单行标签行为的同时定制颜色模型。

```kotlin
val titleColors = ElegantSmallTitleDefaults.colors().copy(
    contentColor = ElegantTheme.colors.textPrimary,
)

ElegantSmallTitle(
    text = "PROMINENT",
    colors = titleColors,
)
```
