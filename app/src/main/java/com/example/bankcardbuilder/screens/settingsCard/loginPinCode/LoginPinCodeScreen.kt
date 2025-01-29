package com.example.bankcardbuilder.screens.settingsCard.loginPinCode

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.bankcardbuilder.screens.settingsCard.PinCodeCircles
import com.example.bankcardbuilder.R
import com.example.bankcardbuilder.screens.TopBarCustom
import com.example.bankcardbuilder.exeption.InvalidFieldFormatException
import com.example.bankcardbuilder.exeption.InvalidPinCodeException
import com.example.bankcardbuilder.exeption.StorageException
import com.example.bankcardbuilder.screens.CustomError
import com.example.bankcardbuilder.screens.UiStateHandler
import com.example.bankcardbuilder.screens.settingsCard.CardPinCodeScreenState
import com.example.bankcardbuilder.screens.settingsCard.PinCodeKeyboard
import com.example.bankcardbuilder.screens.settingsCard.PinCodeUIState
import com.example.bankcardbuilder.util.Dimens

@Composable
fun LoginPinCodeScreen(
    goToMainScreen: () -> Unit
) {

    val viewModel = hiltViewModel<LoginPinCodeViewModel>()
    val uiState by viewModel.uiState.collectAsState()

    LoginPinCodeScreenUi(
        goToMainScreen = { goToMainScreen() },
        screenState = uiState,
        onVerifyPinCodeClick = { viewModel.verifyPinCode() },
        onPinCodeChange = { viewModel.updateScreenState(uiState.copy(pinCode = it)) }
    )
}

@Composable
private fun LoginPinCodeScreenUi(
    goToMainScreen: () -> Unit,
    screenState: CardPinCodeScreenState,
    onVerifyPinCodeClick: () -> Unit,
    onPinCodeChange: (String) -> Unit
) {

    Column(modifier = Modifier.fillMaxSize()) {
        TopBarCustom(
            title = stringResource(R.string.login),
            onBackClicked = { goToMainScreen() }
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

            Spacer(modifier = Modifier.height(Dimens.Height))

            Text(
                text = stringResource(R.string.please_enter_your),
                style = MaterialTheme.typography.titleMedium,
                color = colorResource(id = R.color.gray),
            )
            Text(
                text = stringResource(R.string.pin_code_login),
                style = MaterialTheme.typography.titleMedium,
                color = colorResource(id = R.color.gray),
            )

            Spacer(modifier = Modifier.height(Dimens.Height))

            Text(
                text = stringResource(R.string.enter_pin_code_5_digit),
                style = MaterialTheme.typography.bodySmall,
                color = colorResource(id = R.color.dark_gray),
                fontWeight = FontWeight.Normal,
            )

            Spacer(modifier = Modifier.height(Dimens.SpacerHeight))

            PinCodeCircles(pinCodeLength = screenState.pinCode.length)

            Spacer(modifier = Modifier.height(Dimens.Height))

            PinCodeKeyboard(pinCode = screenState.pinCode, onPinCodeChange = onPinCodeChange)

            Spacer(modifier = Modifier.weight(1f))

            UiStateHandler(
                isLoading = screenState.stateUI is PinCodeUIState.Loading,
                isSuccess = screenState.stateUI is PinCodeUIState.Success,
                isError = (screenState.stateUI as? PinCodeUIState.Error)?.exception,
                isEmpty = screenState.stateUI is PinCodeUIState.Empty,
                onError = { exception ->
                    val errorMessage = when (exception) {
                        is InvalidPinCodeException -> stringResource(R.string.this_pin_code_does_not_exist_please_try_again)
                        is StorageException -> stringResource(R.string.the_error_occurred_while_updating_the_data)
                        is InvalidFieldFormatException -> stringResource(
                            R.string.field_is_empty,
                            exception.field.displayName()
                        )
                        else -> stringResource(R.string.an_unknown_error_occurred)
                    }
                    CustomError(errorMessage)
                },
                successMessage = stringResource(R.string.the_pin_code_is_correct),
                onSuccess = {
                    goToMainScreen()
                },

                onEmpty = {}
            )

            Button(
                onClick = {
                    onVerifyPinCodeClick()
                },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = Dimens.PaddingTopBut)
                    .width(Dimens.WidthBut)
                    .height(Dimens.HeightBut),
                shape = RoundedCornerShape(Dimens.CornerShape),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.orange),
                    contentColor = Color.White
                )
            ) {
                Text(text = stringResource(R.string.go), style = MaterialTheme.typography.titleLarge)
            }
        }
    }
}