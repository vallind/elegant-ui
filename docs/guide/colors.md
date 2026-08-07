# Colors

`ElegantColors` is the semantic color model every component consumes. Components never hardcode raw colors; they resolve theme roles so Light and Dark palettes stay consistent.

## Semantic Roles

| Group | Roles | Purpose |
| :--- | :--- | :--- |
| Canvas | `backgroundCanvas`, `backgroundSubtle` | App and page backgrounds |
| Surface | `surfaceDefault`, `surfaceRaised`, `surfaceSunken`, `surfaceHover` | Containers, cards, raised panels, pressed rows |
| Text | `textPrimary`, `textSecondary`, `textTertiary`, `textInverse` | Content hierarchy and inverted-on-primary text |
| Border | `borderDefault`, `borderStrong` | Hairlines and strong separators |
| Interaction | `interactivePrimary`, `interactivePrimaryPressed`, `interactivePrimaryHover` | Primary actions across their interaction states |
| Focus | `focusRing` | Keyboard focus visibility (opt-in via `ElegantTheme(focusRingEnabled = true)`) |
| Status | `statusPositive`, `statusWarning`, `statusCritical` + `onStatus*` | Semantic feedback tones |

## Reading Roles

```kotlin
Text(
    text = "Primary content",
    color = ElegantTheme.colors.textPrimary,
)
```

## State Resolution

Roles resolve through a fixed precedence: disabled or transition-locked, pressed or dragged, keyboard focused, pointer hovered, resting. Semantic states such as selected, checked, error, loading, and expanded combine with interaction visuals.

## Accessibility

Focus rings must stay visible in Light and Dark themes and never rely on color alone. Text and interactive surfaces must meet contrast baselines in both palettes.

## Dynamic Palettes

`ElegantThemeController` derives the full role set from one seed color. See [Theme](./theme).
