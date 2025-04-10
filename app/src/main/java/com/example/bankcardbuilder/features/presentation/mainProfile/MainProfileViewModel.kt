package com.example.bankcardbuilder.features.presentation.mainProfile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bankcardbuilder.features.domain.AccountsRepository
import com.example.bankcardbuilder.core.domain.AppException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class MainProfileViewModel @Inject constructor(
    private val accountsRepository: AccountsRepository,
    @Named("IODispatcher") private val ioDispatcher: CoroutineDispatcher,
    @Named("MainDispatcher") private val mainDispatcher: CoroutineDispatcher,
) : ViewModel() {

    private val _screenState =
        MutableStateFlow(MainProfileScreenState(stateUI = MainProfileUiState.Empty))
    val screenState: StateFlow<MainProfileScreenState> = _screenState

    init {
        fetchUserProfile()
    }

    private fun fetchUserProfile() {
        viewModelScope.launch(ioDispatcher) {
            _screenState.value = _screenState.value.copy(stateUI = MainProfileUiState.Loading)

            try {
                val userName = accountsRepository.getUserName()
                val email = accountsRepository.getUserEmail()
                val mobilePhone = accountsRepository.getPhoneNumber()

                val userProfile = UserProfileInformation(
                    userName = "${userName.name} ${userName.surname}",
                    email = email,
                    mobilePhone = "+$mobilePhone",
                    cards = emptyList()
                )

                combine(
                    accountsRepository.getAccountPhoto(),
                    accountsRepository.getCardsFlow()
                ) { photo, cards ->

                    userProfile.copy(
                        photo = photo,
                        cards = cards
                    )
                }.collect { updatedProfile ->
                    _screenState.value = _screenState.value.copy(
                        userProfile = updatedProfile,
                        stateUI = MainProfileUiState.Success(userProfile = updatedProfile)
                    )
                }
            } catch (e: AppException) {
                _screenState.value = _screenState.value.copy(stateUI = MainProfileUiState.Error(e))
            }
        }
    }

    private fun toggleCardLock(cardNumber: String) {
        val currentState = _screenState.value
        val card = currentState.userProfile?.cards?.find { it.cardNumber == cardNumber }

        card?.let {
            viewModelScope.launch(ioDispatcher) {
                accountsRepository.toggleCardLock(cardNumber, !it.isLocked)
            }
        }
    }

    private fun clearError() {
        viewModelScope.launch(ioDispatcher) {
            delay(5000)
            _screenState.value = _screenState.value.copy(stateUI = MainProfileUiState.Empty)
        }
    }

    private fun updatePhoto(photoUri: String) {
        val currentScreenState = _screenState.value
        viewModelScope.launch(ioDispatcher) {
            try {
                accountsRepository.setAccountPhoto(photoUri)

                currentScreenState.userProfile?.let { userProfile ->
                    val updatedProfilePhoto = userProfile.copy(photo = photoUri)
                    _screenState.value = currentScreenState.copy(
                        stateUI = MainProfileUiState.Success(userProfile = updatedProfilePhoto)
                    )
                }
            } catch (e: AppException) {
                _screenState.value = currentScreenState.copy(
                    stateUI = MainProfileUiState.Error(e)
                )
            }
        }
    }

    private fun logOut(block: () -> Unit) {
        viewModelScope.launch(ioDispatcher) {
            try {
                accountsRepository.logOut()
                withContext(mainDispatcher) {
                    block.invoke()
                }
            } catch (e: AppException) {
                _screenState.value = MainProfileScreenState(stateUI = MainProfileUiState.Error(e))
            }
        }
    }

    fun onAction(action: MainProfileAction) {
        when (action) {
            is MainProfileAction.OnClickLogOut -> logOut(block = action.block)
            is MainProfileAction.OnUpdatePhoto -> updatePhoto(action.photoUri)
            is MainProfileAction.OnToggleCardLock -> toggleCardLock(action.cardNumber)
            is MainProfileAction.ClearError -> clearError()
            else -> Unit
        }
    }
}