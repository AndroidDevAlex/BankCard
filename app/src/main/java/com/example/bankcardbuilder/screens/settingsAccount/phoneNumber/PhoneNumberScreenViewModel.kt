package com.example.bankcardbuilder.screens.settingsAccount.phoneNumber

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bankcardbuilder.data.AccountsRepository
import com.example.bankcardbuilder.exeption.AppException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class PhoneNumberScreenViewModel @Inject constructor(
    private val accountsRepository: AccountsRepository,
    @Named("IODispatcher") private val ioDispatcher: CoroutineDispatcher
): ViewModel() {

    private val _uiState = MutableStateFlow(PhoneNumberScreenState())
    val uiState: StateFlow<PhoneNumberScreenState> = _uiState

    fun savePhoneNumber() {
        val currentScreenState = _uiState.value
        viewModelScope.launch(ioDispatcher) {
            updateScreenState(currentScreenState.copy(stateUI = PhoneNumberUIState.Loading))
            delay(200)
            try {
                accountsRepository.setPhoneNumber(currentScreenState.phoneNumber)
                updateScreenState(currentScreenState.copy(stateUI = PhoneNumberUIState.Success))
            } catch (e: AppException) {
                updateScreenState(currentScreenState.copy(stateUI = PhoneNumberUIState.Error(e)))
            }
        }
    }

    fun updateScreenState(newState: PhoneNumberScreenState) {
        _uiState.value = newState
    }
}