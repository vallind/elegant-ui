# Elegant UI

Refined Compose Multiplatform UI component library. Targets Android (API 24+), Desktop (JVM), Web (JS + Wasm). iOS is out of scope.

## Quick Start

- For significant features or refactors, sketch a plan first and keep it updated as you work.
- Run the component-specific checks below before handing work off; do not skip failing steps.
- Read the closest source, tests, sample, docs pages, and workflows before editing; verify unstable tooling facts against current official Kotlin, Compose Multiplatform, Android, Gradle, VitePress, and GitHub Actions documentation.

## Key Commands

| Action | Command |
| :--- | :--- |
| Build library (full) | `gradle :elegant-ui:build --stacktrace --no-daemon` |
| Build library (quick check) | `gradle :elegant-ui:compileKotlinDesktop --no-daemon` |
| Test (desktop) | `gradle :elegant-ui:desktopTest --no-daemon` |
| Build showcase | `gradle :showcase:build --stacktrace --no-daemon` |
| Publish KMP repository | `gradle :elegant-ui:publishAllPublicationsToBuildRepository --stacktrace --no-daemon` |
| Run Android sample | `gradle :sample:assembleDebug --stacktrace --no-daemon` |
| Run Desktop sample | `gradle :desktop-sample:createDistributable --stacktrace --no-daemon` |
| Run Windows desktop sample | `gradle :desktop-sample:packageZip --stacktrace --no-daemon` (Windows host) |
| Run Web/Wasm sample | `gradle :web-sample:wasmJsBrowserDistribution --stacktrace --no-daemon` |
| Run Web/JS sample | `gradle :web-sample:jsBrowserDistribution --stacktrace --no-daemon` |
| Validate docs | `cd docs && npm install && npm run docs:check && npm run docs:build` |

GitHub Actions is the authoritative clean environment when local Android SDK, browser runtime, or packaging tools are unavailable. Only claim a command passed when it actually ran successfully.

## Repository Structure

| Directory | Purpose |
| :--- | :--- |
| `elegant-ui/` | Main UI library (Android, Desktop JVM, Web/JS + Web/Wasm KMP targets) |
| `showcase/` | Shared component gallery and component-slug registry |
| `sample/` | Android application launcher |
| `desktop-sample/` | Desktop JVM launcher |
| `web-sample/` | Compose Web/JS + Web/Wasm launcher and documentation iframe runtime |
| `docs/` | VitePress website, English root and `zh_CN` mirror |
| `scripts/` | Multiplatform boundary validation script (run by CI) |
| `.github/workflows/` | Multiplatform build and documentation workflows |

### Component Source Layout

`elegant-ui/src/commonMain/kotlin/com/elegant/compose/ui/`:

| Subdir | Contents |
| :--- | :--- |
| `button/`, `iconbutton/`, `avatar/`, `badge/`, `divider/`, `tag/` | Available components, one package each |
| `internal/` | Shared internal primitives (action-state resolution, etc.) |
| `theme/` | ElegantTheme, ElegantColors, typography, spacing/radius/motion/elevation tokens |

### Platform Source Sets

```
commonMain
├── androidMain
├── desktopMain
└── wasmJsMain
```

99% of UI logic lives in `commonMain`; platform source sets are only for genuinely platform-specific code. Common/public code must not import Android framework types, AWT/Swing, browser DOM, or `kotlinx.browser`. `androidx.compose.*` APIs are allowed only when the artifact is available to Compose Multiplatform `commonMain`. Never recreate `elegant-ui/src/main` or `showcase/src/main`.

## Code Style

- 4-space indentation; no mandatory formatter.
- New `.kt` files carry the repository license header:
  `// Copyright 2026, elegant-ui contributors` + `// SPDX-License-Identifier: Apache-2.0`
  (existing files are not retrofitted).
- Public KDoc on every public declaration. No comments beyond what KDoc requires.
- Public names use the `ElegantXxx` prefix; composables and enums use PascalCase.
- Standard `List`/`Set`/`Map` parameters are unstable to Compose — prefer stable models, a
  caller-owned identity that is remembered outside composition, or clearly documented identity
  contracts; do not add a dependency solely to decorate one API.

## API Conventions

### Composable Function Signature

Follow this parameter ordering:

```kotlin
@Composable
public fun ElegantComponent(
    value: Value,                         // 1. required behavior/state parameters
    onValueChange: (Value) -> Unit,
    modifier: Modifier = Modifier,        // 2. modifier
    enabled: Boolean = true,              // 3. state flags and controlled values
    loading: Boolean = false,
    style: ElegantComponentStyle = ...,   // 4. visual parameters
    colors: ElegantComponentColors = ElegantComponentDefaults.colors(),
    leadingContent: (@Composable () -> Unit)? = null, // 5. optional slots
    content: @Composable () -> Unit,      // 6. primary content lambda last
)
```

Prefer additive overloads or defaulted trailing parameters. Do not expose an unmodified Material component as the Elegant UI public contract.

### Defaults Object

Each component provides an `ElegantXxxDefaults` object:

```kotlin
public object ElegantButtonDefaults {
    public val MinimumTouchHeight: Dp = 48.dp      // constant dimensions as val

    @Composable
    public fun colors(                              // color factories must be @Composable
        style: ElegantButtonStyle = ElegantButtonStyle.Primary,
    ): ElegantButtonColors = resolveButtonColors(style, ElegantTheme.colors)
}
```

### Colors Data Class

```kotlin
@Immutable
public data class ElegantButtonColors(
    val containerColor: Color,
    val pressedContainerColor: Color,
    ...
)
```

### Key Patterns

- **`rememberUpdatedState`** for values whose latest reading must be visible to a long-lived closure without re-running an effect or rebuilding a Modifier: inside `LaunchedEffect`/`DisposableEffect` and inside `remember { }`-cached lambdas. Do NOT use it when forwarding a callback directly to a child composable.
- **`remember` with keys** for derived values.
- **`@NonRestartableComposable`** on thin wrapper composables that fully delegate and read no state themselves.
- **`@Immutable`** requires genuinely immutable fields: all `val`, never mutated, and no lambda/callback fields (lambda equality is reference-based). Use `@Stable` for models holding lambdas or notifying via `MutableState`.
- **Theme colors**: always `ElegantTheme.colors.*`, never hardcoded (raw colors belong only in theme files).
- **Text styles**: always `ElegantTheme.typography.*`.
- **Shapes**: `ElegantRadius` tokens with `RoundedCornerShape(...)`; `CircleShape` for capsules.
- **Motion**: `ElegantMotion` durations; **spacing**: 4dp grid (`ElegantSpacing`).
- **Hoist pure logic** (state precedence, formatting, coercion, fallback) into internal functions so `commonTest` can cover it without a UI harness.
- Keep side effects out of composition; key effects only with values read by the effect.

## Critical Constraints

- **Do NOT expose a Material component directly.** Reuse Material/foundation primitives internally only when they are available to all supported targets and can be fully themed and semantically adapted.
- **Platform types must not leak** into common/public API.
- **Interactive roots** must meet a 48dp minimum target; press motion must not shrink the actual hit target or move neighboring layout.
- **State precedence** baseline: disabled or transition-locked, pressed or dragged, keyboard focused, pointer hovered, resting. Selected, checked, error, loading, expanded, and indeterminate are semantic states that combine with the interaction visuals; disabled and loading states must not invoke callbacks; loading must preserve layout.
- **Overlays** must define dismissal, focus capture/restoration, escape/back handling, and outside-click behavior before implementation.
- **Semantics**: every interactive component defines role, selected/disabled/error/loading state, and caller-configurable localized state descriptions. Focus treatment must remain visible in Light and Dark themes and must not rely only on color.
- **Directional behavior** must use layout direction; do not hardcode left/right when start/end is intended.
- **Showcase registration**: `docs:check` verifies the source contains each `"{slug}" ->` route, so the slug branch, route case, and section composable must land in the same change as the component.
- **Docs validation is mechanical**: both locales must keep identical page sets, heading order, property-table columns, iframe placement, index rows, sidebar entries, and Kotlin example counts.

### Verified Compose Multiplatform 1.11 constraints (commonMain)

These were each discovered on a real target build; do not re-learn them:

- `clickable` does not accept `role` + `indication` in one call. Set the role in a separate
  `semantics { }` block and pass `indication = null` (or the ripple) to `clickable`.
- `NestedScrollConnection` is an interface (`: NestedScrollConnection { }`, no constructor).
- Pointer events require an explicit scope: `pointerInput { awaitPointerEventScope { ... } }`.
- `Composable` getters such as `ElegantTheme.colors` must not be read inside `remember { }`
  lambda bodies on every target — hoist the read to a local `val` first.
- `RenderEffect`/`BlurEffect` exist only in the desktop artifact. Use `Modifier.blur` +
  `BlurredEdgeTreatment` for common blur.
- `ImageVector.PathBuilder` exposes `curveTo`, not `cubicTo`; `Outline.Generic` has no `bounds`
  constructor parameter.
- `Path` and skia-backed drawing APIs are not reliable in plain `commonTest` JUnit runs; test
  geometry as pure data, not through `Path` instances.

## Workflows

### Adding a New Component

1. Create the `@Composable` function (plus `ElegantXxxDefaults` and `ElegantXxxColors`) in `elegant-ui/src/commonMain/kotlin/com/elegant/compose/ui/{package}/`, following the API conventions above; add meaningful contract tests in `commonTest`.
2. Add a showcase section in `showcase/src/commonMain/kotlin/com/elegant/compose/showcase/ElegantShowcaseApp.kt` (slug branch, route case, section composable) covering the default look, public variants, the disabled state, and the important interaction or slot.
3. Add `docs/components/{slug}.md` and `docs/zh_CN/components/{slug}.md` (Miuix format: intro, iframe directly after it, Import, Basic Usage, component-specific types, Component States, Properties tables, Advanced Usage) and update both component indexes and both VitePress sidebars.
4. Verify: library and showcase compile on Android, Desktop, and Web/Wasm; desktop tests pass; docs validation and build pass.
5. Commit with one coherent Conventional Commit; add the component to `.claude/skills/create-component/references/completed-components.md` in the same change.

### Modifying a Component

When changing a component's API, defaults, or behavior, update all related artifacts in the same milestone:

1. **Documentation** (`docs/components/` and `docs/zh_CN/components/`): property tables, Defaults sections, and examples in both locales
2. **Showcase** (`showcase/.../ElegantShowcaseApp.kt`): demo reflecting the changes
3. **Tests** (`commonTest`): regression coverage for state-priority, sizing, focus, selection, dismissal, or adaptive-layout defects

### Fixing Bugs

1. Reproduce the failure with the narrowest meaningful test or showcase state
2. Fix the lowest responsible layer
3. Keep a regression test when the behavior can be expressed deterministically

## Git Commit Style

Use one coherent Conventional Commit per milestone, subject line at most 72 characters:

```text
feat(component): add tag across android desktop and web
fix(button): preserve keyboard focus on web
docs(modal): align chinese property table with the api
build(kmp): repair desktop and wasm publications
```

Scopes follow recent usage: `component`, `showcase`, `docs`, `workflow`, `theme`, `platform`,
`kmp`, `validation`. Check recent `git log --oneline` to stay consistent with current
conventions; keep the body terse and omit it when the subject says everything.
