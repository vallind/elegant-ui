# Best Practices

Patterns for building complete applications with Elegant UI.

## Wrap the Theme Once

Create one application theme that owns seed color and dark mode:

```kotlin
@Composable
fun AppTheme(content: @Composable () -> Unit) {
    val darkTheme = isSystemInDarkTheme()
    val controller = remember { ElegantThemeController(keyColor = Color(0xFF147D64)) }
    ElegantTheme(
        darkTheme = darkTheme,
        colors = if (darkTheme) controller.darkColors() else controller.lightColors(),
    ) {
        content()
    }
}
```

## Keep Components Controlled

Controlled components such as `ElegantInput`, `ElegantSwitch`, and `ElegantBottomSheet` expect the caller to own state. Keep `value` in remembered state and write changes back from the callbacks:

```kotlin
var checked by remember { mutableStateOf(false) }

ElegantSwitch(
    checked = checked,
    onCheckedChange = { checked = it },
    label = "Notifications",
)
```

## Own Dismissals

Overlay components never close themselves. Route every dismissal through `onDismissRequest`:

```kotlin
ElegantBottomSheet(
    visible = visible,
    onDismissRequest = { visible = false },
) {
    // Sheet content
}
```

## Prefer Semantic Tokens

Use `ElegantTheme.colors.*`, `ElegantTheme.typography.*`, and token objects for spacing, radii, motion, and elevation. Raw values make Light and Dark adaptation inconsistent.

## Meet Touch and Focus Baselines

Interactive roots stay at least 48dp tall; keyboard focus stays visible in both themes. Press motion must never shrink the hit target or move neighboring layout.

## Learn From the Showcase

The shared `:showcase` matrix in this repository demonstrates every component with its states and interactions. The Android, Desktop, and Web samples all render the same matrix, so a pattern verified there is verified everywhere.
