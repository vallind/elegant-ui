# Squircle

`ElegantSquircleShape` 是一种连续曲率的圆角矩形,其圆角逼近超椭圆 `|x/a|^n + |y/b|^n = 1`(n ≈ 4)。与 `RoundedCornerShape` 不同——后者的圆角由圆弧与直边在切点处相接、存在切率突变——Squircle 的每个圆角都是一条三次贝塞尔弧,与相邻直边之间没有可见的过渡,整体轮廓呈现为一整条柔和连续的曲线。它实现了 `Shape` 契约,因此任何接受 Shape 的地方都能使用:`ElegantSurface`、`ElegantAvatar`、`Modifier.clip`、边框与阴影。

<iframe id="demoIframe" style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; border: none;" src="../../compose/index.html?id=squircle" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>

## 引入

```kotlin
import com.elegant.compose.ui.shape.ElegantSquircleShape
```

## 基本用法

把 `ElegantSquircleShape` 作为 `shape` 传给任何接受 `Shape` 的组件即可。默认 `cornerRadius` 为 `16.dp`,默认 `smoothing` 为 `0.6f`。

```kotlin
ElegantSurface(shape = ElegantSquircleShape()) {
    Text("Squircle 表面")
}
```

## 组件状态

`smoothing` 控制每个圆角的曲率:`0f` 把圆角坍缩为普通直角,`1f` 还原最圆润且稳定的超椭圆外观。有效半径会钳制到较短边的二分之一,因此小尺寸表面上轮廓永远不会自相交。

```kotlin
ElegantSurface(shape = ElegantSquircleShape(smoothing = 0f)) {
    Text("直角")
}

ElegantSurface(shape = ElegantSquircleShape(smoothing = 0.6f)) {
    Text("默认圆润")
}

ElegantSurface(shape = ElegantSquircleShape(smoothing = 1f)) {
    Text("最圆润")
}
```

## 属性

### ElegantSquircleShape 属性

| 属性名 | 类型 | 说明 | 默认值 | 是否必须 |
| --- | --- | --- | --- | --- |
| `cornerRadius` | `Dp` | 超椭圆弧的圆角半径,钳制到较短边的二分之一 | `16.dp` | 否 |
| `smoothing` | `Float` | `0..1` 之间的贝塞尔因子;`0` 为直角,`1` 为最圆润的弧 | `0.6f` | 否 |

## 进阶用法

### Squircle 头像与卡片

`smoothing` 也让 Squircle 成为身份类表面的理想选择:使用 Squircle 形状的头像保留熟悉的圆角方形身份感,却没有普通圆角那种切点突变。

```kotlin
ElegantAvatar(
    name = "Maya Chen",
    initials = "MC",
    shape = ElegantSquircleShape(cornerRadius = 12.dp, smoothing = 0.8f),
)

ElegantSurface(shape = ElegantSquircleShape(cornerRadius = 20.dp)) {
    Column(Modifier.padding(16.dp)) {
        Text("Squircle 卡片")
    }
}
```

### 紧凑表面

对于小尺寸表面,同时减小半径与 smoothing,让 Squircle 保持圆角矩形而非药丸形的观感。

```kotlin
ElegantSurface(shape = ElegantSquircleShape(cornerRadius = 8.dp, smoothing = 0.4f)) {
    Text("紧凑 Squircle")
}
```
