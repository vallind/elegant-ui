# Radio

`ElegantRadio` 是精致的单选指示组件,用于互斥的选项集合。它渲染一个 20dp 的圆形指示器,选中时以动画圆点填充,保持 48dp 最小交互行,并播报合并后的 `Role.RadioButton` 状态。它适用于设置、筛选与表单中必须从一组里恰好选择一个的场景。

<iframe id="demoIframe" style="width: 100%; height: 460px; border: 1px solid var(--vp-c-divider); border-radius: 12px; display: block; background: var(--vp-c-bg-alt);" src="../../compose/index.html?id=radio" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>

## 引入

```kotlin
import com.elegant.compose.ui.radio.ElegantRadio
import com.elegant.compose.ui.radio.ElegantRadioColors
import com.elegant.compose.ui.radio.ElegantRadioDefaults
```

## 基本用法

Radio 需要 `selected` 与 `onSelect`。可选的 `label` 显示在指示器之后,并作为行语义的一部分被播报;不传时行保持可交互且安静。

```kotlin
ElegantRadio(
    selected = true,
    onSelect = { /* 播报已选中的选项 */ },
    label = "标准配送",
)

ElegantRadio(
    selected = false,
    onSelect = { /* 切换回快速配送 */ },
    label = "快速配送",
)
```

## 组件状态

Radio 行会播报 `Role.RadioButton` 与 `selected` 状态,在主题启用焦点环时在指示器上显示焦点环,提供 hover 与 press 反馈,并保持 48dp 最小交互目标。

状态优先级:disabled、pressed、focused 圆环、hovered、resting。Selected 与 unselected 是语义状态,可与交互颜色组合。

```kotlin
var accent by remember { mutableStateOf("Violet") }

for (candidate in listOf("Violet", "Indigo", "Teal")) {
    ElegantRadio(
        selected = accent == candidate,
        onSelect = { accent = candidate },
        label = candidate,
    )
}

ElegantRadio(
    selected = true,
    onSelect = {},
    enabled = false,
    label = "不可用",
)
```

## 属性

### ElegantRadio 属性

| 属性名 | 类型 | 说明 | 默认值 | 是否必须 |
| --- | --- | --- | --- | --- |
| `selected` | `Boolean` | 是否表达已选中的选项 | - | 是 |
| `onSelect` | `() -> Unit` | Radio 接受选择时调用的回调 | - | 是 |
| `modifier` | `Modifier` | 仅应用一次到交互行的修饰符 | `Modifier` | 否 |
| `enabled` | `Boolean` | 是否接受用户交互 | `true` | 否 |
| `label` | `String?` | 指示器之后可选显示的文本标签 | `null` | 否 |
| `colors` | `ElegantRadioColors` | 主题感知的状态颜色 | `ElegantRadioDefaults.colors()` | 否 |
| `interactionSource` | `MutableInteractionSource?` | 可选提升的交互源,用于观察或控制状态 | `null` | 否 |

### ElegantRadioDefaults

| 成员 | 类型 | 说明 |
| --- | --- | --- |
| `BoxSize` | `Dp` | 20dp 圆形指示器尺寸 |
| `MinimumTouchHeight` | `Dp` | 48dp 最小交互行高度 |
| `AnimationDurationMillis` | `Int` | 标准 160ms 状态过渡时长 |
| `colors()` | `ElegantRadioColors` | Light/Dark 主题感知颜色 |

### ElegantRadioColors

`ElegantRadioColors` 包含 selected 与 unselected 状态的指示器颜色,以及 hovered、pressed、disabled、focused 各变体。应先调用 `ElegantRadioDefaults.colors()`,再仅针对产品明确需要的层级使用 `copy(...)`。

## 进阶用法

### 互斥的单选组

在整组共享一个可变选择状态,确保同时只选中一个 Radio。

```kotlin
var delivery by remember { mutableStateOf("Standard") }

Column {
    for (option in listOf("Standard", "Express", "Overnight")) {
        ElegantRadio(
            selected = delivery == option,
            onSelect = { delivery = option },
            label = option,
        )
    }
}
```

### 自定义颜色

```kotlin
val baseColors = ElegantRadioDefaults.colors()

ElegantRadio(
    selected = selected,
    onSelect = onSelect,
    colors = baseColors.copy(
        selectedColor = Color(0xFF147D64),
        unselectedColor = Color(0xFF92969E),
    ),
    label = "自定义",
)
```
