package com.example.bankcardbuilder.presentation.screens.registration

data class RegistrationActions(
    val goToMainScreen: () -> Unit,
    val onEmailChange: (String) -> Unit,
    val onPasswordChange: (String) -> Unit,
    val onConfirmPasswordChange: (String) -> Unit,
    val onTogglePasswordVisibility: () -> Unit,
    val onToggleConfirmPasswordVisibility: () -> Unit,
    val onNextClick: () -> Unit,
    val onNameChange: (String) -> Unit,
    val onSurnameChange: (String) -> Unit,
    val onPhotoChange: (String) -> Unit,
    val onPhoneNumberChange: (String) -> Unit,
    val onAnswerChange: (String) -> Unit,
    val onBackClick: () -> Unit,
    val clearError: () -> Unit
)