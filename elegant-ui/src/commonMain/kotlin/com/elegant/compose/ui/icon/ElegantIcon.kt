package com.elegant.compose.ui.icon

import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Renders a built-in [ImageVector] icon from [ElegantIcons] with the current content color.
 *
 * A null [contentDescription] makes the icon decorative; a non-null value labels it for
 * accessibility. The icon is rendered through a themed Material icon wrapper whose visual and
 * semantic contract is fully owned by this function.
 *
 * @param icon the vector icon to draw.
 * @param contentDescription localized accessibility label, or null for decorative icons.
 * @param modifier modifier applied once to the icon root.
 * @param tint icon color; defaults to the ambient content color.
 */
@Composable
public fun ElegantIcon(
    icon: ImageVector,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    tint: Color = androidx.compose.material3.LocalContentColor.current,
) {
    Icon(
        imageVector = icon,
        contentDescription = contentDescription,
        modifier = modifier,
        tint = tint,
    )
}

/** Defaults shared by the Elegant UI icon API. */
public object ElegantIconDefaults {
    /** Default icon edge length. */
    public val Size: Dp = 24.dp
}
