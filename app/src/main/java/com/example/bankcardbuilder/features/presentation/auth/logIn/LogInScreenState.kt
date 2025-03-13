package com.example.bankcardbuilder.features.presentation.auth.logIn

import com.example.bankcardbuilder.core.domain.AppException

data class LogInScreenState(
    val email: String = "",
    val password: String = "",
    val passwordVisible: Boolean = false,
    val stateUI: LogInUIState = LogInUIState.Empty
)

sealed class LogInUIState {
    data object Loading : LogInUIState()
    data object Success : LogInUIState()
    data class Error(val exception: AppException) : LogInUIState()
    data object Empty : LogInUIState()
}