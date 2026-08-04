# BottomSheet 底部面板

从屏幕底部边缘滑出的模态面板,覆盖在遮罩之上。适合分享面板、与选中项关联的操作,或任何在移动端受益于宽大近全高表面的流程。

<iframe id="demoIframe" src="../../compose/index.html?id=bottom-sheet" style="width: 100%; height: 320px; border: 1px solid var(--vp-c-divider); border-radius: 8px;"></iframe>

## 引入

```kotlin
import com.elegant.compose.ui.bottomsheet.ElegantBottomSheet
import com.elegant.compose.ui.bottomsheet.ElegantBottomSheetDefaults
```

## 基本用法

`ElegantBottomSheet` 完全受控:调用方持有可见状态,所有关闭路径都会回调 `onDismissRequest`。

```kotlin
var visible by remember { mutableStateOf(false) }

ElegantButton(onClick = { visible = true }) {
    Text("分享")
}

ElegantBottomSheet(
    visible = visible,
    onDismissRequest = { visible = false },
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Text("分享此文件", style = ElegantTheme.typography.titleMedium)
        Text(
            "选择发送方式。面板打开期间保持焦点。",
            style = ElegantTheme.typography.bodyMedium,
            color = ElegantTheme.colors.textSecondary,
        )
        ElegantButton(
            onClick = { visible = false },
            style = ElegantButtonStyle.Secondary,
        ) {
            Text("取消")
        }
    }
}
```

面板宽度上限为 `ElegantBottomSheetDefaults.MaxWidth`,顶部圆角,内容上方居中显示拖拽指示条,内容过高时面板内部可滚动。

## 组件状态

- **显示中**:面板从底部边缘滑入,遮罩同步淡入。
- **关闭中**:点击遮罩、返回键与 Escape 都会回调 `onDismissRequest`;面板不会自行关闭。
- **可滚动**:内容高于屏幕时在圆角面板内部滚动。

## 属性

| 属性名 | 类型 | 说明 | 默认值 | 是否必须 |
| :--- | :--- | :--- | :--- | :--- |
| `visible` | `Boolean` | 是否显示面板;调用方持有关闭状态。 | — | 是 |
| `onDismissRequest` | `() -> Unit` | 点击遮罩、返回键或 Escape 时回调。 | — | 是 |
| `modifier` | `Modifier` | 应用于面板本体。 | `Modifier` | 否 |
| `colors` | `ElegantBottomSheetColors` | 主题化的遮罩、表面、内容与指示条颜色。 | `ElegantBottomSheetDefaults.colors()` | 否 |
| `content` | `@Composable ColumnScope.() -> Unit` | 拖拽指示条下方的面板内容。 | — | 是 |

### 颜色

| 属性名 | 类型 | 说明 |
| :--- | :--- | :--- |
| `scrimColor` | `Color` | 遮罩颜色。 |
| `containerColor` | `Color` | 面板表面颜色。 |
| `contentColor` | `Color` | 局部提供的内容颜色。 |
| `handleColor` | `Color` | 拖拽指示条颜色。 |

## 进阶用法

### 可滚动内容

面板列默认可滚动;内容高于屏幕时会在圆角面板内滚动:

```kotlin
ElegantBottomSheet(visible = visible, onDismissRequest = { visible = false }) {
    Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
        repeat(20) { index ->
            Text("第 $index 行", modifier = Modifier.padding(vertical = 4.dp))
        }
    }
}
```

### 自定义颜色

```kotlin
val colors = ElegantBottomSheetDefaults.colors().copy(
    containerColor = ElegantTheme.colors.surfaceRaised,
    handleColor = ElegantTheme.colors.borderStrong,
)
```
