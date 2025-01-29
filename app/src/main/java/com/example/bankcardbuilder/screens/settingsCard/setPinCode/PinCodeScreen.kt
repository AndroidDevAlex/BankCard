package com.example.bankcardbuilder.screens.settingsCard.setPinCode

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
import com.example.bankcardbuilder.exeption.AuthException
import com.example.bankcardbuilder.exeption.InvalidFieldFormatException
import com.example.bankcardbuilder.exeption.StorageException
import com.example.bankcardbuilder.screens.CustomError
import com.example.bankcardbuilder.screens.UiStateHandler
import com.example.bankcardbuilder.screens.settingsCard.CardPinCodeScreenState
import com.example.bankcardbuilder.screens.settingsCard.PinCodeKeyboard
import com.example.bankcardbuilder.screens.settingsCard.PinCodeUIState
import com.example.bankcardbuilder.util.Dimens

@Composable
fun PinCodeScreen(
    goToCardSettingsScreen: () -> Unit,
    goToMainScreen: () -> Unit
) {

    val viewModel = hiltViewModel<PinCodeViewModel>()
    val uiState by viewModel.uiState.collectAsState()

    PinCodeScreenUi(
        goToCardSettingsScreen = { goToCardSettingsScreen() },
        screenState = uiState,
        onSavePinCodeClick = { viewModel.savePinCode() },
        goToMainScreen = { goToMainScreen() },
        onPinCodeChange = { viewModel.updateScreenState(uiState.copy(pinCode = it)) }
    )
}

@Composable
private fun PinCodeScreenUi(
    screenState: CardPinCodeScreenState,
    goToCardSettingsScreen: () -> Unit,
    goToMainScreen: () -> Unit,
    onSavePinCodeClick: () -> Unit,
    onPinCodeChange: (String) -> Unit
) {

    Column(modifier = Modifier.fillMaxSize()) {
        TopBarCustom(
            title = stringResource(R.string.set_pin_code),
            onBackClicked = { goToCardSettingsScreen() }
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
                text = stringResource(R.string.please_set_your_own),
                style = MaterialTheme.typography.titleMedium,
                color = colorResource(id = R.color.gray),
            )
            Text(
                text = stringResource(R.string.pin_code),
                style = MaterialTheme.typography.titleMedium,
                color = colorResource(id = R.color.gray),
            )

            Spacer(modifier = Modifier.height(Dimens.Height))

            Text(
                text = stringResource(R.string.set_pin_code_5_digit),
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
                        is AuthException -> stringResource(R.string.this_user_does_not_exist)
                        is StorageException -> stringResource(R.string.there_was_an_error_saving_your_data_please_try_again)
                        is InvalidFieldFormatException -> stringResource(
                            R.string.field_must_contain_5_digits,
                            exception.field.displayName()
                        )
                        else -> stringResource(R.string.an_unknown_error_occurred)
                    }
                    CustomError(errorMessage)
                },
                successMessage = stringResource(R.string.pin_code_saved_successfully),
                onSuccess = {
                    goToMainScreen()
                },

                onEmpty = {}
            )
            Button(
                onClick = {
                    onSavePinCodeClick()
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
                Text(text = stringResource(R.string.set), style = MaterialTheme.typography.titleLarge)
            }
        }
    }
}