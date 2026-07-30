package com.elegant.compose.showcase

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.elegant.compose.showcase.generated.resources.Res
import com.elegant.compose.showcase.generated.resources.add_rounded
import com.elegant.compose.showcase.generated.resources.arrow_forward_rounded
import com.elegant.compose.showcase.generated.resources.check_rounded
import com.elegant.compose.ui.button.ElegantButton
import com.elegant.compose.ui.button.ElegantButtonSize
import com.elegant.compose.ui.button.ElegantButtonStyle
import com.elegant.compose.ui.theme.ElegantRadius
import com.elegant.compose.ui.theme.ElegantSpacing
import com.elegant.compose.ui.theme.ElegantTheme
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

internal val SupportedShowcaseComponentIds: Set<String> = setOf("button")

/**
 * Shared component showcase used by Android, Desktop JVM, and Web/Wasm launchers.
 *
 * [componentId] selects the component page requested by the documentation iframe. Unknown values
 * render a clear fallback instead of silently displaying an unrelated component.
 */
@Composable
public fun ElegantShowcaseApp(
    componentId: String = "button",
) {
    when (componentId) {
        "button" -> ButtonShowcase()
        else -> UnknownComponent(componentId)
    }
}

@Composable
private fun ButtonShowcase() {
    var darkTheme by rememberSaveable { mutableStateOf(false) }
    var loading by rememberSaveable { mutableStateOf(false) }
    var tapCount by remember { mutableIntStateOf(0) }

    ElegantTheme(darkTheme = darkTheme) {
        val colors = ElegantTheme.colors
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxSize()
                .background(colors.backgroundCanvas),
        ) {
            val compact = maxWidth < 600.dp
            val pagePadding = if (compact) ElegantSpacing.lg else ElegantSpacing.xxxl
            val contentWidth = (maxWidth - pagePadding * 2)
                .coerceAtMost(880.dp)
                .coerceAtLeast(0.dp)
            val contentOffset = ((maxWidth - contentWidth) / 2)
                .coerceAtLeast(0.dp)

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .statusBarsPadding()
                    .padding(vertical = ElegantSpacing.xl),
                horizontalAlignment = Alignment.Start,
            ) {
                Column(
                    modifier = Modifier
                        .width(contentWidth)
                        .offset(x = contentOffset),
                    verticalArrangement = Arrangement.spacedBy(ElegantSpacing.xl),
                ) {
                    ShowcaseHeader(
                        compact = compact,
                        darkTheme = darkTheme,
                        onDarkThemeChange = { darkTheme = it },
                    )

                    DemoCard(
                        compact = compact,
                        eyebrow = "FOUNDATIONS",
                        title = "Clear action hierarchy",
                        description = "Three emphasis levels share one optical rhythm across every target.",
                    ) {
                        Text(
                            text = "Size scale",
                            style = ElegantTheme.typography.bodyMedium,
                            color = colors.textSecondary,
                        )
                        SizeRow(
                            style = ElegantButtonStyle.Primary,
                            onClick = { tapCount++ },
                        )

                        Text(
                            text = "Emphasis",
                            style = ElegantTheme.typography.bodyMedium,
                            color = colors.textSecondary,
                        )
                        FlowRow(
                            horizontalArrangement = Arrangement.spacedBy(ElegantSpacing.md),
                            verticalArrangement = Arrangement.spacedBy(ElegantSpacing.md),
                        ) {
                            ElegantButton(
                                onClick = { tapCount++ },
                                leadingIcon = { ResourceIcon(Res.drawable.add_rounded) },
                            ) {
                                Text("Create")
                            }
                            ElegantButton(
                                onClick = { tapCount++ },
                                style = ElegantButtonStyle.Secondary,
                            ) {
                                Text("Review")
                            }
                            ElegantButton(
                                onClick = { tapCount++ },
                                style = ElegantButtonStyle.Tertiary,
                                trailingIcon = { ForwardIcon() },
                            ) {
                                Text("Learn more")
                            }
                        }
                    }

                    DemoCard(
                        compact = compact,
                        eyebrow = "IN CONTEXT",
                        title = "Ready to publish",
                        description = "Actions stay balanced inside a realistic confirmation surface.",
                    ) {
                        ConfirmationSurface()

                        FlowRow(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(ElegantSpacing.md),
                            verticalArrangement = Arrangement.spacedBy(ElegantSpacing.md),
                        ) {
                            ElegantButton(
                                onClick = { tapCount++ },
                                style = ElegantButtonStyle.Secondary,
                            ) {
                                Text("Save draft")
                            }
                            ElegantButton(
                                onClick = { loading = !loading },
                                loading = loading,
                                loadingStateDescription = "Publishing",
                                leadingIcon = { ResourceIcon(Res.drawable.check_rounded) },
                            ) {
                                Text("Publish changes")
                            }
                        }
                    }

                    DemoCard(
                        compact = compact,
                        eyebrow = "STATES",
                        title = "Interaction feedback",
                        description = "Hover, press, keyboard focus, loading, and disabled states remain distinct.",
                    ) {
                        FlowRow(
                            horizontalArrangement = Arrangement.spacedBy(ElegantSpacing.md),
                            verticalArrangement = Arrangement.spacedBy(ElegantSpacing.md),
                        ) {
                            ElegantButton(
                                onClick = { tapCount++ },
                                style = ElegantButtonStyle.Secondary,
                                leadingIcon = { ResourceIcon(Res.drawable.add_rounded) },
                                trailingIcon = { ForwardIcon() },
                            ) {
                                Text("With icons")
                            }
                            ElegantButton(
                                onClick = {},
                                enabled = false,
                            ) {
                                Text("Disabled")
                            }
                        }

                        Text(
                            text = "Accepted actions  $tapCount",
                            color = colors.textSecondary,
                            style = ElegantTheme.typography.bodyMedium,
                        )
                    }

                    Spacer(Modifier.height(ElegantSpacing.md))
                }
            }
        }
    }
}

@Composable
private fun ShowcaseHeader(
    compact: Boolean,
    darkTheme: Boolean,
    onDarkThemeChange: (Boolean) -> Unit,
) {
    if (compact) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(ElegantSpacing.md),
        ) {
            HeaderTitle()
            ThemeToggle(
                darkTheme = darkTheme,
                onDarkThemeChange = onDarkThemeChange,
            )
        }
    } else {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            HeaderTitle(Modifier.weight(1f))
            ThemeToggle(
                darkTheme = darkTheme,
                onDarkThemeChange = onDarkThemeChange,
            )
        }
    }
}

@Composable
private fun HeaderTitle(
    modifier: Modifier = Modifier,
) {
    val colors = ElegantTheme.colors
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(ElegantSpacing.xs),
    ) {
        Text(
            text = "Elegant Button",
            color = colors.textPrimary,
            fontSize = 28.sp,
            lineHeight = 34.sp,
            fontWeight = FontWeight.SemiBold,
            letterSpacing = (-0.4).sp,
        )
        Text(
            text = "One shared contract · Android · Desktop · Web",
            modifier = Modifier.fillMaxWidth(),
            color = colors.textSecondary,
            style = ElegantTheme.typography.bodyMedium,
        )
    }
}

@Composable
private fun ThemeToggle(
    darkTheme: Boolean,
    onDarkThemeChange: (Boolean) -> Unit,
) {
    val colors = ElegantTheme.colors
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(ElegantSpacing.md),
    ) {
        Text(
            text = if (darkTheme) "Dark" else "Light",
            color = colors.textSecondary,
            style = ElegantTheme.typography.bodyMedium,
        )
        Switch(
            checked = darkTheme,
            onCheckedChange = onDarkThemeChange,
        )
    }
}

@Composable
private fun ConfirmationSurface() {
    val colors = ElegantTheme.colors
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = colors.surfaceHover,
                shape = RoundedCornerShape(ElegantRadius.md),
            )
            .padding(ElegantSpacing.lg),
        horizontalArrangement = Arrangement.spacedBy(ElegantSpacing.lg),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .background(
                    color = colors.interactivePrimary,
                    shape = RoundedCornerShape(ElegantRadius.sm),
                ),
            contentAlignment = Alignment.Center,
        ) {
            ResourceIcon(
                resource = Res.drawable.check_rounded,
                modifier = Modifier.size(18.dp),
                tint = colors.textInverse,
            )
        }
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(ElegantSpacing.xxs),
        ) {
            Text(
                text = "Button milestone",
                color = colors.textPrimary,
                style = ElegantTheme.typography.titleMedium,
            )
            Text(
                text = "Interaction and visual tokens are synchronized across supported targets.",
                color = colors.textSecondary,
                style = ElegantTheme.typography.bodyMedium,
            )
        }
    }
}

@Composable
private fun SizeRow(
    style: ElegantButtonStyle,
    onClick: () -> Unit,
) {
    FlowRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(ElegantSpacing.md),
        verticalArrangement = Arrangement.spacedBy(ElegantSpacing.md),
        itemVerticalAlignment = Alignment.CenterVertically,
    ) {
        ElegantButton(onClick = onClick, style = style, size = ElegantButtonSize.Small) {
            Text("Small")
        }
        ElegantButton(onClick = onClick, style = style, size = ElegantButtonSize.Medium) {
            Text("Medium")
        }
        ElegantButton(onClick = onClick, style = style, size = ElegantButtonSize.Large) {
            Text("Large")
        }
    }
}

@Composable
private fun DemoCard(
    compact: Boolean,
    eyebrow: String,
    title: String,
    description: String,
    content: @Composable () -> Unit,
) {
    val colors = ElegantTheme.colors
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = colors.surfaceRaised,
                shape = RoundedCornerShape(ElegantRadius.lg),
            )
            .border(
                width = 1.dp,
                color = colors.borderDefault,
                shape = RoundedCornerShape(ElegantRadius.lg),
            )
            .padding(if (compact) ElegantSpacing.xl else ElegantSpacing.xxl),
        verticalArrangement = Arrangement.spacedBy(ElegantSpacing.lg),
    ) {
        Text(
            text = eyebrow,
            color = colors.interactivePrimary,
            fontSize = 11.sp,
            lineHeight = 14.sp,
            fontWeight = FontWeight.SemiBold,
            letterSpacing = 1.sp,
        )
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(ElegantSpacing.xs),
        ) {
            Text(
                text = title,
                color = colors.textPrimary,
                style = ElegantTheme.typography.titleMedium,
            )
            Text(
                text = description,
                modifier = Modifier.fillMaxWidth(),
                color = colors.textSecondary,
                style = ElegantTheme.typography.bodyMedium,
            )
        }
        content()
    }
}

@Composable
private fun ResourceIcon(
    resource: DrawableResource,
    modifier: Modifier = Modifier,
    tint: Color = LocalContentColor.current,
) {
    Icon(
        painter = painterResource(resource),
        contentDescription = null,
        modifier = modifier.fillMaxSize(),
        tint = tint,
    )
}

@Composable
private fun ForwardIcon() {
    val direction = LocalLayoutDirection.current
    ResourceIcon(
        resource = Res.drawable.arrow_forward_rounded,
        modifier = Modifier.graphicsLayer {
            scaleX = if (direction == LayoutDirection.Rtl) -1f else 1f
        },
    )
}

@Composable
private fun UnknownComponent(componentId: String) {
    ElegantTheme {
        val colors = ElegantTheme.colors
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(colors.backgroundCanvas)
                .padding(ElegantSpacing.xxxl),
            verticalArrangement = Arrangement.spacedBy(ElegantSpacing.md),
        ) {
            Text(
                text = "Unknown component",
                color = colors.textPrimary,
                style = ElegantTheme.typography.titleMedium,
            )
            Text(
                text = componentId,
                color = colors.textSecondary,
                style = ElegantTheme.typography.bodyMedium,
            )
        }
    }
}
