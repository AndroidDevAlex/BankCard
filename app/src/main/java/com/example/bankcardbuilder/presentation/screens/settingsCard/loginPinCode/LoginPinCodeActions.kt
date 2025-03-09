package com.example.bankcardbuilder.presentation.screens.settingsCard.loginPinCode

data class LoginPinCodeActions(
    val goToMainScreen: () -> Unit,
    val onVerifyPinCodeClick: () -> Unit,
    val onPinCodeChange: (String) -> Unit
)