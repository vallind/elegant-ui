# Divider

`ElegantDivider` 是用于组织相关内容的精致非交互边界，不会引入多余视觉重量。它支持横向与纵向、实线与虚线、两种语义强调层级以及主题感知颜色。`ElegantLabeledDivider` 进一步提供响应式、RTL 安全的逻辑标签位置。

<iframe id="demoIframe" style="width: 100%; height: 460px; border: 1px solid var(--vp-c-divider); border-radius: 12px; display: block; background: var(--vp-c-bg-alt);" src="../../compose/index.html?id=divider" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>

## 引入

```kotlin
import com.elegant.compose.ui.divider.ElegantDivider
import com.elegant.compose.ui.divider.ElegantDividerColors
import com.elegant.compose.ui.divider.ElegantDividerDefaults
import com.elegant.compose.ui.divider.ElegantDividerEmphasis
import com.elegant.compose.ui.divider.ElegantDividerLabelPosition
import com.elegant.compose.ui.divider.ElegantDividerOrientation
import com.elegant.compose.ui.divider.ElegantDividerStyle
import com.elegant.compose.ui.divider.ElegantLabeledDivider
```

## 基本用法

横向低强调 Divider 会填满可用宽度，并默认保持纯装饰语义。

```kotlin
Column {
    Text("账户")
    ElegantDivider()
    Text("安全")
}
```

## 方向与线条

在纵向堆叠区域之间使用 `Horizontal`。`Vertical` 会填满有界的父布局高度，因此应为所在 Row 或 Divider 提供明确高度。`Dashed` 用于表达更安静的临时或次级边界，同时不改变测量尺寸。

```kotlin
Row(
    modifier = Modifier.height(72.dp),
    verticalAlignment = Alignment.CenterVertically,
) {
    Text("Android", modifier = Modifier.weight(1f))
    ElegantDivider(
        modifier = Modifier.fillMaxHeight(),
        orientation = ElegantDividerOrientation.Vertical,
        emphasis = ElegantDividerEmphasis.Strong,
    )
    Text("Web", modifier = Modifier.weight(1f))
}

ElegantDivider(style = ElegantDividerStyle.Dashed)
```

## 带标签 Divider

`ElegantLabeledDivider` 有意只支持横向布局。`Start` 与 `End` 是逻辑位置，会在 RTL 中自动镜像。组件负责线条与标签间距、内容颜色和默认标签排版，插槽负责其内部绘制。

```kotlin
ElegantLabeledDivider(
    labelPosition = ElegantDividerLabelPosition.Center,
    emphasis = ElegantDividerEmphasis.Strong,
) {
    Text("最近活动")
}
```

## 组件状态

Divider 是非交互组件，因此没有 pressed、focused、selected、disabled 或 loading 状态。`Subtle` 用于分隔相关内容，`Strong` 用于分隔不同区域。普通 Divider 默认不进入语义树，除非提供本地化 `contentDescription`。

对于带标签 Divider，`contentDescription = null` 会保留标签插槽自身语义，非空白值会用一个本地化说明替换后代语义，空字符串则使整个 Divider 保持纯装饰。

```kotlin
ElegantDivider(
    contentDescription = "下一个区域",
    emphasis = ElegantDividerEmphasis.Strong,
)

ElegantLabeledDivider(contentDescription = "") {
    Text("装饰性元素")
}
```

非正数或非有限厚度会回退到 `ElegantDividerDefaults.Thickness`。负数或非有限标签间距会回退到 `ElegantDividerDefaults.LabelGap`。在窄约束下，带标签 Divider 会先收缩线段，再考虑裁剪自定义标签内容。

## 属性

### ElegantDivider 属性

| 属性名 | 类型 | 说明 | 默认值 | 是否必须 |
| --- | --- | --- | --- | --- |
| `modifier` | `Modifier` | 仅应用一次到 Divider 根节点的修饰符 | `Modifier` | 否 |
| `contentDescription` | `String?` | 可选的本地化边界说明 | `null` | 否 |
| `orientation` | `ElegantDividerOrientation` | 横向或纵向布局方向 | `ElegantDividerOrientation.Horizontal` | 否 |
| `style` | `ElegantDividerStyle` | 连续或分段线条处理 | `ElegantDividerStyle.Solid` | 否 |
| `emphasis` | `ElegantDividerEmphasis` | 分隔边界的语义强调层级 | `ElegantDividerEmphasis.Subtle` | 否 |
| `colors` | `ElegantDividerColors` | 主题感知的线条色与标签色 | `ElegantDividerDefaults.colors(emphasis)` | 否 |
| `thickness` | `Dp` | 可见线条厚度 | `ElegantDividerDefaults.Thickness` | 否 |

### ElegantLabeledDivider 属性

| 属性名 | 类型 | 说明 | 默认值 | 是否必须 |
| --- | --- | --- | --- | --- |
| `modifier` | `Modifier` | 仅应用一次到带标签 Divider 根节点的修饰符 | `Modifier` | 否 |
| `contentDescription` | `String?` | 可选本地化语义覆盖；null 保留内容语义 | `null` | 否 |
| `labelPosition` | `ElegantDividerLabelPosition` | 标签在线段之间的逻辑位置 | `ElegantDividerLabelPosition.Center` | 否 |
| `style` | `ElegantDividerStyle` | 连续或分段线条处理 | `ElegantDividerStyle.Solid` | 否 |
| `emphasis` | `ElegantDividerEmphasis` | 分隔边界的语义强调层级 | `ElegantDividerEmphasis.Subtle` | 否 |
| `colors` | `ElegantDividerColors` | 主题感知的线条色与标签色 | `ElegantDividerDefaults.colors(emphasis)` | 否 |
| `thickness` | `Dp` | 可见线条厚度 | `ElegantDividerDefaults.Thickness` | 否 |
| `labelGap` | `Dp` | 标签两侧的留白 | `ElegantDividerDefaults.LabelGap` | 否 |
| `content` | `@Composable () -> Unit` | 标签文字、图标或自定义内容 | - | 是 |

### ElegantDividerOrientation 可选值

| 值 | 行为 |
| --- | --- |
| `Horizontal` | 填满可用宽度并分隔纵向堆叠内容 |
| `Vertical` | 填满有界高度并分隔横向排列内容 |

### ElegantDividerStyle 可选值

| 值 | 行为 |
| --- | --- |
| `Solid` | 绘制一条连续边界 |
| `Dashed` | 绘制固定 8dp 线段与 4dp 间距 |

### ElegantDividerEmphasis 可选值

| 值 | 线条角色 | 标签角色 |
| --- | --- | --- |
| `Subtle` | `borderDefault` | `textSecondary` |
| `Strong` | `borderStrong` | `textPrimary` |

### ElegantDividerLabelPosition 可选值

`Start`、`Center` 与 `End` 使用逻辑布局方向。Center 使用相等线段权重，start 与 end 会移除标签外侧的线段。

### ElegantDividerDefaults

| 成员 | 类型 | 说明 |
| --- | --- | --- |
| `Thickness` | `Dp` | 默认 1dp 发丝线厚度 |
| `LabelGap` | `Dp` | 标签内容每侧默认 12dp 间距 |
| `DashLength` | `Dp` | 固定 8dp 虚线段长度 |
| `DashGap` | `Dp` | 虚线段之间固定 4dp 间距 |
| `colors(emphasis)` | `ElegantDividerColors` | 返回所选强调层级的 Light/Dark 主题感知颜色 |

### ElegantDividerColors

`ElegantDividerColors` 包含 `lineColor` 与 `contentColor`。应先调用 `ElegantDividerDefaults.colors(emphasis)`，再仅针对产品明确需要的层级使用 `copy(...)`。

## 进阶用法

### 缩进内容边界

使用逻辑 padding 创建会在 RTL 中自动镜像的缩进。

```kotlin
ElegantDivider(
    modifier = Modifier.padding(start = 52.dp),
    contentDescription = "下一位团队成员",
)
```

### 自定义区域色调

```kotlin
val baseColors = ElegantDividerDefaults.colors(ElegantDividerEmphasis.Strong)

ElegantLabeledDivider(
    colors = baseColors.copy(
        lineColor = Color(0xFF6C4EFF),
        contentColor = Color(0xFF5840D6),
    ),
) {
    Text("发布候选版本")
}
```
