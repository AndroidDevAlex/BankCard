package com.example.bankcardbuilder.features.presentation.auth.logIn

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bankcardbuilder.features.domain.AccountsRepository
import com.example.bankcardbuilder.core.domain.AppException
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

    private fun updateScreenState(newState: LogInScreenState) {
        _screenState.value = newState
    }

    private fun signIn() {
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

    fun onAction(action: LogInAction) {
        when (action) {
            is LogInAction.OnEmailChange -> updateScreenState(screenState.value.copy(email = action.email))
            is LogInAction.OnPasswordChange -> updateScreenState(screenState.value.copy(password = action.password))
            is LogInAction.OnTogglePasswordVisibility -> updateScreenState(
                screenState.value.copy(
                    passwordVisible = !screenState.value.passwordVisible
                )
            )

            is LogInAction.OnSignInClick -> signIn()
            else -> Unit
        }
    }
}