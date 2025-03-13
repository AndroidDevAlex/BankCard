package com.example.bankcardbuilder.features.presentation.mainProfile

sealed interface MainProfileAction {
    data object OnClickLogOut : MainProfileAction
    data class OnUpdatePhoto(val photoUri: String) : MainProfileAction
    data class OnToggleCardLock(val cardNumber: String) : MainProfileAction
    data object ClearError : MainProfileAction
    data object OnLogOut : MainProfileAction
    data object GoToCardSettingsScreen : MainProfileAction
    data class GoToLoginPinCodeScreen(val pinCode: String) : MainProfileAction
}