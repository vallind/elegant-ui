# Avatar

`ElegantAvatar` 是面向人物、团队与实体的精致跨平台身份组件。它提供自动生成的首字母、三种光学尺寸、主题感知颜色、裁剪后的自定义内容槽和清晰的图像语义，同时不把组件库绑定到特定图片加载框架。

<iframe id="demoIframe" style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; border: none;" src="../../compose/index.html?id=avatar" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>

## 引入

```kotlin
import com.elegant.compose.ui.avatar.ElegantAvatar
import com.elegant.compose.ui.avatar.ElegantAvatarColors
import com.elegant.compose.ui.avatar.ElegantAvatarDefaults
import com.elegant.compose.ui.avatar.ElegantAvatarSize
```

## 基本用法

必需的名称会生成紧凑回退文本，并作为默认无障碍说明。

```kotlin
ElegantAvatar(name = "Ada Lovelace")
```

## 头像尺寸

密集集合使用 `Small`，标准列表和卡片使用 `Medium`，突出的个人资料页面使用 `Large`。

```kotlin
Row(verticalAlignment = Alignment.CenterVertically) {
    ElegantAvatar(
        name = "小头像",
        initials = "S",
        size = ElegantAvatarSize.Small,
    )
    ElegantAvatar(
        name = "中头像",
        initials = "M",
        size = ElegantAvatarSize.Medium,
    )
    ElegantAvatar(
        name = "大头像",
        initials = "L",
        size = ElegantAvatarSize.Large,
    )
}
```

## 组件状态

没有自定义内容时，`ElegantAvatarDefaults.initials(name)` 会提取首尾单词的首字符，单个单词则使用前两个字母；空白或不支持的名称回退为 `?`。产品需要固定标签时可覆盖 `initials`，纯装饰头像则设置 `contentDescription = null`。

```kotlin
ElegantAvatar(
    name = "Elegant UI",
    initials = "EU",
)

ElegantAvatar(
    name = "装饰性工作区",
    contentDescription = null,
)
```

远程加载、点击行为、在线状态和状态徽标是有意分离的职责。应在头像周围组合图片加载器、`ElegantIconButton` 或后续 Badge 组件，而不是把这些契约隐藏进 Avatar 内部。

## 属性

### ElegantAvatar 属性

| 属性名 | 类型 | 说明 | 默认值 | 是否必须 |
| --- | --- | --- | --- | --- |
| `name` | `String` | 用于回退文本和默认语义的人物或实体名称 | - | 是 |
| `modifier` | `Modifier` | 应用于头像容器的修饰符 | `Modifier` | 否 |
| `initials` | `String` | 没有自定义内容时显示的短回退标签 | `ElegantAvatarDefaults.initials(name)` | 否 |
| `contentDescription` | `String?` | 本地化图像说明，纯装饰头像可设为 null | `name` | 否 |
| `size` | `ElegantAvatarSize` | 可视容器与排版预设 | `ElegantAvatarSize.Medium` | 否 |
| `shape` | `Shape` | 用于裁剪和描边所有头像内容的形状 | `CircleShape` | 否 |
| `colors` | `ElegantAvatarColors` | 主题感知的容器色、内容色和轮廓色 | `ElegantAvatarDefaults.colors()` | 否 |
| `borderWidth` | `Dp` | 光学轮廓宽度 | `ElegantAvatarDefaults.BorderWidth` | 否 |
| `content` | `(@Composable () -> Unit)?` | 替换首字母的可选图片、图标或视觉内容 | `null` | 否 |

### ElegantAvatarSize 可选值

| 值 | 容器尺寸 | 排版角色 | 推荐场景 |
| --- | --- | --- | --- |
| `Small` | `32.dp` | `labelMedium` | 密集列表与身份组合 |
| `Medium` | `40.dp` | `labelLarge` | 列表、卡片与资料摘要 |
| `Large` | `56.dp` | `titleMedium` | 资料页标题与宽松页面 |

### ElegantAvatarDefaults

| 成员 | 类型 | 说明 |
| --- | --- | --- |
| `BorderWidth` | `Dp` | 默认 1dp 光学轮廓 |
| `initials(name)` | `String` | 根据人物或实体名称生成紧凑回退标签 |
| `colors()` | `ElegantAvatarColors` | 返回 Light/Dark 主题感知头像颜色 |

### ElegantAvatarColors

`ElegantAvatarColors` 包含 `containerColor`、`contentColor` 和 `borderColor`。应先调用 `ElegantAvatarDefaults.colors()`，再通过 `copy(...)` 设置产品明确需要的身份色调。

## 进阶用法

### 自定义图片内容

Avatar 会把自定义内容裁剪为所选形状。组件已经拥有语义标签，因此嵌套图片的说明应保持为 null。

```kotlin
ElegantAvatar(
    name = "Maya Chen",
    size = ElegantAvatarSize.Large,
) {
    Image(
        painter = painterResource(Res.drawable.maya),
        contentDescription = null,
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.Crop,
    )
}
```

### 自定义身份色调

```kotlin
val baseColors = ElegantAvatarDefaults.colors()

ElegantAvatar(
    name = "Noah Williams",
    colors = baseColors.copy(
        containerColor = Color(0xFF0F766E),
        contentColor = Color.White,
        borderColor = Color(0xFF5EEAD4),
    ),
)
```
