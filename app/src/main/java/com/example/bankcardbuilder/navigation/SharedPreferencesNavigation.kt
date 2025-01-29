package com.example.bankcardbuilder.navigation

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SharedPreferencesNavigation @Inject constructor(
    @ApplicationContext appContext: Context
) : NavigationSettings, ScreenStateManager(appContext) {

    override fun saveCurrentScreen(state: ScreenState) {
        val stateString = when (state) {
            is ScreenState.Splash -> "splash"
            is ScreenState.LogIn -> "logIn"
            is ScreenState.MainProfile -> "mainProfile"
            is ScreenState.SignUp -> "signUp"
            is ScreenState.Profile -> "profile"
            is ScreenState.PhoneNumber -> "phoneNumber"
            is ScreenState.SecurityQuestion -> "securityQuestion"
        }
        navSharedPreferences.edit().putString(LAST_SCREEN, stateString).apply()
    }

    override fun getCurrentScreen(): ScreenState {
        return when (navSharedPreferences.getString(LAST_SCREEN, "logIn") ?: "logIn") {
            "splash" -> ScreenState.Splash
            "logIn" -> ScreenState.LogIn
            "mainProfile" -> ScreenState.MainProfile
            "signUp" -> ScreenState.SignUp
            "profile" -> ScreenState.Profile
            "phoneNumber" -> ScreenState.PhoneNumber
            "securityQuestion" -> ScreenState.SecurityQuestion
            else -> ScreenState.LogIn
        }
    }

    companion object {
        private const val LAST_SCREEN = "last_screen"
    }
}