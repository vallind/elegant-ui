# Switch

`ElegantSwitch` 是精致的开关控件,提供动画胶囊轨道与滑块、主题感知的状态颜色以及可选的内联标签。它适用于设置界面与实时偏好切换:此时需要单个持久且可见的开关状态。

<iframe id="demoIframe" style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; border: none;" src="../../compose/index.html?id=switch" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>

## 引入

```kotlin
import com.elegant.compose.ui.switch.ElegantSwitch
import com.elegant.compose.ui.switch.ElegantSwitchColors
import com.elegant.compose.ui.switch.ElegantSwitchDefaults
```

## 基本用法

Switch 完全受控:传入当前的 `checked` 值,并通过 `onCheckedChange` 回调写回请求的状态。传入 `label` 可在同一条 48dp 交互行内渲染内联文本标签。

```kotlin
var notifications by remember { mutableStateOf(true) }

ElegantSwitch(
    checked = notifications,
    onCheckedChange = { notifications = it },
    label = "推送通知",
)
```

## 组件状态

44x24dp 胶囊轨道承载 checked 与 unchecked 容器,2dp 焦点环仅在行获得键盘焦点时出现。16dp 滑块以标准 160ms 动效在轨道内滑动。`checked` 是语义状态,可与交互视觉组合:按下的开启 Switch 显示按下态开启颜色;禁用 Switch 不会调用 `onCheckedChange`,并通过可切换行的 `Role.Switch` 语义播报禁用状态。

状态优先级:disabled、pressed、hovered、resting;随后在获胜的交互状态上叠加 on 或 off 视觉。

```kotlin
var wifi by remember { mutableStateOf(true) }

ElegantSwitch(
    checked = wifi,
    onCheckedChange = { wifi = it },
    label = "无线网络",
)

ElegantSwitch(
    checked = false,
    onCheckedChange = {},
    enabled = false,
    label = "已禁用",
)
```

## 属性

### ElegantSwitch 属性

| 属性名 | 类型 | 说明 | 默认值 | 是否必须 |
| --- | --- | --- | --- | --- |
| `checked` | `Boolean` | 开关是否开启 | - | 是 |
| `onCheckedChange` | `(Boolean) -> Unit` | 以请求的状态调用的回调 | - | 是 |
| `modifier` | `Modifier` | 仅应用一次到 Switch 行根节点的修饰符 | `Modifier` | 否 |
| `enabled` | `Boolean` | 是否接受用户交互 | `true` | 否 |
| `label` | `String?` | 轨道之后渲染的可选内联文本标签 | `null` | 否 |
| `colors` | `ElegantSwitchColors` | 主题感知的状态颜色 | `ElegantSwitchDefaults.colors()` | 否 |
| `interactionSource` | `MutableInteractionSource?` | 用于观察或控制状态的可选提升交互源 | `null` | 否 |

### ElegantSwitchDefaults

| 成员 | 类型 | 说明 |
| --- | --- | --- |
| `TrackWidth` | `Dp` | 44dp 视觉轨道宽度 |
| `TrackHeight` | `Dp` | 24dp 视觉轨道高度 |
| `ThumbSize` | `Dp` | 16dp 视觉滑块直径 |
| `MinimumTouchHeight` | `Dp` | 48dp 最小交互行高 |
| `PressedThumbScale` | `Float` | 按下或拖动时的滑块缩放系数 |
| `colors()` | `ElegantSwitchColors` | 主题感知的 Light/Dark 颜色 |

### ElegantSwitchColors

`ElegantSwitchColors` 包含 checked、unchecked、hovered、pressed、disabled 与 focused 各状态的轨道色与滑块色。应先调用 `ElegantSwitchDefaults.colors()`,再仅针对产品明确需要的层级使用 `copy(...)`。

## 进阶用法

### 受控偏好组

将每个 Switch 提升到界面状态,使依赖偏好可以受其父项门控。

```kotlin
var autoUpdates by remember { mutableStateOf(true) }
var overnightInstall by remember { mutableStateOf(false) }

ElegantSwitch(
    checked = autoUpdates,
    onCheckedChange = { autoUpdates = it },
    label = "自动更新",
)

ElegantSwitch(
    checked = overnightInstall,
    onCheckedChange = { overnightInstall = it },
    enabled = autoUpdates,
    label = "夜间安装",
)
```

### 自定义颜色

```kotlin
val baseColors = ElegantSwitchDefaults.colors()

ElegantSwitch(
    checked = checked,
    onCheckedChange = onCheckedChange,
    label = "自定义",
    colors = baseColors.copy(
        trackCheckedColor = Color(0xFF147D64),
        thumbCheckedColor = Color.White,
    ),
)
```
