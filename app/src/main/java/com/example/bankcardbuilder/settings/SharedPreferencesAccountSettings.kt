package com.example.bankcardbuilder.settings

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SharedPreferencesAccountSettings @Inject constructor(
    @ApplicationContext appContext: Context
) : AccountSettings, AppSettings(appContext) {

    override fun setCurrentUserEmail(email: String) {
        sharedPreferences.edit().putString(PREF_CURRENT_EMAIL, email).apply()
    }

    override fun getCurrentUserEmail(): String {
        return sharedPreferences.getString(PREF_CURRENT_EMAIL, AccountSettings.NO_ACCOUNT_EMAIL)
            ?: AccountSettings.NO_ACCOUNT_EMAIL
    }

    companion object {
         private const val PREF_CURRENT_EMAIL = "current_user_email"
    }
}