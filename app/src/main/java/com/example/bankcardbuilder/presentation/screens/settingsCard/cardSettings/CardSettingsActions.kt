package com.example.bankcardbuilder.presentation.screens.settingsCard.cardSettings

import androidx.compose.ui.graphics.Color

data class CardSettingsActions(
    val goToMainScreen: () -> Unit,
    val goToPinCodeScreen: () -> Unit,
    val onUserNameChanged: (String) -> Unit,
    val onCardNumberChanged: (String) -> Unit,
    val onExpiryDateChanged: (String) -> Unit,
    val onCardPaySystemChanged: (String) -> Unit,
    val onColorSelected: (Color) -> Unit,
    val onBackClick: () -> Unit,
    val onNextClick: () -> Unit,
    val onPinCodeChange: (String) -> Unit
)