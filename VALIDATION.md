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
