package com.example.bankcardbuilder.screens.auth.splash


sealed class SplashScreenState {
    data object Loading : SplashScreenState()
    data object SignedIn : SplashScreenState()
    data object NotSignedIn : SplashScreenState()
}