package com.example.bankcardbuilder.screens.auth.logIn

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
class LogInViewModel @Inject constructor(
    private val accountsRepository: AccountsRepository,
    @Named("IODispatcher") private val ioDispatcher: CoroutineDispatcher,
) : ViewModel() {

    private val _screenState = MutableStateFlow(LogInScreenState())
    val screenState: StateFlow<LogInScreenState> = _screenState

    fun updateScreenState(newState: LogInScreenState) {
        _screenState.value = newState
    }

    fun signIn() {
        val currentScreenState = _screenState.value
        viewModelScope.launch(ioDispatcher) {
            updateScreenState(currentScreenState.copy(stateUI = LogInUIState.Loading))
            delay(200)
            try {
                accountsRepository.signIn(
                    email = currentScreenState.email,
                    password = currentScreenState.password.toCharArray()
                )
                updateScreenState(currentScreenState.copy(stateUI = LogInUIState.Success))
            } catch (e: AppException) {
                updateScreenState(currentScreenState.copy(stateUI = LogInUIState.Error(e)))
            }
        }
    }
}