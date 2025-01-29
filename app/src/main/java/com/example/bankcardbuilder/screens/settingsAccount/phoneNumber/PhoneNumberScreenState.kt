package com.example.bankcardbuilder.screens.settingsAccount.phoneNumber

import com.example.bankcardbuilder.exeption.AppException

data class PhoneNumberScreenState(
    val phoneNumber: String = "",
    val stateUI: PhoneNumberUIState = PhoneNumberUIState.Empty
)

sealed class PhoneNumberUIState {
    data object Loading : PhoneNumberUIState()
    data object Success : PhoneNumberUIState()
    data class Error(val exception: AppException) : PhoneNumberUIState()
    data object Empty : PhoneNumberUIState()
}