package com.example.bankcardbuilder.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.bankcardbuilder.R

val MulishFontFamily = FontFamily(
    Font(R.font.mulish_extra_bold, FontWeight.ExtraBold),
    Font(R.font.mulish_semi_bold, FontWeight.SemiBold),
    Font(R.font.mulish_bold, FontWeight.Bold),
    Font(R.font.mulish_black, FontWeight.Black),
    Font(R.font.mulish_medium, FontWeight.Medium),
    Font(R.font.mulish_regular, FontWeight.Normal)
)

val AppTypography = Typography(
    bodyMedium = TextStyle(
        fontFamily = MulishFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 13.sp,
        lineHeight = 16.32.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = MulishFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    headlineSmall = TextStyle(
        fontFamily = MulishFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp,
        lineHeight = 42.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = MulishFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 40.sp,
        lineHeight = 42.sp
    ),
    titleLarge = TextStyle(
        fontFamily = MulishFontFamily,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 22.sp
    ),
    displayLarge = TextStyle(
        fontFamily = MulishFontFamily,
        fontWeight = FontWeight.Black,
        fontSize = 28.sp
    )
)