# SwitchPreference

`ElegantSwitchPreference` 是设置行控件:起始侧为标题块,末端锚定 `ElegantSwitch` 开关。它适用于设置界面:此时单个持久开关状态需要标题与可选辅助文本说明。

<iframe id="demoIframe" style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; border: none;" src="../../compose/index.html?id=switch-preference" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>

## 引入

```kotlin
import com.elegant.compose.ui.preference.ElegantPreferenceColors
import com.elegant.compose.ui.preference.ElegantPreferenceDefaults
import com.elegant.compose.ui.preference.ElegantSwitchPreference
```

## 基本用法

偏好项完全受控:传入当前的 `checked` 值,并通过 `onCheckedChange` 回调写回请求的状态。48dp 行在起始侧以 `labelMedium` 排版显示标题,并将开关锚定在末端。只有开关可以切换;点击行本身绝不会调用回调。

```kotlin
var notifications by remember { mutableStateOf(true) }

ElegantSwitchPreference(
    title = "通知",
    checked = notifications,
    onCheckedChange = { notifications = it },
    supportingText = "接收推送通知",
)
```

## 组件状态

行保持 48dp 最小高度并填满容器宽度,水平内边距为 16dp,标题与辅助文本之间为 2dp 间距。非空白的 `supportingText` 以 `bodyMedium` 排版渲染在标题下方;空白或 null 值会折叠为单行标题。当 `enabled` 为 false 时,标题回退为禁用标题色,开关通过自身的 `Role.Switch` 语义播报禁用状态,且不会调用 `onCheckedChange`。当 `showDivider` 为 true 时,一行从起始边缘内缩 16dp 的 1dp 分割线收束该行。

```kotlin
var wifi by remember { mutableStateOf(true) }

ElegantSwitchPreference(
    title = "无线网络",
    checked = wifi,
    onCheckedChange = { wifi = it },
    supportingText = "自动加入已知网络",
)

ElegantSwitchPreference(
    title = "飞行模式",
    checked = false,
    onCheckedChange = {},
    enabled = false,
)
```

## 属性

### ElegantSwitchPreference 属性

| 属性名 | 类型 | 说明 | 默认值 | 是否必须 |
| --- | --- | --- | --- | --- |
| `title` | `String` | 以 `labelMedium` 排版渲染的主要标题文本 | - | 是 |
| `checked` | `Boolean` | 末端开关是否开启 | - | 是 |
| `onCheckedChange` | `(Boolean) -> Unit` | 以请求的状态调用的回调 | - | 是 |
| `modifier` | `Modifier` | 仅应用一次到行根节点的修饰符 | `Modifier` | 否 |
| `supportingText` | `String?` | 仅在非空白时渲染的可选次要文本 | `null` | 否 |
| `enabled` | `Boolean` | 是否接受用户交互 | `true` | 否 |
| `colors` | `ElegantPreferenceColors` | 主题感知的状态颜色 | `ElegantPreferenceDefaults.colors()` | 否 |
| `showDivider` | `Boolean` | 是否用 1dp 分割线收束行底部 | `true` | 否 |

### ElegantPreferenceDefaults

| 成员 | 类型 | 说明 |
| --- | --- | --- |
| `MinimumTouchHeight` | `Dp` | 48dp 最小行高 |
| `colors()` | `ElegantPreferenceColors` | 主题感知的 Light/Dark 颜色 |

### ElegantPreferenceColors

`ElegantPreferenceColors` 包含行容器、标题、辅助文本、禁用标题与分割线颜色。应先调用 `ElegantPreferenceDefaults.colors()`,再仅针对产品明确需要的层级使用 `copy(...)`。

## 进阶用法

### 分割线控制

`showDivider` 自动绘制行底分割线;在设置组的最后一行将其设为 `false`,让组底保持干净。

```kotlin
ElegantSwitchPreference(
    title = "深色模式",
    checked = darkMode,
    onCheckedChange = { darkMode = it },
    showDivider = false,
)
```

### 自定义颜色

```kotlin
val baseColors = ElegantPreferenceDefaults.colors()

ElegantSwitchPreference(
    title = "自定义",
    checked = checked,
    onCheckedChange = onCheckedChange,
    colors = baseColors.copy(
        titleColor = Color(0xFF147D64),
        dividerColor = Color(0xFFC5C8CF),
    ),
)
```
