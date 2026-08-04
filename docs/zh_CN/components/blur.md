# Blur

`Modifier.elegantBlur` 在 Android、Desktop JVM 与 Web/Wasm 上对节点自身绘制的内容施加高斯模糊渲染效果。它映射到 Compose Multiplatform 的模糊渲染效果(`Modifier.blur` 及其 `BlurredEdgeTreatment`),每个受支持的平台都通过平台渲染效果管线实现:Desktop JVM 使用 Skia `ImageFilter`,Android 使用模糊 `RenderEffect`,Web/Wasm 使用等效的 canvas 滤镜。

<iframe id="demoIframe" style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; border: none;" src="../../compose/index.html?id=blur" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>

## 引入

```kotlin
import com.elegant.compose.ui.effect.BlurEdgeTreatment
import com.elegant.compose.ui.effect.elegantBlur
```

## 基本用法

以正数半径挂载 `elegantBlur` 即可模糊节点自身绘制的内容。非正数或非有限半径会让修饰符保持原样。

```kotlin
Text(
    text = "模糊文本",
    modifier = Modifier.elegantBlur(radius = 8.dp),
)
```

## 组件状态

`radius` 控制模糊扩散范围,`edgeTreatment` 控制模糊层在边缘处的行为:`Rectangle` 把模糊裁剪到节点边界内,`Unbounded` 允许模糊超出边界。

```kotlin
Text(
    text = "矩形边缘",
    modifier = Modifier.elegantBlur(
        radius = 12.dp,
        edgeTreatment = BlurEdgeTreatment.Rectangle,
    ),
)

Text(
    text = "无界边缘",
    modifier = Modifier.elegantBlur(
        radius = 12.dp,
        edgeTreatment = BlurEdgeTreatment.Unbounded,
    ),
)
```

## 属性

### Modifier.elegantBlur 属性

| 属性名 | 类型 | 说明 | 默认值 | 是否必须 |
| --- | --- | --- | --- | --- |
| `radius` | `Dp` | 模糊半径;非正数或非有限值让修饰符保持原样 | - | 是 |
| `edgeTreatment` | `BlurEdgeTreatment` | 模糊层在边缘处的处理方式 | `BlurEdgeTreatment.Rectangle` | 否 |

### BlurEdgeTreatment

| 值 | 说明 |
| --- | --- |
| `Rectangle` | 模糊层被裁剪到节点的矩形边界内 |
| `Unbounded` | 模糊可能超出节点的边界 |

## 进阶用法

### 清晰内容背后的模糊副本

该效果只模糊节点自身绘制的内容——在节点背后绘制的兄弟节点在任何受支持的平台上都不会被模糊。如需模糊背景,请在清晰的前景内容背后组合一份模糊的背景副本。

```kotlin
Box {
    Text(
        text = "背景模糊",
        modifier = Modifier.elegantBlur(radius = 12.dp),
    )
    Text(
        text = "清晰前景",
        modifier = Modifier.padding(4.dp),
    )
}
```
