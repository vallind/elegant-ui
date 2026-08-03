# NumberPicker

`ElegantNumberPicker` 是精致的整数垂直步进器:居中的大号数值,上下各一个圆形递增/递减按钮。它适用于数量、座位、页码等无需键盘即可精确选数的场景。快速点击步进一次;按住按钮会在短暂延迟后按固定间隔连续步进,便于快速调整。

<iframe id="demoIframe" style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; border: none;" src="../../compose/index.html?id=number-picker" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>

## 引入

```kotlin
import com.elegant.compose.ui.numberpicker.ElegantNumberPicker
import com.elegant.compose.ui.numberpicker.ElegantNumberPickerColors
import com.elegant.compose.ui.numberpicker.ElegantNumberPickerDefaults
```

## 基本用法

`ElegantNumberPicker` 是受控组件:将整数 `value` 保存在 `remember` 状态中,并在 `onValueChange` 中写回每一次被接受的步进。按钮按 `step` 步进,并在 `minValue`..`maxValue` 处停止。

```kotlin
var quantity by remember { mutableStateOf(1) }

ElegantNumberPicker(
    value = quantity,
    onValueChange = { quantity = it },
    minValue = 1,
    maxValue = 99,
)
```

## 组件状态

按住步进按钮会在 350ms 的初始延迟后每 80ms 重复步进,直到指针松开;快速点击则只应用一次 `step`。到达范围边界时,对应的按钮被禁用并使用禁用颜色渲染,但仍保持可见。当 `enabled` 为 false 时,步进器拒绝指针交互,两个按钮与数值都切换为禁用颜色,且绝不会调用 `onValueChange`。倒置范围(`minValue` 大于 `maxValue`)视为无边界:步进从不钳制,只在 `Int` 极限处停止。

```kotlin
ElegantNumberPicker(
    value = 4,
    onValueChange = {},
    enabled = false,
)

ElegantNumberPicker(
    value = 12,
    onValueChange = { count = it },
    minValue = 1,
    maxValue = 50,
    step = 2,
)
```

## 属性

### ElegantNumberPicker 属性

| 属性名 | 类型 | 说明 | 默认值 | 是否必须 |
| --- | --- | --- | --- | --- |
| `value` | `Int` | 当前整数,由调用方持有 | - | 是 |
| `onValueChange` | `(Int) -> Unit` | 以最新被接受的整数值触发的回调 | - | 是 |
| `modifier` | `Modifier` | 仅应用一次到步进器根节点的修饰符 | `Modifier` | 否 |
| `enabled` | `Boolean` | 是否接受步进与指针交互 | `true` | 否 |
| `minValue` | `Int` | 步进可达的最小值;倒置范围视为无边界 | `0` | 否 |
| `maxValue` | `Int` | 步进可达的最大值;倒置范围视为无边界 | `Int.MAX_VALUE` | 否 |
| `step` | `Int` | 每次步进的增量;非正值回退为 1 | `1` | 否 |
| `colors` | `ElegantNumberPickerColors` | 主题感知的状态颜色 | `ElegantNumberPickerDefaults.colors()` | 否 |

### ElegantNumberPickerDefaults

| 成员 | 类型 | 说明 |
| --- | --- | --- |
| `MinimumTouchHeight` | `Dp` | 为居中的数值预留的 48dp 最小高度 |
| `ButtonSize` | `Dp` | 每个圆形步进按钮的 40dp 边长 |
| `colors()` | `ElegantNumberPickerColors` | 与步进器角色一致的 Light/Dark 主题感知颜色 |

### ElegantNumberPickerColors

`ElegantNumberPickerColors` 包含按主题层级解析的容器、内容、次要内容、禁用内容、hovered 容器、pressed 容器与分隔线角色。应先调用 `ElegantNumberPickerDefaults.colors()`,再仅针对产品明确需要的层级使用 `copy(...)`。

## 进阶用法

### 数量卡片

```kotlin
var seats by remember { mutableStateOf(2) }

ElegantCard {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(ElegantSpacing.xl),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = "座位",
            color = ElegantTheme.colors.textSecondary,
            style = ElegantTheme.typography.labelMedium,
        )
        ElegantNumberPicker(
            value = seats,
            onValueChange = { seats = it },
            minValue = 1,
            maxValue = 8,
        )
    }
}
```
