package com.example.bankcardbuilder.di

import com.example.bankcardbuilder.domain.AccountsRepository
import com.example.bankcardbuilder.data.repository.AccountsRepositoryImpl
import com.example.bankcardbuilder.data.local.AccountsDao
import com.example.bankcardbuilder.data.local.CardsDao
import com.example.bankcardbuilder.security.SecurityUtils
import com.example.bankcardbuilder.settings.AccountSettings
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideAccountsRepository(
        securityUtils: SecurityUtils,
        accountSettings: AccountSettings,
        accountsDao: AccountsDao,
        cardsDao: CardsDao
    ): AccountsRepository {
        return AccountsRepositoryImpl(
            securityUtils,
            accountSettings,
            accountsDao,
            cardsDao
        )
    }
}