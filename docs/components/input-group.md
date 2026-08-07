# InputGroup

`ElegantInputGroup` merges adjacent fields and inline actions into one bordered cluster with a shared container, outline, and 4dp inner padding. Use it for compound controls such as amount fields, search rows, and unit pickers, where several inputs belong to one logical unit.

<iframe id="demoIframe" style="width: 100%; height: 460px; border: 1px solid var(--vp-c-divider); border-radius: 12px; display: block; background: var(--vp-c-bg-alt);" src="../compose/index.html?id=input-group" title="Demo" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin"></iframe>

## Import

```kotlin
import com.elegant.compose.ui.inputgroup.ElegantInputGroup
import com.elegant.compose.ui.inputgroup.ElegantInputGroupColors
import com.elegant.compose.ui.inputgroup.ElegantInputGroupDefaults
```

## Basic Usage

`ElegantInputGroup` is a pure container: it renders one rounded row and lets `content` supply the fields. Pair it with `ElegantInput` in the `Outlined` style so children share the cluster border instead of drawing their own.

```kotlin
var query by remember { mutableStateOf("") }

ElegantInputGroup {
    ElegantInput(
        value = query,
        onValueChange = { query = it },
        placeholder = "Search components",
        style = ElegantInputStyle.Outlined,
        modifier = Modifier.weight(1f),
    )
}
```

## Composition

Children are laid out in a `Row` behind one shared 12dp rounded border and 4dp inner padding. Each child keeps its own behavior, focus, and semantics: the group only clips the cluster to the rounded shape, applies the shared background and outline, and provides its `contentColor` to children through `LocalContentColor`.

```kotlin
var amount by remember { mutableStateOf("") }

ElegantInputGroup {
    ElegantInput(
        value = "$",
        onValueChange = {},
        readOnly = true,
        style = ElegantInputStyle.Outlined,
        modifier = Modifier.width(48.dp),
    )
    ElegantInput(
        value = amount,
        onValueChange = { amount = it },
        style = ElegantInputStyle.Outlined,
        modifier = Modifier.weight(1f),
    )
    ElegantInput(
        value = "USD",
        onValueChange = {},
        readOnly = true,
        style = ElegantInputStyle.Outlined,
        modifier = Modifier.width(56.dp),
    )
}
```

## Component States

The group itself has no interaction state: it never takes focus, shows no hover or press feedback, and declares no semantics of its own. Disabled and error states belong to the children, which keep their own visual language inside the cluster.

```kotlin
ElegantInputGroup {
    ElegantInput(
        value = "Coupon",
        onValueChange = {},
        readOnly = true,
        style = ElegantInputStyle.Outlined,
    )
    ElegantInput(
        value = "WELCOME10",
        onValueChange = {},
        style = ElegantInputStyle.Outlined,
        enabled = false,
        modifier = Modifier.weight(1f),
    )
}
```

## Properties

### ElegantInputGroup Properties

| Property Name | Type | Description | Default Value | Required |
| --- | --- | --- | --- | --- |
| `modifier` | `Modifier` | Modifier applied once to the group root | `Modifier` | No |
| `colors` | `ElegantInputGroupColors` | Theme-aware container, border, and content colors | `ElegantInputGroupDefaults.colors()` | No |
| `content` | `@Composable RowScope.() -> Unit` | Fields and inline actions rendered inside the cluster | - | Yes |

### ElegantInputGroupDefaults

| Member | Type | Description |
| --- | --- | --- |
| `colors()` | `ElegantInputGroupColors` | Theme-aware Light/Dark colors: raised container, default border, primary content |

### ElegantInputGroupColors

`ElegantInputGroupColors` contains the cluster container color, the shared border color, and the content color provided to children through `LocalContentColor`. Start with `ElegantInputGroupDefaults.colors()` and use `copy(...)` only for a deliberate product-specific hierarchy.

## Advanced Usage

### Search Row with Action Suffix

A trailing action can sit inside the same cluster as a plain label; the group's `contentColor` keeps it aligned with the theme.

```kotlin
var query by remember { mutableStateOf("") }

ElegantInputGroup(modifier = Modifier.fillMaxWidth()) {
    ElegantInput(
        value = query,
        onValueChange = { query = it },
        placeholder = "Search releases",
        style = ElegantInputStyle.Outlined,
        modifier = Modifier.weight(1f),
    )
    Row(
        modifier = Modifier
            .defaultMinSize(minHeight = 48.dp)
            .padding(horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = "Search",
            color = ElegantTheme.colors.interactivePrimary,
            style = ElegantTheme.typography.labelMedium,
        )
    }
}
```
