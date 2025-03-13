package com.example.bankcardbuilder.di

import com.example.bankcardbuilder.features.domain.AccountSettings
import com.example.bankcardbuilder.features.data.preferences.SharedPreferencesAccountSettings
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