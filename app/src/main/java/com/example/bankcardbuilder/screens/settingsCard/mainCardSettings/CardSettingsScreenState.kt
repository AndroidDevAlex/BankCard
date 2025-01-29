package com.example.bankcardbuilder.screens.settingsCard.mainCardSettings

import androidx.compose.ui.graphics.Color
import com.example.bankcardbuilder.exeption.AppException
import com.example.bankcardbuilder.ui.theme.Orange

data class CardSettingsScreenState(
    val userName: String = "**** ****",
    val cardNumber: String = "**** **** **** ****",
    val expiryDate: String = "**/**",
    val cardCompany: String = "****",
    val selectedColor: Color = Orange,
    val uiState: CardSettingsUIState = CardSettingsUIState.Empty
)

sealed class CardSettingsUIState {
    data object Loading : CardSettingsUIState()
    data object Success : CardSettingsUIState()
    data class Error(val exception: AppException) : CardSettingsUIState()
    data object Empty : CardSettingsUIState()
}