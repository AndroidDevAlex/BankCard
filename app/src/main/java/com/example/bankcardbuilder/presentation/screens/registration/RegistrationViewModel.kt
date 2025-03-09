package com.example.bankcardbuilder.presentation.screens.registration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bankcardbuilder.domain.AccountsRepository
import com.example.bankcardbuilder.exeption.AppException
import com.example.bankcardbuilder.presentation.entity.FullName
import com.example.bankcardbuilder.presentation.entity.SignUpData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named


@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val accountsRepository: AccountsRepository,
    @Named("IODispatcher") private val ioDispatcher: CoroutineDispatcher,
) : ViewModel() {

    private val _state = MutableStateFlow(RegistrationState())
    val state: StateFlow<RegistrationState> = _state

    fun validation() {
        val currentState = _state.value

        try {
            when (currentState.currentStep) {
                RegistrationStep.SIGN_UP -> currentState.validateEmailAndPassword()
                RegistrationStep.PROFILE -> currentState.validateProfile()
                RegistrationStep.PHONE -> currentState.validatePhoneNumber()
                RegistrationStep.SECURITY_QUESTION -> {
                    currentState.validateSecurityQuestion()
                    signUp()
                    return
                }

                RegistrationStep.SUCCESS_MAIN_PROFILE -> return
            }
            if (currentState.currentStep == RegistrationStep.SECURITY_QUESTION) {
                _state.value = currentState.copy(
                    uiState = RegistrationUIState.Success
                )
            } else {
                goToNextScreen()
            }

        } catch (e: AppException) {
            _state.value = currentState.copy(uiState = RegistrationUIState.Error(e))
        }
    }

    private fun goToNextScreen() {
        val nextStep = when (_state.value.currentStep) {
            RegistrationStep.SIGN_UP -> RegistrationStep.PROFILE
            RegistrationStep.PROFILE -> RegistrationStep.PHONE
            RegistrationStep.PHONE -> RegistrationStep.SECURITY_QUESTION
            RegistrationStep.SECURITY_QUESTION -> RegistrationStep.SUCCESS_MAIN_PROFILE
            RegistrationStep.SUCCESS_MAIN_PROFILE -> RegistrationStep.SUCCESS_MAIN_PROFILE
        }
        _state.value =
            _state.value.copy(currentStep = nextStep, uiState = RegistrationUIState.Empty)
    }

    fun goToPreviousStep() {
        val prevStep = when (_state.value.currentStep) {
            RegistrationStep.PROFILE -> RegistrationStep.SIGN_UP
            RegistrationStep.PHONE -> RegistrationStep.PROFILE
            RegistrationStep.SECURITY_QUESTION -> RegistrationStep.PHONE
            RegistrationStep.SUCCESS_MAIN_PROFILE -> RegistrationStep.SECURITY_QUESTION
            RegistrationStep.SIGN_UP -> RegistrationStep.SIGN_UP
        }
        _state.value =
            _state.value.copy(currentStep = prevStep, uiState = RegistrationUIState.Empty)
    }

    fun updateState(newState: RegistrationState) {
        _state.value = newState
    }

    fun clearError() {
        viewModelScope.launch(ioDispatcher) {
            delay(5000)
            _state.value = _state.value.copy(uiState = RegistrationUIState.Empty)
        }
    }

    private fun signUp() {
        val currentState = _state.value

        val signUpData = SignUpData(
            email = currentState.email,
            password = currentState.password.toCharArray(),
            confirmPassword = currentState.confirmPassword.toCharArray(),
            fullName = FullName(currentState.firstName, currentState.lastName),
            mobileNumber = currentState.phoneNumber,
            answer = currentState.answer,
            photo = currentState.photo,
            createdAt = System.currentTimeMillis()
        )

        viewModelScope.launch(ioDispatcher) {
            updateState(currentState.copy(uiState = RegistrationUIState.Loading))
            delay(200)
            try {
                accountsRepository.createAccount(signUpData)
                updateState(currentState.copy(uiState = RegistrationUIState.Success))

            } catch (e: AppException) {
                updateState(currentState.copy(uiState = RegistrationUIState.Error(e)))
            }
        }
    }
}