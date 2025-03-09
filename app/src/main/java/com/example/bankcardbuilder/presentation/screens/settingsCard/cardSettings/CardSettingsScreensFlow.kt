package com.example.bankcardbuilder.presentation.screens.settingsCard.cardSettings

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.bankcardbuilder.presentation.screens.settingsCard.cardSettings.uiViews.CardDetailsScreenUi
import com.example.bankcardbuilder.presentation.screens.settingsCard.cardSettings.uiViews.SetPinCodeScreenUi

@Composable
fun CardSettingsScreensFlow(
    goToMainScreen: () -> Unit,
    goToPinCodeScreen: () -> Unit
){
    val viewModel = hiltViewModel<CardSettingsViewModel>()
    val screenState by viewModel.state.collectAsState()
    val context = LocalContext.current

    val actions = CardSettingsActions(
        goToMainScreen = goToMainScreen,
        goToPinCodeScreen = goToPinCodeScreen,
        onUserNameChanged = { viewModel.updateState(screenState.copy(userName = it)) },
        onCardNumberChanged = { viewModel.updateState(screenState.copy(cardNumber = it)) },
        onExpiryDateChanged = { viewModel.updateState(screenState.copy(expiryDate = it)) },
        onCardPaySystemChanged = { viewModel.updateState(screenState.copy(cardPaySystem = it)) },
        onColorSelected = { viewModel.updateState(screenState.copy(selectedColor = it)) },
        onBackClick = { viewModel.goToPreviousStep() },
        onNextClick = { viewModel.validation() },
        onPinCodeChange = { viewModel.updateState(screenState.copy(pinCode = it)) }
    )


    when (screenState.currentStep) {
        CardSettingsStep.CARD_SETTINGS -> {
            CardDetailsScreenUi(screenState, actions)
        }
        CardSettingsStep.PIN_CODE -> {
            SetPinCodeScreenUi(screenState, actions, context)
        }
        else -> {}
    }
}