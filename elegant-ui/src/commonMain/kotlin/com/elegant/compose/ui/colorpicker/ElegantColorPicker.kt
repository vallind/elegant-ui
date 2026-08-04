package com.elegant.compose.ui.colorpicker

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.disabled
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.selected
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.elegant.compose.ui.foundation.theme.ElegantColors
import com.elegant.compose.ui.foundation.theme.ElegantTheme

/** Minimum interactive root height of every swatch. */
internal val ColorPickerMinimumTouchHeight: Dp = 48.dp

/** Ring width of a resting, hovered, or focused swatch. */
internal val ColorPickerRingWidth: Dp = 1.dp

/** Ring width of the selected swatch. */
internal val ColorPickerSelectedRingWidth: Dp = 2.dp

/** Opacity applied to every swatch while the picker is disabled. */
internal const val ColorPickerDisabledAlpha: Float = 0.4f

/**
 * The curated default palette rendered by [ElegantColorPicker]: 8 saturated chromatic colors
 * (red, orange, amber, green, teal, blue, violet, pink) followed by 8 light tints of the same
 * hues. These swatch values are a deliberate product constant and intentionally bypass theme
 * roles.
 */
internal val DefaultColorPickerPalette: List<Color> = listOf(
    Color(0xFFEF4444),
    Color(0xFFF97316),
    Color(0xFFF59E0B),
    Color(0xFF22C55E),
    Color(0xFF14B8A6),
    Color(0xFF3B82F6),
    Color(0xFF8B5CF6),
    Color(0xFFEC4899),
    Color(0xFFFECACA),
    Color(0xFFFED7AA),
    Color(0xFFFDE68A),
    Color(0xFFBBF7D0),
    Color(0xFF99F6E4),
    Color(0xFFBFDBFE),
    Color(0xFFDDD6FE),
    Color(0xFFFBCFE8),
)

/**
 * Theme-aware ring colors used by [ElegantColorPicker].
 *
 * Use [ElegantColorPickerDefaults.colors] for theme-aware defaults, then use [copy] for supported
 * product-level customization.
 *
 * @property containerColor base color that also defaults [hoveredContainerColor]; resolves to the
 *   theme hovered-surface role.
 * @property borderColor resting ring color around every swatch.
 * @property selectedBorderColor selection ring color of the selected swatch.
 * @property hoveredContainerColor ring color shown while a swatch is hovered or focused.
 */
@Immutable
public data class ElegantColorPickerColors(
    val containerColor: Color,
    val borderColor: Color,
    val selectedBorderColor: Color,
    val hoveredContainerColor: Color = containerColor,
)

/** Theme-aware defaults for [ElegantColorPicker]. */
public object ElegantColorPickerDefaults {
    /** Visual diameter of every swatch. */
    public val SwatchSize: Dp = 32.dp

    /** Gap between swatch interactive roots on both flow axes. */
    public val SwatchGap: Dp = 8.dp

    /** Returns theme-aware ring colors. */
    @Composable
    public fun colors(): ElegantColorPickerColors = resolveColorPickerColors(ElegantTheme.colors)

    /** Returns the curated default palette of 16 colors. */
    public fun palette(): List<Color> = DefaultColorPickerPalette
}

@Immutable
internal data class ColorSwatchVisuals(
    val ringColor: Color,
    val ringWidth: Dp,
    val fillAlpha: Float,
)

/**
 * Presents a wrapping grid of round color swatches with one controlled selection.
 *
 * [selectedColor] is owned by the caller: clicking a swatch invokes [onColorSelected] with the
 * chosen color, and the caller must write it back to keep the picker responsive. Swatches are
 * rendered from [colors] in a [FlowRow] that wraps on the [ElegantColorPickerDefaults.SwatchGap]
 * rhythm; each swatch is a 32dp circle inside a 48dp minimum interactive root.
 *
 * State precedence per swatch is disabled, selected, hovered, resting: the selected swatch draws
 * a 2dp [ElegantColorPickerColors.selectedBorderColor] ring, a hovered or keyboard-focused swatch
 * draws a 1dp [ElegantColorPickerColors.hoveredContainerColor] ring, and every other swatch keeps
 * a 1dp [ElegantColorPickerColors.borderColor] ring. While [enabled] is false all swatches render
 * at 40% opacity, lose their interaction visuals, and never invoke [onColorSelected].
 *
 * Every swatch announces [Role.Button], its `selected` state, and its `#RRGGBB` hex value as the
 * content description. The [colors] list is owned by the caller and should stay stable across
 * recompositions; an empty list renders nothing.
 *
 * @param selectedColor currently selected color, owned by the caller; equality is component-based.
 * @param onColorSelected callback invoked with the color chosen by the user.
 * @param modifier modifier applied once to the picker root.
 * @param enabled whether swatches accept clicks.
 * @param colors palette swatches rendered in the grid.
 * @param paletteColors theme-aware ring colors.
 */
@Composable
public fun ElegantColorPicker(
    selectedColor: Color,
    onColorSelected: (Color) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    colors: List<Color> = ElegantColorPickerDefaults.palette(),
    paletteColors: ElegantColorPickerColors = ElegantColorPickerDefaults.colors(),
) {
    if (colors.isEmpty()) return
    FlowRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(ElegantColorPickerDefaults.SwatchGap),
        verticalArrangement = Arrangement.spacedBy(ElegantColorPickerDefaults.SwatchGap),
    ) {
        colors.forEach { color ->
            val interactionSource = remember(color) { MutableInteractionSource() }
            val hovered by interactionSource.collectIsHoveredAsState()
            val focused by interactionSource.collectIsFocusedAsState()
            val selected = isSelected(color, selectedColor)
            val visuals = resolveColorSwatchVisuals(
                colors = paletteColors,
                enabled = enabled,
                selected = selected,
                hovered = hovered || focused,
            )
            Box(
                modifier = Modifier
                    .defaultMinSize(minHeight = ColorPickerMinimumTouchHeight)
                    .semantics(mergeDescendants = true) {
                        role = Role.Button
                        if (!enabled) disabled()
                        this.selected = selected
                        contentDescription = colorHex(color)
                    }
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null,
                        enabled = enabled,
                        role = Role.Button,
                        onClick = { onColorSelected(color) },
                    ),
                contentAlignment = Alignment.Center,
            ) {
                Box(
                    modifier = Modifier
                        .alpha(visuals.fillAlpha)
                        .size(ElegantColorPickerDefaults.SwatchSize)
                        .clip(CircleShape)
                        .background(color)
                        .border(
                            width = visuals.ringWidth,
                            color = visuals.ringColor,
                            shape = CircleShape,
                        ),
                )
            }
        }
    }
}

/** Resolves theme-aware ring colors for [ElegantColorPicker]. */
internal fun resolveColorPickerColors(themeColors: ElegantColors): ElegantColorPickerColors =
    ElegantColorPickerColors(
        containerColor = themeColors.surfaceHover,
        borderColor = themeColors.borderDefault,
        selectedBorderColor = themeColors.interactivePrimary,
    )

/** Resolves the swatch ring and opacity following the disabled, selected, hovered, resting chain. */
internal fun resolveColorSwatchVisuals(
    colors: ElegantColorPickerColors,
    enabled: Boolean,
    selected: Boolean,
    hovered: Boolean,
): ColorSwatchVisuals {
    val fillAlpha = if (enabled) 1f else ColorPickerDisabledAlpha
    return when {
        !enabled -> ColorSwatchVisuals(
            ringColor = colors.borderColor,
            ringWidth = ColorPickerRingWidth,
            fillAlpha = fillAlpha,
        )

        selected -> ColorSwatchVisuals(
            ringColor = colors.selectedBorderColor,
            ringWidth = ColorPickerSelectedRingWidth,
            fillAlpha = fillAlpha,
        )

        hovered -> ColorSwatchVisuals(
            ringColor = colors.hoveredContainerColor,
            ringWidth = ColorPickerRingWidth,
            fillAlpha = fillAlpha,
        )

        else -> ColorSwatchVisuals(
            ringColor = colors.borderColor,
            ringWidth = ColorPickerRingWidth,
            fillAlpha = fillAlpha,
        )
    }
}

/** Returns whether [color] matches [selectedColor] by component equality. */
internal fun isSelected(color: Color, selectedColor: Color): Boolean = color == selectedColor

/** Formats [color] as an uppercase `#RRGGBB` hex string, dropping the alpha channel. */
internal fun colorHex(color: Color): String = buildString {
    append('#')
    append(hexByte(color.red))
    append(hexByte(color.green))
    append(hexByte(color.blue))
}

private fun hexByte(value: Float): String =
    (value * 255f + 0.5f).toInt().coerceIn(0, 255).toString(16).uppercase().padStart(2, '0')
