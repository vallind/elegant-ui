# RadioPreference

`ElegantRadioPreference` 是设置风格的单选行,用于呈现一个可选项。与裸 `ElegantRadio` 不同,整行都是交互目标:点击标题、辅助文本或尾部指示器都会触发 `onSelect`。它渲染 `labelMedium` 标题与可选的 `bodyMedium` 辅助行、尾部 `ElegantRadio` 指示器、hovered 与 pressed 容器反馈,以及从起始边缘内缩 16dp 的可选底部分割线。

<iframe id="demoIframe" style="width: 100%; height: 460px; border: 1px solid var(--vp-c-divider); border-radius: 12px; display: block; background: var(--vp-c-bg-alt);" src="../../compose/index.html?id=radio-preference" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>

## 引入

```kotlin
import com.elegant.compose.ui.preference.ElegantRadioPreference
import com.elegant.compose.ui.preference.ElegantRadioPreferenceColors
import com.elegant.compose.ui.preference.ElegantRadioPreferenceDefaults
```

## 基本用法

单选偏好行需要 `title`、`selected` 与 `onSelect`。在整组共享一个选择状态,确保同时只选中一行;每行保持 48dp 最小交互高度。

```kotlin
ElegantRadioPreference(
    title = "Violet",
    selected = accent == "Violet",
    onSelect = { accent = "Violet" },
)

ElegantRadioPreference(
    title = "Indigo",
    selected = accent == "Indigo",
    onSelect = { accent = "Indigo" },
    supportingText = "更安静的蓝色",
    showDivider = false,
)
```

## 组件状态

行会播报 `Role.RadioButton`、`selected` 状态与禁用状态。整行可点击激活;尾部指示器反映相同状态并接受自身指针输入。

状态优先级:disabled、pressed、hovered、resting。禁用行保持 resting 容器,并将标题切换为禁用颜色。

```kotlin
var delivery by remember { mutableStateOf("Standard") }

ElegantRadioPreference(
    title = "Standard",
    selected = delivery == "Standard",
    onSelect = { delivery = "Standard" },
    supportingText = "3 至 5 个工作日",
)

ElegantRadioPreference(
    title = "Express",
    selected = delivery == "Express",
    onSelect = { delivery = "Express" },
    supportingText = "1 至 2 个工作日",
)

ElegantRadioPreference(
    title = "Overnight",
    selected = false,
    onSelect = {},
    enabled = false,
    supportingText = "当前不可用",
)
```

## 属性

### ElegantRadioPreference 属性

| 属性名 | 类型 | 说明 | 默认值 | 是否必须 |
| --- | --- | --- | --- | --- |
| `title` | `String` | 以 `labelMedium` 显示的行标题 | - | 是 |
| `selected` | `Boolean` | 是否表达已选中的选项 | - | 是 |
| `onSelect` | `() -> Unit` | 行接受选择时调用的回调 | - | 是 |
| `modifier` | `Modifier` | 仅应用一次到交互行的修饰符 | `Modifier` | 否 |
| `supportingText` | `String?` | 标题之下可选显示的辅助文本 | `null` | 否 |
| `enabled` | `Boolean` | 是否接受用户交互 | `true` | 否 |
| `colors` | `ElegantRadioPreferenceColors` | 主题感知的状态颜色 | `ElegantRadioPreferenceDefaults.colors()` | 否 |
| `showDivider` | `Boolean` | 是否绘制内缩 16dp 的底部分割线 | `true` | 否 |

### ElegantRadioPreferenceDefaults

| 成员 | 类型 | 说明 |
| --- | --- | --- |
| `MinimumTouchHeight` | `Dp` | 48dp 最小交互行高度 |
| `AnimationDurationMillis` | `Int` | 标准 160ms 状态过渡时长 |
| `colors()` | `ElegantRadioPreferenceColors` | Light/Dark 主题感知颜色 |

### ElegantRadioPreferenceColors

`ElegantRadioPreferenceColors` 包含 resting 容器与标题颜色、辅助文本与分割线颜色、禁用标题颜色,以及 hovered 与 pressed 容器色调。应先调用 `ElegantRadioPreferenceDefaults.colors()`,再仅针对产品明确需要的层级使用 `copy(...)`。

## 进阶用法

### 卡片上的设置组

在卡片表面堆叠行,并让行分割线绘制分隔。

```kotlin
ElegantCard(
    modifier = Modifier.fillMaxWidth(),
) {
    var accent by remember { mutableStateOf("Violet") }

    Column {
        for (candidate in listOf("Violet", "Indigo", "Teal")) {
            ElegantRadioPreference(
                title = candidate,
                selected = accent == candidate,
                onSelect = { accent = candidate },
                showDivider = candidate != "Teal",
            )
        }
    }
}
```

### 自定义颜色

```kotlin
val baseColors = ElegantRadioPreferenceDefaults.colors()

ElegantRadioPreference(
    title = "Compact",
    selected = selected,
    onSelect = onSelect,
    colors = baseColors.copy(
        titleColor = Color(0xFF147D64),
        supportingTextColor = Color(0xFF6E727A),
    ),
    supportingText = "保持行高度紧凑",
)
```
