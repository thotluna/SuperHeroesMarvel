package ve.com.teeac.mynewapplication.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import ve.com.teeac.mynewapplication.R

val robotoCondensedLight = FontFamily(
    Font( R.font.roboto_condensed_light ),
)
val robotoCondensedBold = FontFamily(
    Font( R.font.roboto_condensed_bold ),
)
val robotoCondensedRegular = FontFamily(
    Font( R.font.roboto_condensed_regular ),
)

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    headlineLarge = TextStyle(
        fontFamily = robotoCondensedRegular,
        fontWeight = FontWeight.Normal,
        fontSize = 32.sp,
    ),
    labelLarge = TextStyle(
        fontFamily = robotoCondensedBold,
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp,
    ),
    titleMedium = TextStyle(
        fontFamily = robotoCondensedBold,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
    ),
    titleSmall = TextStyle(
        fontFamily = robotoCondensedBold,
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp,
    ),
    bodyMedium = TextStyle(
        fontFamily = robotoCondensedRegular,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp
    ),
    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)