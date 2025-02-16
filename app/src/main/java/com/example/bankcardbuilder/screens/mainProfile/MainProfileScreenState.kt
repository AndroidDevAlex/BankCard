package com.example.bankcardbuilder.screens.mainProfile

import com.example.bankcardbuilder.exeption.AppException

data class MainProfileScreenState(
    val userProfile: UserProfileInformation? = null,
    val stateUI: MainProfileUiState = MainProfileUiState.Empty
)

sealed class MainProfileUiState {
    data object Loading : MainProfileUiState()
    data object Empty : MainProfileUiState()
    data object LoggedOut : MainProfileUiState()
    data class Success(val userProfile: UserProfileInformation): MainProfileUiState()
    data class Error(val exception: AppException) : MainProfileUiState()
}

data class UserProfileInformation(
    val userName: String = "",
    val email: String = "",
    val mobilePhone: String = "",
    val cards: List<ShortCardInfo> = emptyList(),
    val photo: String? = null
)