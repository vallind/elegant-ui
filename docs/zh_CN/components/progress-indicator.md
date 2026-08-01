# Progress Indicator

`ElegantLinearProgressIndicator` 与 `ElegantCircularProgressIndicator` 是精致的非交互进度指示器。它们渲染全宽圆角线性轨道与圆形圆环,并以已填充的段或弧展示进度,支持 `0f..1f` 内的确定值进度与持续的无限态扫描,同时提供 `progressBarRangeInfo` 无障碍语义与 Light/Dark 主题感知。

<iframe id="demoIframe" style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; border: none;" src="../../compose/index.html?id=progress-indicator" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>

## 引入

```kotlin
import com.elegant.compose.ui.progress.ElegantCircularProgressIndicator
import com.elegant.compose.ui.progress.ElegantLinearProgressIndicator
import com.elegant.compose.ui.progress.ElegantProgressColors
import com.elegant.compose.ui.progress.ElegantProgressDefaults
```

## 基本用法

指示器由 `0f..1f` 范围内的 `progress` 分数驱动。线性指示器从逻辑起始处填充轨道,圆形指示器则从顶部起顺时针扫出弧。超出范围的值会被约束,NaN 按无限态处理。

```kotlin
var uploadProgress by remember { mutableStateOf(0.65f) }

ElegantLinearProgressIndicator(progress = uploadProgress)
ElegantCircularProgressIndicator(progress = uploadProgress)
```

## 组件状态

传入 `progress = null` 会将指示器切换到无限态:线性指示器展示从左向右循环扫描的段,圆形指示器展示持续旋转的弧。两种状态均不可交互,并通过 `progressBarRangeInfo` 语义播报当前进度。

```kotlin
ElegantLinearProgressIndicator(progress = null)
ElegantCircularProgressIndicator(progress = null)
```

## 属性

### ElegantLinearProgressIndicator 属性

| 属性名 | 类型 | 说明 | 默认值 | 是否必须 |
| --- | --- | --- | --- | --- |
| `progress` | `Float?` | 当前进度分数;null 或 NaN 渲染无限态扫描 | - | 是 |
| `modifier` | `Modifier` | 仅应用一次到指示器根节点的修饰符 | `Modifier` | 否 |
| `colors` | `ElegantProgressColors` | 主题感知的指示器与轨道颜色 | `ElegantProgressDefaults.colors()` | 否 |

### ElegantCircularProgressIndicator 属性

| 属性名 | 类型 | 说明 | 默认值 | 是否必须 |
| --- | --- | --- | --- | --- |
| `progress` | `Float?` | 当前进度分数;null 或 NaN 渲染无限态旋转 | - | 是 |
| `modifier` | `Modifier` | 仅应用一次到指示器根节点的修饰符 | `Modifier` | 否 |
| `size` | `Dp` | 圆形圆环的直径 | `40.dp` | 否 |
| `strokeWidth` | `Dp` | 圆形圆环的描边粗细 | `4.dp` | 否 |
| `colors` | `ElegantProgressColors` | 主题感知的指示器与轨道颜色 | `ElegantProgressDefaults.colors()` | 否 |

### ElegantProgressDefaults

| 成员 | 类型 | 说明 |
| --- | --- | --- |
| `LinearTrackHeight` | `Dp` | 线性轨道的 4dp 高度 |
| `CircularSize` | `Dp` | 圆形圆环的 40dp 直径 |
| `CircularStrokeWidth` | `Dp` | 圆环的 4dp 描边粗细 |
| `AnimationDurationMillis` | `Int` | 标准 160ms 状态过渡时长 |
| `IndeterminateDurationMillis` | `Int` | 单次无限态扫描或旋转的 1200ms 时长 |
| `colors()` | `ElegantProgressColors` | Light/Dark 主题感知颜色 |

### ElegantProgressColors

`ElegantProgressColors` 包含两个指示器共用的指示器颜色与轨道颜色。应先调用 `ElegantProgressDefaults.colors()`,再仅针对产品明确需要的层级使用 `copy(...)`。

## 进阶用法

### 上传行

将线性与圆形指示器结合文本,在真实场景中展示上传进度:圆环与轨道同步展示同一分数,标签实时读出当前百分比。

```kotlin
var uploadProgress by remember { mutableStateOf(0.35f) }

Row(
    modifier = Modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.spacedBy(ElegantSpacing.lg),
    verticalAlignment = Alignment.CenterVertically,
) {
    ElegantCircularProgressIndicator(progress = uploadProgress)
    Column(modifier = Modifier.weight(1f)) {
        Text(
            text = "report.pdf",
            style = ElegantTheme.typography.labelMedium,
        )
        ElegantLinearProgressIndicator(progress = uploadProgress)
    }
}
```
