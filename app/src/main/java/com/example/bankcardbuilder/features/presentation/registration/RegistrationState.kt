package com.example.bankcardbuilder.features.presentation.registration

import android.util.Patterns
import com.example.bankcardbuilder.core.domain.AppException
import com.example.bankcardbuilder.core.domain.EmptyFieldException
import com.example.bankcardbuilder.core.domain.Field
import com.example.bankcardbuilder.core.domain.InvalidEmailException
import com.example.bankcardbuilder.core.domain.InvalidFieldException
import com.example.bankcardbuilder.core.domain.InvalidFieldFormatException
import com.example.bankcardbuilder.core.domain.InvalidPasswordException
import com.example.bankcardbuilder.core.domain.PasswordMismatchException
import com.example.bankcardbuilder.features.presentation.registration.util.capitalizeFirstLetter

data class RegistrationState(
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val photo: String? = null,
    val answer: String = "",
    val phoneNumber: String = "",
    val currentStep: RegistrationStep = RegistrationStep.SIGN_UP,
    val isPasswordVisible: Boolean = false,
    val isConfirmPasswordVisible: Boolean = false,
    val uiState: RegistrationUIState = RegistrationUIState.Empty
) {
    fun validateProfile() {
        if (firstName.isBlank()) throw EmptyFieldException(Field.NAME)
        if (lastName.isBlank()) throw EmptyFieldException(Field.LASTNAME)

        if (firstName.all { it.isUpperCase() }) throw InvalidFieldException(Field.NAME)
        if (lastName.all { it.isUpperCase() }) throw InvalidFieldException(Field.LASTNAME)

        val correctedName = firstName.capitalizeFirstLetter()
        val correctedSurname = lastName.capitalizeFirstLetter()

        if (firstName != correctedName) throw InvalidFieldFormatException(Field.NAME)
        if (lastName != correctedSurname) throw InvalidFieldFormatException(Field.LASTNAME)
    }

    fun validateEmailAndPassword() {
        if (email.isBlank()) throw EmptyFieldException(Field.EMAIL)
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) throw InvalidEmailException()
        if (password.isBlank()) throw EmptyFieldException(Field.PASSWORD)
        if (!password.contentEquals(confirmPassword)) throw PasswordMismatchException()
        if (password.length < 8) throw InvalidPasswordException()
    }

    fun validateSecurityQuestion() {
        if (answer.isBlank()) throw EmptyFieldException(Field.ANSWER)
        if (answer.all { it.isUpperCase() }) throw InvalidFieldException(Field.ANSWER)
        val correctedAnswer = answer.capitalizeFirstLetter()
        if (answer != correctedAnswer) throw InvalidFieldFormatException(Field.ANSWER)
    }

    fun validatePhoneNumber() {
        if (phoneNumber.isBlank()) throw EmptyFieldException(Field.PHONENUMBER)
        if (phoneNumber.length < 10) throw InvalidFieldFormatException(Field.PHONENUMBER)
    }
}

sealed class RegistrationStep {
    data object SIGN_UP : RegistrationStep()
    data object PROFILE : RegistrationStep()
    data object PHONE : RegistrationStep()
    data object SECURITY_QUESTION : RegistrationStep()
    data object SUCCESS_MAIN_PROFILE : RegistrationStep()
}

sealed class RegistrationUIState {
    data object Loading : RegistrationUIState()
    data object Success : RegistrationUIState()
    data class Error(val exception: AppException) : RegistrationUIState()
    data object Empty : RegistrationUIState()
}