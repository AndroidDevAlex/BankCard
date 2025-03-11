package com.example.bankcardbuilder.features.presentation.settingsCard.cardSettings

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.bankcardbuilder.features.presentation.settingsCard.cardSettings.uiViews.CardDetailsScreenUi
import com.example.bankcardbuilder.features.presentation.settingsCard.cardSettings.uiViews.SetPinCodeScreenUi

@Composable
fun CardSettingsScreensFlow(
    goToMainScreen: () -> Unit,
    goToPinCodeScreen: () -> Unit
) {
    val viewModel = hiltViewModel<CardSettingsViewModel>()
    val screenState by viewModel.state.collectAsState()
    val context = LocalContext.current

    val handleAction: (CardSettingsAction) -> Unit = { action ->
        when (action) {
            is CardSettingsAction.GoToMainScreen -> goToMainScreen()
            is CardSettingsAction.GoToPinCodeScreen -> goToPinCodeScreen()
            else -> UShort
        }
        viewModel.onAction(action)
    }

    when (screenState.currentStep) {
        CardSettingsStep.CARD_SETTINGS -> {
            CardDetailsScreenUi(screenState, handleAction)
        }

        CardSettingsStep.PIN_CODE -> {
            SetPinCodeScreenUi(screenState, handleAction, context)
        }

        else -> {}
    }
}