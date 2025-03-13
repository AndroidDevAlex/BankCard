package com.example.bankcardbuilder.features.presentation.auth.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.bankcardbuilder.R
import com.example.bankcardbuilder.core.util.Dimens

@Composable
fun SplashScreen(
    goToLoginScreen: () -> Unit,
    goToMainProfileScreen: () -> Unit
) {

    val viewModel = hiltViewModel<SplashViewModule>()
    val screenState by viewModel.screenState.collectAsState()

    SplashScreenUi(
        screenState = screenState,
        goToLoginScreen = { goToLoginScreen() },
        goToMainProfileScreen = { goToMainProfileScreen() }
    )
}

@Composable
private fun SplashScreenUi(
    screenState: SplashScreenState,
    goToLoginScreen: () -> Unit,
    goToMainProfileScreen: () -> Unit
) {

    val logo = if (isSystemInDarkTheme()) R.drawable.fox_image_black else R.drawable.fox_image_white

    when (screenState) {
        SplashScreenState.NotSignedIn -> {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.background)
                    .windowInsetsPadding(WindowInsets.safeDrawing),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = Dimens.PaddingTopRow),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {

                    Icon(
                        painter = painterResource(id = logo),
                        contentDescription = "FoxBank Logo",
                        modifier = Modifier.size(Dimens.IconSizeMod),
                        tint = Color.Unspecified
                    )
                    Spacer(modifier = Modifier.width(Dimens.SpacerWidth))
                    Text(
                        text = stringResource(R.string.foxbank),
                        style = MaterialTheme.typography.displayLarge.copy(fontSize = Dimens.FontSizeText),
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = stringResource(R.string.welcome_to),
                        style = MaterialTheme.typography.titleLarge.copy(fontSize = Dimens.FontSize53),
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Text(
                        text = stringResource(R.string.fox_bank),
                        style = MaterialTheme.typography.displayLarge.copy(fontSize = Dimens.FontSize35),
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(Dimens.SpacerHeight28))
                    Text(
                        text = stringResource(R.string.your_best_money_transfer_partner),
                        style = MaterialTheme.typography.bodyMedium.copy(fontSize = Dimens.TextFontSp),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
                Button(
                    onClick = { goToLoginScreen() },
                    modifier = Modifier
                        .width(Dimens.ButtonWidthDp)
                        .padding(bottom = Dimens.PaddingBottom110)
                        .height(Dimens.ButtonHeight55),
                    shape = RoundedCornerShape(Dimens.CornerShape),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.tertiary
                    )
                ) {
                    Text(
                        text = stringResource(R.string.get_started),
                        style = MaterialTheme.typography.bodyLarge.copy(fontSize = Dimens.FontSizeText20)
                    )
                }
            }
        }

        is SplashScreenState.Loading -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }

        is SplashScreenState.SignedIn -> {
            LaunchedEffect(Unit) {
                goToMainProfileScreen()
            }
        }
    }
}