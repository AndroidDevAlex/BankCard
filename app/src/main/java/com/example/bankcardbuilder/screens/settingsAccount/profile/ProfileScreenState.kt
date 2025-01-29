package com.example.bankcardbuilder.screens.settingsAccount.profile

import com.example.bankcardbuilder.exeption.AppException

data class ProfileScreenState(
    val firstName: String = "",
    val lastName: String = "",
    val photo: String? = null,
    val stateUI: ProfileUIState = ProfileUIState.Empty
)

sealed class ProfileUIState {
    data object Loading : ProfileUIState()
    data object Success : ProfileUIState()
    data class Error(val exception: AppException) : ProfileUIState()
    data object Empty : ProfileUIState()
}