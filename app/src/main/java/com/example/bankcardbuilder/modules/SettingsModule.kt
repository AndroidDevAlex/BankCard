package com.example.bankcardbuilder.modules

import com.example.bankcardbuilder.settings.AccountSettings
import com.example.bankcardbuilder.settings.SharedPreferencesAccountSettings
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class SettingsModule {

    @Binds
    @Singleton
    abstract fun bindApplicationSettings(
        applicationSettings: SharedPreferencesAccountSettings
    ): AccountSettings

}