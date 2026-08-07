# BasicComponent

A settings-style row base that composes leading content, a title block, trailing controls, and an optional bottom block into one interactive row. Use it to build custom settings rows that stay consistent with the preference family.

<iframe id="demoIframe" src="../compose/index.html?id=basic-component" style="width: 100%; height: 460px; border: 1px solid var(--vp-c-divider); border-radius: 12px; display: block; background: var(--vp-c-bg-alt);"></iframe>

## Import

```kotlin
import com.elegant.compose.ui.basiccomponent.ElegantBasicComponent
import com.elegant.compose.ui.basiccomponent.ElegantBasicComponentDefaults
```

## Basic Usage

Provide `title` and `summary`; the row stays plain until an `onClick` is added, which makes the whole row the interactive target.

```kotlin
ElegantBasicComponent(
    title = "Airplane mode",
    summary = "Turn off all wireless connections",
    startAction = {
        Icon(
            imageVector = ElegantIcons.Settings,
            contentDescription = null,
        )
    },
    endActions = {
        ElegantSwitch(
            checked = enabled,
            onCheckedChange = { enabled = it },
        )
    },
    onClick = { /* open settings */ },
)
```

## Component States

- **Interactive**: providing `onClick` raises the row to a 48dp minimum height, enables hovered and pressed container colors, and announces the button role.
- **Hold down**: `holdDownState` forces the pressed visual while true.
- **Disabled**: with `enabled = false` the row refuses clicks and text colors fall back to the disabled roles.

## Properties

| Property Name | Type | Description | Default Value | Required |
| :--- | :--- | :--- | :--- | :--- |
| `title` | `String?` | Optional row title; rendered with the summary unless `content` is provided. | `null` | No |
| `modifier` | `Modifier` | Applied once to the interactive row. | `Modifier` | No |
| `summary` | `String?` | Optional summary text below the title. | `null` | No |
| `startAction` | `(@Composable () -> Unit)?` | Optional leading content before the title block. | `null` | No |
| `endActions` | `(@Composable RowScope.() -> Unit)?` | Optional trailing content after the title block. | `null` | No |
| `bottomAction` | `(@Composable () -> Unit)?` | Optional content rendered below the row. | `null` | No |
| `onClick` | `(() -> Unit)?` | Optional activation callback; null keeps the row plain. | `null` | No |
| `onClickLabel` | `String?` | Optional accessible label describing the row action. | `null` | No |
| `role` | `Role?` | Optional semantic role announced for the row. | `Role.Button` when interactive | No |
| `holdDownState` | `Boolean` | Forces the pressed visual state while true. | `false` | No |
| `enabled` | `Boolean` | Whether user interaction is accepted. | `true` | No |
| `colors` | `ElegantBasicComponentColors` | Theme-aware state colors. | `ElegantBasicComponentDefaults.colors()` | No |
| `insideMargin` | `PaddingValues` | Padding inside the row. | `ElegantBasicComponentDefaults.InsideMargin` | No |
| `content` | `@Composable () -> Unit` | Optional replacement for the standard title and summary block. | `{}` | No |

## Advanced Usage

### Custom content block

Replace the standard text block with any composable:

```kotlin
ElegantBasicComponent(
    onClick = { },
    content = {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Custom block", modifier = Modifier.weight(1f))
            ElegantBadge(text = "New")
        }
    },
)
```

### Bottom helper content

```kotlin
ElegantBasicComponent(
    title = "Storage",
    summary = "Manage space usage",
    onClick = { },
    bottomAction = {
        Text(
            text = "42.5 GB of 128 GB used",
            style = ElegantTheme.typography.bodyMedium,
            color = ElegantTheme.colors.textTertiary,
        )
    },
)
```
