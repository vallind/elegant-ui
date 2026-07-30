---
name: publish-library
description: Build and publish Elegant UI for Android consumers from its Android-first Kotlin Multiplatform module. Use when asked to publish locally, prepare Maven artifacts, package an AAR, verify consumer coordinates, or prepare a release handoff.
---

# Publish Elegant UI

Android is the only supported runtime target. Publish the complete KMP Maven repository; treat the standalone AAR as a secondary inspection artifact.

## Snapshot handoff

1. Run `./scripts/validate-kmp-boundaries.sh`.
2. Run `gradle :elegant-ui:build --stacktrace --no-daemon`.
3. Run `gradle :elegant-ui:publishAllPublicationsToBuildRepository --stacktrace --no-daemon`.
4. Verify `elegant-ui/build/repo/` contains Gradle module metadata, POMs, sources, and an Android AAR.
5. Test an Android consumer with `implementation("io.github.vallind:elegant-ui:0.1.0-SNAPSHOT")` against that repository.
6. Build `:sample:assembleDebug` and complete physical-device validation.

## Maven Local

```bash
gradle :elegant-ui:publishToMavenLocal --stacktrace --no-daemon
```

The consuming Android project must add `mavenLocal()` and use the root coordinate. Do not instruct consumers to depend on a guessed platform-suffixed artifact.

## Release guardrails

- Do not publish a stable version until signing, POM metadata, license, source archive, API compatibility checks, changelog, and release CI are configured.
- Do not claim Desktop, iOS, or Web support merely because source is in `commonMain`.
- Keep `group = "io.github.vallind"` and artifact `elegant-ui` stable unless a migration plan is approved.
- Preserve semantic versioning and document breaking changes before `1.0` as explicitly unstable.
