package com.example.bankcardbuilder.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat

private val DarkColorScheme = darkColorScheme(
    primary = Orange,
    onSecondary = Color.Black,
    tertiary = Color.White,
    onTertiary = Color(0xFFBDBBBB),
    onTertiaryContainer = Color(0xFF8D8C8C),
    secondaryContainer = Beige,
    background = Color.Black,
    tertiaryContainer = YellowBox,
    surface = Color(0xFFCCCACA),
    onPrimary = Beige,
    onBackground = Color.White,
    onSurface = Color(0xFFD0D0D0),
    surfaceVariant = Color(0xFFDDDCDC),
    outline = Color(0xFFDDDCDC)
)

private val LightColorScheme = lightColorScheme(
    primary = Orange,
    tertiaryContainer = YellowBox,
    onTertiaryContainer = Color(0xFF838080),
    secondaryContainer = LightGrayCard,
    onTertiary = Gray,
    background = Color(0xFFEBE9E9),
    onSecondary = Color.Black,
    surface = Color(0xFFB6B4B4),
    onBackground = Color.Black,
    onPrimary = GrayInfo,
    tertiary = Color.White,
    onSurface = Color(0xFF878787),
    surfaceVariant = Color(0xFF878787),
    outline = Color(0xFFC4C4C4)
)

@Composable
fun BankCardBuilderTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {

            val window = (view.context as Activity).window

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
                window.isStatusBarContrastEnforced = false
            } else {
                WindowCompat.setDecorFitsSystemWindows(window, false)
                WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightStatusBars = true
            }

            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypography,
        content = content
    )
}