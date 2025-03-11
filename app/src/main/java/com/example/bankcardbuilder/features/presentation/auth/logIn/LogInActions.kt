package com.example.bankcardbuilder.features.presentation.auth.logIn

sealed interface LogInAction {
    data object GoToMainScreen : LogInAction
    data object GoToSignUp : LogInAction
    data class OnEmailChange(val email: String) : LogInAction
    data class OnPasswordChange(val password: String) : LogInAction
    data object OnTogglePasswordVisibility : LogInAction
    data object OnSignInClick : LogInAction
}