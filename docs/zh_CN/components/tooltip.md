# Tooltip

`ElegantTooltipBox` 用轻量弹层解释紧凑控件:悬停、键盘聚焦或触摸长按即可显示提示。它适用于图标按钮与快捷键等场景,永久标签反而带来杂乱;提示从不抢占焦点,并始终保持在窗口内。

<iframe id="demoIframe" style="width: 100%; height: 460px; border: 1px solid var(--vp-c-divider); border-radius: 12px; display: block; background: var(--vp-c-bg-alt);" src="../../compose/index.html?id=tooltip" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>

## 引入

```kotlin
import com.elegant.compose.ui.tooltip.ElegantTooltip
import com.elegant.compose.ui.tooltip.ElegantTooltipBox
import com.elegant.compose.ui.tooltip.ElegantTooltipDefaults
import com.elegant.compose.ui.tooltip.ElegantTooltipPlacement
```

## 基本用法

将控件包在 `ElegantTooltipBox` 中并传入提示内容。悬停 `showDelayMillis` 后显示,键盘聚焦立即显示,触摸长按也立即显示。

```kotlin
ElegantTooltipBox(
    tooltip = { ElegantTooltip(text = "保存更改") },
) {
    ElegantButton(onClick = { /* 保存 */ }) {
        Text("保存")
    }
}
```

## 放置方向

`ElegantTooltipPlacement` 将提示放在锚点的上方、下方或侧面。`Start` 与 `End` 是逻辑方向,在 RTL 布局中自动镜像。

```kotlin
ElegantTooltipBox(
    tooltip = { ElegantTooltip(text = "上方放置") },
    placement = ElegantTooltipPlacement.Top,
) {
    ElegantButton(onClick = { /* 操作 */ }) {
        Text("Top")
    }
}

ElegantTooltipBox(
    tooltip = { ElegantTooltip(text = "起始侧放置") },
    placement = ElegantTooltipPlacement.Start,
) {
    ElegantButton(onClick = { /* 操作 */ }) {
        Text("Start")
    }
}
```

## 组件状态

Tooltip 本身没有视觉状态;锚点保留自己的交互状态。显示时机遵循固定优先级:悬停等待 `showDelayMillis`,键盘聚焦与触摸长按立即显示,长按释放立即隐藏。指针离开或焦点移开后,提示在 `hideDelayMillis` 后隐藏。禁用锚点永不显示提示。

```kotlin
ElegantTooltipBox(
    tooltip = { ElegantTooltip(text = "不可用") },
    enabled = false,
) {
    ElegantButton(onClick = {}, enabled = false) {
        Text("已归档")
    }
}
```

## 属性

### ElegantTooltipBox 属性

| 属性名 | 类型 | 说明 | 默认值 | 是否必须 |
| --- | --- | --- | --- | --- |
| `tooltip` | `@Composable () -> Unit` | 显示在锚点附近的弹层内容 | - | 是 |
| `modifier` | `Modifier` | 仅应用一次到锚点的修饰符 | `Modifier` | 否 |
| `enabled` | `Boolean` | 悬停、聚焦与长按是否可显示提示 | `true` | 否 |
| `placement` | `ElegantTooltipPlacement` | 围绕锚点的逻辑放置方向 | `ElegantTooltipPlacement.Top` | 否 |
| `showDelayMillis` | `Long` | 悬停显示延迟;负值按 0 处理 | `600` | 否 |
| `hideDelayMillis` | `Long` | 离开与失焦隐藏延迟;负值按 0 处理 | `100` | 否 |
| `offset` | `Dp` | 锚点与提示之间的间距 | `8.dp` | 否 |
| `content` | `@Composable () -> Unit` | 接收交互的锚点内容 | - | 是 |

### ElegantTooltip 属性

| 属性名 | 类型 | 说明 | 默认值 | 是否必须 |
| --- | --- | --- | --- | --- |
| `text` | `String` | 提示文字 | - | 是 |
| `modifier` | `Modifier` | 仅应用一次到提示表面的修饰符 | `Modifier` | 否 |

### ElegantTooltipPlacement 可选值

| 值 | 行为 |
| --- | --- |
| `Top` | 锚点上方,水平居中 |
| `Bottom` | 锚点下方,水平居中 |
| `Start` | 逻辑起始侧;RTL 中镜像到右侧 |
| `End` | 逻辑结束侧;RTL 中镜像到左侧 |

### ElegantTooltipDefaults

| 成员 | 类型 | 说明 |
| --- | --- | --- |
| `DefaultShowDelayMillis` | `Long` | 600ms 悬停显示延迟 |
| `DefaultHideDelayMillis` | `Long` | 100ms 离开与失焦隐藏延迟 |
| `DefaultOffset` | `Dp` | 锚点与提示之间的 8dp 间距 |
| `MaxWidth` | `Dp` | 提示 280dp 最大宽度,超出后换行 |

## 进阶用法

### 自定义提示内容

`tooltip` 插槽接受任意可组合内容,提示可以承载比单行文字更复杂的结构。

```kotlin
ElegantTooltipBox(
    tooltip = {
        Column(horizontalAlignment = Alignment.Start) {
            Text("键盘快捷键", style = ElegantTheme.typography.labelMedium)
            Text("Ctrl + S", style = ElegantTheme.typography.labelSmall)
        }
    },
) {
    ElegantButton(onClick = { /* 保存 */ }) {
        Text("保存")
    }
}
```

### 调整显示时机

当锚点控件属于高频操作流程时,可以缩短悬停延迟并增大间距。

```kotlin
ElegantTooltipBox(
    tooltip = { ElegantTooltip(text = "已与服务器同步") },
    showDelayMillis = 300,
    hideDelayMillis = 200,
    offset = 12.dp,
) {
    ElegantButton(onClick = { /* 同步 */ }) {
        Text("同步")
    }
}
```
