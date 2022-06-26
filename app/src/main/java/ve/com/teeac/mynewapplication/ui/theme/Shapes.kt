package ve.com.teeac.mynewapplication.ui.theme

import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

val Shape = Shapes(
    extraSmall  = CutCornerShape(
        CornerSize(0.dp),
        CornerSize(0.dp),
        CornerSize(4.dp),
        CornerSize(0.dp)
    ),
    small = CutCornerShape(
        CornerSize(0.dp),
        CornerSize(0.dp),
        CornerSize(8.dp),
        CornerSize(0.dp)
    ),
    medium = CutCornerShape(
        CornerSize(0.dp),
        CornerSize(0.dp),
        CornerSize(16.dp),
        CornerSize(0.dp)
    ),
    large = CutCornerShape(
        CornerSize(0.dp),
        CornerSize(0.dp),
        CornerSize(24.dp),
        CornerSize(0.dp)
    ),
    extraLarge = CutCornerShape(
        CornerSize(0.dp),
        CornerSize(0.dp),
        CornerSize(32.dp),
        CornerSize(0.dp)
    ),
)