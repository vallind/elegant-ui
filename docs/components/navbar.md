# Navbar

`ElegantNavbar` is a top app-bar container that hosts an optional navigation icon, a title, and trailing actions. Use it at the top of a screen as a non-interactive chrome surface whose children keep full ownership of their semantics, text style, and interaction.

<iframe id="demoIframe" style="width: 100%; height: 460px; border: 1px solid var(--vp-c-divider); border-radius: 12px; display: block; background: var(--vp-c-bg-alt);" src="../compose/index.html?id=navbar" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>

## Import

```kotlin
import com.elegant.compose.ui.navbar.ElegantNavbar
import com.elegant.compose.ui.navbar.ElegantNavbarColors
import com.elegant.compose.ui.navbar.ElegantNavbarDefaults
```

## Basic Usage

The bar renders at a 56dp height with 16dp horizontal padding and a 1dp bottom border. The optional navigation icon sits at the logical start (it mirrors automatically in RTL) and is separated from the title by a 4dp gap; the title occupies the remaining width and the actions follow at the end. The bar provides the content color through `LocalContentColor` but applies no text style, so the title should set its own style such as `ElegantTheme.typography.titleMedium`.

```kotlin
ElegantNavbar(
    navigationIcon = {
        ElegantIconButton(
            onClick = { /* open navigation drawer */ },
            contentDescription = "Open navigation",
        ) {
            Icon(Icons.Default.Menu, contentDescription = null)
        }
    },
    title = {
        Text(
            text = "Home",
            style = ElegantTheme.typography.titleMedium,
        )
    },
    actions = {
        ElegantIconButton(
            onClick = { /* share current page */ },
            contentDescription = "Share",
        ) {
            Icon(Icons.Default.Share, contentDescription = null)
        }
    },
)
```

## Component States

The bar itself is non-interactive: it defines no role, owns no focus, and has no hover, press, or disabled visuals, and it never merges or clears the semantics of its content. State behavior belongs entirely to the children, so a disabled action inside the bar announces its own state while the bar keeps rendering the same chrome.

```kotlin
ElegantNavbar(
    title = {
        Text(
            text = "Draft",
            style = ElegantTheme.typography.titleMedium,
        )
    },
    actions = {
        ElegantIconButton(
            onClick = { /* publish draft */ },
            contentDescription = "Publish",
            enabled = false,
        ) {
            Icon(Icons.Default.Check, contentDescription = null)
        }
    },
)
```

## Properties

### ElegantNavbar Properties

| Property Name | Type | Description | Default Value | Required |
| --- | --- | --- | --- | --- |
| `navigationIcon` | `(@Composable () -> Unit)?` | Optional leading content placed before the title | `null` | No |
| `modifier` | `Modifier` | Modifier applied once to the bar root | `Modifier` | No |
| `title` | `@Composable () -> Unit` | Title content; the text style is caller-owned | - | Yes |
| `actions` | `@Composable RowScope.() -> Unit` | Trailing action content laid out in row scope | `{}` | No |
| `colors` | `ElegantNavbarColors` | Theme-aware container, content, and border colors | `ElegantNavbarDefaults.colors()` | No |

### ElegantNavbarDefaults

| Member | Type | Description |
| --- | --- | --- |
| `Height` | `Dp` | Standard 56dp bar height |
| `HorizontalPadding` | `Dp` | 16dp horizontal breathing room at both bar edges |
| `ItemGap` | `Dp` | 4dp gap between the navigation icon and the title |
| `colors()` | `ElegantNavbarColors` | Theme-aware Light/Dark colors |

### ElegantNavbarColors

`ElegantNavbarColors` contains the container color (bar background), the content color (provided to the title through `LocalContentColor`), and the border color (1dp bottom separator). Start with `ElegantNavbarDefaults.colors()` and use `copy(...)` only for a deliberate product-specific hierarchy.

## Advanced Usage

### Document Bar Composition

Combine `ElegantNavbar` with `ElegantDivider` and body text to build a document page. The navigation icon mirrors to the logical start automatically in RTL.

```kotlin
Column {
    ElegantNavbar(
        navigationIcon = {
            ElegantIconButton(
                onClick = { /* navigate back */ },
                contentDescription = "Back",
            ) {
                Icon(Icons.Default.ArrowBack, contentDescription = null)
            }
        },
        title = {
            Text(
                text = "Settings",
                style = ElegantTheme.typography.titleMedium,
            )
        },
        actions = {
            ElegantIconButton(
                onClick = { /* open more options */ },
                contentDescription = "More options",
            ) {
                Icon(Icons.Default.MoreVert, contentDescription = null)
            }
        },
    )
    ElegantDivider()
    Text(
        text = "A document page that places a navbar above a divider and body content.",
        modifier = Modifier.padding(16.dp),
        style = ElegantTheme.typography.bodyMedium,
    )
}
```

### Custom Colors

```kotlin
ElegantNavbar(
    title = {
        Text(
            text = "Brand",
            style = ElegantTheme.typography.titleMedium,
        )
    },
    colors = ElegantNavbarDefaults.colors().copy(
        containerColor = Color(0xFF6C4EFF),
        contentColor = Color.White,
        borderColor = Color.Transparent,
    ),
)
```
