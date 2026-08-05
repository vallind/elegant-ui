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

private var bankCardsIconCache: ImageVector? = null

/** Stacked bank cards. */
public val ElegantIcons.BankCards: ImageVector
    get() {
        bankCardsIconCache?.let { return it }
        return ImageVector.Builder(
            name = "ElegantIcons.BankCards",
            defaultWidth = 24.0f.dp,
            defaultHeight = 24.0f.dp,
            viewportWidth = 1224.0f,
            viewportHeight = 1224.0f,
        ).apply {
            group(scaleY = -1.0f, translationY = 1224.0f) {
                addPath(
                    pathData = listOf(
                        PathNode.MoveTo(1044.0f, 232.0f),
                        PathNode.QuadTo(1085.0f, 254.0f, 1107.0f, 295.0f),
                        PathNode.QuadTo(1117.0f, 316.0f, 1119.5f, 345.5f),
                        PathNode.QuadTo(1122.0f, 375.0f, 1122.0f, 445.0f),
                        PathNode.VerticalTo(741.0f),
                        PathNode.HorizontalTo(102.0f),
                        PathNode.VerticalTo(445.0f),
                        PathNode.QuadTo(102.0f, 375.0f, 104.5f, 345.5f),
                        PathNode.QuadTo(107.0f, 316.0f, 117.0f, 295.0f),
                        PathNode.QuadTo(139.0f, 254.0f, 180.0f, 232.0f),
                        PathNode.QuadTo(201.0f, 222.0f, 230.5f, 219.5f),
                        PathNode.QuadTo(260.0f, 217.0f, 330.0f, 217.0f),
                        PathNode.HorizontalTo(894.0f),
                        PathNode.QuadTo(964.0f, 217.0f, 993.5f, 219.5f),
                        PathNode.QuadTo(1023.0f, 222.0f, 1044.0f, 232.0f),
                        PathNode.Close,
                        PathNode.MoveTo(734.0f, 395.0f),
                        PathNode.VerticalTo(438.0f),
                        PathNode.QuadTo(734.0f, 470.0f, 747.5f, 483.0f),
                        PathNode.QuadTo(761.0f, 496.0f, 792.0f, 496.0f),
                        PathNode.HorizontalTo(898.0f),
                        PathNode.QuadTo(929.0f, 496.0f, 942.5f, 483.0f),
                        PathNode.QuadTo(956.0f, 470.0f, 956.0f, 438.0f),
                        PathNode.VerticalTo(395.0f),
                        PathNode.QuadTo(956.0f, 363.0f, 942.5f, 349.5f),
                        PathNode.QuadTo(929.0f, 336.0f, 898.0f, 336.0f),
                        PathNode.HorizontalTo(792.0f),
                        PathNode.QuadTo(761.0f, 336.0f, 747.5f, 349.5f),
                        PathNode.QuadTo(734.0f, 363.0f, 734.0f, 395.0f),
                        PathNode.Close,
                        PathNode.MoveTo(1107.0f, 930.0f),
                        PathNode.QuadTo(1085.0f, 971.0f, 1044.0f, 992.0f),
                        PathNode.QuadTo(1023.0f, 1002.0f, 993.5f, 1004.5f),
                        PathNode.QuadTo(964.0f, 1007.0f, 894.0f, 1007.0f),
                        PathNode.HorizontalTo(330.0f),
                        PathNode.QuadTo(260.0f, 1007.0f, 230.5f, 1004.5f),
                        PathNode.QuadTo(201.0f, 1002.0f, 180.0f, 992.0f),
                        PathNode.QuadTo(139.0f, 971.0f, 117.0f, 930.0f),
                        PathNode.QuadTo(108.0f, 911.0f, 105.0f, 884.0f),
                        PathNode.QuadTo(102.0f, 857.0f, 102.0f, 807.0f),
                        PathNode.HorizontalTo(1122.0f),
                        PathNode.QuadTo(1122.0f, 857.0f, 1119.0f, 884.0f),
                        PathNode.QuadTo(1116.0f, 911.0f, 1107.0f, 930.0f),
                        PathNode.Close,
                    ),
                    fill = SolidColor(Color.Black),
                    fillAlpha = 1f,
                    pathFillType = PathFillType.NonZero,
                )
            }
        }.build().also { bankCardsIconCache = it }
    }

private var carrierIconCache: ImageVector? = null

/** Signal carrier bars. */
public val ElegantIcons.Carrier: ImageVector
    get() {
        carrierIconCache?.let { return it }
        return ImageVector.Builder(
            name = "ElegantIcons.Carrier",
            defaultWidth = 24.0f.dp,
            defaultHeight = 24.0f.dp,
            viewportWidth = 1138.8f,
            viewportHeight = 1138.8f,
        ).apply {
            group(scaleY = -1.0f, translationY = 1138.8f) {
                addPath(
                    pathData = listOf(
                        PathNode.MoveTo(911.9f, 129.3f),
                        PathNode.QuadTo(961.7f, 156.6f, 984.1f, 208.3f),
                        PathNode.QuadTo(994.8f, 234.6f, 994.3f, 271.4f),
                        PathNode.QuadTo(993.8f, 308.2f, 985.2f, 393.4f),
                        PathNode.LineTo(944.6f, 796.8f),
                        PathNode.QuadTo(937.9f, 865.5f, 932.1f, 897.5f),
                        PathNode.QuadTo(926.3f, 929.5f, 913.5f, 951.0f),
                        PathNode.QuadTo(888.2f, 992.0f, 844.2f, 1013.5f),
                        PathNode.QuadTo(821.7f, 1024.2f, 790.7f, 1026.7f),
                        PathNode.QuadTo(759.6f, 1029.1f, 688.1f, 1029.1f),
                        PathNode.HorizontalTo(450.7f),
                        PathNode.QuadTo(379.2f, 1029.1f, 348.1f, 1026.7f),
                        PathNode.QuadTo(317.1f, 1024.2f, 294.6f, 1013.5f),
                        PathNode.QuadTo(250.6f, 992.0f, 225.3f, 951.0f),
                        PathNode.QuadTo(212.5f, 929.5f, 206.7f, 897.5f),
                        PathNode.QuadTo(200.9f, 865.5f, 194.2f, 796.8f),
                        PathNode.LineTo(153.6f, 393.4f),
                        PathNode.QuadTo(145.0f, 308.2f, 144.5f, 271.4f),
                        PathNode.QuadTo(144.0f, 234.6f, 155.7f, 208.3f),
                        PathNode.QuadTo(176.3f, 156.6f, 226.9f, 129.3f),
                        PathNode.QuadTo(251.4f, 115.6f, 288.3f, 112.7f),
                        PathNode.QuadTo(325.2f, 109.7f, 411.1f, 109.7f),
                        PathNode.HorizontalTo(727.7f),
                        PathNode.QuadTo(813.6f, 109.7f, 850.5f, 112.7f),
                        PathNode.QuadTo(887.4f, 115.6f, 911.9f, 129.3f),
                        PathNode.Close,
                        PathNode.MoveTo(370.0f, 825.7f),
                        PathNode.VerticalTo(869.4f),
                        PathNode.QuadTo(370.0f, 880.6f, 376.6f, 887.6f),
                        PathNode.QuadTo(383.1f, 894.7f, 395.2f, 894.7f),
                        PathNode.HorizontalTo(410.7f),
                        PathNode.QuadTo(423.7f, 894.7f, 430.3f, 888.1f),
                        PathNode.QuadTo(436.8f, 881.6f, 436.8f, 869.4f),
                        PathNode.VerticalTo(825.7f),
                        PathNode.QuadTo(436.8f, 790.7f, 454.5f, 760.4f),
                        PathNode.QuadTo(472.1f, 730.1f, 502.6f, 712.5f),
                        PathNode.QuadTo(533.2f, 694.8f, 569.4f, 694.8f),
                        PathNode.QuadTo(605.6f, 694.8f, 636.2f, 712.5f),
                        PathNode.QuadTo(666.7f, 730.1f, 684.3f, 760.4f),
                        PathNode.QuadTo(702.0f, 790.7f, 702.0f, 825.7f),
                        PathNode.VerticalTo(869.4f),
                        PathNode.QuadTo(702.0f, 881.6f, 708.5f, 888.1f),
                        PathNode.QuadTo(715.1f, 894.7f, 728.1f, 894.7f),
                        PathNode.HorizontalTo(742.7f),
                        PathNode.QuadTo(755.7f, 894.7f, 762.2f, 888.1f),
                        PathNode.QuadTo(768.8f, 881.6f, 768.8f, 869.4f),
                        PathNode.VerticalTo(825.7f),
                        PathNode.QuadTo(768.8f, 771.7f, 742.0f, 726.2f),
                        PathNode.QuadTo(715.3f, 680.7f, 669.5f, 654.0f),
                        PathNode.QuadTo(623.8f, 627.3f, 569.4f, 627.3f),
                        PathNode.QuadTo(515.0f, 627.3f, 469.3f, 654.0f),
                        PathNode.QuadTo(423.5f, 680.7f, 396.8f, 726.2f),
                        PathNode.QuadTo(370.0f, 771.7f, 370.0f, 825.7f),
                        PathNode.Close,
                    ),
                    fill = SolidColor(Color.Black),
                    fillAlpha = 1f,
                    pathFillType = PathFillType.NonZero,
                )
            }
        }.build().also { carrierIconCache = it }
    }

private var communityIconCache: ImageVector? = null

/** Two person silhouettes for a community. */
public val ElegantIcons.Community: ImageVector
    get() {
        communityIconCache?.let { return it }
        return ImageVector.Builder(
            name = "ElegantIcons.Community",
            defaultWidth = 24.0f.dp,
            defaultHeight = 24.0f.dp,
            viewportWidth = 1178.4f,
            viewportHeight = 1178.4f,
        ).apply {
            group(scaleY = -1.0f, translationY = 1178.4f) {
                addPath(
                    pathData = listOf(
                        PathNode.MoveTo(135.4f, 166.9f),
                        PathNode.QuadTo(126.3f, 182.3f, 122.4f, 204.6f),
                        PathNode.QuadTo(118.6f, 226.9f, 118.6f, 281.9f),
                        PathNode.VerticalTo(749.8f),
                        PathNode.QuadTo(118.6f, 838.6f, 121.5f, 876.5f),
                        PathNode.QuadTo(124.4f, 914.4f, 137.9f, 941.6f),
                        PathNode.QuadTo(165.9f, 995.0f, 218.2f, 1021.8f),
                        PathNode.QuadTo(245.4f, 1035.5f, 283.8f, 1038.8f),
                        PathNode.QuadTo(322.2f, 1042.2f, 411.1f, 1042.2f),
                        PathNode.HorizontalTo(767.4f),
                        PathNode.QuadTo(856.3f, 1042.2f, 894.2f, 1038.8f),
                        PathNode.QuadTo(932.1f, 1035.5f, 959.3f, 1021.8f),
                        PathNode.QuadTo(985.3f, 1009.3f, 1006.1f, 988.5f),
                        PathNode.QuadTo(1027.0f, 967.7f, 1039.5f, 941.6f),
                        PathNode.QuadTo(1053.2f, 914.4f, 1056.5f, 876.5f),
                        PathNode.QuadTo(1059.8f, 838.6f, 1059.8f, 749.8f),
                        PathNode.VerticalTo(564.9f),
                        PathNode.QuadTo(1059.8f, 476.1f, 1056.5f, 437.7f),
                        PathNode.QuadTo(1053.2f, 399.3f, 1039.5f, 372.2f),
                        PathNode.QuadTo(1012.3f, 319.9f, 959.3f, 291.9f),
                        PathNode.QuadTo(932.1f, 278.4f, 894.2f, 275.5f),
                        PathNode.QuadTo(856.3f, 272.5f, 767.4f, 272.5f),
                        PathNode.HorizontalTo(506.3f),
                        PathNode.QuadTo(483.4f, 272.5f, 466.9f, 272.1f),
                        PathNode.QuadTo(450.3f, 271.6f, 439.2f, 268.7f),
                        PathNode.QuadTo(420.2f, 266.1f, 397.0f, 255.6f),
                        PathNode.QuadTo(379.7f, 247.2f, 341.1f, 219.7f),
                        PathNode.LineTo(316.3f, 202.5f),
                        PathNode.QuadTo(262.4f, 165.0f, 238.8f, 151.5f),
                        PathNode.QuadTo(215.2f, 138.1f, 196.8f, 137.2f),
                        PathNode.QuadTo(177.1f, 136.4f, 160.3f, 144.7f),
                        PathNode.QuadTo(143.5f, 153.0f, 135.4f, 166.9f),
                        PathNode.Close,
                        PathNode.MoveTo(354.2f, 543.9f),
                        PathNode.VerticalTo(566.8f),
                        PathNode.QuadTo(354.2f, 585.0f, 362.5f, 591.8f),
                        PathNode.QuadTo(370.7f, 598.6f, 388.9f, 598.6f),
                        PathNode.HorizontalTo(693.2f),
                        PathNode.QuadTo(710.4f, 598.6f, 719.2f, 591.8f),
                        PathNode.QuadTo(727.9f, 585.0f, 727.9f, 568.7f),
                        PathNode.VerticalTo(543.9f),
                        PathNode.QuadTo(727.9f, 526.8f, 719.6f, 519.4f),
                        PathNode.QuadTo(711.3f, 512.1f, 693.2f, 512.1f),
                        PathNode.HorizontalTo(388.9f),
                        PathNode.QuadTo(370.9f, 512.1f, 362.6f, 519.3f),
                        PathNode.QuadTo(354.2f, 526.6f, 354.2f, 543.9f),
                        PathNode.Close,
                        PathNode.MoveTo(365.0f, 337.6f),
                        PathNode.QuadTo(389.2f, 350.3f, 418.2f, 355.1f),
                        PathNode.QuadTo(432.7f, 357.9f, 453.8f, 358.4f),
                        PathNode.QuadTo(474.9f, 358.9f, 503.5f, 358.9f),
                        PathNode.HorizontalTo(809.3f),
                        PathNode.QuadTo(859.8f, 358.9f, 880.9f, 360.7f),
                        PathNode.QuadTo(902.0f, 362.6f, 917.7f, 370.0f),
                        PathNode.QuadTo(947.5f, 385.2f, 961.2f, 414.6f),
                        PathNode.QuadTo(968.7f, 429.3f, 970.6f, 450.9f),
                        PathNode.QuadTo(972.4f, 472.5f, 972.4f, 522.9f),
                        PathNode.VerticalTo(790.7f),
                        PathNode.QuadTo(972.4f, 842.1f, 970.6f, 863.3f),
                        PathNode.QuadTo(968.7f, 884.5f, 961.2f, 899.1f),
                        PathNode.QuadTo(947.4f, 928.6f, 917.7f, 943.7f),
                        PathNode.QuadTo(902.0f, 951.1f, 880.9f, 952.9f),
                        PathNode.QuadTo(859.8f, 954.7f, 809.3f, 954.7f),
                        PathNode.HorizontalTo(369.1f),
                        PathNode.QuadTo(318.6f, 954.7f, 297.1f, 952.9f),
                        PathNode.QuadTo(275.5f, 951.1f, 260.7f, 943.7f),
                        PathNode.QuadTo(230.1f, 929.6f, 216.2f, 899.1f),
                        PathNode.QuadTo(208.7f, 884.5f, 206.9f, 863.3f),
                        PathNode.QuadTo(205.1f, 842.1f, 205.1f, 790.7f),
                        PathNode.VerticalTo(265.7f),
                        PathNode.QuadTo(205.1f, 249.2f, 213.3f, 245.5f),
                        PathNode.QuadTo(221.5f, 241.8f, 239.7f, 255.4f),
                        PathNode.LineTo(293.1f, 292.6f),
                        PathNode.QuadTo(316.0f, 308.8f, 333.9f, 320.3f),
                        PathNode.QuadTo(351.7f, 331.9f, 365.0f, 337.6f),
                        PathNode.Close,
                        PathNode.MoveTo(354.2f, 747.9f),
                        PathNode.VerticalTo(771.6f),
                        PathNode.QuadTo(354.2f, 786.8f, 362.6f, 794.2f),
                        PathNode.QuadTo(370.9f, 801.6f, 387.0f, 801.6f),
                        PathNode.HorizontalTo(790.4f),
                        PathNode.QuadTo(807.5f, 801.6f, 815.9f, 794.7f),
                        PathNode.QuadTo(824.2f, 787.8f, 824.2f, 771.6f),
                        PathNode.VerticalTo(747.9f),
                        PathNode.QuadTo(824.2f, 730.7f, 815.9f, 723.3f),
                        PathNode.QuadTo(807.5f, 716.0f, 790.4f, 716.0f),
                        PathNode.HorizontalTo(387.0f),
                        PathNode.QuadTo(371.7f, 716.0f, 363.0f, 723.4f),
                        PathNode.QuadTo(354.2f, 730.8f, 354.2f, 747.9f),
                        PathNode.Close,
                    ),
                    fill = SolidColor(Color.Black),
                    fillAlpha = 1f,
                    pathFillType = PathFillType.NonZero,
                )
            }
        }.build().also { communityIconCache = it }
    }

private var contactsIconCache: ImageVector? = null

/** Person in a circle for contacts. */
public val ElegantIcons.Contacts: ImageVector
    get() {
        contactsIconCache?.let { return it }
        return ImageVector.Builder(
            name = "ElegantIcons.Contacts",
            defaultWidth = 24.0f.dp,
            defaultHeight = 24.0f.dp,
            viewportWidth = 1186.8f,
            viewportHeight = 1186.8f,
        ).apply {
            group(scaleY = -1.0f, translationY = 1186.8f) {
                addPath(
                    pathData = listOf(
                        PathNode.MoveTo(1044.9f, 109.4f),
                        PathNode.QuadTo(1068.9f, 122.4f, 1078.9f, 145.4f),
                        PathNode.QuadTo(1084.9f, 157.4f, 1086.4f, 173.9f),
                        PathNode.QuadTo(1087.9f, 190.4f, 1087.9f, 230.4f),
                        PathNode.QuadTo(1087.9f, 281.4f, 1086.4f, 303.9f),
                        PathNode.QuadTo(1084.9f, 326.4f, 1076.9f, 347.4f),
                        PathNode.QuadTo(1069.9f, 367.4f, 1053.9f, 389.9f),
                        PathNode.QuadTo(1037.9f, 412.4f, 1020.9f, 425.4f),
                        PathNode.QuadTo(1003.9f, 439.4f, 986.9f, 446.9f),
                        PathNode.QuadTo(969.9f, 454.4f, 933.9f, 466.4f),
                        PathNode.QuadTo(767.9f, 522.4f, 593.9f, 522.4f),
                        PathNode.QuadTo(418.9f, 522.4f, 253.9f, 466.4f),
                        PathNode.QuadTo(216.9f, 454.4f, 199.9f, 446.9f),
                        PathNode.QuadTo(182.9f, 439.4f, 165.9f, 425.4f),
                        PathNode.QuadTo(148.9f, 411.4f, 132.9f, 389.4f),
                        PathNode.QuadTo(116.9f, 367.4f, 109.9f, 347.4f),
                        PathNode.QuadTo(101.9f, 326.4f, 100.4f, 303.9f),
                        PathNode.QuadTo(98.9f, 281.4f, 98.9f, 230.4f),
                        PathNode.QuadTo(98.9f, 190.4f, 100.4f, 173.9f),
                        PathNode.QuadTo(101.9f, 157.4f, 107.9f, 145.4f),
                        PathNode.QuadTo(117.9f, 122.4f, 141.9f, 109.4f),
                        PathNode.QuadTo(153.9f, 103.4f, 170.9f, 101.9f),
                        PathNode.QuadTo(187.9f, 100.4f, 226.9f, 100.4f),
                        PathNode.HorizontalTo(959.9f),
                        PathNode.QuadTo(998.9f, 100.4f, 1015.9f, 101.9f),
                        PathNode.QuadTo(1032.9f, 103.4f, 1044.9f, 109.4f),
                        PathNode.Close,
                        PathNode.MoveTo(183.9f, 216.4f),
                        PathNode.VerticalTo(240.4f),
                        PathNode.QuadTo(183.9f, 272.4f, 184.9f, 286.9f),
                        PathNode.QuadTo(185.9f, 301.4f, 190.9f, 314.4f),
                        PathNode.QuadTo(201.9f, 340.4f, 219.9f, 355.4f),
                        PathNode.QuadTo(230.9f, 364.4f, 242.9f, 370.4f),
                        PathNode.QuadTo(254.9f, 376.4f, 277.9f, 383.4f),
                        PathNode.QuadTo(422.9f, 437.4f, 583.9f, 437.4f),
                        PathNode.QuadTo(757.9f, 437.4f, 912.9f, 375.4f),
                        PathNode.LineTo(922.9f, 371.4f),
                        PathNode.QuadTo(937.9f, 365.4f, 948.9f, 359.4f),
                        PathNode.QuadTo(959.9f, 353.4f, 967.9f, 346.4f),
                        PathNode.QuadTo(987.9f, 328.4f, 995.9f, 304.4f),
                        PathNode.QuadTo(1000.9f, 292.4f, 1001.9f, 278.4f),
                        PathNode.QuadTo(1002.9f, 264.4f, 1002.9f, 233.4f),
                        PathNode.VerticalTo(216.4f),
                        PathNode.QuadTo(1002.9f, 196.4f, 997.4f, 191.4f),
                        PathNode.QuadTo(991.9f, 186.4f, 971.9f, 186.4f),
                        PathNode.HorizontalTo(214.9f),
                        PathNode.QuadTo(194.9f, 186.4f, 189.4f, 191.4f),
                        PathNode.QuadTo(183.9f, 196.4f, 183.9f, 216.4f),
                        PathNode.Close,
                        PathNode.MoveTo(841.9f, 837.4f),
                        PathNode.QuadTo(841.9f, 904.4f, 808.4f, 961.9f),
                        PathNode.QuadTo(774.9f, 1019.4f, 717.9f, 1052.9f),
                        PathNode.QuadTo(660.9f, 1086.4f, 593.9f, 1086.4f),
                        PathNode.QuadTo(525.9f, 1086.4f, 468.9f, 1052.9f),
                        PathNode.QuadTo(411.9f, 1019.4f, 378.4f, 961.9f),
                        PathNode.QuadTo(344.9f, 904.4f, 344.9f, 837.4f),
                        PathNode.QuadTo(344.9f, 770.4f, 378.4f, 713.4f),
                        PathNode.QuadTo(411.9f, 656.4f, 468.9f, 622.9f),
                        PathNode.QuadTo(525.9f, 589.4f, 593.9f, 589.4f),
                        PathNode.QuadTo(660.9f, 589.4f, 717.9f, 622.9f),
                        PathNode.QuadTo(774.9f, 656.4f, 808.4f, 713.4f),
                        PathNode.QuadTo(841.9f, 770.4f, 841.9f, 837.4f),
                        PathNode.Close,
                        PathNode.MoveTo(429.9f, 837.4f),
                        PathNode.QuadTo(429.9f, 881.4f, 451.9f, 918.9f),
                        PathNode.QuadTo(473.9f, 956.4f, 511.9f, 978.4f),
                        PathNode.QuadTo(549.9f, 1000.4f, 593.9f, 1000.4f),
                        PathNode.QuadTo(637.9f, 1000.4f, 675.4f, 978.4f),
                        PathNode.QuadTo(712.9f, 956.4f, 734.9f, 918.9f),
                        PathNode.QuadTo(756.9f, 881.4f, 756.9f, 837.4f),
                        PathNode.QuadTo(756.9f, 793.4f, 734.9f, 755.9f),
                        PathNode.QuadTo(712.9f, 718.4f, 675.4f, 696.4f),
                        PathNode.QuadTo(637.9f, 674.4f, 593.9f, 674.4f),
                        PathNode.QuadTo(549.9f, 674.4f, 511.9f, 696.4f),
                        PathNode.QuadTo(473.9f, 718.4f, 451.9f, 755.9f),
                        PathNode.QuadTo(429.9f, 793.4f, 429.9f, 837.4f),
                        PathNode.Close,
                    ),
                    fill = SolidColor(Color.Black),
                    fillAlpha = 1f,
                    pathFillType = PathFillType.NonZero,
                )
            }
        }.build().also { contactsIconCache = it }
    }

private var contactsBookIconCache: ImageVector? = null

/** Open book with a person. */
public val ElegantIcons.ContactsBook: ImageVector
    get() {
        contactsBookIconCache?.let { return it }
        return ImageVector.Builder(
            name = "ElegantIcons.ContactsBook",
            defaultWidth = 24.0f.dp,
            defaultHeight = 24.0f.dp,
            viewportWidth = 1225.2f,
            viewportHeight = 1225.2f,
        ).apply {
            group(scaleY = -1.0f, translationY = 1225.2f) {
                addPath(
                    pathData = listOf(
                        PathNode.MoveTo(1123.1f, 612.1f),
                        PathNode.QuadTo(1123.1f, 751.1f, 1054.6f, 868.1f),
                        PathNode.QuadTo(986.1f, 985.1f, 869.1f, 1054.1f),
                        PathNode.QuadTo(752.1f, 1123.1f, 613.1f, 1123.1f),
                        PathNode.QuadTo(474.1f, 1123.1f, 357.1f, 1054.1f),
                        PathNode.QuadTo(240.1f, 985.1f, 171.1f, 868.1f),
                        PathNode.QuadTo(102.1f, 751.1f, 102.1f, 612.1f),
                        PathNode.QuadTo(102.1f, 474.1f, 170.1f, 357.1f),
                        PathNode.QuadTo(238.1f, 240.1f, 355.6f, 171.1f),
                        PathNode.QuadTo(473.1f, 102.1f, 613.1f, 102.1f),
                        PathNode.QuadTo(753.1f, 102.1f, 870.1f, 170.6f),
                        PathNode.QuadTo(987.1f, 239.1f, 1055.1f, 356.6f),
                        PathNode.QuadTo(1123.1f, 474.1f, 1123.1f, 612.1f),
                        PathNode.Close,
                        PathNode.MoveTo(335.1f, 290.1f),
                        PathNode.QuadTo(393.1f, 371.1f, 465.1f, 414.1f),
                        PathNode.QuadTo(537.1f, 457.1f, 613.1f, 457.1f),
                        PathNode.QuadTo(689.1f, 457.1f, 761.1f, 414.1f),
                        PathNode.QuadTo(833.1f, 371.1f, 890.1f, 290.1f),
                        PathNode.QuadTo(833.1f, 241.1f, 762.1f, 214.1f),
                        PathNode.QuadTo(691.1f, 187.1f, 613.1f, 187.1f),
                        PathNode.QuadTo(535.1f, 187.1f, 463.6f, 214.1f),
                        PathNode.QuadTo(392.1f, 241.1f, 335.1f, 290.1f),
                        PathNode.Close,
                        PathNode.MoveTo(188.1f, 612.1f),
                        PathNode.QuadTo(188.1f, 728.1f, 245.1f, 825.6f),
                        PathNode.QuadTo(302.1f, 923.1f, 399.6f, 980.1f),
                        PathNode.QuadTo(497.1f, 1037.1f, 613.1f, 1037.1f),
                        PathNode.QuadTo(728.1f, 1037.1f, 825.6f, 980.1f),
                        PathNode.QuadTo(923.1f, 923.1f, 980.6f, 825.6f),
                        PathNode.QuadTo(1038.1f, 728.1f, 1038.1f, 612.1f),
                        PathNode.QuadTo(1038.1f, 540.1f, 1015.1f, 474.1f),
                        PathNode.QuadTo(992.1f, 408.1f, 950.1f, 354.1f),
                        PathNode.QuadTo(883.1f, 443.1f, 796.1f, 492.6f),
                        PathNode.QuadTo(709.1f, 542.1f, 613.1f, 542.1f),
                        PathNode.QuadTo(516.1f, 542.1f, 429.6f, 492.6f),
                        PathNode.QuadTo(343.1f, 443.1f, 276.1f, 354.1f),
                        PathNode.QuadTo(234.1f, 408.1f, 211.1f, 474.1f),
                        PathNode.QuadTo(188.1f, 540.1f, 188.1f, 612.1f),
                        PathNode.Close,
                        PathNode.MoveTo(798.1f, 765.1f),
                        PathNode.QuadTo(798.1f, 815.1f, 773.1f, 857.6f),
                        PathNode.QuadTo(748.1f, 900.1f, 705.6f, 925.1f),
                        PathNode.QuadTo(663.1f, 950.1f, 613.1f, 950.1f),
                        PathNode.QuadTo(563.1f, 950.1f, 520.6f, 925.1f),
                        PathNode.QuadTo(478.1f, 900.1f, 453.1f, 857.6f),
                        PathNode.QuadTo(428.1f, 815.1f, 428.1f, 765.1f),
                        PathNode.QuadTo(428.1f, 715.1f, 453.1f, 672.6f),
                        PathNode.QuadTo(478.1f, 630.1f, 520.6f, 605.1f),
                        PathNode.QuadTo(563.1f, 580.1f, 613.1f, 580.1f),
                        PathNode.QuadTo(663.1f, 580.1f, 705.6f, 605.1f),
                        PathNode.QuadTo(748.1f, 630.1f, 773.1f, 672.6f),
                        PathNode.QuadTo(798.1f, 715.1f, 798.1f, 765.1f),
                        PathNode.Close,
                        PathNode.MoveTo(518.1f, 765.1f),
                        PathNode.QuadTo(518.1f, 804.1f, 545.6f, 832.1f),
                        PathNode.QuadTo(573.1f, 860.1f, 613.1f, 860.1f),
                        PathNode.QuadTo(652.1f, 860.1f, 680.1f, 832.1f),
                        PathNode.QuadTo(708.1f, 804.1f, 708.1f, 765.1f),
                        PathNode.QuadTo(708.1f, 726.1f, 680.1f, 698.1f),
                        PathNode.QuadTo(652.1f, 670.1f, 613.1f, 670.1f),
                        PathNode.QuadTo(573.1f, 670.1f, 545.6f, 698.1f),
                        PathNode.QuadTo(518.1f, 726.1f, 518.1f, 765.1f),
                        PathNode.Close,
                    ),
                    fill = SolidColor(Color.Black),
                    fillAlpha = 1f,
                    pathFillType = PathFillType.NonZero,
                )
            }
        }.build().also { contactsBookIconCache = it }
    }

private var contactsCircleIconCache: ImageVector? = null

/** Person inside a filled circle. */
public val ElegantIcons.ContactsCircle: ImageVector
    get() {
        contactsCircleIconCache?.let { return it }
        return ImageVector.Builder(
            name = "ElegantIcons.ContactsCircle",
            defaultWidth = 24.0f.dp,
            defaultHeight = 24.0f.dp,
            viewportWidth = 1197.6f,
            viewportHeight = 1197.6f,
        ).apply {
            group(scaleY = -1.0f, translationY = 1197.6f) {
                addPath(
                    pathData = listOf(
                        PathNode.MoveTo(1097.8f, 598.3f),
                        PathNode.QuadTo(1097.8f, 734.3f, 1030.8f, 848.8f),
                        PathNode.QuadTo(963.8f, 963.3f, 848.8f, 1030.3f),
                        PathNode.QuadTo(733.8f, 1097.3f, 598.8f, 1097.3f),
                        PathNode.QuadTo(463.8f, 1097.3f, 348.8f, 1030.3f),
                        PathNode.QuadTo(233.8f, 963.3f, 166.8f, 848.8f),
                        PathNode.QuadTo(99.8f, 734.3f, 99.8f, 598.3f),
                        PathNode.QuadTo(99.8f, 463.3f, 166.8f, 348.8f),
                        PathNode.QuadTo(233.8f, 234.3f, 348.8f, 167.3f),
                        PathNode.QuadTo(463.8f, 100.3f, 598.8f, 100.3f),
                        PathNode.QuadTo(733.8f, 100.3f, 848.8f, 167.3f),
                        PathNode.QuadTo(963.8f, 234.3f, 1030.8f, 348.8f),
                        PathNode.QuadTo(1097.8f, 463.3f, 1097.8f, 598.3f),
                        PathNode.Close,
                        PathNode.MoveTo(871.8f, 194.3f),
                        PathNode.QuadTo(902.8f, 211.3f, 927.8f, 236.3f),
                        PathNode.QuadTo(952.8f, 261.3f, 968.8f, 292.3f),
                        PathNode.QuadTo(970.8f, 298.3f, 967.8f, 302.3f),
                        PathNode.QuadTo(892.8f, 411.3f, 797.8f, 471.8f),
                        PathNode.QuadTo(702.8f, 532.3f, 598.8f, 532.3f),
                        PathNode.QuadTo(494.8f, 532.3f, 399.8f, 471.8f),
                        PathNode.QuadTo(304.8f, 411.3f, 229.8f, 302.3f),
                        PathNode.QuadTo(228.8f, 299.3f, 227.8f, 295.3f),
                        PathNode.QuadTo(228.8f, 294.3f, 228.8f, 292.3f),
                        PathNode.QuadTo(261.8f, 228.3f, 325.8f, 194.3f),
                        PathNode.QuadTo(358.8f, 177.3f, 405.3f, 173.8f),
                        PathNode.QuadTo(451.8f, 170.3f, 560.8f, 170.3f),
                        PathNode.HorizontalTo(636.8f),
                        PathNode.QuadTo(745.8f, 170.3f, 792.3f, 173.8f),
                        PathNode.QuadTo(838.8f, 177.3f, 871.8f, 194.3f),
                        PathNode.Close,
                        PathNode.MoveTo(194.8f, 598.3f),
                        PathNode.QuadTo(194.8f, 708.3f, 249.3f, 800.8f),
                        PathNode.QuadTo(303.8f, 893.3f, 396.3f, 947.8f),
                        PathNode.QuadTo(488.8f, 1002.3f, 598.8f, 1002.3f),
                        PathNode.QuadTo(708.8f, 1002.3f, 801.3f, 947.8f),
                        PathNode.QuadTo(893.8f, 893.3f, 948.3f, 800.8f),
                        PathNode.QuadTo(1002.8f, 708.3f, 1002.8f, 598.3f),
                        PathNode.QuadTo(1002.8f, 488.3f, 948.3f, 395.8f),
                        PathNode.QuadTo(893.8f, 303.3f, 801.3f, 249.3f),
                        PathNode.QuadTo(708.8f, 195.3f, 598.8f, 195.3f),
                        PathNode.QuadTo(488.8f, 195.3f, 396.3f, 249.3f),
                        PathNode.QuadTo(303.8f, 303.3f, 249.3f, 395.8f),
                        PathNode.QuadTo(194.8f, 488.3f, 194.8f, 598.3f),
                        PathNode.Close,
                        PathNode.MoveTo(755.8f, 739.3f),
                        PathNode.QuadTo(755.8f, 782.3f, 734.8f, 818.3f),
                        PathNode.QuadTo(713.8f, 854.3f, 677.8f, 875.3f),
                        PathNode.QuadTo(641.8f, 896.3f, 598.8f, 896.3f),
                        PathNode.QuadTo(555.8f, 896.3f, 519.8f, 875.3f),
                        PathNode.QuadTo(483.8f, 854.3f, 462.8f, 818.3f),
                        PathNode.QuadTo(441.8f, 782.3f, 441.8f, 739.3f),
                        PathNode.QuadTo(441.8f, 697.3f, 462.8f, 660.8f),
                        PathNode.QuadTo(483.8f, 624.3f, 519.8f, 603.3f),
                        PathNode.QuadTo(555.8f, 582.3f, 598.8f, 582.3f),
                        PathNode.QuadTo(641.8f, 582.3f, 677.8f, 603.3f),
                        PathNode.QuadTo(713.8f, 624.3f, 734.8f, 660.8f),
                        PathNode.QuadTo(755.8f, 697.3f, 755.8f, 739.3f),
                        PathNode.Close,
                    ),
                    fill = SolidColor(Color.Black),
                    fillAlpha = 1f,
                    pathFillType = PathFillType.NonZero,
                )
            }
        }.build().also { contactsCircleIconCache = it }
    }

private var emailIconCache: ImageVector? = null

/** Envelope. */
public val ElegantIcons.Email: ImageVector
    get() {
        emailIconCache?.let { return it }
        return ImageVector.Builder(
            name = "ElegantIcons.Email",
            defaultWidth = 24.0f.dp,
            defaultHeight = 24.0f.dp,
            viewportWidth = 1279.2f,
            viewportHeight = 1279.2f,
        ).apply {
            group(scaleY = -1.0f, translationY = 1279.2f) {
                addPath(
                    pathData = listOf(
                        PathNode.MoveTo(1070.6f, 227.1f),
                        PathNode.QuadTo(1123.6f, 255.1f, 1149.6f, 306.1f),
                        PathNode.QuadTo(1165.6f, 335.1f, 1169.1f, 375.6f),
                        PathNode.QuadTo(1172.6f, 416.1f, 1172.6f, 512.1f),
                        PathNode.VerticalTo(766.1f),
                        PathNode.QuadTo(1172.6f, 862.1f, 1169.1f, 903.1f),
                        PathNode.QuadTo(1165.6f, 944.1f, 1149.6f, 972.1f),
                        PathNode.QuadTo(1124.6f, 1022.1f, 1070.6f, 1052.1f),
                        PathNode.QuadTo(1041.6f, 1067.1f, 1000.6f, 1070.6f),
                        PathNode.QuadTo(959.6f, 1074.1f, 863.6f, 1074.1f),
                        PathNode.HorizontalTo(414.6f),
                        PathNode.QuadTo(318.6f, 1074.1f, 277.1f, 1070.6f),
                        PathNode.QuadTo(235.6f, 1067.1f, 207.6f, 1052.1f),
                        PathNode.QuadTo(155.6f, 1023.1f, 128.6f, 972.1f),
                        PathNode.QuadTo(113.6f, 944.1f, 110.1f, 903.1f),
                        PathNode.QuadTo(106.6f, 862.1f, 106.6f, 766.1f),
                        PathNode.VerticalTo(512.1f),
                        PathNode.QuadTo(106.6f, 416.1f, 110.1f, 375.6f),
                        PathNode.QuadTo(113.6f, 335.1f, 128.6f, 306.1f),
                        PathNode.QuadTo(157.6f, 254.1f, 207.6f, 227.1f),
                        PathNode.QuadTo(235.6f, 212.1f, 277.1f, 208.6f),
                        PathNode.QuadTo(318.6f, 205.1f, 414.6f, 205.1f),
                        PathNode.HorizontalTo(863.6f),
                        PathNode.QuadTo(959.6f, 205.1f, 1000.6f, 208.6f),
                        PathNode.QuadTo(1041.6f, 212.1f, 1070.6f, 227.1f),
                        PathNode.Close,
                        PathNode.MoveTo(249.6f, 303.1f),
                        PathNode.QuadTo(222.6f, 317.1f, 204.6f, 348.1f),
                        PathNode.QuadTo(195.6f, 364.1f, 193.6f, 388.1f),
                        PathNode.QuadTo(191.6f, 412.1f, 191.6f, 467.1f),
                        PathNode.VerticalTo(811.1f),
                        PathNode.LineTo(192.6f, 871.1f),
                        PathNode.QuadTo(192.6f, 886.1f, 199.6f, 887.6f),
                        PathNode.QuadTo(206.6f, 889.1f, 216.6f, 881.1f),
                        PathNode.LineTo(526.6f, 619.1f),
                        PathNode.QuadTo(560.6f, 591.1f, 577.1f, 579.6f),
                        PathNode.QuadTo(593.6f, 568.1f, 609.6f, 562.1f),
                        PathNode.QuadTo(623.6f, 557.1f, 639.6f, 557.1f),
                        PathNode.QuadTo(655.6f, 557.1f, 669.6f, 562.1f),
                        PathNode.QuadTo(684.6f, 568.1f, 701.1f, 579.6f),
                        PathNode.QuadTo(717.6f, 591.1f, 751.6f, 619.1f),
                        PathNode.LineTo(1062.6f, 881.1f),
                        PathNode.QuadTo(1074.6f, 891.1f, 1080.6f, 888.6f),
                        PathNode.QuadTo(1086.6f, 886.1f, 1086.6f, 871.1f),
                        PathNode.VerticalTo(811.1f),
                        PathNode.VerticalTo(467.1f),
                        PathNode.QuadTo(1086.6f, 412.1f, 1084.6f, 388.1f),
                        PathNode.QuadTo(1082.6f, 364.1f, 1073.6f, 348.1f),
                        PathNode.QuadTo(1057.6f, 319.1f, 1028.6f, 303.1f),
                        PathNode.QuadTo(1012.6f, 294.1f, 988.6f, 292.1f),
                        PathNode.QuadTo(964.6f, 290.1f, 909.6f, 290.1f),
                        PathNode.HorizontalTo(368.6f),
                        PathNode.QuadTo(314.6f, 290.1f, 290.6f, 292.1f),
                        PathNode.QuadTo(266.6f, 294.1f, 249.6f, 303.1f),
                        PathNode.Close,
                        PathNode.MoveTo(253.6f, 977.1f),
                        PathNode.QuadTo(267.6f, 985.1f, 291.1f, 986.6f),
                        PathNode.QuadTo(314.6f, 988.1f, 368.6f, 988.1f),
                        PathNode.HorizontalTo(909.6f),
                        PathNode.QuadTo(964.6f, 988.1f, 987.6f, 986.6f),
                        PathNode.QuadTo(1010.6f, 985.1f, 1024.6f, 977.1f),
                        PathNode.QuadTo(1030.6f, 974.1f, 1031.1f, 970.6f),
                        PathNode.QuadTo(1031.6f, 967.1f, 1027.6f, 963.1f),
                        PathNode.LineTo(655.6f, 651.1f),
                        PathNode.QuadTo(647.6f, 645.1f, 638.1f, 645.1f),
                        PathNode.QuadTo(628.6f, 645.1f, 620.6f, 652.1f),
                        PathNode.LineTo(249.6f, 964.1f),
                        PathNode.QuadTo(246.6f, 966.1f, 247.6f, 970.1f),
                        PathNode.QuadTo(248.6f, 974.1f, 253.6f, 977.1f),
                        PathNode.Close,
                    ),
                    fill = SolidColor(Color.Black),
                    fillAlpha = 1f,
                    pathFillType = PathFillType.NonZero,
                )
            }
        }.build().also { emailIconCache = it }
    }

private var messagesIconCache: ImageVector? = null

/** Two overlapping speech bubbles. */
public val ElegantIcons.Messages: ImageVector
    get() {
        messagesIconCache?.let { return it }
        return ImageVector.Builder(
            name = "ElegantIcons.Messages",
            defaultWidth = 24.0f.dp,
            defaultHeight = 24.0f.dp,
            viewportWidth = 1263.6f,
            viewportHeight = 1263.6f,
        ).apply {
            group(scaleY = -1.0f, translationY = 1263.6f) {
                addPath(
                    pathData = listOf(
                        PathNode.MoveTo(258.3f, 380.8f),
                        PathNode.QuadTo(188.3f, 438.8f, 146.8f, 517.8f),
                        PathNode.QuadTo(105.3f, 596.8f, 105.3f, 686.8f),
                        PathNode.QuadTo(105.3f, 814.8f, 180.8f, 913.8f),
                        PathNode.QuadTo(256.3f, 1012.8f, 377.8f, 1066.8f),
                        PathNode.QuadTo(499.3f, 1120.8f, 632.3f, 1120.8f),
                        PathNode.QuadTo(773.3f, 1120.8f, 894.3f, 1064.3f),
                        PathNode.QuadTo(1015.3f, 1007.8f, 1086.8f, 908.3f),
                        PathNode.QuadTo(1158.3f, 808.8f, 1158.3f, 686.8f),
                        PathNode.QuadTo(1158.3f, 559.8f, 1100.8f, 473.8f),
                        PathNode.QuadTo(1043.3f, 387.8f, 949.3f, 329.8f),
                        PathNode.QuadTo(893.3f, 295.8f, 847.3f, 278.3f),
                        PathNode.QuadTo(801.3f, 260.8f, 723.3f, 237.8f),
                        PathNode.LineTo(698.3f, 230.8f),
                        PathNode.QuadTo(649.3f, 215.8f, 506.3f, 174.8f),
                        PathNode.LineTo(398.3f, 143.8f),
                        PathNode.QuadTo(393.3f, 142.8f, 389.3f, 146.8f),
                        PathNode.QuadTo(385.3f, 150.8f, 387.3f, 155.8f),
                        PathNode.LineTo(421.3f, 269.8f),
                        PathNode.QuadTo(423.3f, 277.8f, 420.3f, 284.3f),
                        PathNode.QuadTo(417.3f, 290.8f, 409.3f, 293.8f),
                        PathNode.QuadTo(322.3f, 326.8f, 258.3f, 380.8f),
                        PathNode.Close,
                    ),
                    fill = SolidColor(Color.Black),
                    fillAlpha = 1f,
                    pathFillType = PathFillType.NonZero,
                )
            }
        }.build().also { messagesIconCache = it }
    }

private var phoneIconCache: ImageVector? = null

/** Handset. */
public val ElegantIcons.Phone: ImageVector
    get() {
        phoneIconCache?.let { return it }
        return ImageVector.Builder(
            name = "ElegantIcons.Phone",
            defaultWidth = 24.0f.dp,
            defaultHeight = 24.0f.dp,
            viewportWidth = 1178.4f,
            viewportHeight = 1178.4f,
        ).apply {
            group(scaleY = -1.0f, translationY = 1178.4f) {
                addPath(
                    pathData = listOf(
                        PathNode.MoveTo(383.7f, 383.2f),
                        PathNode.QuadTo(296.7f, 470.2f, 226.7f, 578.7f),
                        PathNode.QuadTo(156.7f, 687.2f, 118.7f, 800.2f),
                        PathNode.QuadTo(98.7f, 858.2f, 111.2f, 921.7f),
                        PathNode.QuadTo(123.7f, 985.2f, 175.7f, 1020.2f),
                        PathNode.QuadTo(221.7f, 1052.2f, 250.7f, 1066.2f),
                        PathNode.QuadTo(277.7f, 1080.2f, 305.2f, 1070.2f),
                        PathNode.QuadTo(332.7f, 1060.2f, 347.7f, 1034.2f),
                        PathNode.LineTo(450.7f, 856.2f),
                        PathNode.QuadTo(460.7f, 838.2f, 459.7f, 818.7f),
                        PathNode.QuadTo(458.7f, 799.2f, 449.7f, 783.2f),
                        PathNode.LineTo(414.7f, 717.2f),
                        PathNode.QuadTo(401.7f, 693.2f, 402.7f, 666.2f),
                        PathNode.QuadTo(403.7f, 639.2f, 417.7f, 616.2f),
                        PathNode.QuadTo(462.7f, 545.2f, 504.7f, 503.2f),
                        PathNode.QuadTo(549.7f, 458.2f, 617.7f, 416.2f),
                        PathNode.QuadTo(639.7f, 402.2f, 667.2f, 401.2f),
                        PathNode.QuadTo(694.7f, 400.2f, 718.7f, 413.2f),
                        PathNode.LineTo(784.7f, 449.2f),
                        PathNode.QuadTo(800.7f, 457.2f, 820.7f, 458.2f),
                        PathNode.QuadTo(840.7f, 459.2f, 857.7f, 449.2f),
                        PathNode.LineTo(1035.7f, 346.2f),
                        PathNode.QuadTo(1062.7f, 330.2f, 1071.2f, 302.7f),
                        PathNode.QuadTo(1079.7f, 275.2f, 1066.7f, 249.2f),
                        PathNode.QuadTo(1052.7f, 218.2f, 1021.7f, 174.2f),
                        PathNode.QuadTo(985.7f, 123.2f, 922.7f, 110.7f),
                        PathNode.QuadTo(859.7f, 98.2f, 801.7f, 117.2f),
                        PathNode.QuadTo(687.7f, 155.2f, 579.7f, 225.2f),
                        PathNode.QuadTo(471.7f, 295.2f, 383.7f, 383.2f),
                        PathNode.Close,
                    ),
                    fill = SolidColor(Color.Black),
                    fillAlpha = 1f,
                    pathFillType = PathFillType.NonZero,
                )
            }
        }.build().also { phoneIconCache = it }
    }

private var removeContactIconCache: ImageVector? = null

/** Person with a minus mark. */
public val ElegantIcons.RemoveContact: ImageVector
    get() {
        removeContactIconCache?.let { return it }
        return ImageVector.Builder(
            name = "ElegantIcons.RemoveContact",
            defaultWidth = 24.0f.dp,
            defaultHeight = 24.0f.dp,
            viewportWidth = 1182.0f,
            viewportHeight = 1182.0f,
        ).apply {
            group(scaleY = -1.0f, translationY = 1182.0f) {
                addPath(
                    pathData = listOf(
                        PathNode.MoveTo(686.5f, 153.5f),
                        PathNode.VerticalTo(181.5f),
                        PathNode.QuadTo(686.5f, 208.5f, 656.5f, 208.5f),
                        PathNode.HorizontalTo(208.5f),
                        PathNode.QuadTo(195.5f, 208.5f, 190.0f, 214.0f),
                        PathNode.QuadTo(184.5f, 219.5f, 184.5f, 233.5f),
                        PathNode.VerticalTo(263.5f),
                        PathNode.QuadTo(184.5f, 296.5f, 185.5f, 311.0f),
                        PathNode.QuadTo(186.5f, 325.5f, 191.5f, 337.5f),
                        PathNode.QuadTo(197.5f, 354.5f, 220.5f, 377.5f),
                        PathNode.QuadTo(229.5f, 386.5f, 240.0f, 391.5f),
                        PathNode.QuadTo(250.5f, 396.5f, 270.5f, 403.5f),
                        PathNode.LineTo(278.5f, 406.5f),
                        PathNode.QuadTo(423.5f, 460.5f, 583.5f, 460.5f),
                        PathNode.QuadTo(610.5f, 460.5f, 652.5f, 458.5f),
                        PathNode.QuadTo(668.5f, 457.5f, 677.5f, 463.0f),
                        PathNode.QuadTo(686.5f, 468.5f, 687.5f, 485.5f),
                        PathNode.LineTo(689.5f, 513.5f),
                        PathNode.QuadTo(690.5f, 526.5f, 682.5f, 534.5f),
                        PathNode.QuadTo(674.5f, 542.5f, 660.5f, 543.5f),
                        PathNode.LineTo(612.5f, 544.5f),
                        PathNode.QuadTo(514.5f, 547.5f, 427.5f, 533.0f),
                        PathNode.QuadTo(340.5f, 518.5f, 253.5f, 490.5f),
                        PathNode.QuadTo(215.5f, 476.5f, 199.0f, 469.5f),
                        PathNode.QuadTo(182.5f, 462.5f, 166.5f, 448.5f),
                        PathNode.QuadTo(149.5f, 435.5f, 133.5f, 413.0f),
                        PathNode.QuadTo(117.5f, 390.5f, 110.5f, 370.5f),
                        PathNode.QuadTo(102.5f, 349.5f, 100.5f, 327.0f),
                        PathNode.QuadTo(98.5f, 304.5f, 98.5f, 253.5f),
                        PathNode.QuadTo(98.5f, 213.5f, 100.0f, 196.5f),
                        PathNode.QuadTo(101.5f, 179.5f, 107.5f, 167.5f),
                        PathNode.QuadTo(117.5f, 145.5f, 142.5f, 132.5f),
                        PathNode.QuadTo(154.5f, 126.5f, 171.5f, 125.0f),
                        PathNode.QuadTo(188.5f, 123.5f, 227.5f, 123.5f),
                        PathNode.HorizontalTo(656.5f),
                        PathNode.QuadTo(671.5f, 123.5f, 679.0f, 131.0f),
                        PathNode.QuadTo(686.5f, 138.5f, 686.5f, 153.5f),
                        PathNode.Close,
                        PathNode.MoveTo(902.5f, 130.5f),
                        PathNode.LineTo(1061.5f, 290.5f),
                        PathNode.QuadTo(1082.5f, 311.5f, 1083.0f, 327.0f),
                        PathNode.QuadTo(1083.5f, 342.5f, 1061.5f, 364.5f),
                        PathNode.LineTo(892.5f, 533.5f),
                        PathNode.QuadTo(884.5f, 541.5f, 873.5f, 543.0f),
                        PathNode.QuadTo(862.5f, 544.5f, 852.5f, 534.5f),
                        PathNode.LineTo(831.5f, 513.5f),
                        PathNode.QuadTo(821.5f, 503.5f, 822.0f, 492.5f),
                        PathNode.QuadTo(822.5f, 481.5f, 831.5f, 473.5f),
                        PathNode.LineTo(934.5f, 370.5f),
                        PathNode.HorizontalTo(582.5f),
                        PathNode.QuadTo(571.5f, 370.5f, 563.5f, 362.5f),
                        PathNode.QuadTo(555.5f, 354.5f, 555.5f, 343.5f),
                        PathNode.VerticalTo(311.5f),
                        PathNode.QuadTo(555.5f, 300.5f, 563.5f, 292.5f),
                        PathNode.QuadTo(571.5f, 284.5f, 582.5f, 284.5f),
                        PathNode.HorizontalTo(934.5f),
                        PathNode.LineTo(842.5f, 191.5f),
                        PathNode.QuadTo(826.5f, 174.5f, 823.5f, 164.0f),
                        PathNode.QuadTo(820.5f, 153.5f, 831.5f, 142.5f),
                        PathNode.LineTo(852.5f, 120.5f),
                        PathNode.QuadTo(864.5f, 108.5f, 876.0f, 112.0f),
                        PathNode.QuadTo(887.5f, 115.5f, 902.5f, 130.5f),
                        PathNode.Close,
                        PathNode.MoveTo(823.5f, 842.5f),
                        PathNode.QuadTo(823.5f, 905.5f, 792.5f, 958.5f),
                        PathNode.QuadTo(761.5f, 1011.5f, 709.0f, 1042.5f),
                        PathNode.QuadTo(656.5f, 1073.5f, 593.5f, 1073.5f),
                        PathNode.QuadTo(530.5f, 1073.5f, 478.0f, 1042.5f),
                        PathNode.QuadTo(425.5f, 1011.5f, 394.5f, 958.5f),
                        PathNode.QuadTo(363.5f, 905.5f, 363.5f, 842.5f),
                        PathNode.QuadTo(363.5f, 780.5f, 394.5f, 727.5f),
                        PathNode.QuadTo(425.5f, 674.5f, 478.5f, 643.5f),
                        PathNode.QuadTo(531.5f, 612.5f, 593.5f, 612.5f),
                        PathNode.QuadTo(655.5f, 612.5f, 708.5f, 643.5f),
                        PathNode.QuadTo(761.5f, 674.5f, 792.5f, 727.5f),
                        PathNode.QuadTo(823.5f, 780.5f, 823.5f, 842.5f),
                        PathNode.Close,
                        PathNode.MoveTo(452.5f, 842.5f),
                        PathNode.QuadTo(452.5f, 880.5f, 471.5f, 913.0f),
                        PathNode.QuadTo(490.5f, 945.5f, 523.0f, 964.5f),
                        PathNode.QuadTo(555.5f, 983.5f, 593.5f, 983.5f),
                        PathNode.QuadTo(631.5f, 983.5f, 664.0f, 964.5f),
                        PathNode.QuadTo(696.5f, 945.5f, 715.0f, 913.0f),
                        PathNode.QuadTo(733.5f, 880.5f, 733.5f, 842.5f),
                        PathNode.QuadTo(733.5f, 804.5f, 715.0f, 772.0f),
                        PathNode.QuadTo(696.5f, 739.5f, 664.0f, 720.5f),
                        PathNode.QuadTo(631.5f, 701.5f, 593.5f, 701.5f),
                        PathNode.QuadTo(555.5f, 701.5f, 523.0f, 720.5f),
                        PathNode.QuadTo(490.5f, 739.5f, 471.5f, 772.0f),
                        PathNode.QuadTo(452.5f, 804.5f, 452.5f, 842.5f),
                        PathNode.Close,
                    ),
                    fill = SolidColor(Color.Black),
                    fillAlpha = 1f,
                    pathFillType = PathFillType.NonZero,
                )
            }
        }.build().also { removeContactIconCache = it }
    }

private var replyAllIconCache: ImageVector? = null

/** Two reply speech bubbles. */
public val ElegantIcons.ReplyAll: ImageVector
    get() {
        replyAllIconCache?.let { return it }
        return ImageVector.Builder(
            name = "ElegantIcons.ReplyAll",
            defaultWidth = 24.0f.dp,
            defaultHeight = 24.0f.dp,
            viewportWidth = 1324.8f,
            viewportHeight = 1324.8f,
        ).apply {
            group(scaleY = -1.0f, translationY = 1324.8f) {
                addPath(
                    pathData = listOf(
                        PathNode.MoveTo(1023.4f, 638.9f),
                        PathNode.QuadTo(1023.4f, 744.9f, 961.4f, 830.9f),
                        PathNode.QuadTo(899.4f, 916.9f, 794.4f, 965.9f),
                        PathNode.QuadTo(689.4f, 1014.9f, 566.4f, 1014.9f),
                        PathNode.QuadTo(451.4f, 1014.9f, 346.4f, 967.9f),
                        PathNode.QuadTo(241.4f, 920.9f, 175.9f, 835.4f),
                        PathNode.QuadTo(110.4f, 749.9f, 110.4f, 638.9f),
                        PathNode.QuadTo(110.4f, 559.9f, 145.4f, 492.4f),
                        PathNode.QuadTo(180.4f, 424.9f, 239.9f, 375.9f),
                        PathNode.QuadTo(299.4f, 326.9f, 371.4f, 297.9f),
                        PathNode.QuadTo(378.4f, 295.9f, 381.4f, 288.9f),
                        PathNode.QuadTo(384.4f, 281.9f, 382.4f, 274.9f),
                        PathNode.LineTo(354.4f, 180.9f),
                        PathNode.QuadTo(352.4f, 175.9f, 356.9f, 171.9f),
                        PathNode.QuadTo(361.4f, 167.9f, 368.4f, 169.9f),
                        PathNode.LineTo(667.4f, 256.9f),
                        PathNode.QuadTo(827.4f, 303.9f, 925.4f, 393.9f),
                        PathNode.QuadTo(1023.4f, 483.9f, 1023.4f, 638.9f),
                        PathNode.Close,
                        PathNode.MoveTo(402.4f, 377.9f),
                        PathNode.QuadTo(312.4f, 413.9f, 253.9f, 481.4f),
                        PathNode.QuadTo(195.4f, 548.9f, 195.4f, 638.9f),
                        PathNode.QuadTo(195.4f, 725.9f, 249.9f, 791.9f),
                        PathNode.QuadTo(304.4f, 857.9f, 389.9f, 893.4f),
                        PathNode.QuadTo(475.4f, 928.9f, 566.4f, 928.9f),
                        PathNode.QuadTo(672.4f, 928.9f, 757.4f, 888.9f),
                        PathNode.QuadTo(842.4f, 848.9f, 889.9f, 781.9f),
                        PathNode.QuadTo(937.4f, 714.9f, 937.4f, 638.9f),
                        PathNode.QuadTo(937.4f, 514.9f, 856.9f, 445.4f),
                        PathNode.QuadTo(776.4f, 375.9f, 639.4f, 336.9f),
                        PathNode.LineTo(480.4f, 290.9f),
                        PathNode.QuadTo(476.4f, 289.9f, 472.4f, 291.9f),
                        PathNode.QuadTo(468.4f, 293.9f, 467.4f, 298.9f),
                        PathNode.QuadTo(463.4f, 325.9f, 445.4f, 346.9f),
                        PathNode.QuadTo(427.4f, 367.9f, 402.4f, 377.9f),
                        PathNode.Close,
                        PathNode.MoveTo(1127.4f, 585.9f),
                        PathNode.QuadTo(1170.4f, 630.9f, 1192.4f, 684.9f),
                        PathNode.QuadTo(1214.4f, 738.9f, 1214.4f, 808.9f),
                        PathNode.QuadTo(1214.4f, 906.9f, 1157.4f, 986.4f),
                        PathNode.QuadTo(1100.4f, 1065.9f, 1003.9f, 1111.4f),
                        PathNode.QuadTo(907.4f, 1156.9f, 793.4f, 1156.9f),
                        PathNode.QuadTo(689.4f, 1156.9f, 582.4f, 1110.9f),
                        PathNode.QuadTo(577.4f, 1108.9f, 575.9f, 1103.9f),
                        PathNode.QuadTo(574.4f, 1098.9f, 577.4f, 1094.9f),
                        PathNode.QuadTo(580.4f, 1090.9f, 586.4f, 1090.9f),
                        PathNode.QuadTo(661.4f, 1091.9f, 739.4f, 1073.9f),
                        PathNode.QuadTo(747.4f, 1071.9f, 750.4f, 1071.9f),
                        PathNode.QuadTo(757.4f, 1070.9f, 767.4f, 1070.9f),
                        PathNode.QuadTo(777.4f, 1070.9f, 793.4f, 1070.9f),
                        PathNode.QuadTo(890.4f, 1070.9f, 966.9f, 1034.4f),
                        PathNode.QuadTo(1043.4f, 997.9f, 1086.4f, 937.4f),
                        PathNode.QuadTo(1129.4f, 876.9f, 1129.4f, 808.9f),
                        PathNode.QuadTo(1129.4f, 760.9f, 1113.4f, 716.9f),
                        PathNode.QuadTo(1108.4f, 701.9f, 1109.4f, 692.9f),
                        PathNode.QuadTo(1115.4f, 642.9f, 1108.4f, 597.9f),
                        PathNode.QuadTo(1106.4f, 582.9f, 1111.9f, 578.9f),
                        PathNode.QuadTo(1117.4f, 574.9f, 1127.4f, 585.9f),
                        PathNode.Close,
                    ),
                    fill = SolidColor(Color.Black),
                    fillAlpha = 1f,
                    pathFillType = PathFillType.NonZero,
                )
            }
        }.build().also { replyAllIconCache = it }
    }

