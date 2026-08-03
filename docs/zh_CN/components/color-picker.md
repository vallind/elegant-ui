# ColorPicker

`ElegantColorPicker` 以自动换行的圆形色块网格展示调色板,并通过受控回调上报所选颜色。它适用于主题定制、强调色选择,以及任何需要用户从一组精选颜色中挑选一个值的场景。

<iframe id="demoIframe" style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; border: none;" src="../../compose/index.html?id=color-picker" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>

## 引入

```kotlin
import com.elegant.compose.ui.colorpicker.ElegantColorPicker
import com.elegant.compose.ui.colorpicker.ElegantColorPickerColors
import com.elegant.compose.ui.colorpicker.ElegantColorPickerDefaults
```

## 基本用法

`ElegantColorPicker` 是受控组件:`selectedColor` 由调用方持有,每次点击色块都会以所选颜色触发 `onColorSelected`,由调用方写回。

```kotlin
var selected by remember { mutableStateOf(ElegantColorPickerDefaults.palette().first()) }

ElegantColorPicker(
    selectedColor = selected,
    onColorSelected = { selected = it },
)
```

## 调色板

默认调色板是一组固定的精选 16 色:8 个饱和彩色(红、橙、琥珀、绿、青、蓝、紫、粉)后接 8 个同色相的浅色。色块以 32dp 渲染在按 8dp 节奏自动换行的 `FlowRow` 中。可以通过 `colors` 传入任意 `List<Color>` 作为产品自定义调色板;该列表由调用方持有,并应在重组期间保持稳定。

```kotlin
val brandPalette = listOf(
    Color(0xFF6C4EFF),
    Color(0xFF5840D6),
    Color(0xFFA99CFF),
    Color(0xFF17181A),
)

ElegantColorPicker(
    selectedColor = brand,
    onColorSelected = { brand = it },
    colors = brandPalette,
)
```

## 组件状态

每个色块的状态优先级为 disabled、selected、hovered、resting。选中的色块绘制 2dp `interactivePrimary` 圆环;hovered 或键盘聚焦的色块绘制 1dp 圆环;disabled 的色块以 40% 不透明度渲染,且永不触发回调。每个色块会播报 `Role.Button`、其 `selected` 状态,并以 `#RRGGBB` 十六进制值作为内容描述。

```kotlin
ElegantColorPicker(
    selectedColor = selected,
    onColorSelected = { selected = it },
)

ElegantColorPicker(
    selectedColor = selected,
    onColorSelected = {},
    enabled = false,
)
```

## 属性

### ElegantColorPicker 属性

| 属性名 | 类型 | 说明 | 默认值 | 是否必须 |
| --- | --- | --- | --- | --- |
| `selectedColor` | `Color` | 当前选中的颜色,由调用方持有;按分量比较 | - | 是 |
| `onColorSelected` | `(Color) -> Unit` | 以用户所选颜色触发的回调 | - | 是 |
| `modifier` | `Modifier` | 仅应用一次到选择器根节点的修饰符 | `Modifier` | 否 |
| `enabled` | `Boolean` | 色块是否接受点击 | `true` | 否 |
| `colors` | `List<Color>` | 渲染在网格中的调色板色块 | `ElegantColorPickerDefaults.palette()` | 否 |
| `paletteColors` | `ElegantColorPickerColors` | resting、selected 与 hovered 状态的主题感知圆环颜色 | `ElegantColorPickerDefaults.colors()` | 否 |

### ElegantColorPickerDefaults

| 成员 | 类型 | 说明 |
| --- | --- | --- |
| `SwatchSize` | `Dp` | 每个色块的 32dp 视觉直径 |
| `SwatchGap` | `Dp` | 两个流向轴上色块交互根之间的 8dp 间距 |
| `colors()` | `ElegantColorPickerColors` | 主题感知的 Light/Dark 圆环颜色 |
| `palette()` | `List<Color>` | 精选的 16 色默认调色板 |

### ElegantColorPickerColors

`ElegantColorPickerColors` 包含静止边框色、选中圆环色与 hovered 圆环色(默认取 `containerColor`)。应先调用 `ElegantColorPickerDefaults.colors()`,再仅针对产品明确需要的层级使用 `copy(...)`。

## 进阶用法

### 主题强调色选择器

从主题角色构建小调色板,让强调色跟随当前的 Light 或 Dark 主题。

```kotlin
var accent by remember { mutableStateOf(ElegantTheme.colors.interactivePrimary) }

val accentPalette = listOf(
    ElegantTheme.colors.interactivePrimary,
    ElegantTheme.colors.interactivePrimaryHover,
    ElegantTheme.colors.interactivePrimaryPressed,
    ElegantTheme.colors.focusRing,
)

ElegantColorPicker(
    selectedColor = accent,
    onColorSelected = { accent = it },
    colors = accentPalette,
)
```

### 十六进制读数

色块通过语义播报其十六进制值;也可以用一个简单的格式化函数在屏幕上显示同样的值。

```kotlin
var selected by remember { mutableStateOf(ElegantColorPickerDefaults.palette().first()) }

fun hex(color: Color): String {
    fun channel(value: Float): Int = (value * 255f + 0.5f).toInt().coerceIn(0, 255)
    return listOf(color.red, color.green, color.blue).joinToString(prefix = "#", separator = "") {
        channel(it).toString(16).uppercase().padStart(2, '0')
    }
}

Column {
    ElegantColorPicker(
        selectedColor = selected,
        onColorSelected = { selected = it },
    )
    Text(
        text = hex(selected),
        style = ElegantTheme.typography.bodyMedium,
        color = ElegantTheme.colors.textSecondary,
    )
}
```
