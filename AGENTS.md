# Elegant UI repository guidance

Elegant UI is a refined Compose Multiplatform component library. Android, Desktop JVM, and Web/Wasm are supported targets. The repository contains the published `:elegant-ui` KMP library, a shared `:showcase`, three platform launchers, synchronized English and Simplified Chinese documentation, and GitHub Actions for multiplatform artifacts and GitHub Pages.

## Platform contract

- **Android:** supported, API 24+
- **Desktop JVM:** supported on current 64-bit macOS, Windows, and Linux environments supported by Compose Multiplatform
- **Web/Wasm:** supported on modern browsers with WasmGC
- **iOS:** out of scope; do not add iOS targets, source sets, APIs, samples, or claims without a separate approved milestone
- A component is incomplete when it fails to compile or demonstrate its public contract on any supported target.
- Keep the public API shared. Platform-specific behavior may adapt input modality and system integration, but not silently change component meaning.

## Authority and scope

- This file is the single repository-wide engineering authority. `CLAUDE.md` references it.
- Read the closest source, tests, sample, docs pages, and workflows before editing.
- Preserve the project’s original design language. Reference mature projects for process and quality, never for brand or copied implementation.
- Work on one component milestone at a time. Do not begin the next component before all required gates pass.
- Verify unstable tooling facts against current official Kotlin, Compose Multiplatform, Android, Gradle, VitePress, and GitHub Actions documentation.

## Repository map

| Path | Responsibility |
| --- | --- |
| `elegant-ui/src/commonMain/` | Public components, tokens, state, defaults, semantics, shared behavior |
| `elegant-ui/src/commonTest/` | Platform-independent contract and pure-state tests |
| `elegant-ui/src/androidMain/` | Android-only adapters and manifest |
| `elegant-ui/src/desktopMain/` | Desktop-only adapters when genuinely required |
| `elegant-ui/src/wasmJsMain/` | Browser-only adapters when genuinely required |
| `showcase/src/commonMain/` | Shared component gallery and component-slug registry |
| `sample/` | Android application launcher and physical-device validation |
| `desktop-sample/` | Desktop JVM launcher and keyboard/mouse/window validation |
| `web-sample/` | Compose Web/Wasm launcher and documentation iframe runtime |
| `docs/` | VitePress website, English root and `zh_CN` mirror |
| `scripts/validate-kmp-boundaries.sh` | Source-set and platform-target boundary checks |
| `.github/workflows/android.yml` | Multiplatform build and artifact workflow |
| `.github/workflows/docs.yml` | Compose Web demo + VitePress Pages workflow |

## Required commands

```bash
./scripts/validate-kmp-boundaries.sh

gradle :elegant-ui:build --stacktrace --no-daemon
gradle :showcase:build --stacktrace --no-daemon
gradle :elegant-ui:publishAllPublicationsToBuildRepository --stacktrace --no-daemon
gradle :sample:assembleDebug --stacktrace --no-daemon
gradle :desktop-sample:createDistributable --stacktrace --no-daemon
gradle :web-sample:wasmJsBrowserDistribution --stacktrace --no-daemon

cd docs
npm install
npm run docs:check
npm run docs:build
```

Only claim a command passed when it actually ran successfully. GitHub Actions is the authoritative clean environment when local Android SDK, browser runtime, or packaging tools are unavailable.

## Source-set policy

1. Put public Composables, theme values, state models, defaults, semantic behavior, and reusable animation/layout logic in `commonMain`.
2. Use `androidMain`, `desktopMain`, or `wasmJsMain` only for APIs that cannot be expressed with common Compose Multiplatform APIs.
3. Define the smallest stable common contract before adding platform implementations.
4. Prefer dependency injection or a narrow interface. Use `expect`/`actual` only when compile-time specialization is the clearest design.
5. Never recreate `elegant-ui/src/main` or `showcase/src/main`.

Common/public code must not expose or import:

- Android framework types such as `Context`, `Activity`, `Drawable`, `View`, or resource IDs
- Desktop-only AWT or Swing types
- browser DOM, `kotlinx.browser`, or `org.w3c.dom` types
- platform path, window, clipboard, or lifecycle objects without a narrow abstraction

`androidx.compose.*` APIs are allowed only when the artifact is available to Compose Multiplatform `commonMain`.

## Component API contract

Use this public parameter order:

1. required behavior/state parameters
2. `modifier: Modifier = Modifier`
3. state flags and controlled values
4. visual configuration such as style, size, colors, shape
5. optional slots
6. primary `content` lambda last

Example:

```kotlin
@Composable
public fun ElegantComponent(
    value: Value,
    onValueChange: (Value) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    loading: Boolean = false,
    style: ElegantComponentStyle = ElegantComponentStyle.Default,
    colors: ElegantComponentColors = ElegantComponentDefaults.colors(),
    leadingContent: (@Composable () -> Unit)? = null,
    content: @Composable () -> Unit,
)
```

Requirements:

- Add public KDoc to every public declaration.
- Use `@Immutable` only for values that are actually immutable.
- Complex visual APIs expose `ElegantXxxDefaults` and `ElegantXxxColors` or an equivalent immutable state model.
- Centralize state-to-visual resolution.
- Do not expose an unmodified Material component as the Elegant UI public contract.
- Preserve binary/source compatibility deliberately; document breaking changes during `0.x`.

## Foundations and visual rules

- Use semantic values from `ElegantTheme`; raw colors belong only in foundation/theme files.
- Use a 4dp spacing grid unless the component contract explicitly documents an exception.
- Support Light and Dark themes.
- Preserve hierarchy, contrast, typography, radius, and motion across all supported targets.
- The interactive root must meet a 48dp minimum target unless a stricter platform rule applies.
- Loading or transition states must prevent duplicate activation.
- Do not encode platform-specific input assumptions into shared visuals.

## Accessibility and input

Every interactive component must define correct Compose semantics, role, selected/disabled/error/loading state, and useful state descriptions.

Validate by platform:

- **Android:** touch, hardware keyboard, TalkBack, font scale, density, RTL, Light/Dark.
- **Desktop:** mouse hover/press, keyboard activation, focus traversal, high DPI, window resize, Light/Dark.
- **Web/Wasm:** pointer, keyboard activation, browser focus, viewport resize, browser zoom, Light/Dark, supported screen-reader behavior where applicable.

A browser visual approximation is not accepted. The documentation iframe is built from the real `:web-sample` Compose Web/Wasm application.

## Shared showcase contract

`showcase/src/commonMain/.../ElegantShowcaseApp.kt` is the single component-demo registry.

For every new component:

- add a stable lowercase slug;
- register the slug in the shared `when`/registry;
- render the same state matrix on Android, Desktop, and Web;
- keep platform launchers thin;
- avoid duplicating component demo UI in launcher modules.

Platform launchers may supply platform context such as the Web query parameter, window title, or Android Activity lifecycle, but must call the shared showcase.

## Miuix-format component documentation

Each component creates both:

```text
docs/components/{slug}.md
docs/zh_CN/components/{slug}.md
```

Required top-level order:

```text
# ComponentName
introduction
iframe
Import / 引入
Basic Usage / 基本用法
component-specific types or behavior
Component States / 组件状态
Properties / 属性
Advanced Usage / 进阶用法
```

Rules:

- Place the iframe directly after the introduction; do not add a separate Demo heading.
- Use `../compose/index.html?id={slug}` for English and `../../compose/index.html?id={slug}` for Chinese.
- The iframe must load the real Compose Web/Wasm build generated from `:web-sample`.
- Property tables use property name, type, description, default value, required.
- Document public parameters in signature order and cover all public enums, Defaults, Colors, constants, and factories users need.
- Keep English and Chinese Kotlin example counts and API identifiers aligned.
- Update both sidebars and both component indexes.

## Testing requirements

For each component:

- add `commonTest` coverage for stable enums, pure state resolution, and contract logic;
- add platform tests when behavior depends on a platform adapter;
- compile all three targets;
- exercise the shared showcase on all three launchers;
- verify Web query routing for the component slug;
- keep platform-specific validation recorded in `VALIDATION.md`.

Do not add empty or tautological tests merely to satisfy a file-count requirement.

## CI and artifacts

The **Multiplatform Build** workflow must produce:

- `elegant-ui-maven-repository` containing root KMP metadata and Android/Desktop/Web publications
- `elegant-ui-android-aar`
- `elegant-ui-android-sample`
- `elegant-ui-desktop-sample-linux`
- `elegant-ui-web-sample`

The **Documentation** workflow must:

1. build `:web-sample:wasmJsBrowserDistribution`;
2. copy the real distribution into `docs/public/compose`;
3. run documentation validation;
4. build VitePress;
5. deploy GitHub Pages on `main` pushes.

## Consumer contract

Supported same-build dependency:

```kotlin
implementation(project(":elegant-ui"))
```

Supported KMP publication dependency:

```kotlin
commonMain.dependencies {
    implementation("io.github.vallind:elegant-ui:0.1.0-SNAPSHOT")
}
```

Standalone Android apps may use the same coordinate in `dependencies`. Do not instruct consumers to guess target-suffixed artifacts or copy only an AAR for normal integration.

## Component definition of done

A component lands only when:

1. Public API, states, visual contract, and slug are locked.
2. Shared implementation and KDoc are complete.
3. Platform types do not leak into common/public API.
4. Semantic tokens, Defaults/Colors, state resolution, and tests are complete.
5. KMP boundary validation passes.
6. Shared showcase registration covers all public variants and states.
7. Android, Desktop, and Web launchers compile and display the component.
8. Android touch/TalkBack checks are accepted.
9. Desktop keyboard/mouse/focus/window checks are accepted.
10. Web keyboard/pointer/focus/viewport checks are accepted.
11. English and Chinese Miuix-format pages match the real API.
12. The real Compose Web iframe, both sidebars, and both indexes are updated.
13. Maven publication contains all supported variants.
14. Both GitHub Actions workflows succeed.
15. No unrelated next-component or iOS work is included.

## Commit style

Use one coherent Conventional Commit per milestone:

```text
feat(component): add icon button across android desktop and web
fix(button): preserve keyboard focus on web
build(kmp): repair desktop and wasm publications
```
