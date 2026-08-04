package com.elegant.compose.ui.colorpicker

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.elegant.compose.ui.theme.ElegantSpacing

/**
 * Combines [ElegantColorArea] and [ElegantHueSlider] into one free-form color-selection panel.
 *
 * The panel is a controlled component: [color] is owned by the caller, and every interaction
 * reports the resolved color through [onColorChange], which the caller must write back. Dragging
 * or tapping the area keeps the hue of [color] and changes its saturation and value; dragging or
 * tapping the hue slider keeps the saturation and value and changes the hue. The two controls are
 * stacked on the [ElegantSpacing.lg] rhythm, and [enabled] is forwarded to both.
 *
 * @param color currently selected color, owned by the caller.
 * @param onColorChange callback invoked with the color resolved by either control.
 * @param modifier modifier applied once to the panel column.
 * @param enabled whether either control accepts user interaction.
 * @param areaColors theme-aware colors forwarded to [ElegantColorArea].
 * @param hueColors theme-aware colors forwarded to [ElegantHueSlider].
 */
@Composable
public fun ElegantColorPickerPanel(
    color: Color,
    onColorChange: (Color) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    areaColors: ElegantColorAreaColors = ElegantColorAreaDefaults.colors(),
    hueColors: ElegantHueSliderColors = ElegantHueSliderDefaults.colors(),
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(ElegantSpacing.lg),
    ) {
        ElegantColorArea(
            color = color,
            onColorChange = onColorChange,
            enabled = enabled,
            colors = areaColors,
        )
        ElegantHueSlider(
            hue = rgbToHsv(color).hue,
            onHueChange = { hue ->
                val hsv = rgbToHsv(color)
                onColorChange(hsvToRgb(clampHsv(HsvColor(hue, hsv.saturation, hsv.value))))
            },
            enabled = enabled,
            colors = hueColors,
        )
    }
}
