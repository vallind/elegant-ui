# Toast

`ElegantToast` 是用于轻量反馈的顶部临时消息表面,短暂出现后自动消失。与锚定在屏幕底部的 `ElegantSnackbar` 不同,Toast 从顶部滑入,支持标题与可选描述,并提供关闭操作。`ElegantToastHostState` 与 `ElegantToastHost` 配合使用,负责消息的滑入滑出动画、按预设时长自动关闭,并挂起调用方协程直到消息被关闭。

<iframe id="demoIframe" style="width: 100%; height: 460px; border: 1px solid var(--vp-c-divider); border-radius: 12px; display: block; background: var(--vp-c-bg-alt);" src="../../compose/index.html?id=toast" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>

## 引入

```kotlin
import com.elegant.compose.ui.toast.ElegantToast
import com.elegant.compose.ui.toast.ElegantToastColors
import com.elegant.compose.ui.toast.ElegantToastDefaults
import com.elegant.compose.ui.toast.ElegantToastDuration
import com.elegant.compose.ui.toast.ElegantToastHost
import com.elegant.compose.ui.toast.ElegantToastHostState
```

## 基本用法

宿主/状态模式驱动所有 Toast。每个宿主用一个 `remember` 创建 `ElegantToastHostState`,把 `ElegantToastHost` 固定在屏幕或表面的顶部,并从生命周期长于宿主的协程作用域(如 `rememberCoroutineScope`)中启动 `showToast`。宿主会在自身边界内将消息对齐到顶部居中——与把消息固定到底部的 `ElegantSnackbarHost` 相反。

```kotlin
val toastHostState = remember { ElegantToastHostState() }
val scope = rememberCoroutineScope()

Box(Modifier.fillMaxSize()) {
    Column(Modifier.align(Alignment.Center)) {
        ElegantButton(
            onClick = {
                scope.launch {
                    toastHostState.showToast("更改已保存")
                }
            },
        ) {
            Text("显示 Toast")
        }
    }
    ElegantToastHost(
        hostState = toastHostState,
        modifier = Modifier.align(Alignment.TopCenter),
    )
}
```

`showToast` 会挂起直到消息被关闭,因此调用方可以在反馈离开屏幕后立即继续工作流。

## 时长

`ElegantToastDuration` 选择自动关闭时机:`Short` 在 4000ms 后关闭,`Long` 在 10000ms 后关闭,`Indefinite` 永不按计时器关闭,会一直保留到点击关闭操作或宿主离开组合。

```kotlin
toastHostState.showToast(
    title = "已自动保存",
    duration = ElegantToastDuration.Long,
)

toastHostState.showToast(
    title = "正在上传",
    description = "此文件会一直保留到你关闭它。",
    duration = ElegantToastDuration.Indefinite,
)
```

## 组件状态

Toast 是临时、不可聚焦的表面:标题与描述保留文本自身的语义,不播报任何交互角色。可选的关闭操作会播报为 `Role.Button`,内容描述为 `"Close"`,保持 48dp 最小触控目标,并以关闭图标色显示波纹。在宿主中点击关闭操作会立即关闭消息。显示新消息会替换当前消息,被替换的调用如同被关闭一样恢复。

```kotlin
toastHostState.showToast(
    title = "下载完成",
    description = "文件已准备好,可在你的资料库中找到。",
)

ElegantToast(
    title = "草稿已保存",
    description = "刚刚同步。",
    onClose = { /* 保留自定义的关闭流程 */ },
)
```

第二个示例单独渲染表面,用于自定义布局;宿主变体会自动把 `onClose` 接入关闭逻辑。

## 属性

### ElegantToastHost 属性

| 属性名 | 类型 | 说明 | 默认值 | 是否必须 |
| --- | --- | --- | --- | --- |
| `hostState` | `ElegantToastHostState` | 驱动所显示消息的抬升状态 | - | 是 |
| `modifier` | `Modifier` | 仅应用一次到宿主根节点的修饰符 | `Modifier` | 否 |
| `colors` | `ElegantToastColors` | 主题感知的状态颜色 | `ElegantToastDefaults.colors()` | 否 |

### ElegantToastHostState

| 成员 | 类型 | 说明 |
| --- | --- | --- |
| `showToast(title, description, duration)` | `suspend fun` | 显示一条消息并挂起,直到被计时器或关闭操作关闭 |

### ElegantToast 属性

| 属性名 | 类型 | 说明 | 默认值 | 是否必须 |
| --- | --- | --- | --- | --- |
| `title` | `String` | 消息的标题文本 | - | 是 |
| `modifier` | `Modifier` | 仅应用一次到表面根节点的修饰符 | `Modifier` | 否 |
| `description` | `String?` | 可选的支持性文本;空白值会隐藏描述 | `null` | 否 |
| `colors` | `ElegantToastColors` | 主题感知的状态颜色 | `ElegantToastDefaults.colors()` | 否 |
| `onClose` | `(() -> Unit)?` | 点击关闭操作时调用的回调;null 表示不渲染关闭操作 | `null` | 否 |

### ElegantToastDuration 可选值

| 值 | 行为 |
| --- | --- |
| `Short` | 4000ms 后自动关闭 |
| `Long` | 10000ms 后自动关闭 |
| `Indefinite` | 永不自动关闭;仅关闭操作会关闭 |

### ElegantToastDefaults

| 成员 | 类型 | 说明 |
| --- | --- | --- |
| `ShortDurationMillis` | `Long` | `Short` 的 4000ms 自动关闭延迟 |
| `LongDurationMillis` | `Long` | `Long` 的 10000ms 自动关闭延迟 |
| `AnimationDurationMillis` | `Int` | 标准 160ms 滑入滑出过渡时长 |
| `MaxWidth` | `Dp` | 360dp 最大表面宽度 |
| `colors()` | `ElegantToastColors` | Light/Dark 主题感知颜色 |

### ElegantToastColors

`ElegantToastColors` 包含容器色、标题色、描述色与关闭图标色。应先调用 `ElegantToastDefaults.colors()`,再仅针对产品明确需要的层级使用 `copy(...)`。

## 进阶用法

### 顺序工作流

由于 `showToast` 会挂起直到关闭,工作流可以在反馈之后串联后续工作;无论消息是被计时器还是关闭操作关闭,调用都会恢复。

```kotlin
scope.launch {
    toastHostState.showToast(
        title = "下载完成",
        description = "正在打开文件。",
    )
    openDownload()
}
```

### 自定义颜色

```kotlin
val baseColors = ElegantToastDefaults.colors()

ElegantToast(
    title = "自定义表面",
    colors = baseColors.copy(
        containerColor = ElegantTheme.colors.backgroundSubtle,
    ),
)
```
