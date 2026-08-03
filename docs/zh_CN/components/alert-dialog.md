# Alert Dialog

`ElegantAlertDialog` 是 Elegant UI 遮罩组件家族中的确认变体:一个包含标题、可选描述,以及成对的取消/确认操作行的紧凑表面,在平台对话框窗口内居中于变暗的遮罩层之上。它适用于破坏性或重要的确认,这些确认必须阻止应用其余部分继续操作直到处理完成。

<iframe id="demoIframe" style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; border: none;" src="../../compose/index.html?id=alert-dialog" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>

## 引入

```kotlin
import com.elegant.compose.ui.alertdialog.ElegantAlertDialog
import com.elegant.compose.ui.alertdialog.ElegantAlertDialogColors
import com.elegant.compose.ui.alertdialog.ElegantAlertDialogDefaults
```

## 基本用法

`ElegantAlertDialog` 是受控遮罩:调用方持有 `visible` 状态,并将其设为 false 来让对话框离开组合。对话框负责渲染遮罩层、宽度上限为 400dp 且带 24dp 内边距的居中表面、标题与可选描述,以及操作行;可选的 `content` 插槽位于描述与操作之间。

```kotlin
var visible by remember { mutableStateOf(false) }

ElegantButton(onClick = { visible = true }) {
    Text("删除项目")
}

ElegantAlertDialog(
    visible = visible,
    onDismissRequest = { visible = false },
    title = "删除项目?",
    description = "此操作无法撤销。",
    confirmText = "删除",
    onConfirm = { visible = false },
    dismissText = "取消",
    onDismiss = { visible = false },
)
```

## 组件状态

`ElegantAlertDialog` 是只有一个可见状态、并在确认按钮上提供 `confirmEnabled` 开关的受控遮罩;它没有 loading 状态。

**关闭方式。** 四条路径可以关闭或解决对话框。点击遮罩层会通过 `dismissOnClickOutside` 调用 `onDismissRequest`。Android 的系统返回键,或 Desktop 与 Web 上的 Escape 键,会通过 `dismissOnBackPress` 调用 `onDismissRequest`。取消按钮在提供了 `onDismiss` 时调用它,否则调用 `onDismissRequest`。确认按钮只调用 `onConfirm`,它永远不会自行关闭对话框,因此由调用方根据确认结果决定保持对话框打开,或将 `visible` 设为 false。

**焦点。** 可见期间,平台对话框窗口会捕获焦点并将焦点遍历限制在对话框内容内;周围的应用处于不可操作状态。关闭后,焦点返回之前聚焦的元素。确认与取消按钮都是 `ElegantButton`,带有各自的角色与状态。

**描述与取消按钮。** 为 null 或空白的 `description` 不会被渲染。为 null 或空白的 `dismissText` 会隐藏取消按钮,此时确认按钮是唯一的操作。

**入场。** 表面在 `AnimationDurationMillis` 时长内淡入并缩放(alpha 0 到 1,scale 0.98 到 1);遮罩层随对话框窗口一起出现。

```kotlin
// 点击遮罩层、返回/Escape、以及取消都会让对话框关闭;
// 确认只解决结果,而不会自行关闭。
ElegantAlertDialog(
    visible = visible,
    onDismissRequest = { visible = false },
    title = "放弃更改?",
    confirmText = "放弃",
    onConfirm = {
        // 执行破坏性操作;在 visible 变化之前对话框保持打开。
        visible = false
    },
    dismissText = "继续编辑",
    onDismiss = { visible = false },
)
```

## 属性

### ElegantAlertDialog 属性

| 属性名 | 类型 | 说明 | 默认值 | 是否必须 |
| --- | --- | --- | --- | --- |
| `visible` | `Boolean` | 是否显示对话框;false 时不参与组合 | - | 是 |
| `onDismissRequest` | `() -> Unit` | 点击遮罩层、返回/Escape、以及 `onDismiss` 为 null 时点击取消按钮会调用 | - | 是 |
| `modifier` | `Modifier` | 仅应用一次到全屏对话框根节点的修饰符 | `Modifier` | 否 |
| `title` | `String` | 以 `titleMedium` 样式渲染的对话框标题文本 | - | 是 |
| `description` | `String?` | 可选的辅助文本;为 null 或空白时隐藏 | `null` | 否 |
| `confirmText` | `String` | 确认按钮的文本 | - | 是 |
| `onConfirm` | `() -> Unit` | 确认按钮接受激活时调用;对话框永远不会自行关闭 | - | 是 |
| `dismissText` | `String?` | 取消按钮的文本;为 null 或空白时隐藏 | `null` | 否 |
| `onDismiss` | `(() -> Unit)?` | 由取消按钮调用的可选回调;为 null 时回退到 `onDismissRequest` | `null` | 否 |
| `confirmEnabled` | `Boolean` | 确认按钮是否接受激活 | `true` | 否 |
| `colors` | `ElegantAlertDialogColors` | 主题感知的对话框颜色 | `ElegantAlertDialogDefaults.colors()` | 否 |
| `content` | `@Composable () -> Unit` | 渲染在描述与操作行之间的可选插槽 | `{}` | 否 |

### ElegantAlertDialogDefaults

| 成员 | 类型 | 说明 |
| --- | --- | --- |
| `MaxWidth` | `Dp` | 内容换行前表面宽度的 400dp 上限 |
| `ScrimAlpha` | `Float` | 应用于黑色遮罩层的 0.4 透明度 |
| `AnimationDurationMillis` | `Int` | 220ms 强调入场时长 |
| `colors()` | `ElegantAlertDialogColors` | 主题感知的 Light/Dark 颜色 |

### ElegantAlertDialogColors

`ElegantAlertDialogColors` 包含遮罩层颜色、表面容器颜色、通过 `LocalContentColor` 提供的内容颜色、标题颜色,以及描述颜色。应先调用 `ElegantAlertDialogDefaults.colors()`,再仅针对产品明确需要的表面层级使用 `copy(...)`。

## 进阶用法

### 破坏性确认

破坏性确认会在前置条件满足之前让确认按钮保持禁用,从而避免在调用方就绪前发生意外的激活。

```kotlin
var pending by remember { mutableStateOf(false) }
var canDelete by remember { mutableStateOf(false) }

ElegantButton(onClick = { pending = true }) {
    Text("删除项目")
}

ElegantAlertDialog(
    visible = pending,
    onDismissRequest = { pending = false },
    title = "删除项目?",
    description = "项目及其历史将被永久删除。",
    confirmText = "删除",
    onConfirm = {
        pending = false
        canDelete = true
    },
    dismissText = "取消",
    onDismiss = { pending = false },
    confirmEnabled = canDelete,
)
```

### 自定义颜色

以 `ElegantAlertDialogDefaults.colors()` 为基准,使用 `copy(...)` 为产品定制表面样式。

```kotlin
val baseColors = ElegantAlertDialogDefaults.colors()

ElegantAlertDialog(
    visible = visible,
    onDismissRequest = { visible = false },
    title = "删除项目?",
    confirmText = "删除",
    onConfirm = { visible = false },
    dismissText = "取消",
    onDismiss = { visible = false },
    colors = baseColors.copy(
        containerColor = Color(0xFF202229),
        titleColor = Color(0xFFF6F7F9),
    ),
)
```
