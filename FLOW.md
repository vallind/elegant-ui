# Delivery flow

Figma remains the visual source of truth, but it does not block implementation.

Android is the only supported runtime target. Library code is common-first so future targets can be added without changing core component APIs.

For each component:

1. Freeze the visual, API, state, accessibility, source-set, and website contract.
2. Confirm the implementation belongs in `commonMain`; document any required Android adapter.
3. Implement semantic tokens, defaults/colors, public KDoc, shared behavior, and common tests.
4. Run KMP boundary validation.
5. Add and register the Android physical-device sample demo.
6. Add matching English and Simplified Chinese Miuix-format pages.
7. Add or update the iframe visual demo, both locale sidebars, and both component indexes.
8. Run documentation validation and the VitePress build.
9. Build the KMP library, publish the build-local Maven repository, and assemble the Android sample APK.
10. Push one coherent component milestone.
11. GitHub Actions deploys documentation and uploads Maven, AAR, and APK artifacts.
12. The user installs the APK on a physical Android device and reports observations.
13. Fix documentation, visual, interaction, accessibility, KMP-boundary, or Android-specific defects.
14. Mark the component complete and begin the next component.

A component is not complete until both CI workflows pass and physical-device validation is accepted. Planned Desktop, iOS, or Web targets are separate architecture milestones and must not be added speculatively during component delivery.
