package com.example.bankcardbuilder.presentation.screens.auth.logIn

data class LogInActions(
    val goToMainScreen: () -> Unit,
    val goToSignUp: () -> Unit,
    val onEmailChange: (String) -> Unit,
    val onPasswordChange: (String) -> Unit,
    val onTogglePasswordVisibility: () -> Unit,
    val onSignInClick: () -> Unit
)