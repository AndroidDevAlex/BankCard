package com.example.bankcardbuilder.screens.settingsCard

import com.example.bankcardbuilder.exeption.AppException

data class CardPinCodeScreenState(
    var pinCode: String = "",
    val stateUI: PinCodeUIState = PinCodeUIState.Empty
)

sealed class PinCodeUIState {
    data object Loading : PinCodeUIState()
    data object Success : PinCodeUIState()
    data class Error(val exception: AppException) : PinCodeUIState()
    data object Empty : PinCodeUIState()
}