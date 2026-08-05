// Copyright 2026, elegant-ui contributors
// SPDX-License-Identifier: Apache-2.0
// Ported from compose-miuix-ui/miuix (Apache-2.0).

package com.elegant.compose.ui.blur

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import com.elegant.compose.ui.blur.highlight.ElegantHighlight
import androidx.compose.ui.graphics.BlendMode as ComposeBlendMode

/**
 * Applies background blur to the content behind this composable.
 *
 * Blend colors support both standard SkBlendMode (0-29, GPU hardware) and
 * custom modes (100-121, 200-203, runtime shader). See [ElegantBlurBlendMode].
 *
 * @param backdrop The [ElegantBackdrop] providing the background content to blur.
 * @param shape The shape provider for the blur region clipping.
 * @param blurRadius The blur radius in dp. Internally converted to pixels using display density.
 *   Clamped to [0, [ElegantBlurDefaults.MaxBlurRadius]].
 * @param noiseCoefficient Noise dithering coefficient for anti-banding. 0 disables noise.
 * @param colors Color adjustments and blend layers applied after blur.
 * @param highlight Optional edge highlight painted on top of the content. `null` skips drawing.
 * @param contentBlendMode Optional [ComposeBlendMode] for compositing content over the blur.
 *   Use [ComposeBlendMode.DstIn] for foreground blur (content alpha masks the blur).
 *   null means content draws normally on top.
 * @param enabled Whether blur is active. When false, the effect is skipped and content draws normally.
 */
fun Modifier.elegantTextureBlur(
    backdrop: ElegantBackdrop,
    shape: Shape,
    blurRadius: Float = ElegantBlurDefaults.BlurRadius,
    noiseCoefficient: Float = ElegantBlurDefaults.NoiseCoefficient,
    colors: ElegantBlurColors = ElegantBlurColors(),
    highlight: ElegantHighlight? = null,
    contentBlendMode: ComposeBlendMode = ComposeBlendMode.SrcOver,
    enabled: Boolean = true,
): Modifier = elegantTextureEffect(
    backdrop = backdrop,
    shape = shape,
    blurRadius = blurRadius,
    noiseCoefficient = noiseCoefficient,
    colors = colors,
    highlight = highlight,
    contentBlendMode = contentBlendMode,
    enabled = enabled,
)

/**
 * Applies background blur with independent horizontal and vertical radii.
 *
 * @param backdrop The [ElegantBackdrop] providing the background content to blur.
 * @param shape The shape provider for the blur region clipping.
 * @param blurRadiusX The horizontal blur radius in dp. Internally converted to pixels using display density.
 *   Clamped to [0, [ElegantBlurDefaults.MaxBlurRadius]].
 * @param blurRadiusY The vertical blur radius in dp. Internally converted to pixels using display density.
 *   Clamped to [0, [ElegantBlurDefaults.MaxBlurRadius]].
 * @param noiseCoefficient Noise dithering coefficient for anti-banding. 0 disables noise.
 * @param colors Color adjustments and blend layers applied after blur.
 * @param highlight Optional edge highlight painted on top of the content. `null` skips drawing.
 * @param contentBlendMode Optional [ComposeBlendMode] for compositing content over the blur.
 *   Use [ComposeBlendMode.DstIn] for foreground blur (content alpha masks the blur).
 *   null means content draws normally on top.
 * @param enabled Whether blur is active. When false, the effect is skipped and content draws normally.
 */
fun Modifier.elegantTextureBlur(
    backdrop: ElegantBackdrop,
    shape: Shape,
    blurRadiusX: Float,
    blurRadiusY: Float,
    noiseCoefficient: Float = ElegantBlurDefaults.NoiseCoefficient,
    colors: ElegantBlurColors = ElegantBlurColors(),
    highlight: ElegantHighlight? = null,
    contentBlendMode: ComposeBlendMode = ComposeBlendMode.SrcOver,
    enabled: Boolean = true,
): Modifier = elegantTextureEffect(
    backdrop = backdrop,
    shape = shape,
    blurRadiusX = blurRadiusX,
    blurRadiusY = blurRadiusY,
    noiseCoefficient = noiseCoefficient,
    colors = colors,
    highlight = highlight,
    contentBlendMode = contentBlendMode,
    enabled = enabled,
)

/**
 * Applies the complete texture effect: backdrop blur + color blending
 * (with all custom blend modes).
 *
 * @param backdrop The [ElegantBackdrop] providing the background content to blur.
 * @param shape Shape provider for the blur region clipping.
 * @param blurRadius The blur radius in dp. Internally converted to pixels using display density.
 *   Clamped to [0, [ElegantBlurDefaults.MaxBlurRadius]].
 * @param noiseCoefficient Noise dithering coefficient for anti-banding.
 * @param colors Color adjustments and blend layers applied after blur.
 * @param highlight Optional edge highlight painted on top of the content. `null` skips drawing.
 * @param contentBlendMode Optional [ComposeBlendMode] for compositing content over the blur.
 *   Use [ComposeBlendMode.DstIn] for foreground blur (content alpha masks the blur).
 *   null means content draws normally on top.
 * @param enabled Whether the effect is active. When false, the effect is skipped and content draws normally.
 */
fun Modifier.elegantTextureEffect(
    backdrop: ElegantBackdrop,
    shape: Shape,
    blurRadius: Float = ElegantBlurDefaults.BlurRadius,
    noiseCoefficient: Float = ElegantBlurDefaults.NoiseCoefficient,
    colors: ElegantBlurColors = ElegantBlurColors(),
    highlight: ElegantHighlight? = null,
    contentBlendMode: ComposeBlendMode = ComposeBlendMode.SrcOver,
    enabled: Boolean = true,
): Modifier = elegantTextureEffect(
    backdrop = backdrop,
    shape = shape,
    blurRadiusX = blurRadius,
    blurRadiusY = blurRadius,
    noiseCoefficient = noiseCoefficient,
    colors = colors,
    highlight = highlight,
    contentBlendMode = contentBlendMode,
    enabled = enabled,
)

/**
 * Applies the complete texture effect with independent horizontal and vertical
 * blur radii: backdrop blur + color blending (with all custom blend modes).
 *
 * @param backdrop The [ElegantBackdrop] providing the background content to blur.
 * @param shape Shape provider for the blur region clipping.
 * @param blurRadiusX The horizontal blur radius in dp. Internally converted to pixels using display density.
 *   Clamped to [0, [ElegantBlurDefaults.MaxBlurRadius]].
 * @param blurRadiusY The vertical blur radius in dp. Internally converted to pixels using display density.
 *   Clamped to [0, [ElegantBlurDefaults.MaxBlurRadius]].
 * @param noiseCoefficient Noise dithering coefficient for anti-banding.
 * @param colors Color adjustments and blend layers applied after blur.
 * @param highlight Optional edge highlight painted on top of the content. `null` skips drawing.
 * @param contentBlendMode Optional [ComposeBlendMode] for compositing content over the blur.
 *   Use [ComposeBlendMode.DstIn] for foreground blur (content alpha masks the blur).
 *   null means content draws normally on top.
 * @param enabled Whether the effect is active. When false, the effect is skipped and content draws normally.
 */
fun Modifier.elegantTextureEffect(
    backdrop: ElegantBackdrop,
    shape: Shape,
    blurRadiusX: Float,
    blurRadiusY: Float,
    noiseCoefficient: Float = ElegantBlurDefaults.NoiseCoefficient,
    colors: ElegantBlurColors = ElegantBlurColors(),
    highlight: ElegantHighlight? = null,
    contentBlendMode: ComposeBlendMode = ComposeBlendMode.SrcOver,
    enabled: Boolean = true,
): Modifier = this.elegantBackdrop(
    backdrop = backdrop,
    shape = { shape },
    effects = {
        elegantTextureBlurEffect(
            blurRadiusX = blurRadiusX,
            blurRadiusY = blurRadiusY,
            noiseCoefficient = noiseCoefficient,
            colors = colors,
        )
    },
    highlight = if (highlight != null) {
        { highlight }
    } else {
        null
    },
    contentBlendMode = contentBlendMode,
    enabled = enabled,
)

/**
 * Applies a **progressive (gradient) backdrop blur**: the blur strength ramps from full to zero
 * along [gradient]'s direction — a genuine medium blur in the middle of the ramp, a pixel-sharp
 * full-resolution clear end. Ideal for navigation bars and edge fades. Same color / blend / noise /
 * highlight pipeline as [elegantTextureBlur], applied so the effects fade out with the blur.
 *
 * Costs more than [elegantTextureBlur]: on top of the downscaled level stack, the pixel-sharp clear end
 * adds a full-resolution overlay pass per frame. Prefer it for bars and edge bands over large
 * fills.
 *
 * @param backdrop The [ElegantBackdrop] providing the background content to blur.
 * @param shape The shape provider for the blur region clipping.
 * @param blurRadius The blur radius in dp at full strength. Internally converted to pixels using
 *   display density. Clamped to [0, [ElegantBlurDefaults.MaxBlurRadius]].
 * @param gradient Direction and band controlling where the blur is full vs zero. Defaults to
 *   [ElegantProgressiveBlur.Top].
 * @param noiseCoefficient Noise dithering coefficient for anti-banding. 0 (the default) disables noise.
 * @param colors Color adjustments and blend layers applied after blur.
 * @param highlight Optional edge highlight painted on top of the content. `null` skips drawing.
 * @param contentBlendMode Optional [ComposeBlendMode] for compositing content over the blur.
 * @param enabled Whether blur is active. When false, the effect is skipped and content draws normally.
 */
fun Modifier.elegantProgressiveTextureBlur(
    backdrop: ElegantBackdrop,
    shape: Shape,
    blurRadius: Float = ElegantBlurDefaults.BlurRadius,
    gradient: ElegantProgressiveBlur = ElegantProgressiveBlur.Top,
    noiseCoefficient: Float = ElegantBlurDefaults.ProgressiveNoiseCoefficient,
    colors: ElegantBlurColors = ElegantBlurColors(),
    highlight: ElegantHighlight? = null,
    contentBlendMode: ComposeBlendMode = ComposeBlendMode.SrcOver,
    enabled: Boolean = true,
): Modifier = elegantProgressiveTextureBlur(
    backdrop = backdrop,
    shape = shape,
    blurRadiusX = blurRadius,
    blurRadiusY = blurRadius,
    gradient = gradient,
    noiseCoefficient = noiseCoefficient,
    colors = colors,
    highlight = highlight,
    contentBlendMode = contentBlendMode,
    enabled = enabled,
)

/**
 * Applies a progressive (gradient) backdrop blur with independent horizontal and vertical radii.
 *
 * @param backdrop The [ElegantBackdrop] providing the background content to blur.
 * @param shape The shape provider for the blur region clipping.
 * @param blurRadiusX The horizontal blur radius in dp at full strength. Clamped to
 *   [0, [ElegantBlurDefaults.MaxBlurRadius]].
 * @param blurRadiusY The vertical blur radius in dp at full strength. Clamped to
 *   [0, [ElegantBlurDefaults.MaxBlurRadius]].
 * @param gradient Direction and band controlling where the blur is full vs zero.
 * @param noiseCoefficient Noise dithering coefficient for anti-banding. 0 (the default) disables noise.
 * @param colors Color adjustments and blend layers applied after blur.
 * @param highlight Optional edge highlight painted on top of the content. `null` skips drawing.
 * @param contentBlendMode Optional [ComposeBlendMode] for compositing content over the blur.
 * @param enabled Whether blur is active. When false, the effect is skipped and content draws normally.
 */
fun Modifier.elegantProgressiveTextureBlur(
    backdrop: ElegantBackdrop,
    shape: Shape,
    blurRadiusX: Float,
    blurRadiusY: Float,
    gradient: ElegantProgressiveBlur = ElegantProgressiveBlur.Top,
    noiseCoefficient: Float = ElegantBlurDefaults.ProgressiveNoiseCoefficient,
    colors: ElegantBlurColors = ElegantBlurColors(),
    highlight: ElegantHighlight? = null,
    contentBlendMode: ComposeBlendMode = ComposeBlendMode.SrcOver,
    enabled: Boolean = true,
): Modifier = this.elegantBackdrop(
    backdrop = backdrop,
    shape = { shape },
    effects = {
        elegantProgressiveTextureBlurEffect(
            blurRadiusX = blurRadiusX,
            blurRadiusY = blurRadiusY,
            gradient = gradient,
            noiseCoefficient = noiseCoefficient,
            colors = colors,
        )
    },
    highlight = if (highlight != null) {
        { highlight }
    } else {
        null
    },
    contentBlendMode = contentBlendMode,
    progressiveGradient = gradient,
    enabled = enabled,
)
