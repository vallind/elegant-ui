# Spinner

`ElegantSpinner` 是精致的非交互加载指示器。它在中央渲染持续旋转的圆环,并在圆环下方可选展示加载标签,始终处于无限态,提供 `progressBarRangeInfo` 无障碍语义与 Light/Dark 主题感知。

<iframe id="demoIframe" style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; border: none;" src="../../compose/index.html?id=spinner" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>

## 引入

```kotlin
import com.elegant.compose.ui.spinner.ElegantSpinner
import com.elegant.compose.ui.spinner.ElegantSpinnerColors
import com.elegant.compose.ui.spinner.ElegantSpinnerDefaults
```

## 基本用法

Spinner 始终渲染无限态:270 度弧以无限循环顺时针旋转。传入 `label` 可在圆环下方居中展示加载文本;传入 `null` 或省略该参数则只展示圆环。

```kotlin
ElegantSpinner()

ElegantSpinner(label = "Loading...")
```

## 组件状态

Spinner 没有确定态,始终传达持续活动。`size` 与 `strokeWidth` 控制圆环几何,标签则说明正在加载的内容。组件不可交互,并通过 `progressBarRangeInfo` 语义播报无限进度;旋转弧背后的轨道为装饰性元素。

```kotlin
ElegantSpinner(size = 24.dp, strokeWidth = 3.dp)
ElegantSpinner(size = 40.dp, strokeWidth = 4.dp)
ElegantSpinner(size = 56.dp, strokeWidth = 5.dp)
```

## 属性

### ElegantSpinner 属性

| 属性名 | 类型 | 说明 | 默认值 | 是否必须 |
| --- | --- | --- | --- | --- |
| `modifier` | `Modifier` | 仅应用一次到 Spinner 根节点的修饰符 | `Modifier` | 否 |
| `size` | `Dp` | Spinner 圆环的直径;非正数或非有限值回退到默认值 | `40.dp` | 否 |
| `strokeWidth` | `Dp` | Spinner 圆环的描边粗细;非正数或非有限值回退到默认值 | `4.dp` | 否 |
| `label` | `String?` | 可选加载标签,展示在圆环下方;null 隐藏它 | `null` | 否 |
| `colors` | `ElegantSpinnerColors` | 主题感知的指示器、轨道与标签颜色 | `ElegantSpinnerDefaults.colors()` | 否 |

### ElegantSpinnerDefaults

| 成员 | 类型 | 说明 |
| --- | --- | --- |
| `Size` | `Dp` | Spinner 圆环的 40dp 直径 |
| `StrokeWidth` | `Dp` | 圆环的 4dp 描边粗细 |
| `AnimationDurationMillis` | `Int` | 标准 160ms 状态过渡时长 |
| `colors()` | `ElegantSpinnerColors` | Light/Dark 主题感知颜色 |

### ElegantSpinnerColors

`ElegantSpinnerColors` 包含 Spinner 使用的指示器、轨道与标签颜色。应先调用 `ElegantSpinnerDefaults.colors()`,再仅针对产品明确需要的层级使用 `copy(...)`。

## 进阶用法

### 加载卡片

将 Spinner 与骨架占位符结合,预览加载卡片:Spinner 传达待定状态,微光线条则预览即将填充卡片的内容。

```kotlin
ElegantCard(modifier = Modifier.fillMaxWidth()) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(ElegantSpacing.xl),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        ElegantSpinner(label = "Loading...")
        Spacer(modifier = Modifier.height(ElegantSpacing.lg))
        ElegantSkeletonBlock(columns = 3)
    }
}
```
