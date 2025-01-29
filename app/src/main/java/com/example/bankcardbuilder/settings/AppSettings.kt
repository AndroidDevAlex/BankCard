package com.example.bankcardbuilder.settings

import android.content.Context
import android.content.SharedPreferences

abstract class AppSettings(appContext: Context) {

    protected val sharedPreferences: SharedPreferences = appContext.getSharedPreferences("settings", Context.MODE_PRIVATE)

}