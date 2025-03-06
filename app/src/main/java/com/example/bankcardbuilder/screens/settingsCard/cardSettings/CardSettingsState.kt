package com.example.bankcardbuilder.screens.settingsCard.cardSettings

import androidx.compose.ui.graphics.Color
import com.example.bankcardbuilder.exeption.AppException
import com.example.bankcardbuilder.exeption.EmptyFieldException
import com.example.bankcardbuilder.exeption.Field
import com.example.bankcardbuilder.exeption.InvalidFieldFormatException
import com.example.bankcardbuilder.ui.theme.BrightOrange

data class CardSettingsState(
    val userName: String = "Your Name",
    val cardNumber: String = "**** **** **** ****",
    val expiryDate: String = "MM/YY",
    val cardPaySystem: String = "PaySystem",
    val selectedColor: Color = BrightOrange,
    val pinCode: String = "",
    val currentStep: CardSettingsStep = CardSettingsStep.CARD_SETTINGS,
    val uiState: CardSettingsUIState = CardSettingsUIState.Empty
) {
    fun validateCardDetails() {
        if (userName == "Your Name") throw InvalidFieldFormatException(Field.NAME)

        if (cardNumber == "**** **** **** ****") throw EmptyFieldException(
            Field.CARDNUMBER
        )
        if (cardNumber.filter { it.isDigit() }.length < 16) throw InvalidFieldFormatException(
            Field.CARDNUMBER
        )

        if (expiryDate == "MM/YY") throw EmptyFieldException(
            Field.EXPIRYDATE
        )
        if (expiryDate.filter { it.isDigit() }.length < 4) throw InvalidFieldFormatException(
            Field.EXPIRYDATE
        )

        if (cardPaySystem == "PaySystem") throw EmptyFieldException(Field.CARDPAYSYSTEM)
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