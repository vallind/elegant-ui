# CheckboxPreference

`ElegantCheckboxPreference` 是设置行组件:将标题块与末端对齐的复选框配对,遵循与 `ElegantSwitchPreference` 共享的偏好行模式。适用于每行一个独立选项的多选设置。

<iframe id="demoIframe" style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; border: none;" src="../../compose/index.html?id=checkbox-preference" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>

## 引入

```kotlin
import com.elegant.compose.ui.preference.ElegantCheckboxPreference
import com.elegant.compose.ui.preference.ElegantCheckboxPreferenceColors
import com.elegant.compose.ui.preference.ElegantCheckboxPreferenceDefaults
```

## 基本用法

偏好行保持 48dp 最小高度、两行标题块,以及拥有切换语义的尾部复选框。

```kotlin
ElegantCheckboxPreference(
    title = "相机",
    checked = cameraEnabled,
    onCheckedChange = { cameraEnabled = it },
    supportingText = "允许拍照与录像",
)
```

## 组件状态

行本身不可点击;复选框拥有切换交互并播报勾选状态。禁用行将标题调暗为禁用角色,向复选框传递 `enabled = false`,且绝不调用 `onCheckedChange`。底部分隔线(自起始边内缩 16dp)绘制在行之间;组的最后一行请传 `showDivider = false`。

```kotlin
ElegantCheckboxPreference(
    title = "麦克风",
    checked = false,
    onCheckedChange = {},
    enabled = false,
    supportingText = "当前不可用",
    showDivider = false,
)
```

## 属性

### ElegantCheckboxPreference 属性

| 属性名 | 类型 | 说明 | 默认值 | 是否必须 |
| --- | --- | --- | --- | --- |
| `title` | `String` | 行标题 | - | 是 |
| `checked` | `Boolean` | 选项是否勾选 | - | 是 |
| `onCheckedChange` | `(Boolean) -> Unit` | 携带新勾选状态的回调 | - | 是 |
| `modifier` | `Modifier` | 仅应用一次到行根节点的修饰符 | `Modifier` | 否 |
| `supportingText` | `String?` | 标题下方的可选辅助行 | `null` | 否 |
| `enabled` | `Boolean` | 选项是否接受交互 | `true` | 否 |
| `colors` | `ElegantCheckboxPreferenceColors` | 主题感知的行颜色 | `ElegantCheckboxPreferenceDefaults.colors()` | 否 |
| `showDivider` | `Boolean` | 是否绘制底部分隔线 | `true` | 否 |

### ElegantCheckboxPreferenceColors

`ElegantCheckboxPreferenceColors` 包含 `containerColor`(预留)、`titleColor`、`supportingTextColor`、`disabledTitleColor` 与 `dividerColor`。应先调用 `ElegantCheckboxPreferenceDefaults.colors()`,再仅针对产品明确需要的层级使用 `copy(...)`。

## 进阶用法

### 权限分组

在卡片内堆叠行,并在最后一行隐藏分隔线。

```kotlin
ElegantCard {
    Column {
        ElegantCheckboxPreference(
            title = "相机",
            checked = permissions.contains("camera"),
            onCheckedChange = { checked ->
                permissions = if (checked) {
                    permissions + "camera"
                } else {
                    permissions - "camera"
                }
            },
        )
        ElegantCheckboxPreference(
            title = "照片",
            checked = permissions.contains("photos"),
            onCheckedChange = { checked ->
                permissions = if (checked) {
                    permissions + "photos"
                } else {
                    permissions - "photos"
                }
            },
            showDivider = false,
        )
    }
}
```
