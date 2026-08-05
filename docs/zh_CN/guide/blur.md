# Blur

Elegant UI 提供两套模糊体系。`Modifier.elegantBlur` 在 Android、Desktop JVM 与 Web/Wasm 上对节点自身绘制的内容施加高斯模糊。`elegant-blur` 模块则提供完整的背景玻璃效果体系:把表面背后的内容捕获进图形层,再施加降采样、噪点抖动、多级纹理模糊——支持渐进(渐变)过渡、颜色混合与边缘高光,与 Miuix 玻璃管线逐像素一致。

<iframe id="demoIframe" style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; border: none;" src="../../compose/index.html?id=blur" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>

## 引入

节点模糊位于 `foundation.effect`,背景模糊体系位于 `elegant-blur` 模块:

```kotlin
import com.elegant.compose.ui.foundation.effect.BlurEdgeTreatment
import com.elegant.compose.ui.foundation.effect.elegantBlur
import com.elegant.compose.ui.blur.rememberElegantLayerBackdrop
import com.elegant.compose.ui.blur.elegantLayerBackdrop
import com.elegant.compose.ui.blur.elegantTextureBlur
import com.elegant.compose.ui.blur.elegantProgressiveTextureBlur
```

## 基本用法

以正数半径挂载 `elegantBlur` 即可模糊节点自身绘制的内容。非正数或非有限半径会让修饰符保持原样。

```kotlin
Text(
    text = "模糊文本",
    modifier = Modifier.elegantBlur(radius = 8.dp),
)
```

## 背景模糊

背景体系模糊的是表面*背后*的内容,而不是表面自身的内容。先把背景捕获进一个层,再把模糊施加到前景表面:

```kotlin
val backdrop = rememberElegantLayerBackdrop()

Box(Modifier.elegantLayerBackdrop(backdrop)) { /* 滚动的背景内容 */ }

Box(
    Modifier
        .size(200.dp)
        .elegantTextureBlur(
            backdrop = backdrop,
            shape = RoundedCornerShape(ElegantRadius.lg),
            blurRadius = 20f,
        ),
) { /* 清晰的前景内容 */ }
```

`elegantTextureBlur` 会降采样捕获的层、做抗色带抖动,再用可分离高斯核模糊,最后把表面自身内容合成在顶部。`ElegantBlurDefaults.blurColors` 产生的 `colors` 负责亮度/对比度/饱和度调整与分层颜色混合(支持全部标准与扩展混合模式);`highlight` 在顶部绘制 `ElegantHighlight` 边缘光晕;`contentBlendMode = ComposeBlendMode.DstIn` 用内容 alpha 遮罩模糊,实现前景模糊。

## 渐进模糊

`elegantProgressiveTextureBlur` 沿渐变方向把模糊从全强度过渡到像素级清晰——非常适合导航栏与边缘渐变。`ElegantProgressiveBlur` 预设覆盖四条边(`Top`、`Bottom`、`Left`、`Right`);`angle`、`startFraction`、`endFraction` 与 `curve` 可自定义渐变带。

```kotlin
val backdrop = rememberElegantLayerBackdrop()

Box(
    Modifier
        .fillMaxWidth()
        .height(56.dp)
        .elegantProgressiveTextureBlur(
            backdrop = backdrop,
            shape = RectangleShape,
            blurRadius = 40f,
            gradient = ElegantProgressiveBlur.Bottom,
        ),
) { /* 导航栏内容 */ }
```

## 属性

### Modifier.elegantBlur 属性

| 属性名 | 类型 | 说明 | 默认值 | 是否必须 |
| --- | --- | --- | --- | --- |
| `radius` | `Dp` | 模糊半径;非正数或非有限值让修饰符保持原样 | - | 是 |
| `edgeTreatment` | `BlurEdgeTreatment` | 模糊层在边缘处的处理方式 | `BlurEdgeTreatment.Rectangle` | 否 |

### Modifier.elegantTextureBlur 属性

| 属性名 | 类型 | 说明 | 默认值 | 是否必须 |
| --- | --- | --- | --- | --- |
| `backdrop` | `ElegantBackdrop` | 用于模糊的已捕获背景 | - | 是 |
| `shape` | `Shape` | 裁剪模糊区域 | - | 是 |
| `blurRadius` | `Float` | 以 dp 计的模糊半径,钳制在 `ElegantBlurDefaults.MaxBlurRadius` 内 | `20f` | 否 |
| `noiseCoefficient` | `Float` | 抗色带抖动强度;0 表示禁用 | `0.0045f` | 否 |
| `colors` | `ElegantBlurColors` | 亮度/对比度/饱和度调整与混合层 | `ElegantBlurColors()` | 否 |
| `highlight` | `ElegantHighlight?` | 绘制在顶部的边缘高光;null 跳过 | `null` | 否 |
| `contentBlendMode` | `BlendMode` | 内容合成到模糊之上的方式 | `SrcOver` | 否 |
| `enabled` | `Boolean` | 为 false 时跳过效果 | `true` | 否 |

### Modifier.elegantProgressiveTextureBlur 属性

| 属性名 | 类型 | 说明 | 默认值 | 是否必须 |
| --- | --- | --- | --- | --- |
| `backdrop` | `ElegantBackdrop` | 用于模糊的已捕获背景 | - | 是 |
| `shape` | `Shape` | 裁剪模糊区域 | - | 是 |
| `blurRadius` | `Float` | 全强度处的模糊半径,以 dp 计 | `20f` | 否 |
| `gradient` | `ElegantProgressiveBlur` | 模糊全强度与零的位置 | `ElegantProgressiveBlur.Top` | 否 |
| `noiseCoefficient` | `Float` | 抗色带抖动强度;0 表示禁用 | `0f` | 否 |
| `colors` | `ElegantBlurColors` | 亮度/对比度/饱和度调整与混合层 | `ElegantBlurColors()` | 否 |
| `highlight` | `ElegantHighlight?` | 绘制在顶部的边缘高光;null 跳过 | `null` | 否 |
| `contentBlendMode` | `BlendMode` | 内容合成到模糊之上的方式 | `SrcOver` | 否 |
| `enabled` | `Boolean` | 为 false 时跳过效果 | `true` | 否 |

## 进阶用法

### 带色调与高光的玻璃面板

把捕获的背景与带色调的模糊、光晕组合,即得经典玻璃卡片。`ElegantBlurDefaults.blurColors` 会记住颜色配置,保持 `@Immutable` 稳定性。

```kotlin
val backdrop = rememberElegantLayerBackdrop()
val colors = ElegantBlurDefaults.blurColors(
    blendColors = listOf(
        ElegantBlendColorEntry(Color.White.copy(alpha = 0.4f), ElegantBlurBlendMode.SrcOver),
    ),
    brightness = 0.05f,
)

Box(Modifier.elegantLayerBackdrop(backdrop)) { /* 背景 */ }

ElegantCard(
    modifier = Modifier.elegantTextureBlur(
        backdrop = backdrop,
        shape = RoundedCornerShape(ElegantRadius.lg),
        blurRadius = 24f,
        colors = colors,
        highlight = ElegantHighlight.GlassStrokeMiddleLight,
    ),
) {
    Text("玻璃表面")
}
```

背景模糊管线依赖运行时着色器;在缺乏支持的平台或 API 级别(`isRuntimeShaderSupported()` 为 false,例如低于 API 33 的 Android)上,效果会直接绘制而不模糊——与参考实现行为一致。`Modifier.elegantBlur` 没有此限制。
