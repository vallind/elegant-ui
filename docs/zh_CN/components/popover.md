# Popover

`ElegantPopover` 在触发器附近显示浮层:点击触发器打开,点击外部或按 Escape 关闭。它适用于锚定在控件上的情境设置、菜单与快捷操作。可聚焦的弹窗在打开时把键盘焦点移入浮层,关闭时归还给触发器;浮层是无箭头的悬浮卡片。

<iframe id="demoIframe" style="width: 100%; height: 460px; border: 1px solid var(--vp-c-divider); border-radius: 12px; display: block; background: var(--vp-c-bg-alt);" src="../../compose/index.html?id=popover" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>

## 引入

```kotlin
import com.elegant.compose.ui.popover.ElegantPopover
import com.elegant.compose.ui.popover.ElegantPopoverDefaults
import com.elegant.compose.ui.popover.ElegantPopoverPlacement
```

## 基本用法

将触发器包在 `ElegantPopover` 中并传入浮层内容。点击触发器切换浮层开关;点击弹窗外侧、按 Escape 或使用平台返回手势都会关闭浮层。

```kotlin
ElegantPopover(
    popover = {
        Column(verticalArrangement = Arrangement.spacedBy(ElegantSpacing.xs)) {
            Text("设置", style = ElegantTheme.typography.labelMedium)
            Text("通知、外观与账户偏好。")
        }
    },
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(ElegantRadius.sm))
            .background(ElegantTheme.colors.backgroundSubtle)
            .padding(horizontal = ElegantSpacing.lg, vertical = ElegantSpacing.sm),
    ) {
        Text("打开设置")
    }
}
```

## 放置方向

`ElegantPopoverPlacement` 将浮层放在触发器的上方、下方或侧面。`Start` 与 `End` 是逻辑方向,在 RTL 布局中自动镜像。

```kotlin
ElegantPopover(
    popover = { Text("上方放置") },
    placement = ElegantPopoverPlacement.Top,
) {
    Text("Top")
}

ElegantPopover(
    popover = { Text("起始侧放置") },
    placement = ElegantPopoverPlacement.Start,
) {
    Text("Start")
}
```

## 组件状态

Popover 本身没有视觉状态。开关行为是纯粹的切换:点击触发器打开,平台弹窗在外部点击、Escape 或返回键时关闭。由于弹窗可聚焦,打开时键盘焦点移入浮层,关闭时归还给触发器;浮层内的交互内容保持正常的焦点行为。禁用触发器永不打开浮层。

```kotlin
ElegantPopover(
    popover = { Text("不可用") },
    enabled = false,
) {
    Text("已归档")
}
```

## 属性

### ElegantPopover 属性

| 属性名 | 类型 | 说明 | 默认值 | 是否必须 |
| --- | --- | --- | --- | --- |
| `popover` | `@Composable () -> Unit` | 显示在触发器附近的浮层内容 | - | 是 |
| `modifier` | `Modifier` | 仅应用一次到触发器的修饰符 | `Modifier` | 否 |
| `enabled` | `Boolean` | 点击触发器是否可打开浮层 | `true` | 否 |
| `placement` | `ElegantPopoverPlacement` | 围绕触发器的逻辑放置方向 | `ElegantPopoverPlacement.Bottom` | 否 |
| `offset` | `Dp` | 触发器与浮层之间的间距 | `8.dp` | 否 |
| `colors` | `ElegantPopoverColors` | 浮层表面颜色 | `ElegantPopoverDefaults.colors()` | 否 |
| `content` | `@Composable () -> Unit` | 触发器内容;点击它切换浮层开关 | - | 是 |

### ElegantPopoverColors

| 属性名 | 类型 | 说明 |
| --- | --- | --- |
| `containerColor` | `Color` | 浮层表面背景 |
| `contentColor` | `Color` | 浮层内的文字与图标颜色 |
| `borderColor` | `Color` | 浮层表面轮廓 |

### ElegantPopoverPlacement 可选值

| 值 | 行为 |
| --- | --- |
| `Top` | 触发器上方,水平居中 |
| `Bottom` | 触发器下方,水平居中 |
| `Start` | 逻辑起始侧;RTL 中镜像到右侧 |
| `End` | 逻辑结束侧;RTL 中镜像到左侧 |

### ElegantPopoverDefaults

| 成员 | 类型 | 说明 |
| --- | --- | --- |
| `MaxWidth` | `Dp` | 浮层 320dp 最大宽度,超出后换行 |
| `Offset` | `Dp` | 触发器与浮层之间的 8dp 间距 |
| `AnimationDurationMillis` | `Int` | 90ms 浮层进入过渡时长 |

## 进阶用法

### 可交互的浮层内容

`popover` 插槽接受任意可组合内容;由于弹窗可聚焦,其中的控件保持正常的点击与焦点行为。

```kotlin
ElegantPopover(
    popover = {
        Column(verticalArrangement = Arrangement.spacedBy(ElegantSpacing.md)) {
            Text("清除所有筛选条件?", style = ElegantTheme.typography.labelMedium)
            ElegantButton(onClick = { /* 确认 */ }) {
                Text("确认")
            }
        }
    },
) {
    Text("管理")
}
```

### 调整放置方向与间距

用 `offset` 增大间距,并选择适配周围布局的放置方向。

```kotlin
ElegantPopover(
    popover = { Text("账户设置") },
    placement = ElegantPopoverPlacement.End,
    offset = 12.dp,
) {
    Text("账户")
}
```
