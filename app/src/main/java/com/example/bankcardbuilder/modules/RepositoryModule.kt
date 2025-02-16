package com.example.bankcardbuilder.modules

import com.example.bankcardbuilder.data.AccountsRepository
import com.example.bankcardbuilder.data.AccountsRepositoryImpl
import com.example.bankcardbuilder.dataBase.AccountsDao
import com.example.bankcardbuilder.dataBase.CardsDao
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