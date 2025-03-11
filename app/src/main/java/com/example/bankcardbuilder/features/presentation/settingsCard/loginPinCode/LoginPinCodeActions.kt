package com.example.bankcardbuilder.features.presentation.settingsCard.loginPinCode

sealed interface LoginPinCodeAction {
    data object GoToMainScreen : LoginPinCodeAction
    data object OnVerifyPinCodeClick : LoginPinCodeAction
    data class OnPinCodeChange(val pinCode: String) : LoginPinCodeAction
}