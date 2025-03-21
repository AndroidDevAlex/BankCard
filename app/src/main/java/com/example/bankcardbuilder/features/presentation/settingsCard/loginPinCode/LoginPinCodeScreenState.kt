package com.example.bankcardbuilder.features.presentation.settingsCard.loginPinCode

import com.example.bankcardbuilder.core.domain.AppException

data class LoginPinCodeScreenState(
    var pinCode: String = "",
    val stateUI: LoginPinCodeUIState = LoginPinCodeUIState.Empty
)

sealed class LoginPinCodeUIState {
    data object Loading : LoginPinCodeUIState()
    data object Success : LoginPinCodeUIState()
    data class Error(val exception: AppException) : LoginPinCodeUIState()
    data object Empty : LoginPinCodeUIState()
}