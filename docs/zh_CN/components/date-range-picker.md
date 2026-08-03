# Date Range Picker

`ElegantDateRangePicker` 是只读的日期范围字段,点击后打开双月日历面板:它并排复用 `ElegantCalendar` 网格,遵循输入家族 Filled 输入节奏,并携带可在 `commonMain` 中跨 Android、Desktop JVM 与 Web/Wasm 使用的受控不可变 `ElegantDateRange` 模型,不依赖平台日期 API。

<iframe id="demoIframe" style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; border: none;" src="../../compose/index.html?id=date-range-picker" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>

## 引入

```kotlin
import com.elegant.compose.ui.daterangepicker.ElegantDateRange
import com.elegant.compose.ui.daterangepicker.ElegantDateRangePicker
import com.elegant.compose.ui.daterangepicker.ElegantDateRangePickerColors
import com.elegant.compose.ui.daterangepicker.ElegantDateRangePickerDefaults
import com.elegant.compose.ui.calendar.ElegantDate
```

## 基本用法

范围是受控的 `ElegantDateRange`:调用方持有它,并在每次点击日期后将最新值通过 `onRangeSelected` 写回。

```kotlin
var range by remember { mutableStateOf(ElegantDateRange(null, null)) }

ElegantDateRangePicker(
    range = range,
    onRangeSelected = { range = it },
    label = "Stay dates",
    placeholder = "Pick a stay",
)
```

## 组件状态

首次点击可选日期设置开始日期;第二次点击设置结束日期;在开始日期之前进行第二次点击会将开始日期前移并清空结束日期以便重新选取;完整范围确定后的点击会开启新范围。选择后面板保持打开以便调整两个端点,点击面板外部或按 Escape 时关闭,两者均由平台弹窗提供。位于 `minDate`..`maxDate` 之外的日期以禁用状态渲染,绝不触发回调。本版本不绘制范围内日期的连续着色:范围通过两个端点和字段读出文本传达。

面板并排显示两个月,起始月份依次回退为当前开始日期所在月、`minDate`、2000 年 1 月 —— 库在 `commonMain` 中没有时钟 —— 表头行按一个月为步长移动月份对,并钳制到 `minDate`/`maxDate` 的月份范围。当 `enabled = false` 时字段变暗、拒绝点击与焦点,且绝不打开面板。

```kotlin
ElegantDateRangePicker(
    range = range,
    onRangeSelected = { range = it },
    minDate = ElegantDate(2026, 1, 1),
    maxDate = ElegantDate(2026, 12, 31),
)
```

## 属性

### ElegantDateRangePicker 属性

| 属性名 | 类型 | 说明 | 默认值 | 是否必须 |
| --- | --- | --- | --- | --- |
| `range` | `ElegantDateRange` | 当前选中范围,由调用方持有 | - | 是 |
| `onRangeSelected` | `(ElegantDateRange) -> Unit` | 携带最新接受范围的回调 | - | 是 |
| `modifier` | `Modifier` | 仅应用一次到选择器根节点的修饰符 | `Modifier` | 否 |
| `enabled` | `Boolean` | 字段是否接受点击以及面板是否可打开 | `true` | 否 |
| `label` | `String?` | 字段上方的可选标签 | `null` | 否 |
| `placeholder` | `String?` | 启用且为空时的可选提示 | `null` | 否 |
| `isError` | `Boolean` | 字段是否传达错误状态 | `false` | 否 |
| `errorText` | `String?` | 字段下方的可选错误信息 | `null` | 否 |
| `supportingText` | `String?` | 字段下方的可选辅助文本 | `null` | 否 |
| `minDate` | `ElegantDate?` | 最早可选日期 | `null` | 否 |
| `maxDate` | `ElegantDate?` | 最晚可选日期 | `null` | 否 |
| `colors` | `ElegantDateRangePickerColors` | 主题感知的状态颜色 | `ElegantDateRangePickerDefaults.colors()` | 否 |

### ElegantDateRange

`ElegantDateRange(start, end)` 是基于 `ElegantDate` 的不可变范围模型。两端点均为 null 表示尚未选择;开始日期非 null 而结束日期为 null 表示等待结束日期的范围。开始日期为 null 而结束日期非 null 是无效构造,调用方不应构造;选择器绝不发出该状态。

### ElegantDateRangePickerDefaults

| 成员 | 类型 | 说明 |
| --- | --- | --- |
| `MinimumTouchHeight` | `Dp` | 默认 48dp 字段容器高度 |
| `colors()` | `ElegantDateRangePickerColors` | Light/Dark 主题感知颜色 |

### ElegantDateRangePickerColors

`ElegantDateRangePickerColors` 与输入家族携带相同的字段角色:容器、悬停、聚焦、禁用、边框、内容、占位符、标签、辅助与错误颜色。应先调用 `ElegantDateRangePickerDefaults.colors()`,再仅针对产品明确需要的层级使用 `copy(...)`。

## 进阶用法

### 有界预订范围

```kotlin
ElegantDateRangePicker(
    range = range,
    onRangeSelected = { range = it },
    label = "Trip dates",
    minDate = ElegantDate(2026, 8, 1),
    maxDate = ElegantDate(2026, 8, 31),
)
```

### 错误反馈

```kotlin
ElegantDateRangePicker(
    range = range,
    onRangeSelected = { range = it },
    label = "Stay dates",
    isError = range.end == null && range.start != null,
    errorText = "Pick an end day to complete the stay.",
)
```
