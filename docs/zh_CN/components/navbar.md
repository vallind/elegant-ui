# Navbar

`ElegantNavbar` 是顶部应用栏容器,承载可选导航图标、标题与尾部操作。它作为非交互的界面框架置于屏幕顶部,子内容完全保留自身的语义、文本样式与交互能力。

<iframe id="demoIframe" style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; border: none;" src="../../compose/index.html?id=navbar" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>

## 引入

```kotlin
import com.elegant.compose.ui.navbar.ElegantNavbar
import com.elegant.compose.ui.navbar.ElegantNavbarColors
import com.elegant.compose.ui.navbar.ElegantNavbarDefaults
```

## 基本用法

导航栏以 56dp 高度渲染,两侧有 16dp 水平内边距与 1dp 底部描边。可选导航图标位于逻辑起始侧(在 RTL 下自动镜像),与标题之间留有 4dp 间距;标题占据剩余宽度,操作内容排在末尾。导航栏通过 `LocalContentColor` 提供内容颜色,但不应用任何文本样式,因此标题应自行设置样式,例如 `ElegantTheme.typography.titleMedium`。

```kotlin
ElegantNavbar(
    navigationIcon = {
        ElegantIconButton(
            onClick = { /* 打开导航抽屉 */ },
            contentDescription = "打开导航",
        ) {
            Icon(Icons.Default.Menu, contentDescription = null)
        }
    },
    title = {
        Text(
            text = "首页",
            style = ElegantTheme.typography.titleMedium,
        )
    },
    actions = {
        ElegantIconButton(
            onClick = { /* 分享当前页面 */ },
            contentDescription = "分享",
        ) {
            Icon(Icons.Default.Share, contentDescription = null)
        }
    },
)
```

## 组件状态

导航栏本身是非交互组件:它不定义角色、不拥有焦点,也没有 hover、press 或 disabled 视觉,并且从不合并或清除内容的语义。状态行为完全属于子组件,因此栏内被禁用的操作会播报自身状态,而导航栏仍以相同的框架渲染。

```kotlin
ElegantNavbar(
    title = {
        Text(
            text = "草稿",
            style = ElegantTheme.typography.titleMedium,
        )
    },
    actions = {
        ElegantIconButton(
            onClick = { /* 发布草稿 */ },
            contentDescription = "发布",
            enabled = false,
        ) {
            Icon(Icons.Default.Check, contentDescription = null)
        }
    },
)
```

## 属性

### ElegantNavbar 属性

| 属性名 | 类型 | 说明 | 默认值 | 是否必须 |
| --- | --- | --- | --- | --- |
| `navigationIcon` | `(@Composable () -> Unit)?` | 可选,位于标题之前的起始侧内容 | `null` | 否 |
| `modifier` | `Modifier` | 仅应用一次到导航栏根节点的修饰符 | `Modifier` | 否 |
| `title` | `@Composable () -> Unit` | 标题内容;文本样式由调用方负责 | - | 是 |
| `actions` | `@Composable RowScope.() -> Unit` | 在行作用域中布局的尾部操作内容 | `{}` | 否 |
| `colors` | `ElegantNavbarColors` | 主题感知的容器、内容与边框颜色 | `ElegantNavbarDefaults.colors()` | 否 |

### ElegantNavbarDefaults

| 成员 | 类型 | 说明 |
| --- | --- | --- |
| `Height` | `Dp` | 标准 56dp 栏高度 |
| `HorizontalPadding` | `Dp` | 栏两侧 16dp 水平留白 |
| `ItemGap` | `Dp` | 导航图标与标题之间的 4dp 间距 |
| `colors()` | `ElegantNavbarColors` | 主题感知的 Light/Dark 颜色 |

### ElegantNavbarColors

`ElegantNavbarColors` 包含容器色(栏背景)、内容色(通过 `LocalContentColor` 提供给标题)与边框色(1dp 底部描边)。应先调用 `ElegantNavbarDefaults.colors()`,再仅针对产品明确需要的层级使用 `copy(...)`。

## 进阶用法

### 文档页组合

将 `ElegantNavbar` 与 `ElegantDivider` 和正文组合,构建文档页面。导航图标在 RTL 下会自动镜像到逻辑起始侧。

```kotlin
Column {
    ElegantNavbar(
        navigationIcon = {
            ElegantIconButton(
                onClick = { /* 返回上一页 */ },
                contentDescription = "返回",
            ) {
                Icon(Icons.Default.ArrowBack, contentDescription = null)
            }
        },
        title = {
            Text(
                text = "设置",
                style = ElegantTheme.typography.titleMedium,
            )
        },
        actions = {
            ElegantIconButton(
                onClick = { /* 打开更多选项 */ },
                contentDescription = "更多选项",
            ) {
                Icon(Icons.Default.MoreVert, contentDescription = null)
            }
        },
    )
    ElegantDivider()
    Text(
        text = "一个将导航栏置于分隔线与正文之上的文档页面。",
        modifier = Modifier.padding(16.dp),
        style = ElegantTheme.typography.bodyMedium,
    )
}
```

### 自定义颜色

```kotlin
ElegantNavbar(
    title = {
        Text(
            text = "品牌",
            style = ElegantTheme.typography.titleMedium,
        )
    },
    colors = ElegantNavbarDefaults.colors().copy(
        containerColor = Color(0xFF6C4EFF),
        contentColor = Color.White,
        borderColor = Color.Transparent,
    ),
)
```
