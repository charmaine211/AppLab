package applab.veiligthuis.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import applab.veiligthuis.R

val Montserrat = FontFamily(
    Font(R.font.montserrat)
)

val Typography = Typography(
    body1 = TextStyle(
        fontFamily = Montserrat,
        fontWeight = FontWeight.Normal,
        fontSize = 13.sp
    ),
    h1 = TextStyle( fontFamily = Montserrat,
        fontWeight = FontWeight.Bold,
        fontSize = 13.sp)

)