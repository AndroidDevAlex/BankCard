package com.example.bankcardbuilder.screens.settingsCard.cardSettings

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.bankcardbuilder.screens.settingsCard.cardSettings.uiViews.CardDetailsScreenUi
import com.example.bankcardbuilder.screens.settingsCard.cardSettings.uiViews.SetPinCodeScreenUi

@Composable
fun CardSettingsScreensFlow(
    goToMainScreen: () -> Unit,
    goToPinCodeScreen: () -> Unit
){
    val viewModel = hiltViewModel<CardSettingsViewModel>()
    val screenState by viewModel.state.collectAsState()
    val context = LocalContext.current

    when (screenState.currentStep) {
        CardSettingsStep.CARD_SETTINGS -> {
            CardDetailsScreenUi(
                screenState = screenState,
                onUserNameChanged = { viewModel.updateState(screenState.copy(userName = it)) },
                onCardNumberChanged = { viewModel.updateState(screenState.copy(cardNumber = it)) },
                onExpiryDateChanged = { viewModel.updateState(screenState.copy(expiryDate = it)) },
                onCardPaySystemChanged = { viewModel.updateState(screenState.copy(cardPaySystem = it)) },
                onColorSelected = { viewModel.updateState(screenState.copy(selectedColor = it)) },
                onBackClick = { goToMainScreen() },
                goToPinCodeScreen = { goToPinCodeScreen() },
                onNextClick = { viewModel.validation()}
            )
        }
        CardSettingsStep.PIN_CODE -> {
            SetPinCodeScreenUi(
                screenState = screenState,
                onBackClick = { viewModel.goToPreviousStep() },
                onNextClick = { viewModel.validation() },
                goToMainScreen = { goToMainScreen() },
                onPinCodeChange = { viewModel.updateState(screenState.copy(pinCode = it)) },
                context = context
            )
        }
        else -> {}
    }
}