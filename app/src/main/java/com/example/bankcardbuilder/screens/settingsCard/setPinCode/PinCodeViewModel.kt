package com.example.bankcardbuilder.screens.settingsCard.setPinCode

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bankcardbuilder.data.AccountsRepository
import com.example.bankcardbuilder.exeption.AppException
import com.example.bankcardbuilder.screens.settingsCard.CardPinCodeScreenState
import com.example.bankcardbuilder.screens.settingsCard.PinCodeUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class PinCodeViewModel @Inject constructor(
    private val repository: AccountsRepository,
    @Named("IODispatcher") private val ioDispatcher: CoroutineDispatcher,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _uiState = MutableStateFlow(CardPinCodeScreenState())
    val uiState: StateFlow<CardPinCodeScreenState> = _uiState

    private val cardNumber: String = savedStateHandle["cardNumber"] ?: error("Card number is required")

    fun savePinCode() {
        val currentScreenState = _uiState.value
        viewModelScope.launch(ioDispatcher) {
            updateScreenState(currentScreenState.copy(stateUI = PinCodeUIState.Loading))
            try {
                repository.setPinCode(currentScreenState.pinCode, cardNumber)
                updateScreenState(currentScreenState.copy(stateUI = PinCodeUIState.Success))
            } catch (e: AppException) {
                updateScreenState(currentScreenState.copy(stateUI = PinCodeUIState.Error(e)))
            }
        }
    }

     fun updateScreenState(newState: CardPinCodeScreenState) {
        _uiState.value = newState
    }
}