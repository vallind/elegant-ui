package com.elegant.compose.ui.foundation.effect

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotSame
import kotlin.test.assertSame

internal class ElegantBlurContractTest {

    @Test
    fun resolveBlurRadiusCoercesNonPositiveAndNonFiniteValuesToZero() {
        assertEquals(0.dp, resolveBlurRadius(0.dp))
        assertEquals(0.dp, resolveBlurRadius((-4).dp))
        assertEquals(0.dp, resolveBlurRadius(Dp(Float.NaN)))
        assertEquals(0.dp, resolveBlurRadius(Dp(Float.POSITIVE_INFINITY)))
        assertEquals(0.dp, resolveBlurRadius(Dp(Float.NEGATIVE_INFINITY)))
    }

    @Test
    fun resolveBlurRadiusPassesPositiveValuesThrough() {
        assertEquals(8.dp, resolveBlurRadius(8.dp))
        assertEquals(0.5.dp, resolveBlurRadius(0.5.dp))
        assertEquals(Dp(2.25f), resolveBlurRadius(Dp(2.25f)))
    }

    @Test
    fun edgeTreatmentsMapOntoTheComposeTreatments() {
        assertEquals(
            BlurredEdgeTreatment.Rectangle,
            resolveBlurEdgeTreatment(BlurEdgeTreatment.Rectangle),
        )
        assertEquals(
            BlurredEdgeTreatment.Unbounded,
            resolveBlurEdgeTreatment(BlurEdgeTreatment.Unbounded),
        )
    }

    @Test
    fun enumDeclaresRectangleFirstThenUnbounded() {
        assertEquals(
            listOf(BlurEdgeTreatment.Rectangle, BlurEdgeTreatment.Unbounded),
            BlurEdgeTreatment.entries.toList(),
        )
    }

    @Test
    fun zeroOrNegativeRadiusLeavesTheModifierUntouched() {
        val modifier = Modifier

        assertSame(modifier, modifier.elegantBlur(0.dp))
        assertSame(modifier, modifier.elegantBlur((-10).dp))
        assertSame(modifier, modifier.elegantBlur(Dp(Float.NaN)))
        assertSame(modifier, modifier.elegantBlur(Dp(Float.POSITIVE_INFINITY)))
    }

    @Test
    fun positiveRadiusAttachesABlurModifier() {
        assertNotSame(Modifier, Modifier.elegantBlur(8.dp))
        assertNotSame(Modifier, Modifier.elegantBlur(8.dp, BlurEdgeTreatment.Unbounded))
    }
}
