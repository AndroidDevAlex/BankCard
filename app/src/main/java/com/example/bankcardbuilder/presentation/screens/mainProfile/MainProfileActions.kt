package com.example.bankcardbuilder.presentation.screens.mainProfile

data class MainProfileActions(
    val goToCardSettingsScreen: () -> Unit,
    val logOut: () -> Unit,
    val onClickLogOut: () -> Unit,
    val onPhotoChange: (String) -> Unit,
    val onLockToggle: (String) -> Unit,
    val goToLoginPinCodeScreen: (String) -> Unit,
    val clearError: () -> Unit
)