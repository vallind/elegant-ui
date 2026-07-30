package com.elegant.compose.ui.avatar

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.elegant.compose.ui.theme.ElegantTheme

/** Visual size presets supported by [ElegantAvatar]. */
public enum class ElegantAvatarSize {
    /** Compact 32dp avatar for dense rows and grouped identities. */
    Small,

    /** Default 40dp avatar for lists, cards, and profile summaries. */
    Medium,

    /** Prominent 56dp avatar for profile headers and spacious surfaces. */
    Large,
}

/**
 * Theme-aware colors used by [ElegantAvatar].
 *
 * @property containerColor background shown behind initials or transparent custom content.
 * @property contentColor initials and locally provided content color.
 * @property borderColor subtle outline separating the avatar from surrounding surfaces.
 */
@Immutable
public data class ElegantAvatarColors(
    val containerColor: Color,
    val contentColor: Color,
    val borderColor: Color,
)

/** Defaults and factories for [ElegantAvatar]. */
public object ElegantAvatarDefaults {
    /** Default optical outline width. */
    public val BorderWidth: Dp = 1.dp

    /**
     * Resolves a compact fallback label from [name].
     *
     * Multiple words use the first character of the first and last word. A single word uses its
     * first two letters, while a blank or unsupported name resolves to `?`.
     */
    public fun initials(name: String): String = resolveAvatarInitials(name)

    /** Returns theme-aware avatar colors. */
    @Composable
    public fun colors(): ElegantAvatarColors {
        val colors = ElegantTheme.colors
        return ElegantAvatarColors(
            containerColor = colors.surfaceHover,
            contentColor = colors.interactivePrimary,
            borderColor = colors.focusRing.copy(alpha = 0.36f),
        )
    }
}

@Immutable
internal data class AvatarMetrics(
    val containerSize: Dp,
    val textStyle: TextStyle,
)

/**
 * Displays a person or entity identity as initials or custom visual content.
 *
 * [name] supplies the default initials and accessible description. Set [contentDescription] to
 * `null` for a decorative avatar. When [content] is present it replaces the initials and should
 * keep its own content description null so the avatar owns one clear semantic label.
 *
 * Remote image loading, click behavior, presence indicators, and badges are intentionally outside
 * this display component. Compose those concerns around the avatar with dedicated APIs.
 *
 * @param name person or entity name used by the default fallback and accessibility semantics.
 * @param modifier modifier applied to the avatar container.
 * @param initials fallback label displayed when [content] is absent.
 * @param contentDescription localized semantic description, or null when decorative.
 * @param size visual size preset.
 * @param shape clipping and outline shape.
 * @param colors theme-aware container, content, and border colors.
 * @param borderWidth optical outline width.
 * @param content optional custom image, icon, or visual content replacing the initials.
 */
@Composable
public fun ElegantAvatar(
    name: String,
    modifier: Modifier = Modifier,
    initials: String = ElegantAvatarDefaults.initials(name),
    contentDescription: String? = name,
    size: ElegantAvatarSize = ElegantAvatarSize.Medium,
    shape: Shape = CircleShape,
    colors: ElegantAvatarColors = ElegantAvatarDefaults.colors(),
    borderWidth: Dp = ElegantAvatarDefaults.BorderWidth,
    content: (@Composable () -> Unit)? = null,
) {
    val metrics = avatarMetricsFor(size)
    val semanticModifier = if (contentDescription.isNullOrBlank()) {
        Modifier.clearAndSetSemantics {}
    } else {
        Modifier.semantics(mergeDescendants = true) {
            this.contentDescription = contentDescription
            role = Role.Image
        }
    }

    Box(
        modifier = modifier
            .then(semanticModifier)
            .size(metrics.containerSize)
            .clip(shape)
            .background(colors.containerColor)
            .border(
                width = borderWidth,
                color = colors.borderColor,
                shape = shape,
            ),
        contentAlignment = Alignment.Center,
    ) {
        CompositionLocalProvider(LocalContentColor provides colors.contentColor) {
            if (content == null) {
                Text(
                    text = initials.ifBlank { ElegantAvatarDefaults.initials(name) },
                    color = colors.contentColor,
                    style = metrics.textStyle,
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                    overflow = TextOverflow.Clip,
                )
            } else {
                content()
            }
        }
    }
}

@Composable
internal fun avatarMetricsFor(size: ElegantAvatarSize): AvatarMetrics = when (size) {
    ElegantAvatarSize.Small -> AvatarMetrics(
        containerSize = avatarContainerSizeFor(size),
        textStyle = ElegantTheme.typography.labelMedium,
    )

    ElegantAvatarSize.Medium -> AvatarMetrics(
        containerSize = avatarContainerSizeFor(size),
        textStyle = ElegantTheme.typography.labelLarge,
    )

    ElegantAvatarSize.Large -> AvatarMetrics(
        containerSize = avatarContainerSizeFor(size),
        textStyle = ElegantTheme.typography.titleMedium,
    )
}

internal fun avatarContainerSizeFor(size: ElegantAvatarSize): Dp = when (size) {
    ElegantAvatarSize.Small -> 32.dp
    ElegantAvatarSize.Medium -> 40.dp
    ElegantAvatarSize.Large -> 56.dp
}

internal fun resolveAvatarInitials(name: String): String {
    val words = name
        .trim()
        .split(Regex("\\s+"))
        .map { word -> word.filter(Char::isLetterOrDigit) }
        .filter(String::isNotEmpty)

    val value = when {
        words.isEmpty() -> "?"
        words.size == 1 -> words.first().take(2)
        else -> "${words.first().first()}${words.last().first()}"
    }
    return value.uppercase()
}
