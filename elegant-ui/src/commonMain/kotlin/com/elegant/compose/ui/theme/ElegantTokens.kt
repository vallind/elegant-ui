package com.elegant.compose.ui.theme

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/** Spacing tokens based on the Elegant UI 4dp layout rhythm. */
public object ElegantSpacing {
    /** No spacing. */
    public val none: Dp = 0.dp
    /** 2dp optical correction. */
    public val xxs: Dp = 2.dp
    /** 4dp base unit. */
    public val xs: Dp = 4.dp
    /** 6dp compact gap. */
    public val sm: Dp = 6.dp
    /** 8dp standard compact spacing. */
    public val md: Dp = 8.dp
    /** 12dp standard component spacing. */
    public val lg: Dp = 12.dp
    /** 16dp standard content spacing. */
    public val xl: Dp = 16.dp
    /** 20dp expanded content spacing. */
    public val xxl: Dp = 20.dp
    /** 24dp section spacing. */
    public val xxxl: Dp = 24.dp
}

/** Corner-radius tokens shared by Elegant UI components. */
public object ElegantRadius {
    /** Square corners. */
    public val none: Dp = 0.dp
    /** 4dp subtle rounding. */
    public val xs: Dp = 4.dp
    /** 8dp compact component rounding. */
    public val sm: Dp = 8.dp
    /** 12dp standard component rounding. */
    public val md: Dp = 12.dp
    /** 16dp prominent surface rounding. */
    public val lg: Dp = 16.dp
    /** Fully rounded shape token. */
    public val full: Dp = 999.dp
}

/** Motion-duration tokens shared by interactive Elegant UI components. */
public object ElegantMotion {
    /** Immediate feedback for a pointer or touch press. */
    public const val fastDurationMillis: Int = 90
    /** Standard transition for hover, focus, and state changes. */
    public const val standardDurationMillis: Int = 160
    /** Emphasized transition for larger content changes. */
    public const val emphasizedDurationMillis: Int = 220
}

/** Tonal elevation tokens shared by Elegant UI components. */
public object ElegantElevation {
    /** Flat elements without a cast shadow. */
    public val none: Dp = 0.dp
    /** Resting elevation for subtly raised controls. */
    public val low: Dp = 1.dp
    /** Hover or focus elevation for interactive controls. */
    public val medium: Dp = 3.dp
}
