# Modal

`ElegantModal` 是模态遮罩组件,在平台对话框窗口内将圆角表面居中于变暗的遮罩层之上。它适用于确认、聚焦表单,以及其他必须阻止应用其余部分继续操作直到处理完成的任务。

<iframe id="demoIframe" style="width: 100%; height: 460px; border: 1px solid var(--vp-c-divider); border-radius: 12px; display: block; background: var(--vp-c-bg-alt);" src="../../compose/index.html?id=modal" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>

## 引入

```kotlin
import com.elegant.compose.ui.modal.ElegantModal
import com.elegant.compose.ui.modal.ElegantModalColors
import com.elegant.compose.ui.modal.ElegantModalDefaults
```

## 基本用法

`ElegantModal` 是受控遮罩:调用方持有 `visible` 状态,并将其设为 false 来让对话框离开组合。对话框负责渲染遮罩层、宽度上限为 480dp 且带 24dp 内边距的居中表面,以及内容颜色;标题、描述与操作布局由调用方在内容中自行组织。

```kotlin
var visible by remember { mutableStateOf(false) }

ElegantButton(onClick = { visible = true }) {
    Text("打开弹窗")
}

ElegantModal(
    visible = visible,
    onDismissRequest = { visible = false },
) {
    Column {
        Text("删除项目?", style = ElegantTheme.typography.titleMedium)
        Text("此操作无法撤销。", style = ElegantTheme.typography.bodyMedium)
        Row(Modifier.align(Alignment.End)) {
            ElegantButton(
                onClick = { visible = false },
                style = ElegantButtonStyle.Secondary,
            ) {
                Text("取消")
            }
            ElegantButton(onClick = { visible = false }) {
                Text("删除")
            }
        }
    }
}
```

## 组件状态

`ElegantModal` 是只有可见状态的受控遮罩;它没有 disabled 或 loading 状态。

**关闭方式。** 三种路径可以关闭弹窗。点击遮罩层会通过 `dismissOnClickOutside` 调用 `onDismissRequest`。Android 的系统返回键,或 Desktop 与 Web 上的 Escape 键,会通过 `dismissOnBackPress` 调用 `onDismissRequest`。将 `visible` 设为 false 会直接移除对话框窗口,不再调用 `onDismissRequest`,因为该决定已由调用方做出。

**焦点。** 可见期间,平台对话框窗口会捕获焦点并将焦点遍历限制在弹窗内容内;周围的应用处于不可操作状态。关闭后,焦点返回之前聚焦的元素。

**入场。** 表面在 `AnimationDurationMillis` 时长内淡入并缩放(alpha 0 到 1,scale 0.98 到 1);遮罩层随对话框窗口一起出现。

```kotlin
// 点击遮罩层、返回/Escape、以及 visible = false 都会让弹窗保持关闭。
ElegantModal(
    visible = visible,
    onDismissRequest = { visible = false },
) {
    Text("弹窗内容")
}
```

## 属性

### ElegantModal 属性

| 属性名 | 类型 | 说明 | 默认值 | 是否必须 |
| --- | --- | --- | --- | --- |
| `visible` | `Boolean` | 是否显示弹窗;false 时不参与组合 | - | 是 |
| `onDismissRequest` | `() -> Unit` | 点击遮罩层与返回/Escape 关闭时调用 | - | 是 |
| `modifier` | `Modifier` | 仅应用一次到全屏对话框根节点的修饰符 | `Modifier` | 否 |
| `shape` | `Shape` | 弹窗表面的裁剪与阴影形状 | `ElegantModalDefaults.Shape` | 否 |
| `colors` | `ElegantModalColors` | 主题感知的弹窗颜色 | `ElegantModalDefaults.colors()` | 否 |
| `content` | `@Composable () -> Unit` | 弹窗内容;内边距由弹窗提供 | - | 是 |

### ElegantModalDefaults

| 成员 | 类型 | 说明 |
| --- | --- | --- |
| `MaxWidth` | `Dp` | 内容换行前表面宽度的 480dp 上限 |
| `Shape` | `Shape` | 共享的 16dp 圆角形状 |
| `ScrimAlpha` | `Float` | 应用于黑色遮罩层的 0.4 透明度 |
| `AnimationDurationMillis` | `Int` | 220ms 强调入场时长 |
| `colors()` | `ElegantModalColors` | 主题感知的 Light/Dark 颜色 |

### ElegantModalColors

`ElegantModalColors` 包含遮罩层颜色、表面容器颜色,以及通过 `LocalContentColor` 提供的内容颜色。应先调用 `ElegantModalDefaults.colors()`,再仅针对产品明确需要的表面层级使用 `copy(...)`。

## 进阶用法

### 确认流程

确认弹窗将警告标题与描述,和次级取消操作与主确认操作组合在一起。`onDismissRequest` 覆盖取消按钮、遮罩层点击以及返回/Escape,因此每条路径都会让弹窗保持关闭。

```kotlin
var confirmVisible by remember { mutableStateOf(false) }

ElegantButton(onClick = { confirmVisible = true }) {
    Text("删除文件")
}

ElegantModal(
    visible = confirmVisible,
    onDismissRequest = { confirmVisible = false },
) {
    Column(verticalArrangement = Arrangement.spacedBy(ElegantSpacing.lg)) {
        Column(verticalArrangement = Arrangement.spacedBy(ElegantSpacing.xs)) {
            Text("删除此文件?", style = ElegantTheme.typography.titleMedium)
            Text("该文件将被永久删除。", style = ElegantTheme.typography.bodyMedium)
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(ElegantSpacing.md, Alignment.End),
        ) {
            ElegantButton(
                onClick = { confirmVisible = false },
                style = ElegantButtonStyle.Secondary,
            ) {
                Text("取消")
            }
            ElegantButton(onClick = { confirmVisible = false }) {
                Text("删除")
            }
        }
    }
}
```

### 自定义表面

```kotlin
val baseColors = ElegantModalDefaults.colors()

ElegantModal(
    visible = visible,
    onDismissRequest = { visible = false },
    colors = baseColors.copy(
        containerColor = Color(0xFF202229),
        contentColor = Color(0xFFF6F7F9),
    ),
) {
    Text("自定义表面")
}
```
