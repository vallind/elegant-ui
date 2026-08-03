# Toolbar

`ElegantToolbar` 是嵌入调用方布局的内联操作条。扁平的、无边框表面以固定 48dp 高度填满容器宽度,承载一行图标操作。与 `ElegantFloatingToolbar`(带中等级别投影、两端全圆角的高起浮动药丸)不同,Toolbar 本身不加投影、不加圆角、也不做任何定位:它适合放在卡片、列表或编辑器的顶部或底部,边框与分隔线由调用方自行决定。

<iframe id="demoIframe" style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; border: none;" src="../../compose/index.html?id=toolbar" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>

## 引入

```kotlin
import com.elegant.compose.ui.toolbar.ElegantToolbar
import com.elegant.compose.ui.toolbar.ElegantToolbarColors
import com.elegant.compose.ui.toolbar.ElegantToolbarDefaults
```

## 基本用法

为每个操作提供简短、面向动作的 `contentDescription`,图标本身保持纯装饰。相邻操作之间使用 `ElegantToolbarDefaults.ItemGap` 作为推荐间距。

```kotlin
ElegantToolbar {
    ElegantIconButton(
        onClick = { /* 加粗 */ },
        contentDescription = "加粗",
    ) {
        Icon(Icons.Default.FormatBold, contentDescription = null)
    }
    ElegantIconButton(
        onClick = { /* 斜体 */ },
        contentDescription = "斜体",
    ) {
        Icon(Icons.Default.FormatItalic, contentDescription = null)
    }
    ElegantIconButton(
        onClick = { /* 下划线 */ },
        contentDescription = "下划线",
    ) {
        Icon(Icons.Default.FormatUnderlined, contentDescription = null)
    }
}
```

## 组件状态

Toolbar 是非交互容器:它自身没有 pressed、focused、selected、disabled 或 loading 状态,也不贡献任何语义。条内的操作自行负责交互与状态,操作条通过 `LocalContentColor` 提供它们的内容色。

```kotlin
ElegantToolbar {
    ElegantIconButton(
        onClick = { /* 左对齐 */ },
        contentDescription = "左对齐",
    ) {
        Icon(Icons.Default.FormatAlignLeft, contentDescription = null)
    }
    ElegantIconButton(
        onClick = { /* 居中对齐 */ },
        contentDescription = "居中对齐",
    ) {
        Icon(Icons.Default.FormatAlignCenter, contentDescription = null)
    }
    ElegantIconButton(
        onClick = {},
        contentDescription = "右对齐",
        enabled = false,
    ) {
        Icon(Icons.Default.FormatAlignRight, contentDescription = null)
    }
}
```

## 属性

### ElegantToolbar 属性

| 属性名 | 类型 | 说明 | 默认值 | 是否必须 |
| --- | --- | --- | --- | --- |
| `modifier` | `Modifier` | 仅应用一次到操作条根节点的修饰符 | `Modifier` | 否 |
| `colors` | `ElegantToolbarColors` | 主题感知的操作条与内容颜色 | `ElegantToolbarDefaults.colors()` | 否 |
| `content` | `@Composable RowScope.() -> Unit` | 一行操作;操作之间的间距由调用方负责 | - | 是 |

### ElegantToolbarDefaults

| 成员 | 类型 | 说明 |
| --- | --- | --- |
| `Height` | `Dp` | 固定 48dp 操作条高度 |
| `HorizontalPadding` | `Dp` | 操作条每侧 4dp 横向内边距 |
| `ItemGap` | `Dp` | 相邻操作之间的推荐 4dp 间距 |
| `colors()` | `ElegantToolbarColors` | 返回 Light/Dark 主题感知颜色 |

### ElegantToolbarColors

`ElegantToolbarColors` 包含 `containerColor`、`contentColor` 与 `dividerColor`。应先调用 `ElegantToolbarDefaults.colors()`,再仅针对产品明确需要的层级使用 `copy(...)`。

## 进阶用法

### 内联于编辑器中

操作条填满容器宽度,因此可以自然地嵌入卡片或编辑器的其他内容之间。

```kotlin
Column {
    Text("选中的段落文本")
    ElegantToolbar {
        ElegantIconButton(
            onClick = { /* 复制 */ },
            contentDescription = "复制",
        ) {
            Icon(Icons.Default.Copy, contentDescription = null)
        }
        ElegantIconButton(
            onClick = { /* 粘贴 */ },
            contentDescription = "粘贴",
        ) {
            Icon(Icons.Default.ContentPaste, contentDescription = null)
        }
    }
}
```

### 分隔操作

使用主题感知的 `dividerColor` 在操作组之间绘制可选分隔线。

```kotlin
val toolbarColors = ElegantToolbarDefaults.colors()

ElegantToolbar {
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
val baseColors = ElegantToolbarDefaults.colors()

ElegantToolbar(
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
