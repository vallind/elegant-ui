# Skeleton

`ElegantSkeleton` 是加载时预览内容的微光占位表面。它适用于加载列表、个人资料头部、媒体区块,以及任何需要主题感知占位符、稍后替换为真实内容的区域。

<iframe id="demoIframe" style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; border: none;" src="../../compose/index.html?id=skeleton" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>

## 引入

```kotlin
import com.elegant.compose.ui.skeleton.ElegantSkeleton
import com.elegant.compose.ui.skeleton.ElegantSkeletonBlock
import com.elegant.compose.ui.skeleton.ElegantSkeletonColors
import com.elegant.compose.ui.skeleton.ElegantSkeletonDefaults
```

## 基本用法

单个 `ElegantSkeleton` 渲染带持续移动高光带的圆角、主题感知占位符。尺寸由调用方负责:骨架会填满修饰符提供的宽高。

```kotlin
ElegantSkeleton(
    modifier = Modifier
        .fillMaxWidth()
        .height(96.dp),
)
```

`ElegantSkeletonBlock` 是微光文字行的便捷列。末行通过 `lastLineWidthFraction` 缩短,让段落看起来像真实文案。

```kotlin
ElegantSkeletonBlock(
    columns = 3,
    modifier = Modifier.fillMaxWidth(),
)
```

## 组件状态

骨架没有 hover、press、focus 或 disabled 状态。它默认是装饰性的:`ElegantSkeleton` 会清除自身的语义,让屏幕阅读器在内容加载期间跳过占位符。颜色从 `ElegantTheme` 解析并在 Light 与 Dark 之间自动适配;高光色始终比基底表面更亮。

```kotlin
var loading by remember { mutableStateOf(true) }

if (loading) {
    ElegantSkeletonBlock(columns = 3, modifier = Modifier.fillMaxWidth())
} else {
    Column {
        Text("内容已就绪")
    }
}
```

## 属性

### ElegantSkeleton 属性

| 属性名 | 类型 | 说明 | 默认值 | 是否必须 |
| --- | --- | --- | --- | --- |
| `modifier` | `Modifier` | 仅应用一次到骨架根节点的修饰符 | `Modifier` | 否 |
| `shape` | `Shape` | 占位符的裁剪形状 | `RoundedCornerShape(ElegantRadius.sm)` | 否 |
| `colors` | `ElegantSkeletonColors` | 主题感知的基底色与高光色 | `ElegantSkeletonDefaults.colors()` | 否 |

### ElegantSkeletonBlock 属性

| 属性名 | 类型 | 说明 | 默认值 | 是否必须 |
| --- | --- | --- | --- | --- |
| `columns` | `Int` | 微光行数;至少强制为 1 | `3` | 否 |
| `modifier` | `Modifier` | 仅应用一次到列根节点的修饰符 | `Modifier` | 否 |
| `shape` | `Shape` | 每行共享的裁剪形状 | `RoundedCornerShape(ElegantRadius.xs)` | 否 |
| `spacing` | `Dp` | 行之间的垂直间距 | `ElegantSpacing.md` | 否 |
| `colors` | `ElegantSkeletonColors` | 主题感知的基底色与高光色 | `ElegantSkeletonDefaults.colors()` | 否 |
| `lastLineWidthFraction` | `Float` | 末行的宽度比例;NaN 回退为 0.6f 并限制在 0.2f..1f | `0.6f` | 否 |

### ElegantSkeletonDefaults

| 成员 | 类型 | 说明 |
| --- | --- | --- |
| `AnimationDurationMillis` | `Int` | 一次微光扫过的 1400ms 时长 |
| `colors()` | `ElegantSkeletonColors` | Light/Dark 主题感知的基底色与高光色 |

### ElegantSkeletonColors

`ElegantSkeletonColors` 包含常驻的 `baseColor` 与移动的 `highlightColor` 光带。应先调用 `ElegantSkeletonDefaults.colors()`,再仅针对产品明确需要的占位调色板使用 `copy(...)`。

## 进阶用法

### 个人资料卡片骨架

组合圆形头像占位符与微光行,在真实数据到达前预览个人资料卡片。

```kotlin
ElegantCard {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(ElegantSpacing.xl),
        horizontalArrangement = Arrangement.spacedBy(ElegantSpacing.lg),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        ElegantSkeleton(
            modifier = Modifier.size(48.dp),
            shape = CircleShape,
        )
        ElegantSkeletonBlock(
            columns = 2,
            modifier = Modifier.weight(1f),
        )
    }
}
```

### 自定义颜色

```kotlin
val placeholderColors = ElegantSkeletonDefaults.colors().copy(
    baseColor = Color(0xFFEDEEF1),
    highlightColor = Color(0xFFF1F1F3),
)

ElegantSkeleton(
    modifier = Modifier
        .fillMaxWidth()
        .height(96.dp),
    colors = placeholderColors,
)
```
