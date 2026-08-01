# Snackbar

`ElegantSnackbar` 是用于轻量反馈的临时消息表面,短暂出现后自动消失。`ElegantSnackbarHostState` 与 `ElegantSnackbarHost` 配合使用,负责消息的滑入滑出动画、按预设时长自动关闭,并挂起调用方协程直到消息被关闭。

<iframe id="demoIframe" style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; border: none;" src="../../compose/index.html?id=snackbar" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>

## 引入

```kotlin
import com.elegant.compose.ui.snackbar.ElegantSnackbar
import com.elegant.compose.ui.snackbar.ElegantSnackbarColors
import com.elegant.compose.ui.snackbar.ElegantSnackbarDefaults
import com.elegant.compose.ui.snackbar.ElegantSnackbarDuration
import com.elegant.compose.ui.snackbar.ElegantSnackbarHost
import com.elegant.compose.ui.snackbar.ElegantSnackbarHostState
```

## 基本用法

宿主/状态模式驱动所有 Snackbar。每个宿主用一个 `remember` 创建 `ElegantSnackbarHostState`,把 `ElegantSnackbarHost` 固定在屏幕或表面的底部,并从生命周期长于宿主的协程作用域(如 `rememberCoroutineScope`)中启动 `showSnackbar`。宿主会在自身边界内将消息对齐到底部居中。

```kotlin
val snackbarHostState = remember { ElegantSnackbarHostState() }
val scope = rememberCoroutineScope()

Box(Modifier.fillMaxSize()) {
    Column(Modifier.align(Alignment.Center)) {
        ElegantButton(
            onClick = {
                scope.launch {
                    snackbarHostState.showSnackbar("消息已删除")
                }
            },
        ) {
            Text("显示 Snackbar")
        }
    }
    ElegantSnackbarHost(
        hostState = snackbarHostState,
        modifier = Modifier.align(Alignment.BottomCenter),
    )
}
```

`showSnackbar` 会挂起直到消息被关闭,因此调用方可以在反馈离开屏幕后立即继续工作流。

## 时长

`ElegantSnackbarDuration` 选择自动关闭时机:`Short` 在 4000ms 后关闭,`Long` 在 10000ms 后关闭,`Indefinite` 永不按计时器关闭,会一直保留到点击操作或宿主离开组合。

```kotlin
snackbarHostState.showSnackbar(
    message = "已自动保存",
    duration = ElegantSnackbarDuration.Long,
)

snackbarHostState.showSnackbar(
    message = "正在录制",
    duration = ElegantSnackbarDuration.Indefinite,
)
```

## 组件状态

Snackbar 是临时、不可聚焦的表面:消息保留内容自身的语义,不播报任何交互角色,也从不拦截点击。可选的 `actionLabel` 会播报为 `Role.Button`,保持 48dp 最小触控目标,并以操作内容色显示波纹。在宿主中点击操作标签会立即关闭消息。显示新消息会替换当前消息,被替换的调用如同被关闭一样恢复。

```kotlin
snackbarHostState.showSnackbar(
    message = "项目已移至回收站",
    actionLabel = "撤销",
)

ElegantSnackbar(
    text = "草稿已保存",
    actionLabel = "重试",
    onActionClick = { /* 重试失败的保存 */ },
)
```

第二个示例单独渲染表面,用于自定义布局;宿主变体会自动把 `onActionClick` 接入关闭逻辑。

## 属性

### ElegantSnackbarHost 属性

| 属性名 | 类型 | 说明 | 默认值 | 是否必须 |
| --- | --- | --- | --- | --- |
| `hostState` | `ElegantSnackbarHostState` | 驱动所显示消息的抬升状态 | - | 是 |
| `modifier` | `Modifier` | 仅应用一次到宿主根节点的修饰符 | `Modifier` | 否 |
| `colors` | `ElegantSnackbarColors` | 主题感知的状态颜色 | `ElegantSnackbarDefaults.colors()` | 否 |

### ElegantSnackbarHostState

| 成员 | 类型 | 说明 |
| --- | --- | --- |
| `showSnackbar(message, actionLabel, duration)` | `suspend fun` | 显示一条消息并挂起,直到被计时器或操作点击关闭 |

### ElegantSnackbar 属性

| 属性名 | 类型 | 说明 | 默认值 | 是否必须 |
| --- | --- | --- | --- | --- |
| `text` | `String` | 临时消息文本 | - | 是 |
| `modifier` | `Modifier` | 仅应用一次到表面根节点的修饰符 | `Modifier` | 否 |
| `actionLabel` | `String?` | 显示在消息之后的可选操作标签 | `null` | 否 |
| `onActionClick` | `(() -> Unit)?` | 点击操作标签时调用的回调;null 表示标签无交互 | `null` | 否 |
| `colors` | `ElegantSnackbarColors` | 主题感知的状态颜色 | `ElegantSnackbarDefaults.colors()` | 否 |

### ElegantSnackbarDuration 可选值

| 值 | 行为 |
| --- | --- |
| `Short` | 4000ms 后自动关闭 |
| `Long` | 10000ms 后自动关闭 |
| `Indefinite` | 永不自动关闭;仅操作点击会关闭 |

### ElegantSnackbarDefaults

| 成员 | 类型 | 说明 |
| --- | --- | --- |
| `ShortDurationMillis` | `Long` | `Short` 的 4000ms 自动关闭延迟 |
| `LongDurationMillis` | `Long` | `Long` 的 10000ms 自动关闭延迟 |
| `AnimationDurationMillis` | `Int` | 标准 160ms 滑入滑出过渡时长 |
| `colors()` | `ElegantSnackbarColors` | Light/Dark 主题感知颜色 |

### ElegantSnackbarColors

`ElegantSnackbarColors` 包含容器色、消息内容色与操作内容色。应先调用 `ElegantSnackbarDefaults.colors()`,再仅针对产品明确需要的层级使用 `copy(...)`。

## 进阶用法

### 顺序工作流

由于 `showSnackbar` 会挂起直到关闭,工作流可以在反馈之后串联后续工作;无论消息是被计时器还是操作点击关闭,调用都会恢复。

```kotlin
scope.launch {
    snackbarHostState.showSnackbar(
        message = "下载完成",
        actionLabel = "打开",
    )
    openDownload()
}
```

### 自定义颜色

```kotlin
val baseColors = ElegantSnackbarDefaults.colors()

ElegantSnackbar(
    text = "自定义表面",
    colors = baseColors.copy(
        containerColor = ElegantTheme.colors.backgroundSubtle,
    ),
)
```
