package com.example.bankcardbuilder.screens.settingsCard.cardSettings

import androidx.compose.ui.graphics.Color
import com.example.bankcardbuilder.exeption.AppException
import com.example.bankcardbuilder.exeption.Field
import com.example.bankcardbuilder.exeption.InvalidFieldFormatException
import com.example.bankcardbuilder.ui.theme.Orange

data class CardSettingsState(
    val userName: String = "**** ****",
    val cardNumber: String = "**** **** **** ****",
    val expiryDate: String = "**/**",
    val cardCompany: String = "****",
    val selectedColor: Color = Orange,
    val pinCode: String = "",
    val currentStep: CardSettingsStep = CardSettingsStep.CARD_SETTINGS,
    val uiState: CardSettingsUIState = CardSettingsUIState.Empty
) {
    fun validateCardDetails() {
        if (userName.contains('*')) throw InvalidFieldFormatException(Field.NAME)

        if (cardNumber.contains('*') || cardNumber.length != 16) throw InvalidFieldFormatException(
            Field.CARDNUMBER
        )

        if (expiryDate.contains('*') || expiryDate.length != 4) throw InvalidFieldFormatException(
            Field.EXPIRYDATE
        )

        if (cardCompany.contains('*')) throw InvalidFieldFormatException(Field.CARDCOMPANY)
    }

    fun validatePinCode() {
        if (!pinCode.matches(Regex("\\d{5}"))) throw InvalidFieldFormatException(Field.PINCODE)
    }
}

sealed class CardSettingsStep {
    data object CARD_SETTINGS : CardSettingsStep()
    data object PIN_CODE : CardSettingsStep()
    data object SUCCESS_TO_MAIN_PROFILE : CardSettingsStep()
}

sealed class CardSettingsUIState {
    data object Loading : CardSettingsUIState()
    data object Success : CardSettingsUIState()
    data class Error(val exception: AppException) : CardSettingsUIState()
    data object Empty : CardSettingsUIState()
}