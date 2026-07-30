# Delivery flow

Elegant UI supports Android, Desktop JVM, and Web/Wasm. Figma remains a visual reference but does not block implementation.

For each component:

1. Freeze the visual, API, state, semantics, slug, source-set, and platform acceptance contract.
2. Implement shareable source, semantic tokens, Defaults/Colors, KDoc, and common tests in `commonMain`.
3. Add narrowly scoped platform adapters only when common Compose APIs are insufficient.
4. Run multiplatform boundary validation.
5. Register the component and full state matrix once in the shared `:showcase`.
6. Build and inspect Android, Desktop, and Web launchers.
7. Add matching English and Simplified Chinese Miuix-format pages.
8. Ensure the iframe loads the real `:web-sample` Compose Web/Wasm build.
9. Update both locale sidebars and component indexes.
10. Run documentation checks and build VitePress.
11. Build the library, publish the KMP Maven repository, and package all platform samples.
12. Push one coherent component milestone.
13. GitHub Actions deploys documentation and uploads Maven, Android, Desktop, and Web artifacts.
14. Complete Android touch/TalkBack, Desktop keyboard/mouse/focus, and Web keyboard/pointer/viewport validation.
15. Fix accepted defects, rerun affected gates, and close the component milestone.

A component is not complete until all three supported targets and both CI workflows pass. iOS is outside the current roadmap and must not be added incidentally.
