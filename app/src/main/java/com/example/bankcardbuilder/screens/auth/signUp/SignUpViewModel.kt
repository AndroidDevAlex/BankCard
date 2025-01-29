package com.example.bankcardbuilder.screens.auth.signUp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bankcardbuilder.data.AccountsRepository
import com.example.bankcardbuilder.exeption.AppException
import com.example.bankcardbuilder.models.SignUpData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val accountsRepository: AccountsRepository,
    @Named("IODispatcher") private val ioDispatcher: CoroutineDispatcher,
) : ViewModel() {

    private val _screenState = MutableStateFlow(SignUpScreenState())
    val screenState: StateFlow<SignUpScreenState> = _screenState

    fun updateScreenState(newState: SignUpScreenState) {
        _screenState.value = newState
    }

    fun signUp() {
        val currentScreenState = _screenState.value
        val signUpData = SignUpData(
            email = currentScreenState.email,
            password = currentScreenState.password.toCharArray(),
            confirmPassword = currentScreenState.confirmPassword.toCharArray()
        )

        viewModelScope.launch(ioDispatcher) {
            updateScreenState(_screenState.value.copy(stateUI = SignUpUIState.Loading))
            delay(200)
            try {
                accountsRepository.signUp(signUpData)
                updateScreenState(_screenState.value.copy(stateUI = SignUpUIState.Success))
            } catch (e: AppException) {
                updateScreenState(_screenState.value.copy(stateUI = SignUpUIState.Error(e)))
            }
        }
    }
}