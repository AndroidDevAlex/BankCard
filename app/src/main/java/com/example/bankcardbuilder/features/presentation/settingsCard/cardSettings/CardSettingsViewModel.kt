package com.example.bankcardbuilder.features.presentation.settingsCard.cardSettings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bankcardbuilder.features.domain.AccountsRepository
import com.example.bankcardbuilder.core.domain.AppException
import com.example.bankcardbuilder.features.domain.entity.CardInfo
import com.example.bankcardbuilder.core.util.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class CardSettingsViewModel @Inject constructor(
    private val repository: AccountsRepository,
    @Named("IODispatcher") private val ioDispatcher: CoroutineDispatcher,
) : ViewModel() {

    private val _state = MutableStateFlow(CardSettingsState())
    val state: StateFlow<CardSettingsState> = _state

    private fun validation() {
        val currentState = _state.value

        try {
            when (currentState.currentStep) {
                CardSettingsStep.CARD_SETTINGS -> currentState.validateCardDetails()
                CardSettingsStep.PIN_CODE -> {
                    currentState.validatePinCode()
                    saveCardSettings()
                    return
                }

                CardSettingsStep.SUCCESS_TO_MAIN_PROFILE -> return
            }
            if (currentState.currentStep != CardSettingsStep.PIN_CODE) {
                goToNextStep()
            }

        } catch (e: AppException) {
            _state.value = currentState.copy(uiState = CardSettingsUIState.Error(e))
        }
    }

    private fun goToNextStep() {
        val nextStep = when (_state.value.currentStep) {
            CardSettingsStep.CARD_SETTINGS -> CardSettingsStep.PIN_CODE
            CardSettingsStep.PIN_CODE -> CardSettingsStep.SUCCESS_TO_MAIN_PROFILE
            CardSettingsStep.SUCCESS_TO_MAIN_PROFILE -> CardSettingsStep.SUCCESS_TO_MAIN_PROFILE
        }
        _state.value =
            _state.value.copy(currentStep = nextStep, uiState = CardSettingsUIState.Empty)
    }

    private fun updateState(newState: CardSettingsState) {
        _state.value = newState
    }

    private fun goToPreviousStep() {
        val prevStep = when (_state.value.currentStep) {
            CardSettingsStep.PIN_CODE -> CardSettingsStep.CARD_SETTINGS
            CardSettingsStep.SUCCESS_TO_MAIN_PROFILE -> CardSettingsStep.PIN_CODE
            CardSettingsStep.CARD_SETTINGS -> CardSettingsStep.CARD_SETTINGS
        }
        _state.value =
            _state.value.copy(currentStep = prevStep, uiState = CardSettingsUIState.Empty)
    }

    private fun saveCardSettings() {
        val currentState = _state.value

        val cardInfo = CardInfo(
            cardNameUser = currentState.userName,
            cardNumber = currentState.cardNumber,
            expiryDate = currentState.expiryDate,
            cardPaySystem = currentState.cardPaySystem,
            cardColor = Utils.toHex(currentState.selectedColor),
            pinCode = currentState.pinCode

        )

        viewModelScope.launch(ioDispatcher) {
            updateState(currentState.copy(uiState = CardSettingsUIState.Loading))
            delay(200)
            try {
                repository.setCardData(cardInfo)
                updateState(currentState.copy(uiState = CardSettingsUIState.Success))
            } catch (e: AppException) {
                updateState(currentState.copy(uiState = CardSettingsUIState.Error(e)))
            }
        }
    }

    fun onAction(action: CardSettingsAction) {
        val currentState = _state.value

        when (action) {

            is CardSettingsAction.OnUserNameChanged -> updateState(currentState.copy(userName = action.userName))
            is CardSettingsAction.OnCardNumberChanged -> updateState(currentState.copy(cardNumber = action.cardNumber))
            is CardSettingsAction.OnExpiryDateChanged -> updateState(currentState.copy(expiryDate = action.expiryDate))
            is CardSettingsAction.OnCardPaySystemChanged -> updateState(
                currentState.copy(
                    cardPaySystem = action.cardPaySystem
                )
            )

            is CardSettingsAction.OnColorSelected -> updateState(currentState.copy(selectedColor = action.color))
            is CardSettingsAction.OnPinCodeChange -> updateState(currentState.copy(pinCode = action.pinCode))

            is CardSettingsAction.OnBackClick -> goToPreviousStep()
            is CardSettingsAction.OnNextClick -> validation()
            else -> Unit
        }
    }
}