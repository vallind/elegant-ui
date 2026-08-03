# CheckboxPreference

`ElegantCheckboxPreference` is a settings-row component that pairs a title block with an end-anchored checkbox, following the preference row pattern shared with `ElegantSwitchPreference`. Use it for multi-select settings where each row is one independent option.

<iframe id="demoIframe" style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; border: none;" src="../compose/index.html?id=checkbox-preference" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>

## Import

```kotlin
import com.elegant.compose.ui.preference.ElegantCheckboxPreference
import com.elegant.compose.ui.preference.ElegantCheckboxPreferenceColors
import com.elegant.compose.ui.preference.ElegantCheckboxPreferenceDefaults
```

## Basic Usage

A preference row keeps a 48dp minimum height, a two-line title block, and a trailing checkbox that owns the toggle semantics.

```kotlin
ElegantCheckboxPreference(
    title = "Camera",
    checked = cameraEnabled,
    onCheckedChange = { cameraEnabled = it },
    supportingText = "Allow photo and video capture",
)
```

## Component States

The row itself is not clickable; the checkbox owns the toggle interaction and announces its checked state. Disabled rows dim the title to the disabled role, pass `enabled = false` to the checkbox, and never invoke `onCheckedChange`. The bottom divider (inset 16dp from the start edge) renders between rows; pass `showDivider = false` for the last row of a group.

```kotlin
ElegantCheckboxPreference(
    title = "Microphone",
    checked = false,
    onCheckedChange = {},
    enabled = false,
    supportingText = "Currently unavailable",
    showDivider = false,
)
```

## Properties

### ElegantCheckboxPreference Properties

| Property Name | Type | Description | Default Value | Required |
| --- | --- | --- | --- | --- |
| `title` | `String` | Row title | - | Yes |
| `checked` | `Boolean` | Whether the option is checked | - | Yes |
| `onCheckedChange` | `(Boolean) -> Unit` | Callback with the new checked state | - | Yes |
| `modifier` | `Modifier` | Modifier applied once to the row root | `Modifier` | No |
| `supportingText` | `String?` | Optional supporting line below the title | `null` | No |
| `enabled` | `Boolean` | Whether the option accepts interaction | `true` | No |
| `colors` | `ElegantCheckboxPreferenceColors` | Theme-aware row colors | `ElegantCheckboxPreferenceDefaults.colors()` | No |
| `showDivider` | `Boolean` | Whether a bottom divider is drawn | `true` | No |

### ElegantCheckboxPreferenceColors

`ElegantCheckboxPreferenceColors` contains `containerColor` (reserved), `titleColor`, `supportingTextColor`, `disabledTitleColor`, and `dividerColor`. Start with `ElegantCheckboxPreferenceDefaults.colors()` and use `copy(...)` only for a deliberate product-specific hierarchy.

## Advanced Usage

### Grouped Permissions

Stack rows inside a card and hide the divider on the last row.

```kotlin
ElegantCard {
    Column {
        ElegantCheckboxPreference(
            title = "Camera",
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
            title = "Photos",
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
