package com.example.bankcardbuilder.features.presentation.settingsCard.cardSettings

import androidx.compose.ui.graphics.Color

sealed interface CardSettingsAction {
    data object GoToMainScreen : CardSettingsAction
    data object GoToPinCodeScreen : CardSettingsAction
    data class OnUserNameChanged(val userName: String) : CardSettingsAction
    data class OnCardNumberChanged(val cardNumber: String) : CardSettingsAction
    data class OnExpiryDateChanged(val expiryDate: String) : CardSettingsAction
    data class OnCardPaySystemChanged(val cardPaySystem: String) : CardSettingsAction
    data class OnColorSelected(val color: Color) : CardSettingsAction
    data object OnBackClick : CardSettingsAction
    data object OnNextClick : CardSettingsAction
    data class OnPinCodeChange(val pinCode: String) : CardSettingsAction
}