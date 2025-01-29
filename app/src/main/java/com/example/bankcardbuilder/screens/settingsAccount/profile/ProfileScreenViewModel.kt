package com.example.bankcardbuilder.screens.settingsAccount.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bankcardbuilder.data.AccountsRepository
import com.example.bankcardbuilder.exeption.AppException
import com.example.bankcardbuilder.models.FullName
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class ProfileScreenViewModel @Inject constructor(
    private val accountsRepository: AccountsRepository,
    @Named("IODispatcher") private val ioDispatcher: CoroutineDispatcher
): ViewModel() {

    private val _uiState = MutableStateFlow(ProfileScreenState())
    val uiState: StateFlow<ProfileScreenState> = _uiState

    fun saveProfile() {
        val currentScreenState = _uiState.value
        viewModelScope.launch(ioDispatcher) {
            updateScreenState(currentScreenState.copy(stateUI = ProfileUIState.Loading))
            delay(200)
            try {
                accountsRepository.setUserName(
                    FullName(
                        name = currentScreenState.firstName,
                        surname = currentScreenState.lastName
                    )
                )
                 currentScreenState.photo?.let { accountsRepository.setAccountPhoto(it) }

                updateScreenState(currentScreenState.copy(stateUI = ProfileUIState.Success))
            } catch (e: AppException) {
                updateScreenState(currentScreenState.copy(stateUI = ProfileUIState.Error(e)))
            }
        }
    }

     fun updateScreenState(newState: ProfileScreenState) {
        _uiState.value = newState
    }
}