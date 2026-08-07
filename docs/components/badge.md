# Badge

`ElegantBadge` is a compact cross-platform status system for labels, presence, and counts. It combines five semantic tones, three optical sizes, predictable overflow formatting, accessible dot and count APIs, and RTL-aware corner placement without turning status into an interactive control.

<iframe id="demoIframe" style="width: 100%; height: 460px; border: 1px solid var(--vp-c-divider); border-radius: 12px; display: block; background: var(--vp-c-bg-alt);" src="../compose/index.html?id=badge" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>

## Import

```kotlin
import com.elegant.compose.ui.badge.ElegantBadge
import com.elegant.compose.ui.badge.ElegantBadgeBox
import com.elegant.compose.ui.badge.ElegantBadgeColors
import com.elegant.compose.ui.badge.ElegantBadgeDefaults
import com.elegant.compose.ui.badge.ElegantBadgeDot
import com.elegant.compose.ui.badge.ElegantBadgePlacement
import com.elegant.compose.ui.badge.ElegantBadgeSize
import com.elegant.compose.ui.badge.ElegantBadgeStyle
import com.elegant.compose.ui.badge.ElegantCountBadge
```

## Basic Usage

Compose a localized count over an existing control. The badge does not change the measured size or interaction contract of its content.

```kotlin
ElegantBadgeBox(
    badge = {
        ElegantCountBadge(
            count = unreadCount,
            contentDescription = "$unreadCount unread messages",
            size = ElegantBadgeSize.Small,
        )
    },
) {
    ElegantIconButton(
        onClick = onOpenInbox,
        contentDescription = "Open inbox",
    ) {
        Icon(
            painter = painterResource(Res.drawable.inbox),
            contentDescription = null,
        )
    }
}
```

## Badge Modes

Use `ElegantBadge` for a short label, `ElegantBadgeDot` for presence or status, and `ElegantCountBadge` for numeric overflow behavior. Badge content is intentionally non-interactive; compose actions around it instead of placing click handling inside it.

```kotlin
ElegantBadge(style = ElegantBadgeStyle.Neutral) {
    Text("Beta")
}

ElegantBadgeDot(
    contentDescription = "Online",
    style = ElegantBadgeStyle.Positive,
)

ElegantCountBadge(
    count = 120,
    maxCount = 99, // Displays 99+
    contentDescription = "More than 99 alerts",
    style = ElegantBadgeStyle.Critical,
)
```

## Placement

`ElegantBadgeBox` centers the badge on a logical corner. Start and end mirror automatically in RTL. The badge may draw outside the layout bounds, so avoid clipping its parent when that overflow must remain visible.

```kotlin
ElegantBadgeBox(
    badge = {
        ElegantBadgeDot(
            contentDescription = "Available",
            style = ElegantBadgeStyle.Positive,
        )
    },
    placement = ElegantBadgePlacement.BottomEnd,
) {
    ElegantAvatar(name = "Maya Chen")
}
```

## Component States

Counts at or below zero are hidden by default. Set `showZero = true` to retain a visible zero. Negative values resolve to `0`, and non-positive `maxCount` values are safely treated as `1`. A null label preserves descendant semantics, while an empty `contentDescription` makes label or count content decorative.

```kotlin
ElegantCountBadge(
    count = 0,
    showZero = true,
    contentDescription = "No unread updates",
)

ElegantCountBadge(
    count = failedJobs,
    contentDescription = "$failedJobs failed jobs",
    style = ElegantBadgeStyle.Critical,
)
```

## Properties

### ElegantBadge Properties

| Property Name | Type | Description | Default Value | Required |
| --- | --- | --- | --- | --- |
| `modifier` | `Modifier` | Modifier applied once to the badge container | `Modifier` | No |
| `contentDescription` | `String?` | Localized label; null preserves content semantics and an empty value makes content decorative | `null` | No |
| `style` | `ElegantBadgeStyle` | Semantic visual style | `ElegantBadgeStyle.Accent` | No |
| `size` | `ElegantBadgeSize` | Optical container and typography preset | `ElegantBadgeSize.Medium` | No |
| `shape` | `Shape` | Clipping and outline shape | `ElegantBadgeDefaults.shape()` | No |
| `colors` | `ElegantBadgeColors` | Theme-aware container, content, and outline colors | `ElegantBadgeDefaults.colors(style)` | No |
| `borderWidth` | `Dp` | Optical outline width | `ElegantBadgeDefaults.BorderWidth` | No |
| `content` | `@Composable () -> Unit` | Short label, number, icon, or custom badge content | - | Yes |

### ElegantBadgeDot Properties

| Property Name | Type | Description | Default Value | Required |
| --- | --- | --- | --- | --- |
| `modifier` | `Modifier` | Modifier applied once to the dot | `Modifier` | No |
| `contentDescription` | `String?` | Localized status description; null or blank makes the dot decorative | `null` | No |
| `style` | `ElegantBadgeStyle` | Semantic visual style | `ElegantBadgeStyle.Accent` | No |
| `size` | `ElegantBadgeSize` | Optical dot size preset | `ElegantBadgeSize.Medium` | No |
| `shape` | `Shape` | Clipping and outline shape | `ElegantBadgeDefaults.shape()` | No |
| `colors` | `ElegantBadgeColors` | Theme-aware indicator and outline colors | `ElegantBadgeDefaults.colors(style)` | No |
| `borderWidth` | `Dp` | Optical outline width | `ElegantBadgeDefaults.BorderWidth` | No |

### ElegantCountBadge Properties

| Property Name | Type | Description | Default Value | Required |
| --- | --- | --- | --- | --- |
| `count` | `Int` | Current numeric count | - | Yes |
| `modifier` | `Modifier` | Modifier applied once to the visible badge | `Modifier` | No |
| `maxCount` | `Int` | Largest value displayed without a `+` suffix | `ElegantBadgeDefaults.DefaultMaxCount` | No |
| `showZero` | `Boolean` | Whether zero and negative counts remain visible as `0` | `false` | No |
| `contentDescription` | `String?` | Localized semantic description | `ElegantBadgeDefaults.countLabel(count, maxCount)` | No |
| `style` | `ElegantBadgeStyle` | Semantic visual style | `ElegantBadgeStyle.Accent` | No |
| `size` | `ElegantBadgeSize` | Optical container and typography preset | `ElegantBadgeSize.Medium` | No |
| `shape` | `Shape` | Clipping and outline shape | `ElegantBadgeDefaults.shape()` | No |
| `colors` | `ElegantBadgeColors` | Theme-aware container, content, and outline colors | `ElegantBadgeDefaults.colors(style)` | No |
| `borderWidth` | `Dp` | Optical outline width | `ElegantBadgeDefaults.BorderWidth` | No |

### ElegantBadgeBox Properties

| Property Name | Type | Description | Default Value | Required |
| --- | --- | --- | --- | --- |
| `badge` | `@Composable () -> Unit` | Status, count, or custom badge displayed over the content | - | Yes |
| `modifier` | `Modifier` | Modifier applied once to the content-sized layout root | `Modifier` | No |
| `placement` | `ElegantBadgePlacement` | Logical corner used to anchor the badge | `ElegantBadgePlacement.TopEnd` | No |
| `content` | `@Composable () -> Unit` | Content receiving the overlay | - | Yes |

### ElegantBadgeStyle Values

| Value | Meaning |
| --- | --- |
| `Neutral` | Quiet metadata without action or severity |
| `Accent` | Brand-accent status or count requiring emphasis |
| `Positive` | Successful, available, or healthy status |
| `Warning` | Status requiring awareness |
| `Critical` | Urgent, failed, destructive, or critical status |

### ElegantBadgeSize Values

| Value | Label Minimum | Dot Size | Typography |
| --- | --- | --- | --- |
| `Small` | `18.dp` | `6.dp` | `labelSmall` |
| `Medium` | `22.dp` | `8.dp` | `labelSmall` |
| `Large` | `26.dp` | `10.dp` | `labelMedium` |

The compact 2dp increments are an intentional optical exception to the standard 4dp grid.

### ElegantBadgePlacement Values

`TopStart`, `TopEnd`, `BottomStart`, and `BottomEnd` use logical edges and therefore mirror in RTL.

### ElegantBadgeDefaults

| Member | Type | Description |
| --- | --- | --- |
| `BorderWidth` | `Dp` | Default 1dp optical outline |
| `DefaultMaxCount` | `Int` | Default overflow threshold of 99 |
| `countLabel(count, maxCount)` | `String` | Coerces invalid input and returns the compact count label |
| `shape()` | `Shape` | Returns the fully rounded badge shape |
| `colors(style)` | `ElegantBadgeColors` | Returns Light/Dark theme-aware colors for a semantic style |

### ElegantBadgeColors

`ElegantBadgeColors` contains `containerColor`, `contentColor`, and `borderColor`. Start with `ElegantBadgeDefaults.colors(style)` and use `copy(...)` for deliberate product-specific customization.

## Advanced Usage

Use a custom color model when status belongs to a product-specific vocabulary while preserving badge geometry and semantics.

```kotlin
val baseColors = ElegantBadgeDefaults.colors(ElegantBadgeStyle.Accent)

ElegantBadge(
    contentDescription = "Experimental feature",
    colors = baseColors.copy(
        containerColor = Color(0xFF0F766E),
        contentColor = Color.White,
        borderColor = Color(0xFF5EEAD4),
    ),
) {
    Text("Experimental")
}
```
