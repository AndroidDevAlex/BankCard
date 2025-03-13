package com.example.bankcardbuilder.features.presentation.auth.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bankcardbuilder.features.domain.AccountsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class SplashViewModule @Inject constructor(
    private val accountsRepository: AccountsRepository,
    @Named("IODispatcher") private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _screenState = MutableStateFlow<SplashScreenState>(SplashScreenState.Loading)
    val screenState: StateFlow<SplashScreenState> = _screenState

    init {
        viewModelScope.launch(ioDispatcher) {
            val isSignedIn = accountsRepository.isSignedIn()
            if (isSignedIn) {
                _screenState.value = SplashScreenState.SignedIn
            } else {
                _screenState.value = SplashScreenState.NotSignedIn
            }
        }
    }
}