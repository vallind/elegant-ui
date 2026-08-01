# Alert

`ElegantAlert` 是用于行内反馈的非交互状态横幅。它提供四种语义样式、可选图标与由调用方持有的操作插槽，同时横幅本身不附加焦点、按压或角色语义，从而让周围交互保持可预测。

<iframe id="demoIframe" style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; border: none;" src="../../compose/index.html?id=alert" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>

## 引入

```kotlin
import com.elegant.compose.ui.alert.ElegantAlert
import com.elegant.compose.ui.alert.ElegantAlertColors
import com.elegant.compose.ui.alert.ElegantAlertDefaults
import com.elegant.compose.ui.alert.ElegantAlertStyle
```

## 基本用法

使用必填标题、可选说明与可选前置图标组合一条 Alert。标题与说明是普通文本，因此无需额外接线即可将语义传达给辅助技术。

```kotlin
ElegantAlert(
    title = "存储空间即将用尽",
    description = "请至少释放 500 MB 空间以继续同步。",
    style = ElegantAlertStyle.Warning,
    icon = {
        Icon(
            painter = painterResource(Res.drawable.share_rounded),
            contentDescription = null,
        )
    },
)
```

## 警示样式

按消息含义选择样式：`Neutral` 用于一般信息，`Positive` 用于成功结果，`Warning` 用于需要关注的情况，`Critical` 用于紧急或破坏性情况。语义样式会从当前主题派生着色容器、边框与图标颜色。

```kotlin
ElegantAlert(
    title = "计划内维护",
    description = "服务将于 06:00 UTC 恢复。",
)

ElegantAlert(
    title = "备份已完成",
    style = ElegantAlertStyle.Positive,
    icon = {
        Icon(
            painter = painterResource(Res.drawable.check_rounded),
            contentDescription = null,
        )
    },
)

ElegantAlert(
    title = "磁盘空间不足",
    description = "卷上仅剩 10% 空间。",
    style = ElegantAlertStyle.Warning,
)

ElegantAlert(
    title = "部署失败",
    description = "请查看日志并重试发布。",
    style = ElegantAlertStyle.Critical,
)
```

## 组件状态

空白或 null 的 `description` 会被完全省略，只保留仅含标题的横幅。横幅本身不可交互：不附加角色、焦点或按压处理，`action` 插槽由调用方持有，因此放置其中的按钮会保留自身的焦点与激活行为。

```kotlin
ElegantAlert(
    title = "会话已过期",
    description = "  ",
    style = ElegantAlertStyle.Critical,
    action = {
        ElegantButton(
            onClick = onSignInAgain,
            style = ElegantButtonStyle.Secondary,
            size = ElegantButtonSize.Small,
        ) {
            Text("重新登录")
        }
    },
)
```

## 属性

### ElegantAlert 属性

| 属性名 | 类型 | 说明 | 默认值 | 是否必须 |
| --- | --- | --- | --- | --- |
| `style` | `ElegantAlertStyle` | 语义视觉样式 | `ElegantAlertStyle.Neutral` | 否 |
| `modifier` | `Modifier` | 仅应用一次到横幅根节点的修饰符 | `Modifier` | 否 |
| `title` | `String` | 横幅中显示的主要消息 | - | 是 |
| `description` | `String?` | 标题下方显示的支持性说明；空白值会被省略 | `null` | 否 |
| `icon` | `@Composable () -> Unit?` | 标题之前的可选内容，使用图标色着色 | `null` | 否 |
| `action` | `@Composable () -> Unit?` | 文本列之后的可选内容，如按钮或文本链接 | `null` | 否 |
| `colors` | `ElegantAlertColors` | 主题感知的容器色、文本色、边框色与图标色 | `ElegantAlertDefaults.colors(style)` | 否 |

### ElegantAlertStyle 可选值

| 值 | 含义 |
| --- | --- |
| `Neutral` | 无需强调或严重性的一般信息 |
| `Positive` | 成功、可用或健康的结果 |
| `Warning` | 需要关注的情况 |
| `Critical` | 紧急、失败、破坏性或严重情况 |

### ElegantAlertDefaults

| 成员 | 类型 | 说明 |
| --- | --- | --- |
| `colors(style)` | `ElegantAlertColors` | 返回语义样式对应的 Light/Dark 主题感知颜色 |

### ElegantAlertColors

`ElegantAlertColors` 包含 `containerColor`、`contentColor`、`supportingColor`、`borderColor` 与 `iconColor`。应先调用 `ElegantAlertDefaults.colors(style)`，再通过 `copy(...)` 进行产品明确需要的定制。

## 进阶用法

当 Alert 需要匹配产品专用词汇时，可使用自定义颜色模型，同时保留横幅的几何与内容语义。

```kotlin
val baseColors = ElegantAlertDefaults.colors(ElegantAlertStyle.Positive)

ElegantAlert(
    title = "部署已完成",
    description = "所有服务均已恢复正常。",
    style = ElegantAlertStyle.Positive,
    colors = baseColors.copy(
        containerColor = Color(0xFF0F766E),
        borderColor = Color(0xFF5EEAD4),
    ),
)
```
