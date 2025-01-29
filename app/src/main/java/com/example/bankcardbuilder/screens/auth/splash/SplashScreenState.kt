package com.example.bankcardbuilder.screens.auth.splash

import com.example.bankcardbuilder.navigation.ScreenState

sealed class SplashScreenState {
    data object Loading : SplashScreenState()
    data class SignedIn(val screenState: ScreenState) : SplashScreenState()
    data object NotSignedIn : SplashScreenState()
}