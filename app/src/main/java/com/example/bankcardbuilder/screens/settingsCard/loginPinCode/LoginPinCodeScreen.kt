package com.example.bankcardbuilder.screens.settingsCard.loginPinCode

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.bankcardbuilder.screens.settingsCard.PinCodeCircles
import com.example.bankcardbuilder.R
import com.example.bankcardbuilder.exeption.AuthException
import com.example.bankcardbuilder.exeption.CardNotFoundException
import com.example.bankcardbuilder.screens.TopBarCustom
import com.example.bankcardbuilder.exeption.InvalidFieldFormatException
import com.example.bankcardbuilder.exeption.InvalidPinCodeException
import com.example.bankcardbuilder.exeption.StorageException
import com.example.bankcardbuilder.screens.CustomErrorSnackbar
import com.example.bankcardbuilder.screens.settingsCard.PinCodeKeyboard
import com.example.bankcardbuilder.util.Dimens

@Composable
fun LoginPinCodeScreen(
    goToMainScreen: () -> Unit
) {

    val viewModel = hiltViewModel<LoginPinCodeViewModel>()
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    LoginPinCodeScreenUi(
        goToMainScreen = { goToMainScreen() },
        screenState = uiState,
        onVerifyPinCodeClick = { viewModel.verifyPinCode() },
        onPinCodeChange = { viewModel.updateScreenState(uiState.copy(pinCode = it)) },
        context = context
    )
}

@Composable
private fun LoginPinCodeScreenUi(
    goToMainScreen: () -> Unit,
    screenState: LoginPinCodeScreenState,
    onVerifyPinCodeClick: () -> Unit,
    onPinCodeChange: (String) -> Unit,
    context: Context
) {

    val snackbar = remember { SnackbarHostState() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .windowInsetsPadding(WindowInsets.safeDrawing)
    ) {
        TopBarCustom(
            title = stringResource(R.string.login),
            onBackClicked = { goToMainScreen() },
            spacerWidth = Dimens.SpacerWidth75
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = Dimens.PaddingTop,
                    start = Dimens.PaddingStart,
                    end = Dimens.PaddingEnd,
                    bottom = Dimens.PaddingBottom
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(Dimens.SpacerHeight42))

            Text(
                text = stringResource(R.string.please_enter_your),
                style = MaterialTheme.typography.bodyLarge.copy(fontSize = Dimens.TextFont),
                color = MaterialTheme.colorScheme.surface
            )
            Text(
                text = stringResource(R.string.pin_code_login),
                style = MaterialTheme.typography.bodyLarge.copy(fontSize = Dimens.TextFont),
                color = MaterialTheme.colorScheme.surface
            )

            Spacer(modifier = Modifier.height(Dimens.Height))

            Text(
                text = stringResource(R.string.enter_pin_code_5_digit),
                style = MaterialTheme.typography.bodyLarge.copy(fontSize = Dimens.TextFontSize13),
                color = MaterialTheme.colorScheme.onTertiary
            )

            Spacer(modifier = Modifier.height(Dimens.SpacerHeight))

            PinCodeCircles(pinCodeLength = screenState.pinCode.length)

            Spacer(modifier = Modifier.height(Dimens.Height))

            PinCodeKeyboard(pinCode = screenState.pinCode, onPinCodeChange = onPinCodeChange)

            Spacer(modifier = Modifier.weight(1f))

            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.weight(1f))

                Box(contentAlignment = Alignment.BottomCenter) {
                    Button(
                        onClick = {
                            onVerifyPinCodeClick()
                        },
                        modifier = Modifier
                            .padding(top = Dimens.PaddingTopBut)
                            .width(Dimens.WidthBut)
                            .height(Dimens.HeightBut),
                        shape = RoundedCornerShape(Dimens.CornerShape),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.tertiary
                        )
                    ) {
                        Text(
                            text = stringResource(R.string.go),
                            style = MaterialTheme.typography.bodyLarge.copy(fontSize = Dimens.TextFontSize)
                        )
                    }
                    CustomErrorSnackbar(snackbar = snackbar)
                }
            }

            LaunchedEffect(screenState.stateUI) {
                when (val state = screenState.stateUI) {
                    is LoginPinCodeUIState.Success -> {
                        goToMainScreen()
                    }

                    is LoginPinCodeUIState.Error -> {
                        val message = when (val error = state.exception) {
                            is InvalidFieldFormatException -> context.getString(R.string.pin_code_is_empty)
                            is CardNotFoundException -> context.getString(R.string.card_not_found)
                            is InvalidPinCodeException -> context.getString(R.string.this_pin_code_does_not_exist_please_try_again)
                            is StorageException -> context.getString(R.string.the_error_occurred_while_updating_the_data)
                            is AuthException -> context.getString(R.string.this_user_does_not_exist)
                            else -> error.message
                                ?: context.getString(R.string.an_unknown_error_occurred)
                        }
                        snackbar.showSnackbar(message)
                    }

                    else -> Unit
                }
            }
        }
    }
}