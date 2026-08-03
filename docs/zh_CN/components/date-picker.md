# DatePicker

`ElegantDatePicker` 是带日历弹层的只读日期字段:字段在 Filled 输入表面上渲染所选日期,点击后在其下方打开 `ElegantCalendar`。适用于调用方需要选择一个受边界约束的公历日期的场景,如预订或表单流程,支持 Android、Desktop JVM 与 Web/Wasm。

<iframe id="demoIframe" style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; border: none;" src="../../compose/index.html?id=date-picker" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>

## 引入

```kotlin
import com.elegant.compose.ui.datepicker.ElegantDatePicker
import com.elegant.compose.ui.datepicker.ElegantDatePickerColors
import com.elegant.compose.ui.datepicker.ElegantDatePickerDefaults
import com.elegant.compose.ui.calendar.ElegantDate
```

## 基本用法

`ElegantDatePicker` 是受控组件:将选中的 `date` 保存在调用方持有的状态中,并从 `onDateSelected` 回写每次选择。未选择日期时,字段显示 `placeholder`;已选择日期以补零的 `YYYY-MM-DD` 字符串渲染。

```kotlin
var selected by remember { mutableStateOf<ElegantDate?>(null) }

ElegantDatePicker(
    date = selected,
    onDateSelected = { selected = it },
    label = "出发日期",
    placeholder = "选择出发日期",
)
```

## 组件状态

点击字段会在其正下方打开一个可聚焦的日历弹层,起始对齐并钳制在窗口内。弹层依次以所选日期、`minDate` 作为初始月份;当用户选择一个日期(同时会调用 `onDateSelected`)、点击弹层外部或按下 Escape 时关闭——后两种手势均由平台弹层交付。弹层不会因字段失去焦点而关闭。

`isError` 以 `statusCritical` 绘制字段边框、在字段下方用错误文本替换辅助文本,并通过语义播报错误文本。当 `enabled` 为 false 时,字段以暗淡样式渲染、绝不打开弹层,也绝不调用 `onDateSelected`;位于 `minDate`..`maxDate` 之外的日期在日历内处于禁用状态,永不可选。

```kotlin
ElegantDatePicker(
    date = selected,
    onDateSelected = { selected = it },
    label = "到达日期",
    isError = true,
    errorText = "请选择到达日期。",
)

ElegantDatePicker(
    date = null,
    onDateSelected = {},
    label = "出发日期",
    enabled = false,
)
```

## 属性

### ElegantDatePicker 属性

| 属性名 | 类型 | 说明 | 默认值 | 是否必须 |
| --- | --- | --- | --- | --- |
| `date` | `ElegantDate?` | 选中日期,由调用方持有 | - | 是 |
| `onDateSelected` | `(ElegantDate) -> Unit` | 选择日期时触发的回调 | - | 是 |
| `modifier` | `Modifier` | 仅应用一次到组件根节点的修饰符 | `Modifier` | 否 |
| `enabled` | `Boolean` | 字段是否可以打开弹层并选择日期 | `true` | 否 |
| `label` | `String?` | 字段上方显示的标签 | `null` | 否 |
| `placeholder` | `String?` | 字段启用且为空时显示的提示 | `null` | 否 |
| `isError` | `Boolean` | 字段是否传达错误状态 | `false` | 否 |
| `errorText` | `String?` | 错误消息,显示在字段下方并在 `isError` 时播报 | `null` | 否 |
| `supportingText` | `String?` | 辅助文本,除非显示了错误文本 | `null` | 否 |
| `minDate` | `ElegantDate?` | 最早可选日期 | `null` | 否 |
| `maxDate` | `ElegantDate?` | 最晚可选日期 | `null` | 否 |
| `colors` | `ElegantDatePickerColors` | 主题感知的状态颜色 | `ElegantDatePickerDefaults.colors()` | 否 |

### ElegantDate

`ElegantDate(year, month, day)` 是不可变公历日期值,`month` 取值 1..12,可按时间先后比较。日期选择器与 `ElegantCalendar` 共享该模型,绝不依赖平台日期类型。

### ElegantDatePickerDefaults

| 成员 | 类型 | 说明 |
| --- | --- | --- |
| `MinimumTouchHeight` | `Dp` | 48dp 字段容器最小高度 |
| `colors()` | `ElegantDatePickerColors` | Light/Dark 主题感知的 Filled 字段颜色 |

### ElegantDatePickerColors

`ElegantDatePickerColors` 包含静态、悬停、聚焦、禁用与错误状态的容器、边框与内容颜色,以及占位符、标签、辅助文本与错误文本颜色。应先调用 `ElegantDatePickerDefaults.colors()`,再仅针对产品明确需要的层级使用 `copy(...)`。

## 进阶用法

### 有界预订范围

```kotlin
ElegantDatePicker(
    date = selected,
    onDateSelected = { selected = it },
    label = "入住日期",
    minDate = ElegantDate(2026, 8, 1),
    maxDate = ElegantDate(2026, 8, 31),
)
```

### 自定义强调

```kotlin
val baseColors = ElegantDatePickerDefaults.colors()

ElegantDatePicker(
    date = selected,
    onDateSelected = { selected = it },
    label = "出发日期",
    colors = baseColors.copy(
        errorBorderColor = Color(0xFFC63D52),
        errorTextColor = Color(0xFFC63D52),
    ),
    isError = true,
    errorText = "请选择出发日期。",
)
```
