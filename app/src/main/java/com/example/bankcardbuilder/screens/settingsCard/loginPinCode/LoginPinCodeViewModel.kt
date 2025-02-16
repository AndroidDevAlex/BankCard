package com.example.bankcardbuilder.screens.settingsCard.loginPinCode

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bankcardbuilder.data.AccountsRepository
import com.example.bankcardbuilder.exeption.AppException
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

    fun verifyPinCode() {
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

    fun updateScreenState(newState: LoginPinCodeScreenState) {
        _uiState.value = newState
    }
}