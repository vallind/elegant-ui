package com.elegant.compose.ui.foundation.icons

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.graphics.vector.PathNode
import androidx.compose.ui.graphics.vector.group
import androidx.compose.ui.unit.dp

/**
 * Built-in Elegant UI vector icons.
 *
 * Icons are 24dp viewport vector paths that inherit their color from the render tint; the fill
 * color is fixed to opaque black and replaced at draw time. Use [ElegantIcon] to render them.
 */
public object ElegantIcons {

    /** Arrow pointing left. */
    public val ArrowLeft: ImageVector = arrowIcon(direction = -1f, name = "ElegantIcons.ArrowLeft")

    /** Arrow pointing right. */
    public val ArrowRight: ImageVector = arrowIcon(direction = 1f, name = "ElegantIcons.ArrowRight")

    /** Arrow pointing up. */
    public val ArrowUp: ImageVector = arrowIcon(direction = -1f, vertical = true, name = "ElegantIcons.ArrowUp")

    /** Arrow pointing down. */
    public val ArrowDown: ImageVector = arrowIcon(direction = 1f, vertical = true, name = "ElegantIcons.ArrowDown")



    /** Chevron pointing up. */
    public val ChevronUp: ImageVector = chevronIcon(direction = -1f, vertical = true, name = "ElegantIcons.ChevronUp")

    /** Chevron pointing down. */
    public val ChevronDown: ImageVector = chevronIcon(direction = 1f, vertical = true, name = "ElegantIcons.ChevronDown")



    /** Plus mark. */
    public val Plus: ImageVector = plusIcon("ElegantIcons.Plus")

    /** Minus mark. */
    public val Minus: ImageVector = minusIcon("ElegantIcons.Minus")





    /** Three vertical dots. */
    public val MoreVert: ImageVector = dotsIcon(vertical = true, name = "ElegantIcons.MoreVert")

    /** Three horizontal dots. */
    public val MoreHoriz: ImageVector = dotsIcon(vertical = false, name = "ElegantIcons.MoreHoriz")

    /** Person silhouette. */
    public val Person: ImageVector = personIcon("ElegantIcons.Person")



    /** Bell for notifications. */
    public val Notifications: ImageVector = notificationsIcon("ElegantIcons.Notifications")

    /** Five-point star. */
    public val Star: ImageVector = starIcon("ElegantIcons.Star")

    /** Heart silhouette. */
    public val Heart: ImageVector = heartIcon("ElegantIcons.Heart")

    /** All icons in declaration order. */
    private var CheckCache: ImageVector? = null

    /** Mirrors the reference icon geometry for Check. */
    public val Check: ImageVector
        get() {
            CheckCache?.let { return it }
            return         ImageVector.Builder("ElegantIcons.Check", 26.dp, 26.dp, 56f, 56f).apply {
                        path(
                            fill = SolidColor(Color.Black),
                            fillAlpha = 1f,
                            pathFillType = PathFillType.EvenOdd,
                        ) {
                            moveTo(46.8171f, 18.1514f)
                            curveTo(48.0496f, 16.6624f, 47.8417f, 14.4561f, 46.3527f, 13.2235f)
                            curveTo(44.8636f, 11.991f, 42.6573f, 12.1989f, 41.4247f, 13.6879f)
                            lineTo(22.9535f, 36.0031f)
                            lineTo(13.4007f, 26.4502f)
                            curveTo(12.0338f, 25.0833f, 9.8177f, 25.0833f, 8.4509f, 26.4502f)
                            curveTo(7.0841f, 27.817f, 7.0841f, 30.0331f, 8.4509f, 31.3999f)
                            lineTo(20.7077f, 43.6567f)
                            curveTo(21.7243f, 44.6733f, 23.2108f, 44.9338f, 24.4682f, 44.4381f)
                            curveTo(25.0159f, 44.2302f, 25.5189f, 43.8818f, 25.9192f, 43.3982f)
                            lineTo(46.8171f, 18.1514f)
                            close()
                        }
                    }.build().also { CheckCache = it }
        }
    private var CloseCache: ImageVector? = null

    /** Mirrors the reference icon geometry for Close. */
    public val Close: ImageVector
        get() {
            CloseCache?.let { return it }
            return         ImageVector.Builder(
                        name = "ElegantIcons.Close",
                        defaultWidth = 24.0f.dp,
                        defaultHeight = 24.0f.dp,
                        viewportWidth = 1047.6f,
                        viewportHeight = 1047.6f,
                    ).apply {
                        group(scaleY = -1.0f, translationY = 1047.6f) {
                            addPath(
                                pathData = listOf(
                                    PathNode.MoveTo(523.8f, 593.3f),
                                    PathNode.LineTo(832.7f, 903.9f),
                                    PathNode.QuadTo(845.0f, 916.5f, 857.1f, 916.3f),
                                    PathNode.QuadTo(869.2f, 916.2f, 881.2f, 903.9f),
                                    PathNode.LineTo(897.5f, 887.6f),
                                    PathNode.QuadTo(909.6f, 875.6f, 909.7f, 863.8f),
                                    PathNode.QuadTo(909.9f, 852.0f, 897.5f, 839.1f),
                                    PathNode.LineTo(588.6f, 528.4f),
                                    PathNode.LineTo(898.8f, 218.3f),
                                    PathNode.QuadTo(911.0f, 206.6f, 911.3f, 194.8f),
                                    PathNode.QuadTo(911.7f, 182.9f, 898.8f, 170.5f),
                                    PathNode.LineTo(881.0f, 152.7f),
                                    PathNode.QuadTo(858.2f, 129.9f, 833.6f, 153.8f),
                                    PathNode.LineTo(523.8f, 463.6f),
                                    PathNode.LineTo(213.1f, 154.7f),
                                    PathNode.QuadTo(201.5f, 142.7f, 189.2f, 142.5f),
                                    PathNode.QuadTo(177.0f, 142.3f, 164.6f, 154.7f),
                                    PathNode.LineTo(148.6f, 171.0f),
                                    PathNode.QuadTo(136.3f, 182.7f, 136.3f, 194.9f),
                                    PathNode.QuadTo(136.3f, 207.1f, 148.2f, 219.2f),
                                    PathNode.LineTo(458.9f, 528.4f),
                                    PathNode.LineTo(149.1f, 839.1f),
                                    PathNode.QuadTo(137.1f, 852.0f, 137.1f, 863.8f),
                                    PathNode.QuadTo(137.1f, 875.6f, 149.1f, 887.6f),
                                    PathNode.LineTo(165.5f, 903.9f),
                                    PathNode.QuadTo(178.4f, 916.5f, 190.0f, 916.5f),
                                    PathNode.QuadTo(201.6f, 916.5f, 214.0f, 903.9f),
                                    PathNode.Close,
                                ),
                                fill = SolidColor(Color.Black),
                                fillAlpha = 1f,
                                pathFillType = PathFillType.NonZero,
                            )
                        }
                    }.build().also { CloseCache = it }
        }
    private var SearchCache: ImageVector? = null

    /** Mirrors the reference icon geometry for Search. */
    public val Search: ImageVector
        get() {
            SearchCache?.let { return it }
            return         ImageVector.Builder(
                        name = "ElegantIcons.Search",
                        defaultWidth = 24.0f.dp,
                        defaultHeight = 24.0f.dp,
                        viewportWidth = 1204.8f,
                        viewportHeight = 1204.8f,
                    ).apply {
                        group(scaleY = -1.0f, translationY = 1204.8f) {
                            addPath(
                                pathData = listOf(
                                    PathNode.MoveTo(726.9f, 366.4f),
                                    PathNode.QuadTo(746.9f, 378.4f, 754.9f, 377.9f),
                                    PathNode.QuadTo(762.9f, 377.4f, 776.9f, 363.4f),
                                    PathNode.LineTo(1027.9f, 112.4f),
                                    PathNode.QuadTo(1038.9f, 101.4f, 1049.4f, 100.9f),
                                    PathNode.QuadTo(1059.9f, 100.4f, 1071.9f, 112.4f),
                                    PathNode.LineTo(1088.9f, 129.4f),
                                    PathNode.QuadTo(1100.9f, 141.4f, 1100.9f, 150.9f),
                                    PathNode.QuadTo(1100.9f, 160.4f, 1088.9f, 172.4f),
                                    PathNode.LineTo(834.9f, 427.4f),
                                    PathNode.QuadTo(822.9f, 439.4f, 823.4f, 446.9f),
                                    PathNode.QuadTo(823.9f, 454.4f, 836.9f, 473.4f),
                                    PathNode.QuadTo(870.9f, 522.4f, 889.4f, 580.9f),
                                    PathNode.QuadTo(907.9f, 639.4f, 907.9f, 702.4f),
                                    PathNode.QuadTo(907.9f, 811.4f, 853.9f, 903.9f),
                                    PathNode.QuadTo(799.9f, 996.4f, 707.4f, 1050.4f),
                                    PathNode.QuadTo(614.9f, 1104.4f, 505.9f, 1104.4f),
                                    PathNode.QuadTo(396.9f, 1104.4f, 304.4f, 1050.4f),
                                    PathNode.QuadTo(211.9f, 996.4f, 157.9f, 903.9f),
                                    PathNode.QuadTo(103.9f, 811.4f, 103.9f, 702.4f),
                                    PathNode.QuadTo(103.9f, 593.4f, 157.9f, 500.9f),
                                    PathNode.QuadTo(211.9f, 408.4f, 304.4f, 354.4f),
                                    PathNode.QuadTo(396.9f, 300.4f, 505.9f, 300.4f),
                                    PathNode.QuadTo(565.9f, 300.4f, 622.4f, 317.4f),
                                    PathNode.QuadTo(678.9f, 334.4f, 726.9f, 366.4f),
                                    PathNode.Close,
                                    PathNode.MoveTo(188.9f, 702.4f),
                                    PathNode.QuadTo(188.9f, 788.4f, 231.4f, 861.4f),
                                    PathNode.QuadTo(273.9f, 934.4f, 346.9f, 976.9f),
                                    PathNode.QuadTo(419.9f, 1019.4f, 505.9f, 1019.4f),
                                    PathNode.QuadTo(591.9f, 1019.4f, 664.4f, 976.9f),
                                    PathNode.QuadTo(736.9f, 934.4f, 779.4f, 861.4f),
                                    PathNode.QuadTo(821.9f, 788.4f, 821.9f, 702.4f),
                                    PathNode.QuadTo(821.9f, 616.4f, 779.4f, 543.4f),
                                    PathNode.QuadTo(736.9f, 470.4f, 664.4f, 427.9f),
                                    PathNode.QuadTo(591.9f, 385.4f, 505.9f, 385.4f),
                                    PathNode.QuadTo(419.9f, 385.4f, 346.9f, 427.9f),
                                    PathNode.QuadTo(273.9f, 470.4f, 231.4f, 543.4f),
                                    PathNode.QuadTo(188.9f, 616.4f, 188.9f, 702.4f),
                                    PathNode.Close,
                                ),
                                fill = SolidColor(Color.Black),
                                fillAlpha = 1f,
                                pathFillType = PathFillType.NonZero,
                            )
                        }
                    }.build().also { SearchCache = it }
        }
    private var EditCache: ImageVector? = null

    /** Mirrors the reference icon geometry for Edit. */
    public val Edit: ImageVector
        get() {
            EditCache?.let { return it }
            return         ImageVector.Builder(
                        name = "ElegantIcons.Edit",
                        defaultWidth = 24.0f.dp,
                        defaultHeight = 24.0f.dp,
                        viewportWidth = 1156.8f,
                        viewportHeight = 1156.8f,
                    ).apply {
                        group(scaleY = -1.0f, translationY = 1156.8f) {
                            addPath(
                                pathData = listOf(
                                    PathNode.MoveTo(878.7f, 150.2f),
                                    PathNode.QuadTo(930.7f, 176.3f, 956.8f, 228.3f),
                                    PathNode.QuadTo(970.2f, 254.6f, 973.5f, 291.5f),
                                    PathNode.QuadTo(976.7f, 328.5f, 976.7f, 414.2f),
                                    PathNode.VerticalTo(713.7f),
                                    PathNode.QuadTo(976.7f, 728.2f, 968.8f, 735.2f),
                                    PathNode.QuadTo(960.8f, 742.2f, 950.5f, 741.8f),
                                    PathNode.QuadTo(940.2f, 741.3f, 933.7f, 734.7f),
                                    PathNode.LineTo(908.8f, 708.9f),
                                    PathNode.QuadTo(898.1f, 698.3f, 894.2f, 685.5f),
                                    PathNode.QuadTo(890.2f, 672.8f, 890.2f, 654.7f),
                                    PathNode.VerticalTo(374.6f),
                                    PathNode.QuadTo(890.2f, 325.0f, 888.5f, 304.7f),
                                    PathNode.QuadTo(886.8f, 284.3f, 879.7f, 270.0f),
                                    PathNode.QuadTo(866.1f, 241.6f, 837.9f, 227.3f),
                                    PathNode.QuadTo(822.7f, 220.2f, 802.5f, 218.5f),
                                    PathNode.QuadTo(782.3f, 216.7f, 733.4f, 216.7f),
                                    PathNode.HorizontalTo(377.4f),
                                    PathNode.QuadTo(328.5f, 216.7f, 307.8f, 218.5f),
                                    PathNode.QuadTo(287.1f, 220.2f, 272.9f, 227.3f),
                                    PathNode.QuadTo(246.0f, 240.9f, 230.1f, 270.0f),
                                    PathNode.QuadTo(223.0f, 284.3f, 221.3f, 304.7f),
                                    PathNode.QuadTo(219.6f, 325.0f, 219.6f, 374.6f),
                                    PathNode.VerticalTo(745.3f),
                                    PathNode.QuadTo(219.6f, 794.8f, 221.3f, 815.2f),
                                    PathNode.QuadTo(223.0f, 835.6f, 230.1f, 850.8f),
                                    PathNode.QuadTo(246.0f, 879.9f, 272.9f, 892.5f),
                                    PathNode.QuadTo(287.1f, 899.7f, 307.8f, 901.4f),
                                    PathNode.QuadTo(328.5f, 903.1f, 377.4f, 903.1f),
                                    PathNode.HorizontalTo(667.5f),
                                    PathNode.QuadTo(704.3f, 903.1f, 720.5f, 918.0f),
                                    PathNode.LineTo(748.2f, 943.8f),
                                    PathNode.QuadTo(758.7f, 954.3f, 758.5f, 964.9f),
                                    PathNode.QuadTo(758.4f, 975.5f, 750.0f, 982.5f),
                                    PathNode.QuadTo(741.6f, 989.5f, 730.2f, 989.5f),
                                    PathNode.HorizontalTo(418.0f),
                                    PathNode.QuadTo(332.0f, 989.5f, 294.7f, 986.3f),
                                    PathNode.QuadTo(257.4f, 983.1f, 231.1f, 969.6f),
                                    PathNode.QuadTo(180.3f, 943.6f, 153.0f, 891.6f),
                                    PathNode.QuadTo(139.9f, 865.3f, 137.0f, 828.5f),
                                    PathNode.QuadTo(134.1f, 791.7f, 134.1f, 705.6f),
                                    PathNode.VerticalTo(414.2f),
                                    PathNode.QuadTo(134.1f, 328.5f, 137.0f, 291.5f),
                                    PathNode.QuadTo(139.9f, 254.6f, 153.0f, 228.3f),
                                    PathNode.QuadTo(180.3f, 176.3f, 231.1f, 150.2f),
                                    PathNode.QuadTo(257.4f, 136.8f, 294.7f, 133.5f),
                                    PathNode.QuadTo(332.0f, 130.3f, 418.0f, 130.3f),
                                    PathNode.HorizontalTo(692.8f),
                                    PathNode.QuadTo(778.8f, 130.3f, 815.6f, 133.5f),
                                    PathNode.QuadTo(852.4f, 136.8f, 878.7f, 150.2f),
                                    PathNode.Close,
                                    PathNode.MoveTo(578.2f, 496.5f),
                                    PathNode.LineTo(1009.8f, 929.0f),
                                    PathNode.QuadTo(1022.5f, 942.1f, 1021.3f, 961.9f),
                                    PathNode.QuadTo(1020.0f, 981.7f, 1009.8f, 992.2f),
                                    PathNode.LineTo(988.6f, 1013.4f),
                                    PathNode.QuadTo(977.2f, 1024.6f, 957.0f, 1025.5f),
                                    PathNode.QuadTo(936.8f, 1026.4f, 921.7f, 1011.6f),
                                    PathNode.LineTo(492.9f, 581.9f),
                                    PathNode.QuadTo(461.3f, 550.9f, 438.0f, 511.0f),
                                    PathNode.LineTo(388.2f, 417.6f),
                                    PathNode.QuadTo(384.7f, 410.6f, 387.9f, 403.1f),
                                    PathNode.QuadTo(391.2f, 395.8f, 398.6f, 392.5f),
                                    PathNode.QuadTo(406.0f, 389.2f, 413.0f, 392.7f),
                                    PathNode.LineTo(506.4f, 442.5f),
                                    PathNode.QuadTo(545.3f, 464.5f, 578.2f, 496.5f),
                                    PathNode.Close,
                                ),
                                fill = SolidColor(Color.Black),
                                fillAlpha = 1f,
                                pathFillType = PathFillType.NonZero,
                            )
                        }
                    }.build().also { EditCache = it }
        }
    private var DeleteCache: ImageVector? = null

    /** Mirrors the reference icon geometry for Delete. */
    public val Delete: ImageVector
        get() {
            DeleteCache?.let { return it }
            return         ImageVector.Builder(
                        name = "ElegantIcons.Delete",
                        defaultWidth = 24.0f.dp,
                        defaultHeight = 24.0f.dp,
                        viewportWidth = 1278.0f,
                        viewportHeight = 1278.0f,
                    ).apply {
                        group(scaleY = -1.0f, translationY = 1278.0f) {
                            addPath(
                                pathData = listOf(
                                    PathNode.MoveTo(900.5f, 120.5f),
                                    PathNode.QuadTo(941.5f, 139.5f, 962.5f, 177.5f),
                                    PathNode.QuadTo(973.5f, 197.5f, 978.0f, 226.0f),
                                    PathNode.QuadTo(982.5f, 254.5f, 986.5f, 317.5f),
                                    PathNode.LineTo(1029.5f, 911.5f),
                                    PathNode.HorizontalTo(1100.5f),
                                    PathNode.QuadTo(1110.5f, 911.5f, 1118.5f, 919.5f),
                                    PathNode.QuadTo(1126.5f, 927.5f, 1126.5f, 939.5f),
                                    PathNode.VerticalTo(968.5f),
                                    PathNode.QuadTo(1126.5f, 980.5f, 1118.5f, 988.5f),
                                    PathNode.QuadTo(1110.5f, 996.5f, 1100.5f, 996.5f),
                                    PathNode.HorizontalTo(921.5f),
                                    PathNode.QuadTo(897.5f, 996.5f, 887.5f, 1002.5f),
                                    PathNode.QuadTo(877.5f, 1008.5f, 868.5f, 1028.5f),
                                    PathNode.LineTo(846.5f, 1075.5f),
                                    PathNode.LineTo(839.5f, 1089.5f),
                                    PathNode.QuadTo(831.5f, 1107.5f, 824.5f, 1120.0f),
                                    PathNode.QuadTo(817.5f, 1132.5f, 809.5f, 1140.5f),
                                    PathNode.QuadTo(791.5f, 1158.5f, 770.5f, 1165.5f),
                                    PathNode.QuadTo(758.5f, 1169.5f, 740.0f, 1170.5f),
                                    PathNode.QuadTo(721.5f, 1171.5f, 695.5f, 1171.5f),
                                    PathNode.HorizontalTo(582.5f),
                                    PathNode.QuadTo(557.5f, 1171.5f, 539.0f, 1170.5f),
                                    PathNode.QuadTo(520.5f, 1169.5f, 508.5f, 1165.5f),
                                    PathNode.QuadTo(486.5f, 1158.5f, 468.5f, 1140.5f),
                                    PathNode.QuadTo(460.5f, 1131.5f, 451.5f, 1115.0f),
                                    PathNode.QuadTo(442.5f, 1098.5f, 431.5f, 1075.5f),
                                    PathNode.LineTo(406.5f, 1023.5f),
                                    PathNode.QuadTo(398.5f, 1005.5f, 390.0f, 1001.0f),
                                    PathNode.QuadTo(381.5f, 996.5f, 355.5f, 996.5f),
                                    PathNode.HorizontalTo(179.5f),
                                    PathNode.QuadTo(169.5f, 996.5f, 160.5f, 989.0f),
                                    PathNode.QuadTo(151.5f, 981.5f, 151.5f, 965.5f),
                                    PathNode.VerticalTo(941.5f),
                                    PathNode.QuadTo(151.5f, 926.5f, 160.5f, 919.0f),
                                    PathNode.QuadTo(169.5f, 911.5f, 179.5f, 911.5f),
                                    PathNode.HorizontalTo(239.5f),
                                    PathNode.LineTo(280.5f, 318.5f),
                                    PathNode.QuadTo(284.5f, 254.5f, 289.0f, 226.0f),
                                    PathNode.QuadTo(293.5f, 197.5f, 304.5f, 177.5f),
                                    PathNode.QuadTo(328.5f, 138.5f, 366.5f, 120.5f),
                                    PathNode.QuadTo(386.5f, 110.5f, 414.5f, 108.5f),
                                    PathNode.QuadTo(442.5f, 106.5f, 508.5f, 106.5f),
                                    PathNode.HorizontalTo(759.5f),
                                    PathNode.QuadTo(825.5f, 106.5f, 853.0f, 108.5f),
                                    PathNode.QuadTo(880.5f, 110.5f, 900.5f, 120.5f),
                                    PathNode.Close,
                                    PathNode.MoveTo(410.5f, 200.5f),
                                    PathNode.QuadTo(390.5f, 209.5f, 379.5f, 229.5f),
                                    PathNode.QuadTo(374.5f, 238.5f, 372.0f, 256.5f),
                                    PathNode.QuadTo(369.5f, 274.5f, 367.5f, 299.5f),
                                    PathNode.LineTo(324.5f, 911.5f),
                                    PathNode.HorizontalTo(944.5f),
                                    PathNode.LineTo(901.5f, 299.5f),
                                    PathNode.QuadTo(899.5f, 274.5f, 897.0f, 256.5f),
                                    PathNode.QuadTo(894.5f, 238.5f, 889.5f, 229.5f),
                                    PathNode.QuadTo(878.5f, 209.5f, 858.5f, 200.5f),
                                    PathNode.QuadTo(848.5f, 195.5f, 834.5f, 194.5f),
                                    PathNode.QuadTo(820.5f, 193.5f, 787.5f, 193.5f),
                                    PathNode.HorizontalTo(481.5f),
                                    PathNode.QuadTo(448.5f, 193.5f, 434.5f, 194.5f),
                                    PathNode.QuadTo(420.5f, 195.5f, 410.5f, 200.5f),
                                    PathNode.Close,
                                    PathNode.MoveTo(585.5f, 357.5f),
                                    PathNode.LineTo(569.5f, 773.5f),
                                    PathNode.QuadTo(568.5f, 783.5f, 561.0f, 791.0f),
                                    PathNode.QuadTo(553.5f, 798.5f, 542.5f, 798.5f),
                                    PathNode.HorizontalTo(510.5f),
                                    PathNode.QuadTo(499.5f, 798.5f, 491.5f, 790.5f),
                                    PathNode.QuadTo(483.5f, 782.5f, 483.5f, 771.5f),
                                    PathNode.LineTo(500.5f, 355.5f),
                                    PathNode.QuadTo(501.5f, 344.5f, 509.0f, 337.0f),
                                    PathNode.QuadTo(516.5f, 329.5f, 527.5f, 329.5f),
                                    PathNode.HorizontalTo(559.5f),
                                    PathNode.QuadTo(570.5f, 329.5f, 578.5f, 338.0f),
                                    PathNode.QuadTo(586.5f, 346.5f, 585.5f, 357.5f),
                                    PathNode.Close,
                                    PathNode.MoveTo(768.5f, 355.5f),
                                    PathNode.LineTo(785.5f, 771.5f),
                                    PathNode.QuadTo(785.5f, 782.5f, 777.5f, 790.5f),
                                    PathNode.QuadTo(769.5f, 798.5f, 758.5f, 798.5f),
                                    PathNode.HorizontalTo(726.5f),
                                    PathNode.QuadTo(715.5f, 798.5f, 708.0f, 791.0f),
                                    PathNode.QuadTo(700.5f, 783.5f, 699.5f, 773.5f),
                                    PathNode.LineTo(683.5f, 357.5f),
                                    PathNode.QuadTo(682.5f, 346.5f, 690.5f, 338.0f),
                                    PathNode.QuadTo(698.5f, 329.5f, 709.5f, 329.5f),
                                    PathNode.HorizontalTo(741.5f),
                                    PathNode.QuadTo(752.5f, 329.5f, 760.0f, 337.0f),
                                    PathNode.QuadTo(767.5f, 344.5f, 768.5f, 355.5f),
                                    PathNode.Close,
                                    PathNode.MoveTo(493.5f, 1005.5f),
                                    PathNode.LineTo(515.5f, 1050.5f),
                                    PathNode.QuadTo(525.5f, 1072.5f, 538.0f, 1079.0f),
                                    PathNode.QuadTo(550.5f, 1085.5f, 576.5f, 1085.5f),
                                    PathNode.HorizontalTo(701.5f),
                                    PathNode.QuadTo(728.5f, 1085.5f, 740.0f, 1079.0f),
                                    PathNode.QuadTo(751.5f, 1072.5f, 761.5f, 1052.5f),
                                    PathNode.LineTo(785.5f, 1005.5f),
                                    PathNode.QuadTo(787.5f, 1002.5f, 786.0f, 999.5f),
                                    PathNode.QuadTo(784.5f, 996.5f, 780.5f, 996.5f),
                                    PathNode.HorizontalTo(498.5f),
                                    PathNode.QuadTo(493.5f, 996.5f, 492.5f, 999.0f),
                                    PathNode.QuadTo(491.5f, 1001.5f, 493.5f, 1005.5f),
                                    PathNode.Close,
                                ),
                                fill = SolidColor(Color.Black),
                                fillAlpha = 1f,
                                pathFillType = PathFillType.NonZero,
                            )
                        }
                    }.build().also { DeleteCache = it }
        }
    private var ShareCache: ImageVector? = null

    /** Mirrors the reference icon geometry for Share. */
    public val Share: ImageVector
        get() {
            ShareCache?.let { return it }
            return         ImageVector.Builder(
                        name = "ElegantIcons.Share",
                        defaultWidth = 24.0f.dp,
                        defaultHeight = 24.0f.dp,
                        viewportWidth = 1407.6f,
                        viewportHeight = 1407.6f,
                    ).apply {
                        group(scaleY = -1.0f, translationY = 1407.6f) {
                            addPath(
                                pathData = listOf(
                                    PathNode.MoveTo(1049.8f, 137.3f),
                                    PathNode.QuadTo(1104.8f, 166.3f, 1132.8f, 220.3f),
                                    PathNode.QuadTo(1146.8f, 248.3f, 1150.3f, 287.8f),
                                    PathNode.QuadTo(1153.8f, 327.3f, 1153.8f, 421.3f),
                                    PathNode.VerticalTo(640.3f),
                                    PathNode.QuadTo(1153.8f, 715.3f, 1151.8f, 747.8f),
                                    PathNode.QuadTo(1149.8f, 780.3f, 1139.8f, 803.3f),
                                    PathNode.QuadTo(1125.8f, 839.3f, 1098.3f, 866.8f),
                                    PathNode.QuadTo(1070.8f, 894.3f, 1034.8f, 908.3f),
                                    PathNode.QuadTo(1010.8f, 918.3f, 977.8f, 920.3f),
                                    PathNode.QuadTo(944.8f, 922.3f, 860.8f, 922.3f),
                                    PathNode.QuadTo(848.8f, 922.3f, 840.3f, 914.8f),
                                    PathNode.QuadTo(831.8f, 907.3f, 831.8f, 892.3f),
                                    PathNode.VerticalTo(869.3f),
                                    PathNode.QuadTo(831.8f, 850.3f, 840.3f, 843.3f),
                                    PathNode.QuadTo(848.8f, 836.3f, 863.8f, 836.3f),
                                    PathNode.HorizontalTo(893.8f),
                                    PathNode.QuadTo(946.8f, 836.3f, 969.3f, 834.3f),
                                    PathNode.QuadTo(991.8f, 832.3f, 1008.8f, 824.3f),
                                    PathNode.QuadTo(1023.8f, 817.3f, 1036.3f, 804.8f),
                                    PathNode.QuadTo(1048.8f, 792.3f, 1055.8f, 777.3f),
                                    PathNode.QuadTo(1063.8f, 760.3f, 1065.8f, 737.8f),
                                    PathNode.QuadTo(1067.8f, 715.3f, 1067.8f, 661.3f),
                                    PathNode.VerticalTo(377.3f),
                                    PathNode.QuadTo(1067.8f, 323.3f, 1065.8f, 300.8f),
                                    PathNode.QuadTo(1063.8f, 278.3f, 1055.8f, 262.3f),
                                    PathNode.QuadTo(1041.8f, 232.3f, 1008.8f, 214.3f),
                                    PathNode.QuadTo(991.8f, 206.3f, 969.3f, 204.3f),
                                    PathNode.QuadTo(946.8f, 202.3f, 893.8f, 202.3f),
                                    PathNode.HorizontalTo(513.8f),
                                    PathNode.QuadTo(460.8f, 202.3f, 437.8f, 204.3f),
                                    PathNode.QuadTo(414.8f, 206.3f, 398.8f, 214.3f),
                                    PathNode.QuadTo(365.8f, 232.3f, 350.8f, 262.3f),
                                    PathNode.QuadTo(342.8f, 278.3f, 340.8f, 300.8f),
                                    PathNode.QuadTo(338.8f, 323.3f, 338.8f, 377.3f),
                                    PathNode.VerticalTo(661.3f),
                                    PathNode.QuadTo(338.8f, 715.3f, 340.8f, 737.8f),
                                    PathNode.QuadTo(342.8f, 760.3f, 350.8f, 777.3f),
                                    PathNode.QuadTo(358.8f, 792.3f, 370.8f, 804.8f),
                                    PathNode.QuadTo(382.8f, 817.3f, 398.8f, 824.3f),
                                    PathNode.QuadTo(414.8f, 832.3f, 437.8f, 834.3f),
                                    PathNode.QuadTo(460.8f, 836.3f, 513.8f, 836.3f),
                                    PathNode.HorizontalTo(542.8f),
                                    PathNode.QuadTo(559.8f, 836.3f, 567.3f, 843.8f),
                                    PathNode.QuadTo(574.8f, 851.3f, 574.8f, 869.3f),
                                    PathNode.VerticalTo(892.3f),
                                    PathNode.QuadTo(574.8f, 922.3f, 544.8f, 922.3f),
                                    PathNode.QuadTo(462.8f, 922.3f, 429.3f, 920.3f),
                                    PathNode.QuadTo(395.8f, 918.3f, 372.8f, 908.3f),
                                    PathNode.QuadTo(336.8f, 894.3f, 309.3f, 866.8f),
                                    PathNode.QuadTo(281.8f, 839.3f, 266.8f, 802.3f),
                                    PathNode.QuadTo(257.8f, 779.3f, 255.8f, 747.3f),
                                    PathNode.QuadTo(253.8f, 715.3f, 253.8f, 640.3f),
                                    PathNode.VerticalTo(421.3f),
                                    PathNode.QuadTo(253.8f, 327.3f, 256.8f, 287.8f),
                                    PathNode.QuadTo(259.8f, 248.3f, 273.8f, 220.3f),
                                    PathNode.QuadTo(302.8f, 166.3f, 356.8f, 137.3f),
                                    PathNode.QuadTo(384.8f, 123.3f, 424.8f, 120.3f),
                                    PathNode.QuadTo(464.8f, 117.3f, 557.8f, 117.3f),
                                    PathNode.HorizontalTo(849.8f),
                                    PathNode.QuadTo(942.8f, 117.3f, 982.3f, 120.3f),
                                    PathNode.QuadTo(1021.8f, 123.3f, 1049.8f, 137.3f),
                                    PathNode.Close,
                                    PathNode.MoveTo(745.8f, 497.3f),
                                    PathNode.VerticalTo(1142.3f),
                                    PathNode.LineTo(878.8f, 1009.3f),
                                    PathNode.QuadTo(890.8f, 997.3f, 902.3f, 996.8f),
                                    PathNode.QuadTo(913.8f, 996.3f, 927.8f, 1010.3f),
                                    PathNode.LineTo(938.8f, 1021.3f),
                                    PathNode.QuadTo(952.8f, 1035.3f, 952.8f, 1045.8f),
                                    PathNode.QuadTo(952.8f, 1056.3f, 939.8f, 1069.3f),
                                    PathNode.LineTo(733.8f, 1275.3f),
                                    PathNode.QuadTo(719.8f, 1289.3f, 703.8f, 1289.8f),
                                    PathNode.QuadTo(687.8f, 1290.3f, 674.8f, 1277.3f),
                                    PathNode.LineTo(461.8f, 1064.3f),
                                    PathNode.QuadTo(454.8f, 1058.3f, 453.8f, 1047.8f),
                                    PathNode.QuadTo(452.8f, 1037.3f, 462.8f, 1027.3f),
                                    PathNode.LineTo(483.8f, 1006.3f),
                                    PathNode.QuadTo(494.8f, 995.3f, 505.3f, 996.3f),
                                    PathNode.QuadTo(515.8f, 997.3f, 527.8f, 1009.3f),
                                    PathNode.LineTo(660.8f, 1142.3f),
                                    PathNode.VerticalTo(497.3f),
                                    PathNode.QuadTo(660.8f, 482.3f, 668.3f, 475.3f),
                                    PathNode.QuadTo(675.8f, 468.3f, 690.8f, 468.3f),
                                    PathNode.HorizontalTo(717.8f),
                                    PathNode.QuadTo(730.8f, 468.3f, 738.3f, 475.8f),
                                    PathNode.QuadTo(745.8f, 483.3f, 745.8f, 497.3f),
                                    PathNode.Close,
                                ),
                                fill = SolidColor(Color.Black),
                                fillAlpha = 1f,
                                pathFillType = PathFillType.NonZero,
                            )
                        }
                    }.build().also { ShareCache = it }
        }
    private var HomeCache: ImageVector? = null

    /** Mirrors the reference icon geometry for Home. */
    public val Home: ImageVector
        get() {
            HomeCache?.let { return it }
            return         ImageVector.Builder(
                        name = "ElegantIcons.Home",
                        defaultWidth = 24.0f.dp,
                        defaultHeight = 24.0f.dp,
                        viewportWidth = 24.0f,
                        viewportHeight = 24.0f,
                    ).apply {
                        addPath(
                            pathData = listOf(
                                PathNode.MoveTo(2.243f, 10.404f),
                                PathNode.CurveTo(2.125f, 10.848f, 2.125f, 11.328f, 2.125f, 12.286f),
                                PathNode.LineTo(2.125f, 17.205f),
                                PathNode.CurveTo(2.125f, 18.883f, 2.125f, 19.722f, 2.451f, 20.363f),
                                PathNode.CurveTo(2.738f, 20.927f, 3.196f, 21.386f, 3.761f, 21.673f),
                                PathNode.CurveTo(4.402f, 22.0f, 5.241f, 22.0f, 6.919f, 22.0f),
                                PathNode.LineTo(7.164f, 22.0f),
                                PathNode.CurveTo(7.836f, 22.0f, 8.171f, 22.0f, 8.428f, 21.869f),
                                PathNode.CurveTo(8.653f, 21.754f, 8.836f, 21.571f, 8.951f, 21.346f),
                                PathNode.CurveTo(9.082f, 21.089f, 9.082f, 20.754f, 9.082f, 20.082f),
                                PathNode.LineTo(9.082f, 17.57f),
                                PathNode.CurveTo(9.082f, 15.959f, 10.388f, 14.653f, 11.999f, 14.653f),
                                PathNode.CurveTo(13.609f, 14.653f, 14.915f, 15.959f, 14.915f, 17.57f),
                                PathNode.LineTo(14.915f, 20.082f),
                                PathNode.CurveTo(14.915f, 20.754f, 14.915f, 21.089f, 15.047f, 21.346f),
                                PathNode.CurveTo(15.161f, 21.571f, 15.345f, 21.754f, 15.57f, 21.869f),
                                PathNode.CurveTo(15.826f, 22.0f, 16.162f, 22.0f, 16.833f, 22.0f),
                                PathNode.LineTo(17.08f, 22.0f),
                                PathNode.CurveTo(18.758f, 22.0f, 19.597f, 22.0f, 20.238f, 21.673f),
                                PathNode.CurveTo(20.802f, 21.386f, 21.261f, 20.927f, 21.548f, 20.363f),
                                PathNode.CurveTo(21.875f, 19.722f, 21.875f, 18.883f, 21.875f, 17.205f),
                                PathNode.LineTo(21.875f, 12.286f),
                                PathNode.CurveTo(21.875f, 11.327f, 21.875f, 10.848f, 21.756f, 10.404f),
                                PathNode.CurveTo(21.65f, 10.01f, 21.476f, 9.638f, 21.243f, 9.303f),
                                PathNode.CurveTo(20.979f, 8.926f, 20.611f, 8.619f, 19.877f, 8.002f),
                                PathNode.LineTo(14.055f, 3.114f),
                                PathNode.CurveTo(13.324f, 2.501f, 12.959f, 2.194f, 12.55f, 2.078f),
                                PathNode.CurveTo(12.19f, 1.974f, 11.808f, 1.974f, 11.449f, 2.078f),
                                PathNode.CurveTo(11.04f, 2.194f, 10.675f, 2.501f, 9.944f, 3.114f),
                                PathNode.LineTo(4.122f, 8.002f),
                                PathNode.CurveTo(3.387f, 8.619f, 3.02f, 8.926f, 2.756f, 9.303f),
                                PathNode.CurveTo(2.523f, 9.638f, 2.349f, 10.01f, 2.243f, 10.404f),
                                PathNode.Close,
                            ),
                            fill = SolidColor(Color.Black),
                            fillAlpha = 1f,
                            pathFillType = PathFillType.NonZero,
                        )
                    }.build().also { HomeCache = it }
        }
    private var SettingsCache: ImageVector? = null

    /** Mirrors the reference icon geometry for Settings. */
    public val Settings: ImageVector
        get() {
            SettingsCache?.let { return it }
            return         ImageVector.Builder(
                        name = "ElegantIcons.Settings",
                        defaultWidth = 24.0f.dp,
                        defaultHeight = 24.0f.dp,
                        viewportWidth = 1239.6f,
                        viewportHeight = 1239.6f,
                    ).apply {
                        group(scaleY = -1.0f, translationY = 1239.6f) {
                            addPath(
                                pathData = listOf(
                                    PathNode.MoveTo(643.8f, 108.3f),
                                    PathNode.QuadTo(653.8f, 110.3f, 666.8f, 116.3f),
                                    PathNode.QuadTo(679.8f, 122.3f, 694.8f, 131.3f),
                                    PathNode.LineTo(712.8f, 141.3f),
                                    PathNode.LineTo(993.8f, 299.3f),
                                    PathNode.QuadTo(1023.8f, 316.3f, 1037.3f, 324.8f),
                                    PathNode.QuadTo(1050.8f, 333.3f, 1059.8f, 343.3f),
                                    PathNode.QuadTo(1077.8f, 362.3f, 1084.8f, 386.3f),
                                    PathNode.QuadTo(1088.8f, 398.3f, 1089.8f, 418.3f),
                                    PathNode.QuadTo(1090.8f, 438.3f, 1090.8f, 465.3f),
                                    PathNode.VerticalTo(774.3f),
                                    PathNode.QuadTo(1090.8f, 801.3f, 1089.8f, 820.8f),
                                    PathNode.QuadTo(1088.8f, 840.3f, 1084.8f, 852.3f),
                                    PathNode.QuadTo(1077.8f, 875.3f, 1059.8f, 896.3f),
                                    PathNode.QuadTo(1051.8f, 905.3f, 1038.3f, 913.8f),
                                    PathNode.QuadTo(1024.8f, 922.3f, 1005.8f, 932.3f),
                                    PathNode.LineTo(993.8f, 939.3f),
                                    PathNode.LineTo(712.8f, 1098.3f),
                                    PathNode.QuadTo(690.8f, 1111.3f, 673.3f, 1119.8f),
                                    PathNode.QuadTo(655.8f, 1128.3f, 643.8f, 1130.3f),
                                    PathNode.QuadTo(619.8f, 1136.3f, 595.8f, 1130.3f),
                                    PathNode.QuadTo(583.8f, 1128.3f, 566.3f, 1119.8f),
                                    PathNode.QuadTo(548.8f, 1111.3f, 526.8f, 1098.3f),
                                    PathNode.LineTo(245.8f, 939.3f),
                                    PathNode.QuadTo(238.8f, 935.3f, 230.8f, 931.3f),
                                    PathNode.QuadTo(213.8f, 921.3f, 200.3f, 912.8f),
                                    PathNode.QuadTo(186.8f, 904.3f, 179.8f, 896.3f),
                                    PathNode.QuadTo(161.8f, 875.3f, 154.8f, 852.3f),
                                    PathNode.QuadTo(150.8f, 840.3f, 149.8f, 820.8f),
                                    PathNode.QuadTo(148.8f, 801.3f, 148.8f, 774.3f),
                                    PathNode.VerticalTo(465.3f),
                                    PathNode.QuadTo(148.8f, 438.3f, 149.8f, 418.3f),
                                    PathNode.QuadTo(150.8f, 398.3f, 154.8f, 386.3f),
                                    PathNode.QuadTo(161.8f, 362.3f, 179.8f, 343.3f),
                                    PathNode.QuadTo(187.8f, 334.3f, 201.8f, 325.3f),
                                    PathNode.QuadTo(215.8f, 316.3f, 234.8f, 305.3f),
                                    PathNode.LineTo(245.8f, 299.3f),
                                    PathNode.LineTo(526.8f, 141.3f),
                                    PathNode.LineTo(544.8f, 131.3f),
                                    PathNode.QuadTo(559.8f, 122.3f, 572.8f, 116.3f),
                                    PathNode.QuadTo(585.8f, 110.3f, 595.8f, 108.3f),
                                    PathNode.QuadTo(619.8f, 103.3f, 643.8f, 108.3f),
                                    PathNode.Close,
                                    PathNode.MoveTo(234.8f, 438.3f),
                                    PathNode.VerticalTo(807.3f),
                                    PathNode.QuadTo(234.8f, 821.3f, 241.3f, 831.8f),
                                    PathNode.QuadTo(247.8f, 842.3f, 262.8f, 851.3f),
                                    PathNode.LineTo(587.8f, 1034.3f),
                                    PathNode.QuadTo(606.8f, 1045.3f, 618.3f, 1045.8f),
                                    PathNode.QuadTo(629.8f, 1046.3f, 646.8f, 1037.3f),
                                    PathNode.LineTo(966.8f, 857.3f),
                                    PathNode.QuadTo(991.8f, 843.3f, 998.8f, 831.3f),
                                    PathNode.QuadTo(1005.8f, 819.3f, 1005.8f, 790.3f),
                                    PathNode.VerticalTo(441.3f),
                                    PathNode.QuadTo(1005.8f, 422.3f, 1000.8f, 410.8f),
                                    PathNode.QuadTo(995.8f, 399.3f, 981.8f, 391.3f),
                                    PathNode.LineTo(651.8f, 204.3f),
                                    PathNode.QuadTo(632.8f, 193.3f, 620.3f, 193.3f),
                                    PathNode.QuadTo(607.8f, 193.3f, 587.8f, 204.3f),
                                    PathNode.LineTo(265.8f, 386.3f),
                                    PathNode.QuadTo(247.8f, 396.3f, 241.3f, 406.8f),
                                    PathNode.QuadTo(234.8f, 417.3f, 234.8f, 438.3f),
                                    PathNode.Close,
                                    PathNode.MoveTo(819.8f, 619.3f),
                                    PathNode.QuadTo(819.8f, 674.3f, 792.8f, 719.8f),
                                    PathNode.QuadTo(765.8f, 765.3f, 720.3f, 792.3f),
                                    PathNode.QuadTo(674.8f, 819.3f, 619.8f, 819.3f),
                                    PathNode.QuadTo(565.8f, 819.3f, 519.8f, 792.3f),
                                    PathNode.QuadTo(473.8f, 765.3f, 446.8f, 719.8f),
                                    PathNode.QuadTo(419.8f, 674.3f, 419.8f, 619.3f),
                                    PathNode.QuadTo(419.8f, 565.3f, 446.8f, 519.3f),
                                    PathNode.QuadTo(473.8f, 473.3f, 519.8f, 446.3f),
                                    PathNode.QuadTo(565.8f, 419.3f, 619.8f, 419.3f),
                                    PathNode.QuadTo(674.8f, 419.3f, 720.3f, 446.3f),
                                    PathNode.QuadTo(765.8f, 473.3f, 792.8f, 519.3f),
                                    PathNode.QuadTo(819.8f, 565.3f, 819.8f, 619.3f),
                                    PathNode.Close,
                                    PathNode.MoveTo(507.8f, 619.3f),
                                    PathNode.QuadTo(507.8f, 666.3f, 540.8f, 698.8f),
                                    PathNode.QuadTo(573.8f, 731.3f, 619.8f, 731.3f),
                                    PathNode.QuadTo(666.8f, 731.3f, 699.3f, 698.8f),
                                    PathNode.QuadTo(731.8f, 666.3f, 731.8f, 619.3f),
                                    PathNode.QuadTo(731.8f, 573.3f, 699.3f, 540.3f),
                                    PathNode.QuadTo(666.8f, 507.3f, 619.8f, 507.3f),
                                    PathNode.QuadTo(573.8f, 507.3f, 540.8f, 540.3f),
                                    PathNode.QuadTo(507.8f, 573.3f, 507.8f, 619.3f),
                                    PathNode.Close,
                                ),
                                fill = SolidColor(Color.Black),
                                fillAlpha = 1f,
                                pathFillType = PathFillType.NonZero,
                            )
                        }
                    }.build().also { SettingsCache = it }
        }
    private var ChevronLeftCache: ImageVector? = null

    /** Mirrors the reference icon geometry for ChevronLeft. */
    public val ChevronLeft: ImageVector
        get() {
            ChevronLeftCache?.let { return it }
            return         ImageVector.Builder(
                        name = "ElegantIcons.ChevronLeft",
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
                    }.build().also { ChevronLeftCache = it }
        }
    private var ChevronRightCache: ImageVector? = null

    /** Mirrors the reference icon geometry for ChevronRight. */
    public val ChevronRight: ImageVector
        get() {
            ChevronRightCache?.let { return it }
            return         ImageVector.Builder(
                        name = "ElegantIcons.ChevronRight",
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
                    }.build().also { ChevronRightCache = it }
        }
    private var RefreshCache: ImageVector? = null

    /** Mirrors the reference icon geometry for Refresh. */
    public val Refresh: ImageVector
        get() {
            RefreshCache?.let { return it }
            return         ImageVector.Builder(
                        name = "ElegantIcons.Refresh",
                        defaultWidth = 24.0f.dp,
                        defaultHeight = 24.0f.dp,
                        viewportWidth = 1225.2f,
                        viewportHeight = 1225.2f,
                    ).apply {
                        group(scaleY = -1.0f, translationY = 1225.2f) {
                            addPath(
                                pathData = listOf(
                                    PathNode.MoveTo(1025.1f, 245.1f),
                                    PathNode.LineTo(1004.1f, 272.1f),
                                    PathNode.QuadTo(998.1f, 280.1f, 998.1f, 287.6f),
                                    PathNode.QuadTo(998.1f, 295.1f, 1005.1f, 304.1f),
                                    PathNode.QuadTo(1066.1f, 385.1f, 1092.6f, 481.1f),
                                    PathNode.QuadTo(1119.1f, 577.1f, 1107.6f, 675.6f),
                                    PathNode.QuadTo(1096.1f, 774.1f, 1044.1f, 862.1f),
                                    PathNode.QuadTo(988.1f, 958.1f, 899.1f, 1020.6f),
                                    PathNode.QuadTo(810.1f, 1083.1f, 702.6f, 1103.1f),
                                    PathNode.QuadTo(595.1f, 1123.1f, 485.1f, 1095.1f),
                                    PathNode.QuadTo(471.1f, 1091.1f, 466.1f, 1079.6f),
                                    PathNode.QuadTo(461.1f, 1068.1f, 467.1f, 1057.1f),
                                    PathNode.LineTo(483.1f, 1030.1f),
                                    PathNode.QuadTo(489.1f, 1018.1f, 496.1f, 1015.6f),
                                    PathNode.QuadTo(503.1f, 1013.1f, 515.1f, 1015.1f),
                                    PathNode.QuadTo(605.1f, 1035.1f, 692.6f, 1017.6f),
                                    PathNode.QuadTo(780.1f, 1000.1f, 852.6f, 949.1f),
                                    PathNode.QuadTo(925.1f, 898.1f, 970.1f, 819.1f),
                                    PathNode.QuadTo(1012.1f, 747.1f, 1022.6f, 666.6f),
                                    PathNode.QuadTo(1033.1f, 586.1f, 1012.6f, 508.6f),
                                    PathNode.QuadTo(992.1f, 431.1f, 945.1f, 367.1f),
                                    PathNode.QuadTo(942.1f, 363.1f, 937.6f, 363.1f),
                                    PathNode.QuadTo(933.1f, 363.1f, 929.1f, 368.1f),
                                    PathNode.LineTo(913.1f, 388.1f),
                                    PathNode.QuadTo(907.1f, 395.1f, 898.1f, 397.1f),
                                    PathNode.QuadTo(889.1f, 399.1f, 881.1f, 394.1f),
                                    PathNode.QuadTo(873.1f, 389.1f, 869.1f, 375.1f),
                                    PathNode.LineTo(831.1f, 232.1f),
                                    PathNode.QuadTo(828.1f, 220.1f, 834.6f, 211.1f),
                                    PathNode.QuadTo(841.1f, 202.1f, 854.1f, 202.1f),
                                    PathNode.LineTo(1008.1f, 206.1f),
                                    PathNode.QuadTo(1019.1f, 206.1f, 1025.1f, 212.6f),
                                    PathNode.QuadTo(1031.1f, 219.1f, 1031.1f, 228.6f),
                                    PathNode.QuadTo(1031.1f, 238.1f, 1025.1f, 245.1f),
                                    PathNode.Close,
                                    PathNode.MoveTo(744.1f, 166.1f),
                                    PathNode.LineTo(728.1f, 194.1f),
                                    PathNode.QuadTo(720.1f, 212.1f, 693.1f, 207.1f),
                                    PathNode.QuadTo(606.1f, 191.1f, 522.1f, 210.1f),
                                    PathNode.QuadTo(438.1f, 229.1f, 368.6f, 279.6f),
                                    PathNode.QuadTo(299.1f, 330.1f, 255.1f, 406.1f),
                                    PathNode.QuadTo(214.1f, 477.1f, 203.1f, 556.6f),
                                    PathNode.QuadTo(192.1f, 636.1f, 211.6f, 713.1f),
                                    PathNode.QuadTo(231.1f, 790.1f, 277.1f, 854.1f),
                                    PathNode.QuadTo(281.1f, 859.1f, 287.6f, 859.1f),
                                    PathNode.QuadTo(294.1f, 859.1f, 298.1f, 854.1f),
                                    PathNode.LineTo(317.1f, 831.1f),
                                    PathNode.QuadTo(327.1f, 820.1f, 340.1f, 823.1f),
                                    PathNode.QuadTo(353.1f, 826.1f, 357.1f, 840.1f),
                                    PathNode.LineTo(396.1f, 982.1f),
                                    PathNode.QuadTo(401.1f, 999.1f, 395.1f, 1009.6f),
                                    PathNode.QuadTo(389.1f, 1020.1f, 374.1f, 1019.1f),
                                    PathNode.LineTo(219.1f, 1015.1f),
                                    PathNode.QuadTo(209.1f, 1015.1f, 203.6f, 1008.6f),
                                    PathNode.QuadTo(198.1f, 1002.1f, 197.6f, 993.6f),
                                    PathNode.QuadTo(197.1f, 985.1f, 201.1f, 979.1f),
                                    PathNode.LineTo(219.1f, 954.1f),
                                    PathNode.QuadTo(226.1f, 945.1f, 226.6f, 937.6f),
                                    PathNode.QuadTo(227.1f, 930.1f, 222.1f, 923.1f),
                                    PathNode.QuadTo(160.1f, 843.1f, 133.1f, 747.1f),
                                    PathNode.QuadTo(106.1f, 651.1f, 117.6f, 551.6f),
                                    PathNode.QuadTo(129.1f, 452.1f, 181.1f, 363.1f),
                                    PathNode.QuadTo(235.1f, 269.1f, 322.1f, 207.6f),
                                    PathNode.QuadTo(409.1f, 146.1f, 513.6f, 124.1f),
                                    PathNode.QuadTo(618.1f, 102.1f, 724.1f, 126.1f),
                                    PathNode.QuadTo(740.1f, 130.1f, 745.6f, 142.6f),
                                    PathNode.QuadTo(751.1f, 155.1f, 744.1f, 166.1f),
                                    PathNode.Close,
                                ),
                                fill = SolidColor(Color.Black),
                                fillAlpha = 1f,
                                pathFillType = PathFillType.NonZero,
                            )
                        }
                    }.build().also { RefreshCache = it }
        }
    private var DownloadCache: ImageVector? = null

    /** Mirrors the reference icon geometry for Download. */
    public val Download: ImageVector
        get() {
            DownloadCache?.let { return it }
            return         ImageVector.Builder(
                        name = "ElegantIcons.Download",
                        defaultWidth = 24.0f.dp,
                        defaultHeight = 24.0f.dp,
                        viewportWidth = 1274.4f,
                        viewportHeight = 1274.4f,
                    ).apply {
                        group(scaleY = -1.0f, translationY = 1274.4f) {
                            addPath(
                                pathData = listOf(
                                    PathNode.MoveTo(983.2f, 127.2f),
                                    PathNode.QuadTo(1010.2f, 140.2f, 1031.7f, 161.7f),
                                    PathNode.QuadTo(1053.2f, 183.2f, 1066.2f, 210.2f),
                                    PathNode.QuadTo(1080.2f, 238.2f, 1083.7f, 277.7f),
                                    PathNode.QuadTo(1087.2f, 317.2f, 1087.2f, 410.2f),
                                    PathNode.VerticalTo(677.2f),
                                    PathNode.QuadTo(1087.2f, 752.2f, 1085.2f, 784.7f),
                                    PathNode.QuadTo(1083.2f, 817.2f, 1073.2f, 840.2f),
                                    PathNode.QuadTo(1059.2f, 876.2f, 1031.7f, 903.7f),
                                    PathNode.QuadTo(1004.2f, 931.2f, 968.2f, 945.2f),
                                    PathNode.QuadTo(945.2f, 955.2f, 911.7f, 957.2f),
                                    PathNode.QuadTo(878.2f, 959.2f, 795.2f, 959.2f),
                                    PathNode.QuadTo(782.2f, 959.2f, 773.7f, 952.7f),
                                    PathNode.QuadTo(765.2f, 946.2f, 765.2f, 930.2f),
                                    PathNode.VerticalTo(906.2f),
                                    PathNode.QuadTo(765.2f, 887.2f, 774.2f, 880.2f),
                                    PathNode.QuadTo(783.2f, 873.2f, 799.2f, 873.2f),
                                    PathNode.HorizontalTo(827.2f),
                                    PathNode.QuadTo(880.2f, 873.2f, 902.7f, 871.7f),
                                    PathNode.QuadTo(925.2f, 870.2f, 942.2f, 862.2f),
                                    PathNode.QuadTo(957.2f, 854.2f, 969.7f, 841.7f),
                                    PathNode.QuadTo(982.2f, 829.2f, 989.2f, 814.2f),
                                    PathNode.QuadTo(997.2f, 797.2f, 999.2f, 774.7f),
                                    PathNode.QuadTo(1001.2f, 752.2f, 1001.2f, 699.2f),
                                    PathNode.VerticalTo(367.2f),
                                    PathNode.QuadTo(1001.2f, 314.2f, 999.2f, 291.2f),
                                    PathNode.QuadTo(997.2f, 268.2f, 989.2f, 252.2f),
                                    PathNode.QuadTo(982.2f, 236.2f, 969.7f, 224.2f),
                                    PathNode.QuadTo(957.2f, 212.2f, 942.2f, 204.2f),
                                    PathNode.QuadTo(925.2f, 196.2f, 902.7f, 194.2f),
                                    PathNode.QuadTo(880.2f, 192.2f, 827.2f, 192.2f),
                                    PathNode.HorizontalTo(447.2f),
                                    PathNode.QuadTo(394.2f, 192.2f, 371.2f, 194.2f),
                                    PathNode.QuadTo(348.2f, 196.2f, 332.2f, 204.2f),
                                    PathNode.QuadTo(316.2f, 211.2f, 304.2f, 223.7f),
                                    PathNode.QuadTo(292.2f, 236.2f, 284.2f, 252.2f),
                                    PathNode.QuadTo(276.2f, 268.2f, 274.2f, 291.2f),
                                    PathNode.QuadTo(272.2f, 314.2f, 272.2f, 367.2f),
                                    PathNode.VerticalTo(699.2f),
                                    PathNode.QuadTo(272.2f, 752.2f, 274.2f, 774.7f),
                                    PathNode.QuadTo(276.2f, 797.2f, 284.2f, 814.2f),
                                    PathNode.QuadTo(299.2f, 845.2f, 332.2f, 862.2f),
                                    PathNode.QuadTo(348.2f, 870.2f, 371.2f, 871.7f),
                                    PathNode.QuadTo(394.2f, 873.2f, 447.2f, 873.2f),
                                    PathNode.HorizontalTo(476.2f),
                                    PathNode.QuadTo(492.2f, 873.2f, 500.2f, 880.7f),
                                    PathNode.QuadTo(508.2f, 888.2f, 508.2f, 907.2f),
                                    PathNode.VerticalTo(930.2f),
                                    PathNode.QuadTo(508.2f, 959.2f, 479.2f, 959.2f),
                                    PathNode.QuadTo(396.2f, 959.2f, 362.7f, 957.2f),
                                    PathNode.QuadTo(329.2f, 955.2f, 306.2f, 946.2f),
                                    PathNode.QuadTo(270.2f, 931.2f, 242.7f, 903.7f),
                                    PathNode.QuadTo(215.2f, 876.2f, 200.2f, 840.2f),
                                    PathNode.QuadTo(191.2f, 817.2f, 189.2f, 784.7f),
                                    PathNode.QuadTo(187.2f, 752.2f, 187.2f, 677.2f),
                                    PathNode.VerticalTo(410.2f),
                                    PathNode.QuadTo(187.2f, 317.2f, 190.2f, 277.7f),
                                    PathNode.QuadTo(193.2f, 238.2f, 207.2f, 210.2f),
                                    PathNode.QuadTo(236.2f, 155.2f, 290.2f, 127.2f),
                                    PathNode.QuadTo(318.2f, 113.2f, 358.2f, 109.7f),
                                    PathNode.QuadTo(398.2f, 106.2f, 491.2f, 106.2f),
                                    PathNode.HorizontalTo(783.2f),
                                    PathNode.QuadTo(876.2f, 106.2f, 915.7f, 109.7f),
                                    PathNode.QuadTo(955.2f, 113.2f, 983.2f, 127.2f),
                                    PathNode.Close,
                                    PathNode.MoveTo(676.2f, 391.2f),
                                    PathNode.LineTo(877.2f, 591.2f),
                                    PathNode.QuadTo(885.2f, 599.2f, 886.2f, 610.7f),
                                    PathNode.QuadTo(887.2f, 622.2f, 877.2f, 632.2f),
                                    PathNode.LineTo(856.2f, 652.2f),
                                    PathNode.QuadTo(845.2f, 662.2f, 834.7f, 660.7f),
                                    PathNode.QuadTo(824.2f, 659.2f, 816.2f, 651.2f),
                                    PathNode.LineTo(680.2f, 515.2f),
                                    PathNode.VerticalTo(1141.2f),
                                    PathNode.QuadTo(680.2f, 1152.2f, 672.2f, 1160.2f),
                                    PathNode.QuadTo(664.2f, 1168.2f, 653.2f, 1168.2f),
                                    PathNode.HorizontalTo(620.2f),
                                    PathNode.QuadTo(609.2f, 1168.2f, 601.7f, 1160.2f),
                                    PathNode.QuadTo(594.2f, 1152.2f, 594.2f, 1141.2f),
                                    PathNode.VerticalTo(515.2f),
                                    PathNode.LineTo(455.2f, 652.2f),
                                    PathNode.QuadTo(447.2f, 660.2f, 437.2f, 660.7f),
                                    PathNode.QuadTo(427.2f, 661.2f, 418.2f, 652.2f),
                                    PathNode.LineTo(396.2f, 630.2f),
                                    PathNode.QuadTo(387.2f, 621.2f, 387.7f, 609.7f),
                                    PathNode.QuadTo(388.2f, 598.2f, 402.2f, 584.2f),
                                    PathNode.LineTo(597.2f, 391.2f),
                                    PathNode.QuadTo(618.2f, 370.2f, 637.2f, 370.7f),
                                    PathNode.QuadTo(656.2f, 371.2f, 676.2f, 391.2f),
                                    PathNode.Close,
                                ),
                                fill = SolidColor(Color.Black),
                                fillAlpha = 1f,
                                pathFillType = PathFillType.NonZero,
                            )
                        }
                    }.build().also { DownloadCache = it }
        }
    private var VolumeUpCache: ImageVector? = null

    /** Mirrors the reference icon geometry for VolumeUp. */
    public val VolumeUp: ImageVector
        get() {
            VolumeUpCache?.let { return it }
            return         ImageVector.Builder(
                        name = "ElegantIcons.VolumeUp",
                        defaultWidth = 24.0f.dp,
                        defaultHeight = 24.0f.dp,
                        viewportWidth = 1192.8f,
                        viewportHeight = 1192.8f,
                    ).apply {
                        group(scaleY = -1.0f, translationY = 1192.8f) {
                            addPath(
                                pathData = listOf(
                                    PathNode.MoveTo(216.4f, 400.9f),
                                    PathNode.QuadTo(201.4f, 408.9f, 191.4f, 424.9f),
                                    PathNode.QuadTo(187.4f, 433.9f, 186.4f, 448.9f),
                                    PathNode.QuadTo(185.4f, 463.9f, 185.4f, 484.9f),
                                    PathNode.VerticalTo(705.9f),
                                    PathNode.QuadTo(185.4f, 726.9f, 186.4f, 742.4f),
                                    PathNode.QuadTo(187.4f, 757.9f, 191.4f, 765.9f),
                                    PathNode.QuadTo(198.4f, 780.9f, 216.4f, 790.9f),
                                    PathNode.QuadTo(224.4f, 794.9f, 239.9f, 795.9f),
                                    PathNode.QuadTo(255.4f, 796.9f, 276.4f, 796.9f),
                                    PathNode.HorizontalTo(410.4f),
                                    PathNode.QuadTo(430.4f, 796.9f, 444.9f, 797.4f),
                                    PathNode.QuadTo(459.4f, 797.9f, 469.4f, 799.9f),
                                    PathNode.QuadTo(489.4f, 804.9f, 506.4f, 812.9f),
                                    PathNode.QuadTo(518.4f, 818.9f, 554.4f, 847.9f),
                                    PathNode.LineTo(699.4f, 964.9f),
                                    PathNode.VerticalTo(226.9f),
                                    PathNode.LineTo(555.4f, 343.9f),
                                    PathNode.QuadTo(545.4f, 352.9f, 539.4f, 355.9f),
                                    PathNode.QuadTo(517.4f, 373.9f, 508.4f, 378.9f),
                                    PathNode.QuadTo(490.4f, 387.9f, 471.4f, 390.9f),
                                    PathNode.QuadTo(457.4f, 393.9f, 412.4f, 393.9f),
                                    PathNode.HorizontalTo(276.4f),
                                    PathNode.QuadTo(248.4f, 393.9f, 236.4f, 394.9f),
                                    PathNode.QuadTo(224.4f, 395.9f, 216.4f, 400.9f),
                                    PathNode.Close,
                                    PathNode.MoveTo(494.4f, 282.9f),
                                    PathNode.LineTo(661.4f, 147.9f),
                                    PathNode.LineTo(674.4f, 137.9f),
                                    PathNode.QuadTo(700.4f, 116.9f, 713.9f, 108.4f),
                                    PathNode.QuadTo(727.4f, 99.9f, 737.4f, 99.9f),
                                    PathNode.QuadTo(748.4f, 99.9f, 757.9f, 104.9f),
                                    PathNode.QuadTo(767.4f, 109.9f, 774.4f, 117.9f),
                                    PathNode.QuadTo(782.4f, 126.9f, 783.9f, 144.4f),
                                    PathNode.QuadTo(785.4f, 161.9f, 785.4f, 206.9f),
                                    PathNode.VerticalTo(984.9f),
                                    PathNode.QuadTo(785.4f, 1029.9f, 783.9f, 1047.9f),
                                    PathNode.QuadTo(782.4f, 1065.9f, 774.4f, 1074.9f),
                                    PathNode.QuadTo(767.4f, 1083.9f, 757.9f, 1088.4f),
                                    PathNode.QuadTo(748.4f, 1092.9f, 737.4f, 1091.9f),
                                    PathNode.QuadTo(725.4f, 1091.9f, 711.4f, 1082.4f),
                                    PathNode.QuadTo(697.4f, 1072.9f, 661.4f, 1043.9f),
                                    PathNode.LineTo(483.4f, 900.9f),
                                    PathNode.QuadTo(470.4f, 890.9f, 456.9f, 886.9f),
                                    PathNode.QuadTo(443.4f, 882.9f, 420.4f, 882.9f),
                                    PathNode.HorizontalTo(327.4f),
                                    PathNode.QuadTo(257.4f, 882.9f, 227.9f, 880.4f),
                                    PathNode.QuadTo(198.4f, 877.9f, 177.4f, 866.9f),
                                    PathNode.QuadTo(135.4f, 844.9f, 115.4f, 804.9f),
                                    PathNode.QuadTo(104.4f, 783.9f, 101.9f, 753.9f),
                                    PathNode.QuadTo(99.4f, 723.9f, 99.4f, 653.9f),
                                    PathNode.LineTo(100.4f, 536.9f),
                                    PathNode.QuadTo(100.4f, 466.9f, 102.9f, 437.4f),
                                    PathNode.QuadTo(105.4f, 407.9f, 115.4f, 386.9f),
                                    PathNode.QuadTo(137.4f, 345.9f, 178.4f, 323.9f),
                                    PathNode.QuadTo(199.4f, 313.9f, 228.9f, 311.4f),
                                    PathNode.QuadTo(258.4f, 308.9f, 327.4f, 308.9f),
                                    PathNode.HorizontalTo(426.4f),
                                    PathNode.QuadTo(446.4f, 308.9f, 460.9f, 303.4f),
                                    PathNode.QuadTo(475.4f, 297.9f, 494.4f, 282.9f),
                                    PathNode.Close,
                                    PathNode.MoveTo(903.4f, 824.9f),
                                    PathNode.QuadTo(955.4f, 776.9f, 981.4f, 718.9f),
                                    PathNode.QuadTo(1007.4f, 660.9f, 1007.4f, 595.9f),
                                    PathNode.QuadTo(1007.4f, 530.9f, 981.4f, 471.9f),
                                    PathNode.QuadTo(955.4f, 412.9f, 898.4f, 361.9f),
                                    PathNode.QuadTo(890.4f, 353.9f, 889.9f, 342.4f),
                                    PathNode.QuadTo(889.4f, 330.9f, 897.4f, 322.9f),
                                    PathNode.LineTo(916.4f, 301.9f),
                                    PathNode.QuadTo(925.4f, 292.9f, 937.4f, 293.4f),
                                    PathNode.QuadTo(949.4f, 293.9f, 958.4f, 301.9f),
                                    PathNode.QuadTo(1023.4f, 358.9f, 1058.4f, 434.4f),
                                    PathNode.QuadTo(1093.4f, 509.9f, 1093.4f, 595.9f),
                                    PathNode.QuadTo(1093.4f, 681.9f, 1057.9f, 758.4f),
                                    PathNode.QuadTo(1022.4f, 834.9f, 958.4f, 889.9f),
                                    PathNode.QuadTo(947.4f, 898.9f, 935.4f, 898.4f),
                                    PathNode.QuadTo(923.4f, 897.9f, 914.4f, 887.9f),
                                    PathNode.LineTo(899.4f, 870.9f),
                                    PathNode.QuadTo(888.4f, 857.9f, 889.4f, 847.4f),
                                    PathNode.QuadTo(890.4f, 836.9f, 903.4f, 824.9f),
                                    PathNode.Close,
                                ),
                                fill = SolidColor(Color.Black),
                                fillAlpha = 1f,
                                pathFillType = PathFillType.NonZero,
                            )
                        }
                    }.build().also { VolumeUpCache = it }
        }
    private var VolumeOffCache: ImageVector? = null

    /** Mirrors the reference icon geometry for VolumeOff. */
    public val VolumeOff: ImageVector
        get() {
            VolumeOffCache?.let { return it }
            return         ImageVector.Builder(
                        name = "ElegantIcons.VolumeOff",
                        defaultWidth = 24.0f.dp,
                        defaultHeight = 24.0f.dp,
                        viewportWidth = 1191.6f,
                        viewportHeight = 1191.6f,
                    ).apply {
                        group(scaleY = -1.0f, translationY = 1191.6f) {
                            addPath(
                                pathData = listOf(
                                    PathNode.MoveTo(1030.8f, 278.3f),
                                    PathNode.LineTo(258.8f, 1051.3f),
                                    PathNode.QuadTo(246.8f, 1063.3f, 236.3f, 1063.8f),
                                    PathNode.QuadTo(225.8f, 1064.3f, 215.8f, 1055.3f),
                                    PathNode.LineTo(195.8f, 1036.3f),
                                    PathNode.QuadTo(185.8f, 1027.3f, 186.3f, 1015.8f),
                                    PathNode.QuadTo(186.8f, 1004.3f, 197.8f, 993.3f),
                                    PathNode.LineTo(973.8f, 217.3f),
                                    PathNode.QuadTo(982.8f, 208.3f, 993.3f, 206.8f),
                                    PathNode.QuadTo(1003.8f, 205.3f, 1014.8f, 217.3f),
                                    PathNode.LineTo(1033.8f, 236.3f),
                                    PathNode.QuadTo(1043.8f, 246.3f, 1042.8f, 256.8f),
                                    PathNode.QuadTo(1041.8f, 267.3f, 1030.8f, 278.3f),
                                    PathNode.Close,
                                    PathNode.MoveTo(841.8f, 117.3f),
                                    PathNode.QuadTo(849.8f, 127.3f, 851.3f, 144.8f),
                                    PathNode.QuadTo(852.8f, 162.3f, 852.8f, 206.3f),
                                    PathNode.VerticalTo(220.3f),
                                    PathNode.LineTo(774.8f, 299.3f),
                                    PathNode.QuadTo(771.8f, 302.3f, 769.8f, 300.8f),
                                    PathNode.QuadTo(767.8f, 299.3f, 767.8f, 295.3f),
                                    PathNode.VerticalTo(227.3f),
                                    PathNode.LineTo(623.8f, 343.3f),
                                    PathNode.LineTo(607.8f, 356.3f),
                                    PathNode.QuadTo(589.8f, 372.3f, 577.8f, 378.3f),
                                    PathNode.QuadTo(561.8f, 387.3f, 539.8f, 391.3f),
                                    PathNode.QuadTo(518.8f, 394.3f, 479.8f, 394.3f),
                                    PathNode.HorizontalTo(324.8f),
                                    PathNode.QuadTo(296.8f, 394.3f, 285.3f, 395.3f),
                                    PathNode.QuadTo(273.8f, 396.3f, 265.8f, 401.3f),
                                    PathNode.QuadTo(247.8f, 409.3f, 240.8f, 425.3f),
                                    PathNode.QuadTo(235.8f, 434.3f, 234.8f, 445.8f),
                                    PathNode.QuadTo(233.8f, 457.3f, 233.8f, 485.3f),
                                    PathNode.VerticalTo(705.3f),
                                    PathNode.QuadTo(233.8f, 727.3f, 234.8f, 742.3f),
                                    PathNode.QuadTo(235.8f, 757.3f, 238.8f, 766.3f),
                                    PathNode.QuadTo(247.8f, 782.3f, 265.8f, 790.3f),
                                    PathNode.QuadTo(273.8f, 793.3f, 281.8f, 796.3f),
                                    PathNode.LineTo(215.8f, 861.3f),
                                    PathNode.QuadTo(198.8f, 850.3f, 185.8f, 836.3f),
                                    PathNode.QuadTo(172.8f, 822.3f, 163.8f, 804.3f),
                                    PathNode.QuadTo(152.8f, 783.3f, 150.3f, 753.8f),
                                    PathNode.QuadTo(147.8f, 724.3f, 147.8f, 654.3f),
                                    PathNode.VerticalTo(536.3f),
                                    PathNode.QuadTo(147.8f, 467.3f, 150.3f, 437.8f),
                                    PathNode.QuadTo(152.8f, 408.3f, 164.8f, 387.3f),
                                    PathNode.QuadTo(186.8f, 342.3f, 226.8f, 324.3f),
                                    PathNode.QuadTo(247.8f, 313.3f, 277.3f, 310.8f),
                                    PathNode.QuadTo(306.8f, 308.3f, 376.8f, 308.3f),
                                    PathNode.HorizontalTo(494.8f),
                                    PathNode.QuadTo(515.8f, 308.3f, 533.8f, 300.8f),
                                    PathNode.QuadTo(551.8f, 293.3f, 567.8f, 279.3f),
                                    PathNode.LineTo(729.8f, 147.3f),
                                    PathNode.LineTo(745.8f, 135.3f),
                                    PathNode.QuadTo(769.8f, 115.3f, 782.3f, 107.3f),
                                    PathNode.QuadTo(794.8f, 99.3f, 804.8f, 99.3f),
                                    PathNode.QuadTo(815.8f, 99.3f, 825.8f, 104.3f),
                                    PathNode.QuadTo(835.8f, 109.3f, 841.8f, 117.3f),
                                    PathNode.Close,
                                    PathNode.MoveTo(841.8f, 1074.3f),
                                    PathNode.QuadTo(835.8f, 1082.3f, 825.8f, 1087.3f),
                                    PathNode.QuadTo(815.8f, 1092.3f, 804.8f, 1092.3f),
                                    PathNode.QuadTo(794.8f, 1092.3f, 782.3f, 1084.3f),
                                    PathNode.QuadTo(769.8f, 1076.3f, 745.8f, 1056.3f),
                                    PathNode.QuadTo(741.8f, 1054.3f, 737.8f, 1050.8f),
                                    PathNode.QuadTo(733.8f, 1047.3f, 729.8f, 1044.3f),
                                    PathNode.LineTo(534.8f, 889.3f),
                                    PathNode.LineTo(596.8f, 828.3f),
                                    PathNode.LineTo(767.8f, 965.3f),
                                    PathNode.VerticalTo(657.3f),
                                    PathNode.LineTo(852.8f, 571.3f),
                                    PathNode.VerticalTo(985.3f),
                                    PathNode.QuadTo(852.8f, 1029.3f, 851.3f, 1047.8f),
                                    PathNode.QuadTo(849.8f, 1066.3f, 841.8f, 1074.3f),
                                    PathNode.Close,
                                ),
                                fill = SolidColor(Color.Black),
                                fillAlpha = 1f,
                                pathFillType = PathFillType.NonZero,
                            )
                        }
                    }.build().also { VolumeOffCache = it }
        }
    private var FilterCache: ImageVector? = null

    /** Mirrors the reference icon geometry for Filter. */
    public val Filter: ImageVector
        get() {
            FilterCache?.let { return it }
            return         ImageVector.Builder(
                        name = "ElegantIcons.Filter",
                        defaultWidth = 24.0f.dp,
                        defaultHeight = 24.0f.dp,
                        viewportWidth = 1148.4f,
                        viewportHeight = 1148.4f,
                    ).apply {
                        group(scaleY = -1.0f, translationY = 1148.4f) {
                            addPath(
                                pathData = listOf(
                                    PathNode.MoveTo(606.7f, 144.7f),
                                    PathNode.VerticalTo(671.7f),
                                    PathNode.QuadTo(606.7f, 700.7f, 622.7f, 724.7f),
                                    PathNode.LineTo(793.7f, 978.7f),
                                    PathNode.QuadTo(804.7f, 993.7f, 802.2f, 1011.2f),
                                    PathNode.QuadTo(799.7f, 1028.7f, 786.2f, 1040.7f),
                                    PathNode.QuadTo(772.7f, 1052.7f, 754.7f, 1052.7f),
                                    PathNode.HorizontalTo(165.7f),
                                    PathNode.QuadTo(147.7f, 1052.7f, 134.7f, 1040.7f),
                                    PathNode.QuadTo(121.7f, 1028.7f, 119.2f, 1011.7f),
                                    PathNode.QuadTo(116.7f, 994.7f, 126.7f, 978.7f),
                                    PathNode.LineTo(297.7f, 724.7f),
                                    PathNode.QuadTo(314.7f, 701.7f, 314.7f, 671.7f),
                                    PathNode.VerticalTo(298.7f),
                                    PathNode.QuadTo(314.7f, 274.7f, 326.2f, 253.7f),
                                    PathNode.QuadTo(337.7f, 232.7f, 357.7f, 219.7f),
                                    PathNode.LineTo(533.7f, 105.7f),
                                    PathNode.QuadTo(548.7f, 95.7f, 565.7f, 98.2f),
                                    PathNode.QuadTo(582.7f, 100.7f, 594.7f, 113.7f),
                                    PathNode.QuadTo(606.7f, 126.7f, 606.7f, 144.7f),
                                    PathNode.Close,
                                    PathNode.MoveTo(399.7f, 319.7f),
                                    PathNode.VerticalTo(674.7f),
                                    PathNode.QuadTo(399.7f, 726.7f, 370.7f, 770.7f),
                                    PathNode.LineTo(247.7f, 952.7f),
                                    PathNode.QuadTo(244.7f, 957.7f, 247.2f, 962.7f),
                                    PathNode.QuadTo(249.7f, 967.7f, 255.7f, 967.7f),
                                    PathNode.HorizontalTo(665.7f),
                                    PathNode.QuadTo(670.7f, 967.7f, 673.7f, 962.2f),
                                    PathNode.QuadTo(676.7f, 956.7f, 673.7f, 952.7f),
                                    PathNode.LineTo(550.7f, 770.7f),
                                    PathNode.QuadTo(520.7f, 724.7f, 520.7f, 674.7f),
                                    PathNode.VerticalTo(229.7f),
                                    PathNode.QuadTo(520.7f, 215.7f, 506.7f, 224.7f),
                                    PathNode.LineTo(421.7f, 279.7f),
                                    PathNode.QuadTo(411.7f, 286.7f, 405.7f, 297.2f),
                                    PathNode.QuadTo(399.7f, 307.7f, 399.7f, 319.7f),
                                    PathNode.Close,
                                    PathNode.MoveTo(1031.7f, 501.7f),
                                    PathNode.VerticalTo(526.7f),
                                    PathNode.QuadTo(1031.7f, 542.7f, 1023.2f, 549.2f),
                                    PathNode.QuadTo(1014.7f, 555.7f, 997.7f, 555.7f),
                                    PathNode.HorizontalTo(801.7f),
                                    PathNode.QuadTo(783.7f, 555.7f, 775.7f, 549.2f),
                                    PathNode.QuadTo(767.7f, 542.7f, 767.7f, 524.7f),
                                    PathNode.VerticalTo(501.7f),
                                    PathNode.QuadTo(767.7f, 484.7f, 775.7f, 477.7f),
                                    PathNode.QuadTo(783.7f, 470.7f, 801.7f, 470.7f),
                                    PathNode.HorizontalTo(997.7f),
                                    PathNode.QuadTo(1015.7f, 470.7f, 1023.7f, 477.7f),
                                    PathNode.QuadTo(1031.7f, 484.7f, 1031.7f, 501.7f),
                                    PathNode.Close,
                                    PathNode.MoveTo(1031.7f, 283.7f),
                                    PathNode.VerticalTo(308.7f),
                                    PathNode.QuadTo(1031.7f, 324.7f, 1023.2f, 331.2f),
                                    PathNode.QuadTo(1014.7f, 337.7f, 997.7f, 337.7f),
                                    PathNode.HorizontalTo(801.7f),
                                    PathNode.QuadTo(783.7f, 337.7f, 775.7f, 331.2f),
                                    PathNode.QuadTo(767.7f, 324.7f, 767.7f, 306.7f),
                                    PathNode.VerticalTo(283.7f),
                                    PathNode.QuadTo(767.7f, 266.7f, 775.7f, 259.7f),
                                    PathNode.QuadTo(783.7f, 252.7f, 801.7f, 252.7f),
                                    PathNode.HorizontalTo(997.7f),
                                    PathNode.QuadTo(1015.7f, 252.7f, 1023.7f, 259.7f),
                                    PathNode.QuadTo(1031.7f, 266.7f, 1031.7f, 283.7f),
                                    PathNode.Close,
                                    PathNode.MoveTo(1031.7f, 704.7f),
                                    PathNode.VerticalTo(729.7f),
                                    PathNode.QuadTo(1031.7f, 745.7f, 1023.2f, 752.2f),
                                    PathNode.QuadTo(1014.7f, 758.7f, 997.7f, 758.7f),
                                    PathNode.HorizontalTo(801.7f),
                                    PathNode.QuadTo(783.7f, 758.7f, 775.7f, 752.2f),
                                    PathNode.QuadTo(767.7f, 745.7f, 767.7f, 727.7f),
                                    PathNode.VerticalTo(704.7f),
                                    PathNode.QuadTo(767.7f, 687.7f, 775.7f, 680.7f),
                                    PathNode.QuadTo(783.7f, 673.7f, 801.7f, 673.7f),
                                    PathNode.HorizontalTo(997.7f),
                                    PathNode.QuadTo(1015.7f, 673.7f, 1023.7f, 680.7f),
                                    PathNode.QuadTo(1031.7f, 687.7f, 1031.7f, 704.7f),
                                    PathNode.Close,
                                ),
                                fill = SolidColor(Color.Black),
                                fillAlpha = 1f,
                                pathFillType = PathFillType.NonZero,
                            )
                        }
                    }.build().also { FilterCache = it }
        }
    private var SendCache: ImageVector? = null

    /** Mirrors the reference icon geometry for Send. */
    public val Send: ImageVector
        get() {
            SendCache?.let { return it }
            return         ImageVector.Builder(
                        name = "ElegantIcons.Send",
                        defaultWidth = 24.0f.dp,
                        defaultHeight = 24.0f.dp,
                        viewportWidth = 1148.4f,
                        viewportHeight = 1148.4f,
                    ).apply {
                        group(scaleY = -1.0f, translationY = 1148.4f) {
                            addPath(
                                pathData = listOf(
                                    PathNode.MoveTo(981.7f, 196.7f),
                                    PathNode.LineTo(1048.7f, 930.7f),
                                    PathNode.QuadTo(1052.7f, 969.7f, 1030.7f, 1000.7f),
                                    PathNode.QuadTo(1008.7f, 1031.7f, 971.7f, 1041.7f),
                                    PathNode.QuadTo(934.7f, 1051.7f, 899.7f, 1034.7f),
                                    PathNode.LineTo(160.7f, 675.7f),
                                    PathNode.QuadTo(123.7f, 657.7f, 109.7f, 621.2f),
                                    PathNode.QuadTo(95.7f, 584.7f, 108.7f, 547.2f),
                                    PathNode.QuadTo(121.7f, 509.7f, 157.7f, 489.7f),
                                    PathNode.LineTo(358.7f, 381.7f),
                                    PathNode.QuadTo(367.7f, 376.7f, 372.7f, 367.7f),
                                    PathNode.QuadTo(377.7f, 358.7f, 377.7f, 347.7f),
                                    PathNode.VerticalTo(178.7f),
                                    PathNode.QuadTo(377.7f, 148.7f, 397.7f, 127.7f),
                                    PathNode.QuadTo(417.7f, 106.7f, 447.2f, 103.7f),
                                    PathNode.QuadTo(476.7f, 100.7f, 500.7f, 118.7f),
                                    PathNode.LineTo(610.7f, 203.7f),
                                    PathNode.QuadTo(618.7f, 210.7f, 629.7f, 211.2f),
                                    PathNode.QuadTo(640.7f, 211.7f, 650.7f, 206.7f),
                                    PathNode.LineTo(828.7f, 113.7f),
                                    PathNode.QuadTo(860.7f, 96.7f, 895.2f, 103.2f),
                                    PathNode.QuadTo(929.7f, 109.7f, 954.2f, 135.2f),
                                    PathNode.QuadTo(978.7f, 160.7f, 981.7f, 196.7f),
                                    PathNode.Close,
                                    PathNode.MoveTo(572.7f, 282.7f),
                                    PathNode.LineTo(478.7f, 205.7f),
                                    PathNode.QuadTo(474.7f, 201.7f, 469.2f, 204.7f),
                                    PathNode.QuadTo(463.7f, 207.7f, 463.7f, 213.7f),
                                    PathNode.VerticalTo(362.7f),
                                    PathNode.QuadTo(463.7f, 388.7f, 449.7f, 411.7f),
                                    PathNode.QuadTo(435.7f, 434.7f, 412.7f, 446.7f),
                                    PathNode.LineTo(198.7f, 560.7f),
                                    PathNode.QuadTo(189.7f, 564.7f, 186.7f, 573.2f),
                                    PathNode.QuadTo(183.7f, 581.7f, 187.2f, 590.2f),
                                    PathNode.QuadTo(190.7f, 598.7f, 198.7f, 602.7f),
                                    PathNode.LineTo(888.7f, 931.7f),
                                    PathNode.LineTo(530.7f, 521.7f),
                                    PathNode.QuadTo(523.7f, 513.7f, 524.7f, 502.2f),
                                    PathNode.QuadTo(525.7f, 490.7f, 534.7f, 483.7f),
                                    PathNode.LineTo(560.7f, 463.7f),
                                    PathNode.QuadTo(568.7f, 457.7f, 579.2f, 458.7f),
                                    PathNode.QuadTo(589.7f, 459.7f, 596.7f, 467.7f),
                                    PathNode.LineTo(958.7f, 881.7f),
                                    PathNode.LineTo(896.7f, 215.7f),
                                    PathNode.QuadTo(895.7f, 202.7f, 884.7f, 196.7f),
                                    PathNode.QuadTo(873.7f, 190.7f, 862.7f, 196.7f),
                                    PathNode.LineTo(676.7f, 292.7f),
                                    PathNode.QuadTo(651.7f, 305.7f, 623.7f, 303.2f),
                                    PathNode.QuadTo(595.7f, 300.7f, 572.7f, 282.7f),
                                    PathNode.Close,
                                ),
                                fill = SolidColor(Color.Black),
                                fillAlpha = 1f,
                                pathFillType = PathFillType.NonZero,
                            )
                        }
                    }.build().also { SendCache = it }
        }
    private var ReplyCache: ImageVector? = null

    /** Mirrors the reference icon geometry for Reply. */
    public val Reply: ImageVector
        get() {
            ReplyCache?.let { return it }
            return         ImageVector.Builder(
                        name = "ElegantIcons.Reply",
                        defaultWidth = 24.0f.dp,
                        defaultHeight = 24.0f.dp,
                        viewportWidth = 1264.8f,
                        viewportHeight = 1264.8f,
                    ).apply {
                        group(scaleY = -1.0f, translationY = 1264.8f) {
                            addPath(
                                pathData = listOf(
                                    PathNode.MoveTo(1159.4f, 686.9f),
                                    PathNode.QuadTo(1159.4f, 809.9f, 1087.9f, 908.9f),
                                    PathNode.QuadTo(1016.4f, 1007.9f, 895.4f, 1064.4f),
                                    PathNode.QuadTo(774.4f, 1120.9f, 632.4f, 1120.9f),
                                    PathNode.QuadTo(500.4f, 1120.9f, 378.4f, 1066.9f),
                                    PathNode.QuadTo(256.4f, 1012.9f, 180.9f, 913.9f),
                                    PathNode.QuadTo(105.4f, 814.9f, 105.4f, 686.9f),
                                    PathNode.QuadTo(105.4f, 596.9f, 145.9f, 519.4f),
                                    PathNode.QuadTo(186.4f, 441.9f, 255.4f, 383.9f),
                                    PathNode.QuadTo(324.4f, 325.9f, 409.4f, 292.9f),
                                    PathNode.QuadTo(416.4f, 290.9f, 419.9f, 284.4f),
                                    PathNode.QuadTo(423.4f, 277.9f, 421.4f, 269.9f),
                                    PathNode.LineTo(387.4f, 155.9f),
                                    PathNode.QuadTo(385.4f, 150.9f, 389.4f, 147.4f),
                                    PathNode.QuadTo(393.4f, 143.9f, 399.4f, 144.9f),
                                    PathNode.LineTo(745.4f, 244.9f),
                                    PathNode.QuadTo(935.4f, 299.9f, 1047.4f, 403.9f),
                                    PathNode.QuadTo(1159.4f, 507.9f, 1159.4f, 686.9f),
                                    PathNode.Close,
                                    PathNode.MoveTo(441.4f, 377.9f),
                                    PathNode.QuadTo(374.4f, 402.9f, 318.9f, 447.9f),
                                    PathNode.QuadTo(263.4f, 492.9f, 229.9f, 554.4f),
                                    PathNode.QuadTo(196.4f, 615.9f, 196.4f, 686.9f),
                                    PathNode.QuadTo(196.4f, 788.9f, 259.4f, 867.4f),
                                    PathNode.QuadTo(322.4f, 945.9f, 422.9f, 988.4f),
                                    PathNode.QuadTo(523.4f, 1030.9f, 632.4f, 1030.9f),
                                    PathNode.QuadTo(756.4f, 1030.9f, 855.9f, 983.4f),
                                    PathNode.QuadTo(955.4f, 935.9f, 1011.9f, 856.9f),
                                    PathNode.QuadTo(1068.4f, 777.9f, 1068.4f, 686.9f),
                                    PathNode.QuadTo(1068.4f, 585.9f, 1022.4f, 515.9f),
                                    PathNode.QuadTo(976.4f, 445.9f, 900.4f, 403.4f),
                                    PathNode.QuadTo(824.4f, 360.9f, 719.4f, 330.9f),
                                    PathNode.LineTo(512.4f, 270.9f),
                                    PathNode.QuadTo(514.4f, 305.9f, 494.9f, 335.4f),
                                    PathNode.QuadTo(475.4f, 364.9f, 441.4f, 377.9f),
                                    PathNode.Close,
                                ),
                                fill = SolidColor(Color.Black),
                                fillAlpha = 1f,
                                pathFillType = PathFillType.NonZero,
                            )
                        }
                    }.build().also { ReplyCache = it }
        }
    private var ForwardCache: ImageVector? = null

    /** Mirrors the reference icon geometry for Forward. */
    public val Forward: ImageVector
        get() {
            ForwardCache?.let { return it }
            return         ImageVector.Builder(
                        name = "ElegantIcons.Forward",
                        defaultWidth = 24.0f.dp,
                        defaultHeight = 24.0f.dp,
                        viewportWidth = 1141.2f,
                        viewportHeight = 1141.2f,
                    ).apply {
                        group(scaleY = -1.0f, translationY = 1141.2f) {
                            addPath(
                                pathData = listOf(
                                    PathNode.MoveTo(848.9f, 148.1f),
                                    PathNode.QuadTo(898.8f, 173.6f, 927.0f, 226.3f),
                                    PathNode.QuadTo(940.2f, 252.7f, 943.1f, 289.7f),
                                    PathNode.QuadTo(946.0f, 326.7f, 946.0f, 412.6f),
                                    PathNode.VerticalTo(510.7f),
                                    PathNode.QuadTo(946.0f, 524.9f, 939.0f, 533.3f),
                                    PathNode.QuadTo(932.0f, 541.6f, 913.9f, 541.6f),
                                    PathNode.HorizontalTo(893.6f),
                                    PathNode.QuadTo(874.8f, 541.6f, 867.7f, 533.3f),
                                    PathNode.QuadTo(860.5f, 524.9f, 860.5f, 510.7f),
                                    PathNode.VerticalTo(372.9f),
                                    PathNode.QuadTo(860.5f, 323.9f, 858.8f, 303.1f),
                                    PathNode.QuadTo(857.1f, 282.4f, 849.9f, 268.1f),
                                    PathNode.QuadTo(836.3f, 238.8f, 807.1f, 225.2f),
                                    PathNode.QuadTo(792.8f, 218.1f, 772.4f, 216.3f),
                                    PathNode.QuadTo(751.9f, 214.6f, 702.3f, 214.6f),
                                    PathNode.HorizontalTo(372.4f),
                                    PathNode.QuadTo(322.8f, 214.6f, 302.3f, 216.3f),
                                    PathNode.QuadTo(281.9f, 218.1f, 266.7f, 225.2f),
                                    PathNode.QuadTo(238.4f, 239.5f, 224.7f, 268.1f),
                                    PathNode.QuadTo(217.6f, 282.4f, 215.9f, 303.1f),
                                    PathNode.QuadTo(214.2f, 323.9f, 214.2f, 372.9f),
                                    PathNode.VerticalTo(700.9f),
                                    PathNode.QuadTo(214.2f, 750.6f, 215.9f, 771.0f),
                                    PathNode.QuadTo(217.6f, 791.4f, 224.7f, 805.7f),
                                    PathNode.QuadTo(237.5f, 832.7f, 266.7f, 848.5f),
                                    PathNode.QuadTo(281.9f, 855.7f, 302.3f, 857.4f),
                                    PathNode.QuadTo(322.8f, 859.1f, 372.4f, 859.1f),
                                    PathNode.HorizontalTo(505.3f),
                                    PathNode.QuadTo(518.6f, 859.1f, 526.6f, 866.6f),
                                    PathNode.QuadTo(534.7f, 874.1f, 534.7f, 889.4f),
                                    PathNode.VerticalTo(914.4f),
                                    PathNode.QuadTo(534.7f, 929.7f, 526.6f, 937.2f),
                                    PathNode.QuadTo(518.6f, 944.6f, 505.3f, 944.6f),
                                    PathNode.HorizontalTo(412.1f),
                                    PathNode.QuadTo(326.2f, 944.6f, 289.2f, 941.7f),
                                    PathNode.QuadTo(252.2f, 938.8f, 225.9f, 925.6f),
                                    PathNode.QuadTo(173.8f, 898.4f, 147.6f, 847.5f),
                                    PathNode.QuadTo(134.2f, 821.1f, 131.0f, 784.1f),
                                    PathNode.QuadTo(127.7f, 747.1f, 127.7f, 660.3f),
                                    PathNode.VerticalTo(412.6f),
                                    PathNode.QuadTo(127.7f, 326.7f, 131.0f, 289.7f),
                                    PathNode.QuadTo(134.2f, 252.7f, 147.6f, 226.3f),
                                    PathNode.QuadTo(174.7f, 173.6f, 225.9f, 148.1f),
                                    PathNode.QuadTo(252.2f, 134.9f, 289.2f, 132.0f),
                                    PathNode.QuadTo(326.2f, 129.1f, 412.1f, 129.1f),
                                    PathNode.HorizontalTo(661.6f),
                                    PathNode.QuadTo(748.5f, 129.1f, 785.5f, 132.0f),
                                    PathNode.QuadTo(822.5f, 134.9f, 848.9f, 148.1f),
                                    PathNode.Close,
                                    PathNode.MoveTo(517.2f, 455.4f),
                                    PathNode.LineTo(928.0f, 865.5f),
                                    PathNode.VerticalTo(700.0f),
                                    PathNode.QuadTo(928.0f, 685.6f, 934.5f, 678.6f),
                                    PathNode.QuadTo(941.1f, 671.5f, 954.6f, 671.5f),
                                    PathNode.HorizontalTo(986.0f),
                                    PathNode.QuadTo(999.5f, 671.5f, 1006.5f, 678.6f),
                                    PathNode.QuadTo(1013.5f, 685.6f, 1013.5f, 700.0f),
                                    PathNode.VerticalTo(957.8f),
                                    PathNode.QuadTo(1013.5f, 985.2f, 1000.5f, 998.6f),
                                    PathNode.QuadTo(987.5f, 1012.1f, 960.1f, 1012.1f),
                                    PathNode.HorizontalTo(699.5f),
                                    PathNode.QuadTo(687.9f, 1012.1f, 680.4f, 1005.0f),
                                    PathNode.QuadTo(672.9f, 997.8f, 672.9f, 985.5f),
                                    PathNode.VerticalTo(954.1f),
                                    PathNode.QuadTo(672.9f, 940.8f, 680.1f, 933.7f),
                                    PathNode.QuadTo(687.2f, 926.6f, 699.5f, 926.6f),
                                    PathNode.HorizontalTo(866.9f),
                                    PathNode.LineTo(458.6f, 518.6f),
                                    PathNode.QuadTo(448.6f, 508.3f, 447.6f, 497.2f),
                                    PathNode.QuadTo(446.5f, 486.0f, 456.8f, 475.7f),
                                    PathNode.LineTo(476.2f, 456.3f),
                                    PathNode.QuadTo(486.4f, 446.1f, 497.2f, 446.5f),
                                    PathNode.QuadTo(507.9f, 447.0f, 517.2f, 455.4f),
                                    PathNode.Close,
                                ),
                                fill = SolidColor(Color.Black),
                                fillAlpha = 1f,
                                pathFillType = PathFillType.NonZero,
                            )
                        }
                    }.build().also { ForwardCache = it }
        }
    private var LockCache: ImageVector? = null

    /** Mirrors the reference icon geometry for Lock. */
    public val Lock: ImageVector
        get() {
            LockCache?.let { return it }
            return         ImageVector.Builder(
                        name = "ElegantIcons.Lock",
                        defaultWidth = 24.0f.dp,
                        defaultHeight = 24.0f.dp,
                        viewportWidth = 1281.6f,
                        viewportHeight = 1281.6f,
                    ).apply {
                        group(scaleY = -1.0f, translationY = 1281.6f) {
                            addPath(
                                pathData = listOf(
                                    PathNode.MoveTo(978.8f, 123.8f),
                                    PathNode.QuadTo(1024.8f, 146.8f, 1047.8f, 192.8f),
                                    PathNode.QuadTo(1059.8f, 215.8f, 1062.3f, 248.3f),
                                    PathNode.QuadTo(1064.8f, 280.8f, 1064.8f, 357.8f),
                                    PathNode.VerticalTo(563.8f),
                                    PathNode.QuadTo(1064.8f, 640.8f, 1062.3f, 673.3f),
                                    PathNode.QuadTo(1059.8f, 705.8f, 1047.8f, 728.8f),
                                    PathNode.QuadTo(1024.8f, 773.8f, 978.8f, 796.8f),
                                    PathNode.QuadTo(964.8f, 803.8f, 947.8f, 807.3f),
                                    PathNode.QuadTo(930.8f, 810.8f, 905.8f, 812.8f),
                                    PathNode.VerticalTo(909.8f),
                                    PathNode.QuadTo(905.8f, 981.8f, 870.3f, 1042.8f),
                                    PathNode.QuadTo(834.8f, 1103.8f, 773.8f, 1139.3f),
                                    PathNode.QuadTo(712.8f, 1174.8f, 640.8f, 1174.8f),
                                    PathNode.QuadTo(568.8f, 1174.8f, 507.8f, 1139.3f),
                                    PathNode.QuadTo(446.8f, 1103.8f, 411.3f, 1042.8f),
                                    PathNode.QuadTo(375.8f, 981.8f, 375.8f, 909.8f),
                                    PathNode.VerticalTo(812.8f),
                                    PathNode.QuadTo(326.8f, 809.8f, 301.8f, 796.8f),
                                    PathNode.QuadTo(279.8f, 785.8f, 262.3f, 768.3f),
                                    PathNode.QuadTo(244.8f, 750.8f, 233.8f, 728.8f),
                                    PathNode.QuadTo(221.8f, 705.8f, 219.3f, 673.3f),
                                    PathNode.QuadTo(216.8f, 640.8f, 216.8f, 563.8f),
                                    PathNode.VerticalTo(357.8f),
                                    PathNode.QuadTo(216.8f, 280.8f, 219.3f, 248.3f),
                                    PathNode.QuadTo(221.8f, 215.8f, 233.8f, 192.8f),
                                    PathNode.QuadTo(256.8f, 146.8f, 301.8f, 123.8f),
                                    PathNode.QuadTo(324.8f, 111.8f, 357.8f, 109.3f),
                                    PathNode.QuadTo(390.8f, 106.8f, 467.8f, 106.8f),
                                    PathNode.HorizontalTo(813.8f),
                                    PathNode.QuadTo(890.8f, 106.8f, 923.3f, 109.3f),
                                    PathNode.QuadTo(955.8f, 111.8f, 978.8f, 123.8f),
                                    PathNode.Close,
                                    PathNode.MoveTo(345.8f, 200.8f),
                                    PathNode.QuadTo(322.8f, 212.8f, 310.8f, 235.8f),
                                    PathNode.QuadTo(304.8f, 246.8f, 303.3f, 262.8f),
                                    PathNode.QuadTo(301.8f, 278.8f, 301.8f, 310.8f),
                                    PathNode.VerticalTo(321.8f),
                                    PathNode.VerticalTo(599.8f),
                                    PathNode.QuadTo(301.8f, 639.8f, 303.3f, 656.3f),
                                    PathNode.QuadTo(304.8f, 672.8f, 310.8f, 684.8f),
                                    PathNode.QuadTo(322.8f, 707.8f, 345.8f, 719.8f),
                                    PathNode.QuadTo(357.8f, 725.8f, 374.8f, 727.3f),
                                    PathNode.QuadTo(391.8f, 728.8f, 430.8f, 728.8f),
                                    PathNode.HorizontalTo(849.8f),
                                    PathNode.QuadTo(889.8f, 728.8f, 906.3f, 727.3f),
                                    PathNode.QuadTo(922.8f, 725.8f, 934.8f, 719.8f),
                                    PathNode.QuadTo(958.8f, 707.8f, 970.8f, 684.8f),
                                    PathNode.QuadTo(975.8f, 672.8f, 977.3f, 656.3f),
                                    PathNode.QuadTo(978.8f, 639.8f, 978.8f, 599.8f),
                                    PathNode.VerticalTo(321.8f),
                                    PathNode.QuadTo(978.8f, 281.8f, 977.8f, 264.8f),
                                    PathNode.QuadTo(976.8f, 247.8f, 970.8f, 235.8f),
                                    PathNode.QuadTo(958.8f, 212.8f, 934.8f, 200.8f),
                                    PathNode.QuadTo(922.8f, 194.8f, 906.3f, 193.3f),
                                    PathNode.QuadTo(889.8f, 191.8f, 849.8f, 191.8f),
                                    PathNode.HorizontalTo(430.8f),
                                    PathNode.QuadTo(391.8f, 191.8f, 374.8f, 193.3f),
                                    PathNode.QuadTo(357.8f, 194.8f, 345.8f, 200.8f),
                                    PathNode.Close,
                                    PathNode.MoveTo(640.8f, 1089.8f),
                                    PathNode.QuadTo(689.8f, 1089.8f, 730.8f, 1065.3f),
                                    PathNode.QuadTo(771.8f, 1040.8f, 795.8f, 999.8f),
                                    PathNode.QuadTo(819.8f, 958.8f, 819.8f, 909.8f),
                                    PathNode.VerticalTo(814.8f),
                                    PathNode.HorizontalTo(460.8f),
                                    PathNode.VerticalTo(909.8f),
                                    PathNode.QuadTo(460.8f, 958.8f, 485.3f, 999.8f),
                                    PathNode.QuadTo(509.8f, 1040.8f, 550.8f, 1065.3f),
                                    PathNode.QuadTo(591.8f, 1089.8f, 640.8f, 1089.8f),
                                    PathNode.Close,
                                ),
                                fill = SolidColor(Color.Black),
                                fillAlpha = 1f,
                                pathFillType = PathFillType.NonZero,
                            )
                        }
                    }.build().also { LockCache = it }
        }
    private var UnlockCache: ImageVector? = null

    /** Mirrors the reference icon geometry for Unlock. */
    public val Unlock: ImageVector
        get() {
            UnlockCache?.let { return it }
            return         ImageVector.Builder(
                        name = "ElegantIcons.Unlock",
                        defaultWidth = 24.0f.dp,
                        defaultHeight = 24.0f.dp,
                        viewportWidth = 1281.6f,
                        viewportHeight = 1281.6f,
                    ).apply {
                        group(scaleY = -1.0f, translationY = 1281.6f) {
                            addPath(
                                pathData = listOf(
                                    PathNode.MoveTo(978.8f, 123.8f),
                                    PathNode.QuadTo(1024.8f, 146.8f, 1047.8f, 192.8f),
                                    PathNode.QuadTo(1059.8f, 215.8f, 1062.3f, 248.3f),
                                    PathNode.QuadTo(1064.8f, 280.8f, 1064.8f, 357.8f),
                                    PathNode.VerticalTo(563.8f),
                                    PathNode.QuadTo(1064.8f, 640.8f, 1062.3f, 673.3f),
                                    PathNode.QuadTo(1059.8f, 705.8f, 1047.8f, 728.8f),
                                    PathNode.QuadTo(1024.8f, 773.8f, 978.8f, 796.8f),
                                    PathNode.QuadTo(955.8f, 808.8f, 923.3f, 811.3f),
                                    PathNode.QuadTo(890.8f, 813.8f, 813.8f, 813.8f),
                                    PathNode.HorizontalTo(499.8f),
                                    PathNode.QuadTo(477.8f, 813.8f, 469.3f, 821.8f),
                                    PathNode.QuadTo(460.8f, 829.8f, 460.8f, 849.8f),
                                    PathNode.VerticalTo(909.8f),
                                    PathNode.QuadTo(460.8f, 958.8f, 485.3f, 999.8f),
                                    PathNode.QuadTo(509.8f, 1040.8f, 550.8f, 1065.3f),
                                    PathNode.QuadTo(591.8f, 1089.8f, 640.8f, 1089.8f),
                                    PathNode.QuadTo(682.8f, 1089.8f, 720.3f, 1071.3f),
                                    PathNode.QuadTo(757.8f, 1052.8f, 783.8f, 1018.8f),
                                    PathNode.QuadTo(809.8f, 984.8f, 817.8f, 940.8f),
                                    PathNode.QuadTo(820.8f, 924.8f, 828.3f, 918.3f),
                                    PathNode.QuadTo(835.8f, 911.8f, 854.8f, 911.8f),
                                    PathNode.HorizontalTo(875.8f),
                                    PathNode.QuadTo(893.8f, 911.8f, 899.8f, 920.8f),
                                    PathNode.QuadTo(905.8f, 929.8f, 902.8f, 947.8f),
                                    PathNode.QuadTo(892.8f, 1012.8f, 855.3f, 1064.8f),
                                    PathNode.QuadTo(817.8f, 1116.8f, 761.8f, 1145.8f),
                                    PathNode.QuadTo(705.8f, 1174.8f, 640.8f, 1174.8f),
                                    PathNode.QuadTo(568.8f, 1174.8f, 507.8f, 1139.3f),
                                    PathNode.QuadTo(446.8f, 1103.8f, 411.3f, 1042.8f),
                                    PathNode.QuadTo(375.8f, 981.8f, 375.8f, 909.8f),
                                    PathNode.VerticalTo(812.8f),
                                    PathNode.QuadTo(350.8f, 810.8f, 333.3f, 807.3f),
                                    PathNode.QuadTo(315.8f, 803.8f, 301.8f, 796.8f),
                                    PathNode.QuadTo(256.8f, 773.8f, 233.8f, 728.8f),
                                    PathNode.QuadTo(221.8f, 705.8f, 219.3f, 673.3f),
                                    PathNode.QuadTo(216.8f, 640.8f, 216.8f, 563.8f),
                                    PathNode.VerticalTo(357.8f),
                                    PathNode.QuadTo(216.8f, 280.8f, 219.3f, 248.3f),
                                    PathNode.QuadTo(221.8f, 215.8f, 233.8f, 192.8f),
                                    PathNode.QuadTo(256.8f, 146.8f, 301.8f, 123.8f),
                                    PathNode.QuadTo(324.8f, 111.8f, 357.8f, 109.3f),
                                    PathNode.QuadTo(390.8f, 106.8f, 467.8f, 106.8f),
                                    PathNode.HorizontalTo(813.8f),
                                    PathNode.QuadTo(890.8f, 106.8f, 923.3f, 109.3f),
                                    PathNode.QuadTo(955.8f, 111.8f, 978.8f, 123.8f),
                                    PathNode.Close,
                                    PathNode.MoveTo(345.8f, 200.8f),
                                    PathNode.QuadTo(322.8f, 212.8f, 310.8f, 235.8f),
                                    PathNode.QuadTo(304.8f, 247.8f, 303.3f, 264.8f),
                                    PathNode.QuadTo(301.8f, 281.8f, 301.8f, 321.8f),
                                    PathNode.VerticalTo(599.8f),
                                    PathNode.VerticalTo(610.8f),
                                    PathNode.QuadTo(301.8f, 641.8f, 303.3f, 657.8f),
                                    PathNode.QuadTo(304.8f, 673.8f, 310.8f, 684.8f),
                                    PathNode.QuadTo(322.8f, 707.8f, 345.8f, 719.8f),
                                    PathNode.QuadTo(357.8f, 725.8f, 374.8f, 727.3f),
                                    PathNode.QuadTo(391.8f, 728.8f, 430.8f, 728.8f),
                                    PathNode.HorizontalTo(849.8f),
                                    PathNode.QuadTo(889.8f, 728.8f, 906.3f, 727.3f),
                                    PathNode.QuadTo(922.8f, 725.8f, 934.8f, 719.8f),
                                    PathNode.QuadTo(958.8f, 707.8f, 970.8f, 684.8f),
                                    PathNode.QuadTo(975.8f, 672.8f, 977.3f, 656.3f),
                                    PathNode.QuadTo(978.8f, 639.8f, 978.8f, 599.8f),
                                    PathNode.VerticalTo(321.8f),
                                    PathNode.QuadTo(978.8f, 281.8f, 977.8f, 264.8f),
                                    PathNode.QuadTo(976.8f, 247.8f, 970.8f, 235.8f),
                                    PathNode.QuadTo(958.8f, 212.8f, 934.8f, 200.8f),
                                    PathNode.QuadTo(922.8f, 194.8f, 906.3f, 193.3f),
                                    PathNode.QuadTo(889.8f, 191.8f, 849.8f, 191.8f),
                                    PathNode.HorizontalTo(430.8f),
                                    PathNode.QuadTo(391.8f, 191.8f, 374.8f, 193.3f),
                                    PathNode.QuadTo(357.8f, 194.8f, 345.8f, 200.8f),
                                    PathNode.Close,
                                ),
                                fill = SolidColor(Color.Black),
                                fillAlpha = 1f,
                                pathFillType = PathFillType.NonZero,
                            )
                        }
                    }.build().also { UnlockCache = it }
        }
    private var LocationCache: ImageVector? = null

    /** Mirrors the reference icon geometry for Location. */
    public val Location: ImageVector
        get() {
            LocationCache?.let { return it }
            return         ImageVector.Builder(
                        name = "ElegantIcons.Location",
                        defaultWidth = 24.0f.dp,
                        defaultHeight = 24.0f.dp,
                        viewportWidth = 1311.6f,
                        viewportHeight = 1311.6f,
                    ).apply {
                        group(scaleY = -1.0f, translationY = 1311.6f) {
                            addPath(
                                pathData = listOf(
                                    PathNode.MoveTo(699.8f, 316.3f),
                                    PathNode.QuadTo(767.8f, 401.3f, 846.3f, 518.3f),
                                    PathNode.QuadTo(924.8f, 635.3f, 963.8f, 726.3f),
                                    PathNode.QuadTo(995.8f, 800.3f, 995.8f, 869.3f),
                                    PathNode.QuadTo(995.8f, 959.3f, 951.3f, 1035.8f),
                                    PathNode.QuadTo(906.8f, 1112.3f, 830.3f, 1157.3f),
                                    PathNode.QuadTo(753.8f, 1202.3f, 663.8f, 1202.3f),
                                    PathNode.QuadTo(572.8f, 1202.3f, 496.3f, 1157.3f),
                                    PathNode.QuadTo(419.8f, 1112.3f, 374.8f, 1036.3f),
                                    PathNode.QuadTo(329.8f, 960.3f, 329.8f, 869.3f),
                                    PathNode.QuadTo(329.8f, 800.3f, 362.8f, 724.3f),
                                    PathNode.QuadTo(399.8f, 636.3f, 477.8f, 521.8f),
                                    PathNode.QuadTo(555.8f, 407.3f, 628.8f, 316.3f),
                                    PathNode.QuadTo(642.8f, 298.3f, 664.8f, 298.3f),
                                    PathNode.QuadTo(686.8f, 298.3f, 699.8f, 316.3f),
                                    PathNode.Close,
                                    PathNode.MoveTo(1150.8f, 290.3f),
                                    PathNode.QuadTo(1150.8f, 340.3f, 1078.8f, 383.3f),
                                    PathNode.QuadTo(1006.8f, 426.3f, 885.8f, 450.3f),
                                    PathNode.QuadTo(871.8f, 453.3f, 863.8f, 450.3f),
                                    PathNode.QuadTo(855.8f, 447.3f, 849.8f, 438.3f),
                                    PathNode.LineTo(824.8f, 401.3f),
                                    PathNode.QuadTo(818.8f, 392.3f, 821.3f, 385.8f),
                                    PathNode.QuadTo(823.8f, 379.3f, 837.8f, 377.3f),
                                    PathNode.QuadTo(931.8f, 364.3f, 987.3f, 340.3f),
                                    PathNode.QuadTo(1042.8f, 316.3f, 1042.8f, 290.3f),
                                    PathNode.QuadTo(1042.8f, 263.3f, 990.8f, 240.8f),
                                    PathNode.QuadTo(938.8f, 218.3f, 850.3f, 205.3f),
                                    PathNode.QuadTo(761.8f, 192.3f, 656.8f, 192.3f),
                                    PathNode.QuadTo(551.8f, 192.3f, 462.8f, 205.3f),
                                    PathNode.QuadTo(373.8f, 218.3f, 321.8f, 240.8f),
                                    PathNode.QuadTo(269.8f, 263.3f, 269.8f, 290.3f),
                                    PathNode.QuadTo(269.8f, 318.3f, 328.8f, 342.3f),
                                    PathNode.QuadTo(387.8f, 366.3f, 491.8f, 379.3f),
                                    PathNode.QuadTo(504.8f, 380.3f, 505.8f, 389.3f),
                                    PathNode.QuadTo(506.8f, 398.3f, 496.8f, 411.3f),
                                    PathNode.LineTo(474.8f, 440.3f),
                                    PathNode.QuadTo(467.8f, 450.3f, 460.8f, 452.3f),
                                    PathNode.QuadTo(453.8f, 454.3f, 435.8f, 451.3f),
                                    PathNode.QuadTo(311.8f, 429.3f, 236.3f, 385.8f),
                                    PathNode.QuadTo(160.8f, 342.3f, 160.8f, 290.3f),
                                    PathNode.QuadTo(160.8f, 241.3f, 227.3f, 199.8f),
                                    PathNode.QuadTo(293.8f, 158.3f, 407.8f, 133.8f),
                                    PathNode.QuadTo(521.8f, 109.3f, 656.8f, 109.3f),
                                    PathNode.QuadTo(790.8f, 109.3f, 904.3f, 133.8f),
                                    PathNode.QuadTo(1017.8f, 158.3f, 1084.3f, 199.8f),
                                    PathNode.QuadTo(1150.8f, 241.3f, 1150.8f, 290.3f),
                                    PathNode.Close,
                                    PathNode.MoveTo(542.8f, 866.3f),
                                    PathNode.QuadTo(542.8f, 899.3f, 559.3f, 926.8f),
                                    PathNode.QuadTo(575.8f, 954.3f, 603.3f, 970.8f),
                                    PathNode.QuadTo(630.8f, 987.3f, 663.8f, 987.3f),
                                    PathNode.QuadTo(695.8f, 987.3f, 723.8f, 970.8f),
                                    PathNode.QuadTo(751.8f, 954.3f, 768.3f, 926.3f),
                                    PathNode.QuadTo(784.8f, 898.3f, 784.8f, 866.3f),
                                    PathNode.QuadTo(784.8f, 833.3f, 768.3f, 805.8f),
                                    PathNode.QuadTo(751.8f, 778.3f, 724.3f, 761.8f),
                                    PathNode.QuadTo(696.8f, 745.3f, 663.8f, 745.3f),
                                    PathNode.QuadTo(630.8f, 745.3f, 602.8f, 761.3f),
                                    PathNode.QuadTo(574.8f, 777.3f, 558.8f, 805.3f),
                                    PathNode.QuadTo(542.8f, 833.3f, 542.8f, 866.3f),
                                    PathNode.Close,
                                ),
                                fill = SolidColor(Color.Black),
                                fillAlpha = 1f,
                                pathFillType = PathFillType.NonZero,
                            )
                        }
                    }.build().also { LocationCache = it }
        }
    private var ImageCache: ImageVector? = null

    /** Mirrors the reference icon geometry for Image. */
    public val Image: ImageVector
        get() {
            ImageCache?.let { return it }
            return         ImageVector.Builder(
                        name = "ElegantIcons.Image",
                        defaultWidth = 24.0f.dp,
                        defaultHeight = 24.0f.dp,
                        viewportWidth = 1254.0f,
                        viewportHeight = 1254.0f,
                    ).apply {
                        group(scaleY = -1.0f, translationY = 1254.0f) {
                            addPath(
                                pathData = listOf(
                                    PathNode.MoveTo(1033.4f, 209.8f),
                                    PathNode.QuadTo(1087.3f, 237.1f, 1114.5f, 290.9f),
                                    PathNode.QuadTo(1128.3f, 318.2f, 1131.7f, 356.7f),
                                    PathNode.QuadTo(1135.1f, 395.2f, 1135.1f, 485.4f),
                                    PathNode.VerticalTo(768.6f),
                                    PathNode.QuadTo(1135.1f, 858.9f, 1131.7f, 897.8f),
                                    PathNode.QuadTo(1128.3f, 936.7f, 1114.5f, 963.1f),
                                    PathNode.QuadTo(1087.3f, 1017.0f, 1033.4f, 1044.2f),
                                    PathNode.QuadTo(1006.1f, 1058.0f, 968.1f, 1061.4f),
                                    PathNode.QuadTo(930.0f, 1064.8f, 838.9f, 1064.8f),
                                    PathNode.HorizontalTo(415.1f),
                                    PathNode.QuadTo(324.9f, 1064.8f, 286.4f, 1061.4f),
                                    PathNode.QuadTo(247.9f, 1058.0f, 220.6f, 1044.2f),
                                    PathNode.QuadTo(166.7f, 1017.0f, 139.5f, 963.1f),
                                    PathNode.QuadTo(125.7f, 936.7f, 122.3f, 897.8f),
                                    PathNode.QuadTo(118.9f, 858.9f, 118.9f, 768.6f),
                                    PathNode.VerticalTo(485.4f),
                                    PathNode.QuadTo(118.9f, 395.2f, 122.3f, 356.7f),
                                    PathNode.QuadTo(125.7f, 318.2f, 139.5f, 290.9f),
                                    PathNode.QuadTo(166.7f, 237.1f, 220.6f, 209.8f),
                                    PathNode.QuadTo(247.9f, 196.0f, 286.4f, 192.6f),
                                    PathNode.QuadTo(324.9f, 189.2f, 415.1f, 189.2f),
                                    PathNode.HorizontalTo(838.9f),
                                    PathNode.QuadTo(930.0f, 189.2f, 968.1f, 192.6f),
                                    PathNode.QuadTo(1006.1f, 196.0f, 1033.4f, 209.8f),
                                    PathNode.Close,
                                    PathNode.MoveTo(365.7f, 283.9f),
                                    PathNode.QuadTo(323.9f, 283.9f, 303.3f, 285.8f),
                                    PathNode.QuadTo(282.7f, 287.7f, 268.3f, 294.5f),
                                    PathNode.QuadTo(242.7f, 308.7f, 226.7f, 333.2f),
                                    PathNode.LineTo(386.4f, 509.3f),
                                    PathNode.QuadTo(393.9f, 516.8f, 400.0f, 517.3f),
                                    PathNode.QuadTo(406.2f, 517.8f, 413.8f, 510.2f),
                                    PathNode.LineTo(461.7f, 459.4f),
                                    PathNode.LineTo(472.4f, 447.8f),
                                    PathNode.QuadTo(490.9f, 428.3f, 500.7f, 419.9f),
                                    PathNode.QuadTo(510.5f, 411.6f, 520.4f, 408.7f),
                                    PathNode.QuadTo(543.1f, 402.8f, 565.8f, 408.7f),
                                    PathNode.QuadTo(576.7f, 412.6f, 588.9f, 423.3f),
                                    PathNode.QuadTo(601.0f, 434.0f, 624.5f, 459.4f),
                                    PathNode.LineTo(807.0f, 651.6f),
                                    PathNode.QuadTo(812.7f, 658.3f, 818.5f, 658.3f),
                                    PathNode.QuadTo(824.3f, 658.3f, 830.1f, 652.6f),
                                    PathNode.LineTo(1043.3f, 418.1f),
                                    PathNode.QuadTo(1042.3f, 387.5f, 1039.9f, 370.2f),
                                    PathNode.QuadTo(1037.5f, 353.0f, 1031.7f, 340.6f),
                                    PathNode.QuadTo(1014.6f, 309.9f, 985.8f, 294.5f),
                                    PathNode.QuadTo(970.5f, 286.9f, 948.7f, 285.4f),
                                    PathNode.QuadTo(927.0f, 283.9f, 874.8f, 283.9f),
                                    PathNode.HorizontalTo(379.2f),
                                    PathNode.Close,
                                    PathNode.MoveTo(223.3f, 913.5f),
                                    PathNode.QuadTo(230.0f, 928.8f, 242.0f, 940.3f),
                                    PathNode.QuadTo(253.9f, 951.8f, 268.2f, 959.5f),
                                    PathNode.QuadTo(284.5f, 967.2f, 306.2f, 969.1f),
                                    PathNode.QuadTo(328.0f, 971.0f, 379.2f, 971.0f),
                                    PathNode.HorizontalTo(874.8f),
                                    PathNode.QuadTo(927.0f, 971.0f, 948.7f, 969.1f),
                                    PathNode.QuadTo(970.5f, 967.2f, 985.8f, 959.5f),
                                    PathNode.QuadTo(1014.6f, 945.0f, 1031.7f, 913.4f),
                                    PathNode.QuadTo(1039.5f, 898.1f, 1041.4f, 876.5f),
                                    PathNode.QuadTo(1043.3f, 854.8f, 1043.3f, 802.5f),
                                    PathNode.VerticalTo(544.7f),
                                    PathNode.LineTo(900.9f, 700.5f),
                                    PathNode.QuadTo(874.5f, 728.9f, 863.8f, 738.6f),
                                    PathNode.QuadTo(853.1f, 748.4f, 842.2f, 752.2f),
                                    PathNode.QuadTo(819.5f, 760.1f, 796.8f, 752.2f),
                                    PathNode.QuadTo(787.0f, 749.3f, 778.2f, 742.0f),
                                    PathNode.QuadTo(769.3f, 734.7f, 747.8f, 713.1f),
                                    PathNode.LineTo(737.1f, 702.5f),
                                    PathNode.LineTo(553.6f, 508.4f),
                                    PathNode.QuadTo(548.8f, 503.6f, 543.1f, 503.6f),
                                    PathNode.QuadTo(537.3f, 503.6f, 531.7f, 509.2f),
                                    PathNode.LineTo(482.0f, 561.9f),
                                    PathNode.QuadTo(457.5f, 588.3f, 445.8f, 599.0f),
                                    PathNode.QuadTo(434.0f, 609.7f, 422.3f, 612.6f),
                                    PathNode.QuadTo(399.6f, 620.4f, 376.9f, 612.6f),
                                    PathNode.QuadTo(366.0f, 608.7f, 355.3f, 599.0f),
                                    PathNode.QuadTo(344.6f, 589.2f, 318.2f, 560.9f),
                                    PathNode.LineTo(211.7f, 442.8f),
                                    PathNode.VerticalTo(802.5f),
                                    PathNode.QuadTo(211.7f, 854.8f, 213.6f, 876.5f),
                                    PathNode.QuadTo(215.5f, 898.2f, 223.3f, 913.5f),
                                    PathNode.Close,
                                    PathNode.MoveTo(473.2f, 806.4f),
                                    PathNode.QuadTo(473.2f, 840.6f, 449.7f, 864.2f),
                                    PathNode.QuadTo(426.1f, 887.8f, 391.9f, 887.8f),
                                    PathNode.QuadTo(357.6f, 887.8f, 333.6f, 863.8f),
                                    PathNode.QuadTo(309.5f, 839.8f, 309.5f, 806.4f),
                                    PathNode.QuadTo(309.5f, 773.0f, 334.0f, 748.5f),
                                    PathNode.QuadTo(358.5f, 724.0f, 391.9f, 724.0f),
                                    PathNode.QuadTo(425.2f, 724.0f, 449.2f, 748.1f),
                                    PathNode.QuadTo(473.2f, 772.2f, 473.2f, 806.4f),
                                    PathNode.Close,
                                ),
                                fill = SolidColor(Color.Black),
                                fillAlpha = 1f,
                                pathFillType = PathFillType.NonZero,
                            )
                        }
                    }.build().also { ImageCache = it }
        }
    private var PlayCache: ImageVector? = null

    /** Mirrors the reference icon geometry for Play. */
    public val Play: ImageVector
        get() {
            PlayCache?.let { return it }
            return         ImageVector.Builder(
                        name = "ElegantIcons.Play",
                        defaultWidth = 24.0f.dp,
                        defaultHeight = 24.0f.dp,
                        viewportWidth = 1168.8f,
                        viewportHeight = 1168.8f,
                    ).apply {
                        group(scaleY = -1.0f, translationY = 1168.8f) {
                            addPath(
                                pathData = listOf(
                                    PathNode.MoveTo(419.9f, 161.4f),
                                    PathNode.LineTo(878.9f, 427.4f),
                                    PathNode.QuadTo(944.9f, 465.4f, 971.9f, 485.4f),
                                    PathNode.QuadTo(998.9f, 505.4f, 1010.9f, 532.4f),
                                    PathNode.QuadTo(1021.9f, 557.4f, 1021.9f, 585.4f),
                                    PathNode.QuadTo(1021.9f, 613.4f, 1010.9f, 638.4f),
                                    PathNode.QuadTo(998.9f, 664.4f, 971.9f, 684.9f),
                                    PathNode.QuadTo(944.9f, 705.4f, 878.9f, 742.4f),
                                    PathNode.LineTo(419.9f, 1008.4f),
                                    PathNode.QuadTo(357.9f, 1044.4f, 324.9f, 1057.9f),
                                    PathNode.QuadTo(291.9f, 1071.4f, 263.9f, 1068.4f),
                                    PathNode.QuadTo(236.9f, 1066.4f, 212.9f, 1052.9f),
                                    PathNode.QuadTo(188.9f, 1039.4f, 171.9f, 1016.4f),
                                    PathNode.QuadTo(154.9f, 994.4f, 150.9f, 960.9f),
                                    PathNode.QuadTo(146.9f, 927.4f, 146.9f, 851.4f),
                                    PathNode.VerticalTo(320.4f),
                                    PathNode.QuadTo(146.9f, 242.4f, 150.4f, 209.4f),
                                    PathNode.QuadTo(153.9f, 176.4f, 170.9f, 152.4f),
                                    PathNode.QuadTo(187.9f, 130.4f, 212.4f, 116.4f),
                                    PathNode.QuadTo(236.9f, 102.4f, 262.9f, 100.4f),
                                    PathNode.QuadTo(291.9f, 97.4f, 324.4f, 110.9f),
                                    PathNode.QuadTo(356.9f, 124.4f, 419.9f, 161.4f),
                                    PathNode.Close,
                                    PathNode.MoveTo(244.9f, 205.4f),
                                    PathNode.QuadTo(239.9f, 212.4f, 238.4f, 235.9f),
                                    PathNode.QuadTo(236.9f, 259.4f, 236.9f, 320.4f),
                                    PathNode.VerticalTo(851.4f),
                                    PathNode.QuadTo(236.9f, 910.4f, 238.4f, 932.4f),
                                    PathNode.QuadTo(239.9f, 954.4f, 243.9f, 961.4f),
                                    PathNode.QuadTo(248.9f, 968.4f, 256.4f, 973.4f),
                                    PathNode.QuadTo(263.9f, 978.4f, 272.9f, 979.4f),
                                    PathNode.QuadTo(279.9f, 980.4f, 301.9f, 969.4f),
                                    PathNode.QuadTo(323.9f, 958.4f, 373.9f, 930.4f),
                                    PathNode.LineTo(834.9f, 664.4f),
                                    PathNode.QuadTo(881.9f, 637.4f, 901.4f, 623.9f),
                                    PathNode.QuadTo(920.9f, 610.4f, 925.9f, 602.4f),
                                    PathNode.QuadTo(935.9f, 586.4f, 927.9f, 568.4f),
                                    PathNode.QuadTo(923.9f, 560.4f, 904.4f, 546.9f),
                                    PathNode.QuadTo(884.9f, 533.4f, 834.9f, 505.4f),
                                    PathNode.LineTo(373.9f, 239.4f),
                                    PathNode.QuadTo(321.9f, 209.4f, 301.9f, 199.4f),
                                    PathNode.QuadTo(281.9f, 189.4f, 272.9f, 190.4f),
                                    PathNode.QuadTo(253.9f, 192.4f, 244.9f, 205.4f),
                                    PathNode.Close,
                                ),
                                fill = SolidColor(Color.Black),
                                fillAlpha = 1f,
                                pathFillType = PathFillType.NonZero,
                            )
                        }
                    }.build().also { PlayCache = it }
        }
    private var PauseCache: ImageVector? = null

    /** Mirrors the reference icon geometry for Pause. */
    public val Pause: ImageVector
        get() {
            PauseCache?.let { return it }
            return         ImageVector.Builder(
                        name = "ElegantIcons.Pause",
                        defaultWidth = 24.0f.dp,
                        defaultHeight = 24.0f.dp,
                        viewportWidth = 1134.0f,
                        viewportHeight = 1134.0f,
                    ).apply {
                        group(scaleY = -1.0f, translationY = 1134.0f) {
                            addPath(
                                pathData = listOf(
                                    PathNode.MoveTo(892.5f, 124.5f),
                                    PathNode.VerticalTo(1009.5f),
                                    PathNode.QuadTo(892.5f, 1023.5f, 884.5f, 1031.5f),
                                    PathNode.QuadTo(876.5f, 1039.5f, 862.5f, 1039.5f),
                                    PathNode.HorizontalTo(832.5f),
                                    PathNode.QuadTo(818.5f, 1039.5f, 810.5f, 1031.5f),
                                    PathNode.QuadTo(802.5f, 1023.5f, 802.5f, 1009.5f),
                                    PathNode.VerticalTo(124.5f),
                                    PathNode.QuadTo(802.5f, 110.5f, 810.5f, 102.5f),
                                    PathNode.QuadTo(818.5f, 94.5f, 832.5f, 94.5f),
                                    PathNode.HorizontalTo(862.5f),
                                    PathNode.QuadTo(876.5f, 94.5f, 884.5f, 102.5f),
                                    PathNode.QuadTo(892.5f, 110.5f, 892.5f, 124.5f),
                                    PathNode.Close,
                                    PathNode.MoveTo(331.5f, 124.5f),
                                    PathNode.VerticalTo(1009.5f),
                                    PathNode.QuadTo(331.5f, 1023.5f, 323.5f, 1031.5f),
                                    PathNode.QuadTo(315.5f, 1039.5f, 301.5f, 1039.5f),
                                    PathNode.HorizontalTo(271.5f),
                                    PathNode.QuadTo(257.5f, 1039.5f, 249.5f, 1031.5f),
                                    PathNode.QuadTo(241.5f, 1023.5f, 241.5f, 1009.5f),
                                    PathNode.VerticalTo(124.5f),
                                    PathNode.QuadTo(241.5f, 110.5f, 249.5f, 102.5f),
                                    PathNode.QuadTo(257.5f, 94.5f, 271.5f, 94.5f),
                                    PathNode.HorizontalTo(301.5f),
                                    PathNode.QuadTo(315.5f, 94.5f, 323.5f, 102.5f),
                                    PathNode.QuadTo(331.5f, 110.5f, 331.5f, 124.5f),
                                    PathNode.Close,
                                ),
                                fill = SolidColor(Color.Black),
                                fillAlpha = 1f,
                                pathFillType = PathFillType.NonZero,
                            )
                        }
                    }.build().also { PauseCache = it }
        }
    private var InfoCache: ImageVector? = null

    /** Mirrors the reference icon geometry for Info. */
    public val Info: ImageVector
        get() {
            InfoCache?.let { return it }
            return         ImageVector.Builder(
                        name = "ElegantIcons.Info",
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
                                    PathNode.QuadTo(102.1f, 473.1f, 171.1f, 356.1f),
                                    PathNode.QuadTo(240.1f, 239.1f, 357.1f, 170.6f),
                                    PathNode.QuadTo(474.1f, 102.1f, 613.1f, 102.1f),
                                    PathNode.QuadTo(752.1f, 102.1f, 869.1f, 170.6f),
                                    PathNode.QuadTo(986.1f, 239.1f, 1054.6f, 356.1f),
                                    PathNode.QuadTo(1123.1f, 473.1f, 1123.1f, 612.1f),
                                    PathNode.Close,
                                    PathNode.MoveTo(188.1f, 612.1f),
                                    PathNode.QuadTo(188.1f, 728.1f, 245.1f, 825.6f),
                                    PathNode.QuadTo(302.1f, 923.1f, 399.6f, 980.1f),
                                    PathNode.QuadTo(497.1f, 1037.1f, 613.1f, 1037.1f),
                                    PathNode.QuadTo(728.1f, 1037.1f, 825.6f, 980.1f),
                                    PathNode.QuadTo(923.1f, 923.1f, 980.6f, 825.6f),
                                    PathNode.QuadTo(1038.1f, 728.1f, 1038.1f, 612.1f),
                                    PathNode.QuadTo(1038.1f, 496.1f, 980.6f, 398.6f),
                                    PathNode.QuadTo(923.1f, 301.1f, 825.6f, 244.1f),
                                    PathNode.QuadTo(728.1f, 187.1f, 613.1f, 187.1f),
                                    PathNode.QuadTo(497.1f, 187.1f, 399.6f, 244.1f),
                                    PathNode.QuadTo(302.1f, 301.1f, 245.1f, 398.6f),
                                    PathNode.QuadTo(188.1f, 496.1f, 188.1f, 612.1f),
                                    PathNode.Close,
                                    PathNode.MoveTo(667.1f, 848.1f),
                                    PathNode.QuadTo(667.1f, 871.1f, 651.6f, 886.6f),
                                    PathNode.QuadTo(636.1f, 902.1f, 613.1f, 902.1f),
                                    PathNode.QuadTo(590.1f, 902.1f, 574.1f, 886.1f),
                                    PathNode.QuadTo(558.1f, 870.1f, 558.1f, 848.1f),
                                    PathNode.QuadTo(558.1f, 826.1f, 574.6f, 809.6f),
                                    PathNode.QuadTo(591.1f, 793.1f, 613.1f, 793.1f),
                                    PathNode.QuadTo(635.1f, 793.1f, 651.1f, 809.1f),
                                    PathNode.QuadTo(667.1f, 825.1f, 667.1f, 848.1f),
                                    PathNode.Close,
                                    PathNode.MoveTo(655.1f, 350.1f),
                                    PathNode.VerticalTo(709.1f),
                                    PathNode.QuadTo(655.1f, 721.1f, 648.1f, 728.6f),
                                    PathNode.QuadTo(641.1f, 736.1f, 626.1f, 736.1f),
                                    PathNode.HorizontalTo(598.1f),
                                    PathNode.QuadTo(585.1f, 736.1f, 577.6f, 728.1f),
                                    PathNode.QuadTo(570.1f, 720.1f, 570.1f, 709.1f),
                                    PathNode.VerticalTo(350.1f),
                                    PathNode.QuadTo(570.1f, 337.1f, 578.1f, 330.1f),
                                    PathNode.QuadTo(586.1f, 323.1f, 599.1f, 323.1f),
                                    PathNode.HorizontalTo(627.1f),
                                    PathNode.QuadTo(640.1f, 323.1f, 647.6f, 330.1f),
                                    PathNode.QuadTo(655.1f, 337.1f, 655.1f, 350.1f),
                                    PathNode.Close,
                                ),
                                fill = SolidColor(Color.Black),
                                fillAlpha = 1f,
                                pathFillType = PathFillType.NonZero,
                            )
                        }
                    }.build().also { InfoCache = it }
        }
    private var HelpCache: ImageVector? = null

    /** Mirrors the reference icon geometry for Help. */
    public val Help: ImageVector
        get() {
            HelpCache?.let { return it }
            return         ImageVector.Builder(
                        name = "ElegantIcons.Help",
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
                                    PathNode.QuadTo(102.1f, 473.1f, 171.1f, 356.1f),
                                    PathNode.QuadTo(240.1f, 239.1f, 357.1f, 170.6f),
                                    PathNode.QuadTo(474.1f, 102.1f, 613.1f, 102.1f),
                                    PathNode.QuadTo(752.1f, 102.1f, 869.1f, 170.6f),
                                    PathNode.QuadTo(986.1f, 239.1f, 1054.6f, 356.1f),
                                    PathNode.QuadTo(1123.1f, 473.1f, 1123.1f, 612.1f),
                                    PathNode.Close,
                                    PathNode.MoveTo(188.1f, 612.1f),
                                    PathNode.QuadTo(188.1f, 728.1f, 245.1f, 825.6f),
                                    PathNode.QuadTo(302.1f, 923.1f, 399.6f, 980.1f),
                                    PathNode.QuadTo(497.1f, 1037.1f, 613.1f, 1037.1f),
                                    PathNode.QuadTo(729.1f, 1037.1f, 826.6f, 980.1f),
                                    PathNode.QuadTo(924.1f, 923.1f, 981.1f, 825.6f),
                                    PathNode.QuadTo(1038.1f, 728.1f, 1038.1f, 612.1f),
                                    PathNode.QuadTo(1038.1f, 497.1f, 981.1f, 399.6f),
                                    PathNode.QuadTo(924.1f, 302.1f, 826.6f, 244.6f),
                                    PathNode.QuadTo(729.1f, 187.1f, 613.1f, 187.1f),
                                    PathNode.QuadTo(497.1f, 187.1f, 399.6f, 244.6f),
                                    PathNode.QuadTo(302.1f, 302.1f, 245.1f, 399.6f),
                                    PathNode.QuadTo(188.1f, 497.1f, 188.1f, 612.1f),
                                    PathNode.Close,
                                    PathNode.MoveTo(610.1f, 332.1f),
                                    PathNode.QuadTo(632.1f, 332.1f, 648.1f, 348.1f),
                                    PathNode.QuadTo(664.1f, 364.1f, 664.1f, 387.1f),
                                    PathNode.QuadTo(664.1f, 410.1f, 648.1f, 426.1f),
                                    PathNode.QuadTo(632.1f, 442.1f, 610.1f, 442.1f),
                                    PathNode.QuadTo(587.1f, 442.1f, 571.1f, 426.1f),
                                    PathNode.QuadTo(555.1f, 410.1f, 555.1f, 387.1f),
                                    PathNode.QuadTo(555.1f, 364.1f, 571.1f, 348.1f),
                                    PathNode.QuadTo(587.1f, 332.1f, 610.1f, 332.1f),
                                    PathNode.Close,
                                    PathNode.MoveTo(653.1f, 515.1f),
                                    PathNode.QuadTo(661.1f, 543.1f, 674.1f, 564.6f),
                                    PathNode.QuadTo(687.1f, 586.1f, 704.1f, 601.1f),
                                    PathNode.LineTo(733.1f, 627.1f),
                                    PathNode.QuadTo(784.1f, 675.1f, 784.1f, 743.1f),
                                    PathNode.QuadTo(784.1f, 793.1f, 759.1f, 830.1f),
                                    PathNode.QuadTo(734.1f, 867.1f, 695.6f, 886.6f),
                                    PathNode.QuadTo(657.1f, 906.1f, 617.1f, 906.1f),
                                    PathNode.QuadTo(569.1f, 906.1f, 529.6f, 883.6f),
                                    PathNode.QuadTo(490.1f, 861.1f, 468.1f, 825.6f),
                                    PathNode.QuadTo(446.1f, 790.1f, 446.1f, 751.1f),
                                    PathNode.QuadTo(446.1f, 738.1f, 451.1f, 732.1f),
                                    PathNode.QuadTo(456.1f, 726.1f, 467.1f, 726.1f),
                                    PathNode.HorizontalTo(506.1f),
                                    PathNode.QuadTo(520.1f, 726.1f, 526.1f, 733.6f),
                                    PathNode.QuadTo(532.1f, 741.1f, 534.1f, 755.1f),
                                    PathNode.QuadTo(538.1f, 788.1f, 562.6f, 804.6f),
                                    PathNode.QuadTo(587.1f, 821.1f, 615.1f, 821.1f),
                                    PathNode.QuadTo(645.1f, 821.1f, 671.6f, 801.1f),
                                    PathNode.QuadTo(698.1f, 781.1f, 698.1f, 745.1f),
                                    PathNode.QuadTo(698.1f, 715.1f, 677.1f, 695.1f),
                                    PathNode.LineTo(636.1f, 654.1f),
                                    PathNode.QuadTo(582.1f, 600.1f, 567.1f, 519.1f),
                                    PathNode.QuadTo(565.1f, 509.1f, 571.1f, 502.1f),
                                    PathNode.QuadTo(577.1f, 495.1f, 588.1f, 495.1f),
                                    PathNode.HorizontalTo(624.1f),
                                    PathNode.QuadTo(636.1f, 495.1f, 643.1f, 499.6f),
                                    PathNode.QuadTo(650.1f, 504.1f, 653.1f, 515.1f),
                                    PathNode.Close,
                                ),
                                fill = SolidColor(Color.Black),
                                fillAlpha = 1f,
                                pathFillType = PathFillType.NonZero,
                            )
                        }
                    }.build().also { HelpCache = it }
        }
    private var GridCache: ImageVector? = null

    /** Mirrors the reference icon geometry for Grid. */
    public val Grid: ImageVector
        get() {
            GridCache?.let { return it }
            return         ImageVector.Builder(
                        name = "ElegantIcons.Grid",
                        defaultWidth = 24.0f.dp,
                        defaultHeight = 24.0f.dp,
                        viewportWidth = 1120.8f,
                        viewportHeight = 1120.8f,
                    ).apply {
                        group(scaleY = -1.0f, translationY = 1120.8f) {
                            addPath(
                                pathData = listOf(
                                    PathNode.MoveTo(416.8f, 890.1f),
                                    PathNode.QuadTo(425.3f, 886.9f, 431.5f, 881.0f),
                                    PathNode.QuadTo(437.6f, 875.2f, 439.8f, 867.5f),
                                    PathNode.QuadTo(443.3f, 856.5f, 443.3f, 819.8f),
                                    PathNode.VerticalTo(751.2f),
                                    PathNode.QuadTo(443.3f, 714.2f, 439.8f, 703.1f),
                                    PathNode.QuadTo(437.6f, 695.4f, 431.3f, 689.6f),
                                    PathNode.QuadTo(424.9f, 683.7f, 416.8f, 681.0f),
                                    PathNode.QuadTo(404.5f, 677.0f, 370.5f, 677.0f),
                                    PathNode.HorizontalTo(359.4f),
                                    PathNode.HorizontalTo(311.9f),
                                    PathNode.HorizontalTo(300.9f),
                                    PathNode.QuadTo(266.8f, 677.0f, 254.5f, 681.0f),
                                    PathNode.QuadTo(246.0f, 683.7f, 239.7f, 689.6f),
                                    PathNode.QuadTo(233.4f, 695.4f, 231.1f, 703.1f),
                                    PathNode.QuadTo(228.0f, 714.9f, 228.0f, 751.2f),
                                    PathNode.VerticalTo(819.8f),
                                    PathNode.QuadTo(228.0f, 838.7f, 228.7f, 849.9f),
                                    PathNode.QuadTo(229.4f, 861.2f, 231.1f, 867.5f),
                                    PathNode.QuadTo(233.4f, 875.2f, 239.7f, 881.0f),
                                    PathNode.QuadTo(246.0f, 886.9f, 254.5f, 890.1f),
                                    PathNode.QuadTo(262.9f, 892.7f, 278.4f, 893.4f),
                                    PathNode.QuadTo(293.8f, 894.1f, 311.9f, 894.1f),
                                    PathNode.HorizontalTo(359.4f),
                                    PathNode.QuadTo(377.5f, 894.1f, 392.9f, 893.4f),
                                    PathNode.QuadTo(408.4f, 892.7f, 416.8f, 890.1f),
                                    PathNode.Close,
                                    PathNode.MoveTo(447.8f, 601.9f),
                                    PathNode.QuadTo(471.8f, 610.1f, 489.4f, 628.7f),
                                    PathNode.QuadTo(507.1f, 647.2f, 517.6f, 672.1f),
                                    PathNode.QuadTo(524.6f, 689.1f, 526.5f, 710.7f),
                                    PathNode.QuadTo(528.5f, 732.4f, 528.5f, 767.9f),
                                    PathNode.VerticalTo(803.1f),
                                    PathNode.QuadTo(528.5f, 838.6f, 526.5f, 860.0f),
                                    PathNode.QuadTo(524.6f, 881.5f, 517.6f, 898.6f),
                                    PathNode.QuadTo(507.1f, 923.4f, 489.4f, 941.9f),
                                    PathNode.QuadTo(471.8f, 960.5f, 447.8f, 969.2f),
                                    PathNode.QuadTo(418.8f, 979.2f, 373.4f, 979.2f),
                                    PathNode.HorizontalTo(297.8f),
                                    PathNode.QuadTo(252.5f, 979.2f, 223.5f, 969.2f),
                                    PathNode.QuadTo(199.5f, 960.5f, 181.6f, 941.8f),
                                    PathNode.QuadTo(163.8f, 923.0f, 153.8f, 898.6f),
                                    PathNode.QuadTo(146.7f, 881.5f, 144.8f, 860.0f),
                                    PathNode.QuadTo(142.8f, 838.6f, 142.8f, 803.1f),
                                    PathNode.VerticalTo(767.9f),
                                    PathNode.QuadTo(142.8f, 732.4f, 144.8f, 710.7f),
                                    PathNode.QuadTo(146.7f, 689.1f, 153.8f, 672.1f),
                                    PathNode.QuadTo(163.8f, 647.6f, 181.6f, 628.8f),
                                    PathNode.QuadTo(199.5f, 610.1f, 223.5f, 601.9f),
                                    PathNode.QuadTo(251.1f, 591.8f, 297.8f, 591.8f),
                                    PathNode.HorizontalTo(373.4f),
                                    PathNode.QuadTo(420.6f, 591.8f, 447.8f, 601.9f),
                                    PathNode.Close,
                                    PathNode.MoveTo(866.3f, 890.1f),
                                    PathNode.QuadTo(874.8f, 886.9f, 881.1f, 881.0f),
                                    PathNode.QuadTo(887.4f, 875.2f, 889.7f, 867.5f),
                                    PathNode.QuadTo(891.4f, 861.2f, 892.1f, 849.9f),
                                    PathNode.QuadTo(892.8f, 838.7f, 892.8f, 819.8f),
                                    PathNode.VerticalTo(751.2f),
                                    PathNode.QuadTo(892.8f, 732.3f, 892.1f, 720.8f),
                                    PathNode.QuadTo(891.4f, 709.4f, 889.7f, 703.1f),
                                    PathNode.QuadTo(887.4f, 695.4f, 881.1f, 689.6f),
                                    PathNode.QuadTo(874.8f, 683.7f, 866.3f, 681.0f),
                                    PathNode.QuadTo(854.0f, 677.0f, 819.9f, 677.0f),
                                    PathNode.HorizontalTo(808.8f),
                                    PathNode.HorizontalTo(761.4f),
                                    PathNode.HorizontalTo(750.3f),
                                    PathNode.QuadTo(716.2f, 677.0f, 704.0f, 681.0f),
                                    PathNode.QuadTo(695.4f, 683.7f, 689.1f, 689.6f),
                                    PathNode.QuadTo(682.9f, 695.4f, 681.0f, 703.1f),
                                    PathNode.QuadTo(677.5f, 714.2f, 677.5f, 751.2f),
                                    PathNode.VerticalTo(819.8f),
                                    PathNode.QuadTo(677.5f, 856.5f, 681.0f, 867.5f),
                                    PathNode.QuadTo(683.2f, 875.2f, 689.5f, 881.0f),
                                    PathNode.QuadTo(695.8f, 886.9f, 704.0f, 890.1f),
                                    PathNode.QuadTo(712.4f, 892.7f, 727.9f, 893.4f),
                                    PathNode.QuadTo(743.3f, 894.1f, 761.4f, 894.1f),
                                    PathNode.HorizontalTo(808.8f),
                                    PathNode.QuadTo(826.9f, 894.1f, 842.4f, 893.4f),
                                    PathNode.QuadTo(857.9f, 892.7f, 866.3f, 890.1f),
                                    PathNode.Close,
                                    PathNode.MoveTo(897.3f, 601.9f),
                                    PathNode.QuadTo(921.2f, 610.1f, 939.1f, 628.8f),
                                    PathNode.QuadTo(957.0f, 647.6f, 967.0f, 672.1f),
                                    PathNode.QuadTo(974.1f, 689.1f, 976.0f, 710.7f),
                                    PathNode.QuadTo(978.0f, 732.4f, 978.0f, 767.9f),
                                    PathNode.VerticalTo(803.1f),
                                    PathNode.QuadTo(978.0f, 838.6f, 976.0f, 860.0f),
                                    PathNode.QuadTo(974.1f, 881.5f, 967.0f, 898.6f),
                                    PathNode.QuadTo(957.0f, 923.0f, 939.1f, 941.8f),
                                    PathNode.QuadTo(921.2f, 960.5f, 897.3f, 969.2f),
                                    PathNode.QuadTo(868.2f, 979.2f, 822.9f, 979.2f),
                                    PathNode.HorizontalTo(747.3f),
                                    PathNode.QuadTo(702.0f, 979.2f, 673.0f, 969.2f),
                                    PathNode.QuadTo(649.0f, 960.5f, 631.3f, 941.9f),
                                    PathNode.QuadTo(613.6f, 923.4f, 603.2f, 898.6f),
                                    PathNode.QuadTo(596.2f, 881.5f, 594.3f, 860.0f),
                                    PathNode.QuadTo(592.3f, 838.6f, 592.3f, 803.1f),
                                    PathNode.VerticalTo(767.9f),
                                    PathNode.QuadTo(592.3f, 732.4f, 594.3f, 710.7f),
                                    PathNode.QuadTo(596.2f, 689.1f, 603.2f, 672.1f),
                                    PathNode.QuadTo(613.6f, 647.2f, 631.3f, 628.7f),
                                    PathNode.QuadTo(649.0f, 610.1f, 673.0f, 601.9f),
                                    PathNode.QuadTo(700.6f, 591.8f, 747.3f, 591.8f),
                                    PathNode.HorizontalTo(822.9f),
                                    PathNode.QuadTo(870.1f, 591.8f, 897.3f, 601.9f),
                                    PathNode.Close,
                                    PathNode.MoveTo(370.5f, 443.8f),
                                    PathNode.QuadTo(404.5f, 443.8f, 416.8f, 439.8f),
                                    PathNode.QuadTo(424.9f, 437.1f, 431.3f, 431.2f),
                                    PathNode.QuadTo(437.6f, 425.3f, 439.8f, 417.7f),
                                    PathNode.QuadTo(443.3f, 406.6f, 443.3f, 369.6f),
                                    PathNode.VerticalTo(301.0f),
                                    PathNode.QuadTo(443.3f, 264.3f, 439.8f, 253.3f),
                                    PathNode.QuadTo(437.6f, 245.6f, 431.5f, 239.7f),
                                    PathNode.QuadTo(425.3f, 233.9f, 416.8f, 230.7f),
                                    PathNode.QuadTo(408.4f, 228.1f, 392.9f, 227.4f),
                                    PathNode.QuadTo(377.5f, 226.7f, 359.4f, 226.7f),
                                    PathNode.HorizontalTo(311.9f),
                                    PathNode.QuadTo(293.8f, 226.7f, 278.4f, 227.4f),
                                    PathNode.QuadTo(262.9f, 228.1f, 254.5f, 230.7f),
                                    PathNode.QuadTo(246.0f, 233.9f, 239.7f, 239.7f),
                                    PathNode.QuadTo(233.4f, 245.6f, 231.1f, 253.3f),
                                    PathNode.QuadTo(228.0f, 265.1f, 228.0f, 301.0f),
                                    PathNode.VerticalTo(369.6f),
                                    PathNode.QuadTo(228.0f, 388.5f, 228.7f, 400.0f),
                                    PathNode.QuadTo(229.4f, 411.4f, 231.1f, 417.7f),
                                    PathNode.QuadTo(233.4f, 425.3f, 239.7f, 431.2f),
                                    PathNode.QuadTo(246.0f, 437.1f, 254.5f, 439.8f),
                                    PathNode.QuadTo(266.8f, 443.8f, 300.9f, 443.8f),
                                    PathNode.HorizontalTo(311.9f),
                                    PathNode.HorizontalTo(359.4f),
                                    PathNode.Close,
                                    PathNode.MoveTo(447.8f, 151.6f),
                                    PathNode.QuadTo(471.8f, 160.3f, 489.4f, 178.9f),
                                    PathNode.QuadTo(507.1f, 197.4f, 517.6f, 222.2f),
                                    PathNode.QuadTo(524.6f, 239.3f, 526.5f, 260.8f),
                                    PathNode.QuadTo(528.5f, 282.2f, 528.5f, 317.7f),
                                    PathNode.VerticalTo(352.9f),
                                    PathNode.QuadTo(528.5f, 388.3f, 526.5f, 410.0f),
                                    PathNode.QuadTo(524.6f, 431.7f, 517.6f, 448.7f),
                                    PathNode.QuadTo(507.1f, 473.6f, 489.4f, 492.1f),
                                    PathNode.QuadTo(471.8f, 510.7f, 447.8f, 518.9f),
                                    PathNode.QuadTo(420.6f, 529.0f, 373.4f, 529.0f),
                                    PathNode.HorizontalTo(297.8f),
                                    PathNode.QuadTo(251.1f, 529.0f, 223.5f, 518.9f),
                                    PathNode.QuadTo(199.5f, 510.7f, 181.6f, 491.9f),
                                    PathNode.QuadTo(163.8f, 473.2f, 153.8f, 448.7f),
                                    PathNode.QuadTo(146.7f, 431.7f, 144.8f, 410.0f),
                                    PathNode.QuadTo(142.8f, 388.3f, 142.8f, 352.9f),
                                    PathNode.VerticalTo(317.7f),
                                    PathNode.QuadTo(142.8f, 282.2f, 144.8f, 260.8f),
                                    PathNode.QuadTo(146.7f, 239.3f, 153.8f, 222.2f),
                                    PathNode.QuadTo(163.8f, 197.7f, 181.6f, 179.0f),
                                    PathNode.QuadTo(199.5f, 160.3f, 223.5f, 151.6f),
                                    PathNode.QuadTo(252.5f, 141.6f, 297.8f, 141.6f),
                                    PathNode.HorizontalTo(373.4f),
                                    PathNode.QuadTo(418.8f, 141.6f, 447.8f, 151.6f),
                                    PathNode.Close,
                                    PathNode.MoveTo(819.9f, 443.8f),
                                    PathNode.QuadTo(854.0f, 443.8f, 866.3f, 439.8f),
                                    PathNode.QuadTo(874.8f, 437.1f, 881.1f, 431.2f),
                                    PathNode.QuadTo(887.4f, 425.3f, 889.7f, 417.7f),
                                    PathNode.QuadTo(891.4f, 411.4f, 892.1f, 400.0f),
                                    PathNode.QuadTo(892.8f, 388.5f, 892.8f, 369.6f),
                                    PathNode.VerticalTo(301.0f),
                                    PathNode.QuadTo(892.8f, 282.0f, 892.1f, 270.8f),
                                    PathNode.QuadTo(891.4f, 259.6f, 889.7f, 253.3f),
                                    PathNode.QuadTo(887.4f, 245.6f, 881.1f, 239.7f),
                                    PathNode.QuadTo(874.8f, 233.9f, 866.3f, 230.7f),
                                    PathNode.QuadTo(857.9f, 228.1f, 842.4f, 227.4f),
                                    PathNode.QuadTo(826.9f, 226.7f, 808.8f, 226.7f),
                                    PathNode.HorizontalTo(761.4f),
                                    PathNode.QuadTo(743.3f, 226.7f, 727.9f, 227.4f),
                                    PathNode.QuadTo(712.4f, 228.1f, 704.0f, 230.7f),
                                    PathNode.QuadTo(695.4f, 233.9f, 689.1f, 239.7f),
                                    PathNode.QuadTo(682.9f, 245.6f, 681.0f, 253.3f),
                                    PathNode.QuadTo(677.5f, 264.3f, 677.5f, 301.0f),
                                    PathNode.VerticalTo(369.6f),
                                    PathNode.QuadTo(677.5f, 406.6f, 681.0f, 417.7f),
                                    PathNode.QuadTo(683.2f, 425.3f, 689.5f, 431.2f),
                                    PathNode.QuadTo(695.8f, 437.1f, 704.0f, 439.8f),
                                    PathNode.QuadTo(716.2f, 443.8f, 750.3f, 443.8f),
                                    PathNode.HorizontalTo(761.4f),
                                    PathNode.HorizontalTo(808.8f),
                                    PathNode.Close,
                                    PathNode.MoveTo(897.3f, 151.6f),
                                    PathNode.QuadTo(921.2f, 160.3f, 939.1f, 179.0f),
                                    PathNode.QuadTo(957.0f, 197.7f, 967.0f, 222.2f),
                                    PathNode.QuadTo(974.1f, 239.3f, 976.0f, 260.8f),
                                    PathNode.QuadTo(978.0f, 282.2f, 978.0f, 317.7f),
                                    PathNode.VerticalTo(352.9f),
                                    PathNode.QuadTo(978.0f, 388.3f, 976.0f, 410.0f),
                                    PathNode.QuadTo(974.1f, 431.7f, 967.0f, 448.7f),
                                    PathNode.QuadTo(957.0f, 473.2f, 939.1f, 491.9f),
                                    PathNode.QuadTo(921.2f, 510.7f, 897.3f, 518.9f),
                                    PathNode.QuadTo(870.1f, 529.0f, 822.9f, 529.0f),
                                    PathNode.HorizontalTo(747.3f),
                                    PathNode.QuadTo(700.6f, 529.0f, 673.0f, 518.9f),
                                    PathNode.QuadTo(649.0f, 510.7f, 631.3f, 492.1f),
                                    PathNode.QuadTo(613.6f, 473.6f, 603.2f, 448.7f),
                                    PathNode.QuadTo(596.2f, 431.7f, 594.3f, 410.0f),
                                    PathNode.QuadTo(592.3f, 388.3f, 592.3f, 352.9f),
                                    PathNode.VerticalTo(317.7f),
                                    PathNode.QuadTo(592.3f, 282.2f, 594.3f, 260.8f),
                                    PathNode.QuadTo(596.2f, 239.3f, 603.2f, 222.2f),
                                    PathNode.QuadTo(613.6f, 197.4f, 631.3f, 178.9f),
                                    PathNode.QuadTo(649.0f, 160.3f, 673.0f, 151.6f),
                                    PathNode.QuadTo(702.0f, 141.6f, 747.3f, 141.6f),
                                    PathNode.HorizontalTo(822.9f),
                                    PathNode.QuadTo(868.2f, 141.6f, 897.3f, 151.6f),
                                    PathNode.Close,
                                ),
                                fill = SolidColor(Color.Black),
                                fillAlpha = 1f,
                                pathFillType = PathFillType.NonZero,
                            )
                        }
                    }.build().also { GridCache = it }
        }
    private var CopyCache: ImageVector? = null

    /** Mirrors the reference icon geometry for Copy. */
    public val Copy: ImageVector
        get() {
            CopyCache?.let { return it }
            return         ImageVector.Builder(
                        name = "ElegantIcons.Copy",
                        defaultWidth = 24.0f.dp,
                        defaultHeight = 24.0f.dp,
                        viewportWidth = 1251.6f,
                        viewportHeight = 1251.6f,
                    ).apply {
                        group(scaleY = -1.0f, translationY = 1251.6f) {
                            addPath(
                                pathData = listOf(
                                    PathNode.MoveTo(796.3f, 145.7f),
                                    PathNode.QuadTo(848.3f, 172.6f, 872.7f, 222.1f),
                                    PathNode.QuadTo(887.2f, 250.1f, 890.5f, 289.5f),
                                    PathNode.QuadTo(893.9f, 329.0f, 893.9f, 420.9f),
                                    PathNode.VerticalTo(597.9f),
                                    PathNode.QuadTo(893.9f, 689.8f, 890.5f, 729.3f),
                                    PathNode.QuadTo(887.2f, 768.8f, 872.7f, 796.8f),
                                    PathNode.QuadTo(846.5f, 846.9f, 796.3f, 872.2f),
                                    PathNode.QuadTo(769.3f, 887.5f, 729.8f, 891.0f),
                                    PathNode.QuadTo(690.3f, 894.4f, 598.3f, 894.4f),
                                    PathNode.HorizontalTo(421.4f),
                                    PathNode.QuadTo(329.4f, 894.4f, 290.0f, 891.0f),
                                    PathNode.QuadTo(250.6f, 887.5f, 222.6f, 872.2f),
                                    PathNode.QuadTo(174.1f, 847.8f, 146.2f, 796.8f),
                                    PathNode.QuadTo(131.7f, 768.8f, 128.4f, 729.3f),
                                    PathNode.QuadTo(125.0f, 689.8f, 125.0f, 597.9f),
                                    PathNode.VerticalTo(420.9f),
                                    PathNode.QuadTo(125.0f, 329.0f, 128.4f, 289.5f),
                                    PathNode.QuadTo(131.7f, 250.1f, 146.2f, 222.1f),
                                    PathNode.QuadTo(172.3f, 171.8f, 222.6f, 145.7f),
                                    PathNode.QuadTo(250.6f, 131.2f, 290.0f, 127.9f),
                                    PathNode.QuadTo(329.4f, 124.5f, 421.4f, 124.5f),
                                    PathNode.HorizontalTo(598.3f),
                                    PathNode.QuadTo(690.3f, 124.5f, 729.8f, 127.9f),
                                    PathNode.QuadTo(769.3f, 131.2f, 796.3f, 145.7f),
                                    PathNode.Close,
                                    PathNode.MoveTo(264.6f, 221.7f),
                                    PathNode.QuadTo(236.3f, 236.8f, 222.4f, 265.0f),
                                    PathNode.QuadTo(213.9f, 281.0f, 212.0f, 302.8f),
                                    PathNode.QuadTo(210.1f, 324.6f, 210.1f, 376.9f),
                                    PathNode.VerticalTo(640.9f),
                                    PathNode.QuadTo(210.1f, 693.5f, 211.9f, 716.2f),
                                    PathNode.QuadTo(213.8f, 738.8f, 222.2f, 753.8f),
                                    PathNode.QuadTo(229.7f, 767.1f, 240.5f, 778.2f),
                                    PathNode.QuadTo(251.3f, 789.4f, 264.6f, 796.1f),
                                    PathNode.QuadTo(279.6f, 804.5f, 302.3f, 806.4f),
                                    PathNode.QuadTo(324.9f, 808.3f, 377.4f, 808.3f),
                                    PathNode.HorizontalTo(641.4f),
                                    PathNode.QuadTo(694.0f, 808.3f, 716.6f, 806.4f),
                                    PathNode.QuadTo(739.3f, 804.5f, 754.3f, 796.1f),
                                    PathNode.QuadTo(767.6f, 789.4f, 778.7f, 778.2f),
                                    PathNode.QuadTo(789.8f, 767.1f, 796.5f, 753.8f),
                                    PathNode.QuadTo(805.0f, 738.8f, 806.9f, 716.2f),
                                    PathNode.QuadTo(808.8f, 693.5f, 808.8f, 640.9f),
                                    PathNode.VerticalTo(376.9f),
                                    PathNode.QuadTo(808.8f, 324.6f, 806.9f, 302.8f),
                                    PathNode.QuadTo(805.0f, 281.0f, 796.5f, 265.0f),
                                    PathNode.QuadTo(782.3f, 236.6f, 754.3f, 221.7f),
                                    PathNode.QuadTo(739.3f, 213.3f, 716.6f, 211.4f),
                                    PathNode.QuadTo(694.0f, 209.6f, 641.4f, 209.6f),
                                    PathNode.HorizontalTo(377.4f),
                                    PathNode.QuadTo(324.9f, 209.6f, 302.3f, 211.4f),
                                    PathNode.QuadTo(279.6f, 213.3f, 264.6f, 221.7f),
                                    PathNode.Close,
                                    PathNode.MoveTo(943.1f, 1127.1f),
                                    PathNode.HorizontalTo(591.1f),
                                    PathNode.QuadTo(533.5f, 1127.1f, 486.4f, 1094.6f),
                                    PathNode.QuadTo(439.3f, 1062.2f, 418.1f, 1006.1f),
                                    PathNode.QuadTo(411.4f, 988.4f, 419.5f, 977.7f),
                                    PathNode.QuadTo(427.7f, 966.9f, 445.0f, 966.9f),
                                    PathNode.HorizontalTo(474.7f),
                                    PathNode.QuadTo(485.7f, 966.9f, 491.8f, 971.3f),
                                    PathNode.QuadTo(497.8f, 975.7f, 508.2f, 993.4f),
                                    PathNode.QuadTo(522.0f, 1015.5f, 545.0f, 1028.8f),
                                    PathNode.QuadTo(568.0f, 1042.0f, 595.2f, 1042.0f),
                                    PathNode.HorizontalTo(939.2f),
                                    PathNode.QuadTo(981.4f, 1042.0f, 1011.5f, 1012.0f),
                                    PathNode.QuadTo(1041.5f, 981.9f, 1041.5f, 938.7f),
                                    PathNode.VerticalTo(595.3f),
                                    PathNode.QuadTo(1041.5f, 568.1f, 1028.9f, 546.0f),
                                    PathNode.QuadTo(1016.1f, 524.0f, 995.1f, 510.2f),
                                    PathNode.QuadTo(991.3f, 507.3f, 986.7f, 504.5f),
                                    PathNode.QuadTo(972.6f, 495.7f, 969.6f, 490.1f),
                                    PathNode.QuadTo(966.6f, 484.4f, 966.6f, 465.1f),
                                    PathNode.VerticalTo(441.4f),
                                    PathNode.QuadTo(966.6f, 426.7f, 979.0f, 418.9f),
                                    PathNode.QuadTo(991.5f, 411.1f, 1007.2f, 418.7f),
                                    PathNode.QuadTo(1063.1f, 441.2f, 1094.8f, 486.9f),
                                    PathNode.QuadTo(1126.6f, 532.6f, 1126.6f, 591.6f),
                                    PathNode.VerticalTo(943.5f),
                                    PathNode.QuadTo(1126.6f, 993.7f, 1101.9f, 1035.7f),
                                    PathNode.QuadTo(1077.2f, 1077.7f, 1035.2f, 1102.4f),
                                    PathNode.QuadTo(993.2f, 1127.1f, 943.1f, 1127.1f),
                                    PathNode.Close,
                                ),
                                fill = SolidColor(Color.Black),
                                fillAlpha = 1f,
                                pathFillType = PathFillType.NonZero,
                            )
                        }
                    }.build().also { CopyCache = it }
        }

    public val All: List<ImageVector> = listOf(
        ArrowLeft,
        ArrowRight,
        ArrowUp,
        ArrowDown,
        ChevronUp,
        ChevronDown,
        Plus,
        Minus,
        MoreVert,
        MoreHoriz,
        Person,
        Notifications,
        Star,
        Heart,
        Check,
        Close,
        Search,
        Edit,
        Delete,
        Share,
        Home,
        Settings,
        ChevronLeft,
        ChevronRight,
        Refresh,
        Download,
        VolumeUp,
        VolumeOff,
        Filter,
        Send,
        Reply,
        Forward,
        Lock,
        Unlock,
        Location,
        Image,
        Play,
        Pause,
        Info,
        Help,
        Grid,
        Copy,
    )

    /** Three polyline points for a chevron in a 24-grid of [size]. */
    internal fun chevronPolyline(direction: Float, vertical: Boolean, size: Float): List<Offset> {
        val tip = size / 2f
        val arm = size * 0.22f
        return if (vertical) {
            val vertex = if (direction < 0) tip - arm else tip + arm
            val wing = if (direction < 0) tip + 2f * arm else tip - 2f * arm
            listOf(
                Offset(tip - arm, wing),
                Offset(tip, vertex),
                Offset(tip + arm, wing),
            )
        } else {
            val vertex = if (direction < 0) tip - arm else tip + arm
            val wing = if (direction < 0) tip + 2f * arm else tip - 2f * arm
            listOf(
                Offset(wing, tip - arm),
                Offset(vertex, tip),
                Offset(wing, tip + arm),
            )
        }
    }

    /** Four endpoints of the close (X) glyph for a 24-grid of [size] with [insetFraction] inset. */
    internal fun closeEndpoints(size: Float, insetFraction: Float): List<Offset> {
        val inset = size * insetFraction.coerceIn(0.2f, 0.45f)
        return listOf(
            Offset(inset, inset),
            Offset(size - inset, size - inset),
            Offset(size - inset, inset),
            Offset(inset, size - inset),
        )
    }

    /** Three polyline points of the check glyph for a 24-grid of [size]. */
    internal fun checkPolyline(size: Float): List<Offset> = listOf(
        Offset(size * 0.17f, size * 0.52f),
        Offset(size * 0.42f, size * 0.75f),
        Offset(size * 0.84f, size * 0.26f),
    )

    /** Four endpoints of the plus glyph for a 24-grid of [size]. */
    internal fun plusEndpoints(size: Float): List<Offset> = listOf(
        Offset(size / 2f, size * 0.16f),
        Offset(size / 2f, size * 0.84f),
        Offset(size * 0.16f, size / 2f),
        Offset(size * 0.84f, size / 2f),
    )

    /** Ten points of a five-point star centered at [center] with the given radii. */
    internal fun starPoints(center: Offset, outerRadius: Float, innerRadius: Float): List<Offset> {
        val points = mutableListOf<Offset>()
        for (index in 0 until 10) {
            val angle = kotlin.math.PI * (90.0 + index * 36.0) / 180.0
            val radius = if (index % 2 == 0) outerRadius else innerRadius
            points.add(
                Offset(
                    x = center.x + (radius * kotlin.math.cos(angle)).toFloat(),
                    y = center.y - (radius * kotlin.math.sin(angle)).toFloat(),
                ),
            )
        }
        return points
    }

    /** Person geometry: head center + radius, shoulder arc endpoints. */
    internal fun personGeometry(size: Float): PersonGeometry = PersonGeometry(
        headCenter = Offset(size / 2f, size * 0.36f),
        headRadius = size * 0.17f,
        shoulderLeft = Offset(size * 0.14f, size * 0.88f),
        shoulderRight = Offset(size * 0.86f, size * 0.88f),
    )

    /** Geometry of the person icon. */
    internal data class PersonGeometry(
        val headCenter: Offset,
        val headRadius: Float,
        val shoulderLeft: Offset,
        val shoulderRight: Offset,
    )
}

private fun vectorIcon(name: String, block: androidx.compose.ui.graphics.vector.ImageVector.Builder.() -> Unit): ImageVector =
    ImageVector.Builder(
        name = name,
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f,
    ).apply(block).build()

private fun filledPath(
    builder: androidx.compose.ui.graphics.vector.ImageVector.Builder,
    block: androidx.compose.ui.graphics.vector.PathBuilder.() -> Unit,
) {
    builder.path(fill = SolidColor(Color.Black), pathBuilder = block)
}

private fun strokedPath(
    builder: androidx.compose.ui.graphics.vector.ImageVector.Builder,
    width: Float,
    block: androidx.compose.ui.graphics.vector.PathBuilder.() -> Unit,
) {
    builder.path(
        fill = null,
        stroke = SolidColor(Color.Black),
        strokeLineWidth = width,
        strokeLineCap = StrokeCap.Round,
        strokeLineJoin = StrokeJoin.Round,
        pathBuilder = block,
    )
}

private fun arrowIcon(direction: Float, vertical: Boolean = false, name: String): ImageVector = vectorIcon(name) {
    if (vertical) {
        strokedPath(this, 2.4f) {
            moveTo(12f, 3.5f + if (direction > 0) 1f else 0f)
            lineTo(12f, if (direction > 0) 20.5f else 3.5f)
        }
        filledPath(this) {
            moveTo(7.5f, if (direction > 0) 13f else 8.5f)
            lineTo(12f, if (direction > 0) 19f else 4f)
            lineTo(16.5f, if (direction > 0) 13f else 8.5f)
            close()
        }
    } else {
        strokedPath(this, 2.4f) {
            moveTo(if (direction > 0) 3.5f else 20.5f, 12f)
            lineTo(if (direction > 0) 20.5f else 3.5f, 12f)
        }
        filledPath(this) {
            moveTo(if (direction > 0) 11f else 8.5f, 7.5f)
            lineTo(if (direction > 0) 20f else 4f, 12f)
            lineTo(if (direction > 0) 11f else 8.5f, 16.5f)
            close()
        }
    }
}

private fun chevronIcon(direction: Float, vertical: Boolean = false, name: String): ImageVector = vectorIcon(name) {
    val points = ElegantIcons.chevronPolyline(direction, vertical, 24f)
    strokedPath(this, 2.2f) {
        moveTo(points[0].x, points[0].y)
        lineTo(points[1].x, points[1].y)
        lineTo(points[2].x, points[2].y)
    }
}

private fun checkIcon(name: String): ImageVector = vectorIcon(name) {
    val points = ElegantIcons.checkPolyline(24f)
    strokedPath(this, 2.6f) {
        moveTo(points[0].x, points[0].y)
        lineTo(points[1].x, points[1].y)
        lineTo(points[2].x, points[2].y)
    }
}

private fun closeIcon(name: String): ImageVector = vectorIcon(name) {
    val endpoints = ElegantIcons.closeEndpoints(24f, 0.25f)
    strokedPath(this, 2.2f) {
        moveTo(endpoints[0].x, endpoints[0].y)
        lineTo(endpoints[1].x, endpoints[1].y)
        moveTo(endpoints[2].x, endpoints[2].y)
        lineTo(endpoints[3].x, endpoints[3].y)
    }
}

private fun plusIcon(name: String): ImageVector = vectorIcon(name) {
    val endpoints = ElegantIcons.plusEndpoints(24f)
    strokedPath(this, 2.2f) {
        moveTo(endpoints[0].x, endpoints[0].y)
        lineTo(endpoints[1].x, endpoints[1].y)
        moveTo(endpoints[2].x, endpoints[2].y)
        lineTo(endpoints[3].x, endpoints[3].y)
    }
}

private fun minusIcon(name: String): ImageVector = vectorIcon(name) {
    strokedPath(this, 2.2f) {
        moveTo(4f, 12f)
        lineTo(20f, 12f)
    }
}

private fun searchIcon(name: String): ImageVector = vectorIcon(name) {
    strokedPath(this, 2.2f) {
        moveTo(15.4f, 10.4f)
        arcTo(
            horizontalEllipseRadius = 5.4f,
            verticalEllipseRadius = 5.4f,
            theta = 0f,
            isMoreThanHalf = false,
            isPositiveArc = true,
            x1 = 4.6f,
            y1 = 10.4f,
        )
        arcTo(
            horizontalEllipseRadius = 5.4f,
            verticalEllipseRadius = 5.4f,
            theta = 0f,
            isMoreThanHalf = false,
            isPositiveArc = true,
            x1 = 15.4f,
            y1 = 10.4f,
        )
        moveTo(14.2f, 14.2f)
        lineTo(20f, 20f)
    }
}

private fun editIcon(name: String): ImageVector = vectorIcon(name) {
    filledPath(this) {
        moveTo(16.6f, 3.4f)
        lineTo(20.6f, 7.4f)
        lineTo(6.5f, 21.5f)
        lineTo(2.5f, 21.5f)
        lineTo(2.5f, 17.5f)
        close()
    }
}

private fun deleteIcon(name: String): ImageVector = vectorIcon(name) {
    filledPath(this) {
        moveTo(4f, 7f)
        lineTo(20f, 7f)
        lineTo(20f, 9f)
        lineTo(4f, 9f)
        close()
    }
    filledPath(this) {
        moveTo(9.5f, 3.5f)
        lineTo(14.5f, 3.5f)
        lineTo(15.5f, 7f)
        lineTo(8.5f, 7f)
        close()
    }
    filledPath(this) {
        moveTo(6.5f, 10f)
        lineTo(17.5f, 10f)
        lineTo(16.5f, 20f)
        lineTo(7.5f, 20f)
        close()
    }
}

private fun shareIcon(name: String): ImageVector = vectorIcon(name) {
    strokedPath(this, 2f) {
        moveTo(7.5f, 12f)
        lineTo(16.5f, 6.5f)
        moveTo(7.5f, 12f)
        lineTo(16.5f, 17.5f)
    }
    fun dot(builder: androidx.compose.ui.graphics.vector.ImageVector.Builder, x: Float, y: Float) {
        filledPath(builder) {
            moveTo(x + 2f, y)
            arcTo(2f, 2f, 0f, false, true, x - 2f, y)
            arcTo(2f, 2f, 0f, false, true, x + 2f, y)
            close()
        }
    }
    dot(this, 6f, 12f)
    dot(this, 17.5f, 6f)
    dot(this, 17.5f, 17.5f)
}

private fun dotsIcon(vertical: Boolean, name: String): ImageVector = vectorIcon(name) {
    fun dot(builder: androidx.compose.ui.graphics.vector.ImageVector.Builder, x: Float, y: Float) {
        filledPath(builder) {
            moveTo(x + 1.8f, y)
            arcTo(1.8f, 1.8f, 0f, false, true, x - 1.8f, y)
            arcTo(1.8f, 1.8f, 0f, false, true, x + 1.8f, y)
            close()
        }
    }
    if (vertical) {
        dot(this, 12f, 5.5f)
        dot(this, 12f, 12f)
        dot(this, 12f, 18.5f)
    } else {
        dot(this, 5.5f, 12f)
        dot(this, 12f, 12f)
        dot(this, 18.5f, 12f)
    }
}

private fun personIcon(name: String): ImageVector = vectorIcon(name) {
    val geometry = ElegantIcons.personGeometry(24f)
    filledPath(this) {
        moveTo(geometry.headCenter.x + geometry.headRadius, geometry.headCenter.y)
        arcTo(
            geometry.headRadius,
            geometry.headRadius,
            0f,
            false,
            true,
            geometry.headCenter.x - geometry.headRadius,
            geometry.headCenter.y,
        )
        arcTo(
            geometry.headRadius,
            geometry.headRadius,
            0f,
            false,
            true,
            geometry.headCenter.x + geometry.headRadius,
            geometry.headCenter.y,
        )
        close()
    }
    filledPath(this) {
        moveTo(geometry.shoulderLeft.x, geometry.shoulderLeft.y)
        curveTo(
            geometry.shoulderLeft.x + 1.2f,
            geometry.headCenter.y + geometry.headRadius * 1.2f,
            geometry.shoulderRight.x - 1.2f,
            geometry.headCenter.y + geometry.headRadius * 1.2f,
            geometry.shoulderRight.x,
            geometry.shoulderRight.y,
        )
        close()
    }
}

private fun homeIcon(name: String): ImageVector = vectorIcon(name) {
    filledPath(this) {
        moveTo(12f, 3f)
        lineTo(21f, 11f)
        lineTo(19.2f, 11f)
        lineTo(19.2f, 21f)
        lineTo(4.8f, 21f)
        lineTo(4.8f, 11f)
        lineTo(3f, 11f)
        close()
    }
}

private fun settingsIcon(name: String): ImageVector = vectorIcon(name) {
    strokedPath(this, 2.2f) {
        moveTo(18.5f, 12f)
        arcTo(6.5f, 6.5f, 0f, false, true, 5.5f, 12f)
        arcTo(6.5f, 6.5f, 0f, false, true, 18.5f, 12f)
        close()
    }
    strokedPath(this, 2.2f) {
        for (angle in intArrayOf(0, 45, 90, 135, 180, 225, 270, 315)) {
            val radians = kotlin.math.PI * angle / 180.0
            val cos = kotlin.math.cos(radians).toFloat()
            val sin = kotlin.math.sin(radians).toFloat()
            moveTo(12f + 8.5f * cos, 12f + 8.5f * sin)
            lineTo(12f + 10.5f * cos, 12f + 10.5f * sin)
        }
    }
}

private fun notificationsIcon(name: String): ImageVector = vectorIcon(name) {
    filledPath(this) {
        moveTo(4.6f, 17f)
        arcTo(7.4f, 6.4f, 0f, false, false, 19.4f, 17f)
        lineTo(19.4f, 18f)
        lineTo(4.6f, 18f)
        close()
    }
    filledPath(this) {
        moveTo(12f, 18.2f)
        arcTo(1.6f, 1.6f, 0f, false, true, 10.4f, 19.8f)
        arcTo(1.6f, 1.6f, 0f, false, true, 12f, 21.4f)
        arcTo(1.6f, 1.6f, 0f, false, true, 13.6f, 19.8f)
        arcTo(1.6f, 1.6f, 0f, false, true, 12f, 18.2f)
        close()
    }
}

private fun starIcon(name: String): ImageVector = vectorIcon(name) {
    val points = ElegantIcons.starPoints(Offset(12f, 12f), 9.5f, 3.9f)
    filledPath(this) {
        moveTo(points[0].x, points[0].y)
        for (index in 1 until points.size) {
            lineTo(points[index].x, points[index].y)
        }
        close()
    }
}

private fun heartIcon(name: String): ImageVector = vectorIcon(name) {
    filledPath(this) {
        moveTo(12f, 20.5f)
        curveTo(13f, 19.5f, 20f, 14.5f, 20f, 8.5f)
        curveTo(20f, 5.6f, 17.5f, 4f, 15f, 4f)
        curveTo(13.1f, 4f, 12f, 5.4f, 12f, 7f)
        curveTo(12f, 5.4f, 10.9f, 4f, 9f, 4f)
        curveTo(6.5f, 4f, 4f, 5.6f, 4f, 8.5f)
        curveTo(4f, 14.5f, 11f, 19.5f, 12f, 20.5f)
        close()
    }
}