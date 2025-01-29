package com.example.bankcardbuilder.screens.auth.signUp

import com.example.bankcardbuilder.exeption.AppException

data class SignUpScreenState(
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val passwordVisible: Boolean = false,
    val confirmPasswordVisible: Boolean = false,
    val stateUI: SignUpUIState = SignUpUIState.Empty
)

sealed class SignUpUIState {
    data object Loading : SignUpUIState()
    data object Success : SignUpUIState()
    data class Error(val exception: AppException) : SignUpUIState()
    data object Empty : SignUpUIState()
}