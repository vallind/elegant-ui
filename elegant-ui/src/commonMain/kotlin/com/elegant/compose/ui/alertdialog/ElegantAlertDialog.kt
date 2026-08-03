package com.elegant.compose.ui.alertdialog

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.elegant.compose.ui.button.ElegantButton
import com.elegant.compose.ui.button.ElegantButtonSize
import com.elegant.compose.ui.button.ElegantButtonStyle
import com.elegant.compose.ui.theme.ElegantColors
import com.elegant.compose.ui.theme.ElegantElevation
import com.elegant.compose.ui.theme.ElegantMotion
import com.elegant.compose.ui.theme.ElegantRadius
import com.elegant.compose.ui.theme.ElegantSpacing
import com.elegant.compose.ui.theme.ElegantTheme

/**
 * Theme-aware colors used by [ElegantAlertDialog].
 *
 * Use [ElegantAlertDialogDefaults.colors] for theme-aware defaults, then use [copy] for supported
 * product-level customization.
 *
 * @property scrimColor dimming overlay drawn across the window behind the dialog surface.
 * @property containerColor dialog surface container color.
 * @property contentColor content color provided to the dialog content through [LocalContentColor].
 * @property titleColor color of the dialog title text.
 * @property descriptionColor color of the optional description text.
 */
@Immutable
public data class ElegantAlertDialogColors(
    val scrimColor: Color,
    val containerColor: Color,
    val contentColor: Color,
    val titleColor: Color,
    val descriptionColor: Color,
)

/** Theme-aware defaults for [ElegantAlertDialog]. */
public object ElegantAlertDialogDefaults {
    /** Maximum width of the dialog surface before the content wraps. */
    public val MaxWidth: Dp = resolveAlertDialogMaxWidth()

    /** Alpha applied to the standard black scrim overlay color. */
    public const val ScrimAlpha: Float = 0.4f

    /** Duration of the dialog entrance transition. */
    public const val AnimationDurationMillis: Int = ElegantMotion.emphasizedDurationMillis

    /** Returns theme-aware colors for the active Light or Dark theme. */
    @Composable
    public fun colors(): ElegantAlertDialogColors = resolveAlertDialogColors(ElegantTheme.colors)
}

/**
 * Shows a compact confirmation surface centered over a dimming scrim inside a platform dialog
 * window.
 *
 * [ElegantAlertDialog] is the confirmation variant of the Elegant UI overlay family: the caller
 * owns [visible] and removes the dialog from composition by setting it to false. The dialog
 * renders the full-screen scrim and a centered surface capped at [ElegantAlertDialogDefaults.MaxWidth]
 * with 24dp internal padding, a medium tonal shadow, and 16dp rounded corners. The surface holds
 * the [title] in the `titleMedium` style, the [description] in the `bodyMedium` style only while it
 * is non-blank, an optional [content] slot, and an action row that places an optional dismiss
 * button and the confirm button at the end.
 *
 * Dismissal contract:
 * - an outside click on the scrim invokes [onDismissRequest] through `dismissOnClickOutside`;
 * - the system back key on Android or the Escape key on Desktop and Web invokes [onDismissRequest]
 *   through `dismissOnBackPress`;
 * - the dismiss button invokes [onDismiss] when provided and [onDismissRequest] otherwise;
 * - the confirm button invokes [onConfirm] and never dismisses the dialog itself; the caller keeps
 *   the dialog open or sets [visible] to false as the confirmation outcome requires.
 *
 * Focus contract: while visible, the platform dialog window captures focus, restricts focus
 * traversal to the dialog content, and makes the surrounding app inert; on dismissal, focus
 * returns to the previously focused element. The confirm and dismiss buttons are [ElegantButton]s
 * with their own roles and states; the dialog window provides the dialog semantics. The surface
 * fades and scales in (alpha 0 to 1, scale 0.98 to 1) over
 * [ElegantAlertDialogDefaults.AnimationDurationMillis] while the scrim appears with the dialog
 * window.
 *
 * @param visible whether the dialog is shown; false composes nothing.
 * @param onDismissRequest callback invoked for user-initiated dismissal: scrim click, back press
 * on Android, Escape on Desktop and Web, and the dismiss button when [onDismiss] is not provided.
 * @param modifier modifier applied once to the full-screen dialog root.
 * @param title dialog title text.
 * @param description optional supporting text; rendered only while non-blank.
 * @param confirmText label of the confirm button.
 * @param onConfirm callback invoked when the confirm button accepts an activation.
 * @param dismissText label of the dismiss button; rendered only while non-blank.
 * @param onDismiss optional callback invoked by the dismiss button; falls back to
 * [onDismissRequest] when null.
 * @param confirmEnabled whether the confirm button accepts activation.
 * @param colors theme-aware dialog colors.
 * @param content optional slot rendered between the description and the action row.
 */
@Composable
public fun ElegantAlertDialog(
    visible: Boolean,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    title: String,
    description: String? = null,
    confirmText: String,
    onConfirm: () -> Unit,
    dismissText: String? = null,
    onDismiss: (() -> Unit)? = null,
    confirmEnabled: Boolean = true,
    colors: ElegantAlertDialogColors = ElegantAlertDialogDefaults.colors(),
    content: @Composable () -> Unit = {},
) {
    if (!visible) {
        return
    }
    val entranceProgress = remember { Animatable(0f) }
    LaunchedEffect(Unit) {
        entranceProgress.animateTo(
            targetValue = 1f,
            animationSpec = tween(
                durationMillis = ElegantAlertDialogDefaults.AnimationDurationMillis,
                easing = FastOutSlowInEasing,
            ),
        )
    }
    val shape = RoundedCornerShape(ElegantRadius.lg)
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true,
            usePlatformDefaultWidth = false,
        ),
    ) {
        Box(modifier = modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(colors.scrimColor),
            )
            Box(
                modifier = Modifier
                    .align(Alignment.Center)
                    .graphicsLayer {
                        val progress = entranceProgress.value
                        alpha = progress
                        scaleX = 0.98f + 0.02f * progress
                        scaleY = 0.98f + 0.02f * progress
                    }
                    .widthIn(max = resolveAlertDialogMaxWidth())
                    .shadow(
                        elevation = ElegantElevation.medium,
                        shape = shape,
                        clip = false,
                    )
                    .clip(shape)
                    .background(colors.containerColor)
                    .padding(ElegantSpacing.xxxl),
            ) {
                CompositionLocalProvider(LocalContentColor provides colors.contentColor) {
                    Column {
                        Text(
                            text = title,
                            style = ElegantTheme.typography.titleMedium,
                            color = colors.titleColor,
                        )
                        val resolvedDescription = resolveDescription(description)
                        if (resolvedDescription != null) {
                            Spacer(Modifier.height(ElegantSpacing.md))
                            Text(
                                text = resolvedDescription,
                                style = ElegantTheme.typography.bodyMedium,
                                color = colors.descriptionColor,
                            )
                        }
                        Spacer(Modifier.height(ElegantSpacing.xl))
                        content()
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = ElegantSpacing.xl),
                            horizontalArrangement = Arrangement.spacedBy(ElegantSpacing.lg, Alignment.End),
                        ) {
                            if (dismissButtonVisible(dismissText)) {
                                ElegantButton(
                                    onClick = onDismiss ?: onDismissRequest,
                                    style = ElegantButtonStyle.Secondary,
                                    size = ElegantButtonSize.Small,
                                ) {
                                    Text(dismissText.orEmpty())
                                }
                            }
                            ElegantButton(
                                onClick = onConfirm,
                                enabled = confirmEnabled,
                                size = ElegantButtonSize.Small,
                            ) {
                                Text(confirmText)
                            }
                        }
                    }
                }
            }
        }
    }
}

/** Returns the maximum width of the dialog surface before the content wraps. */
internal fun resolveAlertDialogMaxWidth(): Dp = 400.dp

/**
 * Resolves theme-aware alert dialog colors.
 *
 * The scrim is a fixed black overlay at [ElegantAlertDialogDefaults.ScrimAlpha] rather than a theme
 * role, because the dimming layer must stay visibly darker than both Light and Dark surfaces. The
 * title shares the primary text color, while the description steps down to the secondary role.
 */
internal fun resolveAlertDialogColors(themeColors: ElegantColors): ElegantAlertDialogColors =
    ElegantAlertDialogColors(
        scrimColor = Color.Black.copy(alpha = ElegantAlertDialogDefaults.ScrimAlpha),
        containerColor = themeColors.surfaceRaised,
        contentColor = themeColors.textPrimary,
        titleColor = themeColors.textPrimary,
        descriptionColor = themeColors.textSecondary,
    )

/** Returns [description] as-is, or null when it is null or blank. */
internal fun resolveDescription(description: String?): String? = description?.takeIf { it.isNotBlank() }

/** Returns whether a non-blank [dismissText] should render the dismiss button. */
internal fun dismissButtonVisible(dismissText: String?): Boolean = !dismissText.isNullOrBlank()
