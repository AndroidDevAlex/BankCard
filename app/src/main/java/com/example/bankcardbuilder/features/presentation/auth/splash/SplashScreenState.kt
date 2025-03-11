package com.example.bankcardbuilder.features.presentation.auth.splash


sealed class SplashScreenState {
    data object Loading : SplashScreenState()
    data object SignedIn : SplashScreenState()
    data object NotSignedIn : SplashScreenState()
}