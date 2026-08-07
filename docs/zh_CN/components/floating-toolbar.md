# FloatingToolbar

`ElegantFloatingToolbar` 是悬浮在内容上方的浮动操作条。带中等级别投影、两端全圆角的高起药丸形表面承载一行图标操作;行按内容自适应宽度,高度从 48dp 最小值起自适应增长。组件本身不做任何定位——请把它包进 `Box`,悬浮在选区、段落或编辑器之上。

<iframe id="demoIframe" style="width: 100%; height: 460px; border: 1px solid var(--vp-c-divider); border-radius: 12px; display: block; background: var(--vp-c-bg-alt);" src="../../compose/index.html?id=floating-toolbar" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>

## 引入

```kotlin
import com.elegant.compose.ui.floatingtoolbar.ElegantFloatingToolbar
import com.elegant.compose.ui.floatingtoolbar.ElegantFloatingToolbarColors
import com.elegant.compose.ui.floatingtoolbar.ElegantFloatingToolbarDefaults
```

## 基本用法

为每个操作提供简短、面向动作的 `contentDescription`,图标本身保持纯装饰。相邻操作之间使用 `ElegantFloatingToolbarDefaults.ItemGap` 作为推荐间距。

```kotlin
ElegantFloatingToolbar {
    ElegantIconButton(
        onClick = { /* 编辑 */ },
        contentDescription = "编辑",
    ) {
        Icon(Icons.Default.Edit, contentDescription = null)
    }
    ElegantIconButton(
        onClick = { /* 删除 */ },
        contentDescription = "删除",
    ) {
        Icon(Icons.Default.Delete, contentDescription = null)
    }
}
```

## 组件状态

FloatingToolbar 是非交互容器:它自身没有 pressed、focused、selected、disabled 或 loading 状态,也不贡献任何语义。条内的操作自行负责交互与状态,药丸通过 `LocalContentColor` 提供它们的内容色。

```kotlin
ElegantFloatingToolbar {
    ElegantIconButton(
        onClick = { /* 重试 */ },
        contentDescription = "重试",
    ) {
        Icon(Icons.Default.Refresh, contentDescription = null)
    }
    ElegantIconButton(
        onClick = {},
        contentDescription = "删除项目",
        enabled = false,
    ) {
        Icon(Icons.Default.Delete, contentDescription = null)
    }
}
```

## 属性

### ElegantFloatingToolbar 属性

| 属性名 | 类型 | 说明 | 默认值 | 是否必须 |
| --- | --- | --- | --- | --- |
| `modifier` | `Modifier` | 仅应用一次到药丸根节点的修饰符 | `Modifier` | 否 |
| `colors` | `ElegantFloatingToolbarColors` | 主题感知的药丸与内容颜色 | `ElegantFloatingToolbarDefaults.colors()` | 否 |
| `content` | `@Composable RowScope.() -> Unit` | 一行操作;操作之间的间距由调用方负责 | - | 是 |

### ElegantFloatingToolbarDefaults

| 成员 | 类型 | 说明 |
| --- | --- | --- |
| `Height` | `Dp` | 最小 48dp 药丸高度 |
| `HorizontalPadding` | `Dp` | 药丸每侧 4dp 横向内边距 |
| `ItemGap` | `Dp` | 相邻操作之间的推荐 4dp 间距 |
| `colors()` | `ElegantFloatingToolbarColors` | 返回 Light/Dark 主题感知颜色 |

### ElegantFloatingToolbarColors

`ElegantFloatingToolbarColors` 包含 `containerColor`、`contentColor` 与 `dividerColor`。应先调用 `ElegantFloatingToolbarDefaults.colors()`,再仅针对产品明确需要的层级使用 `copy(...)`。

## 进阶用法

### 悬浮于内容之上

操作条按内容包裹且不做任何定位;把它包进 `Box`,用 `Modifier.align` 悬浮在表面或段落之上。

```kotlin
Box {
    Text("选中的段落文本")
    ElegantFloatingToolbar(
        modifier = Modifier.align(Alignment.TopCenter),
    ) {
        ElegantIconButton(
            onClick = { /* 复制 */ },
            contentDescription = "复制",
        ) {
            Icon(Icons.Default.Copy, contentDescription = null)
        }
    }
}
```

### 分隔操作

使用主题感知的 `dividerColor` 在操作组之间绘制可选分隔线。

```kotlin
val toolbarColors = ElegantFloatingToolbarDefaults.colors()

ElegantFloatingToolbar {
    ElegantIconButton(
        onClick = { /* 剪切 */ },
        contentDescription = "剪切",
    ) {
        Icon(Icons.Default.ContentCut, contentDescription = null)
    }
    ElegantDivider(
        modifier = Modifier
            .height(24.dp)
            .padding(horizontal = 4.dp),
        orientation = ElegantDividerOrientation.Vertical,
        colors = ElegantDividerDefaults.colors(ElegantDividerEmphasis.Subtle).copy(
            lineColor = toolbarColors.dividerColor,
        ),
    )
    ElegantIconButton(
        onClick = { /* 粘贴 */ },
        contentDescription = "粘贴",
    ) {
        Icon(Icons.Default.ContentPaste, contentDescription = null)
    }
}
```

### 自定义颜色

```kotlin
val baseColors = ElegantFloatingToolbarDefaults.colors()

ElegantFloatingToolbar(
    colors = baseColors.copy(
        containerColor = Color(0xFF17181A),
        contentColor = Color(0xFFF6F7F9),
        dividerColor = Color(0xFF343740),
    ),
) {
    ElegantIconButton(
        onClick = { /* 编辑 */ },
        contentDescription = "编辑",
    ) {
        Icon(Icons.Default.Edit, contentDescription = null)
    }
}
```
