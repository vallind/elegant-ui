package com.elegant.compose.showcase

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.elegant.compose.ui.button.ElegantButton
import com.elegant.compose.ui.button.ElegantButtonSize
import com.elegant.compose.ui.button.ElegantButtonStyle
import com.elegant.compose.ui.theme.ElegantTheme

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
            val horizontalPadding = if (maxWidth < 480.dp) 12.dp else 20.dp

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .statusBarsPadding()
                    .padding(horizontal = horizontalPadding, vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(18.dp),
            ) {
                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    itemVerticalAlignment = Alignment.CenterVertically,
                ) {
                    Column {
                        Text(
                            text = "Elegant UI",
                            color = colors.textPrimary,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.SemiBold,
                        )
                        Text(
                            text = "Button · Android / Desktop / Web",
                            color = colors.textSecondary,
                        )
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("Dark", color = colors.textSecondary)
                        Switch(checked = darkTheme, onCheckedChange = { darkTheme = it })
                    }
                }

                DemoCard(title = "Primary · 主要操作") {
                    SizeRow(style = ElegantButtonStyle.Primary, onClick = { tapCount++ })
                    ElegantButton(
                        onClick = { loading = !loading },
                        style = ElegantButtonStyle.Primary,
                        loading = loading,
                        leadingIcon = { Text("+") },
                        trailingIcon = { Text("→") },
                    ) { Text(if (loading) "Loading" else "Toggle loading") }
                    ElegantButton(
                        onClick = {},
                        style = ElegantButtonStyle.Primary,
                        enabled = false,
                    ) { Text("Disabled") }
                }

                DemoCard(title = "Secondary · 次要操作") {
                    SizeRow(style = ElegantButtonStyle.Secondary, onClick = { tapCount++ })
                    ElegantButton(
                        onClick = { tapCount++ },
                        style = ElegantButtonStyle.Secondary,
                        leadingIcon = { Text("+") },
                        trailingIcon = { Text("→") },
                    ) { Text("With icons") }
                    ElegantButton(
                        onClick = {},
                        style = ElegantButtonStyle.Secondary,
                        enabled = false,
                    ) { Text("Disabled") }
                }

                DemoCard(title = "Tertiary · 低强调操作") {
                    SizeRow(style = ElegantButtonStyle.Tertiary, onClick = { tapCount++ })
                    ElegantButton(
                        onClick = { tapCount++ },
                        style = ElegantButtonStyle.Tertiary,
                        trailingIcon = { Text("→") },
                    ) { Text("Learn more") }
                }

                DemoCard(title = "Interaction check") {
                    Text(
                        text = "Tap count: $tapCount",
                        color = colors.textPrimary,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium,
                    )
                    Text(
                        text = "Verify press, focus, keyboard, loading, disabled, sizing, and Light/Dark behavior.",
                        color = colors.textSecondary,
                    )
                }

                Spacer(Modifier.height(24.dp))
            }
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
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
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
    title: String,
    content: @Composable () -> Unit,
) {
    val colors = ElegantTheme.colors
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(colors.surfaceRaised, RoundedCornerShape(18.dp))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Text(
            text = title,
            color = colors.textPrimary,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
        )
        content()
    }
}

@Composable
private fun UnknownComponent(componentId: String) {
    ElegantTheme {
        val colors = ElegantTheme.colors
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(colors.backgroundCanvas)
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text("Unknown component", color = colors.textPrimary, fontSize = 22.sp)
            Text(componentId, color = colors.textSecondary)
        }
    }
}
