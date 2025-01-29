package com.example.bankcardbuilder.dataBase

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    version = 1,
    entities = [
        AccountDbEntity::class,
        CardDbEntity::class
    ]
)

abstract class AppDataBase : RoomDatabase() {

    abstract fun getAccountsDao(): AccountsDao

    abstract fun getCardsDao(): CardsDao
}