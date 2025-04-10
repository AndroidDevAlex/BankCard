package com.example.bankcardbuilder.features.presentation.settingsCard.loginPinCode

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bankcardbuilder.features.domain.AccountsRepository
import com.example.bankcardbuilder.core.domain.AppException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class LoginPinCodeViewModel @Inject constructor(
    private val accountsRepository: AccountsRepository,
    @Named("IODispatcher") private val ioDispatcher: CoroutineDispatcher,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginPinCodeScreenState())
    val uiState: StateFlow<LoginPinCodeScreenState> = _uiState

    private val cardNumber: String =
        savedStateHandle["cardNumber"] ?: error("Card number is required")

    private fun verifyPinCode() {
        val currentScreenState = _uiState.value
        viewModelScope.launch(ioDispatcher) {
            updateScreenState(currentScreenState.copy(stateUI = LoginPinCodeUIState.Loading))
            try {
                val isValid =
                    accountsRepository.isPinCodeCorrect(cardNumber, currentScreenState.pinCode)
                if (isValid) {
                    accountsRepository.toggleCardLock(cardNumber, false)
                    updateScreenState(_uiState.value.copy(stateUI = LoginPinCodeUIState.Success))
                }
            } catch (e: AppException) {
                updateScreenState(currentScreenState.copy(stateUI = LoginPinCodeUIState.Error(e)))
            }
        }
    }

    private fun updateScreenState(newState: LoginPinCodeScreenState) {
        _uiState.value = newState
    }

    fun onAction(action: LoginPinCodeAction) {
        val currentState = _uiState.value

        when (action) {
            is LoginPinCodeAction.OnVerifyPinCodeClick -> verifyPinCode()
            is LoginPinCodeAction.OnPinCodeChange -> updateScreenState(currentState.copy(pinCode = action.pinCode))
            else -> Unit
        }
    }
}