# Alert

`ElegantAlert` is a non-interactive status banner for inline feedback. It combines four semantic styles, an optional icon, and a caller-owned action slot while keeping the banner itself free of focus, press, and role semantics so surrounding interactions stay predictable.

<iframe id="demoIframe" style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; border: none;" src="../compose/index.html?id=alert" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>

## Import

```kotlin
import com.elegant.compose.ui.alert.ElegantAlert
import com.elegant.compose.ui.alert.ElegantAlertColors
import com.elegant.compose.ui.alert.ElegantAlertDefaults
import com.elegant.compose.ui.alert.ElegantAlertStyle
```

## Basic Usage

Compose an alert with a required title, an optional description, and an optional leading icon. The title and description are plain text, so their semantics reach assistive technology without any extra wiring.

```kotlin
ElegantAlert(
    title = "Storage almost full",
    description = "Free up at least 500 MB to continue syncing.",
    style = ElegantAlertStyle.Warning,
    icon = {
        Icon(
            painter = painterResource(Res.drawable.share_rounded),
            contentDescription = null,
        )
    },
)
```

## Alert Styles

Pick the style that matches the message: `Neutral` for general information, `Positive` for successful outcomes, `Warning` for situations that need awareness, and `Critical` for urgent or destructive situations. Semantic styles derive tinted containers, borders, and icon colors from the active theme.

```kotlin
ElegantAlert(
    title = "Scheduled maintenance",
    description = "The service resumes at 06:00 UTC.",
)

ElegantAlert(
    title = "Backup completed",
    style = ElegantAlertStyle.Positive,
    icon = {
        Icon(
            painter = painterResource(Res.drawable.check_rounded),
            contentDescription = null,
        )
    },
)

ElegantAlert(
    title = "Low disk space",
    description = "Only 10% of the volume remains.",
    style = ElegantAlertStyle.Warning,
)

ElegantAlert(
    title = "Deployment failed",
    description = "Review the logs and retry the release.",
    style = ElegantAlertStyle.Critical,
)
```

## Component States

A blank or null `description` is omitted entirely, leaving a title-only banner. The banner itself is non-interactive: no role, focus, or press handling is added, and the `action` slot stays caller-owned, so a button placed there keeps its own focus and activation behavior.

```kotlin
ElegantAlert(
    title = "Session expired",
    description = "  ",
    style = ElegantAlertStyle.Critical,
    action = {
        ElegantButton(
            onClick = onSignInAgain,
            style = ElegantButtonStyle.Secondary,
            size = ElegantButtonSize.Small,
        ) {
            Text("Sign in")
        }
    },
)
```

## Properties

### ElegantAlert Properties

| Property Name | Type | Description | Default Value | Required |
| --- | --- | --- | --- | --- |
| `style` | `ElegantAlertStyle` | Semantic visual style | `ElegantAlertStyle.Neutral` | No |
| `modifier` | `Modifier` | Modifier applied once to the banner root | `Modifier` | No |
| `title` | `String` | Primary message shown in the banner | - | Yes |
| `description` | `String?` | Supporting message shown under the title; blank values are omitted | `null` | No |
| `icon` | `@Composable () -> Unit?` | Optional content before the title, tinted with the icon color | `null` | No |
| `action` | `@Composable () -> Unit?` | Optional content after the text column, such as a button or text link | `null` | No |
| `colors` | `ElegantAlertColors` | Theme-aware container, text, border, and icon colors | `ElegantAlertDefaults.colors(style)` | No |

### ElegantAlertStyle Values

| Value | Meaning |
| --- | --- |
| `Neutral` | General information without emphasis or severity |
| `Positive` | Successful, available, or healthy outcome |
| `Warning` | Situation that requires awareness |
| `Critical` | Urgent, failed, destructive, or critical situation |

### ElegantAlertDefaults

| Member | Type | Description |
| --- | --- | --- |
| `colors(style)` | `ElegantAlertColors` | Returns Light/Dark theme-aware colors for a semantic style |

### ElegantAlertColors

`ElegantAlertColors` contains `containerColor`, `contentColor`, `supportingColor`, `borderColor`, and `iconColor`. Start with `ElegantAlertDefaults.colors(style)` and use `copy(...)` for deliberate product-specific customization.

## Advanced Usage

Use a custom color model when the alert must match a product-specific vocabulary while preserving banner geometry and content semantics.

```kotlin
val baseColors = ElegantAlertDefaults.colors(ElegantAlertStyle.Positive)

ElegantAlert(
    title = "Deployment finished",
    description = "All services are healthy again.",
    style = ElegantAlertStyle.Positive,
    colors = baseColors.copy(
        containerColor = Color(0xFF0F766E),
        borderColor = Color(0xFF5EEAD4),
    ),
)
```
