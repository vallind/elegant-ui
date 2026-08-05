// Copyright 2026, elegant-ui contributors
// SPDX-License-Identifier: Apache-2.0
// Icon geometry replicated from compose-miuix-ui/miuix (Apache-2.0), Regular weight.

package com.elegant.compose.ui.foundation.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.PathNode
import androidx.compose.ui.graphics.vector.group
import androidx.compose.ui.unit.dp

private var backIconCache: ImageVector? = null

/** Arrow pointing back to the previous level. */
public val ElegantIcons.Back: ImageVector
    get() {
        backIconCache?.let { return it }
        return ImageVector.Builder(
            name = "ElegantIcons.Back",
            defaultWidth = 24.0f.dp,
            defaultHeight = 24.0f.dp,
            viewportWidth = 1330.8f,
            viewportHeight = 1330.8f,
        ).apply {
            group(scaleY = -1.0f, translationY = 1330.8f) {
                addPath(
                    pathData = listOf(
                        PathNode.MoveTo(255.9f, 622.4f),
                        PathNode.HorizontalTo(1187.9f),
                        PathNode.QuadTo(1203.9f, 622.4f, 1211.9f, 630.4f),
                        PathNode.QuadTo(1219.9f, 638.4f, 1219.9f, 653.4f),
                        PathNode.VerticalTo(680.4f),
                        PathNode.QuadTo(1219.9f, 693.4f, 1211.4f, 700.9f),
                        PathNode.QuadTo(1202.9f, 708.4f, 1187.9f, 708.4f),
                        PathNode.HorizontalTo(255.9f),
                        PathNode.LineTo(539.9f, 992.4f),
                        PathNode.QuadTo(549.9f, 1002.4f, 549.9f, 1012.4f),
                        PathNode.QuadTo(549.9f, 1022.4f, 537.9f, 1034.4f),
                        PathNode.LineTo(520.9f, 1051.4f),
                        PathNode.QuadTo(508.9f, 1063.4f, 498.9f, 1063.4f),
                        PathNode.QuadTo(488.9f, 1063.4f, 476.9f, 1051.4f),
                        PathNode.LineTo(129.9f, 703.4f),
                        PathNode.QuadTo(111.9f, 685.4f, 111.4f, 665.9f),
                        PathNode.QuadTo(110.9f, 646.4f, 130.9f, 626.4f),
                        PathNode.LineTo(476.9f, 280.4f),
                        PathNode.QuadTo(488.9f, 268.4f, 498.4f, 267.9f),
                        PathNode.QuadTo(507.9f, 267.4f, 520.9f, 280.4f),
                        PathNode.LineTo(539.9f, 299.4f),
                        PathNode.QuadTo(550.9f, 310.4f, 550.9f, 318.9f),
                        PathNode.QuadTo(550.9f, 327.4f, 538.9f, 339.4f),
                        PathNode.Close,
                    ),
                    fill = SolidColor(Color.Black),
                    fillAlpha = 1f,
                    pathFillType = PathFillType.NonZero,
                )
            }
        }.build().also { backIconCache = it }
    }

private var chevronBackwardIconCache: ImageVector? = null

/** Chevron pointing back. */
public val ElegantIcons.ChevronBackward: ImageVector
    get() {
        chevronBackwardIconCache?.let { return it }
        return ImageVector.Builder(
            name = "ElegantIcons.ChevronBackward",
            defaultWidth = 24.0f.dp,
            defaultHeight = 24.0f.dp,
            viewportWidth = 1207.2f,
            viewportHeight = 1207.2f,
        ).apply {
            group(scaleY = -1.0f, translationY = 1207.2f) {
                addPath(
                    pathData = listOf(
                        PathNode.MoveTo(333.6f, 583.6f),
                        PathNode.LineTo(807.6f, 110.6f),
                        PathNode.QuadTo(817.6f, 100.6f, 828.1f, 101.1f),
                        PathNode.QuadTo(838.6f, 101.6f, 852.6f, 115.6f),
                        PathNode.LineTo(870.6f, 133.6f),
                        PathNode.QuadTo(879.6f, 142.6f, 880.6f, 152.6f),
                        PathNode.QuadTo(881.6f, 162.6f, 873.6f, 170.6f),
                        PathNode.LineTo(442.6f, 602.6f),
                        PathNode.LineTo(873.6f, 1033.6f),
                        PathNode.QuadTo(881.6f, 1041.6f, 881.1f, 1052.6f),
                        PathNode.QuadTo(880.6f, 1063.6f, 873.6f, 1071.6f),
                        PathNode.LineTo(844.6f, 1099.6f),
                        PathNode.QuadTo(837.6f, 1106.6f, 827.6f, 1106.1f),
                        PathNode.QuadTo(817.6f, 1105.6f, 809.6f, 1097.6f),
                        PathNode.LineTo(332.6f, 620.6f),
                        PathNode.QuadTo(325.6f, 613.6f, 325.6f, 602.6f),
                        PathNode.QuadTo(325.6f, 591.6f, 333.6f, 583.6f),
                        PathNode.Close,
                    ),
                    fill = SolidColor(Color.Black),
                    fillAlpha = 1f,
                    pathFillType = PathFillType.NonZero,
                )
            }
        }.build().also { chevronBackwardIconCache = it }
    }

private var chevronForwardIconCache: ImageVector? = null

/** Chevron pointing forward. */
public val ElegantIcons.ChevronForward: ImageVector
    get() {
        chevronForwardIconCache?.let { return it }
        return ImageVector.Builder(
            name = "ElegantIcons.ChevronForward",
            defaultWidth = 24.0f.dp,
            defaultHeight = 24.0f.dp,
            viewportWidth = 1207.2f,
            viewportHeight = 1207.2f,
        ).apply {
            group(scaleY = -1.0f, translationY = 1207.2f) {
                addPath(
                    pathData = listOf(
                        PathNode.MoveTo(362.6f, 1099.6f),
                        PathNode.LineTo(333.6f, 1071.6f),
                        PathNode.QuadTo(326.6f, 1063.6f, 326.1f, 1052.6f),
                        PathNode.QuadTo(325.6f, 1041.6f, 333.6f, 1033.6f),
                        PathNode.LineTo(764.6f, 602.6f),
                        PathNode.LineTo(333.6f, 170.6f),
                        PathNode.QuadTo(325.6f, 162.6f, 326.6f, 152.6f),
                        PathNode.QuadTo(327.6f, 142.6f, 336.6f, 133.6f),
                        PathNode.LineTo(354.6f, 115.6f),
                        PathNode.QuadTo(368.6f, 101.6f, 379.1f, 101.1f),
                        PathNode.QuadTo(389.6f, 100.6f, 399.6f, 110.6f),
                        PathNode.LineTo(873.6f, 583.6f),
                        PathNode.QuadTo(881.6f, 591.6f, 881.6f, 602.6f),
                        PathNode.QuadTo(881.6f, 613.6f, 874.6f, 620.6f),
                        PathNode.LineTo(397.6f, 1097.6f),
                        PathNode.QuadTo(389.6f, 1105.6f, 379.6f, 1106.1f),
                        PathNode.QuadTo(369.6f, 1106.6f, 362.6f, 1099.6f),
                        PathNode.Close,
                    ),
                    fill = SolidColor(Color.Black),
                    fillAlpha = 1f,
                    pathFillType = PathFillType.NonZero,
                )
            }
        }.build().also { chevronForwardIconCache = it }
    }

private var expandLessIconCache: ImageVector? = null

/** Chevron collapsing upward. */
public val ElegantIcons.ExpandLess: ImageVector
    get() {
        expandLessIconCache?.let { return it }
        return ImageVector.Builder(
            name = "ElegantIcons.ExpandLess",
            defaultWidth = 24.0f.dp,
            defaultHeight = 24.0f.dp,
            viewportWidth = 1122.0f,
            viewportHeight = 1122.0f,
        ).apply {
            group(scaleY = -1.0f, translationY = 1122.0f) {
                addPath(
                    pathData = listOf(
                        PathNode.MoveTo(739.5f, 119.5f),
                        PathNode.VerticalTo(378.5f),
                        PathNode.QuadTo(739.5f, 380.5f, 740.5f, 382.0f),
                        PathNode.QuadTo(741.5f, 383.5f, 743.5f, 383.5f),
                        PathNode.HorizontalTo(1002.5f),
                        PathNode.QuadTo(1013.5f, 383.5f, 1021.0f, 391.0f),
                        PathNode.QuadTo(1028.5f, 398.5f, 1028.5f, 409.5f),
                        PathNode.VerticalTo(446.5f),
                        PathNode.QuadTo(1028.5f, 457.5f, 1021.0f, 465.5f),
                        PathNode.QuadTo(1013.5f, 473.5f, 1002.5f, 473.5f),
                        PathNode.HorizontalTo(708.5f),
                        PathNode.QuadTo(679.5f, 473.5f, 664.0f, 458.0f),
                        PathNode.QuadTo(648.5f, 442.5f, 648.5f, 413.5f),
                        PathNode.VerticalTo(119.5f),
                        PathNode.QuadTo(648.5f, 108.5f, 656.5f, 101.0f),
                        PathNode.QuadTo(664.5f, 93.5f, 675.5f, 93.5f),
                        PathNode.HorizontalTo(712.5f),
                        PathNode.QuadTo(723.5f, 93.5f, 731.5f, 101.0f),
                        PathNode.QuadTo(739.5f, 108.5f, 739.5f, 119.5f),
                        PathNode.Close,
                        PathNode.MoveTo(473.5f, 709.5f),
                        PathNode.VerticalTo(1002.5f),
                        PathNode.QuadTo(473.5f, 1013.5f, 466.0f, 1021.0f),
                        PathNode.QuadTo(458.5f, 1028.5f, 447.5f, 1028.5f),
                        PathNode.HorizontalTo(410.5f),
                        PathNode.QuadTo(399.5f, 1028.5f, 391.5f, 1021.0f),
                        PathNode.QuadTo(383.5f, 1013.5f, 383.5f, 1002.5f),
                        PathNode.VerticalTo(744.5f),
                        PathNode.QuadTo(383.5f, 739.5f, 378.5f, 739.5f),
                        PathNode.HorizontalTo(120.5f),
                        PathNode.QuadTo(109.5f, 739.5f, 101.5f, 731.5f),
                        PathNode.QuadTo(93.5f, 723.5f, 93.5f, 712.5f),
                        PathNode.VerticalTo(675.5f),
                        PathNode.QuadTo(93.5f, 664.5f, 101.5f, 657.0f),
                        PathNode.QuadTo(109.5f, 649.5f, 120.5f, 649.5f),
                        PathNode.HorizontalTo(413.5f),
                        PathNode.QuadTo(442.5f, 649.5f, 458.0f, 665.0f),
                        PathNode.QuadTo(473.5f, 680.5f, 473.5f, 709.5f),
                        PathNode.Close,
                    ),
                    fill = SolidColor(Color.Black),
                    fillAlpha = 1f,
                    pathFillType = PathFillType.NonZero,
                )
            }
        }.build().also { expandLessIconCache = it }
    }

private var expandMoreIconCache: ImageVector? = null

/** Chevron expanding downward. */
public val ElegantIcons.ExpandMore: ImageVector
    get() {
        expandMoreIconCache?.let { return it }
        return ImageVector.Builder(
            name = "ElegantIcons.ExpandMore",
            defaultWidth = 24.0f.dp,
            defaultHeight = 24.0f.dp,
            viewportWidth = 1122.0f,
            viewportHeight = 1122.0f,
        ).apply {
            group(scaleY = -1.0f, translationY = 1122.0f) {
                addPath(
                    pathData = listOf(
                        PathNode.MoveTo(183.5f, 675.5f),
                        PathNode.VerticalTo(933.5f),
                        PathNode.QuadTo(183.5f, 935.5f, 185.0f, 937.0f),
                        PathNode.QuadTo(186.5f, 938.5f, 188.5f, 938.5f),
                        PathNode.HorizontalTo(446.5f),
                        PathNode.QuadTo(457.5f, 938.5f, 465.5f, 946.0f),
                        PathNode.QuadTo(473.5f, 953.5f, 473.5f, 964.5f),
                        PathNode.VerticalTo(1001.5f),
                        PathNode.QuadTo(473.5f, 1012.5f, 465.5f, 1020.5f),
                        PathNode.QuadTo(457.5f, 1028.5f, 446.5f, 1028.5f),
                        PathNode.HorizontalTo(153.5f),
                        PathNode.QuadTo(124.5f, 1028.5f, 109.0f, 1013.0f),
                        PathNode.QuadTo(93.5f, 997.5f, 93.5f, 968.5f),
                        PathNode.VerticalTo(675.5f),
                        PathNode.QuadTo(93.5f, 664.5f, 101.5f, 656.5f),
                        PathNode.QuadTo(109.5f, 648.5f, 120.5f, 648.5f),
                        PathNode.HorizontalTo(157.5f),
                        PathNode.QuadTo(168.5f, 648.5f, 176.0f, 656.5f),
                        PathNode.QuadTo(183.5f, 664.5f, 183.5f, 675.5f),
                        PathNode.Close,
                        PathNode.MoveTo(655.5f, 560.5f),
                        PathNode.QuadTo(655.5f, 600.5f, 627.5f, 628.0f),
                        PathNode.QuadTo(599.5f, 655.5f, 560.5f, 655.5f),
                        PathNode.QuadTo(520.5f, 655.5f, 493.0f, 628.0f),
                        PathNode.QuadTo(465.5f, 600.5f, 465.5f, 560.5f),
                        PathNode.QuadTo(465.5f, 521.5f, 493.0f, 493.5f),
                        PathNode.QuadTo(520.5f, 465.5f, 560.5f, 465.5f),
                        PathNode.QuadTo(599.5f, 465.5f, 627.5f, 493.5f),
                        PathNode.QuadTo(655.5f, 521.5f, 655.5f, 560.5f),
                        PathNode.Close,
                        PathNode.MoveTo(1028.5f, 153.5f),
                        PathNode.VerticalTo(446.5f),
                        PathNode.QuadTo(1028.5f, 457.5f, 1020.5f, 465.5f),
                        PathNode.QuadTo(1012.5f, 473.5f, 1001.5f, 473.5f),
                        PathNode.HorizontalTo(964.5f),
                        PathNode.QuadTo(953.5f, 473.5f, 945.5f, 465.5f),
                        PathNode.QuadTo(937.5f, 457.5f, 937.5f, 446.5f),
                        PathNode.VerticalTo(188.5f),
                        PathNode.QuadTo(937.5f, 183.5f, 933.5f, 183.5f),
                        PathNode.HorizontalTo(674.5f),
                        PathNode.QuadTo(664.5f, 183.5f, 656.5f, 175.5f),
                        PathNode.QuadTo(648.5f, 167.5f, 648.5f, 157.5f),
                        PathNode.VerticalTo(119.5f),
                        PathNode.QuadTo(648.5f, 108.5f, 656.5f, 101.0f),
                        PathNode.QuadTo(664.5f, 93.5f, 674.5f, 93.5f),
                        PathNode.HorizontalTo(968.5f),
                        PathNode.QuadTo(997.5f, 93.5f, 1013.0f, 109.0f),
                        PathNode.QuadTo(1028.5f, 124.5f, 1028.5f, 153.5f),
                        PathNode.Close,
                    ),
                    fill = SolidColor(Color.Black),
                    fillAlpha = 1f,
                    pathFillType = PathFillType.NonZero,
                )
            }
        }.build().also { expandMoreIconCache = it }
    }

private var rotateLeftIconCache: ImageVector? = null

/** Arrow rotating counterclockwise. */
public val ElegantIcons.RotateLeft: ImageVector
    get() {
        rotateLeftIconCache?.let { return it }
        return ImageVector.Builder(
            name = "ElegantIcons.RotateLeft",
            defaultWidth = 24.0f.dp,
            defaultHeight = 24.0f.dp,
            viewportWidth = 1347.6f,
            viewportHeight = 1347.6f,
        ).apply {
            group(scaleY = -1.0f, translationY = 1347.6f) {
                addPath(
                    pathData = listOf(
                        PathNode.MoveTo(865.3f, 134.3f),
                        PathNode.QuadTo(917.3f, 163.3f, 944.3f, 213.3f),
                        PathNode.QuadTo(959.3f, 242.3f, 962.8f, 283.3f),
                        PathNode.QuadTo(966.3f, 324.3f, 966.3f, 420.3f),
                        PathNode.VerticalTo(605.3f),
                        PathNode.QuadTo(966.3f, 701.3f, 962.8f, 742.3f),
                        PathNode.QuadTo(959.3f, 783.3f, 944.3f, 811.3f),
                        PathNode.QuadTo(931.3f, 837.3f, 910.8f, 857.3f),
                        PathNode.QuadTo(890.3f, 877.3f, 865.3f, 890.3f),
                        PathNode.QuadTo(836.3f, 905.3f, 795.3f, 908.8f),
                        PathNode.QuadTo(754.3f, 912.3f, 658.3f, 912.3f),
                        PathNode.HorizontalTo(473.3f),
                        PathNode.QuadTo(377.3f, 912.3f, 336.3f, 908.8f),
                        PathNode.QuadTo(295.3f, 905.3f, 266.3f, 890.3f),
                        PathNode.QuadTo(215.3f, 864.3f, 187.3f, 811.3f),
                        PathNode.QuadTo(172.3f, 783.3f, 168.8f, 742.3f),
                        PathNode.QuadTo(165.3f, 701.3f, 165.3f, 605.3f),
                        PathNode.VerticalTo(420.3f),
                        PathNode.QuadTo(165.3f, 324.3f, 168.8f, 283.3f),
                        PathNode.QuadTo(172.3f, 242.3f, 187.3f, 213.3f),
                        PathNode.QuadTo(214.3f, 163.3f, 266.3f, 134.3f),
                        PathNode.QuadTo(295.3f, 119.3f, 336.3f, 115.8f),
                        PathNode.QuadTo(377.3f, 112.3f, 473.3f, 112.3f),
                        PathNode.HorizontalTo(658.3f),
                        PathNode.QuadTo(754.3f, 112.3f, 795.3f, 115.8f),
                        PathNode.QuadTo(836.3f, 119.3f, 865.3f, 134.3f),
                        PathNode.Close,
                        PathNode.MoveTo(309.3f, 210.3f),
                        PathNode.QuadTo(280.3f, 225.3f, 263.3f, 255.3f),
                        PathNode.QuadTo(255.3f, 271.3f, 253.3f, 295.3f),
                        PathNode.QuadTo(251.3f, 319.3f, 251.3f, 374.3f),
                        PathNode.VerticalTo(650.3f),
                        PathNode.QuadTo(251.3f, 705.3f, 253.3f, 729.3f),
                        PathNode.QuadTo(255.3f, 753.3f, 263.3f, 769.3f),
                        PathNode.QuadTo(280.3f, 799.3f, 309.3f, 814.3f),
                        PathNode.QuadTo(326.3f, 823.3f, 349.3f, 825.3f),
                        PathNode.QuadTo(372.3f, 827.3f, 428.3f, 827.3f),
                        PathNode.HorizontalTo(703.3f),
                        PathNode.QuadTo(759.3f, 827.3f, 782.3f, 825.3f),
                        PathNode.QuadTo(805.3f, 823.3f, 822.3f, 814.3f),
                        PathNode.QuadTo(850.3f, 800.3f, 868.3f, 769.3f),
                        PathNode.QuadTo(876.3f, 753.3f, 878.3f, 729.3f),
                        PathNode.QuadTo(880.3f, 705.3f, 880.3f, 650.3f),
                        PathNode.VerticalTo(374.3f),
                        PathNode.QuadTo(880.3f, 319.3f, 878.3f, 295.3f),
                        PathNode.QuadTo(876.3f, 271.3f, 868.3f, 255.3f),
                        PathNode.QuadTo(850.3f, 224.3f, 822.3f, 210.3f),
                        PathNode.QuadTo(805.3f, 201.3f, 782.3f, 199.3f),
                        PathNode.QuadTo(759.3f, 197.3f, 703.3f, 197.3f),
                        PathNode.HorizontalTo(428.3f),
                        PathNode.QuadTo(372.3f, 197.3f, 349.3f, 199.3f),
                        PathNode.QuadTo(326.3f, 201.3f, 309.3f, 210.3f),
                        PathNode.Close,
                        PathNode.MoveTo(1182.3f, 669.3f),
                        PathNode.QuadTo(1182.3f, 756.3f, 1179.3f, 801.3f),
                        PathNode.QuadTo(1176.3f, 846.3f, 1162.3f, 886.3f),
                        PathNode.QuadTo(1134.3f, 969.3f, 1072.8f, 1030.8f),
                        PathNode.QuadTo(1011.3f, 1092.3f, 928.3f, 1120.3f),
                        PathNode.QuadTo(898.3f, 1130.3f, 862.3f, 1134.3f),
                        PathNode.QuadTo(826.3f, 1138.3f, 773.3f, 1139.3f),
                        PathNode.VerticalTo(1203.3f),
                        PathNode.QuadTo(773.3f, 1220.3f, 767.3f, 1227.8f),
                        PathNode.QuadTo(761.3f, 1235.3f, 751.8f, 1235.3f),
                        PathNode.QuadTo(742.3f, 1235.3f, 733.3f, 1228.3f),
                        PathNode.LineTo(585.3f, 1118.3f),
                        PathNode.QuadTo(575.3f, 1111.3f, 575.3f, 1099.3f),
                        PathNode.QuadTo(575.3f, 1087.3f, 585.3f, 1080.3f),
                        PathNode.LineTo(733.3f, 970.3f),
                        PathNode.QuadTo(748.3f, 959.3f, 760.8f, 965.3f),
                        PathNode.QuadTo(773.3f, 971.3f, 773.3f, 993.3f),
                        PathNode.VerticalTo(1053.3f),
                        PathNode.QuadTo(821.3f, 1052.3f, 849.8f, 1049.3f),
                        PathNode.QuadTo(878.3f, 1046.3f, 900.3f, 1039.3f),
                        PathNode.QuadTo(964.3f, 1017.3f, 1011.8f, 969.8f),
                        PathNode.QuadTo(1059.3f, 922.3f, 1081.3f, 859.3f),
                        PathNode.QuadTo(1091.3f, 830.3f, 1094.3f, 792.8f),
                        PathNode.QuadTo(1097.3f, 755.3f, 1097.3f, 669.3f),
                        PathNode.QuadTo(1097.3f, 657.3f, 1104.3f, 649.8f),
                        PathNode.QuadTo(1111.3f, 642.3f, 1126.3f, 642.3f),
                        PathNode.HorizontalTo(1155.3f),
                        PathNode.QuadTo(1166.3f, 642.3f, 1174.3f, 649.8f),
                        PathNode.QuadTo(1182.3f, 657.3f, 1182.3f, 669.3f),
                        PathNode.Close,
                    ),
                    fill = SolidColor(Color.Black),
                    fillAlpha = 1f,
                    pathFillType = PathFillType.NonZero,
                )
            }
        }.build().also { rotateLeftIconCache = it }
    }

private var zoomOutIconCache: ImageVector? = null

/** Magnifier with a minus mark. */
public val ElegantIcons.ZoomOut: ImageVector
    get() {
        zoomOutIconCache?.let { return it }
        return ImageVector.Builder(
            name = "ElegantIcons.ZoomOut",
            defaultWidth = 24.0f.dp,
            defaultHeight = 24.0f.dp,
            viewportWidth = 1126.8f,
            viewportHeight = 1126.8f,
        ).apply {
            group(scaleY = -1.0f, translationY = 1126.8f) {
                addPath(
                    pathData = listOf(
                        PathNode.MoveTo(502.9f, 682.9f),
                        PathNode.VerticalTo(1004.9f),
                        PathNode.QuadTo(502.9f, 1016.9f, 496.9f, 1024.4f),
                        PathNode.QuadTo(490.9f, 1031.9f, 478.9f, 1031.9f),
                        PathNode.HorizontalTo(440.9f),
                        PathNode.QuadTo(428.9f, 1031.9f, 422.9f, 1024.4f),
                        PathNode.QuadTo(416.9f, 1016.9f, 416.9f, 1004.9f),
                        PathNode.VerticalTo(768.9f),
                        PathNode.LineTo(169.9f, 1015.9f),
                        PathNode.QuadTo(161.9f, 1024.9f, 150.9f, 1026.4f),
                        PathNode.QuadTo(139.9f, 1027.9f, 129.9f, 1017.9f),
                        PathNode.LineTo(108.9f, 996.9f),
                        PathNode.QuadTo(98.9f, 986.9f, 99.9f, 975.9f),
                        PathNode.QuadTo(100.9f, 964.9f, 108.9f, 956.9f),
                        PathNode.LineTo(356.9f, 708.9f),
                        PathNode.HorizontalTo(119.9f),
                        PathNode.QuadTo(107.9f, 708.9f, 100.9f, 701.9f),
                        PathNode.QuadTo(93.9f, 694.9f, 93.9f, 685.9f),
                        PathNode.VerticalTo(645.9f),
                        PathNode.QuadTo(93.9f, 635.9f, 100.9f, 629.4f),
                        PathNode.QuadTo(107.9f, 622.9f, 119.9f, 622.9f),
                        PathNode.HorizontalTo(442.9f),
                        PathNode.QuadTo(471.9f, 622.9f, 487.4f, 638.4f),
                        PathNode.QuadTo(502.9f, 653.9f, 502.9f, 682.9f),
                        PathNode.Close,
                        PathNode.MoveTo(623.9f, 443.9f),
                        PathNode.VerticalTo(121.9f),
                        PathNode.QuadTo(623.9f, 109.9f, 629.9f, 102.4f),
                        PathNode.QuadTo(635.9f, 94.9f, 647.9f, 94.9f),
                        PathNode.HorizontalTo(685.9f),
                        PathNode.QuadTo(697.9f, 94.9f, 703.9f, 102.4f),
                        PathNode.QuadTo(709.9f, 109.9f, 709.9f, 121.9f),
                        PathNode.VerticalTo(357.9f),
                        PathNode.LineTo(956.9f, 110.9f),
                        PathNode.QuadTo(964.9f, 101.9f, 975.9f, 100.4f),
                        PathNode.QuadTo(986.9f, 98.9f, 996.9f, 108.9f),
                        PathNode.LineTo(1017.9f, 129.9f),
                        PathNode.QuadTo(1027.9f, 139.9f, 1026.9f, 150.9f),
                        PathNode.QuadTo(1025.9f, 161.9f, 1017.9f, 169.9f),
                        PathNode.LineTo(769.9f, 417.9f),
                        PathNode.HorizontalTo(1006.9f),
                        PathNode.QuadTo(1018.9f, 417.9f, 1025.9f, 424.9f),
                        PathNode.QuadTo(1032.9f, 431.9f, 1032.9f, 440.9f),
                        PathNode.VerticalTo(480.9f),
                        PathNode.QuadTo(1032.9f, 490.9f, 1025.9f, 497.4f),
                        PathNode.QuadTo(1018.9f, 503.9f, 1006.9f, 503.9f),
                        PathNode.HorizontalTo(683.9f),
                        PathNode.QuadTo(654.9f, 503.9f, 639.4f, 488.4f),
                        PathNode.QuadTo(623.9f, 472.9f, 623.9f, 443.9f),
                        PathNode.Close,
                    ),
                    fill = SolidColor(Color.Black),
                    fillAlpha = 1f,
                    pathFillType = PathFillType.NonZero,
                )
            }
        }.build().also { zoomOutIconCache = it }
    }

