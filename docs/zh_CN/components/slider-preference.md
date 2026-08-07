# Slider Preference

`ElegantSliderPreference` 是面向有界取值的设置风格偏好项。它渲染标题行与格式化后的当前值、可选的辅助说明文本,以及标题行下方的全宽 `ElegantSlider`,底部还有可选的缩进发丝分隔线。滑块拥有全部交互;标题行本身不可点击。

<iframe id="demoIframe" style="width: 100%; height: 460px; border: 1px solid var(--vp-c-divider); border-radius: 12px; display: block; background: var(--vp-c-bg-alt);" src="../../compose/index.html?id=slider-preference" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>

## 引入

```kotlin
import com.elegant.compose.ui.preference.ElegantSliderPreference
import com.elegant.compose.ui.preference.ElegantSliderPreferenceColors
import com.elegant.compose.ui.preference.ElegantSliderPreferenceDefaults
```

## 基本用法

偏好项是受控组件:父级持有 `value`,每次交互都会通过 `onValueChange` 上报。`valueFormatter` 在标题行末尾渲染当前值。默认情况下,取值在 `0f..1f` 范围内连续变化。

```kotlin
var brightness by remember { mutableStateOf(0.6f) }

ElegantSliderPreference(
    title = "Brightness",
    value = brightness,
    onValueChange = { brightness = it },
    valueFormatter = { "${(it * 100).roundToInt()}%" },
)
```

## 组件状态

`enabled = false` 的偏好项会以安静的禁用色调渲染标题与值,阻止滑块上的拖拽、点击与键盘交互,并向辅助技术播报禁用状态。辅助说明文本与缩进分隔线保持其静止颜色。

状态优先级由滑块持有:disabled、pressed 或 dragged、keyboard focused、pointer hovered、resting。

```kotlin
ElegantSliderPreference(
    title = "Brightness",
    value = 0.6f,
    onValueChange = {},
    enabled = false,
)
```

## 属性

### ElegantSliderPreference 属性

| 属性名 | 类型 | 说明 | 默认值 | 是否必须 |
| --- | --- | --- | --- | --- |
| `title` | `String` | 标题行起始处显示的标签 | - | 是 |
| `value` | `Float` | 当前滑块值;限制在 `valueRange` 内,NaN 在区间起点渲染 | - | 是 |
| `onValueChange` | `(Float) -> Unit` | 用户交互后携带解析值调用的回调 | - | 是 |
| `modifier` | `Modifier` | 仅应用一次到偏好项根节点的修饰符 | `Modifier` | 否 |
| `supportingText` | `String?` | 标题行下方可选指导文本;空白文本会被隐藏 | `null` | 否 |
| `valueRange` | `ClosedFloatingPointRange<Float>` | 取值被约束的范围 | `0f..1f` | 否 |
| `steps` | `Int` | 区间端点之间的离散吸附位置数量;为零时保持连续 | `0` | 否 |
| `valueFormatter` | `(Float) -> String` | 在标题行末尾格式化当前值用于显示 | `{ it.toString() }` | 否 |
| `enabled` | `Boolean` | 滑块是否接受用户交互 | `true` | 否 |
| `colors` | `ElegantSliderPreferenceColors` | 主题感知的文本与分隔线颜色 | `ElegantSliderPreferenceDefaults.colors()` | 否 |
| `showDivider` | `Boolean` | 是否在偏好项下方绘制发丝分隔线 | `true` | 否 |

### ElegantSliderPreferenceDefaults

| 成员 | 类型 | 说明 |
| --- | --- | --- |
| `MinimumTouchHeight` | `Dp` | 内容行 56dp 最小高度 |
| `colors()` | `ElegantSliderPreferenceColors` | Light/Dark 主题感知颜色 |

### ElegantSliderPreferenceColors

`ElegantSliderPreferenceColors` 包含标题、辅助文本、值、禁用标题与分隔线的颜色。应先调用 `ElegantSliderPreferenceDefaults.colors()`,再仅针对产品明确需要的层级使用 `copy(...)`。

## 进阶用法

### 步进偏好项

设置 `steps` 可将取值吸附到离散位置,并通过 `supportingText` 引导用户。解析后的值已经取整,因此父级应将其映射回整数状态。

```kotlin
var level by remember { mutableStateOf(2) }

ElegantSliderPreference(
    title = "Text scale",
    supportingText = "Applies to all in-app text",
    value = level.toFloat(),
    onValueChange = { level = it.roundToInt() },
    valueRange = 0f..4f,
    steps = 4,
    valueFormatter = { "${it.roundToInt()}x" },
)
```
