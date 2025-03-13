package com.example.bankcardbuilder.features.domain

interface AccountSettings {

    fun getCurrentUserEmail(): String

    fun setCurrentUserEmail(email: String)

    companion object {
        const val NO_ACCOUNT_EMAIL = ""
    }
}