# Card

`ElegantCard` 是精致的表面组件,通过三种视觉变体与可选激活交互来组织内容。它适用于个人资料行、统计数据、设置条目,以及任何需要圆角、主题感知容器的内容块。

<iframe id="demoIframe" style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; border: none;" src="../../compose/index.html?id=card" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>

## 引入

```kotlin
import com.elegant.compose.ui.card.ElegantCard
import com.elegant.compose.ui.card.ElegantCardColors
import com.elegant.compose.ui.card.ElegantCardDefaults
import com.elegant.compose.ui.card.ElegantCardStyle
```

## 基本用法

不传 `onClick` 的 Card 是非交互组件:它只渲染容器、边框与阴影,保留内容自身的语义,并且不支持焦点。Card 不添加内部内边距,因此间距由调用方负责。内容通过 `LocalContentColor` 获得 Card 的内容颜色。

```kotlin
ElegantCard {
    Column(Modifier.padding(16.dp)) {
        Text("发布说明")
        Text("Card 在一个表面中组织相关内容。")
    }
}
```

## 风格

三种变体覆盖表面层级:`Filled` 位于默认表面之上,`Outlined` 抬升容器并用 1dp 边框标记,`Elevated` 抬升容器并带有常驻色调阴影。三者共享 16dp 圆角。

```kotlin
ElegantCard {
    Text("填充")
}

ElegantCard(style = ElegantCardStyle.Outlined) {
    Text("描边")
}

ElegantCard(style = ElegantCardStyle.Elevated) {
    Text("抬升")
}
```

## 组件状态

非交互 Card 没有 hover、press、focus 或 disabled 状态。传入 `onClick` 后 Card 变为按钮式表面:它会播报 `Role.Button`,保持 48dp 最小交互根,在主题启用焦点环时显示焦点环,提供带波纹的 hover 与 press 颜色反馈,并在按下或禁用时降为零级阴影。

交互 Card 的状态优先级:disabled、pressed、focused 边框、hovered、resting。hover 与 focus 状态下阴影保持常驻值。

```kotlin
var taps by remember { mutableIntStateOf(0) }

ElegantCard(
    onClick = { taps += 1 },
) {
    Column(Modifier.padding(16.dp)) {
        Text("点击激活")
        Text("已激活 $taps 次")
    }
}

ElegantCard(
    onClick = {},
    enabled = false,
) {
    Text("已禁用")
}
```

## 属性

### ElegantCard 属性

| 属性名 | 类型 | 说明 | 默认值 | 是否必须 |
| --- | --- | --- | --- | --- |
| `onClick` | `(() -> Unit)?` | 可选激活回调;null 保持非交互 | `null` | 否 |
| `onLongPress` | `(() -> Unit)?` | 可选长按回调;启用组合点击处理 | `null` | 否 |
| `modifier` | `Modifier` | 仅应用一次到 Card 根节点的修饰符 | `Modifier` | 否 |
| `enabled` | `Boolean` | 是否接受用户交互 | `true` | 否 |
| `style` | `ElegantCardStyle` | 视觉变体 | `ElegantCardStyle.Filled` | 否 |
| `shape` | `Shape` | 裁剪、边框与阴影形状 | `ElegantCardDefaults.shape(style)` | 否 |
| `colors` | `ElegantCardColors` | 主题感知的状态颜色 | `ElegantCardDefaults.colors(style)` | 否 |
| `elevation` | `Dp` | 常驻阴影高度;按下与禁用时降为 none | `ElegantCardDefaults.elevation(style)` | 否 |
| `holdDownState` | `Boolean` | 为 true 时强制按下视觉状态 | `false` | 否 |
| `pressFeedback` | `ElegantPressFeedbackType` | 按压物理反馈;`Sink` 压缩卡片,`Tilt` 围绕触点旋转卡片 | `ElegantPressFeedbackType.None` | 否 |
| `content` | `@Composable () -> Unit` | Card 内容;内边距由调用方负责 | - | 是 |

### ElegantCardStyle 可选值

| 值 | 行为 |
| --- | --- |
| `Filled` | 无边框、无阴影的默认表面容器 |
| `Outlined` | 带可见 1dp 边框的抬升容器 |
| `Elevated` | 带常驻色调阴影的抬升容器 |

### ElegantCardDefaults

| 成员 | 类型 | 说明 |
| --- | --- | --- |
| `MinimumTouchHeight` | `Dp` | 可点击 Card 使用的 48dp 最小交互根高度 |
| `AnimationDurationMillis` | `Int` | 标准 160ms 状态过渡时长 |
| `colors(style)` | `ElegantCardColors` | 所选风格的 Light/Dark 主题感知颜色 |
| `shape(style)` | `Shape` | 每种风格共享的 16dp 圆角形状 |
| `elevation(style)` | `Dp` | 每种风格的常驻阴影高度;仅 `Elevated` 投射阴影 |

### ElegantCardColors

`ElegantCardColors` 包含容器色、内容色与边框色,以及 hovered、pressed、disabled 与焦点环覆盖值。应先调用 `ElegantCardDefaults.colors(style)`,再仅针对产品明确需要的层级使用 `copy(...)`。

## 进阶用法

### 个人资料卡片组合

在描边卡片内组合 `ElegantAvatar`、文本与 `ElegantIconButton`,构建带次级操作的真实表面。

```kotlin
ElegantCard(style = ElegantCardStyle.Outlined) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        ElegantAvatar(name = "Maya Chen", initials = "MC")
        Column(Modifier.weight(1f).padding(start = 12.dp)) {
            Text("Maya Chen")
            Text("设计系统负责人")
        }
        ElegantIconButton(
            onClick = { /* 打开个人资料菜单 */ },
            contentDescription = "更多个人资料操作",
        ) {
            Icon(Icons.Default.MoreVert, contentDescription = null)
        }
    }
}
```

### 自定义颜色

```kotlin
val baseColors = ElegantCardDefaults.colors(ElegantCardStyle.Filled)

ElegantCard(
    colors = baseColors.copy(
        containerColor = Color(0xFF6C4EFF),
        contentColor = Color.White,
    ),
) {
    Text("自定义表面")
}
```
