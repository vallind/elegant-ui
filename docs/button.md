# Button

Buttons trigger an immediate action. Elegant Button uses three emphasis levels, three sizes, complete interaction states, and a minimum 48dp touch target.

## API

```kotlin
ElegantButton(
    onClick = { /* action */ },
    style = ElegantButtonStyle.Primary,
    size = ElegantButtonSize.Medium,
    enabled = true,
    loading = false,
    leadingIcon = { /* optional */ },
    trailingIcon = { /* optional */ },
) {
    Text("Continue")
}
```

## Styles

- `Primary`: the single dominant action in a surface or task.
- `Secondary`: supporting actions with a visible container and border.
- `Tertiary`: low-emphasis actions, links, or contextual commands.

## Sizes

| Size | Visual height | Minimum touch height | Horizontal padding |
|---|---:|---:|---:|
| Small | 36dp | 48dp | 12dp |
| Medium | 40dp | 48dp | 16dp |
| Large | 48dp | 48dp | 20dp |

## States

Default, pressed, focused, disabled, and loading are supported. A loading button is not clickable and preserves its semantic button role.

## Accessibility

- Minimum touch height: 48dp.
- Button role is exposed through semantics.
- Disabled state is exposed through semantics.
- Loading exposes a state description.
- Icons are decorative by default; provide a description only when they convey information not present in the label.
