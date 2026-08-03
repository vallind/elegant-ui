# Fieldset

`ElegantFieldset` 是带边框的表单分区,通过可选的图例(legend)将相关字段组织在一起。它适用于收货地址、联系表单,以及任何属于同一逻辑块的输入集合。

<iframe id="demoIframe" style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; border: none;" src="../../compose/index.html?id=fieldset" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>

## 引入

```kotlin
import com.elegant.compose.ui.fieldset.ElegantFieldset
import com.elegant.compose.ui.fieldset.ElegantFieldsetColors
import com.elegant.compose.ui.fieldset.ElegantFieldsetDefaults
```

## 基本用法

Fieldset 渲染一个带 1dp 边框与 16dp 内部内边距的抬升圆角容器。图例在渲染前会被修剪,并且只有非空白时才显示在内容上方。内容通过 `LocalContentColor` 获得内容颜色,字段之间的间距由调用方负责。

```kotlin
ElegantFieldset(legend = "联系信息") {
    Column(verticalArrangement = Arrangement.spacedBy(ElegantSpacing.lg)) {
        ElegantInput(value = "", onValueChange = {}, label = "邮箱")
        ElegantInput(value = "", onValueChange = {}, label = "电话")
    }
}
```

## 组件状态

Fieldset 是非交互表面:它没有 hover、press、focus 或 disabled 状态,也不添加任何 role、焦点或点击处理。内容的语义会原样保留。null 或空白的图例会完全省略图例行,内容从带边框的分区顶部开始。

```kotlin
ElegantFieldset {
    Text("没有图例的 Fieldset 只渲染带边框的分区。")
}
```

## 属性

### ElegantFieldset 属性

| 属性名 | 类型 | 说明 | 默认值 | 是否必须 |
| --- | --- | --- | --- | --- |
| `modifier` | `Modifier` | 仅应用一次到 Fieldset 根节点的修饰符 | `Modifier` | 否 |
| `legend` | `String?` | 内容上方的可选图例;null 或空白的图例会被省略 | `null` | 否 |
| `colors` | `ElegantFieldsetColors` | 主题感知的颜色 | `ElegantFieldsetDefaults.colors()` | 否 |
| `content` | `@Composable () -> Unit` | 分组在带边框分区内的字段或内容 | - | 是 |

### ElegantFieldsetDefaults

| 成员 | 类型 | 说明 |
| --- | --- | --- |
| `LegendGap` | `Dp` | 图例与内容之间的 8dp 间距 |
| `ContentPadding` | `Dp` | 带边框分区内部的 16dp 内边距 |
| `colors()` | `ElegantFieldsetColors` | 主题感知的 Light/Dark 颜色 |

### ElegantFieldsetColors

`ElegantFieldsetColors` 包含容器色、边框色、图例色与内容色。应先调用 `ElegantFieldsetDefaults.colors()`,再仅针对产品明确需要的层级使用 `copy(...)`。

## 进阶用法

### 分组结账表单

在一个 `ElegantCard` 内组合两个 Fieldset,在同一表面上将收货与支付关注点分开。

```kotlin
ElegantCard {
    Column(Modifier.padding(16.dp)) {
        ElegantFieldset(legend = "收货地址") {
            ElegantInput(value = "", onValueChange = {}, label = "街道")
        }
        Spacer(Modifier.height(12.dp))
        ElegantFieldset(legend = "支付信息") {
            ElegantInput(value = "", onValueChange = {}, label = "卡号")
        }
    }
}
```
