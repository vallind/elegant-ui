# ColorPicker Panel

`ElegantColorPickerPanel` 将饱和度 × 明度 `ElegantColorArea` 与彩虹色相 `ElegantHueSlider` 组合成一个自由 HSV 取色面板。它适用于主题强调色、绘图工具,以及任何需要用户在完整连续色彩空间而不是精选调色板中取色的场景。

<iframe id="demoIframe" style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; border: none;" src="../../compose/index.html?id=color-picker-panel" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>

## 引入

```kotlin
import com.elegant.compose.ui.colorpicker.ElegantColorPickerPanel
import com.elegant.compose.ui.colorpicker.ElegantColorArea
import com.elegant.compose.ui.colorpicker.ElegantHueSlider
import com.elegant.compose.ui.colorpicker.ElegantColorPicker
```

## 基本用法

`ElegantColorPickerPanel` 是受控组件:`color` 由调用方持有,每次交互都会以解析后的颜色触发 `onColorChange`,由调用方写回。在面板上拖动或点击会保持色相并改变饱和度与明度;在滑块上拖动或点击会保持饱和度与明度并改变色相。

```kotlin
var selected by remember { mutableStateOf(Color(0xFF6C4EFF)) }

ElegantColorPickerPanel(
    color = selected,
    onColorChange = { selected = it },
)
```

## 组件状态

面板将状态转发给两个控件:`enabled` 为 false 时两者都不会触发回调。面板以 40% 不透明度渲染,滑块以 40% 不透明度渲染;键盘聚焦时两者都会将轮廓与滑块圆环切换为焦点色。面板播报 `Role.Slider`,并以 `#RRGGBB` 十六进制值作为内容描述;滑块播报 `Role.Slider` 及 `0..360` 进度范围。

```kotlin
ElegantColorPickerPanel(
    color = selected,
    onColorChange = { selected = it },
)

ElegantColorPickerPanel(
    color = selected,
    onColorChange = {},
    enabled = false,
)
```

## 属性

### ElegantColorPickerPanel 属性

| 属性名 | 类型 | 说明 | 默认值 | 是否必须 |
| --- | --- | --- | --- | --- |
| `color` | `Color` | 当前选中的颜色,由调用方持有 | - | 是 |
| `onColorChange` | `(Color) -> Unit` | 以任一控件解析出的颜色触发的回调 | - | 是 |
| `modifier` | `Modifier` | 仅应用一次到面板列的修饰符 | `Modifier` | 否 |
| `enabled` | `Boolean` | 任一控件是否接受用户交互 | `true` | 否 |
| `areaColors` | `ElegantColorAreaColors` | 转发给取色面板的主题感知颜色 | `ElegantColorAreaDefaults.colors()` | 否 |
| `hueColors` | `ElegantHueSliderColors` | 转发给色相滑块的主题感知颜色 | `ElegantHueSliderDefaults.colors()` | 否 |

### ElegantColorArea 属性

| 属性名 | 类型 | 说明 | 默认值 | 是否必须 |
| --- | --- | --- | --- | --- |
| `color` | `Color` | 当前选中的颜色,由调用方持有 | - | 是 |
| `onColorChange` | `(Color) -> Unit` | 以指针或按键位置解析出的颜色触发的回调 | - | 是 |
| `modifier` | `Modifier` | 仅应用一次到面板根节点的修饰符 | `Modifier` | 否 |
| `enabled` | `Boolean` | 面板是否接受指针与键盘交互 | `true` | 否 |
| `colors` | `ElegantColorAreaColors` | 填充、轮廓、滑块与焦点圆环的主题感知颜色 | `ElegantColorAreaDefaults.colors()` | 否 |

### ElegantColorAreaDefaults

| 成员 | 类型 | 说明 |
| --- | --- | --- |
| `Width` | `Dp` | 220dp 默认面板宽度 |
| `Height` | `Dp` | 160dp 默认面板高度 |
| `ThumbSize` | `Dp` | 16dp 圆形滑块直径 |
| `colors()` | `ElegantColorAreaColors` | 主题感知的 Light/Dark 颜色 |

### ElegantColorAreaColors

`ElegantColorAreaColors` 包含基础填充、轮廓与滑块圆环色、滑块填充色,以及焦点轮廓色。应先调用 `ElegantColorAreaDefaults.colors()`,再仅针对产品明确需要的层级使用 `copy(...)`。

### ElegantHueSlider 属性

| 属性名 | 类型 | 说明 | 默认值 | 是否必须 |
| --- | --- | --- | --- | --- |
| `hue` | `Float` | 当前色相角度,由调用方持有;超出 `0..360` 的值会被钳制 | - | 是 |
| `onHueChange` | `(Float) -> Unit` | 用户交互后以解析出的色相触发的回调 | - | 是 |
| `modifier` | `Modifier` | 仅应用一次到交互根节点的修饰符 | `Modifier` | 否 |
| `enabled` | `Boolean` | 滑块是否接受指针与键盘交互 | `true` | 否 |
| `colors` | `ElegantHueSliderColors` | 轨道、滑块、轮廓与焦点圆环的主题感知颜色 | `ElegantHueSliderDefaults.colors()` | 否 |

### ElegantHueSliderDefaults

| 成员 | 类型 | 说明 |
| --- | --- | --- |
| `Height` | `Dp` | 12dp 彩虹轨道高度 |
| `ThumbSize` | `Dp` | 16dp 圆形滑块直径 |
| `colors()` | `ElegantHueSliderColors` | 主题感知的 Light/Dark 颜色 |

### ElegantHueSliderColors

`ElegantHueSliderColors` 包含轨道基础色(默认透明,因为彩虹渐变即轨道)、滑块填充色、轮廓与滑块圆环色,以及焦点轮廓色。应先调用 `ElegantHueSliderDefaults.colors()`,再仅针对产品明确需要的层级使用 `copy(...)`。

## 进阶用法

### 主题强调色编辑器

将自由取色面板与精选色块选择器组合,让用户既可以拖动遍历连续色彩空间,也可以直接跳到调色板颜色。

```kotlin
var accent by remember { mutableStateOf(Color(0xFF6C4EFF)) }

Column(verticalArrangement = Arrangement.spacedBy(ElegantSpacing.lg)) {
    ElegantColorPickerPanel(
        color = accent,
        onColorChange = { accent = it },
    )
    ElegantColorPicker(
        selectedColor = accent,
        onColorSelected = { accent = it },
    )
}
```
