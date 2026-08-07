# Scaffold

`ElegantScaffold` 构建屏幕外壳:顶部与底部栏固定在边缘,浮动操作按钮与 Snackbar 宿主悬浮其上。它通过 `onSizeChanged` 测量栏高度,并通过 `PaddingValues` 将计算出的内边距交给内容槽位,保证可滚动内容不会被界面框架遮挡。

<iframe id="demoIframe" style="width: 100%; height: 460px; border: 1px solid var(--vp-c-divider); border-radius: 12px; display: block; background: var(--vp-c-bg-alt);" src="../../compose/index.html?id=scaffold" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>

## 引入

```kotlin
import com.elegant.compose.ui.scaffold.ElegantScaffold
import com.elegant.compose.ui.scaffold.ElegantScaffoldColors
import com.elegant.compose.ui.scaffold.ElegantScaffoldDefaults
```

## 基本用法

顶部栏固定在顶部边缘,底部栏固定在底部边缘;两者均被自动测量,因此栏高度变化会直接传导到内容内边距,无需调用方自行记录。内容槽位填充栏下方的区域,并接收一个 `PaddingValues`,其顶部等于测得的顶部栏高度,底部等于测得的底部栏高度。

```kotlin
ElegantScaffold(
    topBar = {
        ElegantNavbar(
            title = {
                Text(
                    text = "首页",
                    style = ElegantTheme.typography.titleMedium,
                )
            },
        )
    },
) { innerPadding ->
    Column(
        modifier = Modifier.padding(innerPadding),
    ) {
        Text(
            text = "内容按测得的顶部栏高度缩进。",
            modifier = Modifier.padding(ElegantSpacing.xl),
            style = ElegantTheme.typography.bodyMedium,
        )
    }
}
```

## 组件状态

每个槽位都是可选的:省略某条栏,对应的内边距即为零;没有任何栏时,内容全幅铺满。浮动操作按钮悬浮于底部栏上方、位于底部末端;Snackbar 宿主悬浮于底部栏上方,留有 8dp 间距。Scaffold 本身不定义角色、不拥有焦点,也从不合并或清除内容的语义,交互子组件保留自身的无障碍契约。

```kotlin
ElegantScaffold(
    floatingActionButton = {
        ElegantFloatingActionButton(onClick = { /* 编写消息 */ }) {
            Icon(Icons.Default.Edit, contentDescription = null)
        }
    },
) { innerPadding ->
    Column(
        modifier = Modifier.padding(innerPadding),
    ) {
        Text(
            text = "没有栏,没有内边距:内容铺满画布。",
            modifier = Modifier.padding(ElegantSpacing.xl),
            style = ElegantTheme.typography.bodyMedium,
        )
    }
}
```

## 属性

### ElegantScaffold 属性

| 属性名 | 类型 | 说明 | 默认值 | 是否必须 |
| --- | --- | --- | --- | --- |
| `modifier` | `Modifier` | 仅应用一次到 Scaffold 根节点的修饰符 | `Modifier` | 否 |
| `topBar` | `(@Composable () -> Unit)?` | 固定在顶部边缘、用于计算内容顶部内边距的内容 | `null` | 否 |
| `bottomBar` | `(@Composable () -> Unit)?` | 固定在底部边缘、用于计算内容底部内边距的内容 | `null` | 否 |
| `floatingActionButton` | `(@Composable () -> Unit)?` | 悬浮于底部栏上方、位于底部末端的内容 | `null` | 否 |
| `snackbarHost` | `(@Composable () -> Unit)?` | 悬浮于底部栏上方、水平居中的内容 | `null` | 否 |
| `colors` | `ElegantScaffoldColors` | 主题感知的背景与内容颜色 | `ElegantScaffoldDefaults.colors()` | 否 |
| `content` | `@Composable (PaddingValues) -> Unit` | 主要内容;接收由测得的栏高度产生的内边距 | - | 是 |

### ElegantScaffoldDefaults

| 成员 | 类型 | 说明 |
| --- | --- | --- |
| `FloatingActionButtonMargin` | `Dp` | 浮动操作按钮与屏幕边缘及底部栏之间的 16dp 间距 |
| `SnackbarHostMargin` | `Dp` | Snackbar 宿主与底部栏之间的 8dp 间距 |
| `colors()` | `ElegantScaffoldColors` | 主题感知的 Light/Dark 颜色 |

### ElegantScaffoldColors

`ElegantScaffoldColors` 包含背景色(绘制在每一层之下的画布)与内容色(通过 `LocalContentColor` 提供给内容槽位)。应先调用 `ElegantScaffoldDefaults.colors()`,再仅针对产品明确需要的层级使用 `copy(...)`。

## 进阶用法

### 完整屏幕外壳

将导航栏、底部导航栏、浮动操作按钮与 Snackbar 宿主组合到同一屏幕中。Snackbar 宿主绘制在浮动操作按钮之下,`showSnackbar` 会挂起直至消息关闭,后续工作因此可以串联在反馈之后。

```kotlin
val snackbarHostState = remember { ElegantSnackbarHostState() }
val scope = rememberCoroutineScope()

ElegantScaffold(
    topBar = {
        ElegantNavbar(
            title = {
                Text(
                    text = "资料库",
                    style = ElegantTheme.typography.titleMedium,
                )
            },
        )
    },
    bottomBar = {
        ElegantNavigationBar(
            selectedIndex = 0,
            onSelect = { /* 切换目的地 */ },
            items = listOf(
                ElegantNavigationBarItem("首页"),
                ElegantNavigationBarItem("资料库"),
                ElegantNavigationBarItem("设置"),
            ),
        )
    },
    floatingActionButton = {
        ElegantFloatingActionButton(
            onClick = {
                scope.launch {
                    snackbarHostState.showSnackbar("消息已起草")
                }
            },
        ) {
            Icon(Icons.Default.Edit, contentDescription = null)
        }
    },
    snackbarHost = {
        ElegantSnackbarHost(hostState = snackbarHostState)
    },
) { innerPadding ->
    Column(
        modifier = Modifier.padding(innerPadding),
    ) {
        Text(
            text = "导航栏、可滚动内容、底部导航栏、FAB 与 Snackbar 共享同一外壳。",
            modifier = Modifier.padding(ElegantSpacing.xl),
            style = ElegantTheme.typography.bodyMedium,
        )
    }
}
```
