package com.example.bankcardbuilder.navigation

sealed class ScreenState {
    data object Splash : ScreenState()
    data object LogIn : ScreenState()
    data object MainProfile : ScreenState()
    data object SignUp : ScreenState()
    data object Profile : ScreenState()
    data object PhoneNumber : ScreenState()
    data object SecurityQuestion : ScreenState()
}