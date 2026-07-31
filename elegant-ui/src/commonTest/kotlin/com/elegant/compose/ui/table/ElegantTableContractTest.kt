package com.elegant.compose.ui.table

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.elegant.compose.ui.theme.ElegantDarkColors
import com.elegant.compose.ui.theme.ElegantLightColors
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue

internal class ElegantTableContractTest {

    @Test
    fun publicModelsRemainStable() {
        val column = ElegantTableColumn(title = "Component")
        assertEquals("Component", column.title)
        assertEquals(1f, column.weight)
        assertEquals(TextAlign.Start, column.textAlign)
        assertEquals(null, column.width)

        val fixed = ElegantTableColumn(
            title = "Count",
            weight = 0.5f,
            textAlign = TextAlign.End,
            width = 64.dp,
        )
        assertEquals("Count", fixed.title)
        assertEquals(0.5f, fixed.weight)
        assertEquals(TextAlign.End, fixed.textAlign)
        assertEquals(64.dp, fixed.width)

        val row = ElegantTableRow(cells = listOf("Button", "Available"))
        assertEquals(listOf("Button", "Available"), row.cells)
    }

    @Test
    fun publicMetricsRemainOnTheElegantRhythm() {
        assertEquals(44.dp, ElegantTableDefaults.RowMinHeight)
        assertEquals(12.dp, ElegantTableDefaults.CellHorizontalPadding)
        assertEquals(40.dp, ElegantTableDefaults.HeaderHeight)
    }

    @Test
    fun validationAcceptsMatchingRowsAndRejectsMismatchedRows() {
        val columns = listOf(
            ElegantTableColumn(title = "Component"),
            ElegantTableColumn(title = "Status"),
        )

        assertTrue(validateTable(columns, listOf(ElegantTableRow(cells = listOf("Button", "Available")))))
        assertTrue(validateTable(columns, emptyList()))
        assertFalse(validateTable(columns, listOf(ElegantTableRow(cells = listOf("Button")))))
        assertFalse(
            validateTable(
                columns,
                listOf(ElegantTableRow(cells = listOf("Button", "Available", "Web"))),
            ),
        )
        assertFalse(
            validateTable(
                columns,
                listOf(
                    ElegantTableRow(cells = listOf("Button")),
                    ElegantTableRow(cells = listOf("Tag", "Available")),
                ),
            ),
        )
        assertTrue(validateTable(emptyList(), listOf(ElegantTableRow(cells = emptyList()))))
        assertFalse(validateTable(emptyList(), listOf(ElegantTableRow(cells = listOf("Button")))))
    }

    @Test
    fun weightsCoerceToPositiveValues() {
        assertEquals(1f, resolveColumnWeight(ElegantTableColumn(title = "Default")))
        assertEquals(2.5f, resolveColumnWeight(ElegantTableColumn(title = "Heavy", weight = 2.5f)))
        assertEquals(1f, resolveColumnWeight(ElegantTableColumn(title = "Zero", weight = 0f)))
        assertEquals(1f, resolveColumnWeight(ElegantTableColumn(title = "Negative", weight = -1f)))
        assertEquals(1f, resolveColumnWeight(ElegantTableColumn(title = "NaN", weight = Float.NaN)))
        assertEquals(
            1f,
            resolveColumnWeight(ElegantTableColumn(title = "Infinite", weight = Float.POSITIVE_INFINITY)),
        )
    }

    @Test
    fun defaultColorsResolveSemanticThemeRoles() {
        val light = resolveTableColors(ElegantLightColors)

        assertEquals(ElegantLightColors.backgroundSubtle, light.headerContainerColor)
        assertEquals(ElegantLightColors.textSecondary, light.headerContentColor)
        assertEquals(ElegantLightColors.textPrimary, light.rowContentColor)
        assertEquals(Color.Transparent, light.rowBackgroundColor)
        assertEquals(ElegantLightColors.borderDefault, light.borderColor)
    }

    @Test
    fun defaultColorsAdaptBetweenLightAndDarkThemes() {
        val light = resolveTableColors(ElegantLightColors)
        val dark = resolveTableColors(ElegantDarkColors)

        assertNotEquals(light.headerContainerColor, dark.headerContainerColor)
        assertNotEquals(light.headerContentColor, dark.headerContentColor)
        assertNotEquals(light.rowContentColor, dark.rowContentColor)
        assertEquals(Color.Transparent, dark.rowBackgroundColor)
        assertNotEquals(light.borderColor, dark.borderColor)
    }

    @Test
    fun customColorsRemainAnImmutableValueContract() {
        val colors = ElegantTableColors(
            headerContainerColor = Color.White,
            headerContentColor = Color.Black,
            rowContentColor = Color.DarkGray,
            rowBackgroundColor = Color.Transparent,
            borderColor = Color.Gray,
        )

        assertEquals(Color.White, colors.headerContainerColor)
        assertEquals(Color.DarkGray, colors.rowContentColor)
        assertEquals(colors, colors.copy())
    }
}
