package com.example.bankcardbuilder.screens.mainProfile

import androidx.compose.ui.graphics.Color
import com.example.bankcardbuilder.ui.theme.Amethyst
import com.example.bankcardbuilder.ui.theme.Aquamarine
import com.example.bankcardbuilder.ui.theme.Beige
import com.example.bankcardbuilder.ui.theme.DarkGreen
import com.example.bankcardbuilder.ui.theme.OliveGreen
import com.example.bankcardbuilder.ui.theme.Orange
import com.example.bankcardbuilder.ui.theme.Pink
import com.example.bankcardbuilder.ui.theme.SandyBrown

fun parseColor(colorString: String): Color {
    return when (colorString) {
        "#CCCCCC" -> Color.LightGray
        "#00FF00" -> Color.Green
        "#FFFF00" -> Color.Yellow
        "#00FFFF" -> Color.Cyan
        "#FFFFFF" -> Color.White
        "#FF0000" -> Color.Red
        "#0000FF" -> Color.Blue
        "#FF00FF" -> Color.Magenta
        "#9966CC" -> Amethyst
        "#EC1E88" -> Pink
        "#B16241" -> Orange
        "#1D6604" -> DarkGreen
        "#DACCA2" -> Beige
        "#7FFFD4" -> Aquamarine
        "#89AC76" -> OliveGreen
        "#C1876B" -> SandyBrown
        else -> Color.Black
    }
}