# Multiplatform validation checklist

Build: Button Android / Desktop / Web milestone `0.1.0-SNAPSHOT`

## Build record

- Commit / workflow run:
- KMP Maven artifact:
- Android artifact:
- Desktop artifact:
- Web artifact / deployed URL:
- Observations:

## Shared visual contract

- [ ] Primary, Secondary, and Tertiary hierarchy is clear
- [ ] Small, Medium, and Large proportions are balanced
- [ ] Light and Dark themes preserve hierarchy and contrast
- [ ] Hovered, pressed, focused, loading, and disabled states are recognizable
- [ ] Pointer hover raises without changing layout or hit-target geometry
- [ ] Touch press shows ripple and restrained scale feedback
- [ ] Loading prevents duplicate activation
- [ ] Loading preserves the resting button width
- [ ] Leading and trailing content align correctly
- [ ] Custom `ElegantButtonColors` preserve contrast and focus indication

## Android

Environment:

- Device model:
- Android version:
- Density / display scale:
- Font scale:

Checks:

- [ ] Latest `elegant-ui-android-sample` installs and launches
- [ ] Interactive roots are at least 48dp and easy to tap
- [ ] Hardware keyboard can focus and activate controls
- [ ] TalkBack announces role, label, disabled, and loading state
- [ ] Increased font scale remains usable
- [ ] RTL and landscape layouts remain usable

## Desktop JVM

Environment:

- OS / version:
- JDK:
- Display scale:

Checks:

- [ ] `elegant-ui-desktop-sample-linux` or a local distributable launches
- [ ] Mouse hover/press behavior is restrained and clear
- [ ] Tab/Shift+Tab focus traversal is predictable
- [ ] Enter/Space activates focused controls
- [ ] Focus ring remains visible in Light and Dark themes
- [ ] Window resizing and high-DPI scaling remain usable

## Web/Wasm

Environment:

- Browser / version:
- OS:
- Browser zoom:
- Viewport sizes:

Checks:

- [ ] `elegant-ui-web-sample` or the deployed documentation iframe loads
- [ ] `?id=button` resolves the Button showcase
- [ ] Pointer and keyboard activation work
- [ ] Browser focus remains visible
- [ ] Narrow and wide viewports remain usable
- [ ] Browser zoom does not clip essential content
- [ ] Loading and disabled behavior match Android and Desktop semantics

Record acceptance or actionable defects before beginning the next component.

## IconButton milestone

Automated checks:

- [x] Shared pure state priority and size metrics have `commonTest` coverage
- [x] Button regression contracts and IconButton contracts pass on Desktop JVM
- [x] `:elegant-ui` and `:showcase` compile for Android, Desktop JVM, and Web/Wasm
- [x] KMP boundary validation passes
- [x] Android sample APK assembles in GitHub Actions
- [x] Desktop JVM distributable builds
- [x] Web/Wasm browser distribution builds
- [x] KMP publication contains Android, Desktop JVM, and Web/Wasm variants
- [x] English and Simplified Chinese documentation checks and builds pass
- [x] GitHub Actions Multiplatform Build succeeds (`30584457095`)
- [x] GitHub Actions Documentation succeeds (`30584456944`)

Platform acceptance checks:

- [ ] Android touch target, keyboard activation, TalkBack, font scale, density, RTL, and Light/Dark accepted
- [ ] Desktop hover/press, keyboard activation, focus traversal, high DPI, resize, and Light/Dark accepted
- [ ] Web pointer, keyboard activation, browser focus, viewport resize, zoom, and Light/Dark accepted

Routes:

- [x] Shared registry recognizes `button` and `icon-button`
- [ ] `?id=icon-button` loads the real Compose Web/Wasm showcase in the documentation iframe

Local environment notes:

- `:elegant-ui:build` and `:showcase:build` reached all target compilation and Desktop tests,
  then stopped because ChromeHeadless is unavailable in Termux for Wasm browser tests.
- `:sample:assembleDebug` stopped while downloading `androidx.core:core-ktx:1.19.0` because
  the local Java client could not complete a TLS handshake with Google Maven.
- GitHub Actions remains authoritative for the clean browser-test and Android-sample results.

Record platform acceptance or actionable defects before beginning the Avatar milestone.

## Avatar milestone

Automated checks:

- [x] Stable sizes, generated initials, fallback behavior, and metrics have `commonTest` coverage
- [x] Avatar, Button, and IconButton contracts pass on Desktop JVM
- [x] `:elegant-ui` and `:showcase` compile for Android, Desktop JVM, and Web/Wasm
- [x] KMP boundary validation passes
- [x] Android sample APK assembles in GitHub Actions
- [x] Desktop JVM distributable builds
- [x] Web/Wasm browser distribution builds
- [x] KMP publication contains root metadata plus Android, Desktop JVM, and Web/Wasm variants
- [x] English and Simplified Chinese Miuix-format pages are structurally aligned
- [x] Documentation checks and VitePress build pass
- [x] GitHub Actions Multiplatform Build succeeds (`30585914525`)
- [x] GitHub Actions Documentation succeeds (`30585914506`)

Platform acceptance checks:

- [ ] Android density, font scale, RTL, TalkBack image semantics, and Light/Dark accepted
- [ ] Desktop high DPI, window resize, screen-reader semantics, and Light/Dark accepted
- [ ] Web viewport resize, browser zoom, screen-reader semantics, and Light/Dark accepted

Routes and compositions:

- [x] Shared registry recognizes `button`, `icon-button`, and `avatar`
- [x] Avatar composes with IconButton inside the shared team-roster example
- [ ] `?id=avatar` loads the real Compose Web/Wasm showcase in the documentation iframe

Local environment notes:

- Avatar is implemented entirely in `commonMain`; no platform adapters or platform types are used.
- Remote image loading, click behavior, presence, and badges remain separate composition concerns.
- `:elegant-ui:build` and `:showcase:build` reached all target compilation and Desktop tests,
  then stopped because ChromeHeadless is unavailable in Termux for Wasm browser tests.
- `:sample:assembleDebug` stopped while downloading `androidx.core:core-ktx:1.19.0` because
  the local Java client could not complete a TLS handshake with Google Maven.
- GitHub Actions remains authoritative for clean browser tests, Android assembly, artifacts, and Pages.

Record platform acceptance or actionable defects before beginning the Badge milestone.

## Badge milestone

Automated checks:

- [x] Stable styles, sizes, logical placements, count coercion, overflow formatting, theme-role resolution, and 4.5:1 text contrast have `commonTest` coverage
- [x] Badge, theme, Avatar, Button, and IconButton contracts pass on Desktop JVM
- [x] `:elegant-ui` and `:showcase` compile for Android, Desktop JVM, and Web/Wasm
- [x] KMP boundary validation passes
- [ ] Android sample APK assembles in the local environment
- [x] Desktop JVM distributable builds
- [x] Web/Wasm browser distribution builds
- [x] KMP publication contains root metadata plus Android, Desktop JVM, and Web/Wasm variants
- [x] The Desktop publication contains the Badge public API and the root sources publication contains `ElegantBadge.kt`
- [x] English and Simplified Chinese Miuix-format pages are structurally aligned
- [x] Documentation validation and VitePress build pass with the real Compose Web distribution
- [x] GitHub Actions Multiplatform Build succeeds (`30588644363`)
- [x] GitHub Actions Documentation succeeds (`30588643461`)

Platform acceptance checks:

- [ ] Android density, font scale, RTL, TalkBack status/count semantics, and Light/Dark accepted
- [ ] Desktop high DPI, window resize, screen-reader semantics, and Light/Dark accepted
- [ ] Web viewport resize, browser zoom, screen-reader semantics, RTL, and Light/Dark accepted

Routes and compositions:

- [x] Shared registry recognizes `button`, `icon-button`, `avatar`, and `badge`
- [x] Badge composes with Avatar and IconButton without changing their measured interaction size
- [x] All five semantic styles, three optical sizes, count boundaries, and four logical placements appear in the shared showcase
- [ ] `?id=badge` loads the real Compose Web/Wasm showcase in a supported browser

Local environment notes:

- Badge is implemented entirely in `commonMain`; no platform adapters or platform types are used.
- Positive, Warning, and Critical semantic color roles were added as defaulted trailing
  `ElegantColors` properties. Existing source construction remains compatible; the primary
  constructor ABI may change during the documented `0.x` snapshot period.
- `:elegant-ui:build` and `:showcase:build` reached Android, Desktop, and Wasm compilation plus
  Desktop and Android tests, then stopped because ChromeHeadless is unavailable in Termux.
- `:sample:assembleDebug` stopped while resolving `androidx.core:core-ktx:1.19.0` because the local
  Java client could not complete a TLS handshake with Google Maven.
- `:elegant-ui:publishAllPublicationsToBuildRepository`,
  `:desktop-sample:createDistributable`, and `:web-sample:wasmJsBrowserDistribution` succeeded.
- `npm install --no-audit --no-fund`, `npm run docs:check`, and `npm run docs:build` succeeded.
- GitHub Actions remains authoritative for clean browser tests, Android assembly, artifacts, and
  Pages deployment.

Record platform acceptance or actionable defects before beginning the Divider milestone.

## Divider milestone

Automated checks:

- [x] Stable orientations, styles, emphasis levels, logical label positions, invalid dimensions, and theme-role resolution have `commonTest` coverage
- [x] Divider, Badge, Avatar, Button, IconButton, shared action, showcase routing, and theme contracts pass on Desktop JVM
- [x] `:elegant-ui` and `:showcase` compile separately for Android, Desktop JVM, and Web/Wasm
- [x] KMP boundary validation passes
- [ ] Android sample APK assembles in the local environment
- [x] Desktop JVM distributable builds
- [x] Web/Wasm browser distribution builds
- [x] KMP publication contains root metadata plus Android, Desktop JVM, and Web/Wasm variants
- [x] The Desktop publication contains the Divider public API and the root sources publication contains `ElegantDivider.kt`
- [x] English and Simplified Chinese Miuix-format pages have aligned Kotlin examples and public identifiers
- [x] Documentation validation and VitePress build pass with the real Compose Web distribution
- [ ] GitHub Actions Multiplatform Build succeeds
- [ ] GitHub Actions Documentation succeeds

Platform acceptance checks:

- [ ] Android density, RTL, TalkBack decorative/named semantics, font scale, and Light/Dark accepted
- [ ] Desktop high DPI, window resize, screen-reader semantics, and Light/Dark accepted
- [ ] Web viewport resize, browser zoom, screen-reader semantics, RTL, and Light/Dark accepted

Routes and compositions:

- [x] Shared registry recognizes `button`, `icon-button`, `avatar`, `badge`, and `divider`
- [x] Showcase covers both orientations, both styles, both emphasis levels, all three logical label positions, and RTL
- [x] Divider composes with Avatar and constrained metric content without adding interaction behavior
- [ ] `?id=divider` loads the real Compose Web/Wasm showcase in a supported browser

Local environment notes:

- Divider is implemented entirely in `commonMain`; no platform adapters, platform types, new
  dependencies, interactive state, or motion were required.
- `:elegant-ui:compileAndroidMain`, `:showcase:compileAndroidMain`, the two Desktop test tasks,
  and all four Wasm main/test compilation tasks succeeded as separately scheduled platform gates.
- `:elegant-ui:build` and `:showcase:build` completed Android, Desktop, and Wasm compilation plus
  Android/Desktop tests, then stopped only because ChromeHeadless is unavailable in Termux.
- `:sample:assembleDebug` stopped while resolving `androidx.core:core-ktx:1.19.0` because the local
  Java client could not complete a TLS handshake with Google Maven, including an unrestricted
  network retry.
- `:elegant-ui:publishAllPublicationsToBuildRepository`,
  `:desktop-sample:createDistributable`, and `:web-sample:wasmJsBrowserDistribution` succeeded.
- `npm install --no-audit --no-fund`, `npm run docs:check`, and `npm run docs:build` succeeded.
- GitHub Actions remains authoritative for browser tests, Android assembly, final artifacts, and
  Pages deployment. Screenshot and pixel-diff evidence are not completion gates for this milestone.

Record platform acceptance or actionable defects before beginning the Tag milestone.
