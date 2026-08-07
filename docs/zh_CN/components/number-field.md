# NumberField

`ElegantNumberField` 是精致的整数输入组件,提供可选的标签、占位符、辅助文本或错误文本、前置图标、紧凑的增减步进按钮以及方向键步进。它适用于数量、计数、年龄等场景:键盘与指针都必须能调整到同一个受约束的数值。

<iframe id="demoIframe" style="width: 100%; height: 460px; border: 1px solid var(--vp-c-divider); border-radius: 12px; display: block; background: var(--vp-c-bg-alt);" src="../../compose/index.html?id=number-field" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>

## 引入

```kotlin
import com.elegant.compose.ui.numberfield.ElegantNumberField
import com.elegant.compose.ui.numberfield.ElegantNumberFieldColors
import com.elegant.compose.ui.numberfield.ElegantNumberFieldDefaults
```

## 基本用法

`ElegantNumberField` 是受控组件:将整数 `value` 保存在 `remember` 状态中,并在 `onValueChange` 中写回每一次被接受的值。聚焦时,编辑中的文本是本地草稿;空草稿或悬空的负号等不完整输入不会被提交,失去焦点时草稿重置为 `value`。

```kotlin
var quantity by remember { mutableStateOf(1) }

ElegantNumberField(
    value = quantity,
    onValueChange = { quantity = it },
    label = "数量",
    minValue = 1,
    maxValue = 99,
    supportingText = "数量至少为 1 的订单免运费。",
)
```

## 组件状态

`ElegantNumberField` 遵循交互优先级:disabled、错误边框、focused 边框、hovered 边框、resting。`isError` 将边框变为 `statusCritical`,用 `errorText` 替换输入框下方的辅助文本,并通过语义播报错误信息。

当 `enabled` 为 false 时,输入框拒绝聚焦、输入、方向键步进与两个步进按钮;按钮在到达范围边界时保持可见并使用禁用颜色。倒置范围(`minValue` 大于 `maxValue`)视为无边界:不拒绝任何值,步进只在 `Int` 极限处停止。

```kotlin
ElegantNumberField(
    value = 4,
    onValueChange = {},
    label = "禁用输入框",
    enabled = false,
)

ElegantNumberField(
    value = 12,
    onValueChange = { count = it },
    label = "人数",
    minValue = 1,
    maxValue = 50,
    isError = true,
    errorText = "大厅最多容纳 50 人。",
)
```

## 属性

### ElegantNumberField 属性

| 属性名 | 类型 | 说明 | 默认值 | 是否必须 |
| --- | --- | --- | --- | --- |
| `value` | `Int` | 当前已提交的整数,由调用方持有 | - | 是 |
| `onValueChange` | `(Int) -> Unit` | 以最新被接受的整数值触发的回调 | - | 是 |
| `modifier` | `Modifier` | 仅应用一次到输入组件根节点的修饰符 | `Modifier` | 否 |
| `enabled` | `Boolean` | 输入框是否接受聚焦、输入与步进 | `true` | 否 |
| `label` | `String?` | 显示在输入框上方的标签 | `null` | 否 |
| `placeholder` | `String?` | 输入框启用且为空时显示在内部的提示 | `null` | 否 |
| `minValue` | `Int` | 最小接受值;倒置范围视为无边界 | `Int.MIN_VALUE` | 否 |
| `maxValue` | `Int` | 最大接受值;倒置范围视为无边界 | `Int.MAX_VALUE` | 否 |
| `step` | `Int` | 步进按钮与方向键使用的增量;非正值回退为 1 | `ElegantNumberFieldDefaults.Step` | 否 |
| `supportingText` | `String?` | 显示在输入框下方的辅助文本,错误文本出现时隐藏 | `null` | 否 |
| `isError` | `Boolean` | 输入框是否表达错误状态 | `false` | 否 |
| `errorText` | `String?` | 当 `isError` 时显示并播报的错误信息 | `null` | 否 |
| `colors` | `ElegantNumberFieldColors` | 主题感知的状态颜色 | `ElegantNumberFieldDefaults.colors()` | 否 |
| `leadingIcon` | `@Composable (() -> Unit)?` | 输入区域前的内容,使用输入框内容色着色 | `null` | 否 |

### ElegantNumberFieldDefaults

| 成员 | 类型 | 说明 |
| --- | --- | --- |
| `Step` | `Int` | 步进使用的默认增量 `1` |
| `MinimumTouchHeight` | `Dp` | 48dp 最小输入框高度 |
| `AnimationDurationMillis` | `Int` | 标准 160ms 状态过渡时长 |
| `colors()` | `ElegantNumberFieldColors` | 与填充输入角色一致的 Light/Dark 主题感知颜色 |

### ElegantNumberFieldColors

`ElegantNumberFieldColors` 包含与 `ElegantInputColors` 相同的容器、边框、内容、占位符、标签、辅助文本与错误文本角色,并按填充输入的主题层级解析。应先调用 `ElegantNumberFieldDefaults.colors()`,再仅针对产品明确需要的层级使用 `copy(...)`。

## 进阶用法

### 有界步进

```kotlin
var guests by remember { mutableStateOf(2) }

ElegantNumberField(
    value = guests,
    onValueChange = { guests = it },
    label = "宾客",
    minValue = 1,
    maxValue = 8,
    step = 2,
    supportingText = "步进按钮在范围边界处停止。",
)
```

### 自定义错误样式

```kotlin
val baseColors = ElegantNumberFieldDefaults.colors()

ElegantNumberField(
    value = age,
    onValueChange = { age = it },
    label = "年龄",
    minValue = 18,
    colors = baseColors.copy(
        errorBorderColor = Color(0xFFC63D52),
        errorTextColor = Color(0xFFC63D52),
    ),
    isError = true,
    errorText = "您必须年满 18 周岁。",
)
```
