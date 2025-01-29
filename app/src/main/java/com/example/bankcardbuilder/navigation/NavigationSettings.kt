package com.example.bankcardbuilder.navigation

interface NavigationSettings {

    fun saveCurrentScreen(state: ScreenState)

    fun getCurrentScreen(): ScreenState
}