# Button 按钮

按钮用于触发即时操作。Elegant Button 提供三种强调层级、三种尺寸、完整交互状态，并保证至少 48dp 的触控区域。

## API

```kotlin
ElegantButton(
    onClick = { /* 执行动作 */ },
    style = ElegantButtonStyle.Primary,
    size = ElegantButtonSize.Medium,
    enabled = true,
    loading = false,
    leadingIcon = { /* 可选 */ },
    trailingIcon = { /* 可选 */ },
) {
    Text("继续")
}
```

## 样式

- `Primary`：一个页面或任务中的唯一主要操作。
- `Secondary`：具有可见容器与边框的辅助操作。
- `Tertiary`：低强调操作、文字链接或上下文命令。

## 尺寸

| 尺寸 | 可视高度 | 最小触控高度 | 水平内边距 |
|---|---:|---:|---:|
| Small | 36dp | 48dp | 12dp |
| Medium | 40dp | 48dp | 16dp |
| Large | 48dp | 48dp | 20dp |

## 状态

支持 Default、Pressed、Focused、Disabled 与 Loading。Loading 状态不可点击，同时保留按钮语义。

## 无障碍

- 最小触控高度为 48dp。
- 通过 Semantics 暴露 Button Role。
- 禁用状态通过 Semantics 暴露。
- Loading 状态提供状态描述。
- 图标默认作为装饰；仅当图标包含标签未表达的信息时提供描述。

## 真机检查项

- 三种样式和三种尺寸是否层级清晰。
- Small 的视觉高度虽为 36dp，但点击区域是否容易触发。
- 长按或点击时按压反馈是否自然。
- Light / Dark 切换后对比度是否舒适。
- Loading 状态是否稳定且不会重复触发点击。
- Disabled 是否足够明确但不过度发灰。
