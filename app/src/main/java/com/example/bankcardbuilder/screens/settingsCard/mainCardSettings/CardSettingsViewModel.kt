package com.example.bankcardbuilder.screens.settingsCard.mainCardSettings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bankcardbuilder.util.Utils
import com.example.bankcardbuilder.data.AccountsRepository
import com.example.bankcardbuilder.exeption.AppException
import com.example.bankcardbuilder.models.CardInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class CardSettingsViewModel @Inject constructor(
    private val repository: AccountsRepository,
    @Named("IODispatcher") private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _screenState = MutableStateFlow(CardSettingsScreenState())
    val screenState: StateFlow<CardSettingsScreenState> = _screenState

    fun updateScreenState(newState: CardSettingsScreenState) {
        _screenState.value = newState
    }

    fun saveCardData() {
        viewModelScope.launch(ioDispatcher) {
            try {
                _screenState.value = _screenState.value.copy(uiState = CardSettingsUIState.Loading)

                val cardInfo = CardInfo(
                    cardNameUser = _screenState.value.userName,
                    cardNumber = _screenState.value.cardNumber,
                    expiryDate = _screenState.value.expiryDate,
                    cardCompany = _screenState.value.cardCompany,
                    cardColor = Utils.toHex(screenState.value.selectedColor)
                )

                repository.setCardData(cardInfo)

                _screenState.value = _screenState.value.copy(uiState = CardSettingsUIState.Success)
            } catch (e: AppException) {
                _screenState.value = _screenState.value.copy(uiState = CardSettingsUIState.Error(e))
            }
        }
    }
}