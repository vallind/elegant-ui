# SmallTitle

`ElegantSmallTitle` renders a compact non-interactive section heading for preference groups and settings surfaces. It draws the label in `labelSmall` typography with the secondary text color on a single line, truncates long text with an ellipsis, and adapts to Light and Dark themes without adding an interaction role or a semantics node of its own.

<iframe id="demoIframe" style="width: 100%; height: 460px; border: 1px solid var(--vp-c-divider); border-radius: 12px; display: block; background: var(--vp-c-bg-alt);" src="../compose/index.html?id=small-title" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>

## Import

```kotlin
import com.elegant.compose.ui.smalltitle.ElegantSmallTitle
import com.elegant.compose.ui.smalltitle.ElegantSmallTitleColors
import com.elegant.compose.ui.smalltitle.ElegantSmallTitleDefaults
```

## Basic Usage

Pass the group label directly as `text` and place the title above the related preference rows. The label uses `labelSmall` typography, so it reads as a quiet section marker without competing with the rows below it.

```kotlin
Column(
    verticalArrangement = Arrangement.spacedBy(ElegantSpacing.sm),
) {
    ElegantSmallTitle(text = "GENERAL")
    ElegantSwitchPreference(
        title = "Dark mode",
        checked = darkMode,
        onCheckedChange = { darkMode = it },
    )
}
```

## Component States

`ElegantSmallTitle` is non-interactive: it has no role, no press or focus states, and no disabled condition. It also adds no semantics node of its own, so the label text remains readable to assistive technology without announcing heading structure. Text that does not fit on one line is truncated with an ellipsis.

```kotlin
ElegantSmallTitle(
    text = "A very long section heading that does not fit on one line",
)
```

## Properties

### ElegantSmallTitle Properties

| Property Name | Type | Description | Default Value | Required |
| --- | --- | --- | --- | --- |
| `text` | `String` | Heading text rendered in `labelSmall` typography | - | Yes |
| `modifier` | `Modifier` | Modifier applied once to the title | `Modifier` | No |
| `colors` | `ElegantSmallTitleColors` | Theme-aware label colors | `ElegantSmallTitleDefaults.colors()` | No |

### ElegantSmallTitleDefaults

| Member | Type | Description |
| --- | --- | --- |
| `colors()` | `ElegantSmallTitleColors` | Returns Light/Dark theme-aware small-title colors |

### ElegantSmallTitleColors

`ElegantSmallTitleColors` contains `contentColor`. Start with `ElegantSmallTitleDefaults.colors()` and use `copy(...)` for deliberate product-specific customization.

## Advanced Usage

Customize the color model while preserving the single-line label behavior when section headings need a stronger visual emphasis.

```kotlin
val titleColors = ElegantSmallTitleDefaults.colors().copy(
    contentColor = ElegantTheme.colors.textPrimary,
)

ElegantSmallTitle(
    text = "PROMINENT",
    colors = titleColors,
)
```
