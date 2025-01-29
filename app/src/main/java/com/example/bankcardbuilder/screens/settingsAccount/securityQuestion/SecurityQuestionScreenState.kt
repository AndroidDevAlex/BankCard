package com.example.bankcardbuilder.screens.settingsAccount.securityQuestion

import com.example.bankcardbuilder.exeption.AppException

sealed class SecurityQuestionScreenState {
    data object Loading : SecurityQuestionScreenState()
    data object Success : SecurityQuestionScreenState()
    data class Error(val exception: AppException) : SecurityQuestionScreenState()
    data object Empty : SecurityQuestionScreenState()
}