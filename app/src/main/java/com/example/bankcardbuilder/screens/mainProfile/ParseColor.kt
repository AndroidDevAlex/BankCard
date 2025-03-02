package com.example.bankcardbuilder.screens.mainProfile

import androidx.compose.ui.graphics.Color
import com.example.bankcardbuilder.ui.theme.Black
import com.example.bankcardbuilder.ui.theme.DarkBlue
import com.example.bankcardbuilder.ui.theme.DarkGray
import com.example.bankcardbuilder.ui.theme.Blue
import com.example.bankcardbuilder.ui.theme.BrightBlue
import com.example.bankcardbuilder.ui.theme.BrightOrange
import com.example.bankcardbuilder.ui.theme.Green
import com.example.bankcardbuilder.ui.theme.LightBlue
import com.example.bankcardbuilder.ui.theme.LightGray
import com.example.bankcardbuilder.ui.theme.LightTurquoise
import com.example.bankcardbuilder.ui.theme.OrangeYellow
import com.example.bankcardbuilder.ui.theme.Red
import com.example.bankcardbuilder.ui.theme.VeryDarkBlue
import com.example.bankcardbuilder.ui.theme.Violet
import com.example.bankcardbuilder.ui.theme.White
import com.example.bankcardbuilder.ui.theme.Yellow

fun parseColor(colorString: String): Color {
    return when (colorString) {
        "#8CD9E9" -> LightTurquoise
        "#34A853" -> Green
        "#0166FF" -> Blue
        "#F59D31" -> OrangeYellow
        "#FC6020" -> BrightOrange
        "#009CDE" -> BrightBlue
        "#E80B26" -> Red
        "#FBBC05" -> Yellow
        "#979797" -> LightGray
        "#1E1E1E" -> DarkGray
        "#003087" -> DarkBlue
        "#001A4D" -> VeryDarkBlue
        "#392993" -> Violet
        "#6875E2" -> LightBlue
        "#FFFFFF" -> White
        else -> Black
    }
}