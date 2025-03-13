package com.example.bankcardbuilder.features.presentation.registration

sealed interface RegistrationAction {
    data object GoToMainScreen : RegistrationAction
    data class OnEmailChange(val email: String) : RegistrationAction
    data class OnPasswordChange(val password: String) : RegistrationAction
    data class OnConfirmPasswordChange(val confirmPassword: String) : RegistrationAction
    data object OnTogglePasswordVisibility : RegistrationAction
    data object OnToggleConfirmPasswordVisibility : RegistrationAction
    data object OnNextClick : RegistrationAction
    data class OnNameChange(val name: String) : RegistrationAction
    data class OnSurnameChange(val surname: String) : RegistrationAction
    data class OnPhotoChange(val photoUri: String) : RegistrationAction
    data class OnPhoneNumberChange(val phoneNumber: String) : RegistrationAction
    data class OnAnswerChange(val answer: String) : RegistrationAction
    data object OnBackClick : RegistrationAction
    data object ClearError : RegistrationAction
}