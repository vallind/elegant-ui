# Badge

`ElegantBadge` 是面向标签、在线状态与数量的紧凑跨平台状态系统。它提供五种语义色调、三种光学尺寸、可预测的溢出格式、可访问的状态点与计数 API，以及支持 RTL 的角落定位，同时不会把状态误装成可交互控件。

<iframe id="demoIframe" style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; border: none;" src="../../compose/index.html?id=badge" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>

## 引入

```kotlin
import com.elegant.compose.ui.badge.ElegantBadge
import com.elegant.compose.ui.badge.ElegantBadgeBox
import com.elegant.compose.ui.badge.ElegantBadgeColors
import com.elegant.compose.ui.badge.ElegantBadgeDefaults
import com.elegant.compose.ui.badge.ElegantBadgeDot
import com.elegant.compose.ui.badge.ElegantBadgePlacement
import com.elegant.compose.ui.badge.ElegantBadgeSize
import com.elegant.compose.ui.badge.ElegantBadgeStyle
import com.elegant.compose.ui.badge.ElegantCountBadge
```

## 基本用法

在现有控件上组合本地化计数。Badge 不会改变内容的测量尺寸或交互契约。

```kotlin
ElegantBadgeBox(
    badge = {
        ElegantCountBadge(
            count = unreadCount,
            contentDescription = "$unreadCount 条未读消息",
            size = ElegantBadgeSize.Small,
        )
    },
) {
    ElegantIconButton(
        onClick = onOpenInbox,
        contentDescription = "打开收件箱",
    ) {
        Icon(
            painter = painterResource(Res.drawable.inbox),
            contentDescription = null,
        )
    }
}
```

## 徽标模式

短标签使用 `ElegantBadge`，在线或状态提示使用 `ElegantBadgeDot`，需要数值溢出行为时使用 `ElegantCountBadge`。Badge 内容有意保持不可交互；应在外围组合操作，而不是在内部放置点击处理。

```kotlin
ElegantBadge(style = ElegantBadgeStyle.Neutral) {
    Text("Beta")
}

ElegantBadgeDot(
    contentDescription = "在线",
    style = ElegantBadgeStyle.Positive,
)

ElegantCountBadge(
    count = 120,
    maxCount = 99, // 显示 99+
    contentDescription = "超过 99 条提醒",
    style = ElegantBadgeStyle.Critical,
)
```

## 定位

`ElegantBadgeBox` 会把徽标中心对齐到逻辑角落。Start 与 End 在 RTL 下自动镜像。徽标可能绘制到布局边界之外，因此需要保留溢出时不要裁剪父容器。

```kotlin
ElegantBadgeBox(
    badge = {
        ElegantBadgeDot(
            contentDescription = "可联系",
            style = ElegantBadgeStyle.Positive,
        )
    },
    placement = ElegantBadgePlacement.BottomEnd,
) {
    ElegantAvatar(name = "Maya Chen")
}
```

## 组件状态

数量小于等于零时默认隐藏。设置 `showZero = true` 可保留可见的零值。负数会解析为 `0`，非正数 `maxCount` 会安全地按 `1` 处理。说明为 null 时保留子内容语义，空的 `contentDescription` 则让标签或计数内容保持纯装饰。

```kotlin
ElegantCountBadge(
    count = 0,
    showZero = true,
    contentDescription = "没有未读更新",
)

ElegantCountBadge(
    count = failedJobs,
    contentDescription = "$failedJobs 个失败任务",
    style = ElegantBadgeStyle.Critical,
)
```

## 属性

### ElegantBadge 属性

| 属性名 | 类型 | 说明 | 默认值 | 是否必须 |
| --- | --- | --- | --- | --- |
| `modifier` | `Modifier` | 仅应用一次到徽标容器的修饰符 | `Modifier` | 否 |
| `contentDescription` | `String?` | 本地化说明；null 保留内容语义，空值使内容保持纯装饰 | `null` | 否 |
| `style` | `ElegantBadgeStyle` | 语义视觉样式 | `ElegantBadgeStyle.Accent` | 否 |
| `size` | `ElegantBadgeSize` | 光学容器与排版预设 | `ElegantBadgeSize.Medium` | 否 |
| `shape` | `Shape` | 裁剪与轮廓形状 | `ElegantBadgeDefaults.shape()` | 否 |
| `colors` | `ElegantBadgeColors` | 主题感知的容器色、内容色与轮廓色 | `ElegantBadgeDefaults.colors(style)` | 否 |
| `borderWidth` | `Dp` | 光学轮廓宽度 | `ElegantBadgeDefaults.BorderWidth` | 否 |
| `content` | `@Composable () -> Unit` | 短标签、数字、图标或自定义徽标内容 | - | 是 |

### ElegantBadgeDot 属性

| 属性名 | 类型 | 说明 | 默认值 | 是否必须 |
| --- | --- | --- | --- | --- |
| `modifier` | `Modifier` | 仅应用一次到状态点的修饰符 | `Modifier` | 否 |
| `contentDescription` | `String?` | 本地化状态说明；null 或空白使状态点保持纯装饰 | `null` | 否 |
| `style` | `ElegantBadgeStyle` | 语义视觉样式 | `ElegantBadgeStyle.Accent` | 否 |
| `size` | `ElegantBadgeSize` | 光学状态点尺寸预设 | `ElegantBadgeSize.Medium` | 否 |
| `shape` | `Shape` | 裁剪与轮廓形状 | `ElegantBadgeDefaults.shape()` | 否 |
| `colors` | `ElegantBadgeColors` | 主题感知的指示器与轮廓颜色 | `ElegantBadgeDefaults.colors(style)` | 否 |
| `borderWidth` | `Dp` | 光学轮廓宽度 | `ElegantBadgeDefaults.BorderWidth` | 否 |

### ElegantCountBadge 属性

| 属性名 | 类型 | 说明 | 默认值 | 是否必须 |
| --- | --- | --- | --- | --- |
| `count` | `Int` | 当前数值 | - | 是 |
| `modifier` | `Modifier` | 仅应用一次到可见徽标的修饰符 | `Modifier` | 否 |
| `maxCount` | `Int` | 不添加 `+` 后缀时显示的最大值 | `ElegantBadgeDefaults.DefaultMaxCount` | 否 |
| `showZero` | `Boolean` | 是否将零和负数保留为可见的 `0` | `false` | 否 |
| `contentDescription` | `String?` | 本地化语义说明 | `ElegantBadgeDefaults.countLabel(count, maxCount)` | 否 |
| `style` | `ElegantBadgeStyle` | 语义视觉样式 | `ElegantBadgeStyle.Accent` | 否 |
| `size` | `ElegantBadgeSize` | 光学容器与排版预设 | `ElegantBadgeSize.Medium` | 否 |
| `shape` | `Shape` | 裁剪与轮廓形状 | `ElegantBadgeDefaults.shape()` | 否 |
| `colors` | `ElegantBadgeColors` | 主题感知的容器色、内容色与轮廓色 | `ElegantBadgeDefaults.colors(style)` | 否 |
| `borderWidth` | `Dp` | 光学轮廓宽度 | `ElegantBadgeDefaults.BorderWidth` | 否 |

### ElegantBadgeBox 属性

| 属性名 | 类型 | 说明 | 默认值 | 是否必须 |
| --- | --- | --- | --- | --- |
| `badge` | `@Composable () -> Unit` | 显示在内容上方的状态、计数或自定义徽标 | - | 是 |
| `modifier` | `Modifier` | 仅应用一次到内容尺寸布局根节点的修饰符 | `Modifier` | 否 |
| `placement` | `ElegantBadgePlacement` | 用于锚定徽标的逻辑角落 | `ElegantBadgePlacement.TopEnd` | 否 |
| `content` | `@Composable () -> Unit` | 接收叠加徽标的内容 | - | 是 |

### ElegantBadgeStyle 可选值

| 值 | 含义 |
| --- | --- |
| `Neutral` | 不表示操作或严重性的安静元数据 |
| `Accent` | 需要强调的品牌色状态或计数 |
| `Positive` | 成功、可用或健康状态 |
| `Warning` | 需要关注的状态 |
| `Critical` | 紧急、失败、破坏性或严重状态 |

### ElegantBadgeSize 可选值

| 值 | 标签最小尺寸 | 状态点尺寸 | 排版 |
| --- | --- | --- | --- |
| `Small` | `18.dp` | `6.dp` | `labelSmall` |
| `Medium` | `22.dp` | `8.dp` | `labelSmall` |
| `Large` | `26.dp` | `10.dp` | `labelMedium` |

紧凑的 2dp 递增是对标准 4dp 网格的有意光学例外。

### ElegantBadgePlacement 可选值

`TopStart`、`TopEnd`、`BottomStart` 与 `BottomEnd` 使用逻辑边缘，因此会在 RTL 下自动镜像。

### ElegantBadgeDefaults

| 成员 | 类型 | 说明 |
| --- | --- | --- |
| `BorderWidth` | `Dp` | 默认 1dp 光学轮廓 |
| `DefaultMaxCount` | `Int` | 默认溢出阈值 99 |
| `countLabel(count, maxCount)` | `String` | 纠正无效输入并返回紧凑计数文本 |
| `shape()` | `Shape` | 返回完全圆润的徽标形状 |
| `colors(style)` | `ElegantBadgeColors` | 返回语义样式对应的 Light/Dark 主题感知颜色 |

### ElegantBadgeColors

`ElegantBadgeColors` 包含 `containerColor`、`contentColor` 与 `borderColor`。应先调用 `ElegantBadgeDefaults.colors(style)`，再通过 `copy(...)` 进行产品明确需要的定制。

## 进阶用法

当状态属于产品专用词汇时，可使用自定义颜色模型，同时保留 Badge 的几何与语义契约。

```kotlin
val baseColors = ElegantBadgeDefaults.colors(ElegantBadgeStyle.Accent)

ElegantBadge(
    contentDescription = "实验性功能",
    colors = baseColors.copy(
        containerColor = Color(0xFF0F766E),
        contentColor = Color.White,
        borderColor = Color(0xFF5EEAD4),
    ),
) {
    Text("Experimental")
}
```
