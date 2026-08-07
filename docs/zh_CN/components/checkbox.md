# Checkbox

`ElegantCheckbox` 是精致的多选控件,提供动画对勾、主题感知的状态颜色以及可选的内联标签。它适用于多选列表与偏好设置界面:此时需要紧凑且始终可见的选择状态。

<iframe id="demoIframe" style="width: 100%; height: 460px; border: 1px solid var(--vp-c-divider); border-radius: 12px; display: block; background: var(--vp-c-bg-alt);" src="../../compose/index.html?id=checkbox" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>

## 引入

```kotlin
import com.elegant.compose.ui.checkbox.ElegantCheckbox
import com.elegant.compose.ui.checkbox.ElegantCheckboxColors
import com.elegant.compose.ui.checkbox.ElegantCheckboxDefaults
```

## 基本用法

Checkbox 完全受控:传入当前的 `checked` 值,并通过 `onCheckedChange` 回调写回请求的状态。传入 `label` 可在同一条 48dp 交互行内渲染内联文本标签。

```kotlin
var subscribed by remember { mutableStateOf(true) }

ElegantCheckbox(
    checked = subscribed,
    onCheckedChange = { subscribed = it },
    label = "订阅发布说明",
)
```

## 组件状态

20dp 圆角方框承载 checked 与 unchecked 容器,2dp 边框传达 hover 与键盘焦点。`checked` 是语义状态,可与交互视觉组合:按下的已勾选 Checkbox 显示按下态已勾选颜色,启用焦点环时,焦点环优先于 hover 边框。禁用 Checkbox 不会调用 `onCheckedChange`,并通过可切换行的 `Role.Checkbox` 语义播报禁用状态。

状态优先级:disabled、pressed、hovered、resting;随后在获胜的交互状态上叠加 checked 或 unchecked 容器。

```kotlin
var options by remember { mutableStateOf(setOf("稳定版")) }

ElegantCheckbox(
    checked = "稳定版" in options,
    onCheckedChange = { checked ->
        options = if (checked) options + "稳定版" else options - "稳定版"
    },
    label = "稳定版渠道",
)

ElegantCheckbox(
    checked = false,
    onCheckedChange = {},
    enabled = false,
    label = "已禁用",
)
```

## 属性

### ElegantCheckbox 属性

| 属性名 | 类型 | 说明 | 默认值 | 是否必须 |
| --- | --- | --- | --- | --- |
| `checked` | `Boolean` | 是否选中 | - | 是 |
| `onCheckedChange` | `(Boolean) -> Unit` | 以请求的选择状态调用的回调 | - | 是 |
| `modifier` | `Modifier` | 仅应用一次到 Checkbox 行根节点的修饰符 | `Modifier` | 否 |
| `enabled` | `Boolean` | 是否接受用户交互 | `true` | 否 |
| `label` | `String?` | 方框之后渲染的可选内联文本标签 | `null` | 否 |
| `colors` | `ElegantCheckboxColors` | 主题感知的状态颜色 | `ElegantCheckboxDefaults.colors()` | 否 |
| `interactionSource` | `MutableInteractionSource?` | 用于观察或控制状态的可选提升交互源 | `null` | 否 |

### ElegantCheckboxDefaults

| 成员 | 类型 | 说明 |
| --- | --- | --- |
| `BoxSize` | `Dp` | 20dp 视觉方框尺寸 |
| `MinimumTouchHeight` | `Dp` | 48dp 最小交互行高 |
| `AnimationDurationMillis` | `Int` | 标准 160ms 状态过渡时长 |
| `colors()` | `ElegantCheckboxColors` | 主题感知的 Light/Dark 颜色 |

### ElegantCheckboxColors

`ElegantCheckboxColors` 包含 checked、unchecked、hovered、pressed、disabled 与 focused 各状态的容器色、内容色与边框色。应先调用 `ElegantCheckboxDefaults.colors()`,再仅针对产品明确需要的层级使用 `copy(...)`。

## 进阶用法

### 受控偏好组

将每个 Checkbox 提升到界面状态,使依赖偏好可以受其父项门控。

```kotlin
var emailUpdates by remember { mutableStateOf(true) }
var announcements by remember { mutableStateOf(false) }

ElegantCheckbox(
    checked = emailUpdates,
    onCheckedChange = { emailUpdates = it },
    label = "邮件更新",
)

ElegantCheckbox(
    checked = announcements,
    onCheckedChange = { announcements = it },
    enabled = emailUpdates,
    label = "公告",
)
```

### 自定义颜色

```kotlin
val baseColors = ElegantCheckboxDefaults.colors()

ElegantCheckbox(
    checked = checked,
    onCheckedChange = onCheckedChange,
    label = "自定义",
    colors = baseColors.copy(
        checkedContainerColor = Color(0xFF147D64),
        checkedContentColor = Color.White,
    ),
)
```
