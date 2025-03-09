package com.example.bankcardbuilder.di

import android.content.Context
import androidx.room.Room
import com.example.bankcardbuilder.data.local.AccountsDao
import com.example.bankcardbuilder.data.local.AppDataBase
import com.example.bankcardbuilder.data.local.CardsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Provides
    @Singleton
    fun provideBankCardDB(@ApplicationContext context: Context): AppDataBase {
        return Room.databaseBuilder(
            context,
            AppDataBase::class.java,
            "local_database"
        ).addMigrations(
            AppDataBase.MIGRATION_1_2,
            AppDataBase.MIGRATION_2_3,
            AppDataBase.MIGRATION_3_4
        )
            .build()
    }

    @Provides
    @Singleton
    fun provideAccountsDao(database: AppDataBase): AccountsDao {
        return database.getAccountsDao()
    }

    @Provides
    @Singleton
    fun provideCardsDao(database: AppDataBase): CardsDao {
        return database.getCardsDao()
    }
}