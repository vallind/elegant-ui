# Text Styles

`ElegantTypography` owns ten text roles shared by every component on every target. The five base roles cover compact labels, supporting content, and section titles; the extended roles add the HyperOS reading scale (17sp body and large display sizes) used by app bars and large content surfaces.

## Roles

| Role | Size | Use |
| :--- | :--- | :--- |
| `labelSmall` | 12sp | Compact component labels |
| `labelMedium` | 14sp | Default component labels |
| `labelLarge` | 16sp | Prominent component labels |
| `bodyMedium` | 14sp | Default supporting content |
| `bodyLarge` | 17sp | Large supporting content |
| `footnote` | 13sp | Small annotations |
| `titleMedium` | 18sp | Standard section titles |
| `titleLarge` | 20sp | Prominent section titles, collapsed app-bar titles |
| `titleXl` | 24sp | Large section titles |
| `headlineLarge` | 32sp | Display titles, expanded large app-bar titles |

## Reading Styles

```kotlin
Text(
    text = "Section title",
    style = ElegantTheme.typography.titleMedium,
)
```

## Typography in Components

Components apply `ElegantTheme.typography.*` for their default text styles and accept a caller-provided `style` where text customization matters:

```kotlin
Text(
    text = "Label",
    style = ElegantTheme.typography.labelMedium,
    color = ElegantTheme.colors.textSecondary,
)
```

## Consistency Rules

- Components never hardcode font sizes or weights; always read `ElegantTheme.typography`.
- Text hierarchy stays stable in Light and Dark themes and across font-scaling changes.
- Combine with color roles from [Colors](./colors) for the full semantic contract.
