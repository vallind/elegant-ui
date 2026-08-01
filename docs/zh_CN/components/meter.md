# Meter

`ElegantMeter` 是精致的非交互确定值仪表。它渲染全宽圆角轨道,以与约束后数值成比例的部分展示填充,填充色调可从使用区间(健康、高位、临界)自动解析,也可由调用方强制指定;轨道下方可显示可选标签,同时提供 `progressBarRangeInfo` 无障碍语义与 Light/Dark 主题感知。

<iframe id="demoIframe" style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; border: none;" src="../../compose/index.html?id=meter" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>

## 引入

```kotlin
import com.elegant.compose.ui.meter.ElegantMeter
import com.elegant.compose.ui.meter.ElegantMeterColors
import com.elegant.compose.ui.meter.ElegantMeterDefaults
import com.elegant.compose.ui.meter.ElegantMeterTone
```

## 基本用法

仪表由相对于 `valueRange`(默认为 `0f..1f`)测量的 `value` 驱动。轨道从逻辑起始处填充,填充色调根据分数自动解析:低于或等于 `highThreshold` 时为健康,高于它且不超过 90% 时为高位,超过 90% 时为临界。超出范围的值会被约束,NaN 渲染空轨道。

```kotlin
var storageUsed by remember { mutableStateOf(0.42f) }

ElegantMeter(
    value = storageUsed,
    label = "Storage used",
)
```

## 组件状态

仪表始终为确定值且不可交互,并通过 `progressBarRangeInfo` 语义播报当前分数。填充色调可通过 `tone` 参数强制指定;`ElegantMeterTone` 枚举提供 `Neutral`、`Positive`、`Warning` 与 `Critical`。

```kotlin
ElegantMeter(value = 0.35f, tone = ElegantMeterTone.Positive)
ElegantMeter(value = 0.72f, tone = ElegantMeterTone.Warning)
ElegantMeter(value = 0.96f, tone = ElegantMeterTone.Critical)
```

## 属性

### ElegantMeter 属性

| 属性名 | 类型 | 说明 | 默认值 | 是否必须 |
| --- | --- | --- | --- | --- |
| `value` | `Float` | 相对于 `valueRange` 测量的当前值;超出范围的值会被约束 | - | 是 |
| `modifier` | `Modifier` | 仅应用一次到仪表根节点的修饰符 | `Modifier` | 否 |
| `label` | `String?` | 可选标签,渲染在轨道下方;空白标签会被省略 | `null` | 否 |
| `tone` | `ElegantMeterTone?` | 显式语义填充色调;null 时根据分数自动解析 | `null` | 否 |
| `valueRange` | `ClosedFloatingPointRange<Float>` | 用于测量值的范围 | `0f..1f` | 否 |
| `lowThreshold` | `Float` | 低使用区间与中使用区间的边界 | `0.33f` | 否 |
| `highThreshold` | `Float` | 健康使用区间与高位使用区间的边界;低于或等于它的分数解析为 `Positive` | `0.66f` | 否 |
| `colors` | `ElegantMeterColors` | 主题感知的轨道、填充与文本颜色 | `ElegantMeterDefaults.colors()` | 否 |

### ElegantMeterDefaults

| 成员 | 类型 | 说明 |
| --- | --- | --- |
| `TrackHeight` | `Dp` | 仪表轨道的 6dp 高度 |
| `LowThreshold` | `Float` | 低使用区间与中使用区间之间的 0.33f 边界 |
| `HighThreshold` | `Float` | 健康使用区间与高位使用区间之间的 0.66f 边界 |
| `colors()` | `ElegantMeterColors` | Light/Dark 主题感知颜色 |

### ElegantMeterColors

`ElegantMeterColors` 包含轨道颜色、每种 `ElegantMeterTone` 的填充颜色,以及内容与标签文本颜色。应先调用 `ElegantMeterDefaults.colors()`,再仅针对产品明确需要的层级使用 `copy(...)`。

### ElegantMeterTone

`ElegantMeterTone` 选择填充的语义色调。将其传入 `tone` 参数可强制指定色调,或保持该参数为 null 以根据分数自动解析。

| 色调 | 说明 |
| --- | --- |
| `Neutral` | 仪表无语义含义或数值未知时使用的品牌填充色 |
| `Positive` | 数值处于正常运行范围内时使用的健康填充色 |
| `Warning` | 数值接近配置上限时使用的高位填充色 |
| `Critical` | 数值达到或超过上限时使用的临界填充色 |

## 进阶用法

### 存储行

将数值对照自定义范围测量,并在仪表旁展示:轨道直接反映 `valueRange`,数值文本由调用方自行组合。

```kotlin
var usedBytes by remember { mutableStateOf(63.4f) }
val capacity = 128f

Row(
    modifier = Modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.spacedBy(ElegantSpacing.lg),
    verticalAlignment = Alignment.CenterVertically,
) {
    Column(modifier = Modifier.weight(1f)) {
        ElegantMeter(
            value = usedBytes,
            valueRange = 0f..capacity,
            label = "Storage",
        )
        Text(
            text = "$usedBytes GB of $capacity GB",
            style = ElegantTheme.typography.labelMedium,
        )
    }
}
```
