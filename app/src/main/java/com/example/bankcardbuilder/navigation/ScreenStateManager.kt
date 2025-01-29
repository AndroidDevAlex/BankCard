package com.example.bankcardbuilder.navigation

import android.content.Context

abstract class ScreenStateManager(context: Context) {

    protected val navSharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

}