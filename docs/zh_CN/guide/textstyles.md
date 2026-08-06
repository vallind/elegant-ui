# 文本样式

`ElegantTypography` 拥有十个文本角色,在所有目标平台上被每个组件共享。五个基础角色覆盖紧凑标签、辅助内容与章节标题;扩展角色补充 HyperOS 阅读字号(17sp 正文与大标题档),供应用栏与大面积内容表面使用。

## 角色

| 角色 | 字号 | 用途 |
| :--- | :--- | :--- |
| `labelSmall` | 12sp | 紧凑组件标签 |
| `labelMedium` | 14sp | 默认组件标签 |
| `labelLarge` | 16sp | 突出组件标签 |
| `bodyMedium` | 14sp | 默认辅助内容 |
| `bodyLarge` | 17sp | 大号辅助内容 |
| `footnote` | 13sp | 小号注解 |
| `titleMedium` | 18sp | 标准章节标题 |
| `titleLarge` | 20sp | 突出章节标题、折叠后的应用栏标题 |
| `titleXl` | 24sp | 大号章节标题 |
| `headlineLarge` | 32sp | 展示标题、展开后的大标题应用栏 |

## 读取样式

```kotlin
Text(
    text = "章节标题",
    style = ElegantTheme.typography.titleMedium,
)
```

## 组件中的排版

组件对其默认文本样式应用 `ElegantTheme.typography.*`,并在文本定制重要处接受调用方提供的 `style`:

```kotlin
Text(
    text = "标签",
    style = ElegantTheme.typography.labelMedium,
    color = ElegantTheme.colors.textSecondary,
)
```

## 一致性规则

- 组件从不硬编码字号或字重,始终读取 `ElegantTheme.typography`。
- 文本层级在亮色与暗色主题中及字体缩放变化下保持稳定。
- 与[颜色](./colors)中的颜色角色组合,构成完整语义契约。
