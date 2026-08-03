# Calendar

`ElegantCalendar` 是精致的月历网格日期选择器,支持月份导航、范围边界,以及可在 `commonMain` 中跨 Android、Desktop JVM 与 Web/Wasm 使用的稳定 `ElegantDate` 模型,不依赖平台日期 API。它是日历家族的基石。

<iframe id="demoIframe" style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; border: none;" src="../../compose/index.html?id=calendar" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>

## 引入

```kotlin
import com.elegant.compose.ui.calendar.ElegantCalendar
import com.elegant.compose.ui.calendar.ElegantCalendarColors
import com.elegant.compose.ui.calendar.ElegantCalendarDefaults
import com.elegant.compose.ui.calendar.ElegantDate
```

## 基本用法

受控日历渲染周一开头的 42 格月历网格;选中日期由调用方持有。

```kotlin
var selected by remember { mutableStateOf<ElegantDate?>(null) }

ElegantCalendar(
    selectedDate = selected,
    onDateSelected = { selected = it },
)
```

## 组件状态

网格以周一为每周第一天。相邻月份的领起与收尾日期以暗淡样式渲染,且永不可选。位于 `minDate`..`maxDate` 之外的日期,以及 `enabled = false` 时的整个网格均为禁用状态:它们以禁用角色渲染、播报禁用语义,且绝不调用 `onDateSelected`。选中日期以强调容器与反色文字填充;悬停的可选日期以悬停表面着色。

月份导航在存在 `minDate`/`maxDate` 边界时钳制到其月份范围。库在 `commonMain` 中没有时钟,因此初始可见月份依次回退为 `initialMonth`、`selectedDate`、`minDate`,最后为 2000 年 1 月;需要"本月"时请传入自己的 `initialMonth`。

```kotlin
ElegantCalendar(
    selectedDate = selected,
    onDateSelected = { selected = it },
    minDate = ElegantDate(2026, 1, 1),
    maxDate = ElegantDate(2026, 12, 31),
)
```

## 属性

### ElegantCalendar 属性

| 属性名 | 类型 | 说明 | 默认值 | 是否必须 |
| --- | --- | --- | --- | --- |
| `selectedDate` | `ElegantDate?` | 当前选中日期 | - | 是 |
| `onDateSelected` | `(ElegantDate) -> Unit` | 携带所选日期的回调 | - | 是 |
| `modifier` | `Modifier` | 仅应用一次到日历根节点的修饰符 | `Modifier` | 否 |
| `enabled` | `Boolean` | 是否接受选择与导航 | `true` | 否 |
| `initialMonth` | `ElegantDate?` | 首次组合时显示的月份 | `null` | 否 |
| `minDate` | `ElegantDate?` | 最早可选日期 | `null` | 否 |
| `maxDate` | `ElegantDate?` | 最晚可选日期 | `null` | 否 |
| `colors` | `ElegantCalendarColors` | 主题感知的日历颜色 | `ElegantCalendarDefaults.colors()` | 否 |

### ElegantDate

`ElegantDate(year, month, day)` 是不可变公历日期值,`month` 取值 1..12,可按时间先后比较。直接按此结构构造即可;日历家族绝不依赖平台日期类型。

### ElegantCalendarDefaults

| 成员 | 类型 | 说明 |
| --- | --- | --- |
| `WeekdayRowHeight` | `Dp` | 默认 32dp 星期表头行高 |
| `DayCellSize` | `Dp` | 默认 40dp 日期单元格边长 |
| `DayCellGap` | `Dp` | 默认 4dp 日期单元格间距 |
| `NavigationSize` | `Dp` | 默认 40dp 月份导航按钮尺寸 |
| `colors()` | `ElegantCalendarColors` | Light/Dark 主题感知颜色 |

### ElegantCalendarColors

`ElegantCalendarColors` 包含容器、表头、星期、日期、选中、禁用、悬停、今日与边框角色。应先调用 `ElegantCalendarDefaults.colors()`,再仅针对产品明确需要的层级使用 `copy(...)`。

## 进阶用法

### 有界预订范围

```kotlin
ElegantCalendar(
    selectedDate = selected,
    onDateSelected = { selected = it },
    minDate = ElegantDate(2026, 8, 1),
    maxDate = ElegantDate(2026, 8, 31),
    initialMonth = ElegantDate(2026, 8, 1),
)
```

### 自定义强调

```kotlin
val baseColors = ElegantCalendarDefaults.colors()

ElegantCalendar(
    selectedDate = selected,
    onDateSelected = { selected = it },
    colors = baseColors.copy(
        selectedDayContainerColor = Color(0xFF6C4EFF),
        selectedDayColor = Color.White,
    ),
)
```
