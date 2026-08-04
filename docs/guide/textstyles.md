# Text Styles

`ElegantTypography` owns five text roles shared by every component on every target.

## Roles

| Role | Size | Use |
| :--- | :--- | :--- |
| `labelSmall` | 12sp | Compact component labels |
| `labelMedium` | 14sp | Default component labels |
| `labelLarge` | 16sp | Prominent component labels |
| `bodyMedium` | 14sp | Default supporting content |
| `titleMedium` | 16sp | Standard section titles |

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
