# Elegant UI

Refined Compose Multiplatform UI component library for **Android (API 24+)**, **Desktop JVM**, and **Web/Wasm**.

[![Kotlin](https://img.shields.io/badge/kotlin-2.4.10-7F52FF)](https://kotlinlang.org/)
[![Compose Multiplatform](https://img.shields.io/badge/compose-1.11.1-4285F4)](https://kotlinlang.org/compose-multiplatform/)
[![License](https://img.shields.io/github/license/vallind/elegant-ui)](LICENSE)

## Features

- **75 components** across foundations, forms, content, navigation, feedback, and overlays
- One shared contract on Android, Desktop JVM, and Web/Wasm from a single `commonMain`
- Theme-aware semantic colors, typography, spacing, radius, motion, and elevation tokens
- Fully themed and semantically adapted — no Material component is exposed as the public contract
- Bilingual documentation (English + Simplified Chinese) with real Compose Web demos

## Components

| Group | Components |
| :--- | :--- |
| Foundations | Button, IconButton, Avatar, Badge, Divider, Tag, Tooltip, ProgressIndicator, Skeleton, Alert, Snackbar, Toast, Link, Kbd, Label, Surface, Spinner, ScrollShadow, CloseButton, SmallTitle, Calendar |
| Forms | Input, Textarea, NumberField, SearchBar, Checkbox, Radio, Switch, Slider, Select, Autocomplete, InputGroup, InputOtp, ToggleButton, ButtonGroup, CheckboxGroup, RadioGroup, SwitchGroup, TagGroup, Meter, Description, CheckboxPreference, SwitchPreference, RadioPreference, SliderPreference, ArrowPreference, Fieldset, ColorPicker, NumberPicker |
| Content | Card, List, EmptyState, Table, Pagination, Accordion, Disclosure, Popover, Menu, DatePicker, DateRangePicker, FloatingToolbar, Toolbar, FloatingActionButton, PullToRefresh, ScrollBar |
| Navigation | Navbar, Sidebar, Tabs, Breadcrumb, NavigationBar, NavigationRail, Scaffold, ArrowPreference, AlertDialog, Drawer, Modal |

## Getting Started

```kotlin
kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation("io.github.vallind:elegant-ui:0.1.0-SNAPSHOT")
        }
    }
}
```

Or use the same-build dependency:

```kotlin
implementation(project(":elegant-ui"))
```

```kotlin
@Composable
fun App() {
    ElegantTheme {
        ElegantButton(onClick = { /* ... */ }) {
            Text("Hello")
        }
    }
}
```

## Documentation

- [Component gallery](https://vallind.github.io/elegant-ui/components/)
- [Getting started](https://vallind.github.io/elegant-ui/guide/getting-started)
- Chinese mirror: `https://vallind.github.io/elegant-ui/zh_CN/`

## Development

```bash
gradle :elegant-ui:build --no-daemon          # library + tests
gradle :showcase:build --no-daemon            # shared showcase
gradle :desktop-sample:createDistributable    # desktop launcher
gradle :web-sample:wasmJsBrowserDistribution  # web launcher
```

GitHub Actions is the authoritative clean environment for Android assembly, browser tests, and final artifacts. See `AGENTS.md` for the full repository guidance and component workflows.

## Acknowledgments

The HyperOS visual language behind the v0.2 feedback baseline — overlay indication, Folme springs, squircle shapes, and density dimensions — is ported from [Miuix](https://github.com/yukonga/Miuix) (Apache-2.0) by compose-miuix-ui contributors. The ported foundation files retain their copyright headers and attribution.

## License

Apache-2.0
