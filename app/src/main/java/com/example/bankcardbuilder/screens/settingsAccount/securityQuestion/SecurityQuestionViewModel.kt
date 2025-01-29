package com.example.bankcardbuilder.screens.settingsAccount.securityQuestion

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
class SecurityQuestionViewModel @Inject constructor(
    private val accountsRepository: AccountsRepository,
    @Named("IODispatcher") private val ioDispatcher: CoroutineDispatcher
): ViewModel() {

    private val _answer = MutableStateFlow("")
    val answer: StateFlow<String> = _answer

    private val _screenState = MutableStateFlow<SecurityQuestionScreenState>(
        SecurityQuestionScreenState.Empty)
    val screenState: StateFlow<SecurityQuestionScreenState> = _screenState

    fun saveAnswer() {
        viewModelScope.launch(ioDispatcher) {
            _screenState.value = SecurityQuestionScreenState.Loading
            try {
                accountsRepository.setAccountAnswer(_answer.value)
                _screenState.value = SecurityQuestionScreenState.Success
            } catch (e: AppException) {
                _screenState.value = SecurityQuestionScreenState.Error(e)
            }
        }
    }

    fun updateAnswer(newAnswer: String) {
        _answer.value = newAnswer
    }
}