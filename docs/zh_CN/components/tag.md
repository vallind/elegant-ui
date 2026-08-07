# Tag

`ElegantTag` 是精致的分类与标签组件,提供四种视觉变体、三种光学尺寸以及可选的选中交互。它适用于筛选、分类、元数据与状态标记等场景:此时 Badge 太小,而 Button 又过重。

<iframe id="demoIframe" style="width: 100%; height: 460px; border: 1px solid var(--vp-c-divider); border-radius: 12px; display: block; background: var(--vp-c-bg-alt);" src="../../compose/index.html?id=tag" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>

## 引入

```kotlin
import com.elegant.compose.ui.tag.ElegantTag
import com.elegant.compose.ui.tag.ElegantTagColors
import com.elegant.compose.ui.tag.ElegantTagDefaults
import com.elegant.compose.ui.tag.ElegantTagSize
import com.elegant.compose.ui.tag.ElegantTagStyle
```

## 基本用法

不传 `onClick` 的 Tag 是非交互组件,按光学高度渲染,并保留内容自身的语义。

```kotlin
Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
    ElegantTag(style = ElegantTagStyle.Filled) {
        Text("稳定")
    }
    ElegantTag(style = ElegantTagStyle.Outlined) {
        Text("测试版")
    }
    ElegantTag(style = ElegantTagStyle.Plain) {
        Text("内部")
    }
}
```

## 风格与尺寸

四种变体覆盖分类层级:`Filled` 用强调容器占据主导,`Tinted` 在默认表面上保持平衡强调,`Outlined` 以边框标记次级分类,`Plain` 则安静呈现三级元数据。`Small`、`Medium`、`Large` 共享同一光学节奏。

```kotlin
ElegantTag(style = ElegantTagStyle.Tinted) {
    Text("设计")
}

ElegantTag(size = ElegantTagSize.Small) {
    Text("紧凑")
}
ElegantTag(size = ElegantTagSize.Large) {
    Text("突出")
}
```

## 组件状态

非交互 Tag 没有 hover、press、focus 或 disabled 状态。传入 `onClick` 后 Tag 变为可选控件:它会播报 `Role.Button` 与 `selected` 状态,在主题启用焦点环时显示焦点环,提供 hover 与 press 反馈,并保持 48dp 最小交互目标,同时药丸外形仍保持紧凑。

交互 Tag 的状态优先级:disabled、pressed、selected、focused 边框、hovered、resting。`selected` 是语义状态,可与交互视觉组合。

```kotlin
var filters by remember { mutableStateOf(setOf("设计")) }

ElegantTag(
    onClick = {
        filters = if ("设计" in filters) {
            filters - "设计"
        } else {
            filters + "设计"
        }
    },
    selected = "设计" in filters,
    style = ElegantTagStyle.Filled,
    leadingContent = {
        Box(Modifier.size(6.dp).background(Color.White, CircleShape))
    },
) {
    Text("设计")
}

ElegantTag(
    onClick = {},
    enabled = false,
) {
    Text("已禁用")
}
```

## 属性

### ElegantTag 属性

| 属性名 | 类型 | 说明 | 默认值 | 是否必须 |
| --- | --- | --- | --- | --- |
| `onClick` | `(() -> Unit)?` | 可选激活回调;null 保持非交互 | `null` | 否 |
| `modifier` | `Modifier` | 仅应用一次到 Tag 根节点的修饰符 | `Modifier` | 否 |
| `selected` | `Boolean` | 是否表达已选中的筛选或分类 | `false` | 否 |
| `enabled` | `Boolean` | 是否接受用户交互 | `true` | 否 |
| `style` | `ElegantTagStyle` | 视觉变体 | `ElegantTagStyle.Tinted` | 否 |
| `size` | `ElegantTagSize` | 光学尺寸预设 | `ElegantTagSize.Medium` | 否 |
| `shape` | `Shape` | 裁剪与描边形状 | `ElegantTagDefaults.shape()` | 否 |
| `colors` | `ElegantTagColors` | 主题感知的状态颜色 | `ElegantTagDefaults.colors(style)` | 否 |
| `leadingContent` | `@Composable (() -> Unit)?` | 标签前的内容,如状态圆点或图标 | `null` | 否 |
| `content` | `@Composable () -> Unit` | 标签文字或自定义内容 | - | 是 |

### ElegantTagStyle 可选值

| 值 | 行为 |
| --- | --- |
| `Filled` | 强调实色容器与反色内容,占据主导 |
| `Tinted` | 柔和强调容器,保持平衡强调 |
| `Outlined` | 透明容器与可见边框 |
| `Plain` | 无边框的安静容器 |

### ElegantTagSize 可选值

| 值 | 光学高度 |
| --- | --- |
| `Small` | 24dp |
| `Medium` | 28dp |
| `Large` | 32dp |

### ElegantTagDefaults

| 成员 | 类型 | 说明 |
| --- | --- | --- |
| `MinimumTouchHeight` | `Dp` | 可选 Tag 使用的 48dp 最小交互根高度 |
| `AnimationDurationMillis` | `Int` | 标准 160ms 状态过渡时长 |
| `PressAnimationDurationMillis` | `Int` | 即时 90ms 按压反馈时长 |
| `PressedScale` | `Float` | 克制的 0.97 按压缩放 |
| `shape()` | `Shape` | 默认全圆药丸形状 |
| `colors(style)` | `ElegantTagColors` | 所选风格的 Light/Dark 主题感知颜色 |

### ElegantTagColors

`ElegantTagColors` 包含默认、selected、hovered、pressed、disabled 与 focused 各状态的容器色、内容色、边框色与边框宽度。应先调用 `ElegantTagDefaults.colors(style)`,再仅针对产品明确需要的层级使用 `copy(...)`。

## 进阶用法

### 可筛选的过滤器 Chip

结合 `selected` 与显式风格切换,同时表达类别与选择状态。

```kotlin
ElegantTag(
    onClick = { onSelect(candidate) },
    selected = selected,
    style = if (selected) ElegantTagStyle.Filled else ElegantTagStyle.Outlined,
    leadingContent = if (selected) {
        { BadgeDot() }
    } else {
        null
    },
) {
    Text(candidate)
}
```

### 自定义强调

```kotlin
val baseColors = ElegantTagDefaults.colors(ElegantTagStyle.Outlined)

ElegantTag(
    style = ElegantTagStyle.Outlined,
    colors = baseColors.copy(
        selectedContainerColor = Color(0xFF6C4EFF),
        selectedContentColor = Color.White,
        selectedBorderColor = Color(0xFF6C4EFF),
    ),
    onClick = { /* 切换选择 */ },
    selected = selected,
) {
    Text("自定义")
}
```
