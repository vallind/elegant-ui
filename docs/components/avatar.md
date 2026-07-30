# Avatar

`ElegantAvatar` is a refined cross-platform identity component for people, teams, and entities. It provides generated initials, three optical sizes, theme-aware colors, a clipped custom-content slot, and clear image semantics without coupling the library to an image-loading framework.

<iframe id="demoIframe" style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; border: none;" src="../compose/index.html?id=avatar" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>

## Import

```kotlin
import com.elegant.compose.ui.avatar.ElegantAvatar
import com.elegant.compose.ui.avatar.ElegantAvatarColors
import com.elegant.compose.ui.avatar.ElegantAvatarDefaults
import com.elegant.compose.ui.avatar.ElegantAvatarSize
```

## Basic Usage

The required name generates a compact fallback and becomes the default accessible description.

```kotlin
ElegantAvatar(name = "Ada Lovelace")
```

## Avatar Sizes

Use `Small` in dense collections, `Medium` in standard rows and cards, and `Large` in prominent profile surfaces.

```kotlin
Row(verticalAlignment = Alignment.CenterVertically) {
    ElegantAvatar(
        name = "Small avatar",
        initials = "S",
        size = ElegantAvatarSize.Small,
    )
    ElegantAvatar(
        name = "Medium avatar",
        initials = "M",
        size = ElegantAvatarSize.Medium,
    )
    ElegantAvatar(
        name = "Large avatar",
        initials = "L",
        size = ElegantAvatarSize.Large,
    )
}
```

## Component States

When custom content is absent, `ElegantAvatarDefaults.initials(name)` uses the first and last words or the first two letters of a single word. A blank unsupported name falls back to `?`. Override `initials` for product-specific labels, or set `contentDescription = null` when the avatar is purely decorative.

```kotlin
ElegantAvatar(
    name = "Elegant UI",
    initials = "EU",
)

ElegantAvatar(
    name = "Decorative workspace",
    contentDescription = null,
)
```

Remote loading, click behavior, presence state, and status badges are intentionally separate concerns. Compose an image loader or `ElegantIconButton` with `ElegantBadgeBox` around the avatar instead of hiding those contracts inside it.

## Properties

### ElegantAvatar Properties

| Property Name | Type | Description | Default Value | Required |
| --- | --- | --- | --- | --- |
| `name` | `String` | Person or entity name used for fallback text and default semantics | - | Yes |
| `modifier` | `Modifier` | Modifier applied to the avatar container | `Modifier` | No |
| `initials` | `String` | Short fallback label shown when custom content is absent | `ElegantAvatarDefaults.initials(name)` | No |
| `contentDescription` | `String?` | Localized image description, or null for a decorative avatar | `name` | No |
| `size` | `ElegantAvatarSize` | Visual container and typography preset | `ElegantAvatarSize.Medium` | No |
| `shape` | `Shape` | Shape used to clip and outline all avatar content | `CircleShape` | No |
| `colors` | `ElegantAvatarColors` | Theme-aware container, content, and outline colors | `ElegantAvatarDefaults.colors()` | No |
| `borderWidth` | `Dp` | Optical outline width | `ElegantAvatarDefaults.BorderWidth` | No |
| `content` | `(@Composable () -> Unit)?` | Optional custom image, icon, or visual content replacing initials | `null` | No |

### ElegantAvatarSize Values

| Value | Container Size | Typography Role | Recommended Context |
| --- | --- | --- | --- |
| `Small` | `32.dp` | `labelMedium` | Dense rows and identity groups |
| `Medium` | `40.dp` | `labelLarge` | Lists, cards, and profile summaries |
| `Large` | `56.dp` | `titleMedium` | Profile headers and spacious surfaces |

### ElegantAvatarDefaults

| Member | Type | Description |
| --- | --- | --- |
| `BorderWidth` | `Dp` | Default 1dp optical outline |
| `initials(name)` | `String` | Resolves a compact fallback label from a person or entity name |
| `colors()` | `ElegantAvatarColors` | Returns Light/Dark theme-aware avatar colors |

### ElegantAvatarColors

`ElegantAvatarColors` contains `containerColor`, `contentColor`, and `borderColor`. Start with `ElegantAvatarDefaults.colors()` and use `copy(...)` when a product needs a deliberate identity tone.

## Advanced Usage

### Custom Image Content

The avatar clips custom content to the selected shape. Keep the nested image description null because the avatar owns the semantic label.

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

### Custom Identity Tone

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
