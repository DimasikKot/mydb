package icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val RefreshNotes: ImageVector
    get() {
        if (Source_notes != null) {
            return Source_notes!!
        }
        Source_notes = ImageVector.Builder(
            name = "Source_notes",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 960f,
            viewportHeight = 960f
        ).apply {
            path(
                fill = SolidColor(Color.Black),
                fillAlpha = 1.0f,
                stroke = null,
                strokeAlpha = 1.0f,
                strokeLineWidth = 1.0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 1.0f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(200f, 760f)
                verticalLineToRelative(-560f)
                close()
                moveToRelative(80f, -400f)
                horizontalLineToRelative(400f)
                verticalLineToRelative(-80f)
                horizontalLineTo(280f)
                close()
                moveToRelative(0f, 160f)
                horizontalLineToRelative(190f)
                quadToRelative(20f, -24f, 43.5f, -44.5f)
                reflectiveQuadTo(565f, 440f)
                horizontalLineTo(280f)
                close()
                moveToRelative(0f, 160f)
                horizontalLineToRelative(122f)
                quadToRelative(2f, -21f, 7.5f, -41f)
                reflectiveQuadToRelative(13.5f, -39f)
                horizontalLineTo(280f)
                close()
                moveToRelative(-80f, 160f)
                quadToRelative(-33f, 0f, -56.5f, -23.5f)
                reflectiveQuadTo(120f, 760f)
                verticalLineToRelative(-560f)
                quadToRelative(0f, -33f, 23.5f, -56.5f)
                reflectiveQuadTo(200f, 120f)
                horizontalLineToRelative(560f)
                quadToRelative(33f, 0f, 56.5f, 23.5f)
                reflectiveQuadTo(840f, 200f)
                verticalLineToRelative(223f)
                quadToRelative(-19f, -8f, -39f, -13.5f)
                reflectiveQuadToRelative(-41f, -7.5f)
                verticalLineToRelative(-202f)
                horizontalLineTo(200f)
                verticalLineToRelative(560f)
                horizontalLineToRelative(202f)
                quadToRelative(2f, 21f, 7.5f, 41f)
                reflectiveQuadToRelative(13.5f, 39f)
                close()
                moveToRelative(520f, 80f)
                quadToRelative(-73f, 0f, -127.5f, -45.5f)
                reflectiveQuadTo(524f, 760f)
                horizontalLineToRelative(62f)
                quadToRelative(13f, 44f, 49.5f, 72f)
                reflectiveQuadToRelative(84.5f, 28f)
                quadToRelative(58f, 0f, 99f, -41f)
                reflectiveQuadToRelative(41f, -99f)
                reflectiveQuadToRelative(-41f, -99f)
                reflectiveQuadToRelative(-99f, -41f)
                quadToRelative(-29f, 0f, -54f, 10.5f)
                reflectiveQuadTo(622f, 620f)
                horizontalLineToRelative(58f)
                verticalLineToRelative(60f)
                horizontalLineTo(520f)
                verticalLineToRelative(-160f)
                horizontalLineToRelative(60f)
                verticalLineToRelative(57f)
                quadToRelative(27f, -26f, 63f, -41.5f)
                reflectiveQuadToRelative(77f, -15.5f)
                quadToRelative(83f, 0f, 141.5f, 58.5f)
                reflectiveQuadTo(920f, 720f)
                reflectiveQuadTo(861.5f, 861.5f)
                reflectiveQuadTo(720f, 920f)
            }
        }.build()
        return Source_notes!!
    }

private var Source_notes: ImageVector? = null

