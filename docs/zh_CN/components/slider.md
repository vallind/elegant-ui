# Slider

`ElegantSlider` 是精致的受控取值滑块。它在一个 48dp 最小交互目标内渲染全宽圆角轨道、已填充的激活段与圆形滑块,并支持拖拽、点击跳转、离散步进、键盘方向键调节以及完整的禁用与无障碍语义。

<iframe id="demoIframe" style="width: 100%; height: 460px; border: 1px solid var(--vp-c-divider); border-radius: 12px; display: block; background: var(--vp-c-bg-alt);" src="../../compose/index.html?id=slider" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>

## 引入

```kotlin
import com.elegant.compose.ui.slider.ElegantSlider
import com.elegant.compose.ui.slider.ElegantSliderColors
import com.elegant.compose.ui.slider.ElegantSliderDefaults
```

## 基本用法

滑块是受控组件:父级持有 `value`,每次交互都会通过 `onValueChange` 上报解析后的值。默认情况下,取值在 `0f..1f` 范围内连续变化。

```kotlin
var volume by remember { mutableStateOf(0.5f) }

ElegantSlider(
    value = volume,
    onValueChange = { volume = it },
)
```

## 组件状态

`enabled = false` 的滑块会呈现安静的禁用颜色,阻止拖拽、点击与键盘交互,并向辅助技术播报禁用状态。指针悬停时轨道与滑块高亮,按下或拖拽时滑块放大至 1.1 倍同时保持 48dp 点击目标,键盘焦点会为滑块着色并启用方向键调节。

状态优先级:disabled、pressed 或 dragged、keyboard focused、pointer hovered、resting。

```kotlin
ElegantSlider(
    value = 0.5f,
    onValueChange = {},
    enabled = false,
)
```

## 属性

### ElegantSlider 属性

| 属性名 | 类型 | 说明 | 默认值 | 是否必须 |
| --- | --- | --- | --- | --- |
| `value` | `Float` | 当前滑块值;限制在 `valueRange` 内,NaN 在区间起点渲染 | - | 是 |
| `onValueChange` | `(Float) -> Unit` | 用户交互后携带解析值调用的回调 | - | 是 |
| `modifier` | `Modifier` | 仅应用一次到交互根节点的修饰符 | `Modifier` | 否 |
| `enabled` | `Boolean` | 是否接受用户交互 | `true` | 否 |
| `valueRange` | `ClosedFloatingPointRange<Float>` | 取值被约束的范围 | `0f..1f` | 否 |
| `steps` | `Int` | 区间端点之间的离散吸附位置数量;为零时保持连续 | `0` | 否 |
| `colors` | `ElegantSliderColors` | 主题感知的状态颜色 | `ElegantSliderDefaults.colors()` | 否 |
| `interactionSource` | `MutableInteractionSource?` | 可选的提升交互源,用于观察或控制状态 | `null` | 否 |

### ElegantSliderDefaults

| 成员 | 类型 | 说明 |
| --- | --- | --- |
| `TrackHeight` | `Dp` | 全宽轨道的 4dp 高度 |
| `ThumbSize` | `Dp` | 圆形滑块的 20dp 直径 |
| `MinimumTouchHeight` | `Dp` | 48dp 最小交互根高度 |
| `AnimationDurationMillis` | `Int` | 标准 160ms 状态过渡时长 |
| `colors()` | `ElegantSliderColors` | Light/Dark 主题感知颜色 |

### ElegantSliderColors

`ElegantSliderColors` 包含 resting、hovered、pressed、focused 与 disabled 各状态的轨道色、激活轨道色与滑块色。应先调用 `ElegantSliderDefaults.colors()`,再仅针对产品明确需要的层级使用 `copy(...)`。

## 进阶用法

### 步进滑块

设置 `steps` 可将取值吸附到离散位置:`steps = 4` 会在端点之间解析出五个等距位置。解析后的值已经取整,因此父级应将其映射回整数状态。

```kotlin
var level by remember { mutableStateOf(2) }

ElegantSlider(
    value = level.toFloat(),
    onValueChange = { level = it.roundToInt() },
    valueRange = 0f..4f,
    steps = 4,
)
```
