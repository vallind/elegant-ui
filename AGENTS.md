# Elegant UI

Elegant UI is a refined Compose component library with a common-first Kotlin Multiplatform architecture. Android is the only configured and supported runtime target today. The repository contains the reusable `:elegant-ui` KMP library, an installable Android `:sample` application, synchronized English and Simplified Chinese documentation, and GitHub Actions workflows for documentation, Android artifacts, Maven publications, and physical-device validation.

The visual direction is elegant, premium, restrained, modern, and precise. Figma is the visual source of truth when available, but unavailable Figma automation must not block implementation, CI, or device testing.

## Non-negotiable platform statement

- **Supported:** Android API 24+
- **Planned, not supported:** Desktop JVM, iOS, Web/Wasm
- `commonMain` means the API and implementation are designed to be shareable. It does not mean every platform is already supported.
- Do not claim a platform is supported until its Gradle target, clean CI, sample, tests, documentation, release variant, and platform acceptance checks exist.
- The Android `:sample` remains a separate `com.android.application` module. Do not try to turn it into the KMP library module.

## Quick start

- Write a short plan before a new component, public API change, source-set migration, or meaningful refactor.
- Read the closest existing component and both locale pages before introducing a new API.
- Complete one component end to end. Do not begin the next V1 component until the current one passes CI and physical-device validation.
- Put shareable component code in `commonMain` by default. Platform-specific code requires a documented reason.
- Run KMP boundary validation, Gradle checks, documentation checks, and the relevant clean builds before handoff.
- Consult current official Android, Kotlin, Compose Multiplatform, Gradle, and VitePress documentation for unstable tooling facts.
- Never describe a static parse, mock browser demo, or unexecuted task as a successful build.

## Key commands

The repository currently uses the system Gradle executable in CI. Replace `gradle` with `./gradlew` after the wrapper is checked in.

| Action | Command |
| :--- | :--- |
| Validate common/platform boundaries | `./scripts/validate-kmp-boundaries.sh` |
| Compile and test the KMP library | `gradle :elegant-ui:build --stacktrace --no-daemon` |
| Build Android sample APK | `gradle :sample:assembleDebug --stacktrace --no-daemon` |
| Publish library to build-local Maven repo | `gradle :elegant-ui:publishAllPublicationsToBuildRepository --stacktrace --no-daemon` |
| Publish for another local project | `gradle :elegant-ui:publishToMavenLocal --stacktrace --no-daemon` |
| Run all verification tasks | `gradle check --stacktrace --no-daemon` |
| Run Android lint | `gradle lint --stacktrace --no-daemon` |
| Install sample on a connected device | `gradle :sample:installDebug` |
| Validate bilingual website | `cd docs && npm run docs:check` |
| Run documentation website | `cd docs && npm install && npm run docs:dev` |
| Build documentation website | `cd docs && npm install && npm run docs:build` |

GitHub Actions is the authoritative clean build until the repository includes a Gradle Wrapper and the local Android SDK is known to match CI.

## Repository structure

| Path | Purpose |
| :--- | :--- |
| `elegant-ui/` | KMP library module and public Elegant UI API |
| `elegant-ui/src/commonMain/kotlin/` | Default location for foundations and reusable Compose components |
| `elegant-ui/src/commonTest/kotlin/` | Platform-independent contract and pure behavior tests |
| `elegant-ui/src/androidMain/` | Android-only adapters, manifest, resources, or implementations |
| `elegant-ui/src/androidHostTest/` | Android-specific host tests when a component needs them |
| `elegant-ui/src/androidDeviceTest/` | Future Android device tests when enabled |
| `sample/` | Installable Android application used for physical-device acceptance |
| `docs/` | VitePress website root and English content |
| `docs/zh_CN/` | Simplified Chinese mirror |
| `docs/public/compose/index.html` | Miuix-style iframe visual demo registry |
| `scripts/validate-kmp-boundaries.sh` | Fails when common code imports Android-only APIs or legacy source paths return |
| `.github/workflows/android.yml` | KMP build, Maven publication, APK/AAR packaging, and artifacts |
| `.github/workflows/docs.yml` | Documentation validation, build, and GitHub Pages deployment |
| `PROJECT_BRIEF.md` | Locked scope and platform strategy |
| `FLOW.md` | Per-component delivery sequence |
| `VALIDATION.md` | Physical-device acceptance checklist |
| `.claude/skills/` | Repeatable repository workflows |

## Source-set policy

Library packages live under:

```text
elegant-ui/src/commonMain/kotlin/com/elegant/compose/ui/
├── theme/
├── button/
└── <component>/
```

Use this decision order:

1. **`commonMain`** — default for public Composables, tokens, state models, defaults, animation logic, semantics, and shared layout.
2. **`androidMain`** — only when an implementation requires Android APIs or Android resources.
3. **Future platform source sets** — add only together with the target, CI, sample, documentation, and acceptance criteria.
4. **`expect` / `actual`** — use only for a narrow platform capability with a stable common contract. Do not use it merely to hide avoidable platform coupling.

Do not recreate `elegant-ui/src/main`. The Android-KMP plugin uses KMP source-set directories.

## KMP public API boundary

Public declarations in `commonMain` must not expose Android platform types, including:

- `android.content.Context`
- `android.app.Activity`
- `android.graphics.*`
- `android.graphics.drawable.Drawable`
- Android `View` types
- Android-only resource identifiers

Compose packages under `androidx.compose.*` are allowed when supplied by Compose Multiplatform and available to `commonMain`.

When platform behavior is unavoidable:

- define a narrow semantic interface in `commonMain`;
- implement it in `androidMain`;
- keep platform objects out of component signatures;
- document fallback behavior and test the Android implementation;
- do not pretend the adapter exists on planned targets.

## Dependency and version policy

- The library uses Kotlin Multiplatform, the official Android-KMP library plugin, Compose Multiplatform, and the Compose compiler plugin.
- The Compose compiler plugin version must match the Kotlin plugin version.
- Use Compose Multiplatform dependencies in `commonMain`; do not add AndroidX-only dependencies there.
- Android-only dependencies belong in `androidMain` or the Android sample.
- Prefer `api` only when a dependency's types are part of the public contract or consumers need them to compose content. Prefer `implementation` otherwise.
- Material 3 may be used as implementation infrastructure, but Elegant UI owns its public visual system. Do not expose raw Material components as Elegant components.
- Version upgrades require current official compatibility verification and a clean CI build.

## Design and token rules

- Components consume semantic values from `ElegantTheme`, typography, spacing, radius, motion, and component-specific defaults.
- Raw colors belong in foundation/theme files. Do not scatter hexadecimal values through component implementations or sample screens.
- Light and dark themes are equal requirements.
- Preserve a calm hierarchy: one dominant primary action, restrained secondary containers, and low-emphasis tertiary actions.
- Use the Figma specification when available. When unavailable, use the locked contract and continue through Compose, CI, and device validation.
- Public names, KDoc, and documentation use Elegant UI terminology and do not claim another design system's brand.

## Kotlin and Compose style

- Use explicit `public` for public API.
- Use trailing commas in multiline declarations and calls.
- Prefer immutable state models and annotate only truly immutable values with `@Immutable`.
- Centralize state-to-visual resolution rather than scattering state branches through layout code.
- Use `remember` only for values that should survive recomposition or are expensive to recreate.
- Use stable, descriptive animation labels.
- Avoid intrinsic measurement and custom `Layout` unless standard primitives cannot satisfy the contract.
- Keep imports sorted and remove unused imports.
- Public API requires KDoc.
- Common code must remain free of Android-only imports.

## Public API conventions

### Composable parameter order

Use this order unless a component has a documented reason to differ:

```kotlin
@Composable
public fun ElegantComponent(
    // 1. Required state and behavior
    value: Value,
    onValueChange: (Value) -> Unit,
    // 2. Modifier
    modifier: Modifier = Modifier,
    // 3. State flags
    enabled: Boolean = true,
    loading: Boolean = false,
    // 4. Visual configuration
    style: ElegantComponentStyle = ElegantComponentStyle.Default,
    size: ElegantComponentSize = ElegantComponentSize.Medium,
    colors: ElegantComponentColors = ElegantComponentDefaults.colors(style),
    // 5. Optional slots
    leadingContent: (@Composable () -> Unit)? = null,
    trailingContent: (@Composable () -> Unit)? = null,
    // 6. Main content, always last
    content: @Composable () -> Unit,
)
```

Required state callbacks remain adjacent to their values when that improves comprehension.

### Defaults and colors

- A component with reusable dimensions, shapes, motion, or colors exposes `ElegantXxxDefaults`.
- Complex visual state uses a truly immutable `ElegantXxxColors` or equivalent model.
- Theme-aware factory functions are composable.
- Public customization represents stable design-system decisions, not raw Figma primitive IDs.
- Document defaults, colors, enums, and public constants in both locale pages.

### State and semantics

Each applicable component handles:

- default;
- pressed;
- focused;
- selected or checked;
- disabled;
- loading;
- error;
- RTL;
- large font scale;
- keyboard and screen-reader behavior.

Interactive roots must meet the 48dp minimum touch target unless a stricter platform requirement applies. Loading states must prevent duplicate activation. Use the correct semantics role and expose meaningful state descriptions.

## Android-only support policy

Android is the release gate during `0.x`:

- `:sample` must build and install;
- GitHub Actions must produce a debug APK;
- the KMP publication must contain an Android AAR and Gradle module metadata;
- the user must complete `VALIDATION.md` on a named Android device;
- failures on Android block the next component.

Planned platform code must not be added speculatively. A future target is introduced through a dedicated architecture milestone, not as incidental component work.

## Consumer integration contract

Supported Android integration methods:

1. Same build: `implementation(project(":elegant-ui"))`
2. Separate local project: `gradle :elegant-ui:publishToMavenLocal`
3. CI handoff: download the complete `elegant-ui-maven-repository` artifact and register it as a Maven repository
4. Future stable release: `implementation("io.github.vallind:elegant-ui:<version>")`

The root Maven coordinate is `io.github.vallind:elegant-ui`. Consumers should use the complete KMP Maven publication rather than copying a standalone AAR. The direct AAR is for inspection or temporary emergency testing only.

## Component documentation contract

Every delivered component must include:

- English page: `docs/components/{slug}.md`
- Simplified Chinese mirror: `docs/zh_CN/components/{slug}.md`
- Miuix-style iframe demo registered by `?id={slug}`
- matching sidebars and component indexes
- real imports, examples, property table, enums/defaults/colors, states, and advanced usage

Required page order:

```text
Introduction
iframe demo
Import / 引入
Basic Usage / 基本用法
Component-specific types or behavior
Component States / 组件状态
Properties / 属性
Advanced Usage / 进阶用法
```

The browser demo is a visual aid. It is not the Compose runtime and does not replace the Android APK or physical-device gate.

## Verification order

Before handoff:

1. `./scripts/validate-kmp-boundaries.sh`
2. `cd docs && npm run docs:check`
3. `cd docs && npm run docs:build`
4. `gradle :elegant-ui:build --stacktrace --no-daemon`
5. `gradle :elegant-ui:publishAllPublicationsToBuildRepository --stacktrace --no-daemon`
6. `gradle :sample:assembleDebug --stacktrace --no-daemon`
7. `gradle check lint --stacktrace --no-daemon`
8. Confirm the Maven repository contains Gradle metadata, POMs, sources, and an Android AAR.
9. Confirm the sample APK exists.
10. Verify **Android Build** and **Documentation** GitHub Actions.
11. Install the latest APK and record physical-device results.

If an environment cannot execute a required command, state exactly what was not run and defer authority to CI. Never infer success from syntax checks alone.

## Completion definition

A component is complete only when:

1. The public/state/visual/platform contract is locked.
2. Common-first source and public KDoc are complete.
3. KMP boundary validation passes.
4. Tokens/defaults/colors are complete.
5. Android sample demo is registered and interactive.
6. English and Chinese pages match the real API.
7. The iframe demo and both discovery indexes are updated.
8. `VALIDATION.md` covers component-specific checks.
9. Documentation CI succeeds.
10. KMP/Android CI succeeds and publishes Maven, AAR, and APK artifacts.
11. Physical-device validation is accepted.
12. No unrelated next-component work is included.
