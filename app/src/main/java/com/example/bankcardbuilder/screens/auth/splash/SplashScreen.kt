package com.example.bankcardbuilder.screens.auth.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.bankcardbuilder.R
import com.example.bankcardbuilder.navigation.ScreenState
import com.example.bankcardbuilder.util.Dimens

@Composable
fun SplashScreen(
    goToLoginScreen: () -> Unit,
    goToMainProfileScreen: () -> Unit,
    goToProfileScreen: () -> Unit,
    goToPhoneNumberScreen: () -> Unit,
    goToSecurityQuestionScreen: () -> Unit
) {

    val viewModel = hiltViewModel<SplashViewModule>()
    val screenState by viewModel.screenState.collectAsState()

    SplashScreenUi(
        screenState = screenState,
        goToLoginScreen = { goToLoginScreen() },
        goToMainProfileScreen = { goToMainProfileScreen() },
        goToProfileScreen = { goToProfileScreen() },
        goToPhoneNumberScreen = { goToPhoneNumberScreen() },
        goToSecurityQuestionScreen = { goToSecurityQuestionScreen() }
    )
}

@Composable
private fun SplashScreenUi(
    screenState: SplashScreenState,
    goToLoginScreen: () -> Unit,
    goToMainProfileScreen: () -> Unit,
    goToProfileScreen: () -> Unit,
    goToPhoneNumberScreen: () -> Unit,
    goToSecurityQuestionScreen: () -> Unit
) {

    when (screenState) {
        SplashScreenState.NotSignedIn -> {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = Dimens.PaddingRow),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.fox_image),
                        contentDescription = "FoxBank Logo",
                        modifier = Modifier.size(Dimens.IconSizeMod),
                        tint = Color.Unspecified
                    )
                    Spacer(modifier = Modifier.width(Dimens.SpacerWidth))
                    Text(
                        text = stringResource(R.string.foxbank),
                        style = MaterialTheme.typography.displayMedium.copy(fontSize = Dimens.FontSizeText),
                        fontWeight = FontWeight.ExtraBold
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
                        style = MaterialTheme.typography.headlineLarge.copy(fontSize = Dimens.FontSizeText),
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = stringResource(R.string.fox_bank),
                        style = MaterialTheme.typography.headlineMedium.copy(fontSize = Dimens.FontSize35),
                        fontWeight = FontWeight.Bold,
                        color = colorResource(id = R.color.orange)
                    )
                    Spacer(modifier = Modifier.height(Dimens.SpacerHeight12))
                    Text(
                        text = stringResource(R.string.your_best_money_transfer_partner),
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray
                    )
                }
                Button(
                    onClick = { goToLoginScreen() },
                    modifier = Modifier
                        .width(Dimens.ButtonWidth)
                        .padding(bottom = Dimens.PaddingButBottom)
                        .height(Dimens.ButtonHeight),
                    shape = RoundedCornerShape(Dimens.CornerShape),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = R.color.orange),
                        contentColor = Color.White
                    )
                ) {
                    Text(text = stringResource(R.string.get_started), style = MaterialTheme.typography.titleLarge)
                }
            }
        }

        is SplashScreenState.Loading -> {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = colorResource(id = R.color.black))
            }
        }

        is SplashScreenState.SignedIn -> {
            LaunchedEffect(Unit) {
                when (screenState.screenState) {
                    is ScreenState.SignUp -> goToProfileScreen()
                    is ScreenState.Profile -> goToPhoneNumberScreen()
                    is ScreenState.PhoneNumber -> goToSecurityQuestionScreen()
                    is ScreenState.SecurityQuestion -> goToMainProfileScreen()
                    else -> goToMainProfileScreen()
                }
            }
        }
    }
}